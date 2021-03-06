package com.weatherforecast.api.endpoints.integ;

import static com.weatherforecast.api.test.utils.ITestUtils.createURLWithPort;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.weatherforecast.api.WeathrFrcstAppMain;

@RunWith(SpringRunner.class)
@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,classes= {WeathrFrcstAppMain.class})
@TestPropertySource(locations="classpath:test.properties")
public class WethrFrcstCntrlIntIntegTest {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Value("${server.port}")
	private String port;
	
	@Value("${application.test.host}")
	private String host;
	
	private HttpHeaders httpHeaders;
	
	@Test
	public void fetchWethrFrecstParams_returnsStatusOKWithPositiveDataWhenCityLondon() throws JSONException {
		
		httpHeaders = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, httpHeaders);
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("weather-forecast/v1/data?city=London&countryCd=us",host,port),HttpMethod.GET, entity, String.class);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void fetchWethrFrecstParams_assertJsonRespreturnsPositiveWhenCityDelhi() throws JSONException {
		
		httpHeaders = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, httpHeaders);
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("weather-forecast/v1/data?city=Delhi&countryCd=in",host,port),HttpMethod.GET, entity, String.class);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void fetchWethrFrecstParams_whenInvalidCityMentionedThrowsNotFoundException() throws JSONException {
		
		httpHeaders = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, httpHeaders);
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("weather-forecast/v1/data?city=ABCD&countryCd=in",host,port),HttpMethod.GET, entity, String.class);
		Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	
	@Test
	public void fetchWethrFrecstParams_whenInvalidCityAndInvalidCntryMentionedThrowsNotFoundException() throws JSONException {
		
		httpHeaders = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, httpHeaders);
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("weather-forecast/v1/data?city=ABCD&countryCd=ABCD",host,port),HttpMethod.GET, entity, String.class);
		Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	
	@Test
	public void fetchWethrFrecstParams_whenCityAndCntryNotMentionedThrowsNotFoundException() throws JSONException {
		
		httpHeaders = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, httpHeaders);
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("weather-forecast/v1/data?",host,port),HttpMethod.GET, entity, String.class);
		Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

}
