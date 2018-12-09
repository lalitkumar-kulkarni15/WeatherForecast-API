package com.weatherforecast.api.exception;

public class UnauthorisedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UnauthorisedException(final String message) {
		super(message);
	}
}
