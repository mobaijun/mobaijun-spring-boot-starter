package com.mobaijun.sensitive.core;

import cn.hutool.core.util.DesensitizedUtil;
import java.util.function.Function;
import lombok.AllArgsConstructor;

/**
 * Description: []
 * Author: [mobaijun]
 * Date: [2024/7/30 9:49]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@AllArgsConstructor
public enum SensitiveStrategy {

    /**
     * 身份证脱敏
     */
    ID_CARD(s -> DesensitizedUtil.idCardNum(s, 3, 4)),

    /**
     * 手机号脱敏
     */
    PHONE(DesensitizedUtil::mobilePhone),

    /**
     * 地址脱敏
     */
    ADDRESS(s -> DesensitizedUtil.address(s, 8)),

    /**
     * 邮箱脱敏
     */
    EMAIL(DesensitizedUtil::email),

    /**
     * 银行卡
     */
    BANK_CARD(DesensitizedUtil::bankCard);

    /**
     * 可自行添加其他脱敏策略
     */
    private final Function<String, String> desensitize;

    public Function<String, String> desensitize() {
        return desensitize;
    }
}
