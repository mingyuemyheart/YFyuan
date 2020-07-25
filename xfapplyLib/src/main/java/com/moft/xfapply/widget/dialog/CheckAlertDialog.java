package com.moft.xfapply.widget.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.moft.xfapply.R;

public class CheckAlertDialog {

    public static void show(Context cxt, String content, final OnDoingListener listener) {
        final AlertDialog dlg = new AlertDialog.Builder(cxt).create();
        dlg.show();
        Window window = dlg.getWindow();

        window.setContentView(R.layout.dialog_check_alert);
        LinearLayout ll_title = (LinearLayout) window.findViewById(R.id.ll_title);
        ll_title.setVisibility(View.VISIBLE);
        
        TextView tv_title = (TextView) window.findViewById(R.id.tv_title);
        tv_title.setText("提示");
        
        TextView tv_content = (TextView) window.findViewById(R.id.tv_content);
        tv_content.setText(content);

        final CheckBox cb_hint = (CheckBox) window.findViewById(R.id.cb_hint);

        TextView ll_doing_ok = (TextView) window.findViewById(R.id.ll_doing_ok);
        ll_doing_ok.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onOK(cb_hint.isChecked());
                }
                
                dlg.dismiss();
            }
        });

        TextView ll_doing_ng = (TextView) window.findViewById(R.id.ll_doing_ng);
        ll_doing_ng.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onNG(cb_hint.isChecked());
                }
                
                dlg.dismiss();
            }
        });
    }

    public static void show(Context cxt, String content, final OnDoingListener listener, boolean isTouchOutside) {
        final AlertDialog dlg = new AlertDialog.Builder(cxt).create();
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

        final CheckBox cb_hint = (CheckBox) window.findViewById(R.id.cb_hint);

        TextView ll_doing_ok = (TextView) window.findViewById(R.id.ll_doing_ok);
        ll_doing_ok.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onOK(cb_hint.isChecked());
                }

                dlg.dismiss();
            }
        });

        TextView ll_doing_ng = (TextView) window.findViewById(R.id.ll_doing_ng);
        ll_doing_ng.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onNG(cb_hint.isChecked());
                }

                dlg.dismiss();
            }
        });
    }


    public static void show(Context cxt, String content, final OnDoingListener listener, String ok, String ng) {
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

        final CheckBox cb_hint = (CheckBox) window.findViewById(R.id.cb_hint);

        TextView ll_doing_ok = (TextView) window.findViewById(R.id.ll_doing_ok);
        ll_doing_ok.setText(ok);
        ll_doing_ok.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onOK(cb_hint.isChecked());
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
                    listener.onNG(cb_hint.isChecked());
                }
                
                dlg.dismiss();
            }
        });
    }

    public interface OnDoingListener{
        void onOK(boolean isChecked);
        void onNG(boolean isChecked);
    }
}
