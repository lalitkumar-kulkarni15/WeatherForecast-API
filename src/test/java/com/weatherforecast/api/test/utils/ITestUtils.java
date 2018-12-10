package com.weatherforecast.api.test.utils;

import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface ITestUtils {

	public static String createURLWithPort(final String uri, final String host, final String port) {
		return "http://" + host + ":" + port + uri;
	}
	
	public static JsonNode getJsonNode(final String data) throws IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readTree(data);
		return rootNode.path("list");

	}

}
