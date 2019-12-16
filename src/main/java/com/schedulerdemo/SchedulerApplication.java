package com.schedulerdemo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Mamatha Dec 12, 2019 09:55:23 AM SchedulerApplication.java
 */
@EnableScheduling
@EnableSwagger2
@EnableJpaRepositories("com.schedulerdemo.repository")
@EntityScan(basePackages = {"com.schedulerdemo.model"})
@SpringBootApplication
public class SchedulerApplication {

  public static void main(String[] args) {
    SpringApplication.run(SchedulerApplication.class, args);
  }

  @Bean
  public Docket api() {
    Docket docket = new Docket(DocumentationType.SWAGGER_2)
        .groupName("V1")
        .apiInfo(apiInfo())
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.schedulerdemo.controller"))
        .paths(PathSelectors.regex("/((?!error).)*"))
        .build();
    docket.useDefaultResponseMessages(false)
        .directModelSubstitute(Object.class, java.lang.Void.class);
    return docket;
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("Task Scheduler Rest API")
        .description("REST API for Task Scheduler")
        .version("1.0.0")
        .build();

  }
}