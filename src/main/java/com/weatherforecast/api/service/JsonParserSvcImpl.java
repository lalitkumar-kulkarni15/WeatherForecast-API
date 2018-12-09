package com.weatherforecast.api.service;

import java.io.IOException;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class JsonParserSvcImpl implements IParser {

	public JsonNode parseData(final String data) throws IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readTree(data);
		return rootNode.path("list");

	}
}
