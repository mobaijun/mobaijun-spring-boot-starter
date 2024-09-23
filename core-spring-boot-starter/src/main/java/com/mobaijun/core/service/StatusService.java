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
package com.mobaijun.core.service;

import com.mobaijun.base.entity.QueryStatusModel;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Description: [状态修改接口]
 * Author: [mobaijun]
 * Date: [2024/8/15 17:15]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 *
 * @param <T>      实体类型
 * @param <ID>     实体id类型
 * @param <Status> 状态属性类型
 */
public interface StatusService<T, ID, Status> {

    /**
     * 通用修改状态接口（同步版本）
     *
     * @param queryStatusModel 查询条件
     * @return 修改后的实体，如果实体不存在则返回Optional.empty()
     */
    Optional<T> updateStatus(QueryStatusModel<ID, Status> queryStatusModel);

    /**
     * 通用修改状态接口（异步版本）
     *
     * @param queryStatusModel 查询条件
     * @return CompletableFuture 包含修改后的实体
     */
    default CompletableFuture<Optional<T>> updateStatusAsync(QueryStatusModel<ID, Status> queryStatusModel) {
        return CompletableFuture.supplyAsync(() -> updateStatus(queryStatusModel));
    }
}

