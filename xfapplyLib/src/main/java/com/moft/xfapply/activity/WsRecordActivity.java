package com.moft.xfapply.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.moft.xfapply.R;
import com.moft.xfapply.activity.logic.WsRecordLogic;
import com.moft.xfapply.app.Constant;
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.base.BaseActivity;
import com.moft.xfapply.model.common.QueryCondition;
import com.moft.xfapply.model.entity.GeomElementDBInfo;
import com.moft.xfapply.model.external.AppDefs;

import java.util.ArrayList;

public class WsRecordActivity extends BaseActivity implements
        WsRecordLogic.OnWsRecordLogicListener {
    private WsRecordLogic mWsRecordLogic = null;
    private QueryCondition mCondition = new QueryCondition();
    private TextView tv_title = null;
    private View re_search_key = null;
    private View re_sadian = null;
    private EditText et_search = null;
    private TextView tv_search = null;

    private ArrayList<String> mGeoUuidList = new ArrayList<>();
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ws_record);

        Intent intent = getIntent();
        if (intent.hasExtra("QueryCondition")) {
            mCondition = (QueryCondition) intent.getSerializableExtra("QueryCondition");
            mGeoUuidList = intent.getStringArrayListExtra("GeoElementUuidList");
        } else {
            boolean isFromMap = intent.getBooleanExtra("FromMap", false);
            if (isFromMap) {
                mCondition.copy(LvApplication.getInstance().getMapQueryCondition());
            } else {
                mCondition.copy(LvApplication.getInstance().getDataQueryCondition());
            }
        }
        
        LinearLayout ll_content = (LinearLayout) this.findViewById(R.id.ll_content);
        tv_title = (TextView) this.findViewById(R.id.tv_title);

        tv_search = (TextView) this.findViewById(R.id.tv_search);
        tv_search.setVisibility(View.VISIBLE);
        tv_search.setText(mCondition.getKey());
        et_search = (EditText) this.findViewById(R.id.et_search);
        et_search.setVisibility(View.GONE);
        et_search.setText(mCondition.getKey());
        et_search.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                String key = "";
                if (s.length() > 0) {
                    key = et_search.getText().toString().trim();
                    tv_search.setText(key);
                } else {
                    tv_search.setText("关键字");
                }
                mCondition.setKey(key);
                mWsRecordLogic.reloadData();
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });
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
        re_sadian = this.findViewById(R.id.re_sadian);
        if (isCanSadian()) {
            re_sadian.setVisibility(View.VISIBLE);
        } else {
            re_sadian.setVisibility(View.GONE);
        }
        re_sadian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mWsRecordLogic.doMap();
            }
        });

        final ProgressDialog dialog = new ProgressDialog(this);

        dialog.setMessage("正在加载数据...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.show();

        mWsRecordLogic = new WsRecordLogic(ll_content, this);
        mWsRecordLogic.setTitleView(tv_title);
        mWsRecordLogic.setListener(this);
        mWsRecordLogic.setCondition(mCondition);
        if(mGeoUuidList != null && mGeoUuidList.size() > 0) {
            mWsRecordLogic.setGeoUuidList(mGeoUuidList);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mWsRecordLogic.init();

                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        }, 100);
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        
        mWsRecordLogic.onActivityResult(requestCode, resultCode, data);
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
    public boolean onPreStartDetailActivity(GeomElementDBInfo dto) {
        return false;
    }

    @Override
    public void onKeyBoardClose() {
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et_search.getWindowToken(),0);

        et_search.setVisibility(View.GONE);
        tv_search.setVisibility(View.VISIBLE);
    }

    @Override
    public void back(View view) {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    private boolean isCanSadian() {
        boolean result = true;
        String eleType = mCondition.getSylxCode();
        if (AppDefs.CompEleType.FIRE_VEHICLE.toString().equals(eleType)) {
            result = false;
        } else if (AppDefs.CompEleType.EXTINGUISHING_AGENT.toString().equals(eleType)) {
            result = false;
        } else if (AppDefs.CompEleType.EQUIPMENT.toString().equals(eleType)) {
            result = false;
        } else if (Constant.HAZARD_CHEMICAL.equals(eleType)) {
            result = false;
        } else {
            result = true;
        }
        return result;
    }
}
