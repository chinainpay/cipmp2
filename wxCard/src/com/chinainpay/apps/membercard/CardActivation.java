package com.chinainpay.apps.membercard;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import net.javabone.common.util.DaoUtil;
import net.javabone.common.util.TransactionDataSource;
import net.javabone.common.web.JsonUtil;

import com.chinainpay.apps.pool.Trade;
import com.chinainpay.apps.pool.impl.TradeImpl;
import com.chinainpay.apps.util.DriveUtil;
import com.chinainpay.mp.app.pagelet.Pagelet;
import com.chinainpay.mp.app.pagelet.axis.P;

public class CardActivation implements Pagelet {
	private DataSource ds = P.ds("base");
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyyMMddHHmmss");
	/**
	 * 售卖数据激活
	 */
	private static final long serialVersionUID = 7683660388314581927L;

	@Execute("访问")
	@Pagelet.Purview()
	public void execute() throws SQLException {
		List<Map<String, Object>> list = DaoUtil.list(ds, 0,
						"select * from T_CIPMP_SALES_RECORD where MER_CODE=? and STATE='1' ",
						P.mp.getMerchant().getMerCode());
		int sum = (list.size() - 1) / 10 + 1;
		P.setAttribute("list", DaoUtil.list(ds,	0,
						"select * from T_CIPMP_SALES_RECORD where MER_CODE=? and STATE='1' limit 10",
						P.mp.getMerchant().getMerCode()));

		P.setAttribute("page", 1);
		P.setAttribute("sum", sum);
	}

	@Execute("分页")
	@Pagelet.Purview("execute")
	public void pageInto(@RequestParam("pojo") HashMap<String, String> pojo)
			throws SQLException {

		String sqlList = "select * from T_CIPMP_SALES_RECORD where MER_CODE=? and STATE='1' limit 10";
		List<Map<String, Object>> list = DaoUtil.list(ds, 0, sqlList, P.mp
				.getMerchant().getMerCode());
		String page = pojo.get("page") == null ? "1" : pojo.get("page")
				.toString().trim();
		int sum = (list.size() - 1) / 10 + 1;
		if (Integer.parseInt(page) < 1) {
			page = "1";
		} else if (Integer.parseInt(page) > sum) {
			page = sum + "";
		}
		System.out.println(sum);
		System.out.println(page);
		String sql = "select * from T_CIPMP_SALES_RECORD where MER_CODE=? and STATE='1' limit "
				+ (Integer.parseInt(page) - 1) * 10 + ",10";
		P.setAttribute("list",
				DaoUtil.list(ds, 0, sql, P.mp.getMerchant().getMerCode()));
		P.setAttribute("page", page);
		P.setAttribute("sum", sum);
	}

	@Execute("激活")
	@Pagelet.Purview("execute")
	public void activation(@RequestParam("pojo") HashMap<String, String> pojo)
			throws Exception {
		TransactionDataSource tds = new TransactionDataSource(ds);
		String sales_no = pojo.get("sales_no") == null ? "" : pojo
				.get("sales_no").toString().trim();// 销售单号
		String[] sales_nos = sales_no.split(",");
		for (int i = 0; i < sales_nos.length; i++) {
			List<Map<String, Object>> list = DaoUtil.list(tds, 0,
					"select * from T_CIPMP_SALES_RECORD where SALES_NO=?",
					sales_nos[i]);
			String sql = "update T_CIPMP_SALES_RECORD set STATE='2' where SALES_NO=?";
			String store_num = "";// 商户号
			String dateStr = dateFormat.format(new Date());
			String clientDate = dateStr.substring(0, 8);
			String clientTime = dateStr.substring(8);
			String oper_user = P.mp.getAdmin().getCode()== null ? "" : P.mp.getAdmin().getCode();
			int mag = DaoUtil.execute(tds, sql, sales_nos[i]);
			String mtoreNo = null;
			String terminal = null;
			try {
				mtoreNo = DriveUtil.getDrive().get("mtoreNo").toString();
				terminal = DriveUtil.getDrive().get("terminal").toString();
				P.log.d(mtoreNo+"------"+terminal);
			} catch (Exception e1) {
				tds.transactionRollback();
				tds.close();
				return ;
			}
			if (mag == 1) {
				Trade t = new TradeImpl();
				Date d = new Date();
				String orderno = d.getTime() + store_num;
				String money= (Integer.parseInt(list.get(0)
						.get("SALES_MONEY").toString())*Integer.parseInt(list.get(0)
								.get("CARD_COUNT").toString()))+"";
				try {
					t.Recharge(orderno, list.get(0).get("NO_START").toString(),
							list.get(0).get("NO_END").toString(), list.get(0)
									.get("SALES_MONEY").toString(), money, clientDate,
							clientTime, mtoreNo,
							store_num, oper_user, terminal);
				} catch (Exception e) {
					tds.transactionRollback();
					tds.close();
					e.printStackTrace();
					P.log.d("激活异常");
					P.setAttribute("msg", "激活异常");
					execute();
					return;
				}
			}
		}
		P.log.d("激活提交");
		tds.transactionCommit();
		tds.close();
		execute();
	}

