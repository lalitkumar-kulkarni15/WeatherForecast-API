package com.weatherforecast.api.business;

import java.io.IOException;
import com.weatherforecast.api.exception.DataNotFoundException;
import com.weatherforecast.api.exception.UnauthorisedException;
import com.weatherforecast.api.exception.WeatherForecastException;
import com.weatherforecast.api.model.WeatherForecastResp;

public interface IWeathrForecstBusiness {
	
	public WeatherForecastResp getWeatherStats(final String city,final String cntry) throws DataNotFoundException, IOException, UnauthorisedException, WeatherForecastException;

}
