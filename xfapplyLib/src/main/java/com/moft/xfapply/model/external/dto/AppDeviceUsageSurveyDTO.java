package com.moft.xfapply.model.external.dto;

import java.util.Date;
import java.util.List;

/**
 * 终端日均使用(AppDeviceUsageSurvey)
 * 
 * @author zhao.chq
 * @version 1.0.0 2017-12-19
 */
public class AppDeviceUsageSurveyDTO extends RestDTO implements java.io.Serializable {
    /**
     * @Fields serialVersionUID :
     */
    private static final long serialVersionUID = -8923250531556477289L;

    private Date startDate;
    private Date endDate;
    private Integer totalDay;
    private Integer total;
    private List<AppDeviceUsageSurveyChildDTO> appDeviceUsageSurveyChildDTO;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getTotalDay() {
        return totalDay;
    }

    public void setTotalDay(Integer totalDay) {
        this.totalDay = totalDay;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<AppDeviceUsageSurveyChildDTO> getAppDeviceUsageSurveyChildDTO() {
        return appDeviceUsageSurveyChildDTO;
    }

    public void setAppDeviceUsageSurveyChildDTO(List<AppDeviceUsageSurveyChildDTO> appDeviceUsageSurveyChildDTO) {
        this.appDeviceUsageSurveyChildDTO = appDeviceUsageSurveyChildDTO;
    }
}