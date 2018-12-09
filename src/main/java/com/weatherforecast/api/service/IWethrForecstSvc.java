package com.weatherforecast.api.service;

import com.weatherforecast.api.exception.DataNotFoundException;
import com.weatherforecast.api.exception.UnauthorisedException;
import com.weatherforecast.api.exception.WeatherForecastException;
import com.weatherforecast.api.model.WethrForecastReq;

public interface IWethrForecstSvc {
	
	public String fetchWhtrFrecstParams(final WethrForecastReq wethrFrescReqDto) throws DataNotFoundException, UnauthorisedException, WeatherForecastException;

}
