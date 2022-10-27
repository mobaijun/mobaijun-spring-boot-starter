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
package com.mobaijun.captcha.util;

import com.mobaijun.captcha.enums.CaptchaEnums;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: CaptchaTypeUtil
 * class description： 获取验证码类型
 *
 * @author MoBaiJun 2022/10/27 10:37
 */
public class CaptchaTypeUtil {


    public CaptchaEnums getCaptchaEnums(CaptchaEnums captchaEnums) {
        switch (captchaEnums) {
            // 数字
            case ARITHMETIC:
                return CaptchaEnums.ARITHMETIC;
            // 中文
            case CHINESE:
                return CaptchaEnums.CHINESE;
            // 中文 GIF
            case CHINESE_GIF:
                return CaptchaEnums.CHINESE_GIF;
            // GIF
            case GIF:
                return CaptchaEnums.GIF;
            // 滑块验证码
            case SLIDER:
                return CaptchaEnums.SLIDER;
            // 旋转
            case ROTATE:
                return CaptchaEnums.ROTATE;
            // 滑块还原验证码
            case SLIDE_REDUCE:
                return CaptchaEnums.SLIDE_REDUCE;
            // 文字图片点击验证码
            case WORD_IMAGE_CLICK:
                return CaptchaEnums.WORD_IMAGE_CLICK;
            // 点选验证码
            case IMAGE_CLICK:
                return CaptchaEnums.IMAGE_CLICK;
            // 拼接
            case CONCAT:
                return CaptchaEnums.CONCAT;
            default:
                // 其他
                return CaptchaEnums.OTHER;
        }
    }
}