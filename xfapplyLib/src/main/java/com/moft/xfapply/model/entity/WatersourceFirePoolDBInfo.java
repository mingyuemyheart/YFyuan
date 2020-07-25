package com.moft.xfapply.model.entity;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.moft.xfapply.activity.bussiness.GeomEleBussiness;
import com.moft.xfapply.app.Constant;
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.logic.setting.PrefUserInfo;
import com.moft.xfapply.model.base.IGeomElementInfo;
import com.moft.xfapply.model.common.Dictionary;
import com.moft.xfapply.model.common.PrimaryAttribute;
import com.moft.xfapply.model.common.PropertyConditon;
import com.moft.xfapply.model.common.PropertyDes;
import com.moft.xfapply.model.external.AppDefs;
import com.moft.xfapply.utils.DictionaryUtil;
import com.moft.xfapply.utils.StringUtil;
import com.moft.xfapply.utils.Utils;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Property;
import net.tsz.afinal.annotation.sqlite.Table;
import net.tsz.afinal.annotation.sqlite.Transient;

@Table(name = "watersource_fire_pool")
public class WatersourceFirePoolDBInfo implements IGeomElementInfo {
	/**
	 * @Fields serialVersionUID :
	 */
	@Transient
	private static final long serialVersionUID = 1L;

	@Id
	private String uuid; // 唯一标识
	private String code; // 编码
	@Property(column = "department_uuid")
	private String departmentUuid; // 机构标识
	private String name; // 名称
	@Property(column = "ele_type")
	private String eleType; // 分类
	private String address; // 地址
	@Property(column = "geometry_text")
	private String geometryText; // 中心位置Json文本
	@Property(column = "longitude")
	private String longitude; // 百度精度
	@Property(column = "latitude")
	private String latitude; // 百度纬度
	@Property(column = "geometry_type")
	private String geometryType;//位置类型
	private Boolean deleted; // 删除标识
	@Property(column = "create_person")
	private String createPerson; // 提交人
	@Property(column = "update_person")
	private String updatePerson; // 最新更新者
	@Property(column = "create_date")
	private Date createDate; // 创建时间
	@Property(column = "update_date")
	private Date updateDate; // 更新时间
	private String remark; // 备注
	@Property(column = "belongto_group")
	private String  belongtoGroup; //城市
	@Property(column = "data_quality")
	private Double dataQuality;
	private String keywords; //关键字查询

	@Property(column = "district_code")
	private String districtCode;
	@Property(column = "ws_type")
	private String wsType; // 水源类型
	@Property(column = "ws_attch")
	private String wsAttch; // 水源归属
	@Property(column = "subordinate_unit")
	private String subordinateUnit; // 所属单位（小区）
	@Property(column = "available_state")
	private String availableState; // 可用状态
	@Property(column = "supply_unit")
	private String supplyUnit; // 供水单位
	@Property(column = "contact_tel")
	private String contactTel; // 联系方式
	private Integer version;

	@Property(column = "water_storage")
	private Double waterStorage; // 储水量（t）
	@Property(column = "intake_flow")
	private Double intakeFlow; // 取水最大流量（L/s）
	@Property(column = "inlet_flow")
	private Double inletFlow; // 进水流量（L/s）
	@Property(column = "vehicles_num")
	private Integer vehiclesNum; // 同时取水车辆数（辆）
	@Property(column = "height_diff")
	private Double heightDiff; // 取水口与水面高差（m）
	@Property(column = "replenish_time")
	private Integer replenishTime; // 补水时间（h）
	@Property(column = "network_form")
	private String networkForm; // 进水管网形式

    @Transient
    private List<PropertyDes> pdListDetail = new ArrayList<PropertyDes>();

	public WatersourceFirePoolDBInfo() {
		name = "消防水池";
		waterStorage = 0d;
		intakeFlow = 0d;
		inletFlow = 0d;
		vehiclesNum = 0;
		heightDiff = 0d;
		replenishTime = 0;
	}
    
