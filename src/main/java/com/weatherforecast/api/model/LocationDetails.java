package com.weatherforecast.api.model;

public class LocationDetails {
	
	private String cityName;

	private String cntryName;

	public LocationDetails(String cityName, String cntryName) {
		super();
		this.cityName = cityName;
		this.cntryName = cntryName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCntryName() {
		return cntryName;
	}

	public void setCntryName(String cntryName) {
		this.cntryName = cntryName;
	}

}
