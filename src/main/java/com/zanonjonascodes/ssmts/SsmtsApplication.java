package com.zanonjonascodes.ssmts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
// @ComponentScan( basePackages = "com.zanonjonascodes.ssmts")
// @EntityScan(basePackages = "com.zanonjonascodes.ssmts")
@EnableWebSecurity
public class SsmtsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SsmtsApplication.class, args);
	}

}
