package com.mobaijun.core.exception.user;

import com.mobaijun.core.exception.base.BaseException;
import java.io.Serial;
import java.io.Serializable;

/**
 * 用户信息异常类
 *
 * @author mobaijun
 */
public class UserException extends BaseException implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public UserException(String code, Object... args) {
        super("user", code, args, null);
    }
}
