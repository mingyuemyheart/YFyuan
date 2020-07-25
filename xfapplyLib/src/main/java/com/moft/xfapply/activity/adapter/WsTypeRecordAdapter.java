package com.moft.xfapply.activity.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.moft.xfapply.activity.bussiness.StandardBussiness;
import com.moft.xfapply.app.Constant;
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.model.common.GeomElementType;

import java.util.List;

@SuppressLint("InflateParams")
public class WsTypeRecordAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;
    private OnItemSelectedListener mListener = null;
    private List<GeomElementType> vrList;

    public WsTypeRecordAdapter(Context context, List<GeomElementType> mt, OnItemSelectedListener l) {
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
    public GeomElementType getItem(int position) {
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
            convertView = inflater.inflate(R.layout.item_wstype_record, null);
            
            holder = new ViewHolder();
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_result = (TextView) convertView.findViewById(R.id.tv_result);
            holder.iv_avatar = (ImageView) convertView.findViewById(R.id.iv_avatar);
            
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        GeomElementType vr = getItem(position);

        holder.tv_name.setText(vr.getType());

        String result = "共" + vr.getCount() + "条";
        if (Constant.HAZARD_CHEMICAL.equals(vr.getTypeCode())) {
            StandardBussiness sb = StandardBussiness.getInstance();
            int msdsCount = sb.getMsdsCount("");
            result = "共" + (vr.getCount()) + "条,来自MSDS库" + msdsCount + "条";
        }

        holder.tv_result.setText(result);

        int resId = LvApplication.getInstance().getEleIconResId(vr.getTypeCode());
        holder.iv_avatar.setImageResource(resId);

        final int pos = position;
        RelativeLayout re_parent = (RelativeLayout) convertView.findViewById(R.id.re_parent);
        re_parent.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {                
                if (mListener != null) {
                    GeomElementType dto = vrList.get(pos);
                    mListener.onItemClick(dto);
                }
            }
        });
        re_parent.setOnLongClickListener(new OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {                
                if (mListener != null) {
                    GeomElementType dto = vrList.get(pos);
                    mListener.onItemLongClick(dto);
                }
                
                return false;
            }
        });
        return convertView;
    }
    
    public interface OnItemSelectedListener{
        void onItemClick(GeomElementType dto);
        void onItemLongClick(GeomElementType dto);
    }
    
    private static class ViewHolder {
        TextView tv_name;
        TextView tv_result;
        ImageView iv_avatar;
    }
}