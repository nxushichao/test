package com.gkdz.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wu
 */
@Configuration
@EnableSwagger2WebMvc
public class SwaggerConfig {

    @Value("${knife4j.token}")
    private String token;

    //  private final OpenApiExtensionResolver openApiExtensionResolver;
    //
    //  @Autowired
    //  public SwaggerConfig(OpenApiExtensionResolver openApiExtensionResolver) {
    //    this.openApiExtensionResolver = openApiExtensionResolver;
    //  }

    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        /*添加接口请求头参数配置 没有的话 可以忽略*/
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        tokenPar.name("token").description("令牌").defaultValue(token).modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());

        String groupName = "智能工具车";
        Docket docket = new Docket(DocumentationType.SWAGGER_2).host("https://www.baidu.com").apiInfo(apiInfo()).groupName(groupName).select().apis(RequestHandlerSelectors.basePackage("com.gkdz.server.modules")).paths(PathSelectors.any()).build().globalOperationParameters(pars);
        //赋予插件体系
        //            .extensions(openApiExtensionResolver.buildExtensions(groupName));
        return docket;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("智能工具车接口文档").description("智能工具车接口文档").termsOfServiceUrl("http://162.14.117.93:10020/").contact(new Contact("Java开发组", "", "")).version("1.0.0").build();
    }
}
