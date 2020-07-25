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

@Table(name = "watersource_fire_hydrant")
public class WatersourceFireHydrantDBInfo implements IGeomElementInfo {
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

	@Property(column = "place_form")
	private String placeForm; // 放置形式
	@Property(column = "network_form")
	private String networkForm; // 管网形式
	@Property(column = "network_diameter")
	private Double networkDiameter; // 管网直径（mm）
	@Property(column = "network_pressure_type")
	private String networkPressureType; // 管网压力类型
	@Property(column = "network_pressure")
	private Double networkPressure; // 管网压力范围（Mpa）
	@Property(column = "interface_form")
	private String interfaceForm; // 接口形式
	@Property(column = "interface_caliber")
	private Double interfaceCaliber; // 接口口径（mm）
	private Double flow; // 最大流量（L/s）

	@Transient
	private List<PropertyDes> pdListDetail = new ArrayList<PropertyDes>();

	public WatersourceFireHydrantDBInfo() {
		name = "消火栓";
		networkPressure = 0d;
		flow = 0d;
	}

	public List<PropertyDes> getPdListDetail() {
		pdListDetail.clear();

		pdListDetail.add(new PropertyDes("编码", "setCode", String.class, code, PropertyDes.TYPE_TEXT));
		pdListDetail.add(new PropertyDes("名称 *", "setName", String.class, name, PropertyDes.TYPE_TEXT, true));
		pdListDetail.add(new PropertyDes("地址", "setAddress", String.class, address, PropertyDes.TYPE_EDIT));
		pdListDetail.add(new PropertyDes("行政区", "setDistrictCode", String.class, districtCode, LvApplication.getInstance().getCurrentRegionDicList()));
		pdListDetail.add(new PropertyDes("所属管网", "setWsAttch", String.class, wsAttch, LvApplication.getInstance().getCompDicMap().get("SY_SSGW"), false));
		pdListDetail.add(new PropertyDes("所属单位", "setSubordinateUnit", String.class, subordinateUnit, PropertyDes.TYPE_EDIT));
//		pdListDetail.add(new PropertyDes("所属中队", "setStationUuid", String.class, stationUuid, GeomEleBussiness.getInstance().getStationSquadronDic(belongtoGroup)));
		pdListDetail.add(new PropertyDes("可用状态", "setAvailableState", String.class, availableState, LvApplication.getInstance().getCompDicMap().get("SY_KYZT"), false));

		pdListDetail.add(new PropertyDes("放置形式", "setPlaceForm", String.class, placeForm, LvApplication.getInstance().getCompDicMap().get("SY_FZXS")));
		pdListDetail.add(new PropertyDes("管网形式", "setNetworkForm", String.class, networkForm, LvApplication.getInstance().getCompDicMap().get("SY_GWXS")));
		pdListDetail.add(new PropertyDes("管网直径(mm)", "setNetworkDiameter", Double.class, networkDiameter, LvApplication.getInstance().getCompDicMap().get("SY_GWZJ"), false));
		pdListDetail.add(new PropertyDes("管网压力类型", "setNetworkPressureType", String.class, networkPressureType, LvApplication.getInstance().getCompDicMap().get("SY_GWYL")));
		pdListDetail.add(new PropertyDes("管网压力范围(Mpa)", "setNetworkPressure", Double.class, networkPressure, PropertyDes.TYPE_EDIT));
		pdListDetail.add(new PropertyDes("接口形式", "setInterfaceForm", String.class, interfaceForm, LvApplication.getInstance().getCompDicMap().get("SY_JKXS")));
		pdListDetail.add(new PropertyDes("接口口径(mm)", "setInterfaceCaliber", Double.class, interfaceCaliber, LvApplication.getInstance().getCompDicMap().get("SY_JKKJ"), false));
		pdListDetail.add(new PropertyDes("最大流量(L/s)", "setFlow", Double.class, flow, PropertyDes.TYPE_EDIT));

		pdListDetail.add(new PropertyDes("供水单位", "setSupplyUnit", String.class, supplyUnit, PropertyDes.TYPE_EDIT));
		pdListDetail.add(new PropertyDes("联系方式", "setContactTel", String.class, contactTel, PropertyDes.TYPE_EDIT));
		pdListDetail.add(new PropertyDes("备注", "setRemark", String.class, remark, PropertyDes.TYPE_EDIT));

		return pdListDetail;
	}

