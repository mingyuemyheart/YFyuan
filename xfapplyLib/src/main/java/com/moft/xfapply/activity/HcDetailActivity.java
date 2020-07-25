package com.moft.xfapply.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.moft.xfapply.R;
import com.moft.xfapply.activity.bussiness.StandardBussiness;
import com.moft.xfapply.activity.logic.PropertyLogic;
import com.moft.xfapply.base.BaseActivity;
import com.moft.xfapply.model.common.PropertyDes;
import com.moft.xfapply.model.common.WhpViewInfo;
import com.moft.xfapply.model.entity.HazardChemicalDBInfo;
import com.moft.xfapply.model.entity.MsdsDBInfo;

import java.util.List;

public class HcDetailActivity extends BaseActivity {
    protected LinearLayout ll_content = null;
    private TextView tv_title = null;
    private PropertyLogic mCurPropertyLogic = null;

    private List<PropertyDes> pdListDetail = null;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hc_detail);

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("正在加载数据...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initView();
                initData();

                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        }, 100);
    }

    protected void initView() {
        ll_content = (LinearLayout) findViewById(R.id.ll_content);
        tv_title = (TextView) this.findViewById(R.id.tv_title);
        tv_title.setText("危化品");
        tv_title.setFocusable(true);
        tv_title.setFocusableInTouchMode(true);
    }

    protected void initData() {
        Intent intent = getIntent();
        WhpViewInfo info = (WhpViewInfo)intent.getSerializableExtra("WhpViewInfo");
        if (info == null) {
            Toast.makeText(this, "无效的数据类型参数！",
                    Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        StandardBussiness sb = StandardBussiness.getInstance();
        if (info.getType() == 0) {
            HazardChemicalDBInfo hc = sb.getHazardChemicalById(info.getUuid());
            pdListDetail = hc.getPdListDetail();
        } else {
            MsdsDBInfo msds = sb.getMsdsById(info.getUuid());
            pdListDetail = msds.getPdListDetail();
        }

        initProperty();
    }

    public void onBackClick(View v) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initProperty() {
        mCurPropertyLogic = new PropertyLogic(ll_content, this);
        mCurPropertyLogic.clear();
        mCurPropertyLogic.setPdListDetail(pdListDetail);
        mCurPropertyLogic.init();
    }
}
