package com.weatherforecast.api.testsuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;
import com.weatherforecast.api.business.integ.WeatherForecastBusinessImplIntTest;
import com.weatherforecast.api.endpoints.integ.WethrFrcstCntrlIntIntegTest;
import com.weatherforecast.api.service.integ.JsonParserSvcImplIntTest;
import com.weatherforecast.api.service.integ.OpnWeathrMpForecstSvcImplIntTest;
import com.weatherforecast.api.service.integ.RestClientSvcImplIntTest;
import com.weatherforecast.api.service.integ.WeathrDataProcessSvcImplTest;
import org.junit.runners.Suite;	

@RunWith(Suite.class)
@SuiteClasses({
		WethrFrcstCntrlIntIntegTest.class,
		WeatherForecastBusinessImplIntTest.class,
		JsonParserSvcImplIntTest.class,
		OpnWeathrMpForecstSvcImplIntTest.class,
		RestClientSvcImplIntTest.class,
		WeathrDataProcessSvcImplTest.class
})
public class IntegrationTestCaseSuite {

}
