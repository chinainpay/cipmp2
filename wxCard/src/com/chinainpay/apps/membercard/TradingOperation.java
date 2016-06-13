package com.chinainpay.apps.membercard;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import net.javabone.common.util.DaoUtil;
import net.javabone.common.util.RegexpUtils;
import net.javabone.common.web.JsonUtil;

import org.apache.commons.lang.StringUtils;

import com.chinainpay.apps.pool.CardService;
import com.chinainpay.apps.pool.Flow;
import com.chinainpay.apps.pool.Trade;
import com.chinainpay.apps.pool.impl.CardServiceImpl;
import com.chinainpay.apps.pool.impl.FlowImpl;
import com.chinainpay.apps.pool.impl.TradeImpl;
import com.chinainpay.apps.util.DriveUtil;
import com.chinainpay.mp.app.pagelet.Pagelet;
import com.chinainpay.mp.app.pagelet.Pagelet.RequestParam;
import com.chinainpay.mp.app.pagelet.axis.P;

public class TradingOperation implements Pagelet {
	private DataSource ds = P.ds("base");
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyyMMddHHmmss");
	/**
	 * 交易操作面板
	 */
	private static final long serialVersionUID = -3488459679532727164L;

	@Execute("访问")
	@Pagelet.Purview()
	public void execute() throws SQLException {
		List<Map<String, Object>> LEVELlist = DaoUtil.list(ds, 0,
				"select * from T_CIPMP_CARD_TYPE where MER_CODE =?", P.mp
						.getMerchant().getMerCode());
		String Tongtype = DaoUtil
				.list(ds,
						0,
						"select * from T_CIPMP_MEMBER_CARD_SETUP where MER_CODE = ?",
						P.mp.getMerchant().getMerCode()).get(0)
				.get("ALLOW_ACTIVATION_TREAMCARD").toString();
		List<Map<String, Object>> memberDataList = DaoUtil.list(ds, 0,
				"SELECT * FROM T_CIPMP_MEMBER_DATA_SET WHERE MER_CODE=?", P.mp
						.getMerchant().getMerCode());
		P.log.d(memberDataList);
		P.setAttribute("memberDataList", memberDataList);// 商户个人售卡列表集合
		P.setAttribute("LEVEL", LEVELlist);
		P.setAttribute("Tongtype", Tongtype);
		P.setAttribute("oper_user", P.mp.getAdmin().getCode());
	}

	@Execute("换卡")
	@Pagelet.Purview("execute")
	public void replacement(@RequestParam("pojo") HashMap<String, String> pojo)
			throws Exception {
		String cardNoFrom = pojo.get("card_no") == null ? "" : pojo
				.get("card_no").toString().trim();
		String cardNoTo = pojo.get("cardNoTo") == null ? "" : pojo
				.get("cardNoTo").toString().trim();
		String password = pojo.get("password") == null ? "" : pojo
				.get("password").toString().trim();
		String dateStr = dateFormat.format(new Date());
		String clientDate = dateStr.substring(0, 8);
		String clientTime = dateStr.substring(8);
		String merchantNo = "102100079970534";// P.mp.getMerchant().getMerCode();
		String oper_user = P.mp.getAdmin().getCode() == null ? "" : P.mp
				.getAdmin().getCode();
		String store_num = "";
		CardService cardService = new CardServiceImpl();
		String data = cardService.cardChange(cardNoFrom, cardNoTo, password,
				clientDate, clientTime, merchantNo, store_num, oper_user, "");
		if (data == "") {
			String cardSql = "select * from T_CIPMP_CARD where CARD_NUM =?";
			List<Map<String, Object>> list = DaoUtil.list(ds, 0, cardSql,
					cardNoFrom);
			DaoUtil.execute(
					ds,
					"update T_CIPMP_CARD set LAST_USER=?,LAST_TIME=?, MEMB_PHONE=?  where CARD_NUM=?",
					oper_user, clientDate, list.get(0).get("MEMB_PHONE"),
					cardNoTo);
			DaoUtil.execute(
					ds,
					"update T_CIPMP_CARD set LAST_USER=null,LAST_TIME=null, MEMB_PHONE=null  where CARD_NUM=?",
					cardNoFrom);
		}
	}

