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

/**
 * <p>This class has a responsibility of processing the json message which is fetched by invoking the 
 *  weather API. It filters out the data as per the date range from the 5 days data which is present
 *  It biforgates the data according to the day and night timing range and then calculates the averages
 *  of the weather parameters like temperature and pressure and sends back to the business class 
 *  {@code WeatherForecstBusinessImpl}.</p>
 * 
 * @author  lalitkumar kulkarni
 * @since   08-12-2018
 * @version 1.0
 */
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
	
	/**
	 * This method converts the input json string data to the {@code WeatherForecastResp} by calculating the temperature and
	 * pressure averages.
	 * 
	 * @author lalitkumar kulkarni
	 * @since 08-12-2018
	 * @version 1.0
	 */
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
	
	/**
	 * This method parses the input json string which is received from the weather api 
	 * and returns the iterator of the json node which is used for the further processing.
	 * 
	 * @param  jsonData The input json string which is received from the weather api
	 * @return Iterator<JsonNode> The iterator of the json node which is used for the further processing
	 * @throws IOException 
	 */
	private Iterator<JsonNode> getJsonNodeIterator(final String jsonData) throws IOException{
		
		JsonNode jsonNode = parser.parseData(jsonData);
		Iterator<JsonNode> jsonNodes = jsonNode.iterator();
		return jsonNodes;
	}
	
	/**
	 * This method converts the date time in the json node to {@code LocalDateTime } format
	 * 
	 * @param  node           The input json node which is created by parsing the json received
	 *                        from the weather api.
	 * @return LocalDateTime  This i sthe local date time in the {@code LocalDateTime} format 
	 *                        converted from the input json node.
	 */
	private LocalDateTime getDtTmOfRecFromJSon(JsonNode node) {
		
		String dateTime = node.path(DATE_TXT_FIELD).asText();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DT_TM_FRMT);
		return LocalDateTime.parse(dateTime, formatter);
	}
	
	/**
	 * This method gets the 3 elligible dates starting from the current date for which the
	 * temperature stats are to be returned back. It loops for each date , calculates the 
	 * weather metrics i.e. day,night average temperature and pressure and sends back 
	 * {@code WeatherForecastResp} 
	 * 
	 * @param  tempListDay        The average day temperature list.
	 * @param  pressureListDay    The average pressure list.
	 * @param  tempListNightly    The average nightly temperature list.
	 * @return WeatherForecastResp The api response object.
	 */
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
	
	/**
	 * This method populates the response to the api which contains the day temperature, nightly temperature and average pressure.
	 * for 3 consecutive days.
	 * 
	 * @param  tempAvgDay      The average day temperature.
	 * @param  pressureAvg     The average pressure for that particular date.
	 * @param  tempAvgNight    The average nightly temperature.
	 * @param  lclDt           The local date for which the metrics is to be calcuated.
	 * @return WeatherForecast The response object which contains the average day temperature, nightly temperature 
	 *                         and average pressure of 3 consecutive days.
	 */
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
	
	/**
	 * <p>This method uses the stream api to filter out the date time which fits into date time range and then
	 * calculates the average pressure of the particular date. </p>
	 * 
	 * @param  pressureListDay The list of the pressures to be filtered out.
	 * @param  lclDt  The local date.
	 * @return double The average day pressure of the city calculated.
	 */
	private double calcAvgPressure(List<StatsDto> pressureListDay,LocalDate lclDt) {
		return pressureListDay.stream().filter(i->i.getLocalDate().equals(lclDt)).collect(Collectors.toList())
				.stream().map(a->a.getPressure()).mapToDouble(g->g).average().orElse(Double.valueOf(BASE_VAL).doubleValue());
	}
	
	/**
	 * <p>This method uses the stream api to filter out the date time which fits into the day date time range and then
	 * calculates the average day temperature. </p>
	 * 
	 * @param  tempListDay The list of the temperatures to be filtered out.
	 * @param  lclDt  The local date.
	 * @return double The average day temperature of the city calculated.
	 */
	private double calcAvgDayTemp(List<StatsDto> tempListDay,LocalDate lclDt) {
		return tempListDay.stream().filter(i->i.getLocalDate().equals(lclDt)).collect(Collectors.toList()).stream().map(a->a.getTemp()).mapToDouble(g->g).average().orElse(Double.valueOf(BASE_VAL).doubleValue());
	}
	
	/**
	 * <p>This method uses the stream api to filter out the date time which fits into the nightly date time range and then
	 * calculates the average nightly temperature.</p> 
	 * 
	 * @param  tempListNightly The list of the temperatures to be filtered out.
	 * @param  lclDt  The local date.
	 * @return double The average nightly temperature of the city calculated.
	 */
	private double calcAvgNightlyTemp(List<StatsDto> tempListNightly,LocalDate lclDt) {
		return tempListNightly.stream().filter(i->i.getLocalDate().equals(lclDt)).collect(Collectors.toList()).stream().map(a->a.getTemp()).mapToDouble(g->g).average().orElse(Double.valueOf(BASE_VAL).doubleValue());
	}
	
	/**
	 * <p>This method is responsible for extracting put the temperature of the city from the json node
	 * object. </p>
	 * 
	 * @param  node     The JSON node object which is created by convertin the json strin 
	 *                  fetched from the weather rest api.
	 * @return StatsDto Contains the temperature info which is derieve from the json node object
	 *                  which is fetche from the third party weather API.
	 */
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

	/**
	 * <p>This method is responsible for extracting put the pressure of the city from the json node
	 * object. </p>
	 * 
	 * @param node      The JSON node object which is created by convertin the json strin 
	 *                  fetched from the weather rest api.
	 * @return StatsDto Contains the pressure info which is derieve from the json node object
	 *                  which is fetche from the third party weather API.
	 */
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
