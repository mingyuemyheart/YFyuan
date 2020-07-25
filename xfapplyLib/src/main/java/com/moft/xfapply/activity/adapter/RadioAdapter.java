package com.moft.xfapply.activity.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.moft.xfapply.R;
import com.moft.xfapply.model.common.Dictionary;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sxf on 2019-04-10.
 */

public class RadioAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;
    private OnItemSelectedListener mListener = null;

    private int curIndex = -1;
    private List<Dictionary> typeList = new ArrayList<>();

    public RadioAdapter(Context context, List<Dictionary> list, int cur, OnItemSelectedListener listener) {
        this.context = context;
        inflater = LayoutInflater.from(context);

        typeList = list;
        curIndex = cur;
        mListener = listener;
    }

    @Override
    public int getCount() {
        return typeList.size();
    }

    @Override
    public Dictionary getItem(int i) {
        return typeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.radio_sel_item, null);

            holder = new ViewHolder();
            holder.re_parent = convertView.findViewById(R.id.re_parent);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Dictionary dic = getItem(position);

        holder.tv_name.setText(dic.getValue());
        if (curIndex == position) {
            holder.tv_name.setTextColor(Color.rgb(0, 116, 199));
            holder.re_parent.setBackgroundResource(R.drawable.btn_list_item_bg);
        } else {
            holder.tv_name.setTextColor(Color.rgb(53, 53, 53));
            holder.re_parent.setBackgroundResource(R.drawable.btn_list_item_bg_level2);
        }

        final int pos = position;
        holder.re_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curIndex = pos;
                if (mListener != null) {
                    mListener.onItemSelected(dic, curIndex);
                }
            }
        });

        return convertView;
    }

    public interface OnItemSelectedListener{
        void onItemSelected(Dictionary dic, int pos);
    }

    private static class ViewHolder {
        TextView tv_name;
        View re_parent;
    }
}
