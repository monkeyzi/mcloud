package com.monkeyzi.mcloud.common.core.base;

import com.monkeyzi.mcloud.common.result.R;
import com.monkeyzi.mcloud.common.utils.PublicUtil;

/**
 * @author: 高yg
 * @date: 2019/4/16 21:52
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
public class BaseController {

    /**
     * 处理响应
     * @param result
     * @param <T>
     * @return
     */
    protected <T> R<T> handleResult(T result){
        boolean flag=isFlag(result);
        if (flag){
            return R.ok("success",result);
        }else {
            return R.error("error",null);
        }
    }

    /**
     * 处理响应
     * @param result
     * @param msg
     * @param <E>
     * @return
     */
    protected <E> R<E> handleResult(E result,String msg){
        boolean flag=isFlag(result);
        if (flag){
            return R.ok(msg,result);
        }else {
            return R.error(msg,null);
        }
    }

    private boolean isFlag(Object result) {
        boolean flag;
        if (result instanceof Integer) {
            flag = (Integer) result > 0;
        } else if (result instanceof Boolean) {
            flag = (Boolean) result;
        } else {
            flag = PublicUtil.isNotEmpty(result);
        }
        return flag;
    }
}
