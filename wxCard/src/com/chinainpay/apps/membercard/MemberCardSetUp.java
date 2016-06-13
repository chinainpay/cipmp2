package com.chinainpay.apps.membercard;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import net.javabone.common.util.DaoUtil;
import net.javabone.common.util.TransactionDataSource;

import org.json.simple.JSONArray;

import com.chinainpay.mp.app.pagelet.Pagelet;
import com.chinainpay.mp.app.pagelet.axis.P;
import com.chinainpay.mp.mq.client.ServiceNotFoundException;
import com.chinainpay.mp.mq.client.ServiceTimeoutException;

/**
 * 会员系统设置
 * 
 * @author ying
 * 
 */
public class MemberCardSetUp implements Pagelet {
	private static final long serialVersionUID = 1L;
	private DataSource ds = P.ds("base");

	@Execute("访问")
	@Pagelet.Purview()
	public void execute() throws SQLException {
		String merchantNo = P.mp.getMerchant().getMerCode();
		Map<String, Object> mp = DaoUtil.row(ds,
				"SELECT * FROM T_CIPMP_MEMBER_CARD_SETUP WHERE MER_CODE=? ",
				merchantNo);
		P.setAttribute("pojo", mp);
		List<Map<String, Object>> mpList = DaoUtil.list(ds, 0,
				"SELECT * FROM T_CIPMP_MEMBER_DATA_SET WHERE MER_CODE=? ",
				merchantNo);
		P.setAttribute("memberInfo", JSONArray.toJSONString(mpList));
		String sql = "SELECT * FROM T_CIPMP_CARD where VIRTUAL_CARD = '0' and MER_CODE = ? group by card_type ";
		P.setAttribute("cardTypeList", DaoUtil.list(ds, 0, sql, merchantNo));
	}

	/**
	 * 会员系统设置-->会员卡设置
	 * 
	 * @param pojo
	 * @throws SQLException
	 * @throws ServiceTimeoutException 
	 * @throws ServiceNotFoundException 
	 */
	@Execute("会员卡设置")
	@Pagelet.Purview("execute")
	public void memberCardSetUp(
			@RequestParam("pojo") HashMap<String, String> pojo,
			@RequestParam("GroupCard") String groupCard,
			@RequestParam("GroupCard2") String GroupCard2,
			@RequestParam("file") File file) throws SQLException, ServiceNotFoundException, ServiceTimeoutException {
		String fileImage="";
		//------------支付参数--------------------------
		String TERMINAL = pojo.get("TERMINAL") == null ? "" : pojo
				.get("TERMINAL").toString().trim();
		String MTORENO = pojo.get("MTORENO") == null ? "" : pojo
				.get("MTORENO").toString().trim();
		//------------------------------------
		if(file!=null){
			String fileName=file.getName();
			P.log.d(fileName);
			String fileType=fileName.substring(fileName.lastIndexOf(".", fileName.lastIndexOf(".")-1), fileName.lastIndexOf("."));
			P.log.d("图片类型"+fileType);
			fileImage=P.mp.getService("Base", "updateFile",fileType, file).getValue().toString();
			P.log.d("调用接口返回地址================================" + fileImage);
		}
		String PERSONALCARDPARAM = pojo.get("PERSONALCARD") == null ? "" : pojo
				.get("PERSONALCARD").toString().trim();
		String PERSONALCARD = PERSONALCARDPARAM.equals("on") ? "0" : "1";
		P.log.d("个人开卡是否激活======" + PERSONALCARDPARAM);

		groupCard = groupCard == null ? "" : groupCard.trim();
		String TREAMCARD = groupCard.equals("on") ? "" : "2";
		P.log.d("是否允许团体售卡======" + groupCard);

		if (!TREAMCARD.equals("2")) {
			String TREAMCARDPARAM = pojo.get("TREAMCARD") == null ? "" : pojo
					.get("TREAMCARD").toString().trim();
			TREAMCARD = TREAMCARDPARAM.equals("on") ? "0" : "1";
			P.log.d("团队售卡是否激活======" + TREAMCARDPARAM);
		}
		GroupCard2 = GroupCard2 == null ? "" : GroupCard2.trim();
		String TREAMCARD2 = GroupCard2.equals("on") ? "0" : "1";
		P.log.d("是否允许虚拟卡卡======" + TREAMCARD2);
		String VIRTUALCARD_TYPE = pojo.get("v1") == null ? "0" : pojo.get("v1")
				.toString().trim();
		String VIRTUALCARD_PRIVILEGE = pojo.get("v2") == null ? "" : pojo
				.get("v2").toString().trim();
		String GATEWAY_URL = pojo.get("http") == null ? "" : pojo
				.get("http").toString().trim();
		String merchantNo = P.mp.getMerchant().getMerCode();
		if(fileImage.equals("")){
			String updSql = "UPDATE T_CIPMP_MEMBER_CARD_SETUP SET  ALLOW_ACTIVATION_VIRTUALCARD=?,VIRTUALCARD_TYPE=?,VIRTUALCARD_PRIVILEGE=?, ALLOW_ACTIVATION_PERSONALCARD=? , ALLOW_ACTIVATION_TREAMCARD=?, GATEWAY_URL = ?,TERMINAL=?,MTORENO=? WHERE MER_CODE=?";
			DaoUtil.execute(ds, updSql, TREAMCARD2, VIRTUALCARD_TYPE,
						VIRTUALCARD_PRIVILEGE, PERSONALCARD, TREAMCARD, GATEWAY_URL,TERMINAL,MTORENO,merchantNo);
		}else{
			String updSql = "UPDATE T_CIPMP_MEMBER_CARD_SETUP SET ALLOW_ACTIVATION_VIRTUALCARD=?,VIRTUALCARD_TYPE=?,VIRTUALCARD_PRIVILEGE=?, ALLOW_ACTIVATION_PERSONALCARD=? , ALLOW_ACTIVATION_TREAMCARD=?,IMAGE_ICON=?,GATEWAY_URL = ?,TERMINAL=?,MTORENO=? WHERE MER_CODE=?";
			DaoUtil.execute(ds, updSql, TREAMCARD2, VIRTUALCARD_TYPE,
						VIRTUALCARD_PRIVILEGE, PERSONALCARD, TREAMCARD, fileImage,GATEWAY_URL,TERMINAL,MTORENO,merchantNo);
		}
		execute();
	}


