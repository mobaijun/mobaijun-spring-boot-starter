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
package com.mobaijun.mybatis.plus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mobaijun.base.model.PageParam;
import com.mobaijun.mybatis.plus.toolkit.PageUtil;
import java.util.Collection;
import org.apache.ibatis.annotations.Param;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * InterfaceName: ExtendMapper
 * 接口描述：扩展映射器
 *
 * @author MoBaiJun 2022/5/7 16:23
 */
public interface ExtendMapper<T> extends BaseMapper<T> {

    /**
     * 根据 PageParam 生成一个 IPage 实例
     *
     * @param pageParam 分页参数
     * @param <V>       返回的 Record 对象
     * @return IPage
     */
    default <V> IPage<V> prodPage(PageParam pageParam) {
        return PageUtil.prodPage(pageParam);
    }

    /**
     * 批量插入数据 实现类 InsertBatchSomeColumn
     *
     * @param list 数据列表
     */
    void insertBatchSomeColumn(@Param("collection") Collection<T> list);
}