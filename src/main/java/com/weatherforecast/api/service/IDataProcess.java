package com.weatherforecast.api.service;

import java.io.IOException;
import com.weatherforecast.api.exception.WeatherForecastException;
import com.weatherforecast.api.model.WeatherForecastResp;

public interface IDataProcess {
	
	public WeatherForecastResp processWeathrDataTotAvg(final String jsonData) throws IOException, WeatherForecastException;

}
