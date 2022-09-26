package com.mobaijun.file.core;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * software：IntelliJ IDEA 2022.1
 * interface name: FileClient
 * interface description： 文件客户端接口
 *
 * @author MoBaiJun 2022/9/26 14:49
 */
public interface FileClient {

    /**
     * 文件上传
     *
     * @param stream       文件流
     * @param relativePath 相对路径
     * @return 文件路径
     * @throws IOException java.io.IOException
     */
    String uploadFile(InputStream stream, String relativePath) throws IOException;

    /**
     * 文件下载
     *
     * @param relativePath 文件路径
     * @return 文件
     * @throws IOException java.io.IOException
     */
    File downloadFile(String relativePath) throws IOException;

    /**
     * 删除文件
     *
     * @param relativePath 文件路径
     * @return 是否成功
     */
    boolean deleteFile(String relativePath);
}
