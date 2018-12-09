package com.weatherforecast.api.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import com.weatherforecast.api.exception.DataNotFoundException;
import com.weatherforecast.api.exception.UnauthorisedException;
import com.weatherforecast.api.exception.WeatherForecastException;

public interface IApiClient {
	
	public ResponseEntity<String> invokeGetResource(final String uri,final HttpHeaders httpHeaders) throws UnauthorisedException, DataNotFoundException, WeatherForecastException ;

}
