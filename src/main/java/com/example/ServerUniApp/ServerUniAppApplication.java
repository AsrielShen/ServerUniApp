package com.example.ServerUniApp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.ServerUniApp.mapper")
public class ServerUniAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerUniAppApplication.class, args);
	}
}
