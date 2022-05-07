package com.mobaijun.mybatis.plus.model.domain;

import lombok.Data;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * ClassName: SelectData
 * 类描述： 下拉框数据
 *
 * @author MoBaiJun 2022/5/7 16:01
 */
@Data
public class SelectData<T
        > {

    /**
     * 显示的数据
     */
    private String name;

    /**
     * 选中获取的属性
     */
    private Object value;

    /**
     * 是否被选中
     */
    private Boolean selected;

    /**
     * 是否禁用
     */
    private Boolean disabled;

    /**
     * 分组标识
     */
    private String type;

    /**
     * 扩展对象
     */
    private T extendObj;
}
