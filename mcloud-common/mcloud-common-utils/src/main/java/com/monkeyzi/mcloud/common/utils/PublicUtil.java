package com.monkeyzi.mcloud.common.utils;

import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Map;

@NoArgsConstructor
public class PublicUtil {

    /**
     * 判断一个对象是否为空  类型为String 集合  map
     * @param obj
     * @return
     */
    public static boolean isEmpty(Object obj){
        if (obj==null){
            return true;
        }
        if (obj==""){
            return true;
        }
        if (obj instanceof String){
            return ((String) obj).length()==0;
        }
        if (obj instanceof Collection){
            return  ((Collection) obj).size()==0;
        }
        if (obj instanceof Map){
            return ((Map) obj).size()==0;
        }
        return false;
    }

    /**
     * 判断一个对象不为空
     * @param obj
     * @return
     */
    public  static Boolean isNotEmpty(Object obj){
        return !isEmpty(obj);
    }
}
