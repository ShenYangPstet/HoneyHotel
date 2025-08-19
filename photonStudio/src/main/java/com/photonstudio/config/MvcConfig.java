package com.photonstudio.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.photonstudio.interceptor.UserInterceptor;
import com.photonstudio.pojo.FileUploadProperteis;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer{
	@Autowired
	private FileUploadProperteis fileUploadProperteis;
	@Autowired
	private UserInterceptor userInterceptor;
	 public void addResourceHandlers(ResourceHandlerRegistry registry) {
	        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
	        registry.addResourceHandler(fileUploadProperteis.getStaticAccessPath()+"**").
	        addResourceLocations("file:"+fileUploadProperteis.getUploadFolder());
		 /*registry.addResourceHandler("/assets/**")
				 .addResourceLocations("classpath:/assets/");*/
		 registry.addResourceHandler("/**").addResourceLocations("classpath:/templates/");
		 registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
		 registry.addResourceHandler("/favicon.ico").addResourceLocations("classpath:/META-INF/resources/");
		 registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	    }
	 @Override
	public void addInterceptors(InterceptorRegistry registry) {
		//registry.addInterceptor(userInterceptor).addPathPatterns("/zsqy/**")
		//.excludePathPatterns("/zsqy/licenseBySP","/zsqy/importlicense","/zsqy/licenseByUser","/zsqy/other/**","/zsqy/licenseDate");
	}

}