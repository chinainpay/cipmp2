package com.chinainpay.apps.ticket.util;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.chinainpay.apps.util.HttpURLConnectionUtil;
import com.chinainpay.apps.util.HttpsTool;
import com.chinainpay.mp.app.pagelet.axis.P;
import com.chinainpay.mp.mq.client.ServiceNotFoundException;
import com.chinainpay.mp.mq.client.ServiceTimeoutException;

/**
 * 二维码工具类
 * 
 * @author ying
 * 
 */
public class CreateTwoTicket {
	private static String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=ACCESS_TOKEN";//生成新的二维码
	private static String getImageByTicke="https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET";//根据Ticket获得二维码
	private static String delTicketUrl="https://api.weixin.qq.com/card/delete?access_token=TOKEN";//删除微信上的卡券

	/**
	 * 临时二维码
	 * 未测试过
	 * @param param
	 * @return
	 */
	public static String QR_SCENE(String param, String accessToken,int scene_id) {
		url = url.replaceAll("ACCESS_TOKEN", accessToken);
		JSONObject sceneObj = new JSONObject();
		sceneObj.put("scene_id", scene_id);

		JSONObject actioninfoObj = new JSONObject();
		actioninfoObj.put("scene", sceneObj);// 二维码详细信息
		JSONObject lastJsonObj=new JSONObject();
		lastJsonObj.put("action_name", "QR_SCENE");
		lastJsonObj.put("expire_seconds", 604800);
		lastJsonObj.put("action_info", actioninfoObj);
		
		String result=HttpsTool.sendHttpRequest(url, lastJsonObj.toString());
		P.log.d(">>>>>>>>>>>最终的结果："+result);
		return result;
	}

	/**
	 * 永久二维码
	 * 
	 * @param param
	 * @return
	 */
	public static String QR_LIMIT_SCENE(String accessToken,int scene_id) {
		url = url.replaceAll("ACCESS_TOKEN", accessToken);
		P.log.d(">>"+accessToken);
		JSONObject sceneObj = new JSONObject();
		sceneObj.put("scene_id", scene_id);
		//sceneObj.put("scene_str", param);

		JSONObject actioninfoObj = new JSONObject();
		actioninfoObj.put("scene", sceneObj);// 二维码详细信息

		JSONObject lastJsonObj=new JSONObject();
		lastJsonObj.put("action_name", "QR_LIMIT_SCENE");
		lastJsonObj.put("action_info", actioninfoObj);
		P.log.d("》》》》》》》》》》》》》》获取二维码图片地址："+url);
		P.log.d("》》》》》》》》》》》》》》获取二维码图片参数："+lastJsonObj);
		String result=new HttpURLConnectionUtil().sendHttpRequest(url, lastJsonObj.toString(), "POST", 20000);
		
		P.log.d(">>>>>>>>>>>最终的结果："+result);
		return result;
	}
	
	/**
	 * 字符串形式永久二维码
	 * 
	 * @param param
	 * @return
	 * @throws ServiceTimeoutException 
	 * @throws ServiceNotFoundException 
	 */
	public static String QR_LIMIT_STR_SCENE(String param) throws ServiceNotFoundException, ServiceTimeoutException {
		JSONObject sceneObj = new JSONObject();
		sceneObj.put("scene_str", param);
		
		JSONObject actioninfoObj = new JSONObject();
		actioninfoObj.put("scene", sceneObj);// 二维码详细信息

		JSONObject lastJsonObj=new JSONObject();
		lastJsonObj.put("action_name", "QR_LIMIT_STR_SCENE");
		lastJsonObj.put("action_info", actioninfoObj);
		P.log.d("》》》》》》》》》》》》》》获取二维码图片地址："+url);
		P.log.d("》》》》》》》》》》》》》》获取二维码图片参数："+lastJsonObj);
		String result=new HttpURLConnectionUtil().sendHttpRequest(url.replaceAll("ACCESS_TOKEN", P.mp.getService("Weixin", 60000, "getAccessTokey").getValue()
				  .toString()), lastJsonObj.toString(), "POST", 20000);
		
		P.log.d(">>>>>>>>>>>最终的结果："+result);
		return result;
	}

	
	/**
	 * 根据地址得到文件流
	 */
	public static void getImageByTicket(String ticket,HttpServletResponse response){
		JSONObject joTicket = new JSONObject();
		joTicket.put("ticket", ticket);
		String qrcodeImg = getImageByTicke.replace("TICKET", ticket);

		try {
			URL u = new URL(qrcodeImg);
			try {
				DataInputStream dis = new DataInputStream(u.openStream());
				ServletOutputStream out = response.getOutputStream();
				int length;
				byte[] buffer = new byte[1024];
				while ((length = dis.read(buffer)) > 0) {
					out.write(buffer, 0, length);
				}
				dis.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}// 打开网络输入流

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除卡券
	 * @param card_id
	 * @throws ServiceNotFoundException
	 * @throws ServiceTimeoutException
	 */
	public static String delTicket(String card_id) throws ServiceNotFoundException, ServiceTimeoutException{
		delTicketUrl=delTicketUrl.replaceAll("TOKEN", P.mp.getService("Weixin", 60000, "getAccessTokey")
				.getValue().toString());
		JSONObject param=new JSONObject();
		param.put("card_id", card_id);
		String result=new HttpURLConnectionUtil().sendHttpRequest(delTicketUrl, param.toString(), "POST", 20000);
		return result;
	}
}
