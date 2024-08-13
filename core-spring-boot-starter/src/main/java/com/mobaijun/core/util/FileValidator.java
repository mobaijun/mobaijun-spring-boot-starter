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
package com.mobaijun.core.util;

import org.springframework.web.multipart.MultipartFile;

/**
 * Description: [excel 文件类型校验类]
 * Author: [mobaijun]
 * Date: [2024/7/11 12:00]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public class FileValidator {

    /**
     * 支持的 Excel 文件扩展名
     */
    private static final String[] SUPPORTED_EXTENSIONS = {".xls", ".xlsx"};

    /**
     * 验证给定的文件是否为非空的 Excel 文件
     *
     * @param file 要验证的文件
     * @throws IllegalArgumentException 如果文件为空或不是 Excel 文件
     */
    public static void validateExcelFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("文件不能为空");
        }

        String fileName = file.getOriginalFilename();
        if (fileName == null || !isSupportedExtension(fileName)) {
            throw new IllegalArgumentException("文件格式不正确，请上传Excel文件");
        }
    }

    /**
     * 检查给定的文件名是否具有支持的 Excel 扩展名
     *
     * @param fileName 文件名
     * @return 如果文件具有支持的 Excel 扩展名，则返回 true；否则返回 false
     */
    private static boolean isSupportedExtension(String fileName) {
        for (String extension : SUPPORTED_EXTENSIONS) {
            if (fileName.toLowerCase().endsWith(extension)) {
                return true;
            }
        }
        return false;
    }
}
