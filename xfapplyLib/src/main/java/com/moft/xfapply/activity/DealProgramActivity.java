package com.moft.xfapply.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.moft.xfapply.R;
import com.moft.xfapply.activity.logic.DisposalPointLogic;
import com.moft.xfapply.base.BaseActivity;
import com.moft.xfapply.model.common.QueryCondition;
import com.moft.xfapply.model.external.dto.DisposalPointDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sxf on 2019-05-02.
 */

public class DealProgramActivity extends BaseActivity implements
        DisposalPointLogic.OnDisposalPointLogicListener {
    private QueryCondition mCondition = new QueryCondition();
    private View re_search_key = null;
    private TextView tv_title = null;
    private EditText et_search;
    private TextView tv_search;

    private DisposalPointLogic mDisposalPointLogic = null;

    private List<DisposalPointDTO> fileList = new ArrayList<DisposalPointDTO>();

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_program);

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
                mDisposalPointLogic.reloadData();
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

        final ProgressDialog dialog = new ProgressDialog(this);

        dialog.setMessage("正在加载数据...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.show();

        mDisposalPointLogic = new DisposalPointLogic(ll_content, this);
        mDisposalPointLogic.setTitleView(tv_title);
        mDisposalPointLogic.setListener(this);
        mDisposalPointLogic.setCondition(mCondition);
        
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mDisposalPointLogic.init();

                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        }, 100);
    }

    public void onBackClick(View v) {
        finish();
    }

    @Override
    public boolean onPreStartDetailActivity(DisposalPointDTO dto) {
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
}
