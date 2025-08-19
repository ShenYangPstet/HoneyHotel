package com.photonstudio;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import springfox.documentation.oas.annotations.EnableOpenApi;



@SpringBootApplication
@EnableWebSocket
@EnableOpenApi//Swagger3
@MapperScan("com.photonstudio.mapper")
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
