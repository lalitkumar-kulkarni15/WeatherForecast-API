package com.weatherforecast.api.model;

public class DayWeathrForecast {
	
	private String temp;
	
	private String range;
	
	public String getRange() {
		return range;
	}

	public void setRange(String range) {
		this.range = range;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	public DayWeathrForecast(String temp, String range) {
		super();
		this.temp = temp;
		this.range = range;
	}

}
