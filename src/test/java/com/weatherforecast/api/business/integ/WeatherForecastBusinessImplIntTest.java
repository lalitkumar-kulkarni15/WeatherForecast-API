package com.weatherforecast.api.business.integ;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.weatherforecast.api.WeathrFrcstAppMain;
import com.weatherforecast.api.business.IWeathrForecstBusiness;
import com.weatherforecast.api.exception.DataNotFoundException;
import com.weatherforecast.api.exception.UnauthorisedException;
import com.weatherforecast.api.exception.WeatherForecastException;
import com.weatherforecast.api.model.WeatherForecastResp;

@RunWith(SpringRunner.class)
@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,classes= {WeathrFrcstAppMain.class})
@TestPropertySource(locations="classpath:test.properties")
public class WeatherForecastBusinessImplIntTest {
	
	@Autowired
	IWeathrForecstBusiness business;
	
	@Test
	public void getWeatherStatsTest_assertCity_Positive() throws JSONException, DataNotFoundException, IOException, UnauthorisedException, WeatherForecastException {
		
		WeatherForecastResp weatherForecastResp = business.getWeatherStats("London","us");
		assertTrue(weatherForecastResp.getLocDetails().getCityName().equalsIgnoreCase("London"));
	}
	
	@Test
	public void getWeatherStatsTest_assertCntry_Positive() throws JSONException, DataNotFoundException, IOException, UnauthorisedException, WeatherForecastException {
		
		WeatherForecastResp weatherForecastResp = business.getWeatherStats("London","us");
		assertTrue(weatherForecastResp.getLocDetails().getCntryName().equalsIgnoreCase("us"));
	}
	
	@Test
	public void getWeatherStatsTest_assertNonBlankDate_Positive() throws JSONException, DataNotFoundException, IOException, UnauthorisedException, WeatherForecastException {
		
		WeatherForecastResp weatherForecastResp = business.getWeatherStats("London","us");
		assertNotNull((weatherForecastResp.getInvocatnDtTm()));
	}

}
