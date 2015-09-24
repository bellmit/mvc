package com.thd.base.util;

import java.security.MessageDigest;

/** 
 * 采用MD5加密解密 
 * 主要调用string2MD5
 */
public class MD5 {

	/*** 
	 * MD5加码 生成32位md5码  不可逆
	 */
	public static String string2MD5(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++) {
			byteArray[i] = (byte) charArray[i];
		}

		byte[] md5Bytes = md5.digest(byteArray);

		StringBuffer hexValue = new StringBuffer();

		for (byte md5Byte : md5Bytes) {
			int val = (md5Byte) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}

		return hexValue.toString();
	}

	/** 
	 * 加密解密算法 执行一次加密，两次解密 
	 */
	public static String convertMD5(String inStr) {

		char[] a = inStr.toCharArray();
		for (int i = 0; i < a.length; i++) {
			a[i] = (char) (a[i] ^ 't');
		}
		String s = new String(a);
		return s;

	}

	// 可逆的加密算法   
	public static String KL(String inStr) {
		// String s = new String(inStr);   
		char[] a = inStr.toCharArray();
		for (int i = 0; i < a.length; i++) {
			a[i] = (char) (a[i] ^ 't');
		}
		String s = new String(a);
		return s;
	}

	// 加密后解密   
	public static String JM(String inStr) {
		char[] a = inStr.toCharArray();
		for (int i = 0; i < a.length; i++) {
			a[i] = (char) (a[i] ^ 't');
		}
		String k = new String(a);
		return k;
	}

	// 测试主函数  
	public static void main(String args[]) {
		String s = new String("11111111111");
		System.out.println("原始：" + s);
		System.out.println("MD5后：" + string2MD5(s));
		System.out.println("加密的MD5后：" + KL(string2MD5(s)));
		System.out.println("解密的加密的MD5后：" + convertMD5(KL(string2MD5(s))));

		String s1 = new String("a");
		System.out.println("原始：" + s1);
		System.out.println("MD5后：" + string2MD5(s1));
		System.out.println("MD5 KL后：" + KL(s1));
		System.out.println("MD5 KL KL后：" + KL(KL(s1)));

	}
}
