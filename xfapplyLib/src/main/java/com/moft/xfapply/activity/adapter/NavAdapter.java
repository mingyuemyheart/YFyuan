package com.moft.xfapply.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.moft.xfapply.R;
import com.moft.xfapply.model.common.Dictionary;

import java.util.List;


public class NavAdapter  extends BaseAdapter  {
    private LayoutInflater inflater;
    private int curSelected;
    List<Dictionary> vbList;

    public NavAdapter(Context context, List<Dictionary> mt, int cur) {
        inflater = LayoutInflater.from(context);

        vbList = mt;
        curSelected = cur;
    }

    public NavAdapter(Context context, List<Dictionary> mt) {
        inflater = LayoutInflater.from(context);

        vbList = mt;
        curSelected = 0;
    }

    @Override
    public int getCount() {
        return vbList.size();
    }

    @Override
    public Dictionary getItem(int position) {
        return vbList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        NavAdapter.ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_nav_list, null);

            holder = new NavAdapter.ViewHolder();
            holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
            holder.iv_mark = (ImageView) convertView.findViewById(R.id.iv_mark);

            convertView.setTag(holder);
        } else {
            holder = (NavAdapter.ViewHolder) convertView.getTag();
        }

        holder.tv_content.setText(getItem(position).getValue());

        int resId = getItem(position).getResListId();
        if (resId == -1) {
            holder.iv_mark.setVisibility(View.GONE);
        } else {
            holder.iv_mark.setImageResource(resId);
            holder.iv_mark.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView tv_content;
        ImageView iv_mark;
    }
}
