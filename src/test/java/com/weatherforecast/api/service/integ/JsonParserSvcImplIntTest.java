package com.weatherforecast.api.service.integ;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.weatherforecast.api.WeathrFrcstAppMain;
import com.weatherforecast.api.service.IParser;

@RunWith(SpringRunner.class)
@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,classes= {WeathrFrcstAppMain.class})
@TestPropertySource(locations="classpath:test.properties")
public class JsonParserSvcImplIntTest {
	
	@Autowired
	private IParser parser;
	
	@Test
	public void parseDataTest() throws IOException {
		
		ClassPathResource res = new ClassPathResource("src/test/resources/testdata/weatherForecastData.json");
		File file = new File(res.getPath());
		String content = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
		parser.parseData(content);
		
	}

}
