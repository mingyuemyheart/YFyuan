package com.moft.xfapply.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.moft.xfapply.R;
import com.moft.xfapply.activity.bussiness.GeomEleBussiness;
import com.moft.xfapply.app.Constant;
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.base.BaseActivity;
import com.moft.xfapply.logic.network.OkHttpClientManager;
import com.moft.xfapply.logic.setting.PrefSetting;
import com.moft.xfapply.logic.setting.PrefUserInfo;
import com.moft.xfapply.model.entity.GeomElementDBInfo;
import com.moft.xfapply.model.external.AppDefs;
import com.moft.xfapply.model.external.result.SimpleRestResult;
import com.moft.xfapply.utils.StringUtil;
import com.moft.xfapply.utils.ZXingUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

public class QRCodeActivity extends BaseActivity {
    private TextView tv_valid1 = null;
    private TextView tv_valid2 = null;
    private TextView tv_valid3 = null;
    private TextView tv_valid4 = null;

    private TextView tv_name = null;
    private TextView tv_result = null;

    private View re_failure = null;
    private View re_loading_process = null;
    private ImageView iv_qrcode = null;
    private TextView tv_qrcode = null;
    private TextView tv_copy = null;

    private int validType = 0;
    private int validCurTextColor = Color.rgb(0, 116, 199);
    private int validTextColor = Color.rgb(133, 133, 133);

