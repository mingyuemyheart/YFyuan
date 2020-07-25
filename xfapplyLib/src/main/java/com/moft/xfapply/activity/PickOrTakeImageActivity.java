package com.moft.xfapply.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.moft.xfapply.R;
import com.moft.xfapply.activity.adapter.SketchDiagramAdapter;
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.base.BaseActivity;
import com.moft.xfapply.model.common.EleCondition;
import com.moft.xfapply.model.entity.KeyUnitMediaDBInfo;
import com.moft.xfapply.utils.CommonUtil;
import com.moft.xfapply.utils.StringUtil;

import java.io.Serializable;
import java.util.List;

public class PickOrTakeImageActivity extends BaseActivity {

    private EleCondition eleCondition = null;
    private List<KeyUnitMediaDBInfo> mediaList;

    private SketchDiagramAdapter adapter;
    private TextView tv_date;
    private ListView listView;

    /** 每张图片需要显示的高度和宽度 */
    private int perWidth;
    private boolean isEditable = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_or_take_image_activity);

        Intent intent = getIntent();
        if (intent.hasExtra("EleCondition")) {
            eleCondition = (EleCondition)intent.getSerializableExtra("EleCondition");
        } else {
            return;
        }
        if (intent.hasExtra("isEditable")) {
            isEditable = intent.getBooleanExtra("isEditable", false);
        }
        initKeyUnitPickMediaInfos(intent);
        initView();
        initData();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    protected void initView() {
        listView = (ListView) findViewById(R.id.list);

        TextView tv_title = (TextView)findViewById(R.id.tv_title);
        if(!StringUtil.isEmpty(eleCondition.getName())) {
            tv_title.setText(eleCondition.getName());
        } else {
            tv_title.setText("关键图示");
        }

        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back(v);
            }
        });
    }

    protected void initData() {
        //计算每张图片应该显示的宽度
        perWidth = (((WindowManager) (LvApplication.getInstance().
                getSystemService(Context.WINDOW_SERVICE))).
                getDefaultDisplay().getWidth() - CommonUtil.dip2px(this, 20))/3;


        adapter = new SketchDiagramAdapter(this, mediaList, getWindow().getDecorView());
        adapter.setListener(new SketchDiagramAdapter.SketchDiagramAdapterListener() {
            @Override
            public void onTakeAttach(String classfication) {

            }

            @Override
            public void onTakePhoto(List<KeyUnitMediaDBInfo> list, int position) {
                Intent intent = new Intent();
                intent.setClass(PickOrTakeImageActivity.this, PickBigImagesActivity.class);
                intent.putExtra(PickBigImagesActivity.EXTRA_HISTORY, eleCondition.getHistory());
                intent.putExtra(PickBigImagesActivity.EXTRA_DATA, (Serializable) list);
                intent.putExtra(PickBigImagesActivity.EXTRA_CURRENT_PIC, position);
                startActivity(intent);
            }

            @Override
            public void onRemoveAttach(List<KeyUnitMediaDBInfo> selectedList) {
            }
        });
        adapter.setEditable(isEditable);
        adapter.setPicWidth(perWidth);

        listView.setAdapter(adapter);
    }

    private void initKeyUnitPickMediaInfos(Intent intent){
        mediaList =  (List<KeyUnitMediaDBInfo>)intent.getSerializableExtra("pick_media_infos");
        if (mediaList == null || mediaList.isEmpty()) {
            if (!isEditable) {
                Toast.makeText(this, "无关键图示！",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
            return;
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
