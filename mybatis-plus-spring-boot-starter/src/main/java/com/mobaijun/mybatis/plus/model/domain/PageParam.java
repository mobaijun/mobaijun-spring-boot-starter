package com.mobaijun.mybatis.plus.model.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * ClassName: PageParam
 * 类描述： 分页
 *
 * @author MoBaiJun 2022/5/7 15:54
 */
@Data
public class PageParam {

    /**
     * 当前页码
     */
    @Min(value = 1, message = "当前页不能小于 1")
    private long current = 1;

    /**
     * 每页显示条数不能小于1
     */
    @Min(value = 1, message = "每页显示条数不能小于1")
    private long size = 10;

    /**
     * 排序规则
     */
    @Valid
    private List<Sort> sorts = new ArrayList<>();

    @Getter
    @Setter
    public static class Sort {

        /**
         * 排序字段格式非法
         */
        @Pattern(regexp = PageParamRequest.SORT_FILED_REGEX, message = "排序字段格式非法")
        private String field;

        /**
         * 是否正序排序
         */
        private boolean asc;
    }
}
