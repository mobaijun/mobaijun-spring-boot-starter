package com.mobaijun.core.exception.user;

import java.io.Serial;
import java.io.Serializable;

/**
 * 验证码错误异常类
 *
 * @author mobaijun
 */
public class CaptchaException extends UserException implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public CaptchaException() {
        super("user.jcaptcha.error");
    }

    public CaptchaException(String msg) {
        super(msg);
    }
}
