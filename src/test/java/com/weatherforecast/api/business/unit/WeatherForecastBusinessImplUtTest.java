package com.weatherforecast.api.business.unit;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
import com.weatherforecast.api.business.WeatherForecstBusinessImpl;
import com.weatherforecast.api.exception.DataNotFoundException;
import com.weatherforecast.api.exception.UnauthorisedException;
import com.weatherforecast.api.exception.WeatherForecastException;
import com.weatherforecast.api.model.WeatherForecastResp;
import com.weatherforecast.api.service.IDataProcess;
import com.weatherforecast.api.service.IWethrForecstSvc;

@RunWith(MockitoJUnitRunner.class)
public class WeatherForecastBusinessImplUtTest {
	
	@Mock
	private IWethrForecstSvc weathrForecstSvc;
	
	@Mock
	private IDataProcess dataProcessor;
	
	@Mock
	private WeatherForecastResp weatherForecastResp;
	
	@InjectMocks
	private WeatherForecstBusinessImpl weathrForecstBusiness;
	
	private static String jsonResp; 

	@Before
	public void populateJsonResp() throws IOException {

		ClassPathResource res = new ClassPathResource("src/test/resources/testdata/jsonSuccessResponse.json");
		File file = new File(res.getPath());
		jsonResp = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
	}
	
	@Test
	public void getWeatherStatsTest_checksInvocationTimesPositive() throws DataNotFoundException, UnauthorisedException, WeatherForecastException, IOException {
		
		when(weathrForecstSvc.fetchWhtrFrecstParams(Mockito.any())).thenReturn(jsonResp);
		when(dataProcessor.processWeathrDataTotAvg(Mockito.anyString())).thenReturn(weatherForecastResp);
		weathrForecstBusiness.getWeatherStats("London", "us");
		verify(weatherForecastResp,times(1)).setLocDetails(Mockito.any());
		verify(weatherForecastResp,times(1)).setInvocatnDtTm(Mockito.any());
	}
	
	@Test(expected=DataNotFoundException.class)
	public void getWeatherStatsTest_throwsDataNotFoundException() throws DataNotFoundException, UnauthorisedException, WeatherForecastException, IOException {
		
		when(weathrForecstSvc.fetchWhtrFrecstParams(Mockito.any())).thenThrow(DataNotFoundException.class);
		weathrForecstBusiness.getWeatherStats("London", "us");
	}
	
	@Test(expected=UnauthorisedException.class)
	public void getWeatherStatsTest_throwsUnauthorisedException() throws DataNotFoundException, UnauthorisedException, WeatherForecastException, IOException {
		
		when(weathrForecstSvc.fetchWhtrFrecstParams(Mockito.any())).thenThrow(UnauthorisedException.class);
		weathrForecstBusiness.getWeatherStats("London", "us");
	}
	
	@Test(expected=WeatherForecastException.class)
	public void getWeatherStatsTest_throwsWeatherForecastExceptionException() throws DataNotFoundException, UnauthorisedException, WeatherForecastException, IOException {
		
		when(weathrForecstSvc.fetchWhtrFrecstParams(Mockito.any())).thenThrow(WeatherForecastException.class);
		weathrForecstBusiness.getWeatherStats("London", "us");
	}
	
	@Test(expected=IOException.class)
	public void getWeatherStatsTest_WeathrForecastBusinessthrowsIOException() throws DataNotFoundException, UnauthorisedException, WeatherForecastException, IOException {
		
		when(weathrForecstSvc.fetchWhtrFrecstParams(Mockito.any())).thenReturn(jsonResp);
		when(dataProcessor.processWeathrDataTotAvg(Mockito.anyString())).thenThrow(IOException.class);
		weathrForecstBusiness.getWeatherStats("London", "us");
	}
	
	@Test(expected=WeatherForecastException.class)
	public void getWeatherStatsTest_WeathrForecastBusinessthrowsWeatherForecastException() throws DataNotFoundException, UnauthorisedException, WeatherForecastException, IOException {
		
		when(weathrForecstSvc.fetchWhtrFrecstParams(Mockito.any())).thenReturn(jsonResp);
		when(dataProcessor.processWeathrDataTotAvg(Mockito.anyString())).thenThrow(WeatherForecastException.class);
		weathrForecstBusiness.getWeatherStats("London", "us");
	}

}
