package com.mobaijun.captcha.exception;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: TextCaptchaException
 * class description： 文字验证码异常
 *
 * @author MoBaiJun 2022/10/27 10:35
 */
public class TextCaptchaException extends RuntimeException {
    public TextCaptchaException() {
    }

    public TextCaptchaException(String message) {
        super(message);
    }

    public TextCaptchaException(String message, Throwable cause) {
        super(message, cause);
    }

    public TextCaptchaException(Throwable cause) {
        super(cause);
    }

    public TextCaptchaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
