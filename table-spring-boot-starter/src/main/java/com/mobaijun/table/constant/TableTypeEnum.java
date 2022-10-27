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
package com.mobaijun.table.constant;

/**
 * software：IntelliJ IDEA 2022.1
 * enum name: TableTypeEnum
 * enum description： 表类型
 * 如果需要初始化数据，可以到本仓库提取<<a href="https://github.com/april-projects/init-data/">...</a>>
 *
 * @author MoBaiJun 2022/6/28 14:58
 */
public enum TableTypeEnum {

    /**
     * 全部表
     */
    ALL,

    /**
     * 字典表
     */
    DICT,

    /**
     * 配置表
     */
    CONFIG,

    /**
     * 备份表
     */
    BACKUPS,

    /**
     * 地图</>
     */
    REGION,

    /**
     * 组件配置表
     */
    SECOND,

    /**
     * 未知
     */
    OHTER
}