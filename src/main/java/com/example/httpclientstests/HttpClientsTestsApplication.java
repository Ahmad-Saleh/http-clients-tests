package com.example.httpclientstests;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@EnableFeignClients
@SpringBootApplication
public class HttpClientsTestsApplication {

	public static void main(String[] args) {
		SpringApplication.run(HttpClientsTestsApplication.class, args);
	}

}
