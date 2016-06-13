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
import com.chinainpay.mp.app.tags.base.Page;

public class MemberOrderManage extends DaoViewBase {
	/**
	 * 会员订单管理
	 */
	private static final long serialVersionUID = -6853425754293707765L;
	private DataSource ds = P.ds("base");

	@Execute("访问")
	@Pagelet.Purview()
	public void execute(@RequestParam("pojo") HashMap<String, String> pojo)
			throws SQLException {
		String table = pojo.get("table") == null ? "T_CIPMP_MYORDER" : pojo.get("table").toString().trim();;
		String field = pojo.get("sortField") == null ? "ordertime+" : pojo.get("sortField").toString().trim();;

		Page page = query(pojo, "base", field, 10, "SELECT * From "+table);
		P.setAttribute("page", page);
	}

	@Execute("访问")
	@Pagelet.Purview()
	public void blablabla(@RequestParam("pojo") HashMap<String, String> pojo)
			throws SQLException {
		String table = pojo.get("table") == null ? "T_CIPMP_MYORDER" : pojo.get("table").toString().trim();;
		String field = pojo.get("sortField") == null ? "ordertime+" : pojo.get("sortField").toString().trim();;

		Page page = query(pojo, "base", field, 10, "SELECT * From "+table);
		P.setAttribute("page", page);
	}
	@Execute("访问")
	@Pagelet.Purview()
	public void myInsert(@RequestParam("pojo") HashMap<String, String> pojo)
			throws SQLException {
		String table = pojo.get("table") == null ? "T_CIPMP_MYORDER" : pojo.get("table").toString().trim();;
		String field = pojo.get("sortField") == null ? "actualnumber-" : pojo.get("sortField").toString().trim();;

		Page page = query(pojo, "base", field, 10, "SELECT * From "+table+" where orderstate=?", 3);
		P.setAttribute("page", page);
	}

	@Execute("访问")
	@Pagelet.Purview()
	public void myExport(@RequestParam("pojo") HashMap<String, String> pojo)
			throws SQLException {
		String table = pojo.get("table") == null ? "T_CIPMP_MYORDER" : pojo.get("table").toString().trim();;
		String field = pojo.get("sortField") == null ? "actualmoney-" : pojo.get("sortField").toString().trim();;

		Page page = query(pojo, "base", field, 10, "SELECT * From "+table);
		P.setAttribute("page", page);
	}

}
