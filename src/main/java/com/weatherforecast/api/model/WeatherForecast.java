package com.weatherforecast.api.model;

import java.time.LocalDate;

public class WeatherForecast {
	
	private LocalDate date;
	
	private DayWeathrForecast dayAvgTemp;
	
	private NightlyWeathrForecast nightlyAvgTemp;
	
	private String avgPressure;
	
	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public WeatherForecast(DayWeathrForecast dayAvgTemp, NightlyWeathrForecast nightlyAvgTemp,String avgPressure,LocalDate localDate) {
		super();
		this.dayAvgTemp = dayAvgTemp;
		this.nightlyAvgTemp = nightlyAvgTemp;
		this.avgPressure = avgPressure;
		this.date = localDate;
	}
	
	public String getAvgPressure() {
		return avgPressure;
	}

	public void setAvgPressure(String avgPressure) {
		this.avgPressure = avgPressure;
	}

	public DayWeathrForecast getDayAvgTemp() {
		return dayAvgTemp;
	}

	public void setDayAvgTemp(DayWeathrForecast dayAvgTemp) {
		this.dayAvgTemp = dayAvgTemp;
	}

	public NightlyWeathrForecast getNightlyAvgTemp() {
		return nightlyAvgTemp;
	}

	public void setNightlyAvgTemp(NightlyWeathrForecast nightlyAvgTemp) {
		this.nightlyAvgTemp = nightlyAvgTemp;
	}

}
