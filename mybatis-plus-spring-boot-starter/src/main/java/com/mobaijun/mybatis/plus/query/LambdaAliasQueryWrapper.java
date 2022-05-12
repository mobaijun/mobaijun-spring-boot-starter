package com.mobaijun.mybatis.plus.query;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.mobaijun.mybatis.plus.alias.TableAliasHelper;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * ClassName: LambdaAliasQueryWrapper
 * 类描述：生成可携带表别名的查询条件 当前实体必须被配置表列名注解
 *
 * @author MoBaiJun 2022/5/7 16:14
 */
public class LambdaAliasQueryWrapper<T> extends LambdaQueryWrapper<T> {

    private final String tableAlias;

    /**
     * 带别名的查询列 sql 片段，默认为null，第一次使用时加载
     * eg. t.id,t.name
     */
    private String allAliasSqlSelect = null;

    public LambdaAliasQueryWrapper(T entity) {
        super(entity);
        this.tableAlias = TableAliasHelper.tableAlias(getEntityClass());
    }

    public LambdaAliasQueryWrapper(Class<T> entityClass) {
        super(entityClass);
        this.tableAlias = TableAliasHelper.tableAlias(getEntityClass());
    }

    /**
     * 获取查询带别名的查询字段 TODO 暂时没有想到好的方法进行查询字段注入 本来的想法是 自定义注入 SqlFragment 但是目前 mybatis-plus 的
     * TableInfo 解析在 xml 解析之后进行，导致 include 标签被提前替换， 先在 wrapper 中做简单处理吧
     *
     * @return String allAliasSqlSelect
     */
    public String getAllAliasSqlSelect() {
        if (allAliasSqlSelect == null) {
            allAliasSqlSelect = TableAliasHelper.tableAliasSelectSql(getEntityClass());
        }
        return allAliasSqlSelect;
    }

    /**
     * 查询条件构造时添加上表别名
     *
     * @param column 字段Lambda
     * @return 表别名.字段名，如：t.id
     */
    @Override
    protected String columnToString(SFunction<T, ?> column) {
        if (column instanceof OtherTableColumnAliasFunction) {
            @SuppressWarnings("unchecked")
            OtherTableColumnAliasFunction<T> otherTableColumnAlias = (OtherTableColumnAliasFunction<T>) column;
            return otherTableColumnAlias.apply(null);
        }
        String columnName = super.columnToString(column, true);
        return tableAlias == null ? columnName : tableAlias + "." + columnName;
    }
}
