package com.moft.xfapply.app;

public class Constant {
    public static final String FOLDER = "/sycj/";
    public static final String DB = "db/";
    public static final String TEMP_FOLDER = "temp/";
    public static final String ATTACH_FOLDER = "attach/";
    public static final String CRASH_FOLDER = "crash/";
    public static final String CRASH_LOG = "yy-crash";

    public static final int LIST_INFO = 0;
    public static final int SEARCH_INFO_MAP = 2;

    public static final int MSG_REGISTER_CLIENT = 0;
    public static final int MSG_NOTIFY_SHARE = 1;
    public static final int MSG_NOTIFY_OPEN_SHARE = 2;
    public static final int MSG_EXIT_CLIENT = 4;
    public static final int MSG_NETWORK_CHANGED = 5;

    public static final String METHOD_DEVICE_AUTH = "/api/devices/rest/c/app-devices/authenticate";
    public static final String METHOD_DEVICE_REGISTER = "/api/devices/rest/c/app-devices/register";
    public static final String METHOD_UPDATE_APP_VERSION = "/api/devices/rest/c/app-device-version/submit";
    public static final String METHOD_LOGIN = "/api/uaa/rest/q/auth-security-accounts/login";
    public static final String METHOD_USER_UPDATE_PASSWORD = "/api/uaa/rest/c/auth-security-accounts/%s/update-password";
    public static final String METHOD_USER_UPDATE_FIREFIGHTERS = "/api/uaa/rest/c/firefighters/updateByUuid/%s";
    public static final String METHOD_GET_SYS_DICTIONARY = "/api/system/rest/q/sys-dictionarys";
    public static final String METHOD_GET_OFFLINE_INFO = "/api/exportdb/rest/q/export-dbs/by-districtcode/%s";
    public static final String METHOD_GET_EXPORT_FILE  = "/api/exportdb/rest/q/export-dbs/export-file";

    public static final String METHOD_GET_INQUIRY_INC_DATA = "/api/exportdb/rest/q/export-dbs/sqlite/inc-sql/inquiry";
    public static final String METHOD_GET_ACTUAL_SQL = "/api/exportdb/rest/q/export-dbs/sqlite/actual-sql";

    public static final String METHOD_GET_WATERSOURCE_FIRE_HYDRANT = "/api/watersources/rest/q/water-sources/xhs/%s";
    public static final String METHOD_GET_WATERSOURCE_CRANE = "/api/watersources/rest/q/water-sources/sh/%s";
    public static final String METHOD_GET_WATERSOURCE_FIRE_POOL = "/api/watersources/rest/q/water-sources/sc/%s";
    public static final String METHOD_GET_WATERSOURCE_NATURE_INTAKE = "/api/watersources/rest/q/water-sources/trqsd/%s";
    public static final String METHOD_GET_KEY_UNIT = "/api/keyunit/rest/q/key-units/%s";
    public static final String METHOD_GET_FIRE_VEHICLES = "/api/material/rest/q/fire-vehicles/%s";
    public static final String METHOD_GET_EQUIPMENT = "/api/material/rest/q/equipments/%s";
    public static final String METHOD_GET_EXTINGUISHING_AGENT = "/api/material/rest/q/extinguishing-agents/%s";
    public static final String METHOD_GET_STATION_BRIGADES = "/api/station/rest/q/station-brigades/%s";
    public static final String METHOD_GET_STATION_DETACHMENTS = "/api/station/rest/q/station-detachments/%s";
    public static final String METHOD_GET_STATION_BATTALIONS = "/api/station/rest/q/station-battalions/%s";
    public static final String METHOD_GET_STATION_SQUADRONS = "/api/station/rest/q/station-squadrons/%s";
    public static final String METHOD_GET_ENTERPRISE_FOURCES = "/api/station/rest/q/enterprise-fources/%s";
    public static final String METHOD_GET_JOINT_FORCE = "/api/knowledge/rest/q/joint-forces/%s";

    public static final String METHOD_APP_ACCESS_SUBMIT = "/api/devices/rest/c/app-device-accesss/submit";
    public static final String METHOD_GET_VEHICLES_GPS = "/VehicleMonitor/rest/vehicle/getVehicleRealDataList";

