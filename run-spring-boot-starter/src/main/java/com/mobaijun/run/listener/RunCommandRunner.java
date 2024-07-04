package com.mobaijun.run.listener;

import com.mobaijun.run.prop.RunProperties;
import java.io.IOException;
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
     * 记录日志
     */
    private void logUrls() {
        logger.info("{-------------------------------------------------------------------------}");
        runProperties.getUrl().forEach(this::openUrl);
        logger.info("{-------------------------------------------------------------------------}");
    }

    /**
     * 打开指定 URL
     *
     * @param url 要打开的 URL
     */
    public void openUrl(String url) {
        String os = System.getProperty("os.name").toLowerCase();
        // 使用操作系统特定的命令打开 URL
        if (os.contains("win")) {
            openUrlWindows(url);
        } else if (os.contains("mac")) {
            openUrlMac(url);
        }
        logger.info("Successfully opened URL: {}", url);
    }

    /**
     * Windows
     *
     * @param url url
     */
    private void openUrlWindows(String url) {
        try {
            Runtime.getRuntime().exec("cmd /c start " + url);
        } catch (IOException e) {
            handleException(e);
        }
    }

    /**
     * macOS
     *
     * @param url url
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
}
