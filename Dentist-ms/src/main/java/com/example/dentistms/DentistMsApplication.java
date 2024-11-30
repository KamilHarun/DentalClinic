package com.example.dentistms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class DentistMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DentistMsApplication.class, args);
	}

}
