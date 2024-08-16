package com.mobaijun.base.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Description: [修改状态请求参数]
 * Author: [mobaijun]
 * Date: [2024/8/16 11:06]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 *
 * @param <I> 主键类型
 * @param <S> 状态类型
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "修改状态请求参数", description = "[修改状态请求参数]")
public class QueryStatusModel<I, S> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(name = "id", description = "[主键]")
    private I id;

    @Schema(name = "status", description = "[状态]")
    private S status;
}
