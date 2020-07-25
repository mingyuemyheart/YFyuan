package com.moft.xfapply.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;
import com.moft.xfapply.R;
import com.moft.xfapply.activity.bussiness.UserBussiness;
import com.moft.xfapply.base.BaseActivity;
import com.moft.xfapply.logic.setting.PrefUserInfo;
import com.moft.xfapply.model.common.Dictionary;
import com.moft.xfapply.utils.DateUtil;
import com.moft.xfapply.utils.StorageUtil;
import com.moft.xfapply.utils.StringUtil;
import com.moft.xfapply.widget.dialog.ListDialog;
import com.moft.xfapply.widget.dialog.ListDialog.OnSelectedListener;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

@SuppressLint("SdCardPath")
public class MyUserInfoActivity extends BaseActivity {

    private RelativeLayout re_avatar;
    private RelativeLayout re_name;
    private RelativeLayout re_sex;
    private RelativeLayout re_address; //age
    private RelativeLayout re_phone;
    private RelativeLayout re_remark; //email
//    private RelativeLayout re_sign;

    private ImageView iv_avatar;
    private TextView tv_name;
    private TextView tv_company;
    private TextView tv_company_code;
    private TextView tv_sex;
    private TextView tv_address;
    private TextView tv_phone;
    private TextView tv_remark;
//    private TextView tv_sign;

    private static final int PHOTO_REQUEST_TAKEPHOTO = 1;
    private static final int PHOTO_REQUEST_GALLERY = 2;
    private static final int PHOTO_REQUEST_CUT = 3;
    private static final int UPDATE_ADDRESS = 4;
    private static final int UPDATE_PHONE = 6;
    private static final int UPDATE_REMARK = 7;
//    private static final int UPDATE_SIGN = 8;
    
    private String avatar = "";
    private String imageName = "";
    private String username = "";
    private String company = "";
    private String company_code = "";
    private String sex = "";
    private String address = "";
    private String phone = "";
    private String remark = "";
//    private String sign = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo);
        
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    private void initView() {
        username = PrefUserInfo.getInstance().getUserInfo("real_name");

        company_code = PrefUserInfo.getInstance().getUserInfo("presonal_code"); // 编码
        company = PrefUserInfo.getInstance().getUserInfo("duty"); //职位

        sex = PrefUserInfo.getInstance().getUserInfo("sex");
        phone = PrefUserInfo.getInstance().getUserInfo("contactMobile");
        address = PrefUserInfo.getInstance().getUserInfo("address");
        remark = PrefUserInfo.getInstance().getUserInfo("remark");
//        sign = PrefUserInfo.getInstance().getUserInfo("sign");
        avatar = PrefUserInfo.getInstance().getUserInfo("avatar");

        re_avatar = (RelativeLayout) this.findViewById(R.id.re_avatar);
        re_name = (RelativeLayout) this.findViewById(R.id.re_name);
        re_sex = (RelativeLayout) this.findViewById(R.id.re_sex);
        re_address = (RelativeLayout) this.findViewById(R.id.re_address);
        re_phone = (RelativeLayout) this.findViewById(R.id.re_phone);
        re_remark = (RelativeLayout) this.findViewById(R.id.re_remark);
//        re_sign = (RelativeLayout) this.findViewById(R.id.re_sign);
        
        re_avatar.setOnClickListener(new MyListener());
        re_name.setOnClickListener(new MyListener());
        re_sex.setOnClickListener(new MyListener());
        re_address.setOnClickListener(new MyListener());
        re_phone.setOnClickListener(new MyListener());
        re_remark.setOnClickListener(new MyListener());
//        re_sign.setOnClickListener(new MyListener());
        
        iv_avatar = (ImageView) this.findViewById(R.id.iv_avatar);
        tv_name = (TextView) this.findViewById(R.id.tv_name);
        tv_company = (TextView) this.findViewById(R.id.tv_company);
        tv_company_code = (TextView) this.findViewById(R.id.tv_company_code);
        tv_sex = (TextView) this.findViewById(R.id.tv_sex);
        tv_address = (TextView) this.findViewById(R.id.tv_address);
        tv_phone = (TextView) this.findViewById(R.id.tv_phone);
        tv_remark = (TextView) this.findViewById(R.id.tv_remark);
//        tv_sign = (TextView) this.findViewById(R.id.tv_sign);
        
        tv_name.setText(username);
        tv_company.setText(company);
        tv_company_code.setText(company_code);
        tv_sex.setText(sex);
        tv_address.setText(address);
        tv_phone.setText(phone);
        tv_remark.setText(remark);
//        tv_sign.setText(sign);

        showUserAvatar(iv_avatar, avatar);
    }

