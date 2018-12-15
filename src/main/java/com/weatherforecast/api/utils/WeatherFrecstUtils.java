package com.weatherforecast.api.utils;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import com.weatherforecast.api.model.StatsDto;

public interface WeatherFrecstUtils {
	
	public static LocalDate getTodaysDate() {
		return LocalDate.now();
	}
	
	public static List<LocalDate> getElligibleDatesForRange(double noOfDays) {
		
		long noOfDayss = (long) noOfDays;
		return IntStream.iterate(0, i -> i + 1)
	      .limit(noOfDayss)
	      .mapToObj(i -> LocalDate.now().plusDays(i))
	      .collect(Collectors.toList()); 
	}

	/**
	 * <p>This method uses the stream api to filter out the date time which fits into date time range and then
	 * calculates the average pressure of the particular date. </p>
	 * 
	 * @param  pressureListDay The list of the pressures to be filtered out.
	 * @param  lclDt  The local date.
	 * @return double The average day pressure of the city calculated.
	 */
	public static double calcAvgPressure(List<StatsDto> pressuremetrcsDtlList,LocalDate lclDt,double baseValue) {
		return pressuremetrcsDtlList.stream().filter(i->i.getLocalDate().equals(lclDt)).collect(Collectors.toList())
				.stream().map(StatsDto::getPressure).mapToDouble(g->g).average().orElse(baseValue);
	}
	
	/**
	 * <p>This method uses the stream api to filter out the date time which fits into the nightly date time range and then
	 * calculates the average temperature.</p> 
	 * 
	 * @param  tempListThe list of the temperatures to be filtered out.
	 * @param  lclDt  The local date.
	 * @return double The average temperature of the city calculated.
	 */
	public static double calcAvgTemp(List<StatsDto> tempListDay,LocalDate lclDt,double baseValue) {
		return tempListDay.stream().filter(i->i.getLocalDate().equals(lclDt)).collect(Collectors.toList()).stream().map(StatsDto::getTemp).mapToDouble(g->g).average().orElse(baseValue);
	}
}
