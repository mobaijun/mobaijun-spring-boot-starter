package com.mobaijun.dubbo.enums;

import lombok.AllArgsConstructor;

/**
 * Description: [请求日志泛型]
 * Author: [mobaijun]
 * Date: [2024/8/5 11:56]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@AllArgsConstructor
public enum RequestLogEnum {

    /**
     * info 基础信息
     */
    INFO,

    /**
     * param 参数信息
     */
    PARAM,

    /**
     * full 全部
     */
    FULL;
}
