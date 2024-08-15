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
package com.mobaijun.table.entity.defaults;

import com.mobaijun.table.base.BaseCreateTable;
import com.mobaijun.table.constant.Constant;
import com.mobaijun.table.constant.TableConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: DefaultAll
 * class description： 默认全部数据表
 * 如果需要初始化数据，可以到本仓库提取<<a href="https://github.com/april-projects/init-data/">...</a>>
 *
 * @author MoBaiJun 2022/6/28 16:09
 */
public class DefaultAll extends BaseCreateTable {

    /**
     * logger
     */
    private static final Logger log = LoggerFactory.getLogger(DefaultAll.class);

    @Override
    public String splicingSql(String tablePrefix) {
        StringBuilder sql = new StringBuilder();
        // 项目配置表
        if (StringUtils.hasText(tablePrefix)) {
            // 拼接表前缀
            sql
                    // MySQL备份表
                    .append(Constant.TABLE_PREFIX)
                    .append(tablePrefix)
                    .append(TableConstant.MYSQL_BACKUPS)
                    .append("\n")
                    // 项目配置表
                    .append(Constant.TABLE_PREFIX)
                    .append(tablePrefix)
                    .append(TableConstant.CONFIG)
                    .append("\n")
                    // 字典表
                    .append(Constant.TABLE_PREFIX)
                    .append(tablePrefix)
                    .append(TableConstant.DICT_TYPE)
                    .append("\n")
                    .append(Constant.TABLE_PREFIX)
                    .append(tablePrefix)
                    .append(TableConstant.DICT_DATA)
                    .append("\n")
                    // 地区表
                    .append(Constant.TABLE_PREFIX)
                    .append(tablePrefix)
                    .append(TableConstant.REGION)
                    .append("\n")
                    // 组件配置表
                    .append(Constant.TABLE_PREFIX)
                    .append(tablePrefix)
                    .append(TableConstant.SECOND);
        } else {
            // 空的不拼接
            sql
                    // MySQL备份表
                    .append(Constant.TABLE_PREFIX)
                    .append(TableConstant.MYSQL_BACKUPS)
                    .append("\n")
                    // 项目配置表
                    .append(Constant.TABLE_PREFIX)
                    .append(TableConstant.CONFIG)
                    .append("\n")
                    // 字典表
                    .append(Constant.TABLE_PREFIX)
                    .append(TableConstant.DICT_TYPE)
                    .append("\n")
                    .append(Constant.TABLE_PREFIX)
                    .append(TableConstant.DICT_DATA)
                    .append("\n")
                    // 地区表
                    .append(Constant.TABLE_PREFIX)
                    .append(TableConstant.REGION)
                    .append("\n")
                    // 组件配置表
                    .append(Constant.TABLE_PREFIX)
                    .append(TableConstant.SECOND);
        }
        log.info("The SQL statement to create all configuration tables is:{}", sql);
        return sql.toString();
    }
}