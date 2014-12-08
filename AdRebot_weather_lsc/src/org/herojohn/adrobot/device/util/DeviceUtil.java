/**
 * 
 */
package org.herojohn.adrobot.device.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author hero john
 * 
 */
public class DeviceUtil {
	
	private static final String[] chars = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A",
		"B", "C", "D", "E", "F" };
	public static String getMacAddr(char separator) {
		String result = "";
		for (int i = 0; i < 6; i++) {
			int m = (int) Math.rint(Math.random() * chars.length);
			String a = m > 0 ? chars[(m - 1)] : chars[m];
			int n = (int) Math.rint(Math.random() * chars.length);
			String b = n > 0 ? chars[(n - 1)] : chars[n];
			result = result + a + b+ separator;
		}
		return result.substring(0, result.length() - 1);
	}
	
	public static String getMacAddr() {
		String result = "";
		for (int i = 0; i < 6; i++) {
			int m = (int) Math.rint(Math.random() * chars.length);
			String a = m > 0 ? chars[(m - 1)] : chars[m];
			int n = (int) Math.rint(Math.random() * chars.length);
			String b = n > 0 ? chars[(n - 1)] : chars[n];
			result = result + a + b;
		}
		return result;
	}
	
	public static List<String> readMac(String filePath) {
		List<String> list = new ArrayList<String>(200000);
		File file = new File(filePath);
		BufferedReader bufferedReader = null;
		InputStreamReader read = null;
		try {
			if (file.isFile() && file.exists()) {
				read = new InputStreamReader(new FileInputStream(file), "utf-8");
				bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					list.add(lineTxt);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				read.close();
				bufferedReader.close();
			} catch (Exception localException2) {
			}
		}
		return list;
	}
	
	
	
	public static void main(String[] args) {
		System.out.println(getMacAddr(':'));
//		String sign = MD5.hash("310260525771317");
//		System.out.println(sign);
//		System.out.println("238539822239163".hashCode());
////		byte[] bytes = sign.getBytes();
////		StringBuilder localObject = new StringBuilder();
////	    for (int m = 0; m < bytes.length; m++)
////	      if (Integer.toHexString(bytes[m] & 0xFF).length() == 1)
////	        localObject.append("0").append(Integer.toHexString(bytes[m] & 0xFF));
////	      else
////	    	  localObject.append(Integer.toHexString(bytes[m] & 0xFF));
////	    System.out.println(localObject);
	}
	
	
	
	/**
	 * 中国移动GSM系统使用00、02；TD系统使用07，中国联通GSM系统使用01、06，中国电信CDMA系统使用03、05，中国铁通系统使用20
	 */
	private static String[] operators = new String[]{"00","02","07","01","06","03","05"};
	/**
	 * 根据运营商分布的比例来随机生成imsi
	 * 中国移动占20%，联通45%，电信35%
	 * @return
	 */
	public static String getIMSI() {
		StringBuilder imsi = new StringBuilder("460");
		int random = new Random().nextInt(100);
		int index = -1;
		if(random >=0 && random < 8) {
			index = 0;
		}else if(random >=8 && random < 18) {
			index = 1;
		}else if(random >=18 && random < 20) {
			index = 2;
		}else if(random >=20 && random < 42) {
			index = 3;
		}else if(random >=42 && random < 65) {
			index = 4;
		}else if(random >=65 && random < 83) {
			index = 5;
		}else if(random >=83 && random < 100) {
			index = 6;
		}
		//index = new Random().nextInt(operators.length);
		imsi.append(operators[index]);
		imsi.append(generateCheckCode(10));
		return imsi.toString();
	}
	
	/**
	 * 
	 * @param networktype 0为中国移动gsm，1为中国移动td，2为中国联通gsm，3为中国电信CDMA
	 * @return
	 */
	public static String getIMSI(int networktype) {
		StringBuilder imsi = new StringBuilder("460");
		int index = 0;
		if(networktype == 0) {
			index = new Random().nextInt(2);
		}else if(networktype == 1) {
			index = 2;
		}else if(networktype == 2) {
			index = new Random().nextInt(getRandomNumber(3,4));
		}else if(networktype == 3) {
			index = new Random().nextInt(getRandomNumber(5,6));
		}
		imsi.append(operators[index]);
		imsi.append(generateCheckCode(10));
		return imsi.toString();
	}
	
	/**
	 * 根据运营商分布的比例来随机生成imsi
	 * 中国移动占20%，联通45%，电信35%
	 * @return
	 */
	public static String getIMSI(String mnc) {
		StringBuilder imsi = new StringBuilder("460");
		if(mnc == null) {
			int random = new Random().nextInt(100);
			int index = -1;
			if(random >=0 && random < 8) {
				index = 0;
			}else if(random >=8 && random < 18) {
				index = 1;
			}else if(random >=18 && random < 20) {
				index = 2;
			}else if(random >=20 && random < 42) {
				index = 3;
			}else if(random >=42 && random < 65) {
				index = 4;
			}else if(random >=65 && random < 83) {
				index = 5;
			}else if(random >=83 && random < 100) {
				index = 6;
			}
			imsi.append(operators[index]);
		}else {
			imsi.append(mnc);
		}
		imsi.append(generateCheckCode(10));
		return imsi.toString();
	}
	
	private static String[] tacs = new String[]{
		//352370053009131
		/*Samsung i9300*/"35186905","35316305","35316605","35332805","35231505","35371805","35371905","35372005","35372105","35381705","35381805","35374205","35331705","35353705","35392105","35299305"
		,"35555000","35403003","86151901","35275705","35204305","86345701","86390201","86824801","35470804","35372205","86651701","35887502","35180903","35917901","86222501","86784801","86928900","35948505"
		,"86867800","35381105","86831801","86592030","35435600","86706401","35470004","35647204","86050501","35644004","86625900","35644104","35831304","35644044","35644204","35961404","86404478","35974203"
		,"35621604","35808504","35254004","35794704","35842104","35914804","35229603"
		
	};
	public static String getIMEI() {
		StringBuilder imei = new StringBuilder(15);
		int index = new Random().nextInt(tacs.length);
		imei.append(tacs[index]);
		imei.append(generateCheckCode(7));
		return imei.toString();
	}
	
	 /** 
     * 随机生成n位数字字符数组 
     * @return rands 
     */ 
	private static final String chs = "0123456789";
    public static char[] generateCheckCode(int length) {
        char[] rands = new char[length];
        for (int i = 0; i < length; i++) {
            int rand = (int) (Math.random() * 10); 
            rands[i] = chs.charAt(rand); 
        }
        return rands; 
    }
    
    /**
     * 生成指定范围的随机数 
     * @param min
     * @param max
     * @return
     */
    public static int getRandomNumber(int min,int max) {
    	Random random = new Random();
        return random.nextInt(max)%(max-min+1) + min;
    }
    
    public static int getRNum(int num) {
      int m = (int)Math.rint(Math.random() * num);
      if (m == 0) return 1;
      return m;
    }
    
    private static final char[] UpperChars = new char[]{'A','B','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    
    public static String getChars(int length) {
    	StringBuilder sb = new StringBuilder();
		for(int i = 0;i<length;i++) {
			sb.append(UpperChars[new Random().nextInt(UpperChars.length)]);
		}
		return sb.toString();
    }
}
