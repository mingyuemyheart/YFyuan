package com.moft.xfapply.activity.bussiness;

import android.app.ProgressDialog;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.moft.xfapply.app.Constant;
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.event.CommandEvent;
import com.moft.xfapply.logic.network.OkHttpClientManager;
import com.moft.xfapply.model.common.DeviceAccessParam;
import com.moft.xfapply.model.common.Dictionary;
import com.moft.xfapply.model.entity.AppAccessDBInfo;
import com.moft.xfapply.model.entity.MapSearchInfo;
import com.moft.xfapply.model.entity.OfflineDBInfo;
import com.moft.xfapply.model.entity.SysDepartmentDBInfo;
import com.moft.xfapply.model.entity.SysDictionaryDBInfo;
import com.moft.xfapply.model.entity.SysDistrictDBInfo;
import com.moft.xfapply.model.external.AppDefs;
import com.moft.xfapply.model.external.dto.OfflineDbDTO;
import com.moft.xfapply.model.external.dto.SysDictionaryDTO;
import com.moft.xfapply.model.external.result.ArrayRestResult;
import com.moft.xfapply.model.external.result.SimpleRestResult;
import com.moft.xfapply.task.UpdateDictionaryAsyncTask;
import com.moft.xfapply.utils.DateUtil;
import com.moft.xfapply.utils.DbUtil;
import com.moft.xfapply.utils.GsonUtil;
import com.moft.xfapply.utils.LogUtils;
import com.moft.xfapply.utils.StorageUtil;
import com.moft.xfapply.utils.StringUtil;
import com.moft.xfapply.utils.TelManagerUtil;

import net.tsz.afinal.FinalDb;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

/**
 * Created by wangquan on 2017/4/20.
 */

public class CommonBussiness {
    private static CommonBussiness instance = null;
    private Context context = null;
    private ProgressDialog dialog = null;

    private CommonBussiness() {
        context = LvApplication.getContext();
        dialog = new ProgressDialog(context);
    }

    public static CommonBussiness getInstance() {
        if (instance == null) {
            instance = new CommonBussiness();
        }
        return instance;
    }

    public void loadToCommonDB(String city) {
        DbUtil.getInstance().syncDB(city, StorageUtil.getDbPath(), StorageUtil.getTempPath(), city);
        LvApplication.getInstance().clearOrgDicMap();
    }

    public Map<String, List<Dictionary>> getRootDicCompMap() {
        Map<String, List<Dictionary>> retMap = getDicCompMap(0);

        return retMap;
    }

    private Map<String, List<Dictionary>> getDicCompMap(Integer depth) {
        Map<String, List<Dictionary>> retMap = new HashMap<>();
        FinalDb commonDb = DbUtil.getInstance().getDB(Constant.DB_NAME_COMMON);
        String condition = String.format(Constant.SEARCH_COND_DEPTH, depth);

        List<SysDictionaryDBInfo> list = commonDb.findAllByWhere(SysDictionaryDBInfo.class, condition);

        for (SysDictionaryDBInfo item : list) {
            String key = item.getConfigUuid();
            if (retMap.containsKey(key)) {
                List<Dictionary> subList = retMap.get(key);
                subList.add(new Dictionary(item.getCode(), item.getName(), item.getUuid(), item.getParentUuid()));
            } else {
                List<Dictionary> subList = new ArrayList<Dictionary>();
                subList.add(new Dictionary(item.getCode(), item.getName(), item.getUuid(), item.getParentUuid()));
                //subList.add(new Dictionary("自定义", "-1"));
                retMap.put(key, subList);
            }
        }

        return retMap;
    }

    private Map<String, List<Dictionary>> getDicCompMap(Integer depth, String typeUuid) {
        Map<String, List<Dictionary>> retMap = new HashMap<>();
        FinalDb commonDb = DbUtil.getInstance().getDB(Constant.DB_NAME_COMMON);
        String condition = String.format("%s and %s", String.format(Constant.SEARCH_COND_DEPTH, depth), String.format(Constant.SEARCH_COND_PARENT_UUID, typeUuid));

        List<SysDictionaryDBInfo> list = commonDb.findAllByWhere(SysDictionaryDBInfo.class, condition);

        for (SysDictionaryDBInfo item : list) {
            String key = item.getParentUuid();
            if (retMap.containsKey(key)) {
                List<Dictionary> subList = retMap.get(key);
                subList.add(new Dictionary(item.getCode(), item.getName(), item.getUuid(), item.getParentUuid()));
            } else {
                List<Dictionary> subList = new ArrayList<Dictionary>();
                subList.add(new Dictionary(item.getCode(), item.getName(), item.getUuid(), item.getParentUuid()));
                //subList.add(new Dictionary("自定义", "-1"));
                retMap.put(key, subList);
            }
        }
        return retMap;
    }

