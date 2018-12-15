package com.weatherforecast.api.service;

import java.util.Arrays;
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
import static com.weatherforecast.api.constants.IGenericConstants.API_KEY;
import static com.weatherforecast.api.constants.IGenericConstants.MODE;
import static com.weatherforecast.api.constants.IGenericConstants.UNITS;
import static com.weatherforecast.api.constants.IGenericConstants.COMMA_SEP;
import static com.weatherforecast.api.constants.IGenericConstants.PARAM_VAR;


/**
 * <p>This class has a responsibility of invoking the weather API to get the weather metrics to get the </p>
 * weather data in json format , converts to java object and then sends it back to the rest controller 
 * {@code WethrFrcstCntrl}
 * 
 * @author  lalitkumar kulkarni
 * @since   08-12-2018
 * @version 1.0
 */
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

	/**
	 * This method is responsible to invoke the weather api usin the rest client
	 * and send the response back to the business layer in json string format. 
	 * 
	 * @author  lalitkumar kulkarni
	 * @since   08-12-2018
	 * @version 1.0
	 * @param   WethrForecastReq This object contains the city name and country.
	 * @return  String The actual json string response after invokin the weather api.
	 */
	@Override
	public String fetchWhtrFrecstParams(final WethrForecastReq wethrFrescReqDto)
			throws DataNotFoundException, UnauthorisedException, WeatherForecastException {

		String wethrForecstDataJsonResp = null;

		final String uri = createRestUrI(wethrFrescReqDto.getCityNm(), wethrFrescReqDto.getCntryCd(),
				wethrFrescReqDto.getRespCntntTyp());
		final HttpHeaders httpHeaders = getHttpHeaders();
		ResponseEntity<String> wethrForecstDataResp = (apiClient.invokeGetResource(uri, httpHeaders));

		if (null != wethrForecstDataResp.getBody() && !wethrForecstDataResp.getBody().isEmpty()
				&& HttpStatus.OK.equals(wethrForecstDataResp.getStatusCode())) {

			wethrForecstDataJsonResp = wethrForecstDataResp.getBody();
			return wethrForecstDataJsonResp;

		} else {
			throw new DataNotFoundException("API service returned blank response");
		}
		  
	}
	
	/**
	 * <p>This method populates the http headers which are required for invoking the
	 * rest api of weather forecast.<p/>
	 * 
	 * @author  lalitkumar kulkarni
	 * @since   08-12-2018
	 * @version 1.0
	 * @return HttpHeaders Returns the http headers with x api key and media types.
	 */
	private HttpHeaders getHttpHeaders() {
		
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.add(API_KEY,apiKey);
		return httpHeaders;
	}
	

	/**
	 * <p>This method creates the uri of the rest api to be invoked.</p>
	 * 
	 * @param  cityNm     City name for which the weather data is to be requested. 
	 * @param  cntryNm    Country name of which city for which the weather data is to be requested.
	 * @param  contentTyp Weather json or xml etc.
	 * @return String     Returns the fully qualified restfull api uri.
	 */
	private String createRestUrI(final String cityNm,final String cntryNm,final String contentTyp) {
		
		final StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append(weatherForecastURL);
		urlBuilder.append(PARAM_VAR);
		urlBuilder.append(cityNm);
		urlBuilder.append(COMMA_SEP);
		urlBuilder.append(cntryNm);
		urlBuilder.append(MODE);
		urlBuilder.append(contentTyp);
		urlBuilder.append(UNITS);
		return urlBuilder.toString();
	}

}
