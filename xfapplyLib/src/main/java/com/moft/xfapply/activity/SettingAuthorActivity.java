 
package com.moft.xfapply.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.util.Date;

import com.moft.xfapply.R;
import com.moft.xfapply.activity.bussiness.DeviceAuthBussiness;
import com.moft.xfapply.base.BaseActivity;
import com.moft.xfapply.logic.setting.PrefSetting;
import com.moft.xfapply.utils.DateUtil;
import com.moft.xfapply.utils.TelManagerUtil;

public class SettingAuthorActivity extends BaseActivity implements OnClickListener {
	private RelativeLayout rl_rjsqzt;
    private TextView tv_rjsqzt;
    
	private RelativeLayout rl_dqsj;
    private TextView tv_dqsj;
    
    private TextView tv_device;
    private TextView tv_sdcard;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_author);
      
        if(savedInstanceState != null && savedInstanceState.getBoolean("isConflict", false)) {
            return;
        }
        initView();
        refreshView();
    }

    @Override
    protected void onResume() {
        refreshView();
        super.onResume();
    }

    private void initView() {
        rl_rjsqzt = (RelativeLayout) this.findViewById(R.id.rl_rjsqzt);
        rl_rjsqzt.setOnClickListener(this);
        tv_rjsqzt = (TextView) this.findViewById(R.id.tv_rjsqzt);
        rl_dqsj = (RelativeLayout) this.findViewById(R.id.rl_dqsj);
        rl_dqsj.setOnClickListener(this);
        tv_dqsj = (TextView) this.findViewById(R.id.tv_dqsj);
        tv_device = (TextView) this.findViewById(R.id.tv_device);
        tv_sdcard = (TextView) this.findViewById(R.id.tv_sdcard);
    }

    private void refreshView() {
        tv_rjsqzt.setText(PrefSetting.getInstance().getAuthenticate());
        if(PrefSetting.getInstance().getExpires() != 0) {
            tv_dqsj.setText(DateUtil.format(new Date(PrefSetting.getInstance().getExpires()), "yyyy年MM月dd日"));
        } else {
            tv_dqsj.setText("");
        }
        tv_device.setText(TelManagerUtil.getInstance().getDeviceId());
        tv_sdcard.setText(getSdcardCode());
    }

    private String getSdcardCode() {
        String code = "";
        
        Object localOb;
        String str1 = null;

        try {
          localOb = new FileReader("/sys/block/mmcblk0/device/type");
          localOb = new BufferedReader((Reader) localOb).readLine()
              .toLowerCase().contentEquals("sd");
          if (localOb != null) {
            str1 = "/sys/block/mmcblk0/device/";
          }
        } catch (Exception e1) {
          System.out.println(e1.getMessage());
        }
        
        try {
          localOb = new FileReader("/sys/block/mmcblk1/device/type");
          localOb = new BufferedReader((Reader) localOb).readLine()
              .toLowerCase().contentEquals("sd");
          if (localOb != null) {
            str1 = "/sys/block/mmcblk1/device/";
          }
        } catch (Exception e1) {
          System.out.println(e1.getMessage());
        }
        
        try {

          localOb = new FileReader("/sys/block/mmcblk2/device/type");
          localOb = new BufferedReader((Reader) localOb).readLine()
              .toLowerCase().contentEquals("sd");
          if (localOb != null) {
            str1 = "/sys/block/mmcblk2/device/";
          }
        } catch (Exception e1) {
          System.out.println(e1.getMessage());
        }
        
        localOb = "";
        
        try {
            localOb = new FileReader(str1 + "serial"); // 串号/序列号
            code = new BufferedReader((Reader) localOb).readLine();
          } catch (Exception e1) {
            System.out.println(e1.getMessage());
          }
        
        return code;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.rl_rjsqzt) {
        } else if (i == R.id.rl_dqsj) {
        } else if (i == R.id.rl_refresh) {// ID：886054 授权成功后，未取消提示，提示授权成功。王旭 2019-02-25 START。
            DeviceAuthBussiness.getInstance().deviceAuthenticate(new DeviceAuthBussiness.OnDeviceAuthListener() {
                @Override
                public void onAuthCompleted() {
                    refreshView();
                }

                @Override
                public void onError(String err) {
                    Toast.makeText(SettingAuthorActivity.this, err, Toast.LENGTH_SHORT).show();
                }
            });
            // ID：886054 授权成功后，未取消提示，提示授权成功。王旭 2019-02-25 END
        }

    }
    
    @Override
    public void onSaveInstanceState(Bundle outState) {        
        super.onSaveInstanceState(outState);        
    }
}
