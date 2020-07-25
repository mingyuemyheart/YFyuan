package com.moft.xfapply.widget.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.moft.xfapply.R;
import com.moft.xfapply.activity.adapter.DicExpandableAdapter;
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.model.common.Dictionary;

import java.util.List;

/*
 * @Author: 王旭
 * @Date:   2019-03-04
 * Description：ID:886063 【数据】选择大中队分类，将列表显示改成树形显示。
 * 新增Adapter，用于处理出现显示的Dialog
 */
public class ExpandableDialog {
    
    public static void show(Context cxt, final Dictionary root, List<Dictionary> contentList, int cur, final OnSelectedListener listener) {
        final AlertDialog dlg = new AlertDialog.Builder(cxt).create();
        dlg.show();
        Window window = dlg.getWindow();

        window.setContentView(R.layout.dialog_expandable_list);

        final ExpandableListView list = (ExpandableListView) window.findViewById(R.id.list);
        list.setVisibility(View.VISIBLE);
        LinearLayout ll_title = (LinearLayout) window.findViewById(R.id.ll_title);
        ll_title.setVisibility(View.VISIBLE);

        TextView tv_title = (TextView) window.findViewById(R.id.tv_title_root);
        tv_title.setText(root.getValue());
        tv_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSelected(root);

                dlg.cancel();
            }
        });

        final ImageView iv_collapse_root = (ImageView)window.findViewById(R.id.iv_collapse_root);
        iv_collapse_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.getVisibility() == View.VISIBLE) {
                    list.setVisibility(View.INVISIBLE);
                    iv_collapse_root.setImageResource(R.drawable.squared_plus);
                } else {
                    list.setVisibility(View.VISIBLE);
                    iv_collapse_root.setImageResource(R.drawable.squared_minus);
                }
            }
        });

        list.setGroupIndicator(null);
        list.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                return true;
            }
        });
        list.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                return false;
            }
        });

        DicExpandableAdapter adapter = new DicExpandableAdapter(cxt, contentList);
        adapter.setOnGroupClickListener(new DicExpandableAdapter.OnGroupClickListener() {
            @Override
            public void onGroupClick(View view, int position) {
                boolean isExpanded = list.isGroupExpanded(position);
                if(isExpanded) {
                    list.collapseGroup(position);
                } else {
                    list.expandGroup(position);
                }
            }

            @Override
            public void onItemClick(Dictionary item) {
                listener.onSelected(item);

                dlg.cancel();
            }
        });

        list.setAdapter(adapter);
    }

    public interface OnSelectedListener{
        void onSelected(Dictionary item);
    }

    public interface OnSearchListener{
        void onSelected(int pos);
        void onSearch();
    }
}
