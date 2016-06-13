package com.chinainpay.apps.ticket;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.xml.parsers.ParserConfigurationException;

import net.javabone.common.util.DaoUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.xml.sax.SAXException;

import com.chinainpay.mp.app.pagelet.Pagelet;
import com.chinainpay.mp.app.pagelet.axis.P;
import com.chinainpay.mp.mq.client.ServiceNotFoundException;
import com.chinainpay.mp.mq.client.ServiceTimeoutException;
import com.chinainpay.apps.service.CouponServices;
import com.chinainpay.apps.ticket.util.CardTicketManagerUtil;
import com.chinainpay.apps.ticket.util.CompareTicketGroup;
import com.chinainpay.apps.ticket.util.CreateTwoTicket;
import com.chinainpay.apps.ticket.util.PageUtil;
import com.chinainpay.apps.ticket.util.TicketLocalDataUtil;
import com.chinainpay.apps.util.HttpURLConnectionUtil;

public class TicketManager implements Pagelet {

	private static final long serialVersionUID = 1L;
	private DataSource ds = P.ds("base");
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Execute("访问地址")
	@Pagelet.Purview()
	public void execute(@RequestParam("pageSize") String pageSize) throws SQLException, SAXException, IOException,
			ParserConfigurationException, ParseException {
		String merNo = P.mp.getMerchant().getMerCode();
		pageSize=pageSize.equals("")?"1":pageSize;
		int currentPage=Integer.parseInt(pageSize);
		int currentSqlPage=Integer.parseInt(pageSize)-1;
		int pageCount=10;
		P.setAttribute("list", CardTicketManagerUtil.getCardTicketList(merNo));
		StringBuilder sql=new StringBuilder();
		sql.append("SELECT * FROM (");
		sql.append("SELECT t.card_id,t.card_type,bi.title,bi.date_info_type,bi.begin_timestamp,bi.end_timestamp,bi.fixed_term,bi.fixed_begin_term,bi.sku_quantity,t.status,t.create_time,t.MER_CODE,(SELECT COUNT(*) FROM T_CIPMP_CARD_TICKET_RECEIVE WHERE title=bi.title AND ticket_type=t.card_type AND ticket_state=0 )AS lqcount ,(SELECT COUNT(*) FROM T_CIPMP_CARD_TICKET_RECEIVE WHERE title=bi.title AND ticket_type=t.card_type AND ticket_state=1 )AS hxcount FROM T_CIPMP_BASE_INFO bi,T_CIPMP_TICKET_CASH t WHERE bi.base_info_id=t.base_info_id ");
		sql.append("UNION ALL ");
		sql.append("SELECT t.card_id,t.card_type,bi.title,bi.date_info_type,bi.begin_timestamp,bi.end_timestamp,bi.fixed_term,bi.fixed_begin_term,bi.sku_quantity,t.status,t.create_time,t.MER_CODE,(SELECT COUNT(*) FROM T_CIPMP_CARD_TICKET_RECEIVE WHERE title=bi.title AND ticket_type=t.card_type AND ticket_state=0 )AS lqcount ,(SELECT COUNT(*) FROM T_CIPMP_CARD_TICKET_RECEIVE WHERE title=bi.title AND ticket_type=t.card_type AND ticket_state=1 )AS hxcount FROM T_CIPMP_BASE_INFO bi,T_CIPMP_TICKET_DISCOUNT t WHERE bi.base_info_id=t.base_info_id ");
		sql.append("UNION ALL ");
		sql.append("SELECT t.card_id,t.card_type,bi.title,bi.date_info_type,bi.begin_timestamp,bi.end_timestamp,bi.fixed_term,bi.fixed_begin_term,bi.sku_quantity,t.status,t.create_time,t.MER_CODE,(SELECT COUNT(*) FROM T_CIPMP_CARD_TICKET_RECEIVE WHERE title=bi.title AND ticket_type=t.card_type AND ticket_state=0 )AS lqcount ,(SELECT COUNT(*) FROM T_CIPMP_CARD_TICKET_RECEIVE WHERE title=bi.title AND ticket_type=t.card_type AND ticket_state=1 )AS hxcount FROM T_CIPMP_BASE_INFO bi,T_CIPMP_TICKET_GENERAL_COUPON t WHERE bi.base_info_id=t.base_info_id ");
		sql.append("UNION ALL ");
		sql.append("SELECT t.card_id,t.card_type,bi.title,bi.date_info_type,bi.begin_timestamp,bi.end_timestamp,bi.fixed_term,bi.fixed_begin_term,bi.sku_quantity,t.status,t.create_time,t.MER_CODE,(SELECT COUNT(*) FROM T_CIPMP_CARD_TICKET_RECEIVE WHERE title=bi.title AND ticket_type=t.card_type AND ticket_state=0 )AS lqcount ,(SELECT COUNT(*) FROM T_CIPMP_CARD_TICKET_RECEIVE WHERE title=bi.title AND ticket_type=t.card_type AND ticket_state=1 )AS hxcount FROM T_CIPMP_BASE_INFO bi,T_CIPMP_TICKET_GIFT t WHERE bi.base_info_id=t.base_info_id ");
		sql.append("UNION ALL ");
		sql.append("SELECT t.card_id,t.card_type,bi.title,bi.date_info_type,bi.begin_timestamp,bi.end_timestamp,bi.fixed_term,bi.fixed_begin_term,bi.sku_quantity,t.status,t.create_time,t.MER_CODE,(SELECT COUNT(*) FROM T_CIPMP_CARD_TICKET_RECEIVE WHERE title=bi.title AND ticket_type=t.card_type AND ticket_state=0 )AS lqcount ,(SELECT COUNT(*) FROM T_CIPMP_CARD_TICKET_RECEIVE WHERE title=bi.title AND ticket_type=t.card_type AND ticket_state=1 )AS hxcount FROM T_CIPMP_BASE_INFO bi,T_CIPMP_TICKET_GROUPON t WHERE bi.base_info_id=t.base_info_id ");
		sql.append(") AS tt ");
		sql.append("WHERE tt.status!='0' AND tt.MER_CODE=? ");
		sql.append("ORDER BY tt.create_time DESC ");
		List<Map<String, Object>> list=DaoUtil.list(ds, pageCount, currentSqlPage, sql.toString(), merNo);
		list=CardTicketManagerUtil.eachTicket(list);
		P.setAttribute("list", list);
		StringBuilder sql2=new StringBuilder();
		sql2.append("SELECT COUNT(*) AS COUNT FROM (");
		sql2.append("SELECT t.card_id,t.card_type,bi.title,bi.date_info_type,bi.begin_timestamp,bi.end_timestamp,bi.fixed_term,bi.fixed_begin_term,bi.sku_quantity,t.status,t.create_time,t.MER_CODE FROM T_CIPMP_BASE_INFO bi,T_CIPMP_TICKET_CASH t WHERE bi.base_info_id=t.base_info_id ");
		sql2.append("UNION ALL ");
		sql2.append("SELECT t.card_id,t.card_type,bi.title,bi.date_info_type,bi.begin_timestamp,bi.end_timestamp,bi.fixed_term,bi.fixed_begin_term,bi.sku_quantity,t.status,t.create_time,t.MER_CODE FROM T_CIPMP_BASE_INFO bi,T_CIPMP_TICKET_DISCOUNT t WHERE bi.base_info_id=t.base_info_id ");
		sql2.append("UNION ALL ");
		sql2.append("SELECT t.card_id,t.card_type,bi.title,bi.date_info_type,bi.begin_timestamp,bi.end_timestamp,bi.fixed_term,bi.fixed_begin_term,bi.sku_quantity,t.status,t.create_time,t.MER_CODE FROM T_CIPMP_BASE_INFO bi,T_CIPMP_TICKET_GENERAL_COUPON t WHERE bi.base_info_id=t.base_info_id ");
		sql2.append("UNION ALL ");
		sql2.append("SELECT t.card_id,t.card_type,bi.title,bi.date_info_type,bi.begin_timestamp,bi.end_timestamp,bi.fixed_term,bi.fixed_begin_term,bi.sku_quantity,t.status,t.create_time,t.MER_CODE FROM T_CIPMP_BASE_INFO bi,T_CIPMP_TICKET_GIFT t WHERE bi.base_info_id=t.base_info_id ");
		sql2.append("UNION ALL ");
		sql2.append("SELECT t.card_id,t.card_type,bi.title,bi.date_info_type,bi.begin_timestamp,bi.end_timestamp,bi.fixed_term,bi.fixed_begin_term,bi.sku_quantity,t.status,t.create_time,t.MER_CODE FROM T_CIPMP_BASE_INFO bi,T_CIPMP_TICKET_GROUPON t WHERE bi.base_info_id=t.base_info_id ");
		sql2.append(") AS tt ");
		sql2.append("WHERE tt.status!='0' AND tt.MER_CODE=? ");
		sql2.append("ORDER BY tt.create_time DESC ");
		Map<String, Object> map=DaoUtil.row(ds, sql2.toString(), merNo);
		int totalRow=0;
		if(map.get("COUNT")!=null){
			totalRow=Integer.parseInt(map.get("COUNT").toString());
		}
		P.setAttribute("page",new PageUtil(totalRow, currentPage, pageCount));
	}