	/**
	 * 会员系统设置-->会员资料设置
	 * 
	 * @param pojo
	 * @throws SQLException
	 */
	@Execute("会员资料设置")
	@Pagelet.Purview("execute")
	public void memberSetUp(@RequestParam("pojo") HashMap<String, Object> pojo)
			throws SQLException {
		//P.log.d(pojo);
		TransactionDataSource tds = new TransactionDataSource(ds);
		String merchantNo = P.mp.getMerchant().getMerCode();
		try {
			// 先删除所有
			String delSql = "DELETE FROM T_CIPMP_MEMBER_DATA_SET WHERE MER_CODE=? ";
			DaoUtil.execute(tds, delSql, merchantNo);
			if (pojo.size() != 0) {
				String[] PRO_NAME = pojo.get("PRO_NAME") instanceof String[] ? (String[]) pojo
						.get("PRO_NAME") : new String[] { (String) pojo
						.get("PRO_NAME") };
				String[] PROMPT_CONTENT = pojo.get("PROMPT_CONTENT") instanceof String[] ? (String[]) pojo
						.get("PROMPT_CONTENT") : new String[] { (String) pojo
						.get("PROMPT_CONTENT") };
				String[] PRO_TYPE = pojo.get("PRO_TYPE") instanceof String[] ? (String[]) pojo
						.get("PRO_TYPE") : new String[] { (String) pojo
						.get("PRO_TYPE") };
				for (int i = 0; i < PRO_NAME.length; i++) {
					String isRequired = "0";
					if (null == pojo.get("IS_REQUIRED" + (i + 1))) {
						isRequired = "1";
					}
					P.log.d(PRO_NAME[i] + ":" + isRequired);
					DaoUtil.execute(
							tds,
							"INSERT INTO T_CIPMP_MEMBER_DATA_SET VALUES (?,NULL,?,?,?,?);",
							merchantNo, PRO_NAME[i].toString().trim(),
							PROMPT_CONTENT[i].toString().trim(), PRO_TYPE[i].toString().trim(),
							isRequired);

				}
			}
			P.setAttribute("message", tds.transactionCommitOrRollback() ? "OK"
					: "失败");
		} catch (Exception e) {
			e.printStackTrace();
			tds.transactionRollback();
			tds.close();
		} finally {
			execute();
		}
	}

	@Execute("上传虚拟卡图片")
	@Pagelet.Purview("execute")
	public void uploadImageFile(@RequestParam("file") File file) {
		P.log.d(file);
	}
}
