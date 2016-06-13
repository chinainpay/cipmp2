package com.chinainpay.apps.pool.impl;

import java.net.URLEncoder;
import java.util.TreeMap;

import net.sf.json.JSONObject;

import com.chinainpay.apps.pool.CardService;
import com.chinainpay.apps.pool.CulwsServices;
import com.chinainpay.apps.util.HttpTool;
import com.chinainpay.mp.app.pagelet.axis.P;
import com.cul.ws.imtxn.service.bean.xsd.CodeMsg;

public class CardServiceImpl implements CardService {
	@Override
	public String cardState(String cardNoFrom, String cardNoTo, String type,
			String clientDate, String clientTime, String merchantNo,
			String storeNo, String userId, String terminal) throws Exception {
		TreeMap<String, String> paramMap = new TreeMap<String, String>();
		paramMap.put("cardNoFrom", URLEncoder.encode(cardNoFrom, "utf-8"));
		paramMap.put("cardNoTo", URLEncoder.encode(cardNoTo, "utf-8"));
		paramMap.put("type", URLEncoder.encode(type, "utf-8"));
		paramMap.put("clientDate", URLEncoder.encode(clientDate, "utf-8"));
		paramMap.put("clientTime", URLEncoder.encode(clientTime, "utf-8"));
		paramMap.put("merchantNo", URLEncoder.encode(merchantNo, "utf-8"));
		paramMap.put("storeNo", URLEncoder.encode(storeNo, "utf-8"));
		paramMap.put("userId", URLEncoder.encode(userId, "utf-8"));
		paramMap.put("driveNo", URLEncoder.encode("0101", "utf-8"));
		paramMap.put("terminal", URLEncoder.encode(terminal, "utf-8"));
		paramMap.put("serviceName", URLEncoder.encode("cardState", "utf-8"));
		TreeMap<String, String> signMap = SignMap.encryptPwdOrNewPwd(paramMap);
		String zhi  = SignMap.getMac(signMap);
		String url1 = P.getContextParameter("yszxUrl").toString()+ zhi;
//		String result =HttpTool.sendHttpGetRequest(url1);
		String result = "{'code':'00','msg':'测试成功'}";
		P.log.d("接口返回数据" + result);
		JSONObject jsonObject = JSONObject.fromObject(result);
		P.log.d(jsonObject.get("code"));
		result = jsonObject.get("code").toString().equals("00") ?  jsonObject.get("msg").toString() : jsonObject.get("msg").toString();
		return result;
	}

	@Override
	public String cardChange(String cardNoFrom, String cardNoTo,
			String password, String clientDate, String clientTime,
			String merchantNo, String storeNo, String userId, String mac)
			throws Exception {
		CodeMsg data = CulwsServices.ictv(merchantNo, userId, cardNoTo,
				cardNoFrom, password);
		return data.getMsg();
	}

	@Override
	public String cardReqExpire(String cardNoFrom, String cardNoTo,
			String reqExpDate, String clientDate, String clientTime,
			String merchantNo, String storeNo, String userId, String mac)
			throws Exception {
		CodeMsg data = CulwsServices.batchPostpone(merchantNo, userId,
				reqExpDate, cardNoFrom, cardNoTo);
		return data.toString();
	}

	@Override
	public String cardRecycling(String cardNoFrom, String cardNoTo,
			String clientDate, String clientTime, String merchantNo,
			String storeNo, String userId, String mac) throws Exception {
		CodeMsg data = CulwsServices.irec(merchantNo, userId, cardNoTo,
				cardNoFrom);
		return data.toString();
	}

	@Override
	public String cardPwdReset(String cardNo, String password,
			String clientDate, String clientTime, String merchantNo,
			String storeNo, String userId, String terminal) throws Exception {
		TreeMap<String, String> paramMap = new TreeMap<String, String>();
		paramMap.put("cardNo", URLEncoder.encode(cardNo, "utf-8"));
		paramMap.put("password", URLEncoder.encode(password, "utf-8"));
		paramMap.put("clientDate", URLEncoder.encode(clientDate, "utf-8"));
		paramMap.put("clientTime", URLEncoder.encode(clientTime, "utf-8"));
		paramMap.put("merchantNo", URLEncoder.encode(merchantNo, "utf-8"));
		paramMap.put("storeNo", URLEncoder.encode(storeNo, "utf-8"));
		paramMap.put("userId", URLEncoder.encode(userId, "utf-8"));
		paramMap.put("driveNo", URLEncoder.encode("0101", "utf-8"));
		paramMap.put("terminal", URLEncoder.encode(terminal, "utf-8"));
		paramMap.put("serviceName", URLEncoder.encode("cardPwdReset", "utf-8"));
		TreeMap<String, String> signMap = SignMap.encryptPwdOrNewPwd(paramMap);
		String zhi  = SignMap.getMac(signMap);
		String url1 = P.getContextParameter("yszxUrl").toString()+ zhi;
		//String result =HttpTool.sendHttpGetRequest(url1);
		String result = "{'code':'00','msg':'充值成功'}";
		P.log.d("接口返回数据" + result);
		JSONObject jsonObject = JSONObject.fromObject(result);
		P.log.d(jsonObject.get("code"));
		result = jsonObject.get("code").toString().equals("00") ?  jsonObject.get("msg").toString() : jsonObject.get("msg").toString();
		return result;
	}

	@Override
	public String cardPwdChange(String cardNo, String oldPassword,
			String newPassword, String clientDate, String clientTime,
			String merchantNo, String storeNo, String userId, String terminal)
			throws Exception {
		TreeMap<String, String> paramMap = new TreeMap<String, String>();
		paramMap.put("cardNo", URLEncoder.encode(cardNo, "utf-8"));
		paramMap.put("oldPassword", URLEncoder.encode(oldPassword, "utf-8"));
		paramMap.put("newPassword", URLEncoder.encode(newPassword, "utf-8"));
		paramMap.put("clientDate", URLEncoder.encode(clientDate, "utf-8"));
		paramMap.put("clientTime", URLEncoder.encode(clientTime, "utf-8"));
		paramMap.put("merchantNo", URLEncoder.encode(merchantNo, "utf-8"));
		paramMap.put("storeNo", URLEncoder.encode(storeNo, "utf-8"));
		paramMap.put("userId", URLEncoder.encode(userId, "utf-8"));
		paramMap.put("driveNo", URLEncoder.encode("0101", "utf-8"));
		paramMap.put("terminal", URLEncoder.encode(terminal, "utf-8"));
		paramMap.put("serviceName", URLEncoder.encode("cardPwdChange", "utf-8"));
		TreeMap<String, String> signMap = SignMap.encryptPwdOrNewPwd(paramMap);
		String zhi  = SignMap.getMac(signMap);
		String url1 = P.getContextParameter("yszxUrl").toString()+ zhi;
//		String result =HttpTool.sendHttpGetRequest(url1);
		String result = "{'code':'00','msg':'重置密码成功'}";
		P.log.d("接口返回数据" + result);
		JSONObject jsonObject = JSONObject.fromObject(result);
		P.log.d(jsonObject.get("code"));
		result = jsonObject.get("code").toString().equals("00") ?  jsonObject.get("msg").toString() : jsonObject.get("msg").toString();
		return result;
	}

	@Override
	public String cardPwdNums(String cardNo, String clientDate,
			String clientTime, String merchantNo, String storeNo,
			String userId, String mac) throws Exception {
		CodeMsg data = CulwsServices.rstpt(merchantNo, userId, cardNo);
		return data.toString();
	}

}
