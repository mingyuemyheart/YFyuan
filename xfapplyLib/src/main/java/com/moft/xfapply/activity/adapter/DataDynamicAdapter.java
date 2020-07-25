package com.moft.xfapply.activity.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moft.xfapply.R;
import com.moft.xfapply.activity.bussiness.CommonBussiness;
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.logic.setting.PrefUserInfo;
import com.moft.xfapply.model.common.Dictionary;
import com.moft.xfapply.model.external.AppDefs;
import com.moft.xfapply.model.external.dto.EntityMaintainTaskDTO;
import com.moft.xfapply.utils.DateUtil;
import com.moft.xfapply.utils.DictionaryUtil;
import com.moft.xfapply.utils.StringUtil;

import java.util.List;

@SuppressLint("InflateParams")
public class DataDynamicAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;
    private List<EntityMaintainTaskDTO> list;
    private OnItemSelectedListener mListener = null;

    public DataDynamicAdapter(Context context, List<EntityMaintainTaskDTO> list, OnItemSelectedListener l) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.list = list;
        mListener = l;
    }
    
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public EntityMaintainTaskDTO getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_data_dynamic, null);
            
            holder = new ViewHolder();
            holder.tv_description = (TextView) convertView.findViewById(R.id.tv_description);
            holder.tv_operation = (TextView) convertView.findViewById(R.id.tv_operation);
            holder.iv_operation = (ImageView) convertView.findViewById(R.id.iv_operation);
            holder.tv_update_time = (TextView) convertView.findViewById(R.id.tv_update_time);
            holder.tv_station = (TextView) convertView.findViewById(R.id.tv_station);
            holder.tv_name_zd = (TextView) convertView.findViewById(R.id.tv_name_zd);
            holder.iv_avatar = (ImageView) convertView.findViewById(R.id.iv_avatar);
            
            convertView.setTag(R.id.item_view_holder, holder);
        } else {
            holder = (ViewHolder) convertView.getTag(R.id.item_view_holder);
        }

        EntityMaintainTaskDTO info = list.get(position);


        String type = info.getEntityType();
        int resId = getResId(type);
        holder.iv_avatar.setImageResource(resId);
        if(AppDefs.ActionType.NEW.toString().equals(info.getOperateType())) {
            holder.iv_operation.setImageResource(R.drawable.status_new);
            holder.tv_operation.setText("新建");
        } else if(AppDefs.ActionType.MODIFY.toString().equals(info.getOperateType())) {
            holder.iv_operation.setImageResource(R.drawable.status_modify);
            holder.tv_operation.setText("更新");
        } else if(AppDefs.ActionType.DELETE.toString().equals(info.getOperateType())) {
            holder.iv_operation.setImageResource(R.drawable.status_delete);
            holder.tv_operation.setText("删除");
        }
        holder.tv_description.setText(info.getMaintainDescription());
        holder.tv_update_time.setText(info.getUpdateDate() == null ? "" : DateUtil.format(info.getUpdateDate(), "yyyy-MM-dd HH:mm"));

        PrefUserInfo pui = PrefUserInfo.getInstance();
        if ("0".equals(pui.getUserInfo("department_grade"))) {
            String zdName = getZdStationName(info.getDepartmentUuid());
            if(!StringUtil.isEmpty(zdName)) {
                holder.tv_name_zd.setText(zdName);
                holder.tv_name_zd.setVisibility(View.VISIBLE);
            } else {
                holder.tv_name_zd.setVisibility(View.GONE);
            }
        } else {
            holder.tv_name_zd.setVisibility(View.GONE);
        }

        holder.tv_station.setText(getStationName(info));
        convertView.setTag(info);

        final int pos = position;
        RelativeLayout re_parent = (RelativeLayout) convertView.findViewById(R.id.re_parent);
        re_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    EntityMaintainTaskDTO dto = list.get(pos);
                    mListener.onItemClick(dto);
                }
            }
        });

        return convertView;
    }

    private String getZdStationName(String departmentUuid) {
        String zdName = "";
        Dictionary curDic = DictionaryUtil.getDictionaryByCode(departmentUuid, LvApplication.getInstance().getCurrentOrgList());
        if(curDic != null) {
            String departmentCode = curDic.getId();
            if (!StringUtil.isEmpty(departmentCode)) {
                zdName = CommonBussiness.getInstance().getDistrictNameByCode(departmentCode.substring(0, 4));
            }

//            if(departmentCode.length() == 8) {
//                String str = departmentCode.substring(departmentCode.length() - 4);
//                if(!"0000".equals(str)) {
//                    str = departmentCode.substring(0, 4) + "0000";
//                    Dictionary dic = DictionaryUtil.getDictionaryById(str, LvApplication.getInstance().getCurrentOrgList());
//                    if(dic != null) {
//                        zdName = dic.getValue().replace("支队", "");
//                    }
//                }
//            }
        }
        return zdName;
    }

    private String getStationName(EntityMaintainTaskDTO info) {
        String departmentName = DictionaryUtil.getValueByCode(info.getDepartmentUuid(), LvApplication.getInstance().getCurrentOrgList());
        if(StringUtil.isEmpty(departmentName)) {
            departmentName = info.getSubmitName();
        }
        return departmentName;
    }

    private int getResId(String type) {
            for (Dictionary dic : LvApplication.getInstance().getEleTypeDicList()) {
                if (dic.getCode().equals(type)) {
                    return dic.getResListId();
                } else if(dic.getCode().startsWith(AppDefs.CompEleType.ENTERPRISE_FOURCE.toString())) {
                    return R.drawable.icon_qtxfd;
                }
            }
        return R.drawable.icon_sy;
    }

    public interface OnItemSelectedListener{
        void onItemClick(EntityMaintainTaskDTO dto);
        void onItemLongClick(EntityMaintainTaskDTO dto);
    }
    
    private static class ViewHolder {
        TextView tv_description;
        TextView tv_operation;
        ImageView iv_operation;
        TextView tv_update_time;
        TextView tv_station;
        TextView tv_name_zd;
        ImageView iv_avatar;
    }
}