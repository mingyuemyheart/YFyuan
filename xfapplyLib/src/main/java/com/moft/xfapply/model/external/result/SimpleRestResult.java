/**
 * Copyright (C), 2012-2018, 大连云帆科技有限公司
 * FileName: SimpleRestResult
 * Author:   cuihaijun
 * Date:     2018/3/20 17:14
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.moft.xfapply.model.external.result;

/**
 * REST返回业务对象<br>
 *
 * @author cuihaijun
 * @create 2018/3/20
 * @since 1.0.0
 */
public class SimpleRestResult<T> extends RestResult {
    
    /**
     * @Fields serialVersionUID :
     */
    private static final long serialVersionUID = -1536416657048171029L;
    
    private T      data;
    private Object extraData;
    private Object optionResData;
    
    public T getData() {
        return data;
    }
    
    public void setData(T data) {
        this.data = data;
    }
    
    public Object getExtraData() {
        return extraData;
    }
    
    public void setExtraData(Object extraData) {
        this.extraData = extraData;
    }
    
    public Object getOptionResData() {
        return optionResData;
    }
    
    public void setOptionResData(Object optionResData) {
        this.optionResData = optionResData;
    }
}
