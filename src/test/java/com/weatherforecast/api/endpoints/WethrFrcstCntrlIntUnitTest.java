package com.weatherforecast.api.endpoints;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.weatherforecast.api.business.IWeathrForecstBusiness;
import com.weatherforecast.api.exception.DataNotFoundException;
import com.weatherforecast.api.exception.WeatherForecastException;
import com.weatherforecast.api.model.DayWeathrForecast;
import com.weatherforecast.api.model.LocationDetails;
import com.weatherforecast.api.model.NightlyWeathrForecast;
import com.weatherforecast.api.model.WeatherForecast;
import com.weatherforecast.api.model.WeatherForecastResp;

@RunWith(SpringRunner.class)
@WebMvcTest(WethrFrcstCntrl.class)
public class TWethrFrcstCntrlIntUnitTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private IWeathrForecstBusiness weathrForecstBusiness;
	
	@Test
	public void testfetchWethrFrecstParamsTest_returnsPositiveResp() throws Exception {
		
		when(weathrForecstBusiness.getWeatherStats(Mockito.anyString(),Mockito.anyString())).thenReturn(populateTestDataFrPositiveRespScenario());
		
		RequestBuilder request = MockMvcRequestBuilders.get("/weather-forecast/v1/data?city=Kolhapur&countryCd=in").
				accept(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(request).andExpect(status().isOk()).andExpect(content().json("{\"invocatnDtTm\":\"2018-12-09T19:59:00\",\"locDetails\":{\"cityName\":\"Pune\",\"cntryName\":\"in\"},\"weatherForecast\":[{\"date\":\"2018-12-09\",\"dayAvgTemp\":{\"temp\":\"25.8\",\"range\":\"06-18\"},\"nightlyAvgTemp\":{\"temp\":\"25.8\",\"range\":\"18-06\"},\"avgPressure\":\"234.00\"}]}")).andReturn();
		
	}
	
	@Test
	public void testfetchWethrFrecstParamsTest_returnsDataNotFoundEx() throws Exception {
		
		when(weathrForecstBusiness.getWeatherStats(Mockito.anyString(),Mockito.anyString())).thenThrow(DataNotFoundException.class);
		
		RequestBuilder request = MockMvcRequestBuilders.get("/weather-forecast/v1/data?city=Kolhapur&countryCd=in").
				accept(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(request).andExpect(status().isNotFound()).andReturn();
		
	}
	
	@Test
	public void testfetchWethrFrecstParamsTest_returnsIntServerExForIOEx() throws Exception {
		
		when(weathrForecstBusiness.getWeatherStats(Mockito.anyString(),Mockito.anyString())).thenThrow(IOException.class);
		
		RequestBuilder request = MockMvcRequestBuilders.get("/weather-forecast/v1/data?city=Kolhapur&countryCd=in").
				accept(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(request).andExpect(status().isInternalServerError()).andReturn();
		
	} 
	
	@Test
	public void testfetchWethrFrecstParamsTest_returnsIntServerExForWeathrFrcstEx() throws Exception {
		
		when(weathrForecstBusiness.getWeatherStats(Mockito.anyString(),Mockito.anyString())).thenThrow(WeatherForecastException.class);
		
		RequestBuilder request = MockMvcRequestBuilders.get("/weather-forecast/v1/data?city=Kolhapur&countryCd=in").
				accept(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(request).andExpect(status().isInternalServerError()).andReturn();
		
	}
	
	private WeatherForecastResp populateTestDataFrPositiveRespScenario() {
		
		WeatherForecastResp weatherForecastResp = new WeatherForecastResp();
		LocalDateTime localDtTm = LocalDateTime.of(2018, 12, 9, 19, 59);
		weatherForecastResp.setInvocatnDtTm(localDtTm);
		LocationDetails locationDetails = new LocationDetails("Pune", "in");
		weatherForecastResp.setLocDetails(locationDetails);
		DayWeathrForecast dayWeatherForecast = new DayWeathrForecast("25.8", "06-18");
		NightlyWeathrForecast nightlyWeathrForecast = new NightlyWeathrForecast("25.8", "18-06");
		WeatherForecast weatherForecast = new WeatherForecast(dayWeatherForecast, nightlyWeathrForecast, "234.00", LocalDate.of(2018, 12, 9));
		List<WeatherForecast> weatherForecastList = new ArrayList<>();
		weatherForecastList.add(weatherForecast);
		weatherForecastResp.setWeatherForecast(weatherForecastList);
		return weatherForecastResp;
	}

}
