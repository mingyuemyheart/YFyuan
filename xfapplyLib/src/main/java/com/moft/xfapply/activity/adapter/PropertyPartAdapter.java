package com.moft.xfapply.activity.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moft.xfapply.R;
import com.moft.xfapply.app.Constant;
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.model.base.IPropertyPartInfo;
import com.moft.xfapply.model.entity.DataVerifyDBInfo;
import com.moft.xfapply.model.external.AppDefs;
import com.moft.xfapply.utils.DateUtil;
import com.moft.xfapply.utils.DictionaryUtil;
import com.moft.xfapply.utils.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressLint("InflateParams")
public class PropertyPartAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;

    private List<IPropertyPartInfo> list;

    public PropertyPartAdapter(Context context, List<IPropertyPartInfo> list) {
        this.context = context;
        inflater = LayoutInflater.from(context);

        this.list = list;
    }
    
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public IPropertyPartInfo getItem(int position) {
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
            convertView = inflater.inflate(R.layout.property_part_item, null);

            holder = new ViewHolder();
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_attr1 = (TextView) convertView.findViewById(R.id.tv_attr1);
            holder.tv_attr2 = (TextView) convertView.findViewById(R.id.tv_attr2);
            holder.tv_description = (TextView) convertView.findViewById(R.id.tv_description);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        IPropertyPartInfo info = getItem(position);

        if(AppDefs.KeyUnitJsonType.JSON_COMPOSITION.toString().equals(info.getType())) {
            holder.tv_attr1.setVisibility(View.GONE);
            holder.tv_attr2.setVisibility(View.GONE);
        } else if(AppDefs.KeyUnitJsonType.JSON_MATERIAL.toString().equals(info.getType())) {
            holder.tv_attr2.setVisibility(View.GONE);
            if(StringUtil.isEmpty(info.getAttr1())) {
                holder.tv_attr1.setVisibility(View.GONE);
            } else {
                holder.tv_attr1.setText(String.format("储量：%s吨", info.getAttr1()));
            }
        } else if(AppDefs.KeyUnitJsonType.JSON_PRODUCT.toString().equals(info.getType())) {
            holder.tv_attr2.setVisibility(View.GONE);
            if(StringUtil.isEmpty(info.getAttr1())) {
                holder.tv_attr1.setVisibility(View.GONE);
            } else {
                holder.tv_attr1.setText(String.format("储量：%s吨", info.getAttr1()));
            }
        } else if(AppDefs.KeyUnitJsonType.JSON_STORAGE_MEDIA.toString().equals(info.getType())) {
            if(StringUtil.isEmpty(info.getAttr1())) {
                holder.tv_attr1.setVisibility(View.GONE);
            } else {
                holder.tv_attr1.setText(String.format("储量：%sm³", info.getAttr1()));
            }
            if(StringUtil.isEmpty(info.getAttr2())) {
                holder.tv_attr2.setVisibility(View.GONE);
            } else {
                holder.tv_attr2.setText(String.format("液位高度：%sm", info.getAttr2()));
            }
        } else if(AppDefs.KeyUnitJsonType.JSON_HAZARD_MEDIA.toString().equals(info.getType())) {
            holder.tv_attr2.setVisibility(View.GONE);
            if(StringUtil.isEmpty(info.getAttr1())) {
                holder.tv_attr1.setVisibility(View.GONE);
            } else {
                holder.tv_attr1.setText(String.format("储量：%sm³", info.getAttr1()));
            }
        }
        holder.tv_name.setText(info.getName());
        holder.tv_description.setText(info.getDescription());

        return convertView;
    }
    
    private static class ViewHolder {
        TextView tv_name;
        TextView tv_attr1;
        TextView tv_attr2;
        TextView tv_description;
    }
}