package com.moft.xfapply.activity.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.moft.xfapply.R;
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.model.base.IPartitionInfo;
import com.moft.xfapply.model.entity.MaterialExtinguishingAgentDBInfo;
import com.moft.xfapply.model.entity.PartitionFloorDBInfo;
import com.moft.xfapply.model.entity.PartitionStorageDBInfo;
import com.moft.xfapply.model.external.AppDefs;
import com.moft.xfapply.utils.DictionaryUtil;
import com.moft.xfapply.utils.StringUtil;

import java.util.List;

@SuppressLint("InflateParams")
public class FireEngineExtinguishingAgentAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;

    private List<MaterialExtinguishingAgentDBInfo> list;

    public FireEngineExtinguishingAgentAdapter(Context context, List<MaterialExtinguishingAgentDBInfo> list) {
        this.context = context;
        inflater = LayoutInflater.from(context);

        this.list = list;
    }
    
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public MaterialExtinguishingAgentDBInfo getItem(int position) {
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

        MaterialExtinguishingAgentDBInfo info = getItem(position);

        holder.tv_name.setText(info.getName());
        holder.tv_count.setText(String.format("车载量：%s", StringUtil.get(info.getInventory())));
        convertView.setTag(info);

        return convertView;
    }
    
    private static class ViewHolder {
        TextView tv_name;
        TextView tv_count;
    }
}