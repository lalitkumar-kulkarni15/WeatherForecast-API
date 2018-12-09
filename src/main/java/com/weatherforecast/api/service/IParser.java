package com.weatherforecast.api.service;

import java.io.IOException;
import com.fasterxml.jackson.databind.JsonNode;

public interface IParser {
	
	public JsonNode parseData(final String data) throws IOException;

}
