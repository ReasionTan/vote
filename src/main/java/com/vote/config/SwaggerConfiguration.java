package com.vote.config;

import com.vote.basic.constant.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
@Profile(Constants.SPRING_PROFILE_SWAGGER)
public class SwaggerConfiguration {

    private final Logger log = LoggerFactory.getLogger(SwaggerConfiguration.class);

    @Autowired
    private ProjectProperties projectProperties;

    /**
     * aapi管理接口
     */
    public static final String API = "/(api)/.*";
    /**
     * 系统管理后台接口
     */
    public static final String ADMIN = "/(admin)/.*";

    /**
     * Swagger Springfox configuration.
     *
     * @return the Swagger Springfox configuration
     */
    @Bean
    public Docket swaggerSpringFoxDocket() {
        log.info("启动 Swagger");
        StopWatch watch = new StopWatch();
        watch.start();
        Contact contact = new Contact(
                projectProperties.getSwagger().getContactName(),
                projectProperties.getSwagger().getContactUrl(),
                projectProperties.getSwagger().getContactEmail());
        ApiInfo apiInfo = new ApiInfo(
                projectProperties.getSwagger().getTitle(),
                projectProperties.getSwagger().getDescription(),
                projectProperties.getSwagger().getVersion(),
                projectProperties.getSwagger().getTermsOfServiceUrl(),
                contact,
                projectProperties.getSwagger().getLicense(),
                projectProperties.getSwagger().getLicenseUrl());

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .groupName("API接口")
                .apiInfo(apiInfo)
                .forCodeGeneration(true)
                .genericModelSubstitutes(ResponseEntity.class)
                .globalOperationParameters(globalParamters())
                .ignoredParameterTypes(java.sql.Date.class, Principal.class)
                .directModelSubstitute(java.time.LocalDate.class, java.sql.Date.class)
                .directModelSubstitute(java.time.ZonedDateTime.class, Date.class)
                .directModelSubstitute(java.time.LocalDateTime.class, Date.class)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.vote.controller.app"))
                .paths(regex(API))
                .build();
        watch.stop();
        log.info("启动 Swagger 使用了 {} ms", watch.getTotalTimeMillis());
        return docket;
    }

    @Bean
    public Docket createAdminRestApi() {
        Contact contact = new Contact(
                projectProperties.getSwagger().getContactName(),
                projectProperties.getSwagger().getContactUrl(),
                projectProperties.getSwagger().getContactEmail());
        ApiInfo apiInfo = new ApiInfo(
                "系统管理后台管理接口",
                projectProperties.getSwagger().getDescription(),
                projectProperties.getSwagger().getVersion(),
                projectProperties.getSwagger().getTermsOfServiceUrl(),
                contact,
                projectProperties.getSwagger().getLicense(),
                projectProperties.getSwagger().getLicenseUrl());
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("系统管理后台接口")
                .apiInfo(apiInfo)
                .globalOperationParameters(globalParamters())
                .select()
                //api接口包扫描路径
                .apis(RequestHandlerSelectors.basePackage("com.vote.controller.system"))
                //可以根据url路径设置哪些请求加入文档，忽略哪些请求
                .paths(PathSelectors.regex(ADMIN))
                .build();
    }


    private List<Parameter> globalParamters() {
        List<Parameter> parameters = new ArrayList<>();
        Parameter version = new ParameterBuilder()
                .name("Version")
                .description("客户端版本号")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .defaultValue("v")
                .build();
        Parameter platform = new ParameterBuilder()
                .name("Platform")
                .description("平台 苹果：ios 安卓：android pc端 web 管理后台 sys")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .defaultValue("ios")
                .build();
        parameters.add(version);
        parameters.add(platform);
        return parameters;
    }
}
