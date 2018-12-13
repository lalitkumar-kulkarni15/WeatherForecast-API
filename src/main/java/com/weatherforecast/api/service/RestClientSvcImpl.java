package com.weatherforecast.api.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.weatherforecast.api.exception.DataNotFoundException;
import com.weatherforecast.api.exception.UnauthorisedException;
import com.weatherforecast.api.exception.WeatherForecastException;

/**
 * <p>This class has a responsibility of invoking the third party rest API to fetch the weather 
 * forecast metrics</p>
 * 
 * @author  lalitkumar kulkarni
 * @since   08-12-2018
 * @version 1.0
 */
@Service(value = "restClientSvcImpl")
public class RestClientSvcImpl implements IApiClient {

	private RestTemplate restTemplate;
	
	public RestClientSvcImpl(RestTemplate restTemplate) {
		this.restTemplate = restTemplate; 
	}

	private static final String PARAM = "parameters";

	/**
	 * This method is a utility method which invokes the get method of the 3rd party api
	 * 
	 * @author lalitkumar kulkarni
	 * @since 08-12-2018
	 * @version 1.0
	 * @param uri The uri of the get method to be invoked.
	 * @param HttpHeaders The http headers to be passed as part of the uri.
	 * @throws UnauthorisedException This exception is thrown when the user is not authorised to invoke the api.
	 * @throws DataNotFoundException This exception is thrown when the data is not found by the api.
	 * @throws WeatherForecastException This exception is thrown when in case anything other than mentioned goes wrong in the api..
	 */
	public ResponseEntity<String> invokeGetResource(final String uri, final HttpHeaders httpHeaders) throws UnauthorisedException, DataNotFoundException, WeatherForecastException {

		try {
			HttpEntity<String> entity = new HttpEntity<>(PARAM, httpHeaders);
			return restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
		} catch (HttpClientErrorException exception) {
			
			HttpStatus httpStatus = exception.getStatusCode();
			
			if(HttpStatus.UNAUTHORIZED.equals(httpStatus)) {
				throw new UnauthorisedException("Unauthorised exception");
			} else if (HttpStatus.NOT_FOUND.equals(httpStatus)) {
				throw new DataNotFoundException("We couldnt find the data of the city you are looking for.");
			} else {
				throw new WeatherForecastException("Something went wrong with invoking the weather API.");
			}  
			
		}  
		
	}

}
