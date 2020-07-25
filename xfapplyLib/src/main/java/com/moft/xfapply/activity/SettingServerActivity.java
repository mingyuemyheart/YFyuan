 
package com.moft.xfapply.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.moft.xfapply.R;
import com.moft.xfapply.base.BaseActivity;
import com.moft.xfapply.event.CommandEvent;
import com.moft.xfapply.logic.setting.PrefSetting;
import com.moft.xfapply.utils.StringUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
public class SettingServerActivity extends BaseActivity {
    
    private EditText et_ip;
    private EditText et_port;   
    private RelativeLayout rl_save;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_server);
        
        String ip = PrefSetting.getInstance().getServerIP();
        String port = PrefSetting.getInstance().getServerPort();

        et_ip = (EditText) this.findViewById(R.id.et_ip);
        et_port = (EditText) this.findViewById(R.id.et_port);
        
        et_port.setText(port);
        et_ip.setText(ip);
        
        et_ip.addTextChangedListener(new TextChange());
        et_port.addTextChangedListener(new TextChange());

        rl_save = (RelativeLayout) this.findViewById(R.id.rl_save);
        rl_save.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                PrefSetting.getInstance().setServerIP(et_ip.getText().toString().trim());
                PrefSetting.getInstance().setServerPort(et_port.getText().toString().trim());

                // ID：886054 授权成功后，未取消提示，提示授权成功。王旭 2019-02-25 START。
                EventBus.getDefault().post(new CommandEvent(CommandEvent.CMD_NETWORK_CHANGED)); // 发送Event至mainActivity 刷新授权提示
                // ID：886054 授权成功后，未取消提示，提示授权成功。王旭 2019-02-25 END

                back(view);
            }
        });
    }

    class TextChange implements TextWatcher {
        @Override
        public void afterTextChanged(Editable arg0) {
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                int arg3) {
        }

        @Override
        public void onTextChanged(CharSequence cs, int start, int before,
                int count) {
            boolean Sign1 = false;
            boolean Sign2 = false;        
            
            if (et_ip.getText() != null && et_port.getText() != null) {
                Sign1 = isIP(et_ip.getText().toString().trim()) || isDomain(et_ip.getText().toString().trim());
                Sign2 = isPort(et_port.getText().toString().trim());
            }
              
            if (Sign1 && Sign2) {
                rl_save.setVisibility(View.VISIBLE);
            }
            else {
                rl_save.setVisibility(View.GONE);
            }
        }
    }
    
    private boolean isPort(String port) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(port);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }
    
    private boolean isIP(String addr) {
        if (addr.length() < 7 || addr.length() > 15 || "".equals(addr)) {
            return false;
        }
        /**
         * 判断IP格式和范围
         */
        String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";

        Pattern pat = Pattern.compile(rexp);

        Matcher mat = pat.matcher(addr);

        boolean ipAddress = mat.find();

        return ipAddress;
    }

    private boolean isDomain(String addr) {
        if(StringUtil.isEmpty(addr)) {
            return false;
        }
        String rexp = "(?=^.{3,255}$)[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+";
        Pattern pat = Pattern.compile(rexp);
        Matcher mat = pat.matcher(addr);

        return mat.find();
    }
}
