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
package com.mobaijun.mybatis.plus.toolkit;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mobaijun.base.model.PageParam;

import java.util.List;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * ClassName: PageUtil
 * 类描述：分页工具
 *
 * @author MoBaiJun 2022/5/7 16:21
 */
public class PageUtil {

    private PageUtil() {
    }

    /**
     * 根据 PageParam 生成一个 IPage 实例
     *
     * @param pageParam 分页参数
     * @param <V>       返回的 Record 对象
     * @return IPage
     */
    public static <V> IPage<V> prodPage(PageParam pageParam) {
        Page<V> page = new Page<>(pageParam.getCurrent(), pageParam.getSize());
        List<PageParam.Sort> sorts = pageParam.getSorts();
        sorts.forEach(sort -> {
            OrderItem orderItem = sort.isAsc() ? OrderItem.asc(sort.getField()) : OrderItem.desc(sort.getField());
            page.addOrder(orderItem);
        });
        return page;
    }
}