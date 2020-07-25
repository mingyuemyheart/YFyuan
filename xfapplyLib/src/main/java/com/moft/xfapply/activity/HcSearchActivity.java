package com.moft.xfapply.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.moft.xfapply.R;
import com.moft.xfapply.activity.adapter.HazardChemicalAdapter;
import com.moft.xfapply.activity.bussiness.StandardBussiness;
import com.moft.xfapply.activity.logic.PropertyConditionLogic;
import com.moft.xfapply.base.BaseActivity;
import com.moft.xfapply.logic.setting.PrefSetting;
import com.moft.xfapply.model.common.PropertyConditon;
import com.moft.xfapply.model.common.QueryCondition;
import com.moft.xfapply.model.common.WhpViewInfo;
import com.moft.xfapply.model.entity.HazardChemicalDBInfo;
import com.moft.xfapply.utils.StringUtil;
import com.moft.xfapply.widget.XListView;
import com.moft.xfapply.widget.dialog.CustomAlertDialog;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class HcSearchActivity extends BaseActivity implements
        XListView.IXListViewListener {
    private QueryCondition mCondition = new QueryCondition();
    private XListView listView;
    private View re_search_key = null;
    private EditText et_search;
    private TextView tv_search;

    private int page = 0;
    private int pageItem = 100;
    private Handler mHandler;

    private HazardChemicalAdapter adapter;
    private List<WhpViewInfo> hcList;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hc_search);
        mHandler = new Handler();
        pageItem = PrefSetting.getInstance().getPageNum();

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

    private void initData() {
    }

    protected void initView() {
        tv_search = (TextView) this.findViewById(R.id.tv_search);
        et_search = (EditText) this.findViewById(R.id.et_search);

        re_search_key = this.findViewById(R.id.re_search_key);
        re_search_key.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_search.setVisibility(View.GONE);
                et_search.setVisibility(View.VISIBLE);
                et_search.requestFocus();
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(et_search, 0);
            }
        });

        listView = (XListView)findViewById(R.id.list);
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(et_search.getWindowToken(),0);

                et_search.setVisibility(View.GONE);
                tv_search.setVisibility(View.VISIBLE);
                return false;
            }
        });
        listView.setPullLoadEnable(true);
        listView.setXListViewListener(this);

        tv_search = (TextView) this.findViewById(R.id.tv_search);
        tv_search.setVisibility(View.VISIBLE);
        tv_search.setText(mCondition.getKey());
        et_search = (EditText) this.findViewById(R.id.et_search);
        et_search.setVisibility(View.GONE);
        et_search.setText(mCondition.getKey());
        et_search.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String key = "";
                if (s.length() > 0) {
                    key = et_search.getText().toString().trim();
                    tv_search.setText(key);
                } else {
                    tv_search.setText("关键字");
                }

                mCondition.setKey(key);
                searchByKey(key);
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void afterTextChanged(Editable s) {

            }
        });

        hcList = loadData();
        adapter = new HazardChemicalAdapter(HcSearchActivity.this, hcList, new HazardChemicalAdapter.OnItemSelectedListener() {
            @Override
            public void onItemClick(WhpViewInfo info) {
                Intent intent = new Intent(HcSearchActivity.this, HcDetailActivity.class);
                intent.putExtra("WhpViewInfo", (Serializable)info);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(WhpViewInfo info) {

            }
        });
        listView.setAdapter(adapter);
    }

    private List<WhpViewInfo> loadData() {
        List<WhpViewInfo> list = StandardBussiness.getInstance().
                getHazardChemicalList(mCondition, pageItem, page);

        resetListView();
        if (pageItem == -1) {
            listView.finishLoadMore();
        } else {
            if (list.size() < pageItem) {
                listView.finishLoadMore();
            } else {
                listView.setLoadMoreNormal();
            }
        }

        return list;
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

    private void reloadData() {
        page = 0;
        hcList.clear();

        List<WhpViewInfo> tmpList = loadData();

        hcList.addAll(tmpList);
        adapter.notifyDataSetChanged();
    }

    public void onBackClick(View v) {
        finish();
    }
    
    private void searchByKey(String key) {
        final ProgressDialog dialog = new ProgressDialog(HcSearchActivity.this);
        dialog.setMessage("正在查找...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();

        mCondition.setType(QueryCondition.TYPE_KEY);
        mCondition.setKey(key);
        reloadData();
        dialog.dismiss();
    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                reloadData();
            }
        }, 500);
    }

    @Override
    public void onLoadMore() {
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                page += pageItem;

                List<WhpViewInfo> list = loadData();
                hcList.addAll(list);

                adapter.notifyDataSetChanged();
            }
        }, 500);
    }
}
