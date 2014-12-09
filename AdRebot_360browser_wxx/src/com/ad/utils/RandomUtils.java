package com.ad.utils;

import java.util.Random;

import com.ad.vo.Mobile;

public class RandomUtils {
	private static String IMSI;
	private static String networkType;
	private static String networkOperation;
	
	//赋值依赖变量
	private static void initRandomEnvironment(String imsi,String netType,String netOperation ){
		IMSI = imsi;
		networkType = netType;
		networkOperation = netOperation;
	}
	
	//随机生成对应网络运营商真实手机号码
	public static String randomLine1Number(String imsi,String netType,String netOperation){
		//需要根据 networkType IMSI networkOperation来randomLine1Number
		initRandomEnvironment(imsi,netType,netOperation); 
		
		//如果是wifi我们随机生成所有号段的号码,目前一共26个号段
		if(networkType.equalsIgnoreCase("wifi")){
			
			// 中国联通
				//130 131 132 155 156 185 186
			if(IMSI.startsWith("46001") || IMSI.startsWith("46006")){
				int random = new Random().nextInt(7);
				if(random == 0)	return getRandomPhoneNumber("+86130");
				if(random == 1)	return getRandomPhoneNumber("+86131");
				if(random == 2)	return getRandomPhoneNumber("+86132");
				if(random == 3)	return getRandomPhoneNumber("+86155");
				if(random == 4)	return getRandomPhoneNumber("+86156");
				if(random == 5)	return getRandomPhoneNumber("+86185");
				if(random == 6) return getRandomPhoneNumber("+86186");
			}
			//中国移动
				//134 135 136 137 138 139 147 150 151 152 157 158 159 182 187 188
			if(IMSI.startsWith("46000") || IMSI.startsWith("46002") || IMSI.startsWith("46007")){
				int random = new Random().nextInt(16);
				if(random == 0)	return getRandomPhoneNumber("+86134");
				if(random == 1)	return getRandomPhoneNumber("+86135");
				if(random == 2)	return getRandomPhoneNumber("+86136");
				if(random == 3)	return getRandomPhoneNumber("+86137");
				if(random == 4)	return getRandomPhoneNumber("+86138");
				if(random == 5)	return getRandomPhoneNumber("+86139");
				if(random == 6)	return getRandomPhoneNumber("+86147");
				if(random == 7)	return getRandomPhoneNumber("+86150");
				if(random == 8)	return getRandomPhoneNumber("+86151");
				if(random == 9)	return getRandomPhoneNumber("+86152");
				if(random == 10)return getRandomPhoneNumber("+86157");
				if(random == 11)return getRandomPhoneNumber("+86158");
				if(random == 12)return getRandomPhoneNumber("+86159");
				if(random == 13)return getRandomPhoneNumber("+86182");
				if(random == 14)return getRandomPhoneNumber("+86187");
				if(random == 15)return getRandomPhoneNumber("+86188");				
			}	
			//中国电信
				//153 180 189
			if(IMSI.startsWith("46003") || IMSI.startsWith("46005")){
				int random = new Random().nextInt(3);
				if(random == 1)	return getRandomPhoneNumber("+86153");
				if(random == 2)	return getRandomPhoneNumber("+86180");
				if(random == 3)	return getRandomPhoneNumber("+86189");
			}			
		}
		//如果不是wifi，生成的号码要跟运营商对应
		else{
			return randomLine1Number("Mobile");
		}
		return null;
	}
	
	//随机生成对应网络运营商真实手机号码
	private static String randomLine1Number(String mobile){
		//联通2g 
			//130 131 132 155 156 185 
		if(networkOperation.equals(Mobile.CHINA_UNICOM) 
				&& ( networkType.equals(Mobile.ChinaUnicom.GPRS) || networkType.equals(Mobile.ChinaUnicom.EDGE) ) ){
			int random = new Random().nextInt(6);
			if(random == 0)	return getRandomPhoneNumber("+86130");
			if(random == 1)	return getRandomPhoneNumber("+86131");
			if(random == 2)	return getRandomPhoneNumber("+86132");
			if(random == 3)	return getRandomPhoneNumber("+86155");
			if(random == 4)	return getRandomPhoneNumber("+86156");
			if(random == 5)	return getRandomPhoneNumber("+86185");
		}
		//联通3g 
			//186
		if(networkOperation.equals(Mobile.CHINA_UNICOM) 
				&& ( networkType.equals(Mobile.ChinaUnicom.HSPA) 
						|| networkType.equals(Mobile.ChinaUnicom.UMTS) 
						|| networkType.equals(Mobile.ChinaUnicom.HSDPA) 
						|| networkType.equals(Mobile.ChinaUnicom.HSPAP)) ){
			return getRandomPhoneNumber("+86186");
		}
		
		//坑爹移动
			//134 135 136 137 138 139 147 150 151 152 157 158 159 182 187 188
		if(networkOperation.equals(Mobile.CHINA_MOBILE) 
				&& ( networkType.equals(Mobile.ChinaMobile.GPRS) || networkType.equals(Mobile.ChinaMobile.EDGE) ) ){
			int random = new Random().nextInt(16);
			if(random == 0)	return getRandomPhoneNumber("+86134");
			if(random == 1)	return getRandomPhoneNumber("+86135");
			if(random == 2)	return getRandomPhoneNumber("+86136");
			if(random == 3)	return getRandomPhoneNumber("+86137");
			if(random == 4)	return getRandomPhoneNumber("+86138");
			if(random == 5)	return getRandomPhoneNumber("+86139");
			if(random == 6)	return getRandomPhoneNumber("+86147");
			if(random == 7)	return getRandomPhoneNumber("+86150");
			if(random == 8)	return getRandomPhoneNumber("+86151");
			if(random == 9)	return getRandomPhoneNumber("+86152");
			if(random == 10)return getRandomPhoneNumber("+86157");
			if(random == 11)return getRandomPhoneNumber("+86158");
			if(random == 12)return getRandomPhoneNumber("+86159");
			if(random == 13)return getRandomPhoneNumber("+86182");
			if(random == 14)return getRandomPhoneNumber("+86187");
			if(random == 15)return getRandomPhoneNumber("+86188");
		}		
		
		//电信2g 
			//153 180
		if(networkOperation.equals(Mobile.CHINA_TELCOM) 
				&& networkType.equals(Mobile.ChinaTelCom.CDMA) ){
			int random = new Random().nextInt(2);
			if(random == 1)	return getRandomPhoneNumber("+86153");
			if(random == 2)	return getRandomPhoneNumber("+86180");
		}		
		//电信3g 
			//189
		if(networkOperation.equals(Mobile.CHINA_TELCOM) 
				&& ( networkType.equals(Mobile.ChinaTelCom.EVDO_0) 
						|| networkType.equals(Mobile.ChinaTelCom.EVDO_A) 
						|| networkType.equals(Mobile.ChinaTelCom.EVDO_B) ) ){
			return getRandomPhoneNumber("+86189");
		}
		
		return null;
	}
	
	//生成随机号码
	private static String getRandomPhoneNumber(String prefix){
		String number = "";
		number = prefix +
			+ new Random().nextInt(10)
			+ new Random().nextInt(10)
			+ new Random().nextInt(10)
			+ new Random().nextInt(10)
			+ new Random().nextInt(10)
			+ new Random().nextInt(10)
			+ new Random().nextInt(10)
			+ new Random().nextInt(10);
		return number;
	}
}
