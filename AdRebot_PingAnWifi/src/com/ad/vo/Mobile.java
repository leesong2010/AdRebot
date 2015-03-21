package com.ad.vo;

import java.util.Random;

public class Mobile {

	public static final String CHINA_MOBILE = "ChinaMobile";
	public static final String CHINA_UNICOM = "ChinaUnicom";
	public static final String CHINA_TELCOM = "ChinaTelecom";
	
	//移动
	public static class ChinaMobile{
		public final static String GPRS = "GPRS";
		public final static String EDGE = "EDGE";
		
		//随机获取移动网络类型
		public static String getRandomType()
		{
			String type = "";
			int ran = new Random().nextInt(100);
			if(ran >=0 && ran <50)
				type = ChinaMobile.GPRS;
			else
				type = ChinaMobile.EDGE;
			return type;
		}
	}
	
	//联通
	public static class ChinaUnicom{
		public final static String GPRS = "GPRS";
		public final static String EDGE = "EDGE";
			
		public final static String HSPA = "HSPA";
		public final static String HSDPA = "HSDPA";
		public final static String HSPAP = "HSPAP";
		public final static String UMTS = "UMTS";
		
		//随机获取移动网络类型
		public static String getRandomType()
		{
			String type = "";
			int ran = new Random().nextInt(100);
			//80%取3G
			if(ran >=0 && ran <80)
			{
				int ransub = new Random().nextInt(100);
				if(ransub>=0 && ransub <30) type = ChinaUnicom.HSPA;
				else if(ransub>=30 && ransub <60) type = ChinaUnicom.HSDPA;
				else if(ransub>=60 && ransub <90) type = ChinaUnicom.HSPAP;
				else type = ChinaUnicom.UMTS;
			}
			else
			{
				int ransub = new Random().nextInt(100);
				if(ransub>=0 && ransub <50) type = ChinaUnicom.EDGE;
				else type = ChinaUnicom.GPRS;
			}
			return type;
		}
	}
	
	
	//电信
	public static class ChinaTelCom{
		public final static String CDMA = "CDMA";
		public final static String EVDO_0 = "EVDO_0";
		public final static String EVDO_A = "EVDO_A";
		public final static String EVDO_B = "EVDO_B";
					
		//随机获取移动网络类型
		public static String getRandomType()
		{
			String type = "";
			int ran = new Random().nextInt(100);
			if(ran >=0 && ran <20){
				type = ChinaTelCom.CDMA;
			}
			//80% 3g
			else{
				int ransub = new Random().nextInt(100);
				if(ransub>=0 && ransub <35) type = ChinaTelCom.EVDO_0;
				else if(ransub>=35 && ransub <70) type = ChinaTelCom.EVDO_A;
				else if(ransub>=70 && ransub <100) type = ChinaTelCom.EVDO_B;						
			}
			return type;
		}
	}
}