	@Execute("未激活卡片详情")
	@Pagelet.Purview("execute")
	public void sellCardData(@RequestParam("pojo") HashMap<String, String> pojo)
			throws SQLException {
		String sales_no = pojo.get("sales_no") == null ? "" : pojo
				.get("sales_no").toString().trim();// 销售单号
		String ss = pojo.get("ss") == null ? "" : pojo.get("ss").toString()
				.trim();// 搜索卡号
		String merchantNo = P.mp.getMerchant().getMerCode();
		String querySql = "SELECT * FROM T_CIPMP_SALES_RECORD WHERE SALES_NO=? AND MER_CODE=? limit 10";// 在销售表里查询出起始卡号和结束卡号
		Map<String, Object> mp = DaoUtil
				.row(ds, querySql, sales_no, merchantNo);
		List<Map<String, Object>> list = DaoUtil.list(ds, 0, querySql,
				sales_no, merchantNo);
		String page = pojo.get("page") == null ? "1" : pojo.get("page")
				.toString().trim();
		int sum = (list.size() - 1) / 10 + 1;
		if (Integer.parseInt(page) < 1) {
			page = "1"; 
		} else if (Integer.parseInt(page) > sum) {
			page = sum + "";
		}
		if (Integer.parseInt(mp.get("GOUKA_WAY").toString()) == 1) {// 团体售卡
			String startNo = mp.get("NO_START").toString();// 起始卡号
			String endNo = mp.get("NO_END").toString();// 结束卡号
			String detailSql = "";
			if (!ss.equals("")) {
				startNo = mp.get("NO_START").toString();// 起始卡号
				endNo = mp.get("NO_END").toString();// 结束卡号
				detailSql = "SELECT * FROM (SELECT * FROM T_CIPMP_CARD WHERE CARD_NUM BETWEEN ? AND ? AND MER_CODE=?) as A where A.CARD_NUM like '%"
						+ ss
						+ "%' limit "
						+ (Integer.parseInt(page) - 1)
						* 10
						+ ",10";
				list = DaoUtil.list(ds, 0, detailSql, startNo, endNo, merchantNo);
			} else {
				startNo = mp.get("NO_START").toString();// 起始卡号
				endNo = mp.get("NO_END").toString();// 结束卡号
				detailSql = "SELECT * FROM T_CIPMP_CARD WHERE CARD_NUM BETWEEN ? AND ? AND MER_CODE=? limit "
						+ (Integer.parseInt(page) - 1) * 10 + ",10";
				list = DaoUtil.list(ds, 0, detailSql, startNo, endNo, merchantNo);
			}
		}
		P.setAttribute("ssText", ss);
		P.setAttribute("user", mp);
		P.setAttribute("list", list);
		P.setAttribute("page", page);
		P.setAttribute("sum", sum);
		P.setAttribute("sales_no", sales_no);
	}

}
