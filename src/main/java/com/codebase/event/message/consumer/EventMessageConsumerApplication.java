package com.codebase.event.message.consumer;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.client.RestClientException;


@SpringBootApplication
@EnableEurekaClient
public class EventMessageConsumerApplication {

	public static void main(String[] args) throws RestClientException, IOException {
		SpringApplication.run(EventMessageConsumerApplication.class, args);
	}
}
