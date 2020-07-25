package com.moft.xfapply.model.common;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/15 0015.
 */

public class PrimaryAttribute implements Serializable {
    /**
     * @Fields serialVersionUID :
     */
    private static final long serialVersionUID = 1L;
    private String primaryValue1;
    private String primaryValue2;
    private String primaryValue3;
    private String primaryValue4 = ""; // ID:900352【物质器材灭火剂】追加车辆信息显示。 存放车载灭火剂和车载器材的所属车辆的名字

    public String getPrimaryValue1() {
        return primaryValue1;
    }

    public void setPrimaryValue1(String primaryValue1) {
        this.primaryValue1 = primaryValue1;
    }

    public String getPrimaryValue2() {
        return primaryValue2;
    }

    public void setPrimaryValue2(String primaryValue2) {
        this.primaryValue2 = primaryValue2;
    }

    public String getPrimaryValue3() {
        return primaryValue3;
    }

    public void setPrimaryValue3(String primaryValue3) {
        this.primaryValue3 = primaryValue3;
    }

    public String getPrimaryValue4() {
        return primaryValue4;
    }

    public void setPrimaryValue4(String primaryValue4) {
        this.primaryValue4 = primaryValue4;
    }
}
