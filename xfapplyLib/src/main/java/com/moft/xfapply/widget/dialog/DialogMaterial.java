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
import com.moft.xfapply.model.KeyUnitJson.MaterialJsonInfo;
import com.moft.xfapply.model.KeyUnitJson.ProductJsonInfo;
import com.moft.xfapply.utils.StringUtil;
import com.moft.xfapply.utils.Utils;

public class DialogMaterial {

    public static void show(Context cxt, boolean isEdit, String title, MaterialJsonInfo info, final DialogComposition.OnEditDoingListener listener) {
        final AlertDialog dlg = new AlertDialog.Builder(cxt).create();
        dlg.show();
        Window window = dlg.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        window.setContentView(R.layout.dialog_keyunit_material);
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

    private static void initEditView(final AlertDialog dlg, Window window, final MaterialJsonInfo info, final DialogComposition.OnEditDoingListener listener) {
        final EditText et_name = (EditText)window.findViewById(R.id.et_name);
        final EditText et_storage = (EditText)window.findViewById(R.id.et_storage);
        final EditText et_nature = (EditText) window.findViewById(R.id.et_nature);
        et_name.setVisibility(View.VISIBLE);
        et_storage.setVisibility(View.VISIBLE);
        et_nature.setVisibility(View.VISIBLE);
        et_name.setText(StringUtil.get(info.getName()));
        et_storage.setText(StringUtil.get(info.getStorage()));
        et_nature.setText(StringUtil.get(info.getNature()));

        TextView ll_doing_ok = (TextView) window.findViewById(R.id.ll_doing_ok);
        ll_doing_ok.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                dlg.dismiss();
                info.setName(et_name.getText().toString());
                info.setStorage(Utils.convertToDouble(et_storage.getText().toString()));
                info.setNature(et_nature.getText().toString());
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
    private static void initView(Window window, MaterialJsonInfo info) {
        final TextView tv_name = (TextView)window.findViewById(R.id.tv_name_value);
        final TextView tv_storage = (TextView)window.findViewById(R.id.tv_storage_value);
        final TextView tv_nature = (TextView) window.findViewById(R.id.tv_nature);
        tv_name.setVisibility(View.VISIBLE);
        tv_storage.setVisibility(View.VISIBLE);
        tv_nature.setVisibility(View.VISIBLE);
        tv_name.setText(StringUtil.get(info.getName()));
        tv_storage.setText(StringUtil.get(info.getStorage()));
        tv_nature.setText(StringUtil.get(info.getNature()));
    }

    public interface OnEditDoingListener{
        void onOK();
        void onNG();
    }
}
