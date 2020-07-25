package com.moft.xfapply.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moft.xfapply.R;
import com.moft.xfapply.activity.bussiness.CommonBussiness;
import com.moft.xfapply.activity.bussiness.GeomEleBussiness;
import com.moft.xfapply.activity.logic.SearchHistoryLogic;
import com.moft.xfapply.activity.logic.SearchPoiLogic;
import com.moft.xfapply.activity.logic.SearchRecordLogic;
import com.moft.xfapply.activity.logic.SearchSuggestLogic;
import com.moft.xfapply.app.Constant;
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.base.BaseActivity;
import com.moft.xfapply.model.base.IGeomElementInfo;
import com.moft.xfapply.model.common.EleCondition;
import com.moft.xfapply.model.common.QueryCondition;
import com.moft.xfapply.model.entity.GeomElementDBInfo;
import com.moft.xfapply.model.entity.MapSearchInfo;
import com.moft.xfapply.utils.StringUtil;
import com.moft.xfapply.utils.Utils;

public class MapSearchActivity extends BaseActivity implements
        SearchSuggestLogic.OnSearchMapLogicListener,
        SearchRecordLogic.OnWsRecordSearchLogicListener,
        SearchPoiLogic.OnSearchPoiLogicListener {
    private SearchSuggestLogic mSearchSuggestLogic = null;
    private SearchPoiLogic mSearchPoiLogic = null;
    private SearchHistoryLogic mSearchHistoryLogic = null;
    private SearchRecordLogic mSearchRecordLogic = null;
    private QueryCondition mCondition = new QueryCondition();

    private LinearLayout ll_content = null;

    private TextView tv_search = null;
    private TextView et_search = null;
    private RelativeLayout re_search = null;
    private View re_sadian = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_map);

        Intent intent = getIntent();
        if (intent.getBooleanExtra("FromMap", false)) {
            mCondition = LvApplication.getInstance().getMapQueryCondition();
        } else {
            mCondition = LvApplication.getInstance().getMapDisasterQueryCondition();
        }

        ll_content = (LinearLayout) this.findViewById(R.id.ll_content);
        ll_content.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(et_search.getWindowToken(), 0);
                return false;
            }
        });

        re_sadian = this.findViewById(R.id.re_sadian);
        re_sadian.setVisibility(View.GONE);
        re_sadian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSearchRecordLogic.doMap();
            }
        });

        re_search = (RelativeLayout) this.findViewById(R.id.re_search);
        tv_search = (TextView) this.findViewById(R.id.tv_search);
        et_search = (EditText) this.findViewById(R.id.et_search);
        et_search.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.length() > 0) {
                    re_search.setVisibility(View.VISIBLE);
                    tv_search.setText(et_search.getText().toString().trim());
                    re_sadian.setVisibility(View.GONE);

                    String key = et_search.getText().toString().trim();
                    if (StringUtil.isEmpty(key)) {
                        return;
                    }

                    mSearchHistoryLogic.hide();
                    mSearchSuggestLogic.unhide();
                    mSearchSuggestLogic.searchByKey(key);
                } else {
                    re_search.setVisibility(View.GONE);
                    tv_search.setText("");

                    mSearchSuggestLogic.hide();
                    mSearchHistoryLogic.unhide();
                }
                mSearchPoiLogic.hide();
                mSearchRecordLogic.hide();
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });
        re_search.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                doSearchAuto();
            }
        });

        mSearchHistoryLogic = new SearchHistoryLogic(ll_content, this);
        mSearchHistoryLogic.setListener(this);
        mSearchHistoryLogic.init();

        mSearchSuggestLogic = new SearchSuggestLogic(ll_content, this);
        mSearchSuggestLogic.setListener(this);
        mSearchSuggestLogic.init();
        mSearchSuggestLogic.hide();

        mSearchPoiLogic = new SearchPoiLogic(ll_content, this);
        mSearchPoiLogic.setListener(this);
        mSearchPoiLogic.setListenerSelf(this);
        mSearchPoiLogic.init();
        mSearchPoiLogic.hide();

        mSearchRecordLogic = new SearchRecordLogic(ll_content, this);
        mSearchRecordLogic.setCondition(mCondition);
        mSearchRecordLogic.setListener(MapSearchActivity.this);
        mSearchRecordLogic.init();
        mSearchRecordLogic.hide();

        if (intent.getBooleanExtra("AutoSearch", false)) {
            if (mCondition.getType() == QueryCondition.TYPE_KEY && !StringUtil.isEmpty(mCondition.getKey())) {
                et_search.setText(mCondition.getKey());
                doSearchAuto();
            }
        } else {
            mCondition.setKey("");
        }
    }

    private void doSearchAuto() {
        String key = et_search.getText().toString().trim();
        if (StringUtil.isEmpty(key)) {
            return;
        }

        re_search.setVisibility(View.GONE);
        re_sadian.setVisibility(View.VISIBLE);

        mSearchSuggestLogic.hide();
        mSearchPoiLogic.hide();
        mSearchRecordLogic.unhide();
        mSearchPoiLogic.setSearchKey(key);
        mSearchRecordLogic.setSearchKey(key);
        mSearchRecordLogic.searchByKey();

        MapSearchInfo msi = new MapSearchInfo();
        msi.setName(key);
        msi.setType(Constant.MAP_SEARCH_HISTORY);
        CommonBussiness.getInstance().updateMapSearchInfo(msi);

        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et_search.getWindowToken(), 0);
    }

    @Override
    protected void onDestroy() {
        if (mSearchHistoryLogic != null) {
            mSearchHistoryLogic.destroy();
        }
        if (mSearchSuggestLogic != null) {
            mSearchSuggestLogic.destroy();
        }
        if (mSearchPoiLogic != null) {
            mSearchPoiLogic.destroy();
        }
        super.onDestroy();
    }

    public void back(View view) {
        if (mSearchPoiLogic != null && mSearchPoiLogic.isShowing() && !mSearchPoiLogic.canBack()) {
            mSearchPoiLogic.doBack();
            return;
        }
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {

            back(null);

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onStartMapPos(MapSearchInfo dto) {
        if (Constant.MAP_SEARCH_FIRE.equals(dto.getType())) {
            String extras = dto.getExtra();
            if (!StringUtil.isEmpty(extras)) {
                String[] exs = extras.split(Constant.OUTLINE_DIVIDER);

                EleCondition ec = new EleCondition();
                ec.setUuid(exs[1]);
                ec.setType(exs[0]);
                ec.setCityCode(exs[2]);
                GeomEleBussiness eleBussiness = GeomEleBussiness.getInstance();
                IGeomElementInfo gei = (IGeomElementInfo)eleBussiness.getSpecGeomEleInfoByUuid(ec);
                if(gei != null) {
                    String extra = gei.getEleType() + Constant.OUTLINE_DIVIDER +
                            gei.getUuid() + Constant.OUTLINE_DIVIDER + gei.getBelongtoGroup();
                    //对应其他消防队伍分为4类
                    extra += Constant.OUTLINE_DIVIDER + gei.getResEleType();
                    extra += Constant.OUTLINE_DIVIDER + gei.getOutline();
                    dto.setExtra(extra);
                }
            }
        }

        CommonBussiness.getInstance().updateMapSearchInfo(dto);

        Intent intent = new Intent();
        intent.putExtra("MapSearchInfo", dto);
        setResult(RESULT_OK, intent);

        finish();
    }

    @Override
    public void onStartSearch(MapSearchInfo dto) {
        tv_search.setText(dto.getName());
        et_search.setText(dto.getName());
        onKeyBoardClose();
        re_search.setVisibility(View.GONE);
        re_sadian.setVisibility(View.VISIBLE);

        mSearchSuggestLogic.hide();
        mSearchPoiLogic.hide();
        mSearchRecordLogic.unhide();
        mSearchPoiLogic.setSearchKey(dto.getName());
        mSearchRecordLogic.setSearchKey(dto.getName());
        mSearchRecordLogic.searchByKey();
    }

    @Override
    public boolean onPreStartDetailActivity(GeomElementDBInfo dto) {
        if (!dto.isGeoPosValid()) {
            return false;
        }

        EleCondition ec = new EleCondition();
        ec.setUuid(dto.getUuid());
        ec.setType(dto.getEleType());
        ec.setCityCode(dto.getBelongtoGroup());
        GeomEleBussiness eleBussiness = GeomEleBussiness.getInstance();
        IGeomElementInfo gei = (IGeomElementInfo)eleBussiness.getSpecGeomEleInfoByUuid(ec);

        MapSearchInfo msi = new MapSearchInfo();
        msi.setName(dto.getName());
        msi.setType(Constant.MAP_SEARCH_FIRE);
        msi.setAddress(dto.getAddress());
        msi.setUid("");
        msi.setLat((int) (Utils.convertToDouble(dto.getLatitude()) * 1E6));
        msi.setLng((int) (Utils.convertToDouble(dto.getLongitude()) * 1E6));
        String extra = dto.getEleType() + Constant.OUTLINE_DIVIDER +
                dto.getUuid() + Constant.OUTLINE_DIVIDER + dto.getBelongtoGroup();
        //对应其他消防队伍分为4类
        extra += Constant.OUTLINE_DIVIDER + dto.getResEleType();
        extra += Constant.OUTLINE_DIVIDER + gei.getOutline();
        msi.setExtra(extra);

        onStartMapPos(msi);

        return true;
    }

    @Override
    public void onKeyBoardClose() {
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et_search.getWindowToken(),0);
    }

    @Override
    public void onSearchChanged(int type) {
        if (type == 1) {
            re_sadian.setVisibility(View.GONE);
            mSearchPoiLogic.unhide();
            mSearchRecordLogic.hide();
            mSearchPoiLogic.searchByKey();
        } else {
            re_sadian.setVisibility(View.VISIBLE);
            mSearchPoiLogic.hide();
            mSearchRecordLogic.unhide();
            mSearchRecordLogic.searchByKey();
        }
    }
}