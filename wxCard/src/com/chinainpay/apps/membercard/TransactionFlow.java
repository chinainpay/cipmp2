package com.chinainpay.apps.membercard;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;
import javax.xml.parsers.ParserConfigurationException;

import net.javabone.common.util.DaoUtil;
import net.javabone.common.web.JsonUtil;

import org.xml.sax.SAXException;

import com.chinainpay.apps.pool.Flow;
import com.chinainpay.apps.pool.Trade;
import com.chinainpay.apps.pool.impl.FlowImpl;
import com.chinainpay.apps.pool.impl.TradeImpl;
import com.chinainpay.apps.util.DriveUtil;
import com.chinainpay.mp.app.pagelet.Pagelet;
import com.chinainpay.mp.app.pagelet.axis.P;
import com.chinainpay.mp.mq.client.ServiceNotFoundException;
import com.chinainpay.mp.mq.client.ServiceTimeoutException;
public class TransactionFlow implements Pagelet {
	private DataSource ds = P.ds("base");
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyyMMddHHmmss");
	/**
	 * 交易流水查询
	 */
	private static final long serialVersionUID = -6454657889073459723L;

	@Execute("访问")
	@Pagelet.Purview()
	public void execute() throws SQLException, ServiceNotFoundException,
			ServiceTimeoutException, ParserConfigurationException,
			SAXException, IOException {
		// 查询门店
		P.setAttribute(
				"MAKECARD",
				P.mp.getService("dd8a5b78-d8e9-4576-bc66-2e77c5d550d7", 60000,
						"getStores", P.mp.getMerchant().getMerCode())
						.getValue());
//		String dateStr = dateFormat.format(new Date());
//		String clientDate = dateStr.substring(0, 8);
//		String clientTime = dateStr.substring(8);
//		String mtoreNo = DriveUtil.getDrive().get("mtoreNo").toString();
//		String terminal = DriveUtil.getDrive().get("terminal").toString();
////		P.log.d(AppServices.cardPwdReset("2326113010000010025", "123456", clientDate, clientTime, mtoreNo, "", "", terminal));
////		P.log.d(AppServices.cardPwdChange("2326113010000010025", "123456", "123456", clientDate, clientTime, mtoreNo, "", "", terminal));
////		Trade trade = new TradeImpl();
////		String msg = null;
////		try {
////		 msg = 	trade.Consumption("1821071412", "2326113010000010025", "2326113010000010025", "1", "1", clientDate, clientTime, mtoreNo, "", terminal, "", "Y", "123456");
////		} catch (Exception e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////		P.log.d(msg);
////		P.log.d(P.getContextParameter("aa"));
		
	}

	@Execute("交易流水查询")
	@Pagelet.Purview("execute")
	public void query(@RequestParam("pojo") HashMap<String, String> pojo)
			throws Exception {
		Flow f = new FlowImpl();
		List<Map<String, Object>> data = null;
		String merCode = P.mp.getMerchant().getMerCode();
		String card_no = pojo.get("card_no") == null ? "" : pojo.get("card_no")
				.toString().trim();// 手机号 或 卡号
		String dateTime = pojo.get("dateTime") == null ? "" : pojo
				.get("dateTime").toString().trim();// 时间8位
		String oper_user = P.mp.getAdmin().getCode() == null ? "" : P.mp
				.getAdmin().getCode();
		String store_code = pojo.get("store_code") == null ? "" : pojo
				.get("store_code").toString().trim();// 门店好
		String page = pojo.get("page") == null ? "1" : pojo.get("page")
				.toString().trim();// 页码
		if (Integer.parseInt(page) < 1) {
			page = "1";
		}
		String type = "H";
		SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String dateTimeForm = "20140101";
		String dateTimeTo = formatter.format(new Date());
		String mtoreNo = null;
		String terminal = null;
		try {
			mtoreNo = DriveUtil.getDrive().get("mtoreNo").toString();
			terminal = DriveUtil.getDrive().get("terminal").toString();
		} catch (Exception e1) {
			return ;
		}
		if (dateTime.equals("")) {
			dateTimeForm = "20140101";
			dateTimeTo = formatter.format(new Date());
			type = "H";
		} else {
			dateTimeForm = formatter.format(sf2.parse(dateTime));
			dateTimeTo = formatter.format(sf2.parse(dateTime)); 
			type = "H";  
			if (formatter.format(sf2.parse(dateTime)).equals(
					formatter.format(new Date()))) {
				dateTimeForm = formatter.format(sf2.parse(dateTime));
				dateTimeTo = formatter.format(sf2.parse(dateTime));
				type = "T";
			}
		}

		// 长度为11查询手机号
		try {
			if (card_no.length() == 11) {
				List<Map<String, Object>> list = DaoUtil
						.list(ds,
								0,
								"select * from T_CIPMP_CARD where MER_CODE =? and MEMB_PHONE= ? ",
								merCode, card_no);
				if (list.size() > 1) {
					P.setAttribute("mag", "您的手机绑定多张卡号，请使用卡号查询");
					P.setAttribute("page", "");
					P.setAttribute("dateTime", "");
					P.setAttribute("card_no", "");
					P.setAttribute("tradeDetailsList", "");
					return;
				} else {
					data = f.tradeDetails(list.get(0).get("CARD_NUM")
							.toString(), "N", "", "Y", page, "10", mtoreNo,
							store_code, oper_user, type, dateTimeForm,
							dateTimeTo, terminal);
				}
			} else {
				data = f.tradeDetails(card_no, "N", "", "Y", page, "10",
						mtoreNo, store_code, oper_user, type, dateTimeForm,
						dateTimeTo, terminal);
			}
			if (data == null) {
				page = (Integer.parseInt(page) - 1) + "";
				data = f.tradeDetails(card_no, "N", "", "Y", page, "10",
						mtoreNo, store_code, oper_user, type, dateTimeForm,
						dateTimeTo, terminal);
			}
		} catch (Exception e) {
			e.printStackTrace();
			P.log.d("交易流水查询异常");
			P.setAttribute("mag", "交易流水查询异常");
			return;
		}
		P.setAttribute("page", page);
		P.setAttribute("dateTime", dateTime);
		P.setAttribute("card_no", card_no);
		P.setAttribute("tradeDetailsList", data);
	}
}
