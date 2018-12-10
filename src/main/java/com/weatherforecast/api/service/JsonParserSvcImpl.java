package com.weatherforecast.api.service;

import java.io.IOException;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * <p>This class has a responsibility of parsing the input json string message and convert it to the
 * JsonNode object which is used for furthur data processing purpose by the service classes
 * @author  lalitkumar kulkarni
 * @since   08-12-2018
 * @version 1.0
 */
@Service
public class JsonParserSvcImpl implements IParser {

	public JsonNode parseData(final String data) throws IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readTree(data);
		return rootNode.path("list");

	}
}
