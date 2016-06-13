package com.chinainpay.apps.service;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import net.javabone.common.util.DaoUtil;
import net.javabone.common.util.TransactionDataSource;
import net.javabone.common.web.JsonUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.chinainpay.apps.pool.CardService;
import com.chinainpay.apps.pool.Flow;
import com.chinainpay.apps.pool.Trade;
import com.chinainpay.apps.pool.impl.CardServiceImpl;
import com.chinainpay.apps.pool.impl.FlowImpl;
import com.chinainpay.apps.pool.impl.TradeImpl;
import com.chinainpay.apps.util.DriveUtil;
import com.chinainpay.apps.util.HttpsTool;
import com.chinainpay.mp.app.pagelet.axis.P;
import com.chinainpay.mp.mq.client.ServiceNotFoundException;
import com.chinainpay.mp.mq.client.ServiceTimeoutException;

/**
 * 微信创建卡票券通用接口
 * 
 * @author ying
 * 
 */
public class CouponServices {
	private static String ticketColorUrl = "https://api.weixin.qq.com/card/getcolors";// 卡票颜色列表
	private static DataSource ds = P.ds("base");
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyyMMddHHmmss");

	/**
	 * 测试接口
	 * 
	 * @param merchantId
	 * @return
	 * @throws SQLException
	 */
	static public String test1(String openid) throws SQLException {
		String sql = "SELECT WX_CARD_NUM FROM T_CIPMP_MEMBER WHERE WX_OPENID=?";
		Map<String, Object> mp = DaoUtil.row(ds, sql, openid);
		return mp == null ? "" : mp.get("WX_CARD_NUM").toString();
	}

	/**
	 * 调用用户信息参数接口
	 * 
	 * @param merchantId
	 * @return
	 * @throws SQLException
	 */
	static public JSONArray parameters(String merchantId) throws SQLException {
		List<Map<String, Object>> memberDataList = DaoUtil.list(ds, 0,
				"SELECT * FROM T_CIPMP_MEMBER_DATA_SET WHERE MER_CODE=?", P.mp
						.getMerchant().getMerCode());
		return JSONArray.fromObject(memberDataList);
	}

