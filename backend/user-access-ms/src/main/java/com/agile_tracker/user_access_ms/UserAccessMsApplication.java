package com.agile_tracker.user_access_ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication()
@EnableFeignClients
@EnableAsync
public class UserAccessMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserAccessMsApplication.class, args);
	}

}
