package com.chinainpay.apps.membercard.entity.base;

import com.chinainpay.apps.membercard.entity.baseInfo.BaseInfo;

public class Groupon extends WXCard {
	private BaseInfo base_info;
	private String deal_detail;//团购券专用，团购详情
	
	public BaseInfo getBase_info() {
		return base_info;
	}
	public void setBase_info(BaseInfo base_info) {
		this.base_info = base_info;
	}
	public String getDeal_detail() {
		return deal_detail;
	}
	public void setDeal_detail(String deal_detail) {
		this.deal_detail = deal_detail;
	}
	
	
}
