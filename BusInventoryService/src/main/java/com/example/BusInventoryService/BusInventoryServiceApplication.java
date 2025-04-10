package com.example.BusInventoryService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BusInventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BusInventoryServiceApplication.class, args);
	}

}
