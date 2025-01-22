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
package com.mobaijun.i18n.config;

import com.mobaijun.i18n.message.WildcardReloadableResourceBundleMessageSource;
import java.nio.charset.Charset;
import java.time.Duration;
import java.util.Optional;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.context.MessageSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.ConcurrentReferenceHashMap;
import org.springframework.util.StringUtils;

/**
 * Description: {@link org.springframework.boot.autoconfigure.EnableAutoConfiguration Auto-configuration} for {@link MessageSource}.
 * Author: [mobaijun]
 * Date: [2024/8/15 12:40]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@AutoConfiguration(before = MessageSourceAutoConfiguration.class)
@ConditionalOnMissingBean(name = AbstractApplicationContext.MESSAGE_SOURCE_BEAN_NAME, search = SearchStrategy.CURRENT)
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@Conditional(CustomMessageSourceAutoConfiguration.ResourceBundleCondition.class)
@EnableConfigurationProperties
public class CustomMessageSourceAutoConfiguration {

    /**
     * 空资源数组
     */
    private static final Resource[] NO_RESOURCES = {};

    /**
     * 获取messageSourceProperties
     *
     * @return 返回messageSourceProperties
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.messages")
    @ConditionalOnMissingBean
    public MessageSourceProperties messageSourceProperties() {
        return new MessageSourceProperties();
    }

    /**
     * 获取messageSource
     *
     * @param properties 参数
     * @return 返回messageSource
     */
    @Bean
    public MessageSource messageSource(MessageSourceProperties properties) {
        WildcardReloadableResourceBundleMessageSource messageSource = new WildcardReloadableResourceBundleMessageSource();

        // 设置 basenames
        Optional.ofNullable(properties.getBasename())
                .filter(basenames -> !basenames.isEmpty())
                .ifPresent(basenames -> messageSource.setBasenames(basenames.toArray(new String[0])));

        // 设置其他属性
        Optional.ofNullable(properties.getEncoding())
                .map(Charset::name)
                .ifPresent(messageSource::setDefaultEncoding);

        messageSource.setFallbackToSystemLocale(properties.isFallbackToSystemLocale());

        Optional.ofNullable(properties.getCacheDuration())
                .map(Duration::toMillis)
                .ifPresent(messageSource::setCacheMillis);

        messageSource.setAlwaysUseMessageFormat(properties.isAlwaysUseMessageFormat());
        messageSource.setUseCodeAsDefaultMessage(properties.isUseCodeAsDefaultMessage());

        return messageSource;
    }

    /**
     * 资源条件
     */
    protected static class ResourceBundleCondition extends SpringBootCondition {

        /**
         * 缓存
         */
        private static final ConcurrentReferenceHashMap<String, ConditionOutcome> cache = new ConcurrentReferenceHashMap<>();

        /**
         * 获取匹配结果
         *
         * @param context  上下文
         * @param metadata 注解元数据
         * @return 返回匹配结果
         */
        @Override
        public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
            String basename = context.getEnvironment().getProperty("spring.messages.basename", "messages");
            ConditionOutcome outcome = cache.get(basename);
            if (outcome == null) {
                outcome = getMatchOutcomeForBasename(context, basename);
                cache.put(basename, outcome);
            }
            return outcome;
        }

        /**
         * 获取匹配结果
         *
         * @param context  上下文
         * @param basename 基础名称
         * @return 返回匹配结果
         */
        private ConditionOutcome getMatchOutcomeForBasename(ConditionContext context, String basename) {
            ConditionMessage.Builder message = ConditionMessage.forCondition("ResourceBundle");
            for (String name : StringUtils.commaDelimitedListToStringArray(StringUtils.trimAllWhitespace(basename))) {
                for (Resource resource : getResources(context.getClassLoader(), name)) {
                    if (resource.exists()) {
                        return ConditionOutcome.match(message.found("bundle").items(resource));
                    }
                }
            }
            return ConditionOutcome.noMatch(message.didNotFind("bundle with basename " + basename).atAll());
        }

        /**
         * 获取资源
         *
         * @param classLoader 加载器
         * @param name        名称
         * @return 返回资源
         */
        private Resource[] getResources(ClassLoader classLoader, String name) {
            String target = name.replace('.', '/');
            try {
                return new PathMatchingResourcePatternResolver(classLoader)
                        .getResources("classpath*:" + target + ".properties");
            } catch (Exception ex) {
                return NO_RESOURCES;
            }
        }
    }
}
