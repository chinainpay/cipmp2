package com.chinainpay.apps.membercard.entity.base;

import com.chinainpay.apps.membercard.entity.baseInfo.BaseInfo;

public class MeetingTicket extends WXCard {
	private String meeting_detail;// 会议详情
	private String map_url;// 会场导览图
	private BaseInfo base_info;

	public String getMeeting_detail() {
		return meeting_detail;
	}

	public void setMeeting_detail(String meeting_detail) {
		this.meeting_detail = meeting_detail;
	}

	public String getMap_url() {
		return map_url;
	}

	public void setMap_url(String map_url) {
		this.map_url = map_url;
	}

	public BaseInfo getBase_info() {
		return base_info;
	}

	public void setBase_info(BaseInfo base_info) {
		this.base_info = base_info;
	}

}
