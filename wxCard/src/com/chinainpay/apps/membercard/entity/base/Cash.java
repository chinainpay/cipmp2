package com.chinainpay.apps.membercard.entity.base;

import com.chinainpay.apps.membercard.entity.baseInfo.BaseInfo;

public class Cash {
	private BaseInfo base_info;
	private String least_cost;// 代金券专用，表示起用金额（单位为分）
	private String reduce_cost;// 代金券专用，表示减免金额（单位为分）

	public BaseInfo getBase_info() {
		return base_info;
	}

	public void setBase_info(BaseInfo base_info) {
		this.base_info = base_info;
	}

	public String getLeast_cost() {
		return least_cost;
	}

	public void setLeast_cost(String least_cost) {
		this.least_cost = least_cost;
	}

	public String getReduce_cost() {
		return reduce_cost;
	}

	public void setReduce_cost(String reduce_cost) {
		this.reduce_cost = reduce_cost;
	}

}
