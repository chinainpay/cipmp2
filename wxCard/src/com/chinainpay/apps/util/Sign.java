package com.chinainpay.apps.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import com.chinainpay.apps.util.MD5Util;
import com.chinainpay.mp.app.pagelet.axis.P;

public class Sign {
	public static void main(String[] args) {
		String jsapi_ticket = "jsapi_ticket";

		// 注意 URL 一定要动态获取，不能 hardcode
		String url = "http://example.com";
		Map<String, String> ret = sign(jsapi_ticket, url);
		for (Map.Entry<String, String> entry : ret.entrySet()) {
			System.out.println(entry.getKey() + ", " + entry.getValue());
		}
	};
	/**
	 * js_ticket签名
	 * @param jsapi_ticket
	 * @param url
	 * @return
	 */
	public static Map<String, String> sign(String jsapi_ticket, String url) {
		Map<String, String> ret = new HashMap<String, String>();
		String string1;
		String signature = "";
		
		String nonce_str=JsApiList.create_nonce_str();
		String timestamp=JsApiList.create_timestamp();
		// 注意这里参数名必须全部小写，且必须有序
		string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str
				+ "&timestamp=" + timestamp + "&url=" + url;
		System.out.println(string1);

		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		ret.put("url", url);
		ret.put("jsapi_ticket", jsapi_ticket);
		ret.put("nonceStr", nonce_str);
		ret.put("timestamp", timestamp);
		ret.put("signature", signature);

		return ret;
	}

	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}
	
	
	
	/**
	 * 对账信息签名
	 */
	public static String createSign(TreeMap<String,String> param,String PATERNER_KEY){
		String string1=originalString(param);
		String stringSignTemp = string1 + "key="+PATERNER_KEY;
		P.log.d("签名调试输出："+stringSignTemp);
		String paysign=MD5Util.MD5Encode(stringSignTemp, "UTF-8").toUpperCase();
		return paysign;
	}
	
	/**
	 * wxpackage组装原始串
	 * @param treeMap
	 * @return
	 */
	public static String originalString(TreeMap<String,String> treeMap){
		Set<Entry<String, String>> entry = treeMap.entrySet();
		StringBuffer sb = new StringBuffer();
		for(Entry<String,String> obj : entry){
			String k = obj.getKey();
			String v = obj.getValue();
			if(v == null && v.equals(""))
				continue;
			sb.append(k+"="+v+"&");
		}
		return sb.toString();
	}
}
