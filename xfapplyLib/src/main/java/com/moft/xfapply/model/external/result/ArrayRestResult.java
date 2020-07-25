/**
 * Copyright (C), 2012-2018, 大连云帆科技有限公司
 * FileName: ArrayRestResult
 * Author:   cuihaijun
 * Date:     2018/3/20 17:14
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.moft.xfapply.model.external.result;

import java.util.List;

/**
 * REST返回数组对象<br>
 *
 * @author cuihaijun
 * @create 2018/3/20
 * @since 1.0.0
 */
public class ArrayRestResult extends RestResult {
    
    /**
     * @Fields serialVersionUID :
     */
    private static final long serialVersionUID = -5769002136746135162L;
    
    private int total = 0;
    
    @SuppressWarnings("rawtypes")
    private List contents = null;
    
    public ArrayRestResult() {
    }
    
    public int getTotal() {
        return total;
    }
    
    public void setTotal(int total) {
        this.total = total;
    }
    
    @SuppressWarnings("rawtypes")
    public List getContents() {
        return contents;
    }
    
    @SuppressWarnings("rawtypes")
    public void setContents(List contents) {
        this.contents = contents;
    }
    
}
