package com.weatherforecast.api.endpoints;

import java.io.IOException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.weatherforecast.api.business.IWeathrForecstBusiness;
import com.weatherforecast.api.exception.DataNotFoundException;
import com.weatherforecast.api.exception.UnauthorisedException;
import com.weatherforecast.api.exception.WeatherForecastException;
import com.weatherforecast.api.model.WeatherForecastResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/weather-forecast")
@Api(tags = "Weather forecast API", value = "Performs CRUD operations on the patient.")
public class WethrFrcstCntrl {
	
	private IWeathrForecstBusiness weathrForecstBusiness;
	
	public WethrFrcstCntrl(IWeathrForecstBusiness weathrForecstBusiness) {
		this.weathrForecstBusiness = weathrForecstBusiness;
	}  
	
	@GetMapping("/v1/data")
	@ApiOperation(value = "Get the average day and nightly temperature of 3 days.",
				  notes = "This API invokes the 3rd party API - https://openweathermap.org to fetch the weather forecast data i.e. temperature in degrees and pressure in hpa parameters.")
	@ApiResponses(value = {@ApiResponse(code = 500, message = "Something went wrong in the service, please contact the system administrator - Email - lalitkulkarniofficial@gmail.com"),
			               @ApiResponse(code = 200, message = "Weather forecast has been successfully fetched.") })
						   @ApiResponse(code = 400, message = "Bad input request.Please check the error description for more details.")
	public ResponseEntity<WeatherForecastResp> fetchWethrFrecstParams(@RequestParam(value="city",required=true) final String cityNm,
			@RequestParam(value="countryCd",required=true) final String cntryCd) throws  WeatherForecastException, DataNotFoundException, IOException, UnauthorisedException {
		
		final WeatherForecastResp weathrForecstResp = weathrForecstBusiness.getWeatherStats(cityNm, cntryCd);
		return ResponseEntity.ok().body(weathrForecstResp);
		
	}

}

