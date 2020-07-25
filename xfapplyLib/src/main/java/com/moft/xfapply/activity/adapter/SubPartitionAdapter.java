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
import com.moft.xfapply.model.base.IPropertyPartInfo;
import com.moft.xfapply.model.entity.PartitionFloorDBInfo;
import com.moft.xfapply.model.entity.PartitionStorageDBInfo;
import com.moft.xfapply.model.external.AppDefs;
import com.moft.xfapply.model.external.dto.PartitionFloorDTO;
import com.moft.xfapply.utils.DictionaryUtil;
import com.moft.xfapply.utils.StringUtil;

import java.util.List;

@SuppressLint("InflateParams")
public class SubPartitionAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;

    private List<IPartitionInfo> list;

    public SubPartitionAdapter(Context context, List<IPartitionInfo> list) {
        this.context = context;
        inflater = LayoutInflater.from(context);

        this.list = list;
    }
    
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public IPartitionInfo getItem(int position) {
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
            convertView = inflater.inflate(R.layout.sub_partition_item, null);

            holder = new ViewHolder();
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_attr = (TextView) convertView.findViewById(R.id.tv_attr);
            holder.tv_description = (TextView) convertView.findViewById(R.id.tv_description);

            convertView.setTag(R.id.item_view_holder, holder);
        } else {
            holder = (ViewHolder) convertView.getTag(R.id.item_view_holder);
        }

        IPartitionInfo info = getItem(position);

        if(AppDefs.KeyUnitPartitionType.PARTITION_FLOOR.toString().equals(info.getPartitionType())) {
            PartitionFloorDBInfo floorDBInfo = (PartitionFloorDBInfo)info;
            String content = floorDBInfo.getName();
            String type = DictionaryUtil.getValueByCode(floorDBInfo.getFloorType(), LvApplication.getInstance().getCompDicMap().get("FLOOR_TYPE"));
            if(!StringUtil.isEmpty(type)) {
                content += String.format(" (%s)", type);
            }
            holder.tv_name.setText(content);
            String description = floorDBInfo.getFunctionDescription();
            if(StringUtil.isEmpty(description)) {
                description = floorDBInfo.getRemark();
            }
            holder.tv_description.setText(description);
            holder.tv_attr.setVisibility(View.GONE);
            holder.tv_description.setVisibility(View.VISIBLE);
        } else if(AppDefs.KeyUnitPartitionType.PARTITION_STORAGE.toString().equals(info.getPartitionType())) {
            PartitionStorageDBInfo storageDBInfo = (PartitionStorageDBInfo)info;
            String content = storageDBInfo.getName();
            String type = DictionaryUtil.getValueByCode(storageDBInfo.getType(), LvApplication.getInstance().getCompDicMap().get("ZDDW_CGLX"));
            if(!StringUtil.isEmpty(type)) {
                content += String.format(" (%s)", type);
            }
            holder.tv_name.setText(content);
            holder.tv_attr.setText(String.format("容量：%s 立方米", StringUtil.get(storageDBInfo.getCapacity())));
            holder.tv_attr.setVisibility(View.VISIBLE);
            holder.tv_description.setVisibility(View.GONE);
        }

        convertView.setTag(info);

        return convertView;
    }
    
    private static class ViewHolder {
        TextView tv_name;
        TextView tv_attr;
        TextView tv_description;
    }
}