package com.moft.xfapply.activity.logic;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.moft.xfapply.R;
import com.moft.xfapply.activity.bussiness.CommonBussiness;
import com.moft.xfapply.activity.bussiness.OfflineDataBussiness;
import com.moft.xfapply.app.Constant;
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.base.ViewLogic;
import com.moft.xfapply.logic.network.NetworkFileAsync;
import com.moft.xfapply.logic.network.NetworkFileAsync.OnNetworkFileResultListener;
import com.moft.xfapply.logic.setting.PrefSetting;
import com.moft.xfapply.model.common.IncOfflineProcess;
import com.moft.xfapply.model.entity.OfflineDBInfo;
import com.moft.xfapply.model.entity.OfflineDBUpdateDateInfo;
import com.moft.xfapply.task.DBTransferAsyncTask;
import com.moft.xfapply.task.LoadToCommonDBAsyncTask;
import com.moft.xfapply.utils.StorageUtil;
import com.moft.xfapply.utils.StringUtil;
import com.moft.xfapply.utils.WindowUtil;
import com.moft.xfapply.widget.dialog.CustomAlertDialog;
import com.moft.xfapply.widget.dialog.CustomAlertDialog.OnDoingListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DownloadLogic extends ViewLogic {

    public DownloadLogic(View v, Activity a) {
        super(v, a);
    }
    
    private List<OnDownloadLogicListener> mListener = new ArrayList<OnDownloadLogicListener>();

    private RelativeLayout rl_download = null; 
    private RelativeLayout rl_download_hint = null;
    
    private RelativeLayout re_operation = null;   
    private ImageView iv_item_operation = null;
    private RelativeLayout re_stop = null;
    private HorizontalScrollView sv_download = null;
    private TextView tvPer = null;
    private ProgressBar pb_progressbar = null;
    
    private Map<String, NetworkFileAsync> downloaders = new HashMap<String, NetworkFileAsync>(); 
    private OfflineDBInfo mOfflineDBInfo = null;
    
    private ProgressDialog dialog = null;
    private Dialog confirmOfflineDialog = null;
    
    private boolean isDealing = false;

    public void init() {
        sv_download = (HorizontalScrollView)getView().findViewById(R.id.sv_download);
        rl_download = (RelativeLayout) getView().findViewById(R.id.rl_download); 
        rl_download_hint = (RelativeLayout) getView().findViewById(R.id.rl_download_hint);

        //设置下载控件的高度，宽度（解决ScrollView容器下子控件不能填充父容器问题）
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(WindowUtil.getWindowsWidth(getActivity()), WindowUtil.dp2px(getActivity(), 48));
        rl_download.setLayoutParams(params);

        rl_download_hint.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                if(mOfflineDBInfo.getVersionNo() != null) {
                    rl_download_hint.setVisibility(View.GONE);
                    re_operation.setVisibility(View.VISIBLE);
                    sv_download.setVisibility(View.VISIBLE);
                    if ("downloading".equals(mOfflineDBInfo.getStatus())) {
                        re_stop.setVisibility(View.GONE);
                    }
                } else {
                    Toast.makeText(getActivity(), "无离线数据包", Toast.LENGTH_SHORT).show();
                }
            }
        });
        
        iv_item_operation = (ImageView) getView().findViewById(R.id.iv_item_operation); 
        tvPer = (TextView) getView().findViewById(R.id.tv_item_operation_per);
        pb_progressbar = (ProgressBar) getView().findViewById(R.id.pb_progressbar);

        re_operation = (RelativeLayout) getView().findViewById(R.id.re_operation); 
        re_operation.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((Integer)iv_item_operation.getTag()).intValue() == 0) {                        
                    doDownload();
                } else {
                    pauseDownload();
                }
            }
        });

        //停止下载数据离线包
        re_stop = (RelativeLayout) getView().findViewById(R.id.re_stop);
        re_stop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                stopDownload(view);
            }
        });

        dialog = new ProgressDialog(getActivity());
        String cityName = LvApplication.getInstance().getCityName();
        mOfflineDBInfo = OfflineDataBussiness.getInstance().getOfflineDBInfo(cityName);
        refreshDownloadBar();
    }

    private void updateOfflineDBInfo() {
        if ("finish".equals(mOfflineDBInfo.getStatus())) {
            sv_download.setVisibility(View.GONE);
            rl_download_hint.setVisibility(View.GONE);
        } else if ("downloading".equals(mOfflineDBInfo.getStatus()) || "pause".equals(mOfflineDBInfo.getStatus())) {
            sv_download.setVisibility(View.GONE);
            rl_download_hint.setVisibility(View.VISIBLE);

            iv_item_operation.setVisibility(View.VISIBLE);
            iv_item_operation.setBackgroundResource(R.drawable.icon_download);
            iv_item_operation.setTag(new Integer(0));

            tvPer.setText(mOfflineDBInfo.getPercent() + "%");
            pb_progressbar.setProgress(mOfflineDBInfo.getPercent());
        } else {
            sv_download.setVisibility(View.GONE);
            rl_download_hint.setVisibility(View.VISIBLE);

            iv_item_operation.setBackgroundResource(R.drawable.icon_download);
            iv_item_operation.setTag(new Integer(0));
        }
    }

    public void confirmOfflineDB() {
        String cityName = LvApplication.getInstance().getCityName();
        mOfflineDBInfo = OfflineDataBussiness.getInstance().getOfflineDBInfo(cityName);
        refreshDownloadBar();
        remindDownloadDialog();
    }

    private void refreshDownloadBar() {
        if (PrefSetting.getInstance().getOfflineDownload() && LvApplication.getInstance().isDownloadedOfflineDB()) {
            updateOfflineDBInfo();
        } else {
            sv_download.setVisibility(View.GONE);
            rl_download_hint.setVisibility(View.GONE);
        }
    }

    private void remindDownloadDialog() {
        if(StringUtil.isEmpty(mOfflineDBInfo.getStatus()) &&
                mOfflineDBInfo.getVersionNo() != null &&
                LvApplication.getInstance().isDownloadedOfflineDB()) {
            // 关闭正在提示的Dialog
            if (confirmOfflineDialog != null && confirmOfflineDialog.isShowing()) {
                confirmOfflineDialog.dismiss();
                confirmOfflineDialog = null;
            }

            String content = LvApplication.getInstance().getCityName() + "有最新离线包，请更新！" +
                    "\n建议通过WIFI环境下载离线包。";
            confirmOfflineDialog = CustomAlertDialog.show(getActivity(), content, new OnDoingListener() {
                @Override
                public void onOK() {
                    rl_download_hint.setVisibility(View.GONE);
                    re_operation.setVisibility(View.VISIBLE);
                    sv_download.setVisibility(View.VISIBLE);

                    iv_item_operation.setBackgroundResource(R.drawable.icon_download);
                    iv_item_operation.setTag(new Integer(0));
                    doDownload();
                }

                @Override
                public void onNG() {

                }
            });
        }
    }

    private void stopDownload(final View view) {
        CustomAlertDialog.show(getActivity(), "是否要终止离线数据包下载？", new OnDoingListener() {

            @Override
            public void onOK() {
                view.setVisibility(View.GONE);

                String urlStr = convertToURL(mOfflineDBInfo.getUrl());

                if(downloaders.containsKey(urlStr)) {
                    NetworkFileAsync nfa = downloaders.get(urlStr);
                    nfa.stop();
                    downloaders.remove(urlStr);
                }

                iv_item_operation.setBackgroundResource(R.drawable.icon_download);
                iv_item_operation.setTag(new Integer(0));
                tvPer.setText(0 + "%");
                pb_progressbar.setProgress(0);
                isDealing = false;
                mOfflineDBInfo.setPercent(0);
                mOfflineDBInfo.setStatus("");
                OfflineDataBussiness.getInstance().updateOfflineInfo(mOfflineDBInfo);
            }

            @Override
            public void onNG() {

            }
        });
    }

    private void pauseDownload() {
        iv_item_operation.setBackgroundResource(R.drawable.icon_download);
        iv_item_operation.setTag(new Integer(0));

        String urlStr = convertToURL(mOfflineDBInfo.getUrl());
        
        if (StringUtil.isEmpty(urlStr)) {
            return;
        }

        NetworkFileAsync nfa = downloaders.get(urlStr);
        nfa.pause();
        isDealing = false;

        mOfflineDBInfo.setStatus("pause");
        OfflineDataBussiness.getInstance().updateOfflineInfo(mOfflineDBInfo);
    }
    
    private void doDownload() {
        isDealing = true;
        iv_item_operation.setBackgroundResource(R.drawable.icon_download_pause);
        iv_item_operation.setTag(new Integer(1));
        re_stop.setVisibility(View.VISIBLE);

        String urlStr = convertToURL(mOfflineDBInfo.getUrl());
        
        if (StringUtil.isEmpty(urlStr)) {
            return;
        }

        final String dbName = LvApplication.getInstance().getCityName() + ".zip";
        final String dbPath = StorageUtil.getTempPath();

        NetworkFileAsync nfa = new NetworkFileAsync(urlStr, new OnNetworkFileResultListener() {
            @Override
            public void onSuccess(String result) {
                DBTransferAsyncTask task  = new DBTransferAsyncTask(new DBTransferAsyncTask.OnDBTransferListener() {
                    @Override
                    public void onPreExecute() {

                    }

                    @Override
                    public void onPostExecute(boolean success) {
                        if (success) {
                            tvPer.setText("完成");
                            isDealing = false;
                            mOfflineDBInfo.setPercent(100);
                            mOfflineDBInfo.setStatus("finish");
                            re_operation.setVisibility(View.GONE);
                            re_stop.setVisibility(View.GONE);
                            pb_progressbar.setProgress(0);
                            tvPer.setText("0%");
                            if (mListener != null) {
                                for (DownloadLogic.OnDownloadLogicListener listener : mListener) {
                                    if (listener != null) {
                                        listener.onDownloadFinish();
                                    }
                                }
                            }
                            OfflineDataBussiness.getInstance().updateOfflineInfo(mOfflineDBInfo);
                            loadOfflineData();
                            refreshDownloadBar();
                        } else {
                            Toast.makeText(getActivity(), "加载离线数据包失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                task.execute(dbPath + dbName, LvApplication.getInstance().getCityName() + ".db");
            }

            @Override
            public void onFailure(String msg) {
                iv_item_operation.setBackgroundResource(R.drawable.icon_download);
                iv_item_operation.setTag(new Integer(0));

                Toast.makeText(getActivity(),
                        "获取离线数据包失败，原因：" + msg, Toast.LENGTH_SHORT)
                        .show();
                isDealing = false;
            }

            @Override
            public void onProgress(int progress) {
                //解决多线程下载不能发stop事件问题
                if(re_stop.getVisibility() != View.GONE) {
                    tvPer.setText(progress + "%");
                    pb_progressbar.setProgress(progress);

                    mOfflineDBInfo.setPercent(progress);
                    if (isDealing) {
                        mOfflineDBInfo.setStatus("downloading");
                    }
                    OfflineDataBussiness.getInstance().updateOfflineInfo(mOfflineDBInfo);
                }
            }
        });
        nfa.execute(dbPath + dbName);
        downloaders.put(urlStr, nfa);
    }

    private String convertToURL(String url) {
        return "http://" + PrefSetting.getInstance().getServerIP() + ":" +
                PrefSetting.getInstance().getServerPort() + Constant.SERVICE_NAME + Constant.DB_NAME + url;
    }

    public void loadOfflineData() {
        //ID:T886049 19-02-26 【移动终端】终端与平台数据一致，实时同步（新建数据）。 王泉 开始
        final String city = LvApplication.getInstance().getCityName();

        LoadToCommonDBAsyncTask asyncTask = new LoadToCommonDBAsyncTask(new LoadToCommonDBAsyncTask.OnLoadToCommonDBListener() {
            @Override
            public void onPreExecute() {
                dialog.setMessage("正在加载离线数据包...");
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.setCancelable(false);
                dialog.show();

                isDealing = true;
            }

            @Override
            public void onProgressUpdate(String msg) {
                dialog.setMessage(msg);
            }

            @Override
            public void onPostExecute() {
                dialog.dismiss();
                isDealing = false;
                //ID:T886049 19-02-26 【移动终端】终端与平台数据一致，实时同步（新建数据）。 王泉
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            for (DownloadLogic.OnDownloadLogicListener listener : mListener) {
                                if (listener != null) {
                                    listener.onLoadOfflineFinish();
                                }
                            }
                        }
                        OfflineDataBussiness.getInstance().updateGetIncOfflineData();
                    }
                }, 1000);
            }
        });
        asyncTask.execute(city);
        //ID:T886049 19-02-26 【移动终端】终端与平台数据一致，实时同步（新建数据）。 王泉 结束
    }

    //ID:T886049 19-02-26 【移动终端】终端与平台数据一致，实时同步（新建数据）。 王泉
    public void stopDownload() {
        if(iv_item_operation != null && iv_item_operation.getTag() != null) {
            if (((Integer) iv_item_operation.getTag()).intValue() == 1) {
                pauseDownload();
            }
        }
    }

    public void addListener(OnDownloadLogicListener listener) {
        mListener.add(listener);
    }
    
    public void removeListener(OnDownloadLogicListener listener) {
        mListener.remove(listener);
    }

    public boolean isDealing() {
        return isDealing;
    }

    public void setDealing(boolean isDealing) {
        this.isDealing = isDealing;
    }

    public interface OnDownloadLogicListener{
        void onDownloadFinish();
        void onLoadOfflineFinish();
    }

    //ID:B925904 19-03-27 【应用终端】大量数据同步时报内存不足异常。 王泉
    public interface OnCheckUpdateOfflineDBListener {
        void onCheckUpdateOfflineDBFinish(boolean isUpdate, String belong);
    }
}