	@Execute("充值")
	@Pagelet.Purview("execute")
	public StringBuffer recharge(
			@RequestParam("pojo") HashMap<String, String> pojo)
			throws Exception {
		Map<String, Object> rMap = new HashMap<String, Object>();
		String card_no = pojo.get("card_no") == null ? "" : pojo.get("card_no")
				.toString().trim();
		String sales_money = pojo.get("sales_money") == null ? "" : pojo
				.get("sales_money").toString().trim();
		String sales_type = pojo.get("sales_type") == null ? "" : pojo
				.get("sales_type").toString().trim();
		String sales_text = pojo.get("sales_text") == null ? "" : pojo
				.get("sales_text").toString().trim();
		String dateStr = dateFormat.format(new Date());
		String clientDate = dateStr.substring(0, 8);
		String clientTime = dateStr.substring(8);
		String merchantNo = P.mp.getMerchant().getMerCode();
		String oper_user = P.mp.getAdmin().getCode();
		String store_num = "";
		String cardSql = "";
		cardSql = "select * from T_CIPMP_CARD where CARD_NUM =?";
		if (card_no.length() == 11) {
			cardSql = "select * from T_CIPMP_CARD where MEMB_PHONE =?";
		}

		List<Map<String, Object>> list = DaoUtil.list(ds, 0, cardSql, card_no);
		if (list.size() == 0) {
			rMap.put("errorMsg", "没有该类型卡号");
			return new StringBuffer(JsonUtil.encoder((Map<?, ?>) rMap));
		}
		if (list.size() != 1) {
			rMap.put("errorMsg", "您的手机绑定多张卡号，请使用卡号充值");
			return new StringBuffer(JsonUtil.encoder((Map<?, ?>) rMap));
		}
		if (!list.get(0).get("MER_CODE").equals(merchantNo)) {
			rMap.put("errorMsg", "您无权对此卡充值");
			return new StringBuffer(JsonUtil.encoder((Map<?, ?>) rMap));
		}
		if (!list.get(0).get("CARD_STATE").equals("2")) {
			rMap.put("errorMsg", "该卡号未售卖");
			return new StringBuffer(JsonUtil.encoder((Map<?, ?>) rMap));
		}
		List<Map<String, Object>> list2 = DaoUtil
				.list(ds,
						0,
						"select * from T_CIPMP_SALES_RECORD where USER_PHONE =? and STATE='1'",
						list.get(0).get("MEMB_PHONE"));
		if (list2.size() > 0) {
			rMap.put("errorMsg", "该卡号未激活");
			return new StringBuffer(JsonUtil.encoder((Map<?, ?>) rMap));
		}
		Trade trade = new TradeImpl();
		Date d = new Date();
		P.log.d("充值金额" + sales_money);
		String orderno = d.getTime() + store_num;
		String data;
		String mtoreNo = null;
		String terminal = null;
		try {
			mtoreNo = DriveUtil.getDrive().get("mtoreNo").toString();
			terminal = DriveUtil.getDrive().get("terminal").toString();
		} catch (Exception e1) {
			rMap.put("errorMsg", "请先添加支付驱动");
			return new StringBuffer(JsonUtil.encoder((Map<?, ?>) rMap));
		}
		try {
			data = trade.Recharge(orderno, list.get(0).get("CARD_NUM")
					.toString(), list.get(0).get("CARD_NUM").toString(),
					(Double.parseDouble(sales_money) * 100) + "", (Double.parseDouble(sales_money) * 100) + "", clientDate, clientTime,
					mtoreNo, store_num, oper_user, terminal);
		} catch (Exception e) {
			data = "充值异常";
			e.printStackTrace();
		}
		DaoUtil.execute(ds, "INSERT INTO T_CIPMP_RECHARGE_ORDER(ORDER_ID,CORD_NO,RECHARGE_TYPE,RECHARGE_MONEY,RECHARGE_TIME,RECHARGE_START,MER_CODE,OPERATOR_CODE,RECHARGE_TEXT) VALUES(?,?,?,?,?,?,?,?,?);", orderno,list.get(0).get("CARD_NUM")
				.toString(),sales_type,(int)(Double.parseDouble(sales_money) * 100),dateStr,data,merchantNo,P.mp.getAdmin().getName(),sales_text);
		rMap.put("errorMsg", data);
		return new StringBuffer(JsonUtil.encoder((Map<?, ?>) rMap));
	}

