package com.mobaijun.run.listener;

import com.mobaijun.run.prop.RunProperties;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.springframework.boot.CommandLineRunner;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

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
     * 注入配置文件
     */
    private final RunProperties runProperties;

    /**
     * 构造器注入
     *
     * @param runProperties 运行配置属性
     */
    public RunCommandRunner(RunProperties runProperties) {
        this.runProperties = runProperties;
    }

    @Override
    public void run(String... args) {
        openUrl(runProperties.getFrontEndAddress());
        openUrl(runProperties.getSwaggerUrl());
        openUrl(runProperties.getProjectHomeUrl());
    }

    /**
     * 打开指定 URL
     *
     * @param url 要打开的 URL
     */
    private void openUrl(String url) {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                if (url != null && !url.trim().isEmpty()) {
                    Desktop.getDesktop().browse(new URI(url));
                } else {
                    logger.warn("URL is null or empty. Skipping.");
                }
            } catch (IOException | URISyntaxException e) {
                handleException(e);
            }
        } else {
            logger.warn("Desktop browsing is not supported on this platform.");
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
}
