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
     * 判断当前数据库是否为MariaDB。
     *
     * @return 如果是MariaDB则返回true，否则返回false
     */
    public static boolean isMariaDb() {
        return DbType.MARIADB == getDataBaseType();
    }

    /**
     * 判断当前数据库是否为Oracle12c。
     *
     * @return 如果是Oracle12c则返回true，否则返回false
     */
    public static boolean isOracle12c() {
        return DbType.ORACLE_12C == getDataBaseType();
    }

    /**
     * 判断当前数据库是否为DB2。
     *
     * @return 如果是DB2则返回true，否则返回false
     */
    public static boolean isDb2() {
        return DbType.DB2 == getDataBaseType();
    }

    /**
     * 判断当前数据库是否为H2。
     *
     * @return 如果是H2则返回true，否则返回false
     */
    public static boolean isH2() {
        return DbType.H2 == getDataBaseType();
    }

    /**
     * 判断当前数据库是否为HSQL。
     *
     * @return 如果是HSQL则返回true，否则返回false
     */
    public static boolean isHsql() {
        return DbType.HSQL == getDataBaseType();
    }

    /**
     * 判断当前数据库是否为SQLite。
     *
     * @return 如果是SQLite则返回true，否则返回false
     */
    public static boolean isSqlite() {
        return DbType.SQLITE == getDataBaseType();
    }

    /**
     * 判断当前数据库是否为SQLServer2005。
     *
     * @return 如果是SQLServer2005则返回true，否则返回false
     */
    public static boolean isSqlServer2005() {
        return DbType.SQL_SERVER2005 == getDataBaseType();
    }

    /**
     * 判断当前数据库是否为DM（达梦数据库）。
     *
     * @return 如果是DM则返回true，否则返回false
     */
    public static boolean isDm() {
        return DbType.DM == getDataBaseType();
    }

    /**
     * 判断当前数据库是否为虚谷数据库。
     *
     * @return 如果是虚谷数据库则返回true，否则返回false
     */
    public static boolean isXugu() {
        return DbType.XU_GU == getDataBaseType();
    }

    /**
     * 判断当前数据库是否为KingbaseES（人大金仓）。
     *
     * @return 如果是KingbaseES则返回true，否则返回false
     */
    public static boolean isKingbaseEs() {
        return DbType.KINGBASE_ES == getDataBaseType();
    }

    /**
     * 判断当前数据库是否为Phoenix HBase数据库。
     *
     * @return 如果是Phoenix则返回true，否则返回false
     */
    public static boolean isPhoenix() {
        return DbType.PHOENIX == getDataBaseType();
    }

    /**
     * 判断当前数据库是否为Gauss数据库。
     *
     * @return 如果是Gauss则返回true，否则返回false
     */
    public static boolean isGauss() {
        return DbType.GAUSS == getDataBaseType();
    }

    /**
     * 判断当前数据库是否为ClickHouse数据库。
     *
     * @return 如果是ClickHouse则返回true，否则返回false
     */
    public static boolean isClickHouse() {
        return DbType.CLICK_HOUSE == getDataBaseType();
    }

    /**
     * 判断当前数据库是否为GBase。
     *
     * @return 如果是GBase则返回true，否则返回false
     */
    public static boolean isGbase() {
        return DbType.GBASE == getDataBaseType();
    }

    /**
     * 判断当前数据库是否为GBase8S。
     *
     * @return 如果是GBase8S则返回true，否则返回false
     */
    public static boolean isGbase8s() {
        return DbType.GBASE_8S == getDataBaseType();
    }

    /**
     * 判断当前数据库是否为GBase8S-PG。
     *
     * @return 如果是GBase8S-PG则返回true，否则返回false
     */
    public static boolean isGbase8sPg() {
        return DbType.GBASE8S_PG == getDataBaseType();
    }

    /**
     * 判断当前数据库是否为GBase8C。
     *
     * @return 如果是GBase8C则返回true，否则返回false
     */
    public static boolean isGbase8c() {
        return DbType.GBASE_8C == getDataBaseType();
    }

    /**
     * 判断当前数据库是否为Sinodb（星瑞格）。
     *
     * @return 如果是Sinodb则返回true，否则返回false
     */
    public static boolean isSinodb() {
        return DbType.SINODB == getDataBaseType();
    }

    /**
     * 判断当前数据库是否为Oscar（神通）。
     *
     * @return 如果是Oscar则返回true，否则返回false
     */
    public static boolean isOscar() {
        return DbType.OSCAR == getDataBaseType();
    }

    /**
     * 判断当前数据库是否为Sybase ASE。
     *
     * @return 如果是Sybase则返回true，否则返回false
     */
    public static boolean isSybase() {
        return DbType.SYBASE == getDataBaseType();
    }

    /**
     * 判断当前数据库是否为OceanBase。
     *
     * @return 如果是OceanBase则返回true，否则返回false
     */
    public static boolean isOceanBase() {
        return DbType.OCEAN_BASE == getDataBaseType();
    }

    /**
     * 判断当前数据库是否为Firebird。
     *
     * @return 如果是Firebird则返回true，否则返回false
     */
    public static boolean isFirebird() {
        return DbType.FIREBIRD == getDataBaseType();
    }

    /**
     * 判断当前数据库是否为HighGo。
     *
     * @return 如果是HighGo则返回true，否则返回false
     */
    public static boolean isHighGo() {
        return DbType.HIGH_GO == getDataBaseType();
    }

    /**
     * 判断当前数据库是否为CUBRID。
     *
     * @return 如果是CUBRID则返回true，否则返回false
     */
    public static boolean isCubrid() {
        return DbType.CUBRID == getDataBaseType();
    }

    /**
     * 判断当前数据库是否为SUNDB。
     *
     * @return 如果是SUNDB则返回true，否则返回false
     */
    public static boolean isSunDb() {
        return DbType.SUNDB == getDataBaseType();
    }

    /**
     * 判断当前数据库是否为SAP HANA。
     *
     * @return 如果是SAP HANA则返回true，否则返回false
     */
    public static boolean isSapHana() {
        return DbType.SAP_HANA == getDataBaseType();
    }

    /**
     * 判断当前数据库是否为Impala。
     *
     * @return 如果是Impala则返回true，否则返回false
     */
    public static boolean isImpala() {
        return DbType.IMPALA == getDataBaseType();
    }

    /**
     * 判断当前数据库是否为Vertica。
     *
     * @return 如果是Vertica则返回true，否则返回false
     */
    public static boolean isVertica() {
        return DbType.VERTICA == getDataBaseType();
    }

    /**
     * 判断当前数据库是否为XCloud。
     *
     * @return 如果是XCloud则返回true，否则返回false
     */
    public static boolean isXCloud() {
        return DbType.XCloud == getDataBaseType();
    }

    /**
     * 判断当前数据库是否为Redshift（亚马逊）。
     *
     * @return 如果是Redshift则返回true，否则返回false
     */
    public static boolean isRedshift() {
        return DbType.REDSHIFT == getDataBaseType();
    }

    /**
     * 判断当前数据库是否为OpenGauss。
     *
     * @return 如果是OpenGauss则返回true，否则返回false
     */
    public static boolean isOpenGauss() {
        return DbType.OPENGAUSS == getDataBaseType();
    }

    /**
     * 判断当前数据库是否为TDengine。
     *
     * @return 如果是TDengine则返回true，否则返回false
     */
    public static boolean isTdEngine() {
        return DbType.TDENGINE == getDataBaseType();
    }

    /**
     * 判断当前数据库是否为Informix。
     *
     * @return 如果是Informix则返回true，否则返回false
     */
    public static boolean isInformix() {
        return DbType.INFORMIX == getDataBaseType();
    }

    /**
     * 判断当前数据库是否为UXDB（优炫）。
     *
     * @return 如果是UXDB则返回true，否则返回false
     */
    public static boolean isUxDb() {
        return DbType.UXDB == getDataBaseType();
    }

    /**
     * 判断当前数据库是否为Lealone。
     *
     * @return 如果是Lealone则返回true，否则返回false
     */
    public static boolean isLealone() {
        return DbType.LEALONE == getDataBaseType();
    }

    /**
     * 判断当前数据库是否为Trino。
     *
     * @return 如果是Trino则返回true，否则返回false
     */
    public static boolean isTrino() {
        return DbType.TRINO == getDataBaseType();
    }

    /**
     * 判断当前数据库是否为Presto。
     *
     * @return 如果是Presto则返回true，否则返回false
     */
    public static boolean isPresto() {
        return DbType.PRESTO == getDataBaseType();
    }

    /**
     * 判断当前数据库是否为Other。
     *
     * @return 如果是Other则返回true，否则返回false
     */
    public static boolean isOther() {
        return DbType.OTHER == getDataBaseType();
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
        return switch (dbType) {
            case SQL_SERVER ->
                // charindex(',100,' , ',0,100,101,') <> 0
                    "charindex(',%s,' , ','+%s+',') <> 0".formatted(var, var2);
            case POSTGRE_SQL ->
                // (select position(',100,' in ',0,100,101,')) <> 0
                    "(select position(',%s,' in ','||%s||',')) <> 0".formatted(var, var2);
            case ORACLE, ORACLE_12C ->
                // instr(',0,100,101,' , ',100,') <> 0
                    "instr(','||%s||',' , ',%s,') <> 0".formatted(var2, var);
            case MARIADB, MYSQL, CUBRID, TDENGINE, OTHER ->
                // find_in_set('100' , '0,100,101')
                    "find_in_set('%s' , %s) <> 0".formatted(var, var2);
            case DB2, SAP_HANA, INFORMIX, DM ->
                // locate(',100,' , ',0,100,101,') <> 0
                    "locate(',%s,' , ','||%s||',') <> 0".formatted(var, var2);
            case H2, HSQL, SQLITE, PHOENIX ->
                // instr(',0,100,101,' , ',100,') <> 0
                    "instr(',' || %s || ',', ',%s,') <> 0".formatted(var2, var);
            case XU_GU, KINGBASE_ES, FIREBIRD, REDSHIFT, UXDB, GAUSS, OPENGAUSS ->
                // position(',100,' in ',0,100,101,') <> 0
                    "position(',%s,' in ','||%s||',') <> 0".formatted(var, var2);
            case CLICK_HOUSE ->
                // position(',0,100,101,' in ',100,') > 0
                    "position(','||%s||',' in ',%s,') > 0".formatted(var2, var);
            case GBASE, GBASE_8S, GBASE_8C, GBASE8S_PG, TRINO, PRESTO ->
                // locate(',100,' , ',0,100,101,') > 0
                    "locate(',%s,' , ','||%s||',') > 0".formatted(var, var2);
            case SINODB, OSCAR, SYBASE ->
                // charindex(',100,' , ',0,100,101,') > 0
                    "charindex(',%s,' , ','+%s+',') > 0".formatted(var, var2);
            case OCEAN_BASE, HIGH_GO, VERTICA, XCloud, LEALONE ->
                // instr(',0,100,101,' , ',100,') > 0
                    "instr(',' || %s || ',', ',%s,') > 0".formatted(var2, var);
            default -> throw new UnsupportedOperationException("Unsupported database type: " + dbType);
        };
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
