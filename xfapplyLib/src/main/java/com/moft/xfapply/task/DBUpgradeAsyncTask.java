package com.moft.xfapply.task;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.moft.xfapply.activity.bussiness.CommonBussiness;
import com.moft.xfapply.activity.bussiness.DBUpdateBussiness;
import com.moft.xfapply.app.Constant;
import com.moft.xfapply.model.common.ExceptionCategory;
import com.moft.xfapply.model.common.UpgradeDBInfo;
import com.moft.xfapply.utils.DateUtil;
import com.moft.xfapply.utils.DbUtil;
import com.moft.xfapply.utils.FileUtil;
import com.moft.xfapply.utils.StorageUtil;
import com.moft.xfapply.utils.StringUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangquan on 2017/4/25.
 */

public class DBUpgradeAsyncTask extends AsyncTask<Object, Integer, List<ExceptionCategory>> {
    private OnDBUpgradeListener listener;
    public DBUpgradeAsyncTask(OnDBUpgradeListener listener) {
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
    protected List<ExceptionCategory> doInBackground(Object... params) {
        List<ExceptionCategory> exceptionCategories = new ArrayList<>();
        List<UpgradeDBInfo> upgradeDBInfoList = (List<UpgradeDBInfo>)params[0];
        int dbVersion = (int)params[1];
        String dataPath = StorageUtil.getHomePath().substring(0, StorageUtil.getHomePath().length() - 1) + "_bak_" + String.format("v%d_", dbVersion) + DateUtil.getCurrentTime2() + "/" + Constant.DB;
        int stepCount = upgradeDBInfoList.size();
        publishProgress(0, stepCount);
        for(int index = 0; index < stepCount; ++index) {
            UpgradeDBInfo info = upgradeDBInfoList.get(index);
            if(info.getUpgradeCommand() != null && info.getUpgradeCommand().size() > 0) {
                List<String> commands = new ArrayList<>();
                int baseLine = info.getBaseLine();
                for(UpgradeDBInfo.UpgradeCommand upgradeCommand : info.getUpgradeCommand()) {
                    for(String command : upgradeCommand.getCommands()) {
                        commands.add(command);
                    }
                }
                if(UpgradeDBInfo.MODE.common.toString().equals(info.getMode())) {
                    ExceptionCategory exceptionCategory = updateCommonDB(dbVersion, baseLine, commands, dataPath);
                    if(exceptionCategory != null) {
                        exceptionCategories.add(exceptionCategory);
                    }
                } else if(UpgradeDBInfo.MODE.offline.toString().equals(info.getMode())) {
                    ExceptionCategory exceptionCategory = updateOfflineDB(dbVersion, baseLine, commands, dataPath);
                    if(exceptionCategory != null) {
                        exceptionCategories.add(exceptionCategory);
                    }
                } else if(UpgradeDBInfo.MODE.history.toString().equals(info.getMode())) {
                    ExceptionCategory exceptionCategory = updateHistoryDB(dbVersion, baseLine, commands, dataPath);
                    if(exceptionCategory != null) {
                        exceptionCategories.add(exceptionCategory);
                    }
                }
            }
            publishProgress(index, stepCount);
        }

        return exceptionCategories;
    }

    private ExceptionCategory updateCommonDB(int dbVersion, int baseLine, List<String> commands, String dataPath) {
        ExceptionCategory ret = null;
        if(dbVersion < baseLine) {
            String targetFile = dataPath + Constant.DB_NAME_COMMON + Constant.DB_EXTENSION;
            moveToFile(StorageUtil.getDbPath() + Constant.DB_NAME_COMMON + Constant.DB_EXTENSION, targetFile);
        } else {
            if(commands.size() > 0) {
                SQLiteDatabase commonDB = DbUtil.getInstance().getRawDB(Constant.DB_NAME_COMMON);
                if (commonDB != null) {
                    List<String> exceptions = DbUtil.getInstance().executeSQLBatch(commonDB, commands);
                    DbUtil.getInstance().closeRawDB(commonDB);
                    if (exceptions.size() > 0) {
                        ret = new ExceptionCategory("本地数据库", exceptions);
                    }
                }
            }
        }
        return ret;
    }

    private ExceptionCategory updateOfflineDB(int dbVersion, int baseLine, List<String> commands, String dataPath) {
        ExceptionCategory ret = null;
        String dbPath = StorageUtil.getDbPath().substring(0, StorageUtil.getDbPath().length() - 1);
        File dbFolder = new File(dbPath);
        if(dbFolder.exists()) {
            for(File file : dbFolder.listFiles()) {
                if(file.isFile()) {
                    String dbName = file.getName().substring(0, file.getName().lastIndexOf("."));
                    String suffix = file.getName().substring(file.getName().lastIndexOf("."));
                    if(Constant.DB_EXTENSION.equals(suffix) && !dbName.equals(Constant.DB_NAME_COMMON)) {
                        if(dbVersion < baseLine) {
                            String targetFile = dataPath + file.getName();
                            moveToFile(file.getPath(), targetFile);
                        } else {
                            if(commands.size() > 0) {
                                SQLiteDatabase offlineDB = DbUtil.getInstance().getRawDB(dbName);
                                if (offlineDB != null) {
                                    List<String> exceptions = DbUtil.getInstance().executeSQLBatch(offlineDB, commands);
                                    DbUtil.getInstance().closeRawDB(offlineDB);
                                    if (exceptions.size() > 0) {
                                        ret = new ExceptionCategory("离线数据库", exceptions);
                                        return ret;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return ret;
    }

    private ExceptionCategory updateHistoryDB(int dbVersion, int baseLine, List<String> commands, String dataPath) {
        ExceptionCategory ret = null;
        String dbPath = StorageUtil.getDbPath().substring(0, StorageUtil.getDbPath().length() - 1);
        File dbFolder = new File(dbPath);
        if(dbFolder.exists()) {
            for(File file : dbFolder.listFiles()) {
                if(file.isDirectory()) {
                    String dbName = "main_" + file.getName();
                    String historyName = file.getPath() + "/" + dbName + Constant.DB_EXTENSION;
                    if(FileUtil.isFileExists(historyName)) {
                        if(dbVersion < baseLine) {
                            String targetFile = dataPath + file.getName() + "/" + dbName + Constant.DB_EXTENSION;
                            moveToFile(historyName, targetFile);
                        } else {
                            if(commands.size() > 0) {
                                SQLiteDatabase historyDB = DbUtil.getInstance().getRawDB(file.getName() + "/" + dbName);
                                if (historyDB != null) {
                                    List<String> exceptions = DbUtil.getInstance().executeSQLBatch(historyDB, commands);
                                    DbUtil.getInstance().closeRawDB(historyDB);
                                    if (exceptions.size() > 0) {
                                        ret = new ExceptionCategory("历史数据库", exceptions);
                                        return ret;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return ret;
    }

    private void moveToFile(String srcFile, String dstFile) {
        String path = dstFile.substring(0, dstFile.lastIndexOf("/"));
        if(!FileUtil.isFileExists(path)) {
            FileUtil.createDir(path);
        }
        FileUtil.renameFile(srcFile, dstFile);
    }

    private boolean checkColumnExist(SQLiteDatabase db, String tableName, String columnName) {
        boolean result = false ;
        Cursor cursor = null ;
        try{
            //查询一行
            cursor = db.rawQuery( "SELECT * FROM " + tableName + " LIMIT 0", null );
            result = cursor != null && cursor.getColumnIndex(columnName) != -1 ;
        }catch (Exception e){

        }finally{
            if(null != cursor && !cursor.isClosed()){
                cursor.close() ;
            }
        }

        return result ;
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        if(listener != null) {
            listener.onProgressUpdate(progress[0] * 100 / progress[1]);
        }
    }

    @Override
    protected void onPostExecute(List<ExceptionCategory> list) {
        if(list.size() == 0) {
            if (listener != null) {
                listener.onPostExecute();
            }
        } else {
            if (listener != null) {
                listener.onPostException(list);
            }
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
    public interface OnDBUpgradeListener {
        public void onPreExecute();
        public void onProgressUpdate(int value);
        public void onPostExecute();
        public void onPostException(List<ExceptionCategory> list);
    }
}