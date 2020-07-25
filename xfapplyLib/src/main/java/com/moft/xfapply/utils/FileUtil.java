package com.moft.xfapply.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;

import androidx.core.content.FileProvider;

import com.moft.xfapply.app.LvApplication;

public class FileUtil {
 
    /**
     * 保存图片到制定路径
     * 
     * @param filepath
     * @param bitmap
     */
    public static String saveFile(String filepath, Bitmap bitmap) {
        String pictureDir = "";
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] byteArray = baos.toByteArray();
            File file = new File(filepath);
            file.delete();
            if (!file.exists()) {
                file.createNewFile();
            }
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(byteArray);
            pictureDir = file.getPath();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (baos != null) {
                try {
                    baos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return pictureDir;
    }
    
    public static void saveFile(String filepath, byte[] data) {
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        ByteArrayOutputStream baos = null;
        try {
            File file = new File(filepath);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(data);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (baos != null) {
                try {
                    baos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return;
    }    

    @SuppressLint("SdCardPath")
    public static boolean isFileExists(String filepath) {
        File file = new File(filepath);

        return file.exists();
    }
 
    public static void copyFile(String oldPath, String newPath) {           
        try {   
            int byteread = 0;   
            File oldfile = new File(oldPath);   
            File newfile = new File(newPath); 
            if (oldfile.getPath().equals(newfile.getPath())) {
                return;
            }
            
            if (oldfile.exists()) { //文件存在时   
                InputStream inStream = new FileInputStream(oldPath); //读入原文件   
                FileOutputStream fs = new FileOutputStream(newPath);   
                byte[] buffer = new byte[1444];   
                while ( (byteread = inStream.read(buffer)) != -1) {   
                    fs.write(buffer, 0, byteread);   
                }   
                inStream.close();   
                fs.close();
            }   
        }   
        catch (Exception e) {   
            e.printStackTrace();   
        }   
   
    }   

    public static File createFile(String srcFileName) {
        boolean result = true;
        
        File file = new File(srcFileName);  
        if(file.exists()) {   
            file.delete();
        } 
        
        try {
            result = file.createNewFile();
            if (!result) {
                return null;
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return file;
    }
    
    public static boolean moveFile(String srcFileName, String destDirName) {
        File srcFile = new File(srcFileName);  
        if(!srcFile.exists() || !srcFile.isFile())   
            return false;
          
        return srcFile.renameTo(new File(destDirName)); 
    }
        
    public static void deleteFile(String srcFileName) {
        if (StringUtil.isEmpty(srcFileName)) {
            return;
        }
        
        File srcFile = new File(srcFileName);  
        if(!srcFile.exists() || (!srcFile.isFile() && !srcFile.isDirectory()))   
            return;
        
        if (srcFile.isFile()) {
             srcFile.delete();
             return;
        }

        if(srcFile.isDirectory()){  
            File[] childFiles = srcFile.listFiles();  
            if (childFiles == null || childFiles.length == 0) {  
                srcFile.delete();  
                return;  
            }  

            for (int i = 0; i < childFiles.length; i++) {  
                childFiles[i].delete();  
            }  
            srcFile.delete();  
        }  
    }

    public static boolean renameFile(String oldPath, String newPath) {
        boolean ret = false;
        if(!oldPath.equals(newPath)){//新的文件名和以前文件名不同时,才有必要进行重命名
            File oldfile = new File(oldPath);
            File newfile = new File(newPath);
            if(!oldfile.exists()){
                return ret;
            }
            if(!newfile.exists()) {//若在该目录下已经有一个文件和新文件名相同，则不允许重命名
                oldfile.renameTo(newfile);
                ret = true;
            }
        }
        return ret;
    }
    
    public static String getFileName(String fNameOrPath) {
        
        String name=""; 
        
        int pathIndex = fNameOrPath.lastIndexOf("/"); 
        
        name = fNameOrPath.substring(pathIndex+1); 
        
        return name; 
    }
    
    public static String getFileNameNoSuffix(String fNameOrPath) {
        
        String name=""; 
        int dotIndex = fNameOrPath.lastIndexOf("."); 
        if(dotIndex < 0){ 
            return name; 
        } 
        
        int pathIndex = fNameOrPath.lastIndexOf("/"); 
        
        name = fNameOrPath.substring(pathIndex+1,dotIndex); 
        return name; 
    }
    
    public static String getFileSuffix(String fNameOrPath) {

        String name=""; 
        int dotIndex = fNameOrPath.lastIndexOf("."); 
        if(dotIndex < 0){ 
            return name; 
        } 
        
        name = fNameOrPath.substring(dotIndex).toLowerCase(); 
        
        return name; 
    }
    
    public static String getFileSuffixNoDot(String fNameOrPath) {

        String name=""; 
        int dotIndex = fNameOrPath.lastIndexOf("."); 
        if(dotIndex < 0){ 
            return name; 
        } 
        
        name = fNameOrPath.substring(dotIndex + 1).toLowerCase(); 
        
        return name; 
    }
    
    public static void createDir(String dirPath) {
        File dir = new File(dirPath);

        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
    public static Intent openFile(String filePath) {

        File file = new File(filePath);
        if (!file.exists())
            return null;
		/* 取得扩展名 */
        String end = file
                .getName()
                .substring(file.getName().lastIndexOf(".") + 1,
                        file.getName().length()).toLowerCase();
		/* 依扩展名的类型决定MimeType */
        if (end.equals("m4a") || end.equals("mp3") || end.equals("mid")
                || end.equals("xmf") || end.equals("ogg") || end.equals("wav")) {
            return getAudioFileIntent(filePath);
        } else if (end.equals("3gp") || end.equals("mp4")) {
            return getAudioFileIntent(filePath);
        } else if (end.equals("jpg") || end.equals("gif") || end.equals("png")
                || end.equals("jpeg") || end.equals("bmp")) {
            return getImageFileIntent(filePath);
        } else if (end.equals("apk")) {
            return getApkFileIntent(filePath);
        } else if (end.equals("ppt") || end.equals("pptx")) {
            return getPptFileIntent(filePath);
        } else if (end.equals("xls") || end.equals("xlsx")) {
            return getExcelFileIntent(filePath);
        } else if (end.equals("doc") || end.equals("docx")) {
            return getWordFileIntent(filePath);
        } else if (end.equals("pdf")) {
            return getPdfFileIntent(filePath);
        } else if (end.equals("chm")) {
            return getChmFileIntent(filePath);
        } else if (end.equals("txt")) {
            return getTextFileIntent(filePath, false);
        } else {
            return getAllIntent(filePath);
        }
    }
    // Android获取一个用于打开APK文件的intent
    public static Intent getAllIntent(String param) {

        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "*/*");
        return intent;
    }

    // Android获取一个用于打开APK文件的intent
    public static Intent getApkFileIntent(String param) {

        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        return intent;
    }

    // Android获取一个用于打开VIDEO文件的intent
    public static Intent getVideoFileIntent(String param) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "video/*");
        return intent;
    }

    // Android获取一个用于打开AUDIO文件的intent
    public static Intent getAudioFileIntent(String param) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "audio/*");
        return intent;
    }

    // Android获取一个用于打开Html文件的intent
    public static Intent getHtmlFileIntent(String param) {

        Uri uri = Uri.parse(param).buildUpon()
                .encodedAuthority("com.android.htmlfileprovider")
                .scheme("content").encodedPath(param).build();
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(uri, "text/html");
        return intent;
    }

    // Android获取一个用于打开图片文件的intent
    public static Intent getImageFileIntent(String param) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = getUriFromFile(param); //Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "image/*");
        return intent;
    }

    // Android获取一个用于打开PPT文件的intent
    public static Intent getPptFileIntent(String param) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        return intent;
    }

