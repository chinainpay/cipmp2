package com.chinainpay.apps.membercard.entity.baseInfo;

import com.chinainpay.apps.membercard.entity.base.Sku;

public class BaseInfo {
	
	public static final String CODE_TYPE_TEXT="CODE_TYPE_TEXT";//文本
	public static final String CODE_TYPE_BARCODE="CODE_TYPE_BARCODE";// 一维码 
	public static final String CODE_TYPE_QRCODE="CODE_TYPE_QRCODE";//二维码
	public static final String CODE_TYPE_ONLY_QRCODE="CODE_TYPE_ONLY_QRCODE";//二维码无 code 显示
	public static final String CODE_TYPE_ONLY_BARCODE="CODE_TYPE_ONLY_BARCODE";//一维码无 code 显示
	
	private String logo_url;//卡券的商户 logo
	private String code_type;//code 码展示类型
	private String brand_name;//商户名字,字数上限为12个汉字
	private String title;//券名，字数上限为 9 个汉字
	private String sub_title;//券名的副标题， 字数上限为 18 个汉字
	private String color;//券颜色
	private String notice;//使用提醒，字数上限为 12 个汉字
	private String description;//使用说明
	private String location_id_list;//门店位置 ID
	private String use_custom_code;//是否自定义 code 码   或 false，不填代表默认为 false
	private String bind_openid;//是否指定用户领取，填写 true 或false。不填代表默认为否。
	private String can_share;//领取卡券原生页面是否可分享，填写 true 或 false，true 代表可分享。默认为 true
	private String can_give_friend;//卡券是否可转赠，填写 true 或false,true 代表可转赠。默认为true
	private String get_limit;//每人最大领取次数，不填写默认等于 quantity
	private String service_phone;//客服电话
	private String source;//第三方来源名
	private String custom_url_name;//商户自定义入口名称长度限制在 5 个汉字内
	private String custom_url;//商户自定义入口跳转外链的地址
	private String custom_url_sub_title;//显示在入口右侧的 tips，长度限制在 6 个汉字内。
	private String promotion_url_name;//营销场景的自定义入口
	private String promotion_url;//入口跳转外链的地址链接
	private String promotion_url_sub_title;//显示在入口右侧的 tips，长度限制在 6 个汉字内。
	
	private DateInfo date_info;//基本的卡券数据
	private Sku sku;//商品信息
	
	public String getLogo_url() {
		return logo_url;
	}
	public void setLogo_url(String logo_url) {
		this.logo_url = logo_url;
	}
	public String getCode_type() {
		return code_type;
	}
	public void setCode_type(String code_type) {
		this.code_type = code_type;
	}
	public String getBrand_name() {
		return brand_name;
	}
	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSub_title() {
		return sub_title;
	}
	public void setSub_title(String sub_title) {
		this.sub_title = sub_title;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLocation_id_list() {
		return location_id_list;
	}
	public void setLocation_id_list(String location_id_list) {
		this.location_id_list = location_id_list;
	}
	public String getUse_custom_code() {
		return use_custom_code;
	}
	public void setUse_custom_code(String use_custom_code) {
		this.use_custom_code = use_custom_code;
	}
	public String getBind_openid() {
		return bind_openid;
	}
	public void setBind_openid(String bind_openid) {
		this.bind_openid = bind_openid;
	}
	public String getCan_share() {
		return can_share;
	}
	public void setCan_share(String can_share) {
		this.can_share = can_share;
	}
	public String getCan_give_friend() {
		return can_give_friend;
	}
	public void setCan_give_friend(String can_give_friend) {
		this.can_give_friend = can_give_friend;
	}
	public String getGet_limit() {
		return get_limit;
	}
	public void setGet_limit(String get_limit) {
		this.get_limit = get_limit;
	}
	public String getService_phone() {
		return service_phone;
	}
	public void setService_phone(String service_phone) {
		this.service_phone = service_phone;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getCustom_url_name() {
		return custom_url_name;
	}
	public void setCustom_url_name(String custom_url_name) {
		this.custom_url_name = custom_url_name;
	}
	public String getCustom_url() {
		return custom_url;
	}
	public void setCustom_url(String custom_url) {
		this.custom_url = custom_url;
	}
	public String getCustom_url_sub_title() {
		return custom_url_sub_title;
	}
	public void setCustom_url_sub_title(String custom_url_sub_title) {
		this.custom_url_sub_title = custom_url_sub_title;
	}
	public String getPromotion_url_name() {
		return promotion_url_name;
	}
	public void setPromotion_url_name(String promotion_url_name) {
		this.promotion_url_name = promotion_url_name;
	}
	public String getPromotion_url() {
		return promotion_url;
	}
	public void setPromotion_url(String promotion_url) {
		this.promotion_url = promotion_url;
	}
	public String getPromotion_url_sub_title() {
		return promotion_url_sub_title;
	}
	public void setPromotion_url_sub_title(String promotion_url_sub_title) {
		this.promotion_url_sub_title = promotion_url_sub_title;
	}
	public DateInfo getDate_info() {
		return date_info;
	}
	public void setDate_info(DateInfo date_info) {
		this.date_info = date_info;
	}
	public Sku getSku() {
		return sku;
	}
	public void setSku(Sku sku) {
		this.sku = sku;
	}
	
	
}
