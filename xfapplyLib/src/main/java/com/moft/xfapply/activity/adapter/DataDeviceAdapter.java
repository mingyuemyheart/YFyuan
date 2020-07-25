package com.moft.xfapply.activity.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.moft.xfapply.R;
import com.moft.xfapply.activity.bussiness.CommonBussiness;
import com.moft.xfapply.logic.setting.PrefUserInfo;
import com.moft.xfapply.model.external.AppDefs;
import com.moft.xfapply.model.external.dto.AppDeviceUsageSurveyChildDTO;
import com.moft.xfapply.utils.StringUtil;

import java.text.DecimalFormat;
import java.util.List;

@SuppressLint("InflateParams")
public class DataDeviceAdapter extends BaseAdapter {
    private DecimalFormat decimalFormat = new DecimalFormat("###################.##");
    private LayoutInflater inflater;
    private Context context;
    private List<AppDeviceUsageSurveyChildDTO> list;
    private boolean isAverageTime;
    private Integer dayCount;

    public DataDeviceAdapter(Context context, List<AppDeviceUsageSurveyChildDTO> list, boolean isAverageTime, Integer dayCount) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.list = list;
        this.isAverageTime = isAverageTime;
        this.dayCount = dayCount;
    }
    
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public AppDeviceUsageSurveyChildDTO getItem(int position) {
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
            convertView = inflater.inflate(R.layout.item_data_mobile, null);
            
            holder = new ViewHolder();
            holder.re_bg = convertView.findViewById(R.id.re_bg);
            holder.tv_no = (TextView) convertView.findViewById(R.id.tv_no);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_name_zd = (TextView) convertView.findViewById(R.id.tv_name_zd);
            holder.tv_count = (TextView) convertView.findViewById(R.id.tv_count);
            holder.tv_integrity = (TextView) convertView.findViewById(R.id.tv_integrity);
            
            convertView.setTag(R.id.item_view_holder, holder);
        } else {
            holder = (ViewHolder) convertView.getTag(R.id.item_view_holder);
        }

        AppDeviceUsageSurveyChildDTO info = list.get(position);

        if (position % 2 != 0) {
            holder.re_bg.setBackgroundResource(R.drawable.btn_list_item_bg);
        } else {
            holder.re_bg.setBackgroundResource(R.drawable.btn_list_item_bg_level2);
        }

        holder.tv_no.setText(StringUtil.get(position + 1));
        holder.tv_name.setText(StringUtil.get(info.getDepartmentName()));
        if(!isAverageTime) {
            holder.tv_integrity.setText(String.format("%sh", getUseTime(info)));
            holder.tv_count.setText(String.format("%d台", info.getTotalCnt()));
        } else {
            holder.tv_integrity.setText(String.format("%sh", getUseCJAverageTime(info)));
            holder.tv_count.setText(String.format("%sh", getUseYYAverageTime(info)));
        }

        if(AppDefs.CompEleType.DETACHMENT.toString().equals(checkDepartmentCode(info.getDepartmentCode()))) {
            holder.tv_name_zd.setVisibility(View.GONE);
        } else {
            PrefUserInfo pui = PrefUserInfo.getInstance();
            String grade = pui.getUserInfo("department_grade");
            if ("0".equals(grade)) {
                holder.tv_name_zd.setVisibility(View.VISIBLE);
                if (!StringUtil.isEmpty(info.getDetachmentCode())) {
                    holder.tv_name_zd.setText(CommonBussiness.getInstance().getDistrictNameByCode(info.getDetachmentCode().substring(0, 4)));
                }
//                holder.tv_name_zd.setText(info.getDetachmentName() == null ? "" : info.getDetachmentName().replace("支队", ""));
            } else {
                holder.tv_name_zd.setVisibility(View.GONE);
            }
        }

        convertView.setTag(info);
        return convertView;
    }

    private String checkDepartmentCode(String departmentCode) {
        String station = AppDefs.CompEleType.SQUADRON.toString();
        if(!StringUtil.isEmpty(departmentCode)) {
            if(departmentCode.length() >= 8) {
                String str = departmentCode.substring(departmentCode.length() - 4);
                if("0000".equals(str)) {
                    station = AppDefs.CompEleType.DETACHMENT.toString();
                    return station;
                }
                str = departmentCode.substring(departmentCode.length() - 2);
                if("00".equals(str)) {
                    station = AppDefs.CompEleType.BATTALION.toString();
                    return station;
                }
            }
        }
        return station;
    }

    private String getUseTime(AppDeviceUsageSurveyChildDTO info) {
        Float operateTime = 0f;
        if(info.getCjOperateTime() != null) {
            operateTime += info.getCjOperateTime();
        }
        if(info.getYyOperateTime() != null) {
            operateTime += info.getYyOperateTime();
        }
        return decimalFormat.format(operateTime);
    }

    private String getUseYYAverageTime(AppDeviceUsageSurveyChildDTO info) {
        Float operateTime = info.getYyOperateTime();
        Integer useCount = info.getYyUseCnt();

        if (operateTime == null || operateTime == 0 ||
                        useCount == null || useCount == 0 ||
                        dayCount == null || dayCount == 0) {
            return "0";
        }
        return decimalFormat.format(operateTime / dayCount / useCount);
    }

    private String getUseCJAverageTime(AppDeviceUsageSurveyChildDTO info) {
        Float operateTime = info.getCjOperateTime();
        Integer useCount = info.getCjUseCnt();

        if (operateTime == null || operateTime == 0 ||
                useCount == null || useCount == 0 ||
                dayCount == null || dayCount == 0) {
            return "0";
        }
        return decimalFormat.format(operateTime / dayCount / useCount);
    }

    private static class ViewHolder {
        TextView tv_no;
        TextView tv_name;
        TextView tv_name_zd;
        TextView tv_count;
        TextView tv_integrity;
        View re_bg;
    }
}