package com.chinainpay.apps.membercard.entity.base;

public class WXCard {
	public static final String GENERAL_COUPON="GENERAL_COUPON";//通用券
	public static final String GROUPON="GROUPON";//团购券
	public static final String DISCOUNT="DISCOUNT";//折扣券
	public static final String GIFT="GIFT";//礼品券
	public static final String CASH="CASH";//代金券
	public static final String MEMBER_CARD="MEMBER_CARD";//会员卡
	public static final String SCENIC_TICKET="SCENIC_TICKET";//景点门票
	public static final String MOVIE_TICKET="MOVIE_TICKET";//电影票
	public static final String BOARDING_PASS="BOARDING_PASS";//飞机票
	public static final String LUCKY_MONEY="LUCKY_MONEY";//红包
	public static final String MEETING_TICKET="MEETING_TICKET";//会议门票
	
	private String card_type;//卡券类型

	public String getCard_type() {
		return card_type;
	}

	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}
	
}
