package com.weatherforecast.api.testsuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import com.weatherforecast.api.business.unit.WeatherForecastBusinessImplUtTest;
import com.weatherforecast.api.endpoints.WethrFrcstCntrlIntUnitTest;
import com.weatherforecast.api.service.integ.WeathrDataProcessSvcImplTest;
import com.weatherforecast.api.service.unit.DateRangePredicateTest;
import com.weatherforecast.api.service.unit.OpnWeathrMpForecstSvcImplTest;
import com.weatherforecast.api.service.unit.RestClientSvcImplTest;

@RunWith(Suite.class)
@SuiteClasses({
	WeatherForecastBusinessImplUtTest.class,
	WethrFrcstCntrlIntUnitTest.class,
	DateRangePredicateTest.class,
	OpnWeathrMpForecstSvcImplTest.class,
	RestClientSvcImplTest.class,
	WeathrDataProcessSvcImplTest.class
})
public class UnitTestCaseSuite {

}
