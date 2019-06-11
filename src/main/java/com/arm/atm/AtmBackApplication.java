package com.arm.atm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.Async;

@DependsOn
@Async
public class AtmBackApplication {

	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(AtmBackApplication.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(AtmBackApplication.class, args);
	}
}
