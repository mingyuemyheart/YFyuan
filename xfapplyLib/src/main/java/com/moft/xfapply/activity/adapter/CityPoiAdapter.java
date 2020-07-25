package com.moft.xfapply.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moft.xfapply.R;
import com.moft.xfapply.model.common.CityPoi;

import java.util.List;

/**
 * Created by sxf on 2019-04-22.
 */

public class CityPoiAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;
    private OnItemSelectedListener mListener = null;
    private List<CityPoi> vrList;

    public CityPoiAdapter(Context context, List<CityPoi> mt, OnItemSelectedListener l) {
        this.context = context;
        inflater = LayoutInflater.from(context);

        mListener = l;
        vrList = mt;
    }

    @Override
    public int getCount() {
        return vrList.size();
    }

    @Override
    public CityPoi getItem(int position) {
        return vrList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_city_poi, null);

            holder = new ViewHolder();
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_result = (TextView) convertView.findViewById(R.id.tv_result);
            holder.iv_avatar = (ImageView) convertView.findViewById(R.id.iv_avatar);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        CityPoi vr = getItem(position);

        holder.tv_name.setText(vr.getCityInfo());

        if (vr.getTotalCnt() == -1) {
            holder.tv_result.setText("正在搜索中...");
        } else {
            holder.tv_result.setText("共找到" + vr.getTotalCnt() + "条相关结果");
        }

        final int pos = position;
        RelativeLayout re_parent = (RelativeLayout) convertView.findViewById(R.id.re_parent);
        re_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    CityPoi dto = vrList.get(pos);
                    mListener.onItemClick(dto);
                }
            }
        });
        re_parent.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                if (mListener != null) {
                    CityPoi dto = vrList.get(pos);
                    mListener.onItemLongClick(dto);
                }

                return false;
            }
        });
        return convertView;
    }

    public interface OnItemSelectedListener{
        void onItemClick(CityPoi dto);
        void onItemLongClick(CityPoi dto);
    }

    private static class ViewHolder {
        TextView tv_name;
        TextView tv_result;
        ImageView iv_avatar;
    }
}