    private List<Dictionary> getDicCompList(Integer depth, String configUuid, String typeUuid) {
        List<Dictionary> retList = new ArrayList<>();
        FinalDb commonDb = DbUtil.getInstance().getDB(Constant.DB_NAME_COMMON);
        String condition = String.format("%s and %s", String.format(Constant.SEARCH_COND_DEPTH, depth), String.format(Constant.SEARCH_COND_CONFIG_UUID, configUuid));

        List<SysDictionaryDBInfo> list = commonDb.findAllByWhere(SysDictionaryDBInfo.class, condition);

        if(list != null && list.size() > 0) {
            for (SysDictionaryDBInfo item : list) {
                if(!StringUtil.isEmpty(typeUuid)) {
                    if(typeUuid.equals(item.getParentUuid())) {
                        retList.add(new Dictionary(item.getCode(), item.getName(), item.getUuid(), item.getParentUuid()));
                    }
                } else {
                    if(StringUtil.isEmpty(item.getParentUuid())) {
                        retList.add(new Dictionary(item.getCode(), item.getName(), item.getUuid(), item.getParentUuid()));
                    }
                }
            }
        }
        return retList;
    }

    public void setSubDicCompMap(Map<String, List<Dictionary>> dicMap) {
        FinalDb commonDb = DbUtil.getInstance().getDB(Constant.DB_NAME_COMMON);
        String condition = String.format(Constant.SEARCH_COND_DEPTH_NOT_EQUALS, 0);

        List<SysDictionaryDBInfo> subList = commonDb.findAllByWhere(SysDictionaryDBInfo.class, condition);

        for (Map.Entry<String, List<Dictionary>> item : dicMap.entrySet()) {
            setSubDicCompMap(item.getValue(), subList);
        }
        //做数据字典的适配，转换成APP需要的数据字典
        convertToAppDicMap(dicMap);
    }

    public List<Dictionary> getRegionListByUser(String userDistrictCode) {
        List<Dictionary> retList = new ArrayList<>();
        FinalDb commonDb = DbUtil.getInstance().getDB(Constant.DB_NAME_COMMON);

        String condition = String.format(Constant.SEARCH_COND_USER_DISTRICT, userDistrictCode);
        List<SysDistrictDBInfo> list = commonDb.findAllByWhere(SysDistrictDBInfo.class, condition);

        if(list != null && list.size() > 0) {
            for (SysDistrictDBInfo item : list) {
                if (userDistrictCode.equals(item.getCode())) {
                    Dictionary rootDic = new Dictionary(item.getName(), item.getCode(), item.getParentCode());
                    rootDic.setSubDictionary(getChildDistrictList(item.getCode(), list));
                    retList.add(rootDic);
                }
            }
        }

        return retList;
    }

    private List<Dictionary> getChildDistrictList(String code, List<SysDistrictDBInfo> districtList) {
        List<Dictionary> list = new ArrayList<>();
        if(!StringUtil.isEmpty(code)) {
            for (SysDistrictDBInfo item : districtList) {
                if (code.equals(item.getParentCode())) {
                    Dictionary dic = new Dictionary(item.getName(), item.getCode(), item.getParentCode());
                    dic.setSubDictionary(getChildDistrictList(item.getCode(), districtList));
                    list.add(dic);
                }
            }
        }
        return list;
    }

    public List<String> getCityListByUser(String userDistrictCode) {
        List<String> retList = new ArrayList<>();
        FinalDb commonDb = DbUtil.getInstance().getDB(Constant.DB_NAME_COMMON);

        String condition = String.format(Constant.SEARCH_COND_USER_CITY, userDistrictCode);
        List<SysDistrictDBInfo> list = commonDb.findAllByWhere(SysDistrictDBInfo.class, condition);

        if(list != null && list.size() > 0) {
            for (SysDistrictDBInfo item : list) {
                retList.add(item.getName());
            }
        }

        return retList;
    }

