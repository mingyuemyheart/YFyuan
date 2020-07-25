package com.moft.xfapply.activity.logic;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moft.xfapply.R;
import com.moft.xfapply.base.ViewLogic;

/**
 * Created by sxf on 2019-04-20.
 */

public class ThemeLogic extends ViewLogic {
    public ThemeLogic(View v, Activity a) {
        super(v, a);
    }

    private OnThemeLogicListener listener = null;
    private PopupWindow popOption = null;

    private LinearLayout re_theme = null;
    private View re_theme_dw = null;
    private View re_theme_dz = null;
    private ImageView iv_theme_dw = null;
    private ImageView iv_theme_dz = null;
    private TextView tv_theme_dw = null;
    private TextView tv_theme_dz = null;
    private View re_theme_sy = null;
    private ImageView iv_theme_sy = null;
    private TextView tv_theme_sy = null;
    private View re_theme_sy1 = null;
    private ImageView iv_theme_sy1 = null;
    private TextView tv_theme_sy1 = null;
    private View re_theme_sy2 = null;
    private ImageView iv_theme_sy2 = null;
    private TextView tv_theme_sy2 = null;
    private View re_theme_sy3 = null;
    private ImageView iv_theme_sy3 = null;
    private TextView tv_theme_sy3 = null;

