package com.moft.xfapply.model.entity;

import com.moft.xfapply.app.Constant;
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.logic.setting.PrefUserInfo;
import com.moft.xfapply.model.base.IGeomElementInfo;
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

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name = "watersource_nature_intake")
public class WatersourceNatureIntakeDBInfo implements IGeomElementInfo {
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

	@Property(column = "nature_type")
	private String natureType; // 天然水源类型
	@Property(column = "has_wharf")
	private Boolean hasWharf; // 有无消防码头
	@Property(column = "vehicles_num")
	private Integer vehiclesNum; // 同时取水车辆数（辆）
	@Property(column = "height_diff")
	private Double heightDiff; // 取水口与水面高差（m）
	@Property(column = "has_dry")
	private Boolean hasDry; // 有无枯水期
	@Property(column = "dry_season")
	private String drySeason; // 枯水期
	@Property(column = "water_quality")
	private String waterQuality; // 水质


	@Transient
	private List<PropertyDes> pdListDetail = new ArrayList<PropertyDes>();

	public WatersourceNatureIntakeDBInfo() {
		name = "天然取水点";
		hasWharf = false;
		vehiclesNum = 0;
		heightDiff = 0d;
		hasDry = false;
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

		pdListDetail.add(new PropertyDes("天然水源类型", "setNatureType", String.class, natureType, LvApplication.getInstance().getCompDicMap().get("SY_TRSY")));
		pdListDetail.add(new PropertyDes("有无消防码头", "setHasWharf", Boolean.class, hasWharf, LvApplication.getInstance().getHasOrNohasDicList()));
		pdListDetail.add(new PropertyDes("同时取水车辆数(台)", "setVehiclesNum", Integer.class, vehiclesNum, PropertyDes.TYPE_EDIT));
		pdListDetail.add(new PropertyDes("取水口与水面高差(m)", "setHeightDiff", Double.class, heightDiff, PropertyDes.TYPE_EDIT));
		pdListDetail.add(new PropertyDes("有无枯水期", "setHasDry", Boolean.class, hasDry, LvApplication.getInstance().getHasOrNohasDicList()));
		pdListDetail.add(new PropertyDes("枯水期", "setDrySeason", String.class, drySeason, PropertyDes.TYPE_EDIT));
		pdListDetail.add(new PropertyDes("水质", "setWaterQuality", String.class, waterQuality, LvApplication.getInstance().getCompDicMap().get("SY_SZ")));

		pdListDetail.add(new PropertyDes("供水单位", "setSupplyUnit", String.class, supplyUnit, PropertyDes.TYPE_EDIT));
		pdListDetail.add(new PropertyDes("联系方式", "setContactTel", String.class, contactTel, PropertyDes.TYPE_EDIT));
		pdListDetail.add(new PropertyDes("备注", "setRemark", String.class, remark, PropertyDes.TYPE_EDIT));

		return pdListDetail;
	}

	@Transient
	private static List<PropertyConditon> pdListDetailForFilter = new ArrayList<PropertyConditon>();
	public static List<PropertyConditon> getPdListDetailForFilter() {
		pdListDetailForFilter.clear();

		pdListDetailForFilter.add(new PropertyConditon("可用状态", "watersource_nature_intake", "available_state", String.class, PropertyConditon.TYPE_LIST, LvApplication.getInstance().getCompDicMap().get("SY_KYZT"), true));
		pdListDetailForFilter.add(new PropertyConditon("水质", "watersource_nature_intake", "water_quality", String.class, PropertyConditon.TYPE_LIST, LvApplication.getInstance().getCompDicMap().get("SY_SZ"), true));
		pdListDetailForFilter.add(new PropertyConditon("枯水期", "watersource_nature_intake", "dry_season", String.class, PropertyConditon.TYPE_EDIT));
//		pdListDetailForFilter.add(new PropertyConditon("取水形式", "watersource_nature_intake", "intake_form", String.class, PropertyConditon.TYPE_LIST, LvApplication.getInstance().getCompDicMap().get("SY_QSXS"), false));
		pdListDetailForFilter.add(new PropertyConditon("取水口与水面高差(m)", "watersource_nature_intake", "height_diff", Double.class, PropertyConditon.TYPE_EDIT_NUMBER));

		return pdListDetailForFilter;
	}
	public static String getTableNameForFilter() {
		return "watersource_nature_intake";
	}
	public static String getSelectColumnForFilter() {
		return "*";
	}

