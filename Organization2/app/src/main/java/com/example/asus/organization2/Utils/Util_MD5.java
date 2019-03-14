package com.example.asus.organization2.Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Util_MD5 {

	public static String SHA1(String decript) {
		try {
			MessageDigest digest = MessageDigest
					.getInstance("SHA-1");
			digest.update(decript.getBytes());
			byte messageDigest[] = digest.digest();
			// Create Hex String
			StringBuffer hexString = new StringBuffer();
			// �ֽ�����ת��Ϊ ʮ������ ��
			for (int i = 0; i < messageDigest.length; i++) {
				String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexString.append(0);
				}
				hexString.append(shaHex);
			}
			return hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}

	// MD5����ʵ��
	public static String getMD5(String str) throws NoSuchAlgorithmException {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		char[] charArray = str.toCharArray();
		byte[] byteArray = new byte[charArray.length];
		for (int i = 0; i < charArray.length; i++) {
			byteArray[i] = (byte) charArray[i];
		}
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}
		return hs;
	}

	// SHA1 ����ʵ��
	public static String encryptToSHA(String info) {
		byte[] digesta = null;
		try {
			// �õ�һ��SHA-1����ϢժҪ
			MessageDigest alga = MessageDigest.getInstance("SHA-1");
			// ���Ҫ���м���ժҪ����Ϣ
			alga.update(info.getBytes());
			// �õ���ժҪ
			digesta = alga.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		// ��ժҪתΪ�ַ���
		String rs = byte2hex(digesta);
		return rs;
	}

	//md5 加密算法
	public static String md5(String text) {
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("md5");
			// 数组 byte[] result -> digest.digest( );  文本 text.getBytes();
			byte[] result = digest.digest(text.getBytes());
			//创建StringBuilder对象 然后建议StringBuffer，安全性高
			//StringBuilder sb = new StringBuilder();
			StringBuffer sb = new StringBuffer();
			// result数组，digest.digest ( ); -> text.getBytes();
			// for 循环数组byte[] result;
			for (byte b : result){
				// 0xff 为16进制
				int number = b & 0xff;
				// number值 转换 字符串 Integer.toHexString( );
				String hex = Integer.toHexString(number);
				if (hex.length() == 1){
					sb.append("0"+hex);
				}else {
					sb.append(hex);
				}
			}
			//sb StringBuffer sb = new StringBuffer();对象实例化
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			//发送异常return空字符串
			return "";
		}
	}
}
