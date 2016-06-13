package com.chinainpay.apps.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;

import net.sf.json.JSONObject;

public class HttpsTool {

	public static String sendHttpRequest(String str_url, String parameter) {
		StringBuffer sb = null;
		HttpURLConnection conn = null;
		try {
			URL url = new URL(str_url);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			// 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在
			// http正文内，因此需要设为true, 默认情况下是false;
			conn.setDoOutput(true);
			// Post 请求不能使用缓存
			conn.setUseCaches(false);
			// 设定请求的方法为"POST"，默认是GET
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Accept-Charset", "utf-8");
			conn.setRequestProperty("contentType", "text/html");
			if (parameter != null) {
				BufferedOutputStream hurlBufOus = null;

				hurlBufOus = new BufferedOutputStream(conn.getOutputStream());
				hurlBufOus.write(parameter.toString().getBytes());
				hurlBufOus.flush();
			}
			// hurlBufOus.close();

			// conn.connect();
			BufferedReader br = new BufferedReader(
					new java.io.InputStreamReader(conn.getInputStream(),
							"utf-8"));// 得到响应
			sb = new StringBuffer("");
			String tmp = "";
			while ((tmp = br.readLine()) != null) {
				sb.append(tmp);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// br.close();
		finally {
			if (conn != null)
				conn.disconnect();
		}
		return sb.toString();
	}

	public static String sendHttpsRequest(String url, String allParams) {
		SSLContext sc = null;
		TrustManager[] trustAllCerts = new TrustManager[] { new javax.net.ssl.X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(
					java.security.cert.X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(
					java.security.cert.X509Certificate[] certs, String authType) {
			}
		} };

		try {
			sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection
					.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
		}

		HostnameVerifier hv = new HostnameVerifier() {
			public boolean verify(String urlHostName, SSLSession session) {
				return urlHostName.equals(session.getPeerHost());
			}
		};
		HttpsURLConnection.setDefaultHostnameVerifier(hv);

		try {
			URL t_url = new URL(url);
			HttpsURLConnection ucon = (HttpsURLConnection) t_url
					.openConnection();
			ucon.setConnectTimeout(30000);
			ucon.setReadTimeout(30000);
			ucon.setDoOutput(true);
			ucon.setDoInput(true);
			ucon.setUseCaches(false);
			ucon.setRequestMethod("GET");
			ucon.setRequestProperty("Accept-Charset", "utf-8");
			ucon.setRequestProperty("contentType", "text/html");
			ucon.setRequestProperty("pageEncoding", "utf-8");
			ucon.connect();
			if (allParams != null) {
				BufferedOutputStream bos = new BufferedOutputStream(
						ucon.getOutputStream());
				bos.write(allParams.getBytes("UTF-8"));
				bos.flush();
				bos.close();
			}
			// 接收响应
			int statuscode = ucon.getResponseCode();
			String statusmsg = ucon.getResponseMessage();
			System.out.println(statuscode + statusmsg);
			// 判断服务器是否正确响应
			String reJson = null;
			if (statuscode == 200) {
				InputStream is = ucon.getInputStream();
				InputStreamReader r = new InputStreamReader(is, "utf-8");
				BufferedReader br = new BufferedReader(r);
				// 读取响应并转为JSON
				reJson = br.readLine();
				System.out.println(new String(reJson.getBytes("GBK"), "utf-8"));

				// 关闭流
				br.close();
				r.close();
				is.close();
			}
			return reJson;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 微信发送消息专用链接
	 * 
	 * @param url
	 *            请求地址
	 * @param allParams
	 *            参数列表 没有可为空
	 * @param timeout
	 *            超时时间
	 * @return
	 */
	public static String sendHttpsRequest(String url, String allParams,
			int timeout) {
		SSLContext sc = null;
		TrustManager[] trustAllCerts = new TrustManager[] { new javax.net.ssl.X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(
					java.security.cert.X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(
					java.security.cert.X509Certificate[] certs, String authType) {
			}
		} };

		try {
			sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection
					.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
		}

		HostnameVerifier hv = new HostnameVerifier() {
			public boolean verify(String urlHostName, SSLSession session) {
				return urlHostName.equals(session.getPeerHost());
			}
		};
		HttpsURLConnection.setDefaultHostnameVerifier(hv);

		try {
			URL t_url = new URL(url);
			HttpsURLConnection ucon = (HttpsURLConnection) t_url
					.openConnection();
			ucon.setConnectTimeout(timeout);
			ucon.setReadTimeout(timeout);
			ucon.setDoOutput(true);
			ucon.setDoInput(true);
			ucon.setUseCaches(false);
			ucon.setRequestMethod("GET");
			ucon.setRequestProperty("Accept-Charset", "utf-8");
			ucon.setRequestProperty("contentType", "text/html");
			ucon.setRequestProperty("pageEncoding", "utf-8");
			ucon.connect();
			if (allParams != null) {
				BufferedOutputStream bos = new BufferedOutputStream(
						ucon.getOutputStream());
				bos.write(allParams.getBytes());
				bos.flush();
				bos.close();
			}
			// 接收响应
			int statuscode = ucon.getResponseCode();
			String statusmsg = ucon.getResponseMessage();
			System.out.println(statuscode + statusmsg);
			// 判断服务器是否正确响应
			String reJson = null;
			if (statuscode == 200) {
				InputStream is = ucon.getInputStream();
				InputStreamReader r = new InputStreamReader(is, "utf-8");
				BufferedReader br = new BufferedReader(r);
				// 读取响应并转为JSON
				reJson = br.readLine();
				System.out.println(new String(reJson.getBytes("GBK"), "utf-8"));

				// 关闭流
				br.close();
				r.close();
				is.close();
			}
			return reJson;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String sendHttpRequestJson(String str_url, String text,
			String openid) {
		try {
			// 创建连接
			URL url = new URL(str_url);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");

			connection.connect();

			// POST请求
			DataOutputStream out = new DataOutputStream(
					connection.getOutputStream());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("content", text);
			Map<String, Object> jsonmap = new HashMap<String, Object>();
			jsonmap.put("touser", openid);
			jsonmap.put("msgtype", "text");
			jsonmap.put("text", map);
			JSONObject json = JSONObject.fromObject(jsonmap);

			out.writeBytes(json.toString());
			out.flush();
			out.close();

			// 读取响应
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String lines;
			StringBuffer sb = new StringBuffer("");
			while ((lines = reader.readLine()) != null) {
				lines = new String(lines.getBytes(), "utf-8");
				sb.append(lines);
			}
			System.out.println(sb);
			reader.close();
			// 断开连接
			connection.disconnect();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String sendHttpsPOSTRequest(String url, String allParams) {
		SSLContext sc = null;
		TrustManager[] trustAllCerts = new TrustManager[] { new javax.net.ssl.X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(
					java.security.cert.X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(
					java.security.cert.X509Certificate[] certs, String authType) {
			}
		} };

		try {
			sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection
					.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
		}

		HostnameVerifier hv = new HostnameVerifier() {
			public boolean verify(String urlHostName, SSLSession session) {
				return urlHostName.equals(session.getPeerHost());
			}
		};
		HttpsURLConnection.setDefaultHostnameVerifier(hv);

		try {
			URL t_url = new URL(url);
			HttpsURLConnection ucon = (HttpsURLConnection) t_url
					.openConnection();
			ucon.setConnectTimeout(30000);
			ucon.setReadTimeout(30000);
			ucon.setDoOutput(true);
			ucon.setDoInput(true);
			ucon.setUseCaches(false);
			ucon.setRequestMethod("POST");
			ucon.setRequestProperty("Accept-Charset", "utf-8");
			ucon.setRequestProperty("contentType", "text/html");
			ucon.setRequestProperty("pageEncoding", "utf-8");
			ucon.connect();
			if (allParams != null) {
				BufferedOutputStream bos = new BufferedOutputStream(
						ucon.getOutputStream());
				bos.write(allParams.getBytes());
				bos.flush();
				bos.close();
			}
			// 接收响应
			int statuscode = ucon.getResponseCode();
			String statusmsg = ucon.getResponseMessage();
			System.out.println(statuscode + statusmsg);
			// 判断服务器是否正确响应
			String reJson = null;
			if (statuscode == 200) {
				InputStream is = ucon.getInputStream();
				InputStreamReader r = new InputStreamReader(is, "utf-8");
				BufferedReader br = new BufferedReader(r);
				// 读取响应并转为JSON
				reJson = br.readLine();
				System.out.println(new String(reJson.getBytes("GBK"), "utf-8"));

				// 关闭流
				br.close();
				r.close();
				is.close();
			}
			return reJson;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	 /**  
     * POST请求，Map形式数据  
     * @param url 请求地址  
     * @param param 请求数据  
     * @param charset 编码方式  
     */  
    public static String sendPost(String url, Map<String, String> param,  
            String charset) {  
  
        StringBuffer buffer = new StringBuffer(); 
        buffer.append("<xml>");
        if (param != null && !param.isEmpty()) {  
            for (Map.Entry<String, String> entry : param.entrySet()) {  
				buffer.append("<" + entry.getKey() + ">");
				buffer.append(entry.getValue());
				buffer.append("</"+entry.getKey()+">");
            }  
        }  
        buffer.append("</xml>");
        PrintWriter out = null;  
        BufferedReader in = null;  
        String result = "";  
        try {  
            URL realUrl = new URL(url);  
            // 打开和URL之间的连接  
            URLConnection conn = realUrl.openConnection();  
            // 设置通用的请求属性  
            conn.setRequestProperty("accept", "*/*");  
            conn.setRequestProperty("connection", "Keep-Alive");  
            conn.setRequestProperty("user-agent",  
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");  
            // 发送POST请求必须设置如下两行  
            conn.setDoOutput(true);  
            conn.setDoInput(true);  
            // 获取URLConnection对象对应的输出流  
            out = new PrintWriter(conn.getOutputStream());  
            // 发送请求参数  
            out.print(buffer);  
            // flush输出流的缓冲  
            out.flush();  
            // 定义BufferedReader输入流来读取URL的响应  
            in = new BufferedReader(new InputStreamReader(  
                    conn.getInputStream(), charset));  
            String line;  
            while ((line = in.readLine()) != null) {  
                result += line;  
            }  
        } catch (Exception e) {  
            System.out.println("发送 POST 请求出现异常！" + e);  
            e.printStackTrace();  
        }  
        // 使用finally块来关闭输出流、输入流  
        finally {  
            try {  
                if (out != null) {  
                    out.close();  
                }  
                if (in != null) {  
                    in.close();  
                }  
            } catch (IOException ex) {  
                ex.printStackTrace();  
            }  
        }  
        return result;  
    }  
}
