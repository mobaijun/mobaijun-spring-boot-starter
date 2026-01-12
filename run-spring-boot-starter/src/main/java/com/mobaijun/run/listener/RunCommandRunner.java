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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;

import java.io.IOException;
import java.util.List;

/**
 * Description: [运行监听器]
 * Author: [mobaijun]
 * Date: [2023/11/23 11:40]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 *
 * @param runProperties 注入配置文件
 */
public record RunCommandRunner(RunProperties runProperties) implements CommandLineRunner {

    /**
     * 日志记录器
     */
    private static final Logger log = LoggerFactory.getLogger(RunCommandRunner.class);

    @Override
    public void run(String... args) {
        if (!runProperties.isEnabled()) {
            return;
        }

        List<String> urls = runProperties.getUrl();
        if (urls == null || urls.isEmpty()) {
            log.warn("Project started, but no URLs are configured to open.");
            return;
        }

        log.info("-------------------------------------------------------------------------");
        urls.forEach(this::openUrl);
        log.info("-------------------------------------------------------------------------");
    }

    private void openUrl(String url) {
        // 1. 构造一个唯一的 Key，防止多个地址相互干扰
        String key = "already.opened." + url.hashCode();

        // 2. 检查系统属性
        if ("true".equals(System.getProperty(key))) {
            log.info("URL already opened in this JVM session, skipping: {}", url);
            return;
        }
        String os = System.getProperty("os.name").toLowerCase();
        ProcessBuilder processBuilder = new ProcessBuilder();

        try {
            if (os.contains("win")) {
                // Windows: 使用 cmd /c start
                processBuilder.command("cmd", "/c", "start", url);
            } else if (os.contains("mac")) {
                // macOS: 使用 open
                processBuilder.command("open", url);
            } else if (os.contains("nix") || os.contains("nux")) {
                // Linux: 使用 xdg-open (通用的图形界面打开命令)
                processBuilder.command("xdg-open", url);
            } else {
                log.error("Unsupported Operating System: {}", os);
                return;
            }

            processBuilder.start();
            log.info("Successfully opened URL: {}", url);
            // 标记为已打开
            System.setProperty(key, "true");
            log.info("Successfully opened URL: {}", url);
        } catch (IOException e) {
            log.error("Failed to open URL [{}]. Error: {}", url, e.getMessage());
        }
    }
}