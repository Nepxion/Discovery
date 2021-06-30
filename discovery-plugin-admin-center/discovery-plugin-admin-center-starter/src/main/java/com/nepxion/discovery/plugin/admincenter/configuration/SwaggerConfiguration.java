package com.nepxion.discovery.plugin.admincenter.configuration;

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
import springfox.documentation.service.Parameter;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerMapping;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.constant.DiscoverySwaggerConstant;

@Configuration
@EnableSwagger2
@ConditionalOnClass(HandlerMapping.class)
@ConditionalOnProperty(value = DiscoverySwaggerConstant.SWAGGER_SERVICE_ENABLED, matchIfMissing = true)
public class SwaggerConfiguration {
    public static final String BASE_PACKAGE = "com.nepxion.discovery.plugin.admincenter.endpoint";
    public static final String DESCRIPTION = "Admin Center Restful APIs";

    @Value("${" + DiscoverySwaggerConstant.SWAGGER_SERVICE_BASE_GROUP + ":" + DiscoveryConstant.NEPXION_DISCOVERY + "}")
    private String baseGroup;

    @Value("${" + DiscoverySwaggerConstant.SWAGGER_SERVICE_SCAN_GROUP + ":}")
    private String scanGroup;

    @Value("${" + DiscoverySwaggerConstant.SWAGGER_SERVICE_SCAN_PACKAGES + ":}")
    private String scanPackages;

    @Value("${" + DiscoveryConstant.SPRING_APPLICATION_NAME + "}")
    private String serviceName;

    @Value("${" + DiscoverySwaggerConstant.SWAGGER_SERVICE_DESCRIPTION + ":" + DESCRIPTION + "}")
    private String description;

    @Value("${" + DiscoverySwaggerConstant.SWAGGER_SERVICE_VERSION + ":" + DiscoveryConstant.DISCOVERY_VERSION + "}")
    private String version;

    @Value("${" + DiscoverySwaggerConstant.SWAGGER_SERVICE_LICENSE_NAME + ":" + DiscoverySwaggerConstant.SWAGGER_SERVICE_LICENSE_NAME_VALUE + "}")
    private String licenseName;

    @Value("${" + DiscoverySwaggerConstant.SWAGGER_SERVICE_LICENSE_URL + ":" + DiscoverySwaggerConstant.SWAGGER_SERVICE_LICENSE_URL_VALUE + "}")
    private String licenseUrl;

    @Value("${" + DiscoverySwaggerConstant.SWAGGER_SERVICE_CONTACT_NAME + ":" + DiscoveryConstant.NEPXION_FIRST_UPPERCASE + "}")
    private String contactName;

    @Value("${" + DiscoverySwaggerConstant.SWAGGER_SERVICE_CONTACT_URL + ":" + DiscoverySwaggerConstant.SWAGGER_SERVICE_CONTACT_URL_VALUE + "}")
    private String contactUrl;

    @Value("${" + DiscoverySwaggerConstant.SWAGGER_SERVICE_CONTACT_EMAIL + ":" + DiscoverySwaggerConstant.SWAGGER_SERVICE_CONTACT_EMAIL_VALUE + "}")
    private String contactEmail;

    @Value("${" + DiscoverySwaggerConstant.SWAGGER_SERVICE_TERMSOFSERVICE_URL + ":" + DiscoverySwaggerConstant.SWAGGER_SERVICE_TERMSOFSERVICE_URL_VALUE + "}")
    private String termsOfServiceUrl;

    @Autowired(required = false)
    private List<Parameter> swaggerHeaderParameters;

    @Autowired(required = false)
    private List<SecurityScheme> swaggerSecuritySchemes;

    @Autowired(required = false)
    private List<SecurityContext> swaggerSecurityContexts;

    @Bean("discoveryDocket")
    public Docket discoveryDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(baseGroup)
                .apiInfo(apiInfo())
                .select()
                .apis(SwaggerConfiguration.basePackage(BASE_PACKAGE)) // 扫描该包下的所有需要在Swagger中展示的API，@ApiIgnore注解标注的除外
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(swaggerHeaderParameters)
                .securitySchemes(swaggerSecuritySchemes)
                .securityContexts(swaggerSecurityContexts != null ? swaggerSecurityContexts : Collections.emptyList());
    }

    @Bean("scanDocket")
    @ConditionalOnProperty(name = DiscoverySwaggerConstant.SWAGGER_SERVICE_SCAN_GROUP)
    public Docket scanDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(scanGroup)
                .apiInfo(apiInfo())
                .select()
                .apis(SwaggerConfiguration.basePackage(scanPackages)) // 扫描该包下的所有需要在Swagger中展示的API，@ApiIgnore注解标注的除外
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(swaggerHeaderParameters)
                .securitySchemes(swaggerSecuritySchemes)
                .securityContexts(swaggerSecurityContexts != null ? swaggerSecurityContexts : Collections.emptyList());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(serviceName)
                .description(description)
                .version(version)
                .license(licenseName)
                .licenseUrl(licenseUrl)
                .contact(new Contact(contactName, contactUrl, contactEmail))
                .termsOfServiceUrl(termsOfServiceUrl)
                .build();
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
                String[] subPackages = basePackage.split(DiscoveryConstant.SEPARATE);
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