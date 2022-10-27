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
package com.mobaijun.file.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: StreamUtil
 * class description： stream 工具类
 *
 * @author MoBaiJun 2022/9/26 15:11
 */
public class StreamUtil {

    /**
     * 默认大小 1024 * 1024 * 8
     */
    public static final int DEFAULT_SIZE = 10485760;

    /**
     * 将输入流内容写入输出流
     *
     * @param inputStream  输入流
     * @param outputStream 输出流
     */
    public static void write(InputStream inputStream, OutputStream outputStream) {
        write(inputStream, outputStream, DEFAULT_SIZE);
    }

    public static void write(InputStream inputStream, OutputStream outputStream, int size) {
        int len;
        byte[] bytes = new byte[size < 1 ? DEFAULT_SIZE : size];
        while (true) {
            try {
                if (!((len = inputStream.read(bytes)) > 0)) {
                    break;
                }
                outputStream.write(bytes, 0, len);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}