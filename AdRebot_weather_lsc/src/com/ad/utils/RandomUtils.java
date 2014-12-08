package com.ad.utils;

import java.util.Random;

import com.ad.vo.Mobile;

public class RandomUtils {
	private static String IMSI;
	private static String networkType;
	private static String networkOperation;
	
	//��ֵ��������
	private static void initRandomEnvironment(String imsi,String netType,String netOperation ){
		IMSI = imsi;
		networkType = netType;
		networkOperation = netOperation;
	}
	
	//������ɶ�Ӧ������Ӫ����ʵ�ֻ�����
	public static String randomLine1Number(String imsi,String netType,String netOperation){
		//��Ҫ���� networkType IMSI networkOperation��randomLine1Number
		initRandomEnvironment(imsi,netType,netOperation); 
		
		//�����wifi��������������кŶεĺ���,Ŀǰһ��26���Ŷ�
		if(networkType.equalsIgnoreCase("wifi")){
			
			// �й���ͨ
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
			//�й��ƶ�
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
			//�й�����
				//153 180 189
			if(IMSI.startsWith("46003") || IMSI.startsWith("46005")){
				int random = new Random().nextInt(3);
				if(random == 1)	return getRandomPhoneNumber("+86153");
				if(random == 2)	return getRandomPhoneNumber("+86180");
				if(random == 3)	return getRandomPhoneNumber("+86189");
			}			
		}
		//�������wifi�����ɵĺ���Ҫ����Ӫ�̶�Ӧ
		else{
			return randomLine1Number("Mobile");
		}
		return null;
	}
	
	//������ɶ�Ӧ������Ӫ����ʵ�ֻ�����
	private static String randomLine1Number(String mobile){
		//��ͨ2g 
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
		//��ͨ3g 
			//186
		if(networkOperation.equals(Mobile.CHINA_UNICOM) 
				&& ( networkType.equals(Mobile.ChinaUnicom.HSPA) 
						|| networkType.equals(Mobile.ChinaUnicom.UMTS) 
						|| networkType.equals(Mobile.ChinaUnicom.HSDPA) 
						|| networkType.equals(Mobile.ChinaUnicom.HSPAP)) ){
			return getRandomPhoneNumber("+86186");
		}
		
		//�ӵ��ƶ�
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
		
		//����2g 
			//153 180
		if(networkOperation.equals(Mobile.CHINA_TELCOM) 
				&& networkType.equals(Mobile.ChinaTelCom.CDMA) ){
			int random = new Random().nextInt(2);
			if(random == 1)	return getRandomPhoneNumber("+86153");
			if(random == 2)	return getRandomPhoneNumber("+86180");
		}		
		//����3g 
			//189
		if(networkOperation.equals(Mobile.CHINA_TELCOM) 
				&& ( networkType.equals(Mobile.ChinaTelCom.EVDO_0) 
						|| networkType.equals(Mobile.ChinaTelCom.EVDO_A) 
						|| networkType.equals(Mobile.ChinaTelCom.EVDO_B) ) ){
			return getRandomPhoneNumber("+86189");
		}
		
		return null;
	}
	
	//�����������
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
