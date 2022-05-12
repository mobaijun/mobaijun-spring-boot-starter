package com.mobaijun.mybatis.plus.config;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.mobaijun.mybatis.plus.injector.CustomSqlInjector;
import com.mobaijun.mybatis.plus.methods.InsertBatchSomeColumnByCollection;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * ClassName: MybatisPlusConfig
 * 类描述： mybatis plus configuration
 *
 * @author MoBaiJun 2022/5/7 15:29
 */
@Configuration
public class MybatisPlusAutoConfiguration {
    /**
     * MybatisPlusInterceptor 插件，默认提供分页插件
     * 如需其他MP内置插件，则需自定义该Bean
     *
     * @return MybatisPlusInterceptor
     */
    @Bean
    @ConditionalOnMissingBean(MybatisPlusInterceptor.class)
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }

    /**
     * 自动填充处理类
     *
     * @return FillMetaObjectHandle
     */
    @Bean
    @ConditionalOnMissingBean(FillMetaObjectHandle.class)
    public FillMetaObjectHandle fillMetaObjectHandle() {
        return new FillMetaObjectHandle();
    }

    /**
     * 自定义批量插入方法注入
     *
     * @return ISqlInjector
     */
    @Bean
    @ConditionalOnMissingBean(ISqlInjector.class)
    public ISqlInjector customSqlInjector() {
        List<AbstractMethod> list = new ArrayList<>();
        // 对于只在更新时进行填充的字段不做插入处理
        list.add(new InsertBatchSomeColumnByCollection(t -> t.getFieldFill() != FieldFill.UPDATE));
        return new CustomSqlInjector(list);
    }
}
