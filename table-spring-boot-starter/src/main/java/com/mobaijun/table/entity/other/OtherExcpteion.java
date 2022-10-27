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
package com.mobaijun.table.entity.other;

import com.mobaijun.table.base.BaseCreateTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: OtherExcpteion
 * class description： 选择其他就告诉用户异常
 * 如果需要初始化数据，可以到本仓库提取<<a href="https://github.com/april-projects/init-data/">...</a>>
 *
 * @author MoBaiJun 2022/6/28 16:13
 */
public class OtherExcpteion extends BaseCreateTable {

    /**
     * logger
     */
    private static final Logger log = LoggerFactory.getLogger(OtherExcpteion.class);

    @Override
    public String splicingSql(String tablePrefix) {
        log.error("Table selector exception, please select another table and try again！！！");
        return "";
    }
}