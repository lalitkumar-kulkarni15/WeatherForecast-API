package com.weatherforecast.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.weatherforecast.api.exception.DataNotFoundException;
import com.weatherforecast.api.exception.UnauthorisedException;
import com.weatherforecast.api.exception.WeatherForecastException;

@Service(value = "restClientSvcImpl")
public class RestClientSvcImpl implements IApiClient {

	@Autowired
	private RestTemplate restTemplate;

	private static final String PARAM = "parameters";

	public ResponseEntity<String> invokeGetResource(final String uri, final HttpHeaders httpHeaders) throws UnauthorisedException, DataNotFoundException, WeatherForecastException {

		try {
			HttpEntity<String> entity = new HttpEntity<>(PARAM, httpHeaders);
			return restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
		} catch (HttpClientErrorException exception) {
			
			HttpStatus httpStatus = exception.getStatusCode();
			
			if(HttpStatus.UNAUTHORIZED.equals(httpStatus)) {
				throw new UnauthorisedException("Unauthorised exception");
			} else if (HttpStatus.NOT_FOUND.equals(httpStatus)) {
				throw new DataNotFoundException("We couldnt find the data of the city you are looking for.");
			} else {
				throw new WeatherForecastException("Something went wrong with invoking the weather API.");
			}
			
		}
		
	}

}
