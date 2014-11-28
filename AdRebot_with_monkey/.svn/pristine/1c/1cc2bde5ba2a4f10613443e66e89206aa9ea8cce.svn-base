

package org.herojohn.adrobot.device.util;

import java.security.MessageDigest;

public final class MD5{

	private static final char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
	
	private static ThreadLocal<MessageDigest> MD5 = new ThreadLocal<MessageDigest>() {
		@Override
		protected MessageDigest initialValue() {
			try {
				return MessageDigest.getInstance("MD5");
			} catch (Exception e) {
			}
			return null;
		}
	};
	
	public final static String hash(String s){
		MessageDigest mdTemp = MD5.get();
		byte[] strTemp = s.getBytes(); 
		mdTemp.update(strTemp); 
		byte[] md = mdTemp.digest(); 
		int j = md.length; 
		char c[] = new char[j * 2]; 
		int k = 0; 
		for (int i = 0; i < j; i++) { 
			byte byte0 = md[i]; 
			c[k++] = hexDigits[byte0 >>> 4 & 0xf]; 
			c[k++] = hexDigits[byte0 & 0xf]; 
		}
		return new String(c); 
	}
}