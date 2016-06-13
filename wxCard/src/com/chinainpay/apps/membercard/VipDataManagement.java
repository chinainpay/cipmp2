package com.chinainpay.apps.membercard;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import net.javabone.common.util.DaoUtil;

import com.chinainpay.mp.app.pagelet.Pagelet;
import com.chinainpay.mp.app.pagelet.axis.P;
import com.chinainpay.mp.app.tags.base.DaoViewBase;

public class VipDataManagement extends DaoViewBase {
	/**
	 * 会员数据管理
	 */
	private static final long serialVersionUID = -6853425754293707765L;
	private DataSource ds = P.ds("base");

	@Execute("访问")
	@Pagelet.Purview()
	public void execute(@RequestParam("page") String page2,
			@RequestParam("TRADE_DATE_S") String TRADE_DATE_S,
			@RequestParam("TRADE_DATE_E") String TRADE_DATE_E,
			@RequestParam("pojo") HashMap<String, String> pojo)
			throws RemoteException, SQLException {
		TRADE_DATE_S = TRADE_DATE_S == null ? "" : TRADE_DATE_S.toString();
		TRADE_DATE_E = TRADE_DATE_E == null ? "" : TRADE_DATE_E.toString();
		StringBuffer sb = new StringBuffer(
				"select * from T_CIPMP_CARD,T_CIPMP_MEMBER where T_CIPMP_MEMBER.MEMB_PHONE = T_CIPMP_CARD.MEMB_PHONE and  T_CIPMP_CARD.CARD_NUM= T_CIPMP_MEMBER.WX_CARD_NUM and T_CIPMP_MEMBER.MER_CODE=?");
		List<Object> obj = new ArrayList<Object>();
		obj.add(P.mp.getMerchant().getMerCode());
		int page = page2 == null || page2.equals("") ? 0 : Integer
				.parseInt(page2);
		String phoneNo = pojo.get("phoneNo") == null ? "" : pojo.get("phoneNo")
				.toString();
		String cardNo = pojo.get("cardNo") == null ? "" : pojo.get("cardNo")
				.toString();
		int size = 10;

		if (!TRADE_DATE_S.equals("")) {
			sb.append(" and T_CIPMP_MEMBER.REGISTER_TIME >= '" + TRADE_DATE_S
					+ "'");
		}
		if (!TRADE_DATE_E.equals("")) {
			sb.append(" and T_CIPMP_MEMBER.REGISTER_TIME <= '" + TRADE_DATE_E
					+ " 23:59:59" + "'");
		}
		if (!cardNo.equals("")) {
			sb.append(" and T_CIPMP_MEMBER.WX_CARD_NUM =?");
			obj.add(cardNo);
		}
		if (!phoneNo.equals("")) {
			sb.append(" and T_CIPMP_MEMBER.MEMB_PHONE =?");
			obj.add(phoneNo);
		}
		
		sb.append("order by  T_CIPMP_MEMBER.REGISTER_TIME DESC");
		P.log.d(sb.toString());
		P.log.d(obj.toString());
		List<Map<String, Object>> list = DaoUtil.list(P.ds("base"), 0,
				sb.toString(), obj.toArray());
		int pageCount = (int) Math.ceil(list.size() / (double) size);
		if (page < 1) {
			page = 1;
		} else if (page > pageCount) {
			page = pageCount;
		}
		List<Map<String, Object>> saleList = DaoUtil.list(P.ds("base"), size,
				page - 1, sb.toString(), obj.toArray());
		
		P.setAttribute("list", saleList);
		P.setAttribute("TRADE_DATE_S", TRADE_DATE_S);
		P.setAttribute("TRADE_DATE_E", TRADE_DATE_E);
		P.setAttribute("page", page);
		P.setAttribute("pageCount", pageCount);
		P.setAttribute("cardNo", cardNo);
		P.setAttribute("phoneNo", phoneNo);
		P.setAttribute("vipCount", list.size());
	}

	@Execute("编辑前置查询")
	@Pagelet.Purview("execute")
	public String update(@RequestParam("memb_phone") String memb_phone)
			throws SQLException {
		String sql = "SELECT * FROM T_CIPMP_MEMBER WHERE MER_CODE= ? and MEMB_PHONE=?";
		P.setAttribute("userlist", DaoUtil.list(ds, 0, sql, P.mp.getMerchant()
				.getMerCode(), memb_phone));
		return "newMember";
	}

	@Execute("编辑修改")
	@Pagelet.Purview("execute")
	public void updateInfo(@RequestParam("pojo") HashMap<String, String> pojo)
			throws SQLException {
		String user_name = pojo.get("user_name") == null ? "" : pojo
				.get("user_name").toString().trim();
		String user_phone = pojo.get("user_phone") == null ? "" : pojo
				.get("user_phone").toString().trim();
		String user_sex = pojo.get("user_sex") == null ? "" : pojo
				.get("user_sex").toString().trim();
		DaoUtil.execute(
				ds,
				"UPDATE T_CIPMP_MEMBER SET MEMB_PHONE = ?, MEMB_NAME=?,MEMB_SEX=? WHERE MEMB_PHONE=? and MER_CODE= ? ",
				user_phone, user_name, user_sex, user_phone, P.mp.getMerchant()
						.getMerCode());
	}

