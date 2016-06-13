package com.chinainpay.apps.util;

import java.util.UUID;

public class JsApiList {

	/**
	 * 获得随机字符串
	 * 
	 * @return
	 */
	public static String create_nonce_str() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 获得时间戳
	 * 
	 * @return
	 */
	public static String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}

	/**
	 * 分享接口
	 */
	public static String onMenuShareTimeline = "onMenuShareTimeline";// 获取“分享到朋友圈”按钮点击状态及自定义分享内容接口
	public static String onMenuShareAppMessage = "onMenuShareAppMessage";// 获取“分享给朋友”按钮点击状态及自定义分享内容接口
	public static String onMenuShareQQ = "onMenuShareQQ";// 获取“分享到QQ”按钮点击状态及自定义分享内容接口
	public static String onMenuShareWeibo = "onMenuShareWeibo";// 获取“分享到腾讯微博”按钮点击状态及自定义分享内容接口

	/**
	 * 图像接口
	 */
	public static String chooseImage = "chooseImage";// 拍照或从手机相册中选图接口
	public static String previewImage = "previewImage";// 预览图片接口
	public static String uploadImage = "uploadImage";// 上传图片接口
	public static String downloadImage = "downloadImage";// 下载图片接口

	/**
	 * 音频接口
	 */
	public static String startRecord = "startRecord";// 开始录音接口
	public static String stopRecord = "stopRecord";// 停止录音接口
	public static String onVoiceRecordEnd = "onVoiceRecordEnd";//
	public static String playVoice = "playVoice";// 播放语音接口
	public static String pauseVoice = "pauseVoice";// 暂停播放接口
	public static String stopVoice = "stopVoice";// 停止播放接口
	public static String onVoicePlayEnd = "onVoicePlayEnd";//
	public static String uploadVoice = "uploadVoice";// 上传语音接口
	public static String downloadVoice = "downloadVoice";// 下载语音接口

	/**
	 * 智能接口
	 */
	public static String translateVoice = "translateVoice";// 识别音频并返回识别结果接口

	/**
	 * 设备信息接口
	 */
	public static String getNetworkType = "getNetworkType";// 获取网络状态接口

	/**
	 * 地理位置接口
	 */
	public static String openLocation = "openLocation";// 使用微信内置地图查看位置接口
	public static String getLocation = "getLocation";// 获取地理位置接口

	/**
	 * 界面操作接口
	 */
	public static String hideOptionMenu = "hideOptionMenu";// 隐藏右上角菜单接口
	public static String showOptionMenu = "showOptionMenu";// 显示右上角菜单接口
	public static String hideMenuItems = "hideMenuItems";// 批量隐藏功能按钮接口
	public static String showMenuItems = "showMenuItems";// 批量显示功能按钮接口
	public static String hideAllNonBaseMenuItem = "hideAllNonBaseMenuItem";// 隐藏所有非基础按钮接口
	public static String showAllNonBaseMenuItem = "showAllNonBaseMenuItem";// 显示所有功能按钮接口
	public static String closeWindow = "closeWindow";// 关闭当前网页窗口接口

	/**
	 * 微信支付接口
	 */
	public static String chooseWXPay = "chooseWXPay";// 发起一个微信支付请求

	/**
	 * 微信小店接口
	 */
	public static String openProductSpecificView = "openProductSpecificView";// 跳转微信商品页接

	/**
	 * 微信卡券接口
	 */
	public static String addCard = "addCard";// 批量添加卡券接口
	public static String chooseCard = "chooseCard";// 调起适用于门店的卡券列表并获取用户选择列表
	public static String openCard = "openCard";// 查看微信卡包中的卡券接口

	/**
	 * 微信扫一扫 调起微信扫一扫接口
	 */
	public static String scanQRCode = "scanQRCode";// 调起微信扫一扫接口
}
