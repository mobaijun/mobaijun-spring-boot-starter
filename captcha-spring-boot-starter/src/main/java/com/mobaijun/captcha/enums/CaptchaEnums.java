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
