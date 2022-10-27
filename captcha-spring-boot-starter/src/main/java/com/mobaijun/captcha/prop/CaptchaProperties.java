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