    private void refresh() {
        username = PrefUserInfo.getInstance().getUserInfo("real_name");

        company_code = PrefUserInfo.getInstance().getUserInfo("presonal_code"); // 编码
        company = PrefUserInfo.getInstance().getUserInfo("duty"); //职位

        sex = PrefUserInfo.getInstance().getUserInfo("sex");
        phone = PrefUserInfo.getInstance().getUserInfo("contactMobile");
        address = PrefUserInfo.getInstance().getUserInfo("address");
        remark = PrefUserInfo.getInstance().getUserInfo("remark");
//        sign = PrefUserInfo.getInstance().getUserInfo("sign");
        avatar = PrefUserInfo.getInstance().getUserInfo("avatar");


        tv_name.setText(username);
        tv_company.setText(company);
        tv_company_code.setText(company_code);
        tv_sex.setText(sex);
        tv_address.setText(address);
        tv_phone.setText(phone);
        tv_remark.setText(remark);
//        tv_sign.setText(sign);

        showUserAvatar(iv_avatar, avatar);
    }

    class MyListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            Intent iS = new Intent(MyUserInfoActivity.this,
                    UpdateUserActivity.class);
            int i = v.getId();
            if (i == R.id.re_avatar) {
                showPhotoDialog();
            } else if (i == R.id.re_name) {
            } else if (i == R.id.re_sex) {
                showSexDialog();
            } else if (i == R.id.re_address) {
                iS.putExtra("ori", address);
                iS.putExtra("title", "更改家庭住址");
                iS.putExtra("hint", "请输入家庭住址");

                startActivityForResult(iS, UPDATE_ADDRESS);
            } else if (i == R.id.re_phone) {
                iS.putExtra("ori", phone);
                iS.putExtra("title", "更改联系电话");
                iS.putExtra("hint", "请输入真实联系电话");

                startActivityForResult(iS, UPDATE_PHONE);
            } else if (i == R.id.re_remark) {
                iS.putExtra("ori", remark);
                iS.putExtra("title", "更改备注");
                iS.putExtra("hint", "请输入备注信息");

                startActivityForResult(iS, UPDATE_REMARK);
                //            case R.id.re_sign:
//                iS.putExtra("ori", sign);
//                iS.putExtra("title", "更改个性签名");
//                iS.putExtra("hint", "请输入个性签名");
//
//                startActivityForResult(iS, UPDATE_SIGN);
//                break;
            }
        }

    }

    private void showPhotoDialog() {
        final AlertDialog dlg = new AlertDialog.Builder(this).create();
        dlg.show();
        Window window = dlg.getWindow();

        window.setContentView(R.layout.dialog_menu);

        TextView tv_paizhao = (TextView) window.findViewById(R.id.tv_content1);
        tv_paizhao.setText("拍照");
        tv_paizhao.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SdCardPath")
            public void onClick(View v) {

                imageName = StorageUtil.getTempPath() + DateUtil.getCurrentTime2() + ".png";
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(new File(imageName)));
                startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO);
                dlg.cancel();
            }
        });
        TextView tv_xiangce = (TextView) window.findViewById(R.id.tv_content2);
        tv_xiangce.setText("相册");
        tv_xiangce.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                imageName = StorageUtil.getTempPath() + DateUtil.getCurrentTime2() + ".png";
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, PHOTO_REQUEST_GALLERY);

                dlg.cancel();
            }
        });

    }

    private void showSexDialog() {
        final List<Dictionary> contentList = new ArrayList<Dictionary>();
        contentList.add(new Dictionary("男",""));
        contentList.add(new Dictionary("女",""));
        
        int cur = -1;
        if ("女".equals(sex)) {
            cur = 1;
        } else if ("男".equals(sex)) {
            cur = 0;
        }
        
        ListDialog.show(this, contentList, cur, new OnSelectedListener() {
            @Override
            public void onSelected(int position) {
                sex = contentList.get(position).getValue();
//                tv_sex.setText(sex);
//                updateSex(sex);
                updateFireFighterInfo("sex", sex);
            }
        });
    }

    @SuppressLint("SdCardPath")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
            case PHOTO_REQUEST_TAKEPHOTO:

                startPhotoZoom(
                        Uri.fromFile(new File(imageName)),
                        480);
                break;

            case PHOTO_REQUEST_GALLERY:
                if (data != null)
                    startPhotoZoom(data.getData(), 480);
                break;

            case PHOTO_REQUEST_CUT:
                Bitmap bitmap = BitmapFactory.decodeFile(imageName);
                iv_avatar.setImageBitmap(bitmap);
                updateAvatar(imageName);
                break;

            case UPDATE_ADDRESS:
                if (data != null && data.hasExtra("new")) {
                    address = (String) data.getSerializableExtra("new");
//                    tv_address.setText(address);
//                    updateAddress(address);
                    updateFireFighterInfo("address", address);
                }                
                break;

            case UPDATE_PHONE:
                if (data != null && data.hasExtra("new")) {
                    phone = (String) data.getSerializableExtra("new");
//                    tv_phone.setText(phone);
//                    updatePhone(phone);
                    updateFireFighterInfo("contactMobile", phone);
                }                
                break;

            case UPDATE_REMARK:
                if (data != null && data.hasExtra("new")) {
                    remark = (String) data.getSerializableExtra("new");
//                    tv_remark.setText(remark);
//                    updateRemark(remark);
                    updateFireFighterInfo("remark", remark);
                }                
                break;

