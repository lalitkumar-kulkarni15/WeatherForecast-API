package com.weatherforecast.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppCfg {

	@Bean(name="restTemplate")
	public RestTemplate injectRestRemplate() {
		return new RestTemplate();
	}
	
	
}
