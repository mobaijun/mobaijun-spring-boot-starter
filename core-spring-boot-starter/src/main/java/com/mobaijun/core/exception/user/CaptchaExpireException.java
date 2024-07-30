package com.mobaijun.core.exception.user;

import java.io.Serial;
import java.io.Serializable;

/**
 * 验证码失效异常类
 *
 * @author mobaijun
 */
public class CaptchaExpireException extends UserException implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public CaptchaExpireException() {
        super("user.jcaptcha.expire");
    }
}
