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
     * Supported Excel file extensions.
     */
    private static final String[] SUPPORTED_EXTENSIONS = {".xls", ".xlsx"};

    /**
     * Validates if the given file is a non-empty Excel file.
     *
     * @param file the file to be validated
     * @throws IllegalArgumentException if the file is null, empty, or not an Excel file
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
     * Checks if the given file name has a supported Excel extension.
     *
     * @param fileName the name of the file
     * @return true if the file has a supported Excel extension, false otherwise
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
