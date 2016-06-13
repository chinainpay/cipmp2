package com.chinainpay.apps.pool;


public interface Trade {
	/** 
	 * 充值方法  
	 * @param orderNo       订单号       40位
	 * @param cardNoFrom    起始卡号    0-30位
	 * @param cardNoTo		终止卡号    0-30位
	 * @param amount        单张金额    0-8位
	 * @param totalAmount   总金额        0-12位
	 * @param clientDate    客户端交易日期    8位 yyyymmdd
	 * @param clientTime    客户端交易时间    6位hhmmss
	 * @param merchantNo    商户号        0-30位
	 * @param storeNo		门店号        0-30位
	 * @param userId		操作员        0-30位
	 * @param mac			//校验码
	 * @throws Exception
	 * @return code返回码        msg返回信息      flowNo返回流水号   mac校验码
	 * @throws Exception
	 */
	public String Recharge(
			String orderNo,       //订单号                               40位
			String cardNoFrom,    //起始卡号                           0-30位
			String cardNoTo,	  //终止卡号                           0-30位
			String amount,        //单张金额                           0-8位
			String totalAmount,   //总金额                               0-12位
			String clientDate,	  //客户端交易日期                8位yyyymmdd
			String clientTime,	  //客户端交易时间                6位hhmmss
			String merchantNo,    //商户号                               0-30位
			String storeNo,       //门店号			 0-30位
			String userId,        //操作员			 0-30位
			String terminal       //终端号                    
			) throws Exception;
	
	/**
	 * 撤销充值
	 * @param orderNo
	 * @param cardNoFrom
	 * @param cardNoTo
	 * @param amount
	 * @param totalAmount
	 * @param clientDate
	 * @param clientTime
	 * @param merchantNo
	 * @param storeNo
	 * @param userId
	 * @param mac
	 * @return code返回码        msg返回信息      flowNo返回流水号   mac校验码
	 */
	public  String cancelRecharge(
			String orderNo,       //订单号                               40位
			String cardNoFrom,    //起始卡号                           0-30位
			String cardNoTo,	  //终止卡号                           0-30位
			String amount,        //单张金额                           0-8位
			String totalAmount,   //总金额                               0-12位
			String clientDate,	  //客户端交易日期                8位yyyymmdd
			String clientTime,	  //客户端交易时间                6位hhmmss
			String merchantNo,    //商户号                               0-30位
			String storeNo,       //门店号			 0-30位
			String userId,        //操作员			 0-30位
			String mac            //校验码                              
			
			)throws Exception;
	
	
	/**
	 * 消费
	 * @param orderNo
	 * @param cardNoFrom    起始卡号    0-30位
	 * @param cardNoTo		终止卡号    0-30位
	 * @param amount        单张金额    0-8位
	 * @param totalAmount   总金额        0-12位
	 * @param clientDate
	 * @param clientTime
	 * @param merchantNo
	 * @param storeNo
	 * @param terminal
	 * @param userId
	 * @param isverifyPwd
	 * @param password
	 * @param mac
	 * @return  code返回码        msg返回信息      flowNo返回流水号   balance余额  扣款金额amount mac校验码
	 * @throws Exception
	 */
	public String Consumption(
			String orderNo,       //订单号                               40位
			String cardNoFrom,    //起始卡号                           0-30位
			String cardNoTo,	  //终止卡号                           0-30位
			String amount,        //单张金额                           0-8位
			String totalAmount,   //总金额                               0-12位
			String clientDate,	  //客户端交易日期                8位yyyymmdd
			String clientTime,	  //客户端交易时间                6位hhmmss
			String merchantNo,    //商户号                               0-30位
			String storeNo,       //门店号			 0-30位
			String terminal,      //终端号			 0-30位
			String userId,        //操作员			 0-30位
			String isverifyPwd,	  //是否校验密码                    1位Y,N
			String password      //密码                                   6-20位
			) throws Exception;
	/**
	 * 撤销消费 退货 冲正
	 * @param orderNo
	 * @param cardNo
	 * @param amount
	 * @param clientDate
	 * @param clientTime
	 * @param merchantNo
	 * @param storeNo
	 * @param terminal
	 * @param userId
	 * @param mac
	 * @return  code返回码        msg返回信息    mac校验码
	 * @throws Exception
	 */
	public String cancelConsumption(
			String orderNo,       //订单号                               40位
			String cardNo,    //起始卡号                           0-30位
			String amount,        //单张金额                           0-8位
			String clientDate,	  //客户端交易日期                8位yyyymmdd
			String clientTime,	  //客户端交易时间                6位hhmmss
			String merchantNo,    //商户号                               0-30位
			String storeNo,       //门店号			 0-30位
			String terminal,      //终端号			 0-30位
			String userId,        //操作员			 0-30位
			String mac            //校验码      
			)throws Exception;
	
	/**
	 * 转账
	 * @param orderNo
	 * @param cardNoFrom
	 * @param cardNoTo
	 * @param amount
	 * @param clientDate
	 * @param clientTime
	 * @param merchantNo
	 * @param storeNo
	 * @param userId
	 * @param isverifyPwd
	 * @param password
	 * @param mac
	 * @return
	 * @throws Exception
	 */
	public String Transfer(
			String orderNo,       //订单号                               40位
			String cardNoFrom,    //起始卡号                           0-30位
			String cardNoTo,	  //终止卡号                           0-30位
			String amount,        //单张金额                           0-8位
			String clientDate,	  //客户端交易日期                8位yyyymmdd
			String clientTime,	  //客户端交易时间                6位hhmmss
			String merchantNo,    //商户号                               0-30位
			String storeNo,       //门店号			 0-30位
			String userId,        //操作员			 0-30位
			String isverifyPwd,	  //是否校验密码                    1位Y,N
			String password,      //密码                                   6-20位
			String mac            //校验码      
			)throws Exception;
	
	
	
	
	
	
}