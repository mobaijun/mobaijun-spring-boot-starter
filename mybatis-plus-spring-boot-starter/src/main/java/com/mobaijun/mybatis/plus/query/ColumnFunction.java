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
package com.mobaijun.mybatis.plus.query;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;

/**
 * software：IntelliJ IDEA 2022.2.3
 * interface name: ColumnFunction
 * interface description: 连表查询时，从其他表获取的查询条件
 *
 * @author MoBaiJun 2022/11/15 9:38
 */
@FunctionalInterface
public interface ColumnFunction<T> extends SFunction<T, String> {

    /**
     * 快捷的创建一个 ColumnFunction
     *
     * @param columnString 实际的 column
     * @return ColumnFunction
     */
    static <T> ColumnFunction<T> create(String columnString) {
        return o -> columnString;
    }
}
