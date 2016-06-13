package com.chinainpay.apps.membercard.entity.baseInfo;

public class DateInfoTwo extends DateInfo{
	
	private String fixed_term;//type 为 2 时专用，表示自领取后多少天内有效。（单位为天）领取后当天有效填写 0
	private String fixed_begin_term;//type 为 2 时专用，表示自领取后多少天开始生效。（单位为天）
	
	public String getFixed_term() {
		return fixed_term;
	}
	public void setFixed_term(String fixed_term) {
		this.fixed_term = fixed_term;
	}
	public String getFixed_begin_term() {
		return fixed_begin_term;
	}
	public void setFixed_begin_term(String fixed_begin_term) {
		this.fixed_begin_term = fixed_begin_term;
	}
	
	
}
