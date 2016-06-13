package com.chinainpay.apps.util;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;
import javax.xml.parsers.ParserConfigurationException;

import net.javabone.common.util.DaoUtil;
import net.javabone.common.util.XmlUtil;
import net.sf.json.JSONObject;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.chinainpay.mp.app.pagelet.axis.P;
import com.chinainpay.mp.mq.client.ServiceNotFoundException;
import com.chinainpay.mp.mq.client.ServiceTimeoutException;

public class DriveUtil {
	public static Map<String, Object> getDrive() throws ServiceNotFoundException, ServiceTimeoutException, SAXException, IOException, ParserConfigurationException, SQLException{
		String merchantno  = P.mp.getMerchant().getMerCode();
		List<Map<String, Object>> list =DaoUtil.list(P.ds("base"), 0, "SELECT * FROM T_CIPMP_MEMBER_CARD_SETUP WHERE MER_CODE=?", merchantno);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("terminal", list.get(0).get("TERMINAL"));
		map.put("mtoreNo", list.get(0).get("MTORENO"));
		return map;
	}

}
