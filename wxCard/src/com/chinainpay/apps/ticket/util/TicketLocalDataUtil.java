package com.chinainpay.apps.ticket.util;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.sql.DataSource;

import net.javabone.common.util.DaoUtil;
import net.javabone.common.util.RegexpUtils;
import net.javabone.common.util.TransactionDataSource;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.chinainpay.apps.util.DateUtil;
import com.chinainpay.apps.util.HttpURLConnectionUtil;
import com.chinainpay.mp.app.pagelet.axis.P;
import com.chinainpay.mp.mq.client.ServiceNotFoundException;
import com.chinainpay.mp.mq.client.ServiceTimeoutException;

public class TicketLocalDataUtil {
	private static DataSource ds = P.ds("base");

	/**
	 * 将卡票券添加到数据库
	 * 
	 * jsonObj 保存的json字符串 cardType 卡券类型 card_id 同步到微信返回的card_id
	 * 
	 * @param json
	 * @return
	 * @throws SQLException
	 */
	public static String saveToDate(JSONObject jsonObj, String cardType,String wx_card_id)
			throws SQLException {
		TransactionDataSource tds = new TransactionDataSource(ds);		
		String brandName="";
		String brandSql="SELECT MER_NAME FROM T_CIPMP_MER WHERE MER_CODE=?";
		Map<String,Object> map=DaoUtil.row(tds, brandSql, P.mp.getMerchant().getMerCode());
		if(map!=null){
			brandName=map.get("MER_NAME").toString();
		}
		String msg =validateTicket(jsonObj);
		if(!msg.equals("ok")){
			return msg;
		}
		StringBuffer keyStr = new StringBuffer();
		StringBuffer valueStr = new StringBuffer();

		JSONObject dateInfoObj = JSONObject.fromObject(jsonObj
				.getString("date_info"));
		keyStr.append("(base_info_id");
		String baseInfoId = UUID.randomUUID().toString();
		valueStr.append("('" + baseInfoId + "'");
		keyStr.append(",MER_CODE");
		valueStr.append(",'" + P.mp.getMerchant().getMerCode() + "'");
		if (Integer.parseInt(dateInfoObj.get("type").toString()) == 1) {
			keyStr.append(",date_info_type");
			valueStr.append(",'1'");
			keyStr.append(",begin_timestamp");
			valueStr.append(",'" + dateInfoObj.getString("begin_timestamp")+ "'");
			keyStr.append(",end_timestamp");
			valueStr.append(",'" + dateInfoObj.getString("end_timestamp")+ "'");
		} else if (Integer.parseInt(dateInfoObj.get("type").toString()) == 2) {
			keyStr.append(",date_info_type");
			valueStr.append(",'2'");
			keyStr.append(",fixed_term");
			valueStr.append(",'" + dateInfoObj.getString("fixed_term")
					+ "'");
			keyStr.append(",fixed_begin_term");
			valueStr.append(",'" + dateInfoObj.getString("fixed_begin_term") + "'");
		}
		keyStr.append(",sku_total_quantity");
		valueStr.append(",'" + jsonObj.getString("quantity") + "'");
		keyStr.append(",sku_quantity");
		valueStr.append(",'" + jsonObj.getString("quantity") + "'");
		JSONObject skuReturnJson = new JSONObject();
		skuReturnJson.put("quantity",
				Integer.parseInt(jsonObj.getString("quantity")));

		keyStr.append(",logo_url");
		valueStr.append(",'https://mmbiz.qlogo.cn/mmbiz/qqNSkJXiawtScBYcNdic4SlTqXicY74QHraawnLyNBDh87CibP2kB47RqDricnRj68wI0OibBosiapnDBWVFIAbt683XA/0?wx_fmt=gif'");
		keyStr.append(",code_type");
		valueStr.append(",'" + jsonObj.getString("code_type") + "'");
		keyStr.append(",brand_name");
		valueStr.append(",'"+brandName+"'");
		keyStr.append(",title");
		valueStr.append(",'" + jsonObj.getString("title") + "'");
		if (jsonObj.containsKey("sub_title") && StringUtils.isNotEmpty(jsonObj.getString("sub_title"))) {
			keyStr.append(",sub_title");
			valueStr.append(",'" + jsonObj.getString("sub_title") + "'");
		}
		String color=jsonObj.containsKey("color")?jsonObj.getString("color"):"Color010";
		keyStr.append(",color");
		valueStr.append(",'" + color + "'");
		keyStr.append(",notice");
		valueStr.append(",'" + jsonObj.getString("notice") + "'");
		keyStr.append(",description");
		valueStr.append(",'" + jsonObj.getString("description") + "'");

		 if(jsonObj.containsKey("shopidlist") && StringUtils.isNotEmpty(jsonObj.getString("shopidlist"))){
		 keyStr.append(",location_id_list");
		 valueStr.append(","+jsonObj.getString("shopidlist"));
		 }
		keyStr.append(",can_share");
		valueStr.append(",'false'");
		keyStr.append(",can_give_friend");
		valueStr.append(",'false'");
		if (StringUtils.isNotEmpty(jsonObj.getString("get_limit"))) {
			keyStr.append(",get_limit");
			valueStr.append(",'" + jsonObj.getString("get_limit") + "'");
		}
		if (StringUtils.isNotEmpty(jsonObj.getString("service_phone"))) {
			keyStr.append(",service_phone");
			valueStr.append(",'" + jsonObj.getString("service_phone") + "'");
		}
		keyStr.append(",source)");
		valueStr.append(",'高汇通商业管理有限公司')");
		P.log.d("》》》》》》》》》》》》》》SQL中KEY的值：" + keyStr);
		P.log.d("》》》》》》》》》》》》》》SQL中VALUE的值：" + valueStr);
		StringBuilder sql = new StringBuilder();
		sql.append(" INSERT INTO T_CIPMP_BASE_INFO");
		sql.append(keyStr);
		sql.append(" VALUES");
		sql.append(valueStr);
		P.log.d("》》》》》》》》》》》》》》添加到数据库的SQL：" + sql);
		DaoUtil.execute(tds, sql.toString());
		
		
		//各自添加到各自的数据库
		List<Object> param = new ArrayList<Object>();
		StringBuilder key=new StringBuilder("MER_CODE,wx_card_id,card_id,base_info_id,create_time");
		StringBuilder value=new StringBuilder("?,?,?,?,?");
		
		param.add(P.mp.getMerchant().getMerCode());
		if(wx_card_id.equals("")){
			wx_card_id="";
			key.append(",status");
			value.append(",''");
		}else{
			key.append(",status");
			value.append(",'CARD_STATUS_NOT_VERIFY'");
		}
		param.add(wx_card_id);
		param.add(cardType+UUID.randomUUID().toString());
		param.add(baseInfoId);
		param.add(new Date());
		
		if (cardType.compareToIgnoreCase("DISCOUNT") == 0) {
			if(RegexpUtils.isZIndex(jsonObj.getString("discount")) || RegexpUtils.isPosttiveFloat(jsonObj.getString("discount"))){//判断是否为浮点数
				if(Double.parseDouble(jsonObj.getString("discount").toString())<0.1 || Double.parseDouble(jsonObj.getString("discount").toString())>10){
					msg="折扣额度只能是大于0.1且小于10的数字";
					return msg;
				}
			}else{
				msg="折扣额度类型错误";
				return msg;
			}
			key.append(",card_type");
			value.append(",?");
			param.add(cardType);
			key.append(",discount");
			value.append(",?");
			param.add(jsonObj.getString("discount"));
		}else if(cardType.compareToIgnoreCase("CASH") == 0){
			key.append(",card_type");
			value.append(",?");
			param.add(cardType);
			if(jsonObj.containsKey("least_cost") && !jsonObj.get("least_cost").toString().isEmpty()){
				if(RegexpUtils.isZIndex(jsonObj.getString("least_cost")) || RegexpUtils.isPosttiveFloat(jsonObj.getString("least_cost"))){
					key.append(",least_cost");
					value.append(",?");
					param.add(jsonObj.getString("least_cost"));
				}else{
					msg="抵扣条件类型错误";
					return msg;
				}
			}
			key.append(",reduce_cost");
			value.append(",?");
			param.add(jsonObj.getString("reduce_cost"));
		}else if(cardType.compareToIgnoreCase("GENERAL_COUPON") == 0){
			key.append(",default_detail");
			P.log.d(key);
			value.append(",?");
			param.add(jsonObj.getString("detail"));
		}else if(cardType.compareToIgnoreCase("GROUPON") == 0){
			key.append(",deal_detail");
			P.log.d(key);
			value.append(",?");
			param.add(jsonObj.getString("detail"));
		}else if(cardType.compareToIgnoreCase("GIFT") == 0){
			key.append(",gift");
			P.log.d(key);
			value.append(",?");
			param.add(jsonObj.getString("gift"));
		}
		String ticketSql = "INSERT INTO TABLE("+key+") VALUES("+value+")";
		ticketSql=CompareTicketGroup.getCompareStr(ticketSql, cardType, "TABLE");
		P.log.d("ticketSql>>>>>>>>>>>>>>>>>>>>>>>>"+ticketSql);
		int ticketResult = DaoUtil.execute(tds, ticketSql, param.toArray());
		msg = ticketResult == 0 && ticketResult == 0 ? "失败" : msg;
		P.log.d("》》》》》》》》》》》》》》》添加结果" + msg);
		tds.transactionCommitOrRollback();
		tds.close();
		return msg;
	}

