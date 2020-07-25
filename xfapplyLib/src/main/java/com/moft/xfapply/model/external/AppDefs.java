package com.moft.xfapply.model.external;

/**
 * Created by Administrator on 2018/4/8 0008.
 */

public class AppDefs {

    public enum ResultMessage {
        MSG_OK(0, "ok"),
        MSG_USER_NOTEXIST(10, "invalid username"),
        MSG_PASSWORD_ERROR(11, "password error"),
        MSG_DEVICE_NOTEXIST(20, "invalid device"),
        MSG_DEVICE_EXPIRED(21, "device expired"),
        MSG_DEVICE_NOTANTHORIZED(22, "device unAnthorized"),
        MSG_DEVICE_ALREADY_REGISTER(23,"device already register"),
        MSG_SERVER_ERROR(40, "server error"),
        MSG_PASSWORD_INVALID(402,"PassWord Invalid"),
        MSG_INTERNAL_ERROR(403, "internal error"),
        MSG_TOUR_NOT_USABLE(50, "pano tour id unavailable"),
        MSG_TOUR_ID_NOTEXIST(51, "invalid pano tour id"),
        MSG_PARAM_ERROR(100, "param invalid");

        private int    code;
        private String message;

        private ResultMessage(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return this.code;
        }

        public String getMessage() {
            return this.message;
        }
    }

    // 终端应用标识
    public enum DeviceAppIdentity {
        CJ, // 采集
        YY // 应用
    }

    // 数据提交方式
    public enum SubmitWay {
        MOBILE, // 终端
        PC // PC
    }

    // 数据操作
    public enum ActionType {
        NEW, // 新建
        MODIFY, // 修改
        DELETE // 删除
    }

    // 审核状态
    public enum VerifyState {
        DTJ,//待提交
        DSH, // 待审核
        YSH, // 已审核通过
        YJJ // 已拒绝
    }

    // 重点单位附件类型
    public enum KeyUnitMediaType {
        KU_MEDIA_IMAGE, // 图片
        KU_MEDIA_PANO, // 全景图
        KU_MEDIA_PRE_PALN, // 预案
        KU_MEDIA_OUTDOOR_SCENE,//实景照片
        KU_MEDIA_OVERALL_PLAN, // 总平面图
        KU_MEDIA_FACADE_PLAN, // 外立面图
        KU_MEDIA_MX, // 三维模型
        KU_MEDIA_INTERNAL_PLANE_DIAGRAM, // 内部平面图(功能分区图)
        KU_MEDIA_COMBAT_DEPLOYMENT_DIAGRAM // 作战部署图
    }

    // 附件格式
    public enum MediaFormat {
        NONE,
        IMG, // 图片
        VIDEO, // 视频
        DOC, // word
        EXCEL, // excel
        PPT, // ppt
        TXT, // txt
        PDF, // 水源
        AUDIO, // 音频
        URL
    }

    public enum CoordinateType {
        BD_09,
        ONPLOT
    }

    // CompElement eleType
    public enum CompEleType {
        FIRE_VEHICLE, // 车辆
        EXTINGUISHING_AGENT, // 灭火剂
        EQUIPMENT, // 特殊器材
        JOINT_FORCE, // 联动力量
        WATERSOURCE_FIRE_HYDRANT, // 消火栓
        WATERSOURCE_CRANE, // 水鹤
        WATERSOURCE_FIRE_POOL, // 水池
        WATERSOURCE_NATURE_INTAKE, // 天然取水点
        KEY_UNIT, // 重点单位
        ENTERPRISE_FOURCE, //企业队
        BATTALION, //大队
        BRIGADE, //总队
        DETACHMENT, //支队
        SQUADRON, //中队
    }

    // 重点单位-分区 类型
    public enum KeyUnitPartitionType {
//        PARTITION_STRUCTURE_MONO, //单体建筑
//        PARTITION_STRUCTURE_GROUP, //建筑群（建筑类）
        PARTITION_STRUCTURE, //建筑
        PARTITION_DEVICE, //装置
        PARTITION_STORAGE_ZONE, //储罐
        PARTITION_FLOOR, //楼层
        PARTITION_STORAGE, //储罐
        PARTITION_UNIT //其他分区
    }

