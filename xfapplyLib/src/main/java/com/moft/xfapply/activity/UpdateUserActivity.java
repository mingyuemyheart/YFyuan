package com.moft.xfapply.activity;

import com.moft.xfapply.R;
import com.moft.xfapply.base.BaseActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class UpdateUserActivity extends BaseActivity{
    private String ori = "";
    private String title = "";
    private String hint = "";
    
    private EditText et_name;
    private TextView tv_title;
    private TextView tv_hint;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);        

        Intent intent = getIntent();
        if (intent.hasExtra("ori")) {

            ori = (String) intent.getSerializableExtra("ori");
        }
        if (intent.hasExtra("title")) {

            title = (String) intent.getSerializableExtra("title");
        }
        if (intent.hasExtra("hint")) {

            hint = (String) intent.getSerializableExtra("hint");
        }
        
        et_name= (EditText) this.findViewById(R.id.et_name);
        et_name.setText(ori);
        
        tv_title= (TextView) this.findViewById(R.id.tv_title);
        tv_title.setText(title);
        
        tv_hint= (TextView) this.findViewById(R.id.tv_hint);
        tv_hint.setText(hint);

        RelativeLayout rl_save = (RelativeLayout) this.findViewById(R.id.rl_save);
        rl_save.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String newC = et_name.getText().toString().trim();
                if(ori.equals(newC)||newC.equals("")) {
                    return;
                }  
                
                Intent intent = new Intent();
                intent.putExtra("new", newC);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