	@Execute("查询门店详细信息")
	@Pagelet.Purview("execute")
	public void cardDetails(@RequestParam("pojo") HashMap<String, String> pojo) {
		try {
			String cardid = pojo.get("cardid");
			String card_type = pojo.get("cardtype");
			Map<String, Object> map = CardTicketManagerUtil
					.getCardDetailByCardTypeAndCardId(cardid, card_type);
			P.log.d(">>>>>>>>>>>>>>map>>>>>>>>>>>>>>>" + map);
			P.setAttribute("list", map);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Execute("查询使用详细信息")
	@Pagelet.Purview("execute")
	public void cardUserDetails(
			@RequestParam("page") String page2,
			@RequestParam("cardtype") String cardtype,
			@RequestParam("cardid") String cardid,
			@RequestParam("pojo") HashMap<String, String> pojo,
			@RequestParam("TRADE_DATE_S") String TRADE_DATE_S,
			@RequestParam("TRADE_DATE_E") String TRADE_DATE_E) {
		int size = 20;
		try {
			String card_type = pojo.get("cardtype")==null?cardtype:pojo.get("cardtype");
			String card_id = pojo.get("cardid")==null?cardid:pojo.get("cardid");
			StringBuffer sb = new StringBuffer("SELECT cr.ticket_state,cr.STORE_CODE AS STORE_CODE,cs.STORE_NAME,cr.card_id,cr.ticket_type,count(used_time) as count,cr.title,cr.used_time,cr.orderNo,cr.Terminal_Identity FROM T_CIPMP_CARD_TICKET_RECEIVE cr LEFT JOIN T_CIPMP_STORE cs ON cr.STORE_CODE=cs.STORE_CODE  WHERE (ticket_state=1 OR ticket_state=0)");
			List<Object> obj=new ArrayList<Object>();
			//增加卡种条件
			if(card_type!=null&&!card_type.equals("")){
			sb.append(" and cr.ticket_type=?");
			obj.add(card_type);
			}
			//增加卡ID条件
			if(card_id!=null&&!card_id.equals("")){
			sb.append(" and cr.card_id=?");
			obj.add(card_id);
			}
			String time1 = null;
			String time2 = null;
			//按时间查询
			if (TRADE_DATE_S!=null&&!TRADE_DATE_S.equals("")) {
				sb.append(" and cr.used_time >= '"+TRADE_DATE_S+"'");
			}
			if (TRADE_DATE_E!=null&&!TRADE_DATE_E.equals("")) {
				sb.append(" and cr.used_time <= '"+TRADE_DATE_E+"'");
			}
			sb.append(" group by cr.used_time,cr.orderNo");
			List<Map<String, Object>> list=DaoUtil.list(ds, 0, 0, sb.toString(), obj.toArray());
			
			int count =list.size();
			int totalPages = count % size == 0 ? count/ size : count / size + 1;
			
			int page = page2 == null || page2.equals("") ? 1 : Integer
					.parseInt(page2);
			if (page<1) {
				page=1;
			}
			if (page>totalPages) {
				page = totalPages;
			}
			List<Map<String, Object>> list2 = DaoUtil.list(ds,size,page-1,sb.toString(),obj.toArray());
			for (Map<String, Object> map : list2) {
				String ct = map.get("ticket_type").toString();
				String ts=map.get("ticket_state").toString();
				String sc=map.get("STORE_CODE").toString();
				String sn=map.get("STORE_NAME")!=null?map.get("STORE_NAME").toString():"";
				if(ts.equals("0")){
					ts="已领取";
					sc="";
					sn="";
				}else{
					ts="已核销";
				}
				map.put("tickettype", CompareTicketGroup.getTicketType(ct));
				map.put("ticket_state", ts);
				map.put("STORE_CODE", sc);
				map.put("STORE_NAME", sn);
			}
			P.setAttribute("list", list2);
			P.setAttribute("TRADE_DATE_S",TRADE_DATE_S);
			P.setAttribute("TRADE_DATE_E",TRADE_DATE_E);
			P.setAttribute("sum",totalPages);
			P.setAttribute("cardtype",card_type);
			P.setAttribute("cardid",card_id);
			P.setAttribute("page",page);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}

	@Execute("页面跳转")
	@Pagelet.Purview("execute")
	public String groupTicketUrl(@RequestParam("state") String state)
			throws ServiceNotFoundException, ServiceTimeoutException,
			ParseException, SQLException {
		String returnImage = "/manage/member-card/ticket/";
		if (state.equals("1")) {
			returnImage = returnImage + "discount";// 折扣券
		} else if (state.equals("2")) {
			returnImage = returnImage + "cash";// 代金券
		} else if (state.equals("3")) {
			returnImage = returnImage + "gift";// 礼品券
		} else if (state.equals("4")) {
			returnImage = returnImage + "groupon";// 团购券
		} else if (state.equals("5")) {
			returnImage = returnImage + "general_coupon";// 通用优惠券
		} else {
			returnImage = returnImage + "discount";
		}
		P.log.d("returnImage》》》" + returnImage);
		// 获取颜色列表
		//String colorResult = AppServices.getTicketColor();
		String colorResult = "[{\"name\":\"Color010\",\"value\":\"#55bd47\"},{\"name\":\"Color020\",\"value\":\"#10ad61\"},{\"name\":\"Color030\",\"value\":\"#35a4de\"},{\"name\":\"Color040\",\"value\":\"#3d78da\"},{\"name\":\"Color050\",\"value\":\"#9058cb\"},{\"name\":\"Color060\",\"value\":\"#de9c33\"},{\"name\":\"Color070\",\"value\":\"#ebac16\"},{\"name\":\"Color080\",\"value\":\"#f9861f\"},{\"name\":\"Color081\",\"value\":\"#f08500\"},{\"name\":\"Color090\",\"value\":\"#e75735\"},{\"name\":\"Color100\",\"value\":\"#d54036\"},{\"name\":\"Color101\",\"value\":\" #cf3e36\"}]";
		P.setAttribute("color", JSONArray.fromObject(colorResult));

		// 获取门店列表
//		List<Map<String, Object>> list = (List<Map<String, Object>>) P.mp
//				.getService("2e211b31-7d20-49e0-b595-b86b52617ce4", 60000,
//						"getStores", P.mp.getMerchant().getMerCode()).getValue();
		String sql = "SELECT * FROM T_CIPMP_STORE WHERE MER_CODE=? ORDER BY STORE_CODE ASC";
		List<Map<String, Object>> list=DaoUtil.list(ds,0, sql, P.mp.getMerchant().getMerCode());
		P.setAttribute("storeList", list);

		// 创建日期 前台显示
		String startDate = sdf.format(new Date());
		P.setAttribute("startDate", startDate);

		// 商户名称
		String sql1 = "SELECT MER_NAME FROM T_CIPMP_MER WHERE MER_CODE=?";
		Map<String, Object> map = DaoUtil.row(ds, sql1, P.mp.getMerchant().getMerCode());
		if (map != null) {
			P.setAttribute("brandName", map.get("MER_NAME"));
		}
		return returnImage;
	}

	@Execute("创建卡票券")
	@Pagelet.Purview("execute")
	public StringBuffer createTicketAction(@RequestParam("json") String json,
			@RequestParam("card_type") String card_type)
			throws ServiceNotFoundException, ServiceTimeoutException,
			SQLException, ParseException, IOException, SAXException,
			ParserConfigurationException {
		JSONObject returnMsg = new JSONObject();
		String msg = "";
		P.log.d(json);
		String card_id = "";
		JSONObject jsonObj = JSONObject.fromObject(json);
		if (jsonObj.get("tbweixin").toString().equals("true")) {
			String result = TicketLocalDataUtil.saveToWX(jsonObj, card_type);// 同步到微信
			if (result.equals("ok") || result.startsWith("{")) {
				JSONObject resultJson = JSONObject.fromObject(result);
				String errorMsg = resultJson.getString("errmsg");
				if (errorMsg.equals("ok")) {
					// 将返回的cardid保存到数据库
					card_id = resultJson.get("card_id").toString();
				} else {
					returnMsg.put("errorMsg", result);
					return new StringBuffer(returnMsg.toString());// 同步到微信失败
				}
			} else {
				returnMsg.put("errorMsg", result);
				return new StringBuffer(returnMsg.toString());// 同步到微信失败
			}
		}
		msg = TicketLocalDataUtil.saveToDate(jsonObj, card_type, card_id);// 保存到数据库
		if (msg.equals("ok")) {
			// 跳到列表页
			returnMsg.put("url", "/manage/member-card/ticket/ticketManager.html");
			return new StringBuffer(returnMsg.toString());
		}
		returnMsg.put("errorMsg", msg);
		return new StringBuffer(returnMsg.toString());
	}

	@Execute("投放专卡二维码")
	@Pagelet.Purview("execute")
	public HttpServletResponse deliveryCardTicket(
			@RequestParam("pojo") HashMap<String, String> pojo) {
		HttpServletResponse response = P.getResponse();
		response.setContentType("application/x-download");

		String carddid = pojo.get("cardid");
		String cardType = pojo.get("cardtype");

		if (carddid.equals("") || cardType.equals("")) {
			return response;
		}
		response.setHeader("Content-disposition", "attachment; filename="
				+ carddid + ".jpg");
		try {
			// 1、获得卡券ticket
			String result = CreateTwoTicket.QR_LIMIT_STR_SCENE(carddid);
			P.log.d("deliveryCardTicket_result>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
					+ result);
			JSONObject jo_ticket = JSONObject.fromObject(result);
			if (jo_ticket.containsKey("errcode")) {
				return response;
			}
			String ticket = jo_ticket.getString("ticket");
			// 2、根据ticket获得文件流
			CreateTwoTicket.getImageByTicket(ticket, response);
		} catch (ServiceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceTimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Execute("删除二维码")
	@Pagelet.Purview("execute")
	public void delCardTicket(@RequestParam("pojo") HashMap<String, String> pojo)
			throws ParseException {
		String cardid = pojo.get("cardid") == null ? "" : pojo.get("cardid")
				.toString();
		String cardtype = pojo.get("cardtype") == null ? "" : pojo.get(
				"cardtype").toString();
		try {
			// 查看是否同步到微信，若同步到微信，将微信中的卡券也删除
			String sql = "SELECT wx_card_id FROM TABLE t WHERE card_id=?  and MER_CODE=? ";
			sql = CompareTicketGroup.getCompareStr(sql, cardtype, "TABLE");
			Map<String, Object> map = DaoUtil.row(ds, sql, cardid, P.mp
					.getMerchant().getMerCode());
			String wx_card_id = map.get("wx_card_id") == null ? "" : map.get(
					"wx_card_id").toString();
			if (!wx_card_id.equals("")) {
				CreateTwoTicket.delTicket(cardid);// 删除微信上的卡券
			}
			// 删除本地的卡券
			String delSql = "UPDATE TABLE SET STATUS='0' WHERE card_id=? AND MER_CODE=?";
			delSql = CompareTicketGroup
					.getCompareStr(delSql, cardtype, "TABLE");
			int result = DaoUtil.execute(ds, delSql, cardid, P.mp.getMerchant()
					.getMerCode());
			P.log.d("result=" + result);
			execute("");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceTimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Execute("群发专卡卡券的图文消息")
	@Pagelet.Purview("execute")
	public void deliveryCardTicketImg(
			@RequestParam("pojo") HashMap<String, String> pojo)
			throws ServiceNotFoundException, ServiceTimeoutException, SAXException, IOException, ParserConfigurationException, SQLException, ParseException {
		String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=";
		String access_token = P.mp
				.getService("Weixin", 60000, "getAccessTokey").getValue()
				.toString();
		url += access_token;

		// 获取关注用户的列表信息
		String userUrl = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN";

		String userUrlResult = HttpURLConnectionUtil.sendHttpsRequest(
				userUrl.replace("ACCESS_TOKEN", access_token), null, "POST",
				30000);
		JSONObject jo = JSONObject.fromObject(userUrlResult);
		String data = jo.getString("data");
		JSONObject jo1 = JSONObject.fromObject(data);
		String openidArr = jo1.getString("openid");
		JSONArray openidArrJa = JSONArray.fromObject(openidArr);
		String result = "";
		String merCode = P.mp.getMerchant().getMerCode();
		for (int i = 0; i < openidArrJa.size(); i++) {
			JSONObject jo2 = new JSONObject();
			jo2.put("touser", openidArrJa.get(i));// 用户openId
			jo2.put("msgtype", "news");
			JSONObject jo3 = new JSONObject();
//			jo3.put("content", "Hello World");
			String eventKey = pojo.get("cardid");
			String clickUrl = "http://"
					+ merCode
					+ ".wcyp.gaohuitong.com/mobile/member-card/getCardTicket.html?time="+System.currentTimeMillis()+"&cardId="+ eventKey;// 测试
//			String clickUrl = "http://"
//					+ merCode
//					+ ".cipmp.gaohuitong.com/mobile/member-card/getCardTicket.html?time="+System.currentTimeMillis()+"&cardId="+ eventKey;// 测试
			JSONArray ja = new JSONArray();
			JSONObject joContent = new JSONObject();
			joContent.put("title", "领取卡券");
			joContent.put("description", "快来快来:）");
			joContent.put("url", clickUrl);
			joContent.put("picurl", "https://mmbiz.qlogo.cn/mmbiz/qqNSkJXiawtScBYcNdic4SlTqXicY74QHra48QAU0N5DXd5pu2n3Patno16JTNzSBhjXpZfSeSCgFUYcFhDOE8OkA/0?wx_fmt=gif");
			ja.add(joContent);
			jo3.put("articles", ja);
			jo2.put("news", jo3);
			P.log.d(("群发卡券的数据  " + jo2));
			result = HttpURLConnectionUtil.sendHttpsRequest(url,
					jo2.toString(), "POST", 30000);
			P.log.d(("   *****  群发卡券返回值  *****  " + result));
			execute("");
		}
	}
	
	@Execute("下载流水")
	@Pagelet.Purview("")
	public HttpServletResponse download(
			@RequestParam("page") String page2,
			@RequestParam("cardtype") String cardtype,
			@RequestParam("cardid") String cardid,
			@RequestParam("pojo") HashMap<String, String> pojo,
			@RequestParam("TRADE_DATE_S") String TRADE_DATE_S,
			@RequestParam("TRADE_DATE_E") String TRADE_DATE_E) throws Exception {
		HttpServletResponse response=P.getResponse();
		String card_type = pojo.get("cardtype")==null?cardtype:pojo.get("cardtype");
		String card_id = pojo.get("cardid")==null?cardid:pojo.get("cardid");
		StringBuffer sb = new StringBuffer("SELECT cr.ticket_state,cr.STORE_CODE AS STORE_CODE,cs.STORE_NAME,cr.card_id,cr.ticket_type,count(used_time) as count,cr.title,cr.used_time,cr.orderNo,cr.Terminal_Identity FROM T_CIPMP_CARD_TICKET_RECEIVE cr LEFT JOIN T_CIPMP_STORE cs ON cr.STORE_CODE=cs.STORE_CODE  WHERE (ticket_state=1 OR ticket_state=0)");
		List<Object> obj=new ArrayList<Object>();
		//增加卡种条件
		if(card_type!=null&&!card_type.equals("")){
		sb.append(" and cr.ticket_type=?");
		obj.add(card_type);
		}
		//增加卡ID条件
		if(card_id!=null&&!card_id.equals("")){
		sb.append(" and cr.card_id=?");
		obj.add(card_id);
		}
		String time1 = null;
		String time2 = null;
		//按时间查询
		if (TRADE_DATE_S!=null&&!TRADE_DATE_S.equals("")) {
			sb.append(" and cr.used_time >= '"+TRADE_DATE_S+"'");
		}
		if (TRADE_DATE_E!=null&&!TRADE_DATE_E.equals("")) {
			sb.append(" and cr.used_time <= '"+TRADE_DATE_E+"'");
		}
		sb.append(" group by cr.used_time,cr.orderNo");
		List<Map<String, Object>> list=DaoUtil.list(ds, 0, 0, sb.toString(), obj.toArray());
		for (Map<String, Object> map : list) {
			String ct = map.get("ticket_type").toString();
			String ts=map.get("ticket_state").toString();
			String sc=map.get("STORE_CODE").toString();
			String sn=map.get("STORE_NAME")!=null?map.get("STORE_NAME").toString():"";
			if(ts.equals("0")){
				ts="已领取";
				sc="";
				sn="";
			}else{
				ts="已核销";
			}
			map.put("tickettype", CompareTicketGroup.getTicketType(ct));
			map.put("ticket_state", ts);
			map.put("STORE_CODE", sc);
			map.put("STORE_NAME", sn);
		}
		
		
		
		LinkedHashMap<String, String> ExCorrespondingPro=new LinkedHashMap<String, String>();
		ExCorrespondingPro.put("门店编号", "STORE_CODE");
		ExCorrespondingPro.put("门店名称", "STORE_NAME");
		ExCorrespondingPro.put("优惠券类型", "tickettype");
		ExCorrespondingPro.put("优惠券名称", "title");
		ExCorrespondingPro.put("交易类型", "ticket_state");
		ExCorrespondingPro.put("订单号", "orderNo");
		ExCorrespondingPro.put("POS机终端号", "Terminal_Identity");
		ExCorrespondingPro.put("使用数量", "count");
		ExCorrespondingPro.put("交易时间", "used_time");
 		ExportEx EEX=new ExportEx();
 		response=EEX.exportExcel(list, response, ExCorrespondingPro, "优惠券核销流水详情");
		return response;
		
	}
}
