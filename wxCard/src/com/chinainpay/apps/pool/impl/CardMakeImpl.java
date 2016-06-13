package com.chinainpay.apps.pool.impl;

import java.util.TreeMap;

import com.chinainpay.apps.pool.CardMake;
import com.chinainpay.apps.util.MacUtil;

public class CardMakeImpl implements CardMake {

	@Override
	public String cardMakeApply(String orderNo, String totalAmount,
			String clientDate, String clientTime, String merchantNo,
			String storeNo, String userId, String methodID, String mac)
			throws Exception {
		TreeMap<String, String> paramMap = new TreeMap<String, String>();
		paramMap.put("orderNo", orderNo);
		paramMap.put("totalAmount", totalAmount);
		paramMap.put("clientDate", clientDate);
		paramMap.put("clientTime", clientTime);
		paramMap.put("merchantNo", merchantNo);
		paramMap.put("storeNo", storeNo);
		paramMap.put("userId", userId);
		paramMap.put("methodID", methodID);
		TreeMap<String, String> signMap = MacUtil.encryptPwdOrNewPwd(paramMap);
		// String url = weChatServiceurl + "apply?" + MACUtil.getMac(signMap);
		// String result = HttpTool.sendHttpGetRequest(url);
		return "success";
	}

	@Override
	public String cardMakeData(String orderNo, String totalAmount,
			String clientDate, String clientTime, String merchantNo,
			String storeNo, String userId, String methodID, String mac)
			throws Exception {
		// TODO Auto-generated method stub
		return "success";
	}

	@Override
	public String cardStorage(String orderNo, String clientDate,
			String clientTime, String merchantNo, String storeNo,
			String userId, String methodID, String mac) throws Exception {
		// TODO Auto-generated method stub
		return "success";
	}

	@Override
	public String cardMakeData2(String orderNo, String password,
			String clientDate, String clientTime, String merchantNo,
			String storeNo, String userId, String methodID, String mac)
			throws Exception {
		// TODO Auto-generated method stub
		return "success";
	}

}
