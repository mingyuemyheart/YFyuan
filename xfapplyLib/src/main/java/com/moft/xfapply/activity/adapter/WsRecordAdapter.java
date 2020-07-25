package com.moft.xfapply.activity.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
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
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.model.common.Dictionary;
import com.moft.xfapply.model.common.PrimaryAttribute;
import com.moft.xfapply.model.common.QueryCondition;
import com.moft.xfapply.model.entity.GeomElementDBInfo;
import com.moft.xfapply.utils.StringUtil;

import java.util.List;

@SuppressLint("InflateParams")
public class WsRecordAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;
    private OnItemSelectedListener mListener = null;

    private List<GeomElementDBInfo> eleList;
    private QueryCondition mCondition = null;

    public WsRecordAdapter(Context context, List<GeomElementDBInfo> mt, OnItemSelectedListener l) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        
        mListener = l;
        eleList = mt;
    }
    
    @Override
    public int getCount() {
        return eleList.size();
    }

    @Override
    public GeomElementDBInfo getItem(int position) {
        return eleList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_ws_record, null);
            
            holder = new ViewHolder();
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_code = (TextView) convertView.findViewById(R.id.tv_code);
            holder.tv_address = (TextView) convertView.findViewById(R.id.tv_address);

            holder.tv_extra = (TextView) convertView.findViewById(R.id.tv_extra);
            holder.tv_extra.setVisibility(View.VISIBLE);

            holder.tv_quality = (TextView) convertView.findViewById(R.id.tv_quality);

            holder.iv_status = (ImageView) convertView.findViewById(R.id.iv_status);
            holder.iv_avatar = (ImageView) convertView.findViewById(R.id.iv_avatar);
            
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        GeomElementDBInfo info = getItem(position);

        PrimaryAttribute primaryAttribute = info.getPrimaryValues();
        holder.tv_code.setText(StringUtil.get(primaryAttribute.getPrimaryValue1()));
        holder.tv_name.setText(StringUtil.get(primaryAttribute.getPrimaryValue2()));
        if (StringUtil.isEmpty(StringUtil.get(primaryAttribute.getPrimaryValue2()))) {
            holder.tv_name.setVisibility(View.GONE);
        } else {
            holder.tv_name.setVisibility(View.VISIBLE);
        }
        holder.tv_address.setText(StringUtil.get(primaryAttribute.getPrimaryValue3()));

        String pv4 = StringUtil.get(primaryAttribute.getPrimaryValue4());
        int perIndex = pv4.indexOf("%");
        String pv4SubQuality = pv4.substring(0, perIndex+1);
        String pv4SubVName = pv4.substring(perIndex + 1);
        if (StringUtil.isEmpty(pv4SubVName) && mCondition != null &&
                mCondition.getType() == QueryCondition.TYPE_POSITION) {
            double dis = info.getDistance(mCondition.getLng(), mCondition.getLat());
            pv4SubVName = "" + (int)dis + "米";
        }

        if (!StringUtil.isEmpty(pv4SubQuality)) {
            holder.tv_quality.setVisibility(View.VISIBLE);
            holder.tv_quality.setText(pv4SubQuality);
            double quality = Double.valueOf(pv4SubQuality.substring(0, pv4SubQuality.length()-1));
            if (quality > 100) {
                quality = 100;
                holder.tv_quality.setText("100%");
            }
            if (quality >= 80) {
                holder.tv_quality.setTextColor(Color.rgb(120, 182, 49));
                holder.tv_quality.setBackgroundColor(Color.rgb(219, 239, 196));
            } else if (quality >= 60) {
                holder.tv_quality.setTextColor(Color.rgb(249, 168, 37));
                holder.tv_quality.setBackgroundColor(Color.rgb(254, 247, 236));
            } else {
                holder.tv_quality.setTextColor(Color.rgb(198, 40, 40));
                holder.tv_quality.setBackgroundColor(Color.rgb(250, 232, 232));
            }
        } else {
            holder.tv_quality.setVisibility(View.GONE);
        }

        if (!StringUtil.isEmpty(pv4SubVName)) {
            holder.tv_extra.setText(pv4SubVName);
            holder.tv_extra.setVisibility(View.VISIBLE);
        } else {
            holder.tv_extra.setVisibility(View.GONE);
        }
        holder.iv_status.setVisibility(View.GONE);

        String type = info.getResEleType();
        int resId = getResId(type);
        holder.iv_avatar.setImageResource(resId);

        final int pos = position;
        RelativeLayout re_parent = (RelativeLayout) convertView.findViewById(R.id.re_parent);
        re_parent.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {                
                if (mListener != null) {
                    GeomElementDBInfo dto = eleList.get(pos);
                    mListener.onItemClick(dto);
                }
            }
        });
        re_parent.setOnLongClickListener(new OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {                
                if (mListener != null) {
                    GeomElementDBInfo dto = eleList.get(pos);
                    mListener.onItemLongClick(dto);
                }
                
                return false;
            }
        });
        return convertView;
    }

    private int getResId(String type) {
        for (Dictionary dic : LvApplication.getInstance().getEleTypeDicList()) {
            if (dic.getCode().equals(type)) {
                return dic.getResListId();
            }
        }
        return R.drawable.icon_sy;
    }

    public void setCondition(QueryCondition mCondition) {
        this.mCondition = mCondition;
    }

    public interface OnItemSelectedListener{
        void onItemClick(GeomElementDBInfo dto);
        void onItemLongClick(GeomElementDBInfo dto);
    }
    
    private static class ViewHolder {
        TextView tv_name;
        TextView tv_code;
        TextView tv_address;
        TextView tv_extra; // ID:900352【物质器材灭火剂】追加车辆信息显示。没有的显示""
        TextView tv_quality;
        ImageView iv_avatar;
        ImageView iv_status;
    }
}