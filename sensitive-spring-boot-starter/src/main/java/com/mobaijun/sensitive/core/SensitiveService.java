package com.mobaijun.sensitive.core;

/**
 * Description: [
 * 脱敏服务
 * 默认管理员不过滤
 * 需自行根据业务重写实现
 * ]
 * Author: [mobaijun]
 * Date: [2024/7/30 9:48]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public interface SensitiveService {

    /**
     * 是否脱敏
     */
    boolean isSensitive(String roleKey, String perms);
}