	@Execute("手机号唯一")
	@Pagelet.Purview("execute")
	public void phoneUnique(@RequestParam("pojo") HashMap<String, String> pojo)
			throws SQLException {
		String user_phone = pojo.get("user_phone") == null ? "" : pojo
				.get("user_phone").toString().trim();
		int listSize = DaoUtil
				.execute(
						ds,
						"SELECT * FROM T_CIPMP_MEMBER WHERE MEMB_PHONE=? and MER_CODE= ? ",
						user_phone, P.mp.getMerchant().getMerCode());
		P.setAttribute("listSize", listSize);
	}

	@Execute("分页")
	@Pagelet.Purview("execute")
	public void pageInto(@RequestParam("pojo") HashMap<String, String> pojo)
			throws SQLException {
		int sum = 1;
		String page = pojo.get("page") == null ? "1" : pojo.get("page")
				.toString().trim();

		String ss = pojo.get("ss") == null ? "" : pojo.get("ss").toString()
				.trim();
		List list = null;
		String sql = "";
		if (ss.equals(null) || ss.equals("")) {
			list = DaoUtil
					.list(ds,
							0,
							"SELECT T_CIPMP_CARD.CARD_NUM,T_CIPMP_MEMBER.MEMB_NAME,T_CIPMP_MEMBER.MEMB_PHONE,T_CIPMP_MEMBER.MEMB_SCORE,T_CIPMP_CARD.CARD_TYPE FROM T_CIPMP_CARD,T_CIPMP_MEMBER WHERE T_CIPMP_CARD.MEMB_PHONE=T_CIPMP_MEMBER.MEMB_PHONE AND T_CIPMP_MEMBER.MER_CODE=? ",
							P.mp.getMerchant().getMerCode());
			sum = (list.size() - 1) / 10 + 1;
			if (Integer.parseInt(page) < 1) {
				page = "1";
			} else if (Integer.parseInt(page) > sum) {
				page = sum + "";
			}
			sql = "SELECT T_CIPMP_CARD.CARD_NUM,T_CIPMP_MEMBER.MEMB_NAME,T_CIPMP_MEMBER.MEMB_PHONE,T_CIPMP_MEMBER.MEMB_SCORE,T_CIPMP_CARD.CARD_TYPE FROM T_CIPMP_CARD,T_CIPMP_MEMBER WHERE T_CIPMP_CARD.MEMB_PHONE=T_CIPMP_MEMBER.MEMB_PHONE AND T_CIPMP_MEMBER.MER_CODE=? limit "
					+ (Integer.parseInt(page) - 1) * 10 + ",10";
		} else {
			list = DaoUtil
					.list(ds,
							0,
							"SELECT T_CIPMP_CARD.CARD_NUM,T_CIPMP_MEMBER.MEMB_NAME,T_CIPMP_MEMBER.MEMB_PHONE,T_CIPMP_MEMBER.MEMB_SCORE,T_CIPMP_CARD.CARD_TYPE FROM T_CIPMP_CARD,T_CIPMP_MEMBER WHERE T_CIPMP_CARD.MEMB_PHONE=T_CIPMP_MEMBER.MEMB_PHONE AND T_CIPMP_MEMBER.MER_CODE=? AND T_CIPMP_CARD.CARD_NUM like '%"
									+ ss + "%'", P.mp.getMerchant()
									.getMerCode());
			sum = (list.size() - 1) / 10 + 1;
			if (Integer.parseInt(page) < 1) {
				page = "1";
			} else if (Integer.parseInt(page) > sum) {
				page = sum + "";
			}
			sql = "SELECT T_CIPMP_CARD.CARD_NUM,T_CIPMP_MEMBER.MEMB_NAME,T_CIPMP_MEMBER.MEMB_PHONE,T_CIPMP_MEMBER.MEMB_SCORE,T_CIPMP_CARD.CARD_TYPE FROM T_CIPMP_CARD,T_CIPMP_MEMBER WHERE T_CIPMP_CARD.MEMB_PHONE=T_CIPMP_MEMBER.MEMB_PHONE AND T_CIPMP_MEMBER.MER_CODE=?  AND T_CIPMP_CARD.CARD_NUM like '%"
					+ ss
					+ "%' limit "
					+ (Integer.parseInt(page) - 1)
					* 10
					+ ",10";
		}

		P.setAttribute("list",
				DaoUtil.list(ds, 0, sql, P.mp.getMerchant().getMerCode()));
		P.setAttribute("page", page);
		P.setAttribute("ssText", ss);
		P.setAttribute("sum", sum);
	}

	@Execute("搜索")
	@Pagelet.Purview("execute")
	public void search() throws SQLException {

	}
}
