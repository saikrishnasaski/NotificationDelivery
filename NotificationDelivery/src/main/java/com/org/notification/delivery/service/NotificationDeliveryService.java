package com.org.notification.delivery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationDeliveryService {

	@Autowired
	private RestTemplate restTemplate;
	
	public String sendNotification() {
		return null;
	}
}
