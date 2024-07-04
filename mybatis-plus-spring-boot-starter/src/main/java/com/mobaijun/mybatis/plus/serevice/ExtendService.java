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
package com.mobaijun.mybatis.plus.serevice;

import java.util.Collection;
import org.springframework.transaction.annotation.Transactional;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * InterfaceName: ExtendService
 * 接口描述：以前继承 com.baomidou.mybatisplus.extension.service.IService 的实现类，现在继承当前类
 *
 * @author MoBaiJun 2022/5/7 16:22
 */
public interface ExtendService<T> extends BaseService<T> {

    /**
     * 批量插入数据
     *
     * @param list 数据列表
     * @return int 改动行
     */
    @Transactional(rollbackFor = Exception.class)
    default boolean saveBatchSomeColumn(Collection<T> list) {
        return this.saveBatchSomeColumn(list, DEFAULT_BATCH_SIZE);
    }

    /**
     * 批量插入数据
     *
     * @param list      数据列表
     * @param batchSize 批次插入数据量
     * @return int 改动行
     */
    boolean saveBatchSomeColumn(Collection<T> list, int batchSize);
}