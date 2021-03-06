package com.ad.rebot;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Random;

import org.herojohn.adrobot.device.DeviceFactory;
import org.herojohn.adrobot.device.creator.impl.HaomatongDeviceWebCreator;
import org.herojohn.adrobot.device.model.DeviceConfig;
import org.herojohn.adrobot.device.model.HaomatongDevice;
import org.herojohn.adrobot.device.util.MD5;

import com.ad.utils.AdUtils;
import com.ad.utils.CheckIP;
import com.ad.utils.Loger;
import com.ad.utils.RandomUtils;
import com.ad.vo.AdItem;
import com.ad.vo.IP;
import com.ad.vo.Mobile;

import android.app.ActivityManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;


public class RebotService extends Service{

	
	private String currAdPkg = "com.sg.sledog";
	//private String currAdPkg = "com.example.imei";
	private AdItem currAd;
	
	
	private static final String TAG = "adrebot";
	private boolean appOpened = false;
	private static ServiceBrodcast brodcast;
	
	private static String currSettedIP = "";
	
	//参数设置工具类
	private com.ad.utils.SetParams setParams;	
	//代理工具类
	private com.ad.utils.Proxy proxy;
	
	private HaomatongDevice device = null;
	//所有配置参数
	private String IMEI;                    //IMEI                     1
	private String IMSI;                    //IMSI                     2
	private String Mac;                     //Macaddr                  3
	private String UDID;                    //UDID                     4
	private String line1Number;             //电话号码                                                                     5
	private String screenWidth;             //屏幕宽                                                                         6
	private String screenHeight;            //屏幕高                                                                         7
	private String networkType;             //网络类型                                                                     8
	private String networkOperation;        //网络运营商                                                                9
	private String phoneModel;              //手机型号                                                                  10
	private String phoneManufacturer;       //手机厂商                                                                  11
	private String androidReleaseVersion;   //Android版本号                                              12
	private String screenLayoutSize;        //screenLayoutSize        13	
	private String androidSDKLevel;         //Android api Level       14
	
	private int killCount;
	private String currIP = "";
	private String currPort = "";
	private static boolean STOP_GET_IP = false;
	private List<AdItem> adList;
	private static int succ = 0;//新增成功数
	private static int oldSucc = 0;//活跃用户成功数
	private static boolean isNewUser = false;//如果true，代表是新用户激活
	//private static int retryNum = 0;
	//private static boolean useLocalIP =false;
	HaomatongDeviceWebCreator haomatongDeviceCreator = (HaomatongDeviceWebCreator) DeviceFactory.getInstance("haomatong_web");
	ActivityManager mActivityManager; 
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	public void onCreate() {		
		Log.d(TAG,"Service onCreate");
		
		brodcast = new ServiceBrodcast();
		IntentFilter intentFilter=new IntentFilter();
		intentFilter.addAction("stop_ad_service");
		registerReceiver(brodcast, intentFilter);
			
		
		File f = new File(Environment.getExternalStorageDirectory().getPath() + "/ip.txt");
		mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE); 
		
