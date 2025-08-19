package com.photonstudio.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyConfiguration implements WebMvcConfigurer {
	/*
	 * @Bean public WebMvcConfigurer corsConfigurer() { return new
	 * WebMvcConfigurer() {
	 * 
	 * @Override public void addCorsMappings(CorsRegistry registry) {
	 * registry.addMapping("/**") .allowedHeaders("*") .allowedOrigins("*")
	 * .allowCredentials(true) .allowedMethods("GET", "POST", "DELETE",
	 * "PUT","PATCH") .maxAge(3600); } }; }
	 */
//	private CorsConfiguration corsConfig() {
//	    CorsConfiguration corsConfiguration = new CorsConfiguration();
//	   // 请求常用的三种配置，*代表允许所有，当时你也可以自定义属性（比如header只能带什么，只能是post方式等等）
//
//	    corsConfiguration.addAllowedOrigin("*");
//	    corsConfiguration.addAllowedHeader("*");
//	    corsConfiguration.addAllowedMethod("*");
//	    corsConfiguration.setAllowCredentials(true);
//	    corsConfiguration.setMaxAge(3600L);
//	    return corsConfiguration;
//	}
//	@Bean
//	public CorsFilter corsFilter() {
//	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//	    source.registerCorsConfiguration("/**", corsConfig());
//	    return new CorsFilter(source);
//	}


	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOriginPatterns("*")
				.allowedMethods("GET","HEAD","POST","PUT","DELETE","OPTIONS")
				.allowCredentials(true)
				.maxAge(3600)
				.allowedHeaders("*");
	}
}
