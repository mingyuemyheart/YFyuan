 
package com.moft.xfapply.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.moft.xfapply.R;
import com.moft.xfapply.activity.bussiness.OfflineDataBussiness;
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.base.BaseActivity;
import com.moft.xfapply.event.CommandEvent;
import com.moft.xfapply.logic.setting.PrefSetting;
import com.moft.xfapply.model.common.Dictionary;
import com.moft.xfapply.model.entity.OfflineDBInfo;
import com.moft.xfapply.utils.StringUtil;
import com.moft.xfapply.widget.dialog.CustomAlertDialog;
import com.moft.xfapply.widget.dialog.ListDialog;
import com.moft.xfapply.widget.dialog.ListDialog.OnSelectedListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class SettingOfflineDataActivity extends BaseActivity implements OnClickListener {
    private RelativeLayout rl_updata_mode;
    private TextView tv_auto_update;

    private int curSelectedOfflineDataUpdateMode = 0;
    private List<Dictionary> offlineDataUpdateModeList = new ArrayList<Dictionary>();
    
    private RelativeLayout rl_sync_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_offline_data);
      
        if(savedInstanceState != null && savedInstanceState.getBoolean("isConflict", false))
            return;

        offlineDataUpdateModeList.clear();
        offlineDataUpdateModeList.add(new Dictionary("30分钟", String.valueOf(30 * 60 * 1000)));
        offlineDataUpdateModeList.add(new Dictionary("1小时", String.valueOf(1 * 60 * 60 * 1000)));
        offlineDataUpdateModeList.add(new Dictionary("3小时", String.valueOf(3 * 60 * 60 * 1000)));
        offlineDataUpdateModeList.add(new Dictionary("5小时", String.valueOf(5 * 60 * 60 * 1000)));
        offlineDataUpdateModeList.add(new Dictionary("不更新", "-1"));

        String default_update_mode = PrefSetting.getInstance().getUpdateOfflineDataMode();
        if (!StringUtil.isEmpty(default_update_mode)) {
            int index = 0;
            curSelectedOfflineDataUpdateMode = 0;
            for (Dictionary d : offlineDataUpdateModeList) {
                if (d.getCode().equals(default_update_mode)) {
                    curSelectedOfflineDataUpdateMode = index;
                    break;
                }
                index++;
            }
        } 

        rl_updata_mode = (RelativeLayout) this.findViewById(R.id.rl_updata_mode);
        rl_updata_mode.setOnClickListener(this);

        tv_auto_update = (TextView) this.findViewById(R.id.tv_auto_update);
        tv_auto_update.setText(offlineDataUpdateModeList.get(curSelectedOfflineDataUpdateMode).getValue());
        
        rl_sync_data = (RelativeLayout) this.findViewById(R.id.rl_sync_data);
        rl_sync_data.setOnClickListener(this);

    }
    
    @Override
    public void onSaveInstanceState(Bundle outState) {        
        super.onSaveInstanceState(outState);        
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.rl_updata_mode) {
            showUpdateMode();
        } else if (i == R.id.rl_sync_data) {
            updateIncData();
        }
    }
    
    private void showUpdateMode() {
        ListDialog.show(SettingOfflineDataActivity.this, offlineDataUpdateModeList, curSelectedOfflineDataUpdateMode, new OnSelectedListener() {
            @Override
            public void onSelected(int position) {
                if (curSelectedOfflineDataUpdateMode == position) {
                    return;
                }
                
                curSelectedOfflineDataUpdateMode = position;

                String code = offlineDataUpdateModeList.get(position).getCode();
                String name = offlineDataUpdateModeList.get(position).getValue();
                tv_auto_update.setText(name);

                PrefSetting.getInstance().setUpdateOfflineDataMode(code);
                String msg = String.format("设置成功，应用将每%s自动进行数据同步更新", name);
                if ("-1".equals(code)) {
                    msg = "设置成功，应用将不在自动进行数据同步更新";
                }
                Toast.makeText(SettingOfflineDataActivity.this, msg, Toast.LENGTH_SHORT).show();
                EventBus.getDefault().post(new CommandEvent(CommandEvent.CMD_UPDATE_OFFLINE_MODE_CHANGED));
            }
        });
    }

    public void updateIncData() {
        String content = "请确认是否同步离线数据？";

        CustomAlertDialog.show(SettingOfflineDataActivity.this, content, new CustomAlertDialog.OnDoingListener() {

            @Override
            public void onOK() {
                String cityName = LvApplication.getInstance().getCityName();
                OfflineDBInfo offlineDBInfo = OfflineDataBussiness.getInstance().getOfflineDBInfo(cityName);
                if(offlineDBInfo != null) {
                    String msg = "";
                    if ("downloading".equals(offlineDBInfo.getStatus()) || "pause".equals(offlineDBInfo.getStatus())) {
                        msg = "正在下载离线数据包，不能进行同步离线数据操作！";
                    } else if (StringUtil.isEmpty(offlineDBInfo.getStatus())) {
                        msg = "请更新离线数据包后，重新尝试";
                    } else {
                        msg = "应用将在后台自动进行数据同步";
                        OfflineDataBussiness.getInstance().updateGetIncOfflineData();
                    }
                    Toast.makeText(SettingOfflineDataActivity.this, msg, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SettingOfflineDataActivity.this, "无离线数据库，不能进行同步离线数据操作！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNG() {

            }
        });
    }
}
