package com.weatherforecast.api.service.integ;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.weatherforecast.api.WeathrFrcstAppMain;
import com.weatherforecast.api.exception.DataNotFoundException;
import com.weatherforecast.api.exception.UnauthorisedException;
import com.weatherforecast.api.exception.WeatherForecastException;
import com.weatherforecast.api.service.IApiClient;

@RunWith(SpringRunner.class)
@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,classes= {WeathrFrcstAppMain.class})
@TestPropertySource(locations="classpath:test.properties")
public class RestClientSvcImplIntTest {
	
	@Autowired
	private IApiClient apiClient;
	
	@Value("${application.test.apikey}")
	private String apiKey;
	
	@Value("${application.test.weatherForecastURLLondon}")
	private String weatherForecastURLLondon;
	
	@Test
	public void invokeGetResource_ReturnWeathrParamsVerifiesHttpOkTest() throws UnauthorisedException, DataNotFoundException, WeatherForecastException  {
		
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.add("x-api-key",apiKey);
		
		final Optional<ResponseEntity<String>> apiResponse = Optional.of(apiClient.invokeGetResource(weatherForecastURLLondon,httpHeaders));
		if(apiResponse.isPresent()) {
			final HttpStatus apiResponseStatusCode = apiResponse.get().getStatusCode();
			assertEquals(HttpStatus.OK, apiResponseStatusCode);
		}
		
	}
	
	@Test
	public void invokeGetResource_ReturnWeathrParamsVerifiesNonNullRespTest() throws UnauthorisedException, DataNotFoundException, WeatherForecastException  {
		
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.add("x-api-key",apiKey);
		
		final Optional<ResponseEntity<String>> apiResponse = Optional.of(apiClient.invokeGetResource(weatherForecastURLLondon,httpHeaders));
		if(apiResponse.isPresent()) {
			final String apiResponseJson = apiResponse.get().getBody();
			assertNotNull(apiResponseJson);
		}
		
	}

}
