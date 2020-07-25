package com.moft.xfapply.model.entity;

import com.moft.xfapply.model.common.PropertyDes;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Property;
import net.tsz.afinal.annotation.sqlite.Table;
import net.tsz.afinal.annotation.sqlite.Transient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sxf on 2019-05-03.
 */
@Table(name = "msds")
public class MsdsDBInfo implements Serializable {
    @Id
    @Property(column = "PID")
    private String pid;

    @Property(column = "CNAME")
    private String cname;

    @Property(column = "IDENTITYINFO")
    private String identityInfo;

    @Property(column = "COMPOSITION")
    private String composition;

    @Property(column = "FATALNESS")
    private String fatalness;

    @Property(column = "FIRSTAID")
    private String firstAid;

    @Property(column = "FIREPROTECTION")
    private String fireProtection;

    @Property(column = "LEAKTREATMENT")
    private String leakTreatment;

    @Property(column = "TOUCHPROTECTION")
    private String touchProtection;

    @Property(column = "PCINFO")
    private String pcInfo;

    @Property(column = "STABILITYINFO")
    private String stabilityInfo;

    @Property(column = "TOXICITYINFO")
    private String toxicityInfo;

    @Property(column = "TRANSPORTINFO")
    private String transportInfo;

    @Property(column = "OTHERINFO")
    private String otherInfo;

    @Property(column = "SOURCEINFO")
    private String sourceInfo;

    @Transient
    private static List<PropertyDes> pdListDetail = new ArrayList<PropertyDes>();

    public List<PropertyDes> getPdListDetail() {
        pdListDetail.clear();

        pdListDetail.add(new PropertyDes("名称", "setCName", String.class, cname, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("CAS号", "setComposition", String.class, composition, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("成分信息", "setIdentityInfo", String.class, identityInfo, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("危险性概述", "setFatalness", String.class, fatalness, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("急救措施", "setFirstAid", String.class, firstAid, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("消防措施", "setFireProtection", String.class, fireProtection, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("泄露应急处理", "setLeakTreatment", String.class, leakTreatment, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("接触控制/个体防护", "setTouchProtection", String.class, touchProtection, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("理化特性", "setPcInfo", String.class, pcInfo, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("稳定性和反应活性", "setStabilityInfo", String.class, stabilityInfo, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("毒理学资料", "setToxicityInfo", String.class, toxicityInfo, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("运输信息", "setTransportInfo", String.class, transportInfo, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("其他信息", "setOtherInfo", String.class, otherInfo, PropertyDes.TYPE_EDIT));

        return pdListDetail;
    }

    public String getCname()
    {
        return cname;
    }

    public void setCname(String cname)
    {
        this.cname = cname;
    }

    public String getIdentityInfo()
    {
        return identityInfo;
    }

    public void setIdentityInfo(String identityInfo)
    {
        this.identityInfo = identityInfo;
    }

    public String getComposition()
    {
        return composition;
    }

    public void setComposition(String composition)
    {
        this.composition = composition;
    }

    public String getFatalness()
    {
        return fatalness;
    }

    public void setFatalness(String fatalness)
    {
        this.fatalness = fatalness;
    }

    public String getFirstAid()
    {
        return firstAid;
    }

    public void setFirstAid(String firstAid)
    {
        this.firstAid = firstAid;
    }

    public String getFireProtection()
    {
        return fireProtection;
    }

    public void setFireProtection(String fireProtection)
    {
        this.fireProtection = fireProtection;
    }

    public String getLeakTreatment()
    {
        return leakTreatment;
    }

    public void setLeakTreatment(String leakTreatment)
    {
        this.leakTreatment = leakTreatment;
    }

    public String getTouchProtection()
    {
        return touchProtection;
    }

    public void setTouchProtection(String touchProtection)
    {
        this.touchProtection = touchProtection;
    }

    public String getPcInfo()
    {
        return pcInfo;
    }

    public void setPcInfo(String pcInfo)
    {
        this.pcInfo = pcInfo;
    }

    public String getStabilityInfo()
    {
        return stabilityInfo;
    }

    public void setStabilityInfo(String stabilityInfo)
    {
        this.stabilityInfo = stabilityInfo;
    }

    public String getToxicityInfo()
    {
        return toxicityInfo;
    }

    public void setToxicityInfo(String toxicityInfo)
    {
        this.toxicityInfo = toxicityInfo;
    }

    public String getTransportInfo()
    {
        return transportInfo;
    }

    public void setTransportInfo(String transportInfo)
    {
        this.transportInfo = transportInfo;
    }

    public String getOtherInfo()
    {
        return otherInfo;
    }

    public void setOtherInfo(String otherInfo)
    {
        this.otherInfo = otherInfo;
    }

    public String getSourceInfo()
    {
        return sourceInfo;
    }

    public void setSourceInfo(String sourceInfo)
    {
        this.sourceInfo = sourceInfo;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
