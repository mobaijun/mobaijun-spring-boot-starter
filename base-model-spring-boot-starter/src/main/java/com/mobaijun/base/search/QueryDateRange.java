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
package com.mobaijun.base.search;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mobaijun.base.entity.EntityConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: [LocalDate类型日期扩展实体类]
 * Author: [mobaijun]
 * Date: [2024/9/26 16:51]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "LocalDate类型日期查询扩展类")
public class QueryDateRange implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(title = "搜索值", description = "用于全文搜索或通用关键字搜索")
    private String searchValue;

    @Schema(title = "请求参数", description = "用于存放动态扩展查询条件")
    private Map<String, Object> params = new HashMap<>();

    @Schema(title = "开始日期")
    @JsonFormat(pattern = EntityConstants.DEFAULT_DATE_FORMAT)
    @DateTimeFormat(pattern = EntityConstants.DEFAULT_DATE_FORMAT)
    private LocalDate startDate;

    @Schema(title = "结束日期")
    @JsonFormat(pattern = EntityConstants.DEFAULT_DATE_FORMAT)
    @DateTimeFormat(pattern = EntityConstants.DEFAULT_DATE_FORMAT)
    private LocalDate endDate;

    /**
     * 添加附加参数
     */
    public void addParam(String key, Object value) {
        if (this.params == null) {
            this.params = new HashMap<>();
        }
        this.params.put(key, value);
    }

    /**
     * 获取附加参数值并转换为指定类型
     */
    @SuppressWarnings("unchecked")
    public <V> V getParam(String key) {
        return params == null ? null : (V) params.get(key);
    }

    /**
     * 验证日期范围是否有效
     *
     * @return 如果开始日期和结束日期都不为空且结束日期不早于开始日期，则返回true；否则返回false。
     */
    public boolean isValid() {
        return startDate != null && endDate != null && !endDate.isBefore(startDate);
    }

    /**
     * 检查给定日期是否在范围内
     *
     * @param date 需要检查的日期
     * @return 如果给定日期在日期范围内，则返回true；否则返回false。
     */
    public boolean contains(LocalDate date) {
        return date != null && (date.isEqual(startDate) || date.isAfter(startDate)) &&
                (date.isEqual(endDate) || date.isBefore(endDate));
    }

    /**
     * 计算日期范围的持续时间
     *
     * @param unit 持续时间的单位，如ChronoUnit.DAYS
     * @return 持续时间的数值
     */
    public long getDuration(ChronoUnit unit) {
        if (startDate != null && endDate != null) {
            return unit.between(startDate, endDate);
        }
        return 0;
    }

    /**
     * 获取格式化后的日期范围字符串
     *
     * @param pattern 格式化的模式，如"yyyy-MM-dd"
     * @return 格式化后的日期范围字符串
     */
    public String getFormattedRange(String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return String.format("%s - %s",
                startDate.format(formatter),
                endDate.format(formatter));
    }
}
