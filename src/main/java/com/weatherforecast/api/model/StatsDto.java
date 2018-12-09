package com.weatherforecast.api.model;

import java.time.LocalDate;

public class StatsDto {
	
	private LocalDate localDate;
	
	private double temp;
	
	private double pressure;

	public StatsDto(LocalDate localDate, double temp, double pressure) {
		super();
		this.localDate = localDate;
		this.temp = temp;
		this.pressure = pressure;
	}

	public LocalDate getLocalDate() {
		return localDate;
	}

	public void setLocalDate(LocalDate localDate) {
		this.localDate = localDate;
	}

	public double getTemp() {
		return temp;
	}

	public void setTemp(double temp) {
		this.temp = temp;
	}

	public double getPressure() {
		return pressure;
	}

	public void setPressure(double pressure) {
		this.pressure = pressure;
	}
	
}
