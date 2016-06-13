package com.chinainpay.apps.ticket.util;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import net.javabone.common.util.DaoUtil;

import com.chinainpay.mp.app.pagelet.axis.P;
import com.chinainpay.apps.membercard.entity.base.WXCard;
import com.chinainpay.apps.membercard.util.CodeUtil;
import com.chinainpay.apps.util.DateUtil;

public class CardTicketManagerUtil {
	private static DataSource ds = P.ds("base");

	private static String CARD_STATUS_NOT_VERIFY = "CARD_STATUS_NOT_VERIFY";// 待审核
	private static String CARD_STATUS_VERIFY_FALL = "CARD_STATUS_VERIFY_FALL";// 审核失败
	private static String CARD_STATUS_VERIFY_OK = "CARD_STATUS_VERIFY_OK";// 通过审核
	private static String CARD_STATUS_USER_DELETE = "CARD_STATUS_USER_DELETE";// 卡券被用户删除
	private static String CARD_STATUS_USER_DISPATCH = "CARD_STATUS_USER_DISPATCH";// 在公众平台投放过的卡券

	/**
	 * 得到卡券列表集合
	 * 
	 * @param merNo
	 * @return
	 * @throws SQLException
	 * @throws ParseException 
	 */
	public static List<Map<String, Object>> getCardTicketList(String merNo)
			throws SQLException, ParseException {
		String sql="SELECT * FROM T_CIPMP_BASE_INFO BI,TABLE T WHERE T.base_info_id=BI.base_info_id AND T.MER_CODE=? AND T.status != '0'";
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		//DISCOUNT
		String discountSql = CompareTicketGroup.getCompareStr(sql, "DISCOUNT", "TABLE");//折扣券
		List<Map<String, Object>> discountList = DaoUtil.list(ds, 0, discountSql, merNo);
		if(discountList.size()!=0){
			discountList=eachTicket(discountList);
			returnList.addAll(discountList);
		}
		//CASH
		String cashSql=CompareTicketGroup.getCompareStr(sql, "CASH", "TABLE");//代金券
		List<Map<String, Object>> cashList = DaoUtil.list(ds, 0, cashSql, merNo);
		if(cashList.size()!=0){
			cashList=eachTicket(cashList);
			returnList.addAll(cashList);
		}
		//GENERAL_COUPON
		String generalCouponSql=CompareTicketGroup.getCompareStr(sql, "GENERAL_COUPON", "TABLE");//通用优惠券
		List<Map<String, Object>> generalCouponList = DaoUtil.list(ds, 0, generalCouponSql, merNo);
		if(generalCouponList.size()!=0){
			generalCouponList=eachTicket(generalCouponList);
			returnList.addAll(generalCouponList);
		}
		//GROUPON
		String grouponSql=CompareTicketGroup.getCompareStr(sql, "GROUPON", "TABLE");//团购券
		List<Map<String, Object>> grouponList = DaoUtil.list(ds, 0, grouponSql, merNo);
		if(grouponList.size()!=0){
			grouponList=eachTicket(grouponList);
			returnList.addAll(grouponList);
		}
		//GIFT
		String giftSql=CompareTicketGroup.getCompareStr(sql, "GIFT", "TABLE");//礼品券
		List<Map<String, Object>> giftList = DaoUtil.list(ds, 0, giftSql, merNo);
		if(giftList.size()!=0){
			giftList=eachTicket(giftList);
			returnList.addAll(giftList);
		}
		return returnList;
	}
	
