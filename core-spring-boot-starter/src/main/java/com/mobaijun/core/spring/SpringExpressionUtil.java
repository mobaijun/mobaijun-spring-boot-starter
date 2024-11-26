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
package com.mobaijun.core.spring;

import com.mobaijun.common.collection.MapUtil;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * Description: Spring EL 表达式的工具类
 * Author: [mobaijun]
 * Date: [2024/9/3 15:35]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public class SpringExpressionUtil {

    /**
     * Spring EL 表达式解析器
     */
    private static final ExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();

    /**
     * 参数名发现器
     */
    private static final ParameterNameDiscoverer PARAMETER_NAME_DISCOVERER = new DefaultParameterNameDiscoverer();

    private SpringExpressionUtil() {
    }

    /**
     * 从切面中，单个解析 EL 表达式的结果
     *
     * @param joinPoint        切面点
     * @param expressionString EL 表达式数组
     * @return 执行界面
     */
    public static Object parseExpression(JoinPoint joinPoint, String expressionString) {
        Map<String, Object> result = parseExpressions(joinPoint, Collections.singletonList(expressionString));
        return result.get(expressionString);
    }

    /**
     * 从切面中，批量解析 EL 表达式的结果
     *
     * @param joinPoint         切面点
     * @param expressionStrings EL 表达式数组
     * @return 结果，key 为表达式，value 为对应值
     */
    public static Map<String, Object> parseExpressions(JoinPoint joinPoint, List<String> expressionStrings) {
        // 如果为空，则不进行解析
        if (expressionStrings == null || expressionStrings.isEmpty()) {
            return MapUtil.newHashMap();
        }

        // 第一步，构建解析的上下文 EvaluationContext
        // 通过 joinPoint 获取被注解方法
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        // 使用 spring 的 ParameterNameDiscoverer 获取方法形参名数组
        String[] paramNames = PARAMETER_NAME_DISCOVERER.getParameterNames(method);
        // Spring 的表达式上下文对象
        EvaluationContext context = new StandardEvaluationContext();
        // 给上下文赋值
        assert paramNames != null;
        if (!Arrays.asList(paramNames).isEmpty()) {
            Object[] args = joinPoint.getArgs();
            for (int i = 0; i < paramNames.length; i++) {
                context.setVariable(paramNames[i], args[i]);
            }
        }

        // 第二步，逐个参数解析
        Map<String, Object> result = MapUtil.newHashMap(expressionStrings.size(), true);
        expressionStrings.forEach(key -> {
            Object value = EXPRESSION_PARSER.parseExpression(key).getValue(context);
            result.put(key, value);
        });
        return result;
    }

    /**
     * 从 Bean 工厂，解析 EL 表达式的结果
     *
     * @param expressionString EL 表达式
     * @return 执行界面
     */
    public static Object parseExpression(String expressionString) {
        if (expressionString.isBlank()) {
            return null;
        }
        Expression expression = EXPRESSION_PARSER.parseExpression(expressionString);
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setBeanResolver(new BeanFactoryResolver(SpringUtil.getApplicationContext()));
        return expression.getValue(context);
    }
}
