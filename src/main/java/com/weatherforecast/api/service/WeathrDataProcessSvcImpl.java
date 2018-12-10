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

				if (isWithinDayRange("3").test(dateTme)) {
					tempListDay.add(fetchTemp(node));
					pressureListDay.add(fetchPressure(node));
				}

				if (isWithinNightlyRange("3").test(dateTme)) {
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
		
		String dateTime = node.path("dt_txt").asText();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return LocalDateTime.parse(dateTime, formatter);
	}
	
	private WeatherForecastResp iterateForDatesAndPopResp(List<StatsDto> tempListDay
			,List<StatsDto> pressureListDay,List<StatsDto> tempListNightly) {
		
		List<LocalDate> listLocalDate = getElligibleDatesForRange("3");
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
			dayWeatherForecast = new DayWeathrForecast("NA","06:00-18:00");
		} else {
			dayWeatherForecast = new DayWeathrForecast(Double.toString(tempAvgDay),"06:00-18:00");
		}
		
		if(tempAvgDay==0.00000) {
			nightlyWeatherForecast = new NightlyWeathrForecast("NA","18:00-06:00");
		} else {
			nightlyWeatherForecast = new NightlyWeathrForecast(Double.toString(tempAvgNight),"18:00-06:00");
		}
		
		if(pressureAvg==0.00000) {
			pressure = "NA";
		}else {
			pressure = Double.toString(pressureAvg);
		}
		
		return new WeatherForecast(dayWeatherForecast,nightlyWeatherForecast,pressure,lclDt);
		
	}
	
	private double calcAvgPressure(List<StatsDto> pressureListDay,LocalDate lclDt) {
		return pressureListDay.stream().filter(i->i.getLocalDate().equals(lclDt)).collect(Collectors.toList())
				.stream().map(a->a.getPressure()).mapToDouble(g->g).average().orElse(Double.valueOf("0.00000").doubleValue());
	}
	
	private double calcAvgDayTemp(List<StatsDto> tempListDay,LocalDate lclDt) {
		return tempListDay.stream().filter(i->i.getLocalDate().equals(lclDt)).collect(Collectors.toList()).stream().map(a->a.getTemp()).mapToDouble(g->g).average().orElse(Double.valueOf("0.00000").doubleValue());
	}
	
	private double calcAvgNightlyTemp(List<StatsDto> tempListNightly,LocalDate lclDt) {
		return tempListNightly.stream().filter(i->i.getLocalDate().equals(lclDt)).collect(Collectors.toList()).stream().map(a->a.getTemp()).mapToDouble(g->g).average().orElse(Double.valueOf("0.00000").doubleValue());
	}
	
	private StatsDto fetchTemp(JsonNode node) {

		StatsDto stats = null;
		
		if (node.path("dt_txt").asText() != null) {
			String temp = node.path("main").path("temp").asText();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime dateTme = LocalDateTime.parse(node.path("dt_txt").asText(), formatter);
			stats = new StatsDto(dateTme.toLocalDate(), Double.parseDouble(temp), 0);
			return stats;
		}
		
		return stats;
		
	}

	private StatsDto fetchPressure(JsonNode node) {
		
		StatsDto stats = null;

		if (node.path("dt_txt").asText() != null) {
			String pressure = node.path("main").path("pressure").asText();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime dateTme = LocalDateTime.parse(node.path("dt_txt").asText(), formatter);
			stats = new StatsDto(dateTme.toLocalDate(), 0, Double.parseDouble(pressure));
			return stats;
		}
		
		return stats;
	}

}
