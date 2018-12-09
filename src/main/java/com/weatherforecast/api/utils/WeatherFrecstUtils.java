package com.weatherforecast.api.utils;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

}
