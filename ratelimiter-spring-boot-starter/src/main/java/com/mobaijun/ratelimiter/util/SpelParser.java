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
package com.mobaijun.ratelimiter.util;

import java.lang.reflect.Method;
import java.util.Objects;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * Description: [Spel表达式解析]
 * Author: [mobaijun]
 * Date: [2024/11/14 10:17]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public class SpelParser {

    /**
     * 用于解析SpEL表达式的解析器
     */
    private static final ExpressionParser parser = new SpelExpressionParser();

    /**
     * 用于获取方法参数名称的解析器
     */
    private static final ParameterNameDiscoverer pnd = new DefaultParameterNameDiscoverer();

    /**
     * 解析SpEL表达式，返回解析后的字符串
     *
     * @param spel       SpEL表达式
     * @param method     目标方法
     * @param args       方法参数
     * @param rootObject 根对象（如果需要，默认为 null）
     * @return 解析后的结果
     */
    private static String parseSpel(String spel, Method method, Object[] args, Object rootObject) {
        // 获取方法参数名列表
        String[] paramNames = pnd.getParameterNames(method);

        // SpEL上下文：根据是否需要 rootObject 使用不同的上下文类型
        StandardEvaluationContext context = (rootObject == null)
                ? new StandardEvaluationContext()
                : new MethodBasedEvaluationContext(rootObject, method, args, pnd);

        // 将方法参数放入上下文中
        for (int i = 0; i < Objects.requireNonNull(paramNames).length; i++) {
            context.setVariable(paramNames[i], args[i]);
        }

        try {
            // 解析并返回结果
            return parser.parseExpression(spel).getValue(context, String.class);
        } catch (Exception e) {
            // 解析失败时返回原始SpEL表达式
            return spel;
        }
    }

    /**
     * 解析SpEL表达式，返回解析后的字符串
     *
     * @param spel   SpEL表达式
     * @param method 目标方法
     * @param args   方法参数
     * @return 解析后的结果
     */
    public static String parse(String spel, Method method, Object[] args) {
        return parseSpel(spel, method, args, null);
    }

    /**
     * 支持 #p0 参数索引的表达式解析
     *
     * @param rootObject 根对象，method所在的对象
     * @param spel       SpEL表达式
     * @param method     目标方法
     * @param args       方法入参
     * @return 解析后的字符串
     */
    public static String parse(Object rootObject, String spel, Method method, Object[] args) {
        return parseSpel(spel, method, args, rootObject);
    }
}