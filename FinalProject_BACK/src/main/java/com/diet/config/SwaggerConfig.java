package com.diet.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//http://localhost:9999/swagger-ui/index.html

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@SuppressWarnings("deprecation")
	@Bean
	public Docket postsApi() {
		ApiInfo apiInfo = new ApiInfo("REST API for Diet Service", "Diet App을 위한 REST API입니다.", "1.0", "localhost:8080/", "", "©두희승휘", "");
		
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo)
				.groupName("dietApp")
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.diet.controller"))
				.paths(PathSelectors.ant("/**"))
				.build()
				.securitySchemes(Collections.singletonList(apiKey()))
				.securityContexts(Collections.singletonList(securityContext()));
	}

	// jwt apikey 입력할 수 있도록 추가하기
	private ApiKey apiKey() {
		return new ApiKey("JWT", HttpHeaders.AUTHORIZATION, "header");
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder()
				.securityReferences(Collections.singletonList(defaultAuth()))
				.build();
	}

	private SecurityReference defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = {authorizationScope};
		return new SecurityReference("JWT", authorizationScopes);
	}
}
