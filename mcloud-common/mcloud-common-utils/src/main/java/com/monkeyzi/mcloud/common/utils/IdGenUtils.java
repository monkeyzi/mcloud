package com.monkeyzi.mcloud.common.utils;
/**
 * @author 高艳国
 * @date 2019/4/16 16:06
 * @description  ID生成工具类
 **/
public class IdGenUtils {
    /**
     * 生成一个long类型的Id
     * @return
     */
    public static long nextId(){
        return IdGen.get().nextId();
    }

    /**
     * 生成一个String类型的Id
     * @return
     */
    public static String getStrId(){
        return String.valueOf(IdGen.get().nextId());
    }
}
