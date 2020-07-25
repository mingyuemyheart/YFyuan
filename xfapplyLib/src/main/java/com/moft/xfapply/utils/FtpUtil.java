package com.moft.xfapply.utils;

import com.moft.xfapply.app.Constant;
import com.moft.xfapply.logic.setting.PrefSetting;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class FtpUtil {
    public final static int FTP_CONNECT_SUCCESS = 0;
    public final static int FTP_CONNECT_FAIL = 1;
    public final static int FTP_DISCONNECT_SUCCESS = 2;
    public final static int FTP_DISCONNECT_FAIL = 3;
    public final static int FTP_UPLOAD_SUCCESS = 4;
    public final static int FTP_UPLOAD_FAIL = 5;
    public final static int FTP_DOWN_LOADING = 6;
    public final static int FTP_DOWN_SUCCESS = 7;
    public final static int FTP_DOWN_FAIL = 8;
    public final static int FTP_DELETEFILE_SUCCESS = 9;
    public final static int FTP_DELETEFILE_FAIL = 10;
    public final static int FTP_FILE_NOTEXISTS = 11;

    /**
     * 服务器名.
     */
    private String hostName;

    /**
     * 端口号
     */
    private int serverPort;

    /**
     * 用户名.
     */
    private String userName;

    /**
     * 密码.
     */
    private String password;

    /**
     * FTP连接.
     */
    private FTPClient ftpClient;

    public FtpUtil() {
        hostName = Constant.FTP_SERVER;//PrefSetting.getInstance().getServerIP();
        serverPort = Constant.FTP_PORT;
        userName = Constant.FTP_USER;
        password = Constant.FTP_PASSWORD;
        ftpClient = new FTPClient();
    }

    // -------------------------------------------------------文件上传方法------------------------------------------------

    /**
     * 上传单个文件.
     *
     * @param singleFile
     *            本地文件
     * @param remotePath
     *            FTP目录
     * @param listener
     *            监听器
     * @throws IOException
     */
    public void uploadSingleFile(File singleFile, String remotePath, FtpProgressListener listener) throws IOException {

        // 上传之前初始化
        this.uploadBeforeOperate(remotePath, listener);

        boolean flag = uploadingSingle(singleFile, listener);
        if (flag) {
            listener.onFtpProgress(FTP_UPLOAD_SUCCESS, 0, singleFile);
        } else {
            listener.onFtpProgress(FTP_UPLOAD_FAIL, 0, singleFile);
        }

        // 上传完成之后关闭连接
        this.uploadAfterOperate(listener);
    }

    /**
     * 上传多个文件.
     *
     * @param fileList
     *            本地文件List<File>
     * @param remotePath
     *            FTP目录
     * @param listener
     *            监听器
     * @throws IOException
     */
    public void uploadMultiFile(List<File> fileList, String remotePath, FtpProgressListener listener) throws IOException {

        // 上传之前初始化
        if (!this.uploadBeforeOperate(remotePath, listener)) {
            return;
        }

        for (File singleFile : fileList) {
            boolean flag = uploadingSingle(singleFile, listener);
            if (flag) {
                listener.onFtpProgress(FTP_UPLOAD_SUCCESS, 0, singleFile);
            } else {
                listener.onFtpProgress(FTP_UPLOAD_FAIL, 0, singleFile);
            }
        }

        // 上传完成之后关闭连接
        this.uploadAfterOperate(listener);
    }

    /**
     * 上传单个文件.
     *
     * @param localFile
     *            本地文件
     * @return true上传成功, false上传失败
     * @throws IOException
     */
    private boolean uploadingSingle(File localFile, FtpProgressListener listener) throws IOException {
        boolean flag = true;
        /** 不带进度的方式 */
        // 创建输入流
        InputStream inputStream = new FileInputStream(localFile);
        // 上传单个文件
        flag = ftpClient.storeFile(localFile.getName(), inputStream);
        // 关闭文件流
        inputStream.close();

        // 带有进度的方式
//        BufferedInputStream buffIn = new BufferedInputStream(new FileInputStream(localFile));
//        ProgressInputStream progressInput = new ProgressInputStream(buffIn, listener, localFile);
//        flag = ftpClient.storeFile(localFile.getName(), buffIn);
//        buffIn.close();

        return flag;
    }

    /**
     * 上传文件之前初始化相关参数
     *
     * @param remotePath
     *            FTP目录
     * @param listener
     *            监听器
     * @throws IOException
     */
    private boolean uploadBeforeOperate(String remotePath, FtpProgressListener listener) throws IOException {
        // 打开FTP服务
        try {
            this.openConnect();
            listener.onFtpProgress(FTP_CONNECT_SUCCESS, 0, null);
        } catch (IOException e1) {
            e1.printStackTrace();
            listener.onFtpProgress(FTP_CONNECT_FAIL, 0, null);
            return false;
        }

        // 设置模式
        ftpClient.setFileTransferMode(org.apache.commons.net.ftp.FTP.STREAM_TRANSFER_MODE);
        // FTP下创建文件夹
        ftpClient.makeDirectory(remotePath);
        // 改变FTP目录
        ftpClient.changeWorkingDirectory(remotePath);
        // 上传单个文件
        return true;

    }

    /**
     * 上传完成之后关闭连接
     *
     * @param listener
     * @throws IOException
     */
    private void uploadAfterOperate(FtpProgressListener listener) throws IOException {
        this.closeConnect();
        listener.onFtpProgress(FTP_DISCONNECT_SUCCESS, 0, null);
    }

    // -------------------------------------------------------文件下载方法------------------------------------------------

    /**
     * 下载单个文件，可实现断点下载.
     *
     * @param serverPath
     *            Ftp目录及文件路径
     * @param localPath
     *            本地目录
     * @param fileName
     *            下载之后的文件名称
     * @param listener
     *            监听器
     * @throws IOException
     */
    public void downloadSingleFile(String serverPath, String localPath, String fileName, FtpProgressListener listener) throws Exception {

        // 打开FTP服务
        try {
            this.openConnect();
            listener.onFtpProgress(FTP_CONNECT_SUCCESS, 0, null);
        } catch (IOException e1) {
            e1.printStackTrace();
            listener.onFtpProgress(FTP_CONNECT_FAIL, 0, null);
            return;
        }

        // 先判断服务器文件是否存在
        FTPFile[] files = ftpClient.listFiles(serverPath);
        if (files.length == 0) {
            listener.onFtpProgress(FTP_FILE_NOTEXISTS, 0, null);
            return;
        }

        // 创建本地文件夹
        File mkFile = new File(localPath);
        if (!mkFile.exists()) {
            mkFile.mkdirs();
        }

        localPath = localPath + fileName;
        // 接着判断下载的文件是否能断点下载
        long serverSize = files[0].getSize(); // 获取远程文件的长度
        File localFile = new File(localPath);
        long localSize = 0;
        if (localFile.exists()) {
            localSize = localFile.length(); // 如果本地文件存在，获取本地文件的长度
            if (localSize >= serverSize) {
                File file = new File(localPath);
                file.delete();
            }
        }

        // 进度
        long step = serverSize / 100;
        long process = 0;
        long currentSize = 0;
        // 开始准备下载文件
        OutputStream out = new FileOutputStream(localFile, true);
        ftpClient.setRestartOffset(localSize);
        InputStream input = ftpClient.retrieveFileStream(serverPath);
        byte[] b = new byte[1024];
        int length = 0;
        while ((length = input.read(b)) != -1) {
            out.write(b, 0, length);
            currentSize = currentSize + length;
            if (currentSize / step != process) {
                process = currentSize / step;
                if (process % 5 == 0) { // 每隔%5的进度返回一次
                    listener.onFtpProgress(FTP_DOWN_LOADING, process, null);
                }
            }
        }
        out.flush();
        out.close();
        input.close();

        // 此方法是来确保流处理完毕，如果没有此方法，可能会造成现程序死掉
        if (ftpClient.completePendingCommand()) {
            listener.onFtpProgress(FTP_DOWN_SUCCESS, 0, new File(localPath));
        } else {
            listener.onFtpProgress(FTP_DOWN_FAIL, 0, null);
        }

        // 下载完成之后关闭连接
        this.closeConnect();
        listener.onFtpProgress(FTP_DISCONNECT_SUCCESS, 0, null);

        return;
    }

    // -------------------------------------------------------文件删除方法------------------------------------------------

    /**
     * 删除Ftp下的文件.
     *
     * @param serverPath
     *            Ftp目录及文件路径
     * @param listener
     *            监听器
     * @throws IOException
     */
    public void deleteSingleFile(String serverPath, FtpDeleteFileListener listener) throws Exception {

        // 打开FTP服务
        try {
            this.openConnect();
            listener.onFtpDelete(FTP_CONNECT_SUCCESS);
        } catch (IOException e1) {
            e1.printStackTrace();
            listener.onFtpDelete(FTP_CONNECT_FAIL);
            return;
        }

        // 先判断服务器文件是否存在
        FTPFile[] files = ftpClient.listFiles(serverPath);
        if (files.length == 0) {
            listener.onFtpDelete(FTP_FILE_NOTEXISTS);
            return;
        }

        // 进行删除操作
        boolean flag = true;
        flag = ftpClient.deleteFile(serverPath);
        if (flag) {
            listener.onFtpDelete(FTP_DELETEFILE_SUCCESS);
        } else {
            listener.onFtpDelete(FTP_DELETEFILE_FAIL);
        }

        // 删除完成之后关闭连接
        this.closeConnect();
        listener.onFtpDelete(FTP_DISCONNECT_SUCCESS);

        return;
    }

    // -------------------------------------------------------获取FTP服务器文件-----------------------------------------
    /**
     * @Description TODO 获取指定服务器指定目录下的文件
     * @param serverPath
     *            服务器路径
     * @param filter
     *            过滤器
     * @param listener
     *            监听器
     * @return 返回FTPFile
     * @throws Exception
     */
    public List<FTPFile> listsFiles(String serverPath, FTPFileFilter filter, FtpListFileListener listener) throws Exception {
        List<FTPFile> ftpFileList = new ArrayList<FTPFile>();
        // 打开FTP服务
        try {
            this.openConnect();
            listener.onFtpListFile(FTP_CONNECT_SUCCESS, ftpFileList);
        } catch (IOException e1) {
            e1.printStackTrace();
            listener.onFtpListFile(FTP_CONNECT_FAIL, ftpFileList);
            return null;
        }
        FTPFile[] ftpFiles = ftpClient.listFiles(serverPath, filter);
        if (ftpFiles.length > 0) {
            for (FTPFile ftpfile : ftpFiles) {
                ftpFileList.add(ftpfile);
            }
        }
        // 关闭连接
        this.closeConnect();
        listener.onFtpListFile(FTP_DISCONNECT_SUCCESS, ftpFileList);
        return ftpFileList;
    }

    // -------------------------------------------------------打开关闭连接------------------------------------------------

    /**
     * 打开FTP服务.
     *
     * @throws IOException
     */
    public void openConnect() throws IOException {
        // 中文转码
        ftpClient.setControlEncoding("UTF-8");
        int reply; // 服务器响应值
        // 连接至服务器
        ftpClient.connect(hostName, serverPort);
        // 获取响应值
        reply = ftpClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            // 断开连接
            ftpClient.disconnect();
            throw new IOException("connect fail: " + reply);
        }
        // 登录到服务器
        ftpClient.login(userName, password);
        // 获取响应值
        reply = ftpClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            // 断开连接
            ftpClient.disconnect();
            throw new IOException("connect fail: " + reply);
        } else {
            // 获取登录信息
            FTPClientConfig config = new FTPClientConfig(ftpClient.getSystemType().split(" ")[0]);
            config.setServerLanguageCode("zh");
            ftpClient.configure(config);
            // 使用被动模式设为默认
            ftpClient.enterLocalPassiveMode();
            // 二进制文件支持
            ftpClient.setFileType(org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE);
        }
    }

    /**
     * 关闭FTP服务.
     *
     * @throws IOException
     */
    public void closeConnect() throws IOException {
        if (ftpClient != null) {
            // 退出FTP
            ftpClient.logout();
            // 断开连接
            ftpClient.disconnect();
        }
    }

    // ---------------------------------------------------上传下载进度、删除、获取文件监听---------------------------------------------

    /*
     * 进度监听器
     */
    public interface FtpProgressListener {
        /**
         *
         * @Description TODO FTP 文件长传下载进度触发
         * @param currentStatus
         *            当前FTP状态
         * @param process
         *            当前进度
         * @param targetFile
         *            目标文件
         */
        public void onFtpProgress(int currentStatus, long process, File targetFile);
    }

    /*
     * 文件删除监听
     */
    public interface FtpDeleteFileListener {
        /**
         *
         * @Description TODO 删除FTP文件
         * @param currentStatus
         *            当前FTP状态
         */
        public void onFtpDelete(int currentStatus);
    }

    /*
     * 获取文件监听
     */
    public interface FtpListFileListener {
        /**
         *
         * @Description TODO 列出FTP文件
         * @param currentStatus
         *            当前FTP状态
         * @param ftpFileList
         *            获取的List<FTPFile>
         */
        public void onFtpListFile(int currentStatus, List<FTPFile> ftpFileList);
    }

}
