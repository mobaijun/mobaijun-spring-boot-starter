package com.mobaijun.mybatis.plus.alias;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * ClassName: TableAliasNotFoundException
 * 类描述：TableAlias 注解没有找到时抛出的异常
 *
 * @author MoBaiJun 2022/5/7 16:17
 */
public class TableAliasNotFoundException extends RuntimeException {

    public TableAliasNotFoundException() {
    }

    public TableAliasNotFoundException(String message) {
        super(message);
    }

    public TableAliasNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TableAliasNotFoundException(Throwable cause) {
        super(cause);
    }
}