    //支持省离线数据库查询
    public String getDistrictNameByCode(String code) {
        String districtName = "";
        FinalDb commonDb = DbUtil.getInstance().getDB(Constant.DB_NAME_COMMON);

        String condition = String.format(Constant.SEARCH_COND_CODE, code);
        List<SysDistrictDBInfo> list = commonDb.findAllByWhere(SysDistrictDBInfo.class, condition);
        //支持省离线数据库查询
        if(list != null && list.size() > 0) {
            districtName = list.get(0).getName();
        }
        return districtName;
    }

//    public void createAppAccessRecord() {
//        FinalDb commonDb = DbUtil.getInstance().getDB2(Constant.DB_NAME_COMMON);
//        AppAccessDBInfo info = new AppAccessDBInfo();
//        Long newDate = System.currentTimeMillis();
//
//        info.setDeviceAppId(AppDefs.DeviceAppIdentity.YY.toString());
//        info.setLoginDate(newDate);
//        info.setLastOperateDate(newDate);
//        commonDb.save(info);
//        uploadAppAccessRecord();
//    }

    //ID:837499 19-01-09 终端统计逻辑变更，半小时没有操作，不计入使用时长 王泉
    public void updateCurrentAccessRecordStatus(boolean commit) {
        FinalDb commonDb = DbUtil.getInstance().getDB2(Constant.DB_NAME_COMMON);

        String condition = String.format("device_app_id = '%s'", AppDefs.DeviceAppIdentity.YY.toString());
        List<AppAccessDBInfo> list = commonDb.findAllByWhere(AppAccessDBInfo.class, condition, "login_date desc limit 1");

        if (list != null && list.size() > 0) {
            AppAccessDBInfo info = list.get(0);
            info.setIsCommit(commit);
            commonDb.update(info);
        }
    }

