package com.mobaijun.swagger.config;

import io.swagger.annotations.ApiModel;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;
import springfox.documentation.schema.DefaultTypeNameProvider;
import springfox.documentation.swagger.common.SwaggerPluginSupport;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * software：IntelliJ IDEA 2022.2.3<br>
 * class name: FullPathTypeNameProvider<br>
 * class description: 默认安装路径名称生成文档<br>
 *
 * @author MoBaiJun 2022/12/7 13:40
 */
@Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER - 100)
public class FullPathTypeNameProvider extends DefaultTypeNameProvider {

    /**
     * 符号
     */
    public static final String SPLIT_CHAR = "$";

    @Override
    public String nameFor(Class<?> type) {
        ApiModel annotation = AnnotationUtils.findAnnotation(type,
                ApiModel.class);
        if (annotation == null) {
            return super.nameFor(type);
        }
        if (StringUtils.hasText(annotation.value())) {
            return annotation.value();
        }
        // 如果@ApiModel的value为空，则默认取完整类路径
        int packagePathLength = type.getPackage().getName().length();
        return Stream.of(type.getPackage().getName().split("\\."))
                .map(path -> path.substring(0, 1))
                .collect(Collectors.joining(SPLIT_CHAR))
                + SPLIT_CHAR
                + type.getName().substring(packagePathLength + 1);
    }
}
