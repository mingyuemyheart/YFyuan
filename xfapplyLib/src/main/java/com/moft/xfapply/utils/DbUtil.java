package com.moft.xfapply.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.moft.xfapply.R;
import com.moft.xfapply.activity.bussiness.CommonBussiness;
import com.moft.xfapply.activity.bussiness.DBUpdateBussiness;
import com.moft.xfapply.app.Constant;
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.model.entity.AppAccessDBInfo;
import com.moft.xfapply.model.external.AppDefs;

import net.tsz.afinal.FinalDb;
import net.tsz.afinal.exception.DbException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class DbUtil {
	private static DbUtil mInstance = null;
    Map<String, FinalDb> mapDb = new HashMap<String, FinalDb>();

	public static DbUtil getInstance() {
		if (mInstance == null) {
			mInstance = new DbUtil();
		}
		return mInstance;
	}

    public FinalDb getDB(String key) {
        if (StringUtil.isEmpty(key)) {
            return null;
        }
        
        FinalDb db = null;
        if (mapDb.containsKey(key)) {
            db = mapDb.get(key);
        } else {
            db = DbUtil.getInstance().loadDB(key, key, R.raw.sycx);
        }
        updateAccessRecord();
        return db;
    }

    public FinalDb getDB2(String key) {
        if (StringUtil.isEmpty(key)) {
            return null;
        }

        FinalDb db = null;
        if (mapDb.containsKey(key)) {
            db = mapDb.get(key);
        } else {
            db = DbUtil.getInstance().loadDB(key, key, R.raw.sycx);
        }
        return db;
    }
    
    public void close() {        
        Iterator<Entry<String, FinalDb>> iter = mapDb.entrySet().iterator();
        while (iter.hasNext()) {  
            Entry<String, FinalDb> entry = iter.next();
            FinalDb db = entry.getValue();
        }
    }
    
    public FinalDb loadDB(String key, String dbPath, String dbName, int dbResource) {
        if (mapDb.containsKey(key)) {
            return mapDb.get(key);
        }
        dbName += ".db";
            
        String dbFullName = dbPath + dbName;
        if (!FileUtil.isFileExists(dbFullName)) {
            Context cont = LvApplication.getContext();
            InputStream is = cont.getResources().openRawResource(dbResource);

            copyFileDB(is, dbFullName);
        }

        FinalDb db = FinalDb.create(LvApplication.getInstance(), dbPath, dbName, false);
        mapDb.put(key, db);
        return db;
    }

    //同步DB（将temp文件下的DB，更新到DB文件夹下）
    public void syncDB(String key, String dbPath, String tempDBPath, String dbName) {
        if (mapDb.containsKey(key)) {
            FinalDb db =  mapDb.get(key);
            closeFinalDb(db);
            mapDb.remove(key);
            db = null;
        }
        String rawDbName = dbName;
        dbName += ".db";
        String dbFullName = dbPath + dbName;
        String sourceFullName = tempDBPath + dbName;
        try {
            InputStream is = new FileInputStream(sourceFullName); //读入原文件
            copyFileDB(is, dbFullName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        FinalDb db = FinalDb.create(LvApplication.getInstance(), dbPath, dbName, false);
        mapDb.put(key, db);
    }
    
	public FinalDb loadDB(String key, String dbName, int dbResource, boolean inSdCard) {
	    String dbPath = "";
	    if (inSdCard) {
	        dbPath = StorageUtil.getDbPath();
	    } else {
            dbPath = StorageUtil.getDbPathPrivate();
	    }
        
        return loadDB(key, dbPath, dbName, dbResource);
	}
    
    public FinalDb loadDB(String key, String dbName, int dbResource) {
        return loadDB(key, dbName, dbResource, true);
    }

    public void closeFinalDb(FinalDb db) {
        if (null == db) {
            return;
        }

        Field fieldDb = null;
        Field fieldMap = null;
        SQLiteDatabase oriDb = null;
        HashMap<String, FinalDb> oriDaoMap = null;
        try {
            fieldDb = db.getClass().getDeclaredField("db");
            fieldDb.setAccessible(true);

            fieldMap = db.getClass().getDeclaredField("daoMap");
            fieldMap.setAccessible(true);
            try {
                oriDb = (SQLiteDatabase)fieldDb.get(db);
                oriDaoMap = (HashMap<String, FinalDb>)fieldMap.get(db);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        if (oriDb != null && oriDaoMap != null) {
            String dbPath = oriDb.getPath();
            oriDb.close();

            int headI = dbPath.lastIndexOf("/");
            String dbName = dbPath.substring(headI + 1);
            oriDaoMap.remove(dbName);
        }
    }

    public void closeAllFinalDb() {
        for (FinalDb db : mapDb.values()) {
            closeFinalDb(db);
        }
        mapDb.clear();
    }

    private void copyFileDB(InputStream is, String dbFullName) {
        FileOutputStream fos = null;
        try {
            File dirDatabase = new File(dbFullName);
            if (!dirDatabase.exists()) {
                dirDatabase.createNewFile();
            }
            try {
                fos = new FileOutputStream(dirDatabase);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            byte[] buffer = new byte[8192];
            int count = 0;
            while ((count = is.read(buffer)) > 0) {
                fos.write(buffer, 0, count);
            }
            fos.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SQLiteDatabase getRawDB(String key) {
        SQLiteDatabase db = null;
        String dbPath = StorageUtil.getDbPath();
        String dbName = key + ".db";
        File dbf = new File(dbPath, dbName);
        if (dbf.exists()) {
            db = SQLiteDatabase.openOrCreateDatabase(dbf, null);
        }
        return db;
    }

    public SQLiteDatabase getPrivateRawDB(String key, int dbResource) {
        SQLiteDatabase rawDB = null;
        String dbPath = StorageUtil.getDbPathPrivate();
        String dbName = key + ".db";

        String dbFullName = dbPath + dbName;
        if (!FileUtil.isFileExists(dbFullName)) {
            Context cont = LvApplication.getContext();
            InputStream is = cont.getResources().openRawResource(dbResource);

            copyFileDB(is, dbFullName);
            rawDB = SQLiteDatabase.openOrCreateDatabase(new File(dbFullName), null);
            if(rawDB != null) {
                rawDB.setVersion(SystemUtil.getVersionCode());
            }
        } else {
            rawDB = SQLiteDatabase.openOrCreateDatabase(new File(dbFullName), null);
            if(rawDB != null) {
                if (rawDB.getVersion() < SystemUtil.getVersionCode()) {
                    FileUtil.deleteFile(dbFullName);
                    closeRawDB(rawDB);
                    rawDB = getPrivateRawDB(key, dbResource);
                }
            }
        }
        return rawDB;
    }

    /**
     * 读取数据库文件（.sql），并执行sql语句
     * @param db
     * @@param sqlList
     */
    public List<String> executeSQLBatch(SQLiteDatabase db, List<String> sqlList) {
        List<String> exceptions = new ArrayList<>();
        if(db == null) {
            exceptions.add("db is NULL!");
            return exceptions;
        }

        try {
            //开启事务
            db.beginTransaction();
            for(String sql : sqlList) {
                try {
                    db.execSQL(sql);
                } catch (Exception e) {
                    exceptions.add(e.getMessage());
                    LogUtils.d(String.format("DbUtil executeSQL exception = %s", e.getMessage()));
                }
            }
            //设置事务标志为成功，当结束事务时就会提交事务
            db.setTransactionSuccessful();
        } catch (Exception e) {
            exceptions.add(e.getMessage());
            LogUtils.d(String.format("DbUtil executeSQLBatch exception = %s", e.getMessage()));
        } finally {
            //事务结束
            db.endTransaction();
        }
        return exceptions;
    }
    
    public void closeRawDB(SQLiteDatabase db) {
        if (db != null) {
            db.close();
        }
    }

    private void updateAccessRecord() {
        FinalDb db = null;
        if (mapDb.containsKey(Constant.DB_NAME_COMMON)) {
            db = mapDb.get(Constant.DB_NAME_COMMON);
        } else {
            db = loadDB(Constant.DB_NAME_COMMON, Constant.DB_NAME_COMMON, R.raw.common);
        }
        String condition = String.format("device_app_id = '%s'", AppDefs.DeviceAppIdentity.YY.toString());
        List<AppAccessDBInfo> list = db.findAllByWhere(AppAccessDBInfo.class, condition, "login_date desc limit 1");
        //ID:837499 19-01-09 终端统计逻辑变更，半小时没有操作，不计入使用时长 王泉 开始
        if (list != null && list.size() > 0) {
            AppAccessDBInfo info = list.get(0);
            if(info.getIsCommit() == null || !info.getIsCommit()) {
                Long lastOperateDate = info.getLastOperateDate();
                long curDate = System.currentTimeMillis();
                if((lastOperateDate != null) && (curDate - lastOperateDate < Constant.OPERATION_DEADLINE)) {
                    info.setLastOperateDate(System.currentTimeMillis());
                    db.update(info);
                } else {
                    //如果两次操作超过半小时，认为上一次操作结束，下一次操作开始。
                    info.setIsCommit(true);
                    db.update(info);
                    CommonBussiness.getInstance().submitAppAccessRecord(info, true);
                    AppAccessDBInfo newInfo = new AppAccessDBInfo();
                    Long newDate = System.currentTimeMillis();

                    newInfo.setDeviceAppId(AppDefs.DeviceAppIdentity.YY.toString());
                    newInfo.setLoginDate(newDate);
                    newInfo.setLastOperateDate(newDate);
                    db.save(newInfo);
                }
            } else {
                AppAccessDBInfo newInfo = new AppAccessDBInfo();
                Long newDate = System.currentTimeMillis();

                newInfo.setDeviceAppId(AppDefs.DeviceAppIdentity.YY.toString());
                newInfo.setLoginDate(newDate);
                newInfo.setLastOperateDate(newDate);
                db.save(newInfo);
            }
        } else {
            AppAccessDBInfo newInfo = new AppAccessDBInfo();
            Long newDate = System.currentTimeMillis();

            newInfo.setDeviceAppId(AppDefs.DeviceAppIdentity.YY.toString());
            newInfo.setLoginDate(newDate);
            newInfo.setLastOperateDate(newDate);
            db.save(newInfo);
        }
        //ID:837499 19-01-09 终端统计逻辑变更，半小时没有操作，不计入使用时长 王泉 结束
    }
}
