package com.example.BusInventoryService;

import com.example.BusInventoryService.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BusInventoryServiceApplication {

	@Autowired
	private InventoryService inventoryService;
	public static void main(String[] args) {
		SpringApplication.run(BusInventoryServiceApplication.class, args);
	}
}
