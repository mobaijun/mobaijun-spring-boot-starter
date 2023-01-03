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
package com.mobaijun.table.base;

import com.mobaijun.table.constant.TableTypeEnum;
import com.mobaijun.table.entity.backups.MySqlBaskupScript;
import com.mobaijun.table.entity.config.ConfigScript;
import com.mobaijun.table.entity.defaults.DefaultAll;
import com.mobaijun.table.entity.dict.DictAllScript;
import com.mobaijun.table.entity.other.OtherExcpteion;
import com.mobaijun.table.entity.region.RegionScript;
import com.mobaijun.table.entity.second.SecondScript;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: BaseCreateTable
 * class description： 简单工厂和模板方法
 *
 * @author MoBaiJun 2022/6/28 15:56
 */
public class BaseCreateTable {

    /**
     * 根据表类型拼接 sql
     *
     * @param tablePrefix 表前缀
     */
    public String splicingSql(String tablePrefix) {
        return null;
    }

    /**
     * 获取用户选择的表类型
     * 如果需要初始化数据，可以到本仓库提取<<a href="https://github.com/april-projects/init-data/">...</a>>
     *
     * @param typeEnum 类型
     * @return 选择的执行器
     */
    public static BaseCreateTable typeEnum(TableTypeEnum typeEnum) {
        switch (typeEnum) {
            case DICT:
                // 字典
                return new DictAllScript();
            case CONFIG:
                // 系统配置类
                return new ConfigScript();
            case REGION:
                // 全国地区表
                return new RegionScript();
            case SECOND:
                // 组件配置表
                return new SecondScript();
            case BACKUPS:
                // MySQL备份表
                return new MySqlBaskupScript();
            case OTHER:
                // 其他，找不到
                return new OtherExcpteion();
            default:
                // 默认全部表
                return new DefaultAll();
        }
    }
}