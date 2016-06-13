package com.chinainpay.apps.pool;

public interface CardService {
	/**
	 * 卡状态操作
	 * 
	 * @param cardNoFrom
	 * @param cardNoTo
	 * @param type
	 * @param clientDate
	 * @param clientTime
	 * @param merchantNo
	 * @param storeNo
	 * @param userId
	 * @param mac
	 * @return
	 */
	public String cardState(
			String cardNoFrom,  				// 起始卡号 Str 0-30 非空
			String cardNoTo, 					// 终止卡号 Str 0-30 非空
			String type, 						// 操作代码 Str
			String clientDate, 					// 客户端交易日期 Str 8 yyyymmdd
			String clientTime, 					// 客户端交易时间 Str 6 hhmms
			String merchantNo,					// 商户号 Str 0-30
			String storeNo, 					// 门店号 Str 0-30
			String userId, 						// 操作员 Str 0-30
			String terminal 							// 终端号
	) throws Exception;
	/**
	 * 换卡
	 * @param cardNoFrom
	 * @param cardNoTo
	 * @param password
	 * @param clientDate
	 * @param clientTime
	 * @param merchantNo
	 * @param storeNo
	 * @param userId
	 * @param mac
	 * @return
	 */
	public String cardChange(
			String cardNoFrom,	//起始卡号	Str	0-30	非空
			String cardNoTo,	//终止卡号	Str	0-30	非空
			String password,	//换出卡密码	Str	6-20	非空
			String clientDate,	//客户端交易日期	Str	8	yyyymmdd
			String clientTime,	//客户端交易时间	Str	6	hhmms
			String merchantNo,	//商户号	Str	0-30	
			String storeNo,		//门店号	Str	0-30	
			String userId,		//操作员	Str	0-30	
			String mac			//校验码			

	) throws Exception;
	/**
	 * 卡片延期
	 * @param cardNoFrom
	 * @param cardNoTo
	 * @param reqExpDate
	 * @param clientDate
	 * @param clientTime
	 * @param merchantNo
	 * @param storeNo
	 * @param userId
	 * @param mac
	 * @return
	 */
	public String cardReqExpire(
			String cardNoFrom,	//起始卡号	Str	0-30	非空
			String cardNoTo,	//终止卡号	Str	0-30	非空
			String reqExpDate,	//延期日期	Str	8位	非空yyyymmdd
			String clientDate,	//客户端交易日期	Str	8	yyyymmdd
			String clientTime,	//客户端交易时间	Str	6	hhmms
			String merchantNo,	//商户号	Str	0-30	
			String storeNo,		//门店号	Str	0-30	
			String userId,		//操作员	Str	0-30	
			String mac			//校验码			

	) throws Exception;
	/**
	 * 卡片回收
	 * @param cardNoFrom
	 * @param cardNoTo
	 * @param clientDate
	 * @param clientTime
	 * @param merchantNo
	 * @param storeNo
	 * @param userId
	 * @param mac
	 * @return
	 */
	public String cardRecycling(
			String cardNoFrom,	//起始卡号	Str	0-30	非空
			String cardNoTo,	//终止卡号	Str	0-30	非空
			String clientDate,	//客户端交易日期	Str	8	yyyymmdd
			String clientTime,	//客户端交易时间	Str	6	hhmms
			String merchantNo,	//商户号	Str	0-30	
			String storeNo,		//门店号	Str	0-30	
			String userId,		//操作员	Str	0-30	
			String mac			//校验码			

	) throws Exception;
	/**
	 * 卡密码重置
	 * @param cardNo
	 * @param password
	 * @param clientDate
	 * @param clientTime
	 * @param merchantNo
	 * @param storeNo
	 * @param userId
	 * @param mac
	 * @return
	 */
	public String cardPwdReset(
			String cardNo,	//终止卡号	Str	0-30	非空
			String password,	//新密码	Str	6-20	非空
			String clientDate,	//客户端交易日期	Str	8	yyyymmdd
			String clientTime,	//客户端交易时间	Str	6	hhmms
			String merchantNo,	//商户号	Str	0-30	
			String storeNo,		//门店号	Str	0-30	
			String userId,		//操作员	Str	0-30	
			String terminal			//校验码			

	) throws Exception;
	/**
	 * 卡密码修改
	 * @param cardNo
	 * @param oldPassword
	 * @param newPassword
	 * @param clientDate
	 * @param clientTime
	 * @param merchantNo
	 * @param storeNo
	 * @param userId
	 * @param mac
	 * @return
	 */
	public String cardPwdChange(
			String cardNo,	//终止卡号	Str	0-30	非空
			String oldPassword,	//旧密码	Str	6-20	非空
			String newPassword,	//新密码	Str	6-20	非空
			String clientDate,	//客户端交易日期	Str	8	yyyymmdd
			String clientTime,	//客户端交易时间	Str	6	hhmms
			String merchantNo,	//商户号	Str	0-30	
			String storeNo,		//门店号	Str	0-30	
			String userId,		//操作员	Str	0-30	
			String terminal			//终端号		

	) throws Exception;
	/**
	 * 卡密码尝试次数重置
	 * @param cardNoTo
	 * @param clientDate
	 * @param clientTime
	 * @param merchantNo
	 * @param storeNo
	 * @param userId
	 * @param mac
	 * @return
	 */
	public String cardPwdNums(
			String cardNo,	//终止卡号	Str	0-30	非空
			String clientDate,	//客户端交易日期	Str	8	yyyymmdd
			String clientTime,	//客户端交易时间	Str	6	hhmms
			String merchantNo,	//商户号	Str	0-30	
			String storeNo,		//门店号	Str	0-30	
			String userId,		//操作员	Str	0-30	
			String mac			//校验码			

	) throws Exception;
}
