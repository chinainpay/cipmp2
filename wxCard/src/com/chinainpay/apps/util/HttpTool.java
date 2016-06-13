package com.chinainpay.apps.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import com.chinainpay.mp.app.pagelet.axis.P;

import net.sf.json.JSONObject;

public class HttpTool {

	public HttpTool() {

	}

	public static String sendHttpRequest(String str_url) {
		StringBuffer sb = null;
		HttpURLConnection conn = null;
		try {
			URL url = new URL(str_url);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Accept-Charset", "UTF-8");
			conn.setRequestProperty("contentType", "UTF-8");

			BufferedOutputStream hurlBufOus = null;

			hurlBufOus = new BufferedOutputStream(conn.getOutputStream());
			hurlBufOus.flush();
			// hurlBufOus.close();

			// conn.connect();
			BufferedReader br = new BufferedReader(
					new java.io.InputStreamReader(conn.getInputStream(),
							"UTF-8"));
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

	public static String sendHttpRequest(String str_url, String parameter) {
		StringBuffer sb = null;
		HttpURLConnection conn = null;
		try {
			URL url = new URL(str_url);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Accept-Charset", "UTF-8");
			conn.setRequestProperty("contentType", "UTF-8");

			BufferedOutputStream hurlBufOus = null;

			hurlBufOus = new BufferedOutputStream(conn.getOutputStream());
			hurlBufOus.write(parameter.getBytes());
			hurlBufOus.flush();
			// hurlBufOus.close();

			// conn.connect();
			BufferedReader br = new BufferedReader(
					new java.io.InputStreamReader(conn.getInputStream(),
							"UTF-8"));
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

	public static String sendHttpGetRequest(String str_url) {
		StringBuffer sb = null;
		HttpURLConnection conn = null;
		try {
			URL url = new URL(str_url);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			// 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在
			// http正文内，因此需要设为true, 默认情况下是false;
			conn.setRequestProperty("Accept-Charset", "utf-8");
			conn.setRequestProperty("contentType", "utf-8");
			// hurlBufOus.close();

			// conn.connect();
			BufferedReader br = new BufferedReader(new java.io.InputStreamReader(conn.getInputStream(),"utf-8"));// 得到响应
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

	/**
	 * 设置超时时间的请求单位为毫秒
	 * 
	 * @param str_url
	 *            请求地址
	 * @param timeOut
	 *            超时时间
	 * @return
	 */
	public static String sendHttpGetRequest(String str_url, int timeOut) {
		// String szUrl = "http://www.ee2ee.com/";
		StringBuffer sb = null;
		HttpURLConnection conn = null;
		try {
			URL url = new URL(str_url);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(timeOut);
			conn.setReadTimeout(timeOut);
			// 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在
			// http正文内，因此需要设为true, 默认情况下是false;
			conn.setRequestProperty("Accept-Charset", "utf-8");
			conn.setRequestProperty("contentType", "utf-8");
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
		} catch (SocketTimeoutException e) {
			// 请求超时异常
			System.out.println("请求超时");
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
	public static String sendHttpsRequest(String str_url, String parameter) {
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

			BufferedOutputStream hurlBufOus = null;

			hurlBufOus = new BufferedOutputStream(conn.getOutputStream());
			hurlBufOus.write(parameter.getBytes());
			hurlBufOus.flush();
			// hurlBufOus.close();

			// conn.connect();
			BufferedReader br = new BufferedReader(new java.io.InputStreamReader(conn.getInputStream(),"utf-8"));// 得到响应
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

	/**
	 * 
	 * 实现HTTP Get方法，输入一个链接，将html作为字符串返回。若失败返回长度为0的字符串
	 * 
	 * @param sUrl
	 * 
	 * @return
	 * 
	 */
	public static String HttpGet(String sUrl) {

		String sContent = "";
		HttpURLConnection hc = null;

		try {
			// open a http conneciton
			URL url = new URL(sUrl);
			hc = (HttpURLConnection) url.openConnection();
			hc.setRequestMethod("POST");

			InputStream instream = hc.getInputStream();

			// write to buffer

			byte[] buffer = new byte[4096];
			byte[] sBuffer = new byte[4096];
			int nCount = 0;
			int nRead = 0;
			while (nRead > -1) {
				nRead = instream.read(buffer);
				// String temp = null;
				if (nRead > -1) {
					if ((nCount + nRead) >= sBuffer.length) {
						byte[] sNewBuffer = new byte[sBuffer.length * 2];
						System.arraycopy(sBuffer, 0, sNewBuffer, 0, nCount);
						System.arraycopy(buffer, 0, sNewBuffer, nCount, nRead);
						nCount += nRead;
						sBuffer = sNewBuffer;
					}
					else {
						System.arraycopy(buffer, 0, sBuffer, nCount, nRead);
						nCount += nRead;
					}
				}
			}
			sContent = new String(sBuffer, 0, nCount);
		}catch (Exception e) {
			// LogTool.debug(e);
			sContent = "";
		}finally {
			if (hc != null)
				hc.disconnect();
		}
		return sContent;
	}

	public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// /1、解决https请求的问题
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new X509CertificateManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);

			// /2、兼容GET、POST两种方式

			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);
			if ("GET".equalsIgnoreCase(requestMethod)) {
				httpUrlConn.connect();
			}
			
			// /3、兼容有数据提交、无数据提交两种情况
			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

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
		} catch (Exception e) {
		}
		return jsonObject;
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
            	if(entry.getKey().equals("attach") || entry.getKey().equals("body") || entry.getKey().equals("sign")){
            		buffer.append("<"+entry.getKey()+">");
            		buffer.append("<![CDATA["+entry.getValue()+"]]>");
            		buffer.append("</"+entry.getKey()+">");
            	}else{
            		buffer.append("<"+entry.getKey()+">");
            		buffer.append(entry.getValue());
            		buffer.append("</"+entry.getKey()+">");
            	}
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
            P.log.d("支付请求XML---------"+buffer);
            String buff=new String(buffer.toString().getBytes(),"UTf-8");
            out.print(buff);  
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