package com.mobaijun.swagger.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: WebMvcConfig
 * class description：web 配置类
 *
 * @author MoBaiJun 2022/6/30 15:55
 */
@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurationSupport {

    /**
     * 解决 org.springframework.context.ApplicationContextException: Failed to start bean <br>
     * 'documentationPluginsBootstrapper'; nested exception is java.lang.NullPointerException
     * <a href="http://localhost:8891/swagger-ui/index.html#/">...</a>
     *
     * @param registry ResourceHandlerRegistry
     * @see <a href="https://blog.csdn.net/weixin_39792935/article/details/122215625?spm=1001.2014.3001.5501"></a>
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
        // 解决 swagger 无法访问
        registry.addResourceHandler("swagger-ui/**", "doc.html")
                // 解决 doc.html 无法访问
                .addResourceLocations("classpath:/META-INF/resources/**",
                        // 解决 swagger 无法访问
                        "classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
                .resourceChain(false);
        // 解决 swagger js 无法访问
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
    }
}