	/**
	 * 虚拟卡售卡接口
	 * 
	 * @param merchantId
	 * @return
	 * @throws Exception 
	 */
	static public Map<String, Object> virtualSale(Map<String, Object> pojo)
			throws Exception {
		TransactionDataSource tds = new TransactionDataSource(ds);
		Map<String, Object> rMap = new HashMap<String, Object>();
		String openid = pojo.get("openid") == null ? "" : pojo.get("openid")
				.toString().trim();
		String mercode = pojo.get("mercode") == null ? "" : pojo.get("mercode")
				.toString().trim();
		String password = pojo.get("password") == null ? "" : pojo
				.get("password").toString().trim();
		String card_type = pojo.get("card_type") == null ? "" : pojo
				.get("card_type").toString().trim();
		String memb_phone = pojo.get("memb_phone") == null ? "" : pojo
				.get("memb_phone").toString().trim();
		String sql = "select * from T_CIPMP_CARD where MER_CODE=? and VIRTUAL_CARD='0' and CARD_STATE='1'";
		List<Map<String, Object>> list = DaoUtil.list(tds, 0, sql, mercode);
		String dateStr = dateFormat.format(new Date());
		if (list.size() <= 0) {
			rMap.put("msg", "卡片数量不足");
			rMap.put("code", "01");
			return rMap;
		}
		String cardNum = list.get(0).get("CARD_NUM").toString();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		if (DaoUtil.list(tds, 0,
						"select * from T_CIPMP_MEMBER where MEMB_PHONE=? AND MER_CODE=?",
						memb_phone,P.mp.getMerchant().getMerCode()).size() == 0) {
			String Sql = "INSERT into T_CIPMP_MEMBER(MEMB_PHONE, MER_CODE,WX_OPENID,REGISTER_TIME,WX_CARD_NUM) VALUES(?,?,?,?,?)";
			String msg = DaoUtil.execute(tds, Sql, memb_phone, P.mp
					.getMerchant().getMerCode(), openid, df.format(new Date()),
					cardNum) == 1 ? "" : "失败";
			if (!msg.endsWith("")) {
				tds.transactionRollback();
				tds.close();
				rMap.put("msg", "会员添加异常");
				rMap.put("code", "01");
				return rMap;
			}
		}else{
			String Sql = "UPDATE T_CIPMP_MEMBER SET WX_OPENID=?, WX_CARD_NUM=? WHERE MEMB_PHONE=? AND MER_CODE=?";
			String msg = DaoUtil.execute(tds, Sql,openid ,cardNum,memb_phone ,  P.mp
					.getMerchant().getMerCode()) == 1 ? "" : "失败";
			if (!msg.endsWith("")) {
				tds.transactionRollback();
				tds.close();
				rMap.put("msg", "会员信息修改异常");
				rMap.put("code", "01");
				return rMap;
			}
		}

		String msg1 = DaoUtil.execute(tds,
						"update T_CIPMP_CARD set CARD_PWD=?, LAST_TIME=?, MEMB_PHONE=?, CARD_STATE=? where CARD_NUM=?",
						password, dateStr, memb_phone, "2", cardNum) == 1 ? "" : "失败";
		if (msg1.equals("")) {
			// 重置密码
			String mtoreNo = null;
			String terminal = null;
			try {
				mtoreNo = DriveUtil.getDrive().get("mtoreNo").toString();
				terminal = DriveUtil.getDrive().get("terminal").toString();
				P.log.d("获取银商 商户号："+mtoreNo);
				P.log.d("获取银商 终端号："+terminal);
			} catch (Exception e1) {
				tds.transactionRollback();
				tds.close();
				e1.printStackTrace();
				rMap.put("msg", "数据连接失败");
				rMap.put("code", "02");
				return rMap;
			}
			String clientDate = dateStr.substring(0, 8);
			String clientTime = dateStr.substring(8);
			try {
				String orderno = mercode
						+ dateFormat.format(new Date().getTime());
				String money = "0";
				String msg = Recharge(orderno, cardNum,
						(Double.parseDouble(money) * 100) + "", clientDate,
						clientTime, mtoreNo, "", "", terminal);
				P.log.d("领取虚拟卡充值："+msg);
				if(!msg.equals("充值成功")){
					rMap.put("msg", "数据连接失败");
					rMap.put("code", "02");
					tds.transactionRollback();
					tds.close();
					return rMap;
				}
			} catch (Exception e) {
				tds.transactionRollback();
				tds.close();
				e.printStackTrace();
				rMap.put("msg", "数据连接失败");
				rMap.put("code", "02");
				return rMap;
			}
			P.log.d("cardNum---------="+cardNum+"----password------"+password);
			String msg = "";
			CardService cardService = new CardServiceImpl();
			while (!msg.equals("重置密码成功")) {
				msg =cardService.cardPwdReset(cardNum, password, clientDate,
						clientTime, mtoreNo, "", "", terminal);
				P.log.d("领取虚拟卡重置密码："+msg);
			}
			String msg2 = DaoUtil.execute(tds,
							"update T_CIPMP_CARD set CARD_PWD=?, LAST_TIME=? where CARD_NUM=?",
							password, dateStr, cardNum) == 1 ? "修改密码成功"
					: "修改密码失败";
			rMap.put("errorMsg", "");
			rMap.put("msg", "领取虚拟卡成功");
			rMap.put("code", "00");
			rMap.put("cardNo", list.get(0).get("CARD_NUM"));
			tds.transactionCommitOrRollback();
			return rMap;
		} else {
			rMap.put("msg", "数据连接失败");
			rMap.put("code", "02");
			tds.transactionCommitOrRollback();
			return rMap;
		}
	}

