package com.chinainpay.apps.membercard;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import net.javabone.common.util.DaoUtil;
import net.javabone.common.util.RegexpUtils;
import net.javabone.common.web.JsonUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.chinainpay.apps.util.MacUtil;
import com.chinainpay.apps.util.MD5Util;
import com.chinainpay.apps.pool.Trade;
import com.chinainpay.apps.pool.impl.TradeImpl;
import com.chinainpay.apps.util.DriveUtil;
import com.chinainpay.apps.util.HttpTool;
import com.chinainpay.mp.app.pagelet.Pagelet;
import com.chinainpay.mp.app.pagelet.axis.P;

public class Gateway implements Pagelet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DataSource ds = P.ds("base");
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat dateFormat2 = new SimpleDateFormat(
			"yyyyMMddHHmmss");
	@Execute("访问")
	@Pagelet.Purview()
	public StringBuffer execute() throws SQLException, UnsupportedEncodingException {
		//判断是否配置回调地址 获取参数
		Map<String, Object> rMap = new HashMap<String, Object>();
		String merNo = P.mp.getMerchant().getMerCode();
		
		HttpServletRequest request = P.getRequest();
		String merName = request.getParameter("merName");
		String orderNo = request.getParameter("orderNo");//订单号
		String order_content = request.getParameter("order_content");
		String total_fee = request.getParameter("total_fee");//订单内容
		String callback_url = request.getParameter("callback_url");//回调地址
		String gateway_url = request.getParameter("gateway_url");//跳转页面
		String mac = request.getParameter("mac");//mac
		if(orderNo==null){
			rMap.put("code", "01");
			rMap.put("msg", "单号不能为空");
			return new StringBuffer(JsonUtil.encoder((Map<?, ?>) rMap));
		}
	
		if(merName==null){
			rMap.put("code", "02");
			rMap.put("msg", "收款商户不能为空");
			return new StringBuffer(JsonUtil.encoder((Map<?, ?>) rMap));
		}
		if(order_content==null){
			rMap.put("code", "03");
			rMap.put("msg", "订单内容布不能为空");
			return new StringBuffer(JsonUtil.encoder((Map<?, ?>) rMap));
			
		}else{
			if (!RegexpUtils.isPositiveNumber(total_fee)) {
				rMap.put("code", "04");
				rMap.put("msg", "金额填写错误");
				return new StringBuffer(JsonUtil.encoder((Map<?, ?>) rMap));
			}
		}
		if(gateway_url==null){
			rMap.put("code", "06");
			rMap.put("msg", "未填写跳转地址");
			return new StringBuffer(JsonUtil.encoder((Map<?, ?>) rMap));
		}
		if(callback_url==null){
			rMap.put("code", "05");
			rMap.put("msg", "返回页面地址不能为空");
			return new StringBuffer(JsonUtil.encoder((Map<?, ?>) rMap));
		}
//		order_content = new String(request.getParameter("order_content").getBytes("iso-8859-1"), "utf-8");//订单内容
		TreeMap<String, String> paramMap = new TreeMap<String, String>();
		paramMap.put("orderNo", orderNo);
		paramMap.put("total_fee", total_fee);
//		paramMap.put("order_content", order_content);
		paramMap.put("gateway_url", gateway_url);
		paramMap.put("callback_url", callback_url);
		P.log.d(paramMap);
		String paramMapmac =  MacUtil.createMacString(paramMap);
		paramMapmac = paramMapmac+"&key=911B6446AC61BA88BA88911B64994AC1";
		P.log.d(paramMapmac);
		String md5 = MD5Util.MD5Encode(paramMapmac, "UTF-8");
		P.log.d(md5);
		if(!md5.equals(mac.toLowerCase().trim())){
			rMap.put("code", "08");
			rMap.put("msg", "mac校验错误");
			return new StringBuffer(JsonUtil.encoder((Map<?, ?>) rMap));
		}else{
			String time = dateFormat.format(new Date());
			if(DaoUtil.list(ds,0, "select * from T_CIPMP_GATEWAY where ORDER_ID =? ", orderNo).size()==1){
			}else{
				String saveSql = "INSERT INTO T_CIPMP_GATEWAY (ORDER_ID, MER_CODE,MER_NAME, CARD_NO,ORDER_CONTENT,TOTAL_FEE, TIME_END, TIME_START, TRADE_STATE,LAST_USER,CALLBACK_URL,GATEWAY_URL) VALUES (?, ?, ?, ?, ?,?, ?, ?,?,?,?,?)";
				String  msg =DaoUtil.execute(ds, saveSql, orderNo,merNo,merName,"",order_content,total_fee,"",time,"0","",callback_url,gateway_url)== 1 ? "" : "添加临时订单失败";
				if(msg!=""){
					rMap.put("code", "99");
					rMap.put("msg", "异常");
					return new StringBuffer(JsonUtil.encoder((Map<?, ?>) rMap));
				}
			}
			P.setAttribute("orderNo", orderNo);
			P.setAttribute("mer_name", merName);
			P.setAttribute("order_content", order_content);
			P.setAttribute("total_fee", Double.parseDouble(total_fee)/100);
			P.setAttribute("time_start",time);
			P.setAttribute("GATEWAY_URL",gateway_url);
			return null;
		}
	}
	@Execute("支付")
	@Pagelet.Purview("execute")
	public StringBuffer pay(@RequestParam("pojo") HashMap<String, String> pojo) throws SQLException{
		Map<String, Object> rMap = new HashMap<String, Object>();
		//判断支付是否成功 并返回保存信息或留在本页
		List<Map<String, Object>> list = DaoUtil.list(ds, 0, "SELECT * FROM T_CIPMP_MEMBER_CARD_SETUP WHERE MER_CODE= ?", P.mp.getMerchant().getMerCode());
		String CALLBACK_URL = null;
		String GATEWAY_URL = null;
		if(list.size()==0){
			rMap.put("errorMsg", "支付异常");
			return new StringBuffer(JsonUtil.encoder((Map<?, ?>) rMap));
		}
//		if(list.get(0).get("GATEWAY_URL").equals("")){
//			rMap.put("errorMsg", "支付异常");
//			return new StringBuffer(JsonUtil.encoder((Map<?, ?>) rMap));
//		}else{
//			CALLBACK_URL=list.get(0).get("CALLBACK_URL").toString();
//		}
		String orderNo = pojo.get("orderNo") == null ? "" : pojo
				.get("orderNo").toString().trim();
		List<Map<String, Object>> list2 = DaoUtil.list(ds, 0, "SELECT * FROM T_CIPMP_GATEWAY WHERE MER_CODE= ? AND ORDER_ID=?", P.mp.getMerchant().getMerCode(),orderNo);
		String amount = "";
		if(list.size()!=0){
			amount = list2.get(0).get("TOTAL_FEE").toString();
			CALLBACK_URL=list2.get(0).get("CALLBACK_URL").toString();
			GATEWAY_URL=list2.get(0).get("GATEWAY_URL").toString();
		}
		if(list2.get(0).get("TRADE_STATE").equals("1")){
			rMap.put("errorMsg", "重复订单");
			return new StringBuffer(JsonUtil.encoder((Map<?, ?>) rMap));
		}
		String cardNo = pojo.get("cardNo") == null ? "" : pojo
				.get("cardNo").toString().trim();
		String password = pojo.get("password") == null ? "" : pojo
				.get("password").toString().trim();
		if(cardNo.equals("")){
			rMap.put("errorMsg", "卡号不能为空");
			return new StringBuffer(JsonUtil.encoder((Map<?, ?>) rMap));
		}
		if(password.equals("")){
			rMap.put("errorMsg", "密码不能为空");
			return new StringBuffer(JsonUtil.encoder((Map<?, ?>) rMap));
		}
		String dateStr = dateFormat2.format(new Date());
		String clientDate = dateStr.substring(0, 8);
		String clientTime = dateStr.substring(8);
		String mtoreNo = null;
		String terminal = null;
		try {
			mtoreNo = DriveUtil.getDrive().get("mtoreNo").toString();
			terminal = DriveUtil.getDrive().get("terminal").toString();
		} catch (Exception e1) {
			rMap.put("errorMsg", "请先添加支付驱动");
			return new StringBuffer(JsonUtil.encoder((Map<?, ?>) rMap));
		}
		Trade trade = new TradeImpl();
		String storeNo ="";
		String userId="";
			Map<String, Object> list3 = DaoUtil.row(ds,  "SELECT * FROM T_CIPMP_GATEWAY WHERE MER_CODE= ? AND ORDER_ID=?", P.mp.getMerchant().getMerCode(),orderNo);
			String msg ="";
			try {
				msg = trade.Consumption(orderNo, cardNo, cardNo, amount, amount, clientDate, clientTime, mtoreNo, storeNo, terminal, userId, "Y", password);
			} catch (Exception e1) {
				rMap.put("errorMsg", "扣款失败");
				return new StringBuffer(JsonUtil.encoder((Map<?, ?>) rMap));
			}
			P.log.d(msg);
			if(!msg.trim().equals("交易成功")){
				Map<String, Object> newMap = new HashMap<String, Object>();
				newMap.put("ERRCODE", "1");
				newMap.put("ERRMSG", msg);
				newMap.put("ORDER_ID", list3.get("ORDER_ID").toString());
				
				try {
					HttpTool.sendHttpRequest(CALLBACK_URL, "order="+JSONObject.fromObject(newMap).toString());
				} catch (Exception e) {
//					rMap.put("errorMsg",);
//					return new StringBuffer(JsonUtil.encoder((Map<?, ?>) rMap));
					P.log.d("回调地址错误");
				}
				
				rMap.put("errorMsg",msg);
				return new StringBuffer(JsonUtil.encoder((Map<?, ?>) rMap));
			}
			String saveSql = "UPDATE T_CIPMP_GATEWAY SET CARD_NO = ?,TOTAL_FEE =? ,TIME_END = ?,TRADE_STATE = ? WHERE ORDER_ID = ?";
			String time = dateFormat.format(new Date());
			P.log.d(time);
			String  msg2 =DaoUtil.execute(ds, saveSql, cardNo,amount,time,"1",orderNo)== 1 ? "" : "完成订单失败";
			list3 = DaoUtil.row(ds,  "SELECT * FROM T_CIPMP_GATEWAY WHERE MER_CODE= ? AND ORDER_ID=?", P.mp.getMerchant().getMerCode(),orderNo);
			if(msg2.equals("")){
				//通知回调地址
				TreeMap<String, String> paramMap = new TreeMap<String, String>();
				paramMap.put("ERRCODE", "0");
				paramMap.put("TIME_START", dateFormat.format(list3.get("TIME_START")));
				paramMap.put("TIME_END", time);
				paramMap.put("ORDER_ID", list3.get("ORDER_ID").toString());
				paramMap.put("TOTAL_FEE", list3.get("TOTAL_FEE").toString());
				paramMap.put("CARD_NO", list3.get("CARD_NO").toString());
				String paramMapmac =  MacUtil.createMacString(paramMap);
				paramMapmac = paramMapmac+"&key=911B6446AC61BA88BA88911B64994AC1";
				P.log.d(paramMapmac);
				String md5 = MD5Util.MD5Encode(paramMapmac, "UTF-8");
				P.log.d(md5);				Map<String, Object> newMap = new HashMap<String, Object>();
				newMap.put("ERRCODE", "0");
				newMap.put("TIME_START", dateFormat.format(list3.get("TIME_START")));
				newMap.put("TIME_END", time);
				newMap.put("MER_NAME", list3.get("MER_NAME").toString());
				newMap.put("ORDER_ID", list3.get("ORDER_ID").toString());
				newMap.put("TOTAL_FEE", list3.get("TOTAL_FEE").toString());
				newMap.put("CARD_NO", list3.get("CARD_NO").toString());
				newMap.put("ORDER_CONTENT", list3.get("ORDER_CONTENT").toString());
				newMap.put("MAC", md5);
				P.log.d(JSONObject.fromObject(newMap).toString());
				try {
					HttpTool.sendHttpRequest(CALLBACK_URL, "order="+JSONObject.fromObject(newMap).toString());
				} catch (Exception e) {
//					rMap.put("errorMsg","回调地址错误");
//					return new StringBuffer(JsonUtil.encoder((Map<?, ?>) rMap));
					P.log.d("回调地址错误");
				}
				rMap.put("errorMsg", "");
				try {
					P.log.d(GATEWAY_URL+"?order="+URLEncoder.encode(JSONObject.fromObject(newMap).toString(), "UTF-8"));
					rMap.put("GATEWAY_URL", GATEWAY_URL+"?order="+URLEncoder.encode(JSONObject.fromObject(newMap).toString(), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					P.log.d("跳转地址错误");
				}
				return new StringBuffer(JsonUtil.encoder((Map<?, ?>) rMap));
			}
		return null;
		
	}
}
