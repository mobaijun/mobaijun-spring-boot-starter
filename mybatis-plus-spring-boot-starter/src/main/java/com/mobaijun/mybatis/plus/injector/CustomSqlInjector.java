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
package com.mobaijun.mybatis.plus.injector;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.Configuration;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * ClassName: CustomSqlInjector
 * 类描述：默认的注入器，提供属性来注入自定义方法
 *
 * @author MoBaiJun 2022/5/7 16:30
 */
@RequiredArgsConstructor
public class CustomSqlInjector extends DefaultSqlInjector {

    private final List<AbstractMethod> methods;

    @Override
    public List<AbstractMethod> getMethodList(Configuration configuration, Class<?> mapperClass, TableInfo tableInfo) {
        List<AbstractMethod> methodList = super.getMethodList(configuration, mapperClass, tableInfo);
        methodList.addAll(this.methods);
        return methodList;
    }

}