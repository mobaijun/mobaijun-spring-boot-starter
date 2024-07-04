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
package com.mobaijun.mybatis.plus.serevice.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.mobaijun.mybatis.plus.mapper.ExtendMapper;
import com.mobaijun.mybatis.plus.serevice.ExtendService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * ClassName: ExtendServiceImpl
 * 类描述：以前继承 com.baomidou.mybatisplus.extension.service.impl.ServiceImpl 的实现类，现在继承本类
 *
 * @author MoBaiJun 2022/5/7 16:23
 */
public class ExtendServiceImpl<M extends ExtendMapper<T>, T> extends BaseServiceImpl<M, T> implements ExtendService<T> {

    /**
     * 批量插入数据
     *
     * @param list      数据列表
     * @param batchSize 批次插入数据量
     * @return int 改动行
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveBatchSomeColumn(Collection<T> list, int batchSize) {
        if (CollectionUtils.isEmpty(list)) {
            return false;
        }
        final int batch = Math.min(list.size(), batchSize);
        List<T> subList = new ArrayList<>(batch);
        for (T t : list) {
            if (subList.size() >= batch) {
                this.baseMapper.insertBatchSomeColumn(subList);
                subList = new ArrayList<>(batch);
            }
            subList.add(t);
        }
        if (CollectionUtils.isEmpty(subList)) {
            this.baseMapper.insertBatchSomeColumn(subList);
        }
        return true;
    }
}