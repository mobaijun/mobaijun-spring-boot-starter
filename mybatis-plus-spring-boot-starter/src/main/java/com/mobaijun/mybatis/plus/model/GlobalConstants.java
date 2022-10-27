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
package com.mobaijun.mybatis.plus.model;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * ClassName: GlobalConstants
 * 类描述：全局常量
 *
 * @author MoBaiJun 2022/5/7 16:35
 */
public final class GlobalConstants {

    private GlobalConstants() {
    }

    /**
     * 未被逻辑删除的标识，即有效数据标识 逻辑删除标识，普通情况下可以使用 1 标识删除，0 标识存活 但在有唯一索引的情况下，会导致索引冲突，所以用 0 标识存活，
     * 已删除数据记录为删除时间戳
     */
    public static final Long NOT_DELETED_FLAG = 0L;

    /**
     * 生产环境
     */
    public static final String ENV_PROD = "prod";

    /**
     * 树根节点ID
     */
    public static final Integer TREE_ROOT_ID = 0;
}