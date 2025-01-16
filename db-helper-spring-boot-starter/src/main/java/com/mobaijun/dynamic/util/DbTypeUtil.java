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
package com.mobaijun.dynamic.util;

import com.baomidou.mybatisplus.annotation.DbType;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: [数据源类型]
 * Author: [mobaijun]
 * Date: [2025/1/9 9:44]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public class DbTypeUtil {

    /**
     * 数据库类型枚举
     */
    private static final Map<String, DbType> DB_TYPE_MAP = new HashMap<>();

    static {
        DB_TYPE_MAP.put(":mysql:", DbType.MYSQL);
        DB_TYPE_MAP.put(":cobar:", DbType.MYSQL);
        DB_TYPE_MAP.put(":oracle:", DbType.ORACLE);
        DB_TYPE_MAP.put(":postgresql:", DbType.POSTGRE_SQL);
        DB_TYPE_MAP.put(":sqlserver:", DbType.SQL_SERVER);
        DB_TYPE_MAP.put(":db2:", DbType.DB2);
        DB_TYPE_MAP.put(":mariadb:", DbType.MARIADB);
        DB_TYPE_MAP.put(":sqlite:", DbType.SQLITE);
        DB_TYPE_MAP.put(":h2:", DbType.H2);
        DB_TYPE_MAP.put(":kingbase:", DbType.KINGBASE_ES);
        DB_TYPE_MAP.put(":kingbase8:", DbType.KINGBASE_ES);
        DB_TYPE_MAP.put(":dm:", DbType.DM);
        DB_TYPE_MAP.put(":zenith:", DbType.GAUSS);
        DB_TYPE_MAP.put(":oscar:", DbType.OSCAR);
        DB_TYPE_MAP.put(":firebird:", DbType.FIREBIRD);
        DB_TYPE_MAP.put(":xugu:", DbType.XU_GU);
        DB_TYPE_MAP.put(":clickhouse:", DbType.CLICK_HOUSE);
        DB_TYPE_MAP.put(":sybase:", DbType.SYBASE);
    }

    /**
     * 根据数据库连接地址推断数据库类型
     *
     * @param jdbcUrl url
     * @return DbType
     */
    public static DbType getDbType(String jdbcUrl) {
        // 遍历 Map 查找包含的数据库类型
        for (Map.Entry<String, DbType> entry : DB_TYPE_MAP.entrySet()) {
            if (jdbcUrl.contains(entry.getKey())) {
                return entry.getValue();
            }
        }
        // 默认返回 OTHER
        return DbType.OTHER;
    }

    /**
     * 根据 dbType 返回对应的数据库类型
     *
     * @param dbType 数据库类型
     * @return 数据库对应的值
     */
    public static int getDbType(DbType dbType) {
        return switch (dbType) {
            case MYSQL -> 1;
            case ORACLE -> 2;
            case POSTGRE_SQL -> 3;
            case SQL_SERVER -> 4;
            default -> 0;
        };
    }
}
