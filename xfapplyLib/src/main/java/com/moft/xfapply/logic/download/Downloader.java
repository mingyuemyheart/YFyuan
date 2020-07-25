package com.moft.xfapply.logic.download;

import android.os.Handler;
import android.os.Message;

import com.moft.xfapply.model.entity.DataDownloadDBInfo;
import com.moft.xfapply.utils.LogUtils;
import com.moft.xfapply.utils.StringUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Downloader {  
    private String urlstr;// 下载的地址  
    private String key;// 下载的地址  
    private String localfile;// 保存路径  
    private int threadcount;// 线程数  
    private Handler mHandler;// 消息处理器  
    private DownloadInfoDao dao;// 工具类  
    private int fileSize;// 所要下载的文件的大小  
    private List<DataDownloadDBInfo> infos;// 存放下载信息类的集合
    private static final int INIT = 1;//定义三种下载的状态：初始化状态，正在下载状态，暂停状态  
    private static final int DOWNLOADING = 2;  
    private static final int PAUSE = 3;
    private int state = INIT;  
 
    public Downloader(String urlstr, String localfile, int threadcount, Handler mHandler) {  
        this.urlstr = urlstr;  
        this.key = urlstr;
        this.localfile = localfile;  
        this.threadcount = threadcount;  
        this.mHandler = mHandler;  
        dao = new DownloadInfoDao();  
    }  
    
    public void setKey(String key) {
        this.key = key;
    }
    
    
    /**  
     *判断是否正在下载  
     */  
    public boolean isdownloading() {  
        return state == DOWNLOADING;  
    }  
    /**  
     * 得到downloader里的信息  
     * 首先进行判断是否是第一次下载，如果是第一次就要进行初始化，并将下载器的信息保存到数据库中  
     * 如果不是第一次下载，那就要从数据库中读出之前下载的信息（起始位置，结束为止，文件大小等），并将下载信息返回给下载器  
     */  
    public LoadInfo getDownloaderInfors() {  
        if (isFirst(key)) {   
            init(); 
            return null;  
        } else {  
            //得到数据库中已有的urlstr的下载器的具体信息  
            infos = dao.getInfos(key);  
            int size = 0;  
            int compeleteSize = 0;  
            for (DataDownloadDBInfo info : infos) {
                compeleteSize += info.getCompeleteSize();  
                size += info.getEndPos() - info.getStartPos() + 1;  
            }  
            return new LoadInfo(size, compeleteSize, urlstr);  
        }  
    }  
 
    /**  
     * 初始化  
     */  
    private void init() {
        LogUtils.d(String.format("Downloader init state = %d", state));
        new MyInitThread().start();
    }  
    

    public class MyInitThread extends Thread {  
        @Override  
        public void run() {  
            String error = "";
            HttpURLConnection connection = null;
            RandomAccessFile accessFile = null;
            try {  
                URL url = new URL(urlstr);  
                connection = (HttpURLConnection) url.openConnection();  
                connection.setConnectTimeout(5000);  
                connection.setRequestMethod("GET");  
                fileSize = connection.getContentLength();
                LogUtils.d(String.format("Downloader MyInitThread url = %s, fileSize = %d", urlstr, fileSize));
                File file = new File(localfile);  
                if (!file.exists()) {  
                    file.createNewFile();  
                }  
                // 本地访问文件  
                accessFile = new RandomAccessFile(file, "rwd");
                accessFile.setLength(fileSize);  
            }  
            catch(SocketTimeoutException e)  {  
                e.printStackTrace();  
                error = "网络访问超时";
                LogUtils.d(String.format("Downloader MyInitThread error = %s", e.getMessage()));
            }  
            catch(IOException e)  {  
                e.printStackTrace();  
                error = "网络读写错误";
                LogUtils.d(String.format("Downloader MyInitThread error = %s", e.getMessage()));
            }  
            catch(Exception e)  {  
                e.printStackTrace(); 
                error = "网络访问错误";
                LogUtils.d(String.format("Downloader MyInitThread error = %s", e.getMessage()));
            }  
            finally  
            {
                if (accessFile != null) {  
                    try {  
                        accessFile.close();  
                    } catch (IOException e) {  
                        e.printStackTrace();
                        LogUtils.d(String.format("Downloader MyInitThread error = %s", e.getMessage()));
                    }  
                }  
                if (connection != null) {  
                    connection.disconnect();  
                }  
            }  
            
            if (StringUtil.isEmpty(error)) {
                int range = fileSize / threadcount;  
                infos = new ArrayList<DataDownloadDBInfo>();
                for (int i = 0; i < threadcount - 1; i++) {  
                    DataDownloadDBInfo info = new DataDownloadDBInfo(i, i * range, (i + 1)* range - 1, 0, urlstr);
                    infos.add(info);
                    LogUtils.d(String.format("Downloader MyInitThread DataDownloadDBInfo threadId = %d, startPos = %d, endPos = %d, compeleteSize = %d, url = %s",
                            info.getThreadId(), info.getStartPos(), info.getEndPos(), info.getCompeleteSize(), info.getUrl()));
                }  
                DataDownloadDBInfo info = new DataDownloadDBInfo(threadcount - 1,(threadcount - 1) * range, fileSize - 1, 0, urlstr);
                infos.add(info);
                LogUtils.d(String.format("Downloader MyInitThread DataDownloadDBInfo threadId = %d, startPos = %d, endPos = %d, compeleteSize = %d, url = %s",
                        info.getThreadId(), info.getStartPos(), info.getEndPos(), info.getCompeleteSize(), info.getUrl()));
                //保存infos中的数据到数据库  
                dao.saveInfos(infos);  
                //创建一个LoadInfo对象记载下载器的具体信息  
                LoadInfo loadInfo = new LoadInfo(fileSize, 0, urlstr);  
                
                Message message = Message.obtain();  
                message.what = 0;  //初始化
                message.obj = loadInfo;  
                mHandler.sendMessage(message); 
            } else {
                // 用消息将下载信息传给进度条，对进度条进行更新  
                Message message = Message.obtain();  
                message.what = 2;  //失败
                message.obj = error;  
                mHandler.sendMessage(message); 
                state = PAUSE;  
            }
        }
    }
 
    /**  
     * 判断是否是第一次 下载  
     */  
    private boolean isFirst(String key) {  
        return !dao.isHasInfors(key);  
    }  
 
    /**  
     * 利用线程开始下载数据  
     */  
    public void download() {
        LogUtils.d("Downloader download");
        if (infos != null) {
            LogUtils.d(String.format("Downloader download state = %d", state));
            if (state == DOWNLOADING) {
                return;
            }
            state = DOWNLOADING; 
            for (DataDownloadDBInfo info : infos) {
                MyThread thread = new MyThread(info.getThreadId(), info.getStartPos(),  
                        info.getEndPos(), info.getCompeleteSize(),  
                        info.getUrl());  
                thread.start();
            }  
        }  
    }  
 
    public class MyThread extends Thread {  
        private int threadId;  
        private long startPos;
        private long endPos;
        private long compeleteSize;
        private String urlstr;  
 
        public MyThread(int threadId, long startPos, long endPos,
                        long compeleteSize, String urlstr) {
            this.threadId = threadId;  
            this.startPos = startPos;  
            this.endPos = endPos;  
            this.compeleteSize = compeleteSize;  
            this.urlstr = urlstr;  
        }  
        @Override  
        public void run() {  
            String error = "";
            HttpURLConnection connection = null;
            RandomAccessFile randomAccessFile = null;
            InputStream is = null;  
            try {
                LogUtils.d(String.format("Downloader MyThread threadId = %d, startPos = %d, endPos = %d, compeleteSize = %d, url = %s",
                        threadId, startPos, endPos, compeleteSize, urlstr));
                if (startPos + compeleteSize < endPos) {
                    URL url = new URL(urlstr);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(5000);
                    connection.setRequestMethod("GET");
                    // 设置范围，格式为Range：bytes x-y;
                    connection.setRequestProperty("Range", "bytes=" + (startPos + compeleteSize) + "-" + endPos);

                    randomAccessFile = new RandomAccessFile(localfile, "rwd");
                    randomAccessFile.seek(startPos + compeleteSize);
                    // 将要下载的文件写到保存在保存路径下的文件中
                    is = connection.getInputStream();
                    byte[] buffer = new byte[1024 * 10];
                    int length = -1;
                    while ((length = is.read(buffer)) != -1) {
                        randomAccessFile.write(buffer, 0, length);
                        compeleteSize += length;
                        // 更新数据库中的下载信息
                        dao.updataInfos(threadId, compeleteSize, key);
                        // 用消息将下载信息传给进度条，对进度条进行更新
                        Message message = Message.obtain();
                        message.what = 1;
                        message.obj = urlstr;
                        message.arg1 = length;
                        mHandler.sendMessage(message);
                        if (state == PAUSE || state == INIT) {
                            return;
                        }
                        Thread.sleep(140);
                    }
                } else {
                    LogUtils.d("Downloader MyThread Range bytes = " + (startPos + compeleteSize) + " - " + endPos);
                }
                LogUtils.d(String.format("Downloader MyThread read complete threadId = %d, startPos = %d, endPos = %d, compeleteSize = %d, url = %s",
                        threadId, startPos, endPos, compeleteSize, urlstr));
            }
            catch(SocketTimeoutException e)  {  
                e.printStackTrace();  
                error = "网络访问超时";
                LogUtils.d(String.format("Downloader MyThread error = %s", e.getMessage()));
            }  
            catch(IOException e)  {  
                e.printStackTrace();  
                error = "网络读写错误";
                LogUtils.d(String.format("Downloader MyThread error = %s", e.getMessage()));
            }  
            catch(Exception e)  {  
                e.printStackTrace(); 
                error = "网络访问错误";
                LogUtils.d(String.format("Downloader MyThread error = %s", e.getMessage()));
            } finally {  
                try {  
                    if (is != null) {
                        is.close();
                        is = null;
                    }
                    if (randomAccessFile != null) {
                        randomAccessFile.close(); 
                        randomAccessFile = null;
                    }
                    if (connection != null) {
                        connection.disconnect();  
                        connection = null;
                    }
                } catch (Exception e) {  
                    e.printStackTrace();  
                    error = "网络访问错误";
                    LogUtils.d(String.format("Downloader MyThread error = %s", e.getMessage()));
                }  
            }              

            if (!StringUtil.isEmpty(error)) {
                Message message = Message.obtain();  
                message.what = 2;  //失败
                message.obj = error;  
                mHandler.sendMessage(message); 
                state = PAUSE;  
            } else {
                Message message = Message.obtain();  
                message.what = 3;  //成功
                message.obj = error;  
                mHandler.sendMessage(message); 
            }
        }  
    }  
    //删除数据库中urlstr对应的下载器信息  
    public void delete(String key) {
        LogUtils.d(String.format("Downloader delete key = %s", key));
        dao.delete(key);  
    }  
    //设置暂停  
    public void pause() {
        LogUtils.d(String.format("Downloader pause state = %d", state));
        state = PAUSE;  
    }  
    //重置下载状态  
    public void reset() {
        LogUtils.d(String.format("Downloader reset state = %d", state));
       state = INIT;  
    }
} 