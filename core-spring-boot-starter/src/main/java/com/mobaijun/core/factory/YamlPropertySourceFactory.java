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
package com.mobaijun.core.factory;

import io.micrometer.common.lang.NonNullApi;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.lang.Nullable;

/**
 * Description: []
 * Author: [mobaijun]
 * Date: [2024/7/30 14:32]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@NonNullApi
public class YamlPropertySourceFactory implements PropertySourceFactory {

    /**
     * 从 YAML 资源创建一个 PropertySource。
     *
     * @param name     属性源的名称，可以为 null
     * @param resource 要加载的资源
     * @return 从 YAML 资源创建的 PropertySource
     * @throws IOException 如果资源无法加载
     */
    @Override
    public PropertySource<?> createPropertySource(@Nullable String name, EncodedResource resource) throws IOException {
        Properties propertiesFromYaml = loadYamlIntoProperties(resource);
        // 如果 name 为空，则使用资源的文件名作为名称
        String sourceName = (name != null) ? name : Objects.requireNonNull(resource.getResource().getFilename(), "资源文件名不能为空");
        return new PropertiesPropertySource(sourceName, propertiesFromYaml);
    }

    /**
     * 将 YAML 资源加载到 Properties 对象中。
     *
     * @param resource 要加载的资源
     * @return 从 YAML 资源加载的 Properties
     * @throws FileNotFoundException 如果资源找不到
     */
    private Properties loadYamlIntoProperties(EncodedResource resource) throws FileNotFoundException {
        try {
            YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
            factory.setResources(resource.getResource());
            factory.afterPropertiesSet();
            // 确保工厂 bean 返回的 properties 不为 null
            return Objects.requireNonNull(factory.getObject(), "工厂 bean 返回的 properties 为空");
        } catch (IllegalStateException e) {
            Throwable cause = e.getCause();
            if (cause instanceof FileNotFoundException) {
                throw (FileNotFoundException) cause;
            }
            throw e;
        }
    }
}
