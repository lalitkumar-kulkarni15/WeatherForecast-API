package com.weatherforecast.api.service;

import static com.weatherforecast.api.service.DateRangePredicate.isWithinDayRange;
import static com.weatherforecast.api.service.DateRangePredicate.isWithinNightlyRange;
import static com.weatherforecast.api.utils.WeatherFrecstUtils.getElligibleDatesForRange;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.weatherforecast.api.exception.WeatherForecastException;
import com.weatherforecast.api.model.DayWeathrForecast;
import com.weatherforecast.api.model.NightlyWeathrForecast;
import com.weatherforecast.api.model.StatsDto;
import com.weatherforecast.api.model.WeatherForecast;
import com.weatherforecast.api.model.WeatherForecastResp;

@Service
public class WeathrDataProcessSvcImpl implements IDataProcess {
	
	private IParser parser;
	
	public WeathrDataProcessSvcImpl(IParser parser) {
		this.parser = parser;
	}
	
	private static final String NO_OF_DAYS = "3";
	
	private static final String DATE_TXT_FIELD = "dt_txt";
	
	private static final String TEMP  = "temp";
	
	private static final String MAIN  = "main";
	
	private static final String PRESSURE  = "pressure";
	
	private static final String DT_TM_FRMT = "yyyy-MM-dd HH:mm:ss";
	
	private static final String NOT_AVAILABL = "NA";
	
	private static final String BASE_VAL = "0.00000";
	
	@Override
	public WeatherForecastResp processWeathrDataTotAvg(final String jsonData) throws IOException, WeatherForecastException {
		
		Optional<String> json = Optional.ofNullable(jsonData);
		
		if(json.isPresent() && !json.get().isEmpty()) {  
		
			List<StatsDto> tempListDay = new ArrayList<>();
			List<StatsDto> pressureListDay = new ArrayList<>();
			List<StatsDto> tempListNightly = new ArrayList<>();
			List<StatsDto> pressureListNightly = new ArrayList<>();

			Iterator<JsonNode> jsonNodes = getJsonNodeIterator(jsonData);

			while (jsonNodes.hasNext()) {

				JsonNode node = jsonNodes.next();
				LocalDateTime dateTme = getDtTmOfRecFromJSon(node);

				if (isWithinDayRange(NO_OF_DAYS).test(dateTme)) {
					tempListDay.add(fetchTemp(node));
					pressureListDay.add(fetchPressure(node));
				}

				if (isWithinNightlyRange(NO_OF_DAYS).test(dateTme)) {
					tempListNightly.add(fetchTemp(node));
					pressureListNightly.add(fetchPressure(node));
				}

			}

			pressureListDay.addAll(pressureListNightly);
			return iterateForDatesAndPopResp(tempListDay, pressureListDay, tempListNightly);
			
		} else {
			throw new WeatherForecastException("Input json message is null");
		}
	}
	
	private Iterator<JsonNode> getJsonNodeIterator(final String jsonData) throws IOException{
		
		JsonNode jsonNode = parser.parseData(jsonData);
		Iterator<JsonNode> jsonNodes = jsonNode.iterator();
		return jsonNodes;
	}
	
	private LocalDateTime getDtTmOfRecFromJSon(JsonNode node) {
		
		String dateTime = node.path(DATE_TXT_FIELD).asText();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DT_TM_FRMT);
		return LocalDateTime.parse(dateTime, formatter);
	}
	
	private WeatherForecastResp iterateForDatesAndPopResp(List<StatsDto> tempListDay
			,List<StatsDto> pressureListDay,List<StatsDto> tempListNightly) {
		
		List<LocalDate> listLocalDate = getElligibleDatesForRange(NO_OF_DAYS);
		WeatherForecastResp response = new WeatherForecastResp();
		
		for (LocalDate lclDt : listLocalDate) {

			double tempAvgDay = calcAvgDayTemp(tempListDay, lclDt);
			double pressureAvg = calcAvgPressure(pressureListDay, lclDt);
			double tempAvgNight = calcAvgNightlyTemp(tempListNightly, lclDt);

			WeatherForecast weatherForecast = populateResponse(tempAvgDay, pressureAvg, tempAvgNight, lclDt);
			response.getWeatherForecast().add(weatherForecast);

		}
		
		return response;
	}
	
	private WeatherForecast populateResponse(double tempAvgDay,double pressureAvg,
			double tempAvgNight,LocalDate lclDt) {
		
		DayWeathrForecast dayWeatherForecast = null;
		NightlyWeathrForecast nightlyWeatherForecast = null;
		String pressure = null;
		
		if(tempAvgDay==0.00000) {
			dayWeatherForecast = new DayWeathrForecast(NOT_AVAILABL,"06:00-18:00");
		} else {
			dayWeatherForecast = new DayWeathrForecast(Double.toString(tempAvgDay),"06:00-18:00");
		}
		
		if(tempAvgDay==0.00000) {
			nightlyWeatherForecast = new NightlyWeathrForecast(NOT_AVAILABL,"18:00-06:00");
		} else {
			nightlyWeatherForecast = new NightlyWeathrForecast(Double.toString(tempAvgNight),"18:00-06:00");
		}
		
		if(pressureAvg==0.00000) {
			pressure = NOT_AVAILABL;
		}else {
			pressure = Double.toString(pressureAvg);
		}
		
		return new WeatherForecast(dayWeatherForecast,nightlyWeatherForecast,pressure,lclDt);
		
	}
	
	private double calcAvgPressure(List<StatsDto> pressureListDay,LocalDate lclDt) {
		return pressureListDay.stream().filter(i->i.getLocalDate().equals(lclDt)).collect(Collectors.toList())
				.stream().map(a->a.getPressure()).mapToDouble(g->g).average().orElse(Double.valueOf(BASE_VAL).doubleValue());
	}
	
	private double calcAvgDayTemp(List<StatsDto> tempListDay,LocalDate lclDt) {
		return tempListDay.stream().filter(i->i.getLocalDate().equals(lclDt)).collect(Collectors.toList()).stream().map(a->a.getTemp()).mapToDouble(g->g).average().orElse(Double.valueOf(BASE_VAL).doubleValue());
	}
	
	private double calcAvgNightlyTemp(List<StatsDto> tempListNightly,LocalDate lclDt) {
		return tempListNightly.stream().filter(i->i.getLocalDate().equals(lclDt)).collect(Collectors.toList()).stream().map(a->a.getTemp()).mapToDouble(g->g).average().orElse(Double.valueOf(BASE_VAL).doubleValue());
	}
	
	private StatsDto fetchTemp(JsonNode node) {

		StatsDto stats = null;
		
		if (node.path(DATE_TXT_FIELD).asText() != null) {
			String temp = node.path(MAIN).path(TEMP).asText();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DT_TM_FRMT);
			LocalDateTime dateTme = LocalDateTime.parse(node.path(DATE_TXT_FIELD).asText(), formatter);
			stats = new StatsDto(dateTme.toLocalDate(), Double.parseDouble(temp), 0);
			return stats;
		}
		
		return stats;
		
	}

	private StatsDto fetchPressure(JsonNode node) {
		
		StatsDto stats = null;

		if (node.path(DATE_TXT_FIELD).asText() != null) {
			String pressure = node.path(MAIN).path(PRESSURE).asText();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DT_TM_FRMT);
			LocalDateTime dateTme = LocalDateTime.parse(node.path(DATE_TXT_FIELD).asText(), formatter);
			stats = new StatsDto(dateTme.toLocalDate(), 0, Double.parseDouble(pressure));
			return stats;
		}
		
		return stats;
	}

}
