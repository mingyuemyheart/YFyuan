package com.moft.xfapply.activity.logic;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.moft.xfapply.R;
import com.moft.xfapply.base.ViewLogic;
import com.moft.xfapply.logic.setting.PrefSetting;

/**
 * Created by sxf on 2019-04-20.
 */

public class NearLogic extends ViewLogic {
    public NearLogic(View v, Activity a) {
        super(v, a);
    }

    private OnNearLogicListener listener = null;
    private PopupWindow popOption = null;

    private LinearLayout re_near = null;
    private int defaultColor = Color.rgb(225, 101, 49);
    private int seletedColor = Color.rgb(0, 116, 199);

    public void init() {
        re_near = (LinearLayout)getView().findViewById(R.id.re_near);
        if (re_near != null) {
            re_near.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show();
                    if (listener != null) {
                        listener.onNearShow();
                    }
                }
            });
        }
    }

    public void show() {
        if (popOption == null || !popOption.isShowing()) {
            int[] location = new int[2];
            re_near.getLocationOnScreen(location);

            View popup = getActivity().getLayoutInflater().inflate(R.layout.popup_near, null);
            popOption = new PopupWindow(popup, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);
            popOption.setOutsideTouchable(true);

            re_near.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            int width = re_near.getMeasuredWidth();
            popOption.showAtLocation(re_near, Gravity.NO_GRAVITY, location[0] + width + 20, location[1]);
            popOption.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                }
            });

            initView(popup);
        } else {
            hide();
        }
    }

    public void hide() {
        if (popOption != null && popOption.isShowing()) {
            popOption.dismiss();
            popOption = null;
        }
    }

    private void initView(final View parent) {
        View re_near1 = parent.findViewById(R.id.re_near1);
        re_near1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int dis = 200;
                initTextView(parent, dis);

                if (listener != null) {
                    listener.onNearSetting("0.2");
                }
                hide();
            }
        });
        View re_near2 = parent.findViewById(R.id.re_near2);
        re_near2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int dis = 500;
                initTextView(parent, dis);

                if (listener != null) {
                    listener.onNearSetting("0.5");
                }
                hide();
            }
        });
        View re_near3 = parent.findViewById(R.id.re_near3);
        re_near3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int dis = 1000;
                initTextView(parent, dis);

                if (listener != null) {
                    listener.onNearSetting("1");
                }
                hide();
            }
        });
        View re_near4 = parent.findViewById(R.id.re_near4);
        re_near4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int dis = 3000;
                initTextView(parent, dis);

                if (listener != null) {
                    listener.onNearSetting("3");
                }
            }
        });
        View re_near5 = parent.findViewById(R.id.re_near5);
        re_near5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int dis = 5000;
                initTextView(parent, dis);

                if (listener != null) {
                    listener.onNearSetting("5");
                }
                hide();
            }
        });
        View re_near6 = parent.findViewById(R.id.re_near6);
        re_near6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int dis = 10000;
                initTextView(parent, dis);

                if (listener != null) {
                    listener.onNearSetting("10");
                }
                hide();
            }
        });

        String defaultDis = PrefSetting.getInstance().getLoadDis();
        int dis = (int)(Double.valueOf(defaultDis) * 1000);
        initTextView(parent, dis);
    }

    private void initTextView(View parent, int dis) {
        TextView tv_near1 = (TextView) parent.findViewById(R.id.tv_near1);
        TextView tv_near11 = (TextView) parent.findViewById(R.id.tv_near11);
        tv_near1.setTextColor(defaultColor);
        tv_near11.setTextColor(defaultColor);

        TextView tv_near2 = (TextView) parent.findViewById(R.id.tv_near2);
        TextView tv_near21 = (TextView) parent.findViewById(R.id.tv_near21);
        tv_near2.setTextColor(defaultColor);
        tv_near21.setTextColor(defaultColor);

        TextView tv_near3 = (TextView) parent.findViewById(R.id.tv_near3);
        TextView tv_near31 = (TextView) parent.findViewById(R.id.tv_near31);
        tv_near3.setTextColor(defaultColor);
        tv_near31.setTextColor(defaultColor);

        TextView tv_near4 = (TextView) parent.findViewById(R.id.tv_near4);
        TextView tv_near41 = (TextView) parent.findViewById(R.id.tv_near41);
        tv_near4.setTextColor(defaultColor);
        tv_near41.setTextColor(defaultColor);

        TextView tv_near5 = (TextView) parent.findViewById(R.id.tv_near5);
        TextView tv_near51 = (TextView) parent.findViewById(R.id.tv_near51);
        tv_near5.setTextColor(defaultColor);
        tv_near51.setTextColor(defaultColor);

        TextView tv_near6 = (TextView) parent.findViewById(R.id.tv_near6);
        TextView tv_near61 = (TextView) parent.findViewById(R.id.tv_near61);
        tv_near6.setTextColor(defaultColor);
        tv_near61.setTextColor(defaultColor);

        if (200 == dis) {
            tv_near1.setTextColor(seletedColor);
            tv_near11.setTextColor(seletedColor);
        } else if (500 == dis) {
            tv_near2.setTextColor(seletedColor);
            tv_near21.setTextColor(seletedColor);
        } else if (1000 == dis) {
            tv_near3.setTextColor(seletedColor);
            tv_near31.setTextColor(seletedColor);
        } else if (3000 == dis) {
            tv_near4.setTextColor(seletedColor);
            tv_near41.setTextColor(seletedColor);
        } else if (5000 == dis) {
            tv_near5.setTextColor(seletedColor);
            tv_near51.setTextColor(seletedColor);
        } else if (10000 == dis) {
            tv_near6.setTextColor(seletedColor);
            tv_near61.setTextColor(seletedColor);
        }
    }

    public void setListener(OnNearLogicListener listener) {
        this.listener = listener;
    }

    public interface OnNearLogicListener{
        void onNearSetting(String dis);
        void onNearShow();
    }
}
