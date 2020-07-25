package com.moft.xfapply.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.moft.xfapply.app.Constant;
import com.moft.xfapply.app.LvApplication;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;

public class StorageUtil {
	public static String getHomePath() {	    
        if (!isExternalMemoryAvailable()) {
            return getHomePathPrivate();
        }

        String path = "";
        path = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + Constant.FOLDER;

        File dir = new File(path);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                return null;
            }
        }        
        return path;
	}
	
    public static String getHomePathPrivate() {
        String path = "";
        
        Context cont = LvApplication.getContext();
        File file = cont.getFilesDir();
        File parentFile = file.getParentFile();
        if (parentFile.exists()) {
            path = parentFile.getPath();
        } else {            
            return null;
        }

        path += Constant.FOLDER;

        File dir = new File(path);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                return null;
            }
        }
        
        return path;
    }
	
	public static String getPersonDbPath(String person) {
        String homePath = getDbPath();
        
        String path = homePath + person + "/";
        
        File dir = new File(path);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                return null;
            }
        }
        
        return path;
	}
    
    public static String getTempPath() {
        return getSubPath(Constant.TEMP_FOLDER, true);
    }

    public static String getAttachPath() {
        return getSubPath(Constant.ATTACH_FOLDER, true);
    }

    public static String getDbPath() {
        return getSubPath(Constant.DB, true);
    }

    public static String getDbPathPrivate() {
        return getSubPath(Constant.DB, false);
    }
    
    public static String getCachePath() {  
        String path = null;  
        Context context = LvApplication.getContext();
        if (isExternalMemoryAvailable()) {  
            path = context.getExternalCacheDir().getPath();  
        } else {  
            path = context.getCacheDir().getPath();  
        }  
        return path;  
    }

    public static String getAppCachePath() {
        return getSubPath(Constant.CRASH_FOLDER, true);
    }
    
    @SuppressLint("SimpleDateFormat")
	public static File getTempPhotoFile() {
        File file = null;
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        file = new File(getTempPath() + "IMG_" +  timeStamp + ".jpg");

        return file;
        
    }
    
    private static String getSubPath(String sub, boolean isSdCard) {
        String homePath = "";
        if (isSdCard) {
            homePath = getHomePath();
        } else {
            homePath = getHomePathPrivate();
        }
        
        String path = homePath + sub;
        
        File dir = new File(path);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                return null;
            }
        }
        
        return path;
    }

	public static boolean isExternalMemoryAvailable() {
        new Thread(new Runnable() {
            int count = 0;
            int sleepTime = 1000;
            public void run() {
                while (!Environment.getExternalStorageState().equals(
                        android.os.Environment.MEDIA_MOUNTED)) {
                    
                    if (++count > 5) {
                        break;
                    }
                    
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }).start();
	    
		return Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
	}
	
	public static long getAvailableMaxMemory() {
	    return Runtime.getRuntime().maxMemory();
	}
}