	/**
	 * 新增会员信息
	 * 
	 * @param card_type
	 * @param memb_phone
	 * @return
	 * @throws SQLException
	 */
	static public StringBuffer userInto(HashMap<String, String> pojo)
			throws SQLException {
		String dateStr = dateFormat.format(new Date());
		Map<String, Object> rMap = new HashMap<String, Object>();
		String mercode = pojo.get("mercode") == null ? "" : pojo.get("mercode")
				.toString().trim();// 商户号
		String memb_phone = pojo.get("memb_phone") == null ? "" : pojo
				.get("memb_phone").toString().trim();// 会员手机号
		String memb_name = pojo.get("memb_name") == null ? "" : pojo
				.get("memb_name").toString().trim();// 会员名
		String memb_sex = pojo.get("memb_sex") == null ? "" : pojo
				.get("memb_sex").toString().trim();// 会员性别
		String memb_store = "";
		if (DaoUtil
				.list(ds, 0,
						"select * from T_CIPMP_MEMBER where MEMB_PHONE = ?",
						memb_phone).size() == 0) {
			String Sql = "INSERT into T_CIPMP_MEMBER VALUES(?,?,?,NULL,NULL,?,NULL,NULL,NULL,?,?)";
			String msg = DaoUtil.execute(ds, Sql, memb_phone, P.mp
					.getMerchant().getMerCode(), memb_store, memb_name,
					memb_sex, dateStr) == 1 ? "" : "失败";
			/**
			 * 新添加的字段值进行持久化
			 */
			String sql = "SELECT * FROM T_CIPMP_MEMBER_DATA_SET WHERE MER_CODE=?";
			List<Map<String, Object>> dataList = DaoUtil.list(ds, 0, sql,
					mercode);
			for (int i = 0; i < dataList.size(); i++) {
				String proNo = dataList.get(i).get("PRO_NO").toString();
				String proName = dataList.get(i).get("PRO_NAME").toString();
				String isRequired = dataList.get(i).get("IS_REQUIRED")
						.toString();
				String proValue = pojo.get(mercode + "_" + proNo) == null ? ""
						: pojo.get(mercode + "_" + proNo).toString().trim();
				if (isRequired.equals("0") && proValue.equals("")) {
					rMap.put("msg", proName + "不能为空");
					rMap.put("code", "03");
					return new StringBuffer(JsonUtil.encoder((Map<?, ?>) rMap));
				}
				String insertSql = "INSERT INTO T_CIPMP_MEMBER_DATA_SET_VALUE VALUES(?,?,?,?)";
				String result = DaoUtil.execute(ds, insertSql, mercode, proNo,
						memb_phone, proValue) == 1 ? "OK" : "新增属性失败";
				;
				if (!result.equals("OK")) {
					rMap.put("msg", result);
					rMap.put("code", "01");
					return new StringBuffer(JsonUtil.encoder((Map<?, ?>) rMap));
				}
			}
			rMap.put("msg", "成功");
			rMap.put("code", "00");
			return new StringBuffer(JsonUtil.encoder((Map<?, ?>) rMap));
		}
		rMap.put("msg", "该手机号已存在用户");
		rMap.put("code", "02");
		return new StringBuffer(JsonUtil.encoder((Map<?, ?>) rMap));
	}

	/**
	 * 会员密码验证
	 * 
	 * @param pojo
	 * @return
	 * @throws SQLException
	 */
	static public Map<String, Object> passwordCheck(String mercode,
			String cardNo, String password) throws SQLException {
		Map<String, Object> rMap = new HashMap<String, Object>();
		String sql = "select * from T_CIPMP_CARD where MER_CODE =? and CARD_PWD=? and VIRTUAL_CARD='0' and CARD_STATE='2' and CARD_NUM=?";
		List<Map<String, Object>> list = DaoUtil.list(ds, 0, sql, mercode,
				password, cardNo);
		if (list.size() <= 0) {
			rMap.put("msg", "老密码错误");
			rMap.put("code", "01");
		} else {
			rMap.put("msg", "老密码正确");
			rMap.put("code", "00");
		}
		return rMap;
	}

	/**
	 * 获取已入库的所有虚拟卡类型和库存
	 */
	static public JSONArray virtualCardType(String mercode) throws SQLException {
		String sql = "select CARD_TYPE as type, count(*) as count from  T_CIPMP_CARD where  CARD_STATE='1' and T_CIPMP_CARD.VIRTUAL_CARD = '0' and T_CIPMP_CARD.MER_CODE = ?  group by CARD_TYPE";
		List<Map<String, Object>> list = DaoUtil.list(ds, 0, sql, mercode);
		return JSONArray.fromObject(list);
	}

