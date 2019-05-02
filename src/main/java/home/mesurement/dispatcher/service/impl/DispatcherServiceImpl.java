package home.mesurement.dispatcher.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import home.mesurement.dispatcher.config.ApplicationConfig;
import home.mesurement.dispatcher.service.DispatcherService;

@Service
public class DispatcherServiceImpl implements DispatcherService {
	
	private Logger log = LoggerFactory.getLogger(DispatcherServiceImpl.class.getName());
	
	private final RestTemplate restTemplate;
	private final ApplicationConfig applicationConfig;
	
	public DispatcherServiceImpl(ApplicationConfig applicationConfig, RestTemplateBuilder builder) {
		this.restTemplate = builder.build();
		this.applicationConfig = applicationConfig;
	}
	
	@Override
	public String notifyTemperature(float value) {
		log.info(String.format("About to notify temperature mesurement [%s]", value));
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(applicationConfig.getCollector().getEndpoint() + "/temperature").queryParam("value", Float.toString(value));
		try {
			log.info(String.format("Calling URL: [%s]", builder.toUriString()));
			HttpEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null, String.class);
			log.info(String.format("Received response: %s", response.getBody().toString()));
			return response.getBody().toString();
		}catch(RestClientException e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}
	
	@Override
	public String notifyHumidity(float value) {
		log.info(String.format("About to notify humidity mesurement [%s]", value));
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(applicationConfig.getCollector().getEndpoint() + "/humidity").queryParam("value", Float.toString(value));
		try {
			log.info(String.format("Calling URL: [%s]", builder.toUriString()));
			HttpEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null, String.class);
			log.info(String.format("Received response: %s", response.getBody().toString()));
			return response.getBody().toString();
		}catch(RestClientException e) {
			log.error(e.getMessage(), e);
			return null;
		}
		
	}
	
	@Override
	public String notifyPressure(float value) {
		log.info(String.format("About to notify pressure mesurement [%s]", value));
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(applicationConfig.getCollector().getEndpoint() + "/pressure").queryParam("value", Float.toString(value));
		try {
			log.info(String.format("Calling URL: [%s]", builder.toUriString()));
			HttpEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null, String.class);
			log.info(String.format("Received response: %s", response.getBody().toString()));
			return response.getBody().toString();
		}catch(RestClientException e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}
}
