package com.moft.xfapply.model.KeyDiagram;

import com.moft.xfapply.model.common.Point;

import java.util.List;

/**
 * Created by Administrator on 2018/11/22 0022.
 */

public class GeometryPlot extends Geometry {
    private int baseWidth;
    private int baseHeight;
    private String baseUuid;
    private String baseUrl;
    private List<Point> xys;

    public int getBaseWidth() {
        return baseWidth;
    }

    public void setBaseWidth(int baseWidth) {
        this.baseWidth = baseWidth;
    }

    public int getBaseHeight() {
        return baseHeight;
    }

    public void setBaseHeight(int baseHeight) {
        this.baseHeight = baseHeight;
    }

    public String getBaseUuid() {
        return baseUuid;
    }

    public void setBaseUuid(String baseUuid) {
        this.baseUuid = baseUuid;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public List<Point> getXys() {
        return xys;
    }

    public void setXys(List<Point> xys) {
        this.xys = xys;
    }
}
