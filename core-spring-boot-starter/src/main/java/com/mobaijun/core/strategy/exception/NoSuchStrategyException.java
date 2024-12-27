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
package com.mobaijun.core.strategy.exception;

/**
 * Description: [当无法找到指定类型的策略时抛出该异常]
 * Author: [mobaijun]
 * Date: [2024/12/27 14:39]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public class NoSuchStrategyException extends RuntimeException {

    public <T> NoSuchStrategyException(Class<T> strategyType, String name) {
        super("No strategy found under class '" + strategyType.getName() + "' for name '" + name + "'.");
    }
}