	@Transient
	private static List<PropertyConditon> pdListDetailForFilter = new ArrayList<PropertyConditon>();
	public static List<PropertyConditon> getPdListDetailForFilter() {
		pdListDetailForFilter.clear();

		pdListDetailForFilter.add(new PropertyConditon("可用状态", "watersource_fire_hydrant", "available_state", String.class, PropertyConditon.TYPE_LIST, LvApplication.getInstance().getCompDicMap().get("SY_KYZT"), true));
		pdListDetailForFilter.add(new PropertyConditon("管网形式", "watersource_fire_hydrant", "network_form", String.class, PropertyConditon.TYPE_LIST, LvApplication.getInstance().getCompDicMap().get("SY_GWXS"), true));
		pdListDetailForFilter.add(new PropertyConditon("管网直径(mm)", "watersource_fire_hydrant", "network_diameter", Double.class, PropertyConditon.TYPE_EDIT_NUMBER));
		pdListDetailForFilter.add(new PropertyConditon("管网压力范围(Mpa)", "watersource_fire_hydrant", "network_pressure", Double.class, PropertyConditon.TYPE_EDIT_NUMBER));
		pdListDetailForFilter.add(new PropertyConditon("接口口径(mm)", "watersource_fire_hydrant", "interface_caliber", Double.class, PropertyConditon.TYPE_EDIT_NUMBER));
		pdListDetailForFilter.add(new PropertyConditon("接口形式", "watersource_fire_hydrant", "interface_form", String.class, PropertyConditon.TYPE_LIST, LvApplication.getInstance().getCompDicMap().get("SY_JKXS"), true));
		pdListDetailForFilter.add(new PropertyConditon("放置形式", "watersource_fire_hydrant", "place_form", String.class, PropertyConditon.TYPE_LIST, LvApplication.getInstance().getCompDicMap().get("SY_FZXS"), true));

		return pdListDetailForFilter;
	}
	public static String getTableNameForFilter() {
		return "watersource_fire_hydrant";
	}
	public static String getSelectColumnForFilter() {
		return "*";
	}

	@Override
	public String getOutline() {
		String outline = "";

		outline += "状态：<font color=#0074C7>" + DictionaryUtil.getValueByCode(availableState, LvApplication.getInstance().getCompDicMap().get("SY_KYZT")) +
                "</font>" + Constant.OUTLINE_DIVIDER;
		outline += "管网形式：<font color=#0074C7>" + DictionaryUtil.getValueByCode(networkForm, LvApplication.getInstance().getCompDicMap().get("SY_GWXS")) +
				"</font>&nbsp;&nbsp;管网直径(mm)：<font color=#0074C7>" + StringUtil.get(networkDiameter) + "</font>"  +  Constant.OUTLINE_DIVIDER;
		outline += "接口形式：<font color=#0074C7>" + DictionaryUtil.getValueByCode(StringUtil.get(interfaceForm), LvApplication.getInstance().getCompDicMap().get("SY_JKXS")) +
                "</font>&nbsp;&nbsp;接口口径(mm)：<font color=#0074C7>" + StringUtil.get(interfaceCaliber) + "</font>"  + Constant.OUTLINE_DIVIDER;
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
		wsType = AppDefs.CompEleType.WATERSOURCE_FIRE_HYDRANT.toString();
		if(StringUtil.isEmpty(name)) {
			name = "消火栓";
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

	public String getPlaceForm() {
		return placeForm;
	}

	public void setPlaceForm(String placeForm) {
		this.placeForm = placeForm;
	}

	public String getNetworkForm() {
		return networkForm;
	}

	public void setNetworkForm(String networkForm) {
		this.networkForm = networkForm;
	}

	public Double getNetworkDiameter() {
		return networkDiameter;
	}

	public void setNetworkDiameter(Double networkDiameter) {
		this.networkDiameter = networkDiameter;
	}

	public String getNetworkPressureType() {
		return networkPressureType;
	}

	public void setNetworkPressureType(String networkPressureType) {
		this.networkPressureType = networkPressureType;
	}

	public Double getNetworkPressure() {
		return networkPressure;
	}

	public void setNetworkPressure(Double networkPressure) {
		this.networkPressure = networkPressure;
	}

	public String getInterfaceForm() {
		return interfaceForm;
	}

	public void setInterfaceForm(String interfaceForm) {
		this.interfaceForm = interfaceForm;
	}

	public Double getInterfaceCaliber() {
		return interfaceCaliber;
	}

	public void setInterfaceCaliber(Double interfaceCaliber) {
		this.interfaceCaliber = interfaceCaliber;
	}

	public Double getFlow() {
		return flow;
	}

	public void setFlow(Double flow) {
		this.flow = flow;
	}

	@Override
	public String toString() {
		String str = StringUtil.get(code) + String.format("|%s", StringUtil.get(name)) + "|消火栓" +
				String.format("|%s",  StringUtil.get(address)) +
				String.format("|%s",  DictionaryUtil.getValueByCode(wsAttch, LvApplication.getInstance().getCompDicMap().get("SY_SSGW"))) +
				String.format("|%s",  StringUtil.get(subordinateUnit)) +
				String.format("|%s",  DictionaryUtil.getValueByCode(availableState, LvApplication.getInstance().getCompDicMap().get("SY_KYZT"))) +
				String.format("|%s",  StringUtil.get(supplyUnit)) +
				String.format("|%s",  DictionaryUtil.getValueByCode(placeForm, LvApplication.getInstance().getCompDicMap().get("SY_FZXS"))) +
				String.format("|%s",  DictionaryUtil.getValueByCode(networkForm, LvApplication.getInstance().getCompDicMap().get("SY_GWXS"))) +
				String.format("|%s",  StringUtil.get(networkDiameter) +
				String.format("|%s",  DictionaryUtil.getValueByCode(networkPressureType, LvApplication.getInstance().getCompDicMap().get("SY_GWYL"))) +
				String.format("|%s",  DictionaryUtil.getValueByCode(interfaceForm, LvApplication.getInstance().getCompDicMap().get("SY_JKXS"))) +
				String.format("|%s",  StringUtil.get(interfaceCaliber)));
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