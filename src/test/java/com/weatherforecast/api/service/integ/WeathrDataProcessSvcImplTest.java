package com.weatherforecast.api.service.integ;

import java.io.IOException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.weatherforecast.api.WeathrFrcstAppMain;
import com.weatherforecast.api.exception.WeatherForecastException;
import com.weatherforecast.api.service.IDataProcess;

@RunWith(SpringRunner.class)
@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,classes= {WeathrFrcstAppMain.class})
public class WeathrDataProcessSvcImplTest {
	
	@Autowired
	IDataProcess dataProcessSvc;
	
	@Test(expected=WeatherForecastException.class)
	public void processWeathrDataTotAvgTest_throwsWeatherForecastExWhenpassedNull_Negative() throws IOException, WeatherForecastException {
		dataProcessSvc.processWeathrDataTotAvg(null);
	}
	
	@Test(expected=WeatherForecastException.class)
	public void processWeathrDataTotAvgTest_Negative() throws IOException, WeatherForecastException {
		dataProcessSvc.processWeathrDataTotAvg("");
	}

}
