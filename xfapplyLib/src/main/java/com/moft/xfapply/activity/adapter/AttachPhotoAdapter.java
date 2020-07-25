package com.moft.xfapply.activity.adapter;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.moft.xfapply.R;
import com.moft.xfapply.app.Constant;
import com.moft.xfapply.logic.setting.PrefSetting;
import com.moft.xfapply.model.base.IMediaInfo;
import com.moft.xfapply.model.common.AddMedia;
import com.moft.xfapply.model.external.AppDefs;

public class AttachPhotoAdapter extends BaseAdapter {
    private Context mContext;
    private View mView;
    private LayoutInflater inflater;

    private AttachFileAdapterListener mListener;

    private ArrayList<IMediaInfo> afList = new ArrayList<>();

    private Boolean editable = true;
    private IMediaInfo innerAddAttach = null;

    private PopupWindow popInfo = null;
    private TextView tv_content1 = null;
    private List<Integer> selectedList = new ArrayList<>();
    private boolean isSelecting = false;
    private int picWidth = 120;

    public void setListener(AttachFileAdapterListener l) {
        mListener = l;
    }

    public void setAfList(List<IMediaInfo> mediaList) {
        afList.clear();
        for(IMediaInfo item : mediaList) {
            if (!AppDefs.MediaFormat.URL.name().equals(item.getMediaFormat())) { // 当类型为url时，因为是全景数据，所以不需要展示
                this.afList.add(item);
            }
        }
    }

    public AttachPhotoAdapter(Context context, View v) {
        mContext = context;
        mView = v;
        inflater = LayoutInflater.from(context);
    }

    public AttachPhotoAdapter(Context context, View v,
                              List<IMediaInfo> mediaList, AttachFileAdapterListener rpl) {
        mContext = context;
        mView = v;
        inflater = LayoutInflater.from(context);
        mListener = rpl;

        afList.clear();
        for(IMediaInfo item : mediaList) {
            this.afList.add(item);
        }
    }

    @Override
    public int getCount() {
        int cnt = afList == null ? 0 : afList.size();

        if (innerAddAttach != null) {
            cnt += 1;
        }

        return cnt;
    }

    @Override
    public IMediaInfo getItem(int position) {
        if (position == afList.size()) {
            return innerAddAttach;
        }

        return afList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        IMediaInfo af = getItem(position);

        MyGridViewHolder viewHolder;
        if (convertView == null) {
            int layoutId = R.layout.item_photo_add;
            if(!(af instanceof AddMedia)) {
                layoutId = R.layout.item_photo;
            }
            convertView = inflater.inflate(layoutId, null);

            viewHolder = new MyGridViewHolder();

            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.image);
            viewHolder.iv_select = (ImageView) convertView.findViewById(R.id.iv_select);
            viewHolder.video_flag = (ImageView) convertView.findViewById(R.id.video_flag);
            viewHolder.text = (TextView) convertView.findViewById(R.id.text);

            convertView.setTag(viewHolder);
            convertView.setLayoutParams(new GridView.LayoutParams(picWidth, picWidth));
        } else {
            viewHolder = (MyGridViewHolder) convertView.getTag();
        }

        String fName = af.getMediaDescription();

        if (AppDefs.MediaFormat.IMG.toString().equals(af.getMediaFormat())) {
            viewHolder.imageView.setImageResource(R.drawable.ic_stub);
            viewHolder.text.setText(fName);
            viewHolder.text.setVisibility(View.VISIBLE);

            String remoteFullPath = af.getMediaUrl();
            remoteFullPath = Constant.URL_HEAD + PrefSetting.getInstance().getServerIP()
                    + Constant.URL_MIDDLE + PrefSetting.getInstance().getServerPort()
                    + Constant.SERVICE_NAME
                    + Constant.ATTACH_NAME + remoteFullPath;
            int dotIndex = remoteFullPath.lastIndexOf(".");
            String prefixPath = remoteFullPath.substring(0, dotIndex);
            String tailPath = remoteFullPath.substring(dotIndex);
            String remoteFullPathT1 = prefixPath + "_t1" + tailPath;

            DrawableRequestBuilder<String> thumbnailRequest =
                    Glide.with(mContext).load(remoteFullPathT1);

            Glide.with(mContext)
                    .load(remoteFullPath)
                    .placeholder(R.drawable.ic_stub)
                    .thumbnail(thumbnailRequest)
                    .into(viewHolder.imageView);
        } else if (AppDefs.MediaFormat.NONE.toString().equals(af.getMediaFormat())) {
            viewHolder.imageView.setImageResource(R.drawable.icon_file_none);
            viewHolder.text.setText(fName);
            viewHolder.text.setVisibility(View.VISIBLE);
        }