    public static final String METHOD_GET_INTEGRITY_OVERVIEW = "/api/supervise/rest/q/fire-data-integrity/by-integrity-overview";
    public static final String METHOD_GET_VERIFY_OVERVIEW = "/api/supervise/rest/q/activity-comp-verifys/by-verify-overview-v2";
    public static final String METHOD_GET_DEVICE_OVERVIEW = "/api/supervise/rest/q/app-devices/usage-survey";
    public static final String METHOD_GET_DATA_DYNAMIC = "/api/verify/rest/q/entity-maintain-tasks/browse-page";
    public static final String METHOD_GET_DATA_DYNAMIC_V2 = "/api/verify/rest/q/entity-maintain-tasks/browse-page-v2";

    public static final String METHOD_GET_PROCEDURE  = "/api/media/rest/q/library-medias/list/by-destination?destination=document/procedure&recursive=true&page=%s&start=%s&limit=%s";

    public static final String METHOD_CREATE_PROBLEM = "/api/govern/rest/c/master-problems/create-by-json";

    public static final String METHOD_CREATE_QRCODE = "/preplan-share/rest/c/share-activitys/qrc/key-unit/create";

    // 处置要点
    public static final String METHOD_GET_DISPOSAL_POINTS_LIST = "/api/knowledge/rest/q/disposal-points/browse-list";
    public static final String METHOD_GET_KEY_UNIT_DISPOSAL_POINTS = "/api/keyunit/rest/q/key-units/%s/disposal-points";
    // 知识库
    public static final String METHOD_GET_KNOWLEDGE_LIST = "/api/knowledge/rest/q/knowledge-bases/browse-list";
    // 法律法规
    public static final String METHOD_GET_RAW_REGULATION_LIST = "/api/media/rest/q/library-medias/list/by-destination?destination=document/law&recursive=true";
    // 预案库
    public static final String METHOD_GET_ALL_LIBRARY_LIST = "/api/knowledge/rest/q/plan-library-catalogs/plan-library";
    public static final String METHOD_GET_PLAN_LIBRARY_CATALOGS_LIST = "/api/knowledge/rest/q/plan-library-catalogs/browse";
    public static final String METHOD_GET_PLAN_LIBRARY_LIST = "/api/knowledge/rest/q/plan-library-catalogs/%s/browse";

