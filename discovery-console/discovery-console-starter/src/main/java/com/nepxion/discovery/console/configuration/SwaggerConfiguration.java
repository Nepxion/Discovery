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
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.constant.DiscoverySwaggerConstant;

@Configuration
@EnableSwagger2
@ConditionalOnProperty(value = DiscoverySwaggerConstant.SWAGGER_ENABLED, matchIfMissing = true)
public class SwaggerConfiguration {
    private String discoveryGroup = DiscoverySwaggerConstant.SWAGGER_DEFAULT_GROUP_VALUE;
    private String discoveryPackages = "com.nepxion.discovery.console.endpoint";
    private String discoveryDescription = "Console Restful APIs";
    private String discoveryVersion = DiscoverySwaggerConstant.SWAGGER_DEFAULT_VERSION_VALUE;
    private String discoveryLicenseName = DiscoverySwaggerConstant.SWAGGER_DEFAULT_LICENSE_NAME_VALUE;
    private String discoveryLicenseUrl = DiscoverySwaggerConstant.SWAGGER_DEFAULT_LICENSE_URL_VALUE;
    private String discoveryContactName = DiscoverySwaggerConstant.SWAGGER_DEFAULT_CONTACT_NAME_VALUE;
    private String discoveryContactUrl = DiscoverySwaggerConstant.SWAGGER_DEFAULT_CONTACT_URL_VALUE;
    private String discoveryContactEmail = DiscoverySwaggerConstant.SWAGGER_DEFAULT_CONTACT_EMAIL_VALUE;
    private String discoveryTermsOfServiceUrl = DiscoverySwaggerConstant.SWAGGER_DEFAULT_TERMSOFSERVICE_URL_VALUE;

    @Value("${" + DiscoverySwaggerConstant.SWAGGER_SERVICE_GROUP + ":}")
    private String serviceGroup;

    @Value("${" + DiscoverySwaggerConstant.SWAGGER_SERVICE_PACKAGES + ":}")
    private String servicePackages;

    @Value("${" + DiscoverySwaggerConstant.SWAGGER_SERVICE_DESCRIPTION + ":}")
    private String serviceDescription;

    @Value("${" + DiscoverySwaggerConstant.SWAGGER_SERVICE_VERSION + ":}")
    private String serviceVersion;

    @Value("${" + DiscoverySwaggerConstant.SWAGGER_SERVICE_LICENSE_NAME + ":" + DiscoverySwaggerConstant.SWAGGER_DEFAULT_LICENSE_NAME_VALUE + "}")
    private String serviceLicenseName;

    @Value("${" + DiscoverySwaggerConstant.SWAGGER_SERVICE_LICENSE_URL + ":" + DiscoverySwaggerConstant.SWAGGER_DEFAULT_LICENSE_URL_VALUE + "}")
    private String serviceLicenseUrl;

    @Value("${" + DiscoverySwaggerConstant.SWAGGER_SERVICE_CONTACT_NAME + ":" + DiscoverySwaggerConstant.SWAGGER_DEFAULT_CONTACT_NAME_VALUE + "}")
    private String serviceContactName;

    @Value("${" + DiscoverySwaggerConstant.SWAGGER_SERVICE_CONTACT_URL + ":" + DiscoverySwaggerConstant.SWAGGER_DEFAULT_CONTACT_URL_VALUE + "}")
    private String serviceContactUrl;

    @Value("${" + DiscoverySwaggerConstant.SWAGGER_SERVICE_CONTACT_EMAIL + ":" + DiscoverySwaggerConstant.SWAGGER_DEFAULT_CONTACT_EMAIL_VALUE + "}")
    private String serviceContactEmail;

    @Value("${" + DiscoverySwaggerConstant.SWAGGER_SERVICE_TERMSOFSERVICE_URL + ":" + DiscoverySwaggerConstant.SWAGGER_DEFAULT_TERMSOFSERVICE_URL_VALUE + "}")
    private String serviceTermsOfServiceUrl;

    @Value("${" + DiscoveryConstant.SPRING_APPLICATION_NAME + "}")
    private String serviceName;

    @Autowired(required = false)
    private List<Parameter> swaggerHeaderParameters;

    @Autowired(required = false)
    private List<SecurityScheme> swaggerSecuritySchemes;

    @Autowired(required = false)
    private List<SecurityContext> swaggerSecurityContexts;

    @Bean("discoveryDocket")
    public Docket discoveryDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(discoveryGroup)
                .apiInfo(apiInfo(discoveryDescription,
                        discoveryVersion,
                        discoveryLicenseName,
                        discoveryLicenseUrl,
                        discoveryContactName,
                        discoveryContactUrl,
                        discoveryContactEmail,
                        discoveryTermsOfServiceUrl))
                .select()
                .apis(SwaggerConfiguration.basePackage(discoveryPackages)) // 扫描该包下的所有需要在Swagger中展示的API，@ApiIgnore注解标注的除外
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(swaggerHeaderParameters)
                .securitySchemes(swaggerSecuritySchemes)
                .securityContexts(swaggerSecurityContexts != null ? swaggerSecurityContexts : Collections.emptyList());
    }

    @Bean("serviceDocket")
    @ConditionalOnProperty(name = DiscoverySwaggerConstant.SWAGGER_SERVICE_GROUP)
    public Docket serviceDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(serviceGroup)
                .apiInfo(apiInfo(serviceDescription,
                        serviceVersion,
                        serviceLicenseName,
                        serviceLicenseUrl,
                        serviceContactName,
                        serviceContactUrl,
                        serviceContactEmail,
                        serviceTermsOfServiceUrl))
                .select()
                .apis(SwaggerConfiguration.basePackage(servicePackages)) // 扫描该包下的所有需要在Swagger中展示的API，@ApiIgnore注解标注的除外
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(swaggerHeaderParameters)
                .securitySchemes(swaggerSecuritySchemes)
                .securityContexts(swaggerSecurityContexts != null ? swaggerSecurityContexts : Collections.emptyList());
    }

    private ApiInfo apiInfo(String description,
            String version,
            String licenseName,
            String licenseUrl,
            String contactName,
            String contactUrl,
            String contactEmail,
            String termsOfServiceUrl) {
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