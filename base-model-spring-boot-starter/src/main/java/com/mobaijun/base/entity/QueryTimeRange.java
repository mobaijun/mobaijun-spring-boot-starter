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
package com.mobaijun.base.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Description: [日期扩展实体类]
 * Author: [mobaijun]
 * Date: [2024/7/30 16:51]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "日期查询扩展类")
public class QueryTimeRange implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(title = "开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @Schema(title = "结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    /**
     * 验证时间间隔是否有效
     *
     * @return 如果开始时间和结束时间都不为空且结束时间不早于开始时间，则返回true；否则返回false。
     */
    public boolean isValid() {
        return startTime != null && endTime != null && !endTime.isBefore(startTime);
    }

    /**
     * 检查给定时间是否在范围内
     *
     * @param dateTime 需要检查的时间
     * @return 如果给定时间在时间范围内，则返回true；否则返回false。
     */
    public boolean contains(LocalDateTime dateTime) {
        return dateTime != null && (dateTime.isEqual(startTime) || dateTime.isAfter(startTime)) &&
                (dateTime.isEqual(endTime) || dateTime.isBefore(endTime));
    }

    /**
     * 计算时间范围的持续时间
     *
     * @param unit 持续时间的单位，如ChronoUnit.DAYS、ChronoUnit.HOURS等
     * @return 持续时间的数值
     */
    public long getDuration(ChronoUnit unit) {
        if (startTime != null && endTime != null) {
            return unit.between(startTime, endTime);
        }
        return 0;
    }

    /**
     * 获取格式化后的时间范围字符串
     *
     * @param pattern 格式化的模式，如"yyyy-MM-dd HH:mm:ss"
     * @return 格式化后的时间范围字符串
     */
    public String getFormattedRange(String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return String.format("%s - %s",
                startTime.format(formatter),
                endTime.format(formatter));
    }
}
