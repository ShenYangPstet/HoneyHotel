package com.photonstudio.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.HashSet;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {


    @Bean
    public Docket createRestApi(){
        HashSet<String> strings = new HashSet<>();

        //设置返回的值是JSON格式
        strings.add("application/json");
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .produces(strings)
                .groupName("后台对接文档")//商品组
                .select()
                //扫描扫描包路径 controller所在的
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //设置标题
                .title("中盛起元")

                .description("后台管理系统在线API接口文档")
                //访问地址
                .termsOfServiceUrl("http://192.168.101.24:8001/")
                //版本
                .version("2.0")
                //构建
                .build();
    }
}
