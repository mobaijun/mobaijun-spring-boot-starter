/*
 * Copyright (C) 2022 www.mobaijun.com
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
package com.mobaijun.openapi.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * ClassName: OpenAPIProperties
 * 类描述： Swagger配置读取类
 *
 * @author MoBaiJun 2022/3/12 17:24 blog:<a href="https://www.mobaijun.com">...</a>
 */
@ConfigurationProperties(OpenAPIProperties.PREFIX)
@EnableConfigurationProperties(OpenAPIProperties.class)
public class OpenAPIProperties {

    /**
     * 标识
     */
    public static final String PREFIX = "openapi";

    /**
     * 是否开启(默认开启)
     */
    private Boolean enable = true;

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
     * 概述信息
     */
    private String summary;

    /**
     * 配置作者信息
     */
    private Contact contact = new Contact();


    public OpenAPIProperties() {
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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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
}