/**
 * Copyright (C), 2012-2018, 大连云帆科技有限公司
 * FileName: PushPayload
 * Author:   ZhangShouyan
 * Date:     2018/8/2 0002 14:10
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.moft.xfapply.model.common;

public class PushPayload {
    private String entityType;
    private String entityUuid;
    private String body;
    private Object extraData;

    public PushPayload(String entityType, String entityUuid, String body, Object extraData) {
        this.entityType = entityType;
        this.entityUuid = entityUuid;
        this.body = body;
        this.extraData = extraData;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getEntityUuid() {
        return entityUuid;
    }

    public void setEntityUuid(String entityUuid) {
        this.entityUuid = entityUuid;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Object getExtraData() {
        return extraData;
    }

    public void setExtraData(Object extraData) {
        this.extraData = extraData;
    }
}
