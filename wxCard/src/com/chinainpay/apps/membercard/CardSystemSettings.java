package com.chinainpay.apps.membercard;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import net.javabone.common.util.DaoUtil;

import com.chinainpay.mp.app.pagelet.Pagelet;
import com.chinainpay.mp.app.pagelet.axis.P;

public class CardSystemSettings implements Pagelet {
	private DataSource ds = P.ds("base");
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyyMMddHHmmss");
	/**
	 * 会员系统设置
	 */
	private static final long serialVersionUID = -8640116813742422147L;

	@Execute("访问")
	@Pagelet.Purview()
	public void execute() throws SQLException {
		List<Map<String, Object>> list = DaoUtil.list(ds, 0,
				"select * from T_CIPMP_PROCARD_ATT where MER_CODE =?", P.mp
						.getMerchant().getMerCode());
		if (list.size() == 0) {
			String sql = "insert into T_CIPMP_PROCARD_ATT values(?,?,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);";
			String proc_id = "123";
			DaoUtil.execute(ds, sql, P.mp.getMerchant().getMerCode(), proc_id);
		}
		P.setAttribute("list", list);
	}

	@Execute("会员卡设置")
	@Pagelet.Purview("execute")
	public void cardSetUp(@RequestParam("pojo") HashMap<String, String> pojo)
			throws SQLException {
		String open_wechat_manage = pojo.get("open_wechat_manage") == null ? ""
				: pojo.get("open_wechat_manage").toString().trim();// 卡号或姓名
		String docard_channel = pojo.get("docard_channel") == null ? "" : pojo
				.get("docard_channel").toString().trim();// 卡号或姓名
		String consume_channel = pojo.get("consume_channel") == null ? ""
				: pojo.get("consume_channel").toString().trim();// 卡号或姓名
		String recharge_channel = pojo.get("recharge_channel") == null ? ""
				: pojo.get("recharge_channel").toString().trim();// 卡号或姓名
		String wechat_card_no = pojo.get("wechat_card_no") == null ? "" : pojo
				.get("wechat_card_no").toString().trim();// 卡号或姓名
		String allow_tream_buy_card = pojo.get("allow_tream_buy_card") == null ? ""
				: pojo.get("allow_tream_buy_card").toString().trim();// 卡号或姓名
		String allow_activation_card = pojo.get("allow_activation_card") == null ? ""
				: pojo.get("allow_activation_card").toString().trim();// 卡号或姓名
		String contain_integral = pojo.get("contain_integral") == null ? ""
				: pojo.get("contain_integral").toString().trim();// 卡号或姓名
		String recharge_integral_rule = pojo.get("recharge_integral_rule") == null ? ""
				: pojo.get("recharge_integral_rule").toString().trim();// 卡号或姓名
		String consume_integral_rule = pojo.get("consume_integral_rule") == null ? ""
				: pojo.get("consume_integral_rule").toString().trim();// 卡号或姓名
		String proc_id = pojo.get("proc_id") == null ? "" : pojo.get("proc_id")
				.toString().trim();//卡产品id
		String sql = "update T_CIPMP_PROCARD_ATT set OPEN_WECHAT_MANAGE=?,DOCARD_CHANNEL=?,CONSUME_CHANNEL=?,RECHARGE_CHANNEL=?,WECHAT_CARD_NO=?,ALLOW_TREAM_BUY_CARD=?,ALLOW_ACTIVATION_CARD=?,CONTAIN_INTEGRAL=?,RECHARGE_INTEGRAL_RULE=?,CONSUME_INTEGRAL_RULE=? where MER_CODE=? and PROC_ID=?";
		DaoUtil.execute(ds, sql, open_wechat_manage, docard_channel,
				consume_channel, recharge_channel, wechat_card_no,
				allow_tream_buy_card, allow_activation_card, contain_integral,
				recharge_integral_rule, consume_integral_rule, P.mp
						.getMerchant().getMerCode(), proc_id);
		execute();

	}

	@Execute("会员资料设置")
	@Pagelet.Purview("execute")
	public void dataSetUp(@RequestParam("a") String a,
			@RequestParam("b") String b, @RequestParam("c") String c)
			throws SQLException {
	}

}
