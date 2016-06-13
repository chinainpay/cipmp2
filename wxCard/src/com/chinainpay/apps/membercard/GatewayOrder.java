package com.chinainpay.apps.membercard;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import net.javabone.common.util.DaoUtil;

import com.chinainpay.mp.app.pagelet.Pagelet;
import com.chinainpay.mp.app.pagelet.axis.P;

public class GatewayOrder implements Pagelet {
	private static final long serialVersionUID = -6853425754293707765L;
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private DataSource ds = P.ds("base");
	@Execute("访问")
	@Pagelet.Purview()
	public void execute() throws RemoteException, SQLException {
		String sql = "SELECT * from T_CIPMP_GATEWAY where MER_CODE =? limit 10";
		String sql2 = "SELECT * from T_CIPMP_GATEWAY where MER_CODE =?";
		List<Map<String, Object>> list = DaoUtil.list(ds, 0, sql2, P.mp.getMerchant().getMerCode());
		P.setAttribute("list",DaoUtil.list(ds, 0, sql, P.mp.getMerchant().getMerCode()));
		int sum = (list.size() - 1) / 10 + 1;
		P.setAttribute("page", 1);
		P.setAttribute("sum", sum);
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
							"SELECT * from T_CIPMP_GATEWAY where MER_CODE =?",
							P.mp.getMerchant().getMerCode());
				sum = (list.size() - 1) / 10 + 1;
			if (Integer.parseInt(page) < 1) {
				page = "1";
			} else if (Integer.parseInt(page) > sum) {
				page = sum + "";
			}
			sql = "SELECT * from T_CIPMP_GATEWAY where MER_CODE =? limit "
					+ (Integer.parseInt(page) - 1) * 10 + ",10";
		} else {
			list = DaoUtil
					.list(ds,
							0,
							"SELECT * from T_CIPMP_GATEWAY where MER_CODE =? AND ORDER_ID like '%"+ss+"%'",
							P.mp.getMerchant().getMerCode());
			sum = (list.size() - 1) / 10 + 1;
			if (Integer.parseInt(page) < 1) {
				page = "1";
			} else if (Integer.parseInt(page) > sum) {
				page = sum + "";
			}
			sql = "SELECT * from T_CIPMP_GATEWAY where MER_CODE =? AND ORDER_ID like '%"
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
	@Execute("下载流水")
	@Pagelet.Purview("")
	public File download(@RequestParam("file") File file2) throws IOException{
		String makeCard_NO = P.mp.getMerchant().getMerCode();
        File file = File.createTempFile("_downLoadCardDataFile", ".txt");
		try {
			createNewFile(makeCard_NO, file);
			return file;
		} catch (Exception e) {
			e.printStackTrace();
			return file;
		}
	}
	/**
	 * 数据写入
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	private boolean createNewFile(String makeCard_NO, File file)
			throws Exception {
		FileOutputStream fos = new FileOutputStream(file);
		StringBuffer sb = new StringBuffer();
		String sql = "SELECT ORDER_ID AS 订单编号,MER_CODE AS 商户号,MER_NAME AS 商户名称,CARD_NO AS 支付卡号 ,ORDER_CONTENT AS 订单内容 ,TOTAL_FEE AS 金额,TIME_START AS 支付时间,TRADE_STATE AS 交易状态 FROM T_CIPMP_GATEWAY WHERE MER_CODE=? ";
		List<Map<String, Object>> list = DaoUtil.list(ds, 0, sql, makeCard_NO);
		if (list.size() > 0) {
			Map<String, Object> map = list.get(0);
			Set<String> set = map.keySet();
			for (String key : set) {
				sb.append(key + "\r\t");
			}
			sb.append("\r\n");
		}
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = list.get(i);
			Set<String> set = map.keySet();
			for (String key : set) {
				sb.append(map.get(key) + "\r\t");
			}
			sb.append("\r\n");
		}
		fos.write(sb.toString().getBytes());
		fos.close();
		if (file.exists()) {
			return true;
		} else {
			return false;
		}
	}

}
