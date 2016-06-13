package com.chinainpay.apps.pool;

import java.util.List;
import java.util.Map;


public interface Flow {
	/**
	 * 卡信息查询
	 * @param cardNoFrom
	 * @param cardNoTo
	 * @param isverifyPwd
	 * @param password
	 * @param isPager
	 * @param pageNo
	 * @param pageSize
	 * @param merchantNo
	 * @param storeNo
	 * @param userId
	 * @param mac
	 * @return   code返回码        msg返回信息       
	 *           CardList卡数据(cardNo卡号，balance余额，activeFate激活时间，
	 *                        status状态，hotReason暂停原因，expiredDate有效期，
	 *                        merchantNo发卡商户，storeNo发卡门店，userId操作员，
	 *                        Integral积分)
	 * @throws Exception
	 */
	public List<Map<String, Object>> cardInfo(
			String cardNoFrom,    //起始卡号                           0-30位
			String cardNoTo,	  //终止卡号                           0-30位
			String isverifyPwd,	  //是否校验密码                    1位Y,N
			String password,      //密码                                   6-20位
			String isPager,		  //是否分页
			String pageNo,		  //页码
			String pageSize,	  //页码大小
			String merchantNo,    //商户号                               0-30位
			String storeNo,       //门店号			 0-30位
			String userId,        //操作员			 0-30位
			String terminal            //终端号                            
			) throws Exception;
	
	/**
	 * 交易明细查询
	 * @param merchantNo
	 * @param storeNo
	 * @param userId
	 * @param cardNo
	 * @param type
	 * @param dateFrom
	 * @param dateTo
	 * @param isverifyPwd
	 * @param password
	 * @param isPager
	 * @param pageNo
	 * @param pageSize
	 * @param mac
	 * @return  orderNo流水号             channel渠道             type交易类型 
	 * 			amount金额                   status状态                date交易日期         
	 *          time交易时间                clientDate客户端交易日期
	 * 			clientTime客户端交易时间   merchantNo商户号      storeNo门店号    String terminal,  
	 * @throws Exception
	 */
	 
	public List<Map<String, Object>> tradeDetails(
			String cardNo,    	  //卡号                                   0-30位
			String isverifyPwd,	  //是否校验密码                    1位Y,N
			String password,      //密码                                   6-20位
			String isPager,		  //是否分页
			String pageNo,		  //页码
			String pageSize,	  //页码大小
			String merchantNo,    //商户号                               0-30位
			String storeNo,       //门店号			 0-30位
			String userId,        //操作员			 0-30位
			String type,          //查询类型                           T H 历史当天
			String dateFrom,      //起始日期
			String dateTo,        //结束日期
			String terminal       //终端号     
			)throws Exception;

}
