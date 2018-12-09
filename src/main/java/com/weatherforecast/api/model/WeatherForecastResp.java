package com.weatherforecast.api.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>This is a data transfer object class which is used for populating the weather API data 
 * parameters as mentioned. This is currently being used in {@code WethrFrcstCntrl.java} to 
 * send the weather forecast parameters response back to the consumer who has invoked the API.</p>
 *  
 * @author  lalitkumar kulkarni
 * @version 1.0
 * @since   08-12-2018 
 *
 */
public class WeatherForecastResp {
	
	private LocalDateTime invocatnDtTm;
	
	public WeatherForecastResp(LocalDateTime invocatnDtTm, LocationDetails locDetails,
			List<WeatherForecast> weatherForecast) {
		
		super();
		this.invocatnDtTm = invocatnDtTm;
		this.locDetails = locDetails;
		this.weatherForecast = weatherForecast;
	}

	public List<WeatherForecast> getWeatherForecast() {
		return weatherForecast;
	}

	public void setWeatherForecast(List<WeatherForecast> weatherForecast) {
		this.weatherForecast = weatherForecast;
	}

	private LocationDetails locDetails;
	
	private List<WeatherForecast> weatherForecast = new ArrayList<>();
	
	public WeatherForecastResp() {
		super();
	}
	
	public LocationDetails getLocDetails() {
		return locDetails;
	}

	public void setLocDetails(LocationDetails locDetails) {
		this.locDetails = locDetails;
	}

	public LocalDateTime getInvocatnDtTm() {
		return invocatnDtTm;
	}

	public void setInvocatnDtTm(LocalDateTime invocatnDtTm) {
		this.invocatnDtTm = invocatnDtTm;
	}
	
}
