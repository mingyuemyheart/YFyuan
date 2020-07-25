 
package com.moft.xfapply.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.moft.xfapply.R;
import com.moft.xfapply.base.BaseActivity;
import com.moft.xfapply.logic.setting.PrefSetting;
import com.moft.xfapply.model.common.Dictionary;
import com.moft.xfapply.utils.StringUtil;
import com.moft.xfapply.widget.dialog.ListDialog;
import com.moft.xfapply.widget.dialog.ListDialog.OnSelectedListener;

import java.util.ArrayList;
import java.util.List;
 
public class SettingParamActivity extends BaseActivity implements OnClickListener {
    private RelativeLayout rl_dis;
    private TextView tv_dis;

    private int curSelectedDis = 0;
    private List<Dictionary> disDicList = new ArrayList<Dictionary>();
    
    private RelativeLayout rl_pagenum;
    private TextView tv_pagenum;

    private int curSelectedPageNum = 0;
    private List<Dictionary> pageNumDicList = new ArrayList<Dictionary>();
    
    private RelativeLayout rl_login;
    private ImageView iv_login_on;
    private ImageView iv_login_off;;
    
    private RelativeLayout rl_offline;
    private ImageView iv_offline_on;
    private ImageView iv_offline_off;
    
    private RelativeLayout rl_download;
    private ImageView iv_download_on;
    private ImageView iv_download_off;

    private RelativeLayout rl_disaster_model;
    private ImageView iv_disaster_model_on;
    private ImageView iv_disaster_model_off;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_param);
      
        if(savedInstanceState != null && savedInstanceState.getBoolean("isConflict", false))
            return;

        disDicList.clear();
        disDicList.add(new Dictionary("约200米", "0.2"));
        disDicList.add(new Dictionary("约500米", "0.5"));
        disDicList.add(new Dictionary("约1千米", "1"));
        disDicList.add(new Dictionary("约3千米", "3"));
        disDicList.add(new Dictionary("约5千米", "5"));
        disDicList.add(new Dictionary("约10千米", "10"));
        
        String default_dis = PrefSetting.getInstance().getLoadDis();
        if (!StringUtil.isEmpty(default_dis)) {
            int index = 0;
            curSelectedDis = 0;
            for (Dictionary d : disDicList) {
                if (d.getCode().equals(default_dis)) {
                    curSelectedDis = index;
                    break;
                }
                index++;
            }
        } 

        pageNumDicList.clear();
