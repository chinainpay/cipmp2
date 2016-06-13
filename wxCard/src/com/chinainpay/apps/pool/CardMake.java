package com.chinainpay.apps.pool;

public  interface CardMake {
	/**
	 * 制卡申请
	 * 
	 * @param orderNo
	 * @param totalAmount
	 * @param clientDate
	 * @param clientTime
	 * @param merchantNo
	 * @param storeNo
	 * @param userId
	 * @param methodID
	 * @param mac
	 * @return
	 * @throws Exception
	 */
	public String cardMakeApply(String orderNo, // 申请制卡号 Str 0-40 非空
			String totalAmount, // 申请卡总数 Str 0-8 非空
			String clientDate, // 客户端交易日期 Str 8 yyyymmdd 可为空
			String clientTime, // 客户端交易时间 Str 6 Hhmms可为空
			String merchantNo, // 商户号 Str 0-30 非空
			String storeNo,// 门店号 Str 0-30 非空
			String userId, // 操作员 Str 0-30 非空
			String methodID,// 具体方法ID Str 0-20 非空
			String mac // 校验码 Str 非空
	) throws Exception;

	/**
	 * 获取制卡数据
	 * 
	 * @param orderNo
	 * @param totalAmount
	 * @param clientDate
	 * @param clientTime
	 * @param merchantNo
	 * @param storeNo
	 * @param userId
	 * @param methodID
	 * @param mac
	 * @return
	 * @throws Exception
	 */
	public String cardMakeData(String orderNo, // 申请制卡号 Str 0-40 非空
			String totalAmount, // 申请卡总数 Str 0-8 非空
			String clientDate, // 客户端交易日期 Str 8 yyyymmdd可为空
			String clientTime, // 客户端交易时间 Str 6 Hhmms可为空
			String merchantNo, // 商户号 Str 0-30 非空
			String storeNo, // 门店号 Str 0-30 非空
			String userId, // 操作员 Str 0-30 非空
			String methodID, // 具体方法ID Str 0-20 非空
			String mac // 校验码 Str 非空
	) throws Exception;
	/**
	 * 入库
	 * @param orderNo
	 * @param clientDate
	 * @param clientTime
	 * @param merchantNo
	 * @param storeNo
	 * @param userId
	 * @param methodID
	 * @param mac
	 * @return
	 * @throws Exception
	 */
	public String cardStorage(String orderNo,// 申请制卡号 Str 0-40 非空
			String clientDate,// 客户端交易日期 Str 8 yyyymmdd 可为空
			String clientTime,// 客户端交易时间 Str 6 Hhmms 可为空
			String merchantNo,// 商户号 Str 0-30 非空
			String storeNo,// 门店号 Str 0-30 非空
			String userId,// 操作员 Str 0-30 非空
			String methodID,// 具体方法ID Str 0-20 非空
			String mac// 非空
	) throws Exception;
	/**
	 * 申请卡（卡数据已经存在）
	 * @param orderNo
	 * @param password
	 * @param clientDate
	 * @param clientTime
	 * @param merchantNo
	 * @param storeNo
	 * @param userId
	 * @param methodID
	 * @param mac
	 * @return
	 * @throws Exception
	 */
	
	public String cardMakeData2(
			String orderNo,//	申请制卡号	Str	0-40	非空
			String password,//	申请卡总数	Str	6-20	非空
			String clientDate,//	客户端交易日期	Str	8	yyyymmdd			可为空
			String clientTime,//	客户端交易时间	Str	6	Hhmms			可为空
			String merchantNo,//	商户号	Str	0-30	非空
			String storeNo,//	门店号	Str	0-30	非空
			String userId,//	操作员	Str	0-30	非空
			String methodID,//	具体方法ID	Str	0-20	非空
			String mac//	校验码	Str		非空
	) throws Exception;
	
}
