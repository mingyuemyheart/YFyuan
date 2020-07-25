package com.moft.xfapply.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.moft.xfapply.R;
import com.moft.xfapply.activity.bussiness.CommonBussiness;
import com.moft.xfapply.activity.bussiness.DBUpdateBussiness;
import com.moft.xfapply.activity.bussiness.DeviceAuthBussiness;
import com.moft.xfapply.app.Constant;
import com.moft.xfapply.base.BaseActivity;
import com.moft.xfapply.crash.CrashHandler;
import com.moft.xfapply.logic.setting.PrefSetting;
import com.moft.xfapply.model.common.ExceptionCategory;
import com.moft.xfapply.update.UpdateManager;
import com.moft.xfapply.utils.DateUtil;
import com.moft.xfapply.utils.DbUtil;
import com.moft.xfapply.utils.StringUtil;
import com.moft.xfapply.utils.TelManagerUtil;
import com.moft.xfapply.widget.CompletedView;
import com.moft.xfapply.widget.dialog.CustomAlertDialog;

import java.util.Date;
import java.util.List;

public class PrepareActivity extends BaseActivity {
    private int mActionType = 0; //0设备鉴权  1apk版本检查 2离线库结构更新
    private int mAuthStatus = 0; //0等待鉴权 1已授权 2未授权 3授权中 4授权过期 5终端号错误

    private UpdateManager mManager = null;

    private ImageView iv_avatar = null;
    private View ll_action1 = null;
    private View ll_action2 = null;
    private View ll_action3 = null;
    private TextView tv_action1_title = null;
    private TextView tv_action1_status = null;
    private TextView tv_action1_tin1 = null;
    private TextView tv_action1_tin2 = null;
    private TextView tv_action1_tin3 = null;
    private View ll_action1_tin = null;

    private TextView tv_action2_title = null;
    private TextView tv_action2_status = null;
    private TextView tv_action2_tin2 = null;
    private TextView tv_action2_tin3 = null;
    private View ll_action2_tin = null;
    private CompletedView tasks_View;

    private TextView tv_action3_title = null;
    private TextView tv_action3_status = null;

    private TextView tv_setting_server = null;
    private UpdateManager.UpgradeVersionInfo upgradeVersionInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = View.inflate(this, R.layout.activity_prepare, null);
        setContentView(view);

        iv_avatar = (ImageView)findViewById(R.id.iv_avatar);
        ll_action1 = (View)findViewById(R.id.ll_action1);
        ll_action2 = (View)findViewById(R.id.ll_action2);
        ll_action3 = (View)findViewById(R.id.ll_action3);
        ll_action1_tin = (View)findViewById(R.id.ll_action1_tin);
        tv_action1_title = (TextView)findViewById(R.id.tv_action1_title);
        tv_action1_status = (TextView)findViewById(R.id.tv_action1_status);
        tv_action1_tin1 = (TextView)findViewById(R.id.tv_action1_tin1);
        tv_action1_tin2 = (TextView)findViewById(R.id.tv_action1_tin2);
        tv_action1_tin3 = (TextView)findViewById(R.id.tv_action1_tin3);
        tv_action1_tin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = tv_action1_tin2.getText().toString();
                if (StringUtil.isEmpty(content)) {
                    return;
                }
                String[] cons = content.split(" ");
                if (cons.length <= 1) {
                    return;
                }
                ClipboardManager myClipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
                ClipData myClip = ClipData.newPlainText("text", cons[1]);
                myClipboard.setPrimaryClip(myClip);

