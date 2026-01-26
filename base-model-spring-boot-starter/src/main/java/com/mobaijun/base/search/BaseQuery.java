package com.mobaijun.base.search;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: [查询基类]
 * Author: [mobaijun]
 * Date: [2026/1/26 21:28]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "通用查询基类")
public class BaseQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 搜索值
     */
    @Schema(title = "搜索值", description = "通用关键字搜索")
    private String searchValue;

    /**
     * 请求参数
     */
    @Schema(title = "请求参数")
    private Map<String, Object> params = new HashMap<>();

    /**
     * 获取附加参数值
     */
    @SuppressWarnings("unchecked")
    public <V> V getParam(String key) {
        return params == null ? null : (V) params.get(key);
    }

    /**
     * 添加附加参数
     */
    public void addParam(String key, Object value) {
        if (this.params == null) {
            this.params = new HashMap<>();
        }
        this.params.put(key, value);
    }
}
