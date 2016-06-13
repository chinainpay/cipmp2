package com.chinainpay.apps.membercard;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import net.javabone.common.crypto.CheckBit;
import net.javabone.common.util.DaoUtil;
import net.javabone.common.util.RegexpUtils;
import net.javabone.common.util.TransactionDataSource;
import net.javabone.common.web.JsonUtil;

import com.chinainpay.apps.membercard.entity.StoreCardList;
import com.chinainpay.apps.util.CurrentTime;
import com.chinainpay.mp.app.pagelet.Pagelet;
import com.chinainpay.mp.app.pagelet.axis.P;

public class physicalcardManage implements Pagelet {
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
		// 调用获取制卡数据 判断是否申请制卡成功
		String merCode = P.mp.getMerchant().getMerCode();
		// String merCode = "01";
		// 查询制卡等级
		List<Map<String, Object>> LEVELlist = DaoUtil.list(ds, 0,
				"select * from T_CIPMP_CARD where MER_CODE =? group by VIRTUAL_CARD", merCode);
		// 制卡信息
		String sqlList = "select * from T_CIPMP_MAKECARD where MER_CODE =? ";
		List list = DaoUtil.list(ds, 0, sqlList, merCode);
		P.setAttribute("MAKECARD", DaoUtil.list(ds, 0, "select * from T_CIPMP_MAKECARD where MER_CODE =? limit 10", merCode));
		// 查询门店
		P.setAttribute("STORE", DaoUtil.list(ds, 0,
				"select * from T_CIPMP_STORE where MER_CODE =?", merCode));
		Map<String, Object> MERcount = new HashMap<String, Object>();
		for (int i = 0; i < LEVELlist.size(); i++) {
			String type = LEVELlist.get(i).get("VIRTUAL_CARD").toString();
			String cardSql = "select * from T_CIPMP_CARD where MER_CODE=? and VIRTUAL_CARD=? and CARD_STATE=? and MEMB_STORE is NULL";
			long count = DaoUtil.count(ds, cardSql, merCode, type, "1");
			type = type.equals("1")  ? "实体卡" : "虚拟卡";
			MERcount.put(type, count);
		}
		int sum = (list.size() - 1) / 10 + 1;
		P.setAttribute("page", 1);
		P.setAttribute("sum", sum);
		P.setAttribute("count", MERcount);
	}

	@Execute("分页")
	@Pagelet.Purview("execute")
	public void pageInto(@RequestParam("pojo") HashMap<String, String> pojo)
			throws SQLException {
		execute();
		String merCode = P.mp.getMerchant().getMerCode();
		String sqlList = "select * from T_CIPMP_MAKECARD where MER_CODE =? ";
		List<Map<String, Object>> list = DaoUtil.list(ds, 0, sqlList, merCode);
		String page = pojo.get("page") == null ? "1" : pojo.get("page")
				.toString().trim();
		int sum = (list.size() - 1) / 10 + 1;
		if (Integer.parseInt(page) < 1) {
			page = "1";
		} else if (Integer.parseInt(page) > sum) {
			page = sum + "";
		}
		String sql = "select * from T_CIPMP_MAKECARD where MER_CODE =? limit "
				+ (Integer.parseInt(page) - 1) * 10 + ",10";
		P.setAttribute("MAKECARD", DaoUtil.list(ds, 0, sql, merCode));
		P.setAttribute("page", page);
		P.setAttribute("sum", sum);
	}

	/**
	 * 卡等级方法
	 * 
	 * @throws SQLException
	 */
	@Execute("访问")
	@Pagelet.Purview()
	public void makeCard() throws SQLException {
		String merCode = P.mp.getMerchant().getMerCode();
		// String merCode = "01";
		// 查询制卡等级
		List<Map<String, Object>> LEVELlist = DaoUtil.list(ds, 0,
				"select * from T_CIPMP_CARD_TYPE where MER_CODE =?", merCode);
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
																				// 当前时间戳 + 一位校验码
		String proc_no = pojo.get("proc_no") == null ? "" : pojo.get("proc_no")
				.toString().trim();
		if (proc_no.equals("")) {
			msg = "卡名称不能为空";
			rMap.put("errorMsg", msg);
			return new StringBuffer(JsonUtil.encoder(rMap));
		}
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
				|| Integer.parseInt(card_amt) % 200 != 0) {
			msg = "请输入200的整数倍";
			rMap.put("errorMsg", msg);
			return new StringBuffer(JsonUtil.encoder(rMap));
		} else if (Integer.parseInt(card_amt) > 1000000) {
			msg = "制卡数量应小于100万张";
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
		String sql = "SELECT CARD_TYPE AS 卡类型,CARD_NUM AS 卡号,TWO_MAGNETIC AS 二磁,CARD_PWD AS 卡密码 FROM T_CIPMP_CARD WHERE MAKECARD_NO=? ";
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

	@Execute("获取制卡数据")
	@Pagelet.Purview("execute")
	public void cardMakeData(@RequestParam("pojo") HashMap<String, String> pojo)
			throws Exception {
		String makecard_no = pojo.get("makecard_no") == null ? "" : pojo
				.get("makecard_no").toString().trim();
		String cardSql = "update T_CIPMP_MAKECARD set  CARD_STATES =? ,DAT_DATE=?   where  MAKECARD_NO =?";
		String msg = DaoUtil.execute(ds, cardSql, "2",
				CurrentTime.getSystemTime(), makecard_no) == 1 ? "" : "失败";
		P.setAttribute("msg", msg);
		execute();
	}

	@Execute("下载制卡数据")
	@Pagelet.Purview()
	public File downLoadCardDataFile(
			@RequestParam("pojo") HashMap<String, String> pojo)
			throws Exception {
		String merCode = P.mp.getMerchant().getMerCode();
		// String merCode = "01";
		String makeCard_NO = pojo.get("makeCard_NO");
		File file = File.createTempFile("_downLoadCardDataFile", "");
		try {
			if (createNewFile(makeCard_NO, file)) {
				// Map<String, Object> row = DaoUtil
				// .row(ds,
				// "SELECT count(*) AS FILE_SUM FROM T_CIPMP_MAKECARD WHERE MAKECARD_NO = ?",
				// makeCard_NO);
				// int file_sum =
				// Integer.parseInt(row.get("FILE_SUM").toString()) + 1;
				DaoUtil.execute(
						ds,
						"UPDATE T_CIPMP_MAKECARD SET DOWM_NUM=DOWM_NUM+1 WHERE MAKECARD_NO=? AND MER_CODE=?",
						makeCard_NO, merCode);
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("异常");
		}
		// P.setAttribute("MSG", "下载成功！下载路径为：" + fileurl);
		return file;
	}

	@Execute("入库")
	@Pagelet.Purview("execute")
	public void cardStorage(@RequestParam("pojo") HashMap<String, String> pojo)
			throws Exception {
		String makecard_no = pojo.get("makecard_no") == null ? "" : pojo
				.get("makecard_no").toString().trim();
		String chk_op = pojo.get("chk_op") == null ? "" : pojo.get("chk_op")
				.toString().trim();

		String makeCardSql = "UPDATE T_CIPMP_MAKECARD set CARD_STATES =? ,CHK_DATE=?,CHK_OP=? where MAKECARD_NO =? AND MER_CODE=?";
		int result = DaoUtil.execute(ds, makeCardSql, "3", CurrentTime
				.getSystemTime(), chk_op, makecard_no, P.mp.getMerchant()
				.getMerCode());
		String cardSql = "UPDATE T_CIPMP_CARD SET CARD_STATE=1 WHERE MER_CODE=? AND MAKECARD_NO=?";
		DaoUtil.execute(ds, cardSql, P.mp.getMerchant().getMerCode(),
				makecard_no);
		P.setAttribute("msg", (result == 1 ? "" : "失败"));
		// 入库 保存卡数据到数据后 销毁接口数据 并修改状态为派发
		execute();
	}

	@Execute("进入派发页面")
	@Pagelet.Purview("execute")
	public void dispbthCard(@RequestParam("pojo") HashMap<String, String> pojo)
			throws Exception {
		P.setAttribute("STORE", DaoUtil.list(ds, 0,
				"select * from T_CIPMP_STORE where MER_CODE =?", P.mp
						.getMerchant().getMerCode()));
	}

	@Execute("派发操作")
	@Pagelet.Purview("execute")
	public StringBuffer addDispbth(  String v1,
			@RequestParam("v2") String v2,
			@RequestParam("proc_no") String proc_no,
			@RequestParam("makecard_no") String makecard_no)
			throws SQLException {
		TransactionDataSource tds = new TransactionDataSource(ds);
		Map<String, Object> rMap = new HashMap<String, Object>();
		proc_no = proc_no == null ? "" : proc_no.trim();// 卡类型
		String card_amt = v2 == null ? "" : v2.trim();// 卡数量
		String store_code = v1 == null ? "" : v1.trim();// 门店编号
		makecard_no = makecard_no == null ? "" : makecard_no.trim();
		String oper_user = P.mp.getAdmin().getCode();
		String merCode = P.mp.getMerchant().getMerCode();
		List<Map<String, Object>> list = DaoUtil.list(ds,
						0,
						"select * from T_CIPMP_CARD where MER_CODE =? and CARD_TYPE=? and MEMB_PHONE=0 and MEMB_STORE is NULL  ORDER by CARD_NUM desc limit "
								+ card_amt, merCode, proc_no);
		if (Integer.parseInt(card_amt) == list.size()) {
			for (int i = 0; i < list.size(); i++) {
				String cardNo = list.get(i).get("CARD_NUM").toString();
				String cardSql = "update T_CIPMP_CARD set  MEMB_STORE =? where CARD_NUM =? ";
				DaoUtil.execute(tds, cardSql, store_code, cardNo);
			}
			String orderId = new Date().getTime() + "";
			String cardFrom = list.get(0).get("CARD_NUM").toString();
			String cardTo = list.get(list.size() - 1).get("CARD_NUM")
					.toString();
			String addDispbthSql = "INSERT INTO T_CIPMP_DISPBTH (DISPBATCH_NO, makecard_no, MER_CODE, STROE_CODE, DISDB_DATE, NO_START, NO_END, CARD_AMT, OPERATE_USER, OPERATE_TIME) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			String msg = DaoUtil.execute(tds, addDispbthSql, orderId,
					makecard_no, merCode, store_code,
					CurrentTime.getSystemTime(), cardFrom, cardTo,
					Integer.parseInt(card_amt), oper_user,
					CurrentTime.getSystemTime()) == 1 ? "派发成功" : "派发失败";
			rMap.put("errorMsg", "");
			rMap.put("Msg", msg);
			tds.transactionCommitOrRollback();
			return new StringBuffer(JsonUtil.encoder((Map<?, ?>) rMap));
		} else {
			rMap.put("errorMsg", "卡片数量不足");
			return new StringBuffer(JsonUtil.encoder((Map<?, ?>) rMap));
		}

	}

	@Execute("查看详情")
	@Pagelet.Purview("execute")
	public void detailed(@RequestParam("pojo") HashMap<String, String> pojo)
			throws SQLException {
		String makecard_no = pojo.get("makecard_no") == null ? "" : pojo
				.get("makecard_no").toString().trim();
		// 查询制卡详情
		P.setAttribute("makeCardDetail", DaoUtil.list(ds, 0,
				"select * from T_CIPMP_MAKECARD where MAKECARD_NO =?",
				makecard_no));
		// 查询派卡详情
		P.setAttribute(
				"makecard_List",
				DaoUtil.list(
						ds,
						0,
						"select * from T_CIPMP_DISPBTH where makecard_no =?  ORDER by DISPBATCH_NO desc limit 10",
						makecard_no));
	}

	@Execute("查看门店库存")
	@Pagelet.Purview("execute")
	public void page2(@RequestParam("pojo") HashMap<String, String> pojo)
			throws SQLException {
		String page = pojo.get("page") == null ? "1" : pojo.get("page")
				.toString().trim();
		List<Map<String, Object>> STORElist = DaoUtil.list(ds, 0,
				"select * from T_CIPMP_STORE where MER_CODE =?", P.mp
						.getMerchant().getMerCode());
		List<Map<String, Object>> LEVELlist = DaoUtil.list(ds, 0,
				"select * from T_CIPMP_CARD_TYPE where MER_CODE =?", P.mp
						.getMerchant().getMerCode());
		List<StoreCardList> STOREcardlist = new ArrayList<StoreCardList>();
		for (int i = 0; i < STORElist.size(); i++) {
			StoreCardList s = new StoreCardList();
			Map<String, Object> STOREcount = new HashMap<String, Object>();
			for (int j = 0; j < LEVELlist.size(); j++) {
				String cardSql = "select * from T_CIPMP_CARD where MEMB_STORE=? and  CARD_TYPE=? and  CARD_STATE=?";
				long count = DaoUtil.count(ds, cardSql,
						STORElist.get(i).get("STORE_CODE"), LEVELlist.get(j)
								.get("CARD_TYPE").toString(), "1");
				STOREcount.put(LEVELlist.get(j).get("CARD_TYPE").toString(),
						count);

			}
			s.setStoreName(STORElist.get(i).get("STORE_NAME").toString());
			s.setCardlist(STOREcount);
			STOREcardlist.add(s);

		}
		int row = STOREcardlist.size();
		int pageSize = 2;
		int count = (row - 1) / pageSize + 1;
		if (Integer.parseInt(page) < 1) {
			page = "1";
		} else if (Integer.parseInt(page) > count) {
			page = count + "";
		}
		int Max = Integer.parseInt(page) * pageSize;
		if (Max > STOREcardlist.size()) {
			Max = STOREcardlist.size();
		}
		List<StoreCardList> STOREcardlist2 = new ArrayList<StoreCardList>();
		for (int i = (Integer.parseInt(page) - 1) * pageSize; i < Max; i++) {
			StoreCardList s = STOREcardlist.get(i);
			STOREcardlist2.add(s);
		}
		// 查询门店
		P.setAttribute("STORE", DaoUtil.list(ds, 0,
				"select * from T_CIPMP_STORE where MER_CODE =?", P.mp
						.getMerchant().getMerCode()));
		P.setAttribute("TYPE_count", LEVELlist.size());
		System.out.println(STOREcardlist);
		P.setAttribute("STOREcardlist", STOREcardlist2);
		P.setAttribute("page", page);
		P.setAttribute("sum", count);

	}
}
