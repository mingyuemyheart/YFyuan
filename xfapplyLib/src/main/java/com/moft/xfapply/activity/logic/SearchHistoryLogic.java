package com.moft.xfapply.activity.logic;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.moft.xfapply.R;
import com.moft.xfapply.activity.adapter.SearchMapAdapter;
import com.moft.xfapply.activity.bussiness.CommonBussiness;
import com.moft.xfapply.base.ViewLogic;
import com.moft.xfapply.model.entity.MapSearchInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchHistoryLogic extends ViewLogic {
    public SearchHistoryLogic(View v, Activity a) {
        super(v, a);
    }
    private SearchSuggestLogic.OnSearchMapLogicListener mListener = null;

    private View mViewSelf = null;
    private View mViewClear = null;
    private ListView listView;
    private SearchMapAdapter adapter = null;
    private List<MapSearchInfo> vrList = new ArrayList<>();
    private Map<String, MapSearchInfo> vrMap = new HashMap<>();

    private SearchMapAdapter.OnItemSelectedListener mOnItemSelectedListener = null;

    public void init() {
        mViewSelf = getActivity().getLayoutInflater().inflate(R.layout.map_search_suggest, null);

        mViewClear = getActivity().getLayoutInflater().inflate(R.layout.item_map_search_clear, null);
        mViewClear.setVisibility(View.GONE);
        mViewClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vrList.clear();
                adapter.notifyDataSetChanged();
                CommonBussiness.getInstance().deleteMapSearchInfoAll();
                mViewClear.setVisibility(View.GONE);
            }
        });

        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        mViewClear.measure(w, h);
        mViewClear.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, mViewClear.getMeasuredHeight()));

        LinearLayout ll_content = (LinearLayout)getView();
        ll_content.addView(mViewClear);
        ll_content.addView(mViewSelf);

        mOnItemSelectedListener = new SearchMapAdapter.OnItemSelectedListener() {
            @Override
            public void onItemClick(MapSearchInfo dto) {
                if (!dto.isGeoPosValid()) {
                    if (mListener != null) {
                        mListener.onStartSearch(dto);
                    }
                    return;
                }

                if (mListener != null) {
                    mListener.onStartMapPos(dto);
                }
            }

            @Override
            public void onItemLongClick(MapSearchInfo dto) {
            }
        };

        listView = (ListView) mViewSelf.findViewById(R.id.list);
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mListener != null) {
                    mListener.onKeyBoardClose();
                }
                return false;
            }
        });

        loadData();
        adapter = new SearchMapAdapter(getActivity(), vrList, mOnItemSelectedListener);
        listView.setAdapter(adapter);
    }

    private void loadData() {
        vrList.clear();
        List<MapSearchInfo> list = CommonBussiness.getInstance().getMapSearchInfoList();
        if (list != null) {
            vrList.addAll(list);
        }
        if (vrList.isEmpty()) {
            mViewClear.setVisibility(View.GONE);
        } else {
            mViewClear.setVisibility(View.VISIBLE);
        }
    }

    public void hide() {
        if (mViewSelf != null) {
            mViewSelf.setVisibility(View.GONE);
            mViewClear.setVisibility(View.GONE);
        }
        if (mViewClear != null) {
            mViewClear.setVisibility(View.GONE);
        }
    }

    public void unhide() {
        if (mViewSelf != null) {
            mViewSelf.setVisibility(View.VISIBLE);
        }
        if (mViewClear != null) {
            if (vrList.isEmpty()) {
                mViewClear.setVisibility(View.GONE);
            } else {
                mViewClear.setVisibility(View.VISIBLE);
            }
        }
    }

    public void destroy() {
    }

    public SearchSuggestLogic.OnSearchMapLogicListener getListener() {
        return mListener;
    }

    public void setListener(SearchSuggestLogic.OnSearchMapLogicListener mListener) {
        this.mListener = mListener;
    }

}
