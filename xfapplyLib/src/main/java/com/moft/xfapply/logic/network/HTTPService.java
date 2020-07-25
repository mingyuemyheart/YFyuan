package com.moft.xfapply.logic.network;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import com.moft.xfapply.app.Constant;
import com.moft.xfapply.utils.StringUtil;


@SuppressWarnings("deprecation")
public class HTTPService {
    private static HTTPService instance = null;
    private static final int CONNECTION_TIMEOUT = 30000;
    private static final String DEFAULT_PARAMS_ENCODING = "UTF-8";

    public HTTPService() {
    }

    public static HTTPService getInstance() {
        if (instance == null) {
            instance = new HTTPService();
        }

        return instance;
    }
    
    public static void clear() {
        instance = null;
    }

    /**
     * 是否有可用网络
     */
    public boolean hasActiveNet(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connManager.getActiveNetworkInfo() != null) {
            return connManager.getActiveNetworkInfo().isAvailable();
        }

        return false;
    }

    public static boolean hasWifi(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (networkInfo != null) {
            return networkInfo.isAvailable();
        }

        return false;
    }
    
    public String getFile(String urlStr, String localFile, OnProgressListener listener) {
        String error = "";
        InputStream inputStream = null;  
        HttpURLConnection conn = null;  
        RandomAccessFile randomAccessFile = null;  
        try {  
            URL url = new URL(urlStr);  
            conn = (HttpURLConnection) url.openConnection();  
            conn.setConnectTimeout(CONNECTION_TIMEOUT);
            conn.setRequestMethod("GET");  
            
            randomAccessFile = new RandomAccessFile(localFile, "rwd");  

            inputStream = conn.getInputStream();  
            int totalSize = conn.getContentLength();
            int readedSize = 0;
            int progress = 0;

            byte[] buffer = new byte[2048];  
            int length = -1;  
            while ((length = inputStream.read(buffer)) != -1) {  
                randomAccessFile.write(buffer, 0, length); 
                
                if (listener != null) {                
                    readedSize += length;
                    int curProgress = Math.round((readedSize* 100) / totalSize);
                    if (curProgress - progress >= 1) {
                        listener.onProgress(curProgress);
                    }
                    progress = curProgress;
                }
            }  
            return "OK";  
        }  
        catch(SocketTimeoutException e)  {  
            e.printStackTrace();  
            error = "网络访问超时";
        }  
        catch(IOException e)  {  
            e.printStackTrace();  
            error = "网络读写错误";
        }  
        catch(Exception e)  {  
            e.printStackTrace(); 
            error = "网络访问错误"; 
        }  
        finally  
        {  
            if (inputStream != null) {  
                try {  
                    inputStream.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
            if (randomAccessFile != null) {  
                try {  
                    randomAccessFile.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
            if (conn != null) {  
                conn.disconnect();  
            }  
        }  
        return "NG" + error; 
    }

    public String get(String urlStr, String params) {
        String error = "";
        InputStream inputStream = null;  
        HttpURLConnection conn = null;  
        try {  
            URL url = new URL(urlStr + params);  
            conn = (HttpURLConnection) url.openConnection();  
            conn.setConnectTimeout(CONNECTION_TIMEOUT);
            conn.setRequestMethod("GET");  
            
            // try to get response  
            int statusCode = conn.getResponseCode();  
            if (statusCode >= 200 && statusCode <= 299) {  
                inputStream = conn.getInputStream();
                String response = readStream(inputStream); 
                return "OK" + response;  
            }  else if (statusCode >= 300 && statusCode <= 399) {
                error = "网络重定向错误";
            }  else if (statusCode >= 400 && statusCode <= 499) {
                error = "网络请求错误";
            }  else if (statusCode >= 500 && statusCode <= 599) {
                error = "服务器内部错误";
            }  else {
                error = "网络其他错误";
            }
        }  
        catch(SocketTimeoutException e)  {  
            e.printStackTrace();  
            error = "网络访问超时";
        }  
        catch(IOException e)  {  
            e.printStackTrace();  
            error = "网络读写错误";
        }  
        catch(Exception e)  {  
            e.printStackTrace(); 
            error = "网络访问错误"; 
        }  
        finally  
        {  
            if (inputStream != null) {  
                try {  
                    inputStream.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
            if (conn != null) {  
                conn.disconnect();  
            }  
        }  
        return "NG" + error; 
    }

    public String postMuti(String urlStr, Map<String, String> params, String localFile) {
        String BOUNDARY = java.util.UUID.randomUUID().toString();
        String PREFIX = "--";
        String LINEND = "\r\n";
        String MULTIPART_FROM_DATA = "multipart/form-data";
        String CHARSET = "UTF-8";
        String CONTENT_TYPE = "application/octet-stream";
        
        String error = "";
        InputStream inputStream = null;  
        HttpURLConnection conn = null;  
        try {  
            URL url = new URL(urlStr);  
            conn = (HttpURLConnection) url.openConnection();  
            conn.setConnectTimeout(CONNECTION_TIMEOUT);

            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("charset", CHARSET);
            conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
                + ";boundary=" + BOUNDARY); 
              
            // read response  
            /* for Post request */  
            conn.setRequestMethod("POST");  
            conn.setDoOutput(true);   
            conn.setDoInput(true);
            conn.setUseCaches(false);

            DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());

            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINEND);
                sb.append("Content-Disposition:form-data; name=\"" + entry.getKey()
                        + "\"" + LINEND);
                sb.append(LINEND);
                sb.append(entry.getValue());
                sb.append(LINEND);
            }
            outStream.write(sb.toString().getBytes());

            FormFile file = new FormFile(new FileInputStream(localFile), 
                    localFile, "Photo", CONTENT_TYPE);
            
            StringBuilder sb1 = new StringBuilder();
            sb1.append(PREFIX);
            sb1.append(BOUNDARY);
            sb1.append(LINEND);
            sb1.append("Content-Disposition: form-data; name=\""
                    + file.getFormnames() + "\"; filename=\"" 
                    + file.getFileName() + "\"" + LINEND);
            sb1.append("Content-Type: " + file.getContentType() 
                    + "; charset=" + CHARSET + LINEND);
            sb1.append(LINEND);
            outStream.write(sb1.toString().getBytes());

            InputStream is = file.getInStream();
            if (is != null) {
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = is.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }
                is.close();
            } else {
                outStream.write(file.getData(), 0, file.getData().length);
            }
            outStream.write(LINEND.getBytes());

            byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
            outStream.write(end_data);
            outStream.flush();
            outStream.close();
            
            // try to get response  
            int statusCode = conn.getResponseCode();  
            if (statusCode >= 200 && statusCode <= 299) {  
                inputStream = conn.getInputStream();
                String response = readStream(inputStream); 
                return "OK" + response;  
            }  else if (statusCode >= 300 && statusCode <= 399) {
                error = "网络重定向错误";
            }  else if (statusCode >= 400 && statusCode <= 499) {
                error = "网络请求错误";
            }  else if (statusCode >= 500 && statusCode <= 599) {
                error = "服务器内部错误";
            }  else {
                error = "网络其他错误";
            }
        }  
        catch(SocketTimeoutException e)  {  
            e.printStackTrace();  
            error = "网络访问超时";
        }  
        catch(IOException e)  {  
            e.printStackTrace();  
            error = "网络读写错误";
        }  
        catch(Exception e)  {  
            e.printStackTrace(); 
            error = "网络访问错误"; 
        }  
        finally  
        {  
            if (inputStream != null) {  
                try {  
                    inputStream.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
            if (conn != null) {  
                conn.disconnect();  
            }  
        }  
        return "NG" + error; 
    }

    public String postJson(String urlStr, String jsonString) {
        String error = "";
        InputStream inputStream = null;  
        HttpURLConnection conn = null;  
        try {  
            URL url = new URL(urlStr);  
            conn = (HttpURLConnection) url.openConnection();  
            conn.setConnectTimeout(CONNECTION_TIMEOUT);
      
            /* optional request header */  
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");  
      
            /* optional request header */  
            conn.setRequestProperty("Accept", "application/json");  
              
            // read response  
            /* for Post request */  
            conn.setRequestMethod("POST");  
            conn.setDoOutput(true);   

            StringBuffer buffer = new StringBuffer();
            buffer.append(jsonString);
            byte[] data = buffer.toString().getBytes(DEFAULT_PARAMS_ENCODING);

            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(data);
            outputStream.close();
            
            // try to get response  
            int statusCode = conn.getResponseCode();  
            if (statusCode >= 200 && statusCode <= 299) {  
                inputStream = conn.getInputStream();
                String response = readStream(inputStream); 
                return "OK" + response;  
            }  else if (statusCode >= 300 && statusCode <= 399) {
                error = "网络重定向错误";
            }  else if (statusCode >= 400 && statusCode <= 499) {
                error = "网络请求错误";
            }  else if (statusCode >= 500 && statusCode <= 599) {
                error = "服务器内部错误";
            }  else {
                error = "网络其他错误";
            }
        }  
        catch(SocketTimeoutException e)  {  
            e.printStackTrace();  
            error = "网络访问超时";
        }  
        catch(IOException e)  {  
            e.printStackTrace();  
            error = "网络读写错误";
        }  
        catch(Exception e)  {  
            e.printStackTrace(); 
            error = "网络访问错误"; 
        }  
        finally  
        {  
            if (inputStream != null) {  
                try {  
                    inputStream.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
            if (conn != null) {  
                conn.disconnect();  
            }  
        }  
        return "NG" + error; 
    }


    public String postMap(String urlStr, Map<String, String> key_value) {
        String error = "";
        InputStream inputStream = null;  
        HttpURLConnection conn = null;  
        try {  
            URL url = new URL(urlStr);  
            conn = (HttpURLConnection) url.openConnection();  
            conn.setConnectTimeout(CONNECTION_TIMEOUT);
      
            /* optional request header */  
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");  
      
            /* optional request header */  
            conn.setRequestProperty("Accept", "application/json");  
              
            // read response  
            /* for Post request */  
            conn.setRequestMethod("POST");  
            conn.setDoOutput(true);  
            
            byte[] data = getParamsData(key_value);

            if (null != data) {
                conn.setFixedLengthStreamingMode(data.length);
                conn.setRequestProperty("Content-Length", String.valueOf(data.length));
                OutputStream outputStream = conn.getOutputStream();
                outputStream.write(data);
                outputStream.close();
                
                // try to get response  
                int statusCode = conn.getResponseCode();  
                if (statusCode >= 200 && statusCode <= 299) {  
                    inputStream = conn.getInputStream();
                    String response = readStream(inputStream); 
                    return "OK" + response;  
                }  else if (statusCode >= 300 && statusCode <= 399) {
                    error = "网络重定向错误";
                }  else if (statusCode >= 400 && statusCode <= 499) {
                    error = "网络请求错误";
                }  else if (statusCode >= 500 && statusCode <= 599) {
                    error = "服务器内部错误";
                }  else {
                    error = "网络其他错误";
                }
            }  else {
                error = "程序内部错误";
            }
        }
        catch(SocketTimeoutException e)  {  
            e.printStackTrace();  
            error = "网络访问超时";
        }  
        catch(IOException e)  {  
            e.printStackTrace();  
            error = "网络读写错误";
        }  
        catch(Exception e)  {  
            e.printStackTrace(); 
            error = "网络访问错误"; 
        }  
        finally  
        {  
            if (inputStream != null) {  
                try {  
                    inputStream.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
            if (conn != null) {  
                conn.disconnect();  
            }  
        }  
        return "NG" + error;  
    }

    public InputStream getStream(String url) {
        HttpParams httpparams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpparams,
                CONNECTION_TIMEOUT);
        HttpClient client = new DefaultHttpClient(httpparams);

        // for Bug： url中若有特殊字符，导致httpGet异常的问题 START
        String tmp_http = "";
        String tmp_url = "";
        String regex = "([a-zA-z]+://([^/:]+)(:\\d*)?/)(.*)";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(url);
        if (m.find()) {
            tmp_http = m.group(1);
            tmp_url = m.group(4);
        }
        try {
            tmp_url = URLEncoder.encode(tmp_url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (!StringUtil.isEmpty(tmp_http) && !StringUtil.isEmpty(tmp_url)) {
            url = tmp_http + tmp_url;
        }
        // for Bug： url中若有特殊字符，导致httpGet异常的问题 END

        try {
            HttpGet get = new HttpGet(url);
            HttpResponse response = client.execute(get);
            int status = response.getStatusLine().getStatusCode();

            if (200 == status) {
                return response.getEntity().getContent();
            }
        } catch (Exception e) {
        }

        return null;
    }
    
    private byte[] getParamsData(Map<String, String> params) {
        byte[] data = null;

        try {
            if (null != params && !params.isEmpty()) {
                StringBuffer buffer = new StringBuffer();

                for (Map.Entry<String, String> entry : params.entrySet()) {
                    buffer.append(entry.getKey())
                            .append("=")
                            .append(URLEncoder.encode(entry.getValue(),
                                    DEFAULT_PARAMS_ENCODING)).append("&");
                }
                // 最后一个&要去掉
                buffer.deleteCharAt(buffer.length() - 1);

                data = buffer.toString().getBytes(DEFAULT_PARAMS_ENCODING);
            }
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return data;
    }
    
    private String readStream(InputStream is) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = -1;
            while( (len = is.read(buffer))!=-1){
                baos.write(buffer, 0, len);
            }
            is.close();
            String temptext = new String(baos.toByteArray());
            if(temptext.contains("charset=gb2312")){
                return new String(baos.toByteArray(),"gb2312");
            }else{
                return new String(baos.toByteArray(),"utf-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public interface OnProgressListener{
        void onProgress(int progress);
    }
}
