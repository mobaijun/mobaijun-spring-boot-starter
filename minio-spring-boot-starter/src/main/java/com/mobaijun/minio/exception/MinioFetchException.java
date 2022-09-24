package com.mobaijun.minio.exception;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: MinioFetchException
 * class description：Runtime exception thrown when an error occur while fetching a list of objects.
 *
 * @author MoBaiJun 2022/9/20 9:02
 */
public class MinioFetchException extends RuntimeException {

    public MinioFetchException(String message, Throwable cause) {
        super(message, cause);
    }
}
