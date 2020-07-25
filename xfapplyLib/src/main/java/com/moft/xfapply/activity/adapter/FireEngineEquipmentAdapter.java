package com.moft.xfapply.activity.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.moft.xfapply.R;
import com.moft.xfapply.model.entity.MaterialEquipmentDBInfo;
import com.moft.xfapply.model.entity.MaterialExtinguishingAgentDBInfo;
import com.moft.xfapply.utils.StringUtil;

import java.util.List;

@SuppressLint("InflateParams")
public class FireEngineEquipmentAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;

    private List<MaterialEquipmentDBInfo> list;

    public FireEngineEquipmentAdapter(Context context, List<MaterialEquipmentDBInfo> list) {
        this.context = context;
        inflater = LayoutInflater.from(context);

        this.list = list;
    }
    
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public MaterialEquipmentDBInfo getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fire_engine_part_item, null);

            holder = new ViewHolder();
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_count = (TextView) convertView.findViewById(R.id.tv_count);

            convertView.setTag(R.id.item_view_holder, holder);
        } else {
            holder = (ViewHolder) convertView.getTag(R.id.item_view_holder);
        }

        MaterialEquipmentDBInfo info = getItem(position);

        holder.tv_name.setText(info.getName());
        holder.tv_count.setText(String.format("数量：%s", StringUtil.get(info.getReserve())));
        convertView.setTag(info);

        return convertView;
    }
    
    private static class ViewHolder {
        TextView tv_name;
        TextView tv_count;
    }
}