    public static final String DB_NAME_COMMON = "common";
    public static final String DB_NAME_EXTRA = "extra";
    public static final String DB_NAME_USER = "usercode";
    public static final String DB_EXTENSION = ".db";
    public static final String TABLE_NAME_GEOM_ELEMENT = "geometry_element";
    public static final String TABLE_NAME_DEPARTMENT = "sys_department";
    public static final String TABLE_NAME_KEY_UNIT = "key_unit";
    public static final String TABLE_NAME_WS_FIRE_HYDRANT = "watersource_fire_hydrant";
    public static final String TABLE_NAME_WS_CRANE = "watersource_crane";
    public static final String TABLE_NAME_WS_FIRE_POOL = "watersource_fire_pool";
    public static final String TABLE_NAME_WS_NATURE_INTAKE = "watersource_nature_intake";
    public static final String TABLE_NAME_FIRE_VEHICLE = "material_fire_vehicle";
    public static final String TABLE_NAME_EXTINGUISHING_AGENT = "material_extinguishing_agent";
    public static final String TABLE_NAME_EQUIPMENT = "material_equipment";
    public static final String TABLE_NAME_BRIGADE = "station_brigade";
    public static final String TABLE_NAME_DETACHMENT = "station_detachment";
    public static final String TABLE_NAME_BATTALION = "station_battalion";
    public static final String TABLE_NAME_SQUADRON = "station_squadron";
    public static final String TABLE_NAME_ENTERPRISE_FOURCE = "enterprise_fource";
    public static final String TABLE_NAME_JOINT_FORCE = "joint_force";
    public static final String TABLE_NAME_DISPOSAL_POINT = "disposal_point";
    public static final String TABLE_NAME_FILE_NODE = "file_node";
    public static final String SEARCH_COND_NAME = "name = '%s'";
    public static final String SEARCH_COND_BELONG_TO_GROUP = "belongto_group = '%s'";
    public static final String SEARCH_COND_CLASSIFICATION = "classification = '%s'";
    public static final String SEARCH_COND_CLASSIFICATION_NOT_EQUALS = "classification <> '%s'";
    public static final String SEARCH_COND_UUID = "uuid = '%s'";
    public static final String SEARCH_COND_CONFIG_UUID = "config_uuid = '%s'";
    public static final String SEARCH_COND_PARENT_UUID = "parent_uuid = '%s'";
    public static final String SEARCH_COND_KEY_REGION_UNIT_UUID = "key_unit_region_uuid = '%s'";
    public static final String SEARCH_COND_KEY_UNIT_UUID_AND_PARTITON_UUID = "key_unit_region_uuid = '%s' and partition_uuid = '%s'";
    public static final String SEARCH_COND_BELONG_TO_PARTITON_UUID = "belong_to_partition_uuid = '%s'";
    public static final String SEARCH_COND_PARTITION_UUID = "partition_uuid = '%s'";
    public static final String SEARCH_COND_PARTITION_UUIDS = "partition_uuid in (%s)";
    public static final String SEARCH_COND_STORAGE_UUID = "storage_position_uuid = '%s'";
    public static final String SEARCH_COND_SW_UUID = "ws_uuid = '%s'";
    public static final String SEARCH_COND_MATERIAL_UUID = "material_uuid = '%s'";
    public static final String SEARCH_COND_MATERIAL_UUIDS = "material_uuid in (%s)";
    public static final String SEARCH_COND_STATION_UUID = "station_uuid = '%s'";
    public static final String SEARCH_COND_JOINT_FORCE_UUID = "joint_force_uuid = '%s'";
    public static final String SEARCH_COND_KEY_UNIT_UUID = "key_unit_uuid = '%s'";
    public static final String SEARCH_COND_BELONG_TO_UUID = "belongto_uuid = '%s'";
    public static final String SEARCH_COND_BELONG_TO_TYPE = "belongto_type = '%s'";
    public static final String SEARCH_COND_NOT_DELETED = "(deleted = 0 or deleted is NULL)";
    public static final String SEARCH_COND_JOINT_BRIGADE_NOT_EQUALS = "ele_type <> 'BRIGADE'";
    public static final String SEARCH_COND_DEPARTMENT_UUID_IN = "department_uuid in (%s)";
    public static final String SEARCH_COND_DEPARTMENT_UUID = "department_uuid = '%s'";
    public static final String SEARCH_COND_DEPTH = "depth = %d";
    public static final String SEARCH_COND_GRADE = "grade = %d";
    public static final String SEARCH_COND_DEPTH_NOT_EQUALS = "depth <> %d";
    public static final String SEARCH_COND_ELE_TYPE = "ele_type = '%s'";
    public static final String SEARCH_COND_ELE_TYPE_SY = "ele_type like '%s%%'";
    public static final String SEARCH_COND_TYPE = "type = '%s'";
    public static final String SEARCH_COND_STATUS = "status = '%s'";
    public static final String SEARCH_COND_DATA_TYPE = "data_type = '%s'";
    public static final String SEARCH_COND_ID_NOT_EQUALS = "id <> %d";
    public static final String ORDER_COND_VERSION = "version_no %s limit 1";
    public static final String SEARCH_COND_CODE = "code = %s";
    public static final String SEARCH_COND_USER_DISTRICT = "code like '%s%%' order by code";
    public static final String SEARCH_COND_USER_CITY = "code like '%s%%' and grade = 1 order by code";
    public static final String SEARCH_COND_UUIDS = "uuid in (%s)";
    public static final String SEARCH_COND_PUBLISH_URL = "publish_url = '%s'";

    public static final String PROPERTY_ELE_TYPE = "ele_type";
    public static final String PROPERTY_TYPE = "type";
    public static final String PROPERTY_PARTITION_TYPE = "partitionType";
    public static final String PROPERTY_FIRE_FACILITY_TYPE = "facilityType";
    public static final String PROPERTY_KEY_PART_TYPE = "keyPartType";

    public static final String SEARCH_COND_POS_RANGE = " and (CAST(latitude AS REAL) > %f  and CAST(latitude AS REAL) < %f and CAST(longitude AS REAL) > %f and CAST(longitude AS REAL) < %f)";
    public static final String SEARCH_COND_KEY_WORD = " and keywords like '%%%s%%'";
    public static final String SEARCH_COND_STATION = "ele_type IN ('BRIGADE', 'BATTALION', 'SQUADRON', 'DETACHMENT')";
    public static final String SEARCH_UUID_DELETED_FALSE = "uuid = '%s' and deleted = 0";

    public static final String SEARCH_COND_UN_NAME_CODE = " AND ((name like '%%%s%%') or (un_code like '%%%s%%') or (danger_code like '%%%s%%'))";
    public static final String ORDER_BY_NAME_LIMIT = "name asc %s";
    public static final String TABLE_NAME_HAZARD_CHEMICAL = "dt_kb_hazardchemical";
    public static final String TABLE_NAME_MSDS = "msds";
    public static final String SEARCH_COND_CNAME = " AND ((cname like '%%%s%%'))";
    public static final String ORDER_BY_CNAME_LIMIT = "pid asc %s";

