package com.moft.xfapply.widget.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.moft.xfapply.R;
import com.moft.xfapply.model.KeyUnitJson.CompositionJsonInfo;
import com.moft.xfapply.model.base.IPropertyPartInfo;
import com.moft.xfapply.utils.StringUtil;

public class DialogComposition {

    public static void show(Context cxt,boolean isEdit, String title, CompositionJsonInfo info, final DialogComposition.OnEditDoingListener listener) {
        final AlertDialog dlg = new AlertDialog.Builder(cxt).create();
        dlg.show();
        Window window = dlg.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        window.setContentView(R.layout.dialog_keyunit_composition);
        LinearLayout ll_title = (LinearLayout) window.findViewById(R.id.ll_title);
        ll_title.setVisibility(View.VISIBLE);
        TextView tv_title = (TextView) window.findViewById(R.id.tv_title);

        LinearLayout ll_doing = (LinearLayout) window.findViewById(R.id.ll_doing);
        tv_title.setText(title);

        if(isEdit) {
            ll_doing.setVisibility(View.VISIBLE);
            initEditView(dlg, window, info, listener);
        } else {
            ll_doing.setVisibility(View.GONE);
            initView(window, info);
        }
    }

    private static void initEditView(final AlertDialog dlg, Window window, final CompositionJsonInfo info, final DialogComposition.OnEditDoingListener listener) {
        final EditText et_name = (EditText)window.findViewById(R.id.et_name);
        final EditText et_function = (EditText) window.findViewById(R.id.et_function);
        et_name.setVisibility(View.VISIBLE);
        et_function.setVisibility(View.VISIBLE);
        et_name.setText(StringUtil.get(info.getName()));
        et_function.setText(StringUtil.get(info.getFunction()));

        TextView ll_doing_ok = (TextView) window.findViewById(R.id.ll_doing_ok);
        ll_doing_ok.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                dlg.dismiss();
                info.setName(et_name.getText().toString());
                info.setFunction(et_function.getText().toString());
                if (listener != null) {
                    listener.onOK();
                }
            }
        });
        TextView ll_doing_ng = (TextView) window.findViewById(R.id.ll_doing_ng);
        ll_doing_ng.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                dlg.dismiss();
                if (listener != null) {
                    listener.onNG();
                }
            }
        });
    }
    private static void initView(Window window, CompositionJsonInfo info) {
        final TextView tv_name = (TextView)window.findViewById(R.id.tv_name_value);
        final TextView tv_function = (TextView) window.findViewById(R.id.tv_function);
        tv_name.setVisibility(View.VISIBLE);
        tv_function.setVisibility(View.VISIBLE);
        tv_name.setText(StringUtil.get(info.getName()));
        tv_function.setText(StringUtil.get(info.getFunction()));
    }

    public interface OnEditDoingListener{
        void onOK();
        void onNG();
    }
}
