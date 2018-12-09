package com.weatherforecast.api.model;

public class WethrForecastReq {

	private String cityNm;
	
	private String cntryCd;

	private String respCntntTyp;
	
	public WethrForecastReq() {
		
	}
	
	public WethrForecastReq(String cityNm, String cntryCd, String respCntntTyp) {
		super();
		this.cityNm = cityNm;
		this.cntryCd = cntryCd;
		this.respCntntTyp = respCntntTyp;
	}

	public String getCityNm() {
		return cityNm;
	}

	public void setCityNm(String cityNm) {
		this.cityNm = cityNm;
	}

	public String getCntryCd() {
		return cntryCd;
	}

	public void setCntryCd(String cntryCd) {
		this.cntryCd = cntryCd;
	}

	public String getRespCntntTyp() {
		return respCntntTyp;
	}

	public void setRespCntntTyp(String respCntntTyp) {
		this.respCntntTyp = respCntntTyp;
	}

}
