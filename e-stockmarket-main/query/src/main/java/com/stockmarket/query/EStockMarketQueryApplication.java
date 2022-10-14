package com.stockmarket.query;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EStockMarketQueryApplication {

	public static void main(String[] args) {
	SpringApplication.run(EStockMarketQueryApplication.class, args);

	}

}
