package com.weatherforecast.api.service.integ;

import static org.junit.Assert.assertNotNull;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;
import com.weatherforecast.api.exception.WeatherForecastException;
import com.weatherforecast.api.main.WeathrFrcstAppMain;
import com.weatherforecast.api.model.WeatherForecastResp;
import com.weatherforecast.api.service.IDataProcess;

@RunWith(SpringRunner.class)
@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,classes= {WeathrFrcstAppMain.class})
public class WeathrDataProcessSvcImplTest {
	
	@Autowired
	IDataProcess dataProcessSvc;
	
	@Test
	public void processWeathrDataTotAvgTest_whenReturnsObjectSuccessfully() throws IOException, WeatherForecastException {
		
		ClassPathResource res = new ClassPathResource("src/test/resources/testdata/weatherForecastData.json");
		File file = new File(res.getPath());
		final String content = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
		WeatherForecastResp weatherForeCstResp = dataProcessSvc.processWeathrDataTotAvg(content);
		assertNotNull(weatherForeCstResp);
	}
	
	@Test
	public void processWeathrDataTotAvgTest_assertAvgPressure_Positive() throws IOException, WeatherForecastException {
		
		ClassPathResource res = new ClassPathResource("src/test/resources/testdata/weatherForecastData.json");
		File file = new File(res.getPath());
		final String content = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
		WeatherForecastResp weatherForeCstResp = dataProcessSvc.processWeathrDataTotAvg(content);
		weatherForeCstResp.getWeatherForecast().get(0).getAvgPressure();
		Assert.assertTrue("993.0733333333334".equals(weatherForeCstResp.getWeatherForecast().get(0).getAvgPressure()));
		
	}
	
	@Test
	public void processWeathrDataTotAvgTest_assertDate_Positive() throws IOException, WeatherForecastException {
		
		ClassPathResource res = new ClassPathResource("src/test/resources/testdata/weatherForecastData.json");
		File file = new File(res.getPath());
		final String content = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
		WeatherForecastResp weatherForeCstResp = dataProcessSvc.processWeathrDataTotAvg(content);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		Assert.assertTrue(LocalDate.parse("2018-12-09", formatter).equals(weatherForeCstResp.getWeatherForecast().get(0).getDate()));
		
	}
	
	@Test(expected=WeatherForecastException.class)
	public void processWeathrDataTotAvgTest_throwsWeatherForecastExWhenpassedNull_Negative() throws IOException, WeatherForecastException {
		
		ClassPathResource res = new ClassPathResource("src/test/resources/testdata/weatherForecastData.json");
		File file = new File(res.getPath());
		final String content = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
		WeatherForecastResp weatherForeCstResp = dataProcessSvc.processWeathrDataTotAvg(null);
	}
	
	@Test(expected=WeatherForecastException.class)
	public void processWeathrDataTotAvgTest_Negative() throws IOException, WeatherForecastException {
		
		ClassPathResource res = new ClassPathResource("src/test/resources/testdata/weatherForecastData.json");
		File file = new File(res.getPath());
		final String content = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
		WeatherForecastResp weatherForeCstResp = dataProcessSvc.processWeathrDataTotAvg("");
	}

}
