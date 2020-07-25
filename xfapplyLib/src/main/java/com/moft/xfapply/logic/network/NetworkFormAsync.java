package com.moft.xfapply.logic.network;

import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.moft.xfapply.model.external.result.RestResult;
import com.moft.xfapply.utils.StringUtil;

import android.os.AsyncTask;

public class NetworkFormAsync extends AsyncTask<Map<String, String>, Integer, String> {
    private OnNetworkFormResultListener listener;
    private String url;
    
    public NetworkFormAsync(String url, OnNetworkFormResultListener listener) {  
        super();
        this.listener = listener;
        this.url = url;
    }  

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Map<String, String>... params) {
        if (params.length != 1) {
            return "";
        }
        
        Map<String, String> key_value = params[0];

        HTTPService hs = HTTPService.getInstance();
        String result = hs.postMap(url, key_value);
        
        return result;
    }

    protected void onProgressUpdate(Integer... progress) {     
    }

    @Override
    protected void onPostExecute(String result) {
        if (listener == null || null == result) {
            return;
        }
        
        String status = result.substring(0, 2);
        String content = result.substring(2);
        
        if (!"OK".equals(status)) {
            listener.onFailure(content);
        } else {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

            RestResult rr = gson.fromJson(content, RestResult.class);
            if (rr.isSuccess()) {
                listener.onSuccess(content);                
            } else {
                String msg = "未知错误";
                if (!StringUtil.isEmpty(rr.getMessage())) {
                    msg = rr.getMessage();
                }
                listener.onFailure(msg);
            }            
        }
    }
    
    @Override  
    protected void onCancelled() {   
        super.onCancelled();
    }  

    public interface OnNetworkFormResultListener{
        void onSuccess(String result);
        void onFailure(String msg);
    }
}
