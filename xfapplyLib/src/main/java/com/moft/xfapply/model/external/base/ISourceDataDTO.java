package com.moft.xfapply.model.external.base;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/5/27 0027.
 */

public interface ISourceDataDTO extends Serializable {
    public String getUuid();
    public void setUuid(String uuid);
    public String getEleType();
    public String getGeometryType();
    public void setGeometryType(String geometryType);
    public Double getLongitude();
    public void setLongitude(Double longitude);
    public Double getLatitude();
    public void setLatitude(Double latitude);
    public String getBelongtoGroup();
    public Integer getVersion();
    public Double getDataQuality();
    public void setDataQuality(Double dataQuality);
    public List<IMediaDTO> getMediaInfos();
    public <T> void setMediaInfos(List<T> mediaList);
}
