package com.moft.xfapply.activity.bussiness;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.moft.xfapply.activity.ExceptionReportActivity;
import com.moft.xfapply.app.Constant;
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.model.common.Attribute;
import com.moft.xfapply.model.common.ExceptionCategory;
import com.moft.xfapply.model.common.Table;
import com.moft.xfapply.model.common.UpgradeDBInfo;
import com.moft.xfapply.task.DBUpgradeAsyncTask;
import com.moft.xfapply.utils.DateUtil;
import com.moft.xfapply.utils.DbUtil;
import com.moft.xfapply.utils.FileUtil;
import com.moft.xfapply.utils.GsonUtil;
import com.moft.xfapply.utils.StorageUtil;
import com.moft.xfapply.utils.StringUtil;
import com.moft.xfapply.utils.SystemUtil;
import com.moft.xfapply.utils.Utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangquan on 2017/4/20.
 */
public class DBUpdateBussiness {
    private static DBUpdateBussiness instance = null;
    private final static String VERSION_FILE = "version.txt";
    private final static String UPGRADE_DB_FILE = "upgrade_db.json";
    private Context context = null;

    private final String QUERY_TABLE_SQL = "SELECT tbl_name, sql from sqlite_master WHERE type = 'table'";
    private final String QUERY_COLUMN_SQL = "PRAGMA table_info('%s')";
    private final String DROP_TABLE_SQL = "DROP TABLE %s";
    private final String ADD_COLUMN_SQL = "ALTER TABLE '%s' ADD '%s'%s";
    private final String INSERT_COPY_TABLE_SQL = "INSERT INTO %s SELECT %s FROM %s";
    private final String RENAME_TABLE_SQL = "ALTER TABLE %s RENAME TO %s";

    private DBUpdateBussiness() {
        context = LvApplication.getContext();
    }

    public static DBUpdateBussiness getInstance() {
        if (instance == null) {
            instance = new DBUpdateBussiness();
        }
        return instance;
    }

    public void checkUpgradeDB(OnDBUpdateListener listener) {
        int verCode = getDBVersionCode();
        List<UpgradeDBInfo> upgradeDBInfoList = getUpgradeDBInfos();
        int baseLineVer = getBaseLineVersion(upgradeDBInfoList);
        if(verCode < 0 || baseLineVer < 0 || verCode < baseLineVer) {
            clearLowVersionDB(verCode);
            setDBVersionCode(SystemUtil.getVersionCode());
            if(listener != null) {
                listener.onDBUpdateFinish();
            }
        } else if(verCode >= SystemUtil.getVersionCode()) {
            if(listener != null) {
                listener.onDBUpdateFinish();
            }
        } else {
            upgradeDB(verCode, SystemUtil.getVersionCode(), upgradeDBInfoList, listener);
        }
    }

    public void upgradeDB(int dbVersion, int curVersion, List<UpgradeDBInfo> upgradeDBInfoList, final OnDBUpdateListener listener) {
        for (UpgradeDBInfo item : upgradeDBInfoList) {
            List<UpgradeDBInfo.UpgradeCommand> upgradeCommands = item.getUpgradeCommand();
            if (upgradeCommands != null && upgradeCommands.size() > 0) {
                List<UpgradeDBInfo.UpgradeCommand> delCommand = new ArrayList<>();
                for (UpgradeDBInfo.UpgradeCommand command : upgradeCommands) {
                    if (!(command.getVerNo() > dbVersion && command.getVerNo() <= curVersion)) {
                        delCommand.add(command);
                    }
                }
                if (delCommand.size() > 0) {
                    for (UpgradeDBInfo.UpgradeCommand command : delCommand) {
                        upgradeCommands.remove(command);
                    }
                }
            }
        }
        DBUpgradeAsyncTask task = new DBUpgradeAsyncTask(new DBUpgradeAsyncTask.OnDBUpgradeListener() {
            @Override
            public void onPreExecute() {
                if (listener != null) {
                    listener.onDBUpdateInit();
                }
            }

            @Override
            public void onProgressUpdate(int value) {
                if (listener != null) {
                    listener.onDBUpdateProgress(value);
                }
            }

            @Override
            public void onPostExecute() {
                setDBVersionCode(SystemUtil.getVersionCode());
                if (listener != null) {
                    listener.onDBUpdateFinish();
                }
            }

            @Override
            public void onPostException(List<ExceptionCategory> list) {
                if (listener != null) {
                    listener.onDBUpdateFailed(list);
                }
            }
        });
        task.execute(upgradeDBInfoList, dbVersion);

    }

    public void clearLowVersionDB() {
        int verCode = getDBVersionCode();
        clearLowVersionDB(verCode);
        setDBVersionCode(SystemUtil.getVersionCode());
    }