    private GeomElementDBInfo geoElementInfo = null;
    private Bitmap qrcodeBitmap = null;
    private String url = "";
    private String endTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        boolean initCheck = initData();
        if (!initCheck) {
            Toast.makeText(this, "无效的数据", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        initView();
    }

    private void initView() {
        tv_name = (TextView) this.findViewById(R.id.tv_name);
        tv_result = (TextView) this.findViewById(R.id.tv_result);

        tv_valid1 = (TextView) this.findViewById(R.id.tv_valid1);
        tv_valid2 = (TextView) this.findViewById(R.id.tv_valid2);
        tv_valid3 = (TextView) this.findViewById(R.id.tv_valid3);
        tv_valid4 = (TextView) this.findViewById(R.id.tv_valid4);
        tv_valid1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validType == 0) {
                    return;
                }
                validType = 0;
                refreshValid();
            }
        });
        tv_valid2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validType == 1) {
                    return;
                }
                validType = 1;
                refreshValid();
            }
        });
        tv_valid3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validType == 2) {
                    return;
                }
                validType = 2;
                refreshValid();
            }
        });
        tv_valid4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validType == 3) {
                    return;
                }
                validType = 3;
                refreshValid();
            }
        });

        tv_name.setText(geoElementInfo.getName());
        tv_result.setText(geoElementInfo.getAddress());

        re_failure = this.findViewById(R.id.re_failure);
        re_failure.setVisibility(View.GONE);
        re_failure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData();
            }
        });
        re_loading_process = this.findViewById(R.id.re_loading_process);
        iv_qrcode = (ImageView) this.findViewById(R.id.iv_qrcode);
        iv_qrcode.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (qrcodeBitmap == null) {
                    Toast.makeText(QRCodeActivity.this, "图片不存在", Toast.LENGTH_SHORT).show();
                    return false;
                }
                String picName = geoElementInfo.getUuid() + "_" + validType;
                saveBmp2Gallery(qrcodeBitmap, picName);
                return false;
            }
        });
        tv_qrcode = (TextView) this.findViewById(R.id.tv_qrcode);
        tv_copy = (TextView) this.findViewById(R.id.tv_copy);
        tv_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (re_failure.getVisibility() == View.VISIBLE) {
                    return;
                }

                String content = tv_qrcode.getText().toString();
                if (StringUtil.isEmpty(content)) {
                    return;
                }
                ClipboardManager myClipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
                ClipData myClip = ClipData.newPlainText("text", content);
                myClipboard.setPrimaryClip(myClip);

                Toast.makeText(QRCodeActivity.this, "已复制分享内容", Toast.LENGTH_SHORT).show();
            }
        });
        refreshValid();
    }

    private boolean initData() {
        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        String key = intent.getStringExtra("key");
        String cityCode = intent.getStringExtra("city");

        if (!AppDefs.CompEleType.KEY_UNIT.toString().equals(type)) {
            return false;
        }

        //支持省离线数据库查询
        geoElementInfo = GeomEleBussiness.getInstance().getGeomEleInfo(LvApplication.getInstance().getCityName(), key);
        return true;
    }

    private void refreshValid() {
        if (validType == 0) {
            tv_valid1.setTextColor(validCurTextColor);
            tv_valid2.setTextColor(validTextColor);
            tv_valid3.setTextColor(validTextColor);
            tv_valid4.setTextColor(validTextColor);
        } else if (validType == 1) {
            tv_valid1.setTextColor(validTextColor);
            tv_valid2.setTextColor(validCurTextColor);
            tv_valid3.setTextColor(validTextColor);
            tv_valid4.setTextColor(validTextColor);
        } else if (validType == 2) {
            tv_valid1.setTextColor(validTextColor);
            tv_valid2.setTextColor(validTextColor);
            tv_valid3.setTextColor(validCurTextColor);
            tv_valid4.setTextColor(validTextColor);
        } else if (validType == 3) {
            tv_valid1.setTextColor(validTextColor);
            tv_valid2.setTextColor(validTextColor);
            tv_valid3.setTextColor(validTextColor);
            tv_valid4.setTextColor(validCurTextColor);
        }
        loadData();
    }

    private void loadData() {
        re_loading_process.setVisibility(View.VISIBLE);
        re_failure.setVisibility(View.GONE);
        iv_qrcode.setVisibility(View.GONE);
        tv_qrcode.setText("正在生成分享内容...");

        OkHttpClientManager http = OkHttpClientManager.getInstance();

        Map<String, String> params = new HashMap<>();
        params.put("accountUuid", PrefUserInfo.getInstance().getUserInfo("account_uuid"));
        params.put("accountName", PrefUserInfo.getInstance().getUserInfo("username"));
        params.put("departmentUuid", PrefUserInfo.getInstance().getUserInfo("department_uuid"));
        params.put("departmentName", PrefUserInfo.getInstance().getUserInfo("department"));
        params.put("validType", "" + validType);
        params.put("objectType", geoElementInfo.getEleType());
        params.put("objectUuid", geoElementInfo.getUuid());

        http.postAsyn(http.convertToURL(PrefSetting.getInstance().getServerPort(), Constant.METHOD_CREATE_QRCODE),
                new OkHttpClientManager.ResultCallback<SimpleRestResult<String>>() {
            @Override
            public void onError(Request request, Exception e) {
                doFailure();
            }

            @Override
            public void onResponse(SimpleRestResult<String> result) {
                if (result != null && result.isSuccess() && !StringUtil.isEmpty(result.getData())) {
                    doSuccess(result.getData());
                } else {
                    doFailure();
                }
            }
        }, params);
    }

    private void doSuccess(String url) {
        this.url = url;

        re_loading_process.setVisibility(View.GONE);
        re_failure.setVisibility(View.GONE);
        iv_qrcode.setVisibility(View.VISIBLE);

        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        iv_qrcode.measure(w, h);

        qrcodeBitmap = ZXingUtil.createQRImage(url,
                iv_qrcode.getMeasuredWidth(),
                iv_qrcode.getMeasuredHeight());
        iv_qrcode.setImageBitmap(qrcodeBitmap);

        tv_qrcode.setText(getSharedContent());
    }

    private void doFailure() {
        re_failure.setVisibility(View.VISIBLE);
        re_loading_process.setVisibility(View.GONE);
        iv_qrcode.setVisibility(View.GONE);
        tv_qrcode.setText("分享内容生成失败。");
    }

    private String getSharedContent() {
        String userName = PrefUserInfo.getInstance().getUserInfo("real_name");
        if (StringUtil.isEmpty(userName)) {
            userName = PrefUserInfo.getInstance().getUserInfo("username");
        }

        String content = "";

        content += "【" + userName + "】";
        content += "为您分享了";
        content += " " + geoElementInfo.getName() + " ";
        content += "的信息， " + url + " ，点击链接查看。";

        String valid = "";
        if (validType == 0) {
            valid = "今日有效";
        } else if (validType == 1) {
            valid = "本周有效";
        } else if (validType == 2) {
            valid = "本月有效";
        } else if (validType == 3) {
            valid = "仅一次有效";
        }
        content += "该链接有效期为【" + valid + "】";

        return content;
    }

    public void saveBmp2Gallery(Bitmap bmp, String picName) {
        String fileName = null;
        //系统相册目录
        String galleryPath= Environment.getExternalStorageDirectory()
                + File.separator + Environment.DIRECTORY_DCIM
                + File.separator + "Camera" + File.separator;

        // 声明文件对象
        File file = null;
        // 声明输出流
        FileOutputStream outStream = null;

        try {
            // 如果有目标文件，直接获得文件对象，否则创建一个以filename为名称的文件
            file = new File(galleryPath, picName + ".png");

            // 获得文件相对路径
            fileName = file.toString();
            // 获得输出流，如果文件中有内容，追加内容
            outStream = new FileOutputStream(fileName);
            if (null != outStream) {
                bmp.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            }
        } catch (Exception e) {
            e.getStackTrace();
        }finally {
            try {
                if (outStream != null) {
                    outStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //通知相册更新
        MediaStore.Images.Media.insertImage(getContentResolver(),
                bmp, fileName, null);
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        sendBroadcast(intent);

        Toast.makeText(QRCodeActivity.this, "图片已保存至本地！", Toast.LENGTH_SHORT).show();
    }
}
