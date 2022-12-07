package com.mobaijun.core.spring;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;

import java.util.Arrays;

/**
 * software：IntelliJ IDEA 2022.2.3<br>
 * class name: SpringContextUtils<br>
 * class description: spring 工具类<br>
 *
 * @author MoBaiJun 2022/12/7 14:23
 */
public class SpringContextUtils implements ApplicationContextAware, BeanFactoryPostProcessor {

    /**
     * ConfigurableListableBeanFactory
     */
    private static ConfigurableListableBeanFactory beanFactory;

    /**
     * ApplicationContext
     */
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext)
            throws BeansException {
        SpringContextUtils.applicationContext = applicationContext;
    }

    @Override
    public void postProcessBeanFactory(@NonNull ConfigurableListableBeanFactory beanFactory)
            throws BeansException {
        SpringContextUtils.beanFactory = beanFactory;
    }

    /**
     * getApplicationContext
     *
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * getBean
     *
     * @param name service 注解方式 name 为小驼峰格式
     * @return Object bean 的实例对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) throws BeansException {
        return (T) applicationContext.getBean(name);
    }

    /**
     * getBean
     *
     * @param clz service 对应的类
     * @return Object bean 的实例对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<?> clz) throws BeansException {
        return (T) applicationContext.getBean(clz);
    }

    /**
     * 如果 BeanFactory 包含一个与所给名称匹配的 bean 定义，则返回 true
     *
     * @return boolean
     */
    public static boolean containsBean(String name) {
        return beanFactory.containsBean(name);
    }

    /**
     * 判断以给定名字注册的bean定义是一个singleton还是一个prototype。
     * 如果与给定名字相应的bean定义没有被找到，将会抛出一个异常（NoSuchBeanDefinitionException）
     *
     * @return boolean
     */
    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return beanFactory.isSingleton(name);
    }

    /**
     * getType
     *
     * @return Class 注册对象的类型
     */
    public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        return beanFactory.getType(name);
    }

    /**
     * 如果给定的bean名字在bean定义中有别名，则返回这些别名
     */
    public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
        return beanFactory.getAliases(name);
    }

    /**
     * 获取 aop 代理对象
     *
     * @param invoker invoker
     */
    @SuppressWarnings("unchecked")
    public static <T> T getAopProxy(T invoker) {
        return (T) AopContext.currentProxy();
    }

    /**
     * 获取当前的环境配置，当有多个环境配置时，只获取第一个
     *
     * @return Current environment configuration
     */
    public static String[] getActiveProfiles() {
        return applicationContext.getEnvironment().getActiveProfiles();
    }

    /**
     * 获取当前的环境配置，当有多个环境配置时，只获取第一个
     *
     * @return Current environment configuration
     */
    public static String getActiveProfile() {
        final String[] activeProfiles = getActiveProfiles();
        return !StringUtils.hasText(Arrays.toString(activeProfiles)) ? activeProfiles[0] : null;
    }
}
