package com.arm.atm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
public class AtmBackApplication {
	public static void main(String[] args) {
		SpringApplication.run(AtmBackApplication.class, args);
	}
}