	/**
	 * 卡票券同步到微信
	 * 
	 * @param json
	 * @throws ServiceNotFoundException
	 * @throws ServiceTimeoutException
	 * @throws ParseException
	 * @throws SQLException
	 */
	public static String saveToWX(JSONObject jsonObj, String card_type)
			throws ServiceNotFoundException, ServiceTimeoutException,
			ParseException, SQLException {
		P.log.d(jsonObj);
		String brandName = "";
		String sql = "SELECT MER_NAME FROM T_CIPMP_MER WHERE MER_CODE=?";
		Map<String, Object> map = DaoUtil.row(ds, sql, P.mp.getMerchant()
				.getMerCode());
		if (map != null) {
			brandName = map.get("MER_NAME").toString();
		}
		String msg =validateTicket(jsonObj);
		if(!msg.equals("ok")){
			return msg;
		}
		JSONObject returnJSON = new JSONObject();

		JSONObject dateInfoObj = JSONObject.fromObject(jsonObj
				.getString("date_info"));
		if (Integer.parseInt(dateInfoObj.get("type").toString()) == 1) {
			dateInfoObj.put(
					"begin_timestamp",
					Integer.parseInt(DateUtil.date2TimeStamp(dateInfoObj
							.getString("begin_timestamp") + " 00:00:00")));
			dateInfoObj.put(
					"end_timestamp",
					Integer.parseInt(DateUtil.date2TimeStamp(dateInfoObj
							.getString("end_timestamp") + " 23:59:59")));
		}
		returnJSON.put("date_info", dateInfoObj);
		
		returnJSON.put("use_custom_code", true);

		JSONObject skuReturnJson = new JSONObject();
		skuReturnJson.put("quantity",
				Integer.parseInt(jsonObj.getString("quantity")));
		returnJSON.put("sku", skuReturnJson);

		returnJSON
				.put("logo_url",
						"https://mp.weixin.qq.com/misc/getheadimg?fakeid=2399547855&token=611267785&lang=zh_CN");
		returnJSON.put("code_type", jsonObj.getString("code_type"));
		returnJSON.put("brand_name", brandName);
		returnJSON.put("title", jsonObj.getString("title"));
		if (StringUtils.isNotEmpty(jsonObj.getString("sub_title"))) {
			returnJSON.put("sub_title", jsonObj.getString("sub_title"));
		}
		String color=jsonObj.containsKey("color")?jsonObj.getString("color"):"Color010";
		returnJSON.put("color", color);
		returnJSON.put("notice", jsonObj.getString("notice"));
		returnJSON.put("description", jsonObj.getString("description"));

		returnJSON.put("date_info", dateInfoObj);
		returnJSON.put("sku", skuReturnJson);

		// if(StringUtils.isNotEmpty(jsonObj.getString("location_id_list"))){
		// keyStr.append(",location_id_list");
		// valueStr.append(","+jsonObj.getString("location_id_list"));
		// }
		if (StringUtils.isNotEmpty(jsonObj.getString("can_share"))) {
			returnJSON.put("can_share", false);// jsonObj.getString("can_share")
		}
		if (StringUtils.isNotEmpty(jsonObj.getString("can_give_friend"))) {
			returnJSON.put("can_give_friend", false);// jsonObj.getString("can_give_friend")
		}
		if (StringUtils.isNotEmpty(jsonObj.getString("get_limit"))) {
			returnJSON.put("get_limit",
					Integer.parseInt(jsonObj.getString("get_limit")));
		}
		if (StringUtils.isNotEmpty(jsonObj.getString("service_phone"))) {
			returnJSON.put("service_phone", jsonObj.getString("service_phone"));
		}
		returnJSON.put("source", "高汇通");
		P.log.d("》》》》》》》》》》》》》》base_info的JSON：" + returnJSON);

		JSONObject cardJsonReturn = new JSONObject();
		JSONObject cardTypeJsonReturn = new JSONObject();
		if (card_type.compareToIgnoreCase("DISCOUNT") == 0) {

			cardTypeJsonReturn
					.put("discount", 100 - Double.parseDouble(jsonObj
							.getString("discount")) * 10);
			cardTypeJsonReturn.put("base_info", returnJSON);

			cardJsonReturn.put("card_type", card_type);
			cardJsonReturn.put(card_type.toLowerCase(), cardTypeJsonReturn);
		} else if (card_type.compareToIgnoreCase("CASH") == 0) {
			if(jsonObj.containsKey("least_cost") && !jsonObj.get("least_cost").toString().isEmpty()){
				if(RegexpUtils.isZIndex(jsonObj.getString("least_cost")) || RegexpUtils.isPosttiveFloat(jsonObj.getString("least_cost"))){
					cardTypeJsonReturn.put("least_cost",
							jsonObj.getString("least_cost"));
				}else{
					msg="抵扣条件类型错误";
					return msg;
				}
			}
			cardTypeJsonReturn.put("reduce_cost",
					jsonObj.getString("reduce_cost"));
			cardTypeJsonReturn.put("base_info", returnJSON);

			cardJsonReturn.put("card_type", card_type);
			cardJsonReturn.put(card_type.toLowerCase(), cardTypeJsonReturn);
		} else if (card_type.compareToIgnoreCase("GENERAL_COUPON") == 0) {

			cardTypeJsonReturn.put("default_detail",
					jsonObj.getString("detail"));
			cardTypeJsonReturn.put("base_info", returnJSON);

			cardJsonReturn.put("card_type", card_type);
			cardJsonReturn.put(card_type.toLowerCase(), cardTypeJsonReturn);
		} else if (card_type.compareToIgnoreCase("GROUPON") == 0) {

			cardTypeJsonReturn.put("deal_detail", jsonObj.getString("detail"));
			cardTypeJsonReturn.put("base_info", returnJSON);

			cardJsonReturn.put("card_type", card_type);
			cardJsonReturn.put(card_type.toLowerCase(), cardTypeJsonReturn);
		} else if (card_type.compareToIgnoreCase("GIFT") == 0) {

			cardTypeJsonReturn.put("gift", jsonObj.getString("gift"));
			cardTypeJsonReturn.put("base_info", returnJSON);

			cardJsonReturn.put("card_type", card_type);
			cardJsonReturn.put(card_type.toLowerCase(), cardTypeJsonReturn);
		}
		JSONObject lastCardJson = new JSONObject();
		lastCardJson.put("card", cardJsonReturn);
		P.log.d("》》》》》》》》》》》》》》调用接口的JSON：" + lastCardJson);

		// String testJson="{\"card\":{\"card_type\":\"GROUPON\",\"groupon\":{\"deal_detail\":\"以下锅底2选1\",\"base_info\":{\"title\":\"123元双人火锅套餐\",\"date_info\":{\"end_timestamp\":1433143200,\"type\":1,\"begin_timestamp\":1431002375},\"color\":\"Color010\",\"description\":\"不可与其他优惠同享\",\"sku\":{\"quantity\":50000000},\"notice\":\"使用时向服务员出示此券\",\"brand_name\":\"麦当劳\",\"logo_url\":\"http://mmbiz.qpic.cn/mmbiz/rPm2dlmvwFOjfibpOGhwXISkHasvia653ewHVFOZACfbgS79RofibUaRO8b1hZFGVKSc0XZQnqhXuxibwJpwnD0yiag/0\",\"code_type\":\"CODE_TYPE_QRCODE\"}}}}";
		String url = "https://api.weixin.qq.com/card/create?access_token=ACCESS_TOKEN";
		url = url.replaceAll("ACCESS_TOKEN",
				P.mp.getService("Weixin", 60000, "getAccessTokey").getValue().toString());
		// String result = HttpsTool.sendHttpRequest(url, testJsonObj.toString());
		HttpURLConnectionUtil conn = new HttpURLConnectionUtil();
		String result = conn.sendHttpsRequest(url, lastCardJson.toString(),
				"POST", 30000);
		P.log.d("----------------" + result);
		return result;
	}
	
