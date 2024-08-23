/*
 * Copyright (C) 2022 [www.mobaijun.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mobaijun.dynamic.helper;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.mybatisplus.annotation.DbType;
import com.mobaijun.core.spring.SpringUtil;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

/**
 * Description: [动态数据源工具类，提供数据库类型判断和数据源名称获取的功能。]
 * Author: [mobaijun]
 * Date: [2024/8/13 14:13]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public class DataBaseHelper {

    private static final DynamicRoutingDataSource DS = SpringUtil.getBean(DynamicRoutingDataSource.class);

    /**
     * 获取当前数据库类型。
     *
     * @return 当前数据库类型
     */
    public static DbType getDataBaseType() {
        DataSource dataSource = DS.determineDataSource();
        try (Connection conn = dataSource.getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();
            String databaseProductName = metaData.getDatabaseProductName();
            return DbType.getDbType(databaseProductName);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 判断当前数据库是否为MySQL。
     *
     * @return 如果是MySQL则返回true，否则返回false
     */
    public static boolean isMySql() {
        return DbType.MYSQL == getDataBaseType();
    }

    /**
     * 判断当前数据库是否为Oracle。
     *
     * @return 如果是Oracle则返回true，否则返回false
     */
    public static boolean isOracle() {
        return DbType.ORACLE == getDataBaseType();
    }

    /**
     * 判断当前数据库是否为PostGreSQL。
     *
     * @return 如果是PostGreSQL则返回true，否则返回false
     */
    public static boolean isPostGerSql() {
        return DbType.POSTGRE_SQL == getDataBaseType();
    }

    /**
     * 判断当前数据库是否为SQL Server。
     *
     * @return 如果是SQL Server则返回true，否则返回false
     */
    public static boolean isSqlServer() {
        return DbType.SQL_SERVER == getDataBaseType();
    }

    /**
     * 根据数据库类型，生成对应的SQL查找语句。
     *
     * @param args 要查找的值
     * @param var2 要查找的字符串
     * @return 生成的SQL查找语句
     */
    public static <T> String findInSet(T args, String var2) {
        DbType dbType = getDataBaseType();
        String var = args.toString();
        if (dbType == DbType.SQL_SERVER) {
            // char index(',100,' , ',0,100,101,') <> 0
            return "charindex(',%s,' , ','+%s+',') <> 0".formatted(var, var2);
        } else if (dbType == DbType.POSTGRE_SQL) {
            // (select position(',100,' in ',0,100,101,')) <> 0
            return "(select position(',%s,' in ','||%s||',')) <> 0".formatted(var, var2);
        } else if (dbType == DbType.ORACLE) {
            // instr(',0,100,101,' , ',100,') <> 0
            return "instr(','||%s||',' , ',%s,') <> 0".formatted(var2, var);
        }
        // find_in_set('100' , '0,100,101')
        return "find_in_set('%s' , %s) <> 0".formatted(var, var2);
    }

    /**
     * 获取当前加载的所有数据源名称列表。
     *
     * @return 数据源名称列表
     */
    public static List<String> getDataSourceNameList() {
        return new ArrayList<>(DS.getDataSources().keySet());
    }
}
