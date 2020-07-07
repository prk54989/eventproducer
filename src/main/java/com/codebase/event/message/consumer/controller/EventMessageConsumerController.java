package com.codebase.event.message.consumer.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.codebase.event.message.consumer.model.EventRequest;
import com.codebase.event.message.consumer.model.EventResponse;
import com.codebase.event.message.consumer.util.Utility;


@RestController
public class EventMessageConsumerController {

	private static final Logger logger = LoggerFactory.getLogger(EventMessageConsumerController.class);
	
	private DiscoveryClient discoveryClient;
	
	private Utility  utility;
	
	private LoadBalancerClient loadBalancer;
	
	@Autowired
	public EventMessageConsumerController(
			DiscoveryClient discoveryClient,
			Utility  utility,
			LoadBalancerClient loadBalancer
			) {
		this.discoveryClient = discoveryClient;
		this.utility = utility;
		this.loadBalancer = loadBalancer;
	}

	@RequestMapping(value = "/event-listner/{name}",
	produces = {"application/json"},
	consumes = {"application/json"},
	method = RequestMethod.POST)
	public void handleListener(@PathVariable String name) throws RestClientException, IOException {
//		List<ServiceInstance> instances=discoveryClient.getInstances("event-producer");
//		ServiceInstance serviceInstance=instances.get(0);
//		String baseUrl=serviceInstance.getUri().toString();
		
		//Load balanc - Ribbon Start
		ServiceInstance serviceInstance=loadBalancer.choose("event-producer");
		logger.info(" EventMessageConsumerController : handleListener {}",serviceInstance.getUri());
		String baseUrl=serviceInstance.getUri().toString();
		//Load balanc - Ribbon End
		
		baseUrl=baseUrl+"/publish-events";
		System.out.println("baseUrl  > "+baseUrl);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<EventResponse> response = null;
		try {
			
			EventRequest eventRequest = utility.setEventRequest();
			HttpEntity<EventRequest> requestEntity = new HttpEntity<EventRequest>(eventRequest, getHeaders());
			 response = restTemplate.exchange(baseUrl, HttpMethod.POST, requestEntity, EventResponse.class);
			 
		} catch (Exception ex) {
			System.out.println(ex);
		}
		System.out.println(response.getBody());
	}


	private static HttpHeaders getHeaders() throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		return headers;
	}
	
	
	
	
    
}
