package com.moft.xfapply.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.moft.xfapply.model.common.IncOfflineTypeProcess;
import com.moft.xfapply.model.external.AppDefs;
import com.moft.xfapply.utils.DateUtil;
import com.moft.xfapply.utils.StringUtil;

import com.moft.xfapply.R;

import java.util.List;

/*
 * @Author: 王泉
 * @Date:   2019-02-25
 * No.              Date.          Modifier    Description
 * 【T886049】      2019-02-25     王泉       终端与平台数据一致，实时同步（新建数据）
 */
public class ExpandableAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<IncOfflineTypeProcess> groupList;
    private OnGroupClickListener listener;

    public ExpandableAdapter(Context context, List<IncOfflineTypeProcess> typeProcesses) {
        this.context = context;
        groupList = typeProcesses;
    }

    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int count = 0;
        if (groupPosition < groupList.size() && groupList.get(groupPosition) != null) {

            count = groupList.get(groupPosition).getExceptions().size();
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
        return groupList.get(groupPosition).getExceptions().get(childPosition);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.expandlist_group, null);
            viewHolder.ll_group = (LinearLayout) convertView.findViewById(R.id.ll_group);
            viewHolder.iv_collapse = (ImageView) convertView.findViewById(R.id.iv_collapse);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_update_date = (TextView) convertView.findViewById(R.id.tv_update_date);
            viewHolder.tv_count = (TextView) convertView.findViewById(R.id.tv_count);
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
        IncOfflineTypeProcess process = groupList.get(groupPosition);

        convertView.setTag(process);
        if(isExpanded) {
            viewHolder.iv_collapse.setImageResource(R.drawable.city_arrow_up);
        } else {
            viewHolder.iv_collapse.setImageResource(R.drawable.city_arrow);
        }
        viewHolder.tv_name.setText(convertToName(process.getType()));
        viewHolder.tv_update_date.setText(DateUtil.format(process.getUpdateDate()));
        viewHolder.tv_count.setText(String.format("异常数（%s）", StringUtil.get(process.getExceptions().size())));
        viewHolder.ll_group.setTag(groupPosition);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder viewHolder = null;

        if (null == convertView) {
            viewHolder = new ChildViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.expandlist_item, null);

            viewHolder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);

            convertView.setTag(R.id.item_view_holder, viewHolder);
        } else {
            viewHolder = (ChildViewHolder) convertView.getTag(R.id.item_view_holder);
        }

        String exception = groupList.get(groupPosition).getExceptions().get(childPosition);
        viewHolder.tv_content.setText(exception);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void setOnGroupClickListener(OnGroupClickListener listener) {
        this.listener = listener;
    }

    private String convertToName(String type) {
        String name = "未知";
        if(AppDefs.IncDataType.DEPARTMENT.toString().equals(type)) {
            name = "部门组织";
        } else if(AppDefs.IncDataType.MATERIAL.toString().equals(type)) {
            name = "应急装备";
        } else if(AppDefs.IncDataType.STATION.toString().equals(type)) {
            name = "应急力量";
        } else if(AppDefs.IncDataType.WATERSOURCE.toString().equals(type)) {
            name = "水源";
        } else if(AppDefs.IncDataType.KEY_UNIT.toString().equals(type)) {
            name = "重点单位";
        } else if(AppDefs.IncDataType.JOINT_FORCE.toString().equals(type)) {
            name = "联动力量";
        }
        return name;
    }
    private static class GroupViewHolder {
        LinearLayout ll_group;
        ImageView iv_collapse;
        TextView tv_name;
        TextView tv_update_date;
        TextView tv_count;
    }

    private static class ChildViewHolder {
        TextView tv_content;
    }

    public interface OnGroupClickListener {
        void onGroupClick(View view, int index);
    }
}
