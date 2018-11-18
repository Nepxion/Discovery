package com.nepxion.discovery.console.configuration;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;

@Configuration
@EnableSwagger2
@ConditionalOnProperty(value = "swagger.service.enabled", matchIfMissing = true)
public class SwaggerConfiguration implements WebMvcConfigurer {
    public static final String BASE_PACKAGE = "com.nepxion.discovery.console.endpoint";

    @Value("${spring.application.name}")
    private String serviceName;

    @Value("${swagger.service.base.package:}")
    private String basePackage;

    @Value("${swagger.service.description:Console Restful APIs}")
    private String description;

    @Value("${swagger.service.version:1.0.0}")
    private String version;

    @Value("${swagger.service.license.name:Apache License 2.0}")
    private String license;

    @Value("${swagger.service.license.url:http://www.apache.org/licenses/LICENSE-2.0}")
    private String licenseUrl;

    @Value("${swagger.service.contact.name:Haojun Ren}")
    private String contactName;

    @Value("${swagger.service.contact.url:https://github.com/Nepxion/Discovery}")
    private String contactUrl;

    @Value("${swagger.service.contact.email:1394997@qq.com}")
    private String contactEmail;

    @Value("${swagger.service.termsOfServiceUrl:http://www.nepxion.com")
    private String termsOfServiceUrl;

    @Value("${swagger.cors.registry.enabled:true}")
    private Boolean corsRegistryEnabled;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(SwaggerConfiguration.basePackage(BASE_PACKAGE + (StringUtils.isNotEmpty(basePackage.trim()) ? "," + basePackage.trim() : StringUtils.EMPTY))) // 扫描该包下的所有需要在Swagger中展示的API，@ApiIgnore注解标注的除外
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(serviceName)
                .description(description)
                .version(version)
                .license(license)
                .licenseUrl(licenseUrl)
                .contact(new Contact(contactName, contactUrl, contactEmail))
                .termsOfServiceUrl(termsOfServiceUrl)
                .build();
    }

    // 解决跨域问题
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        if (corsRegistryEnabled) {
            registry.addMapping("/**")
                    .allowedHeaders("*")
                    .allowedMethods("*")
                    .allowedOrigins("*");
        }
    }

    public static Predicate<RequestHandler> basePackage(String basePackage) {
        return new Predicate<RequestHandler>() {
            @Override
            public boolean apply(RequestHandler input) {
                return declaringClass(input).transform(handlerPackage(basePackage)).or(true);
            }
        };
    }

    private static Function<Class<?>, Boolean> handlerPackage(String basePackage) {
        return new Function<Class<?>, Boolean>() {
            @Override
            public Boolean apply(Class<?> input) {
                String[] subPackages = basePackage.split(",");
                for (String subPackage : subPackages) {
                    boolean matched = input.getPackage().getName().startsWith(subPackage.trim());
                    if (matched) {
                        return true;
                    }
                }

                return false;
            }
        };
    }

    @SuppressWarnings("deprecation")
    private static Optional<? extends Class<?>> declaringClass(RequestHandler input) {
        return Optional.fromNullable(input.declaringClass());
    }
}