	@Execute("个人售卡")
	@Pagelet.Purview("execute")
	public StringBuffer personal(
			@RequestParam("pojo") HashMap<String, String> pojo)
			throws Exception {
		// TransactionDataSource ds = new TransactionDataSource(ds);
		String mercode = P.mp.getMerchant().getMerCode();
		String orderno = mercode+dateFormat.format(new Date().getTime());
		Map<String, Object> rMap = new HashMap<String, Object>();
		String card_type = pojo.get("proc_no") == null ? "" : pojo
				.get("proc_no").toString().trim();
		String card_no = pojo.get("card_no") == null ? "" : pojo.get("card_no")
				.toString().trim();
		String sales_money = pojo.get("sales_money") == null ? "" : pojo
				.get("sales_money").toString().trim();
		String memb_phone = pojo.get("memb_phone") == null ? "" : pojo
				.get("memb_phone").toString().trim();
		String memb_name = pojo.get("memb_name") == null ? "" : pojo
				.get("memb_name").toString().trim();
		String memb_sex = pojo.get("memb_sex") == null ? "" : pojo
				.get("memb_sex").toString().trim();
		String actualMoney = pojo.get("actual_money") == null ? "" : pojo
				.get("actual_money").toString().trim();
		String payment_type = pojo.get("payment_type") == null ? "" : pojo
				.get("payment_type").toString().trim();
		if (!RegexpUtils.isNumber(card_no)) {
			rMap.put("errorMsg", "卡号填写错误");
			return new StringBuffer(JsonUtil.encoder((Map<?, ?>) rMap));
		}
		if (!RegexpUtils.isPositiveNumber(actualMoney)) {
			rMap.put("errorMsg", "金额填写错误");
			return new StringBuffer(JsonUtil.encoder((Map<?, ?>) rMap));
		}
		String cardSql = "select * from T_CIPMP_CARD where MER_CODE =? and  MEMB_STORE is NULL and CARD_TYPE=?  and VIRTUAL_CARD='1' and CARD_NUM =?";// 查出一张商户下的卡片
		String cardSql2 = "select * from T_CIPMP_CARD where MER_CODE =? and MEMB_STORE=? and CARD_TYPE=?  and VIRTUAL_CARD='1'  and  CARD_NUM =?";// 查出一张门店下的卡片
		List<Map<String, Object>> list;
		String memb_store = "";
		if (StringUtils.isEmpty(memb_store)) {
			list = DaoUtil.list(ds, 0, cardSql,
					P.mp.getMerchant().getMerCode(), card_type, card_no);
		} else {
			list = DaoUtil.list(ds, 0, cardSql2, P.mp.getMerchant()
					.getMerCode(), memb_store, card_type, card_no);
		}
		if (list.size() <= 0) {
			rMap.put("errorMsg", "没有该类型卡号");
			return new StringBuffer(JsonUtil.encoder((Map<?, ?>) rMap));
		}
		if (list.get(0).get("CARD_STATE").equals("2")) {
			rMap.put("errorMsg", "该卡号已售卖");
			return new StringBuffer(JsonUtil.encoder((Map<?, ?>) rMap));
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		// 判断账户是否存在，
		String store_num = "";
		if (DaoUtil
				.list(ds, 0,
						"select * from T_CIPMP_MEMBER where MEMB_PHONE = ?",
						memb_phone).size() == 0) {
			String Sql = "INSERT into T_CIPMP_MEMBER(MEMB_PHONE, MER_CODE, MEMB_STORE,MEMB_NAME,MEMB_SEX,REGISTER_TIME) VALUES(?,?,?,?,?,?)";
			String msg = DaoUtil.execute(ds, Sql, memb_phone, P.mp
					.getMerchant().getMerCode(), memb_store, memb_name,
					memb_sex, df.format(new Date())) == 1 ? "" : "失败";
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
					rMap.put("errorMsg", proName + "不能为空");
					return new StringBuffer(JsonUtil.encoder((Map<?, ?>) rMap));
				}
				String insertSql = "INSERT INTO T_CIPMP_MEMBER_DATA_SET_VALUE VALUES(?,?,?,?)";
				String result = DaoUtil.execute(ds, insertSql, mercode, proNo,
						memb_phone, proValue) == 1 ? "OK" : "新增属性失败";
				;
				if (!result.equals("OK")) {
					rMap.put("errorMsg", result);
					return new StringBuffer(JsonUtil.encoder((Map<?, ?>) rMap));
				}
			}
		}
		String dateStr = dateFormat.format(new Date());
		String oper_user = P.mp.getAdmin().getCode() == null ? "" : P.mp
				.getAdmin().getCode();