    // 重点单位-重点部位 类型
    public enum KeyUnitKeyPartType {
        KEY_PART_STRUCTURE,
        KEY_PART_DEVICE,
        KEY_PART_STORAGE
    }

    public enum KeyUnitFireFacilityType {
        FIRE_FACILITY_EVACUATION_SAFETY_EXIT,    //安全出口（层）
        FIRE_FACILITY_EVACUATION_STAIRWAY,    //疏散楼梯（层）
        FIRE_FACILITY_EVACUATION_LIFT,    //消防电梯
        FIRE_FACILITY_EVACUATION_WAITING_FLOOR,    //避难层（间）
        FIRE_FACILITY_EVACUATION_BROADCAST,    //应急广播
        FIRE_FACILITY_WATER_PUMP_ROOM,    //消防泵房
        FIRE_FACILITY_WATER_TANK,    //消防水箱
        FIRE_FACILITY_WATER_POOL,    //消防水池
        FIRE_FACILITY_WATER_HYDRANT_INDOOR,    //室内消火栓（层）
        FIRE_FACILITY_WATER_HYDRANT_OUTDOOR,    //室外消火栓
        FIRE_FACILITY_WATER_PUMP_ADAPTER,    //水泵接合器
        FIRE_FACILITY_WATER_SPRAY_SYSTEM,    //喷淋系统（建筑）
        FIRE_FACILITY_WATER_COOLING_SYSTEM,    //冷却水系统（装置/储罐）
        FIRE_FACILITY_WATER_FIXED_MONITOR,    //固定水炮
        FIRE_FACILITY_WATER_SEMIFIXED_DEVICE,    //半固定设施
        FIRE_FACILITY_FOAM_PUMP_ROOM,    //泡沫泵房
        FIRE_FACILITY_FOAM_HYDRANT,    //泡沫消火栓
        FIRE_FACILITY_FOAM_FIXED_MONITOR,    //固定泡沫炮
        FIRE_FACILITY_FOAM_GENERATOR,    //泡沫发生器
        FIRE_FACILITY_FOAM_SEMIFIXED_DEVICE,    //半固定设施
        FIRE_FACILITY_STEAM_FIXED_SYSTEM,    //固定式蒸汽系统
        FIRE_FACILITY_STEAM_SEMIFIXED_SYSTEM,    //半固定蒸汽系统
        FIRE_FACILITY_CONTROL_ROOM,    //消防控制室
        FIRE_FACILITY_SMOKE_PROOF_EXIT,    //排烟口/出烟口（层）
        FIRE_FACILITY_SMOKE_PROOF_SYSTEM,    //防排烟系统
        FIRE_FACILITY_COMPARTMENT,    //防火分区（层）
        FIRE_FACILITY_EXTINGUISHING_GAS,    //气体灭火系统
        FIRE_FACILITY_EXTINGUISHING_POWDER,    //干粉灭火系统
        FIRE_FACILITY_DEVICE,    //其他消防设施
        FIRE_FACILITY_AUTO_ALARM_SYSTEM,    //自动报警系统
        FIRE_FACILITY_AUTO_SPRINKLER_SYSTEM //自动喷淋系统
    }

    public enum KeyUnitFloorType {
        FLOOR_TYPE_UNDERGROUND_FLOOR, //地下
        FLOOR_TYPE_WAITING_FLOOR,     //避难层（间）
        FLOOR_TYPE_FIRST_FLOOR,       //首层
        FLOOR_TYPE_STANDARD_LAYER,   //标准层
        FLOOR_TYPE_TOP_FLOOR          //顶层
    }

    public enum KeyUnitJsonType {
        JSON_COMPOSITION,
        JSON_MATERIAL,
        JSON_PRODUCT,
        JSON_STORAGE_MEDIA,
        JSON_HAZARD_MEDIA
    }

    public enum IncDataType {
        DEPARTMENT, // 部门组织
        MATERIAL, // 应急装备
        STATION, // 应急力量
        WATERSOURCE, // 水源
        KEY_UNIT, // 重点单位
        JOINT_FORCE // 联动力量
    }

    public enum HazardChemicalType {
        HAZARD_CHEMICAL, //
        MSDS, //
    }
}