                Toast.makeText(PrepareActivity.this, "已复制终端号", Toast.LENGTH_SHORT).show();
            }
        });
        tv_action1_tin3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuthStatus = 0;
                refreshView();
            }
        });

        tasks_View = (CompletedView) findViewById(R.id.tasks_view);
        ll_action2_tin = (View)findViewById(R.id.ll_action2_tin);
        tv_action2_title = (TextView)findViewById(R.id.tv_action2_title);
        tv_action2_status = (TextView)findViewById(R.id.tv_action2_status);
        tv_action2_tin2 = (TextView)findViewById(R.id.tv_action2_tin2);
        tv_action2_tin3 = (TextView)findViewById(R.id.tv_action2_tin3);
        tv_action2_tin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(upgradeVersionInfo != null && !StringUtil.isEmpty(upgradeVersionInfo.prompt)) {
                    CustomAlertDialog.show(PrepareActivity.this, upgradeVersionInfo.prompt, new CustomAlertDialog.OnDoingListener() {
                        @Override
                        public void onOK() {
                            tasks_View.setVisibility(View.VISIBLE);
                            tv_action2_tin2.setClickable(false);
                            tv_action2_tin2.setText("更新中");
                            tv_action2_tin3.setVisibility(View.GONE);
                            doUpdateApk();
                        }

                        @Override
                        public void onNG() {

                        }
                    });
                } else {
                    tasks_View.setVisibility(View.VISIBLE);
                    tv_action2_tin2.setClickable(false);
                    tv_action2_tin2.setText("更新中");
                    tv_action2_tin3.setVisibility(View.GONE);
                    doUpdateApk();
                }
            }
        });
        tv_action2_tin3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doNext();
            }
        });

        tv_action3_title = (TextView) findViewById(R.id.tv_action3_title);
        tv_action3_status = (TextView) findViewById(R.id.tv_action3_status);

        tv_setting_server = (TextView)findViewById(R.id.tv_setting_server);
        tv_setting_server.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PrepareActivity.this, SettingServerActivity.class));
            }
        });
        refreshView();

        //上传日志文件到服务器
        CrashHandler.sendPreviousReportsToServer();
    }

    private void doNext() {
        startActivity(new Intent(PrepareActivity.this, LoginActivity.class));
        finish();
    }

    private void refreshAction() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshView();
            }
        }, 100);
    }

    private void doUpdateApk() {
        mManager.doUpdateApk(new UpdateManager.OnUpdateProgressListener() {
            @Override
            public void onProgress(int progress) {
                tasks_View.setProgress(progress);
            }

            @Override
            public void onError() {
                tv_action2_tin2.setClickable(true);
                tv_action2_tin2.setText("下载失败，点击重试");
                tv_action2_tin3.setVisibility(View.VISIBLE);
            }
        });
    }

    private void refreshView() {
        if (mActionType == 0) { //授权
            ll_action1.setVisibility(View.VISIBLE);
            ll_action2.setVisibility(View.GONE);
            ll_action3.setVisibility(View.GONE);
            if (mAuthStatus == 0) { //0等待鉴权
                iv_avatar.setImageResource(R.drawable.auth_ing);

                tv_action1_status.setVisibility(View.GONE);
                ll_action1_tin.setVisibility(View.GONE);

                tv_action1_title.setText("正在验证本终端授权状态...");

                // 检查授权状态
                checkAuth();
            } else if (mAuthStatus == 1) { //1已授权
                iv_avatar.setImageResource(R.drawable.auth_ok);
                tv_action1_title.setText("终端授权状态正常！");

                mActionType = 2;
                refreshAction();
            } else if (mAuthStatus == 2 || mAuthStatus == 3) { //2未授权 3授权中
                iv_avatar.setImageResource(R.drawable.auth_ng);
                tv_action1_status.setVisibility(View.VISIBLE);
                ll_action1_tin.setVisibility(View.VISIBLE);

                tv_action1_title.setText("");
                tv_action1_status.setText("终端未获得授权！");
                tv_action1_tin1.setText("请联系管理员，报终端号进行终端授权！");
                tv_action1_tin2.setText("点击复制 " + TelManagerUtil.getInstance().getDeviceId());
            } else if (mAuthStatus == 4) { //4授权过期
                iv_avatar.setImageResource(R.drawable.auth_ng);
                tv_action1_status.setVisibility(View.VISIBLE);
                ll_action1_tin.setVisibility(View.VISIBLE);

                tv_action1_title.setText("");
                tv_action1_status.setText("授权已过期！" +
                        DateUtil.format(new Date(PrefSetting.getInstance().getExpires()), "yyyy年MM月dd日"));
                tv_action1_tin1.setText("请联系管理员，报终端号重新进行终端授权！");
                tv_action1_tin2.setText("点击复制 " + TelManagerUtil.getInstance().getDeviceId());
            } else if (mAuthStatus == 5) { //5终端号错误
                iv_avatar.setImageResource(R.drawable.auth_ng);
                tv_action1_status.setVisibility(View.VISIBLE);
                ll_action1_tin.setVisibility(View.VISIBLE);

                tv_action1_title.setText("");
                tv_action1_status.setText("获取终端号出现错误，无法进行授权！");
                tv_action1_tin1.setText("请联系管理员！");
                tv_action1_tin2.setVisibility(View.GONE);
            }
        } else if (mActionType == 1) { //apk更新
            // 检查版本更新
            mManager = new UpdateManager(this);

            ll_action1.setVisibility(View.GONE);
            ll_action2.setVisibility(View.VISIBLE);
            ll_action3.setVisibility(View.GONE);
            iv_avatar.setImageResource(R.drawable.apk_update);

            tv_action2_title.setText("正在检查最新版本...");
            tv_action2_status.setText("当前版本号：" + mManager.getVersionName(this));
            ll_action2_tin.setVisibility(View.GONE);

            mManager.checkVersion(new UpdateManager.OnUpdateListener() {
                @Override
                public void onUpdateStatus(UpdateManager.UpgradeVersionInfo info) {
                    upgradeVersionInfo = info;
                    if(info.isUpdate) {
                        tv_action2_title.setText("最新版本号：" + mManager.getVersionName());
                        ll_action2_tin.setVisibility(View.VISIBLE);
                        if(!info.isForceUpdate) {
                            tv_action2_tin3.setVisibility(View.VISIBLE);
                        }
                    } else {
                        tv_action2_title.setText("已是最新版本");
                        ll_action2_tin.setVisibility(View.GONE);
                        doNext();
                    }
                }
            });
        } else if (mActionType == 2) { //离线库
            ll_action1.setVisibility(View.GONE);
            ll_action2.setVisibility(View.GONE);
            ll_action3.setVisibility(View.VISIBLE);

            iv_avatar.setImageResource(R.drawable.data_struct_upadate);

            tv_action3_title.setText("正在检查离线库版本...");
            int verCode = DBUpdateBussiness.getInstance().getDBVersionCode();
            if(verCode >= 0) {
                tv_action3_status.setText("当前版本号：" + verCode);
            } else {
                tv_action3_status.setText("当前版本号：未知");
            }
            //获取App版本失败（HomePath为NULL），导致恶意删除sycj目录
            if(verCode != -999) {
                DBUpdateBussiness.getInstance().checkUpgradeDB(new DBUpdateBussiness.OnDBUpdateListener() {
                    @Override
                    public void onDBUpdateInit() {
                        tv_action3_title.setText("正在同步...");
                        tv_action3_status.setText("已发现新的离线库");
                    }

                    @Override
                    public void onDBUpdateProgress(int value) {
                        tv_action3_title.setText("正在检查离线库版本..." + value + "%");
                    }

                    @Override
                    public void onDBUpdateFinish() {
                        updateDictionaryInfo();
                    }

                    @Override
                    public void onDBUpdateFailed(final List<ExceptionCategory> exception) {
                        tv_action3_title.setText("同步异常！正在备份数据...");
                        DBUpdateBussiness.getInstance().clearLowVersionDB();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateDictionaryInfo();
                            }
                        }, 500);
                    }
                });
            } else {
                Toast.makeText(PrepareActivity.this, "当前版本未知，启动失败！", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {

        }
    }

    private void updateDictionaryInfo() {
        tv_action3_title.setText("正在同步数据字典版本...");
        tv_action3_status.setText("离线库版本正常");
        DbUtil.getInstance().loadDB(Constant.DB_NAME_COMMON, Constant.DB_NAME_COMMON, R.raw.common);
        CommonBussiness.getInstance().requestGetDictionaryData(new CommonBussiness.OnUpdateDictionaryListener() {
            @Override
            public void onUpdateComplete() {
                tv_action3_title.setText("数据字典版本同步完成");
                mActionType = 1;
                refreshAction();
            }
        });
    }

    private void checkAuth() {
        DeviceAuthBussiness.getInstance().deviceAuthenticate(new DeviceAuthBussiness.OnDeviceAuthListener() {
            @Override
            public void onAuthCompleted() {
                mAuthStatus = 1;
                refreshView();
            }

            @Override
            public void onError(String err) {
                if ("终端号错误".equals(err)) {
                    mAuthStatus = 5;
                } else if ("未授权".equals(err)) {
                    mAuthStatus = 2;
                } else if ("授权中".equals(err)) {
                    mAuthStatus = 3;
                } else if ("授权过期".equals(err)) {
                    mAuthStatus = 4;
                } else if ("已授权".equals(err)) {
                    mAuthStatus = 1;
                }
                refreshView();
            }
        });
    }

}
