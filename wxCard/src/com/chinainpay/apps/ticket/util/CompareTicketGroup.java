package com.chinainpay.apps.ticket.util;

import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import net.javabone.common.util.DaoUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.chinainpay.mp.app.pagelet.axis.P;
import com.chinainpay.apps.membercard.entity.base.WXCard;
import com.chinainpay.apps.service.CouponServices;

/**
 * 根据卡类型的不同，将不同的字符串更换
 * @author ying
 *
 */
public class CompareTicketGroup {
	private static DataSource ds = P.ds("base");
	
	private static String T_CIPMP_TICKET_GENERAL_COUPON="T_CIPMP_TICKET_GENERAL_COUPON";//通用优惠券表明
	private static String T_CIPMP_TICKET_GROUPON="T_CIPMP_TICKET_GROUPON";//团购
	private static String T_CIPMP_TICKET_DISCOUNT="T_CIPMP_TICKET_DISCOUNT";//折扣
	private static String T_CIPMP_TICKET_GIFT="T_CIPMP_TICKET_GIFT";//礼品
	private static String T_CIPMP_TICKET_CASH="T_CIPMP_TICKET_CASH";//代金
	/**
	 * 
	 * @param str 原始字符串
	 * @param cardType 卡类型
	 * @param replaceName 替换的原始内容
	 * 此方法只针对替换不同卡券的表明
	 * @return
	 */
	public static String getCompareStr(String str,String cardType,String replaceName){
		if(cardType.equals("GENERAL_COUPON")){
			str=str.replaceAll(replaceName, T_CIPMP_TICKET_GENERAL_COUPON);
		}else if(cardType.equals("GROUPON")){
			str=str.replaceAll(replaceName, T_CIPMP_TICKET_GROUPON);
		}else if(cardType.equals("DISCOUNT")){
			str=str.replaceAll(replaceName, T_CIPMP_TICKET_DISCOUNT);
		}else if(cardType.equals("GIFT")){
			str=str.replaceAll(replaceName, T_CIPMP_TICKET_GIFT);
		}else if(cardType.equals("CASH")){
			str=str.replaceAll(replaceName, T_CIPMP_TICKET_CASH);
		}else if(cardType.equals("MEMBER_CARD")){
			str=str.replaceAll(replaceName, "MEMBER_CARD");
		}else if(cardType.equals("SCENIC_TICKET")){
			str=str.replaceAll(replaceName, "SCENIC_TICKET");
		}else if(cardType.equals("MOVIE_TICKET")){
			str=str.replaceAll(replaceName, "MOVIE_TICKET");
		}else if(cardType.equals("BOARDING_PASS")){
			str=str.replaceAll(replaceName, "BOARDING_PASS");
		}else if(cardType.equals("LUCKY_MONEY")){
			str=str.replaceAll(replaceName, "LUCKY_MONEY");
		}else if(cardType.equals("MEETING_TICKET")){
			str=str.replaceAll(replaceName, "MEETING_TICKET");
		}
		return str;
	}
	
	
	public static String getTicketType(String card_type){
		String cardType="优惠券";
		if (card_type.equals(WXCard.GENERAL_COUPON)) {
			cardType = "通用券";
		} else if (card_type.equals(WXCard.GROUPON)) {
			cardType = "团购券";
		} else if (card_type.equals(WXCard.DISCOUNT)) {
			cardType = "折扣券";
		} else if (card_type.equals(WXCard.GIFT)) {
			cardType = "礼品券";
		} else if (card_type.equals(WXCard.CASH)) {
			cardType = "代金券";		
		} else if (card_type.equals(WXCard.MEMBER_CARD)) {
			cardType = "会员卡";
		} else if (card_type.equals(WXCard.SCENIC_TICKET)) {
			cardType = "景点门票";
		} else if (card_type.equals(WXCard.MOVIE_TICKET)) {
			cardType = "电影票";
		} else if (card_type.equals(WXCard.BOARDING_PASS)) {
			cardType = "飞机票";
		} else if (card_type.equals(WXCard.LUCKY_MONEY)) {
			cardType = "红包";
		} else if (card_type.equals(WXCard.MEETING_TICKET)) {
			cardType = "会议门票";
		}
		return cardType;
	}
	
