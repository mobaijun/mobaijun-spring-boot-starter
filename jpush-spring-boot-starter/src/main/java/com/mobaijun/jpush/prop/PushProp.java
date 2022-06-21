package com.mobaijun.jpush.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: PushProp
 * class description： 极光推送属性文件
 *
 * @author MoBaiJun 2022/6/21 9:18
 */
@ConfigurationProperties(PushProp.PREFIX)
@EnableConfigurationProperties(PushProp.class)
public class PushProp {

    /**
     * 推送前缀
     */
    public static final String PREFIX = "push";

    /**
     * masterSecret(从极光后台获得)
     */
    private String masterSecret;

    /**
     * appKey(从极光后台获得)
     */
    private String appKey;

    /**
     * 此字段的值是用来指定本推送要推送的apns环境，
     * false表示开发，true表示生产；对android和自定义消息无意义
     * 上线之后要改为 true
     */
    private boolean apnsProduction = false;

    /**
     * 是否启用代理服务器
     */
    private boolean useProxy = false;

    /**
     * 代理服务器主机名或IP
     */
    private String proxyHost;

    /**
     * 代理服务器端口号
     */
    private int proxyPort;

    /**
     * 代理服务器用户名
     */
    private String proxyUsername;

    /**
     * 代理服务器密码
     */
    private String proxyPassword;

    /**
     * 重试时间间隔(毫秒)
     */
    private Long retryInterval = 500L;

    /**
     * 最大重试次数(0表示不重试)
     */
    private Integer retryMaxAttempts = 0;

    public String getMasterSecret() {
        return masterSecret.trim();
    }

    public void setMasterSecret(String masterSecret) {
        this.masterSecret = masterSecret;
    }

    public String getAppKey() {
        return appKey.trim();
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public boolean isUseProxy() {
        return useProxy;
    }

    public void setUseProxy(boolean useProxy) {
        this.useProxy = useProxy;
    }

    public String getProxyHost() {
        return proxyHost.trim();
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public int getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }

    public String getProxyUsername() {
        return proxyUsername.trim();
    }

    public void setProxyUsername(String proxyUsername) {
        this.proxyUsername = proxyUsername;
    }

    public String getProxyPassword() {
        return proxyPassword.trim();
    }

    public void setProxyPassword(String proxyPassword) {
        this.proxyPassword = proxyPassword;
    }

    public Long getRetryInterval() {
        return retryInterval;
    }

    public void setRetryInterval(Long retryInterval) {
        this.retryInterval = retryInterval;
    }

    public Integer getRetryMaxAttempts() {
        return retryMaxAttempts;
    }

    public void setRetryMaxAttempts(Integer retryMaxAttempts) {
        this.retryMaxAttempts = retryMaxAttempts;
    }

    public boolean isApnsProduction() {
        return apnsProduction;
    }

    public void setApnsProduction(boolean apnsProduction) {
        this.apnsProduction = apnsProduction;
    }
}
