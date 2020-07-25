package com.moft.xfapply.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.moft.xfapply.R;
import com.moft.xfapply.activity.logic.DetailLogic;
import com.moft.xfapply.activity.logic.FireEngineLogic;
import com.moft.xfapply.activity.logic.SingalLogic;
import com.moft.xfapply.activity.logic.ZddwLogic;
import com.moft.xfapply.app.Constant;
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.base.BaseActivity;
import com.moft.xfapply.event.CommandEvent;
import com.moft.xfapply.model.external.AppDefs;
import com.moft.xfapply.model.external.dto.VehicleRealDataDTO;
import com.moft.xfapply.utils.StringUtil;
import com.moft.xfapply.widget.dialog.CustomAlertDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;

public class GpsDetailActivity extends BaseActivity {

    private TextView tv_plate_no;
    private TextView tv_vendor;
    private TextView tv_dep_name;
    private TextView tv_status;
    private TextView tv_location;
    private TextView tv_altitude;
    private TextView tv_velocity;
    private TextView tv_gas;
    private TextView tv_mileage;

    private VehicleRealDataDTO vehicleInfo;

    // 886078 【地图】在线车辆详细，文言显示问题：保留两位小数 2019-02-18 songmy
    DecimalFormat decimalFormat = new DecimalFormat("###################.##");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps_vehicle_detail);
        initView();
        initData();
    }

    private void initView() {
        tv_plate_no = (TextView)findViewById(R.id.tv_plate_no);
        tv_vendor = (TextView)findViewById(R.id.tv_vendor);
        tv_dep_name = (TextView)findViewById(R.id.tv_dep_name);
        tv_status = (TextView)findViewById(R.id.tv_status);
        tv_location = (TextView)findViewById(R.id.tv_location);
        tv_altitude = (TextView)findViewById(R.id.tv_altitude);
        tv_velocity = (TextView)findViewById(R.id.tv_velocity);
        tv_gas = (TextView)findViewById(R.id.tv_gas);
        tv_mileage = (TextView)findViewById(R.id.tv_mileage);
    }

    private void initData() {
        vehicleInfo = (VehicleRealDataDTO) getIntent().getSerializableExtra(Constant.GEO_TYPE_VEHICLE_GPS);
        if(vehicleInfo != null) {
            tv_plate_no.setText(StringUtil.get(vehicleInfo.getPlateNo()));
            tv_vendor.setText(StringUtil.get(vehicleInfo.getVendor()));
            tv_dep_name.setText(StringUtil.get(vehicleInfo.getDepName()));
            tv_status.setText(vehicleInfo.getOnline() ? "在线" : "不在线");
            tv_location.setText(StringUtil.get(vehicleInfo.getLocation()));
            // 886078 【地图】在线车辆详细，文言显示问题：保留两位小数 2019-02-18 songmy 开始
            // tv_altitude.setText(StringUtil.get(vehicleInfo.getAltitude()));
            // tv_velocity.setText(StringUtil.get(vehicleInfo.getVelocity()));
            // tv_gas.setText(StringUtil.get(vehicleInfo.getGas()));
            // tv_mileage.setText(StringUtil.get(vehicleInfo.getMileage()));
            tv_altitude.setText(decimalFormat.format(vehicleInfo.getAltitude()));
            tv_velocity.setText(decimalFormat.format(vehicleInfo.getVelocity()));
            tv_gas.setText(decimalFormat.format(vehicleInfo.getGas()));
            tv_mileage.setText(decimalFormat.format(vehicleInfo.getMileage()));
            // 886078 【地图】在线车辆详细，文言显示问题：保留两位小数 2019-02-18 songmy 结束
        } else {
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            back(null);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
