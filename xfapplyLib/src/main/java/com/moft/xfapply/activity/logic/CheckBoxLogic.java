package com.moft.xfapply.activity.logic;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.moft.xfapply.R;
import com.moft.xfapply.activity.adapter.CheckBoxAdapter;
import com.moft.xfapply.base.ViewLogic;
import com.moft.xfapply.model.common.Dictionary;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sxf on 2019-04-14.
 */

public class CheckBoxLogic extends ViewLogic {
    public CheckBoxLogic(View v, Activity a) {
        super(v, a);
    }

    private OnCheckBoxLogicListener mListener = null;

    private PopupWindow popInfo = null;
    private View popInfoView = null;
    private LinearLayout popup_content = null;

    private View re_allselect = null;
    private View tv_confirm = null;
    private ImageView iv_allselect = null;
    private TextView tv_allselect = null;
    private ListView list = null;
    private CheckBoxAdapter adapter = null;

    private boolean isAllChecked = true;
    private Boolean defaultCheckValue = true;
    private List<Dictionary> dataList = new ArrayList<>();
    private List<Boolean> dataListCheck = null;
    private int curIndex = -1;
    private boolean isConfirm = false;

    private void init() {
        int checkedCount = 0;
        for(int i = 0; i < dataListCheck.size(); i++) {
            if (dataListCheck.get(i)) {
                checkedCount++;
            }
        }
        isAllChecked = (checkedCount != 0);

        re_allselect = getView().findViewById(R.id.re_allselect);
        tv_confirm = getView().findViewById(R.id.tv_confirm);
        iv_allselect = (ImageView) getView().findViewById(R.id.iv_allselect);
        tv_allselect = (TextView) getView().findViewById(R.id.tv_allselect);
        if (isAllChecked) {
            iv_allselect.setImageResource(R.drawable.checkbox_outline);
        } else {
            iv_allselect.setImageResource(R.drawable.checkbox_outline_bl);
        }
        re_allselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAllChecked = !isAllChecked;
                if (isAllChecked) {
                    iv_allselect.setImageResource(R.drawable.checkbox_outline);
                } else {
                    iv_allselect.setImageResource(R.drawable.checkbox_outline_bl);
                }
                for (int i = 0; i < dataListCheck.size(); i++) {
                    dataListCheck.set(i, isAllChecked);
                }
                adapter.notifyDataSetChanged();
            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isConfirm = true;
                if (mListener != null) {
                    mListener.onConfirmResult(dataListCheck);
                }
                hide();
            }
        });

        list = (ListView) getView().findViewById(R.id.list);
        adapter = new CheckBoxAdapter(getActivity(), dataList, dataListCheck, curIndex, adapterListener);
        adapter.setBackground(R.drawable.btn_list_item_bg_level1);
        adapter.setCurBackground(R.drawable.btn_list_item_bg_level2);
        list.setAdapter(adapter);
    }

    public void show(View parent) {
        isConfirm = false;
        if (popInfo != null && popInfo.isShowing()) {
            popInfo.dismiss();
            popInfo = null;
        }

        popInfoView = getActivity().getLayoutInflater().inflate(R.layout.popup_info, null);
        popup_content = (LinearLayout) popInfoView.findViewById(R.id.popup_content);

        popInfo = new PopupWindow(popInfoView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popInfo.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popInfo.setOutsideTouchable(false);
        popInfo.setAnimationStyle(R.style.AnimTop2);
        popInfo.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (!isConfirm) {
                    if (mListener != null) {
                        mListener.onUnChanged();
                    }
                }
            }
        });

        View popCon = getActivity().getLayoutInflater().inflate(R.layout.checkbox_select, null);
        setView(popCon);

        if (mListener != null && dataListCheck == null) {
            mListener.onLoadListData(dataList);

            dataListCheck = new ArrayList<>();
            for (int i = 0; i < dataList.size(); i++) {
                dataListCheck.add(defaultCheckValue);
            }

            curIndex = mListener.onGetCurrentPos();
        }
        init();

        popup_content.addView(popCon);
        if (popInfo != null && !popInfo.isShowing()) {
            popInfo.showAsDropDown(parent);
        }
    }

    public void hide() {
        if (popInfo != null && popInfo.isShowing()) {
            popInfo.dismiss();
            popInfo = null;
        }
    }

    private CheckBoxAdapter.OnItemSelectedListener adapterListener = new CheckBoxAdapter.OnItemSelectedListener() {
        @Override
        public void onItemClick(Dictionary dic, int pos) {
        }

        @Override
        public void onItemCheck(Dictionary dic, boolean isChecked, int pos) {

        }

        @Override
        public void onSelectedRefresh(int index) {

        }
    };

    public void setListener(OnCheckBoxLogicListener mListener) {
        this.mListener = mListener;
    }

    public void setDefaultCheckValue(Boolean defaultCheckValue) {
        this.defaultCheckValue = defaultCheckValue;
    }

    public interface OnCheckBoxLogicListener{
        void onLoadListData(List<Dictionary> dataList);
        int onGetCurrentPos();
        void onConfirmResult(List<Boolean> dataListCheck);
        void onUnChanged();
    }
}
