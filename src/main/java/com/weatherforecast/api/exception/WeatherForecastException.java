package com.weatherforecast.api.exception;

public class WeatherForecastException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public WeatherForecastException(final String message) {
		super(message);
	}
	
	public WeatherForecastException(Throwable th,final String message) {
		super(message);
	}
}
