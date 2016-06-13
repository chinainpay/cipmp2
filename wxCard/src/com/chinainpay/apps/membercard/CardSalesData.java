package com.chinainpay.apps.membercard;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import net.javabone.common.util.DaoUtil;

import com.chinainpay.mp.app.pagelet.Pagelet;
import com.chinainpay.mp.app.pagelet.axis.P;

public class CardSalesData implements Pagelet {
	private DataSource ds = P.ds("base");
	/**
	 * 会员卡售卖数据
	 */
	private static final long serialVersionUID = 2701038965468964669L;

	@Execute("访问")
	@Pagelet.Purview()
	public void execute() throws SQLException {
		List<Map<String, Object>> list = DaoUtil.list(ds, 0,
				"select * from T_CIPMP_SALES_RECORD where MER_CODE =?", P.mp
						.getMerchant().getMerCode());
		int sum = (list.size() - 1) / 10 + 1;
		P.setAttribute("sum", sum);
		P.setAttribute("page", 1);
		P.setAttribute("list",
				DaoUtil.list(ds, 0,
						"select * from T_CIPMP_SALES_RECORD where MER_CODE =? LIMIT 10",
						P.mp.getMerchant().getMerCode()));
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
			list = DaoUtil.list(ds, 0,
					"select * from T_CIPMP_SALES_RECORD where MER_CODE =? ",
					P.mp.getMerchant().getMerCode());
			sum = (list.size() - 1) / 10 + 1;
			if (Integer.parseInt(page) < 1) {
				page = "1";
			} else if (Integer.parseInt(page) > sum) {
				page = sum + "";
			}
			sql = "select * from T_CIPMP_SALES_RECORD where MER_CODE =? limit "
					+ (Integer.parseInt(page) - 1) * 10 + ",10";
		} else {
			list = DaoUtil.list(ds, 0,
					"select * from T_CIPMP_SALES_RECORD where MER_CODE =? AND SALES_NO like '%"
							+ ss + "%'", P.mp.getMerchant().getMerCode());
			sum = (list.size() - 1) / 10 + 1;
			if (Integer.parseInt(page) < 1) {
				page = "1";
			} else if (Integer.parseInt(page) > sum) {
				page = sum + "";
			}
			sql = "select * from T_CIPMP_SALES_RECORD where MER_CODE =? AND SALES_NO  like '%"
					+ ss
					+ "%' limit "
					+ (Integer.parseInt(page) - 1)
					* 10
					+ ",10";
		}

		P.setAttribute("list", DaoUtil.list(ds, 0, sql, P.mp.getMerchant().getMerCode()));
		P.setAttribute("page", page);
		P.setAttribute("ssText", ss);
		P.setAttribute("sum", sum);
	}

}
