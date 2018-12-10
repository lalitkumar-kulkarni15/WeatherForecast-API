package com.weatherforecast.api.service.integ;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.weatherforecast.api.WeathrFrcstAppMain;
import com.weatherforecast.api.exception.DataNotFoundException;
import com.weatherforecast.api.exception.UnauthorisedException;
import com.weatherforecast.api.exception.WeatherForecastException;
import com.weatherforecast.api.model.WeatherForecastResp;
import com.weatherforecast.api.service.IApiClient;
import com.weatherforecast.api.service.IDataProcess;

@RunWith(SpringRunner.class)
@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,classes= {WeathrFrcstAppMain.class})
@TestPropertySource(locations="classpath:test.properties")
public class WeathrDataProcessSvcImplTest {
	
	@Autowired
	IDataProcess dataProcessSvc;
	
	@Autowired
	private IApiClient apiClient;
	
	@Value("${application.test.apikey}")
	private String apiKey;
	
	@Value("${application.test.weatherForecastURLLondon}")
	private String weatherForecastURLLondon;
	
	@Test(expected=WeatherForecastException.class)
	public void processWeathrDataTotAvgTest_throwsWeatherForecastExWhenpassedNull_Negative() throws IOException, WeatherForecastException {
		dataProcessSvc.processWeathrDataTotAvg(null);
	}
	
	@Test(expected=WeatherForecastException.class)
	public void processWeathrDataTotAvgTest_Negative() throws IOException, WeatherForecastException {
		dataProcessSvc.processWeathrDataTotAvg("");
	}
	
	@Test
	public void invokeGetResource_ReturnWeathrParamsVerifiesNonNullRespTest() throws UnauthorisedException, DataNotFoundException, WeatherForecastException, IOException  {
		
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.add("x-api-key",apiKey);
		
		final Optional<ResponseEntity<String>> apiResponse = Optional.of(apiClient.invokeGetResource(weatherForecastURLLondon,httpHeaders));
		if(apiResponse.isPresent()) {
			final String apiResponseJson = apiResponse.get().getBody();
			WeatherForecastResp resp = dataProcessSvc.processWeathrDataTotAvg(apiResponseJson);
			assertNotNull(resp);
		}
		
	}
	
	@Test
	public void invokeGetResource_ReturnWeathrParamsVerifiesNonNullWeathrFrecstObjRespTest() throws UnauthorisedException, DataNotFoundException, WeatherForecastException, IOException  {
		
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.add("x-api-key",apiKey);
		
		final Optional<ResponseEntity<String>> apiResponse = Optional.of(apiClient.invokeGetResource(weatherForecastURLLondon,httpHeaders));
		if(apiResponse.isPresent()) {
			final String apiResponseJson = apiResponse.get().getBody();
			WeatherForecastResp resp = dataProcessSvc.processWeathrDataTotAvg(apiResponseJson);
			assertNotNull(resp.getWeatherForecast());
		}
		
	}
	
	@Test
	public void invokeGetResource_ReturnWeathrParamsVerifiesNonNullPressureRespTest() throws UnauthorisedException, DataNotFoundException, WeatherForecastException, IOException  {
		
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.add("x-api-key",apiKey);
		
		final Optional<ResponseEntity<String>> apiResponse = Optional.of(apiClient.invokeGetResource(weatherForecastURLLondon,httpHeaders));
		if(apiResponse.isPresent()) {
			final String apiResponseJson = apiResponse.get().getBody();
			WeatherForecastResp resp = dataProcessSvc.processWeathrDataTotAvg(apiResponseJson);
			assertNotNull(resp.getWeatherForecast().get(0).getAvgPressure());
		}
		
	}
	
	@Test
	public void invokeGetResource_ReturnWeathrParamsVerifiesNonNullDayTempRespTest() throws UnauthorisedException, DataNotFoundException, WeatherForecastException, IOException  {
		
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.add("x-api-key",apiKey);
		
		final Optional<ResponseEntity<String>> apiResponse = Optional.of(apiClient.invokeGetResource(weatherForecastURLLondon,httpHeaders));
		if(apiResponse.isPresent()) {
			final String apiResponseJson = apiResponse.get().getBody();
			WeatherForecastResp resp = dataProcessSvc.processWeathrDataTotAvg(apiResponseJson);
			assertNotNull(resp.getWeatherForecast().get(0).getDayAvgTemp());
		}
		
	}
	
	@Test
	public void invokeGetResource_ReturnWeathrParamsVerifiesNonNullNightlyTempRespTest() throws UnauthorisedException, DataNotFoundException, WeatherForecastException, IOException  {
		
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.add("x-api-key",apiKey);
		
		final Optional<ResponseEntity<String>> apiResponse = Optional.of(apiClient.invokeGetResource(weatherForecastURLLondon,httpHeaders));
		if(apiResponse.isPresent()) {
			final String apiResponseJson = apiResponse.get().getBody();
			WeatherForecastResp resp = dataProcessSvc.processWeathrDataTotAvg(apiResponseJson);
			assertNotNull(resp.getWeatherForecast().get(0).getNightlyAvgTemp());
		}
		
	}

}