		String msg1 = DaoUtil
				.execute(
						ds,
						"update T_CIPMP_CARD set LAST_USER=?,LAST_TIME=?, MEMB_PHONE=?,CARD_STATE=?  where CARD_NUM=?",
						oper_user, dateStr, memb_phone, "2",
						list.get(0).get("CARD_NUM")) == 1 ? "" : "失败";
		String STATE = "1";
		if (DaoUtil
				.list(ds,
						0,
						"select * from T_CIPMP_MEMBER_CARD_SETUP where MER_CODE = ?",
						P.mp.getMerchant().getMerCode()).get(0)
				.get("ALLOW_ACTIVATION_PERSONALCARD").equals("1")) {
			STATE = "2";
			Trade trade = new TradeImpl();
			Date d = new Date();
			String clientDate = dateStr.substring(0, 8);
			String clientTime = dateStr.substring(8);
			String mtoreNo = null;
			String terminal = null;
			try {
				mtoreNo = DriveUtil.getDrive().get("mtoreNo").toString();
				terminal = DriveUtil.getDrive().get("terminal").toString();
			} catch (Exception e1) {
				rMap.put("errorMsg", "请先添加支付驱动");
				return new StringBuffer(JsonUtil.encoder((Map<?, ?>) rMap));
			}
			try {
				trade.Recharge(orderno, list.get(0).get("CARD_NUM").toString(),
						list.get(0).get("CARD_NUM").toString(),
						(Double.parseDouble(sales_money) * 100) + "",
						(Double.parseDouble(sales_money) * 100) + "",
						clientDate, clientTime, mtoreNo, store_num,
						oper_user, terminal);
			} catch (Exception e) {
				STATE = "1";
				e.printStackTrace();
			}
		} else {
			STATE = "1";
		}
		String msg2 = DaoUtil
				.execute(
						ds,
						"INSERT into T_CIPMP_SALES_RECORD VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
						orderno, list.get(0).get("CARD_NUM"),
						list.get(0).get("CARD_NUM"), list.size(), (Double
								.parseDouble(sales_money) * 100), oper_user,
						dateStr, STATE,
						(Double.parseDouble(actualMoney) * 100), P.mp
								.getMerchant().getMerCode(), memb_store,
						memb_name, memb_phone, payment_type, "0") == 1 ? ""
				: "售卖失败";
		rMap.put("cardNo", list.get(0).get("CARD_NUM"));
		rMap.put("errorMsg", msg2);
		return new StringBuffer(JsonUtil.encoder((Map<?, ?>) rMap));

	}

	@Execute("团体售卡")
	@Pagelet.Purview("execute")
	public StringBuffer group(@RequestParam("pojo") HashMap<String, String> pojo)
			throws Exception {
		Map<String, Object> rMap = new HashMap<String, Object>();
		String mercode = P.mp.getMerchant().getMerCode();
		String orderno = mercode+dateFormat.format(new Date().getTime());
		String cardNoForm = pojo.get("cardNoForm") == null ? "" : pojo
				.get("cardNoForm").toString().trim();
		String cardNoTo = pojo.get("cardNoTo") == null ? "" : pojo
				.get("cardNoTo").toString().trim();
		String proc_no = pojo.get("proc_no") == null ? "" : pojo.get("proc_no")
				.toString().trim();
		String sales_money = pojo.get("sales_money") == null ? "" : pojo
				.get("sales_money").toString().trim();
		String money = pojo.get("money") == null ? "" : pojo.get("money")
				.toString().trim();
		String memb_phone = pojo.get("memb_phone") == null ? "" : pojo
				.get("memb_phone").toString().trim();
		String memb_name = pojo.get("memb_name") == null ? "" : pojo
				.get("memb_name").toString().trim();
		String memb_store = pojo.get("memb_store") == null ? "" : pojo
				.get("memb_store").toString().trim();
		String card_mun = pojo.get("card_mun") == null ? "" : pojo
				.get("card_mun").toString().trim();
		String payment_type = pojo.get("payment_type") == null ? "" : pojo
				.get("payment_type").toString().trim();
		String actualMoney = pojo.get("actual_money") == null ? "" : pojo
				.get("actual_money").toString().trim();
		if (!RegexpUtils.isNumber(cardNoTo)
				|| !RegexpUtils.isNumber(cardNoForm)) {
			rMap.put("errorMsg", "卡号填写错误");
			return new StringBuffer(JsonUtil.encoder((Map<?, ?>) rMap));
		}
		if (!RegexpUtils.isNumber(sales_money)
				|| !RegexpUtils.isNumber(actualMoney)) {
			rMap.put("errorMsg", "金额填写错误");
			return new StringBuffer(JsonUtil.encoder((Map<?, ?>) rMap));
		}
		String cardSql = "select * from T_CIPMP_CARD where MER_CODE =? and MEMB_STORE is NULL and CARD_TYPE=?   and VIRTUAL_CARD='1' and CARD_STATE='1'and  CARD_NUM >= ?  and CARD_NUM <= ?";
		String cardSql2 = "select * from T_CIPMP_CARD where MER_CODE =? and MEMB_STORE=? and CARD_TYPE=?  and VIRTUAL_CARD='1'  and CARD_STATE='1' and  CARD_NUM >= ? and CARD_NUM <= ? ";
		List<Map<String, Object>> list;
		P.log.d(mercode);
		if (StringUtils.isEmpty(memb_store)) {
			list = DaoUtil.list(ds, 0, cardSql,
					P.mp.getMerchant().getMerCode(), proc_no, cardNoForm,
					cardNoTo);
		} else {
			list = DaoUtil.list(ds, 0, cardSql2, P.mp.getMerchant()
					.getMerCode(), memb_store, proc_no, proc_no, cardNoForm,
					cardNoTo);
		}
		if (list.size() != Integer.parseInt(card_mun)) {
			rMap.put("errorMsg", "卡号段或类型有误");
			return new StringBuffer(JsonUtil.encoder((Map<?, ?>) rMap));
		}
		String dateStr = dateFormat.format(new Date());
		String oper_user = P.mp.getAdmin().getCode() == null ? "" : P.mp
				.getAdmin().getCode();
		for (int i = 0; i < list.size(); i++) {

			DaoUtil.execute(
					ds,
					"update T_CIPMP_CARD set LAST_USER=?,LAST_TIME=?, MEMB_PHONE=?,CARD_STATE=? where CARD_NUM=?",
					oper_user, dateStr, memb_phone, "2",
					list.get(i).get("CARD_NUM"));

		}
		String store_num = "";
		String STATE = "1";
		if (DaoUtil
				.list(ds,
						0,
						"select * from T_CIPMP_MEMBER_CARD_SETUP where MER_CODE = ?",
						P.mp.getMerchant().getMerCode()).get(0)
				.get("ALLOW_ACTIVATION_PERSONALCARD").equals("1")) {
			STATE = "2";
			Trade trade = new TradeImpl();
			Date d = new Date();
			String clientDate = dateStr.substring(0, 8);
			String clientTime = dateStr.substring(8);
			String mtoreNo = null;
			String terminal = null;
			try {
				mtoreNo = DriveUtil.getDrive().get("mtoreNo").toString();
				terminal = DriveUtil.getDrive().get("terminal").toString();
			} catch (Exception e1) {
				rMap.put("errorMsg", "请先添加支付驱动");
				return new StringBuffer(JsonUtil.encoder((Map<?, ?>) rMap));
			}
			try {
				trade.Recharge(orderno,
						list.get(list.size() - 1).get("CARD_NUM").toString(),
						list.get(0).get("CARD_NUM").toString(),
						(Double.parseDouble(sales_money) * 100) + "",
						(Double.parseDouble(money) * 100) + "", clientDate,
						clientTime, mtoreNo, store_num, oper_user,terminal);
			} catch (Exception e) {
				STATE = "1";
				e.printStackTrace();
			}
		} else {
			STATE = "1";
		}
		// 存储售卖记录（未激活）
		String msg2 = DaoUtil
				.execute(
						ds,
						"INSERT into T_CIPMP_SALES_RECORD VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
						orderno, list.get(0).get("CARD_NUM"),
						list.get(list.size() - 1).get("CARD_NUM"), list.size(),
						(Double.parseDouble(sales_money) * 100), oper_user,
						dateStr, STATE,
						(Double.parseDouble(actualMoney) * 100), P.mp
								.getMerchant().getMerCode(), memb_store,
						memb_name, memb_phone, payment_type, "1") == 1 ? ""
				: "失败";
		rMap.put("cardNoFrom", list.get(0).get("CARD_NUM"));
		rMap.put("cardNoTo", list.get(list.size() - 1).get("CARD_NUM"));
		rMap.put("errorMsg", msg2);
		P.log.d(rMap);
		return new StringBuffer(JsonUtil.encoder((Map<?, ?>) rMap));

	}

	@Execute("会员交易查询")
	@Pagelet.Purview("execute")
	public void transaction(@RequestParam("pojo") HashMap<String, String> pojo,@RequestParam("page") String page2,@RequestParam("TRADE_DATE_S") String TRADE_DATE_S,@RequestParam("TRADE_DATE_E") String TRADE_DATE_E)
			throws Exception {
		int size=20;
		StringBuffer sb=new StringBuffer("SELECT * FROM T_CIPMP_RECHARGE_ORDER WHERE MER_CODE=?");
		StringBuffer sb2=new StringBuffer("SELECT  count(*)as sumorder, sum(RECHARGE_MONEY) as summoney  FROM T_CIPMP_RECHARGE_ORDER WHERE MER_CODE=?");
		int count= (int)DaoUtil.count(ds, sb.toString(), P.mp.getMerchant().getMerCode());
		List<Object> obj=new ArrayList<Object>();
		String time1=null;
		String time2=null;
		obj.add(P.mp.getMerchant().getMerCode());
		if(!TRADE_DATE_S.equals("")&&!TRADE_DATE_E.equals("")){
			time1=TRADE_DATE_S+" 00:00:00";
			time2=TRADE_DATE_E+" 23:59:59";
			System.out.println(time1);
			sb.append(" and RECHARGE_TIME between ? and ?");
			sb2.append(" and RECHARGE_TIME between ? and ?");
			obj.add(time1);
			obj.add(time2);
		} else
		if (TRADE_DATE_S!=null&&!TRADE_DATE_S.equals("")) {
			time1=TRADE_DATE_S+" 00:00:00";
			sb.append(" and RECHARGE_TIME >= '"+time1+"'");
			sb2.append(" and RECHARGE_TIME >= '"+time1+"'");
		}else
		if (TRADE_DATE_E!=null&&!TRADE_DATE_E.equals("")) {
			time2=TRADE_DATE_E+" 23:59:59";
			sb.append(" and RECHARGE_TIME <= '"+time2+"'");
			sb2.append(" and RECHARGE_TIME <= '"+time2+"'");
		}
		
		int totalPages = count % size == 0 ? count/ size : count / size + 1;
		System.out.println(totalPages+"总页数,总条数"+count);
		int page = page2 == null || page2.equals("") ? 1 : Integer
				.parseInt(page2);
		if (page<1) {
			page=1;
		}
		if (page>totalPages) {
			page = totalPages;
		}
		List<Map<String, Object>> dataList = DaoUtil.list(ds,size,page-1, sb.toString(),
				obj.toArray());
		P.setAttribute("dataList", dataList);
		
		Map<String, Object> sumList = DaoUtil.row(ds, sb2.toString(),
				obj.toArray());
		P.setAttribute("sumList", sumList);
		P.setAttribute("TRADE_DATE_S",TRADE_DATE_S);
		P.setAttribute("TRADE_DATE_E",TRADE_DATE_E);
		P.setAttribute("sum",totalPages);
		P.setAttribute("page", page);
	}

	@Execute("卡状态操作（冻结解冻挂失解挂）")
	@Pagelet.Purview("execute")
	public StringBuffer stat(@RequestParam("pojo") HashMap<String, String> pojo)
			throws Exception {
		Map<String, Object> rMap = new HashMap<String, Object>();
		String cardNo = pojo.get("cardNo") == null ? "" : pojo.get("cardNo")
				.toString().trim();  
		String storeNo = "";

		// String type: 操作代码，ICLK-冻结，ICUK-冻结，ICLT-挂失，ICUT-解挂，非空
		String type = pojo.get("type") == null ? "" : pojo.get("type")
				.toString().trim();
		P.log.d("操作卡号" + cardNo + "类型" + type);
		String merchantNo =  P.mp.getMerchant().getMerCode();
		String oper_user = P.mp.getAdmin().getCode() == null ? "" : P.mp
				.getAdmin().getCode();
		String dateStr = dateFormat.format(new Date());
		String clientDate = dateStr.substring(0, 8);
		String clientTime = dateStr.substring(8);
		CardService c = new CardServiceImpl();
		String mtoreNo = null;
		String terminal = null;
		try {
			mtoreNo = DriveUtil.getDrive().get("mtoreNo").toString();
			terminal = DriveUtil.getDrive().get("terminal").toString();
		} catch (Exception e1) {
			rMap.put("errorMsg", "请先添加支付驱动");
			return new StringBuffer(JsonUtil.encoder((Map<?, ?>) rMap));
		}
		String data = c.cardState(cardNo, cardNo, type, clientDate, clientTime,
				mtoreNo, storeNo, oper_user, terminal);
		rMap.put("errorMsg", "");
		rMap.put("Msg", data);
		P.log.d("操作结果" + rMap);
		return new StringBuffer(JsonUtil.encoder((Map<?, ?>) rMap));
	}

}
