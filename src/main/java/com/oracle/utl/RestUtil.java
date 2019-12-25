package com.oracle.utl;

import java.time.Instant;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;








public class RestUtil {



	   

	RestTemplate restTemplate=new RestTemplate();



	public ResponseEntity<String> restGetAuth(String url, String identityDomainId) {

		HttpEntity<String> entity = new HttpEntity<>("parameters", new HttpHeaders());
		return invokeRestTemplete(url, HttpMethod.GET, entity);
	}

	public ResponseEntity<String> restGet(String url) {

		HttpEntity<String> entity = new HttpEntity<>(new HttpHeaders());
		return invokeRestTemplete(url, HttpMethod.GET, entity);
	}


	
	public ResponseEntity<String> restPut(String url) {

		HttpEntity<String> entity = new HttpEntity<>(new HttpHeaders());
		return invokeRestTemplete(url, HttpMethod.PUT, entity);
	}
	
	public ResponseEntity<String> restPut(String url, Object body) {


		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.add("Accept", "text/json");
		HttpEntity<Object> entity = new HttpEntity<>(body,headers);
		return invokeRestTemplete(url, HttpMethod.PUT, entity);
	}
	public ResponseEntity<String> restPost(String url, Object body) {

		//restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		

		
		HttpEntity<Object> entity = new HttpEntity<>(body, new HttpHeaders());
		return invokeRestTemplete(url, HttpMethod.POST, entity);
	}


	
	public ResponseEntity<String> restDelete(String url, Map<String, String> allRequestParams) {

		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		HttpEntity<Map<String, String>> entity = new HttpEntity<>(allRequestParams, new HttpHeaders());
		return invokeRestTemplete(url, HttpMethod.DELETE, entity);

	}

	private <T> ResponseEntity<String> invokeRestTemplete(String url, HttpMethod method, HttpEntity<T> entity) {

		long startTime = Instant.now().toEpochMilli();


		ResponseEntity<String> response = null;
		try {

			response = restTemplate.exchange(url, method, entity, String.class);
		
		} catch (HttpClientErrorException e) {
			
			return handleException(e);
		} catch (HttpServerErrorException e) {

			return handleException(e);
		} finally {
			long endTime = Instant.now().toEpochMilli();
			
		}

		return response;

	}

	public ResponseEntity<String> handleException(HttpClientErrorException e) {

		String body = e.getResponseBodyAsString();
		if (StringUtils.isEmpty(body)) {
			body = e.getStatusText();
		}

		HttpHeaders headers = e.getResponseHeaders();
		HttpStatus statusCode = e.getStatusCode();

		ResponseEntity<String> response = new ResponseEntity<>(body, headers, statusCode);
		return response;
	}

	public ResponseEntity<String> handleException(HttpServerErrorException e) {

		String body = e.getResponseBodyAsString();
		if (StringUtils.isEmpty(body)) {
			body = e.getStatusText();
		}

		HttpHeaders headers = e.getResponseHeaders();
		HttpStatus statusCode = e.getStatusCode();

		ResponseEntity<String> response = new ResponseEntity<>(body, headers, statusCode);
		return response;
	}

	public ResponseEntity<String> restGetWithCookieAuth(String url, List<String> cookieList) {

		HttpHeaders headers = new HttpHeaders();
		headers.put(HttpHeaders.COOKIE, cookieList);
		HttpEntity<String> entity = new HttpEntity<>(headers);
		return invokeRestTemplete(url, HttpMethod.GET, entity);
	}

	public ResponseEntity<String> restPutWithCookieAuth(String url, List<String> cookieList, Object body) {

		HttpHeaders headers = new HttpHeaders();
		headers.put(HttpHeaders.COOKIE, cookieList);
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		HttpEntity<Object> entity = new HttpEntity<>(body, headers);
		return invokeRestTemplete(url, HttpMethod.PUT, entity);
	}



}
