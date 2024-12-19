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
package com.mobaijun.base.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * ClassName: PageParam
 * 类描述： 分页
 *
 * @author MoBaiJun 2022/5/7 15:54
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "分页查询参数")
public class PageParam implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 当前页码
     */
    @Schema(title = "当前页码", description = "从 1 开始", defaultValue = "1", example = "1")
    @Min(value = 1, message = "当前页不能小于 1")
    private long current = 1;

    /**
     * 每页显示条数不能小于1
     */
    @Schema(title = "每页显示条数", description = "最大值为系统设置，默认 100", defaultValue = "10")
    @Min(value = 1, message = "每页显示条数不能小于1")
    private long size = 10;

    /**
     * 排序规则
     */
    @Schema(title = "排序规则")
    @Valid
    private List<Sort> sorts = new ArrayList<>();

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(title = "排序元素载体")
    public static class Sort {

        /**
         * 排序字段格式非法
         */
        @Schema(title = "排序字段", example = "id")
        @Pattern(regexp = PageableConstants.SORT_FILED_REGEX, message = "排序字段格式非法")
        private String field;

        /**
         * 是否正序排序
         */
        @Schema(title = "是否正序排序", example = "false")
        private boolean asc;
    }

    /**
     * 获取起始行（适合仅内存分页使用）
     *
     * @return 起始行
     */
    public long getStartRow() {
        // 起始行 = (当前页码 - 1) * 每页显示条数
        return (current - 1) * size;
    }

    /**
     * 获取结束行（适合仅内存分页使用）
     * 注意：此方法不适合数据库分页，因为数据库通常只需要起始行和每页大小。
     */
    public long getEndRow() {
        return current * size;
    }

    /**
     * 获取总页数
     *
     * @param total 总记录数
     * @return 总页数
     */
    public long getTotalPages(long total) {
        return (total + size - 1) / size;
    }

    /**
     * 判断是否为第一页
     *
     * @return 如果当前页是第一页返回 true，否则返回 false
     */
    public boolean isFirstPage() {
        return current == 1;
    }

    /**
     * 判断是否有下一页
     *
     * @param total 总记录数
     * @return 如果有下一页返回 true，否则返回 false
     */
    public boolean hasNextPage(long total) {
        return current * size < total;
    }

    /**
     * 判断是否有上一页
     *
     * @return 如果有上一页返回 true，否则返回 false
     */
    public boolean hasPreviousPage() {
        return current > 1;
    }

    /**
     * 设置默认排序规则
     *
     * @param field 排序字段
     * @param asc   是否正序
     */
    public void setDefaultSort(String field, boolean asc) {
        if (sorts.isEmpty()) {
            sorts.add(new Sort(field, asc));
        }
    }

    /**
     * 清除所有排序规则
     */
    public void clearSorts() {
        sorts.clear();
    }

    /**
     * 获取排序规则字符串
     *
     * @return 拼接后的排序字段字符串
     */
    public String getSortSql() {
        if (sorts.isEmpty()) {
            return "";
        }
        StringBuilder sortSql = new StringBuilder();
        for (Sort sort : sorts) {
            sortSql.append(sort.getField())
                    .append(sort.isAsc() ? " ASC" : " DESC")
                    .append(", ");
        }
        // 去掉最后多余的逗号和空格
        return sortSql.substring(0, sortSql.length() - 2);
    }
}