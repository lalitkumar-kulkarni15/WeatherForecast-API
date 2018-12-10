package com.weatherforecast.api.service.integ;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.JsonNode;
import com.weatherforecast.api.WeathrFrcstAppMain;
import com.weatherforecast.api.exception.DataNotFoundException;
import com.weatherforecast.api.exception.UnauthorisedException;
import com.weatherforecast.api.exception.WeatherForecastException;
import com.weatherforecast.api.model.WethrForecastReq;
import com.weatherforecast.api.service.IParser;
import com.weatherforecast.api.service.IWethrForecstSvc;

@RunWith(SpringRunner.class)
@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,classes= {WeathrFrcstAppMain.class})
@TestPropertySource(locations="classpath:test.properties")
public class OpnWeathrMpForecstSvcImplIntTest {
	
	@Autowired
	private IWethrForecstSvc wethrForecstSvc;
	
	@Autowired
	private IParser parser;
	
	@Test
	public void fetchWhtrFrecstParamsTest_verifiesResponseNotNull() throws DataNotFoundException, IOException, UnauthorisedException, WeatherForecastException {
		
		WethrForecastReq wethrForecstReqDto = new WethrForecastReq("London","us","json");
		Optional<String> response = Optional.of(wethrForecstSvc.fetchWhtrFrecstParams(wethrForecstReqDto));
		assertNotNull(response);
	}
	
	@Test
	public void fetchWhtrFrecstParamsTest_verifiesParsableDt() throws DataNotFoundException, IOException, UnauthorisedException, WeatherForecastException {
		
		WethrForecastReq wethrForecstReqDto = new WethrForecastReq("London","us","json");
		Optional<String> response = Optional.of(wethrForecstSvc.fetchWhtrFrecstParams(wethrForecstReqDto));
		JsonNode jsonNode = parser.parseData(response.get());
		String dateTime = jsonNode.path("dt_txt").asText();
		assertNotNull(dateTime);
	}
	
	@Test
	public void fetchWhtrFrecstParamsTest_verifiesParsableTemp() throws DataNotFoundException, IOException, UnauthorisedException, WeatherForecastException {
		
		WethrForecastReq wethrForecstReqDto = new WethrForecastReq("London","us","json");
		Optional<String> response = Optional.of(wethrForecstSvc.fetchWhtrFrecstParams(wethrForecstReqDto));
		JsonNode jsonNode = parser.parseData(response.get());
		String temp = jsonNode.path("main").path("temp").asText();
		assertNotNull(temp);
	}
	
	@Test
	public void fetchWhtrFrecstParamsTest_verifiesParsablePressure() throws DataNotFoundException, IOException, UnauthorisedException, WeatherForecastException {
		
		WethrForecastReq wethrForecstReqDto = new WethrForecastReq("London","us","json");
		Optional<String> response = Optional.of(wethrForecstSvc.fetchWhtrFrecstParams(wethrForecstReqDto));
		JsonNode jsonNode = parser.parseData(response.get());
		String pressure = jsonNode.path("main").path("pressure").asText();
		assertNotNull(pressure);
	}

}
