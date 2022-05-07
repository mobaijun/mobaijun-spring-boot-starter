package com.mobaijun.mybatis.plus.injector;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import lombok.RequiredArgsConstructor;

import java.util.List;

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
    public List<AbstractMethod> getMethodList(Class<?> mapperClass, TableInfo tableInfo) {
        List<AbstractMethod> list = super.getMethodList(mapperClass, tableInfo);
        list.addAll(this.methods);
        return list;
    }
}
