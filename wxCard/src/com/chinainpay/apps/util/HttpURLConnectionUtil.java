package com.chinainpay.apps.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;

/**
 * URLConnection 请求工具类
 * @author ZhangLei
 *
 */
public class HttpURLConnectionUtil {
	
	 static OutputStream os = null;
	 static BufferedOutputStream bos = null;
	 static InputStream is = null;
	 static InputStreamReader isr = null;
	 static BufferedReader br = null;

	// HTTP
	// ----------------------------------------------------------------------------------------------

	/**
	 * 发送http请求并接收响应
	 * @param requestUrl
	 * @param params
	 * @param requestMethod
	 * @param timeout
	 * @return
	 */
	public  String sendHttpRequest(String requestUrl, String params, String requestMethod, int timeout){
		System.out.println(params);
		StringBuffer sb = null;
		HttpURLConnection conn = null;
		try {
			URL url = new URL(requestUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(timeout);
			conn.setReadTimeout(timeout);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false); // 不使用缓存
			conn.setRequestMethod(requestMethod);
			conn.setRequestProperty("Charset", "UTF-8");
			conn.setRequestProperty("Content-Type", "text/html");
			if ("GET".equalsIgnoreCase(requestMethod))  
				conn.connect();  
            if (null != params && !"".equals(params)) { 
            	os = conn.getOutputStream();
				bos = new BufferedOutputStream(os);
				bos.write(params.getBytes("UTF-8"));
				bos.flush();
			}
			// 接收响应
			is = conn.getInputStream();
			isr = new InputStreamReader(is, "UTF-8");
			br = new BufferedReader(isr);
			sb = new StringBuffer("");
			String temp = "";
			while ((temp = br.readLine()) != null) {
				sb.append(temp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null)
				conn.disconnect();
			closeResources();
		}
		return sb.toString();
	}

	// HTTPS
	// ----------------------------------------------------------------------------------------------
	
	/**
	 * 获取HTTPS TrustManager数组
	 * @return
	 */
	private static  TrustManager [] getTrustManager(){
		TrustManager [] tm = null;
		try{
			tm = new TrustManager[] {
					new javax.net.ssl.X509TrustManager() {
						public java.security.cert.X509Certificate[] getAcceptedIssuers() {
							return null;
						}
						
						public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
						}
						
						public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
						}
					} 
				};
		}catch(Exception e){
			e.printStackTrace();
		}
		return tm;
	}
	
	/**
	 * 获取HTTPS HostnameVerifier对象
	 * @return
	 */
	private static  HostnameVerifier getHostnameVerifier(){
		HostnameVerifier hv = null;
		try{
			hv = new HostnameVerifier() {
				public boolean verify(String urlHostName, SSLSession session) {
					return urlHostName.equals(session.getPeerHost());
				}
			};
		}catch(Exception e){
			e.printStackTrace();
		}
		return hv;
	}
	
	/**
	 * https请求
	 * @param requestUrl 请求地址
	 * @param params 参数
	 * @param requestMethod 请求方式：GET; POST
	 * @param timeout 超时，默认30000
	 * @return
	 */
	public static  String sendHttpsRequest(String requestUrl, String params, String requestMethod, int timeout) {
		String result = null;
		StringBuffer sbf = new StringBuffer();
		HttpsURLConnection ucon = null;
		// 获取HTTPS TrustManager数组
		TrustManager [] tm = getTrustManager();
		// 获取HTTPS HostnameVerifier对象
		HostnameVerifier hv = getHostnameVerifier();
		try {
			SSLContext sc = null;
			sc = SSLContext.getInstance("SSL", "SunJSSE");
			sc.init(null, tm, new java.security.SecureRandom());
			URL url = new URL(requestUrl);
			ucon = (HttpsURLConnection) url.openConnection();
			ucon.setSSLSocketFactory(sc.getSocketFactory());
			ucon.setHostnameVerifier(hv);
			ucon.setConnectTimeout(timeout);
			ucon.setReadTimeout(timeout);
			ucon.setDoOutput(true);
			ucon.setDoInput(true);
			ucon.setUseCaches(false);
			ucon.setRequestMethod(requestMethod);
			ucon.setRequestProperty("Charset", "UTF-8");
			ucon.setRequestProperty("Content-Type", "text/html");
			if ("GET".equalsIgnoreCase(requestMethod))  
				ucon.connect();  
            if (null != params && !"".equals(params)) {  
            	os = ucon.getOutputStream();
				bos = new BufferedOutputStream(os);
				bos.write(params.getBytes("UTF-8"));
				bos.flush();
            }
			// 接收响应
			int statuscode = ucon.getResponseCode();
			String statusmsg = ucon.getResponseMessage();
			System.out.println(statuscode +" : "+ statusmsg);
			// 判断服务器是否正确响应
			if (statuscode == 200) {
				is = ucon.getInputStream();
				isr = new InputStreamReader(is, "UTF-8");
				br = new BufferedReader(isr);
				String temp = "";
				while ((temp = br.readLine()) != null) {
					sbf.append(temp);
				}
				result = sbf.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(ucon != null){
				ucon.disconnect();
			}
			closeResources();
		}
		return result;
	}
	
	
	/**
	 * 通过链接，返回html页面源码。若失败返回长度为0的字符串
	 * 
	 * @param sUrl
	 * @return
	 */
	public  String HttpGetPageSource (String sUrl) {
		String sContent = "";
		HttpURLConnection hc = null;
		try {
			// 设置并打开连接
			URL url = new URL(sUrl);
			hc = (HttpURLConnection) url.openConnection();
			hc.setRequestMethod("POST");
			is = hc.getInputStream();
			// 写入缓存
			byte[] buffer = new byte[4096];
			byte[] sBuffer = new byte[4096];
			int nCount = 0;
			int nRead = 0;
			while (nRead > -1) {
				nRead = is.read(buffer);
				if (nRead > -1) {
					if ((nCount + nRead) >= sBuffer.length) {
						byte[] sNewBuffer = new byte[sBuffer.length * 2];
						System.arraycopy(sBuffer, 0, sNewBuffer, 0, nCount);
						System.arraycopy(buffer, 0, sNewBuffer, nCount, nRead);
						nCount += nRead;
						sBuffer = sNewBuffer;
					} else {
						System.arraycopy(buffer, 0, sBuffer, nCount, nRead);
						nCount += nRead;
					}
				}
			}
			sContent = new String(sBuffer, 0, nCount);
		} catch (Exception e) {
			sContent = "";
		} finally {
			if (hc != null) {
				hc.disconnect();
			}
			closeResources();
		}
		return sContent;
	}
	
	/**
	 * 关闭资源
	 */
	public static  void closeResources() {
		try {
			if (bos != null) {
				bos.close();
				bos = null;
			}
			if (os != null) {
				os.close();
				os = null;
			}
			
			if (br != null) {
				br.close();
				br = null;
			}
			if (isr != null) {
				isr.close();
				isr = null;
			}
			if (is != null) {
				is.close();
				is = null;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 测试
	 * @param args
	 */
	public  void main(String[] args) {
		
	}

}