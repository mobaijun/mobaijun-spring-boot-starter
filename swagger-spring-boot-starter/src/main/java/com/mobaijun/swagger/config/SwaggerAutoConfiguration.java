package com.mobaijun.swagger.config;

import com.google.common.collect.Lists;
import com.mobaijun.swagger.prop.SwaggerProperties;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * ClassName: SwaggerConfig
 * 类描述： swagger配置类
 *
 * @author MoBaiJun 2022/4/26 9:09
 */
@Configuration
@Import({SwaggerConfiguration.class})
public class SwaggerAutoConfiguration {

    private final Logger log = LoggerFactory.getLogger(SwaggerAutoConfiguration.class);

    @Bean
    @ConditionalOnMissingBean
    public SwaggerProperties swaggerProperties() {
        return new SwaggerProperties();
    }

    /**
     * # 常用注解说明
     * 1、@Api：用在controller类，描述API接口
     * 2、@ApiOperation：描述接口方法
     * 3、@ApiModel：描述对象
     * 4、@ApiModelProperty：描述对象属性
     * 5、@ApiImplicitParams：描述接口参数
     * 6、@ApiResponses：描述接口响应
     * 7、@ApiIgnore：忽略接口方法
     * 8、访问地址：<a href="http://localhost:8003/swagger-ui/index.html#/">......</a>
     * 9、doc文档访问地址: <a href="http://localhost:8003/doc.html">.....</a>
     *
     * @param swaggerProperties swaggerProperties
     * @return Docket
     */
    @Bean
    public Docket createRestApi(SwaggerProperties swaggerProperties) {
        log.info("============================ Swagger Api 构建成功 ============================");
        return new Docket(DocumentationType.SWAGGER_2)
                // 是否启用swagger / 生产环境关闭
                .enable(swaggerProperties.getEnable())
                // 服务器地址
                .host(swaggerProperties.getHost())
                // 设置该 docket 的名字,可以实现多个Docket,实现分组
                .apiInfo(apiInfo(swaggerProperties))
                .select()
                // withMethodAnnotation 扫描所有包含(@ApiOperation)的API,用这种方式更加灵活
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                // 正则匹配请求路径，并分配至当前分组，当前所有接口
                .paths(PathSelectors.any())
                .build()
                // 解决 Failed to convert value of type ‘java.lang.String’ to required type ‘java.time.LocalDate’;
                .directModelSubstitute(LocalTime.class, String.class)
                .directModelSubstitute(LocalDateTime.class, String.class)
                // 分组名称
                .groupName(swaggerProperties.getGroupName())
                // 授权信息全局应用
                .securityContexts(securityContexts())
                // 授权信息设置，必要的header token等认证信息
                .securitySchemes(apiKeys());
    }

    /**
     * API 页面上半部分展示信息
     */
    private ApiInfo apiInfo(SwaggerProperties sp) {
        return new ApiInfoBuilder()
                // 标题
                .title(sp.getTitle())
                // 说明
                .description(sp.getDescription())
                // 官网
                .termsOfServiceUrl(sp.getTermsOfServiceUrl())
                // 许可证
                .license(sp.getLicense())
                // 许可证地址
                .licenseUrl(sp.getLicenseUrl())
                // 作者信息
                .contact(new Contact(
                        // 作者
                        sp.getContact().getAuthor(),
                        // 博客地址
                        sp.getContact().getUrl(),
                        // 邮箱
                        sp.getContact().getEmail()))
                // 版本
                .version(sp.getVersion())
                .build();
    }

    /**
     * 设置授权信息
     */
    private List<SecurityScheme> apiKeys() {
        return Lists.newArrayList(new ApiKey(swaggerProperties().getAuthorization().getHeader(),
                swaggerProperties().getAuthorization().getToken(), ApiKeyVehicle.HEADER.getValue()));
    }

    /**
     * 授权信息全局应用
     */
    private List<SecurityContext> securityContexts() {
        return Lists.newArrayList(SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex(swaggerProperties().getAuthorization().getAuthRegex()))
                .build());
    }

    /**
     * 配置默认的全局鉴权策略；其中返回的 SecurityReference 中，reference 即为ApiKey对象里面的name，保持一致才能开启全局鉴权
     */
    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Collections.singletonList(SecurityReference.builder()
                .reference(swaggerProperties().getAuthorization().getHeader())
                .scopes(authorizationScopes).build());
    }
}