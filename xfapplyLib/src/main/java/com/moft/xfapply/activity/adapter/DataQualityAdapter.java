package com.moft.xfapply.activity.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moft.xfapply.R;
import com.moft.xfapply.activity.bussiness.CommonBussiness;
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.logic.setting.PrefUserInfo;
import com.moft.xfapply.model.common.GeomElementType;
import com.moft.xfapply.model.external.AppDefs;
import com.moft.xfapply.model.external.dto.FireIntegrityStatisticsDTO;
import com.moft.xfapply.utils.StringUtil;

import java.text.DecimalFormat;
import java.util.List;

@SuppressLint("InflateParams")
public class DataQualityAdapter extends BaseAdapter {
    private DecimalFormat decimalFormat = new DecimalFormat("###################.#");
    private LayoutInflater inflater;
    private Context context;
    private List<FireIntegrityStatisticsDTO> list;

    public DataQualityAdapter(Context context, List<FireIntegrityStatisticsDTO> list) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.list = list;
    }
    
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public FireIntegrityStatisticsDTO getItem(int position) {
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
            convertView = inflater.inflate(R.layout.item_data_quality, null);
            
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

        FireIntegrityStatisticsDTO info = list.get(position);

        if (position % 2 != 0) {
            holder.re_bg.setBackgroundResource(R.drawable.btn_list_item_bg);
        } else {
            holder.re_bg.setBackgroundResource(R.drawable.btn_list_item_bg_level2);
        }

        holder.tv_no.setText(StringUtil.get(position + 1));
        holder.tv_name.setText(info.getDepartmentName());
        holder.tv_integrity.setText(String.format("%s%%", getIntegrityText(info.getTotalQuality(), info.getTotalCnt())));
        holder.tv_count.setText(StringUtil.get(info.getTotalCnt()));

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

    private String getIntegrityText(Double totalQuality, Integer count) {
        String text = "0";
        if(totalQuality == null || count == null) {
            return text;
        }
        if(count > 0) {
            double value = totalQuality * 100 / count;
            if (value > 100) {
                value = 100;
            }
            text = decimalFormat.format(value);
        }
        return text;
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