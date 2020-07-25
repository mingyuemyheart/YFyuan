package com.moft.xfapply.widget.dialog;

import java.util.List;

import com.moft.xfapply.R;
import com.moft.xfapply.model.common.Dictionary;
import com.moft.xfapply.widget.adapter.ListDialogAdapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ListDialog {
    
    public static void show(Context cxt, List<Dictionary> contentList, int cur, final OnSelectedListener listener) {
        final AlertDialog dlg = new AlertDialog.Builder(cxt).create();
        dlg.show();
        Window window = dlg.getWindow();

        window.setContentView(R.layout.dialog_list);
        LinearLayout ll_title = (LinearLayout) window.findViewById(R.id.ll_title);
        ll_title.setVisibility(View.VISIBLE);
        
        TextView tv_title = (TextView) window.findViewById(R.id.tv_title);
        tv_title.setText("请选择");
        
        final ListView list = (ListView) window.findViewById(R.id.list);
                
        ListDialogAdapter adapter = new ListDialogAdapter(cxt, contentList, cur);
        list.setAdapter(adapter);
        
        list.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                
                listener.onSelected(position);
                
                dlg.cancel();
            }
            
        });
    }

    public interface OnSelectedListener{
        void onSelected(int pos);
    }
}