		if(!f.exists()) 
		{
			Log.d("HMT", "SDCard ip.txt can not found!!!");
			stopSelf();
		}
		else
		{
			HMTContant.ipListAddress = AdUtils.readProperties(Environment.getExternalStorageDirectory().getPath() + "/ip.txt");
		}
		 
		
		initDB();		 
	}

	
	private void initDB(){
		try {
			DeviceConfig config = new DeviceConfig();
			config.setEndpoint("http://42.51.16.181:8089/weather");
			config.setAdSense("haomatong");
			config.setAppName("360");
			config.setDevicesIndex(4);
			haomatongDeviceCreator.init(config);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//初始化相关工具
	private void init(){
		setParams = new com.ad.utils.SetParams(getApplicationContext());
		proxy = new com.ad.utils.Proxy(getApplicationContext());
	}
		
	public void onStart(Intent intent, int startId) {
		Log.d(TAG,"Service onStart");
		init();
		STOP_GET_IP = false;
		try {
			//从sdcard的ad.xml来获取广告列表
			adList = AdUtils.getAds(); 
			Log.d(TAG,"获取到广告列表数:"+adList.size());
			setText("Get Ad Size:"+adList.size());
			if(adList == null || adList.size() == 0)
				Log.d(TAG,"无法获取广告列表!");
			else{
				handler.post(runnable);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	Handler handler = new Handler() {  
	    public void handleMessage(Message msg) {  	    	
	        super.handleMessage(msg);  
	    }  
	};  
	
	public void onDestroy() {
		super.onDestroy();
		
		proxy.stopProxy();
		handler.removeCallbacks(runnable);
		STOP_GET_IP = true;
		unregisterReceiver(brodcast);
		Log.d(TAG,"Service Destory");
	}
	
	private class ServiceBrodcast extends BroadcastReceiver
	{
		@Override
		public void onReceive(Context context, Intent intent) {
			String act = intent.getAction();
			if("stop_ad_service".equals(act)){
				File f = new File(Environment.getExternalStorageDirectory().getPath() + "/paramConfig");
				if(f.exists())f.delete();
				STOP_GET_IP = true;
				stopSelf();
			}
		}		
	}
	
	
	//结束app
	private void killApp(){
		setText("killApp");
		//if(!useLocalIP)
			proxy.stopProxy();
		try {			
			Method forceStopPackage = mActivityManager.getClass().getDeclaredMethod("forceStopPackage", String.class);
			forceStopPackage.setAccessible(true);
			forceStopPackage.invoke(mActivityManager, currAdPkg);

			appOpened = false;
			haomatongDeviceCreator.updateInstalled(device);
			Log.d("kill","_AFTER_KILL_killCount=" + killCount++);
			setText("KillCount" + killCount);			
		} catch (Exception e) {
			Loger.w(AdUtils.getErrorInfoFromException(e));
		}
	}	

	
	//===============================================干掉360所有daemon进程==============================================
	private void killApp2(){
		setText("killApp");
		//if(!useLocalIP)
			proxy.stopProxy();
		try {			
			kill360BrowserAllDaemons();

			appOpened = false;
			haomatongDeviceCreator.updateInstalled(device);
			Log.d("kill","_AFTER_KILL_killCount=" + killCount++);
			setText("KillCount" + killCount);			
		} catch (Exception e) {
			Loger.w(AdUtils.getErrorInfoFromException(e));
		}
	}		
		
	private void kill360BrowserAllDaemons(){
		killUnDead(currAdPkg);
		killUnDead("com.qihoo.daemon");
		killUnDead("com.qihoo360.accounts");
		killUnDead("com.qihoo.appstore");
	}	
	
	private void killUnDead(String unDeadPkgName){
		try {
			Method forceStopPackage = mActivityManager.getClass().getDeclaredMethod("forceStopPackage", String.class);
			forceStopPackage.setAccessible(true);
			forceStopPackage.invoke(mActivityManager, unDeadPkgName);	
		} catch (Exception e) {
		}		
	}	
	//@end of===============================================干掉360所有daemon进程========================================
	
	
	private void startMonkey(){
		try {
			com.ad.utils.ShellCommand cmd = new com.ad.utils.ShellCommand();
			//cmd.su.runWaitFor("monkey -p " + currAdPkg + " --setup scriptfile -f /storage/sdcard0/wxx_MonkeyScript_360.txt 1");
			cmd.su.runWaitFor("monkey --setup scriptfile -f /storage/sdcard0/wxx_MonkeyScript_360browser.txt 1");
		} catch (Exception e) {
			Loger.w(AdUtils.getErrorInfoFromException(e));
		}
	}
	
	private void startApp(){
		setText("Start【" + currAd.getAdName() + "】");
		setText("Start Pkg:" + currAdPkg);
		
		PackageManager pm = getPackageManager(); 
		Intent intent = new Intent();
		intent =pm.getLaunchIntentForPackage(currAdPkg); 
		if(intent == null) {
			setText("Start Intent == null");
			return;
		}
		startActivity(intent);
		//startMonkey();
		appOpened = true;						
				
		if(isNewUser){
			setSuccText("新增:"+ (++succ) + " 活跃:"+ (oldSucc));
			//Loger.w("Imei="+device.getImei() +"-Succ="+succ  + ",活跃:"+ (oldSucc));
		}else{
			setSuccText("新增:"+ (succ) + " 活跃:"+ (++oldSucc));
		}	
	}
		
	private void getDeviceInfo(){
		try {
			int radomDevice = new Random().nextInt(100);
			Log.d(TAG,"ran="+radomDevice);
			if(radomDevice >=0 && radomDevice <80){
				device = haomatongDeviceCreator.getDevice();
				isNewUser = true;
			}
			else{
				device = haomatongDeviceCreator.getInstalledDevice();
				isNewUser = false;
			}
			
			if(device == null){
				Log.d(TAG,"设备取不到，重新取..");
				setText("设备取不到，重新取..");
				device = haomatongDeviceCreator.getDevice();
				isNewUser = true;
			}
			
			IMEI = device.getImei();
			if(IMEI == null || "".equals(IMEI)){
				Log.d(TAG,"设备取不到，重新取..");
				setText("设备取不到，重新取..");
				device = haomatongDeviceCreator.getDevice();
				IMEI = device.getImei();
			}
			
			Mac= device.getMac();
			Log.d(TAG,"get imei="+IMEI);
			setText("Get imei="+IMEI + "-" + (isNewUser == true?"新增用户":"活跃用户"));
			Log.d(TAG,"get mac="+Mac);
			
			IMSI = device.getImsi();
						
			phoneModel = device.getModel();
			phoneManufacturer= device.getName();
			androidReleaseVersion = device.getOsVersion();
			UDID = MD5.hash(device.getMac()).toLowerCase();
			
			screenWidth = String.valueOf(device.getWidth());
			screenHeight = String.valueOf(device.getHeight());
			
			//设置网络类型跟运营商
			setNetwork(radomDevice);
			//根据网络类型生成对应手机号码
			line1Number = RandomUtils.randomLine1Number(IMSI,networkType,networkOperation);
			
			androidSDKLevel =  String.valueOf(device.getApilevel());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
		
	//设置参数
	public void setXXX(){
		Log.d(TAG,"setXXX...");
		setText("setXXX...");
		String ip = "";
		String port = "";
		try {
			while(true)
			{
				if(STOP_GET_IP){
					handler.removeCallbacks(runnable);
					return;
				}
				
				//第一次发送心跳广播
				sendHeartBeatInfo();
				
				try {
					Thread.sleep(1000);
				} catch (Exception e) {}
				
				Log.d(TAG,"获取ip...");
				setText("获取ip..");
				List<String> ipList = CheckIP.getIps(1);
				Log.d(TAG,"获取ip数量:"+ipList.size());
				setText("Get ip size:" + ipList.size());
				if(ipList == null || ipList.size() == 0) continue;
				
				IP ipvo = CheckIP.getIP(ipList.get(0));
				if(ipvo == null)continue;
		        ip = ipvo.getIpAddr();  
		        port = ipvo.getPort();
				
		        String localIP = CheckIP.getLocalIP();
		        Log.d(TAG,"本机ip:"+localIP);
		        setText("This ip:"+localIP);	        
		        
		        proxy.startProxy(ip, port);
		        
		        /**************暂时注释掉ip检测
		        boolean result = CheckIP.isAvailProxy(localIP,ip,port);
		        Log.d(TAG,"获取的ip可用?"+result);
		        setText((ip +":" + port) + (result == true?"-ip Avaliable!":"#UnAvailable Proxy") );
		        if(result == false){
	        		proxy.stopProxy();
//	        		retryNum++;
//	        		if(retryNum>=4){
//	        			setText("重试次数大于4次，用本机搞！");	    
//	        			useLocalIP = true;
//	        			retryNum = 0;
//	        			break;
//	        		}
	        		continue;
		        }
		        else{
	        		currIP = ip;
	        		currPort = port;
	        		break;
		       }
		       */
        		currIP = ip;
        		currPort = port;
        		break;
			}
					
			getDeviceInfo();
			
			try {
				if(isNewUser)
					haomatongDeviceCreator.saveInstalled(device);
			} catch (Exception e) {e.printStackTrace();}
				        
		//	if(!useLocalIP)
		//	{
				Log.d(TAG,"设置代理--ip="+ip+":" +port);
				setText("Start Proxy--ip="+ip+":" +port);
				proxy.startProxy(ip, port);
	//		}
			setParams.set(
						  IMEI, 
						  IMSI,
						  Mac,
						  UDID,
						  line1Number,
						  screenWidth,
						  screenHeight,
						  networkType,
						  networkOperation,
						  phoneModel,
						  phoneManufacturer,
						  androidReleaseVersion,
						  androidSDKLevel,
						  screenLayoutSize);
									
			handler.post(runnable);
					
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	Runnable runnable=new Runnable(){
		public void run(){
			Log.d(TAG,"runnable...");
			setText("runnable...");
			
			currAd = adList.get(new Random().nextInt(adList.size()));
			currAdPkg = currAd.getAdPkg();
			Log.d(TAG,"curAdPkg="+currAdPkg);
			setText("curAdPkg="+currAdPkg);
			
			if("".equals(currIP) && "".equals(currPort) ){
				new Thread(new Runnable() {				
					@Override
					public void run() {
						setXXX();						
					}
				}).start();				
				handler.removeCallbacks(runnable);
			}else{
				//第二次发送心跳广播
				sendHeartBeatInfo();
				
				if(STOP_GET_IP){
					handler.removeCallbacks(runnable);
					return;
				}
				
				startApp();			
				currIP = "";
				currPort = "";
				//device = null;
    			try {
					Thread.sleep(currAd.getDelay() * 1000);
				} catch (Exception e) {}
	    		
    			//killApp2();
    			killApp();
    			device = null;
    			int currentHour = AdUtils.getHour();//获取当前时间
    			if(currentHour >=0 && currentHour <=7)
    				handler.postDelayed(this, 1*1000*10);
    			else
    				handler.postDelayed(this, 500);
			}
		}
	};
	
	private void setNetwork(int radomDevice){
		//50% wifi
		if(radomDevice >=0 && radomDevice <50){
			networkType = "wifi";
			networkOperation = "";				
		}
		//50% Mobile
		else{
			// 中国联通
			if(IMSI.startsWith("46001") || IMSI.startsWith("46006")){
				networkType = Mobile.ChinaUnicom.getRandomType();					
				networkOperation = Mobile.CHINA_UNICOM;					
			}
			//中国移动
			if(IMSI.startsWith("46000") || IMSI.startsWith("46002") || IMSI.startsWith("46007")){
				networkType = Mobile.ChinaMobile.getRandomType();					
				networkOperation = Mobile.CHINA_MOBILE;					
			}	
			//中国电信
			if(IMSI.startsWith("46003") || IMSI.startsWith("46005")){
				networkType = Mobile.ChinaTelCom.getRandomType();					
				networkOperation = Mobile.CHINA_TELCOM;					
			}
		}	
	}	
	
	//获取界面txtview文本
	private String getTextViewText(){
		return AdRebotMainActivity.instants.txtMsg.getText().toString();
	}
	
	private void setText(String txt){	
		String curText = getTextViewText();
		String newText = curText + "\n" + txt;
		
		Intent in = new Intent("adrebot_msg");
		if(AdRebotMainActivity.instants.txtMsg.getLineCount() >= 30)
			in.putExtra("msg", txt);
		else
			in.putExtra("msg", newText);
		sendBroadcast(in);		
	}
	
	private void setSuccText(String txt){	
		String newText = txt;
		
		Intent in = new Intent("succ_msg");
		in.putExtra("succ", newText);
		sendBroadcast(in);		
	}
	
	//发送心跳信息
	private void sendHeartBeatInfo(){
		sendBroadcast(new Intent("HeartBeat"));
	}
}
