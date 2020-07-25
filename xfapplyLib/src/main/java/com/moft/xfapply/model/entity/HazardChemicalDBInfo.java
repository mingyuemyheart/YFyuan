package com.moft.xfapply.model.entity;

import com.moft.xfapply.model.common.PropertyConditon;
import com.moft.xfapply.model.common.PropertyDes;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Property;
import net.tsz.afinal.annotation.sqlite.Table;
import net.tsz.afinal.annotation.sqlite.Transient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 危化品(hazard_chemical)
 * 
 * @author wangxy
 * @version 1.0.0 2017-04-14
 */
@Table(name = "dt_kb_hazardchemical")
public class HazardChemicalDBInfo implements Serializable {
    @Transient
    private static final long serialVersionUID = 1L;

    @Id
    private String  uuid;         // 唯一标识
    @Property(column = "un_code")
    private String  unCode;       // un号
    private String  name;         // 名称
//    private String  cname;        //
    @Property(column = "danger_code")
    private String  dangerCode;   // 违规号
    @Property(column = "special_warning")
    private String  specialWarning;         // 特别警示
    @Property(column = "comb_exp_character")
    private String  combExpCharacter;       // 燃烧、爆炸特性
    @Property(column = "hazard_character")
    private String  hazardCharacter;       // 健康危害特性
    @Property(column = "public_security")
    private String  publicSecurity;         // 公众安全
    @Property(column = "individual_protection")
    private String  individualProtection;         // 个体防护
    @Property(column = "quarantine")
    private String  quarantine;           // 隔离
    @Property(column = "comb_exp_deal")
    private String  combExpDeal;       // 燃烧爆炸处置
    @Property(column = "reveal_deal")
    private String  revealDeal;         // 泄露处置
    @Property(column = "chemical_character")
    private String  chemicalCharacter;         // 理化特性
    @Property(column = "toxicity")
    private String  toxicity;           // 毒性
    @Property(column = "pack_classify_mark")
    private String  packClassifyMark;       // 包装分类与标志
    @Property(column = "main_use")
    private String  mainUse;         // 主要用途
    @Property(column = "data_quality")
    private Double dataQuality;
    @Property(column = "create_person")
    private String createPerson;
    @Property(column = "create_date")
    private Date createDate;
    @Property(column = "update_person")
    private String updatePerson;
    @Property(column = "update_date")
    private Date updateDate;
    private Boolean deleted;
    private Integer version;
    private String remark;      // 备注

    @Transient
    private static List<PropertyDes> pdListDetail = new ArrayList<PropertyDes>();

    public List<PropertyDes> getPdListDetail() {
        pdListDetail.clear();

        pdListDetail.add(new PropertyDes("名称", "setName", String.class, name, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("毒性", "setDx", String.class, toxicity, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("主要用途", "setZyyt", String.class, mainUse, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("特别警示", "setTbjs", String.class, specialWarning, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("燃烧、爆炸特性", "setRsbztx", String.class, combExpCharacter, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("健康危害特性", "setGzaq", String.class, hazardCharacter, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("公众安全", "setGzaq", String.class, publicSecurity, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("个体防护", "setGtfh", String.class, individualProtection, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("隔离", "setGl", String.class, quarantine, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("燃烧爆炸处置", "setRsbzcz", String.class, combExpDeal, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("泄露处置", "setXlcz", String.class, revealDeal, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("理化特性", "setLhtx", String.class, chemicalCharacter, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("包装分类与标志", "setBzflbz", String.class, packClassifyMark, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("un号", "setUnCode", String.class, unCode, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("危规号", "setWeiguiCode", String.class, dangerCode, PropertyDes.TYPE_EDIT));

        return pdListDetail;
    }


    @Transient
    private static List<PropertyConditon> pdListDetailForFilter = new ArrayList<PropertyConditon>();
    public static List<PropertyConditon> getPdListDetailForFilter() {
        pdListDetailForFilter.clear();

        pdListDetailForFilter.add(new PropertyConditon("燃烧、爆炸特性", "dt_kb_hazardchemical", "special_warning", String.class, PropertyConditon.TYPE_EDIT));
        pdListDetailForFilter.add(new PropertyConditon("健康危害特性", "dt_kb_hazardchemical", "hazard_character", String.class, PropertyConditon.TYPE_EDIT));
        pdListDetailForFilter.add(new PropertyConditon("理化特性", "dt_kb_hazardchemical", "chemical_character", String.class, PropertyConditon.TYPE_EDIT));
        pdListDetailForFilter.add(new PropertyConditon("毒性", "dt_kb_hazardchemical", "toxicity", String.class, PropertyConditon.TYPE_EDIT));

        return pdListDetailForFilter;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUnCode() {
        return unCode;
    }

    public void setUnCode(String unCode) {
        this.unCode = unCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDangerCode() {
        return dangerCode;
    }

    public void setDangerCode(String dangerCode) {
        this.dangerCode = dangerCode;
    }

    public String getSpecialWarning() {
        return specialWarning;
    }

    public void setSpecialWarning(String specialWarning) {
        this.specialWarning = specialWarning;
    }

    public String getCombExpCharacter() {
        return combExpCharacter;
    }

    public void setCombExpCharacter(String combExpCharacter) {
        this.combExpCharacter = combExpCharacter;
    }

    public String getHazardCharacter() {
        return hazardCharacter;
    }

    public void setHazardCharacter(String hazardCharacter) {
        this.hazardCharacter = hazardCharacter;
    }

    public String getPublicSecurity() {
        return publicSecurity;
    }

    public void setPublicSecurity(String publicSecurity) {
        this.publicSecurity = publicSecurity;
    }

    public String getIndividualProtection() {
        return individualProtection;
    }

    public void setIndividualProtection(String individualProtection) {
        this.individualProtection = individualProtection;
    }

    public String getQuarantine() {
        return quarantine;
    }

    public void setQuarantine(String quarantine) {
        this.quarantine = quarantine;
    }

    public String getCombExpDeal() {
        return combExpDeal;
    }

    public void setCombExpDeal(String combExpDeal) {
        this.combExpDeal = combExpDeal;
    }

    public String getRevealDeal() {
        return revealDeal;
    }

    public void setRevealDeal(String revealDeal) {
        this.revealDeal = revealDeal;
    }

    public String getChemicalCharacter() {
        return chemicalCharacter;
    }

    public void setChemicalCharacter(String chemicalCharacter) {
        this.chemicalCharacter = chemicalCharacter;
    }

    public String getToxicity() {
        return toxicity;
    }

    public void setToxicity(String toxicity) {
        this.toxicity = toxicity;
    }

    public String getPackClassifyMark() {
        return packClassifyMark;
    }

    public void setPackClassifyMark(String packClassifyMark) {
        this.packClassifyMark = packClassifyMark;
    }

    public String getMainUse() {
        return mainUse;
    }

    public void setMainUse(String mainUse) {
        this.mainUse = mainUse;
    }

    public Double getDataQuality() {
        return dataQuality;
    }

    public void setDataQuality(Double dataQuality) {
        this.dataQuality = dataQuality;
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdatePerson() {
        return updatePerson;
    }

    public void setUpdatePerson(String updatePerson) {
        this.updatePerson = updatePerson;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}