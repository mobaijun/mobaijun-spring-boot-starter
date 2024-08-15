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
package com.mobaijun.log.enums;

/**
 * Description: []
 * Author: [mobaijun]
 * Date: [2024/8/15 10:59]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public final class OperationTypes {

    /**
     * 其他操作
     */
    public static final int OTHER = 0;

    /**
     * 导入操作
     */
    public static final int IMPORT = 1;

    /**
     * 导出操作
     */
    public static final int EXPORT = 2;

    /**
     * 查看操作，主要用于敏感数据查询记录
     */
    public static final int READ = 3;

    /**
     * 新建操作
     */
    public static final int CREATE = 4;

    /**
     * 修改操作
     */
    public static final int UPDATE = 5;

    /**
     * 删除操作
     */
    public static final int DELETE = 6;

    /**
     * 下载操作
     */
    public static final int DOWNLOAD = 7;

    /**
     * 上传操作
     */
    public static final int UPLOAD = 8;

    /**
     * 审核操作
     */
    public static final int AUDIT = 9;
}
