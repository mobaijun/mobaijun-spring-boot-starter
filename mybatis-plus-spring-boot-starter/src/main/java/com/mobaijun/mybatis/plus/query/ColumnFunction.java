package com.mobaijun.mybatis.plus.query;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;

/**
 * software：IntelliJ IDEA 2022.2.3
 * interface name: ColumnFunction
 * interface description: 连表查询时，从其他表获取的查询条件
 *
 * @author MoBaiJun 2022/11/15 9:38
 */
@FunctionalInterface
public interface ColumnFunction<T> extends SFunction<T, String> {

    /**
     * 快捷的创建一个 ColumnFunction
     *
     * @param columnString 实际的 column
     * @return ColumnFunction
     */
    static <T> ColumnFunction<T> create(String columnString) {
        return o -> columnString;
    }
}
