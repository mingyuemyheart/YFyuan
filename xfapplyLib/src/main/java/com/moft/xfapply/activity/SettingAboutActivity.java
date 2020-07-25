 
package com.moft.xfapply.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.moft.xfapply.R;
import com.moft.xfapply.base.BaseActivity;
 
public class SettingAboutActivity extends BaseActivity implements OnClickListener {
    
    private TextView tv_version;
    private TextView tv_mul_text;  // ID:858552 【采集终端】我->关于我们，展示当前版本更新内容。 2019-03-06 王旭

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_about);
      
        if(savedInstanceState != null && savedInstanceState.getBoolean("isConflict", false))
            return;
        
        tv_version = (TextView) this.findViewById(R.id.tv_version);
        tv_mul_text = (TextView) this.findViewById(R.id.tv_mul_text); // ID:858552 【采集终端】我->关于我们，展示当前版本更新内容。 2019-03-06 王旭

        tv_version.setText(getAppName() + " " + getVersion());

        // ID:858552 【采集终端】我->关于我们，展示当前版本更新内容。 2019-03-06 王旭 START
        tv_mul_text.setText(Html.fromHtml(getUpdateContent()));
        tv_mul_text.setMovementMethod(ScrollingMovementMethod.getInstance());
        // ID:858552 【采集终端】我->关于我们，展示当前版本更新内容。 2019-03-06 王旭 END
    }

    /*
    * @Author 王旭
    * @Data 2019-03-06
    * @Description 【采集终端】我->关于我们，展示当前版本更新内容。
    * */
    private String getUpdateContent() {
        StringBuilder updateContext = new StringBuilder()
                .append("<p>")
                .append("<strong>主要更新内容:</strong>").append("<br>")
                .append("<font color = \"gray\">")
                .append("1.新增【监管】模块取代之前的【统计】模块").append("<br>")
                .append("2.增加地图撒点功能").append("<br>")
                .append("3.增加纠错上报功能").append("<br>")
                .append("4.增加综合查询功能").append("<br>")
                .append("4.优化关键图示展现方式").append("<br>")
                .append("</font>")
                .append("<br>")
                .append("<strong>主要修复问题:</strong><br>")
                .append("<font color = \"gray\">")
                .append("1.优化图片加载慢的问题").append("<br>")
                .append("2.修复了其他已知问题").append("<br>")
                .append("</font>")
                .append("</p>");
        return updateContext.toString();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        default:
            break;
        }
    }

	/**
	 * 获取当前应用程序的版本号
	 */
	private String getVersion() {
		PackageManager pm = getPackageManager();
		try {
			PackageInfo packinfo = pm.getPackageInfo(getPackageName(), 0);
            return packinfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return "";
		}
	}

    /**
     * 获取当前应用程序的版本号
     */
    private String getAppName() {
        String name = "";
        PackageManager pm = getPackageManager();
        try {
            name = pm.getApplicationLabel(
                   pm.getApplicationInfo(getPackageName(),
                   PackageManager.GET_META_DATA)).toString();

        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return name;
    }
}
