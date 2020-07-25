package com.moft.xfapply.task;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.moft.xfapply.app.Constant;
import com.moft.xfapply.logic.setting.PrefSetting;
import com.moft.xfapply.logic.setting.PrefUserInfo;
import com.moft.xfapply.model.external.AppDefs;
import com.moft.xfapply.model.external.params.ActualSqlForm;
import com.moft.xfapply.utils.DbUtil;
import com.moft.xfapply.utils.LogUtils;
import com.moft.xfapply.utils.StringUtil;
import com.moft.xfapply.utils.TelManagerUtil;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
 * @Author: 宋满意
 * @Date:   2019-01-24 17:30:53
 * No.              Date.          Modifier    Description
 * 【HW-925904】      2019-04-03     宋满意       大量数据同步时报内存不足异常。
 */
public class RequestGetActualSQLAsyncTask extends AsyncTask<Object, Integer, List<String>> {

    private final static int CONNECTION_TIMEOUT = 3000;
    private final static int SUCCESS_STATUS = 200;
    private final static String SQL_LITE = "sqlList";
    private final static String END_DATE = "endDate";
    private final static String SUCCESS = "success";
    private final static String DATA = "data";
    private final static String NULL_PATTERN = "targetId|message|extraData|optionResData";
    private final static String INT_PATTERN = "errorCode";

    private RequestGetActualSQLListener listener;
    private Date endDate;

    public RequestGetActualSQLAsyncTask(RequestGetActualSQLListener listener) {
        super();
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(listener != null) {
            listener.onPreExecute();
        }
    }

    @Override
    protected List<String> doInBackground(Object... params) {
        // 接口参数
        ActualSqlForm sqlForm = (ActualSqlForm)params[0];
        // 对应修改的数据库
        String dbName = (String)params[1];
        SQLiteDatabase db = DbUtil.getInstance().getRawDB(dbName);
        // 设置返回值：异常列表
        List<String> exceptions = new ArrayList<>();
        InputStream inStream = null;
        // 获得请求客户端
        HttpClient httpclient = getHttpClient();
        String url = convertToURL(Constant.METHOD_GET_ACTUAL_SQL);
        HttpPost httppost = new HttpPost(url);
        // 4.执行,发送请求
        try {
            // 设定参数
            httppost.setEntity(getEntity(sqlForm));
            // 设置权限
            auth(httppost);

            HttpResponse resultRep = httpclient.execute(httppost);
            int statusCode = resultRep.getStatusLine().getStatusCode();
            if (statusCode == SUCCESS_STATUS) {
                // 5.获取传输的数据并解析
                HttpEntity httpEntity = resultRep.getEntity();
                // 6.将HTTP中返回实体转化为输入流
                inStream = httpEntity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
                JsonReader jsonReader = new JsonReader(reader);
                dealJsonReader(jsonReader, db, exceptions);
            } else {
                throw new RuntimeException(String.format("requestGetActualSQL is failed! statusCode = %d", statusCode));
            }
        } catch (Exception e) {
            exceptions.add(e.getMessage());
        } finally {
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            DbUtil.getInstance().closeRawDB(db);
        }
        return exceptions;
    }

