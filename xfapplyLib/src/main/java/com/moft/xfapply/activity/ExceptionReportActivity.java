package com.moft.xfapply.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.moft.xfapply.R;
import com.moft.xfapply.activity.adapter.ExceptionExpandableAdapter;
import com.moft.xfapply.base.BaseActivity;
import com.moft.xfapply.model.common.ExceptionCategory;

import java.util.List;

public class ExceptionReportActivity extends BaseActivity {

    private ExpandableListView listView;
    private ExceptionExpandableAdapter adapter;
    private List<ExceptionCategory> exceptionCategoryList;

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
        if (getIntent() != null) {
            exceptionCategoryList = (List<ExceptionCategory>) getIntent().getSerializableExtra("exception");
            if (exceptionCategoryList != null && exceptionCategoryList.size() > 0) {
                adapter = new ExceptionExpandableAdapter(this, exceptionCategoryList);
                adapter.setOnGroupClickListener(new ExceptionExpandableAdapter.OnGroupClickListener() {
                    @Override
                    public void onGroupClick(View view, int position) {
                        boolean isExpanded = listView.isGroupExpanded(position);
                        if (isExpanded) {
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
        }
    }
}
