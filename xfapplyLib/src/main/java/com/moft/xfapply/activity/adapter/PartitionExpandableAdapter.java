package com.moft.xfapply.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.moft.xfapply.R;
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.model.base.IPartitionInfo;
import com.moft.xfapply.model.common.Dictionary;
import com.moft.xfapply.model.common.PartitionCategary;
import com.moft.xfapply.utils.DictionaryUtil;
import com.moft.xfapply.utils.StringUtil;

import java.util.List;

/*
 * @Author: 王泉
 * @Date:   2019-02-25
 * No.              Date.          Modifier    Description
 * 【T886049】      2019-02-25     王泉       终端与平台数据一致，实时同步（新建数据）
 */
public class PartitionExpandableAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<PartitionCategary> groupList;
    private OnGroupClickListener listener;

    public PartitionExpandableAdapter(Context context, List<PartitionCategary> partitionCategaries) {
        this.context = context;
        groupList = partitionCategaries;
    }

    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int count = 0;
        if (groupPosition < groupList.size() && groupList.get(groupPosition) != null) {

            count = groupList.get(groupPosition).getPartitionInfos().size();
        } else {
            //TODO Toast internet is not avaliable
        }
        return count;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groupList.get(groupPosition).getPartitionInfos().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder viewHolder = null;

        if (null == convertView) {
            viewHolder = new GroupViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.expandlist_partition, null);
            viewHolder.ll_group = (LinearLayout) convertView.findViewById(R.id.ll_group);
            viewHolder.iv_collapse = (ImageView) convertView.findViewById(R.id.iv_collapse);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_address = (TextView) convertView.findViewById(R.id.tv_address);
            convertView.setTag(R.id.item_view_holder, viewHolder);
            viewHolder.ll_group.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = (int)view.getTag();
                        listener.onGroupClick(view, position);
                    }
                }
            });
        } else {
            viewHolder = (GroupViewHolder) convertView.getTag(R.id.item_view_holder);
        }
        PartitionCategary info = groupList.get(groupPosition);

        convertView.setTag(info);
        if(isExpanded) {
            viewHolder.iv_collapse.setImageResource(R.drawable.squared_minus);
        } else {
            viewHolder.iv_collapse.setImageResource(R.drawable.squared_plus);
        }
        viewHolder.tv_name.setText(getValue(info.getName(), "未知名称"));
        viewHolder.tv_address.setText(getValue(info.getAddress(), "位置未知"));
        viewHolder.ll_group.setTag(groupPosition);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder viewHolder = null;

        if (null == convertView) {
            viewHolder = new ChildViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.expandlist_partition_item, null);

            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_address = (TextView) convertView.findViewById(R.id.tv_address);
            viewHolder.ll_item = (LinearLayout)convertView.findViewById(R.id.ll_item);

            convertView.setTag(R.id.item_view_holder, viewHolder);

            viewHolder.ll_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onItemClick(view);
                    }
                }
            });
        } else {
            viewHolder = (ChildViewHolder) convertView.getTag(R.id.item_view_holder);
        }

        IPartitionInfo info = groupList.get(groupPosition).getPartitionInfos().get(childPosition);
        String name = getValue(info.getName(), "未知名称");
        String type = DictionaryUtil.getValueByCode(info.getPartitionType(), LvApplication.getInstance().getCompDicMap().get("PARTITION_TYPE"));
        if(!StringUtil.isEmpty(type)) {
            name  = name + String.format("（%s）", type);
        }
        viewHolder.tv_name.setText(name);
        viewHolder.tv_address.setText(getValue(info.getAddress(), "位置未知"));
        viewHolder.ll_item.setTag(info);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void setOnGroupClickListener(OnGroupClickListener listener) {
        this.listener = listener;
    }

    private String getValue(String name, String defaultValue) {
        String str = defaultValue;
        if(!StringUtil.isEmpty(name)) {
            str = name;
        }
        return str;
    }

    private static class GroupViewHolder {
        LinearLayout ll_group;
        ImageView iv_collapse;
        TextView tv_name;
        TextView tv_address;
    }

    private static class ChildViewHolder {
        LinearLayout ll_item;
        TextView tv_name;
        TextView tv_address;
    }

    public interface OnGroupClickListener {
        void onGroupClick(View view, int index);
        void onItemClick(View view);
    }
}
