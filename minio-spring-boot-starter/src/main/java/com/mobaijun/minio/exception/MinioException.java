package com.mobaijun.minio.exception;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: MinioException
 * class description：Wrapper exception for all Minio errors that occurs
 * while fetching, removing, uploading an object to Minio.
 *
 * @author MoBaiJun 2022/9/20 9:02
 */
public class MinioException extends Exception {

    public MinioException(String message, Throwable cause) {
        super(message, cause);
    }
}
