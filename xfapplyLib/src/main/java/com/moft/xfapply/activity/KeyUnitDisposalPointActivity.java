package com.moft.xfapply.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.moft.xfapply.R;
import com.moft.xfapply.activity.adapter.KeyUnitDisposalPointAdapter;
import com.moft.xfapply.app.Constant;
import com.moft.xfapply.base.BaseActivity;
import com.moft.xfapply.logic.network.OkHttpClientManager;
import com.moft.xfapply.model.common.EleCondition;
import com.moft.xfapply.model.external.dto.KeyUnitDisposalPointDTO;
import com.moft.xfapply.model.external.result.ArrayRestResult;
import com.moft.xfapply.utils.GsonUtil;
import com.moft.xfapply.utils.StringUtil;
import com.moft.xfapply.widget.XListView;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import okhttp3.Request;

/*
 * @Author: 宋满意
 * @Date:   2019-01-24 17:30:53
 * No.              Date.          Modifier    Description
 * 【HW-857417】      2019-01-24     宋满意       重点单位预案显示可以查看多个旧版预案
 */
public class KeyUnitDisposalPointActivity extends BaseActivity implements
        XListView.IXListViewListener {

    private EleCondition eleCondition = null;
    private List<KeyUnitDisposalPointDTO> disposalPoints;
    private String keyUnitUuid;

//    private GridView gridView = null;
//    private KeyUnitDisposalPointAdapter adapter;
    private KeyUnitDisposalPointAdapter adapter = null;
    private XListView listView;
    
    private TextView tv_name;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.disposal_point_activity);

        Intent intent = getIntent();
        if (intent.hasExtra("EleCondition")) {
            eleCondition = (EleCondition)intent.getSerializableExtra("EleCondition");
        } else {
            return;
        }
        initKeyUnitDisposalPointInfos(intent);
        initView();
        initData();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    protected void initView() {
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_name.setText(eleCondition.getName());

        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back(v);
            }
        });
        
        listView = (XListView) findViewById(R.id.list);
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        listView.setPullLoadEnable(false);
        listView.setXListViewListener(this);

    }

    protected void initData() {

        adapter = new KeyUnitDisposalPointAdapter(KeyUnitDisposalPointActivity.this, disposalPoints, new KeyUnitDisposalPointAdapter.OnItemSelectedListener() {
            @Override
            public void onItemClick(KeyUnitDisposalPointDTO info) {

                Intent intent = new Intent(KeyUnitDisposalPointActivity.this, DisposalPointDetailActivity.class);
                if (!StringUtil.isEmpty(info.getPublishUrl())) {
                    intent.putExtra("url", (Serializable)info.getPublishUrl());
                    intent.putExtra("name", (Serializable)info.getDisposalPointName());
                    startActivity(intent);
                } else {
                    Toast.makeText(KeyUnitDisposalPointActivity.this, "无处置要点数据！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onItemLongClick(KeyUnitDisposalPointDTO info) {

            }
        });
//        adapter.setCondition(mCondition);
        listView.setAdapter(adapter);
    }

    private void initKeyUnitDisposalPointInfos(Intent intent){
        keyUnitUuid = intent.getStringExtra("key_unit_uuid");

        disposalPoints =  (List<KeyUnitDisposalPointDTO>)intent.getSerializableExtra("key_unit_disposal_points");
        if (disposalPoints == null || disposalPoints.isEmpty()) {
            Toast.makeText(this, "暂无处置要点！", Toast.LENGTH_SHORT).show();
            finish();
        }

        // 将要点按权重升序排列展示
        Collections.sort(disposalPoints, new Comparator<KeyUnitDisposalPointDTO>() {
            @Override
            public int compare(KeyUnitDisposalPointDTO o1, KeyUnitDisposalPointDTO o2) {
                return o1.getWeight().compareTo(o2.getWeight());
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRefresh() {
        reloadData();
    }

    private void reloadData() {
        if (disposalPoints == null) {
            disposalPoints = new ArrayList<KeyUnitDisposalPointDTO>();
        }
        disposalPoints.clear();

        loadDate();
    }

    @Override
    public void onLoadMore() {

    }

    private void loadDate() {

        OkHttpClientManager http = OkHttpClientManager.getInstance();
        http.getAsyn(http.convertToURL(String.format(Constant.METHOD_GET_KEY_UNIT_DISPOSAL_POINTS, keyUnitUuid)), new OkHttpClientManager.ResultCallback<ArrayRestResult>() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(KeyUnitDisposalPointActivity.this, "网络异常，请检查网络是否正常！",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(ArrayRestResult result) {
                if (result != null && result.isSuccess()) {
                    Gson gson = GsonUtil.create();
                    if (result.getContents() != null) {
                        String jsonStr = gson.toJson(result.getContents());
                        Type listType = new TypeToken<List<KeyUnitDisposalPointDTO>>(){}.getType();
                        List<KeyUnitDisposalPointDTO> kuPoints = gson.fromJson(jsonStr, listType);

                        if (kuPoints.isEmpty()) {
                            Toast.makeText(KeyUnitDisposalPointActivity.this, "暂无处置要点！", Toast.LENGTH_SHORT).show();
                        } else {
                            disposalPoints.addAll(kuPoints);
                        }
                    } else {
                        Toast.makeText(KeyUnitDisposalPointActivity.this, "暂无处置要点！", Toast.LENGTH_SHORT).show();
                    }

                    onRequestSuccess();
                } else {
                    Toast.makeText(KeyUnitDisposalPointActivity.this, "网络异常，请检查网络是否正常！",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void onRequestSuccess() {
        resetListView();
        listView.finishLoadMore();

        adapter.notifyDataSetChanged();
    }

    @SuppressLint("SimpleDateFormat")
    private void resetListView() {
        listView.stopRefresh();
        listView.stopLoadMore();
        SimpleDateFormat sDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        String date = sDateFormat.format(new java.util.Date());
        listView.setRefreshTime(date);
    }
}
