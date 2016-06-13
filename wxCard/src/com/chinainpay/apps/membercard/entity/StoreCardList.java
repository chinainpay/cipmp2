package com.chinainpay.apps.membercard.entity;

import java.util.HashMap;
import java.util.Map;

public class StoreCardList {
	String storeName;
	Map<String, Object>  cardlist = new HashMap<String, Object>();

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public Map<String, Object> getCardlist() {
		return cardlist;
	}

	public void setCardlist(Map<String, Object> cardlist) {
		this.cardlist = cardlist;
	}
}