	/**
	 * 卡余信息查询
	 */
	static public List<Map<String, Object>> cardInfo(String cardNo, // 起始卡号
			String mtoreNo, // 商户号 0-30位
			String storeNo, // 门店号 0-30位
			String userId, // 操作员 0-30位
			String terminal // 终端号
	) throws SQLException {
		Flow flow = new FlowImpl();
		try {
			return flow.cardInfo(cardNo, cardNo, "N", "", "N", "1", "10",
					mtoreNo, storeNo, userId, terminal);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 交易记录
	 */
	static public List<Map<String, Object>> tradeDetails(String cardNo, // 卡号
																		// 0-30位
			String isPager, // 是否分页
			String pageNo, // 页码
			String pageSize, // 页码大小
			String mtoreNo, // 商户号 0-30位
			String storeNo, // 门店号 0-30位
			String userId, // 操作员 0-30位
			String type, // 查询类型 T H 历史当天
			String dateFrom, // 起始日期
			String dateTo, // 结束日期
			String terminal // 终端号
	) throws SQLException {
		Flow flow = new FlowImpl();
		try {
			return flow.tradeDetails(cardNo, "N", "", isPager, pageNo,
					pageSize, mtoreNo, storeNo, userId, type, dateFrom, dateTo,
					terminal);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 充值
	 */
	static public String Recharge(String orderNo, // 订单号 40位
			String cardNo, // 起始卡号 0-30位
			String sales_money, // 金额 0-8位
			String clientDate, // 客户端交易日期 8位yyyymmdd
			String clientTime, // 客户端交易时间 6位hhmmss
			String mtoreNo, // 商户号 0-30位
			String storeNo, // 门店号 0-30位
			String userId, // 操作员 0-30位
			String terminal // 终端号
	) throws SQLException {
		Trade trade = new TradeImpl();
		try {
			P.log.d(orderNo+"--"+cardNo+"--"+sales_money +"--"+clientDate +"--"+clientTime +"--"+mtoreNo +"--"+storeNo+"--"+ userId+"--"+ terminal);
			return trade.Recharge(orderNo, cardNo, cardNo, sales_money,
					sales_money, clientDate, clientTime, mtoreNo, storeNo,
					userId, terminal);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 修改密码
	 */
	static public String cardPwdChange(String cardNo, // 终止卡号 Str 0-30 非空
			String oldPassword, // 旧密码 Str 6-20 非空
			String newPassword, // 新密码 Str 6-20 非空
			String clientDate, // 客户端交易日期 Str 8 yyyymmdd
			String clientTime, // 客户端交易时间 Str 6 hhmms
			String merchantNo, // 商户号 Str 0-30
			String storeNo, // 门店号 Str 0-30
			String userId, // 操作员 Str 0-30
			String terminal // 终端号
	) throws SQLException {
		CardService cardService = new CardServiceImpl();
		try {
			TransactionDataSource tds = new TransactionDataSource(ds);
			String dateStr = dateFormat.format(new Date());
			cardService.cardPwdChange(cardNo, oldPassword, newPassword,
					clientDate, clientTime, merchantNo, storeNo, userId,
					terminal);
			String msg1 = DaoUtil
					.execute(
							tds,
							"update T_CIPMP_CARD set CARD_PWD=? ,LAST_TIME=? where CARD_NUM=?",
							newPassword, dateStr, cardNo) == 1 ? "修改密码成功"
					: "修改密码失败";
			try {
				return msg1;
			} catch (Exception e) {
				e.printStackTrace();
				return msg1;
			} finally {
				tds.transactionCommitOrRollback();
			}
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 重置密码
	 */
	static public String cardPwdReset(String cardNo, // 终止卡号 Str 0-30 非空
			String password, // 新密码 Str 6-20 非空
			String clientDate, // 客户端交易日期 Str 8 yyyymmdd
			String clientTime, // 客户端交易时间 Str 6 hhmms
			String merchantNo, // 商户号 Str 0-30
			String storeNo, // 门店号 Str 0-30
			String userId, // 操作员 Str 0-30
			String terminal // 校验码
	) throws SQLException {
		CardService cardService = new CardServiceImpl();
		try {
			TransactionDataSource tds = new TransactionDataSource(ds);
			try {
				String msg = cardService.cardPwdReset(cardNo, password, clientDate,
						clientTime, merchantNo, storeNo, userId, terminal);
				 String dateStr = dateFormat.format(new Date());
				 msg = DaoUtil
						.execute(
								tds,
								"update T_CIPMP_CARD set CARD_PWD=? ,LAST_TIME=? where CARD_NUM=?",
								password, dateStr, cardNo) == 1 ? "修改密码成功" 
						: "修改密码失败";
				return msg;
			} catch (Exception e) {
				return "";
			} finally {
				tds.transactionCommitOrRollback();
			}
		} catch (Exception e) {
			return null;
		}
	}

	// ------------------------------------------微信端获得用户信息-------------------------------------------//
	/**
	 * 根据openid获得绑定微信用户的卡号
	 * 
	 * @throws SQLException
	 */
	static public String getCardNumByOpenId(String openid) throws SQLException {
		String sql = "SELECT WX_CARD_NUM FROM T_CIPMP_MEMBER WHERE WX_OPENID=?";
		Map<String, Object> mp = DaoUtil.row(ds, sql, openid);
		return mp == null ? "" : mp.get("WX_CARD_NUM").toString();
	}

	// 根据卡号获得商户号和门店号
	static public String getMerAndStroeInfoByCardNum(String cardNum)
			throws SQLException {
		String sql = "SELECT MER_CODE,MEMB_STORE FROM T_CIPMP_CARD WHERE CARD_NUM=?";
		Map<String, Object> mp = DaoUtil.row(ds, sql, cardNum);
		return mp == null ? "[{'MER_CODE':'','MEMB_STORE':''}]" : JSONArray
				.fromObject(mp).toString();
	}
	
	/**
	 * 获得卡券的最新颜色列表，用于卡券创建。
	 * 
	 * @return
	 * @throws ServiceTimeoutException
	 * @throws ServiceNotFoundException
	 */
	static public String getTicketColor() throws ServiceNotFoundException,
			ServiceTimeoutException {
		  String param = "access_token=" + P.mp.getService("Weixin", 60000, "getAccessTokey").getValue().toString();
		  String result = HttpsTool.sendHttpRequest(ticketColorUrl, param);
		  JSONObject resultJson=JSONObject.fromObject(result);
		  String colorList = "[{\"name\":\"Color010\",\"value\":\"#55bd47\"},{\"name\":\"Color020\",\"value\":\"#10ad61\"},{\"name\":\"Color030\",\"value\":\"#35a4de\"},{\"name\":\"Color040\",\"value\":\"#3d78da\"},{\"name\":\"Color050\",\"value\":\"#9058cb\"},{\"name\":\"Color060\",\"value\":\"#de9c33\"},{\"name\":\"Color070\",\"value\":\"#ebac16\"},{\"name\":\"Color080\",\"value\":\"#f9861f\"},{\"name\":\"Color081\",\"value\":\"#f08500\"},{\"name\":\"Color090\",\"value\":\"#e75735\"},{\"name\":\"Color100\",\"value\":\"#d54036\"},{\"name\":\"Color101\",\"value\":\" #cf3e36\"}]";
		  if(resultJson.get("errmsg").equals("ok")){
			  colorList=resultJson.getString("colors");
		  }
		return colorList;
	}
	
	/**
	 * 向别的程序提供卡券列表页
	 * @param mercode	商户号
	 * @param cardtype	卡券类型
	 */
	static public List<Map<String, Object>> getTicketList(String mercode,String cardtype){
		StringBuilder sql=new StringBuilder();
		StringBuilder sql2=new StringBuilder();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();
		try {
			if(cardtype.equalsIgnoreCase("CASH")){
				sql.append("SELECT '代金券' AS card_type,tc.card_id,bi.title,bi.sub_title FROM T_CIPMP_TICKET_CASH tc ");
				sql.append("INNER JOIN T_CIPMP_BASE_INFO bi ON bi.base_info_id=tc.base_info_id ");
				sql.append("WHERE tc.MER_CODE=? AND bi.date_info_type='1' AND TO_DAYS(NOW())>=TO_DAYS(bi.begin_timestamp) AND TO_DAYS(NOW())<=TO_DAYS(bi.end_timestamp) ");
				list=DaoUtil.list(ds, 0, sql.toString(), mercode);
				
				sql2.append("SELECT '代金券',tc.card_id,bi.title,bi.sub_title FROM T_CIPMP_TICKET_CASH tc ");
				sql2.append("INNER JOIN T_CIPMP_BASE_INFO bi ON bi.base_info_id=tc.base_info_id ");
				sql2.append("WHERE tc.MER_CODE=? AND bi.date_info_type='2' ");
				list2=DaoUtil.list(ds, 0, sql2.toString(), mercode);
				if(list.size()!=0){
					if(list2.size()!=0){
						list.addAll(list2);
					}
				}else{
					return list2;
				}
			}else if(cardtype.equalsIgnoreCase("DISCOUNT")){
				sql.append("SELECT '折扣券'AS card_type,td.card_id,bi.title,bi.sub_title,td.discount ");
				sql.append("FROM T_CIPMP_TICKET_DISCOUNT td ");
				sql.append("INNER JOIN T_CIPMP_BASE_INFO bi ON bi.base_info_id=td.base_info_id ");
				sql.append("WHERE td.MER_CODE=? AND bi.date_info_type='1' AND TO_DAYS(NOW())>=TO_DAYS(bi.begin_timestamp) AND TO_DAYS(NOW())<=TO_DAYS(bi.end_timestamp) ");
				list=DaoUtil.list(ds, 0, sql.toString(), mercode);
				
				sql2.append("SELECT '折扣券'AS card_type,td.card_id,bi.title,bi.sub_title,td.discount ");
				sql2.append("FROM T_CIPMP_TICKET_DISCOUNT td ");
				sql2.append("INNER JOIN T_CIPMP_BASE_INFO bi ON bi.base_info_id=td.base_info_id ");
				sql2.append("WHERE td.MER_CODE=? AND bi.date_info_type='2' ");
				list2=DaoUtil.list(ds, 0, sql2.toString(), mercode);
				if(list.size()!=0){
					if(list2.size()!=0){
						list.addAll(list2);
					}
				}else{
					return list2;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
