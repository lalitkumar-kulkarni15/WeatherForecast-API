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

/**
 * <p>This is the business implementation of the API. The rest endpoint refer {@code WethrFrcstCntrl} 
 * invokes this class to get the weather forecast metrics. This class is responsible for invoking the 
 * weather forecast service class and the data processor service in order to fetch the required weather
 * metrics to be sent to the rest endpoint {@code WethrFrcstCntrl} to fetch the weather metrics from the
 * {@link https://openweathermap.org/}.  </p>
 * 
 * @author  Lalit Kulkarni
 * @since   08-12-2018
 * @version 1.0  
 * 
 */
@Component  
public class WeatherForecstBusinessImpl implements IWeathrForecstBusiness {
	
	private IWethrForecstSvc weathrForecstSvc;
	
	private IDataProcess dataProcessor;
	
	@Autowired
	public WeatherForecstBusinessImpl(final IWethrForecstSvc weathrForecstSvc,final IDataProcess dataProcessor) {
		this.weathrForecstSvc = weathrForecstSvc;
		this.dataProcessor = dataProcessor;
	}
	
	/**
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
		
		WethrForecastReq wethrForecastReq = new WethrForecastReq(city,cntry,"json");
		Optional<String> weathrApiResponse = Optional.of(weathrForecstSvc.fetchWhtrFrecstParams(wethrForecastReq));
		WeatherForecastResp weatherForecastResp = null;
		
		if(!weathrApiResponse.get().isEmpty()) {
			weatherForecastResp = dataProcessor.processWeathrDataTotAvg(weathrApiResponse.get());
			weatherForecastResp.setInvocatnDtTm(LocalDateTime.now());
			LocationDetails locDetails = new LocationDetails(city, cntry);
			weatherForecastResp.setLocDetails(locDetails);
		}
		  
		return weatherForecastResp;
	}
  
}