    private List<UpgradeDBInfo> getUpgradeDBInfos() {
        List<UpgradeDBInfo> list = null;
        String jsonStr = getAssetsJson(UPGRADE_DB_FILE);
        try {
            Gson gson = GsonUtil.create();
            Type listType = new TypeToken<List<UpgradeDBInfo>>() {
            }.getType();
            list = gson.fromJson(jsonStr, listType);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private int getBaseLineVersion(List<UpgradeDBInfo> upgradeDBInfoList) {
        int version = -1;
        if(upgradeDBInfoList != null && upgradeDBInfoList.size() > 0) {
            for(UpgradeDBInfo item : upgradeDBInfoList) {
                if(version >= 0) {
                    if(version > item.getBaseLine()) {
                        version = item.getBaseLine();
                    }
                } else {
                    version = item.getBaseLine();
                }
            }
        }
        return version;
    }

    public int getDBVersionCode() {
        int verCode = -1;
        String homePath = StorageUtil.getHomePath();
        if(!StringUtil.isEmpty(homePath)) {
            String pathFile = homePath + VERSION_FILE;
            try {
                FileReader reader = new FileReader(pathFile);
                BufferedReader br = new BufferedReader(reader);
                String line = br.readLine();
                verCode = Utils.ToInteger(line.replace("version: ", ""));
                br.close();
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            verCode = -999;
        }

        return verCode;
    }

    private void setDBVersionCode(int verCode) {
        String pathFile = StorageUtil.getHomePath() + VERSION_FILE;

        try {
            if (!FileUtil.isFileExists(pathFile)) {
                FileUtil.createFile(pathFile);
            }
            FileWriter writer = new FileWriter(pathFile, false);
            BufferedWriter out = new BufferedWriter(writer);
            out.write(String.format("version: %s", StringUtil.get(verCode)));
            out.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getAssetsJson(String fileName){
        StringBuilder stringBuilder = new StringBuilder("");
        //获得assets资源管理器
        AssetManager assetManager = context.getAssets();
        //使用IO流读取json文件内容
        try {
            InputStream inputStream = assetManager.open(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public void checkUpgradeDB(SQLiteDatabase oldRawDB, SQLiteDatabase newRawDB) {
        System.out.println("1/开始:" + System.currentTimeMillis());
        List<Table> oldTables = getAllTableByDBPath(oldRawDB);
        List<Table> newTabls = getAllTableByDBPath(newRawDB);
        System.out.println("2/获得所有表即其属性:" + System.currentTimeMillis());
        if (oldTables == null || oldTables.isEmpty() || newTabls == null || newTabls.isEmpty()) {
            return;
        }

        List<String> sqls = getDiffSql(oldTables, newTabls);
        System.out.println("3/解析sql:" + System.currentTimeMillis());
        if (sqls != null && !sqls.isEmpty()) {
            try {
                oldRawDB.beginTransaction();
                for (String sql : sqls) {
                    if (StringUtil.isEmpty(sql)) {
                        continue;
                    }
                    sql = sql.replaceAll("\\r\\n", "");
                    oldRawDB.execSQL(sql);
                }
                oldRawDB.setTransactionSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                oldRawDB.endTransaction();
            }
        }
        System.out.println("4/执行sql:" + System.currentTimeMillis());
    }

    private List<Table> getAllTableByDBPath(SQLiteDatabase rawDB) {
        List<Table> tables = new ArrayList<>();
        if (rawDB != null) {
            Cursor tableCursor = rawDB.rawQuery(QUERY_TABLE_SQL, null);
            if (tableCursor != null && tableCursor.getCount() > 0) {
                while (tableCursor.moveToNext()) {

                    String tblName = tableCursor.getString(tableCursor.getColumnIndex("tbl_name"));
                    String tblSql = tableCursor.getString(tableCursor.getColumnIndex("sql"));
                    if (StringUtil.isEmpty(tblName) || StringUtil.isEmpty(tblSql)) {
                        continue;
                    }
                    Table table = new Table(tblName, tblSql);

                    Cursor attrCursor = rawDB.rawQuery(String.format(QUERY_COLUMN_SQL, tblName), null);
                    if (attrCursor == null) {
                        continue;
                    }
                    while (attrCursor.moveToNext()) {
                        int cid = attrCursor.getInt(attrCursor.getColumnIndex("cid"));
                        String name = attrCursor.getString(attrCursor.getColumnIndex("name"));
                        String type = attrCursor.getString(attrCursor.getColumnIndex("type"));
                        if (StringUtil.isEmpty(name) || StringUtil.isEmpty(type)) {
                            continue;
                        }
                        table.getAttributes().add(new Attribute(cid, name ,type));
                    }
                    if (table.getAttributes().isEmpty()) {
                        continue;
                    }
                    tables.add(table);
                }

                tableCursor.close();
            }
        }
        return tables;
    }


    private List<String> getDiffSql(List<Table> oldTables, List<Table> newTables) {
        List<String> sqls = new ArrayList<>();

        if (oldTables == null || oldTables.isEmpty() || newTables == null || newTables.isEmpty()) {
            return sqls;
        }
        List<Table> oldTablesTmp = new ArrayList<>(oldTables);

        for (Table newTable : newTables) {
            boolean isExist = false;
            for (Table oldTable : oldTables) {
                if (newTable.getName().equals(oldTable.getName())) {

                    sqls.addAll(getDiffColumnSql(oldTable, newTable));

                    oldTablesTmp.remove(oldTable);

                    isExist = true;
                    break;
                }
            }
            if (!isExist) {
                sqls.add(newTable.getSql());
            }
        }

        if (oldTablesTmp != null && !oldTablesTmp.isEmpty()) {
            for (Table tableTmp : oldTablesTmp) {
                if (tableTmp != null && !StringUtil.isEmpty(tableTmp.getName())) {

                    sqls.add(String.format(DROP_TABLE_SQL, tableTmp.getName()));
                }
            }
        }
        return sqls;
    }

    private List<String> getDiffColumnSql(Table oldTable, Table newTable) {
        List<String> sqls = new ArrayList<>();
        List<String> addSql = new ArrayList<>();
        List<String> alterSql = new ArrayList<>();

        if (newTable == null || newTable.getAttributes() == null
                || newTable.getAttributes().isEmpty() || oldTable == null
                || oldTable.getAttributes() == null || oldTable.getAttributes().isEmpty()) {
            return sqls;
        }

        String tableName = oldTable.getName();
        String tableNameTmp = tableName + "_temp";

        List<Attribute> oldAttributes = oldTable.getAttributes();
        List<Attribute> newAttributes = newTable.getAttributes();

        List<Attribute> oldAttributesTmp = new ArrayList<>(oldAttributes);
        List<Attribute> alterAttributes = new ArrayList<>();

        for (Attribute newAttribute : newAttributes) {
            if (newAttribute == null) {
                continue;
            }
            boolean isExsit = false;
            for (Attribute oldAttribute : oldAttributes) {
                if (oldAttribute != null && newAttribute.getName().equals(oldAttribute.getName())) {
                    isExsit = true;

                    if (!newAttribute.getType().equals(oldAttribute.getType())) {

                        alterAttributes.add(newAttribute);
                    }
                    oldAttributesTmp.remove(oldAttribute);
                    break;
                }
            }
            if (!isExsit) {

                addSql.add(String.format(ADD_COLUMN_SQL, tableName, newAttribute.getName(), newAttribute.getType()));
            }
        }

        if (!oldAttributesTmp.isEmpty() || !alterAttributes.isEmpty()) {

            alterSql.add(String.format(RENAME_TABLE_SQL, tableName, tableNameTmp));

            alterSql.add(newTable.getSql());

            StringBuilder sb = new StringBuilder();
            for (Attribute newAttribute : newAttributes) {
                if (newAttribute == null) {
                    continue;
                }
                boolean isExsit = false;
                for (Attribute oldAttribute : oldAttributes) {
                    if (oldAttribute == null) {
                        continue;
                    }
                    if (oldAttribute.getName().equals(newAttribute.getName())) {
                        isExsit = true;
                        break;
                    }
                }
                if (isExsit) {
                    sb.append(newAttribute.getName()).append(",");
                } else {
                    sb.append("NULL").append(",");
                }
            }
            String columnStr = sb.toString();
            if (StringUtil.isEmpty(columnStr)) {
                alterSql.clear();
            }
            alterSql.add(String.format(INSERT_COPY_TABLE_SQL, tableName, columnStr.substring(0, columnStr.length() - 1), tableNameTmp));

            alterSql.add(String.format(DROP_TABLE_SQL, tableNameTmp));
        }
        if (alterSql != null && !alterSql.isEmpty()) {
            sqls.addAll(alterSql);
        } else {
            sqls.addAll(addSql);
        }

        return sqls;
    }

    public boolean versionIsLow() {
        boolean ret = false;
        SQLiteDatabase rawDB = DbUtil.getInstance().getRawDB(Constant.DB_NAME_COMMON);
        if(rawDB != null) {
            if (rawDB.getVersion() > SystemUtil.getVersionCode()) {
                ret = true;
            }
            DbUtil.getInstance().closeRawDB(rawDB);
        }
        return ret;
    }

    private void clearLowVersionDB(int verCode) {
        String dataPath = StorageUtil.getHomePath().substring(0, StorageUtil.getHomePath().length() - 1);
        if(FileUtil.isFileExists(dataPath)) {
            FileUtil.renameFile(dataPath, dataPath + "_bak_" + String.format("v%d_", verCode) + DateUtil.getCurrentTime2());
        }
    }

    public interface OnDBUpdateListener {
        void onDBUpdateInit();
        void onDBUpdateProgress(int value);
        void onDBUpdateFinish();
        void onDBUpdateFailed(List<ExceptionCategory> exception);
    }
}
