package com.moft.xfapply.widget.dialog;

import com.moft.xfapply.R;
import com.moft.xfapply.utils.StringUtil;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ProgressDialog {

    private static AlertDialog dlg = null;
    private static ProgressBar pb_progressbar = null;
    
    public static void show(Context cxt, String title, String content, final OnProgressCancelListener listener) {
        dlg = new AlertDialog.Builder(cxt).create();
        dlg.setCanceledOnTouchOutside(false);
        dlg.show();
        Window window = dlg.getWindow();

        window.setContentView(R.layout.dialog_progress);
        
        LinearLayout ll_title = (LinearLayout) window.findViewById(R.id.ll_title);
        ll_title.setVisibility(View.VISIBLE);
        
        TextView tv_title = (TextView) window.findViewById(R.id.tv_title);
        tv_title.setText(title);

        LinearLayout ll_content = (LinearLayout) window.findViewById(R.id.ll_content);
        if (!StringUtil.isEmpty(content)) {        
            ll_content.setVisibility(View.VISIBLE);
            
            TextView tv_content = (TextView) window.findViewById(R.id.tv_content);
            tv_content.setText(content);
        } else {
            ll_content.setVisibility(View.GONE);
        }
        
        pb_progressbar = (ProgressBar) window.findViewById(R.id.pb_progressbar);

        TextView ll_doing_ng = (TextView) window.findViewById(R.id.ll_doing_ng);
        ll_doing_ng.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {                
                dlg.dismiss();
            }
        });
        
        dlg.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (listener != null) {
                    listener.onCancel();
                }
            }
        });
    }
    
    public static void setProgress(int progress) {
        pb_progressbar.setProgress(progress);
    }
    
    public static void dismiss() {
        dlg.dismiss();
    }

    public interface OnProgressCancelListener{
        void onCancel();
    }
}
