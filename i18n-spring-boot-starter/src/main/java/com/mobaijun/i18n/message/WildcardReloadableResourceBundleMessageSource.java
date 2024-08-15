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
package com.mobaijun.i18n.message;

import io.micrometer.common.lang.NonNull;
import io.micrometer.common.lang.NonNullApi;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.ResourceUtils;

/**
 * Description:
 * Author: [mobaijun]
 * Date: [2024/8/15 12:03]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Slf4j
@NonNullApi
public class WildcardReloadableResourceBundleMessageSource extends ReloadableResourceBundleMessageSource {

    /**
     * 国际化文件后缀
     */
    private static final String PROPERTIES_SUFFIX = ".properties";
    /**
     * 通配符匹配器
     */
    private static final Pattern LOCALE_PROPERTIES_FILE_NAME_PATTERN = Pattern
            .compile(".*_([a-z]{2}(_[A-Z]{2})?(_[A-Z]+)?)\\.properties$");
    /**
     * 通配符匹配器
     */
    private final PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

    /**
     * 判断是否包含通配符
     */
    public WildcardReloadableResourceBundleMessageSource() {
        super.setResourceLoader(this.resolver);
    }

    /**
     * Calculate all filenames for the given bundle basename and Locale. Will calculate
     * filenames for the given Locale, the system Locale (if applicable), and the default
     * file.
     *
     * @param basename the basename of the bundle
     * @param locale   the locale
     * @return the List of filenames to check
     * @see #setFallbackToSystemLocale
     * @see #calculateFilenamesForLocale
     */
    @Override
    protected List<String> calculateAllFilenames(@NonNull String basename, @NonNull Locale locale) {
        // 父类默认的方法会将 basename 也放入 filenames 列表
        List<String> filenames = super.calculateAllFilenames(basename, locale);
        // 当 basename 有匹配符时，从 filenames 中移除，否则扫描文件将抛出 Illegal char <*> 的异常
        if (containsWildcard(basename)) {
            filenames.remove(basename);
            try {
                Resource[] resources = getResources(basename);
                for (Resource resource : resources) {
                    String resourceUriStr = resource.getURI().toString();
                    // 根据通配符匹配到的多个 basename 对应的文件添加到文件列表末尾，作为兜底匹配
                    if (!LOCALE_PROPERTIES_FILE_NAME_PATTERN.matcher(resourceUriStr).matches()) {
                        String sourcePath = resourceUriStr.replace(PROPERTIES_SUFFIX, "");
                        filenames.add(sourcePath);
                    }
                }
            } catch (IOException ex) {
                log.error("读取国际化信息文件异常", ex);
            }
        }
        return filenames;
    }

    @Override
    protected List<String> calculateFilenamesForLocale(@NonNull String basename, @NonNull Locale locale) {
        // 资源文件名
        List<String> fileNames = new ArrayList<>();
        // 获取到待匹配的国际化信息文件名集合
        List<String> matchFilenames = super.calculateFilenamesForLocale(basename, locale);
        for (String matchFilename : matchFilenames) {
            try {
                Resource[] resources = getResources(matchFilename);
                for (Resource resource : resources) {
                    if (resource.exists()) {
                        String sourcePath = resource.getURI().toString().replace(PROPERTIES_SUFFIX, "");
                        fileNames.add(sourcePath);
                    }
                }
            } catch (IOException ex) {
                log.error("读取国际化信息文件异常", ex);
            }
        }
        return fileNames;
    }

    /**
     * 获取资源文件
     *
     * @param resourceLocationPattern 资源文件路径
     * @return 资源文件
     * @throws IOException 异常信息
     */
    private Resource[] getResources(String resourceLocationPattern) throws IOException {
        // 支持用 . 表示文件层级
        resourceLocationPattern = resourceLocationPattern.replace(".", "/");
        return this.resolver.getResources("classpath*:" + resourceLocationPattern + PROPERTIES_SUFFIX);
    }

    /**
     * 判断是否包含通配符
     *
     * @param str 待判断的字符串
     * @return 包含通配符返回 true，否则返回 false
     */
    private boolean containsWildcard(String str) {
        if (str.startsWith(ResourceUtils.CLASSPATH_URL_PREFIX)) {
            str = str.substring(ResourceUtils.CLASSPATH_URL_PREFIX.length());
        }
        return str.contains("*");
    }
}
