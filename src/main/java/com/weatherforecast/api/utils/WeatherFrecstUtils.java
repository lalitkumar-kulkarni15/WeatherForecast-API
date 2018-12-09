package com.weatherforecast.api.utils;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import com.fasterxml.jackson.databind.JsonNode;

public interface WeatherFrecstUtils {
	
	public static LocalDate getTodaysDate() {
		return LocalDate.now();
	}
	
	public static List<LocalDate> getElligibleDatesForRange(String noOfDays) {
		
		long noOfDayss = Long.parseLong(noOfDays);
		return IntStream.iterate(0, i -> i + 1)
	      .limit(noOfDayss)
	      .mapToObj(i -> LocalDate.now().plusDays(i))
	      .collect(Collectors.toList()); 
	}
	
	public static double fetchTemp(JsonNode node) {

		if (node.path("dt_txt").asText() != null) {
			String temp = node.path("main").path("temp").asText();
			return Double.parseDouble(temp);
		}
		
		return 0;
	}

	public static double fetchPressure(JsonNode node) {

		if (node.path("dt_txt").asText() != null) {
			String pressure = node.path("main").path("pressure").asText();
			return Double.parseDouble(pressure);
		}
		return 0;
	}
	
	public static double calcAverage(final List<Double> parameter) {
		return parameter.stream().mapToDouble(param -> param).average().getAsDouble();		
	}

}
