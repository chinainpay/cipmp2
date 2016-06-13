package com.chinainpay.apps.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import com.chinainpay.mp.app.pagelet.axis.P;
import com.chinainpay.mp.mq.client.ServiceNotFoundException;
import com.chinainpay.mp.mq.client.ServiceTimeoutException;


import net.sf.json.JSONObject;

public class RequestUtils {

	public static String copyOfRequestUtils(String openid,String merNo,String appid,String secret,String content) throws ServiceNotFoundException, ServiceTimeoutException {
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+secret;
		// 获取access token
		String access_token = P.mp.getService("Weixin", 60000, "getAccessTokey").getValue()
				.toString();
		P.log.d("openid:" + openid);
		P.log.d("access_token:" + access_token);

		url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="
				+ access_token;
		// 发送客服消息
		String msg = "{\"touser\": \""+openid+"\",\"msgtype\": \"text\", \"text\": {\"content\": \""+content+"\"}}";
		JSONObject json = httpRequest(url, "POST", msg);
		String errcode = json.get("errcode").toString();
		P.log.d("errcode:" + errcode);
		String errmsg = json.get("errmsg").toString();
		P.log.d("errmsg:" + errmsg);
		return errcode;
	}

	/**
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl
	 *            请求地址
	 * 
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * 
	 * @param outputStr
	 *            提交的数据
	 * 
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */

	public static JSONObject httpRequest(String requestUrl,
			String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new X509CertificateManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url
					.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);
			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();
			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (ConnectException ce) {
			System.out.println("Weixin server connection timed out.");
			ce.printStackTrace();
		} catch (Exception e) {
			System.out.println("https request error:{}");
			e.printStackTrace();
		}
		return jsonObject;
	}
}
