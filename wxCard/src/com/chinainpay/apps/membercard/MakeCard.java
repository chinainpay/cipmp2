package com.chinainpay.apps.membercard;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import net.javabone.common.crypto.CheckBit;
import net.javabone.common.util.DaoUtil;
import net.javabone.common.util.RegexpUtils;
import net.javabone.common.web.JsonUtil;

import com.chinainpay.apps.util.CurrentTime;
import com.chinainpay.mp.app.pagelet.Pagelet;
import com.chinainpay.mp.app.pagelet.axis.P;

public class MakeCard implements Pagelet {
	private DataSource ds = P.ds("base");
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyyMMddHHmmss");
	/**
	 * 实体会员卡管理
	 */
	private static final long serialVersionUID = 6999902554463319214L;

	@Execute("访问")
	@Pagelet.Purview()
	public void execute() throws SQLException {
		String merCode = P.mp.getMerchant().getMerCode();
		// String merCode = "01";
		// 查询制卡等级
		List<Map<String, Object>> LEVELlist = DaoUtil.list(ds, 0,
				"select * from T_CIPMP_CARD_TYPE where MER_CODE=?", merCode);
		P.setAttribute("LEVEL", LEVELlist);
	}

	@Execute("制卡申请")
	@Pagelet.Purview("execute")
	public StringBuffer cardMakeApply(
			@RequestParam("pojo") HashMap<String, String> pojo)
			throws Exception {
		String msg = "";
		Map<String, Object> rMap = new HashMap<String, Object>();
		String merCode = P.mp.getMerchant().getMerCode();
		String makecard_no = CheckBit.generate(System.currentTimeMillis() + "");// 制卡编号
																				// 当前时间戳+一位校验码
		String proc_no = "普卡";
//		if (proc_no.equals("")) {
//			msg = "卡名称不能为空";
//			rMap.put("errorMsg", msg);
//			return new StringBuffer(JsonUtil.encoder(rMap));
//		}
		String card_type = pojo.get("card_type") == null ? "" : pojo
				.get("card_type").toString().trim();
		if (card_type.equals("")) {
			msg = "卡类型不能为空";
			rMap.put("errorMsg", msg);
			return new StringBuffer(JsonUtil.encoder(rMap));
		}
		String card_states = "0";
		String ask_user =P.mp.getAdmin().getCode() == null ? "" : P.mp
				.getAdmin().getCode();
		String card_amt = pojo.get("card_amt") == null ? "" : pojo
				.get("card_amt").toString().trim();
		if (card_amt.equals("")) {
			msg = "制卡数量不能为空";
			rMap.put("errorMsg", msg);
			return new StringBuffer(JsonUtil.encoder(rMap));
		} else if (!RegexpUtils.isZIndex(card_amt)
				|| Integer.parseInt(card_amt) % 100 != 0) {
			msg = "请输入100的整数倍";
			rMap.put("errorMsg", msg);
			return new StringBuffer(JsonUtil.encoder(rMap));
		} else if (Integer.parseInt(card_amt) > 10000) {
			msg = "制卡数量应小于1万张";
			rMap.put("errorMsg", msg);
			return new StringBuffer(JsonUtil.encoder(rMap));
		}
		String cardSql = "INSERT INTO T_CIPMP_MAKECARD VALUES(?,?,?,?,NULL,NULL,?,?,?,?,?,'0',NULL,NULL,NULL)";
		msg = DaoUtil.execute(ds, cardSql, makecard_no, merCode, "",
				proc_no, card_amt, card_type, ask_user,
				CurrentTime.getSystemTime(), card_states) == 1 ? "OK" : "失败";
		if (msg.equals("")) {
			msg = "新增卡类型失败";
			rMap.put("errorMsg", msg);
		}
		return new StringBuffer(JsonUtil.encoder(rMap));
	}
}