    /**
     * 根据卡券ID获得卡券类型
     * @param card_id
     * @return
     */
    public static String getCardTypeByCardId(String card_id){
    	String str="";
    	if(card_id.startsWith("GENERAL_COUPON")){
			str="GENERAL_COUPON";
		}else if(card_id.startsWith("GROUPON")){
			str="GROUPON";
		}else if(card_id.startsWith("DISCOUNT")){
			str="DISCOUNT";
		}else if(card_id.startsWith("GIFT")){
			str="GIFT";
		}else if(card_id.startsWith("CASH")){
			str="CASH";
		}else if(card_id.startsWith("MEMBER_CARD")){
			str="MEMBER_CARD";
		}else if(card_id.startsWith("SCENIC_TICKET")){
			str="SCENIC_TICKET";
		}else if(card_id.startsWith("MOVIE_TICKET")){
			str="MOVIE_TICKET";
		}else if(card_id.startsWith("BOARDING_PASS")){
			str="BOARDING_PASS";
		}else if(card_id.startsWith("LUCKY_MONEY")){
			str="LUCKY_MONEY";
		}else if(card_id.startsWith("MEETING_TICKET")){
			str="MEETING_TICKET";
		}
		return str;
	}
    
    
    /**
     * 根据微信的卡券ID得到专卡的卡券ID
     * @param wx_card_id
     * @return
     * @throws SQLException 
     */
    public static String getCardIdByWXCardId(String wx_card_id) throws SQLException{
    	String cardType="";
    	
    	String cashSql="SELECT COUNT(*) COUNT FROM T_CIPMP_TICKET_CASH WHERE wx_card_id=? AND MER_CODE=?";
    	Map<String, Object> mapCash=DaoUtil.row(ds, cashSql, wx_card_id,P.mp.getMerchant().getMerCode());
    	cardType=Integer.parseInt(mapCash.get("COUNT").toString())==1?WXCard.CASH:cardType;
    	
    	
    	String discountSql="SELECT COUNT(*) COUNT FROM T_CIPMP_TICKET_DISCOUNT WHERE wx_card_id=? AND MER_CODE=?";
    	Map<String, Object> mapDiscount=DaoUtil.row(ds, discountSql, wx_card_id,P.mp.getMerchant().getMerCode());
    	cardType=Integer.parseInt(mapDiscount.get("COUNT").toString())==1?WXCard.DISCOUNT:cardType;
    	
    	String generalSql="SELECT COUNT(*) COUNT FROM T_CIPMP_TICKET_GENERAL_COUPON WHERE wx_card_id=? AND MER_CODE=?";
    	Map<String, Object> mapGeneral=DaoUtil.row(ds, generalSql, wx_card_id,P.mp.getMerchant().getMerCode());
    	cardType=Integer.parseInt(mapGeneral.get("COUNT").toString())==1?WXCard.GENERAL_COUPON:cardType;
    	
    	String giftSql="SELECT COUNT(*) COUNT FROM T_CIPMP_TICKET_GIFT WHERE wx_card_id=? AND MER_CODE=?";
    	Map<String, Object> mapGift=DaoUtil.row(ds, giftSql, wx_card_id,P.mp.getMerchant().getMerCode());
    	cardType=Integer.parseInt(mapGift.get("COUNT").toString())==1?WXCard.GIFT:cardType;
    	
    	String grouponSql="SELECT COUNT(*) COUNT FROM T_CIPMP_TICKET_GROUPON WHERE wx_card_id=? AND MER_CODE=?";
    	Map<String, Object> mapGroupon=DaoUtil.row(ds, grouponSql, wx_card_id,P.mp.getMerchant().getMerCode());
    	cardType=Integer.parseInt(mapGroupon.get("COUNT").toString())==1?WXCard.GROUPON:cardType;
    	
    	String cardSql="SELECT card_id FROM TABLE WHERE wx_card_id=? AND MER_CODE=?";
    	cardSql=CompareTicketGroup.getCompareStr(cardSql, cardType, "TABLE");
    	Map<String, Object> map=DaoUtil.row(ds, cardSql, wx_card_id,P.mp.getMerchant().getMerCode());
    	return map.get("card_id").toString();
    }
}
