package com.moft.xfapply.model.external.dto;

import java.util.Date;

/**
 * (comp_zddw)
 * 
 * @author wangxy
 * @version 1.0.0 2017-04-18
 */
public class CompZddwDTO implements java.io.Serializable {
    
    private static final long serialVersionUID = 7058799358256812970L;
    
    private String road;      // 路/街
    private String no;        // 号
    private String jzlb;      // 建筑类别
    private String xffzr;     // 消防管理人
    private String fzrTel;    // 消防管理人联系方式
    private String zbTel;     // 单位值班电话
    private String fhjb;      // 防火级别
    private String yajb;      // 预案级别
    private String plqkEast;  // 毗邻情况-东
    private String plqkWest;  // 毗邻情况-西
    private String plqkSouth; // 毗邻情况-南
    private String plqkNorth; // 毗邻情况-北
    private String overview;  // 总体概况
    // private Double zdmj; // 占地面积（m2）
    // private Double zjzmj; // 总建筑面积（m2）
    // private String routeTime; // 辖区中队行车路线及时间
    private String gnfqMs; // 功能分区总体描述
    
    /**
     * 2017/06/27 wxy 新增字段：消防法人，消防法人联系方式，防火监督员，防火监督员联系方式， 防火管辖大队，单位性质，审核人
     * 删除字段：辖区中队行车路线及时间 变更字段含义：xffzr：消防管理人 fzrTel：消防管理人联系方式
     * 
     * 2017/06/30 wxy 删除字段：占地面积（m2），总建筑面积（m2），审核人
     */
    private String  xffr;         // 消防法人
    private String  xffrTel;      // 消防法人联系方式
    private String  fhjdy;        // 防火监督员
    private String  fhjdyTel;     // 防火监督员联系方式
    private String  gxdd;         // 防火管辖大队
    private String  nature;       // 单位性质
    private String  reviewer;     // 核查人
    private String  duty;         // 核查人职务
    private Date    reviewerDate; // 更新时间
    private Boolean hasPano;      // 是否有全景图
    
    public String getRoad() {
        return road;
    }
    
    public void setRoad(String road) {
        this.road = road;
    }
    
    public String getNo() {
        return no;
    }
    
    public void setNo(String no) {
        this.no = no;
    }
    
    public String getJzlb() {
        return jzlb;
    }
    
    public void setJzlb(String jzlb) {
        this.jzlb = jzlb;
    }
    
    public String getXffzr() {
        return xffzr;
    }
    
    public void setXffzr(String xffzr) {
        this.xffzr = xffzr;
    }
    
    public String getFzrTel() {
        return fzrTel;
    }
    
    public void setFzrTel(String fzrTel) {
        this.fzrTel = fzrTel;
    }
    
    public String getZbTel() {
        return zbTel;
    }
    
    public void setZbTel(String zbTel) {
        this.zbTel = zbTel;
    }
    
    public String getFhjb() {
        return fhjb;
    }
    
    public void setFhjb(String fhjb) {
        this.fhjb = fhjb;
    }
    
    public String getYajb() {
        return yajb;
    }
    
    public void setYajb(String yajb) {
        this.yajb = yajb;
    }
    
    public String getPlqkEast() {
        return plqkEast;
    }
    
    public void setPlqkEast(String plqkEast) {
        this.plqkEast = plqkEast;
    }
    
    public String getPlqkWest() {
        return plqkWest;
    }
    
    public void setPlqkWest(String plqkWest) {
        this.plqkWest = plqkWest;
    }
    
    public String getPlqkSouth() {
        return plqkSouth;
    }
    
    public void setPlqkSouth(String plqkSouth) {
        this.plqkSouth = plqkSouth;
    }
    
    public String getPlqkNorth() {
        return plqkNorth;
    }
    
    public void setPlqkNorth(String plqkNorth) {
        this.plqkNorth = plqkNorth;
    }
    
    public String getOverview() {
        return overview;
    }
    
    public void setOverview(String overview) {
        this.overview = overview;
    }
    
    public String getXffr() {
        return xffr;
    }
    
    public void setXffr(String xffr) {
        this.xffr = xffr;
    }
    
    public String getXffrTel() {
        return xffrTel;
    }
    
    public void setXffrTel(String xffrTel) {
        this.xffrTel = xffrTel;
    }
    
    public String getFhjdy() {
        return fhjdy;
    }
    
    public void setFhjdy(String fhjdy) {
        this.fhjdy = fhjdy;
    }
    
    public String getFhjdyTel() {
        return fhjdyTel;
    }
    
    public void setFhjdyTel(String fhjdyTel) {
        this.fhjdyTel = fhjdyTel;
    }
    
    public String getGxdd() {
        return gxdd;
    }
    
    public void setGxdd(String gxdd) {
        this.gxdd = gxdd;
    }
    
    public String getNature() {
        return nature;
    }
    
    public void setNature(String nature) {
        this.nature = nature;
    }
    
    public String getGnfqMs() {
        return gnfqMs;
    }
    
    public void setGnfqMs(String gnfqMs) {
        this.gnfqMs = gnfqMs;
    }
    
    public String getReviewer() {
        return reviewer;
    }
    
    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }
    
    public String getDuty() {
        return duty;
    }
    
    public void setDuty(String duty) {
        this.duty = duty;
    }
    
    public Date getReviewerDate() {
        return reviewerDate;
    }
    
    public void setReviewerDate(Date reviewerDate) {
        this.reviewerDate = reviewerDate;
    }

    public Boolean getHasPano() {
        return hasPano;
    }

    public void setHasPano(Boolean hasPano) {
        this.hasPano = hasPano;
    }
    
}