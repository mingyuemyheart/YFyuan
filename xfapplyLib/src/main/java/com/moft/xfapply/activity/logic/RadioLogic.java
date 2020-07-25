package com.moft.xfapply.activity.logic;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.moft.xfapply.R;
import com.moft.xfapply.activity.adapter.RadioAdapter;
import com.moft.xfapply.base.ViewLogic;
import com.moft.xfapply.model.common.Dictionary;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sxf on 2019-04-10.
 */

public class RadioLogic extends ViewLogic {
    public RadioLogic(View v, Activity a) {
        super(v, a);
    }

    private OnRadioLogicListener mListener = null;

    private PopupWindow popInfo = null;
    private View popInfoView = null;
    private LinearLayout popup_content = null;

    private ListView list = null;

    private List<Dictionary> dataList = new ArrayList<>();
    private RadioAdapter adapter = null;
    private int curIndex = 0;
    private boolean isConfirm = false;

    private void init() {
        list = (ListView) getView().findViewById(R.id.list);
        adapter = new RadioAdapter(getActivity(), dataList, curIndex,
                new RadioAdapter.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(Dictionary dic, int pos) {
                        isConfirm = true;
                        curIndex = pos;
                        if (mListener != null) {
                            mListener.onConfirmResult(dic, pos);
                        }
                        hide();
                    }
                });
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

        View popCon = getActivity().getLayoutInflater().inflate(R.layout.radio_select, null);
        setView(popCon);

        if (mListener != null && dataList.size() == 0) {
            mListener.onLoadListData(dataList);
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

    public void setListener(OnRadioLogicListener mListener) {
        this.mListener = mListener;
    }

    public interface OnRadioLogicListener{
        void onLoadListData(List<Dictionary> dataList);
        int onGetCurrentPos();
        void onConfirmResult(Dictionary dic, int pos);
        void onUnChanged();
    }
}
