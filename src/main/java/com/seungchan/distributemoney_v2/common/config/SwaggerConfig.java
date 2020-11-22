package com.seungchan.distributemoney_v2.common.config;

import com.seungchan.distributemoney_v2.common.BaseBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends BaseBean {

    private String version;
    private String title;

    @Bean
    public Docket apiV1() throws UnknownHostException {
        version = "V1";
        title = "머니뿌리기 " + version;

        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .groupName(version)
                .select()
                .apis(RequestHandlerSelectors/*.any()*/.basePackage("com.seungchan.distributemoney_v2"))
//                .paths(PathSelectors.ant("/v2/api/**"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(new ApiInfoBuilder()
                        .title(title)
                        .description("Distribute Money API Docs")
                        .version(version)
                        .termsOfServiceUrl(InetAddress.getLocalHost().getHostAddress())
                        .contact(new Contact("baegseungchan", "https://github.com/mokdonjr/", "mokdonjr@gmail.com"))
                        .extensions(new ArrayList<>())
                        .build());
    }
}
