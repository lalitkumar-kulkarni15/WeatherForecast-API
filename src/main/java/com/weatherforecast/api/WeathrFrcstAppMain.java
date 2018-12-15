package com.weatherforecast.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@EnableCaching
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan("com.weatherforecast.api*")
public class WeathrFrcstAppMain {

	public static void main(String[] args) {
		SpringApplication.run(WeathrFrcstAppMain.class, args);
	}
}
