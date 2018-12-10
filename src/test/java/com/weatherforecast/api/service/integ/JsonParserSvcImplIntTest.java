package com.weatherforecast.api.service.integ;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import com.fasterxml.jackson.databind.JsonNode;
import com.weatherforecast.api.WeathrFrcstAppMain;
import com.weatherforecast.api.service.IParser;

@RunWith(SpringRunner.class)
@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,classes= {WeathrFrcstAppMain.class})
@TestPropertySource(locations="classpath:test.properties")
public class JsonParserSvcImplIntTest {
	
	@Autowired
	private IParser parser;
	
	@Test
	public void parseDataTest_parsesJsonSuccessfully() throws IOException {
		
		ClassPathResource res = new ClassPathResource("src/test/resources/testdata/weatherForecastData.json");
		File file = new File(res.getPath());
		String content = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
		JsonNode jsonNode = parser.parseData(content);
		assertNotNull(jsonNode);
		
	}
	
	@Test
	public void parseDataTest_parsesJsonDateSuccessfully() throws IOException {
		
		ClassPathResource res = new ClassPathResource("src/test/resources/testdata/weatherForecastData.json");
		File file = new File(res.getPath());
		String content = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
		JsonNode jsonNode = parser.parseData(content);
		String dateTime = jsonNode.path("dt_txt").asText();
		assertNotNull(dateTime);
		
	}
	
	@Test
	public void parseDataTest_parsesJsonTempSuccessfully() throws IOException {
		
		ClassPathResource res = new ClassPathResource("src/test/resources/testdata/weatherForecastData.json");
		File file = new File(res.getPath());
		String content = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
		JsonNode jsonNode = parser.parseData(content);
		String temp = jsonNode.path("main").path("temp").asText();
		assertNotNull(temp);
		
	}
	
	@Test
	public void parseDataTest_parsesJsonPressureSuccessfully() throws IOException {
		
		ClassPathResource res = new ClassPathResource("src/test/resources/testdata/weatherForecastData.json");
		File file = new File(res.getPath());
		String content = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
		JsonNode jsonNode = parser.parseData(content);
		String pressure = jsonNode.path("main").path("pressure").asText();
		assertNotNull(pressure);
		
	}

}
