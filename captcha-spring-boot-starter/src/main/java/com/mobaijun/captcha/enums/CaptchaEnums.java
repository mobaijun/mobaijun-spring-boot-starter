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
package com.mobaijun.captcha.enums;

/**
 * software：IntelliJ IDEA 2022.1
 * enum name: CaptchaEnums
 * enum description： 验证码枚举类
 *
 * @author MoBaiJun 2022/10/27 10:01
 */
public enum CaptchaEnums {

    /**
     * 算数
     */
    ARITHMETIC,

    /**
     * 中文
     */
    CHINESE,

    /**
     * 中文闪图
     */
    CHINESE_GIF,

    /**
     * 闪图
     */
    GIF,

    /**
     * 滑块验证码
     */
    SLIDER,

    /**
     * 旋转
     */
    ROTATE,

    /**
     * 滑动还原验证码
     */
    SLIDE_REDUCE,

    /**
     * 文字图片点选
     */
    WORD_IMAGE_CLICK,

    /**
     * 点选验证码
     */
    IMAGE_CLICK,

    /**
     * 拼接
     */
    CONCAT,

    /**
     * 规则
     */
    SPEC,

    /**
     * 未识别，其他类型
     */
    OTHER
}