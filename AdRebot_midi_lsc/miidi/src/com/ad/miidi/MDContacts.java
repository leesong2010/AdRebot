package com.ad.miidi;

public class MDContacts {
	
	public static final int CT = 1;//电信网络
	public static final int CMCC = 2;//移动网络
	public static final int UNICOM = 3;//联通网络
	public static final int WIFI = 4;//wifi网络
	
	public static final int NEW_TYPE = 1;//新增用户;
	public static final int OLD_TYPE = 2;//活跃用户
	public static int THREAD_NUM = 0;
	public static int CLICK_ADS = 0;
	public static int WIFI_USER_OPEN_WALLS = 0;
	
	public static  String APPKEY= "";
	public static  String APPID= "";
	public static  String APP_NAME = "";
	
	public static int CURRENT_THREAD_ACTIVITY_NUM = 0;
	public static String APPVC = "";
	public static String APP_VERSION = "";
	public static String APP_TABLE_NAME = "";
	public static String APP_PKG = "";
	public static int TOTAL_NEW_USER = 0;
	public static int TOTAL_ACTIVTE_APP_NUM = 0;
	public static String LOG_TAG = "";
	public static String LOG_PATH = "";
	public static int DELAY_MIN = 0;
	public static int DELAY_MAX = 0;
	public static String DATA_NAME = "";
	
	public static int GIVE = 0;
	public static int SPEND = 0;
	public static String WHICH_IP = "";
	public static String LIB_VER = "android_v2.4.2";
	public static String CHANNEL = "";
	public static String IP138URL = "";
	
	public static boolean useNewPort = false;
	public static boolean useGet = false;
	public static boolean no8088 = false;
	
	static
	{
		/*APPKEY = ConfigReader.get(DATA_NAME, "appkey").get(0);
		APP_NAME = ConfigReader.get(DATA_NAME, "appname").get(0);
		APPVC = 		ConfigReader.get(DATA_NAME, "appvc").get(0);
       APP_VERSION = 		ConfigReader.get(DATA_NAME, "app_version").get(0);
       
       APP_PKG =  ConfigReader.get(DATA_NAME, "app_pkg").get(0);
       TOTAL_NEW_USER = Integer.valueOf(ConfigReader.get(DATA_NAME, "users").get(0));
       TOTAL_ACTIVTE_APP_NUM = Integer.valueOf(ConfigReader.get(DATA_NAME, "apps").get(0));
       
       DELAY_MIN = Integer.valueOf(ConfigReader.get(DATA_NAME, "delay_min").get(0));
       DELAY_MAX = Integer.valueOf(ConfigReader.get(DATA_NAME, "delay_max").get(0));
       
       LOG_TAG = ConfigReader.get(DATA_NAME, "log_tag").get(0);
       LOG_PATH = ConfigReader.get(DATA_NAME, "log_path").get(0);*/
       
//		APPKEY = "4e295096ec390dd12a2f7280eec67c09";//caiput
//		APP_PKG = "org.nice.caipu";
//		SAVE_IMEI_PATH = "c:/dj/acted_imei.txt";
//		APP_NAME = "【test】";
//		APPVC = "102";
//		APP_VERSION = "1.02";
		
		/*APP_TABLE_NAME = ConfigReader.get(DATA_NAME, "table").get(0);*/
		CURRENT_THREAD_ACTIVITY_NUM = 5;
	}
	
}
