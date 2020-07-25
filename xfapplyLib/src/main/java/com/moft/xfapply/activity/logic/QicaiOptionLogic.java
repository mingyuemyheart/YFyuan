package com.moft.xfapply.activity.logic;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.moft.xfapply.R;
import com.moft.xfapply.base.ViewLogic;
import com.moft.xfapply.model.external.AppDefs;

/**
 * Created by sxf on 2019-04-29.
 */

public class QicaiOptionLogic extends ViewLogic {
    public QicaiOptionLogic(View v, Activity a) {
        super(v, a);
    }

    private OnQicaiOptionLogicListener listener = null;
    private PopupWindow popOption = null;

    private LinearLayout re_qicai = null;

    public void init() {
        re_qicai = (LinearLayout)getView().findViewById(R.id.re_qicai);
        re_qicai.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showPopOption();
            }
        });
    }

    private void showPopOption() {
        if (popOption == null || !popOption.isShowing()) {
            int[] location = new int[2];
            re_qicai.getLocationOnScreen(location);

            View popup = getActivity().getLayoutInflater().inflate(R.layout.popup_qicai, null);
            popOption = new PopupWindow(popup, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);
            popOption.setOutsideTouchable(true);

            re_qicai.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            int width = re_qicai.getMeasuredWidth();
            popOption.showAtLocation(re_qicai, Gravity.NO_GRAVITY, location[0] + width + 20, location[1]);
            popOption.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                }
            });

            View re_xfc = popup.findViewById(R.id.re_xfc);
            re_xfc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onQicaiChange(AppDefs.CompEleType.FIRE_VEHICLE.toString());
                    }
                }
            });

            View re_xfqc = popup.findViewById(R.id.re_xfqc);
            re_xfqc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onQicaiChange(AppDefs.CompEleType.EQUIPMENT.toString());
                    }
                }
            });

            View re_mhj = popup.findViewById(R.id.re_mhj);
            re_mhj.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onQicaiChange(AppDefs.CompEleType.EXTINGUISHING_AGENT.toString());
                    }
                }
            });
        } else {
            hidePopOption();
        }
    }

    public void hidePopOption() {
        if (popOption != null && popOption.isShowing()) {
            popOption.dismiss();
            popOption = null;
        }
    }

    public void setListener(OnQicaiOptionLogicListener listener) {
        this.listener = listener;
    }

    public interface OnQicaiOptionLogicListener{
        void onQicaiChange(String type);
    }
}
