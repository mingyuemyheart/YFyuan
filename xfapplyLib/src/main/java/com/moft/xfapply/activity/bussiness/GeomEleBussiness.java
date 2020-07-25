package com.moft.xfapply.activity.bussiness;

import android.os.Build;

import com.baidu.mapapi.model.LatLng;
import com.google.gson.Gson;
import com.moft.xfapply.app.Constant;
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.logic.network.OkHttpClientManager;
import com.moft.xfapply.logic.setting.PrefSetting;
import com.moft.xfapply.logic.setting.PrefUserInfo;
import com.moft.xfapply.model.base.IFireFacilityInfo;
import com.moft.xfapply.model.base.IGeomElementInfo;
import com.moft.xfapply.model.base.IKeyPartInfo;
import com.moft.xfapply.model.base.IMediaInfo;
import com.moft.xfapply.model.base.IPartitionInfo;
import com.moft.xfapply.model.common.DepartmentInfo;
import com.moft.xfapply.model.common.Dictionary;
import com.moft.xfapply.model.common.EleCondition;
import com.moft.xfapply.model.common.GeomElementType;
import com.moft.xfapply.model.common.OrgDataCountSummary;
import com.moft.xfapply.model.common.QueryCondition;
import com.moft.xfapply.model.common.WhpViewInfo;
import com.moft.xfapply.model.entity.EnterpriseFourceDBInfo;
import com.moft.xfapply.model.entity.FireFacilityAutoAlarmSystemDBInfo;
import com.moft.xfapply.model.entity.FireFacilityAutoSprinklerSystemDBInfo;
import com.moft.xfapply.model.entity.FireFacilityCompartmentDBInfo;
import com.moft.xfapply.model.entity.FireFacilityControlRoomDBInfo;
import com.moft.xfapply.model.entity.FireFacilityDeviceDBInfo;
import com.moft.xfapply.model.entity.FireFacilityEvacuationBroadcastDBInfo;
import com.moft.xfapply.model.entity.FireFacilityEvacuationLiftDBInfo;
import com.moft.xfapply.model.entity.FireFacilityEvacuationSafetyExitDBInfo;
import com.moft.xfapply.model.entity.FireFacilityEvacuationStairwayDBInfo;
import com.moft.xfapply.model.entity.FireFacilityEvacuationWaitingFloorDBInfo;
import com.moft.xfapply.model.entity.FireFacilityExtinguishingGasDBInfo;
import com.moft.xfapply.model.entity.FireFacilityExtinguishingPowderDBInfo;
import com.moft.xfapply.model.entity.FireFacilityFoamFixedMonitorDBInfo;
import com.moft.xfapply.model.entity.FireFacilityFoamGeneratorDBInfo;
import com.moft.xfapply.model.entity.FireFacilityFoamHydrantDBInfo;
import com.moft.xfapply.model.entity.FireFacilityFoamPumpRoomDBInfo;
import com.moft.xfapply.model.entity.FireFacilityFoamSemifixedDeviceDBInfo;
import com.moft.xfapply.model.entity.FireFacilitySmokeProofExitDBInfo;
import com.moft.xfapply.model.entity.FireFacilitySmokeProofSystemDBInfo;
import com.moft.xfapply.model.entity.FireFacilitySteamFixedSystemDBInfo;
import com.moft.xfapply.model.entity.FireFacilitySteamSemifixedSystemDBInfo;
import com.moft.xfapply.model.entity.FireFacilityWaterCoolingSystemDBInfo;
import com.moft.xfapply.model.entity.FireFacilityWaterFixedMonitorDBInfo;
import com.moft.xfapply.model.entity.FireFacilityWaterHydrantIndoorDBInfo;
import com.moft.xfapply.model.entity.FireFacilityWaterHydrantOutdoorDBInfo;
import com.moft.xfapply.model.entity.FireFacilityWaterPoolDBInfo;
import com.moft.xfapply.model.entity.FireFacilityWaterPumpAdapterDBInfo;
import com.moft.xfapply.model.entity.FireFacilityWaterPumpRoomDBInfo;
import com.moft.xfapply.model.entity.FireFacilityWaterSemifixedDeviceDBInfo;
import com.moft.xfapply.model.entity.FireFacilityWaterSpraySystemDBInfo;
import com.moft.xfapply.model.entity.FireFacilityWaterTankDBInfo;
import com.moft.xfapply.model.entity.GeomElementDBInfo;
import com.moft.xfapply.model.entity.JointForceDBInfo;
import com.moft.xfapply.model.entity.JointForceMediaDBInfo;
import com.moft.xfapply.model.entity.KeyPartDeviceDBInfo;
import com.moft.xfapply.model.entity.KeyPartStorageDBInfo;
import com.moft.xfapply.model.entity.KeyPartStructureDBInfo;
import com.moft.xfapply.model.entity.KeyUnitDBInfo;
import com.moft.xfapply.model.entity.KeyUnitMediaDBInfo;
import com.moft.xfapply.model.entity.KeyUnitRegionPartitionDBInfo;
import com.moft.xfapply.model.entity.MapSearchInfo;
import com.moft.xfapply.model.entity.MaterialEquipmentDBInfo;
import com.moft.xfapply.model.entity.MaterialExtinguishingAgentDBInfo;
import com.moft.xfapply.model.entity.MaterialFireVehicleDBInfo;
import com.moft.xfapply.model.entity.MaterialMediaDBInfo;
import com.moft.xfapply.model.entity.PartitionDeviceDBInfo;
import com.moft.xfapply.model.entity.PartitionFloorDBInfo;
import com.moft.xfapply.model.entity.PartitionStorageDBInfo;
import com.moft.xfapply.model.entity.PartitionStorageZoneDBInfo;
import com.moft.xfapply.model.entity.PartitionStructureDBInfo;
import com.moft.xfapply.model.entity.PartitionUnitDBInfo;
import com.moft.xfapply.model.entity.StationBattalionDBInfo;
import com.moft.xfapply.model.entity.StationBrigadeDBInfo;
import com.moft.xfapply.model.entity.StationDetachmentDBInfo;
import com.moft.xfapply.model.entity.StationMediaDBInfo;
import com.moft.xfapply.model.entity.StationSquadronDBInfo;
import com.moft.xfapply.model.entity.SysDepartmentDBInfo;
import com.moft.xfapply.model.entity.WatersourceCraneDBInfo;
import com.moft.xfapply.model.entity.WatersourceFireHydrantDBInfo;
import com.moft.xfapply.model.entity.WatersourceFirePoolDBInfo;
import com.moft.xfapply.model.entity.WatersourceMediaDBInfo;
import com.moft.xfapply.model.entity.WatersourceNatureIntakeDBInfo;
import com.moft.xfapply.model.external.AppDefs;
import com.moft.xfapply.model.external.base.IMediaDTO;
import com.moft.xfapply.model.external.base.ISourceDataDTO;
import com.moft.xfapply.model.external.dto.EnterpriseFourceDTO;
import com.moft.xfapply.model.external.dto.EquipmentDTO;
import com.moft.xfapply.model.external.dto.ExtinguishingAgentDTO;
import com.moft.xfapply.model.external.dto.FireFacilityAutoAlarmSystemDTO;
import com.moft.xfapply.model.external.dto.FireFacilityAutoSprinklerSystemDTO;
import com.moft.xfapply.model.external.dto.FireFacilityBaseDTO;
import com.moft.xfapply.model.external.dto.FireFacilityCompartmentDTO;
import com.moft.xfapply.model.external.dto.FireFacilityControlRoomDTO;
import com.moft.xfapply.model.external.dto.FireFacilityDeviceDTO;
import com.moft.xfapply.model.external.dto.FireFacilityEvacuationBroadcastDTO;
import com.moft.xfapply.model.external.dto.FireFacilityEvacuationLiftDTO;
import com.moft.xfapply.model.external.dto.FireFacilityEvacuationSafetyExitDTO;
import com.moft.xfapply.model.external.dto.FireFacilityEvacuationStairwayDTO;
import com.moft.xfapply.model.external.dto.FireFacilityEvacuationWaitingFloorDTO;
import com.moft.xfapply.model.external.dto.FireFacilityExtinguishingGasDTO;
import com.moft.xfapply.model.external.dto.FireFacilityExtinguishingPowderDTO;
import com.moft.xfapply.model.external.dto.FireFacilityFoamFixedMonitorDTO;
import com.moft.xfapply.model.external.dto.FireFacilityFoamGeneratorDTO;
import com.moft.xfapply.model.external.dto.FireFacilityFoamHydrantDTO;
import com.moft.xfapply.model.external.dto.FireFacilityFoamPumpRoomDTO;
import com.moft.xfapply.model.external.dto.FireFacilityFoamSemifixedDeviceDTO;
import com.moft.xfapply.model.external.dto.FireFacilitySmokeProofExitDTO;
import com.moft.xfapply.model.external.dto.FireFacilitySmokeProofSystemDTO;
import com.moft.xfapply.model.external.dto.FireFacilitySteamFixedSystemDTO;
import com.moft.xfapply.model.external.dto.FireFacilitySteamSemifixedSystemDTO;
import com.moft.xfapply.model.external.dto.FireFacilityWaterCoolingSystemDTO;
import com.moft.xfapply.model.external.dto.FireFacilityWaterFixedMonitorDTO;
import com.moft.xfapply.model.external.dto.FireFacilityWaterHydrantIndoorDTO;
import com.moft.xfapply.model.external.dto.FireFacilityWaterHydrantOutdoorDTO;
import com.moft.xfapply.model.external.dto.FireFacilityWaterPoolDTO;
import com.moft.xfapply.model.external.dto.FireFacilityWaterPumpAdapterDTO;
import com.moft.xfapply.model.external.dto.FireFacilityWaterPumpRoomDTO;
import com.moft.xfapply.model.external.dto.FireFacilityWaterSemifixedDeviceDTO;
import com.moft.xfapply.model.external.dto.FireFacilityWaterSpraySystemDTO;
import com.moft.xfapply.model.external.dto.FireFacilityWaterTankDTO;
import com.moft.xfapply.model.external.dto.FireVehicleDTO;
import com.moft.xfapply.model.external.dto.JointForceDTO;
import com.moft.xfapply.model.external.dto.KeyPartBaseDTO;
import com.moft.xfapply.model.external.dto.KeyPartDeviceDTO;
import com.moft.xfapply.model.external.dto.KeyPartStorageDTO;
import com.moft.xfapply.model.external.dto.KeyPartStructureDTO;
import com.moft.xfapply.model.external.dto.KeyUnitDetailDTO;
import com.moft.xfapply.model.external.dto.PartitionBaseDTO;
import com.moft.xfapply.model.external.dto.PartitionDeviceDTO;
import com.moft.xfapply.model.external.dto.PartitionFloorDTO;
import com.moft.xfapply.model.external.dto.PartitionStorageDTO;
import com.moft.xfapply.model.external.dto.PartitionStorageZoneDTO;
import com.moft.xfapply.model.external.dto.PartitionStructureDTO;
import com.moft.xfapply.model.external.dto.PartitionUnitDTO;
import com.moft.xfapply.model.external.dto.StationBattalionDTO;
import com.moft.xfapply.model.external.dto.StationBrigadeDTO;
import com.moft.xfapply.model.external.dto.StationDetachmentDTO;
import com.moft.xfapply.model.external.dto.StationSquadronDTO;
import com.moft.xfapply.model.external.dto.WatersourceCraneDTO;
import com.moft.xfapply.model.external.dto.WatersourceFireHydrantDTO;
import com.moft.xfapply.model.external.dto.WatersourceFirePoolDTO;
import com.moft.xfapply.model.external.dto.WatersourceNatureIntakeDTO;
import com.moft.xfapply.model.external.result.SimpleRestResult;
import com.moft.xfapply.utils.CoordinateUtil;
import com.moft.xfapply.utils.DbUtil;
import com.moft.xfapply.utils.FileUtil;
import com.moft.xfapply.utils.GsonUtil;
import com.moft.xfapply.utils.LogUtils;
import com.moft.xfapply.utils.ReflectionUtil;
import com.moft.xfapply.utils.StringUtil;
import com.moft.xfapply.utils.Utils;

import net.tsz.afinal.FinalDb;
import net.tsz.afinal.db.sqlite.DbModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Request;

public class GeomEleBussiness {
    private static GeomEleBussiness instance = null;
    private OnGeomEleListener listener;
    private Map<String, String> methodMap;
    private Map<String, Class<?>> classMap;
    private Map<String, Class<?>> mediaClassMap;
    private Map<String, Class<?>> partitionClassMap;
    private Map<String, Class<?>> keyPartClassMap;
    private Map<String, Class<?>> fireFacilityClassMap;
    private Map<String, Class<?>> dtoClassMap;
    private Map<String, Class<?>> dtoPartitionClassMap;
    private Map<String, Class<?>> dtoKeyPartClassMap;
    private Map<String, Class<?>> dtoFireFacilityClassMap;

