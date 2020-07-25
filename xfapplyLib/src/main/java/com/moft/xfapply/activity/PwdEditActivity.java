package com.moft.xfapply.activity;

import com.bumptech.glide.Glide;
import com.moft.xfapply.R;
import com.moft.xfapply.activity.bussiness.UserBussiness;
import com.moft.xfapply.base.BaseActivity;
import com.moft.xfapply.logic.setting.PrefUserInfo;
import com.moft.xfapply.utils.StringUtil;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PwdEditActivity extends BaseActivity {
    private ImageView iv_avatar;
    private TextView tv_name;
    private TextView tv_company;
    private ImageView iv_sex;

    private RelativeLayout rl_save;
    
    private TextView tv_username;
    private EditText et_pwd_old;
    private EditText et_pwd1;
    private EditText et_pwd2;
    
    private String avatar;
    private String username;
    private String company;
    private String sex;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd_edit);

        rl_save = (RelativeLayout) this.findViewById(R.id.rl_save);
        rl_save.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                updatePwd();
            }
        });
        initView();
    }

    private void initView() {
        username = PrefUserInfo.getInstance().getUserInfo("username");
        avatar = PrefUserInfo.getInstance().getUserInfo("avatar");
        company = PrefUserInfo.getInstance().getUserInfo("department");
        sex = PrefUserInfo.getInstance().getUserInfo("sex");
        
        iv_avatar = (ImageView) this.findViewById(R.id.iv_avatar);
        tv_name = (TextView) this.findViewById(R.id.tv_name);
        tv_company = (TextView) this.findViewById(R.id.tv_company);
        iv_sex = (ImageView) this.findViewById(R.id.iv_sex);
        
        tv_name.setText(username);
        tv_company.setText(company);        

        if (StringUtil.isEmpty(sex) || "男".equals(sex)) {
            iv_sex.setImageResource(R.drawable.ic_sex_male);
        } else {
            iv_sex.setImageResource(R.drawable.ic_sex_female);
        }

        showUserAvatar(iv_avatar, avatar);
        
        tv_username = (TextView) this.findViewById(R.id.tv_username);
        tv_username.setText(username);
        
        et_pwd1 = (EditText) this.findViewById(R.id.et_pwd1);
        et_pwd2 = (EditText) this.findViewById(R.id.et_pwd2);
        et_pwd_old = (EditText) this.findViewById(R.id.et_pwd_old);
        
        et_pwd1.addTextChangedListener(new TextChange());
        et_pwd2.addTextChangedListener(new TextChange());
        et_pwd_old.addTextChangedListener(new TextChange());

        UserBussiness.getInstance().setListener(new UserBussiness.OnLoginListener() {
            @Override
            public void onSuccess() {
                finish();
            }

            @Override
            public void onSuccessOffline() {

            }

            @Override
            public void onFailure(String msg) {

            }
        });
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
            boolean singOld = et_pwd_old.getText().length() > 0;
            boolean Sign3 = et_pwd1.getText().length() > 0;
            boolean Sign4 = et_pwd2.getText().length() > 0;
            
            if (Sign3 && Sign4 && singOld) {
                rl_save.setVisibility(View.VISIBLE);
            }
            else {
                rl_save.setVisibility(View.GONE);
            }
        }
    }
    
    private  void updatePwd(){
        String oldPwd = et_pwd_old.getText().toString().trim();
        String pwd1 = et_pwd1.getText().toString().trim();
        String pwd2 = et_pwd2.getText().toString().trim();
        
        if (!pwd1.equals(pwd2)) {
            Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_LONG).show();
            return;
        }
        
        if (StringUtil.isPasswordInvalid(pwd1)) {
//            Toast.makeText(this, "密码格式不正确，不能为空并必须大于8位", Toast.LENGTH_LONG).show();
            Toast.makeText(this, "密码格式不正确，不能为空", Toast.LENGTH_LONG).show();
            return;
        }
        
        updateIvnServer(oldPwd, pwd1);
    }

    private  void updateIvnServer(final String oldPassword, final String password){
        UserBussiness.getInstance().updatePassword(oldPassword, password, this);
    }
}
