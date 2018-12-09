package com.weatherforecast.api.service;

import java.util.Arrays;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.weatherforecast.api.exception.DataNotFoundException;
import com.weatherforecast.api.exception.UnauthorisedException;
import com.weatherforecast.api.exception.WeatherForecastException;
import com.weatherforecast.api.model.WethrForecastReq;

@Service
public class OpnWeathrMpForecstSvcImpl implements IWethrForecstSvc{
	
	@Value("${application.prod.apikey}")
	private String apiKey;
	
	@Value("${application.prod.weatherForecastURL}")
	private String weatherForecastURL;
	
	private IApiClient apiClient;
	
	public OpnWeathrMpForecstSvcImpl(IApiClient apiClient) {
		this.apiClient = apiClient;
	}

	@Override
	public String fetchWhtrFrecstParams(final WethrForecastReq wethrFrescReqDto) throws DataNotFoundException, UnauthorisedException, WeatherForecastException {
		
		
		String wethrForecstDataJsonResp = null;

		final String uri = createRestUrI(wethrFrescReqDto.getCityNm(), wethrFrescReqDto.getCntryCd(),
				wethrFrescReqDto.getRespCntntTyp());
		final HttpHeaders httpHeaders = getHttpHeaders();
		ResponseEntity<String> wethrForecstDataResp = (apiClient.invokeGetResource(uri, httpHeaders));
		
		if(null!=wethrForecstDataResp.getBody() && !wethrForecstDataResp.getBody().isEmpty() && HttpStatus.OK.equals(wethrForecstDataResp.getStatusCode())) {  
			
			wethrForecstDataJsonResp = wethrForecstDataResp.getBody();
			return wethrForecstDataJsonResp;  
			
		} else {
			throw new DataNotFoundException("API service returned blank response");
		}
		  
		  
	}
	
	private HttpHeaders getHttpHeaders() {
		
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.add("x-api-key",apiKey);
		return httpHeaders;
	}
	
	private String createRestUrI(final String cityNm,final String cntryNm,final String contentTyp) {
		
		final StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append(weatherForecastURL);
		urlBuilder.append("q=");
		urlBuilder.append(cityNm);
		urlBuilder.append(",");
		urlBuilder.append(cntryNm);
		urlBuilder.append("&mode=");
		urlBuilder.append(contentTyp);
		urlBuilder.append("&units=metric");
		return urlBuilder.toString();
	}

}
