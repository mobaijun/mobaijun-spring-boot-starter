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
package com.mobaijun.run.listener;

import com.mobaijun.run.prop.RunProperties;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;

/**
 * Description: [运行监听器]
 * Author: [mobaijun]
 * Date: [2023/11/23 11:40]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public class RunCommandRunner implements CommandLineRunner {

    /**
     * 日志记录器
     */
    private static final Logger logger = LoggerFactory.getLogger(RunCommandRunner.class);
    /**
     * 记录已打开URL的文件路径
     */
    private static final String OPENED_URLS_FILE = "opened_urls.txt";
    /**
     * 注入配置文件
     */
    private final RunProperties runProperties;

    public RunCommandRunner(RunProperties runProperties) {
        this.runProperties = runProperties;
    }

    @Override
    public void run(String... args) {
        // 判断是否启用
        if (runProperties.isEnabled()) {
            logUrls();
        }
    }

    /**
     * 记录日志并检查URL
     */
    private void logUrls() {
        logger.info("{-------------------------------------------------------------------------}");
        Set<String> openedUrls = loadOpenedUrls();
        runProperties.getUrl().forEach(url -> tryOpenUrl(url, openedUrls));
        saveOpenedUrls(openedUrls);
        logger.info("{-------------------------------------------------------------------------}");
    }

    /**
     * 尝试打开指定URL，如果已记录为打开则跳过
     *
     * @param url        要打开的URL
     * @param openedUrls 已打开的URL集合
     */
    private void tryOpenUrl(String url, Set<String> openedUrls) {
        if (openedUrls.contains(url)) {
            logger.info("URL already opened in browser, skipping: {}", url);
            return;
        }
        openUrl(url);
        openedUrls.add(url);
    }

    /**
     * 打开指定URL
     *
     * @param url 要打开的URL
     */
    public void openUrl(String url) {
        String os = System.getProperty("os.name").toLowerCase();
        // 使用操作系统特定的命令打开URL
        if (os.contains("win")) {
            openUrlWindows(url);
        } else if (os.contains("mac")) {
            openUrlMac(url);
        }
        logger.info("Successfully opened URL: {}", url);
    }

    /**
     * Windows系统打开URL
     *
     * @param url URL
     */
    private void openUrlWindows(String url) {
        try {
            Runtime.getRuntime().exec("cmd /c start " + url);
        } catch (IOException e) {
            handleException(e);
        }
    }

    /**
     * macOS系统打开URL
     *
     * @param url URL
     */
    private void openUrlMac(String url) {
        try {
            Runtime.getRuntime().exec("open " + url);
        } catch (IOException e) {
            handleException(e);
        }
    }

    /**
     * 处理异常情况
     *
     * @param ex 异常对象
     */
    private void handleException(Exception ex) {
        // 记录异常
        logger.error("Error opening the page. Please find the cause and try again: {}", ex.getMessage(), ex);
    }

    /**
     * 从文件加载已打开的URL
     * 如果文件存在但不是当天的，则删除并重建
     *
     * @return 已打开的URL集合
     */
    private Set<String> loadOpenedUrls() {
        Set<String> openedUrls = new HashSet<>();
        Path path = Paths.get(OPENED_URLS_FILE);

        try {
            if (Files.exists(path)) {
                // 获取文件创建时间
                BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
                LocalDate fileCreationDate = attrs.creationTime()
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
                LocalDate today = LocalDate.now();

                // 如果文件不是当天的，删除文件
                if (!fileCreationDate.equals(today)) {
                    Files.delete(path);
                    logger.info("Deleted outdated opened URLs file: {}", path);
                } else {
                    // 文件是当天的，读取内容
                    openedUrls.addAll(Files.readAllLines(path));
                    logger.debug("Loaded {} opened URLs from file: {}", openedUrls.size(), path);
                }
            }
            // 如果文件不存在或已删除，创建新文件（可选，根据需求）
            if (!Files.exists(path)) {
                Files.createFile(path);
                logger.info("Created new opened URLs file: {}", path);
            }
        } catch (IOException e) {
            logger.error("Failed to process opened URLs file: {}", e.getMessage(), e);
        }

        return openedUrls;
    }

    /**
     * 保存已打开的URL到文件
     *
     * @param openedUrls 已打开的URL集合
     */
    private void saveOpenedUrls(Set<String> openedUrls) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(OPENED_URLS_FILE))) {
            for (String url : openedUrls) {
                writer.write(url);
                writer.newLine();
            }
        } catch (IOException e) {
            logger.error("Failed to save opened URLs to file: {}", e.getMessage(), e);
        }
    }
}