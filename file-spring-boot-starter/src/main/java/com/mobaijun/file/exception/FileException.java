package com.mobaijun.file.exception;

import java.io.IOException;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: FileException
 * class description： 文件异常
 *
 * @author MoBaiJun 2022/9/26 14:53
 */
public class FileException extends IOException {

    public FileException() {
    }

    public FileException(String message) {
        super(message);
    }

    public FileException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileException(Throwable cause) {
        super(cause);
    }
}
