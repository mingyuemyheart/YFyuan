package com.moft.xfapply.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.moft.xfapply.R;
import com.moft.xfapply.activity.adapter.AttachMediaAdapter;
import com.moft.xfapply.activity.bussiness.GeomEleBussiness;
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.base.BaseActivity;
import com.moft.xfapply.model.base.IMediaInfo;
import com.moft.xfapply.model.common.EleCondition;
import com.moft.xfapply.utils.CommonUtil;

import java.io.Serializable;
import java.util.List;

/*
 * @Author: 宋满意
 * @Date:   2019-01-24 17:30:53
 * No.              Date.          Modifier    Description
 * 【HW-857417】      2019-01-24     宋满意       重点单位预案显示可以查看多个旧版预案
 */
public class OldPlanActivity extends BaseActivity {

    private EleCondition eleCondition = null;
    private List<IMediaInfo> mediaList;

    private GridView gridView = null;
    private AttachMediaAdapter adapter;
    private TextView tv_date;

    /** 每张图片需要显示的高度和宽度 */
    private int perWidth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.old_plan_activity);

        Intent intent = getIntent();
        if (intent.hasExtra("EleCondition")) {
            eleCondition = (EleCondition)intent.getSerializableExtra("EleCondition");
        } else {
            return;
        }
        initKeyUnitPickMediaInfos(intent);
        initView();
        initData();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    protected void initView() {
        tv_date = (TextView) findViewById(R.id.tv_name);
        tv_date.setText(eleCondition.getName());

        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back(v);
            }
        });
        gridView = (GridView) findViewById(R.id.gv_content);
    }

    protected void initData() {
        //计算每张图片应该显示的宽度
        perWidth = (((WindowManager) (LvApplication.getInstance().
                getSystemService(Context.WINDOW_SERVICE))).
                getDefaultDisplay().getWidth() - CommonUtil.dip2px(this, 20))/3;

        adapter = new AttachMediaAdapter(this,
                this.getWindow().getDecorView());
        AttachMediaAdapter.AttachFileAdapterListener listener =
                new AttachMediaAdapter.AttachFileAdapterListener() {
                    @Override
                    public void onTakePhoto(List<IMediaInfo> list, int position) {
                        Intent intent = new Intent();
                        intent.setClass(OldPlanActivity.this, PickBigImagesActivity.class);
                        intent.putExtra(PickBigImagesActivity.EXTRA_HISTORY, eleCondition.getHistory());
                        intent.putExtra(PickBigImagesActivity.EXTRA_DATA, (Serializable) list);
                        intent.putExtra(PickBigImagesActivity.EXTRA_CURRENT_PIC, position);
                        startActivity(intent);
                    }

                    @Override
                    public void onTakeAttach() {
                    }
                    @Override
                    public void onRemoveAttach() {
                    }
                    @Override
                    public void onUpdateAttach(IMediaInfo info) {
                        GeomEleBussiness.getInstance().updateAttachFile(
                                eleCondition.getCityCode(), info, eleCondition.getHistory());
                    }
                };
        adapter.setListener(listener);
        adapter.setAfList(mediaList);
        adapter.setPicWidth(perWidth);
        adapter.setIsHistory(eleCondition.getHistory());
        adapter.setCity(eleCondition.getCityCode());
        adapter.setEditalbe(false);
        adapter.setIsOldPlan(true);

        gridView.setAdapter(adapter);
    }

    private void initKeyUnitPickMediaInfos(Intent intent){
        mediaList =  (List<IMediaInfo>)intent.getSerializableExtra("pick_media_infos");
        if (mediaList == null || mediaList.isEmpty()) {
            Toast.makeText(this, "无旧版预案！",
                    Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
