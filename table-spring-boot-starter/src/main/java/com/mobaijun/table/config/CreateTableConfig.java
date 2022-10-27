/*
 * Copyright (C) 2022 www.mobaijun.com
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
package com.mobaijun.table.config;

import com.mobaijun.table.base.BaseCreateTable;
import com.mobaijun.table.constant.TableTypeEnum;
import com.mobaijun.table.prop.TableProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: TableAutoConfiguration
 * class description： 自动生成表配置类
 *
 * @author MoBaiJun 2022/6/28 13:56
 */
public class CreateTableConfig {

    /**
     * logger
     */
    private static final Logger log = LoggerFactory.getLogger(CreateTableConfig.class);

    /**
     * 初始化构造，创建表
     *
     * @param tableProperties 数据源配置
     */
    public CreateTableConfig(TableProperties tableProperties) {
        initializationTable(tableProperties);
    }

    /**
     * 初始化创建表语句
     *
     * @param tableProperties 数据源配置
     */
    public void initializationTable(TableProperties tableProperties) {
        Statement stmt = null;
        try {
            Connection connection = null;
            Class.forName(tableProperties.getDriverClassName());
            log.info("Connecting to a selected database...");
            connection = DriverManager.getConnection(tableProperties.getUrl(),
                    tableProperties.getUsername(), tableProperties.getPassword());
            log.info("Connected database successfully...");

            log.info("Creating table in given database...");
            stmt = connection.createStatement();
            List<TableTypeEnum> typeEnum = tableProperties.getTableConfig().getTypeEnum();
            Statement finalStmt = stmt;
            typeEnum.forEach(type -> {
                BaseCreateTable createTable = BaseCreateTable.typeEnum(type);
                try {
                    // 执行 sql
                    finalStmt.executeUpdate(createTable.splicingSql(tableProperties.getTableConfig().getTablePrefix()));
                } catch (SQLException e) {
                    log.error("Failed to create link, driver is{}:", tableProperties.getDriverClassName(), e);
                }
            });
            log.info("Created table in given database...");
            // Handle errors for JDBC
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            log.error("Failed to create link, driver is{}:", tableProperties.getDriverClassName(), e);
        }
        log.info("Goodbye!");
    }
}