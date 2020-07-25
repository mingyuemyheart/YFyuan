package com.moft.xfapply.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.moft.xfapply.R;
import com.moft.xfapply.model.common.WhpViewInfo;
import com.moft.xfapply.model.entity.HazardChemicalDBInfo;

import java.util.List;

public class HazardChemicalAdapter extends BaseAdapter {
	private Context mContext;

	private LayoutInflater mInflater;

	private List<WhpViewInfo> hcList;
	private OnItemSelectedListener listener;

	private ViewHolder mHolder;

	public HazardChemicalAdapter(Context context, List<WhpViewInfo> list, OnItemSelectedListener listener) {
		this.mContext = context;
		this.hcList = list;
		this.listener = listener;
		this.mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return hcList.size();
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
			convertView = mInflater.inflate(R.layout.item_hazard_chemical, null);
			mHolder = new ViewHolder();
			mHolder.name = (TextView) convertView.findViewById(R.id.name);
			mHolder.duxing = (TextView) convertView.findViewById(R.id.duxing);
			mHolder.content = (TextView) convertView.findViewById(R.id.content);
			mHolder.llItem = (LinearLayout)convertView.findViewById(R.id.ll_item);
			convertView.setTag(R.id.item_view_holder, mHolder);
		} else {
			mHolder = (ViewHolder) convertView.getTag(R.id.item_view_holder);
		}
		final WhpViewInfo info = hcList.get(position);
		mHolder.name.setText(info.getName());
		mHolder.duxing.setText(info.getProperty1());
		mHolder.content.setText(info.getProperty2());

		mHolder.llItem.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (listener != null) {
					listener.onItemClick(info);
				}
			}
		});
		convertView.setTag(info);
		return convertView;
	}

	private final class ViewHolder {
		TextView name;
		TextView duxing;
		TextView content;
		LinearLayout llItem;
	}

	public interface OnItemSelectedListener{
		void onItemClick(WhpViewInfo info);
		void onItemLongClick(WhpViewInfo info);
	}

}
