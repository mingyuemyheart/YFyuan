package com.moft.xfapply.activity.logic;

import com.moft.xfapply.R;
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.base.ViewLogic;
import com.moft.xfapply.model.common.QueryCondition;
import com.moft.xfapply.model.entity.MapSearchInfo;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

public class SearchBarLogic extends ViewLogic {

    public SearchBarLogic(View v, FragmentActivity a) {
        super(v, a);
    }

    public SearchBarLogic(View v, Activity a) {
        super(v, a);
    }

    private OnSearchBarLogicListener mListener = null;

    private RelativeLayout re_search = null;
    private RelativeLayout re_list = null;
    private TextView tv_search = null;
    private QueryCondition mCondition = null;

    public void init() {
        re_list = (RelativeLayout) getView().findViewById(R.id.re_list);
        if (re_list != null) {
            re_list.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onClickList();
                    }
                }
            });
        }

        re_search = (RelativeLayout) getView().findViewById(R.id.re_search);
        re_search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onClickSearch();
                }
            }
        });

        tv_search = (TextView) getView().findViewById(R.id.tv_search);
    }

    public void setSearchKey() {
        tv_search.setText(mCondition.getSearchBarText());
    }

    public void setSearchKey(String key) {
        tv_search.setText(key);
    }

    public OnSearchBarLogicListener getListener() {
        return mListener;
    }

    public void setListener(OnSearchBarLogicListener mListener) {
        this.mListener = mListener;
    }

    public void setCondition(QueryCondition mCondition) {
        this.mCondition = mCondition;
    }

    public interface OnSearchBarLogicListener {
        void onClickList();

        void onClickSearch();
    }
}