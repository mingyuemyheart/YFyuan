package com.moft.xfapply.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.moft.xfapply.R;
import com.moft.xfapply.activity.logic.DetailLogic;
import com.moft.xfapply.activity.logic.FireEngineLogic;
import com.moft.xfapply.activity.logic.SingalLogic;
import com.moft.xfapply.activity.logic.ZddwLogic;
import com.moft.xfapply.app.Constant;
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.base.BaseActivity;
import com.moft.xfapply.event.CommandEvent;
import com.moft.xfapply.logic.network.OkHttpClientManager;
import com.moft.xfapply.logic.setting.PrefUserInfo;
import com.moft.xfapply.model.base.IGeomElementInfo;
import com.moft.xfapply.model.common.Dictionary;
import com.moft.xfapply.model.entity.EnterpriseFourceDBInfo;
import com.moft.xfapply.model.external.AppDefs;
import com.moft.xfapply.model.external.result.SimpleRestResult;
import com.moft.xfapply.utils.GsonUtil;
import com.moft.xfapply.utils.StringUtil;
import com.moft.xfapply.widget.dialog.CustomAlertDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

public class WsDetailActivity extends BaseActivity implements
        DetailLogic.OnDetailLogicListener {
    private ProgressDialog dialog;

    private TextView tv_title = null;

    private RelativeLayout rl_refresh = null;
    private RelativeLayout rl_edit = null;
    private RelativeLayout rl_delete = null;
    private RelativeLayout rl_new = null;
    private RelativeLayout rl_save = null;
    private RelativeLayout rl_refresh1 = null;
    private LinearLayout re_report_error = null;

    private ImageView iv_refresh = null;
    private ImageView iv_edit = null;
    private ImageView iv_delete = null;
    private ImageView iv_new = null;
    private ImageView iv_save = null;

    private ImageView iv_refresh_no = null;
    private ImageView iv_edit_no = null;
    private ImageView iv_delete_no = null;
    private ImageView iv_new_no = null;
    private ImageView iv_save_no = null;

    private DetailLogic mDetailLogic = null;
    private boolean isLoadingData = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ws_detail);
        EventBus.builder().logNoSubscriberMessages(false).sendNoSubscriberEvent(false).build();
        EventBus.getDefault().register(this);
        dialog = new ProgressDialog(this);

        dialog.setMessage("正在加载数据...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(initData()) {
                    initView();
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                } else {
                    isLoadingData = true;
                    getEntityData();
                }
                if (tv_title != null) {
                    tv_title.setText(mDetailLogic.getTypeName());
                }
            }
        }, 100);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private boolean initData() {
        boolean ret = false;
        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        String key = intent.getStringExtra("key");
        String cityCode = intent.getStringExtra("city");
        boolean isHistory = intent.getBooleanExtra("his", false);

        if (StringUtil.isEmpty(type)) {
            return ret;
        }

        // 重点单位的逻辑处理类
        if (AppDefs.CompEleType.KEY_UNIT.toString().equals(type)) {
            mDetailLogic = new ZddwLogic(getWindow().getDecorView(), this);
        } else if (AppDefs.CompEleType.FIRE_VEHICLE.toString().equals(type)) {
            mDetailLogic = new FireEngineLogic(getWindow().getDecorView(), this);
        } else {
            mDetailLogic = new SingalLogic(getWindow().getDecorView(), this);
        }

        re_report_error = (LinearLayout)this.findViewById(R.id.re_report_error);
        if (AppDefs.CompEleType.JOINT_FORCE.name().equals(type)) {  // 联动力量不显示纠错功能
            re_report_error.setVisibility(View.GONE);
        } else {
            re_report_error.setVisibility(View.VISIBLE);
        }

        mDetailLogic.setType(type);
        mDetailLogic.setKey(key);
        mDetailLogic.setCityCode(cityCode);
        mDetailLogic.setHistory(isHistory);
        mDetailLogic.setListener(this);
        ret = mDetailLogic.init();

        return ret;
    }

    private void initView() {
        tv_title = (TextView) this.findViewById(R.id.tv_title);
        tv_title.setText(mDetailLogic.getTypeName());
        tv_title.setFocusable(true);
        tv_title.setFocusableInTouchMode(true);

        rl_refresh = (RelativeLayout) this.findViewById(R.id.rl_refresh);  
        rl_edit = (RelativeLayout) this.findViewById(R.id.rl_edit);        
        rl_delete = (RelativeLayout) this.findViewById(R.id.rl_delete);
        rl_new = (RelativeLayout) this.findViewById(R.id.rl_new);
        rl_save = (RelativeLayout) this.findViewById(R.id.rl_save);
        rl_refresh1 = (RelativeLayout)this.findViewById(R.id.rl_refresh1);

        iv_refresh = (ImageView) this.findViewById(R.id.iv_refresh);
        iv_edit = (ImageView) this.findViewById(R.id.iv_edit);
        iv_delete = (ImageView) this.findViewById(R.id.iv_delete);
        iv_new = (ImageView) this.findViewById(R.id.iv_new);
        iv_save = (ImageView) this.findViewById(R.id.iv_save);

        iv_refresh_no = (ImageView) this.findViewById(R.id.iv_refresh_no);
        iv_edit_no = (ImageView) this.findViewById(R.id.iv_edit_no);
        iv_delete_no = (ImageView) this.findViewById(R.id.iv_delete_no);
        iv_new_no = (ImageView) this.findViewById(R.id.iv_new_no);
        iv_save_no = (ImageView) this.findViewById(R.id.iv_save_no);

        rl_new.setVisibility(View.GONE);
        setOperationDisable(rl_new, iv_new, iv_new_no);
        setOperationDisable(rl_delete, iv_delete, iv_delete_no);

        if (mDetailLogic.isEditDisable()) {
            setOperationDisable(rl_edit, iv_edit, iv_edit_no);
        } else {
            setOperationEnable(rl_edit, iv_edit, iv_edit_no);
        }

        if (mDetailLogic.isRefreshDisable()) {
            rl_refresh1.setVisibility(View.GONE);
            setOperationDisable(rl_refresh, iv_refresh, iv_refresh_no);
        } else {
            rl_refresh1.setVisibility(View.VISIBLE);
            setOperationEnable(rl_refresh, iv_refresh, iv_refresh_no);
        }

        if (mDetailLogic.isSaveDisable()) {
            setOperationDisable(rl_save, iv_save, iv_save_no);
        } else {
            setOperationEnable(rl_save, iv_save, iv_save_no);
        }

        mDetailLogic.initView();
    }

    private void setOperationEnable(View layout, ImageView iv_enable, ImageView iv_disable) {
        layout.setEnabled(true);
        iv_enable.setVisibility(View.VISIBLE);
        iv_disable.setVisibility(View.INVISIBLE);
    }

    private void setOperationDisable(View layout, ImageView iv_enable, ImageView iv_disable) {
        layout.setEnabled(false);
        iv_enable.setVisibility(View.INVISIBLE);
        iv_disable.setVisibility(View.VISIBLE);
    }

    public void doReportError(View v) {
        if (mDetailLogic == null || mDetailLogic.getGeomEleDBInfo() == null) {
            return;
        }

        LayoutInflater inflater = LayoutInflater.from(this);
        View fourView = inflater.inflate(R.layout.popup_feedback, null);
        final AlertDialog dlg = new AlertDialog.Builder(this).create();
        dlg .setView((getLayoutInflater().inflate(R.layout.popup_feedback, null)));
        dlg .show();
        dlg .getWindow().setContentView(fourView);

        final IGeomElementInfo geo = mDetailLogic.getGeomEleDBInfo();
        String eleType = geo.getEleType();
        if (geo != null && geo instanceof EnterpriseFourceDBInfo) {
            EnterpriseFourceDBInfo ef = (EnterpriseFourceDBInfo)geo;
            eleType += ef.getType();
        }

        TextView tv_content = (TextView) fourView.findViewById(R.id.tv_eletype);
        for (Dictionary dic : LvApplication.getInstance().getEleTypeDicList()) {
            if (dic.getCode().equals(eleType)) {
                tv_content.setText(dic.getValue());
                break;
            }
        }

        TextView tv_name = (TextView) fourView.findViewById(R.id.tv_name);
        tv_name.setText(geo.getName());

        final EditText et_content = (EditText) fourView.findViewById(R.id.et_content);

        TextView re_report = (TextView) fourView.findViewById(R.id.re_report);
        re_report.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String content = et_content.getText().toString().trim();
                if (StringUtil.isEmpty(content)) {
                    Toast.makeText(WsDetailActivity.this,
                            "请输入问题详情！", Toast.LENGTH_SHORT).show();
                    return;
                }
                doReportError(geo, content, dlg);
            }
        });

        View re_cancel = fourView.findViewById(R.id.re_cancel);
        re_cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dlg.dismiss();
            }
        });
    }

    private void doReportError(IGeomElementInfo geo, String content, final AlertDialog parentDialog) {
        final ProgressDialog dialog = new ProgressDialog(this);
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        dialog.setMessage("正在反馈问题...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.show();

        Map<String, String> params = new HashMap<>();
        params.put("entityType", geo.getEleType());
        params.put("entityUuid", geo.getUuid());
        params.put("departmentUuid", geo.getDepartmentUuid());
        params.put("name", geo.getName());
        params.put("summary", content);
        params.put("createPerson", PrefUserInfo.getInstance().getUserInfo("real_name"));

        final Gson gson = GsonUtil.create();
        OkHttpClientManager http = OkHttpClientManager.getInstance();
        http.postAsyn(http.convertToURL(Constant.METHOD_CREATE_PROBLEM),
                new OkHttpClientManager.ResultCallback<SimpleRestResult>() {
            @Override
            public void onError(Request request, Exception e) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                Toast.makeText(WsDetailActivity.this, "反馈问题失败！",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(SimpleRestResult result) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                if (result != null && result.isSuccess()) {
                    Toast.makeText(WsDetailActivity.this, "反馈问题成功！",
                            Toast.LENGTH_SHORT).show();
                    if (parentDialog != null && parentDialog.isShowing()) {
                        parentDialog.dismiss();
                    }
                } else {
                    Toast.makeText(WsDetailActivity.this, "反馈问题失败！",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }, gson.toJson(params));
    }
    
    public void doRefresh(View v) {
        String content = "是否从服务器获取最新数据？";
        CustomAlertDialog.show(this, content, new CustomAlertDialog.OnDoingListener() {

            @Override
            public void onOK() {
                onRefresh(); // ID:886048 数据详细界面，不提示是否更新最新版本数据，自动更新最新数据 2019-02-22 王旭
            }

            @Override
            public void onNG() {
            }
        });
    }

    public void doEdit(View v) {
        setOperationDisable(rl_edit, iv_edit, iv_edit_no);
        setOperationDisable(rl_refresh, iv_refresh, iv_refresh_no);
        setOperationEnable(rl_delete, iv_delete, iv_delete_no);
        setOperationEnable(rl_save, iv_save, iv_save_no);
        rl_refresh1.setVisibility(View.GONE);

        mDetailLogic.doEdit();
    }

    public void doDelete(View v) {
        String content = "确认要删除本条数据吗？";

        CustomAlertDialog.show(this, content, new CustomAlertDialog.OnDoingListener() {

            @Override
            public void onOK() {
                setOperationEnable(rl_save, iv_save, iv_save_no);
                mDetailLogic.doDelete();
            }

            @Override
            public void onNG() {
            }
        });
    }

    public void doMore(View v) {
        mDetailLogic.doMore();
    }

    public void doSave(View v) {
        if (!mDetailLogic.isChanged()) {
            Toast.makeText(this, "数据未被修改！", Toast.LENGTH_SHORT).show();
            return;
        }

        dialog.setMessage("正在保存...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.show();

        mDetailLogic.doSave();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if (resultCode != RESULT_OK) {
            return;
        }

        mDetailLogic.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            back(null);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        mDetailLogic.destroy();
    }
    
    public void back(View view) {
        if (mDetailLogic.isChanged()) {
            String content = "退出后修改的内容将丢失，是否确认退出？";

            CustomAlertDialog.show(this, content, new CustomAlertDialog.OnDoingListener() {

                @Override
                public void onOK() {
                    mDetailLogic.doFinish(false);
                }

                @Override
                public void onNG() {
                }
            });

            return;
        }

        mDetailLogic.doFinish(false);
    }

    @Override
    public void onSaveSuccess() {
        Toast.makeText(this, "数据已保存到本地！", Toast.LENGTH_SHORT).show();
        dialog.dismiss();

        mDetailLogic.doFinish(false);
    }

    @Override
    public void onSubmitSuccess() {
        Toast.makeText(this, "数据已成功提交至服务器！",
                Toast.LENGTH_SHORT).show();
        dialog.dismiss();

        mDetailLogic.doFinish(false);
    }

    @Override
    public void onSubmitFailure(String msg) {
        Toast.makeText(this, msg,
                Toast.LENGTH_SHORT).show();
        dialog.dismiss();

        mDetailLogic.doFinish(false);
    }

    /*
    * @Auther 王旭
    * @Date 2019-02-22
    * @Description ID:886048 数据详细界面，不提示是否更新最新版本数据，自动更新最新数据
    * */
    @Override
    public void onRefresh() {
        dialog.setMessage("正在加载数据...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.show();

        mDetailLogic.doRefresh();
    }

    @Override
    public void onRefreshSuccess() {
        if (isLoadingData) {
            initView();
            isLoadingData = false;
        } else {
            initView();
            Toast.makeText(this, "加载成功", Toast.LENGTH_SHORT).show();
        }
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void onRefreshFailure() {
        dialog.dismiss();
        Toast.makeText(this, "加载失败", Toast.LENGTH_SHORT).show();
    }

    private void getEntityData() {
        mDetailLogic.getEntityData();  // 数据初始化异常后，会向后台进行数据请求并重新刷新页面
    }

    private Handler mHandler = new Handler();
    @Override
    public void onScrollButtom() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                ScrollView sv = (ScrollView) findViewById(R.id.sv_content);
                sv.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(CommandEvent event) {
        if(CommandEvent.CMD_EXIT.equals(event.command)) {
            finish();
        }
    }
}
