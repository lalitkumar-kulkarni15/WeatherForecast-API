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

@Component
public class WeatherForecstBusinessImpl implements IWeathrForecstBusiness {
	
	private IWethrForecstSvc weathrForecstSvc;
	
	private IDataProcess dataProcessor;
	
	@Autowired
	public WeatherForecstBusinessImpl(final IWethrForecstSvc weathrForecstSvc,final IDataProcess dataProcessor) {
		this.weathrForecstSvc = weathrForecstSvc;
		this.dataProcessor = dataProcessor;
	}
	
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
