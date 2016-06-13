package com.chinainpay.apps.membercard.entity.base;

import com.chinainpay.apps.membercard.entity.baseInfo.BaseInfo;

public class BoardingPass extends WXCard {
	private BaseInfo base_info;
	private String from;// 起点，上限为 18 个汉字
	private String to;// 终点，上限为 18 个汉字
	private String flight;// 航班
	private String departure_time;// 起飞时间。Unix 时间戳格式
	private String landing_time;// 降落时间。Unix 时间戳格式
	private String check_in_url;// 在线值机的链接
	private String gate;// 登机口。如发生登机口变更，建议商家实时调用该接口变更
	private String boarding_time;// 登机时间，只显示“时分”不显示日期，按时间戳格式填写。如发生登机时间变更，
									// 建议商家实时调用该接口变更
	private String air_model;// 机型，上限为 8 个汉字

	public BaseInfo getBase_info() {
		return base_info;
	}

	public void setBase_info(BaseInfo base_info) {
		this.base_info = base_info;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getFlight() {
		return flight;
	}

	public void setFlight(String flight) {
		this.flight = flight;
	}

	public String getDeparture_time() {
		return departure_time;
	}

	public void setDeparture_time(String departure_time) {
		this.departure_time = departure_time;
	}

	public String getLanding_time() {
		return landing_time;
	}

	public void setLanding_time(String landing_time) {
		this.landing_time = landing_time;
	}

	public String getCheck_in_url() {
		return check_in_url;
	}

	public void setCheck_in_url(String check_in_url) {
		this.check_in_url = check_in_url;
	}

	public String getGate() {
		return gate;
	}

	public void setGate(String gate) {
		this.gate = gate;
	}

	public String getBoarding_time() {
		return boarding_time;
	}

	public void setBoarding_time(String boarding_time) {
		this.boarding_time = boarding_time;
	}

	public String getAir_model() {
		return air_model;
	}

	public void setAir_model(String air_model) {
		this.air_model = air_model;
	}

}
