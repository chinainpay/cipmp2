package com.chinainpay.apps.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;
import javax.xml.parsers.ParserConfigurationException;

import net.javabone.common.util.DaoUtil;
import net.javabone.common.util.XmlUtil;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.chinainpay.apps.ticket.util.CompareTicketGroup;
import com.chinainpay.mp.app.pagelet.axis.P;
import com.chinainpay.mp.app.plus.Services;
import com.chinainpay.mp.app.plus.WeixinPush;

public class App implements Services {
	private DataSource ds = P.ds("base");
	
	@Override
	public Class<?> getServicesClass() {
		return CouponServices.class;
	}

	public String getTicketMessage(String eventKey){
		return null;
	}

}