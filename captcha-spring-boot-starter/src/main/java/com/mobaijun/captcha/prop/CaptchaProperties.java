/*
 * Copyright (C) 2022 www.mobaijun.com
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
package com.mobaijun.captcha.prop;

import com.mobaijun.captcha.enums.CaptchaEnums;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: CaptchaProperties
 * class description： 验证码配置文件
 *
 * @author MoBaiJun 2022/10/27 10:01
 */
@ConfigurationProperties(CaptchaProperties.PREFIX)
@EnableConfigurationProperties(CaptchaProperties.class)
public class CaptchaProperties {

    /**
     * 前缀
     */
    public static final String PREFIX = "spring.captcha";

    /**
     * 验证码类型配置(默认数字类型)
     */
    private CaptchaEnums type = CaptchaEnums.ARITHMETIC;

    /**
     * 验证码有效期（分钟）
     */
    private Long expiration = 2L;

    /**
     * 验证码内容长度
     */
    private int length = 4;

    /**
     * 验证码宽度
     */
    private int width = 111;

    /**
     * 验证码高度
     */
    private int height = 36;

    /**
     * 验证码字体
     */
    private String fontName;

    /**
     * 字体大小
     */
    private int fontSize = 25;

    /**
     * 验证码前缀
     */
    private String codeKey;
}