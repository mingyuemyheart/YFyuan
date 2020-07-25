package com.moft.xfapply.activity.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.moft.xfapply.R;
import com.moft.xfapply.model.common.Dictionary;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sxf on 2019-04-09.
 */

public class CheckBoxAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;
    private OnItemSelectedListener mListener = null;

    private boolean isLeveling = false;
    private int background = R.drawable.btn_list_item_bg;
    private int curBackground = R.drawable.btn_list_item_bg;
    private int curIndex = 0;
    private List<Dictionary> dataList = new ArrayList<>();
    private List<Boolean> dataListCheck = new ArrayList<>();

    public CheckBoxAdapter(Context context, List<Dictionary> list,
                           List<Boolean> listCheck, int cur, OnItemSelectedListener listener) {
        this.context = context;
        inflater = LayoutInflater.from(context);

        dataList = list;
        dataListCheck = listCheck;
        curIndex = cur;
        mListener = listener;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Dictionary getItem(int i) {
        return dataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.checkbox_sel_item, null);

            holder = new ViewHolder();
            holder.re_parent = convertView.findViewById(R.id.re_parent);
            holder.re_parent.setBackgroundResource(background);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_name.setBackgroundResource(background);
            holder.iv_check = (ImageView) convertView.findViewById(R.id.iv_check);
            holder.iv_check.setBackgroundResource(background);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Dictionary dic = getItem(position);
        boolean isChecked = dataListCheck.get(position);

        holder.tv_name.setText(dic.getValue());
        if (curIndex == position) {
            holder.tv_name.setTextColor(Color.rgb(0, 116, 199));
        } else {
            holder.tv_name.setTextColor(Color.rgb(53, 53, 53));
        }

        if (curIndex == position) {
            holder.re_parent.setBackgroundResource(curBackground);
            holder.tv_name.setBackgroundResource(curBackground);
            holder.iv_check.setBackgroundResource(curBackground);
        } else {
            holder.re_parent.setBackgroundResource(background);
            holder.tv_name.setBackgroundResource(background);
            holder.iv_check.setBackgroundResource(background);
        }

        if (isChecked) {
            holder.iv_check.setImageResource(R.drawable.checkbox_outline);
        } else {
            holder.iv_check.setImageResource(R.drawable.checkbox_outline_bl);
        }

        final int pos = position;
        final ImageView iv_check = holder.iv_check;
        if (isLeveling) {
            holder.re_parent.setClickable(false);
            holder.iv_check.setClickable(true);
            holder.tv_name.setClickable(true);
            holder.iv_check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isChecked = !dataListCheck.get(pos);
                    if (isChecked) {
                        iv_check.setImageResource(R.drawable.checkbox_outline);
                    } else {
                        iv_check.setImageResource(R.drawable.checkbox_outline_bl);
                    }
                    dataListCheck.set(pos, isChecked);

                    if (mListener != null) {
                        mListener.onItemCheck(dataList.get(pos), isChecked, pos);
                    }
                }
            });
            holder.tv_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (curIndex != -1) {
                        curIndex = pos;
                        if (mListener != null) {
                            mListener.onSelectedRefresh(curIndex);
                        }
                    }

                    if (mListener != null) {
                        mListener.onItemClick(dataList.get(pos), pos);
                    }
                }
            });
        } else {
            holder.re_parent.setClickable(true);
            holder.iv_check.setClickable(false);
            holder.tv_name.setClickable(false);
            holder.re_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isChecked = !dataListCheck.get(pos);
                    if (isChecked) {
                        iv_check.setImageResource(R.drawable.checkbox_outline);
                    } else {
                        iv_check.setImageResource(R.drawable.checkbox_outline_bl);
                    }
                    dataListCheck.set(pos, isChecked);

                    if (curIndex != -1) {
                        curIndex = pos;
                        if (mListener != null) {
                            mListener.onSelectedRefresh(curIndex);
                        }
                    }

                    if (mListener != null) {
                        mListener.onItemClick(dataList.get(pos), pos);
                    }
                }
            });
        }

        return convertView;
    }

    public void setCurIndex(int index) {
        curIndex = index;
    }

    public void setBackground(int resId) {
        background = resId;
        curBackground = resId;
    }

    public void setCurBackground(int curBackground) {
        this.curBackground = curBackground;
    }

    public void setLeveling(boolean leveling) {
        isLeveling = leveling;
    }

    public interface OnItemSelectedListener{
        void onItemClick(Dictionary dic, int pos);
        void onItemCheck(Dictionary dic, boolean isChecked, int pos);
        void onSelectedRefresh(int index);
    }

    private static class ViewHolder {
        ImageView iv_check;
        TextView tv_name;
        View re_parent;
    }
}
