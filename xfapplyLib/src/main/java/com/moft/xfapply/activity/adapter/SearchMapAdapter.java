package com.moft.xfapply.activity.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moft.xfapply.R;
import com.moft.xfapply.app.Constant;
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.model.common.Dictionary;
import com.moft.xfapply.model.entity.MapSearchInfo;
import com.moft.xfapply.utils.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SearchMapAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;
    private Map<String, Integer> resMap;
    private OnItemSelectedListener mListener = null;

    private boolean isShowStatus = false;
    private List<MapSearchInfo> eleList;
    private String key = "";

    public SearchMapAdapter(Context context, List<MapSearchInfo> mt,
                            OnItemSelectedListener l) {
        this.context = context;
        inflater = LayoutInflater.from(context);

        mListener = l;
        
        eleList = mt;
        
        resMap = new HashMap<>();
        resMap.put(Constant.MAP_SEARCH_POI, R.drawable.map_search_suggest);
        resMap.put(Constant.MAP_SEARCH_HISTORY, R.drawable.map_search_history);
    }

    @Override
    public int getCount() {
        return eleList != null ? eleList.size() : 0;
    }

    @Override
    public MapSearchInfo getItem(int position) {
        MapSearchInfo info = null;
        if(eleList != null && eleList.size() > 0) {
            info = eleList.get(position);
        }
        return info;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_map_search, null);

            holder = new ViewHolder();
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_address = (TextView) convertView.findViewById(R.id.tv_address);
            holder.iv_avatar = (ImageView) convertView.findViewById(R.id.iv_avatar);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        MapSearchInfo vr = getItem(position);

        if(vr == null) {
            return convertView;
        }

        holder.tv_name.setText(vr.getName());
        int fstart = vr.getName().indexOf(key);
        if (fstart != -1) {
            int fend = fstart + key.length();
            SpannableStringBuilder style=new SpannableStringBuilder(vr.getName());
            style.setSpan(new ForegroundColorSpan(0xFF3FB838),
                    fstart, fend, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            holder.tv_name.setText(style);
        }

        holder.tv_address.setText(vr.getAddress());

        String type = vr.getType();
        int resId = R.drawable.map_search_suggest;
        if (Constant.MAP_SEARCH_FIRE.equals(type)) {
            String extra = vr.getExtra();
            if (!StringUtil.isEmpty(extra)) {
                String[] exs = extra.split(Constant.OUTLINE_DIVIDER);
                if (exs.length >= 1) {
                    String eleType = exs[0];
                    for (Dictionary dic : LvApplication.getInstance().getEleTypeDicList()) {
                        if (dic.getCode().equals(eleType)) {
                            resId = dic.getResMapId();
                            break;
                        }
                    }
                }
            }
        } else {
            if (resMap.containsKey(type)) {
                resId = resMap.get(type);
            }
        }
        holder.iv_avatar.setImageResource(resId);

        final int pos = position;
        RelativeLayout re_parent = (RelativeLayout) convertView.findViewById(R.id.re_parent);
        re_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    if(eleList != null && eleList.size() > 0) {
                        MapSearchInfo dto = eleList.get(pos);
                        if(dto != null) {
                            mListener.onItemClick(dto);
                        }
                    }
                }
            }
        });
        re_parent.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                if (mListener != null) {
                    if(eleList != null && eleList.size() > 0) {
                        MapSearchInfo dto = eleList.get(pos);
                        if(dto != null) {
                            mListener.onItemLongClick(dto);
                        }
                    }
                }

                return false;
            }
        });
        return convertView;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public interface OnItemSelectedListener{
        void onItemClick(MapSearchInfo dto);
        void onItemLongClick(MapSearchInfo dto);
    }

    private static class ViewHolder {
        TextView tv_name;
        TextView tv_address;
        ImageView iv_avatar;
    }
}
