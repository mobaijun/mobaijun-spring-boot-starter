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
package com.mobaijun.core.util;

import java.lang.reflect.Type;
import lombok.Getter;

/**
 * Type类型参考<br>
 * 通过构建一个类型参考子类，可以获取其泛型参数中的Type类型。例如：
 *
 * <pre>
 * TypeReference&lt;List&lt;String&gt;&gt; list = new TypeReference&lt;List&lt;String&gt;&gt;() {};
 * Type t = tr.getType();
 * </pre>
 * <p>
 * 此类无法应用于通配符泛型参数（wildcard parameters），比如：{@code Class<?>} 或者 {@code List? extends CharSequence>}
 *
 * <p>
 * 此类参考FastJSON的TypeReference实现
 *
 * @param <T> 需要自定义的参考类型
 * @author looly
 * @since 4.2.2
 */
@Getter
public abstract class TypeReference<T> implements Type {

    /**
     * 泛型参数
     * -- GETTER --
     * 获取用户定义的泛型参数
     */
    private final Type type;

    /**
     * 构造
     */
    public TypeReference() {
        this.type = TypeUtil.getTypeArgument(getClass());
    }

    @Override
    public String toString() {
        return this.type.toString();
    }
}