    // Android获取一个用于打开Excel文件的intent
    public static Intent getExcelFileIntent(String param) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/vnd.ms-excel");
        return intent;
    }

    // Android获取一个用于打开Word文件的intent
    public static Intent getWordFileIntent(String param) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/msword");
        return intent;
    }

    // Android获取一个用于打开CHM文件的intent
    public static Intent getChmFileIntent(String param) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/x-chm");
        return intent;
    }

    // Android获取一个用于打开文本文件的intent
    public static Intent getTextFileIntent(String param, boolean paramBoolean) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (paramBoolean) {
            Uri uri1 = Uri.parse(param);
            intent.setDataAndType(uri1, "text/plain");
        } else {
            Uri uri2 = Uri.fromFile(new File(param));
            intent.setDataAndType(uri2, "text/plain");
        }
        return intent;
    }

    // Android获取一个用于打开PDF文件的intent
    public static Intent getPdfFileIntent(String param) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/pdf");
        return intent;
    }

    private static Uri getUriFromFile(String filePath) {
        Uri uri = null;
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            uri = Uri.fromFile(new File(filePath));
        } else {
            /**
             * 7.0 调用系统相机拍照不再允许使用Uri方式，应该替换为FileProvider
             * 并且这样可以解决MIUI系统上拍照返回size为0的情况
             */
            uri = FileProvider.getUriForFile(LvApplication.getInstance(),"com.moft.xfapply.provider", new File(filePath));
        }
        return uri;
    }
}
