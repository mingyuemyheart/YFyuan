package com.moft.xfapply.model.KeyDiagram;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/11/22 0022.
 */

public class Geometry implements Serializable {
    private String geometryType;

    public String getGeometryType() {
        return geometryType;
    }

    public void setGeometryType(String geometryType) {
        this.geometryType = geometryType;
    }
}