//        pageNumDicList.add(new Dictionary("不分页", "-1"));
        pageNumDicList.add(new Dictionary("20条", "20"));
        pageNumDicList.add(new Dictionary("50条", "50"));
        pageNumDicList.add(new Dictionary("100条", "100"));
        
        String default_pageNum = "" + PrefSetting.getInstance().getPageNum();
        if (!StringUtil.isEmpty(default_pageNum)) {
            int index = 0;
            curSelectedPageNum = 0;
            for (Dictionary d : pageNumDicList) {
                if (d.getCode().equals(default_pageNum)) {
                    curSelectedPageNum = index;
                    break;
                }
                index++;
            }
        } 
        
        rl_dis = (RelativeLayout) this.findViewById(R.id.rl_dis);
        rl_dis.setOnClickListener(this);

        tv_dis = (TextView) this.findViewById(R.id.tv_dis);
        tv_dis.setText(disDicList.get(curSelectedDis).getValue());
        
        rl_pagenum = (RelativeLayout) this.findViewById(R.id.rl_pagenum);
        rl_pagenum.setOnClickListener(this);

        tv_pagenum = (TextView) this.findViewById(R.id.tv_pagenum);
        tv_pagenum.setText(pageNumDicList.get(curSelectedPageNum).getValue());
        
        rl_login = (RelativeLayout) this.findViewById(R.id.rl_login);
        rl_login.setOnClickListener(this);
        
        iv_login_on = (ImageView) this.findViewById(R.id.iv_login_on);
        iv_login_off = (ImageView) this.findViewById(R.id.iv_login_off);
      
        if (PrefSetting.getInstance().getAutoLogin()) {
            iv_login_on.setVisibility(View.VISIBLE);
            iv_login_off.setVisibility(View.INVISIBLE);
        } else {
            iv_login_on.setVisibility(View.INVISIBLE);
            iv_login_off.setVisibility(View.VISIBLE);
        }
        
        rl_offline = (RelativeLayout) this.findViewById(R.id.rl_offline);
        rl_offline.setOnClickListener(this);
        
        iv_offline_on = (ImageView) this.findViewById(R.id.iv_offline_on);
        iv_offline_off = (ImageView) this.findViewById(R.id.iv_offline_off);
      
        if (PrefSetting.getInstance().getOfflineLogin()) {
            iv_offline_on.setVisibility(View.VISIBLE);
            iv_offline_off.setVisibility(View.INVISIBLE);
        } else {
            iv_offline_on.setVisibility(View.INVISIBLE);
            iv_offline_off.setVisibility(View.VISIBLE);
        }
        
        rl_download = (RelativeLayout) this.findViewById(R.id.rl_download);
        rl_download.setOnClickListener(this);
        
        iv_download_on = (ImageView) this.findViewById(R.id.iv_download_on);
        iv_download_off = (ImageView) this.findViewById(R.id.iv_download_off);
      
        if (PrefSetting.getInstance().getOfflineDownload()) {
            iv_download_on.setVisibility(View.VISIBLE);
            iv_download_off.setVisibility(View.INVISIBLE);
        } else {
            iv_download_on.setVisibility(View.INVISIBLE);
            iv_download_off.setVisibility(View.VISIBLE);
        }

        rl_disaster_model = (RelativeLayout) this.findViewById(R.id.rl_disaster_model);
        rl_disaster_model.setOnClickListener(this);

        iv_disaster_model_on = (ImageView) this.findViewById(R.id.iv_disaster_model_on);
        iv_disaster_model_off = (ImageView) this.findViewById(R.id.iv_disaster_model_off);

        if (PrefSetting.getInstance().getDisasterModel()) {
            iv_disaster_model_on.setVisibility(View.VISIBLE);
            iv_disaster_model_off.setVisibility(View.INVISIBLE);
        } else {
            iv_disaster_model_on.setVisibility(View.INVISIBLE);
            iv_disaster_model_off.setVisibility(View.VISIBLE);
        }
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState) {        
        super.onSaveInstanceState(outState);        
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.rl_dis) {
            showDis();
        } else if (i == R.id.rl_pagenum) {
            showPageNum();
        } else if (i == R.id.rl_login) {
            doLoginSetting();
        } else if (i == R.id.rl_offline) {
            doLoginOffline();
        } else if (i == R.id.rl_download) {
            doOfflineDownload();
        } else if (i == R.id.rl_disaster_model) {
            doDisasterModel();
        }
    }
    
    private void doLoginSetting() {
        if (iv_login_on.getVisibility() == View.VISIBLE) {
            iv_login_on.setVisibility(View.INVISIBLE);
            iv_login_off.setVisibility(View.VISIBLE);
           
            PrefSetting.getInstance().setAutoLogin(false);
        } else {
            iv_login_on.setVisibility(View.VISIBLE);
            iv_login_off.setVisibility(View.INVISIBLE);
           
            PrefSetting.getInstance().setAutoLogin(true);
        }
    }

    private void doLoginOffline() {
        if (iv_offline_on.getVisibility() == View.VISIBLE) {
            iv_offline_on.setVisibility(View.INVISIBLE);
            iv_offline_off.setVisibility(View.VISIBLE);
           
            PrefSetting.getInstance().setOfflineLogin(false);
        } else {
            iv_offline_on.setVisibility(View.VISIBLE);
            iv_offline_off.setVisibility(View.INVISIBLE);
           
            PrefSetting.getInstance().setOfflineLogin(true);
        }
    }

    private void doOfflineDownload() {
        if (iv_download_on.getVisibility() == View.VISIBLE) {
            iv_download_on.setVisibility(View.INVISIBLE);
            iv_download_off.setVisibility(View.VISIBLE);
           
            PrefSetting.getInstance().setOfflineDownload(false);
        } else {
            iv_download_on.setVisibility(View.VISIBLE);
            iv_download_off.setVisibility(View.INVISIBLE);
           
            PrefSetting.getInstance().setOfflineDownload(true);
        }
    }

    private void doDisasterModel() {
        if (iv_disaster_model_on.getVisibility() == View.VISIBLE) {
            iv_disaster_model_on.setVisibility(View.INVISIBLE);
            iv_disaster_model_off.setVisibility(View.VISIBLE);

            PrefSetting.getInstance().setDisasterModel(false);
        } else {
            iv_disaster_model_on.setVisibility(View.VISIBLE);
            iv_disaster_model_off.setVisibility(View.INVISIBLE);

            PrefSetting.getInstance().setDisasterModel(true);
            Toast.makeText(this, "已开启灾情模式，灾情模式目前处于试用版！在地图操作栏点击灾情模式按钮后进入。",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void showDis() {
        ListDialog.show(SettingParamActivity.this, disDicList, curSelectedDis, new OnSelectedListener() {
            @Override
            public void onSelected(int position) {
                if (curSelectedDis == position) {
                    return;
                }
                
                curSelectedDis = position;

                String code = disDicList.get(position).getCode();
                String name = disDicList.get(position).getValue();
                tv_dis.setText(name);

                PrefSetting.getInstance().setLoadDis(code);
            }
        });
    }
    
    private void showPageNum() {
        ListDialog.show(SettingParamActivity.this, pageNumDicList, curSelectedPageNum, new OnSelectedListener() {
            @Override
            public void onSelected(int position) {
                if (curSelectedPageNum == position) {
                    return;
                }
                
                curSelectedPageNum = position;

                String code = pageNumDicList.get(position).getCode();
                String name = pageNumDicList.get(position).getValue();
                tv_pagenum.setText(name);
                
                PrefSetting.getInstance().setPageNum(Integer.valueOf(code));
            }
        });
    }
}
