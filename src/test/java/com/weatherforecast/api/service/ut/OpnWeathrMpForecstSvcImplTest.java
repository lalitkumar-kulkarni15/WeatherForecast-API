package com.weatherforecast.api.service.ut;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.weatherforecast.api.exception.DataNotFoundException;
import com.weatherforecast.api.exception.UnauthorisedException;
import com.weatherforecast.api.exception.WeatherForecastException;
import com.weatherforecast.api.model.WethrForecastReq;
import com.weatherforecast.api.service.IApiClient;
import com.weatherforecast.api.service.OpnWeathrMpForecstSvcImpl;

@RunWith(MockitoJUnitRunner.class)
public class OpnWeathrMpForecstSvcImplTest {

	@Mock
	IApiClient apiClient;

	@Mock
	ResponseEntity<String> respEntity;

	@InjectMocks
	OpnWeathrMpForecstSvcImpl businessImpl;
	
	private static String jsonResp; 

	@Before
	public void populateJsonResp() throws IOException {

		ClassPathResource res = new ClassPathResource("src/test/resources/testdata/jsonSuccessResponse.json");
		File file = new File(res.getPath());
		jsonResp = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
	}

	@Test
	public void fetchWhtrFrecstParamsTest_returnsSuccessResponse_AssertsNotNull()
			throws UnauthorisedException, DataNotFoundException, WeatherForecastException {

		when(apiClient.invokeGetResource(Mockito.anyString(), Mockito.any())).thenReturn(respEntity);
		when(respEntity.getBody()).thenReturn(jsonResp);
		when(respEntity.getStatusCode()).thenReturn(HttpStatus.OK);
		WethrForecastReq wethrFrescReqDto = new WethrForecastReq("London", "us", "json");
		final String response = businessImpl.fetchWhtrFrecstParams(wethrFrescReqDto);
		assertNotNull(response);
	}

	@Test
	public void fetchWhtrFrecstParamsTest_returnsPositiveResponse()
			throws UnauthorisedException, DataNotFoundException, WeatherForecastException {

		when(apiClient.invokeGetResource(Mockito.anyString(), Mockito.any())).thenReturn(respEntity);
		when(respEntity.getBody()).thenReturn(jsonResp);
		when(respEntity.getStatusCode()).thenReturn(HttpStatus.OK);
		WethrForecastReq wethrFrescReqDto = new WethrForecastReq("London", "us", "json");
		final String response = businessImpl.fetchWhtrFrecstParams(wethrFrescReqDto);
		assertTrue(response.equals(jsonResp));
	}
	
	@Test(expected=DataNotFoundException.class)
	public void fetchWhtrFrecstParamsTest_throwsDataNotFoundExceptionWhenNullResp()
			throws UnauthorisedException, DataNotFoundException, WeatherForecastException {

		when(apiClient.invokeGetResource(Mockito.anyString(), Mockito.any())).thenReturn(respEntity);
		when(respEntity.getBody()).thenReturn(null);
		WethrForecastReq wethrFrescReqDto = new WethrForecastReq("London", "us", "json");
		final String response = businessImpl.fetchWhtrFrecstParams(wethrFrescReqDto);
		assertTrue(response.equals(jsonResp));
	}
	
	@Test(expected=DataNotFoundException.class)
	public void fetchWhtrFrecstParamsTest_throwsDataNotFoundExceptionWhenBlankResp()
			throws UnauthorisedException, DataNotFoundException, WeatherForecastException {

		when(apiClient.invokeGetResource(Mockito.anyString(), Mockito.any())).thenReturn(respEntity);
		when(respEntity.getBody()).thenReturn("");
		WethrForecastReq wethrFrescReqDto = new WethrForecastReq("London", "us", "json");
		final String response = businessImpl.fetchWhtrFrecstParams(wethrFrescReqDto);
		assertTrue(response.equals(jsonResp));
	}

}
