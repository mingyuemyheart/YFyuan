package com.moft.xfapply.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.moft.xfapply.R;
import com.moft.xfapply.activity.bussiness.CommonBussiness;
import com.moft.xfapply.activity.bussiness.OfflineDataBussiness;
import com.moft.xfapply.activity.logic.SearchLogic;
import com.moft.xfapply.app.Constant;
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.base.BaseFragmentActivity;
import com.moft.xfapply.event.CommandEvent;
import com.moft.xfapply.event.UpdateIncDataEvent;
import com.moft.xfapply.logic.setting.PrefSetting;
import com.moft.xfapply.model.common.IncOfflineProcess;
import com.moft.xfapply.model.common.IncOfflineTypeProcess;
import com.moft.xfapply.model.common.MsgNoticeInfo;
import com.moft.xfapply.model.entity.OfflineDBInfo;
import com.moft.xfapply.push.PushService;
import com.moft.xfapply.service.LocationService;
import com.moft.xfapply.utils.DbUtil;
import com.moft.xfapply.utils.LogUtils;
import com.moft.xfapply.utils.SystemUtil;
import com.moft.xfapply.widget.dialog.CustomAlertDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends BaseFragmentActivity {
    private AlertDialog dlg = null;
    private Fragment[] fragments;
    
    private FragmentMap mapFragment;
    private FragmentData dataFragment;
    private FragmentZqsl zqslFragment;
    private FragmentProfile profileFragment;

    private ImageView[] imagebuttons;
    private TextView[] textviews;
    private String[] fragNames = {"地图", "数据 ", "监管", "我"};
    
    private int currentTabIndex = 0;

    private RelativeLayout rl_operation = null;
    private RelativeLayout rl_sub_title = null;
    private ImageView iv_operation = null;

    private SearchLogic mSearchLogic = null;

    /**
     * 与服务端交互的Messenger
     */
    private Messenger service = null;
    private Messenger receiverMessenger = null;

    private ServiceConnection connection;

    private Timer offlineDataUpdateTimer;
    private final static int MSG_OFFLINE_DATA_UPDATE = 0;

    private class ReceiverHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.MSG_NOTIFY_SHARE:
                    EventBus.getDefault().post(new CommandEvent(CommandEvent.CMD_EXIT));
                    Bundle params = msg.getData();
                    MsgNoticeInfo info = (MsgNoticeInfo) params.getSerializable("msg_info");
                    if(info != null) {
                        showSendDialog(info, LvApplication.getInstance().getCityCode());
                    }
                    if(!SystemUtil.isForeground(LvApplication.getInstance(), getClass().getName())) {
                        SystemUtil.moveTaskToFront(LvApplication.getInstance(), getTaskId());
                    }
                    break;
                //ID:T886079 19-03-01 【推送】App显示界面时，直接推送到界面文言提示，可打开重点单位详细。 王泉
                case Constant.MSG_NOTIFY_OPEN_SHARE:
                    EventBus.getDefault().post(new CommandEvent(CommandEvent.CMD_EXIT));
                    Bundle params1 = msg.getData();
                    MsgNoticeInfo info1 = (MsgNoticeInfo) params1.getSerializable("msg_info");
                    if(info1 != null) {
                        if(dlg != null && dlg.isShowing()) {
                            dlg.dismiss();
                        }
                        onViewDetail(info1, LvApplication.getInstance().getCityCode());
                    }
                    if(!SystemUtil.isForeground(LvApplication.getInstance(), getClass().getName())) {
                        SystemUtil.moveTaskToFront(LvApplication.getInstance(), getTaskId());
                    }
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_OFFLINE_DATA_UPDATE:
                    String cityName = LvApplication.getInstance().getCityName();
                    OfflineDBInfo offlineDBInfo = OfflineDataBussiness.getInstance().getOfflineDBInfo(cityName);
                    if(offlineDBInfo != null) {
                        if (!"downloading".equals(offlineDBInfo.getStatus()) && !"pause".equals(offlineDBInfo.getStatus())) {
                            OfflineDataBussiness.getInstance().updateGetIncOfflineData();
                        }
                    } else {
                        // 返回的offlineDBInfo没有空的时候，所以不会走该case
                        Toast.makeText(MainActivity.this, "无离线数据库，不能进行同步离线数据操作！", Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogUtils.d("============================onCreate============================");

        EventBus.builder().logNoSubscriberMessages(false).sendNoSubscriberEvent(false).build();
        EventBus.getDefault().register(this);

        LvApplication.getInstance().initCommonData();
        //根据账户信息加载城市列表
        LvApplication.getInstance().loadCityDicList();
        //同步增量数据

        String cityName = LvApplication.getInstance().getCityName();
        OfflineDBInfo offlineDBInfo = OfflineDataBussiness.getInstance().getOfflineDBInfo(cityName);
        if(offlineDBInfo != null) {
            if ("downloading".equals(offlineDBInfo.getStatus())|| "pause".equals(offlineDBInfo.getStatus())) {
                LvApplication.getInstance().setDownloadedOfflineDB(true);
            } else {
                OfflineDataBussiness.getInstance().updateGetIncOfflineData();
            }
        }
        startTimer(true);

        // 因为平台没有推送功能，所以注释掉该段代码 START
//        LvApplication.getInstance().startPushService();
//        initBindService();
        // 因为平台没有推送功能，所以注释掉该段代码 END

        // 启动位置上报服务
        startService(new Intent(this, LocationService.class));

        //上传应用访问状态
        CommonBussiness.getInstance().uploadAppAccessRecord();

        initView();
    }

    private void initView() {
        TextView tv_select = (TextView) findViewById(R.id.tv_select);
        tv_select.setText(LvApplication.getInstance().getCityName());

        mapFragment = new FragmentMap();
        dataFragment = new FragmentData();
        zqslFragment = new FragmentZqsl();
        profileFragment = new FragmentProfile();
        
        fragments = new Fragment[] {
                mapFragment, dataFragment,
                zqslFragment, profileFragment };
        
        imagebuttons = new ImageView[4];
        imagebuttons[0] = (ImageView) findViewById(R.id.ib_verify);
        imagebuttons[1] = (ImageView) findViewById(R.id.ib_data);
        imagebuttons[2] = (ImageView) findViewById(R.id.ib_message);
        imagebuttons[3] = (ImageView) findViewById(R.id.ib_profile);
        imagebuttons[0].setSelected(true);
        
        textviews = new TextView[4];
        textviews[0] = (TextView) findViewById(R.id.tv_verify);
        textviews[1] = (TextView) findViewById(R.id.tv_data);
        textviews[2] = (TextView) findViewById(R.id.tv_message);
        textviews[3] = (TextView) findViewById(R.id.tv_profile);
        textviews[0].setText(fragNames[0]);
        textviews[1].setText(fragNames[1]);
        textviews[2].setText(fragNames[2]);
        textviews[3].setText(fragNames[3]);
        textviews[0].setTextColor(0xFFff9800);
        
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, mapFragment)
                .add(R.id.fragment_container, dataFragment)
                .add(R.id.fragment_container, profileFragment)
                .add(R.id.fragment_container, zqslFragment)
                .hide(dataFragment).hide(profileFragment)
                .hide(zqslFragment).show(mapFragment).commit();

        rl_operation = (RelativeLayout) findViewById(R.id.rl_operation);
        rl_operation.setVisibility(View.GONE);
        rl_sub_title = (RelativeLayout)findViewById((R.id.rl_sub_title));
        iv_operation = (ImageView) findViewById(R.id.iv_operation);

        mSearchLogic = new SearchLogic(getWindow().getDecorView(), this);
        mSearchLogic.init();
    }

    public void onTabClicked(View view) {
        int index = 0;

        int i = view.getId();
        if (i == R.id.re_verify) {
            index = 0;
        } else if (i == R.id.re_data) {
            index = 1;
        } else if (i == R.id.re_zqsl) {
            index = 2;
        } else if (i == R.id.re_profile) {
            index = 3;
        }

        showFragment(index);
    }
    
    public void showFragment(final int index) {
        rl_operation.setVisibility(View.GONE);
        
        if(0 == index) {
            rl_sub_title.setVisibility(View.VISIBLE);
        } else {
            rl_sub_title.setVisibility(View.GONE);
        }

        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager()
                    .beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fragment_container, fragments[index]);
            }
            trx.show(fragments[index]).commit();
        }
        imagebuttons[currentTabIndex].setSelected(false);
        
        imagebuttons[index].setSelected(true);
        textviews[currentTabIndex].setTextColor(0xFF999999);
        textviews[index].setTextColor(0xFFff9800);
        currentTabIndex = index;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fragments[currentTabIndex].onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (data != null && (data.hasExtra("neededMapChange") || data.hasExtra("MapSearchInfo"))) {
            if (currentTabIndex != 0) {
                showFragment(0);
                mapFragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("cityName", LvApplication.getInstance().getCityName());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState != null) {
            LvApplication.getInstance().setCityName(savedInstanceState.getString("cityName"));
        }
    }
    
    @Override
    protected void onResume() {
        super.onResume();
    }

    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                if (!mapFragment.onKeyDown(keyCode, event)) {
                    Toast.makeText(MainActivity.this, "再按一次退出程序",
                            Toast.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis();
                }
            } else {
                //ID:837499 19-01-09 终端统计逻辑变更，半小时没有操作，不计入使用时长 王泉
                CommonBussiness.getInstance().updateCurrentAccessRecordStatus(true);
                CommonBussiness.getInstance().uploadAppAccessRecord();
                finish();
                return false;
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.d("============================onDestroy============================");
        DbUtil.getInstance().closeAllFinalDb();

        stopTimer();
        //停止位置上报服务
        stopService(new Intent(this, LocationService.class));

//        unbindService(connection); // 因为平台没有推送功能，所以注释掉该段代码

        EventBus.getDefault().unregister(this);

        final Handler closeWaiHandler = new Handler();
        Runnable closeWaitRunnable= new Runnable() {
            private int count = 0;
            @Override
            public void run() {
                count++;
                if (count <= 6 &&
                        SystemUtil.hasServiceRunning(LvApplication.getContext())) {
                    closeWaiHandler.postDelayed(this, 50);
                } else {
                    System.exit(0);
                }
            }
        };
        closeWaiHandler.postDelayed(closeWaitRunnable, 0);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(CommandEvent event) {
        if(CommandEvent.CMD_NETWORK_CHANGED.equals(event.command)) {
            sendMessageToPushServer(Constant.MSG_NETWORK_CHANGED);

        } else if(CommandEvent.CMD_LOGOUT.equals(event.command)) { //ID:T886049 19-02-26 【移动终端】终端与平台数据一致，实时同步（新建数据）。 王泉
            dataFragment.stopDownload();
        } else if (CommandEvent.CMD_UPDATE_OFFLINE_MODE_CHANGED.equals(event.command)) {
            stopTimer();
            startTimer(false);
        }
    }

    private void initBindService() {
        receiverMessenger = new Messenger(new ReceiverHandler());
        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                service = new Messenger(iBinder);
                sendMessageToPushServer(Constant.MSG_REGISTER_CLIENT);
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                service = null;
            }
        };
        //当前Activity绑定服务端
        bindService(new Intent(MainActivity.this, PushService.class), connection, Context.BIND_AUTO_CREATE);
    }

    private void sendMessageToPushServer(int command) {
        // 创建与服务交互的消息实体Message
        Message msg = Message.obtain(null, command);
        //把接收服务器端的回复的Messenger通过Message的replyTo参数传递给服务端
        msg.replyTo = receiverMessenger;
        try {
            service.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void onViewDetail(final MsgNoticeInfo info, final String city) {
        Intent intent = new Intent(MainActivity.this, WsDetailActivity.class);
        intent.putExtra("type", info.getNoticeEntityType());
        intent.putExtra("key", info.getNoticeEntityUuid());
        intent.putExtra("city", city);

        startActivity(intent);
    }

    //ID:T886079 19-03-01 【推送】App显示界面时，直接推送到界面文言提示，可打开重点单位详细。 王泉
    private void showSendDialog(final MsgNoticeInfo info, final String city) {
        String content = "有新的重点单位推送，请确认是否进行查看？";
        if (dlg == null) {
            dlg = new AlertDialog.Builder(this).create();
        }
        CustomAlertDialog.show(dlg, content, new CustomAlertDialog.OnDoingListener() {
            @Override
            public void onOK() {
                onViewDetail(info, city);
                cancelNotification(info.getId());
            }

            @Override
            public void onNG() {
                cancelNotification(info.getId());
            }
        }, false);
    }

    //ID:T886079 19-03-01 【推送】App显示界面时，直接推送到界面文言提示，可打开重点单位详细。 王泉
    private void cancelNotification(int id) {
        NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(id);
        return;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UpdateIncDataEvent event) {
        if(event.getBelongtoGroup().equals(LvApplication.getInstance().getCityCode())) {
            updateLoadingUpdatePanel(event.getBelongtoGroup());
        }
    }

    private void updateLoadingUpdatePanel(String belongtoGroup) {
        IncOfflineProcess process = OfflineDataBussiness.getInstance().getIncOfflineProcess(belongtoGroup);
        if (process == null) {
            return;
        }
        View rl_process = findViewById(R.id.rl_process);
        ProgressBar loading_process = (ProgressBar) findViewById(R.id.loading_process);
        ImageView loading_error = (ImageView) findViewById(R.id.loading_error);
        rl_process.setTag(belongtoGroup);
        if (process.getStatus() == IncOfflineProcess.ProcessStatus.EXECUTING) {
            loading_process.setVisibility(View.VISIBLE);
            loading_error.setVisibility(View.GONE);
            rl_process.setClickable(false);
        } else if (process.getStatus() == IncOfflineProcess.ProcessStatus.FINISH) {
            loading_process.setVisibility(View.GONE);
            int failTaskCount = getFailTaskCount(process.getTypeProcesses());
            if (failTaskCount > 0) {
                loading_error.setVisibility(View.VISIBLE);
                rl_process.setClickable(true);
            } else {
                rl_process.setClickable(false);
            }
        } else if (process.getStatus() == IncOfflineProcess.ProcessStatus.EXCEPTION) {
            loading_process.setVisibility(View.GONE);
            loading_error.setVisibility(View.VISIBLE);
            rl_process.setClickable(true);
        } else {
            loading_process.setVisibility(View.GONE);
            rl_process.setClickable(false);
        }
    }

    //ID:T886049 19-02-26 【移动终端】终端与平台数据一致，实时同步（新建数据）。 王泉
    private String getUpdateProcessText(List<IncOfflineTypeProcess> processes) {
        int finishCount = 0;
        for(IncOfflineTypeProcess item : processes) {
            if(item.getStatus() == IncOfflineTypeProcess.ProcessStatus.FINISH) {
                ++finishCount;
            }
        }
        return String.format("%d/%d", finishCount, processes.size());
    }

    //ID:T886049 19-02-26 【移动终端】终端与平台数据一致，实时同步（新建数据）。 王泉
    private int getFailTaskCount(List<IncOfflineTypeProcess> processes) {
        int failCount = 0;
        for(IncOfflineTypeProcess item : processes) {
            if(item.getStatus() == IncOfflineTypeProcess.ProcessStatus.FINISH && item.getExceptions() != null && item.getExceptions().size() > 0) {
                ++failCount;
            }
        }
        return failCount;
    }

    public void onUpdatePanelClick(View view) {
        ImageView loading_error = (ImageView) findViewById(R.id.loading_error);
        if (loading_error.getVisibility() == View.VISIBLE) {
            Toast.makeText(MainActivity.this, "最新数据同步失败,重新启动会重试",
                    Toast.LENGTH_SHORT).show();
        }

//        String belongtoGroup = (String)view.getTag();
//        if(!StringUtil.isEmpty(belongtoGroup)) {
//            IncOfflineProcess process = OfflineDataBussiness.getInstance().getIncOfflineProcess(belongtoGroup);
//            if (process == null) {
//                return;
//            }
//            if(process.getStatus() == IncOfflineProcess.ProcessStatus.FINISH) {
//                Intent intent = new Intent();
//                intent.setClass(this, UpdateIncDataDetailActivity.class);
//                intent.putExtra("belongtoGroup", belongtoGroup);
//                startActivity(intent);
//            }
//        }
    }

    private void stopTimer() {
        if (offlineDataUpdateTimer != null) {
            offlineDataUpdateTimer.cancel();
            offlineDataUpdateTimer = null;
        }
    }

    private void startTimer(boolean hasDelay) {
        String tmpPeriodTime = PrefSetting.getInstance().getUpdateOfflineDataMode();
        if ("-1".equals(tmpPeriodTime)) {
            // 不更新
            return;
        }

        long delayTime = 0;
        long periodTime = Long.parseLong(tmpPeriodTime);
        if (hasDelay) {
            delayTime = periodTime;
        }

        if (offlineDataUpdateTimer == null) {
            offlineDataUpdateTimer = new Timer();
        }

        offlineDataUpdateTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(MSG_OFFLINE_DATA_UPDATE);
            }
        }, delayTime, periodTime);
    }

}
