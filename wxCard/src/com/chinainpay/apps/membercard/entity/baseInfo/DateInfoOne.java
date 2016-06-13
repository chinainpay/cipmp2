package com.chinainpay.apps.membercard.entity.baseInfo;


public class DateInfoOne extends DateInfo{
	
	private String begin_timestamp;//type 为1 时专用， 表示起用时间。（单位为秒）
	private String end_timestamp;//type 为1 时专用， 表示结束时间（单位为秒）
	
	public String getBegin_timestamp() {
		return begin_timestamp;
	}
	public void setBegin_timestamp(String begin_timestamp) {
		this.begin_timestamp = begin_timestamp;
	}
	public String getEnd_timestamp() {
		return end_timestamp;
	}
	public void setEnd_timestamp(String end_timestamp) {
		this.end_timestamp = end_timestamp;
	}
	
	
}
