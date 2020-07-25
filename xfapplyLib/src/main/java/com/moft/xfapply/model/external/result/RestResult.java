/**
 * Copyright (C), 2012-2018, 大连云帆科技有限公司
 * FileName: RestResult
 * Author:   cuihaijun
 * Date:     2018/3/20 17:14
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.moft.xfapply.model.external.result;

import java.io.Serializable;

/**
 * REST返回业务处理结果<br>
 *
 * @author cuihaijun
 * @create 2018/3/20
 * @since 1.0.0
 */
public class RestResult implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = -2313207886846781745L;
    
    private boolean success  = false;
    private Integer targetId = null;
    
    private Integer errorCode = 0;
    private String  message   = null;
    
    public RestResult() {
    }
    
    public boolean isSuccess() {
        return success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public Integer getTargetId() {
        return targetId;
    }
    
    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }
    
    public Integer getErrorCode() {
        return errorCode;
    }
    
    public void setErrorCode(Integer statusCode) {
        this.errorCode = statusCode;
    }
}
