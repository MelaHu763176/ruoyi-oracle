package com.ruoyi.common.utils.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


import com.ruoyi.common.annotation.Log;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.dozer.MappingException;


/**
 *  * 简单封装Dozer, 实现深度转换Bean<->Bean的Mapper.实现:
 *  * 
 *  * 1. 持有Mapper的单例. 
 *  * 2. 返回值类型转换.
 *  * 3. 批量转换Collection中的所有对象.
 *  * 4. 区分创建新的B对象与将对象A值复制到已存在的B对象两种函数.
 *  * 
 *  * @author hoo
 *  
 */
@Slf4j
public final class Bean2Utils {


    private Bean2Utils() {
    }


    /**
     * 持有Dozer单例, 避免重复创建DozerMapper消耗资源.
     */
    private static DozerBeanMapper dozer = new DozerBeanMapper();



    /**
     * 基于Dozer转换Collection中对象的类型.
     */
    public static <T> List<T> mapList(Collection<?> sourceList, Class<T> destinationClass) {
        List<T> destinationList = new ArrayList<>();
        try {
            for (Object sourceObject : sourceList) {
                T destinationObject = dozer.map(sourceObject, destinationClass);
                destinationList.add(destinationObject);
            }
        } catch (MappingException e) {
            log.error(e.getMessage());
        }
        return destinationList;
    }


    /**
     * 基于Dozer将对象A的值拷贝到对象B中.
     */
    public static void copy(Object source, Object destinationObject) {
        try {
            dozer.map(source, destinationObject);
        } catch (MappingException e) {
            log.error(e.getMessage());
        }
    }
}