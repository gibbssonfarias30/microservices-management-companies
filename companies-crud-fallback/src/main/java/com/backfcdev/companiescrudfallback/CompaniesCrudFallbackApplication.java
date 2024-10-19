package com.backfcdev.companiescrudfallback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class CompaniesCrudFallbackApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompaniesCrudFallbackApplication.class, args);
	}

}
