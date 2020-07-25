package com.moft.xfapply.activity.logic;

import java.io.File;

import com.moft.xfapply.app.Constant;
import com.moft.xfapply.base.BaseActivity;
import com.moft.xfapply.base.ViewLogic;
import com.moft.xfapply.utils.BitmapUtil;
import com.moft.xfapply.utils.StorageUtil;
import com.moft.xfapply.utils.StringUtil;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

public class CameraLogic extends ViewLogic {
    public static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
    private String tempPhotoPath = "";
    private OnCameraLogicListener mListener = null;

    public CameraLogic(View v, BaseActivity a) {
        super(v, a);
    }

    public void init() {
    }
    
    public void doPhoto() {
        try {
            if (!StorageUtil.isExternalMemoryAvailable()) {
                Toast.makeText(getActivity(), "没有找到存储卡！", Toast.LENGTH_LONG).show();
                return;
            }
            
            Intent intent = new Intent(
                    android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            
            File localFile = StorageUtil.getTempPhotoFile();
            tempPhotoPath = localFile.getAbsolutePath();
            
            Uri uri = Uri.fromFile(localFile);
            
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            getActivity().startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO);

        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "没有找到可用的系统相机！", Toast.LENGTH_LONG).show();
            return;
        }
    }
    
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != PHOTO_REQUEST_TAKEPHOTO) {
            return;
        }
        
        if (StringUtil.isEmpty(tempPhotoPath)) {
            return;
        }

        Bitmap photo = BitmapUtil.resizeBitmap(tempPhotoPath, Constant.imgWidth, Constant.imgHeight);
        if(photo != null) {
            BitmapUtil.saveBitmap(tempPhotoPath, photo);
            photo.recycle();
            if (mListener != null) {
                mListener.onPhoto(tempPhotoPath);
            }
        }
    }

    public OnCameraLogicListener getListener() {
        return mListener;
    }

    public void setListener(OnCameraLogicListener myListener) {
        this.mListener = myListener;
    }

    public interface OnCameraLogicListener{
        void onPhoto(String path);
    }
}
