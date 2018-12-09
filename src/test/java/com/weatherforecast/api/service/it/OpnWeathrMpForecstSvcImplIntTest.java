package com.weatherforecast.api.service.it;

import static org.junit.Assert.assertNotNull;
import java.io.IOException;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import com.weatherforecast.api.exception.DataNotFoundException;
import com.weatherforecast.api.exception.UnauthorisedException;
import com.weatherforecast.api.exception.WeatherForecastException;
import com.weatherforecast.api.main.WeathrFrcstAppMain;
import com.weatherforecast.api.model.WethrForecastReq;
import com.weatherforecast.api.service.IDataProcess;
import com.weatherforecast.api.service.IWethrForecstSvc;

@RunWith(SpringRunner.class)
@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,classes= {WeathrFrcstAppMain.class})
@TestPropertySource(locations="classpath:test.properties")
public class OpnWeathrMpForecstSvcImplIntTest {
	
	@Autowired
	private IWethrForecstSvc wethrForecstSvc;
	
	@Autowired
	private IDataProcess dataProcess;
	
	@Test
	public void test() throws DataNotFoundException, IOException, UnauthorisedException, WeatherForecastException {
		
		WethrForecastReq wethrForecstReqDto = new WethrForecastReq("London","us","json");
		Optional<String> response = Optional.of(wethrForecstSvc.fetchWhtrFrecstParams(wethrForecstReqDto));
		dataProcess.processWeathrDataTotAvg(response.get());
		assertNotNull(response);
	}

}