//            case UPDATE_SIGN:
//                if (data != null && data.hasExtra("new")) {
//                    sign = (String) data.getSerializableExtra("new");
//                    tv_sign.setText(sign);
//                    updateSign(sign);
//                }
//                break;

            }
            super.onActivityResult(requestCode, resultCode, data);

        }
    }

    @SuppressLint("SdCardPath")
    private void startPhotoZoom(Uri uri1, int size) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri1, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        intent.putExtra("return-data", false);

        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(new File(imageName)));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    private void showUserAvatar(ImageView imageView, String avatar) {
        if (StringUtil.isEmpty(avatar)) {
            return;
        }
        Glide.with(this)
                .load(avatar)
                .into(imageView);
    }

    private void updateAvatar(final String image) {
        avatar = image;
        PrefUserInfo.getInstance().setUserInfo("avatar", avatar);
    }

    public void updateSex(final String sexnum) {
        sex = sexnum;
        PrefUserInfo.getInstance().setUserInfo("sex", sex);
    }
    
    private void updateAddress(String con) {
        address = con;
        PrefUserInfo.getInstance().setUserInfo("address", address);
    }
    
    private void updatePhone(String con) {
        phone = con;
        PrefUserInfo.getInstance().setUserInfo("contactMobile", phone);
    }

    private void updateRemark(String con) {
        remark = con;
        PrefUserInfo.getInstance().setUserInfo("remark", remark);
    }

    private void updateFireFighterInfo(String key, String value) {
        UserBussiness.getInstance().updateFireFighterInfo(
                key,
                value,
                this,
                new UserBussiness.OnMyUserInfoListener() {

                    @Override
                    public void onSuccess() {
                        refresh();
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
        });
    }
//    private void updateSign(String con) {
//        sign = con;
//        PrefUserInfo.getInstance().setUserInfo("sign", sign);
//    }
}
