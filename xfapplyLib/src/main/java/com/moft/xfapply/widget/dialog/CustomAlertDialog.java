package com.moft.xfapply.widget.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.moft.xfapply.R;

public class CustomAlertDialog {

    public static Dialog show(Context cxt, String content, final OnDoingListener listener) {
        final AlertDialog dlg = new AlertDialog.Builder(cxt).create();
        dlg.show();
        Window window = dlg.getWindow();

        window.setContentView(R.layout.dialog_alert);
        LinearLayout ll_title = (LinearLayout) window.findViewById(R.id.ll_title);
        ll_title.setVisibility(View.VISIBLE);
        
        TextView tv_title = (TextView) window.findViewById(R.id.tv_title);
        tv_title.setText("提示");
        
        TextView tv_content = (TextView) window.findViewById(R.id.tv_content);
        tv_content.setText(content);

        TextView ll_doing_ok = (TextView) window.findViewById(R.id.ll_doing_ok);
        ll_doing_ok.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onOK();
                }
                
                dlg.dismiss();
            }
        });

        TextView ll_doing_ng = (TextView) window.findViewById(R.id.ll_doing_ng);
        ll_doing_ng.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onNG();
                }
                
                dlg.dismiss();
            }
        });
        return dlg;
    }

    public static void show(final AlertDialog dlg, String content, final OnDoingListener listener, boolean isTouchOutside) {
        dlg.show();
        dlg.setCanceledOnTouchOutside(isTouchOutside);// 设置点击屏幕Dialog不消失
        Window window = dlg.getWindow();

        window.setContentView(R.layout.dialog_alert);
        LinearLayout ll_title = (LinearLayout) window.findViewById(R.id.ll_title);
        ll_title.setVisibility(View.VISIBLE);

        TextView tv_title = (TextView) window.findViewById(R.id.tv_title);
        tv_title.setText("提示");

        TextView tv_content = (TextView) window.findViewById(R.id.tv_content);
        tv_content.setText(content);

        TextView ll_doing_ok = (TextView) window.findViewById(R.id.ll_doing_ok);
        ll_doing_ok.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onOK();
                }

                dlg.dismiss();
            }
        });

        TextView ll_doing_ng = (TextView) window.findViewById(R.id.ll_doing_ng);
        ll_doing_ng.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onNG();
                }

                dlg.dismiss();
            }
        });
    }

    public static Dialog show(Context cxt, String title, int layoutId, View contentView, final OnDoingListener listener) {
        final AlertDialog dlg = new AlertDialog.Builder(cxt).create();
        dlg.show();
        Window window = dlg.getWindow();

        window.setContentView(layoutId);
        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

        LinearLayout ll_title = (LinearLayout) window.findViewById(R.id.ll_title);
        ll_title.setVisibility(View.VISIBLE);

        TextView tv_title = (TextView) window.findViewById(R.id.tv_title);
        tv_title.setText(title);
        tv_title.setFocusable(true);
        tv_title.setFocusableInTouchMode(true);

        LinearLayout ll_content = (LinearLayout) window.findViewById(R.id.ll_content);
        ll_content.addView(contentView);

        TextView ll_doing_ok = (TextView) window.findViewById(R.id.ll_doing_ok);
        ll_doing_ok.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onOK();
                }

                dlg.dismiss();
            }
        });

        TextView ll_doing_ng = (TextView) window.findViewById(R.id.ll_doing_ng);
        ll_doing_ng.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onNG();
                }

                dlg.dismiss();
            }
        });
        return dlg;
    }


    public static Dialog show(Context cxt, String content, final OnDoingListener listener, String ok, String ng) {
        final AlertDialog dlg = new AlertDialog.Builder(cxt).create();
        dlg.show();
        Window window = dlg.getWindow();

        window.setContentView(R.layout.dialog_alert);
        LinearLayout ll_title = (LinearLayout) window.findViewById(R.id.ll_title);
        ll_title.setVisibility(View.VISIBLE);
        
        TextView tv_title = (TextView) window.findViewById(R.id.tv_title);
        tv_title.setText("提示");
        
        TextView tv_content = (TextView) window.findViewById(R.id.tv_content);
        tv_content.setText(content);

        TextView ll_doing_ok = (TextView) window.findViewById(R.id.ll_doing_ok);
        ll_doing_ok.setText(ok);
        ll_doing_ok.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onOK();
                }
                
                dlg.dismiss();
            }
        });

        TextView ll_doing_ng = (TextView) window.findViewById(R.id.ll_doing_ng);
        ll_doing_ng.setText(ng);
        ll_doing_ng.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onNG();
                }
                
                dlg.dismiss();
            }
        });
        return dlg;
    }

    public static Dialog show(Context cxt, String content) {
        final AlertDialog dlg = new AlertDialog.Builder(cxt).create();
        dlg.show();
        Window window = dlg.getWindow();

        window.setContentView(R.layout.dialog_alert);
        LinearLayout ll_title = (LinearLayout) window.findViewById(R.id.ll_title);
        ll_title.setVisibility(View.VISIBLE);

        TextView tv_title = (TextView) window.findViewById(R.id.tv_title);
        tv_title.setText("提示");

        TextView tv_content = (TextView) window.findViewById(R.id.tv_content);
        tv_content.setText(content);

        TextView ll_doing_ok = (TextView) window.findViewById(R.id.ll_doing_ok);
        ll_doing_ok.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                dlg.dismiss();
            }
        });

        TextView ll_doing_ng = (TextView) window.findViewById(R.id.ll_doing_ng);
        ll_doing_ng.setVisibility(View.GONE);

        return dlg;
    }

    public static void show(final AlertDialog dlg, String content) {
        dlg.show();
        Window window = dlg.getWindow();

        window.setContentView(R.layout.dialog_alert);
        LinearLayout ll_title = (LinearLayout) window.findViewById(R.id.ll_title);
        ll_title.setVisibility(View.VISIBLE);

        TextView tv_title = (TextView) window.findViewById(R.id.tv_title);
        tv_title.setText("提示");

        TextView tv_content = (TextView) window.findViewById(R.id.tv_content);
        tv_content.setText(content);

        TextView ll_doing_ok = (TextView) window.findViewById(R.id.ll_doing_ok);
        ll_doing_ok.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                dlg.dismiss();
            }
        });

        TextView ll_doing_ng = (TextView) window.findViewById(R.id.ll_doing_ng);
        ll_doing_ng.setVisibility(View.GONE);
    }
    
    public interface OnDoingListener{
        void onOK();
        void onNG();
    }
}
