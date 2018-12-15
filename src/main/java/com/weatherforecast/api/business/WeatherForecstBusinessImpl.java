package com.weatherforecast.api.business;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.weatherforecast.api.exception.DataNotFoundException;
import com.weatherforecast.api.exception.UnauthorisedException;
import com.weatherforecast.api.exception.WeatherForecastException;
import com.weatherforecast.api.model.LocationDetails;
import com.weatherforecast.api.model.WeatherForecastResp;
import com.weatherforecast.api.model.WethrForecastReq;
import com.weatherforecast.api.service.IDataProcess;
import com.weatherforecast.api.service.IWethrForecstSvc;
import static com.weatherforecast.api.constants.IGenericConstants.CONTENT_TYPE;

/**
 * <p>This is the business implementation of the API. The rest endpoint refer {@code WethrFrcstCntrl} 
 * invokes this class to get the weather forecast metrics. This class is responsible for invoking the 
 * weather forecast service {@code OpnWeathrMpForecstSvcImpl } class and the data processor service 
 * {@code WeathrDataProcessSvcImpl } in order to fetch the required weathermetrics to be sent to the 
 * rest endpoint {@code WethrFrcstCntrl} to fetch the weather metrics from the {@link https://openweathermap.org/}.</p>
 * 
 * @author  Lalit Kulkarni
 * @since   08-12-2018
 * @version 1.0  
 */
@Component  
public class WeatherForecstBusinessImpl implements IWeathrForecstBusiness {
	
	private IWethrForecstSvc weathrForecstSvc;
	
	private IDataProcess dataProcessSvc;
	
	@Autowired
	public WeatherForecstBusinessImpl(final IWethrForecstSvc weathrForecstSvc,final IDataProcess dataProcessor) {
		this.weathrForecstSvc = weathrForecstSvc;
		this.dataProcessSvc = dataProcessor;
	}
	
	/**
	 * <p>This method primarily takes the city and country aliases as input from the rest controller 
	 * {@code WethrFrcstCntrl} and invokes {@code OpnWeathrMpForecstSvcImpl } service to call the 5 
	 * day weather data API which is exposed by {@link https://openweathermap.org/}. After receiving 
	 * the 5 day weather data from the API , it then invokes {@code WeathrDataProcessSvcImpl} service
	 * to process that data and filter out the weather metrics for 3 day and thus calculates the 
	 * averages to send the response back to the rest end point.</p>
	 * 
	 * @author lalit kulkarni
	 * @since  08-12-2018
	 * @param  city  : The city alias of which the weather metrics is to be requested.
	 * @param  cntry : The country alias of whoose city the weather metrics is to be requested.
	 * @throws DataNotFoundException
	 * @throws IOException
	 * @throws UnauthorisedException
	 * @throws WeatherForecastException
	 * @return WeatherForecastResp : Returns an object which houses all the weather metrics data.
	 */
	public WeatherForecastResp getWeatherStats(final String city,final String cntry) throws DataNotFoundException, IOException, UnauthorisedException, WeatherForecastException {
		
		// Initialise the weather forecast request object by populating the city ,country nd content type.
		final WethrForecastReq wethrForecastReq = new WethrForecastReq(city,cntry,CONTENT_TYPE);
		// Invoke the weather forecast service and fetch the weather metrics from the weather map api.
		final Optional<String> weathrApiResponse = Optional.of(weathrForecstSvc.fetchWhtrFrecstParams(wethrForecastReq));
		WeatherForecastResp weatherForecastResp = null;
		  
		if(!weathrApiResponse.get().isEmpty()) {
			// Process the json response received from the weather map api and filter it out for 3 consecutive days.
			weatherForecastResp = dataProcessSvc.processWeathrDataTotAvg(weathrApiResponse.get());
			weatherForecastResp.setInvocatnDtTm(LocalDateTime.now());
			// Populate the location details from the request and set it into the weather forecast response.
			LocationDetails locDetails = new LocationDetails(city, cntry);
			weatherForecastResp.setLocDetails(locDetails);
		}
		  
		return weatherForecastResp;
	}
  
}
