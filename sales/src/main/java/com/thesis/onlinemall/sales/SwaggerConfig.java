package com.thesis.onlinemall.sales;

import io.swagger.annotations.ApiOperation;
import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
@Configuration
@EnableSwagger2 //启用Swagger2
public class SwaggerConfig {
  @Bean
  public Docket customDocket() {

    // 配置全局参数返回状态
//    java.util.List<ResponseMessage> resMsgList = Arrays.asList(
//        new ResponseMessageBuilder().code(200).message("成功！").build(),
//        new ResponseMessageBuilder().code(-1).message("失败！").build(),
//        new ResponseMessageBuilder().code(401).message("参数校验错误！").build(),
//        new ResponseMessageBuilder().code(403).message("没有权限操作，请后台添加相应权限！").build(),
//        new ResponseMessageBuilder().code(500).message("服务器内部异常，请稍后重试！").build(),
//        new ResponseMessageBuilder().code(501).message("请登录！").build());

    return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
        .apis(RequestHandlerSelectors.basePackage("com.thesis.onlinemall"))
        .paths(PathSelectors.any())
        .build();
//        .globalResponseMessage(RequestMethod.GET, resMsgList)
//        .globalResponseMessage(RequestMethod.POST, resMsgList)
//        .globalResponseMessage(RequestMethod.PUT, resMsgList)
//        .globalResponseMessage(RequestMethod.DELETE, resMsgList);
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("Hamster接口文档")
        .description("接口文档说明")
        .version("1.0.0")
        .build();
  }

}
