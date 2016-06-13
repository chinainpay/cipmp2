package com.chinainpay.apps.membercard;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import net.javabone.common.util.DaoUtil;
import net.javabone.common.util.RegexpUtils;
import net.javabone.common.web.JsonUtil;

import com.chinainpay.mp.app.pagelet.Pagelet;
import com.chinainpay.mp.app.pagelet.axis.P;

public class CardLevelSetUp implements Pagelet {

	/**
	 * 会员级别设置
	 */
	private static final long serialVersionUID = 7840952652178278963L;
	private DataSource ds = P.ds("base");

	@Execute("访问")
	@Pagelet.Purview()
	public void execute() throws SQLException {	
		List list = DaoUtil.list(ds, 0,
				"select * from T_CIPMP_CARD_TYPE where MER_CODE =? ", P.mp
				.getMerchant().getMerCode());
		P.setAttribute("list",DaoUtil.list(ds, 0,
				"select * from T_CIPMP_CARD_TYPE where MER_CODE =? limit 10", P.mp
				.getMerchant().getMerCode()));	
		int sum = (list.size() - 1) / 5 + 1;
		P.setAttribute("page", 1);
		P.setAttribute("sum", sum);
	}
	@Execute("分页")
	@Pagelet.Purview("execute")
	public void pageInto(@RequestParam("pojo") HashMap<String, String> pojo)
			throws SQLException {
		execute();
		String sqlList = "select * from T_CIPMP_CARD_TYPE where MER_CODE =? ";
		List<Map<String, Object>>  list = DaoUtil.list(ds, 0, sqlList, P.mp.getMerchant()
				.getMerCode());
		String page = pojo.get("page") == null ? "1" : pojo.get("page")
				.toString().trim();
		int sum = (list.size() - 1) / 5 + 1;
		if (Integer.parseInt(page) < 1) {
			page = "1";
		} else if (Integer.parseInt(page) > sum) {
			page = sum + "";
		}
		System.out.println(sum);
		System.out.println(page);
		String sql = "select * from T_CIPMP_CARD_TYPE where MER_CODE =? limit "
				+ (Integer.parseInt(page) - 1) * 10 + ",10";
		P.setAttribute("list",
				DaoUtil.list(ds, 0, sql, P.mp.getMerchant().getMerCode()));
		P.setAttribute("page", page);
		P.setAttribute("sum", sum);
	}
	@Execute("新增卡类型")
	@Pagelet.Purview("execute")
	public StringBuffer cardtype(@RequestParam("pojo") HashMap<String, String> pojo)
			throws SQLException {
		Map<String, Object> rMap = new HashMap<String, Object>();
		String msg="";
		String cardtype = pojo.get("cardtype") == null ? "" : pojo
				.get("cardtype").toString().trim();
		if(cardtype == ""){
			msg ="请输入卡类型";
			rMap.put("errorMsg", msg);
			return new StringBuffer(JsonUtil.encoder(rMap));
		}
		String levelmoney = pojo.get("levelmoney") == null ? "" : pojo
				.get("levelmoney").toString().trim();
		if(levelmoney == ""){
			msg ="请输入定额储值";
			rMap.put("errorMsg", msg);
			return new StringBuffer(JsonUtil.encoder(rMap));
		}
		if(!RegexpUtils.is0ZIndex(levelmoney)){
			msg ="储值金额必须是0或正整数";
			rMap.put("errorMsg", msg);
			return new StringBuffer(JsonUtil.encoder(rMap));
		}
//		String discount = pojo.get("discount") == null ? "" : pojo
//				.get("discount").toString().trim();
		String discount="100";
		if(discount == ""){
			msg ="请输入折扣额度";
			rMap.put("errorMsg", msg);
			return new StringBuffer(JsonUtil.encoder(rMap));
		}
		if(!RegexpUtils.is0ZIndex(discount)){
			msg ="折扣额度填写必须是0或正整数";
			rMap.put("errorMsg", msg);
			return new StringBuffer(JsonUtil.encoder(rMap));
		}
		String MerCode = P.mp.getMerchant().getMerCode();
		String Integral=pojo.get("integral") == null ? "" : pojo
				.get("integral").toString().trim();
		if(Integral == ""){
			msg ="请输入积分规则";
			rMap.put("errorMsg", msg);
			return new StringBuffer(JsonUtil.encoder(rMap));
		}
		if(!RegexpUtils.is0ZIndex(Integral)){
			msg ="积分规则填写必须是0或正整数";
			rMap.put("errorMsg", msg);
			return new StringBuffer(JsonUtil.encoder(rMap));
		}
		String cardSql = "insert into T_CIPMP_CARD_TYPE values(?,?,?,?,?);";
		Map<String, Object> rowMap = DaoUtil
				.row(ds,
						"SELECT COUNT(*) AS COUNT FROM T_CIPMP_CARD_TYPE WHERE MER_CODE=? AND CARD_TYPE= ? ",
						MerCode,cardtype);
		if (Integer.parseInt(rowMap.get("COUNT").toString()) == 0) {
			msg =DaoUtil.execute(ds, cardSql, MerCode, cardtype,
					levelmoney, discount,Integral)== 1 ? "新增卡类型成功"
							: "新增卡类型失败";
			if(msg.equals("新增卡类型失败")){
				rMap.put("errorMsg", msg);
			}
		}else{
			msg ="此中卡类型已经存在";
			rMap.put("errorMsg", msg);
		}
		rMap.put("Msg", msg);
		return new StringBuffer(JsonUtil.encoder(rMap));
		
	}

}
