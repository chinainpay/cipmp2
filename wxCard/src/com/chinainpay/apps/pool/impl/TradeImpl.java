package com.chinainpay.apps.pool.impl;

import java.net.URLEncoder;
import java.util.TreeMap;

import net.sf.json.JSONObject;

import com.chinainpay.apps.pool.CulwsServices;
import com.chinainpay.apps.pool.Trade;
import com.chinainpay.apps.util.HttpTool;
import com.chinainpay.mp.app.pagelet.axis.P;
import com.cul.ws.imtxn.service.bean.xsd.CodeMsg;
import com.cul.ws.imtxn.service.bean.xsd.IdadBillData;

public class TradeImpl implements Trade {

	@Override
	public String Recharge(String orderNo, String cardNoForm, String cardNoTo,
			String amount, String totalAmount, String clientDate,
			String clientTime, String merchantNo, String storeNo,
			String userId, String terminal) throws Exception {
		amount =  ((int)Double.parseDouble(amount) )+ "";
		totalAmount = ((int)Double.parseDouble(totalAmount) )+ "";
		TreeMap<String, String> paramMap = new TreeMap<String, String>();
		paramMap.put("orderNo", URLEncoder.encode(orderNo, "utf-8"));
		paramMap.put("cardNoFrom", URLEncoder.encode(cardNoForm, "utf-8"));
		paramMap.put("cardNoTo", URLEncoder.encode(cardNoTo, "utf-8"));
		paramMap.put("amount", URLEncoder.encode(amount, "utf-8"));
		paramMap.put("totalAmount", URLEncoder.encode(totalAmount, "utf-8"));
		paramMap.put("clientDate", URLEncoder.encode(clientDate, "utf-8"));
		paramMap.put("clientTime", URLEncoder.encode(clientTime, "utf-8"));
		paramMap.put("merchantNo", URLEncoder.encode(merchantNo, "utf-8"));
		paramMap.put("storeNo", URLEncoder.encode(storeNo, "utf-8"));
		paramMap.put("userId", URLEncoder.encode(userId, "utf-8"));
		paramMap.put("driveNo", URLEncoder.encode("0101", "utf-8"));
		paramMap.put("terminal", URLEncoder.encode(terminal, "utf-8"));
		paramMap.put("serviceName", URLEncoder.encode("recharge", "utf-8"));
		TreeMap<String, String> signMap = SignMap.encryptPwdOrNewPwd(paramMap);
		String url1 = P.getContextParameter("yszxUrl").toString() + SignMap.getMac(signMap);
		P.log.d(url1);
//		String result = HttpTool.sendHttpGetRequest(url1);
		String result = "{'code':'00','msg':'充值成功'}";
		if (result.equals("url1")) {
			return "系统繁忙";
		}
		P.log.d("接口返回数据" + result);
		JSONObject jsonObject = JSONObject.fromObject(result);
		P.log.d(jsonObject.get("code"));
		result = jsonObject.get("code").toString().equals("00") ? jsonObject
				.get("msg").toString() : jsonObject.get("msg").toString();
		return result;

	}

	@Override
	public String cancelRecharge(String orderNo, String cardNoFrom,
			String cardNoTo, String amount, String totalAmount,
			String clientDate, String clientTime, String merchantNo,
			String storeNo, String userId, String mac) throws Exception {
		CodeMsg data = CulwsServices.idvv(merchantNo, userId, cardNoTo,
				cardNoFrom, totalAmount, amount, storeNo);
		return data.toString();
	}

	@Override
	public String Consumption(String orderNo, String cardNoFrom,
			String cardNoTo, String amount, String totalAmount,
			String clientDate, String clientTime, String merchantNo,
			String storeNo, String terminal, String userId, String isverifyPwd,
			String password) throws Exception {
		TreeMap<String, String> paramMap = new TreeMap<String, String>();
		paramMap.put("orderNo", URLEncoder.encode(orderNo, "utf-8"));
		paramMap.put("cardNoTo", URLEncoder.encode(cardNoTo, "utf-8"));
		paramMap.put("cardNoFrom", URLEncoder.encode(cardNoFrom, "utf-8"));
		paramMap.put("amount", URLEncoder.encode(amount, "utf-8"));
		paramMap.put("totalAmount", URLEncoder.encode(totalAmount, "utf-8"));
		paramMap.put("clientDate", URLEncoder.encode(clientDate, "utf-8"));
		paramMap.put("clientTime", URLEncoder.encode(clientTime, "utf-8"));
		paramMap.put("merchantNo", URLEncoder.encode(merchantNo, "utf-8"));
		paramMap.put("isverifyPwd",URLEncoder.encode(isverifyPwd, "utf-8"));
		paramMap.put("storeNo", URLEncoder.encode(storeNo, "utf-8"));
		paramMap.put("userId", URLEncoder.encode(userId, "utf-8"));
		paramMap.put("driveNo", URLEncoder.encode("0101", "utf-8"));
		paramMap.put("terminal", URLEncoder.encode(terminal, "utf-8"));
		paramMap.put("password", URLEncoder.encode(password, "utf-8"));
		paramMap.put("serviceName", URLEncoder.encode("consumption", "utf-8"));
		TreeMap<String, String> signMap = SignMap.encryptPwdOrNewPwd(paramMap);
		String url1 = P.getContextParameter("yszxUrl").toString() + SignMap.getMac(signMap);
		P.log.d(url1);
//		String result = HttpTool.sendHttpGetRequest(url1);
		String result = "{'code':'00','msg':'测试成功'}";
		P.log.d("接口返回数据" + result);
		JSONObject jsonObject = JSONObject.fromObject(result);
		P.log.d(jsonObject.get("code"));
		result = jsonObject.get("code").toString().equals("00") ? jsonObject
				.get("msg").toString() : jsonObject.get("msg").toString();
		return result;
	}

	@Override
	public String cancelConsumption(String orderNo, String cardNo,
			String amount, String clientDate, String clientTime,
			String merchantNo, String storeNo, String terminal, String userId,
			String mac) throws Exception {
		return null;
	}

	@Override
	public String Transfer(String orderNo, String cardNoFrom, String cardNoTo,
			String amount, String clientDate, String clientTime,
			String merchantNo, String storeNo, String userId,
			String isverifyPwd, String password, String mac) throws Exception {
		CodeMsg data = CulwsServices.itrf(merchantNo, userId, cardNoTo,
				cardNoFrom, password, amount);
		return data.toString();
	}

}