    private void dealJsonReader(JsonReader jsonReader, SQLiteDatabase db, List<String> exceptions) throws Exception {
        jsonReader.beginObject();
        while (jsonReader.hasNext()){
            String name = jsonReader.nextName();
            if (SUCCESS.equals(name)) {
                boolean success = jsonReader.nextBoolean();
                if (!success) {
                    throw new RuntimeException("requestGetActualSQL is result failed");
                }
            } else if (NULL_PATTERN.contains(name)) {
                jsonReader.nextNull();
            } else if (INT_PATTERN.contains(name)) {
                jsonReader.nextInt();
            } else if (DATA.equals(name)) {
                jsonReader.beginObject();
                while(jsonReader.hasNext()) {
                    String dataName = jsonReader.nextName();
                    if (END_DATE.equals(dataName)) {
                        String endDateStr = jsonReader.nextString();
                        if (StringUtil.isEmpty(endDateStr)) {
                            throw new RuntimeException(String.format("%s%s%s","返回参数[", END_DATE,"]异常"));
                        }
                        endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endDateStr);
                    } else if (SQL_LITE.equals(dataName)) {
                        dealTagSqlList(jsonReader, db, exceptions);
                    } else {
                        jsonReader.nextString();
                    }
                }
                jsonReader.endObject();
            }
        }
        jsonReader.endObject();
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
    }

    @Override
    protected void onPostExecute(List<String> exceptions) {
        if(listener != null) {
            listener.onPostExecute(exceptions, endDate);
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    public interface RequestGetActualSQLListener {
        void onPreExecute();
        void onPostExecute(List<String> exceptions, Date endDate);
    }

    private HttpClient getHttpClient() {
        HttpParams httpparams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpparams,
                CONNECTION_TIMEOUT);
        return new DefaultHttpClient(httpparams);
    }

    private String convertToURL(String methodName) {
        return "http://" + PrefSetting.getInstance().getServerIP() + ":" +
                PrefSetting.getInstance().getServerPort() + Constant.SERVICE_NAME + methodName;
    }

    private StringEntity getEntity(ActualSqlForm sqlForm) throws UnsupportedEncodingException {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        StringEntity entity = new StringEntity(gson.toJson(sqlForm));
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        return entity;
    }

    private void dealTagSqlList(JsonReader jsonReader, SQLiteDatabase db, List<String> exceptions) throws Exception {
        jsonReader.beginArray();
        try {
            if (db == null) {
                exceptions.add("db is NULL!");
                return;
            }
            //开启事务
            db.beginTransaction();
            while (jsonReader.hasNext()) {
                String sql = jsonReader.nextString();
                if (!StringUtil.isEmpty(sql)) {
                    try {
                        try {
                            db.execSQL(sql);
                        } catch (Exception e) {
                            exceptions.add(e.getMessage());
                            LogUtils.d(String.format("DbUtil executeSQL exception = %s", e.getMessage()));
                        }
                    } catch (Exception e) {
                        exceptions.add(e.getMessage());
                        LogUtils.d(String.format("DbUtil executeSQLBatch exception = %s", e.getMessage()));
                    }
                }
            }
            //设置事务标志为成功，当结束事务时就会提交事务
             db.setTransactionSuccessful();
        } catch (Exception e) {
            exceptions.add(e.getMessage());
        } finally {
            //事务结束
            if (db != null) {
                db.endTransaction();
            }
        }
        jsonReader.endArray();
    }

//    private void dealTagSqlList(JsonReader jsonReader, SQLiteDatabase db, List<String> exceptions) throws Exception {
//        jsonReader.beginArray();
//        List<String> sqlList = new ArrayList<>();
//        while (jsonReader.hasNext()) {
//            String sql = jsonReader.nextString();
//            if (!StringUtil.isEmpty(sql)) {
//                sqlList.add(sql);
//                if (sqlList.size() % BATCH_QUANTITY == 0) {
//                    executeSQL(db, sqlList, exceptions);
//                    sqlList = new ArrayList<>();
//                }
//            }
//        }
//        if (sqlList != null && !sqlList.isEmpty()) {
//            executeSQL(db, sqlList, exceptions);
//        }
//        jsonReader.endArray();
//    }

    private void auth(HttpPost httppost) {
        String token = PrefUserInfo.getInstance().getUserInfo("token");
        String deviceId = TelManagerUtil.getInstance().getDeviceId();

        httppost.setHeader("Authorization", token);
        httppost.setHeader("user-agent", AppDefs.SubmitWay.MOBILE.toString());
        httppost.setHeader("device-id", deviceId);
    }
}
