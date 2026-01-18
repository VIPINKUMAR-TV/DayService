package com.expense.DayService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.expense.DayService.client") // 👈 Enables Feign in this service
public class DayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DayServiceApplication.class, args);
	}
}
