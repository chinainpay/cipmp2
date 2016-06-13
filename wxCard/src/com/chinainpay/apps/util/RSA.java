package com.chinainpay.apps.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

public class RSA{
	
	public static final String  SIGN_ALGORITHMS = "SHA1WithRSA";
	public static final String KEY_ALGORITHM = "RSA";   
	
	/**
	* RSA绛惧悕
	* @param content 寰呯鍚嶆暟鎹�
	* @param privateKey 鍟嗘埛绉侀挜
	* @param input_charset 缂栫爜鏍煎紡
	* @return 绛惧悕鍊�
	*/
	public static String sign(String content, String privateKey, String input_charset)
	{
        try 
        {
        	PKCS8EncodedKeySpec priPKCS8 	= new PKCS8EncodedKeySpec( Base64.decode(privateKey) ); 
        	KeyFactory keyf 				= KeyFactory.getInstance("RSA");
        	PrivateKey priKey 				= keyf.generatePrivate(priPKCS8);

            java.security.Signature signature = java.security.Signature
                .getInstance(SIGN_ALGORITHMS);

            signature.initSign(priKey);
            signature.update( content.getBytes(input_charset) );

            byte[] signed = signature.sign();
            return URLEncoder.encode(Base64.encode(signed));
        }
        catch (Exception e) 
        {
        	e.printStackTrace();
        }
        
        return null;
    }
	
	/**
	* RSA楠岀鍚嶆鏌�
	* @param content 寰呯鍚嶆暟鎹�
	* @param sign 绛惧悕鍊�
	* @param ali_public_key 鏀粯瀹濆叕閽�
	* @param input_charset 缂栫爜鏍煎紡
	* @return 甯冨皵鍊�
	*/
	public static boolean doCheck(String content, String sign, String ali_public_key, String input_charset)
	{
		try 
		{
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	        byte[] encodedKey = Base64.decode(ali_public_key);
	        PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

		
			java.security.Signature signature = java.security.Signature
			.getInstance(SIGN_ALGORITHMS);
		
			signature.initVerify(pubKey);
			signature.update( content.getBytes(input_charset) );
		
			boolean bverify = signature.verify( Base64.decode(sign) );
			return bverify;
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	* 瑙ｅ瘑
	* @param content 瀵嗘枃
	* @param private_key 鍟嗘埛绉侀挜
	* @param input_charset 缂栫爜鏍煎紡
	* @return 瑙ｅ瘑鍚庣殑瀛楃涓�
	*/
	public static String decrypt(String content, String private_key, String input_charset) throws Exception {
        PrivateKey prikey = getPrivateKey(private_key);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, prikey);

        InputStream ins = new ByteArrayInputStream(Base64.decode(content));
        ByteArrayOutputStream writer = new ByteArrayOutputStream();
        //rsa瑙ｅ瘑鐨勫瓧鑺傚ぇ灏忔渶澶氭槸128锛屽皢闇�瑕佽В瀵嗙殑鍐呭锛屾寜128浣嶆媶寮�瑙ｅ瘑
        byte[] buf = new byte[128];
        int bufl;

        while ((bufl = ins.read(buf)) != -1) {
            byte[] block = null;

            if (buf.length == bufl) {
                block = buf;
            } else {
                block = new byte[bufl];
                for (int i = 0; i < bufl; i++) {
                    block[i] = buf[i];
                }
            }

            writer.write(cipher.doFinal(block));
        }

        return new String(writer.toByteArray(), input_charset);
    }

	
	/**
	* 寰楀埌绉侀挜
	* @param key 瀵嗛挜瀛楃涓诧紙缁忚繃base64缂栫爜锛�
	* @throws Exception
	*/
	public static PrivateKey getPrivateKey(String key) throws Exception {

		byte[] keyBytes;
		
		keyBytes = Base64.decode(key);
		
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		
		return privateKey;
	}
	
    /** *//** 
     * 瑙ｅ瘑<br> 
* 鐢ㄧ閽ヨВ瀵� http://www.5a520.cn http://www.feng123.com
     *   
     * @param data 
     * @param key 
     * @return 
     * @throws Exception 
     */ 
    public static byte[] decryptByPrivateKey(byte[] data, String key)   
            throws Exception {   
        // 瀵瑰瘑閽ヨВ瀵�   
        byte[] keyBytes = Base64.decode(key);//decryptBASE64(key);

        // 鍙栧緱绉侀挜   
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);   
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);   
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);   

        // 瀵规暟鎹В瀵�   
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());   
        cipher.init(Cipher.DECRYPT_MODE, privateKey);   

        return cipher.doFinal(data);   
    }   

    /** *//** 
     * 瑙ｅ瘑<br> 
     * 鐢ㄧ閽ヨВ瀵� 
     *   
     * @param data 
     * @param key 
     * @return 
     * @throws Exception 
     */ 
    public static byte[] decryptByPublicKey(byte[] data, String key)   
            throws Exception {   
        // 瀵瑰瘑閽ヨВ瀵�   
        byte[] keyBytes = Base64.decode(key);//decryptBASE64(key);   

        // 鍙栧緱鍏挜   
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);   
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);   
        Key publicKey = keyFactory.generatePublic(x509KeySpec);   

        // 瀵规暟鎹В瀵�   
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());   
        cipher.init(Cipher.DECRYPT_MODE, publicKey);   

        return cipher.doFinal(data);   
    }   

    /** *//** 
     * 鍔犲瘑<br> 
     * 鐢ㄥ叕閽ュ姞瀵� 
     *   
     * @param data 
     * @param key 
     * @return 
     * @throws Exception 
     */ 
    public static byte[] encryptByPublicKey(byte[] data, String key)   
            throws Exception {   
    	System.out.println("RSA");
        // 瀵瑰叕閽ヨВ瀵�   
        byte[] keyBytes = Base64.decode(key);//decryptBASE64(key);   

        // 鍙栧緱鍏挜   
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);   
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);   
        Key publicKey = keyFactory.generatePublic(x509KeySpec);   

        // 瀵规暟鎹姞瀵�   
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());   
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);   

        return cipher.doFinal(data);   
    }   

    /** *//** 
     * 鍔犲瘑<br> 
     * 鐢ㄧ閽ュ姞瀵� 
     *   
     * @param data 
     * @param key 
     * @return 
     * @throws Exception 
     */ 
    public static byte[] encryptByPrivateKey(byte[] data, String key)   
            throws Exception {   
        // 瀵瑰瘑閽ヨВ瀵�   
        byte[] keyBytes = Base64.decode(key);//decryptBASE64(key);

        // 鍙栧緱绉侀挜   
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);   
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);   
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);   

        // 瀵规暟鎹姞瀵�   
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());   
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);   

        return cipher.doFinal(data);   
    }   
}
