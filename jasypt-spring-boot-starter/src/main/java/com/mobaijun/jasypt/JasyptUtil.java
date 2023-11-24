package com.mobaijun.jasypt;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.util.StringUtils;

/**
 * Description: [jasypt 工具类]
 * Author: [mobaijun]
 * Date: [2023/11/24 10:13]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public class JasyptUtil {

    /**
     * Jasypt 加密器密码
     */
    private static final String PASSWORD = "aWgC=GRb%m>sv+LVV,7P:q";

    /**
     * 加密内容前缀
     */
    private static final String ENC_PREFIX = "ENC(";

    /**
     * 加密内容后缀
     */
    private static final String ENC_SUFFIX = ")";

    /**
     * Jasypt 加密器
     */
    private static final PooledPBEStringEncryptor ENCRYPTOR;

    static {
        // Jasypt 配置
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(PASSWORD);
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
        config.setStringOutputType("base64");

        // 初始化 Jasypt 加密器
        ENCRYPTOR = new PooledPBEStringEncryptor();
        ENCRYPTOR.setConfig(config);
    }

    /**
     * 加密内容
     *
     * @param content 要加密的内容
     * @return 加密后的字符串
     */
    public static String encrypt(String content) {
        return ENC_PREFIX + ENCRYPTOR.encrypt(content) + ENC_SUFFIX;
    }

    /**
     * 解密内容
     *
     * @param content 要解密的内容
     * @return 解密后的字符串
     */
    public static String decrypt(String content) {
        if (StringUtils.hasLength(content) && content.contains(ENC_PREFIX)) {
            content = content.substring(ENC_PREFIX.length(), content.length() - ENC_SUFFIX.length());
            return ENCRYPTOR.decrypt(content);
        } else {
            return "";
        }
    }

    /**
     * 获取 Jasypt 加密器
     *
     * @return Jasypt 加密器实例
     */
    public static StringEncryptor getEncryptor() {
        return ENCRYPTOR;
    }
}
