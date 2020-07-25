package com.moft.xfapply.widget.route;

import java.util.List;

import com.moft.xfapply.R;
import com.moft.xfapply.model.common.RoutePlanInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RouteLineAdapter extends BaseAdapter {

    private List<RoutePlanInfo> routeLineList;
    private LayoutInflater layoutInflater;
    private int[] resNumberIds = {
            R.drawable.d1, R.drawable.d2, R.drawable.d3, R.drawable.d4, R.drawable.d5};

    public RouteLineAdapter(Context context, List<RoutePlanInfo> routeLineList) {
        this.routeLineList = routeLineList;
        layoutInflater = LayoutInflater.from( context);
    }

    @Override
    public int getCount() {
        return routeLineList.size();
    }

    @Override
    public Object getItem(int position) {
        return routeLineList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_route, null);
            holder = new ViewHolder();
            holder.iv_number = (ImageView) convertView.findViewById(R.id.iv_number);
            holder.tv_number = (TextView) convertView.findViewById(R.id.tv_number);
            holder.tv_info1 = (TextView) convertView.findViewById(R.id.tv_info1);
            holder.tv_info2 = (TextView) convertView.findViewById(R.id.tv_info2);
            holder.tv_info31 = (TextView) convertView.findViewById(R.id.tv_info31);
            holder.tv_info32 = (TextView) convertView.findViewById(R.id.tv_info32);
            holder.tv_info4 = (TextView) convertView.findViewById(R.id.tv_info4);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        RoutePlanInfo rpi = routeLineList.get(position);
        if (position < 5) {
            holder.iv_number.setImageResource(resNumberIds[position]);
            holder.iv_number.setVisibility(View.VISIBLE);
            holder.tv_number.setVisibility(View.GONE);
        } else {
            holder.tv_number.setText("" + (position+1));
            holder.tv_number.setVisibility(View.VISIBLE);
            holder.iv_number.setVisibility(View.GONE);
        }

        holder.tv_info1.setText(rpi.getNameDesc());
        holder.tv_info31.setText(rpi.getLightNumDesc());
        holder.tv_info32.setText(rpi.getCongestionDistanceDesc());

        if (rpi.isDurationPriority()) {
            holder.tv_info2.setText(rpi.getDurationDesc());
            holder.tv_info4.setText(rpi.getDistanceDesc());
        } else {
            holder.tv_info2.setText(rpi.getDistanceDesc());
            holder.tv_info4.setText(rpi.getDurationDesc());
        }

        return convertView;
    }

    private class ViewHolder {
        public ImageView iv_number;
        public TextView tv_number;
        public TextView tv_info1;
        public TextView tv_info2;
        public TextView tv_info31;
        public TextView tv_info32;
        public TextView tv_info4;
    }
}
