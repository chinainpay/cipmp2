package com.chinainpay.apps.membercard.util;

import java.util.Random;

import com.chinainpay.mp.mq.client.ServiceNotFoundException;
import com.chinainpay.mp.mq.client.ServiceTimeoutException;
import com.chinainpay.apps.service.CouponServices;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 生成卡券用户领取的code
 * @author ying
 *
 */
public class CodeUtil {
	
	//生成随机数字  
	/**
	 * 调用实例
	 * getStringRandom(System.currentTimeMillis(),9)  
	 * 生成22为数字
	 * @param startStr 后面拼接的值
	 * @param length   编号长度
	 * @return
	 */
    public static String getStringRandom(Object startStr,int length) {  
    	System.out.println(startStr);
    	Random ran=new Random();
    	StringBuffer returnStr=new StringBuffer();
       for (int i = 0; i < length; i++) {
    	   returnStr.append(ran.nextInt(9));
       }
       System.out.println(returnStr);
        return returnStr.append(startStr).toString();  
    } 
    
    /**
     * 根据数据库色值去颜色值
     * @param color
     * @return
     */
    public static String getColor(String color){
    	String returnColor="#55bd47";
    	String colorList;
		try {
			colorList = CouponServices.getTicketColor();
		} catch (Exception e) {
			colorList = "[{\"name\":\"Color010\",\"value\":\"#55bd47\"},{\"name\":\"Color020\",\"value\":\"#10ad61\"},{\"name\":\"Color030\",\"value\":\"#35a4de\"},{\"name\":\"Color040\",\"value\":\"#3d78da\"},{\"name\":\"Color050\",\"value\":\"#9058cb\"},{\"name\":\"Color060\",\"value\":\"#de9c33\"},{\"name\":\"Color070\",\"value\":\"#ebac16\"},{\"name\":\"Color080\",\"value\":\"#f9861f\"},{\"name\":\"Color081\",\"value\":\"#f08500\"},{\"name\":\"Color090\",\"value\":\"#e75735\"},{\"name\":\"Color100\",\"value\":\"#d54036\"},{\"name\":\"Color101\",\"value\":\" #cf3e36\"}]";
		} 
    	JSONArray array=JSONArray.fromObject(colorList);
    	for (int i = 0; i < array.size(); i++) {
    		String obj=array.get(i).toString();
    		JSONObject objJson=JSONObject.fromObject(obj);
    		if(color.equals(objJson.get("name"))){
    			returnColor=objJson.get("value").toString();
    		}
		}
    	return returnColor;
    }
    
}
