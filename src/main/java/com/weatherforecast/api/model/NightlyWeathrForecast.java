package com.weatherforecast.api.model;

public class NightlyWeathrForecast {

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

	public NightlyWeathrForecast(String temp, String range) {
		super();
		this.temp = temp;
		this.range = range;
	}
}
