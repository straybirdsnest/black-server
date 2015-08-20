package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.exmaple.config.MvcConfig;
import com.exmaple.config.SecurityConfig;

@SpringBootApplication
@Import({ MvcConfig.class, SecurityConfig.class })
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
