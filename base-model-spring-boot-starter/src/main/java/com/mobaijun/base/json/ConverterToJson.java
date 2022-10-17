package com.mobaijun.base.json;

import io.swagger.annotations.ApiModelProperty;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: ConverterToJson
 * class description：对象转 json 字符串
 *
 * @author MoBaiJun 2022/10/17 8:47
 */
public class ConverterToJson {

    /**
     * 对象转 json 格式数据
     *
     * @param isPage 是否分页
     * @param c      对象
     * @param <T>    类型
     */
    public static <T> void coverToJson(boolean isPage, Class<T> c) {
        // 获取所有的字段
        Field[] fields = c.getDeclaredFields();
        StringBuilder sb = new StringBuilder("{");
        String pageFieldStr = "{\"startRow\":0,\"parameter\":{\"current\":\"当前页码,从 1 开始\",\"size\":\"每页显示条数,最大值为系统设置，默认 100\",\"sorts\":\"排序规则\"},\"size\":20,\"current\":1}";
        AtomicInteger i = new AtomicInteger();
        Arrays.stream(fields).forEach(temp -> {
            // 判断字段注解是否存在
            boolean annotationPresent2 = temp.isAnnotationPresent(ApiModelProperty.class);
            if (annotationPresent2) {
                ApiModelProperty name = temp.getAnnotation(ApiModelProperty.class);
                // 获取注解值
                String nameStr = name.value();
                if (i.get() == fields.length - 1) {
                    sb.append("\"").append(temp.getName()).append("\":").append("\"").append(nameStr).append("\"");
                } else {
                    sb.append("\"").append(temp.getName()).append("\":").append("\"").append(nameStr).append("\",");
                }
            }
            i.getAndIncrement();
        });
        sb.append("}");
        System.out.println(isPage ? pageFieldStr.replace("parameterReplace", sb.toString()) : sb.toString());
    }
}