	@Override
	public String getOutline() {
		String outline = "";

		outline += "状态：<font color=#0074C7>" + DictionaryUtil.getValueByCode(availableState, LvApplication.getInstance().getCompDicMap().get("SY_KYZT")) +
				"</font>&nbsp;&nbsp;水源类型：<font color=#0074C7>" + DictionaryUtil.getValueByCode(
				natureType, LvApplication.getInstance().getCompDicMap().get("SY_TRSY")) +
				"</font>&nbsp;&nbsp;水质：<font color=#0074C7>" + DictionaryUtil.getValueByCode(waterQuality, LvApplication.getInstance().getCompDicMap().get("SY_SZ")) + "</font>" + Constant.OUTLINE_DIVIDER;
		outline += "消防码头：<font color=#0074C7>" + DictionaryUtil.getValueByCode(StringUtil.get(hasWharf), LvApplication.getInstance().getHasOrNohasDicList())
				+ "</font>&nbsp;&nbsp;同时取水车辆数(台)：<font color=#0074C7>" + StringUtil.get(vehiclesNum) + "</font>" + Constant.OUTLINE_DIVIDER;
		outline += "取水口与水面高差(m)：<font color=#0074C7>" + StringUtil.get(heightDiff) +
				"</font>&nbsp;&nbsp;枯水期：<font color=#0074C7>" + StringUtil.get(drySeason) + "</font>" + Constant.OUTLINE_DIVIDER;
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
		wsType = AppDefs.CompEleType.WATERSOURCE_NATURE_INTAKE.toString();
		if(StringUtil.isEmpty(name)) {
			name = "天然取水点";
		}
	}

	public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
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

	public String getNatureType() {
		return natureType;
	}

	public void setNatureType(String natureType) {
		this.natureType = natureType;
	}

	public Boolean getHasWharf() {
		return hasWharf;
	}

	public void setHasWharf(Boolean hasWharf) {
		this.hasWharf = hasWharf;
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

	public Boolean getHasDry() {
		return hasDry;
	}

	public void setHasDry(Boolean hasDry) {
		this.hasDry = hasDry;
	}

	public String getDrySeason() {
		return drySeason;
	}

	public void setDrySeason(String drySeason) {
		this.drySeason = drySeason;
	}

	public String getWaterQuality() {
		return waterQuality;
	}

	public void setWaterQuality(String waterQuality) {
		this.waterQuality = waterQuality;
	}


	@Override
	public String toString() {
		String str = StringUtil.get(code) + String.format("|%s", StringUtil.get(name)) + "|天然取水点" +
				String.format("|%s",  StringUtil.get(address)) +
				String.format("|%s",  DictionaryUtil.getValueByCode(wsAttch, LvApplication.getInstance().getCompDicMap().get("SY_SSGW"))) +
				String.format("|%s",  StringUtil.get(subordinateUnit)) +
				String.format("|%s",  DictionaryUtil.getValueByCode(availableState, LvApplication.getInstance().getCompDicMap().get("SY_KYZT"))) +
				String.format("|%s",  StringUtil.get(supplyUnit)) +
				String.format("|%s",  DictionaryUtil.getValueByCode(natureType, LvApplication.getInstance().getCompDicMap().get("SY_TRSY"))) +
				String.format("|%s",  DictionaryUtil.getValueByCode(waterQuality, LvApplication.getInstance().getCompDicMap().get("SY_SZ")));

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