	private static String validateTicket(JSONObject jsonObj) {
		// 先对数据做出判断
		String msg = "ok";
		if (!jsonObj.containsKey("title")
				|| jsonObj.getString("title").length() > 9
				|| jsonObj.getString("title").isEmpty()) {
			msg = "卡券名称不能为空且长度不超过9个汉字";
			return msg;
		}
		if (jsonObj.containsKey("sub_title")
				&& jsonObj.getString("sub_title").length() > 18) {
			msg = "卡券副标题名称长度不超过18个汉字";
			return msg;
		}
		if (!jsonObj.containsKey("notice")
				|| jsonObj.getString("notice").isEmpty()
				|| jsonObj.getString("notice").length() > 16) {
			msg = "操作提示不能为空且长度不超过16个汉字或32个英文字母";
			return msg;
		}
		if (!jsonObj.containsKey("description")
				|| jsonObj.getString("description").isEmpty()
				|| jsonObj.getString("description").length() > 300) {
			msg = "使用须知不能为空且长度不超过300个汉字";
			return msg;
		}
		if (!jsonObj.containsKey("color")) {
			msg = "请选择卡券颜色";
			return msg;
		}
		if(!RegexpUtils.isZIndex(jsonObj.getString("quantity"))){
			msg = "库存必须为整数";
			return msg;
		}
		if(!RegexpUtils.isZIndex(jsonObj.getString("get_limit"))){
			msg = "领取限制必须为整数";
			return msg;
		}

		return msg;
	}
}
