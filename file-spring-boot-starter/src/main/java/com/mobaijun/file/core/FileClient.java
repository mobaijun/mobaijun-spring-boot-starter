/*
 * Copyright (C) 2022 www.mobaijun.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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