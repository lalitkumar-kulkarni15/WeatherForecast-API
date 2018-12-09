package com.weatherforecast.api.test.utils;

public interface ITestUtils {

	public static String createURLWithPort(final String uri, final String host, final String port) {
		return "http://" + host + ":" + port + uri;
	}

}
