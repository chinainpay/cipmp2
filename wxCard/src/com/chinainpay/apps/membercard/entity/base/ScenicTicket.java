package com.chinainpay.apps.membercard.entity.base;

import com.chinainpay.apps.membercard.entity.baseInfo.BaseInfo;

public class ScenicTicket extends WXCard {
	private BaseInfo base_info;
	private String ticket_class;// 票类型， 例如平日全票， 套票等
	private String guide_url;// 导览图 url

	public BaseInfo getBase_info() {
		return base_info;
	}

	public void setBase_info(BaseInfo base_info) {
		this.base_info = base_info;
	}

	public String getTicket_class() {
		return ticket_class;
	}

	public void setTicket_class(String ticket_class) {
		this.ticket_class = ticket_class;
	}

	public String getGuide_url() {
		return guide_url;
	}

	public void setGuide_url(String guide_url) {
		this.guide_url = guide_url;
	}

}
