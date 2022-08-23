package com.mobaijun.base.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collections;
import java.util.List;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * ClassName: PageResult
 * 类描述： 分页返回结果
 *
 * @author MoBaiJun 2022/5/7 16:00
 */
@Getter
@Setter
@ToString
@ApiModel(description = "分页返回结果")
@Schema(title = "分页返回结果")
public class PageResult<T> {


    /**
     * 查询数据列表
     */
    @Schema(title = "查询数据列表")
    @ApiModelProperty(value = "查询数据列表")
    protected List<T> records = Collections.emptyList();

    /**
     * 总数
     */
    @Schema(title = "总数")
    @ApiModelProperty(value = "总数")
    protected Long total = 0L;

    public PageResult() {
    }

    public PageResult(long total) {
        this.total = total;
    }

    public PageResult(List<T> records, long total) {
        this.records = records;
        this.total = total;
    }
}