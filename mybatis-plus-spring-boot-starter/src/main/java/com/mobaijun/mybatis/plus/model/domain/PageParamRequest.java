package com.mobaijun.mybatis.plus.model.domain;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * ClassName: PageParamRequest
 * 类描述： 前端交互请求入参模型，将被转换为 PageParam 对象
 *
 * @author MoBaiJun 2022/5/7 15:56
 */
@Data
public class PageParamRequest {

    public static final String SORT_FILED_REGEX = "(([A-Za-z0-9_]{1,10}.)?[A-Za-z0-9_]{1,64})";

    public static final String SORT_FILED_ORDER = "(desc|asc)";

    public static final String SORT_REGEX = "^" + SORT_FILED_REGEX + "(," + SORT_FILED_ORDER + ")*$";

    @Min(value = 1, message = "当前页不能小于 1")
    private long current = 1;

    @Min(value = 1, message = "每页显示条数不能小于1")
    private long size = 10;

    /**
     * 排序字段，配合 sortOrders 构建排序规则
     *
     * @see PageParamRequest#sort
     * @deprecated 推荐使用 sort 参数，以便更直观的展示排序规则
     */
    @Deprecated
    @Pattern(regexp = "^" + SORT_FILED_REGEX + "(," + SORT_FILED_REGEX + ")*$", message = "排序字段格式非法")
    private String sortFields;

    /**
     * 排序方式，配合 sortFields 构建排序规则
     *
     * @see PageParamRequest#sort
     * @deprecated 推荐使用 sort 参数，以便更直观的展示排序规则
     */
    @Deprecated
    @Pattern(regexp = "^" + SORT_FILED_ORDER + "(," + SORT_FILED_ORDER + ")*$", message = "排序字段格式非法")
    private String sortOrders;

    /**
     * 排序规则，当此属性有值时，忽略 sortFields 和 sortOrders
     */
    @Pattern(regexp = SORT_REGEX, message = "排序字段格式非法")
    private List<String> sort;
}
