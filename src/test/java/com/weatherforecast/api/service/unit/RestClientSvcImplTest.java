package com.weatherforecast.api.service.unit;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.weatherforecast.api.exception.DataNotFoundException;
import com.weatherforecast.api.exception.UnauthorisedException;
import com.weatherforecast.api.exception.WeatherForecastException;
import com.weatherforecast.api.service.RestClientSvcImpl;

@RunWith(MockitoJUnitRunner.class)
public class RestClientSvcImplTest {
	
	@Mock
	private ResponseEntity<String> respEntity;
	
	@Mock
	private RestTemplate restTemplate;
	
	@Mock
	HttpClientErrorException httpClientErrorException;

	@InjectMocks
	RestClientSvcImpl restClientSvc;
	
	@Test
	public void invokeGetResourceTest_returns200OkAndSuccessJsonResp() throws UnauthorisedException, DataNotFoundException, WeatherForecastException, JSONException {
		
		when(respEntity.getStatusCode()).thenReturn(HttpStatus.OK);
		
		when(restTemplate.exchange(
	            ArgumentMatchers.anyString(),
	            ArgumentMatchers.any(HttpMethod.class),
	            ArgumentMatchers.any(),
	            ArgumentMatchers.<Class<String>>any()))
	         .thenReturn(respEntity);
		
		HttpHeaders httpHeaders = getHttpHeaders();
		ResponseEntity<String> respEntity = restClientSvc.invokeGetResource("http://api.openweathermap.org/data/2.5/forecast?q=London,us&mode=json", httpHeaders);
		assertSame(HttpStatus.OK, respEntity.getStatusCode());
	}
	
	@Test(expected=UnauthorisedException.class)
	public void invokeGetResourceTest_throwsUnauthorisedException() throws UnauthorisedException, DataNotFoundException, WeatherForecastException, JSONException {
		
		when(httpClientErrorException.getStatusCode()).thenReturn(HttpStatus.UNAUTHORIZED);
		
		when(restTemplate.exchange(
	            ArgumentMatchers.anyString(),
	            ArgumentMatchers.any(HttpMethod.class),
	            ArgumentMatchers.any(),
	            ArgumentMatchers.<Class<String>>any()))
	         .thenThrow(httpClientErrorException);
		
		HttpHeaders httpHeaders = getHttpHeaders();
		restClientSvc.invokeGetResource("http://api.openweathermap.org/data/2.5/forecast?q=London,us&mode=json", httpHeaders);
	
	}
	
	@Test(expected=DataNotFoundException.class)
	public void invokeGetResourceTest_throwsResourceNtFoundException() throws UnauthorisedException, DataNotFoundException, WeatherForecastException, JSONException {
		
		when(httpClientErrorException.getStatusCode()).thenReturn(HttpStatus.NOT_FOUND);
		
		when(restTemplate.exchange(
	            ArgumentMatchers.anyString(),
	            ArgumentMatchers.any(HttpMethod.class),
	            ArgumentMatchers.any(),
	            ArgumentMatchers.<Class<String>>any()))
	         .thenThrow(httpClientErrorException);
		
		HttpHeaders httpHeaders = getHttpHeaders();
		restClientSvc.invokeGetResource("http://api.openweathermap.org/data/2.5/forecast?q=London,us&mode=json", httpHeaders);
	
	}
	
	@Test(expected=WeatherForecastException.class)
	public void invokeGetResourceTest_throwsWeatherForecastException() throws UnauthorisedException, DataNotFoundException, WeatherForecastException, JSONException {
		
		when(httpClientErrorException.getStatusCode()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR);
		
		when(restTemplate.exchange(
	            ArgumentMatchers.anyString(),
	            ArgumentMatchers.any(HttpMethod.class),
	            ArgumentMatchers.any(),
	            ArgumentMatchers.<Class<String>>any()))
	         .thenThrow(httpClientErrorException);
		
		HttpHeaders httpHeaders = getHttpHeaders();
		restClientSvc.invokeGetResource("http://api.openweathermap.org/data/2.5/forecast?q=London,us&mode=json", httpHeaders);
	
	}
	
	/**
	 * This method populates the http headers which are required for invoking the
	 * rest api of weather forecast.
	 * 
	 * @author  lalitkumar kulkarni
	 * @since   08-12-2018
	 * @version 1.0
	 * @return HttpHeaders Returns the http headers with x api key and media types.
	 */
	private HttpHeaders getHttpHeaders() {
		
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.add("x-api-key","5ebcf5eae2f0fe2924bf6ea7d3730819");
		return httpHeaders;
	}
}
