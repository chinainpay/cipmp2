package com.chinainpay.apps.membercard.entity.baseInfo;

public class DateInfo {
	private int type;//使用时间的类型，仅支持选择一种时间类型的字段填入。1：固定日期区间，2：固定时长（自领取后按天算）

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	
}
