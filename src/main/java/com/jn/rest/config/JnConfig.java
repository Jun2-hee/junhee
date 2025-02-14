package com.jn.rest.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.extern.slf4j.Slf4j;

//https://e-7-e.tistory.com/105 (철희쌤 블로그 확인)
@Slf4j
@Configuration   //설정파일임을 나타내는 어노테이션
public class JnConfig implements WebMvcConfigurer{

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		log.info("요기가 실행되었는지 check?");
		/*
		    사용자  /jnweb/aaa/merong.jpg    => 실제(물리적경로)d:/jnUp/aaa/merong.jpg
		    
		 */
		registry.addResourceHandler("/jnweb/**")             // 웹 접근 경로 
		        .addResourceLocations("file:///d:/jnUp/");  // 서버내 실제 경로(파일이 실제로 저장되는 경로)
	}
}
