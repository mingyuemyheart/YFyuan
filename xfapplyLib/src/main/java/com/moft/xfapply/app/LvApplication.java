package com.moft.xfapply.app;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import androidx.core.app.ActivityCompat;
import androidx.multidex.MultiDex;

import com.baidu.lbsapi.BMapManager;
import com.baidu.lbsapi.MKGeneralListener;
import com.baidu.mapapi.SDKInitializer;
import com.moft.xfapply.R;
import com.moft.xfapply.activity.bussiness.CommonBussiness;
import com.moft.xfapply.logic.setting.PrefUserInfo;
import com.moft.xfapply.model.common.Dictionary;
import com.moft.xfapply.model.common.QueryCondition;
import com.moft.xfapply.model.external.AppDefs;
import com.moft.xfapply.push.PushService;
import com.moft.xfapply.utils.DbUtil;
import com.moft.xfapply.utils.LogUtils;
import com.moft.xfapply.utils.StorageUtil;
import com.moft.xfapply.utils.SystemUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LvApplication extends Application {

	public static Context applicationContext;
	private static LvApplication instance;
    public BMapManager mBMapManager = null;

    private boolean isUpdateDataConfirm;
    private boolean isDoUpdateData;

    private QueryCondition mapQueryCondition = new QueryCondition();
    private QueryCondition dataQueryCondition = new QueryCondition();
    private QueryCondition mapDisasterQueryCondition = new QueryCondition();

    private String mCityName = "";
    private Dictionary curCityDic;
    private List<Dictionary> curRegionDicList;
    private List<String> cityList;
    
    // 数据字典
    private Map<String, List<Dictionary>> orgDicMap;
    private Map<String, List<Dictionary>> compDicMap;
    private List<Dictionary> eleTypeDicList;
    private List<Dictionary> eleTypeWsDicList;
    private List<Dictionary> eleTypeZddwDicList;
    private List<Dictionary> eleTypeGeoDicList;
    private List<Dictionary> hasOrNohasDicList;
    private List<Dictionary> whetherDicList;
    private List<Dictionary> usableDicList;
    private List<Dictionary> keyUnitStructureDicList;
    private Map<String, Integer> resIconMap;
    private Map<String, Integer> geoEleWeightMap = null;
    
    private int X;
    private int Y;
    private int DPI;
    private int screenWidthPixels;
    private int screenHeightPixels;
    private String curLat = "";
    private String curLng = "";
    private boolean isDownloadedOfflineDB = false; // 该变量表示离线包需继续下载，true的情况下，不一定会弹出dialog提示，只是dialog提出的必要条件而非充分条件

    @SuppressWarnings("deprecation")
    @Override
	public void onCreate() {
		super.onCreate();
//        MultiDex.install(this);

        if (instance != null) {
            return;
        }

        applicationContext = this;
        instance = this;

        WindowManager wm = (WindowManager) applicationContext.getSystemService(Context.WINDOW_SERVICE);

        DisplayMetrics dm = new DisplayMetrics();
        dm = getResources().getDisplayMetrics();  
        setX(wm.getDefaultDisplay().getWidth());
        setY(wm.getDefaultDisplay().getHeight());
        setDPI(dm.densityDpi);

        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        screenWidthPixels = outMetrics.widthPixels;
        screenHeightPixels = outMetrics.heightPixels;

        LogUtils.getBuilder().setDir(StorageUtil.getAppCachePath())
                .setLog2FileSwitch(true)
                .setLogSwitch(true)
                .setAppId("yy")
                .setTag("yy").create();
        LogUtils.d("============================application start============================");
        if(isGranted(Manifest.permission.READ_EXTERNAL_STORAGE) && isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            initBDMapSDK();
        }
	}

    private boolean isGranted(String permission) {
        int checkSelfPermission = ActivityCompat.checkSelfPermission(this, permission);
        return checkSelfPermission == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
    
	public static LvApplication getInstance() {
		return instance;
	}
	
	public static Context getContext() {
	    return applicationContext;
	}

    public void initBDMapSDK() {
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(this);
        initEngineManager(this);
    }
    
    public void initEngineManager(Context context) {
        if (mBMapManager == null) {
            mBMapManager = new BMapManager(context);
        }

        if (!mBMapManager.init(new MyGeneralListener())) {
        }
    }

    public boolean isDownloadedOfflineDB() {
        return isDownloadedOfflineDB;
    }

    public void setDownloadedOfflineDB(boolean downloadedOfflineDB) {
        isDownloadedOfflineDB = downloadedOfflineDB;
    }

    public void clearMapQueryCondition() {
        mapQueryCondition = new QueryCondition();
    }

    public void clearDataQueryCondition() {
        dataQueryCondition = new QueryCondition();
    }

    public void clearMapDisasterQueryCondition() {
        mapDisasterQueryCondition = new QueryCondition();
    }

    public QueryCondition getMapQueryCondition() {
        return mapQueryCondition;
    }

    public QueryCondition getDataQueryCondition() {
        return dataQueryCondition;
    }

    public QueryCondition getMapDisasterQueryCondition() {
        return mapDisasterQueryCondition;
    }

    public int getScreenWidthPixels() {
        return screenWidthPixels;
    }

    public int getScreenHeightPixels() {
        return screenHeightPixels;
    }

    // 常用事件监听，用来处理通常的网络错误，授权验证错误等
    public static class MyGeneralListener implements MKGeneralListener {
        @Override
        public void onGetPermissionState(int iError) {
            // 非零值表示key验证未通过
            if (iError != 0) {
            }
        }
    }
	
	public void initCommonData() {
        isUpdateDataConfirm = true;
        isDoUpdateData = true;

        DbUtil.getInstance().loadDB(Constant.DB_NAME_COMMON, Constant.DB_NAME_COMMON, R.raw.common);
        DbUtil.getInstance().loadDB(Constant.DB_NAME_EXTRA, Constant.DB_NAME_EXTRA, R.raw.extra);

        //解决省总队账户登录，切换区域获取数据问题
        compDicMap = CommonBussiness.getInstance().getRootDicCompMap();
        CommonBussiness.getInstance().setSubDicCompMap(compDicMap);
        orgDicMap = new HashMap<>();

        eleTypeWsDicList = new ArrayList<>();

        eleTypeWsDicList.add(new Dictionary("消火栓", AppDefs.CompEleType.WATERSOURCE_FIRE_HYDRANT.toString(), R.drawable.icon_xhs, R.drawable.marker_hydrant_map, R.drawable.marker_hydrant_cur));
        eleTypeWsDicList.add(new Dictionary("消防水鹤", AppDefs.CompEleType.WATERSOURCE_CRANE.toString(), R.drawable.icon_sh, R.drawable.marker_crane_map, R.drawable.marker_crane_cur));
        eleTypeWsDicList.add(new Dictionary("消防水池", AppDefs.CompEleType.WATERSOURCE_FIRE_POOL.toString(), R.drawable.icon_sc, R.drawable.marker_pool_map, R.drawable.marker_pool_cur));
        eleTypeWsDicList.add(new Dictionary("天然取水点", AppDefs.CompEleType.WATERSOURCE_NATURE_INTAKE.toString(), R.drawable.icon_tr, R.drawable.marker_naturalwater_map, R.drawable.marker_naturalwater_cur));

        eleTypeZddwDicList = new ArrayList<>();
        eleTypeZddwDicList.add(new Dictionary("重点单位", AppDefs.CompEleType.KEY_UNIT.toString(), R.drawable.icon_zddw, R.drawable.marker_zddw_map, R.drawable.marker_zddw_cur));

        eleTypeDicList = new ArrayList<>();
        eleTypeDicList.addAll(eleTypeZddwDicList);
        eleTypeDicList.addAll(eleTypeWsDicList);
        eleTypeDicList.add(new Dictionary("消防车", AppDefs.CompEleType.FIRE_VEHICLE.toString(), R.drawable.icon_xfcl, R.drawable.marker_xfc_map, R.drawable.marker_xfc_cur));
        eleTypeDicList.add(new Dictionary("消防器材", AppDefs.CompEleType.EQUIPMENT.toString(), R.drawable.icon_qc, R.drawable.marker_qc_map, R.drawable.marker_qc_cur));
        eleTypeDicList.add(new Dictionary("灭火剂", AppDefs.CompEleType.EXTINGUISHING_AGENT.toString(), R.drawable.icon_mhj, R.drawable.marker_mhq_map, R.drawable.marker_mhq_cur));
        eleTypeDicList.add(new Dictionary("消防总队", AppDefs.CompEleType.BRIGADE.toString(), R.drawable.icon_zongd, R.drawable.marker_zongd_map, R.drawable.marker_zongd_cur));
        eleTypeDicList.add(new Dictionary("消防支队", AppDefs.CompEleType.DETACHMENT.toString(), R.drawable.icon_zhid, R.drawable.marker_zhid_map, R.drawable.marker_zhid_cur));
        eleTypeDicList.add(new Dictionary("消防大队", AppDefs.CompEleType.BATTALION.toString(), R.drawable.icon_zqdd, R.drawable.marker_zqdd_map, R.drawable.marker_zqdd_cur));
        eleTypeDicList.add(new Dictionary("消防中队", AppDefs.CompEleType.SQUADRON.toString(), R.drawable.icon_zqzd, R.drawable.marker_zqzd_map, R.drawable.marker_zqzd_cur));
        //对应其他消防队伍分为4类
        eleTypeDicList.add(new Dictionary("企业专职消防队", AppDefs.CompEleType.ENTERPRISE_FOURCE.toString() + Constant.QYZZXFD, R.drawable.icon_qyd, R.drawable.marker_qyd_map, R.drawable.marker_qyd_cur));
        eleTypeDicList.add(new Dictionary("义务(志愿)消防队", AppDefs.CompEleType.ENTERPRISE_FOURCE.toString() + Constant.YWZYXFD, R.drawable.icon_ywxfd, R.drawable.marker_ywxfd_map, R.drawable.marker_ywxfd_cur));
        eleTypeDicList.add(new Dictionary("微型消防站", AppDefs.CompEleType.ENTERPRISE_FOURCE.toString() + Constant.WXXFZ, R.drawable.icon_wxxfz, R.drawable.marker_wxxfz_map, R.drawable.marker_wxxfz_cur));
        eleTypeDicList.add(new Dictionary("其他消防队伍", AppDefs.CompEleType.ENTERPRISE_FOURCE.toString() + Constant.QTXFDW, R.drawable.icon_qtxfd, R.drawable.marker_qtxfd_map, R.drawable.marker_qtxfd_cur));
        eleTypeDicList.add(new Dictionary("联动力量", AppDefs.CompEleType.JOINT_FORCE.toString(), R.drawable.icon_ldll, R.drawable.marker_ldll_map, R.drawable.marker_ldll_cur));
        eleTypeDicList.add(new Dictionary("危化品", Constant.HAZARD_CHEMICAL, R.drawable.icon_kl, R.drawable.marker_kl_map, R.drawable.marker_kl_cur));
        eleTypeDicList.add(new Dictionary("处置要点", Constant.DEAL_PROGRAM, R.drawable.icon_program, R.drawable.marker_sy, R.drawable.marker_sy_cur));
        eleTypeDicList.add(new Dictionary("知识库", Constant.KNOWLEDGE, R.drawable.icon_knowledge, R.drawable.marker_knowledge_map, R.drawable.marker_knowledge_cur));
        eleTypeDicList.add(new Dictionary("预案库", Constant.PLAN_LIBRARY, R.drawable.icon_plan_library, R.drawable.marker_plan_library_map, R.drawable.marker_plan_library_cur));
        eleTypeDicList.add(new Dictionary("法律法规", Constant.LAW_REGULATION, R.drawable.icon_law_regulation, R.drawable.marker_law_regulation_map, R.drawable.marker_law_regulation_cur));

        eleTypeGeoDicList = new ArrayList<>();
        eleTypeGeoDicList.addAll(eleTypeZddwDicList);
        eleTypeGeoDicList.addAll(eleTypeWsDicList);
        eleTypeGeoDicList.add(new Dictionary("在线消防车", Constant.GEO_TYPE_VEHICLE_GPS, R.drawable.marker_vehicle_map, R.drawable.marker_vehicle_map, R.drawable.marker_vehicle_cur));
        if("0".equals(PrefUserInfo.getInstance().getUserInfo("department_grade"))) {
            eleTypeGeoDicList.add(new Dictionary("消防总队", AppDefs.CompEleType.BRIGADE.toString(), R.drawable.icon_zongd, R.drawable.marker_zongd_map, R.drawable.marker_zongd_cur));
        }
        eleTypeGeoDicList.add(new Dictionary("消防支队", AppDefs.CompEleType.DETACHMENT.toString(), R.drawable.icon_zhid, R.drawable.marker_zhid_map, R.drawable.marker_zhid_cur));
        eleTypeGeoDicList.add(new Dictionary("消防大队", AppDefs.CompEleType.BATTALION.toString(), R.drawable.icon_zqdd, R.drawable.marker_zqdd_map, R.drawable.marker_zqdd_cur));
        eleTypeGeoDicList.add(new Dictionary("消防中队", AppDefs.CompEleType.SQUADRON.toString(), R.drawable.icon_zqzd, R.drawable.marker_zqzd_map, R.drawable.marker_zqzd_cur));
        //对应其他消防队伍分为4类
        eleTypeGeoDicList.add(new Dictionary("企业专职消防队", AppDefs.CompEleType.ENTERPRISE_FOURCE.toString() + Constant.QYZZXFD, R.drawable.icon_qyd, R.drawable.marker_qyd_map, R.drawable.marker_qyd_cur));
        eleTypeGeoDicList.add(new Dictionary("义务(志愿)消防队", AppDefs.CompEleType.ENTERPRISE_FOURCE.toString() + Constant.YWZYXFD, R.drawable.icon_ywxfd, R.drawable.marker_ywxfd_map, R.drawable.marker_ywxfd_cur));
        eleTypeGeoDicList.add(new Dictionary("微型消防站", AppDefs.CompEleType.ENTERPRISE_FOURCE.toString() + Constant.WXXFZ, R.drawable.icon_wxxfz, R.drawable.marker_wxxfz_map, R.drawable.marker_wxxfz_cur));
        eleTypeGeoDicList.add(new Dictionary("其他消防队伍", AppDefs.CompEleType.ENTERPRISE_FOURCE.toString() + Constant.QTXFDW, R.drawable.icon_qtxfd, R.drawable.marker_qtxfd_map, R.drawable.marker_qtxfd_cur));
        eleTypeGeoDicList.add(new Dictionary("联动力量", AppDefs.CompEleType.JOINT_FORCE.toString(), R.drawable.icon_ldll, R.drawable.marker_ldll_map, R.drawable.marker_ldll_cur));

        resIconMap = new HashMap<>();
        resIconMap.put(AppDefs.CompEleType.WATERSOURCE_FIRE_HYDRANT.toString(), R.drawable.icon_xhs);
        resIconMap.put(AppDefs.CompEleType.WATERSOURCE_CRANE.toString(), R.drawable.icon_sh);
        resIconMap.put(AppDefs.CompEleType.WATERSOURCE_FIRE_POOL.toString(), R.drawable.icon_sc);
        resIconMap.put(AppDefs.CompEleType.WATERSOURCE_NATURE_INTAKE.toString(), R.drawable.icon_tr);
        resIconMap.put(AppDefs.CompEleType.KEY_UNIT.toString(), R.drawable.icon_zddw);
        resIconMap.put(AppDefs.CompEleType.FIRE_VEHICLE.toString(), R.drawable.icon_xfcl);
        resIconMap.put(AppDefs.CompEleType.EQUIPMENT.toString(), R.drawable.icon_qc);
        resIconMap.put(AppDefs.CompEleType.EXTINGUISHING_AGENT.toString(), R.drawable.icon_mhj);
        resIconMap.put(AppDefs.CompEleType.BRIGADE.toString(), R.drawable.icon_zongd);
        resIconMap.put(AppDefs.CompEleType.DETACHMENT.toString(), R.drawable.icon_zhid);
        resIconMap.put(AppDefs.CompEleType.BATTALION.toString(), R.drawable.icon_zqdd);
        resIconMap.put(AppDefs.CompEleType.SQUADRON.toString(), R.drawable.icon_zqzd);
        //对应其他消防队伍分为4类
        resIconMap.put(AppDefs.CompEleType.ENTERPRISE_FOURCE.toString() + Constant.QYZZXFD, R.drawable.icon_qyd);
        resIconMap.put(AppDefs.CompEleType.ENTERPRISE_FOURCE.toString() + Constant.YWZYXFD, R.drawable.icon_ywxfd);
        resIconMap.put(AppDefs.CompEleType.ENTERPRISE_FOURCE.toString() + Constant.WXXFZ, R.drawable.icon_wxxfz);
        resIconMap.put(AppDefs.CompEleType.ENTERPRISE_FOURCE.toString() + Constant.QTXFDW, R.drawable.icon_qtxfd);
        resIconMap.put(AppDefs.CompEleType.JOINT_FORCE.toString(), R.drawable.icon_ldll);
        resIconMap.put(Constant.HAZARD_CHEMICAL, R.drawable.icon_kl);
        resIconMap.put(Constant.DEAL_PROGRAM, R.drawable.icon_program1);
        resIconMap.put(Constant.KNOWLEDGE, R.drawable.icon_knowledge);
        resIconMap.put(Constant.PLAN_LIBRARY, R.drawable.icon_plan_library);
        resIconMap.put(Constant.LAW_REGULATION, R.drawable.icon_law_regulation);

        hasOrNohasDicList = new ArrayList<>();
        hasOrNohasDicList.add(new Dictionary("有", "true"));
        hasOrNohasDicList.add(new Dictionary("无", "false"));

        whetherDicList = new ArrayList<>();
        whetherDicList.add(new Dictionary("是", "true"));
        whetherDicList.add(new Dictionary("否", "false"));

        usableDicList = new ArrayList<>();
        usableDicList.add(new Dictionary("可用", "true"));
        usableDicList.add(new Dictionary("不可用", "false"));

        keyUnitStructureDicList = new ArrayList<>();
        keyUnitStructureDicList.add(new Dictionary("单体建筑", "DTJZ"));
        keyUnitStructureDicList.add(new Dictionary("建筑群", "JZQ"));

        geoEleWeightMap = new HashMap<>();
        for (int i = 0; i < eleTypeDicList.size(); i++) {
            Dictionary dic = eleTypeDicList.get(i);
            geoEleWeightMap.put(dic.getCode(), i);
        }
	}

    public Integer getGeoEleWeight(String type) {
        return geoEleWeightMap.get(type);
    }

    public List<Dictionary> getHasOrNohasDicList() {
        return hasOrNohasDicList;
    }

    public List<Dictionary> getWhetherDicList() {
        return whetherDicList;
    }

    public List<Dictionary> getUsableDicList() {
        return usableDicList;
    }

    public List<Dictionary> getKeyUnitStructureDicList() {
        return keyUnitStructureDicList;
    }

    public List<Dictionary> getCurrentRegionDicList() {
        if (curRegionDicList == null) {
            curRegionDicList = new ArrayList<>();
        }
        return curRegionDicList;
    }

    public List<String> getCityList() {
        if(cityList == null) {
            cityList = new ArrayList<>();
        }
        return cityList;
    }

    public List<Dictionary> getCurrentOrgList() {
        List<Dictionary> orgList = null;
        String cityCode = getCityCode();
        if(orgDicMap.containsKey(cityCode)) {
            orgList = orgDicMap.get(cityCode);
        } else {
            orgList = CommonBussiness.getInstance().getOrgMap(getCityName());
            if(orgList != null && orgList.size() > 0) {
                orgDicMap.put(cityCode, orgList);
            }
        }
        return orgList;
    }

    public Map<String, List<Dictionary>> getCompDicMap() {
        return compDicMap;
    }

    public void clearOrgDicMap() {
        orgDicMap.clear();
    }

    public List<Dictionary> getEleTypeGeoDicList() {
        return eleTypeGeoDicList;
    }

    public List<Dictionary> getEleTypeZddwDicList() {
        return eleTypeZddwDicList;
    }

    public List<Dictionary> getEleTypeWsDicList() {
        return eleTypeWsDicList;
    }

    public List<Dictionary> getEleTypeDicList() {
        return eleTypeDicList;
    }

    public Integer getEleIconResId(String type) {
        Integer resId = R.drawable.icon_sy;
        if(resIconMap.containsKey(type)) {
            resId = resIconMap.get(type);
        }
        return resId;
    }
 
	/**
	 * 退出登录,清空数据
	 */
	public void logout() {
	}

    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }

    public String getCurLat() {
        return curLat;
    }

    public void setCurLat(String curLat) {
        this.curLat = curLat;
    }

    public String getCurLng() {
        return curLng;
    }

    public void setCurLng(String curLng) {
        this.curLng = curLng;
    }

    public int getDPI() {
        return DPI;
    }

    public void setDPI(int dPI) {
        DPI = dPI;
    }

    public void loadCityDicList() {
        String userDistrictCode = PrefUserInfo.getInstance().getUserInfo("district_code");

        curRegionDicList = CommonBussiness.getInstance().getRegionListByUser(userDistrictCode);
        if(curRegionDicList.size() > 0) {
            curCityDic = curRegionDicList.get(0);
            mCityName = curCityDic.getValue();
        }
        cityList = CommonBussiness.getInstance().getCityListByUser(userDistrictCode);
    }

    public String getCityName() {
        return mCityName;
    }

    public void setCityName(String cityName) {
        mCityName = cityName;
    }

    public String getCityCode() {
        String cityCode = "";
        if(curCityDic != null) {
            cityCode = curCityDic.getCode();
        }
        return cityCode;
    }

    public void startPushService() {
        if(!SystemUtil.isServiceRunning(this, PushService.class.getName())) {
            if(isGranted(Manifest.permission.READ_PHONE_STATE)) {
                Intent i = new Intent(Intent.ACTION_RUN);
                i.setClass(this, PushService.class);
                startService(i);
            }
        }
    }

    public void resetUpdateDataFlag() {
        isUpdateDataConfirm = true;
        isDoUpdateData = true;
    }

    public boolean getIsUpdateDataConfirm() {
        return isUpdateDataConfirm;
    }

    public void setIsUpdateDataConfirm(boolean updateDataConfirm) {
        isUpdateDataConfirm = updateDataConfirm;
    }

    public boolean getIsDoUpdateData() {
        return isDoUpdateData;
    }

    public void setIsDoUpdateData(boolean doUpdateData) {
        isDoUpdateData = doUpdateData;
    }
}