    public static final int UPLOAD_POSITION_INTERVAL = 60 * 1000;

    public static final String IP = "39.96.0.232";
    public static final String PORT = "38080";
//    public static final String SERVICE_NAME = "";
//    public static final String IP = "192.168.11.121";
//    public static final String PORT = "38080";
    public static final String VEHICLES_GPS_PORT = "52080";
    public static final String FTP_SERVER = "39.96.1.204";
    public static final int FTP_PORT = 21121;
    public static final String FTP_USER = "LiaoNing";
    public static final String FTP_PASSWORD = "LiaoNing";

    public static final String RABBIT_MQ_IP = IP;
    public static final int RABBIT_MQ_PORT = 55672;
    public static final String RABBIT_MQ_USERNAME = "superrd";
    public static final String RABBIT_MQ_PASSWORD = "superrd";
    public static final String RABBIT_MQ_EXCHANGER = "preplan-keyunit-push";

    public static final String SERVICE_NAME = "/preplan";
    public static final String UPDATE_NAME = "/preplan-update";
    public static final String ATTACH_NAME = "/preplan-media";
    public static final String DB_NAME = "/preplan-export-db/export";

    public static final String URL_HEAD = "http://";
    public static final String URL_MIDDLE = ":";
    public static final String APPURL_STRING = "/xfapply-version.xml";

    public static int imgWidth = 640;
    public static int imgHeight = 640;

    public static final String MAP_SEARCH_POSITION= "MAP_SEARCH_POSITION";// 地图位置
    public static final String MAP_SEARCH_POI = "MAP_SEARCH_POI";// POI建议
    public static final String MAP_SEARCH_HISTORY = "MAP_SEARCH_HISTORY";// 历史建议
    public static final String MAP_SEARCH_FIRE = "MAP_SEARCH_FIRE";// ELEMENT
    public static final String MAP_VEHICLE_GPS = "MAP_VEHICLE_GPS"; // 车载

    public static final String OUTLINE_DIVIDER = ";;";

    public static final String KEY_UNIT_JSON_TITLE_ZZZC = "装置组成";
    public static final String KEY_UNIT_JSON_TITLE_ZZYL = "原料信息";
    public static final String KEY_UNIT_JSON_TITLE_ZZCW = "产物信息";
    public static final String KEY_UNIT_JSON_TITLE_CCJZ = "存储介质";
    public static final String KEY_UNIT_JSON_TITLE_WXJZ = "危险介质";

    public static final String GEO_TYPE_VEHICLE_GPS = "VEHICLE_GPS";
    public static final long OPERATION_DEADLINE = 1000 * 60 * 30;

    public static final int ZINDEX_0 = 0;
    public static final int ZINDEX_1 = 1;
    public static final int ZINDEX_2 = 2;
    public static final int ZINDEX_3 = 3;
    public static final int ZINDEX_4 = 99;

    public static final String QYZZXFD = "XFLL_DWLX_01";
    public static final String YWZYXFD = "XFLL_DWLX_02";
    public static final String WXXFZ = "XFLL_DWLX_03";
    public static final String QTXFDW = "XFLL_DWLX_99";

    public static final String HAZARD_CHEMICAL = "HAZARD_CHEMICAL"; // 危化品
    public static final String DEAL_PROGRAM = "DEAL_PROGRAM"; // 处置程序
    public static final String KNOWLEDGE = "KNOWLEDGE"; // 知识库
    public static final String PLAN_LIBRARY = "PLAN_LIBRARY"; // 预案库
    public static final String LAW_REGULATION = "LAW_REGULATION"; // 法律法规

    /*关键字*/
    public static final String PARAM_KEY_WORD        = "keyWord";
    /*单位性质*/
    public static final String PARAM_CHARACTER        = "character";
    /*建筑结构*/
    public static final String PARAM_STRUCTURE        = "structure";
    /*使用性质*/
    public static final String PARAM_USAGE       = "usage";
    public static final String PARAM_NAME        = "name";
    public static final String PARAM_DEPARTMENT_UUID        = "departmentUuid";

    public static final String PAGE_NO         = "page";          // 当前分页
    public static final String PAGE_LIMIT      = "limit";         // 分页大小
    public static final String ORDER_BY        = "orderBy";       // 排序字段
    public static final String ORDER_DIRECTION = "orderDirection"; // 排序方式

}