    public void uploadAppAccessRecord() {
        FinalDb commonDb = DbUtil.getInstance().getDB2(Constant.DB_NAME_COMMON);
        String condition = String.format("device_app_id = '%s'", AppDefs.DeviceAppIdentity.YY.toString());
        List<AppAccessDBInfo> list = commonDb.findAllByWhere(AppAccessDBInfo.class, condition, "login_date asc");

        if(list != null && list.size() > 0) {
            for(int index = 0; index < list.size(); ++index) {
                if(index == list.size() - 1) {
                    //上传应用访问状态
                    //ID:837499 19-01-09 终端统计逻辑变更，半小时没有操作，不计入使用时长 王泉
                    if(list.get(index).getIsCommit() != null && list.get(index).getIsCommit()) {
                        submitAppAccessRecord(list.get(index), true);
                    } else {
                        submitAppAccessRecord(list.get(index), false);
                    }
                } else {
                    //上传应用访问状态，删除本地记录
                    submitAppAccessRecord(list.get(index), true);
                }
                //延迟网络请求1秒
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
        }
    }

    public void submitAppAccessRecord(final AppAccessDBInfo info, final boolean isDel) {
        OkHttpClientManager http = OkHttpClientManager.getInstance();
        final Gson gson = GsonUtil.create();
        final FinalDb commonDb = DbUtil.getInstance().getDB2(Constant.DB_NAME_COMMON);
        DeviceAccessParam accessParam = new DeviceAccessParam();
        accessParam.setIdentity(TelManagerUtil.getInstance().getDeviceId());
        accessParam.setAppIdentity(info.getDeviceAppId());
        accessParam.setLoginDate(DateUtil.format(info.getLoginDate()));
        accessParam.setLastOperateDate(DateUtil.format(info.getLastOperateDate()));
        http.postAsyn(http.convertToURL(Constant.METHOD_APP_ACCESS_SUBMIT), new OkHttpClientManager.ResultCallback<SimpleRestResult>() {
            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(SimpleRestResult result) {
                if (result != null && result.isSuccess()) {
                    if(isDel) {
                        try {
                            commonDb.delete(info);
                        } catch (Exception e) {
                            LogUtils.d(String.format("submitAppAccessRecord exception = %s", e.getMessage()));
                        }
                    }
                }
            }
        }, gson.toJson(accessParam));
        LogUtils.d("submitAppAccessRecord params=" + gson.toJson(accessParam));
    }

    public List<Dictionary> getOrgMap(String dbKey) {
        List<Dictionary> retList = new ArrayList<>();
        FinalDb commonDb = DbUtil.getInstance().getDB(dbKey);
        if(commonDb != null) {
            List<SysDepartmentDBInfo> list = commonDb.findAllByWhere(SysDepartmentDBInfo.class, Constant.SEARCH_COND_NOT_DELETED, "code asc");
            if(list != null) {
                for (SysDepartmentDBInfo item : list) {
                    if (item.getDeleted() == null || !item.getDeleted()) {
                        retList.add(new Dictionary(item.getCode(), item.getName(), item.getUuid(), item.getParentUuid()));
                    }
                }
            }
        }

        return retList;
    }

    public List<Dictionary> getDetachmentOrgMap(String dbKey) {
        List<Dictionary> retList = new ArrayList<>();
        FinalDb commonDb = DbUtil.getInstance().getDB(dbKey);
        if(commonDb != null) {
            List<SysDepartmentDBInfo> list = commonDb.findAllByWhere(SysDepartmentDBInfo.class, String.format("%s and %s", String.format(Constant.SEARCH_COND_GRADE, 1), Constant.SEARCH_COND_NOT_DELETED), "code asc");
            for (SysDepartmentDBInfo item : list) {
                if(item.getDeleted() == null || !item.getDeleted()) {
                    retList.add(new Dictionary(item.getCode(), item.getName(), item.getUuid(), item.getParentUuid()));
                }
            }
        }

        return retList;
    }

    public List<Dictionary> getChildOrgList(String parentUuid) {
        List<Dictionary> retList = new ArrayList<>();
        FinalDb commonDb = DbUtil.getInstance().getDB(LvApplication.getInstance().getCityName());
        if(commonDb != null) {
            List<SysDepartmentDBInfo> list = commonDb.findAllByWhere(SysDepartmentDBInfo.class, String.format("%s and %s", String.format(Constant.SEARCH_COND_PARENT_UUID, parentUuid), Constant.SEARCH_COND_NOT_DELETED), "code asc");
            for (SysDepartmentDBInfo item : list) {
                if(item.getDeleted() == null || !item.getDeleted()) {
                    retList.add(new Dictionary(item.getCode(), item.getName(), item.getUuid(), item.getParentUuid()));
                }
            }
        }

        return retList;
    }

    public List<Dictionary> getChildOrgListByCode(String code) {
        List<Dictionary> retList = new ArrayList<>();
        FinalDb commonDb = DbUtil.getInstance().getDB(LvApplication.getInstance().getCityName());
        if(commonDb != null) {
            List<SysDepartmentDBInfo> list = commonDb.findAllByWhere(SysDepartmentDBInfo.class, String.format("%s and %s", String.format(Constant.SEARCH_COND_CODE, code), Constant.SEARCH_COND_NOT_DELETED));
            if(list != null && list.size() > 0) {
                SysDepartmentDBInfo info = list.get(0);
                list = commonDb.findAllByWhere(SysDepartmentDBInfo.class, String.format("%s and %s", String.format(Constant.SEARCH_COND_PARENT_UUID, info.getUuid()), Constant.SEARCH_COND_NOT_DELETED), "code asc");
                for (SysDepartmentDBInfo item : list) {
                    if(item.getDeleted() == null || !item.getDeleted()) {
                        retList.add(new Dictionary(item.getCode(), item.getName(), item.getUuid(), item.getParentUuid()));
                    }
                }
            }

        }

        return retList;
    }

    public void requestGetDictionaryData(final OnUpdateDictionaryListener listener) {
        OkHttpClientManager http = OkHttpClientManager.getInstance();
        http.getAsyn(http.convertToURL(Constant.METHOD_GET_SYS_DICTIONARY), new OkHttpClientManager.ResultCallback<ArrayRestResult>() {
            @Override
            public void onError(Request request, Exception e) {
                if(listener != null) {
                    listener.onUpdateComplete();
                }
            }

            @Override
            public void onResponse(ArrayRestResult result) {
                if (result != null && result.isSuccess()) {
                    Gson gson = GsonUtil.create();
                    String jsonStr = gson.toJson(result.getContents());

                    Type listType = new TypeToken<List<SysDictionaryDTO>>(){}.getType();
                    List<SysDictionaryDTO> dicCompList = gson.fromJson(jsonStr, listType);
                    UpdateDictionaryAsyncTask task = new UpdateDictionaryAsyncTask(new UpdateDictionaryAsyncTask.OnUpdateDictionaryListener() {
                        @Override
                        public void onPreExecute() {

                        }

                        @Override
                        public void onPostExecute() {
                            if(listener != null) {
                                listener.onUpdateComplete();
                            }
                        }
                    });
                    task.execute(dicCompList);
                } else {
                    if(listener != null) {
                        listener.onUpdateComplete();
                    }
                }
            }
        });
    }

    private void convertToAppDicMap(Map<String, List<Dictionary>> dicMap) {
        List<Dictionary> equipmentDicList = new ArrayList<>();

        addDicList(equipmentDicList, "消防人员防护装备", "FHZB", dicMap.remove("FHZB"));
        addDicList(equipmentDicList, "灭火器材装备", "MHQCZB", dicMap.remove("MHQCZB"));
        addDicList(equipmentDicList, "抢险救援器材", "QXJYQC", dicMap.remove("QXJYQC"));
        addDicList(equipmentDicList, "消防通讯指挥装备", "TXZH", dicMap.remove("TXZH"));
        addDicList(equipmentDicList, "特种消防装备", "TZXFZB", dicMap.remove("TZXFZB"));
        addDicList(equipmentDicList, "防火检查与火灾调查装备", "FHJCYHZDC", dicMap.remove("FHJCYHZDC"));
        addDicList(equipmentDicList, "其他类消防装备器材", "QTLZBQC", dicMap.remove("QTLZBQC"));

        dicMap.put("ZBQC_EX", equipmentDicList);
    }

    private void addDicList(List<Dictionary> dicList, String dicName, String dicCode, List<Dictionary> addList) {
        if(addList != null) {
            Dictionary dictionary = new Dictionary(dicName, dicCode);
            dictionary.setSubDictionary(addList);
            dicList.add(dictionary);
        }
    }

    public List<MapSearchInfo> getMapSearchInfoList() {
        FinalDb commonDb = DbUtil.getInstance().getDB(Constant.DB_NAME_COMMON);
        List<MapSearchInfo> list = commonDb.findAllByWhere(MapSearchInfo.class, "", "id desc");
        return list;
    }

    public void updateMapSearchInfo(MapSearchInfo msi) {
        FinalDb commonDb = DbUtil.getInstance().getDB(Constant.DB_NAME_COMMON);

        String strWhere = String.format("name = '%s' and type = '%s'",
                StringUtil.handleTransferChar(msi.getName()), msi.getType());
        List<MapSearchInfo> list = commonDb.findAllByWhere(MapSearchInfo.class, strWhere);
        if (list == null || list.isEmpty()) {
            commonDb.save(msi);
        } else {
            msi.setId(list.get(0).getId());
            commonDb.update(msi);
        }
    }

    public void deleteMapSearchInfoAll() {
        FinalDb commonDb = DbUtil.getInstance().getDB(Constant.DB_NAME_COMMON);
        commonDb.deleteByWhere(MapSearchInfo.class, "1=1");
    }

    private void setSubDicCompMap(List<Dictionary> dicList, List<SysDictionaryDBInfo> subList) {
        if(dicList != null && dicList.size() > 0) {
            for (Dictionary item : dicList) {
                List<Dictionary> tempList = findSubDicByUuid(item.getCode(), subList);
                if(tempList != null && tempList.size() > 0) {
                    item.setSubDictionary(tempList);
                    setSubDicCompMap(tempList, subList);
                }
            }
        }
    }

    private List<Dictionary> findSubDicByUuid(String uuid, List<SysDictionaryDBInfo> subList) {
        List<Dictionary> retList = new ArrayList<>();
        if(StringUtil.isEmpty(uuid)) {
            return retList;
        }

        if(subList != null && subList.size() > 0) {
            for (SysDictionaryDBInfo item : subList) {
                if(uuid.equals(item.getParentUuid())) {
                    retList.add(new Dictionary(item.getCode(), item.getName(), item.getUuid(), item.getParentUuid()));
                }
            }
        }
        return retList;
    }

    private String convertDBName(String dbName) {
        if(!StringUtil.isEmpty(dbName)) {
            dbName = dbName.replace(".db", ".zip");
        }
        return dbName;
    }

    public interface OnUpdateDBListener{
        void onUpdateComplete();
    }

    // 900379 【应用终端】中大队 添加 “市本级/外区县”字段，其显示的内容与关联的大队一致 smy 2019-03-04
    public SysDepartmentDBInfo getSysDepartmentByCode(String dbKey, String code) {
        FinalDb commonDb = DbUtil.getInstance().getDB(dbKey);
        List<SysDepartmentDBInfo> dbInfos = new ArrayList<>();
        if(commonDb != null) {
            dbInfos = commonDb.findAllByWhere(SysDepartmentDBInfo.class, String.format(Constant.SEARCH_UUID_DELETED_FALSE, code));
        }
        if (dbInfos != null && dbInfos.size() == 1) {
            return dbInfos.get(0);
        }
        return null;
    }

    public interface OnUpdateDictionaryListener{
        void onUpdateComplete();
    }
}
