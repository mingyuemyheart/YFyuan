package com.moft.xfapply.activity.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.moft.xfapply.R;
import com.moft.xfapply.app.Constant;
import com.moft.xfapply.logic.setting.PrefSetting;
import com.moft.xfapply.model.entity.KeyUnitMediaDBInfo;
import com.moft.xfapply.model.external.AppDefs;
import com.moft.xfapply.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class SketchDiagramChildAdapter extends BaseAdapter{
	private List<KeyUnitMediaDBInfo> list = null;
	private Context context;
	private int picWidth = 120;
	private SketchDiagramChildAdapterListener listener;

	private boolean editable = false;

	private List<Integer> selectedList = new ArrayList<>();
	private boolean isSelecting = false;

	public SketchDiagramChildAdapter(Context context, List<KeyUnitMediaDBInfo> list) {
		this.context = context;
		this.list = list;
	}

	public void setListener(SketchDiagramChildAdapterListener listener) {
		this.listener = listener;
	}

	public void setList(List<KeyUnitMediaDBInfo> list) {
		this.list = list;
		this.notifyDataSetChanged();
	}

	public int getCount() {
		return this.list.size();
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public int getPicWidth() {
		return picWidth;
	}

	public void setPicWidth(int picWidth) {
		this.picWidth = picWidth;
	}

	public View getView(final int position, View convertView, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.item_photo, null);
			viewHolder.image = (ImageView) convertView.findViewById(R.id.image);
			viewHolder.iv_select = (ImageView) convertView.findViewById(R.id.iv_select);
			viewHolder.text = (TextView) convertView.findViewById(R.id.text);
			convertView.setTag(R.id.item_view_holder, viewHolder);
			AbsListView.LayoutParams params = (AbsListView.LayoutParams) convertView.getLayoutParams();
			if (params == null) {
				params = new AbsListView.LayoutParams(picWidth, picWidth);
				convertView.setLayoutParams(params);
			} else {
				params.height = picWidth;
				params.width = picWidth;
			}
		} else {
			viewHolder = (ViewHolder) convertView.getTag(R.id.item_view_holder);
		}

		final KeyUnitMediaDBInfo info = list.get(position);
		if (AppDefs.MediaFormat.IMG.toString().equals(info.getMediaFormat())) {
			viewHolder.image.setImageResource(R.drawable.ic_stub);
			viewHolder.text.setText(info.getMediaDescription());
			viewHolder.text.setVisibility(View.VISIBLE);

			String remoteFullPath = info.getMediaUrl();
			if (StringUtil.isEmpty(remoteFullPath)) {
				Glide.with(context)
						.load(info.getLocalPath())
						.placeholder(R.drawable.ic_stub)
						.into(viewHolder.image);
			} else {
				remoteFullPath = Constant.URL_HEAD + PrefSetting.getInstance().getServerIP()
						+ Constant.URL_MIDDLE + PrefSetting.getInstance().getServerPort()
						+ Constant.SERVICE_NAME
						+ Constant.ATTACH_NAME + remoteFullPath;
				int dotIndex = remoteFullPath.lastIndexOf(".");
				String prefixPath = remoteFullPath.substring(0, dotIndex);
				String tailPath = remoteFullPath.substring(dotIndex);
				String remoteFullPathT1 = prefixPath + "_t1" + tailPath;

				DrawableRequestBuilder<String> thumbnailRequest =
						Glide.with(context).load(remoteFullPathT1);

				Glide.with(context)
						.load(remoteFullPath)
						.placeholder(R.drawable.ic_stub)
						.thumbnail(thumbnailRequest)
						.into(viewHolder.image);
			}
		}
		convertView.setTag(info);

		updateSelectView(position, viewHolder.iv_select);

		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (listener != null) {
					if (isSelecting) {
						changeSelectItem(position);
						ViewHolder viewHolder = (ViewHolder) view.getTag(R.id.item_view_holder);
						updateSelectView(position, viewHolder.iv_select);
						listener.onSelectedItem((KeyUnitMediaDBInfo)view.getTag(), isSelected(position));
					} else {
						listener.onTakePhoto(list, position);
					}
				}
			}
		});

		convertView.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View view) {
				if (!editable) {
					return false;
				}

				if(!isSelecting) {
					isSelecting = true;
					changeSelectItem(position);
					ViewHolder viewHolder = (ViewHolder) view.getTag(R.id.item_view_holder);
					updateSelectView(position, viewHolder.iv_select);
					if (listener != null) {
						listener.onSelectedMode((KeyUnitMediaDBInfo) view.getTag());
					}
				}
				return false;
			}
		});
		return convertView;
	}

	private void updateSelectView(int position, ImageView selectView) {
		if (isSelecting) {
			selectView.setVisibility(View.VISIBLE);
			if (isSelected(position)) {
				selectView.setImageResource(R.drawable.dx_checkbox_on);
			} else {
				selectView.setVisibility(View.GONE);
			}
		} else {
			selectView.setVisibility(View.GONE);
		}
	}

	public void setSelecting(boolean isSelecting) {
		this.isSelecting = isSelecting;
		if(!isSelecting) {
			selectedList.clear();
			notifyDataSetChanged();
		}
	}

	public void changeSelectItem(int pos) {
		boolean result = false;

		for (Integer i : selectedList) {
			if (i.intValue() == pos) {
				result = true;
				selectedList.remove(i);
				break;
			}
		}

		if (!result) {
			selectedList.add(pos);
		}
	}

	public boolean isSelected(int pos) {
		boolean result = false;

		for (Integer i : selectedList) {
			if (i.intValue() == pos) {
				result = true;
				break;
			}
		}

		return result;
	}

	public void clearSelectItem() {
		selectedList.clear();
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	final public static class ViewHolder {
		public ImageView image;
		public TextView text;
		public ImageView iv_select;
	}

	public interface SketchDiagramChildAdapterListener {
		void onTakePhoto(List<KeyUnitMediaDBInfo> list, int position);
		void onSelectedItem(KeyUnitMediaDBInfo info, boolean selected);
		void onSelectedMode(KeyUnitMediaDBInfo info);
	}
}