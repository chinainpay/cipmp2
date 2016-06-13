package com.chinainpay.apps.membercard.entity.base;

public class Sku {
	private String quantity;//卡券库存的数量。 （不支持填写 0或无限大）

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	
}
