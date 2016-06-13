package com.chinainpay.apps.pool.impl;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.chinainpay.apps.pool.Flow;
import com.chinainpay.apps.util.HttpTool;
import com.chinainpay.mp.app.pagelet.axis.P;

public class FlowImpl implements Flow {

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> cardInfo(String cardNoForm,
			String cardNoTo, String isverifyPwd, String password,
			String isPager, String pageNo, String pageSize, String merchantNo,
			String storeNo, String userId, String terminal) throws Exception {
		TreeMap<String, String> paramMap = new TreeMap<String, String>();
		paramMap.put("cardNoFrom", URLEncoder.encode(cardNoForm, "utf-8"));
		paramMap.put("cardNoTo", URLEncoder.encode(cardNoTo, "utf-8"));
		paramMap.put("isverifyPwd", URLEncoder.encode(isverifyPwd, "utf-8"));
		paramMap.put("password", URLEncoder.encode(password, "utf-8"));
		paramMap.put("isPager", URLEncoder.encode(isPager, "utf-8"));
		paramMap.put("pageNo", URLEncoder.encode(pageNo, "utf-8"));
		paramMap.put("pageSize", URLEncoder.encode(pageSize, "utf-8"));
		paramMap.put("merchantNo", URLEncoder.encode(merchantNo, "utf-8"));
		paramMap.put("storeNo", URLEncoder.encode(storeNo, "utf-8"));
		paramMap.put("userId", URLEncoder.encode(userId, "utf-8"));
		paramMap.put("driveNo", URLEncoder.encode("0101", "utf-8"));
		paramMap.put("terminal", URLEncoder.encode(terminal, "utf-8"));
		paramMap.put("serviceName", URLEncoder.encode("cardInfo", "utf-8"));
		TreeMap<String, String> signMap = SignMap.encryptPwdOrNewPwd(paramMap);
		String url1 = P.getContextParameter("yszxUrl").toString() + SignMap.getMac(signMap);
		P.log.d("会员信息请求" + url1);
		String result = HttpTool.sendHttpGetRequest(url1);
		P.log.d("会员信息返回" + result);
		JSONObject jsonObject = JSONObject.fromObject(result);
		if (jsonObject.get("code").equals("00")) {
			result = jsonObject.get("dataList").toString();
			JSONArray array = JSONArray.fromObject(result);
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < array.size(); i++) {
				Map<String, Object> object = new HashMap<String, Object>();
				JSONObject jsonObject2 = array.getJSONObject(i);
				object.put("cardNo", jsonObject2.get("cardNo"));
				object.put("balance", jsonObject2.get("balance"));
				object.put("activedDate", jsonObject2.get("activedDate"));
				object.put("statusName", jsonObject2.get("statusName"));
				object.put("hotReasonName", jsonObject2.get("hotReasonName"));
				object.put("expiredDate", jsonObject2.get("expiredDate"));
				object.put("loyaltyClubName",
						jsonObject2.get("loyaltyClubName"));
				object.put("issuerCreateUser",
						jsonObject2.get("issuerCreateUser"));
				list.add(object);
			}
			return list;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> tradeDetails(String cardNo,
			String isverifyPwd, String password, String isPager, String pageNo,
			String pageSize, String merchantNo, String storeNo, String userId,
			String type, String dateFrom, String dateTo, String terminal)
			throws Exception {
		TreeMap<String, String> paramMap = new TreeMap<String, String>();
		paramMap.put("cardNo", URLEncoder.encode(cardNo, "utf-8"));
		paramMap.put("isverifyPwd", URLEncoder.encode(isverifyPwd, "utf-8"));
		paramMap.put("password", URLEncoder.encode(password, "utf-8"));
		paramMap.put("isPager", URLEncoder.encode(isPager, "utf-8"));
		paramMap.put("pageNo", URLEncoder.encode(pageNo, "utf-8"));
		paramMap.put("pageSize", URLEncoder.encode(pageSize, "utf-8"));
		paramMap.put("merchantNo", URLEncoder.encode(merchantNo, "utf-8"));
		paramMap.put("storeNo", URLEncoder.encode(storeNo, "utf-8"));
		paramMap.put("userId", URLEncoder.encode(userId, "utf-8"));
		paramMap.put("type", URLEncoder.encode(type, "utf-8"));
		paramMap.put("dateFrom", URLEncoder.encode(dateFrom, "utf-8"));
		paramMap.put("dateTo", URLEncoder.encode(dateTo, "utf-8"));
		paramMap.put("serviceName", URLEncoder.encode("cardTradeData", "utf-8"));
		paramMap.put("driveNo", URLEncoder.encode("0101", "utf-8"));
		paramMap.put("terminal", URLEncoder.encode(terminal, "utf-8"));
		TreeMap<String, String> signMap = SignMap.encryptPwdOrNewPwd(paramMap);
		String url1 = P.getContextParameter("yszxUrl").toString() + SignMap.getMac(signMap);
		P.log.d("接口请求连接" + url1);
		String result = HttpTool.sendHttpGetRequest(url1);
		P.log.d("接口返回数据" + result);
		try {
			JSONObject jsonObject = JSONObject.fromObject(result);
			if (jsonObject.get("code").equals("00")) {
				if (jsonObject.get("dataList") == null) {
					return null;
				}
				result = jsonObject.get("dataList").toString();
				JSONArray array = JSONArray.fromObject(result);
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				for (int i = 0; i < array.size(); i++) {
					Map<String, Object> object = new HashMap<String, Object>();
					JSONObject jsonObject2 = array.getJSONObject(i);
					object.put("txnId", jsonObject2.get("txnId"));
					object.put("inputChannel", jsonObject2.get("inputChannel"));
					object.put("txnCode", jsonObject2.get("txnCode"));
					object.put("earnAmount", jsonObject2.get("earnAmount"));
					object.put("redeemAmount", jsonObject2.get("redeemAmount"));
					object.put("transferAmount",
							jsonObject2.get("transferAmount"));
					object.put("adjustAmount", jsonObject2.get("adjustAmount"));
					object.put("status", jsonObject2.get("status"));
					object.put("txnTime", jsonObject2.get("txnTime"));
					object.put("merchantNo", jsonObject2.get("merchantNo"));
					list.add(object);
				}
				int sum = (Integer.parseInt(jsonObject.get("total").toString()) - 1) / 10 + 1;
				P.setAttribute("total", sum);
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

}
