package com.backfcdev.companiescrud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class CompaniesCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompaniesCrudApplication.class, args);
	}

}
