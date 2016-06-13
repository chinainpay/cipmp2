package com.chinainpay.apps.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import net.javabone.common.util.ByteUtil;

public class MacUtil {

	private static final String  ght_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC4TO0oUkK8+utkeDke7oSyoNEsfLIwQs3iZn+NCeTgIbUzzTMqaOcXDcv0V4ryR8ALBs453eAQNX+6tRFoC5olh3NuT5DtWuxcNjApccMqbdOxPBNQV1GIS4Aj4cj+GtKN5m5DPWU1jBTJvJD6sH0QExsDtUbVdwJ8sLtksd2AXQIDAQAB";
	private static final String  private_key  = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAL+gra4ar2HF3XnBvkDHS9EeNz3DKucwXrON/9Kkibus0Zq/p9MyZcpP31VE5cBxzcI4YKEfqueLG7oTdbBSUgo/ZrDAq3bnLI1N2Q80YjmczDTpaTFFBbm/xXDj/KKe2fpNHweHs2MwAzM5TJC9womWbAnGNNkItCfwsonMdk6BAgMBAAECgYBXgiDOKRF+LMV/HKKtIlXVIV9IrR+ig8w7YtU4dDsRoVH2wR76q0Egqmjqn5Pr1gyIbQZzjeqV4ki/KLPN2Uns6Dwa8LvQpF7pPPltomlRb4CTL83kgt06o/MTYYYfjIpczBiNnPcEZa6SYE0BIZjjxhZjBiOH/bH/GI8VbszkIQJBAPrMJKTM7Z0akjLqZAn3ve5SvInPeBsPguJDbkf9I+1rHlsM3CycCBdBGDPR94MrsfhYQYvqGETA2pHSdcjGW8sCQQDDmlCZ3Hi6O3hv+Bb63Y0OVDiGL6/Z0HY44fS/OiGJLegjtL8ejwejpcZQR3fqO+uKJTj2jsoPwTz+Zz8TVo1jAkEAwuNGCmZQsweF2lj32iMRDZRvfCujamvYSlkZ5okYlRLraNeKdIPfaaqk0wfzbZPT3Wc/sCj+KNcwQaao7pbtlwJAWHL4TYrvoK7xuBgENC5o8XjOEoePyjvEqdYrOSmNLp3uoe7CmYvt1WdJ9NVMeUPdL5vDVWKeJl+oOBXx5Mm3pwJBAMxZWUmmiDR9CHc4+C8zqXP5FQmgXWSvobOSoEWNMFQffGvJo0UNtYpRgVMwbeI7Mjjr8YiLdlI8X1oQPIpfQpA=";
	public static TreeMap<String, String> encryptPwdOrNewPwd(
			TreeMap<String, String> paramMap) throws Exception {
		TreeMap<String, String> signMap = new TreeMap<String, String>();
		Set<String> set = paramMap.keySet();
		String v;
		for (String key : set) {
			if (key.toUpperCase().equals("PWD")
					|| key.toUpperCase().equals("NEWPWD")) {
				System.out.println("密码加密前数据：" + paramMap.get(key));
				String pwd = paramMap.get(key);
				v = ByteUtil.bytesToHexLower(RSA.encryptByPublicKey(pwd
						.getBytes(), ght_public_key));
				System.out.println("密码加密后数据：" + v);
				signMap.put(key, v);
			} else {
				signMap.put(key, paramMap.get(key));
			}
		}
		return signMap;
	}

	/**
	 * 生成校验串MAC并组装参数URL（没有中文参与用此方法）
	 * 
	 * @param signMap
	 *            加密处理后的MAP集合
	 * @return 返回MAC
	 */
	public static String getMac(TreeMap<String, String> signMap) {
		String mac = createMacString(signMap);
		mac = mac + "&mac=" + RSA.sign(mac, private_key, "utf-8");
		return mac;
	}
	
	public static String createMacString(TreeMap<String, String> map) {
		StringBuffer sb = new StringBuffer();
		Set es = map.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (!"MAC".equals(k) && !"mac".equals(k) && null != v
					&& !"".equals(v)) {
				sb.append(k + "=" + v + "&");
			}
		}
		int longth = sb.toString().length();
		if (longth == 0) {
			longth = 1;
		}
		return sb.toString().substring(0, longth - 1);
	}

	public static String createReturnMacString(TreeMap<String, String> map) {
		StringBuffer sb = new StringBuffer();
		Set es = map.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (!"pwd".equals(k) && !"newPwd".equals(k) && !"MAC".equals(k)
					&& !"mac".equals(k) && null != v && !"".equals(v)) {
				sb.append(k + "=" + v + "&");
			}
		}
		int longth = sb.toString().length();
		if (longth == 0) {
			longth = 1;
		}
		return sb.toString().substring(0, longth - 1);
	}

}
