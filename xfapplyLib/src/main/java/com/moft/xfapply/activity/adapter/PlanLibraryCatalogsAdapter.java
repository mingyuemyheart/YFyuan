package com.moft.xfapply.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.moft.xfapply.R;
import com.moft.xfapply.model.external.dto.PlanLibraryCatalogDTO;

import java.util.List;

/**
 * Created by sxf on 2019-05-02.
 */

public class PlanLibraryCatalogsAdapter extends BaseAdapter {
    private Context mContext;

    private LayoutInflater mInflater;

    private List<PlanLibraryCatalogDTO> fileNodeList;
    private OnItemSelectedListener listener;

    private ViewHolder mHolder;

    public PlanLibraryCatalogsAdapter(Context context, List<PlanLibraryCatalogDTO> list, OnItemSelectedListener listener) {
        this.mContext = context;
        this.fileNodeList = list;
        this.listener = listener;
        this.mInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return fileNodeList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_plan_library, null);
            mHolder = new ViewHolder();
            mHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            mHolder.tv_result = (TextView) convertView.findViewById(R.id.tv_result);
            mHolder.iv_type = (ImageView) convertView.findViewById(R.id.iv_type);
            mHolder.re_parent = convertView.findViewById(R.id.re_parent);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        final PlanLibraryCatalogDTO info = fileNodeList.get(position);

        mHolder.tv_name.setText(info.getName());
        mHolder.tv_result.setVisibility(View.GONE);
        mHolder.iv_type.setImageResource(R.drawable.icon_plan_library);

        mHolder.re_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(info);
                }
            }
        });
        return convertView;
    }

    private final class ViewHolder {
        TextView tv_name;
        TextView tv_result;
        ImageView iv_type;
        View re_parent;
    }

    public interface OnItemSelectedListener{
        void onItemClick(PlanLibraryCatalogDTO info);
        void onItemLongClick(PlanLibraryCatalogDTO info);
    }

}
