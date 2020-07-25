package com.moft.xfapply.activity.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moft.xfapply.R;
import com.moft.xfapply.model.entity.KeyUnitMediaDBInfo;
import com.moft.xfapply.model.external.AppDefs;
import com.moft.xfapply.widget.NoScrollGridView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SketchDiagramAdapter extends BaseAdapter{
	private List<KeyUnitMediaDBInfo> list = null;
	private Context context;
	private SketchDiagramAdapterListener listener;
	private int picWidth = 120;
	private boolean editable;
	private PopupWindow popInfo = null;
	private TextView tv_content1 = null;
	private View mView;
	private boolean isSelecting = false;
	private List<KeyUnitMediaDBInfo> selectedList = new ArrayList<>();
	private List<SketchDiagramChildAdapter> adapterList = new ArrayList<>();

	private Map<String, List<KeyUnitMediaDBInfo>> classificationMediaMap = new LinkedHashMap<String, List<KeyUnitMediaDBInfo>>() {{
		put(AppDefs.KeyUnitMediaType.KU_MEDIA_OUTDOOR_SCENE.toString(), new ArrayList<KeyUnitMediaDBInfo>());
		put(AppDefs.KeyUnitMediaType.KU_MEDIA_OVERALL_PLAN.toString(), new ArrayList<KeyUnitMediaDBInfo>());
		put(AppDefs.KeyUnitMediaType.KU_MEDIA_INTERNAL_PLANE_DIAGRAM.toString(), new ArrayList<KeyUnitMediaDBInfo>());
		put(AppDefs.KeyUnitMediaType.KU_MEDIA_COMBAT_DEPLOYMENT_DIAGRAM.toString(), new ArrayList<KeyUnitMediaDBInfo>());
		put(AppDefs.KeyUnitMediaType.KU_MEDIA_FACADE_PLAN.toString(), new ArrayList<KeyUnitMediaDBInfo>());
	}};
	private List<String> classficationList = new ArrayList<String>() {{
		add(AppDefs.KeyUnitMediaType.KU_MEDIA_OUTDOOR_SCENE.toString());
		add(AppDefs.KeyUnitMediaType.KU_MEDIA_OVERALL_PLAN.toString());
		add(AppDefs.KeyUnitMediaType.KU_MEDIA_INTERNAL_PLANE_DIAGRAM.toString());
		add(AppDefs.KeyUnitMediaType.KU_MEDIA_COMBAT_DEPLOYMENT_DIAGRAM.toString());
		add(AppDefs.KeyUnitMediaType.KU_MEDIA_FACADE_PLAN.toString());
	}};

	private Map<String, String> classificationNameMap = new HashMap<String, String>() {{
		put(AppDefs.KeyUnitMediaType.KU_MEDIA_OUTDOOR_SCENE.toString(), "实景照片");
		put(AppDefs.KeyUnitMediaType.KU_MEDIA_OVERALL_PLAN.toString(), "总体平面图");
		put(AppDefs.KeyUnitMediaType.KU_MEDIA_INTERNAL_PLANE_DIAGRAM.toString(), "内部平面图(功能分区图)");
		put(AppDefs.KeyUnitMediaType.KU_MEDIA_COMBAT_DEPLOYMENT_DIAGRAM.toString(), "作战部署图");
		put(AppDefs.KeyUnitMediaType.KU_MEDIA_FACADE_PLAN.toString(), "外立面图");
	}};

	public SketchDiagramAdapter(Context context, List<KeyUnitMediaDBInfo> list, View view) {
		this.context = context;
		this.list = list;
		this.mView = view;
		updateClassificationMedia();
	}

	private void updateClassificationMedia() {
		for(List<KeyUnitMediaDBInfo> item : classificationMediaMap.values()) {
			item.clear();
		}
		if (this.list != null && !this.list.isEmpty()) {
			for (KeyUnitMediaDBInfo media : this.list) {
				if(classificationMediaMap.containsKey(media.getClassification())) {
					classificationMediaMap.get(media.getClassification()).add(media);
				}
			}
		}
		return;
	}

	public int getCount() {
		return this.classificationMediaMap.size();
	}

	public Object getItem(int position) {
		return classificationMediaMap.get(classficationList.get(position));
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

	public View getView(final int position, View view, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(context).inflate(R.layout.pick_or_take_image_item, null);
			viewHolder.tv_classfication_name = (TextView) view.findViewById(R.id.tv_classfication_name);
			viewHolder.rl_add = (RelativeLayout)view.findViewById(R.id.rl_add);
			viewHolder.iv_add = (ImageView) view.findViewById(R.id.iv_add);
			viewHolder.gradView = (NoScrollGridView) view.findViewById(R.id.gv_content);
			view.setTag(R.id.item_view_holder, viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag(R.id.item_view_holder);
		}

		List<KeyUnitMediaDBInfo> mediaList = classificationMediaMap.get(classficationList.get(position));
		SketchDiagramChildAdapter childAdapter = new SketchDiagramChildAdapter(context, mediaList);
		adapterList.add(childAdapter);
		childAdapter.setPicWidth(picWidth);
		childAdapter.setListener(new SketchDiagramChildAdapter.SketchDiagramChildAdapterListener() {
			@Override
			public void onTakePhoto(List<KeyUnitMediaDBInfo> list, int position) {
				if(listener != null) {
					listener.onTakePhoto(list, position);
				}
			}

			@Override
			public void onSelectedItem(KeyUnitMediaDBInfo info, boolean selected) {
				if(selected) {
					if (!selectedList.contains(info)) {
						selectedList.add(info);
					}
				} else {
					if (selectedList.contains(info)) {
						selectedList.remove(info);
					}
				}
				showPopInfo();
			}

			@Override
			public void onSelectedMode(KeyUnitMediaDBInfo info) {
				for(SketchDiagramChildAdapter item : adapterList) {
					item.setSelecting(true);
				}
				if (!selectedList.contains(info)) {
					selectedList.add(info);
				}
				showPopInfo();
			}
		});
		childAdapter.setEditable(editable);
		viewHolder.gradView.setAdapter(childAdapter);

		viewHolder.tv_classfication_name.setText(classificationNameMap.get(classficationList.get(position)));
		viewHolder.rl_add.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.onTakeAttach(classficationList.get(position));
			}
		});
		if (editable) {
			viewHolder.iv_add.setVisibility(View.VISIBLE);
		} else {
			viewHolder.iv_add.setVisibility(View.GONE);
		}
		view.setTag(mediaList);
		return view;
	}

	public void setListener(SketchDiagramAdapterListener listener) {
		this.listener = listener;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setList(List<KeyUnitMediaDBInfo> list) {
		this.list = list;
		updateClassificationMedia();
	}

	private void showPopInfo() {
		if (popInfo != null && popInfo.isShowing()) {
			if (selectedList.size() == 0) {
				closePopInfo();
			} else {
				if (tv_content1 != null) {
					tv_content1.setText("删除" + selectedList.size() + "项");
				}
			}
			return;
		}

		View popInfoView = LayoutInflater.from(context).inflate(R.layout.menu_delete, null);

		popInfo = new PopupWindow(popInfoView, ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT, true);
		popInfo.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		popInfo.setOutsideTouchable(false);
		popInfo.setTouchable(true);
		popInfo.setAnimationStyle(R.style.AnimBottom);

		try {
			Method method = PopupWindow.class.getDeclaredMethod("setTouchModal", boolean.class);
			method.setAccessible(true);
			method.invoke(popInfo, false);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		popInfo.showAtLocation(mView, Gravity.BOTTOM, 0, 0);

		tv_content1 = (TextView) popInfoView.findViewById(R.id.tv_content1);
		tv_content1.setText("删除" + selectedList.size() + "项");

		LinearLayout ll_content1 = (LinearLayout) popInfoView.findViewById(R.id.ll_content1);
		ll_content1.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				if (listener != null) {
					listener.onRemoveAttach(selectedList);
				}

				closePopInfo();
			}
		});

		LinearLayout ll_content2 = (LinearLayout) popInfoView.findViewById(R.id.ll_content2);
		ll_content2.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				closePopInfo();
			}
		});
	}

	private void closePopInfo() {
		isSelecting = false;
		for(SketchDiagramChildAdapter item : adapterList) {
			item.setSelecting(false);
		}
		popInfo.dismiss();
		popInfo = null;
	}

	final public static class ViewHolder {
		public TextView tv_classfication_name;
		public RelativeLayout rl_add;
		public ImageView iv_add;
		public NoScrollGridView gradView;
	}

	public interface SketchDiagramAdapterListener{
		void onTakeAttach(String classfication);
		void onTakePhoto(List<KeyUnitMediaDBInfo> list, int position);
		void onRemoveAttach(List<KeyUnitMediaDBInfo> selectedList);
	}
}