    public List<PropertyDes> getPdListDetail() {
        pdListDetail.clear();

		pdListDetail.add(new PropertyDes("编码", "setCode", String.class, code, PropertyDes.TYPE_TEXT));
		pdListDetail.add(new PropertyDes("名称*", "setName", String.class, name, PropertyDes.TYPE_TEXT, true));
		pdListDetail.add(new PropertyDes("地址", "setAddress", String.class, address, PropertyDes.TYPE_EDIT));
		pdListDetail.add(new PropertyDes("行政区", "setDistrictCode", String.class, districtCode, LvApplication.getInstance().getCurrentRegionDicList()));
		pdListDetail.add(new PropertyDes("所属管网", "setWsAttch", String.class, wsAttch, LvApplication.getInstance().getCompDicMap().get("SY_SSGW")));
		pdListDetail.add(new PropertyDes("所属单位", "setSubordinateUnit", String.class, subordinateUnit, PropertyDes.TYPE_EDIT));
//		pdListDetail.add(new PropertyDes("所属中队", "setStationUuid", String.class, stationUuid, GeomEleBussiness.getInstance().getStationSquadronDic(belongtoGroup)));
		pdListDetail.add(new PropertyDes("可用状态", "setAvailableState", String.class, availableState, LvApplication.getInstance().getCompDicMap().get("SY_KYZT")));

        pdListDetail.add(new PropertyDes("储水量(t)", "setWaterStorage", Double.class, waterStorage, PropertyDes.TYPE_EDIT));
		pdListDetail.add(new PropertyDes("取水最大流量(L/s)", "setIntakeFlow", Double.class, intakeFlow, PropertyDes.TYPE_EDIT));
		pdListDetail.add(new PropertyDes("进水流量(L/s)", "setInletFlow", Double.class, inletFlow, PropertyDes.TYPE_EDIT));
		pdListDetail.add(new PropertyDes("同时取水车辆数(台)", "setVehiclesNum", Integer.class, vehiclesNum, PropertyDes.TYPE_EDIT));
		pdListDetail.add(new PropertyDes("取水口与水面高差(m)", "setHeightDiff", Double.class, heightDiff, PropertyDes.TYPE_EDIT));
		pdListDetail.add(new PropertyDes("补水时间(h)", "setReplenishTime", Integer.class, replenishTime, PropertyDes.TYPE_EDIT));
		pdListDetail.add(new PropertyDes("进水管网形式", "setNetworkForm", String.class, networkForm, LvApplication.getInstance().getCompDicMap().get("SY_GWXS")));

		pdListDetail.add(new PropertyDes("供水单位", "setSupplyUnit", String.class, supplyUnit, PropertyDes.TYPE_EDIT));
		pdListDetail.add(new PropertyDes("联系方式", "setContactTel", String.class, contactTel, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("备注", "setRemark", String.class, remark, PropertyDes.TYPE_EDIT));
        
        return pdListDetail;
    }

	@Transient
	private static List<PropertyConditon> pdListDetailForFilter = new ArrayList<PropertyConditon>();
	public static List<PropertyConditon> getPdListDetailForFilter() {
		pdListDetailForFilter.clear();

		pdListDetailForFilter.add(new PropertyConditon("可用状态", "watersource_fire_pool", "available_state", String.class, PropertyConditon.TYPE_LIST, LvApplication.getInstance().getCompDicMap().get("SY_KYZT") ,true));
		pdListDetailForFilter.add(new PropertyConditon("进水管网形式", "watersource_fire_pool", "network_form", String.class, PropertyConditon.TYPE_LIST, LvApplication.getInstance().getCompDicMap().get("SY_GWXS"), true));
		pdListDetailForFilter.add(new PropertyConditon("储水量(t)", "watersource_fire_pool", "water_storage", Double.class, PropertyConditon.TYPE_EDIT_NUMBER));
//		pdListDetailForFilter.add(new PropertyConditon("取水形式", "watersource_fire_pool", "intake_form", String.class, PropertyConditon.TYPE_LIST, LvApplication.getInstance().getCompDicMap().get("SY_QSXS"), true));

		return pdListDetailForFilter;
	}
	public static String getTableNameForFilter() {
		return "watersource_fire_pool";
	}
	public static String getSelectColumnForFilter() {
		return "*";
	}

	@Override
	public String getOutline() {
		String outline = "";

		outline += "状态：<font color=#0074C7>" + DictionaryUtil.getValueByCode(availableState, LvApplication.getInstance().getCompDicMap().get("SY_KYZT")) +
				"</font>&nbsp;&nbsp;所属单位：<font color=#0074C7>" + StringUtil.get(subordinateUnit) + "</font>" + Constant.OUTLINE_DIVIDER;
		outline += "储水量(t)：<font color=#0074C7>" + StringUtil.get(waterStorage) +
				"</font>&nbsp;&nbsp;取水最大流量(L/s)：<font color=#0074C7>" + StringUtil.get(intakeFlow) + "</font>" + Constant.OUTLINE_DIVIDER;
		outline += "同时车辆数(台)：<font color=#0074C7>" + StringUtil.get(vehiclesNum) +
                "</font>&nbsp;&nbsp;取水口与水面高差(m)：<font color=#0074C7>" + StringUtil.get(heightDiff) + "</font>" + Constant.OUTLINE_DIVIDER;
        outline += "供水单位：<font color=#0074C7>" + StringUtil.get(supplyUnit) +
                "</font>&nbsp;&nbsp;联系方式：<font color=#0074C7>" + StringUtil.get(contactTel) + "</font>" + Constant.OUTLINE_DIVIDER;

		return outline;
	}

	@Override
	public boolean isGeoPosValid() {
		if (StringUtil.isEmpty(getLongitude()) || StringUtil.isEmpty(getLatitude())) {
			return false;
		}

		Double latD = Utils.convertToDouble(getLatitude());
		Double lngD = Utils.convertToDouble(getLongitude());
		if (latD == null || lngD == null) {
			return false;
		}

		if (latD.intValue() == 0 || lngD.intValue() == 0) {
			return false;
		}

		return true;
	}

	public void setAdapter() {
		if(StringUtil.isEmpty(departmentUuid)) {
			PrefUserInfo pui = PrefUserInfo.getInstance();
			departmentUuid = pui.getUserInfo("department_uuid");
		}
		wsType = AppDefs.CompEleType.WATERSOURCE_FIRE_POOL.toString();
		if(StringUtil.isEmpty(name)) {
			name = "消防水池";
		}
	}

	public PrimaryAttribute getPrimaryValues() {
		PrimaryAttribute attribute = new PrimaryAttribute();
		attribute.setPrimaryValue1(name);
		attribute.setPrimaryValue2(DictionaryUtil.getValueByCode(
				departmentUuid, LvApplication.getInstance().getCurrentOrgList()));
		attribute.setPrimaryValue3(address);

		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(1);
		nf.setMinimumFractionDigits(0);
		nf.setRoundingMode(RoundingMode.HALF_UP);
		nf.setGroupingUsed(false);
		attribute.setPrimaryValue4(nf.format(dataQuality*100)+ "%");

		return  attribute;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDepartmentUuid() {
		return departmentUuid;
	}

	public void setDepartmentUuid(String departmentUuid) {
		this.departmentUuid = departmentUuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEleType() {
		return eleType;
	}

	public void setEleType(String eleType) {
		this.eleType = eleType;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGeometryText() {
		return geometryText;
	}

	public void setGeometryText(String geometryText) {
		this.geometryText = geometryText;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getGeometryType() {
		return geometryType;
	}

	public void setGeometryType(String geometryType) {
		this.geometryType = geometryType;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public String getCreatePerson() {
		return createPerson;
	}

	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}

	public String getUpdatePerson() {
		return updatePerson;
	}

	public void setUpdatePerson(String updatePerson) {
		this.updatePerson = updatePerson;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getBelongtoGroup() {
		return belongtoGroup;
	}

	public void setBelongtoGroup(String belongtoGroup) {
		this.belongtoGroup = belongtoGroup;
	}

	@Override
	public Double getDataQuality() {
		return dataQuality;
	}

	@Override
	public void setDataQuality(Double dataQuality) {
		this.dataQuality = dataQuality;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}

	public String getWsType() {
		return wsType;
	}

	public void setWsType(String wsType) {
		this.wsType = wsType;
	}

	public String getWsAttch() {
		return wsAttch;
	}

	public void setWsAttch(String wsAttch) {
		this.wsAttch = wsAttch;
	}

	public String getSubordinateUnit() {
		return subordinateUnit;
	}

	public void setSubordinateUnit(String subordinateUnit) {
		this.subordinateUnit = subordinateUnit;
	}

	public String getAvailableState() {
		return availableState;
	}

	public void setAvailableState(String availableState) {
		this.availableState = availableState;
	}

	public String getSupplyUnit() {
		return supplyUnit;
	}

	public void setSupplyUnit(String supplyUnit) {
		this.supplyUnit = supplyUnit;
	}

	public String getContactTel() {
		return contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

	@Override
	public Integer getVersion() {
		return version;
	}

	@Override
	public void setVersion(Integer version) {
		this.version = version;
	}

	public Double getWaterStorage() {
		return waterStorage;
	}

	public void setWaterStorage(Double waterStorage) {
		this.waterStorage = waterStorage;
	}

	public Double getIntakeFlow() {
		return intakeFlow;
	}

	public void setIntakeFlow(Double intakeFlow) {
		this.intakeFlow = intakeFlow;
	}

	public Double getInletFlow() {
		return inletFlow;
	}

	public void setInletFlow(Double inletFlow) {
		this.inletFlow = inletFlow;
	}

	public Integer getVehiclesNum() {
		return vehiclesNum;
	}

	public void setVehiclesNum(Integer vehiclesNum) {
		this.vehiclesNum = vehiclesNum;
	}

	public Double getHeightDiff() {
		return heightDiff;
	}

	public void setHeightDiff(Double heightDiff) {
		this.heightDiff = heightDiff;
	}

	public Integer getReplenishTime() {
		return replenishTime;
	}

	public void setReplenishTime(Integer replenishTime) {
		this.replenishTime = replenishTime;
	}

	public String getNetworkForm() {
		return networkForm;
	}

	public void setNetworkForm(String networkForm) {
		this.networkForm = networkForm;
	}

	@Override
	public String toString() {
		String str = StringUtil.get(code) + String.format("|%s", StringUtil.get(name)) + "|消防水池" +
				String.format("|%s",  StringUtil.get(address)) +
				String.format("|%s",  DictionaryUtil.getValueByCode(wsAttch, LvApplication.getInstance().getCompDicMap().get("SY_SSGW"))) +
				String.format("|%s",  StringUtil.get(subordinateUnit)) +
				String.format("|%s",  DictionaryUtil.getValueByCode(availableState, LvApplication.getInstance().getCompDicMap().get("SY_KYZT"))) +
				String.format("|%s",  StringUtil.get(supplyUnit)) +
				String.format("|%s",  DictionaryUtil.getValueByCode(networkForm, LvApplication.getInstance().getCompDicMap().get("SY_GWXS")));
		return str;
	}

	public String getSubType() {
		return null;
	}

	public void setSubType(String subType) {

	}

	//对应其他消防队伍分为4类
	public String getResEleType() {
		return eleType;
	}
}
