package com.mobaijun.core.validate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Locale;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.spi.resourceloading.ResourceBundleLocator;

/**
 * Description: 将消息中空的花括号替换为校验注解的默认值
 * <p>
 * 扩展自原有的 {@link org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator} 消息处理器
 * Author: [mobaijun]
 * Date: [2024/8/15 12:44]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public class EmptyCurlyToDefaultMessageInterpolator extends ResourceBundleMessageInterpolator {

    /**
     * 空的花括号占位符
     */
    private static final String EMPTY_CURLY_BRACES = "{}";

    /**
     * 构造方法
     */
    public EmptyCurlyToDefaultMessageInterpolator() {
    }

    /**
     * 构造方法
     *
     * @param userResourceBundleLocator 用户自定义的资源文件
     */
    public EmptyCurlyToDefaultMessageInterpolator(ResourceBundleLocator userResourceBundleLocator) {
        super(userResourceBundleLocator);
    }

    @Override
    public String interpolate(String message, Context context, Locale locale) {

        // 如果包含花括号占位符
        if (message.contains(EMPTY_CURLY_BRACES)) {
            // 获取注解类型
            Class<? extends Annotation> annotationType = context.getConstraintDescriptor()
                    .getAnnotation()
                    .annotationType();

            Method messageMethod;
            try {
                messageMethod = annotationType.getDeclaredMethod("message");
            } catch (NoSuchMethodException e) {
                return super.interpolate(message, context, locale);
            }

            // 找到对应 message 的默认值，将 {} 替换为默认值
            if (messageMethod.getDefaultValue() != null) {
                Object defaultValue = messageMethod.getDefaultValue();
                if (defaultValue instanceof String defaultMessage) {
                    message = message.replace(EMPTY_CURLY_BRACES, defaultMessage);
                }
            }
        }
        return super.interpolate(message, context, locale);
    }
}
