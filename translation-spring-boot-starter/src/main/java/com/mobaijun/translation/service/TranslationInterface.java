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
package com.mobaijun.translation.service;

/**
 * Description: [翻译接口，用于定义翻译实现类的行为。]
 * Author: [mobaijun]
 * Date: [2024/9/28 8:31]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 *
 * @param <T> 翻译结果的类型
 */
public interface TranslationInterface<T> {

    /**
     * 执行翻译操作。
     *
     * @param key   需要被翻译的键（不能为空）
     * @param other 其他参数，用于提供额外的上下文信息
     * @return 返回键对应的翻译结果
     */
    T translation(Object key, String other);
}

