package com.chinainpay.apps.membercard;

import java.rmi.RemoteException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import net.javabone.common.crypto.DES;
import net.javabone.common.util.DaoUtil;
import net.javabone.common.util.RegexpUtils;
import net.javabone.common.web.JsonUtil;

import com.chinainpay.apps.util.MacUtil;
import com.chinainpay.apps.util.MD5Util;
import com.chinainpay.apps.pool.Flow;
import com.chinainpay.apps.pool.impl.FlowImpl;
import com.chinainpay.apps.util.ByteUtil;
import com.chinainpay.apps.util.RequestUtils;
import com.chinainpay.apps.util.DriveUtil;
import com.chinainpay.mp.app.pagelet.Pagelet;
import com.chinainpay.mp.app.pagelet.axis.P;
import com.chinainpay.mp.mq.client.ServiceNotFoundException;
import com.chinainpay.mp.mq.client.ServiceTimeoutException;

public class Verification implements Pagelet {
	/**
	 * 会员登录验证
	 */
	private static final long serialVersionUID = -6853425754293707765L;
	private DataSource ds = P.ds("base");

	@Execute("访问")
	@Pagelet.Purview()
	public StringBuffer execute() throws RemoteException, SQLException, ServiceNotFoundException, ServiceTimeoutException {
		HttpServletRequest request = P.getRequest();
		Map<String, Object> rMap = new HashMap<String, Object>();
		String phone = request.getParameter("phone");
		String key = "11111111111111112345678912345678";
		if(phone==null){
			rMap.put("code", "01");
			rMap.put("msg", "手机号不能为空");
			return new StringBuffer(JsonUtil.encoder((Map<?, ?>) rMap));
		}
		if(request.getParameter("encodePwd")==null){
			rMap.put("code", "02");
			rMap.put("msg", "密码不能为空");
			return new StringBuffer(JsonUtil.encoder((Map<?, ?>) rMap));
		}
		String encodePwd  = request.getParameter("encodePwd").toString().trim();
		String merNo = P.mp.getMerchant().getMerCode();
		String sql = "SELECT * FROM T_CIPMP_MEMBER WHERE MEMB_PHONE= ?";
		List<Map<String, Object>> list = DaoUtil.list(ds, 0, sql, phone);
		if(list.size()==0){
			rMap.put("code", "02");
			rMap.put("msg", "无法识别用户信息");
			return new StringBuffer(JsonUtil.encoder((Map<?, ?>) rMap));
		}
		if(!merNo.equals(list.get(0).get("MER_CODE"))){
			rMap.put("code", "05");
			rMap.put("msg", "该商户无权操作此卡");
			return new StringBuffer(JsonUtil.encoder((Map<?, ?>) rMap));
		}
		while (phone.length()!=16) {
			phone += "0";
		}
		String mtoreNo = null;
		String terminal = null;
		try {
			mtoreNo = DriveUtil.getDrive().get("mtoreNo").toString();
			terminal = DriveUtil.getDrive().get("terminal").toString();
		} catch (Exception e1) {
			rMap.put("code", "01");
			rMap.put("msg", "驱动错误");
			return new StringBuffer(JsonUtil.encoder((Map<?, ?>) rMap));
		}
		// ===========解密验证=========================
		byte[] decodePwd =null;
		try {
			byte[] result2 = DES.encryptDES(
					ByteUtil.hexToBytes(phone.substring(0, 16)),
					ByteUtil.hexToBytes(key.substring(0, 16)));
			decodePwd = DES.decryptDES(result2, ByteUtil.hexToBytes(encodePwd));
		} catch (GeneralSecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String pwd2 = new String(decodePwd);

		try {
			Flow flow = new FlowImpl();	
			List<Map<String, Object>> result =flow.cardInfo(list.get(0).get("WX_CARD_NUM").toString(), list.get(0).get("WX_CARD_NUM").toString(), "Y", pwd2.substring(0,6), "N", "1", "10",
					mtoreNo, "", "", terminal);
			P.log.d(result);
			if(result!=null){
				rMap.put("code", "00");
				rMap.put("msg", "校验成功");
				return new StringBuffer(JsonUtil.encoder((Map<?, ?>) rMap));
			}
		} catch (Exception e) {
			rMap.put("code", "03");
			rMap.put("msg", "服务器异常");
			return new StringBuffer(JsonUtil.encoder((Map<?, ?>) rMap));
		}
		
		rMap.put("code", "01");
		rMap.put("msg", "驱动错误");
		return new StringBuffer(JsonUtil.encoder((Map<?, ?>) rMap));
		
	}

}
