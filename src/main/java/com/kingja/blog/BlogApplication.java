package com.kingja.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@EnableJpaAuditing
@RestController
@SpringBootApplication
public class BlogApplication {

	@RequestMapping("/")
	public String index(){
		return "Hello Spring Boot";
	}
	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}
}
