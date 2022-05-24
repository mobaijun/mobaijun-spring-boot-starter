package com.mobaijun.swagger.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * ClassName: SwaggerProperties
 * 类描述： Swagger配置读取类
 *
 * @author MoBaiJun 2022/3/12 17:24 blog:<a href="https://www.mobaijun.com">...</a>
 */
@ConfigurationProperties(SwaggerProperties.PREFIX)
@EnableConfigurationProperties(SwaggerProperties.class)
public class SwaggerProperties {

    /**
     * 标识
     */
    public static final String PREFIX = "swagger";

    /**
     * 石否开启
     */
    private Boolean enable;

    /**
     * 应用标题
     */
    private String title;

    /**
     * 描述
     */
    private String description;

    /**
     * 许可证
     **/
    private String license = "";

    /**
     * 许可证URL
     **/
    private String licenseUrl = "";

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * 服务地址
     */
    private String termsOfServiceUrl;

    /**
     * 版本
     */
    private String version;

    /**
     * host信息
     **/
    private String host = "";

    /**
     * 包路径
     **/
    private String basePackage = "";

    /**
     * 配置作者信息
     */
    private Contact contact = new Contact();

    /**
     * 全局统一鉴权配置
     **/
    private Authorization authorization = new Authorization();

    public SwaggerProperties(Boolean enable, String title, String description, String license, String licenseUrl, String groupName, String termsOfServiceUrl, String version, String host, String basePackage, Contact contact, Authorization authorization) {
        this.enable = enable;
        this.title = title;
        this.description = description;
        this.license = license;
        this.licenseUrl = licenseUrl;
        this.groupName = groupName;
        this.termsOfServiceUrl = termsOfServiceUrl;
        this.version = version;
        this.host = host;
        this.basePackage = basePackage;
        this.contact = contact;
        this.authorization = authorization;
    }

    public SwaggerProperties() {
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getLicenseUrl() {
        return licenseUrl;
    }

    public void setLicenseUrl(String licenseUrl) {
        this.licenseUrl = licenseUrl;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getTermsOfServiceUrl() {
        return termsOfServiceUrl;
    }

    public void setTermsOfServiceUrl(String termsOfServiceUrl) {
        this.termsOfServiceUrl = termsOfServiceUrl;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Authorization getAuthorization() {
        return authorization;
    }

    public void setAuthorization(Authorization authorization) {
        this.authorization = authorization;
    }

    public static class Contact {

        /**
         * 联系人
         **/
        private String author = "mobaijun";

        /**
         * 联系人url
         **/
        private String url = "https://www.mobaijun.com";

        /**
         * 联系人email
         **/
        private String email = "mobaijun8@163.com";

        public Contact() {
        }

        public Contact(String author, String url, String email) {
            this.author = author;
            this.url = url;
            this.email = email;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

    /**
     * 鉴权内部类
     */
    public static class Authorization {

        /**
         * 鉴权策略ID，对应 SecurityReferences ID
         */
        private String header = "Authorization";

        /**
         * 鉴权传递的Header参数
         */
        private String token = "TOKEN";

        /**
         * 需要开启鉴权URL的正则
         */
        private String authRegex = "^.*$";

        public Authorization() {
        }

        public Authorization(String header, String token, String authRegex) {
            this.header = header;
            this.token = token;
            this.authRegex = authRegex;
        }

        public String getHeader() {
            return header;
        }

        public void setHeader(String header) {
            this.header = header;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getAuthRegex() {
            return authRegex;
        }

        public void setAuthRegex(String authRegex) {
            this.authRegex = authRegex;
        }
    }
}
