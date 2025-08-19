package com.photonstudio.config;

import java.nio.charset.Charset;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {
	@Bean
	public RestTemplate restTemplate() {
		SimpleClientHttpRequestFactory factory=new SimpleClientHttpRequestFactory();
		factory.setConnectTimeout(10000);
		factory.setReadTimeout(10000);
		
		RestTemplate restTemplate = new RestTemplate(factory);
		
		List<HttpMessageConverter<?>> httpMessageConverters = restTemplate.getMessageConverters();
		httpMessageConverters.stream().forEach(httpMessageConverter -> {
			if(httpMessageConverter instanceof StringHttpMessageConverter){
				StringHttpMessageConverter messageConverter = (StringHttpMessageConverter) httpMessageConverter;
				messageConverter.setDefaultCharset(Charset.forName("UTF-8"));
			}
		});
	    
	    return restTemplate;
	}

}
