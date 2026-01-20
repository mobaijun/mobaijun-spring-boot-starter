/*
 * Copyright (C) 2022 [www.mobaijun.com]
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
package com.mobaijun.core.util.file;

import io.micrometer.common.lang.NonNullApi;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Description: [自定义的 MultipartFile 实现，用于处理 Base64 转换后的文件]
 * Author: [mobaijun]
 * Date: [2024/10/11 14:38]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 *
 * @param fileContent 文件内容的字节数组
 * @param header      文件头部信息，包含 MIME 类型
 */
@NonNullApi
public record Base64MultipartFile(byte[] fileContent, String header) implements MultipartFile {

    /**
     * 构造函数，初始化 Base64MultipartFile。
     *
     * @param fileContent 文件内容的字节数组
     * @param header      文件头部信息
     */
    public Base64MultipartFile(byte[] fileContent, String header) {
        this.fileContent = fileContent;
        // 解析 MIME 类型
        this.header = header.split(";")[0];
    }

    /**
     * 获取文件名，使用当前时间戳和随机数生成唯一的文件名。
     *
     * @return 生成的文件名
     */
    @Override
    public @NotNull String getName() {
        return System.currentTimeMillis() + Math.random() + ".png";
    }

    /**
     * 获取原始文件名，返回生成的文件名。
     *
     * @return 原始文件名
     */
    @Override
    public String getOriginalFilename() {
        return getName();
    }

    /**
     * 获取文件的 MIME 类型。
     *
     * @return 文件的 MIME 类型
     */
    @Override
    public String getContentType() {
        return this.header.split(":")[1];
    }

    /**
     * 判断文件是否为空。
     *
     * @return 如果文件内容为空，则返回 true；否则返回 false
     */
    @Override
    public boolean isEmpty() {
        return fileContent == null || fileContent.length == 0;
    }

    /**
     * 获取文件的大小。
     *
     * @return 文件的字节数
     */
    @Override
    public long getSize() {
        return fileContent.length;
    }

    /**
     * 获取文件的字节数组。
     *
     * @return 文件内容的字节数组
     */
    @Override
    public byte @NotNull [] getBytes() {
        return fileContent;
    }

    /**
     * 获取文件的输入流。
     *
     * @return 文件内容的输入流
     */
    @Override
    public @NotNull InputStream getInputStream() {
        return new ByteArrayInputStream(fileContent);
    }

    /**
     * 将文件内容传输到目标文件。
     *
     * @param dest 目标文件
     * @throws IOException           如果传输过程中发生 I/O 错误
     * @throws IllegalStateException 如果目标文件不可用
     */
    @Override
    public void transferTo(@NotNull File dest) throws IOException, IllegalStateException {
        try (FileOutputStream fos = new FileOutputStream(dest)) {
            fos.write(fileContent);
        }
    }
}