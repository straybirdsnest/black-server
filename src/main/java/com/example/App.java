package com.example;

import com.example.config.DataConfig;
import com.example.config.MvcConfig;
import com.example.config.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({MvcConfig.class, SecurityConfig.class, DataConfig.class})
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

}