    private GeomEleBussiness() {
        methodMap = new HashMap<>();
        methodMap.put(AppDefs.CompEleType.WATERSOURCE_FIRE_HYDRANT.toString(), Constant.METHOD_GET_WATERSOURCE_FIRE_HYDRANT);
        methodMap.put(AppDefs.CompEleType.WATERSOURCE_CRANE.toString(), Constant.METHOD_GET_WATERSOURCE_CRANE);
        methodMap.put(AppDefs.CompEleType.WATERSOURCE_FIRE_POOL.toString(), Constant.METHOD_GET_WATERSOURCE_FIRE_POOL);
        methodMap.put(AppDefs.CompEleType.WATERSOURCE_NATURE_INTAKE.toString(), Constant.METHOD_GET_WATERSOURCE_NATURE_INTAKE);
        methodMap.put(AppDefs.CompEleType.KEY_UNIT.toString(), Constant.METHOD_GET_KEY_UNIT);
        methodMap.put(AppDefs.CompEleType.FIRE_VEHICLE.toString(), Constant.METHOD_GET_FIRE_VEHICLES);
        methodMap.put(AppDefs.CompEleType.EQUIPMENT.toString(), Constant.METHOD_GET_EQUIPMENT);
        methodMap.put(AppDefs.CompEleType.EXTINGUISHING_AGENT.toString(), Constant.METHOD_GET_EXTINGUISHING_AGENT);
        methodMap.put(AppDefs.CompEleType.BRIGADE.toString(), Constant.METHOD_GET_STATION_BRIGADES);
        methodMap.put(AppDefs.CompEleType.DETACHMENT.toString(), Constant.METHOD_GET_STATION_DETACHMENTS);
        methodMap.put(AppDefs.CompEleType.BATTALION.toString(), Constant.METHOD_GET_STATION_BATTALIONS);
        methodMap.put(AppDefs.CompEleType.SQUADRON.toString(), Constant.METHOD_GET_STATION_SQUADRONS);
        methodMap.put(AppDefs.CompEleType.ENTERPRISE_FOURCE.toString(), Constant.METHOD_GET_ENTERPRISE_FOURCES);
        methodMap.put(AppDefs.CompEleType.JOINT_FORCE.toString(), Constant.METHOD_GET_JOINT_FORCE);

        classMap = new HashMap<>();
        classMap.put(AppDefs.CompEleType.WATERSOURCE_FIRE_HYDRANT.toString(), WatersourceFireHydrantDBInfo.class);
        classMap.put(AppDefs.CompEleType.WATERSOURCE_CRANE.toString(), WatersourceCraneDBInfo.class);
        classMap.put(AppDefs.CompEleType.WATERSOURCE_FIRE_POOL.toString(), WatersourceFirePoolDBInfo.class);
        classMap.put(AppDefs.CompEleType.WATERSOURCE_NATURE_INTAKE.toString(), WatersourceNatureIntakeDBInfo.class);
        classMap.put(AppDefs.CompEleType.KEY_UNIT.toString(), KeyUnitDBInfo.class);
        classMap.put(AppDefs.CompEleType.FIRE_VEHICLE.toString(), MaterialFireVehicleDBInfo.class);
        classMap.put(AppDefs.CompEleType.EQUIPMENT.toString(), MaterialEquipmentDBInfo.class);
        classMap.put(AppDefs.CompEleType.EXTINGUISHING_AGENT.toString(), MaterialExtinguishingAgentDBInfo.class);
        classMap.put(AppDefs.CompEleType.BRIGADE.toString(), StationBrigadeDBInfo.class);
        classMap.put(AppDefs.CompEleType.DETACHMENT.toString(), StationDetachmentDBInfo.class);
        classMap.put(AppDefs.CompEleType.BATTALION.toString(), StationBattalionDBInfo.class);
        classMap.put(AppDefs.CompEleType.SQUADRON.toString(), StationSquadronDBInfo.class);
        classMap.put(AppDefs.CompEleType.ENTERPRISE_FOURCE.toString(), EnterpriseFourceDBInfo.class);
        classMap.put(AppDefs.CompEleType.JOINT_FORCE.toString(), JointForceDBInfo.class);

        mediaClassMap = new HashMap<>();
        mediaClassMap.put(AppDefs.CompEleType.WATERSOURCE_FIRE_HYDRANT.toString(), WatersourceMediaDBInfo.class);
        mediaClassMap.put(AppDefs.CompEleType.WATERSOURCE_CRANE.toString(), WatersourceMediaDBInfo.class);
        mediaClassMap.put(AppDefs.CompEleType.WATERSOURCE_FIRE_POOL.toString(), WatersourceMediaDBInfo.class);
        mediaClassMap.put(AppDefs.CompEleType.WATERSOURCE_NATURE_INTAKE.toString(), WatersourceMediaDBInfo.class);
        mediaClassMap.put(AppDefs.CompEleType.KEY_UNIT.toString(), KeyUnitMediaDBInfo.class);
        mediaClassMap.put(AppDefs.CompEleType.FIRE_VEHICLE.toString(), MaterialMediaDBInfo.class);
        mediaClassMap.put(AppDefs.CompEleType.EQUIPMENT.toString(), MaterialMediaDBInfo.class);
        mediaClassMap.put(AppDefs.CompEleType.EXTINGUISHING_AGENT.toString(), MaterialMediaDBInfo.class);
        mediaClassMap.put(AppDefs.CompEleType.BRIGADE.toString(), StationMediaDBInfo.class);
        mediaClassMap.put(AppDefs.CompEleType.DETACHMENT.toString(), StationMediaDBInfo.class);
        mediaClassMap.put(AppDefs.CompEleType.BATTALION.toString(), StationMediaDBInfo.class);
        mediaClassMap.put(AppDefs.CompEleType.SQUADRON.toString(), StationMediaDBInfo.class);
        mediaClassMap.put(AppDefs.CompEleType.ENTERPRISE_FOURCE.toString(), StationMediaDBInfo.class);
        mediaClassMap.put(AppDefs.CompEleType.JOINT_FORCE.toString(), JointForceMediaDBInfo.class);

        partitionClassMap = new HashMap<>();
//        partitionClassMap.put(AppDefs.KeyUnitPartitionType.PARTITION_STRUCTURE_MONO.toString(), PartitionStructureMonoDBInfo.class);
//        partitionClassMap.put(AppDefs.KeyUnitPartitionType.PARTITION_STRUCTURE_GROUP.toString(), PartitionStructureGroupDBInfo.class);
        partitionClassMap.put(AppDefs.KeyUnitPartitionType.PARTITION_STRUCTURE.toString(), PartitionStructureDBInfo.class);
        partitionClassMap.put(AppDefs.KeyUnitPartitionType.PARTITION_DEVICE.toString(), PartitionDeviceDBInfo.class);
        partitionClassMap.put(AppDefs.KeyUnitPartitionType.PARTITION_STORAGE_ZONE.toString(), PartitionStorageZoneDBInfo.class);
        partitionClassMap.put(AppDefs.KeyUnitPartitionType.PARTITION_FLOOR.toString(), PartitionFloorDBInfo.class);
        partitionClassMap.put(AppDefs.KeyUnitPartitionType.PARTITION_STORAGE.toString(), PartitionStorageDBInfo.class);
        partitionClassMap.put(AppDefs.KeyUnitPartitionType.PARTITION_UNIT.toString(), PartitionUnitDBInfo.class);

        keyPartClassMap = new HashMap<>();
        keyPartClassMap.put(AppDefs.KeyUnitKeyPartType.KEY_PART_STRUCTURE.toString(), KeyPartStructureDBInfo.class);
        keyPartClassMap.put(AppDefs.KeyUnitKeyPartType.KEY_PART_DEVICE.toString(), KeyPartDeviceDBInfo.class);
        keyPartClassMap.put(AppDefs.KeyUnitKeyPartType.KEY_PART_STORAGE.toString(), KeyPartStorageDBInfo.class);

        fireFacilityClassMap = new HashMap<>();
        fireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_EVACUATION_SAFETY_EXIT.toString(), FireFacilityEvacuationSafetyExitDBInfo.class);
        fireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_EVACUATION_STAIRWAY.toString(), FireFacilityEvacuationStairwayDBInfo.class);
        fireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_EVACUATION_LIFT.toString(), FireFacilityEvacuationLiftDBInfo.class);
        fireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_EVACUATION_WAITING_FLOOR.toString(), FireFacilityEvacuationWaitingFloorDBInfo.class);
        fireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_EVACUATION_BROADCAST.toString(), FireFacilityEvacuationBroadcastDBInfo.class);
        fireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_WATER_PUMP_ROOM.toString(), FireFacilityWaterPumpRoomDBInfo.class);
        fireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_WATER_TANK.toString(), FireFacilityWaterTankDBInfo.class);
        fireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_WATER_POOL.toString(), FireFacilityWaterPoolDBInfo.class);
        fireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_WATER_HYDRANT_INDOOR.toString(), FireFacilityWaterHydrantIndoorDBInfo.class);
        fireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_WATER_HYDRANT_OUTDOOR.toString(), FireFacilityWaterHydrantOutdoorDBInfo.class);
        fireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_WATER_PUMP_ADAPTER.toString(), FireFacilityWaterPumpAdapterDBInfo.class);
        fireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_WATER_SPRAY_SYSTEM.toString(), FireFacilityWaterSpraySystemDBInfo.class);
        fireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_WATER_COOLING_SYSTEM.toString(), FireFacilityWaterCoolingSystemDBInfo.class);
        fireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_WATER_FIXED_MONITOR.toString(), FireFacilityWaterFixedMonitorDBInfo.class);
        fireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_WATER_SEMIFIXED_DEVICE.toString(), FireFacilityWaterSemifixedDeviceDBInfo.class);


        fireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_FOAM_PUMP_ROOM.toString(), FireFacilityFoamPumpRoomDBInfo.class);
        fireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_FOAM_HYDRANT.toString(), FireFacilityFoamHydrantDBInfo.class);
        fireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_FOAM_FIXED_MONITOR.toString(), FireFacilityFoamFixedMonitorDBInfo.class);
        fireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_FOAM_GENERATOR.toString(), FireFacilityFoamGeneratorDBInfo.class);
        fireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_FOAM_SEMIFIXED_DEVICE.toString(), FireFacilityFoamSemifixedDeviceDBInfo.class);

        fireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_STEAM_FIXED_SYSTEM.toString(), FireFacilitySteamFixedSystemDBInfo.class);
        fireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_STEAM_SEMIFIXED_SYSTEM.toString(), FireFacilitySteamSemifixedSystemDBInfo.class);
        fireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_CONTROL_ROOM.toString(), FireFacilityControlRoomDBInfo.class);
        fireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_SMOKE_PROOF_EXIT.toString(), FireFacilitySmokeProofExitDBInfo.class);
        fireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_SMOKE_PROOF_SYSTEM.toString(), FireFacilitySmokeProofSystemDBInfo.class);
        fireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_COMPARTMENT.toString(), FireFacilityCompartmentDBInfo.class);
        fireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_EXTINGUISHING_GAS.toString(), FireFacilityExtinguishingGasDBInfo.class);
        fireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_EXTINGUISHING_POWDER.toString(), FireFacilityExtinguishingPowderDBInfo.class);
        fireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_DEVICE.toString(), FireFacilityDeviceDBInfo.class);
        fireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_AUTO_ALARM_SYSTEM.toString(), FireFacilityAutoAlarmSystemDBInfo.class);
        fireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_AUTO_SPRINKLER_SYSTEM.toString(), FireFacilityAutoSprinklerSystemDBInfo.class);

        dtoClassMap = new HashMap<>();
        dtoClassMap.put(AppDefs.CompEleType.WATERSOURCE_FIRE_HYDRANT.toString(), WatersourceFireHydrantDTO.class);
        dtoClassMap.put(AppDefs.CompEleType.WATERSOURCE_CRANE.toString(), WatersourceCraneDTO.class);
        dtoClassMap.put(AppDefs.CompEleType.WATERSOURCE_FIRE_POOL.toString(), WatersourceFirePoolDTO.class);
        dtoClassMap.put(AppDefs.CompEleType.WATERSOURCE_NATURE_INTAKE.toString(), WatersourceNatureIntakeDTO.class);
        dtoClassMap.put(AppDefs.CompEleType.KEY_UNIT.toString(), KeyUnitDetailDTO.class);
        dtoClassMap.put(AppDefs.CompEleType.FIRE_VEHICLE.toString(), FireVehicleDTO.class);
        dtoClassMap.put(AppDefs.CompEleType.EQUIPMENT.toString(), EquipmentDTO.class);
        dtoClassMap.put(AppDefs.CompEleType.EXTINGUISHING_AGENT.toString(), ExtinguishingAgentDTO.class);
        dtoClassMap.put(AppDefs.CompEleType.BRIGADE.toString(), StationBrigadeDTO.class);
        dtoClassMap.put(AppDefs.CompEleType.DETACHMENT.toString(), StationDetachmentDTO.class);
        dtoClassMap.put(AppDefs.CompEleType.BATTALION.toString(), StationBattalionDTO.class);
        dtoClassMap.put(AppDefs.CompEleType.SQUADRON.toString(), StationSquadronDTO.class);
        dtoClassMap.put(AppDefs.CompEleType.ENTERPRISE_FOURCE.toString(), EnterpriseFourceDTO.class);
        dtoClassMap.put(AppDefs.CompEleType.JOINT_FORCE.toString(), JointForceDTO.class);

        dtoPartitionClassMap = new HashMap<>();
