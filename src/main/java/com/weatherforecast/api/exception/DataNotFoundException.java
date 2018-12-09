package com.weatherforecast.api.exception;

public class DataNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DataNotFoundException(final String message) {
		super(message);
	}

}