        if (!(af instanceof AddMedia)) {
            if (isSelecting) {
                viewHolder.iv_select.setVisibility(View.VISIBLE);
                if (isSelected(position)) {
                    viewHolder.iv_select.setImageResource(R.drawable.dx_checkbox_on);
                } else {
                    viewHolder.iv_select.setVisibility(View.GONE);
                }
            } else {
                viewHolder.iv_select.setVisibility(View.GONE);
            }
        }

        convertView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                IMediaInfo af = getItem(position);
                if (isSelecting) {
                    if (af instanceof AddMedia) {
                        return;
                    }
                    changeSelectItem(position);
                    notifyDataSetChanged();
                    showPopInfo();
                    return;
                }

                if (af instanceof AddMedia) {
                    if (mListener != null) {
                        mListener.onTakeAttach();
                    }
                    return;
                }

                if (AppDefs.MediaFormat.IMG.toString().equals(af.getMediaFormat())) {
                    if (mListener != null) {
                        mListener.onTakePhoto(afList, position);
                    }
                } else if (AppDefs.MediaFormat.NONE.toString().equals(af.getMediaFormat())) {
                    Toast.makeText(mContext, "未知文件，无法打开！",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        if (editable == true) {
            convertView.setOnLongClickListener(new OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    IMediaInfo af = getItem(position);
                    if (af instanceof AddMedia) {
                        return false;
                    }
                    getItem(position);

                    setSelecting(true);
                    changeSelectItem(position);
                    notifyDataSetChanged();

                    showPopInfo();

                    return false;
                }
            });
        }
        return convertView;
    }

    private void showPopInfo() {
        if (popInfo != null && popInfo.isShowing()) {
            if (getSelectCount() == 0) {
                closePopInfo();
            } else {
                if (tv_content1 != null) {
                    tv_content1.setText("删除" + getSelectCount() + "项");
                }
            }
            return;
        }

        View popInfoView = inflater.inflate(R.layout.menu_delete, null);

        popInfo = new PopupWindow(popInfoView, LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT, true);
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
        tv_content1.setText("删除" + getSelectCount() + "项");

        LinearLayout ll_content1 = (LinearLayout) popInfoView.findViewById(R.id.ll_content1);
        ll_content1.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onRemoveAttach();
                }

                closePopInfo();
            }
        });

        LinearLayout ll_content2 = (LinearLayout) popInfoView.findViewById(R.id.ll_content2);
        ll_content2.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                closePopInfo();
            }
        });
    }

    private void closePopInfo() {
        clearSelectItem();
        setSelecting(false);
        notifyDataSetChanged();

        popInfo.dismiss();
        popInfo = null;
    }

    public boolean isSelecting() {
        return isSelecting;
    }

    public void setSelecting(boolean isSelecting) {
        this.isSelecting = isSelecting;
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

    public void addSelectItem(int pos) {
        selectedList.add(pos);
    }

    public void clearSelectItem() {
        selectedList.clear();;
    }

    public int getSelectCount() {
        return selectedList.size();
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

    public int getSelectItem(int i) {
        return selectedList.get(i);
    }

    public void setEditalbe(boolean editable) {
        this.editable = editable;
        if(this.editable) {
            this.innerAddAttach = new AddMedia();
        }
    }

    private static class MyGridViewHolder {
        ImageView imageView;
        ImageView video_flag;
        TextView text;
        ImageView iv_select;
    }

    public interface AttachFileAdapterListener{
        void onTakeAttach();
        void onRemoveAttach();
        void onUpdateAttach(IMediaInfo info);
        void onTakePhoto(ArrayList<IMediaInfo> mediaList, int position);
    }
}
