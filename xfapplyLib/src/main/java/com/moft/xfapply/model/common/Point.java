package com.moft.xfapply.model.common;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/11/22 0022.
 */

public class Point implements Serializable {
    /**
     * @Fields serialVersionUID :
     */
    private static final long serialVersionUID = 1L;
    private float x;
    private float y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
