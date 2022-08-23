package com.mobaijun.base.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springdoc.api.annotations.ParameterObject;

import javax.validation.Valid;
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
@Valid
@Getter
@Setter
@ParameterObject
@ApiModel(description = "分页查询入参")
@Schema(title = "分页查询入参")
public class PageParamRequest {

    public static final String SORT_FILED_REGEX = "(([A-Za-z0-9_]{1,10}.)?[A-Za-z0-9_]{1,64})";

    public static final String SORT_FILED_ORDER = "(desc|asc)";

    public static final String SORT_REGEX = "^" + SORT_FILED_REGEX + "(," + SORT_FILED_ORDER + ")*$";

    @ApiModelProperty(value = "当前页码, 从 1 开始", example = "1")
    @Parameter(description = "当前页码, 从 1 开始", schema = @Schema(minimum = "1", defaultValue = "1", example = "1"))
    @Min(value = 1, message = "当前页不能小于 1")
    private long current = 1;

    @ApiModelProperty(value = "每页显示条数, 最大值为 100", example = "10")
    @Parameter(description = "每页显示条数, 最大值为 100",
            schema = @Schema(title = "每页显示条数", description = "最大值为系统设置，默认 100", minimum = "1", defaultValue = "10",
                    example = "10"))
    @Min(value = 1, message = "每页显示条数不能小于1")
    private long size = 10;

    /**
     * 排序规则
     */
    @ApiModelProperty(value = "排序规则，格式为：property(,asc|desc)。", example = "id,desc")
    @Parameter(description = "排序规则，格式为：property(,asc|desc)。" + "默认为升序。" + "支持传入多个排序字段。", name = "sort",
            array = @ArraySchema(schema = @Schema(type = "string", pattern = SORT_REGEX, example = "id,desc")))
    @Pattern(regexp = SORT_REGEX, message = "排序字段格式非法")
    private List<String> sort;
}
