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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.moft.xfapply.R;
import com.moft.xfapply.activity.adapter.PlanLibraryCatalogsAdapter;
import com.moft.xfapply.app.Constant;
import com.moft.xfapply.base.BaseActivity;
import com.moft.xfapply.logic.network.OkHttpClientManager;
import com.moft.xfapply.logic.setting.PrefUserInfo;
import com.moft.xfapply.model.common.QueryCondition;
import com.moft.xfapply.model.external.dto.PlanLibraryCatalogDTO;
import com.moft.xfapply.model.external.result.ArrayRestResult;
import com.moft.xfapply.utils.GsonUtil;
import com.moft.xfapply.widget.XListView;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

/**
 * Created by sxf on 2019-05-02.
 */

public class PlanLibraryCatalogsActivity extends BaseActivity implements
        XListView.IXListViewListener {
    private QueryCondition mCondition = new QueryCondition();
    private XListView listView;
    private View re_search_key = null;
    private EditText et_search;
    private TextView tv_search;

    private int page = 1;
    private int start = 0;
    private int pageItem = -1;  // 不分页查找
    private Handler mHandler;

    private PlanLibraryCatalogsAdapter adapter;
    private List<PlanLibraryCatalogDTO> list = new ArrayList<PlanLibraryCatalogDTO>();

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_library);
        mHandler = new Handler();
//        pageItem = PrefSetting.getInstance().getPageNum();   // 不分页

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
        loadData();
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

        adapter = new PlanLibraryCatalogsAdapter(this, list, new PlanLibraryCatalogsAdapter.OnItemSelectedListener() {
            @Override
            public void onItemClick(PlanLibraryCatalogDTO info) {
                Intent intent = new Intent(PlanLibraryCatalogsActivity.this, PlanLibraryActivity.class);
                intent.putExtra("catalogUuid", info.getUuid());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(PlanLibraryCatalogDTO info) {

            }
        });
        listView.setAdapter(adapter);
    }

    private void onRequestSuccess() {
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

    private void reloadData() {
        page = 1;
        start = 0;
        if (list == null) {
            list = new ArrayList<PlanLibraryCatalogDTO>();
        }
        list.clear();

        loadData();
    }

    public void onBackClick(View v) {
        finish();
    }

    private void searchByKey(String key) {
        final ProgressDialog dialog = new ProgressDialog(this);
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
                page++;
                start += pageItem;
                list.clear();

                loadData();
            }
        }, 500);
    }

    private void loadData() {
        Map<String, String> params = new HashMap<String, String>();
        params.put(Constant.PARAM_NAME, mCondition.getKey());
        params.put(Constant.PARAM_DEPARTMENT_UUID, PrefUserInfo.getInstance().getUserInfo("department_uuid"));

        OkHttpClientManager http = OkHttpClientManager.getInstance();
        http.postAsyn(http.convertToURL(String.format(Constant.METHOD_GET_PLAN_LIBRARY_CATALOGS_LIST)),
                new OkHttpClientManager.ResultCallback<ArrayRestResult>() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(PlanLibraryCatalogsActivity.this, "网络请求失败，请检查网络连接！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(ArrayRestResult result) {
                if (result != null && result.isSuccess()) {
                    if (result.getContents() != null) {
                        Gson gson = GsonUtil.create();
                        String jsonStr = gson.toJson(result.getContents());

                        Type listType = new TypeToken<List<PlanLibraryCatalogDTO>>(){}.getType();
                        List<PlanLibraryCatalogDTO> tmp = gson.fromJson(jsonStr, listType);

                        list.addAll(tmp);
                    }
                    onRequestSuccess();
                } else {
                    Toast.makeText(PlanLibraryCatalogsActivity.this, "数据请求失败！" , Toast.LENGTH_SHORT).show();
                }
            }
        }, params);
    }

}
