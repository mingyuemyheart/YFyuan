package com.moft.xfapply.logic.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.moft.xfapply.model.external.result.RestResult;
import com.moft.xfapply.utils.StringUtil;

import android.os.AsyncTask;

public class NetworkJsonAsync extends AsyncTask<String, Integer, String> {
    private OnNetworkJsonResultListener listener;
    private String url;
    
    public NetworkJsonAsync(String url, OnNetworkJsonResultListener listener) {  
        super();
        this.listener = listener;
        this.url = url;
    }  

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        if (params.length != 1) {
            return "";
        }
        
        String jsonStr = params[0];

        HTTPService hs = HTTPService.getInstance();
        String result = hs.postJson(url, jsonStr);
        
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

    public interface OnNetworkJsonResultListener{
        void onSuccess(String result);
        void onFailure(String msg);
    }
}
