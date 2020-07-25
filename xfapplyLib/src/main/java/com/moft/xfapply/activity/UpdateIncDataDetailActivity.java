package com.moft.xfapply.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.moft.xfapply.R;
import com.moft.xfapply.activity.adapter.ExpandableAdapter;
import com.moft.xfapply.activity.bussiness.OfflineDataBussiness;
import com.moft.xfapply.base.BaseActivity;
import com.moft.xfapply.model.common.IncOfflineProcess;
import com.moft.xfapply.utils.StringUtil;

/*
 * @Author: 王泉
 * @Date:   2019-02-25
 * No.              Date.          Modifier    Description
 * 【T886049】      2019-02-25     王泉       终端与平台数据一致，实时同步（新建数据）
 */
public class UpdateIncDataDetailActivity extends BaseActivity {

    private ExpandableListView listView;
    private ExpandableAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_inc_data_detail);
        initView();
    }

    private void initView() {
        listView = (ExpandableListView) findViewById(R.id.expand_list_view);
        listView.setGroupIndicator(null);
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                return true;
            }
        });
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                return false;
            }
        });
        if(getIntent() != null) {
            String belongtoGroup = getIntent().getStringExtra("belongtoGroup");
            if(!StringUtil.isEmpty(belongtoGroup)) {
                IncOfflineProcess process = OfflineDataBussiness.getInstance().getIncOfflineProcess(belongtoGroup);
                if(process != null) {
                    adapter = new ExpandableAdapter(this, process.getTypeProcesses());
                    adapter.setOnGroupClickListener(new ExpandableAdapter.OnGroupClickListener() {
                        @Override
                        public void onGroupClick(View view, int position) {
                            boolean isExpanded = listView.isGroupExpanded(position);
                            if(isExpanded) {
                                listView.collapseGroup(position);
                            } else {
                                listView.expandGroup(position);
                            }
                        }
                    });
                    listView.setAdapter(adapter);
                } else {
                    Toast.makeText(this, "数据内部错误！", Toast.LENGTH_SHORT).show();
                    finish();
                }
            } else {
                Toast.makeText(this, "数据内部错误！", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(this, "数据内部错误！", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
