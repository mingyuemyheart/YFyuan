package com.moft.xfapply.activity.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.moft.xfapply.R;
import com.moft.xfapply.model.common.LayerSelect;

import java.util.List;

@SuppressLint("InflateParams")
public class MapLayerAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;
    private OnItemSelectedListener mListener = null;

    private List<LayerSelect> vrList;

    public MapLayerAdapter(Context context, List<LayerSelect> mt, OnItemSelectedListener l) {
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
    public LayerSelect getItem(int position) {
        return vrList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_pop_layers, null);
            
            holder = new ViewHolder();
            holder.iv_layer_marker = (ImageView) convertView.findViewById(R.id.iv_layer_marker);
            holder.tv_layer_name = (TextView) convertView.findViewById(R.id.tv_layer_name);
            holder.iv_layer_option_open = (ImageView) convertView.findViewById(R.id.iv_layer_option_open);
            holder.iv_layer_option_close = (ImageView) convertView.findViewById(R.id.iv_layer_option_close);
            
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final LayerSelect vr = getItem(position);

        holder.iv_layer_marker.setImageResource(vr.getResId());
        holder.tv_layer_name.setText(vr.getName());
        if (vr.isSelected()) {
            holder.iv_layer_option_open.setVisibility(View.VISIBLE);
            holder.iv_layer_option_close.setVisibility(View.GONE);
        } else {
            holder.iv_layer_option_open.setVisibility(View.GONE);
            holder.iv_layer_option_close.setVisibility(View.VISIBLE);
        }

        holder.iv_layer_option_open.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.iv_layer_option_open.setVisibility(View.GONE);
                holder.iv_layer_option_close.setVisibility(View.VISIBLE);
                if (mListener != null) {
                    vr.setSelected(false);
                    mListener.onItemClick(vr);
                }
            }
        });

        holder.iv_layer_option_close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.iv_layer_option_open.setVisibility(View.VISIBLE);
                holder.iv_layer_option_close.setVisibility(View.GONE);
                if (mListener != null) {
                    vr.setSelected(true);
                    mListener.onItemClick(vr);
                }
            }
        });
        return convertView;
    }

    public interface OnItemSelectedListener{
        void onItemClick(LayerSelect dto);
    }
    
    private static class ViewHolder {
        ImageView iv_layer_marker;
        TextView tv_layer_name;
        ImageView iv_layer_option_open;
        ImageView iv_layer_option_close;
    }
}