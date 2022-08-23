package com.mobaijun.base.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: SelectData
 * class description： 下拉框所对应的视图类
 *
 * @author MoBaiJun 2022/6/30 14:59
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "下拉框数据")
@Schema(title = "下拉框数据")
public class SelectData<T> {

    /**
     * 显示的数据
     */
    @ApiModelProperty(value = "显示的数据", required = true)
    @Schema(title = "显示的数据", required = true)
    private String name;

    /**
     * 选中获取的属性
     */
    @ApiModelProperty(value = "选中获取的属性", required = true)
    @Schema(title = "选中获取的属性", required = true)
    private Object value;

    /**
     * 是否被选中
     */
    @ApiModelProperty(value = "是否被选中")
    @Schema(title = "是否被选中")
    private Boolean selected;

    /**
     * 是否禁用
     */
    @ApiModelProperty(value = "是否禁用")
    @Schema(title = "是否禁用")
    private Boolean disabled;

    /**
     * 分组标识
     */
    @ApiModelProperty(value = "分组标识")
    @Schema(title = "分组标识")
    private String type;

    /**
     * 扩展对象
     */
    @ApiModelProperty(value = "扩展对象")
    @Schema(title = "扩展对象")
    private T extendObj;
}
