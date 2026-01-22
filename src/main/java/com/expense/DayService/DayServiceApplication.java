package com.expense.DayService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EntityScan("com.expense")
@EnableJpaRepositories("com.expense")
@EnableFeignClients(basePackages = "com.expense.DayService.client") // 👈 Enables Feign in this service
public class DayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DayServiceApplication.class, args);
	}
}
