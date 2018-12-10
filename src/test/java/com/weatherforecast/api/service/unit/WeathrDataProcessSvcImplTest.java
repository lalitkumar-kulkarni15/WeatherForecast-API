package com.weatherforecast.api.service.unit;

import static com.weatherforecast.api.test.utils.ITestUtils.getJsonNode;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.TestPropertySource;
import com.fasterxml.jackson.databind.JsonNode;
import com.weatherforecast.api.exception.DataNotFoundException;
import com.weatherforecast.api.exception.UnauthorisedException;
import com.weatherforecast.api.exception.WeatherForecastException;
import com.weatherforecast.api.model.WeatherForecastResp;
import com.weatherforecast.api.service.IParser;
import com.weatherforecast.api.service.WeathrDataProcessSvcImpl;

@RunWith(MockitoJUnitRunner.class)
@TestPropertySource(locations="classpath:test.properties")
public class WeathrDataProcessSvcImplTest {
	
	@Mock
	private IParser parser;
	
	@InjectMocks
	private WeathrDataProcessSvcImpl dataProcess;
	
	private static String jsonResp; 
	
	JsonNode jsonNode = null;

	@Before
	public void populateTestData() throws IOException {

		ClassPathResource res = new ClassPathResource("src/test/resources/testdata/weatherForecastDataOlderDates.json");
		File file = new File(res.getPath());
		jsonResp = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
		jsonNode = getJsonNode(jsonResp);
	}
	
	@Test()
	public void processWeathrDatareturnsZeroPressureWhenPastDataReceived_Test()
			throws UnauthorisedException, DataNotFoundException, WeatherForecastException, IOException {

		when(parser.parseData(Mockito.anyString())).thenReturn(jsonNode);
		WeatherForecastResp weatherForecast = dataProcess.processWeathrDataTotAvg(jsonResp);
		assertEquals("NA", weatherForecast.getWeatherForecast().get(0).getAvgPressure());
	}
	
	@Test()
	public void processWeathrDatareturnsZeroDyTempWhenPastDataReceived_Test()
			throws UnauthorisedException, DataNotFoundException, WeatherForecastException, IOException {

		when(parser.parseData(Mockito.anyString())).thenReturn(jsonNode);
		WeatherForecastResp weatherForecast = dataProcess.processWeathrDataTotAvg(jsonResp);
		assertEquals("NA", weatherForecast.getWeatherForecast().get(0).getDayAvgTemp().getTemp());
	}
	
	@Test()
	public void processWeathrDatareturnsZeroNightlyTempWhenPastDataReceived_Test()
			throws UnauthorisedException, DataNotFoundException, WeatherForecastException, IOException {

		when(parser.parseData(Mockito.anyString())).thenReturn(jsonNode);
		WeatherForecastResp weatherForecast = dataProcess.processWeathrDataTotAvg(jsonResp);
		assertEquals("NA", weatherForecast.getWeatherForecast().get(0).getNightlyAvgTemp().getTemp());
	}

}
