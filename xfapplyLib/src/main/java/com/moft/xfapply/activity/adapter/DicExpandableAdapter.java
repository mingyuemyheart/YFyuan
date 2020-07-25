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
import com.moft.xfapply.model.common.Dictionary;

import java.util.List;

/*
 * @Author: 王旭
 * @Date:   2019-03-04
 * Description：ID:886063 【数据】选择大中队分类，将列表显示改成树形显示。
 * 新增Adapter，用于处理树形的组织
 */
public class DicExpandableAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<Dictionary> orgList;
    private OnGroupClickListener listener;

    public DicExpandableAdapter(Context context, List<Dictionary> typeProcesses) {
        this.context = context;
        orgList = typeProcesses;
        initView();
    }

    private void initView() {

    }

    @Override
    public int getGroupCount() {
        return orgList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int count = 0;
        if (groupPosition < orgList.size() && orgList.get(groupPosition) != null) {
            count = orgList.get(groupPosition).getSubDictionary().size();
        } else {
            //TODO Toast internet is not avaliable
        }
        return count;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return orgList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return orgList.get(groupPosition).getSubDictionary().get(childPosition);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.orglist_group, null);
            viewHolder.ll_group = (LinearLayout) convertView.findViewById(R.id.ll_group);
            viewHolder.iv_collapse = (ImageView) convertView.findViewById(R.id.iv_collapse);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(R.id.item_view_holder, viewHolder);
            // 点击图标
            viewHolder.iv_collapse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = (int)view.getTag();
                        listener.onGroupClick(view,position);
                    }
                }
            });

            // 点击文字
            viewHolder.tv_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        Dictionary dic = (Dictionary)view.getTag();
                        listener.onItemClick(dic);
                    }
                }
            });
        } else {
            viewHolder = (GroupViewHolder) convertView.getTag(R.id.item_view_holder);
        }

        if(isExpanded) {
            viewHolder.iv_collapse.setImageResource(R.drawable.squared_minus);
        } else {
            viewHolder.iv_collapse.setImageResource(R.drawable.squared_plus);
        }

        Dictionary process = orgList.get(groupPosition);
        viewHolder.tv_name.setText(process.getValue());

        viewHolder.tv_name.setTag(process);
        viewHolder.iv_collapse.setTag(groupPosition);

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder viewHolder = null;

        if (null == convertView) {
            viewHolder = new ChildViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.orglist_item, null);

            viewHolder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);

            convertView.setTag(R.id.item_view_holder, viewHolder);

            viewHolder.tv_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        Dictionary dic = (Dictionary)view.getTag();
                        listener.onItemClick(dic);
                    }
                }
            });
        } else {
            viewHolder = (ChildViewHolder) convertView.getTag(R.id.item_view_holder);
        }

        String dicName = orgList.get(groupPosition).getSubDictionary().get(childPosition).getValue();
        viewHolder.tv_content.setText(dicName);

        viewHolder.tv_content.setTag(orgList.get(groupPosition).getSubDictionary().get(childPosition));

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void setOnGroupClickListener(OnGroupClickListener listener) {
        this.listener = listener;
    }

    private static class GroupViewHolder {
        LinearLayout ll_group;
        ImageView iv_collapse;
        TextView tv_name;
//        TextView tv_count;
    }

    private static class ChildViewHolder {
        TextView tv_content;
    }

    public interface OnGroupClickListener {
        void onGroupClick(View view, int index);
        void onItemClick(Dictionary item);
    }

}