//        dtoPartitionClassMap.put(AppDefs.KeyUnitPartitionType.PARTITION_STRUCTURE_MONO.toString(), PartitionStructureMonoDTO.class);
//        dtoPartitionClassMap.put(AppDefs.KeyUnitPartitionType.PARTITION_STRUCTURE_GROUP.toString(), PartitionStructureGroupDTO.class);
        dtoPartitionClassMap.put(AppDefs.KeyUnitPartitionType.PARTITION_STRUCTURE.toString(), PartitionStructureDTO.class);
        dtoPartitionClassMap.put(AppDefs.KeyUnitPartitionType.PARTITION_DEVICE.toString(), PartitionDeviceDTO.class);
        dtoPartitionClassMap.put(AppDefs.KeyUnitPartitionType.PARTITION_STORAGE_ZONE.toString(), PartitionStorageZoneDTO.class);
        dtoPartitionClassMap.put(AppDefs.KeyUnitPartitionType.PARTITION_FLOOR.toString(), PartitionFloorDTO.class);
        dtoPartitionClassMap.put(AppDefs.KeyUnitPartitionType.PARTITION_STORAGE.toString(), PartitionStorageDTO.class);
        dtoPartitionClassMap.put(AppDefs.KeyUnitPartitionType.PARTITION_UNIT.toString(), PartitionUnitDTO.class);

        dtoKeyPartClassMap = new HashMap<>();
        dtoKeyPartClassMap.put(AppDefs.KeyUnitKeyPartType.KEY_PART_STRUCTURE.toString(), KeyPartStructureDTO.class);
        dtoKeyPartClassMap.put(AppDefs.KeyUnitKeyPartType.KEY_PART_DEVICE.toString(), KeyPartDeviceDTO.class);
        dtoKeyPartClassMap.put(AppDefs.KeyUnitKeyPartType.KEY_PART_STORAGE.toString(), KeyPartStorageDTO.class);

        dtoFireFacilityClassMap = new HashMap<>();
        dtoFireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_EVACUATION_SAFETY_EXIT.toString(), FireFacilityEvacuationSafetyExitDTO.class);
        dtoFireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_EVACUATION_STAIRWAY.toString(), FireFacilityEvacuationStairwayDTO.class);
        dtoFireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_EVACUATION_LIFT.toString(), FireFacilityEvacuationLiftDTO.class);
        dtoFireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_EVACUATION_WAITING_FLOOR.toString(), FireFacilityEvacuationWaitingFloorDTO.class);
        dtoFireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_EVACUATION_BROADCAST.toString(), FireFacilityEvacuationBroadcastDTO.class);
        dtoFireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_WATER_PUMP_ROOM.toString(), FireFacilityWaterPumpRoomDTO.class);
        dtoFireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_WATER_TANK.toString(), FireFacilityWaterTankDTO.class);
        dtoFireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_WATER_POOL.toString(), FireFacilityWaterPoolDTO.class);
        dtoFireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_WATER_HYDRANT_INDOOR.toString(), FireFacilityWaterHydrantIndoorDTO.class);
        dtoFireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_WATER_HYDRANT_OUTDOOR.toString(), FireFacilityWaterHydrantOutdoorDTO.class);
        dtoFireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_WATER_PUMP_ADAPTER.toString(), FireFacilityWaterPumpAdapterDTO.class);
        dtoFireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_WATER_SPRAY_SYSTEM.toString(), FireFacilityWaterSpraySystemDTO.class);
        dtoFireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_WATER_COOLING_SYSTEM.toString(), FireFacilityWaterCoolingSystemDTO.class);
        dtoFireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_WATER_FIXED_MONITOR.toString(), FireFacilityWaterFixedMonitorDTO.class);
        dtoFireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_WATER_SEMIFIXED_DEVICE.toString(), FireFacilityWaterSemifixedDeviceDTO.class);


        dtoFireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_FOAM_PUMP_ROOM.toString(), FireFacilityFoamPumpRoomDTO.class);
        dtoFireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_FOAM_HYDRANT.toString(), FireFacilityFoamHydrantDTO.class);
        dtoFireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_FOAM_FIXED_MONITOR.toString(), FireFacilityFoamFixedMonitorDTO.class);
        dtoFireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_FOAM_GENERATOR.toString(), FireFacilityFoamGeneratorDTO.class);
        dtoFireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_FOAM_SEMIFIXED_DEVICE.toString(), FireFacilityFoamSemifixedDeviceDTO.class);

        dtoFireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_STEAM_FIXED_SYSTEM.toString(), FireFacilitySteamFixedSystemDTO.class);
        dtoFireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_STEAM_SEMIFIXED_SYSTEM.toString(), FireFacilitySteamSemifixedSystemDTO.class);
        dtoFireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_CONTROL_ROOM.toString(), FireFacilityControlRoomDTO.class);
        dtoFireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_SMOKE_PROOF_EXIT.toString(), FireFacilitySmokeProofExitDTO.class);
        dtoFireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_SMOKE_PROOF_SYSTEM.toString(), FireFacilitySmokeProofSystemDTO.class);
        dtoFireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_COMPARTMENT.toString(), FireFacilityCompartmentDTO.class);
        dtoFireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_EXTINGUISHING_GAS.toString(), FireFacilityExtinguishingGasDTO.class);
        dtoFireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_EXTINGUISHING_POWDER.toString(), FireFacilityExtinguishingPowderDTO.class);
        dtoFireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_DEVICE.toString(), FireFacilityDeviceDTO.class);
        dtoFireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_AUTO_ALARM_SYSTEM.toString(), FireFacilityAutoAlarmSystemDTO.class);
        dtoFireFacilityClassMap.put(AppDefs.KeyUnitFireFacilityType.FIRE_FACILITY_AUTO_SPRINKLER_SYSTEM.toString(), FireFacilityAutoSprinklerSystemDTO.class);
    }

    public static GeomEleBussiness getInstance() {
        if (instance == null) {
            instance = new GeomEleBussiness();
        }
        return instance;
    }

    public void setListener(OnGeomEleListener listener) {
        this.listener = listener;
    }

    public Map<String, Class<?>> getElementClsMap() {
        return classMap;
    }

    public Map<String, Class<?>> getMediaClsMap() {
        return mediaClassMap;
    }

    public Map<String, Class<?>> getDTOClsMap() {
        return dtoClassMap;
    }

    public ArrayList<GeomElementDBInfo> loadGeomElement(QueryCondition sc) {
        return loadGeomElement(sc, -1, -1);
    }

    public ArrayList<GeomElementDBInfo> loadGeomElement(QueryCondition condition,
                                                        int limit, int offset) {
        ArrayList<GeomElementDBInfo> wsList = new ArrayList<>();

        String dbKey = LvApplication.getInstance().getCityName();
        FinalDb mDb = DbUtil.getInstance().getDB(dbKey);

        String strWhere = "";
        if (StringUtil.isEmpty(condition.getFilterSQL())) {
            if(!StringUtil.isEmpty(condition.getLxSubCode())) {
                //对应其他消防队伍分为4类
                String strSQL = getEnterpriseTypeSql(condition.getLxSubCode()) + " AND " + getWhere(condition);
                strWhere = getSpecGeomEleUuidsByFilter(mDb, strSQL);
                if(strWhere == null) {
                    return wsList;
                }
            } else {
                strWhere = getWhere(condition);
            }
        } else {
            String strSQL = condition.getFilterSQL() + " AND " + getWhere(condition);
            strWhere = getSpecGeomEleUuidsByFilter(mDb, strSQL);
            if(strWhere == null) {
                return wsList;
            }
        }

        String strLimit = getLimit(limit, offset);
        if (StringUtil.isEmpty(strLimit)) {
            wsList = (ArrayList<GeomElementDBInfo>)
                    mDb.findAllByWhere(GeomElementDBInfo.class, strWhere);
        } else {
            wsList = (ArrayList<GeomElementDBInfo>)
                    mDb.findAllByWhere(GeomElementDBInfo.class, strWhere, strLimit);
        }
        if(wsList != null && wsList.size() > 0) {
            //对应其他消防队伍分为4类
            replaceEnterpriseElement(wsList);
            if (QueryCondition.TYPE_POSITION == condition.getType()) {
                int maxDis = PrefSetting.getInstance().getLoadDisNumber();

                List<GeomElementDBInfo> outrangList = new ArrayList<>();
                for (GeomElementDBInfo geo : wsList) {
                    if (geo.getDistance(condition.getLng(), condition.getLat()) > maxDis) {
                        outrangList.add(geo);
                    }
                }
                wsList.removeAll(outrangList);
            }
        }

        if (QueryCondition.TYPE_KEY == condition.getType()) {
            int nReadyCount = 0;
            if (wsList != null) {
                nReadyCount = wsList.size();
            }
            if (nReadyCount < limit) {
                StandardBussiness sb = StandardBussiness.getInstance();
                List<WhpViewInfo> whpList = sb.getHazardChemicalList(condition, limit - nReadyCount, 0);
                for (WhpViewInfo whp : whpList) {
                    GeomElementDBInfo geo = new GeomElementDBInfo();
                    geo.setUuid(whp.getUuid());
                    geo.setEleType(Constant.HAZARD_CHEMICAL);
                    geo.setCode("" + whp.getType());
                    geo.setPrimaryValue1(whp.getName());
                    geo.setPrimaryValue2(whp.getProperty1());
                    geo.setPrimaryValue3(whp.getProperty2());
                    wsList.add(geo);
                }
            }
        }
        return wsList;
    }

    public List<GeomElementDBInfo> loadGeomElementByUuids(QueryCondition condition, List<String> geoUuidList, int limit, int offset) {
        List<GeomElementDBInfo> wsList = new ArrayList<GeomElementDBInfo>();

        String dbKey = LvApplication.getInstance().getCityName();
        FinalDb mDb = DbUtil.getInstance().getDB(dbKey);

        String strWhere = "";
        if (StringUtil.isEmpty(condition.getFilterSQL())) {
            if(!StringUtil.isEmpty(condition.getLxSubCode())) {
                //对应其他消防队伍分为4类
                String strSQL = getEnterpriseTypeSql(condition.getLxSubCode()) + " AND " + getWhere(condition);
                strWhere = getSpecGeomEleUuidsByFilter(mDb, strSQL);
                if(strWhere == null) {
                    return wsList;
                }
            } else {
                strWhere = getWhere(condition);
            }
        } else {
            String strSQL = condition.getFilterSQL() + " AND " + getWhere(condition);
            strWhere = getSpecGeomEleUuidsByFilter(mDb, strSQL);
            if(strWhere == null) {
                return wsList;
            }
        }
        String strLimit = getLimit(limit, offset);
        String sql = getGeoUuidSortSql(strWhere, geoUuidList, strLimit);
        if(!StringUtil.isEmpty(sql)) {
            List<DbModel> dmList = mDb.findDbModelListBySQL(sql);
            wsList = convertToGeomElementList(dmList);
        }
        return  wsList;
    }

    private String getGeoUuidSortSql(String strWhere, List<String> geoUuidList, String strLimit) {
        String retSql = "";
        String caseCondition = "";
        String uuids = "";
        if(geoUuidList != null && geoUuidList.size() > 0) {
            for(int index = 0; index < geoUuidList.size(); ++index) {
                uuids += "'" + geoUuidList.get(index) + "',";
                caseCondition += String.format("WHEN '%s' THEN %d ", geoUuidList.get(index), index);
            }
            if(!StringUtil.isEmpty(uuids)) {
                uuids = uuids.substring(0, uuids.length() - 1);
                caseCondition = caseCondition.substring(0, caseCondition.length() - 1);
                retSql = String.format("SELECT %s, CASE department_uuid %s END AS no_index FROM %s WHERE %s and %s ORDER BY no_index ASC",
                        getGeoAttributeName(), caseCondition, Constant.TABLE_NAME_GEOM_ELEMENT, strWhere,
                        String.format(Constant.SEARCH_COND_DEPARTMENT_UUID_IN, uuids)) + (StringUtil.isEmpty(strLimit) ? "" : String.format(", %s", strLimit));
            }
        }
        return retSql;
    }

    private String getGeoAttributeName() {
        return "uuid, code, department_uuid AS departmentUuid, ele_type AS eleType, deleted, create_person AS createPerson, update_person AS updatePerson, create_date AS createDate," +
                " update_date AS updateDate, remark, name, belongto_group AS belongtoGroup, address, geometry_text AS geometryText, longitude, latitude, data_quality AS dataQuality, geometry_type AS geometryType, " +
                "primary_value1 AS primaryValue1, primary_value2 AS primaryValue2, primary_value3 AS primaryValue3, primary_value4 AS primaryValue4, keywords";
    }

    private List<GeomElementDBInfo> convertToGeomElementList(List<DbModel> dmList) {
        List<GeomElementDBInfo> retList = new ArrayList<>();
        if(dmList != null && dmList.size() > 0) {
            for (DbModel dm : dmList) {
                Gson gson = GsonUtil.create();
                String jsonStr = gson.toJson(dm.getDataMap());
                GeomElementDBInfo info = gson.fromJson(jsonStr, GeomElementDBInfo.class);
                if(info != null) {
                    retList.add(info);
                }
            }
        }
        return retList;
    }

    //对应其他消防队伍分为4类
    private String getSpecGeomEleUuidsByFilter(FinalDb mDb, String strSQL) {
        List<DbModel> dmList =  mDb.findDbModelListBySQL(strSQL);
        if (dmList == null || dmList.size() == 0) {
            return null;
        }

        List<String> uuids = new ArrayList<String>();
        for (DbModel dm :dmList) {
            uuids.add(dm.getString("uuid"));
        }
        return String.format("uuid in (%s)", list2String(uuids));
    }

    //对应其他消防队伍分为4类
    private String getEnterpriseTypeSql(String enterpriseType) {
        return String.format("SELECT uuid FROM %s WHERE %s AND %s", Constant.TABLE_NAME_ENTERPRISE_FOURCE, Constant.SEARCH_COND_NOT_DELETED,
                String.format(Constant.SEARCH_COND_TYPE, enterpriseType));
    }

    //对应其他消防队伍分为4类
    private void replaceEnterpriseElement(List<GeomElementDBInfo> list) {
        if(list.size() > 0) {
            String uuids = "";
            for(GeomElementDBInfo item : list) {
                if(AppDefs.CompEleType.ENTERPRISE_FOURCE.toString().equals(item.getEleType())) {
                    uuids += "'" + item.getUuid() + "',";
                }
            }
            if(uuids.length() > 1) {
                uuids = uuids.substring(0, uuids.length() - 1);
                String dbKey = LvApplication.getInstance().getCityName();
                FinalDb mDb = DbUtil.getInstance().getDB(dbKey);
                List<EnterpriseFourceDBInfo> dbList = mDb.findAll(EnterpriseFourceDBInfo.class, String.format(Constant.SEARCH_COND_UUIDS, uuids));
                for(GeomElementDBInfo item : list) {
                    if(AppDefs.CompEleType.ENTERPRISE_FOURCE.toString().equals(item.getEleType())) {
                        EnterpriseFourceDBInfo dbInfo = findEnterpriseFourceInfo(dbList, item.getUuid());
                        if(dbInfo != null) {
                            item.setSubType(dbInfo.getType());
                        }
                    }
                }
            }
        }
    }

    private EnterpriseFourceDBInfo findEnterpriseFourceInfo(List<EnterpriseFourceDBInfo> dbList, String uuid) {
        EnterpriseFourceDBInfo info = null;
        if(!StringUtil.isEmpty(uuid) && dbList != null && dbList.size() > 0) {
            for(EnterpriseFourceDBInfo item : dbList) {
                if(uuid.equals(item.getUuid())) {
                    info = item;
                    break;
                }
            }
        }
        return info;
    }

    public int getGeomElementCount(QueryCondition condition) {
        int count = 0;

        String dbKey = LvApplication.getInstance().getCityName();
        FinalDb mDb = DbUtil.getInstance().getDB(dbKey);

        if (StringUtil.isEmpty(condition.getFilterSQL())) {
            if(!StringUtil.isEmpty(condition.getLxSubCode())) {
                String strSQL = getEnterpriseTypeSql(condition.getLxSubCode()) + " AND " + getWhere(condition);
                List<DbModel> dmList =  mDb.findDbModelListBySQL(strSQL);

                count = dmList == null ? 0 : dmList.size();
            } else {
                String strWhere = getWhere(condition);
                String tableName = Constant.TABLE_NAME_GEOM_ELEMENT;
                String strSQL = String.format("select count(*) as cnt from %s where %s", tableName, strWhere);

                DbModel dm = mDb.findDbModelBySQL(strSQL);
                count = Integer.valueOf((String) dm.get("cnt"));
            }
        } else {
            String strSQL = condition.getFilterSQL() + " AND " + getWhere(condition);
            List<DbModel> dmList =  mDb.findDbModelListBySQL(strSQL);

            count = dmList == null ? 0 : dmList.size();
        }

        // 地图关键字查询时，需要查询危化品，其他场合（如重点单位列表的关键字查询）则不需要，因此增加条件 StringUtil.isEmpty(condition.getSylx())
        if (!StringUtil.isEmpty(condition.getKey()) && StringUtil.isEmpty(condition.getSylx())) {
            StandardBussiness sb = StandardBussiness.getInstance();
            int whpCount = sb.getHazardChemicalCount(condition.getKey());
            count += whpCount;

            whpCount = sb.getMsdsCount(condition.getKey());
            count += whpCount;
        }

        return count;
    }

    public List<Dictionary> loadGeoElementTypeList(QueryCondition sc) {
        List<Dictionary> eleList = new ArrayList<>();

        String dbKey = LvApplication.getInstance().getCityName();
        FinalDb mDb = DbUtil.getInstance().getDB(dbKey);

        QueryCondition qc = new QueryCondition();
        qc.copy(sc);
        qc.setSylx("");
        qc.setSylxCode("");

        String strWhere = getWhere(qc);
        String strSQL = String.format("select distinct ele_type from geometry_element where %s", strWhere);

        Map<String, String> eleTypeMap = null;

        List<DbModel> dmList = mDb.findDbModelListBySQL(strSQL);
        for (DbModel dm : dmList) {
            if (eleTypeMap == null) {
                eleTypeMap = new HashMap<>();
                for (Dictionary dic : LvApplication.getInstance().getEleTypeGeoDicList()) {
                    eleTypeMap.put(dic.getCode(), dic.getValue());
                }
            }
            String code = dm.getString("ele_type");
            //对应其他消防队伍分为4类
            if(AppDefs.CompEleType.ENTERPRISE_FOURCE.toString().equals(code)) {
                List<Dictionary> enterpriseTypeList = getEnterpriseTypeList(eleTypeMap, strWhere);
                if(enterpriseTypeList != null && enterpriseTypeList.size() > 0) {
                    eleList.addAll(enterpriseTypeList);
                }
            } else {
                String name = eleTypeMap.get(code);
                if (!StringUtil.isEmpty(name)) {
                    eleList.add(new Dictionary(name, code));
                }
            }
        }

        return eleList;
    }

    //对应其他消防队伍分为4类
    private List<Dictionary> getEnterpriseTypeList(Map<String, String> eleTypeMap, String strWhere) {
        List<Dictionary> eleList = new ArrayList<>();
        String strSQL = String.format("select distinct type from %s where %s", Constant.TABLE_NAME_ENTERPRISE_FOURCE, strWhere);
        String dbKey = LvApplication.getInstance().getCityName();
        FinalDb mDb = DbUtil.getInstance().getDB(dbKey);
        List<DbModel> dmList = mDb.findDbModelListBySQL(strSQL);
        for (DbModel dm : dmList) {
            String code = AppDefs.CompEleType.ENTERPRISE_FOURCE.toString() + dm.getString("type");
            String name = eleTypeMap.get(code);
            if (!StringUtil.isEmpty(name)) {
                eleList.add(new Dictionary(name, code));
            }
        }
        return eleList;
    }

    private Map<String, Object> convertToMapKey(Map<String, Object> dataMap) {
        Map<String, Object> retMap = new HashMap<>();
        if(dataMap != null && dataMap.size() > 0) {
            for(Map.Entry<String, Object> item : dataMap.entrySet()) {
                String attribute = item.getKey();
                String[] subStr = attribute.split("_");
                if(subStr.length > 1) {
                    attribute = subStr[0];
                    for(int index = 1; index < subStr.length; ++index) {
                        attribute += StringUtil.captureName(subStr[index]);
                    }
                }
                retMap.put(attribute, item.getValue());
            }
        }
        return retMap;
    }

    private String sqlReplaceAll(String where, String alias) {
        String[] content = where.split(" and| or");
        //修改bug，追加sql语句：and，or，in之前属性字段，追加表的重命名。例如：name = ''改为：A.name = ''
        Pattern pattern = Pattern.compile("([a-zA-Z0-9_]*) (=|<>|in) ");
        for(String item : content) {
            Matcher matcher = pattern.matcher(item);
            if (matcher.find()) {
                String str = matcher.group(1);
                where = where.replace(str, alias + "." + str);
            }
        }
        return where;
    }

    public ArrayList<GeomElementType> getGeomElementSummary(String strWhere) {
        ArrayList<GeomElementType> wsList = new ArrayList<GeomElementType>();

        String dbKey = LvApplication.getInstance().getCityName();
        FinalDb mDb = DbUtil.getInstance().getDB(dbKey);

        String strSQL = String.format("select %s, count(%s) as cnt from %s where ",
                Constant.PROPERTY_ELE_TYPE, Constant.PROPERTY_ELE_TYPE, Constant.TABLE_NAME_GEOM_ELEMENT);
        if (!StringUtil.isEmpty(strWhere)) {
            strSQL += strWhere;
            //支持省离线数据库查询
            if("0".equals(PrefUserInfo.getInstance().getUserInfo("department_grade"))) {
                strSQL += String.format(" and %s group by %s order by %s asc", Constant.SEARCH_COND_NOT_DELETED, Constant.PROPERTY_ELE_TYPE, Constant.PROPERTY_ELE_TYPE);
            } else {
                strSQL += String.format(" and %s and %s group by %s order by %s asc", Constant.SEARCH_COND_NOT_DELETED, Constant.SEARCH_COND_JOINT_BRIGADE_NOT_EQUALS, Constant.PROPERTY_ELE_TYPE, Constant.PROPERTY_ELE_TYPE);
            }
        } else {
            //支持省离线数据库查询
            if("0".equals(PrefUserInfo.getInstance().getUserInfo("department_grade"))) {
                strSQL += String.format("%s group by %s order by %s asc", Constant.SEARCH_COND_NOT_DELETED, Constant.PROPERTY_ELE_TYPE, Constant.PROPERTY_ELE_TYPE);
            } else {
                strSQL += String.format("%s and %s group by %s order by %s asc", Constant.SEARCH_COND_NOT_DELETED, Constant.SEARCH_COND_JOINT_BRIGADE_NOT_EQUALS, Constant.PROPERTY_ELE_TYPE, Constant.PROPERTY_ELE_TYPE);
            }
        }

        List<DbModel> dmList = mDb.findDbModelListBySQL(strSQL);

        for (DbModel dm : dmList) {
            int count = Integer.valueOf((String) dm.get("cnt"));
            String type = (String) dm.get(Constant.PROPERTY_ELE_TYPE);
            GeomElementType wsType = new GeomElementType();
            wsType.setCount(count);
            wsType.setTypeCode(type);
            //对应其他消防队伍分为4类
            if(AppDefs.CompEleType.ENTERPRISE_FOURCE.toString().equals(wsType.getTypeCode())) {
                List<GeomElementType> enterpriseList = getEnterpriseSummary(strWhere);
                if(enterpriseList != null && enterpriseList.size() > 0) {
                    wsList.addAll(enterpriseList);
                }
            } else {
                wsList.add(wsType);
            }
        }

        return wsList;
    }

    //对应其他消防队伍分为4类
    private List<GeomElementType> getEnterpriseSummary(String strWhere) {
        List<GeomElementType> wsList = new ArrayList<GeomElementType>();
        String dbKey = LvApplication.getInstance().getCityName();
        FinalDb mDb = DbUtil.getInstance().getDB(dbKey);
        String strSQL = String.format("select %s, count(%s) as cnt from %s where ",
                Constant.PROPERTY_TYPE, Constant.PROPERTY_TYPE, Constant.TABLE_NAME_ENTERPRISE_FOURCE);
        if (!StringUtil.isEmpty(strWhere)) {
            strSQL += strWhere;
            strSQL += String.format(" and %s group by %s order by %s asc", Constant.SEARCH_COND_NOT_DELETED, Constant.PROPERTY_TYPE, Constant.PROPERTY_TYPE);
        } else {
            strSQL += String.format("%s group by %s order by %s asc", Constant.SEARCH_COND_NOT_DELETED, Constant.PROPERTY_TYPE, Constant.PROPERTY_TYPE);
        }
        List<DbModel> dmList = mDb.findDbModelListBySQL(strSQL);

        if(dmList != null) {
            for (DbModel dm : dmList) {
                int count = Integer.valueOf((String) dm.get("cnt"));
                String type = (String) dm.get(Constant.PROPERTY_TYPE);
                GeomElementType wsType = new GeomElementType();
                wsType.setCount(count);
                wsType.setTypeCode(AppDefs.CompEleType.ENTERPRISE_FOURCE.toString() + type);
                wsList.add(wsType);
            }
        }
        return wsList;
    }

    public List<OrgDataCountSummary> getGeomElementCountByOrg(QueryCondition condition, int grade) {
        List<OrgDataCountSummary> orgSummaryList = new ArrayList<>();
        String dbKey = LvApplication.getInstance().getCityName();
        FinalDb mDb = DbUtil.getInstance().getDB(dbKey);
        String strSQL = getWhere(condition);
        List<DbModel> dmList = mDb.findDbModelListBySQL(String.format("select department_uuid, count(department_uuid) as cnt from geometry_element where %s group by department_uuid", strSQL));
        if(dmList != null && dmList.size() > 0) {
            String departmentUuid = condition.getZqzdCode();
            if (StringUtil.isEmpty(condition.getZqzdCode())) {
                PrefUserInfo pui = PrefUserInfo.getInstance();
                departmentUuid = pui.getUserInfo("department_uuid");
            }
            List<DepartmentInfo> departmentInfos = getDescendantDepartment(departmentUuid);

            if (departmentInfos != null && departmentInfos.size() > 0) {
                String orgUuids = "";
                for (DepartmentInfo item : departmentInfos) {
                    if (item.getGrade() != null && item.getGrade().intValue() == grade) {
                        if (!StringUtil.isEmpty(item.getUuid())) {
                            orgUuids += "'" + item.getUuid() + "',";
                        }
                    }
                }

                if (!StringUtil.isEmpty(orgUuids)) {
                    orgUuids = orgUuids.substring(0, orgUuids.length() - 1);
                    orgSummaryList = getOrgDataCountSummaryList(orgUuids, grade);
                    if (orgSummaryList != null && orgSummaryList.size() > 0) {
                        for (OrgDataCountSummary item : orgSummaryList) {
                            item.setCount(getOrgGeomElementCount(dmList, departmentInfos, item.getUuid()));
                        }
                    }
                }
            }
        }
        return orgSummaryList;
    }

    public List<DepartmentInfo> getDescendantDepartment(String departmentUuid) {
        List<DepartmentInfo> retList = new ArrayList<>();
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;

        if (currentapiVersion < Build.VERSION_CODES.LOLLIPOP) {
            // API 小于21即Android小于5.0，不支持with语法，因此要使用java代码处理实现
            List<SysDepartmentDBInfo> sysDepartment = findAllDepartment(departmentUuid, 1);  // ID:996389 【移动端】部分移动端设备执行递归SQL时发生异常 2019-5-28 王旭
            DepartmentInfo info = null;
            for (SysDepartmentDBInfo i : sysDepartment) {
                info = new DepartmentInfo();
                info.setUuid(i.getUuid());
                info.setParentUuid(i.getParentUuid());
                info.setGrade(i.getGrade());
                retList.add(info);
            }
        } else {
            String dbKey = LvApplication.getInstance().getCityName();
            FinalDb mDb = DbUtil.getInstance().getDB(dbKey);

            String strSQL = "WITH RECURSIVE depTree AS ( " +
                    " SELECT A.* " +
                    " FROM sys_department A " +
                    " WHERE uuid = " + "'" + departmentUuid + "'" +
                    " AND A.deleted = 0 " +
                    " UNION ALL " +
                    " SELECT K.* " +
                    " FROM sys_department K " +
                    " INNER JOIN depTree C ON C.uuid = K.parent_uuid " +
                    " WHERE K.deleted = 0 " +
                    " ) " +
                    " SELECT depTree.uuid, depTree.parent_uuid, depTree.grade " +
                    " FROM depTree";

            List<DbModel> dmList = mDb.findDbModelListBySQL(strSQL);

            for (DbModel dm : dmList) {
                DepartmentInfo info = new DepartmentInfo();
                info.setUuid(dm.getString("uuid"));
                info.setParentUuid(dm.getString("parent_uuid"));
                info.setGrade(dm.getInt("grade"));
                retList.add(info);
            }
        }

        return retList;
    }

    private List<OrgDataCountSummary> getOrgDataCountSummaryList(String uuids, int grade) {
        List<OrgDataCountSummary> retList = new ArrayList<>();
        String dbKey = LvApplication.getInstance().getCityName();
        FinalDb mDb = DbUtil.getInstance().getDB(dbKey);
        String stationTable = "station_brigade";
        if(grade == 1) {
            stationTable = "station_detachment";
        } else if(grade == 2) {
            stationTable = "station_battalion";
        } else if(grade == 3) {
            stationTable = "station_squadron";
        }
        String strSQL = String.format("SELECT uuid, department_uuid, name, type, address, longitude, latitude FROM %s WHERE " + Constant.SEARCH_COND_NOT_DELETED + " and department_uuid in (%s) ORDER BY code asc", stationTable, uuids);

        List<DbModel> dmList = mDb.findDbModelListBySQL(strSQL);
        if(dmList != null && dmList.size() > 0) {
            for (DbModel dm : dmList) {
                OrgDataCountSummary info = new OrgDataCountSummary();
                String departmentUuid = dm.getString("department_uuid");
                if(!StringUtil.isEmpty(departmentUuid) && !"null".equals(departmentUuid)) {
                    info.setUuid(departmentUuid);
                    String name = dm.getString("name");
                    if(!StringUtil.isEmpty(name) && !"null".equals(name)) {
                        info.setOrgName(name);
                    }
                    String type = dm.getString("type");
                    if(!StringUtil.isEmpty(type) && !"null".equals(type)) {
                        info.setType(type);
                    }
                    String address = dm.getString("address");
                    if(!StringUtil.isEmpty(address) && !"null".equals(address)) {
                        info.setAddress(address);
                    }
                    String latitude = dm.getString("latitude");
                    if(!StringUtil.isEmpty(latitude) && !"null".equals(latitude)) {
                        info.setLatitude(latitude);
                    }
                    String longitude = dm.getString("longitude");
                    if(!StringUtil.isEmpty(longitude) && !"null".equals(longitude)) {
                        info.setLongitude(longitude);
                    }
                    retList.add(info);
                }
            }
        }
        return retList;
    }

    private int getOrgGeomElementCount(List<DbModel> dmList, List<DepartmentInfo> departmentList, String departmentUuid) {
        int elementCount = 0;
        List<String> descendantDepartment = getDescendantDepartmentUuid(departmentList, departmentUuid);
        descendantDepartment.add(departmentUuid);

        for (DbModel item : dmList) {
            if(descendantDepartment.contains(item.getString("department_uuid"))) {
                Integer count = item.getInt("cnt");
                if (count != null) {
                    elementCount += count;
                }
            }
        }

        return elementCount;
    }

    private List<String> getDescendantDepartmentUuid(List<DepartmentInfo> departmentList, String departmentUuid) {
        List<String> retList = new ArrayList<>();

        if(!StringUtil.isEmpty(departmentUuid) && departmentList != null && departmentList.size() > 0) {
            for(DepartmentInfo item : departmentList) {
                if(departmentUuid.equals(item.getParentUuid())) {
                    retList.add(item.getUuid());
                    retList.addAll(getDescendantDepartmentUuid(departmentList, item.getUuid()));
                }
            }
        }
        return retList;
    }

    public LatLng getStationLatLng(String departmentUuid) {
        LatLng ret = null;
        if(!StringUtil.isEmpty(departmentUuid)) {
            String dbKey = LvApplication.getInstance().getCityName();
            FinalDb mDb = DbUtil.getInstance().getDB(dbKey);
            if(mDb != null) {
                List<GeomElementDBInfo> elementList = mDb.findAllByWhere(GeomElementDBInfo.class,
                        String.format("%s and %s and %s", Constant.SEARCH_COND_NOT_DELETED, String.format(Constant.SEARCH_COND_DEPARTMENT_UUID, departmentUuid), Constant.SEARCH_COND_STATION));
                if(elementList != null && elementList.size() > 0) {
                    GeomElementDBInfo info = elementList.get(0);
                    if(!StringUtil.isEmpty(info.getLatitude()) && !StringUtil.isEmpty(info.getLongitude())) {
                        Double latD = Utils.convertToDouble(info.getLatitude());
                        Double lngD = Utils.convertToDouble(info.getLongitude());
                        if (latD != null && lngD != null && latD.intValue() != 0 && lngD.intValue() != 0) {
                            ret = new LatLng(latD, lngD);
                        }
                    }
                }
            }
        }
        return ret;
    }

    private String getWhere(QueryCondition sc) {
        //支持省离线数据库查询
        String strWhere = "0".equals(PrefUserInfo.getInstance().getUserInfo("department_grade")) ?
                Constant.SEARCH_COND_NOT_DELETED : String.format("%s and %s", Constant.SEARCH_COND_NOT_DELETED, Constant.SEARCH_COND_JOINT_BRIGADE_NOT_EQUALS);

        if (sc.getType() == QueryCondition.TYPE_KEY) {
            strWhere += getWhere(sc.getKey());
        } else if (sc.getType() == QueryCondition.TYPE_POSITION) {
            strWhere += getWhere(sc.getLat(), sc.getLng());
        }

        if (!StringUtil.isEmpty(sc.getSylxCode())) {
            if ("00".equals(sc.getSylxCode())) { //所有水源
                strWhere += String.format(" and %s", String.format(Constant.SEARCH_COND_ELE_TYPE_SY, "WATERSOURCE_"));
            } else {
                strWhere += String.format(" and %s", String.format(Constant.SEARCH_COND_ELE_TYPE, sc.getSylxCode()));
            }
        }

        if (!StringUtil.isEmpty(sc.getZqzdCode()) && !"00".equals(sc.getZqzdCode())) {
            List<String> allDepartments = getDepartmentUuidAllChildren(sc.getZqzdCode(), 1);
            String departments = list2String(allDepartments);
            strWhere += String.format(" and (%s)", String.format(Constant.SEARCH_COND_DEPARTMENT_UUID_IN, departments));
        }

        if (sc.getType() != QueryCondition.TYPE_KEY) {
            strWhere += getWhere(sc.getKey());
        }

        return strWhere;
    }

    public String getWhereDepartments(String code) {
        String strWhere = "";

        if (StringUtil.isEmpty(code) || "00".equals(code)) {
            return strWhere;
        }

        List<String> allDepartments = getDepartmentUuidAllChildren(code, 1);
        String departments = list2String(allDepartments);
        strWhere += String.format(" (%s)", String.format(Constant.SEARCH_COND_DEPARTMENT_UUID_IN, departments));

        return strWhere;
    }

    private String getWhere(String key) {
        String strWhere = "";

        if (!StringUtil.isEmpty(key)) {
            key = StringUtil.handleTransferChar(key); //ID 925915 【移动终端】地图页面搜索时，因输入法问题，导致sql中包含分隔符引发异常。 王旭 2019-03-29
            strWhere = String.format(Constant.SEARCH_COND_KEY_WORD, key);
        }

        return strWhere;
    }

    private String getWhere(double lat, double lng) {
        String dis = PrefSetting.getInstance().getLoadDis();
        double disI = Double.valueOf(dis);

        CoordinateUtil cu = CoordinateUtil.getInstance();
        double radLat = disI * cu.getLatSub();
        double radLng = disI * cu.getLngSub(lat);

        double latMax = lat + radLat;
        double latMin = lat - radLat;
        double lngMax = lng + radLng;
        double lngMin = lng - radLng;

        String strWhere = String.format(Constant.SEARCH_COND_POS_RANGE, latMin, latMax, lngMin, lngMax);

        return strWhere;
    }

    private String getLimit(int limit, int offset) {
        String strLimit = "";

        if (limit != -1) {
            strLimit = "update_date desc limit " + limit + " offset " + offset; // code -> update_date  ID:894112 数据模块列表显示按照update降序排列，器材，灭火剂将显示的类型修改成名称。 2019-02-25 王旭
        }

        return strLimit;
    }

    public GeomElementDBInfo getGeomEleInfo(String dbKey, String uuid) {
        GeomElementDBInfo eleInfo = null;
        String strWhere = String.format(Constant.SEARCH_COND_UUID, uuid);

        FinalDb mDb = DbUtil.getInstance().getDB(dbKey);
        List<GeomElementDBInfo> eleList = mDb.findAllByWhere(GeomElementDBInfo.class, strWhere);

        if (eleList.size() > 0) {
            eleInfo = eleList.get(0);
        }

        return eleInfo;
    }

    public Map<String, GeomElementDBInfo> getGeomElementInfoByUuids(List<String> uuidList) {
        Map<String, GeomElementDBInfo> retMap = new LinkedHashMap<>();
        if(uuidList != null && uuidList.size() > 0) {
            String uuids = "";
            for(String uuid : uuidList) {
                uuids += "'" + uuid + "',";
            }
            if(uuids.length() > 1) {
                uuids = uuids.substring(0, uuids.length() - 1);
                FinalDb mDb = DbUtil.getInstance().getDB(LvApplication.getInstance().getCityName());
                List<GeomElementDBInfo> tempList = mDb.findAllByWhere(GeomElementDBInfo.class, String.format(Constant.SEARCH_COND_UUIDS, uuids));
                if(tempList != null && tempList.size() > 0) {
                    for(GeomElementDBInfo item : tempList) {
                        retMap.put(item.getUuid(), item);
                    }
                }
            }
        }
        return retMap;
    }

    public List<IPartitionInfo> getPartitionInfoByKeyUnitUuid(String city, String keyUnitUuid, boolean isHistory) {
        List<IPartitionInfo> retList = new ArrayList<>();
        List<KeyUnitRegionPartitionDBInfo> regionList = getRegionPartitions(city, keyUnitUuid, isHistory);

        for(KeyUnitRegionPartitionDBInfo item : regionList) {
            IPartitionInfo info = getPartition(city, item.getPartitionUuid(), isHistory);
            if(info != null && (info.getDeleted() == null || !info.getDeleted())) {
                retList.add(info);
            }
        }
        return retList;
    }

    public IPartitionInfo getPartition(String cityCode, String uuid, boolean isHistory) {
        IPartitionInfo info = null;
        String dbKey = PrefUserInfo.getInstance().getUserInfo(Constant.DB_NAME_USER);
        if (!isHistory) {
            //支持省离线数据库查询
            dbKey = LvApplication.getInstance().getCityName();
        }
        FinalDb mDb = DbUtil.getInstance().getDB(dbKey);
        for (Class<?> cls : partitionClassMap.values()) {
            info = (IPartitionInfo)mDb.findById(uuid, cls);

            if(info != null) {
                break;
            }
        }
        return info;
    }

    public List<KeyUnitRegionPartitionDBInfo> getRegionPartitions(String cityCode, String uuid, boolean isHistory) {
        List<KeyUnitRegionPartitionDBInfo> retList = new ArrayList<>();

        String dbKey = PrefUserInfo.getInstance().getUserInfo(Constant.DB_NAME_USER);
        if (!isHistory) {
            //支持省离线数据库查询
            dbKey = LvApplication.getInstance().getCityName();
        }
        FinalDb mDb = DbUtil.getInstance().getDB(dbKey);
        String condition = String.format(Constant.SEARCH_COND_KEY_REGION_UNIT_UUID, uuid);
        List<KeyUnitRegionPartitionDBInfo> keyUnitRegionPartitionDBList = mDb.findAllByWhere(KeyUnitRegionPartitionDBInfo.class, condition);

        if (keyUnitRegionPartitionDBList != null && keyUnitRegionPartitionDBList.size() > 0) {
            retList.addAll(keyUnitRegionPartitionDBList);
        }
        return retList;
    }

    //ID:B886058 19-02-21 【数据】查看详细重点单位界面，响应速度慢，黑屏。 王泉
    public List<IKeyPartInfo> getKeyPartInfoByPartitionUuids(String cityCode, String partitionUuids, boolean isHistory) {
        List<IKeyPartInfo> retList = new ArrayList<>();

        String strWhere = String.format("%s and %s", String.format(Constant.SEARCH_COND_PARTITION_UUIDS, partitionUuids), Constant.SEARCH_COND_NOT_DELETED);
        String dbKey = PrefUserInfo.getInstance().getUserInfo(Constant.DB_NAME_USER);

        if (!isHistory) {
            //支持省离线数据库查询
            dbKey = LvApplication.getInstance().getCityName();
        }
        FinalDb mDb = DbUtil.getInstance().getDB(dbKey);
        for (Class<?> cls : keyPartClassMap.values()) {
            List<IKeyPartInfo> keyPartList = (List<IKeyPartInfo>)mDb.findAllByWhere(cls, strWhere, "create_date asc, name asc");
            if(keyPartList != null) {
                retList.addAll(keyPartList);
            }
        }
        return retList;
    }

    public List<IPartitionInfo> getSubPartitionInfoByPartitionUuid(String cityCode, String partitionUuid, boolean isHistory) {
        List<IPartitionInfo> retList = new ArrayList<>();

        String strWhere = String.format("%s and %s", String.format(Constant.SEARCH_COND_BELONG_TO_PARTITON_UUID, partitionUuid), Constant.SEARCH_COND_NOT_DELETED);
        String dbKey = PrefUserInfo.getInstance().getUserInfo(Constant.DB_NAME_USER);

        if (!isHistory) {
            //支持省离线数据库查询
            dbKey = LvApplication.getInstance().getCityName();
        }
        FinalDb mDb = DbUtil.getInstance().getDB(dbKey);
        for (Class<?> cls : partitionClassMap.values()) {
            List<IPartitionInfo> partitionList = (List<IPartitionInfo>)mDb.findAllByWhere(cls, strWhere, "create_date asc, name asc");
            if(partitionList != null) {
                retList.addAll(partitionList);
            }
        }
        return retList;
    }

    public List<IKeyPartInfo> getKeyPartInfoByPartitionUuid(String cityCode, String partitionUuid, boolean isHistory) {
        List<IKeyPartInfo> retList = new ArrayList<>();

        String strWhere = String.format("%s and %s", String.format(Constant.SEARCH_COND_PARTITION_UUID, partitionUuid), Constant.SEARCH_COND_NOT_DELETED);
        String dbKey = PrefUserInfo.getInstance().getUserInfo(Constant.DB_NAME_USER);

        if (!isHistory) {
            //支持省离线数据库查询
            dbKey = LvApplication.getInstance().getCityName();
        }
        FinalDb mDb = DbUtil.getInstance().getDB(dbKey);
        for (Class<?> cls : keyPartClassMap.values()) {
            List<IKeyPartInfo> keyPartList = (List<IKeyPartInfo>)mDb.findAllByWhere(cls, strWhere, "create_date asc, name asc");
            if(keyPartList != null) {
                retList.addAll(keyPartList);
            }
        }
        return retList;
    }

    //ID:B886058 19-02-21 【数据】查看详细重点单位界面，响应速度慢，黑屏。 王泉
    public List<IFireFacilityInfo> getFireFacilityInfoByPartitionUuids(String cityCode, String partitionUuids, boolean isHistory) {
        List<IFireFacilityInfo> retList = new ArrayList<>();

        String strWhere = String.format("%s and %s", String.format(Constant.SEARCH_COND_PARTITION_UUIDS, partitionUuids), Constant.SEARCH_COND_NOT_DELETED);
        String dbKey = PrefUserInfo.getInstance().getUserInfo(Constant.DB_NAME_USER);

        if (!isHistory) {
            //支持省离线数据库查询
            dbKey = LvApplication.getInstance().getCityName();
        }
        FinalDb mDb = DbUtil.getInstance().getDB(dbKey);
        for (Class<?> cls : fireFacilityClassMap.values()) {
            List<IFireFacilityInfo> fireFacilityList = (List<IFireFacilityInfo>)mDb.findAllByWhere(cls, strWhere, "create_date asc, name asc");
            if(fireFacilityList != null) {
                retList.addAll(fireFacilityList);
            }
        }
        return retList;
    }

    public List<IFireFacilityInfo> getFireFacilityInfoByPartitionUuid(String cityCode, String partitionUuid, boolean isHistory) {
        List<IFireFacilityInfo> retList = new ArrayList<>();

        String strWhere = String.format("%s and %s", String.format(Constant.SEARCH_COND_PARTITION_UUID, partitionUuid), Constant.SEARCH_COND_NOT_DELETED);
        String dbKey = PrefUserInfo.getInstance().getUserInfo(Constant.DB_NAME_USER);

        if (!isHistory) {
            //支持省离线数据库查询
            dbKey = LvApplication.getInstance().getCityName();
        }
        FinalDb mDb = DbUtil.getInstance().getDB(dbKey);
        for (Class<?> cls : fireFacilityClassMap.values()) {
            List<IFireFacilityInfo> fireFacilityList = (List<IFireFacilityInfo>)mDb.findAllByWhere(cls, strWhere, "create_date asc, name asc");
            if(fireFacilityList != null) {
                retList.addAll(fireFacilityList);
            }
        }
        return retList;
    }

    public List<MaterialExtinguishingAgentDBInfo> getExtinguishingAgentInfosByStorageUuid(String cityCode, String storageUuid, boolean isHistory) {
        String strWhere = String.format("%s and %s", String.format(Constant.SEARCH_COND_STORAGE_UUID, storageUuid), Constant.SEARCH_COND_NOT_DELETED);
        String dbKey = PrefUserInfo.getInstance().getUserInfo(Constant.DB_NAME_USER);

        if (!isHistory) {
            //支持省离线数据库查询
            dbKey = LvApplication.getInstance().getCityName();
        }
        FinalDb mDb = DbUtil.getInstance().getDB(dbKey);
        List<MaterialExtinguishingAgentDBInfo> list = mDb.findAllByWhere(MaterialExtinguishingAgentDBInfo.class, strWhere, "name asc");

        return list;
    }

    public List<MaterialEquipmentDBInfo> getEquipmentInfosByStorageUuid(String cityCode, String storageUuid, boolean isHistory) {
        String strWhere = String.format("%s and %s", String.format(Constant.SEARCH_COND_STORAGE_UUID, storageUuid), Constant.SEARCH_COND_NOT_DELETED);
        String dbKey = PrefUserInfo.getInstance().getUserInfo(Constant.DB_NAME_USER);

        if (!isHistory) {
            //支持省离线数据库查询
            dbKey = LvApplication.getInstance().getCityName();
        }
        FinalDb mDb = DbUtil.getInstance().getDB(dbKey);
        List<MaterialEquipmentDBInfo> list = mDb.findAllByWhere(MaterialEquipmentDBInfo.class, strWhere, "name asc");

        return list;
    }

    public IGeomElementInfo getSpecGeomEleInfoByUuid(EleCondition ec) {
        String condition = String.format(Constant.SEARCH_COND_UUID, ec.getUuid());
        IGeomElementInfo retInfo = getSpecGeomEleInfo(ec, condition);
        return retInfo;
    }

    public IGeomElementInfo getSpecGeomEleInfo(EleCondition ec, String condition) {
        IGeomElementInfo retInfo = null;
        String dbKey = PrefUserInfo.getInstance().getUserInfo(Constant.DB_NAME_USER);

        if (!ec.getHistory()) {
            //支持省离线数据库查询
            dbKey = LvApplication.getInstance().getCityName();
        }
        FinalDb mDb = DbUtil.getInstance().getDB(dbKey);
        List<IGeomElementInfo> eleList = (List<IGeomElementInfo>)mDb.findAllByWhere(classMap.get(ec.getType()), condition);

        if (eleList != null && eleList.size() > 0) {
            retInfo = eleList.get(0);
        }

        return retInfo;
    }

    public List<IMediaInfo> getKeyUnitMediaInfosByCondition(String cityCode, String  condition, boolean isHistory) {
        List<IMediaInfo> mediaList = new ArrayList<IMediaInfo>();
        String dbKey = PrefUserInfo.getInstance().getUserInfo(Constant.DB_NAME_USER);

        if (!isHistory) {
            //支持省离线数据库查询
            dbKey = LvApplication.getInstance().getCityName();
        }
        FinalDb mDb = DbUtil.getInstance().getDB(dbKey);
        Class<?> cls = KeyUnitMediaDBInfo.class;
        List<IMediaInfo> mediaInfoList = (List<IMediaInfo>)mDb.findAllByWhere(cls, String.format("%s and %s", condition, Constant.SEARCH_COND_NOT_DELETED));
        if (mediaInfoList != null && mediaInfoList.size() > 0) {
            mediaList.addAll(mediaInfoList);
        }

        return mediaList;
    }

    public List<IMediaInfo> getKeyUnitPartMediaInfos(String city, String partType, String partUuid, boolean isHistory) {
        String condition = String.format("%s and %s and %s", String.format(Constant.SEARCH_COND_BELONG_TO_TYPE, partType),
                String.format(Constant.SEARCH_COND_BELONG_TO_UUID, partUuid), String.format(Constant.SEARCH_COND_CLASSIFICATION, AppDefs.KeyUnitMediaType.KU_MEDIA_IMAGE.toString()));

        ArrayList<IMediaInfo> attachFileList = getMediaInfosByCondition(city, KeyUnitMediaDBInfo.class, condition, isHistory);

        return attachFileList;
    }

    public List<IMediaInfo> getDescendantMediaInfos(String city, String eleType, String uuid, boolean isHistory) {
        List<IMediaInfo> mediaList = new ArrayList<>();

        if(mediaClassMap.containsKey(eleType)) {
            Class<?> cls = mediaClassMap.get(eleType);
            String condition = getQueryDescendantMediaUuidCondition(cls, eleType, uuid);
            if(!StringUtil.isEmpty(condition)) {
                mediaList = getMediaInfosByCondition(city, cls, condition, isHistory);
            }
        }
        return mediaList;
    }

    public List<MaterialMediaDBInfo> getMaterialMediaInfoByMaterialUuids(String city, String eleType, String uuids, boolean isHistory) {
        String dbKey = PrefUserInfo.getInstance().getUserInfo(Constant.DB_NAME_USER);
        String condition = String.format("%s and %s and %s", String.format(Constant.SEARCH_COND_TYPE, eleType), String.format(Constant.SEARCH_COND_MATERIAL_UUIDS, uuids),  Constant.SEARCH_COND_NOT_DELETED);

        if (!isHistory) {
            dbKey = LvApplication.getInstance().getCityName();
        }
        FinalDb mDb = DbUtil.getInstance().getDB(dbKey);
        List<MaterialMediaDBInfo> attachFileList = mDb.findAllByWhere(MaterialMediaDBInfo.class, condition);
        return attachFileList;
    }

    public List<IMediaInfo> getMediaInfos(String city, String eleType, String uuid, boolean isHistory) {
        List<IMediaInfo> mediaList = new ArrayList<>();

        if(mediaClassMap.containsKey(eleType)) {
            Class<?> cls = mediaClassMap.get(eleType);
            String condition = getQueryMediaUuidCondition(cls, eleType, uuid);
            if(!StringUtil.isEmpty(condition)) {
                mediaList = getMediaInfosByCondition(city, cls, condition, isHistory);
            }
        }
        return mediaList;
    }

    public ArrayList<IMediaInfo> getMediaInfosByCondition(String cityCode, Class<?> cls, String condition, boolean isHistory) {
        ArrayList<IMediaInfo> attachFileList = new ArrayList<IMediaInfo>();
        String dbKey = PrefUserInfo.getInstance().getUserInfo(Constant.DB_NAME_USER);

        if (!isHistory) {
            //支持省离线数据库查询
            dbKey = LvApplication.getInstance().getCityName();
        }
        FinalDb mDb = DbUtil.getInstance().getDB(dbKey);
        List<IMediaInfo> mediaInfoList = (List<IMediaInfo>)mDb.findAllByWhere(cls, String.format("%s and %s", condition, Constant.SEARCH_COND_NOT_DELETED));
        if (mediaInfoList != null && mediaInfoList.size() > 0) {
            attachFileList.addAll(mediaInfoList);
        }

        return attachFileList;
    }

    public void updateGeomElement(String eleType, String uuid, String cid) {
        if(methodMap.containsKey(eleType) && !StringUtil.isEmpty(uuid)) {
            String method = methodMap.get(eleType);
            requestUpdateGeomElement(method, eleType, uuid, cid);
        } else {
            handlerFailure("内部错误", cid);
        }
    }

    public void getGeomElement(String eleType, String uuid, OkHttpClientManager.ResultCallback callback) {
        if(methodMap.containsKey(eleType) && !StringUtil.isEmpty(uuid)) {
            String method = methodMap.get(eleType);
            OkHttpClientManager http = OkHttpClientManager.getInstance();
            http.getAsyn(http.convertToURL(String.format(method, uuid)), callback);
        }
    }

    public void deleteKeyUnitPartitions(String city, String uuid, boolean isHistory) {
        List<KeyUnitRegionPartitionDBInfo> regionPartitions = getRegionPartitions(city, uuid, isHistory);

        for(KeyUnitRegionPartitionDBInfo regionPartition : regionPartitions) {
            //TODO:暂定对策，先删除关联表的item，在删除分区数据（暂不考虑其他重点单位还关联此分区）
            deleteRegionPartition(city, uuid, regionPartition.getPartitionUuid(), isHistory);
            deletePartition(city, uuid, isHistory);
            deleteKeyPartsByPartitionUuid(city, regionPartition.getPartitionUuid(), isHistory);
            deleteFireFacilitysByPartitionUuid(city, regionPartition.getPartitionUuid(), isHistory);
        }
    }

    private void deleteRegionPartition(String cityCode, String uuid, String partitionUuid, boolean isHistory) {
        String dbKey = PrefUserInfo.getInstance().getUserInfo(Constant.DB_NAME_USER);

        if (!isHistory) {
            //支持省离线数据库查询
            dbKey = LvApplication.getInstance().getCityName();
        }
        FinalDb mDb = DbUtil.getInstance().getDB(dbKey);
        String condition = String.format(Constant.SEARCH_COND_KEY_UNIT_UUID_AND_PARTITON_UUID, uuid, partitionUuid);
        mDb.deleteByWhere(KeyUnitRegionPartitionDBInfo.class, condition);
    }

    private void deletePartition(String cityCode, String uuid, boolean isHistory) {
        String dbKey = PrefUserInfo.getInstance().getUserInfo(Constant.DB_NAME_USER);
        if (!isHistory) {
            //支持省离线数据库查询
            dbKey = LvApplication.getInstance().getCityName();
        }
        FinalDb mDb = DbUtil.getInstance().getDB(dbKey);
        String condition = String.format(Constant.SEARCH_COND_UUID, uuid);

        for (Class<?> cls : partitionClassMap.values()) {
            mDb.deleteByWhere(cls, condition);
        }
    }

    public void deleteKeyPartsByPartitionUuid(String cityCode, String partitionUuid, boolean isHistory) {
        String dbKey = PrefUserInfo.getInstance().getUserInfo(Constant.DB_NAME_USER);
        if (!isHistory) {
            //支持省离线数据库查询
            dbKey = LvApplication.getInstance().getCityName();
        }
        FinalDb mDb = DbUtil.getInstance().getDB(dbKey);
        String condition = String.format(Constant.SEARCH_COND_PARTITION_UUID, partitionUuid);
        for (Class<?> cls : keyPartClassMap.values()) {
            mDb.deleteByWhere(cls, condition);
        }
    }

    private void deleteFireFacilitysByPartitionUuid(String cityCode, String partitionUuid, boolean isHistory) {
        String dbKey = PrefUserInfo.getInstance().getUserInfo(Constant.DB_NAME_USER);
        if (!isHistory) {
            //支持省离线数据库查询
            dbKey = LvApplication.getInstance().getCityName();
        }
        FinalDb mDb = DbUtil.getInstance().getDB(dbKey);
        String condition = String.format(Constant.SEARCH_COND_PARTITION_UUID, partitionUuid);
        for (Class<?> cls : fireFacilityClassMap.values()) {
            mDb.deleteByWhere(cls, condition);
        }
    }

    public void deleteFireVehicleExtinguishingAgents(String cityCode, String storageUuid, boolean isHistory) {
        String dbKey = PrefUserInfo.getInstance().getUserInfo(Constant.DB_NAME_USER);
        if (!isHistory) {
            //支持省离线数据库查询
            dbKey = LvApplication.getInstance().getCityName();
        }
        FinalDb mDb = DbUtil.getInstance().getDB(dbKey);
        String condition = String.format(Constant.SEARCH_COND_STORAGE_UUID, storageUuid);
        mDb.deleteByWhere(MaterialExtinguishingAgentDBInfo.class, condition);
    }

    public void deleteFireVehicleEquipments(String cityCode, String storageUuid, boolean isHistory) {
        String dbKey = PrefUserInfo.getInstance().getUserInfo(Constant.DB_NAME_USER);
        if (!isHistory) {
            //支持省离线数据库查询
            dbKey = LvApplication.getInstance().getCityName();
        }
        FinalDb mDb = DbUtil.getInstance().getDB(dbKey);
        String condition = String.format(Constant.SEARCH_COND_STORAGE_UUID, storageUuid);
        mDb.deleteByWhere(MaterialEquipmentDBInfo.class, condition);
    }

    public Map<String, String> getWaterSourceAvailableState(List<GeomElementDBInfo> list) {
        Map<String, String> retMap = new HashMap<>();
        String[] waterSourceUuids = {"", "", "", ""};
        String[] waterSourceTable  = {"watersource_crane", "watersource_fire_hydrant", "watersource_fire_pool", "watersource_nature_intake"};
        for(GeomElementDBInfo item : list) {
            if(AppDefs.CompEleType.WATERSOURCE_CRANE.toString().equals(item.getEleType())) {
                waterSourceUuids[0] += "'" + item.getUuid() + "',";
            } else if(AppDefs.CompEleType.WATERSOURCE_FIRE_HYDRANT.toString().equals(item.getEleType())) {
                waterSourceUuids[1] += "'" + item.getUuid() + "',";
            } else if(AppDefs.CompEleType.WATERSOURCE_FIRE_POOL.toString().equals(item.getEleType())) {
                waterSourceUuids[2] += "'" + item.getUuid() + "',";
            } else if(AppDefs.CompEleType.WATERSOURCE_NATURE_INTAKE.toString().equals(item.getEleType())) {
                waterSourceUuids[3] += "'" + item.getUuid() + "',";
            }
        }
        for(int index = 0; index < waterSourceUuids.length; ++index) {
            String uuids = waterSourceUuids[index];
            if (!StringUtil.isEmpty(uuids)) {
                uuids = uuids.substring(0, uuids.length() - 1);
                Map<String, String> stateMap = getWaterSourceAvailableState(uuids, waterSourceTable[index]);
                if(stateMap != null && stateMap.size() > 0) {
                    retMap.putAll(stateMap);
                }
            }
        }
        return retMap;
    }

    public Map<String, String> getWaterSourceAvailableState(String uuids, String tableName) {
        Map<String, String> retMap = new HashMap<>();
        String dbKey = LvApplication.getInstance().getCityName();
        FinalDb mDb = DbUtil.getInstance().getDB(dbKey);

        String strSQL = String.format("SELECT uuid, available_state FROM %s WHERE " + Constant.SEARCH_COND_NOT_DELETED + " and uuid in (%s)", tableName, uuids);

        List<DbModel> dmList = mDb.findDbModelListBySQL(strSQL);
        if(dmList != null && dmList.size() > 0) {
            for (DbModel dm : dmList) {
                String uuid = dm.getString("uuid");
                String availableState = dm.getString("available_state");
                if (!StringUtil.isEmpty(uuid) && !"null".equals(uuid) &&
                        !StringUtil.isEmpty(availableState) && !"null".equals(availableState)) {
                    retMap.put(uuid, availableState);
                }
            }
        }
        return retMap;
    }

    public boolean isWaterSourceAvailable(GeomElementDBInfo item) {
        if (item == null) {
            return false;
        }
        String dbKey = LvApplication.getInstance().getCityName();
        FinalDb mDb = DbUtil.getInstance().getDB(dbKey);

        String waterSourceTable = "";
        if(AppDefs.CompEleType.WATERSOURCE_CRANE.toString().equals(item.getEleType())) {
            waterSourceTable = "watersource_crane";
        } else if(AppDefs.CompEleType.WATERSOURCE_FIRE_HYDRANT.toString().equals(item.getEleType())) {
            waterSourceTable = "watersource_fire_hydrant";
        } else if(AppDefs.CompEleType.WATERSOURCE_FIRE_POOL.toString().equals(item.getEleType())) {
            waterSourceTable = "watersource_fire_pool";
        } else if(AppDefs.CompEleType.WATERSOURCE_NATURE_INTAKE.toString().equals(item.getEleType())) {
            waterSourceTable = "watersource_nature_intake";
        }

        if (StringUtil.isEmpty(waterSourceTable)) {
            return true;
        }

        String strSQL = String.format("SELECT uuid, available_state FROM %s WHERE "
                + Constant.SEARCH_COND_NOT_DELETED
                + " and uuid = '%s'", waterSourceTable, item.getUuid());

        List<DbModel> dmList = mDb.findDbModelListBySQL(strSQL);
        if(dmList != null && dmList.size() > 0) {
            DbModel dm = dmList.get(0);
            String availableState = dm.getString("available_state");
            if ("SY_KYZT_01".equals(availableState)) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    private void deleteMediaInfos(String cityCode, String eleType, String uuid, boolean isHistory) {
        if(mediaClassMap.containsKey(eleType)) {
            String dbKey = PrefUserInfo.getInstance().getUserInfo(Constant.DB_NAME_USER);

            if (!isHistory) {
                //支持省离线数据库查询
                dbKey = LvApplication.getInstance().getCityName();
            }

            FinalDb mDb = DbUtil.getInstance().getDB(dbKey);
            Class<?> cls = mediaClassMap.get(eleType);
            String condition = getQueryMediaUuidCondition(cls, eleType, uuid);
            if(!StringUtil.isEmpty(condition)) {
                //如果是历史media，删除本地media文件
                if(isHistory) {
                    List<IMediaInfo> mediaList = getMediaInfosByCondition(cityCode, cls, condition, true);
                    for (IMediaInfo item : mediaList) {
                        FileUtil.deleteFile(item.getMediaUrl());
                    }
                }

                mDb.deleteByWhere(cls, condition);
            }
        }
    }

    private void deleteKeyUnitMediaInfos(String cityCode, String uuid, boolean isHistory) {
        String dbKey = PrefUserInfo.getInstance().getUserInfo(Constant.DB_NAME_USER);

        if (!isHistory) {
            //支持省离线数据库查询
            dbKey = LvApplication.getInstance().getCityName();
        }

        FinalDb mDb = DbUtil.getInstance().getDB(dbKey);
        String condition = String.format(Constant.SEARCH_COND_KEY_UNIT_UUID, uuid);
        //如果是历史media，删除本地media文件
        if (isHistory) {
            List<IMediaInfo> mediaList = getMediaInfosByCondition(cityCode, KeyUnitMediaDBInfo.class, condition, true);
            for (IMediaInfo item : mediaList) {
                FileUtil.deleteFile(item.getMediaUrl());
            }
        }

        mDb.deleteByWhere(KeyUnitMediaDBInfo.class, condition);
    }

    //cid说明：为callback id用来区分唯一,无返回标识区分的话不需要赋值
    private void requestUpdateGeomElement(String method, final String eleType, String uuid, final String cid) {
        OkHttpClientManager http = OkHttpClientManager.getInstance();
        http.getAsyn(http.convertToURL(String.format(method, uuid)), new OkHttpClientManager.ResultCallback<SimpleRestResult>() {
            @Override
            public void onError(Request request, Exception e) {
                if(listener != null) {
                    listener.onFailure("更新失败，请确认是否网络异常！", cid);
                }
            }

            @Override
            public void onResponse(SimpleRestResult result) {
                if (result != null && result.isSuccess()) {
                    try {
                        Gson gson = GsonUtil.create();
                        if (result.getData() == null) {
                            handlerFailure("内部错误，数据不存在", cid);
                        } else {
                            String jsonStr = gson.toJson(result.getData());
                            ISourceDataDTO elementInfo = null;
                            if (AppDefs.CompEleType.KEY_UNIT.toString().equals(eleType)) {
                                KeyUnitDetailDTO keyUnitDetailDTO = gson.fromJson(jsonStr, KeyUnitDetailDTO.class);
                                elementInfo = keyUnitDetailDTO.getKeyUnitDTO();
                                if (saveLocalGeomEleInfo(classMap.get(elementInfo.getEleType()), elementInfo)) {
                                    saveLocalKeyUnitPartInfos(elementInfo.getBelongtoGroup(), elementInfo.getUuid(), keyUnitDetailDTO);
                                } else {
                                    handlerFailure("内部错误，类型不匹配", cid);
                                }
                            } else if (AppDefs.CompEleType.FIRE_VEHICLE.toString().equals(eleType)) {
                                elementInfo = gson.fromJson(jsonStr, FireVehicleDTO.class);
                                if (saveLocalGeomEleInfo(classMap.get(elementInfo.getEleType()), elementInfo)) {
                                    saveLocalFireVehiclePartInfos(elementInfo.getBelongtoGroup(), elementInfo.getUuid(), ((FireVehicleDTO) elementInfo));
                                } else {
                                    handlerFailure("内部错误，类型不匹配", cid);
                                }
                            } else {
                                elementInfo = (ISourceDataDTO) gson.fromJson(jsonStr, dtoClassMap.get(eleType));
                                if (!saveLocalGeomEleInfo(classMap.get(elementInfo.getEleType()), elementInfo)) {
                                    handlerFailure("内部错误，类型不匹配", cid);
                                }
                            }

                            if (listener != null) {
                                listener.onSuccess(cid);
                            }
                        }
                    } catch (Exception ex) {
                        handlerFailure("内部错误", cid);
                    }
                } else {
                    String msg = "内部错误";
                    if(result != null) {
                        msg = result.getMessage();
                    }
                    handlerFailure(msg, cid);
                }
            }
        });
    }

    private void handlerFailure(String msg, String cid) {
        if(listener != null) {
            listener.onFailure(msg, cid);
        }
    }

    public void updateCommonGeomEleInfo(GeomElementDBInfo info) {
        try {
            //支持省离线数据库查询
            String dbKey = LvApplication.getInstance().getCityName();
            FinalDb db = DbUtil.getInstance().getDB(dbKey);
            db.update(info);
        }catch (Exception e) {

        }
    }

    private boolean saveLocalGeomEleInfo(Class<?> cls, ISourceDataDTO elementInfo) {
        if(cls == null) {
            return false;
        }
        //支持省离线数据库查询
        String dbKey = LvApplication.getInstance().getCityName();
        EleCondition ec = new EleCondition();
        ec.setUuid(elementInfo.getUuid());
        ec.setType(elementInfo.getEleType());
        ec.setCityCode(elementInfo.getBelongtoGroup());
        ec.setHistory(false);
        IGeomElementInfo regionGeomEleDBInfo = getSpecGeomEleInfoByUuid(ec);
        //保存离线数据
        try {
            if (regionGeomEleDBInfo == null) {
                regionGeomEleDBInfo = (IGeomElementInfo)cls.newInstance();
            }
            ReflectionUtil.convertToObj(regionGeomEleDBInfo, elementInfo);
            //DTO对象转换为DB精度
            regionGeomEleDBInfo.setLatitude(StringUtil.get(elementInfo.getLatitude()));
            regionGeomEleDBInfo.setLongitude(StringUtil.get(elementInfo.getLongitude()));
            saveGeomEleDBInfo(elementInfo.getUuid(), cls, regionGeomEleDBInfo, dbKey);
        }catch (Exception e) {
            e.printStackTrace();
        }
        //保存Common数据
        GeomElementDBInfo commonGeomEleDBInfo = getGeomEleInfo(dbKey, elementInfo.getUuid());
        if(commonGeomEleDBInfo == null) {
            commonGeomEleDBInfo = new GeomElementDBInfo();
        }
        ReflectionUtil.convertToObj(commonGeomEleDBInfo, elementInfo);
        //DTO对象转换为DB精度
        commonGeomEleDBInfo.setLatitude(StringUtil.get(elementInfo.getLatitude()));
        commonGeomEleDBInfo.setLongitude(StringUtil.get(elementInfo.getLongitude()));
        //清空关键字属性，获取列表时重新取得
        commonGeomEleDBInfo.setPrimaryValue1("");
        commonGeomEleDBInfo.setPrimaryValue2("");
        commonGeomEleDBInfo.setPrimaryValue3("");
        commonGeomEleDBInfo.setPrimaryValue4("");

        saveGeomEleDBInfo(elementInfo.getUuid(), GeomElementDBInfo.class, commonGeomEleDBInfo, dbKey);
        //保存离线数据附件数据
        saveMediaDBInfos(elementInfo.getBelongtoGroup(), elementInfo.getEleType(), elementInfo.getUuid(), elementInfo.getMediaInfos(), false);
        //保存User数据(通过审核表获取SnapshotUuid，并更新User数据)???
        return true;
    }

    private void saveGeomEleDBInfo(String uuid, Class<?> cls, Object geomEleInfo, String dbKey) {
        FinalDb db = DbUtil.getInstance().getDB(dbKey);

        try {
            String strWhere = String.format(Constant.SEARCH_COND_UUID, uuid);
            List<Object> eleList = (List<Object>) db.findAllByWhere(cls, strWhere);

            if (eleList.isEmpty()) {
                db.save(geomEleInfo);
            } else {
                db.update(geomEleInfo);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveLocalKeyUnitPartInfos(String group, String eleUuid, KeyUnitDetailDTO keyUnitDetailDTO) {
        //同步更新离线数据与User数据(应用版不更新历史记录)
        List<IMediaDTO> mediaInfos = convertToMediaDTOs(keyUnitDetailDTO.getKeyUnitMediaDTOs());

        deleteKeyUnitPartitions(group, eleUuid, false);
        saveKeyUnitPartitions(group, eleUuid, convertToPartitionDTOs(keyUnitDetailDTO.getPartitionBaseDTOs()), false);
        saveKeyUnitFireFacilitys(group, convertToFireFacilityDTOs(keyUnitDetailDTO.getFireFacilityDTOs()), false);
        saveKeyUnitKeyParts(group, convertToKeyPartDTOs(keyUnitDetailDTO.getKeyPartBaseDTOs()), false);
        saveKeyUnitMediaDBInfos(group, eleUuid, mediaInfos, false);
    }

    private void saveKeyUnitPartitions(String group, String uuid, List<PartitionBaseDTO> list, boolean isHistory) {
        if(list != null && list.size() > 0) {
            for(PartitionBaseDTO item : list) {
                try {
                    if (partitionClassMap.containsKey(item.getPartitionType())) {
                        Class<?> cls = partitionClassMap.get(item.getPartitionType());
                        IPartitionInfo info = (IPartitionInfo) cls.newInstance();

                        ReflectionUtil.convertToObj(info, item);
                        //DTO对象转换为DB精度
                        info.setLatitude(StringUtil.get(item.getLatitude()));
                        info.setLongitude(StringUtil.get(item.getLongitude()));
                        //同步保存离线数据与User数据
                        savePartitionInfo(group, uuid, info, cls, isHistory);
                    }
                } catch (Exception e) {

                }
            }
        }
    }

    private void saveKeyUnitKeyParts(String city, List<KeyPartBaseDTO> list, boolean isHistory) {
        if(list != null && list.size() > 0) {
            for (KeyPartBaseDTO item : list) {
                try {
                    if (keyPartClassMap.containsKey(item.getKeyPartType())) {
                        Class<?> cls = keyPartClassMap.get(item.getKeyPartType());
                        IKeyPartInfo info = (IKeyPartInfo)cls.newInstance();

                        ReflectionUtil.convertToObj(info, item);
                        //DTO对象转换为DB精度
                        info.setLatitude(StringUtil.get(item.getLatitude()));
                        info.setLongitude(StringUtil.get(item.getLongitude()));
                        //同步保存离线数据与User数据
                        saveKeyPartInfo(city, info, cls, isHistory);
                    }
                } catch (Exception e) {

                }
            }
        }
    }

    private void saveKeyUnitFireFacilitys(String city, List<FireFacilityBaseDTO> list, boolean isHistory) {
        if(list != null && list.size() > 0) {
            for (FireFacilityBaseDTO item : list) {
                try {
                    if (fireFacilityClassMap.containsKey(item.getFacilityType())) {
                        Class<?> cls = fireFacilityClassMap.get(item.getFacilityType());
                        IFireFacilityInfo info = (IFireFacilityInfo)cls.newInstance();

                        ReflectionUtil.convertToObj(info, item);
                        //DTO对象转换为DB精度
                        info.setLatitude(StringUtil.get(item.getLatitude()));
                        info.setLongitude(StringUtil.get(item.getLongitude()));
                        //同步保存离线数据与User数据
                        saveFireFacilityInfo(city, info, cls, isHistory);
                    }
                } catch (Exception e) {

                }
            }
        }
    }

    public void savePartitionInfo(String cityCode, String keyUnitUuid, IPartitionInfo info, Class<?> cls, boolean isHistory) {
        String dbKey = PrefUserInfo.getInstance().getUserInfo(Constant.DB_NAME_USER);
        if (!isHistory) {
            //支持省离线数据库查询
            dbKey = LvApplication.getInstance().getCityName();
        }
        FinalDb mDb = DbUtil.getInstance().getDB(dbKey);
        String condition = String.format(Constant.SEARCH_COND_UUID, info.getUuid());
        //保存关键表（RegionPartition）记录(注：父子partition都存储)
        saveRegionPartition(cityCode, dbKey, keyUnitUuid, info.getUuid());
        List<IPartitionInfo> eleList = (List<IPartitionInfo>)mDb.findAllByWhere(cls, condition);
        if (eleList.isEmpty()) {
            mDb.save(info);
        } else {
            mDb.update(info);
        }
    }

    private void saveRegionPartition(String city, String dbKey, String keyUnitUuid, String partitionUuid) {
        FinalDb mDb = DbUtil.getInstance().getDB(dbKey);
        KeyUnitRegionPartitionDBInfo info = new KeyUnitRegionPartitionDBInfo();

        info.setUuid(UUID.randomUUID().toString());
        info.setBelongtoGroup(city);
        info.setKeyUnitRegionUuid(keyUnitUuid);
        info.setPartitionUuid(partitionUuid);
        mDb.save(info);
    }

    public void saveKeyPartInfo(String cityCode, IKeyPartInfo info, Class<?> cls, boolean isHistory) {
        String dbKey = PrefUserInfo.getInstance().getUserInfo(Constant.DB_NAME_USER);
        if (!isHistory) {
            //支持省离线数据库查询
            dbKey = LvApplication.getInstance().getCityName();
        }
        FinalDb mDb = DbUtil.getInstance().getDB(dbKey);

        String condition = String.format(Constant.SEARCH_COND_UUID, info.getUuid());
        List<IKeyPartInfo> eleList = (List<IKeyPartInfo>)mDb.findAllByWhere(cls, condition);

        if (eleList.isEmpty()) {
            mDb.save(info);
        } else {
            mDb.update(info);
        }
    }

    public void saveFireFacilityInfo(String cityCode, IFireFacilityInfo info, Class<?> cls, boolean isHistory) {
        String dbKey = PrefUserInfo.getInstance().getUserInfo(Constant.DB_NAME_USER);
        if (!isHistory) {
            //支持省离线数据库查询
            dbKey = LvApplication.getInstance().getCityName();
        }
        FinalDb mDb = DbUtil.getInstance().getDB(dbKey);

        String condition = String.format(Constant.SEARCH_COND_UUID, info.getUuid());
        List<IFireFacilityInfo> eleList = (List<IFireFacilityInfo>)mDb.findAllByWhere(cls, condition);

        if (eleList.isEmpty()) {
            mDb.save(info);
        } else {
            mDb.update(info);
        }
    }

    public void saveFireVehicleExtinguishingAgentInfo(String cityCode, MaterialExtinguishingAgentDBInfo info, boolean isHistory) {
        String dbKey = PrefUserInfo.getInstance().getUserInfo(Constant.DB_NAME_USER);
        if (!isHistory) {
            //支持省离线数据库查询
            dbKey = LvApplication.getInstance().getCityName();
        }
        FinalDb mDb = DbUtil.getInstance().getDB(dbKey);

        String condition = String.format(Constant.SEARCH_COND_UUID, info.getUuid());
        List<MaterialExtinguishingAgentDBInfo> eleList = mDb.findAllByWhere(MaterialExtinguishingAgentDBInfo.class, condition);

        if (eleList.isEmpty()) {
            mDb.save(info);
        } else {
            mDb.update(info);
        }
    }

    public void saveFireVehicleEquipmentInfo(String cityCode, MaterialEquipmentDBInfo info, boolean isHistory) {
        String dbKey = PrefUserInfo.getInstance().getUserInfo(Constant.DB_NAME_USER);
        if (!isHistory) {
            //支持省离线数据库查询
            dbKey = LvApplication.getInstance().getCityName();
        }
        FinalDb mDb = DbUtil.getInstance().getDB(dbKey);

        String condition = String.format(Constant.SEARCH_COND_UUID, info.getUuid());
        List<MaterialEquipmentDBInfo> eleList = mDb.findAllByWhere(MaterialEquipmentDBInfo.class, condition);

        if (eleList.isEmpty()) {
            mDb.save(info);
        } else {
            mDb.update(info);
        }
    }

    private void saveLocalFireVehiclePartInfos(String city, String eleUuid, FireVehicleDTO fireVehicleDTO) {
        //同步更新离线数据与User数据(车载灭火剂)(应用版不更新历史记录)
        deleteFireVehicleExtinguishingAgents(city, eleUuid, false);
        saveFireVehicleExtinguishingAgents(city, eleUuid, fireVehicleDTO.getExtinguishingAgentDTOs(), false);

        deleteFireVehicleEquipments(city, eleUuid, false);
        saveFireVehicleEquipments(city, eleUuid, fireVehicleDTO.getEquipmentDTOs(), false);
    }

    private void saveFireVehicleExtinguishingAgents(String city, String storageUuid, List<ExtinguishingAgentDTO> list, boolean isHistory) {
        if(list != null && list.size() > 0) {
            for(ExtinguishingAgentDTO item : list) {
                MaterialExtinguishingAgentDBInfo dbInfo = new MaterialExtinguishingAgentDBInfo();

                ReflectionUtil.convertToObj(dbInfo, item);
                dbInfo.setStoragePositionUuid(storageUuid);
                //同步保存离线数据与User数据
                saveFireVehicleExtinguishingAgentInfo(city, dbInfo, isHistory);
            }
        }
    }

    private void saveFireVehicleEquipments(String city, String storageUuid, List<EquipmentDTO> list, boolean isHistory) {
        if(list != null && list.size() > 0) {
            for(EquipmentDTO item : list) {
                MaterialEquipmentDBInfo dbInfo = new MaterialEquipmentDBInfo();

                ReflectionUtil.convertToObj(dbInfo, item);
                dbInfo.setStoragePositionUuid(storageUuid);
                //同步保存离线数据与User数据
                saveFireVehicleEquipmentInfo(city, dbInfo, isHistory);
            }
        }
    }

    private String getQueryMediaUuidCondition(Class<?> cls, String type, String uuid) {
        String condition = null;
        if(cls == WatersourceMediaDBInfo.class) {
            condition = String.format("%s and %s", String.format(Constant.SEARCH_COND_TYPE, type), String.format(Constant.SEARCH_COND_SW_UUID, uuid));
        } else if(cls == MaterialMediaDBInfo.class) {
            condition = String.format("%s and %s", String.format(Constant.SEARCH_COND_TYPE, type), String.format(Constant.SEARCH_COND_MATERIAL_UUID, uuid));
        } else if(cls == StationMediaDBInfo.class) {
            condition = String.format("%s and %s", String.format(Constant.SEARCH_COND_TYPE, type), String.format(Constant.SEARCH_COND_STATION_UUID, uuid));
        } else if(cls == KeyUnitMediaDBInfo.class) {
            //重点单位仅获取base的media数据（仅重点单位下）
            condition = String.format("%s and %s", String.format(Constant.SEARCH_COND_KEY_UNIT_UUID, uuid), String.format(Constant.SEARCH_COND_BELONG_TO_UUID, uuid));
        } else if(cls == JointForceMediaDBInfo.class) {
            condition = String.format(Constant.SEARCH_COND_JOINT_FORCE_UUID, uuid);
        }
        return condition;
    }

    private String getQueryDescendantMediaUuidCondition(Class<?> cls, String type, String uuid) {
        String condition = null;
        if(cls == WatersourceMediaDBInfo.class) {
            condition = String.format("%s and %s", String.format(Constant.SEARCH_COND_TYPE, type), String.format(Constant.SEARCH_COND_SW_UUID, uuid));
        } else if(cls == MaterialMediaDBInfo.class) {
            condition = String.format("%s and %s", String.format(Constant.SEARCH_COND_TYPE, type), String.format(Constant.SEARCH_COND_MATERIAL_UUID, uuid));
        } else if(cls == StationMediaDBInfo.class) {
            condition = String.format("%s and %s", String.format(Constant.SEARCH_COND_TYPE, type), String.format(Constant.SEARCH_COND_STATION_UUID, uuid));
        } else if(cls == KeyUnitMediaDBInfo.class) {
            //重点单位仅获取base的media数据（仅重点单位下）
            condition = String.format(Constant.SEARCH_COND_KEY_UNIT_UUID, uuid);
        } else if(cls == JointForceMediaDBInfo.class) {
            condition = String.format(Constant.SEARCH_COND_JOINT_FORCE_UUID, uuid);
        }
        return condition;
    }

    private void saveMediaDBInfos(String cityCode, String type, String uuid, List<IMediaDTO> mediaList, boolean isHistory) {
        String dbKey = PrefUserInfo.getInstance().getUserInfo(Constant.DB_NAME_USER);

        if (!isHistory) {
            //支持省离线数据库查询
            dbKey = LvApplication.getInstance().getCityName();
        }
        FinalDb db = DbUtil.getInstance().getDB(dbKey);

        if(mediaClassMap.containsKey(type)) {
            Class<?> cls = mediaClassMap.get(type);
            deleteMediaInfos(cityCode, type, uuid, isHistory);
            if (mediaList != null && mediaList.size() > 0) {
                try {
                    for (IMediaDTO item : mediaList) {
                        Object attachDB = cls.newInstance();
                        ReflectionUtil.convertToObj(attachDB, item);
                        db.save(attachDB);
                    }
                } catch (Exception e) {

                }
            }
        }
    }

    private void saveKeyUnitMediaDBInfos(String cityCode, String uuid, List<IMediaDTO> mediaList, boolean isHistory) {
        String dbKey = PrefUserInfo.getInstance().getUserInfo(Constant.DB_NAME_USER);
        if (!isHistory) {
            //支持省离线数据库查询
            dbKey = LvApplication.getInstance().getCityName();
        }
        FinalDb db = DbUtil.getInstance().getDB(dbKey);
        deleteKeyUnitMediaInfos(cityCode, uuid, isHistory);
        if (mediaList != null && mediaList.size() > 0) {
            try {
                for (IMediaDTO item : mediaList) {
                    KeyUnitMediaDBInfo attachDB = new KeyUnitMediaDBInfo();
                    ReflectionUtil.convertToObj(attachDB, item);
                    db.save(attachDB);
                }
            } catch (Exception e) {

            }
        }
    }

    public void updateAttachFile(String cityCode, IMediaInfo mediaInfo, boolean isHistory) {
        String strWhere = String.format(Constant.SEARCH_COND_UUID, mediaInfo.getUuid());

        String dbKey = PrefUserInfo.getInstance().getUserInfo(Constant.DB_NAME_USER);

        if (!isHistory) {
            //支持省离线数据库查询
            dbKey = LvApplication.getInstance().getCityName();
        }
        FinalDb mDb = DbUtil.getInstance().getDB(dbKey);

        List<IMediaInfo> dtos = (List<IMediaInfo>)mDb.findAllByWhere(mediaInfo.getClass(), strWhere);
        if (dtos.size() > 0) {
            //由于离线media与历史media的uuid和belongtoUuid不一致所以小心更新，容易出bug
            IMediaInfo info = dtos.get(0);
            info.setMediaFormat(mediaInfo.getMediaFormat());
            info.setMediaName(mediaInfo.getMediaName());
            info.setLocalPath(mediaInfo.getLocalPath());
            info.setLocalThumbPath(mediaInfo.getLocalThumbPath());
            info.setMediaDescription(mediaInfo.getMediaDescription());
            info.setMediaUrl(mediaInfo.getMediaUrl());
            mDb.update(info);
        }
    }

    private List<PartitionBaseDTO> convertToPartitionDTOs(List<Map<String, Object>> partitionInfos) {
        List<PartitionBaseDTO> retList = new ArrayList<>();
        Gson gson = GsonUtil.create();

        for(Map<String, Object> info : partitionInfos) {
            if(dtoPartitionClassMap.containsKey(info.get(Constant.PROPERTY_PARTITION_TYPE))) {
                Class<?> cls = dtoPartitionClassMap.get(info.get(Constant.PROPERTY_PARTITION_TYPE));
                PartitionBaseDTO partitionBaseDTO = (PartitionBaseDTO)gson.fromJson(gson.toJson(info), cls);
                retList.add(partitionBaseDTO);
            }
        }
        return retList;
    }

    private List<FireFacilityBaseDTO> convertToFireFacilityDTOs(List<Map<String, Object>> fireFacilityInfos) {
        List<FireFacilityBaseDTO> retList = new ArrayList<>();
        Gson gson = GsonUtil.create();

        for(Map<String, Object> info : fireFacilityInfos) {
            if(dtoFireFacilityClassMap.containsKey(info.get(Constant.PROPERTY_FIRE_FACILITY_TYPE))) {
                Class<?> cls = dtoFireFacilityClassMap.get(info.get(Constant.PROPERTY_FIRE_FACILITY_TYPE));
                FireFacilityBaseDTO fireFacilityBaseDTO = (FireFacilityBaseDTO)gson.fromJson(gson.toJson(info), cls);
                retList.add(fireFacilityBaseDTO);
            }
        }
        return retList;
    }

    private List<KeyPartBaseDTO> convertToKeyPartDTOs(List<Map<String, Object>> keyPartInfos) {
        List<KeyPartBaseDTO> retList = new ArrayList<>();
        Gson gson = GsonUtil.create();

        for(Map<String, Object> info : keyPartInfos) {
            if(dtoKeyPartClassMap.containsKey(info.get(Constant.PROPERTY_KEY_PART_TYPE))) {
                Class<?> cls = dtoKeyPartClassMap.get(info.get(Constant.PROPERTY_KEY_PART_TYPE));
                KeyPartBaseDTO keyPartBaseDTO = (KeyPartBaseDTO)gson.fromJson(gson.toJson(info), cls);
                retList.add(keyPartBaseDTO);
            }
        }
        return retList;
    }

    private <T> List<IMediaDTO> convertToMediaDTOs(List<T> mediaInfos) {
        List<IMediaDTO> retInfos = new ArrayList<>();

        if(mediaInfos != null && mediaInfos.size() > 0) {
            for(T item : mediaInfos) {
                retInfos.add((IMediaDTO) item);
            }
        }
        return retInfos;
    }

    //ID:844641 19-01-14 重点单位防火管辖大队变更 王泉
    public List<Dictionary> getPreventionStationDic(String groupCode) {
        List<Dictionary> dicList = new ArrayList<>();

        List<StationBrigadeDBInfo> zdList = getStationBrigade(groupCode);
        for (StationBrigadeDBInfo item : zdList) {
            if(item.getDeleted() == null || !item.getDeleted()) {
                dicList.add(new Dictionary(item.getName(), item.getUuid()));
            }
        }

        List<StationDetachmentDBInfo> ddList = getStationDetachment(groupCode);
        for (StationDetachmentDBInfo item : ddList) {
            if(item.getDeleted() == null || !item.getDeleted()) {
                dicList.add(new Dictionary(item.getName(), item.getUuid()));
            }
        }

        List<StationBattalionDBInfo> zqddList = getStationBattalion(groupCode);
        for (StationBattalionDBInfo item : zqddList) {
            if(item.getDeleted() == null || !item.getDeleted()) {
                dicList.add(new Dictionary(item.getName(), item.getUuid()));
            }
        }

        return dicList;
    }

    private List<StationBrigadeDBInfo> getStationBrigade(String cityCode) {
        List<StationBrigadeDBInfo> dzList = new ArrayList<>();
        //支持省离线数据库查询
        String dbKey = LvApplication.getInstance().getCityName();
        FinalDb db = DbUtil.getInstance().getDB(dbKey);

        if(!StringUtil.isEmpty(cityCode)) {
            try {
                if(cityCode.length() > 2) {
                    cityCode = cityCode.substring(0, 2);
                }
                //支持省离线数据库查询
                List<StationBrigadeDBInfo> dtos = db.findAllByWhere(StationBrigadeDBInfo.class, Constant.SEARCH_COND_NOT_DELETED);
                if (dtos != null && !dtos.isEmpty()) {
                    dzList.addAll(dtos);
                }
            } catch (Exception e) {
                LogUtils.d("Exception", e);
            }
        }

        return dzList;
    }

    public List<Dictionary> getStationDetachmentDic(String groupCode) {
        List<Dictionary> dicList = new ArrayList<>();

        List<StationDetachmentDBInfo> zqddList = getStationDetachment(groupCode);
        for (StationDetachmentDBInfo dz : zqddList) {
            if(dz.getDeleted() == null || !dz.getDeleted()) {
                dicList.add(new Dictionary(dz.getName(), dz.getUuid()));
            }
        }

        return dicList;
    }

    public List<StationDetachmentDBInfo> getStationDetachment(String cityCode) {
        List<StationDetachmentDBInfo> dzList = new ArrayList<>();

        //支持省离线数据库查询
        String dbKey = LvApplication.getInstance().getCityName();
        FinalDb db = DbUtil.getInstance().getDB(dbKey);
        //支持省离线数据库查询
        List<StationDetachmentDBInfo> dtos =  db.findAllByWhere(StationDetachmentDBInfo.class, Constant.SEARCH_COND_NOT_DELETED);
        if (dtos != null && !dtos.isEmpty()) {
            dzList.addAll(dtos);
        }

        return dzList;
    }

    public List<Dictionary> getStationBattalionDic(String groupCode) {
        List<Dictionary> dicList = new ArrayList<>();

        List<StationBattalionDBInfo> zqddList = getStationBattalion(groupCode);
        for (StationBattalionDBInfo dz : zqddList) {
            if(dz.getDeleted() == null || !dz.getDeleted()) {
                dicList.add(new Dictionary(dz.getName(), dz.getUuid()));
            }
        }

        return dicList;
    }

    public List<StationBattalionDBInfo> getStationBattalion(String cityCode) {
        List<StationBattalionDBInfo> dzList = new ArrayList<>();
        //支持省离线数据库查询
        String dbKey = LvApplication.getInstance().getCityName();

        if(!StringUtil.isEmpty(dbKey)) {
            try {
                FinalDb db = DbUtil.getInstance().getDB(dbKey);
                List<StationBattalionDBInfo> dtos = db.findAll(StationBattalionDBInfo.class);
                if (dtos != null && !dtos.isEmpty()) {
                    dzList.addAll(dtos);
                }
            } catch (Exception e) {
                LogUtils.d("Exception = %s", e.getMessage());
            }
        }

        return dzList;
    }

    public List<Dictionary> getStationSquadronDic(String groupCode) {
        List<Dictionary> dicList = new ArrayList<>();

        List<StationSquadronDBInfo> zqzdList = getStationSquadron(groupCode);
        for (StationSquadronDBInfo dz : zqzdList) {
            if(dz.getDeleted() == null || !dz.getDeleted()) {
                dicList.add(new Dictionary(dz.getName(), dz.getUuid()));
            }
        }
        return dicList;
    }

    private List<StationSquadronDBInfo> getStationSquadron(String cityCode) {
        List<StationSquadronDBInfo> dzList = new ArrayList<>();
        if(!StringUtil.isEmpty(cityCode)) {
            //支持省离线数据库查询
            String dbKey = LvApplication.getInstance().getCityName();
            FinalDb db = DbUtil.getInstance().getDB(dbKey);

            try {
                List<StationSquadronDBInfo> dtos = db.findAll(StationSquadronDBInfo.class);
                if (dtos != null && !dtos.isEmpty()) {
                    dzList.addAll(dtos);
                }
            } catch (Exception e) {
                LogUtils.d("Exception", e);
            }
        }

        return dzList;
    }

    public Integer getOverallSquadronDutyNum(String departmentUuid) {
        Integer overallSquadronDutyNum = 0;
        List<String> uuids = getDepartmentUuidAllChildren(departmentUuid, 1);

        List<StationSquadronDBInfo> squadronDBInfos = findSquadronByDepartmentUuidInAndDeletedFalse(uuids);

        if (squadronDBInfos != null && squadronDBInfos.size() > 0) {
            for (StationSquadronDBInfo e: squadronDBInfos) {
                if (e.getServiceNum() != null) {
                    overallSquadronDutyNum += e.getServiceNum();
                }
            }
        }

        return overallSquadronDutyNum;
    }

    private List<StationSquadronDBInfo> findSquadronByDepartmentUuidInAndDeletedFalse(List<String> uuids) {
        List<StationSquadronDBInfo> ret = new ArrayList<StationSquadronDBInfo>();

        String dbKey = LvApplication.getInstance().getCityName();
        FinalDb mDb = DbUtil.getInstance().getDB(dbKey);

        String uuidsStr = list2String(uuids);
        String strSQL = "select * " +
                " from station_squadron a " +
                " where a.department_uuid  in ( "+ uuidsStr + " )" +
                " and a.deleted = 0";

        List<DbModel> dmList = mDb.findDbModelListBySQL(strSQL);
        Gson gson = GsonUtil.create();

        for (DbModel model : dmList) {
            String jsonStr = gson.toJson(convertToMapKey(model.getDataMap()));
            StationSquadronDBInfo info = gson.fromJson(jsonStr, StationSquadronDBInfo.class);
            ret.add(info);
        }

        return ret;
    }

    public Map<String, Object> getStationStatisticsDataByDepartment(String departmentUuid) {
        Map<String, Object> ret = new HashMap<String, Object>();
        Map<String, DbModel> stationStatitics = getStationStatistics();
        List<String> uuids = getDepartmentUuidAllChildren(departmentUuid, 1);
        Integer jurisdictionSquadronsNum = 0;
        Integer jurisdictionBattalionsNum = 0;
        Integer jurisdictionDetachmentsNum = 0;

        for (String uuid : uuids) {
            if (stationStatitics.containsKey(uuid)) {
                jurisdictionSquadronsNum += stationStatitics.get(uuid).getInt("cnt_squadron");
            }
            if (stationStatitics.containsKey(uuid)) {
                jurisdictionBattalionsNum += stationStatitics.get(uuid).getInt("cnt_battalion");
            }
            if (stationStatitics.containsKey(uuid)) {
                jurisdictionDetachmentsNum += stationStatitics.get(uuid).getInt("cnt_detachment");
            }
        }
        ret.put("cntSquadron", jurisdictionSquadronsNum);
        ret.put("cntBattalion", jurisdictionBattalionsNum);
        ret.put("cntDetachment", jurisdictionDetachmentsNum);
        return ret;
    }

    public Map<String, Object> getMaterialStatisticsDataByDepartment(String departmentUuid) {
        Map<String, Object> ret = new HashMap<String, Object>();
        Map<String, DbModel> materialStatitics = getMaterialStatistics();
        List<String> uuids = getDepartmentUuidAllChildren(departmentUuid, 1);
        Integer cntEquipment = 0;
        Double cntExtinguishingAgent = 0d;
        Integer cntFireVehicle = 0;

        for (String uuid : uuids) {
            if (materialStatitics.containsKey(uuid)) {
                cntEquipment += materialStatitics.get(uuid).getInt("cnt_equipment");
                cntExtinguishingAgent += materialStatitics.get(uuid).getDouble("cnt_extinguishing_agent");
                cntFireVehicle += materialStatitics.get(uuid).getInt("cnt_fire_vehicle");
            }
        }
        ret.put("cntEquipment", cntEquipment);
        ret.put("cntExtinguishingAgent", cntExtinguishingAgent);
        ret.put("cntFireVehicle", cntFireVehicle);

        return ret;
    }

    public Map<String, Object> getKeyUnitStatisticsDataByDepartment(String departmentUuid) {
        Map<String, Object> ret = new HashMap<String, Object>();
        Map<String, DbModel> keyUnitStatitics = getkeyUnitStatistics();
        List<String> uuids = getDepartmentUuidAllChildren(departmentUuid, 1);
        Integer cntKeyUnit = 0;

        for (String uuid : uuids) {
            if (keyUnitStatitics.containsKey(uuid)) {
                cntKeyUnit += keyUnitStatitics.get(uuid).getInt("cnt");
            }
        }
        ret.put("cntKeyUnit", cntKeyUnit);

        return ret;
    }

    public Map<String, Object> getWsStatisticsDataByDepartment(String departmentUuid) {
        Map<String, Object> ret = new HashMap<String, Object>();
        Map<String, DbModel> wsStatitics = getWsStatistics();
        List<String> uuids = getDepartmentUuidAllChildren(departmentUuid, 1);
        Integer watersourceCrane = 0;
        Integer watersourceFireHydrant = 0;
        Integer watersourceFirePool = 0;
        Integer watersourceNatureIntake = 0;

        for (String uuid : uuids) {
            if (wsStatitics.containsKey(uuid)) {
                watersourceCrane += wsStatitics.get(uuid).getInt("crane_count");
                watersourceFireHydrant += wsStatitics.get(uuid).getInt("hydrant_count");
                watersourceFirePool += wsStatitics.get(uuid).getInt("pool_count");
                watersourceNatureIntake += wsStatitics.get(uuid).getInt("nature_intake_count");
            }
        }
        ret.put("watersourceCrane", watersourceCrane);
        ret.put("watersourceFireHydrant", watersourceFireHydrant);
        ret.put("watersourceFirePool", watersourceFirePool);
        ret.put("watersourceNatureIntake", watersourceNatureIntake);

        return ret;
    }

    private Map<String, DbModel> getStationStatistics() {
        Map<String, DbModel> ret = new HashMap<String, DbModel>();

        String dbKey = LvApplication.getInstance().getCityName();
        FinalDb mDb = DbUtil.getInstance().getDB(dbKey);

        String strSQL = " SELECT tt.department_uuid AS uuid, " +
                "    sum(tt.cntenterprisefource) AS cnt_enterprise_fource, " +
                "    sum(tt.cntbattalion) AS cnt_battalion, " +
                "    sum(tt.cntsquadron) AS cnt_squadron, " +
                "    sum(tt.cntdetachment) AS cnt_detachment " +
                "   FROM ( SELECT a.department_uuid, " +
                "            count(a.department_uuid) AS cntenterprisefource, " +
                "            0 AS cntbattalion, " +
                "            0 AS cntsquadron, " +
                "            0 AS cntdetachment " +
                "           FROM enterprise_fource a " +
                "          WHERE (a.deleted = 0) " +
                "          GROUP BY a.department_uuid " +
                "        UNION ALL " +
                "         SELECT b.department_uuid, " +
                "            0 AS cntenterprisefource, " +
                "            count(b.department_uuid) AS cntbattalion, " +
                "            0 AS cntsquadron, " +
                "            0 AS cntdetachment " +
                "           FROM station_battalion b " +
                "          WHERE (b.deleted = 0) " +
                "          GROUP BY b.department_uuid " +
                "        UNION ALL " +
                "         SELECT c.department_uuid, " +
                "            0 AS cntenterprisefource, " +
                "            0 AS cntbattalion, " +
                "            count(c.department_uuid) AS cntsquadron, " +
                "            0 AS cntdetachment " +
                "           FROM station_squadron c " +
                "          WHERE (c.deleted = 0) " +
                "          GROUP BY c.department_uuid " +
                "        UNION ALL " +
                "         SELECT c.department_uuid, " +
                "            0 AS cntenterprisefource, " +
                "            0 AS cntbattalion, " +
                "            0 AS cntsquadron, " +
                "            count(c.department_uuid) AS cntdetachment " +
                "           FROM station_detachment c " +
                "          WHERE (c.deleted = 0) " +
                "          GROUP BY c.department_uuid) tt " +
                "  GROUP BY tt.department_uuid " ;

        List<DbModel> dmList = mDb.findDbModelListBySQL(strSQL);

        for (DbModel i : dmList) {
            ret.put(i.getString("uuid"), i);
        }

        return ret;
    }

    private Map<String, DbModel> getMaterialStatistics() {
        Map<String, DbModel> ret = new HashMap<String, DbModel>();

        String dbKey = LvApplication.getInstance().getCityName();
        FinalDb mDb = DbUtil.getInstance().getDB(dbKey);

        String strSQL = "   SELECT tt.department_uuid AS uuid, " +
                "    sum(tt.cntequipment) AS cnt_equipment, " +
                "    sum(tt.cntextinguishingagent) AS cnt_extinguishing_agent, " +
                "    sum(tt.cntfirevenicle) AS cnt_fire_vehicle " +
                "   FROM ( SELECT a.department_uuid, " +
                "            COALESCE(sum(a.reserve), 0) AS cntequipment, " +
                "            0 AS cntextinguishingagent, " +
                "            0 AS cntfirevenicle " +
                "           FROM ( SELECT ma.reserve, " +
                "                    n.department_uuid " +
                "                   FROM material_equipment ma, " +
                "                    material_fire_vehicle n " +
                "                  WHERE (((ma.storage_position_uuid) = (n.uuid)) AND ((ma.storage_position_type) = 'FIRE_VEHICLE') AND (n.deleted = 0) AND (ma.deleted = 0)) " +
                "                UNION ALL " +
                "                 SELECT mm.reserve, " +
                "                    mm.department_uuid " +
                "                   FROM material_equipment mm " +
                "                  WHERE ((mm.deleted = 0) AND ((mm.storage_position_type IS NULL) OR ((mm.storage_position_type) = '')))) a " +
                "          GROUP BY a.department_uuid " +
                "        UNION ALL " +
                "         SELECT b.department_uuid, " +
                "            0 AS cntequipment, " +
                "            COALESCE(sum(b.inventory), 0) AS cntextinguishingagent, " +
                "            0 AS cntfirevenicle " +
                "           FROM ( SELECT ma.inventory, " +
                "                    n.department_uuid " +
                "                   FROM material_extinguishing_agent ma, " +
                "                    material_fire_vehicle n " +
                "                  WHERE (((ma.storage_position_uuid) = (n.uuid)) AND ((ma.storage_position_type) = 'FIRE_VEHICLE') AND (n.deleted = 0) AND (ma.deleted = 0)) " +
                "                UNION ALL " +
                "                 SELECT mm.inventory, " +
                "                    mm.department_uuid " +
                "                   FROM material_extinguishing_agent mm " +
                "                  WHERE ((mm.deleted = 0) AND ((mm.storage_position_type IS NULL) OR ((mm.storage_position_type) = '')))) b " +
                "          GROUP BY b.department_uuid " +
                "        UNION ALL " +
                "         SELECT c.department_uuid, " +
                "            0 AS cntequipment, " +
                "            0 AS cntextinguishingagent, " +
                "            COALESCE(count(c.department_uuid), 0) AS cntfirevenicle " +
                "           FROM material_fire_vehicle c " +
                "          WHERE (c.deleted = 0) " +
                "          GROUP BY c.department_uuid) tt " +
                "  GROUP BY tt.department_uuid; " ;

        List<DbModel> dmList = mDb.findDbModelListBySQL(strSQL);

        for (DbModel i : dmList) {
            ret.put(i.getString("uuid"), i);
        }

        return ret;
    }

    private Map<String, DbModel> getkeyUnitStatistics() {
        Map<String, DbModel> ret = new HashMap<String, DbModel>();

        String dbKey = LvApplication.getInstance().getCityName();
        FinalDb mDb = DbUtil.getInstance().getDB(dbKey);

        String strSQL = "SELECT " +
                " a.department_uuid AS department_uuid, " +
                " count(department_uuid) AS cnt " +
                " FROM " +
                " key_unit a " +
                " WHERE " +
                " a.deleted = 0 " +
                " GROUP BY " +
                " a.department_uuid " ;

        List<DbModel> dmList = mDb.findDbModelListBySQL(strSQL);

        for (DbModel i : dmList) {
            ret.put(i.getString("department_uuid"), i);
        }

        return ret;
    }

    // Todo
    private List<DbModel> getFireVehicleExtinguishingAgentStatistics(String stationUuid, String typeUuid) {
        String dbKey = LvApplication.getInstance().getCityName();
        FinalDb mDb = DbUtil.getInstance().getDB(dbKey);

        String strSQL = " select aa.storage_position_uuid, COALESCE(sum(aa.inventory), 0) as inventory, aa.name as fire_vehicle_name  " +
                "from (  " +
                "select a.type_uuid, a.inventory, a.storage_position_uuid, b.name,b.station_uuid  " +
                "from material_extinguishing_agent a  " +
                "LEFT JOIN material_fire_vehicle b  " +
                "on a.storage_position_uuid = b.uuid  " +
                "where a.deleted = 0  " +
                "and b.deleted = 0  " +
                "and a.storage_position_type = 'FIRE_VEHICLE'  " +
                "and a.type_uuid = '" + typeUuid + "' " +
                "and b.station_uuid = '" + stationUuid + "' " +
                "and a.inventory > 0) aa  " +
                "group by aa.storage_position_uuid, aa.name";

        List<DbModel> dmList = mDb.findDbModelListBySQL(strSQL);

        return dmList;
    }


    private Map<String, DbModel> getWsStatistics() {
        Map<String, DbModel> ret = new HashMap<String, DbModel>();

        String dbKey = LvApplication.getInstance().getCityName();
        FinalDb mDb = DbUtil.getInstance().getDB(dbKey);

        String strSQL = " select department.department_uuid uuid,  " +
                "COALESCE(crane.ws_crane_count,0) crane_count, " +
                "COALESCE(hydrant.ws_fire_hydrant_count, 0) hydrant_count, " +
                "COALESCE(nature_intake.ws_nature_intake_count,0) nature_intake_count,  " +
                "COALESCE(pool.ws_fire_pool_count,0) pool_count " +
                "from ( " +
                "select uuid as department_uuid from sys_department where deleted = 0  " +
                ") department left join ( " +
                "select department_uuid, count(uuid) ws_crane_count from watersource_crane where deleted = 0 group by department_uuid " +
                ") crane on department.department_uuid = crane.department_uuid " +
                "left join ( " +
                "select department_uuid, count(uuid) ws_fire_hydrant_count from watersource_fire_hydrant where deleted = 0 group by department_uuid " +
                ") hydrant on department.department_uuid = hydrant.department_uuid " +
                "left join ( " +
                "select department_uuid, count(uuid) ws_nature_intake_count from watersource_nature_intake where deleted = 0 group by department_uuid " +
                ") nature_intake on department.department_uuid = nature_intake.department_uuid " +
                "left join ( " +
                "select department_uuid, count(uuid) ws_fire_pool_count from watersource_fire_pool where deleted = 0 group by department_uuid " +
                ") pool on department.department_uuid = pool.department_uuid; " ;

        List<DbModel> dmList = mDb.findDbModelListBySQL(strSQL);

        for (DbModel i : dmList) {
            ret.put(i.getString("uuid"), i);
        }

        return ret;
    }

    private String list2String(List<String> uuids) {
        Boolean flag = false;
        StringBuilder result = new StringBuilder();
        for(String uuid : uuids) {
            if (flag) {
                result.append(",");
            } else {
                flag = true;
            }
            result.append("'");
            result.append(uuid);
            result.append("'");
        }

        return result.toString();
    }

    public List<String> getDepartmentUuidAllChildren(String departmentUuid, Integer isContainSelf) {
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        List<String> uuids = new ArrayList<String>();

        if (currentapiVersion < Build.VERSION_CODES.LOLLIPOP) {
            // API 小于21即Android小于5.0，不支持with语法，因此要使用java代码处理实现
            List<SysDepartmentDBInfo> sysDepartments = findAllDepartment(departmentUuid, isContainSelf);  // ID:996389 【移动端】部分移动端设备执行递归SQL时发生异常 2019-5-28 王旭
            for (SysDepartmentDBInfo i : sysDepartments) {
                uuids.add(i.getUuid());
            }
        } else {
            String dbKey = LvApplication.getInstance().getCityName();
            FinalDb mDb = DbUtil.getInstance().getDB(dbKey);

            String strSQL = "WITH RECURSIVE depTree AS ( " +
                    " SELECT A.* " +
                    " FROM sys_department A " +
                    " WHERE uuid = " + "'" + departmentUuid + "'" +
                    " AND A.deleted = 0 " +
                    " UNION ALL " +
                    " SELECT K.* " +
                    " FROM sys_department K " +
                    " INNER JOIN depTree C ON C.uuid = K.parent_uuid " +
                    " WHERE K.deleted = 0 " +
                    " ) " +
                    " SELECT depTree.uuid " +
                    " FROM depTree  " +
                    " where 1 = 1  " +
                    " AND  " +
                    " CASE WHEN 1 = " + isContainSelf + " THEN 1 = 1"+
                    " ELSE uuid != "+ " '" + departmentUuid + "' " + " END ";

            List<DbModel> dmList = mDb.findDbModelListBySQL(strSQL);

            for (DbModel dm : dmList) {
                String uuid = (String) dm.get("uuid");
                uuids.add(uuid);
            }
        }

        return uuids;
    }

    /*
    * @Description ID:996389 【移动端】部分移动端设备执行递归SQL时发生异常 2019-5-28 王旭
    * */
    private List<SysDepartmentDBInfo> findAllDepartment(String departmentUuid, Integer isContainSelf) {
        String dbKey = LvApplication.getInstance().getCityName();
        FinalDb mDb = DbUtil.getInstance().getDB(dbKey);
        List<SysDepartmentDBInfo> departmentResult = new ArrayList<SysDepartmentDBInfo>();

        List<SysDepartmentDBInfo> sysDepartmentDBInfos = mDb.findAllByWhere(SysDepartmentDBInfo.class, Constant.SEARCH_COND_NOT_DELETED);
        if (sysDepartmentDBInfos == null || sysDepartmentDBInfos.size() == 0) {
            return departmentResult;
        }

        SysDepartmentDBInfo findSysdeparment = null;
        Map<String, List<SysDepartmentDBInfo>> sysDepartmentMap = new HashMap<String, List<SysDepartmentDBInfo>>();
        for (SysDepartmentDBInfo info : sysDepartmentDBInfos) {
            if (departmentUuid.equals(info.getUuid())) {
                findSysdeparment = info;
            }

            if (StringUtil.isEmpty(info.getParentUuid())) {
                sysDepartmentMap.put("root", new ArrayList<SysDepartmentDBInfo>(Arrays.asList(info)));
                continue;
            }
            if (sysDepartmentMap.containsKey(info.getParentUuid())) {
                sysDepartmentMap.get(info.getParentUuid()).add(info);
            } else {
                sysDepartmentMap.put(info.getParentUuid(), new ArrayList<SysDepartmentDBInfo>(Arrays.asList(info)));
            }
        }

        getChildDepartment(new ArrayList<SysDepartmentDBInfo>(Arrays.asList(findSysdeparment)), sysDepartmentMap, departmentResult);

        if (isContainSelf == 1) {
            departmentResult.add(findSysdeparment);
        }

        return departmentResult;
    }

    /*
    * @Description ID:996389 【移动端】部分移动端设备执行递归SQL时发生异常 2019-5-28 王旭
    * */
    private void getChildDepartment(List<SysDepartmentDBInfo> findDepartment, Map<String, List<SysDepartmentDBInfo>> sysDepartmentMap, List<SysDepartmentDBInfo> ret) {
        if (findDepartment == null || findDepartment.size() == 0) {
            return;
        }

        List<SysDepartmentDBInfo> tmp = new ArrayList<SysDepartmentDBInfo>();
        for (SysDepartmentDBInfo f : findDepartment) {
            if (sysDepartmentMap.containsKey(f.getUuid())) {
                tmp.addAll(sysDepartmentMap.get(f.getUuid()));
                ret.addAll(sysDepartmentMap.get(f.getUuid()));
            }
        }

        if (tmp.size() == 0) {
            return;
        }
        getChildDepartment(tmp, sysDepartmentMap, ret);

    }

    /*
* @Author 王旭
* @Date 2019-03-05
* @Descrption  ID:900352【物质器材灭火剂】追加车辆信息显示。
* */
    public String getVehicleName(String vehicleUuid, String cityCode) {
        MaterialFireVehicleDBInfo vehicleDBInfo = null;
        //支持省离线数据库查询
        String dbKey = LvApplication.getInstance().getCityName();
        FinalDb db = DbUtil.getInstance().getDB(dbKey);
        try {
            List<MaterialFireVehicleDBInfo> dtos = db.findAllByWhere(MaterialFireVehicleDBInfo.class, String.format("uuid = '%s' ", vehicleUuid));
            if (dtos != null && !dtos.isEmpty()) {
                vehicleDBInfo = dtos.get(0);
            }
        } catch (Exception e) {
            LogUtils.d("Exception", e);
        }
        return vehicleDBInfo == null ? "": vehicleDBInfo.getName();
    }

    /*
    * @Author 王旭
    * @Date 2019-03-05
    * @Descrption  由于车载器材和灭火剂没有departmentUuid，所以需要获取到其所在车辆的departmentUuid；
    * */
    public String getVehicleDepartmentUuid(String vehicleUuid, String cityCode) {
        MaterialFireVehicleDBInfo vehicleDBInfo = null;
        //支持省离线数据库查询
        String dbKey = LvApplication.getInstance().getCityName();
        FinalDb db = DbUtil.getInstance().getDB(dbKey);
        try {
            List<MaterialFireVehicleDBInfo> dtos = db.findAllByWhere(MaterialFireVehicleDBInfo.class, String.format("uuid = '%s' ", vehicleUuid));
            if (dtos != null && !dtos.isEmpty()) {
                vehicleDBInfo = dtos.get(0);
            }
        } catch (Exception e) {
            LogUtils.d("Exception", e);
        }
        return vehicleDBInfo == null ? "": vehicleDBInfo.getDepartmentUuid();
    }

    public String getVehicleStationUuid(String vehicleUuid, String cityCode) {
        MaterialFireVehicleDBInfo vehicleDBInfo = null;
        //支持省离线数据库查询
        String dbKey = LvApplication.getInstance().getCityName();
        FinalDb db = DbUtil.getInstance().getDB(dbKey);
        try {
            List<MaterialFireVehicleDBInfo> dtos = db.findAllByWhere(MaterialFireVehicleDBInfo.class, String.format("uuid = '%s' ", vehicleUuid));
            if (dtos != null && !dtos.isEmpty()) {
                vehicleDBInfo = dtos.get(0);
            }
        } catch (Exception e) {
            LogUtils.d("Exception", e);
        }
        return vehicleDBInfo == null ? "": vehicleDBInfo.getStationUuid();
    }

    /*
    * @Author 王旭
    * @Date 2019-03-05
    * @Description ID:900358 【移动应用端】也添加关于器材的统计，和前台一致
    * */
    public Map<String, String> getEquipmentCount(QueryCondition condition) {
        Map<String, String> ret = new HashMap<String, String>(4);

        String strWhere = "";
        if (StringUtil.isEmpty(condition.getFilterSQL())) {
            strWhere = getWhere(condition);
        } else {
            String strSQL = condition.getFilterSQL() + " AND " + getWhere(condition);

            String dbKey = LvApplication.getInstance().getCityName();
            FinalDb mDb = DbUtil.getInstance().getDB(dbKey);

            List<DbModel> dmList =  mDb.findDbModelListBySQL(strSQL);
            if (dmList == null || dmList.size() == 0) {
                return ret;
            }

            List<String> uuids = new ArrayList<String>();
            for (DbModel dm :dmList) {
                uuids.add(dm.getString("uuid"));
            }
            strWhere = String.format("uuid in (%s)", list2String(uuids));
        }

        ret = getEquipmentCountBySQL(strWhere);

        return ret;
    }

    /*
    * @Author 王旭
    * @Date 2019-03-05
    * @Description ID:900358 【移动应用端】也添加关于器材的统计，和前台一致
    * */
    public Map<String, String> getEquipmentCountBySQL(String strWhere) {
        Map<String, String> ret = new HashMap<String, String>(4);

        String strSQL = String.format("select " +
                " COALESCE(sum(tt.ky),0) as ky, " +
                " COALESCE(sum(tt.sh),0) as sh, " +
                " COALESCE(sum(tt.cz),0) as cz " +
                " FROM ( " +
                " SELECT " +
                "  COALESCE(sum(a.userful_inventory),0) AS ky,COALESCE(sum(a.unuserful_inventory),0) AS sh, 0 as cz " +
                " FROM " +
                " material_equipment a " +
                " WHERE " +
                "  (storage_position_type <> 'FIRE_VEHICLE' or storage_position_type is null) " +
                " And(%s) " +
                " UNION ALL " +
                " SELECT " +
                " 0 as ky, 0 as sh, COALESCE(sum(a.userful_inventory),0) AS cz " +
                " FROM " +
                "  material_equipment a " +
                " WHERE " +
                "  (storage_position_type = 'FIRE_VEHICLE')" +
                "And  (%s) ) tt", strWhere, strWhere);

        String dbKey = LvApplication.getInstance().getCityName();
        FinalDb mDb = DbUtil.getInstance().getDB(dbKey);

        DbModel dm = mDb.findDbModelBySQL(strSQL);

        int ky = Integer.valueOf((String)dm.get("ky"));
        int sh = Integer.valueOf((String)dm.get("sh"));
        int cz = Integer.valueOf((String)dm.get("cz"));
        int zl = ky + sh + cz;

        ret.put("ky", String.valueOf(ky));
        ret.put("sh", String.valueOf(sh));
        ret.put("cz", String.valueOf(cz));
        ret.put("zl", String.valueOf(zl));

        return ret;
    }

    public  List<MapSearchInfo> getMapSearchInfoList(String key) {
        key = StringUtil.handleTransferChar(key);

        String dbKey = LvApplication.getInstance().getCityName();
        FinalDb mDb = DbUtil.getInstance().getDB(dbKey);

        String strWhere = "ele_type = '" +
                AppDefs.CompEleType.KEY_UNIT.toString() +
                "' and name like '%" + key + "%' and deleted = 0";
        List<GeomElementDBInfo> eleList = (List<GeomElementDBInfo>)
                mDb.findAllByWhere(GeomElementDBInfo.class, strWhere, "name asc limit 10");

        List<MapSearchInfo> list = new ArrayList<>();
        for (GeomElementDBInfo dto : eleList) {
            MapSearchInfo msi = new MapSearchInfo();
            msi.setType(Constant.MAP_SEARCH_FIRE);
            msi.setName(dto.getName());
            msi.setAddress(dto.getAddress());
            msi.setUid("");
            Double lat = Utils.convertToDouble(dto.getLatitude());
            Double lng = Utils.convertToDouble(dto.getLongitude());
            if (lat != null && lng != null) {
                msi.setLat((int)(lat * 1e6));
                msi.setLng((int)(lng * 1e6));
            }
            String extra = dto.getEleType() + Constant.OUTLINE_DIVIDER +
                    dto.getUuid() + Constant.OUTLINE_DIVIDER +
                    dto.getBelongtoGroup() + Constant.OUTLINE_DIVIDER;
            //对应其他消防队伍分为4类
            extra += Constant.OUTLINE_DIVIDER + dto.getResEleType();
            msi.setExtra(extra);
            list.add(msi);
        }

        return list;
    }

    // 获取支队department uuid
    public SysDepartmentDBInfo getDetachmentDepartment(String cityCode, String districtCode, String gradeLevel) {
        if(!StringUtil.isEmpty(cityCode)) {
            //支持省离线数据库查询
            String dbKey = LvApplication.getInstance().getCityName();
            FinalDb db = DbUtil.getInstance().getDB(dbKey);
            String sql = " district_code = " + districtCode +
                    " AND grade = " + gradeLevel +
                    " AND (deleted = 0 or deleted is null)";

            try {
                List<SysDepartmentDBInfo> list = (ArrayList<SysDepartmentDBInfo>)
                        db.findAllByWhere(SysDepartmentDBInfo.class, sql);
                if (list != null && !list.isEmpty()) {
                    return list.get(0);
                }
            } catch (Exception e) {
                LogUtils.d("Exception", e);
            }
        }

        return null;
    }

    public interface OnGeomEleListener {
        void onSuccess(String cid);
        void onFailure(String msg, String cid);
    }
}
