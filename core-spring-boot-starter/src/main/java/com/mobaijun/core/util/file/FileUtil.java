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

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.mobaijun.common.constant.StringConstant;
import jakarta.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import lombok.SneakyThrows;
import org.springframework.web.multipart.MultipartFile;

/**
 * Description: [文件处理工具类]
 * Author: [mobaijun]
 * Date: [2024/8/12 9:52]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public class FileUtil extends cn.hutool.core.io.FileUtil {

    /**
     * 下载文件名重新编码
     *
     * @param response     响应对象
     * @param realFileName 真实文件名
     */
    public static void setAttachmentResponseHeader(HttpServletResponse response, String realFileName) {
        String percentEncodedFileName = percentEncode(realFileName);
        String contentDispositionValue = "attachment; filename=%s;filename=%s".formatted(percentEncodedFileName, percentEncodedFileName);
        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition,download-filename");
        response.setHeader("Content-disposition", contentDispositionValue);
        response.setHeader("download-filename", percentEncodedFileName);
    }

    /**
     * 百分号编码工具方法
     *
     * @param s 需要百分号编码的字符串
     * @return 百分号编码后的字符串
     */
    public static String percentEncode(String s) {
        String encode = URLEncoder.encode(s, StandardCharsets.UTF_8);
        return encode.replaceAll("\\+", "%20");
    }

    /**
     * 获取文件大小转 MB 字符串
     *
     * @param file 文件
     * @return 文件大小字符串
     */
    public static String getFileSize(MultipartFile file) {
        if (file.isEmpty()) {
            return StringConstant.BLANK;
        }
        return String.format("%.2fMB", (file.getSize() / (1024.0 * 1024.0)));
    }

    /**
     * 创建临时文件
     * 该文件会在 JVM 退出时，进行删除
     *
     * @param data 文件内容
     * @return 文件
     */
    @SneakyThrows
    public static File createTempFile(String data) {
        File file = createTempFile();
        // 写入内容
        cn.hutool.core.io.FileUtil.writeUtf8String(data, file);
        return file;
    }

    /**
     * 创建临时文件
     * 该文件会在 JVM 退出时，进行删除
     *
     * @param data 文件内容
     * @return 文件
     */
    @SneakyThrows
    public static File createTempFile(byte[] data) {
        File file = createTempFile();
        // 写入内容
        cn.hutool.core.io.FileUtil.writeBytes(data, file);
        return file;
    }

    /**
     * 创建临时文件，无内容
     * 该文件会在 JVM 退出时，进行删除
     *
     * @return 文件
     */
    @SneakyThrows
    public static File createTempFile() {
        // 创建文件，通过 UUID 保证唯一
        File file = File.createTempFile(IdUtil.simpleUUID(), null);
        // 标记 JVM 退出时，自动删除
        file.deleteOnExit();
        return file;
    }

    /**
     * 生成文件路径
     *
     * @param content      文件内容
     * @param originalName 原始文件名
     * @return path，唯一不可重复
     */
    public static String generatePath(byte[] content, String originalName) {
        String sha256Hex = DigestUtil.sha256Hex(content);
        // 情况一：如果存在 name，则优先使用 name 的后缀
        if (StrUtil.isNotBlank(originalName)) {
            String extName = FileNameUtil.extName(originalName);
            return StrUtil.isBlank(extName) ? sha256Hex : sha256Hex + "." + extName;
        }
        // 情况二：基于 content 计算
        return sha256Hex + '.' + FileTypeUtil.getType(new ByteArrayInputStream(content));
    }

    /**
     * 将 Base64 字符串转换为 MultipartFile
     *
     * @param base64Str Base64 编码的字符串
     * @return MultipartFile 对象
     */
    public static MultipartFile base64ToMultipartFile(String base64Str) {
        // 去掉 Base64 编码的头部信息
        String[] base64Parts = base64Str.split(",");
        String base64Data = base64Parts.length > 1 ? base64Parts[1] : base64Parts[0];
        byte[] decodedBytes = Base64.getDecoder().decode(base64Data);
        // 设置默认 MIME 类型为 image/png
        String header = base64Parts.length > 1 ? base64Parts[0] : "data:image/png;base64";

        return new Base64MultipartFile(decodedBytes, header);
    }

    /**
     * 将 Base64 字符串转换为临时文件
     *
     * @param base64Img Base64 编码的图片
     * @return File 对象（临时文件）
     * @throws java.io.IOException 如果解码或写入失败
     */
    public static File base64ToTempFile(String base64Img) throws IOException {
        // 去掉 Base64 编码中的头部信息
        String[] base64Parts = base64Img.split(",");
        String base64Data = base64Parts.length > 1 ? base64Parts[1] : base64Parts[0];

        // 解码 Base64 字符串
        byte[] imgBytes = Base64.getDecoder().decode(base64Data);

        // 创建临时文件
        File tempFile = File.createTempFile("temp_image_", ".png");
        // 在 JVM 退出时自动删除临时文件
        tempFile.deleteOnExit();

        // 写入文件
        try (OutputStream out = new FileOutputStream(tempFile)) {
            out.write(imgBytes);
        }

        return tempFile;
    }
}
