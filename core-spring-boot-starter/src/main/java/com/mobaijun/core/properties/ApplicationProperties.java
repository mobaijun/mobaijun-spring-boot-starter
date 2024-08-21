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
package com.mobaijun.core.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Primary;

/**
 * Description: [项目配置类]
 * Author: [mobaijun]
 * Date: [2024/8/21 9:30]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Getter
@Setter
@Primary
@ToString
@EnableConfigurationProperties(ApplicationProperties.class)
@ConfigurationProperties(prefix = ApplicationProperties.PREFIX)
public class ApplicationProperties {

    /**
     * 配置文件前缀
     */
    public static final String PREFIX = "mobaijun";

    /**
     * 是否打印 bean
     */
    private boolean printBean = true;

    /**
     * 作者信息
     */
    static class Author {
        /**
         * 默认邮箱
         */
        public static final String EMAIL = "mobaijun8@163.com";

        /**
         * 默认 gmail 邮箱
         */
        public static final String EMAIL_GMAIL = "wljmobai@gmail.com";

        /**
         * 默认名称
         */
        public static final String NAME = "MoBaiJun";

        /**
         * 默认名字
         */
        public static final String FIRST_NAME = "墨白";

        /**
         * 默认博客地址
         */
        public static final String BLOG_URL = "https://www.mobaijun.com/";

        /**
         * 默认文档地址
         */
        public static final String DOCS_URL = "https://docs.mobaijun.com/";

        /**
         * 默认 api
         */
        public static final String URL = "https://api.mobaijun.com/";

        /**
         * 默认导航地址
         */
        public static final String NAV_URL = "https://nav.mobaijun.com/";

        /**
         * 默认 GitHub 主页
         */
        public static final String GitHub_HOME = "https://github.com/mobaijun";

        /**
         * 默认组织 GitHub 主页
         */
        public static final String GITHUB_ORGANIZATIONS = "https://github.com/april-projects";

        /**
         * 默认 gitee 主页
         */
        public static final String GITEE_HOME = "https://gitee.com/mobaijun";

        /**
         * 品牌
         */
        public static final String BRAND = "框架师";
    }
}
