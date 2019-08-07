package com.oa.auth.util;


import org.springframework.beans.BeansException;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;

/**
 * @description:
 * @author: kk
 * @create: 2018-12-24
 **/
public class CustomBeanUtils {
    public static void MergeBean(Object source, Object target, List<String> ignoreList,Boolean flag) throws BeansException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {

        Assert.notNull(source, "源对象不能为null");
        Assert.notNull(target, "目标对象不能null");
        Assert.isTrue(target.getClass().isAssignableFrom(source.getClass()),
                "源对象与目标对象不匹配");
        Field[] fields = target.getClass().getDeclaredFields();


//        boolean flag =true;//是否为同一版本
/*        try {
            Field srcfd = source.getClass().getField(benchProp);
            Field tarfd = target.getClass().getField(benchProp);
            if(!StringUtils.isEmpty(benchProp) && srcfd!=null){
                srcfd.setAccessible(true);
                if(srcfd.get(source).equals(tarfd.get(source))){
                    flag =false;//不同版本，直接添加
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        for (Field f : fields) {
            if (f.getType().isAssignableFrom(List.class) && flag) {
                f.setAccessible(true);
                List srcList = (List) f.get(source);
                List tarList = (List) f.get(target);
                if(srcList==null){continue;}
                if(tarList==null){
                    f.setAccessible(true);
                    f.set(target, f.get(source));
                    continue;
                }
                Iterator srcIter = srcList.iterator();
                Iterator tarIter = tarList.iterator();
                if (srcList.size() <= tarList.size()) {
                    while (srcIter.hasNext()) {
                        MergeBean(srcIter.next(), tarIter.next(), ignoreList,flag);
                    }
                    while (tarIter.hasNext()) {
                        tarIter.remove();
                    }
                } else {
                    while (tarIter.hasNext()) {
                        MergeBean(srcIter.next(), tarIter.next(), ignoreList,flag);
                    }
                    while (srcIter.hasNext()) {
                        tarList.add(srcIter.next());
                    }
                }
            } else {
                if (ignoreList != null && ignoreList.contains(f.getName())) {
                    continue;
                }

                f.setAccessible(true);
                f.set(target, f.get(source));
            }

        }


    }
}

