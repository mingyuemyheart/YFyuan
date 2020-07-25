 
package com.moft.xfapply.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.moft.xfapply.R;
import com.moft.xfapply.activity.bussiness.UserBussiness;
import com.moft.xfapply.activity.bussiness.UserBussiness.OnLoginListener;
import com.moft.xfapply.base.BaseActivity;
import com.moft.xfapply.logic.setting.PrefSetting;
import com.moft.xfapply.logic.setting.PrefUserInfo;
import com.moft.xfapply.utils.StringUtil;

public class LoginActivity extends BaseActivity implements OnLoginListener {
    private Button btn_login;
    
    private RelativeLayout re_username;
    private ImageView iv_avatar;    
    private EditText et_password;
    private EditText et_username;
    private TextView tv_name;
    private TextView tv_changeuser;

    private String avatar;
    private String recentUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                doLogin();
            }
        });
                     
        et_password = (EditText) findViewById(R.id.et_password);
        et_password.addTextChangedListener(new TextChange());
        
        et_username = (EditText) findViewById(R.id.et_username);
        et_username.addTextChangedListener(new TextChange());

        iv_avatar = (ImageView) findViewById(R.id.iv_avatar);

        re_username = (RelativeLayout) findViewById(R.id.re_username);
        tv_name = (TextView) this.findViewById(R.id.tv_name);

        tv_changeuser = (TextView) this.findViewById(R.id.tv_changeuser);        
        tv_changeuser.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                initUser();
            }
        });

        initUser();
        if (PrefSetting.getInstance().getChangeUser()) {
            PrefSetting.getInstance().setChangeUser(false);
        } else {
            recentUserName = PrefUserInfo.getInstance().getUserInfo("username");
            if (!StringUtil.isEmpty(recentUserName)) {
                re_username.setVisibility(View.GONE);
                tv_name.setText(recentUserName);
                tv_name.setVisibility(View.VISIBLE);
                tv_changeuser.setVisibility(View.VISIBLE);
                avatar = PrefUserInfo.getInstance().getUserInfo("avatar");
                showUserAvatar(iv_avatar, avatar);

                if (PrefSetting.getInstance().getAutoLogin()) {
                    autoLogin();
                }
            }
        }
    }

    private void initUser() {
        re_username.setVisibility(View.VISIBLE);
        tv_name.setVisibility(View.GONE);
        iv_avatar.setImageResource(R.drawable.default_useravatar);
        et_username.requestFocus();
        tv_changeuser.setVisibility(View.GONE);
    }

    private void autoLogin() {
        PrefUserInfo pui = PrefUserInfo.getInstance();

        String userCode = pui.getRecentUserCode();
        if (!StringUtil.isEmpty(userCode)) {
            String userName = pui.getUserInfo("username", userCode);
            String password = pui.getUserInfo("password", userCode);

            et_username.setText(userName);
            et_password.setText(password);
            et_password.setEnabled(false);
            btn_login.setTextColor(0xFFEEEEEE);
            btn_login.setEnabled(false);
            tv_changeuser.setEnabled(false);

            UserBussiness userBussiness = UserBussiness.getInstance();

            userBussiness.setListener(this);
            userBussiness.setAllowOffline(PrefSetting.getInstance().getOfflineLogin());
            userBussiness.login(userName, password, this);
        } else {
            btn_login.setTextColor(0xFFFFFFFF);
            btn_login.setEnabled(true);
            et_password.setEnabled(true);
            initUser();
        }
    }

    private void showUserAvatar(ImageView imageView, String avatar) {
        if (StringUtil.isEmpty(avatar)) {
            return;
        }
        Glide.with(this)
                .load(avatar)
                .into(imageView);
    }

    // EditText监听器
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
            boolean Sign1 = et_password.getText().length() > 0;
            boolean Sign2 = true;

            if (re_username.getVisibility() == View.VISIBLE) {
                Sign2 = et_username.getText().length() > 0;
            }
            
            if (Sign1 && Sign2) {
                btn_login.setTextColor(0xFFFFFFFF);
                btn_login.setEnabled(true);
            }
            else {
                btn_login.setTextColor(0xFFEEEEEE);
                btn_login.setEnabled(false);
            }
        }
    }
    
    private void doLogin() {
        String userName = "";
        if (re_username.getVisibility() == View.GONE) {
            userName = recentUserName;
        } else {
            userName = et_username.getText().toString().trim();

        }
        String password = et_password.getText().toString().trim();

        UserBussiness userBussiness = UserBussiness.getInstance();
        userBussiness.setListener(this);
        userBussiness.setAllowOffline(PrefSetting.getInstance().getOfflineLogin());
        userBussiness.login(userName, password, this);
    }

    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                return false;
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onSuccess() {
        if(StringUtil.isEmpty(PrefUserInfo.getInstance().getUserInfo("district_code"))) {
            Toast.makeText(this, "账户数据错误，无法登录！请联系管理员。", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "用户 " + PrefUserInfo.getInstance().getUserInfo("username") + " 登录成功！",
                Toast.LENGTH_SHORT).show();

        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onSuccessOffline() {
        onSuccess();
    }

    @Override
    public void onFailure(String msg) {
        btn_login.setTextColor(0xFFFFFFFF);
        btn_login.setEnabled(true);
        tv_changeuser.setEnabled(true);
        et_password.setEnabled(true);
        et_password.setSelection(et_password.getText().length()); // 将光标设置为文本末尾
    }
}
