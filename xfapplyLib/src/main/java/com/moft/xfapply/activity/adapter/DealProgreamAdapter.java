package com.moft.xfapply.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.moft.xfapply.R;
import com.moft.xfapply.model.external.dto.DisposalPointDTO;
import com.moft.xfapply.model.external.dto.DisposalTagDTO;
import com.moft.xfapply.utils.StringUtil;

import java.util.List;

/**
 * Created by sxf on 2019-05-02.
 */

public class DealProgreamAdapter extends BaseAdapter {
    private Context mContext;

    private LayoutInflater mInflater;

    private List<DisposalPointDTO> fileNodeList;
    private OnItemSelectedListener listener;

    private ViewHolder mHolder;

    public DealProgreamAdapter(Context context, List<DisposalPointDTO> list, OnItemSelectedListener listener) {
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
            convertView = mInflater.inflate(R.layout.item_disposal_point, null);
            mHolder = new ViewHolder();
            mHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            mHolder.tv_result = (TextView) convertView.findViewById(R.id.tv_result);
            mHolder.iv_type = (ImageView) convertView.findViewById(R.id.iv_type);
            mHolder.re_parent = convertView.findViewById(R.id.re_parent);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        final DisposalPointDTO info = fileNodeList.get(position);

        mHolder.tv_name.setText(info.getName());

        if (info.getDisposalTagDTOS() == null || info.getDisposalTagDTOS().isEmpty()) {
            mHolder.tv_result.setVisibility(View.GONE);
        } else {
            mHolder.tv_result.setText(getLabel(info.getDisposalTagDTOS()));
            mHolder.tv_result.setVisibility(View.VISIBLE);
        }

        mHolder.iv_type.setImageResource(R.drawable.icon_filecheck);

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

    private String getLabel(List<DisposalTagDTO> disposalTagDTOS) {
        String label = "";

        if (disposalTagDTOS == null || disposalTagDTOS.isEmpty()) {
            return label;
        }

        for (int i = 0, max = disposalTagDTOS.size() ; i < max ; i++) {
            if (!StringUtil.isEmpty(disposalTagDTOS.get(i).getPropertyValue())) {
                label += disposalTagDTOS.get(i).getPropertyValue();
                if (i + 1 < max) {
                    label += "|";
                }
            }
        }

        return label;
    }

    private final class ViewHolder {
        TextView tv_name;
        TextView tv_result;
        ImageView iv_type;
        View re_parent;
    }

    public interface OnItemSelectedListener{
        void onItemClick(DisposalPointDTO info);
        void onItemLongClick(DisposalPointDTO info);
    }

}