	public static List<Map<String, Object>> eachTicket(List<Map<String, Object>> list) throws ParseException{
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> map : list) {
			Map<String, Object> retunMap = new HashMap<String, Object>();
			String state = map.get("status").toString();
			if (state.equals(CARD_STATUS_NOT_VERIFY)) {
				retunMap.put("state", "待审核");
			} else if (state.equals(CARD_STATUS_VERIFY_FALL)) {
				retunMap.put("state", "审核失败");
			} else if (state.equals(CARD_STATUS_VERIFY_OK)) {
				retunMap.put("state", "通过审核");
			} else if (state.equals(CARD_STATUS_USER_DELETE)) {
				retunMap.put("state", "卡券被用户删除");
			} else if (state.equals(CARD_STATUS_USER_DISPATCH)) {
				retunMap.put("state", "在公众平台投放过的卡券");
			}
			String ct = map.get("card_type").toString();
			retunMap.put("card_type", CompareTicketGroup.getTicketType(ct));
			retunMap.put("card_type_param", ct);//后面要作为参数值穿给别人
			String title = map.get("title").toString();
			retunMap.put("title", title);
			String date_info_type = map.get("date_info_type").toString();
			if (date_info_type.equals("1")) {
				String beginTime = map.containsKey("begin_timestamp") == false ? ""
						: map.get("begin_timestamp").toString();
				String endTime = map.containsKey("end_timestamp") == false  ? "" : map
						.get("end_timestamp").toString();
				
				if(DateUtil.compareToCurrentTime(endTime)){
					retunMap.put("date", beginTime + "至" + endTime+"(已过期)");
					retunMap.put("dateState", 1);
				}else{
					retunMap.put("date", beginTime + "至" + endTime);
				}
			} else if (date_info_type.equals("2")) {
				String fixedTerm = map.containsKey("fixed_term") == false  ? "" : map
						.get("fixed_term").toString();// 自领取后多少天内有效
				String fixedBeginTerm = map.containsKey("fixed_begin_term") == false  ? ""
						: map.get("fixed_begin_term").toString();// 表示自领取后多少天开始生效
				retunMap.put("date", "领取后" + fixedBeginTerm + "天开始生效，有效期为"
						+ fixedTerm + "天");
			}
			String quantity = map.get("sku_quantity").toString();
			retunMap.put("quantity", quantity);
			String cardid = map.get("card_id").toString();
			retunMap.put("cardid", cardid);//后面需要用到，勿删
			String lqcount=map.get("lqcount")==null?"0":map.get("lqcount").toString();
			String hxcount=map.get("hxcount")==null?"0":map.get("hxcount").toString();
			retunMap.put("lqcount", lqcount);//已经领取
			retunMap.put("hxcount", hxcount);//已核销
			returnList.add(retunMap);
		}
		return returnList;
	}
	
	
	/**
	 * 根据卡券ID和卡券类型得到卡券详细信息
	 * @param cardid
	 * @param card_type
	 * @return
	 * @throws SQLException
	 */
	public static Map<String, Object> getCardDetailByCardTypeAndCardId(String cardid,String card_type) throws SQLException{
		String sql="SELECT * FROM T_CIPMP_BASE_INFO BI,TABLE T WHERE T.base_info_id=BI.base_info_id AND T.card_id=?";
		// TABLE 会被getCompareStr（）替换成相应的表名称
		sql=CompareTicketGroup.getCompareStr(sql, card_type, "TABLE");
		Map<String, Object> map=DaoUtil.row(ds, sql, cardid);
		
		Map<String, Object> retunMap = new HashMap<String, Object>();
		String cardType=CompareTicketGroup.getTicketType(card_type);
		P.log.d("cardType>>>>>>>>>>>>>>>>>>"+cardType);
		
		String defaultDetail="";
		if(card_type.equals(WXCard.DISCOUNT)){
			retunMap.put("discount", map.get("discount"));
			defaultDetail="凭此券消费打"+map.get("discount")+"折";
		}else if(card_type.equals(WXCard.CASH)){
			retunMap.put("reduce_cost", map.get("reduce_cost"));
			retunMap.put("least_cost", map.get("least_cost"));
			defaultDetail="价值"+map.get("reduce_cost")+"元代金券1张，满"+map.get("least_cost")+"元可使用。";
		}else if (card_type.equals(WXCard.GENERAL_COUPON)) {
			defaultDetail=map.get("default_detail")+"";
		}else if (card_type.equals(WXCard.GROUPON)) {
			defaultDetail=map.get("deal_detail")+"";
		}else if (card_type.equals(WXCard.GIFT)) {
			defaultDetail=map.get("gift")+"";
		}
		retunMap.put("defaultDetail", defaultDetail);
		
		String title = map.get("title").toString();// 卡券标题
		String sub_title = "无";
		if (map.containsKey("sub_title") == true
				&& map.get("sub_title") != null)
			sub_title = map.get("sub_title").toString();// 副标题

		String color = CodeUtil.getColor(map.get("color").toString());// 卡券颜色
		
		String date_info = "";//有效期
		if (map.get("date_info_type").toString().equals("1")) {
			String begin_timestamp = map.get("begin_timestamp").toString();
			String end_timestamp = map.get("end_timestamp").toString();
			date_info = begin_timestamp + "至" + end_timestamp;// 有效期
		} else if (map.get("date_info_type").toString().equals("2")) {
			String fixed_term = map.get("fixed_term").toString();
			String fixed_begin_term = map.get("fixed_begin_term").toString();
			date_info = "自领取后" + fixed_begin_term + "天开始生效" + "，有效期为"
					+ fixed_term + "天";// 有效期
		}

		String brand_name = map.get("brand_name").toString();// 商家名称
		String logo_url = map.get("logo_url").toString();// 商家Logo

		// ///////////////投放设置
		String quantity = map.get("sku_quantity").toString();// 库存
		String code_type = "一维码";
		String notice = map.get("notice").toString();// 操作提示
		String get_limit = map.get("get_limit").toString();// 领取限制
		String can_share = map.get("can_share").toString();// 分享设置
		P.log.d(can_share);
		if (can_share.equals("true")) {
			can_share = "用户可以分享领券链接";
		} else {
			can_share = "用户不可以分享领券链接";
		}

		String can_give_friend = map.get("can_give_friend").toString();// 转赠设置
		
		if (can_give_friend.equals("true")) {
			can_give_friend = "用户领券后可转赠其他好友";
		} else {
			can_give_friend = "用户领券后不可转赠其他好友";
		}

		String service_phone = "";
		if (map.containsKey("service_phone") == true
				&& map.get("service_phone") != null) {
			retunMap.put("service_phone",map.get("service_phone").toString());
		}else{
			retunMap.put("service_phone", service_phone);
		}

		String description = map.get("description").toString();// 使用须知		
		retunMap.put("card_type", cardType);
		retunMap.put("title", title);
		retunMap.put("sub_title", sub_title);
		retunMap.put("color", color);
		retunMap.put("date_info", date_info);
		retunMap.put("brand_name", brand_name);
		retunMap.put("logo_url", logo_url);

		retunMap.put("quantity", quantity);
		retunMap.put("code_type", code_type);
		retunMap.put("notice", notice);
		retunMap.put("get_limit", get_limit);
		retunMap.put("can_share", can_share);
		retunMap.put("can_give_friend", can_give_friend);
		retunMap.put("description", description);
		return retunMap;
	}
}