    public void init() {
        re_theme = (LinearLayout)getView().findViewById(R.id.re_theme);
        re_theme.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showPopOption();
            }
        });
    }

    private void showPopOption() {
        if (popOption == null || !popOption.isShowing()) {
            int[] location = new int[2];
            re_theme.getLocationOnScreen(location);

            View popup = getActivity().getLayoutInflater().inflate(R.layout.popup_theme, null);
            popOption = new PopupWindow(popup, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);
            popOption.setOutsideTouchable(true);

            re_theme.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            int width = re_theme.getMeasuredWidth();
            popOption.showAtLocation(re_theme, Gravity.NO_GRAVITY, location[0] + width + 20, location[1]);
            popOption.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                }
            });

            initThemeView(popup);
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

    private void resetThemeView() {
        iv_theme_dw.setImageResource(R.drawable.marker_zddw_map);
        iv_theme_dz.setImageResource(R.drawable.marker_zqzd_map);
        re_theme_dw.setTag(false);
        re_theme_dz.setTag(false);
        tv_theme_dw.setTextColor(Color.rgb(53, 53, 53));
        tv_theme_dz.setTextColor(Color.rgb(53, 53, 53));
        iv_theme_sy.setImageResource(R.drawable.marker_hydrant_map);
        re_theme_sy.setTag(false);
        tv_theme_sy.setTextColor(Color.rgb(53, 53, 53));
        iv_theme_sy1.setImageResource(R.drawable.marker_crane_map);
        re_theme_sy1.setTag(false);
        tv_theme_sy1.setTextColor(Color.rgb(53, 53, 53));
        iv_theme_sy2.setImageResource(R.drawable.marker_pool_map);
        re_theme_sy2.setTag(false);
        tv_theme_sy2.setTextColor(Color.rgb(53, 53, 53));
        iv_theme_sy3.setImageResource(R.drawable.marker_naturalwater_map);
        re_theme_sy3.setTag(false);
        tv_theme_sy3.setTextColor(Color.rgb(53, 53, 53));
    }

    private void initThemeView(View parent) {
        re_theme_dw = parent.findViewById(R.id.re_theme_dw);
        iv_theme_dw = (ImageView) parent.findViewById(R.id.iv_theme_dw);
        tv_theme_dw = (TextView) parent.findViewById(R.id.tv_theme_dw);
        re_theme_dw.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                boolean isSel = false;
                if (v.getTag() != null) {
                    isSel = (Boolean) v.getTag();
                }
                resetThemeView();
                v.setTag(!isSel);

                if (!isSel) {
                    iv_theme_dw.setImageResource(R.drawable.marker_zddw_cur);
                    iv_theme_dz.setImageResource(R.drawable.marker_zqzd_map);
                    re_theme_dz.setTag(isSel);
                    tv_theme_dw.setTextColor(Color.rgb(242, 103, 110));
                    tv_theme_dz.setTextColor(Color.rgb(53, 53, 53));
                    tv_theme_sy.setTextColor(Color.rgb(53, 53, 53));
                    iv_theme_sy.setImageResource(R.drawable.marker_hydrant_map);
                    re_theme_sy.setTag(isSel);
                    tv_theme_sy1.setTextColor(Color.rgb(53, 53, 53));
                    iv_theme_sy1.setImageResource(R.drawable.marker_crane_map);
                    re_theme_sy1.setTag(isSel);
                    tv_theme_sy2.setTextColor(Color.rgb(53, 53, 53));
                    iv_theme_sy2.setImageResource(R.drawable.marker_pool_map);
                    re_theme_sy2.setTag(isSel);
                    tv_theme_sy3.setTextColor(Color.rgb(53, 53, 53));
                    iv_theme_sy3.setImageResource(R.drawable.marker_naturalwater_map);
                    re_theme_sy3.setTag(isSel);
                    if (listener != null) {
                        listener.onLoadThemeData(0);
                    }
                } else {
                    iv_theme_dw.setImageResource(R.drawable.marker_zddw_map);
                    tv_theme_dw.setTextColor(Color.rgb(53, 53, 53));
                }
                if (listener != null) {
                    listener.onThemeChange();
                }
                hidePopOption();
            }
        });
        re_theme_dz = parent.findViewById(R.id.re_theme_dz);
        iv_theme_dz = (ImageView) parent.findViewById(R.id.iv_theme_dz);
        tv_theme_dz = (TextView) parent.findViewById(R.id.tv_theme_dz);
        re_theme_dz.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                boolean isSel = false;
                if (v.getTag() != null) {
                    isSel = (Boolean) v.getTag();
                }
                resetThemeView();
                v.setTag(!isSel);

                if (!isSel) {
                    iv_theme_dz.setImageResource(R.drawable.marker_zqzd_cur);
                    iv_theme_dw.setImageResource(R.drawable.marker_zddw_map);
                    re_theme_dw.setTag(isSel);
                    tv_theme_dw.setTextColor(Color.rgb(53, 53, 53));
                    tv_theme_dz.setTextColor(Color.rgb(242, 103, 110));
                    tv_theme_sy.setTextColor(Color.rgb(53, 53, 53));
                    iv_theme_sy.setImageResource(R.drawable.marker_hydrant_map);
                    re_theme_sy.setTag(isSel);
                    tv_theme_sy1.setTextColor(Color.rgb(53, 53, 53));
                    iv_theme_sy1.setImageResource(R.drawable.marker_crane_map);
                    re_theme_sy1.setTag(isSel);
                    tv_theme_sy2.setTextColor(Color.rgb(53, 53, 53));
                    iv_theme_sy2.setImageResource(R.drawable.marker_pool_map);
                    re_theme_sy2.setTag(isSel);
                    tv_theme_sy3.setTextColor(Color.rgb(53, 53, 53));
                    iv_theme_sy3.setImageResource(R.drawable.marker_naturalwater_map);
                    re_theme_sy3.setTag(isSel);
                    if (listener != null) {
                        listener.onLoadThemeData(1);
                    }
                } else {
                    iv_theme_dz.setImageResource(R.drawable.marker_zqzd_map);
                    tv_theme_dz.setTextColor(Color.rgb(53, 53, 53));
                }
                if (listener != null) {
                    listener.onThemeChange();
                }
                hidePopOption();
            }
        });
        re_theme_sy = parent.findViewById(R.id.re_theme_sy);
        iv_theme_sy = (ImageView) parent.findViewById(R.id.iv_theme_sy);
        tv_theme_sy = (TextView) parent.findViewById(R.id.tv_theme_sy);
        re_theme_sy.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                boolean isSel = false;
                if (v.getTag() != null) {
                    isSel = (Boolean) v.getTag();
                }
                resetThemeView();
                v.setTag(!isSel);

                if (!isSel) {
                    iv_theme_sy.setImageResource(R.drawable.marker_hydrant_cur);
                    iv_theme_dw.setImageResource(R.drawable.marker_zddw_map);
                    iv_theme_dz.setImageResource(R.drawable.marker_zqzd_map);
                    re_theme_dw.setTag(isSel);
                    re_theme_dz.setTag(isSel);
                    tv_theme_dw.setTextColor(Color.rgb(53, 53, 53));
                    tv_theme_dz.setTextColor(Color.rgb(53, 53, 53));
                    tv_theme_sy.setTextColor(Color.rgb(242, 103, 110));
                    tv_theme_sy1.setTextColor(Color.rgb(53, 53, 53));
                    iv_theme_sy1.setImageResource(R.drawable.marker_crane_map);
                    re_theme_sy1.setTag(isSel);
                    tv_theme_sy2.setTextColor(Color.rgb(53, 53, 53));
                    iv_theme_sy2.setImageResource(R.drawable.marker_pool_map);
                    re_theme_sy2.setTag(isSel);
                    tv_theme_sy3.setTextColor(Color.rgb(53, 53, 53));
                    iv_theme_sy3.setImageResource(R.drawable.marker_naturalwater_map);
                    re_theme_sy3.setTag(isSel);
                    if (listener != null) {
                        listener.onLoadThemeData(2);
                    }
                } else {
                    iv_theme_sy.setImageResource(R.drawable.marker_hydrant_map);
                    tv_theme_sy.setTextColor(Color.rgb(53, 53, 53));
                }
                if (listener != null) {
                    listener.onThemeChange();
                }
                hidePopOption();
            }
        });
        re_theme_sy1 = parent.findViewById(R.id.re_theme_sy1);
        iv_theme_sy1 = (ImageView) parent.findViewById(R.id.iv_theme_sy1);
        tv_theme_sy1 = (TextView) parent.findViewById(R.id.tv_theme_sy1);
        re_theme_sy1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                boolean isSel = false;
                if (v.getTag() != null) {
                    isSel = (Boolean) v.getTag();
                }
                resetThemeView();
                v.setTag(!isSel);

                if (!isSel) {
                    iv_theme_sy.setImageResource(R.drawable.marker_hydrant_map);
                    iv_theme_dw.setImageResource(R.drawable.marker_zddw_map);
                    iv_theme_dz.setImageResource(R.drawable.marker_zqzd_map);
                    re_theme_dw.setTag(isSel);
                    re_theme_dz.setTag(isSel);
                    tv_theme_dw.setTextColor(Color.rgb(53, 53, 53));
                    tv_theme_dz.setTextColor(Color.rgb(53, 53, 53));
                    tv_theme_sy.setTextColor(Color.rgb(53, 53, 53));
                    tv_theme_sy1.setTextColor(Color.rgb(242, 103, 110));
                    iv_theme_sy1.setImageResource(R.drawable.marker_crane_cur);
                    re_theme_sy1.setTag(isSel);
                    tv_theme_sy2.setTextColor(Color.rgb(53, 53, 53));
                    iv_theme_sy2.setImageResource(R.drawable.marker_pool_map);
                    re_theme_sy2.setTag(isSel);
                    tv_theme_sy3.setTextColor(Color.rgb(53, 53, 53));
                    iv_theme_sy3.setImageResource(R.drawable.marker_naturalwater_map);
                    re_theme_sy3.setTag(isSel);
                    if (listener != null) {
                        listener.onLoadThemeData(3);
                    }
                } else {
                    iv_theme_sy1.setImageResource(R.drawable.marker_crane_map);
                    tv_theme_sy1.setTextColor(Color.rgb(53, 53, 53));
                }
                if (listener != null) {
                    listener.onThemeChange();
                }
                hidePopOption();
            }
        });
        re_theme_sy2 = parent.findViewById(R.id.re_theme_sy2);
        iv_theme_sy2 = (ImageView) parent.findViewById(R.id.iv_theme_sy2);
        tv_theme_sy2 = (TextView) parent.findViewById(R.id.tv_theme_sy2);
        re_theme_sy2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                boolean isSel = false;
                if (v.getTag() != null) {
                    isSel = (Boolean) v.getTag();
                }
                resetThemeView();
                v.setTag(!isSel);

                if (!isSel) {
                    iv_theme_sy.setImageResource(R.drawable.marker_hydrant_map);
                    iv_theme_dw.setImageResource(R.drawable.marker_zddw_map);
                    iv_theme_dz.setImageResource(R.drawable.marker_zqzd_map);
                    re_theme_dw.setTag(isSel);
                    re_theme_dz.setTag(isSel);
                    tv_theme_dw.setTextColor(Color.rgb(53, 53, 53));
                    tv_theme_dz.setTextColor(Color.rgb(53, 53, 53));
                    tv_theme_sy.setTextColor(Color.rgb(53, 53, 53));
                    tv_theme_sy1.setTextColor(Color.rgb(53, 53, 53));
                    iv_theme_sy1.setImageResource(R.drawable.marker_crane_map);
                    re_theme_sy1.setTag(isSel);
                    tv_theme_sy2.setTextColor(Color.rgb(242, 103, 110));
                    iv_theme_sy2.setImageResource(R.drawable.marker_pool_cur);
                    re_theme_sy2.setTag(isSel);
                    tv_theme_sy3.setTextColor(Color.rgb(53, 53, 53));
                    iv_theme_sy3.setImageResource(R.drawable.marker_naturalwater_map);
                    re_theme_sy3.setTag(isSel);
                    if (listener != null) {
                        listener.onLoadThemeData(4);
                    }
                } else {
                    iv_theme_sy2.setImageResource(R.drawable.marker_pool_map);
                    tv_theme_sy2.setTextColor(Color.rgb(53, 53, 53));
                }
                if (listener != null) {
                    listener.onThemeChange();
                }
                hidePopOption();
            }
        });
        re_theme_sy3 = parent.findViewById(R.id.re_theme_sy3);
        iv_theme_sy3 = (ImageView) parent.findViewById(R.id.iv_theme_sy3);
        tv_theme_sy3 = (TextView) parent.findViewById(R.id.tv_theme_sy3);
        re_theme_sy3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                boolean isSel = false;
                if (v.getTag() != null) {
                    isSel = (Boolean) v.getTag();
                }
                resetThemeView();
                v.setTag(!isSel);

                if (!isSel) {
                    iv_theme_sy.setImageResource(R.drawable.marker_hydrant_map);
                    iv_theme_dw.setImageResource(R.drawable.marker_zddw_map);
                    iv_theme_dz.setImageResource(R.drawable.marker_zqzd_map);
                    re_theme_dw.setTag(isSel);
                    re_theme_dz.setTag(isSel);
                    tv_theme_dw.setTextColor(Color.rgb(53, 53, 53));
                    tv_theme_dz.setTextColor(Color.rgb(53, 53, 53));
                    tv_theme_sy.setTextColor(Color.rgb(53, 53, 53));
                    tv_theme_sy1.setTextColor(Color.rgb(53, 53, 53));
                    iv_theme_sy1.setImageResource(R.drawable.marker_crane_map);
                    re_theme_sy1.setTag(isSel);
                    tv_theme_sy2.setTextColor(Color.rgb(53, 53, 53));
                    iv_theme_sy2.setImageResource(R.drawable.marker_pool_map);
                    re_theme_sy2.setTag(isSel);
                    tv_theme_sy3.setTextColor(Color.rgb(242, 103, 110));
                    iv_theme_sy3.setImageResource(R.drawable.marker_naturalwater_cur);
                    re_theme_sy3.setTag(isSel);
                    if (listener != null) {
                        listener.onLoadThemeData(5);
                    }
                } else {
                    iv_theme_sy3.setImageResource(R.drawable.marker_naturalwater_map);
                    tv_theme_sy3.setTextColor(Color.rgb(53, 53, 53));
                }
                if (listener != null) {
                    listener.onThemeChange();
                }
                hidePopOption();
            }
        });
    }

    public void setListener(OnThemeLogicListener listener) {
        this.listener = listener;
    }

    public interface OnThemeLogicListener{
        void onThemeChange();
        void onLoadThemeData(int type);
    }
}
