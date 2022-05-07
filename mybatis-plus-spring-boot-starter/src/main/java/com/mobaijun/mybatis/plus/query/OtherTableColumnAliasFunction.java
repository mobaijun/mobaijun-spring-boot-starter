package com.mobaijun.mybatis.plus.query;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * ClassName: OtherTableColumnAliasFunction
 * 类描述：连表查询时，从其他表获取的查询条件
 *
 * @author MoBaiJun 2022/5/7 16:14
 */
@FunctionalInterface
public interface OtherTableColumnAliasFunction <T> extends SFunction<T, String> {
}
