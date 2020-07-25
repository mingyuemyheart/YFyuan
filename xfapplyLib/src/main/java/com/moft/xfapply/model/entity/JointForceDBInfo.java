package com.moft.xfapply.model.entity;

import com.moft.xfapply.app.Constant;
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.model.base.IGeomElementInfo;
import com.moft.xfapply.model.common.PrimaryAttribute;
import com.moft.xfapply.model.common.PropertyConditon;
import com.moft.xfapply.model.common.PropertyDes;
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

@Table(name = "joint_force")
public class JointForceDBInfo implements IGeomElementInfo {
	/**
	 * @Fields serialVersionUID :
	 */
	@Transient
	private static final long serialVersionUID = 1L;

	@Id
	private String uuid; // 唯一标识
	@Property(column = "department_uuid")
	private String departmentUuid; // 机构标识
	private String code; // 编码
	private String name; // 名称
	@Property(column = "ele_type")
	private String eleType; // 分类
	@Property(column = "district_code")
	private String districtCode; //行政区域
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
	/** 数据版本 */
	private Integer version;
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
	private String type; // 应急力量类型

	@Property(column = "duty_phone")
	private String dutyPhone; // 值班电话
	private String fax; // 传真
	@Property(column = "contact_name")
	private String contactName; // 联系人
	private String duties; // 联系人职务
	@Property(column = "mobile_phone")
	private String mobilePhone; // 移动电话
	@Property(column = "emergency_content")
	private String emergencyContent; // 应急服务内容
	@Property(column = "emergency_resource")
	private String emergencyResource; // 应急资源说明

	@Transient
	private List<PropertyDes> pdListDetail = new ArrayList<PropertyDes>();

	public JointForceDBInfo() {

	}

	public List<PropertyDes> getPdListDetail() {
		pdListDetail.clear();

		pdListDetail.add(new PropertyDes("编码", "setCode", String.class, code, PropertyDes.TYPE_TEXT));
		pdListDetail.add(new PropertyDes("名称*", "setName", String.class, name, PropertyDes.TYPE_EDIT, true));
		pdListDetail.add(new PropertyDes("类型*", "setType", String.class, type, LvApplication.getInstance().getCompDicMap().get("YJLL"), true, true));
		pdListDetail.add(new PropertyDes("行政区", "setDistrictCode", String.class, districtCode, LvApplication.getInstance().getCurrentRegionDicList()));
		pdListDetail.add(new PropertyDes("地址", "setAddress", String.class, address, PropertyDes.TYPE_EDIT));
		pdListDetail.add(new PropertyDes("值班电话", "setDutyPhone", String.class, dutyPhone, PropertyDes.TYPE_EDIT));
		pdListDetail.add(new PropertyDes("传真", "setFax", String.class, fax, PropertyDes.TYPE_EDIT));
		pdListDetail.add(new PropertyDes("联系人", "setContactName", String.class, contactName, PropertyDes.TYPE_EDIT));
		pdListDetail.add(new PropertyDes("联系人职务", "setDuties", String.class, duties, PropertyDes.TYPE_EDIT));
		pdListDetail.add(new PropertyDes("移动电话", "setMobilePhone", String.class, mobilePhone, PropertyDes.TYPE_EDIT));
		pdListDetail.add(new PropertyDes("应急服务内容", "setEmergencyContent", String.class, emergencyContent, PropertyDes.TYPE_EDIT));
		pdListDetail.add(new PropertyDes("应急资源说明", "setEmergencyResource", String.class, emergencyResource, PropertyDes.TYPE_EDIT));
		pdListDetail.add(new PropertyDes("备注", "setRemark", String.class, remark, PropertyDes.TYPE_EDIT));

		return pdListDetail;
	}

	@Override
	public String getOutline() {
		String outline = "";

		outline += "联系人及电话：<font color=#0074C7>" + StringUtil.get(contactName) + StringUtil.get(mobilePhone) + "</font>"  + Constant.OUTLINE_DIVIDER;
		outline += "类型：<font color=#0074C7>" + DictionaryUtil.getValueByCode(type, LvApplication.getInstance().getCompDicMap().get("YJLL")) + "</font>"  + Constant.OUTLINE_DIVIDER;
		outline += "&" + "值班电话：" + StringUtil.get(dutyPhone) +  "&"
				+ "联系人(" + StringUtil.get(contactName) + ")：" + StringUtil.get(mobilePhone) + "&"
				+ Constant.OUTLINE_DIVIDER;

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

	@Transient
	private static List<PropertyConditon> pdListDetailForFilter = new ArrayList<PropertyConditon>();
	public static List<PropertyConditon> getPdListDetailForFilter() {
		pdListDetailForFilter.clear();
		pdListDetailForFilter.add(new PropertyConditon("类型", "joint_force", "type", String.class, PropertyConditon.TYPE_LIST, LvApplication.getInstance().getCompDicMap().get("YJLL"), true));
		return pdListDetailForFilter;
	}

	@Override
	public void setAdapter() {
	}

	public PrimaryAttribute getPrimaryValues() {
		PrimaryAttribute attribute = new PrimaryAttribute();
		attribute.setPrimaryValue1(name);
		attribute.setPrimaryValue2("");
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

	@Override
	public String getDepartmentUuid() {
		return this.departmentUuid;
	}

	@Override
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

	public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
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

	public Double getDataQuality() {
		return dataQuality;
	}

	public void setDataQuality(Double dataQuality) {
		this.dataQuality = dataQuality;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	@Override
	public Integer getVersion() {
		return version;
	}

	@Override
	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDutyPhone() {
		return dutyPhone;
	}

	public void setDutyPhone(String dutyPhone) {
		this.dutyPhone = dutyPhone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getDuties() {
		return duties;
	}

	public void setDuties(String duties) {
		this.duties = duties;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getEmergencyContent() {
		return emergencyContent;
	}

	public void setEmergencyContent(String emergencyContent) {
		this.emergencyContent = emergencyContent;
	}

	public String getEmergencyResource() {
		return emergencyResource;
	}

	public void setEmergencyResource(String emergencyResource) {
		this.emergencyResource = emergencyResource;
	}

	@Override
	public String toString() {
		String str = StringUtil.get(code) + String.format("|%s", StringUtil.get(name)) + "|联动力量" +
				String.format("|%s",  DictionaryUtil.getValueByCode(type, LvApplication.getInstance().getCompDicMap().get("YJLL"))) +
				String.format("|%s",  StringUtil.get(address)) +
				String.format("|%s",  StringUtil.get(contactName)) +
				String.format("|%s",  StringUtil.get(duties)) +
				String.format("|%s",  StringUtil.get(emergencyContent)) +
				String.format("|%s",  StringUtil.get(emergencyResource));
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