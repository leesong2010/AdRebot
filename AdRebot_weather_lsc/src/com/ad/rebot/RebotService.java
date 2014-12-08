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
	
	//�������ù�����
	private com.ad.utils.SetParams setParams;	
	//����������
	private com.ad.utils.Proxy proxy;
	
	private HaomatongDevice device = null;
	//�������ò���
	private String IMEI;                    //IMEI                     1
	private String IMSI;                    //IMSI                     2
	private String Mac;                     //Macaddr                  3
	private String UDID;                    //UDID                     4
	private String line1Number;             //�绰����                                                                     5
	private String screenWidth;             //��Ļ��                                                                         6
	private String screenHeight;            //��Ļ��                                                                         7
	private String networkType;             //��������                                                                     8
	private String networkOperation;        //������Ӫ��                                                                9
	private String phoneModel;              //�ֻ��ͺ�                                                                  10
	private String phoneManufacturer;       //�ֻ�����                                                                  11
	private String androidReleaseVersion;   //Android�汾��                                              12
	private String screenLayoutSize;        //screenLayoutSize        13	
	private String androidSDKLevel;         //Android api Level       14
	
	private int killCount;
	private String currIP = "";
	private String currPort = "";
	private static boolean STOP_GET_IP = false;
	private List<AdItem> adList;
	private static int succ = 0;//�����ɹ���
	private static int oldSucc = 0;//��Ծ�û��ɹ���
	private static boolean isNewUser = false;//���true�����������û�����
	//private static int retryNum = 0;
	//private static boolean useLocalIP =false;
	HaomatongDeviceWebCreator haomatongDeviceCreator = (HaomatongDeviceWebCreator) DeviceFactory.getInstance("haomatong_web");
	
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
	//	am = (ActivityManager)getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);  
		
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

	
	private void initDB()
	{
		try {
			DeviceConfig config = new DeviceConfig();
			config.setEndpoint("http://42.51.16.181:8089/weather");
			config.setAdSense("haomatong");
			config.setAppName("weather");
			config.setDevicesIndex(4);
			haomatongDeviceCreator.init(config);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//��ʼ����ع���
	private void init(){
		setParams = new com.ad.utils.SetParams(getApplicationContext());
		proxy = new com.ad.utils.Proxy(getApplicationContext());
	}
		
	public void onStart(Intent intent, int startId) {
		Log.d(TAG,"Service onStart");
		init();
		STOP_GET_IP = false;
		try {
			//��sdcard��ad.xml����ȡ����б�
			adList = AdUtils.getAds(); 
			Log.d(TAG,"��ȡ������б���:"+adList.size());
			setText("Get Ad Size:"+adList.size());
			if(adList == null || adList.size() == 0)
				Log.d(TAG,"�޷���ȡ����б�!");
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
	
	//����app
	private void killApp()
	{
		//if(!useLocalIP)
			proxy.stopProxy();
		
		try {			
			ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);  
			Method method = Class.forName("android.app.ActivityManager").getMethod("forceStopPackage", String.class);  
			method.invoke(mActivityManager, currAdPkg);
			
			appOpened = false;
			//device.setIp(currIP);
			haomatongDeviceCreator.updateInstalled(device);
			Log.d("kill","killCount=" +killCount++);
		//	useLocalIP = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void startApp()
	{
		setText("Start��" + currAd.getAdName() + "��");
		PackageManager pm = getPackageManager(); 
		Intent intent = new Intent();
		intent =pm.getLaunchIntentForPackage(currAdPkg); 		
		startActivity(intent);
		appOpened = true;		
				
		try {
			
		} catch (Exception e) {
			Log.d(TAG,"save failed:");
		}
				
		if(isNewUser){
			setSuccText("����:"+ (++succ) + " ��Ծ:"+ (oldSucc));
			//Loger.w("Imei="+device.getImei() +"-Succ="+succ  + ",��Ծ:"+ (oldSucc));
		}
		else{
			setSuccText("����:"+ (succ) + " ��Ծ:"+ (++oldSucc));
		}	
		
		//retryNum = 0;
	}
		
	private void getDeviceInfo()
	{
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
				Log.d(TAG,"�豸ȡ����������ȡ..");
				device = haomatongDeviceCreator.getDevice();
				isNewUser = true;
			}
			
			IMEI = device.getImei();
			if(IMEI == null || "".equals(IMEI)){
				Log.d(TAG,"�豸ȡ����������ȡ..");
				device = haomatongDeviceCreator.getDevice();
				IMEI = device.getImei();
			}
			
			Mac= device.getMac();
			Log.d(TAG,"get imei="+IMEI);
			setText("Get imei="+IMEI + "-" + (isNewUser == true?"Added":"Old"));
			Log.d(TAG,"get mac="+Mac);
			
			IMSI = device.getImsi();
						
			phoneModel = device.getModel();
			phoneManufacturer= device.getName();
			androidReleaseVersion = device.getOsVersion();
			UDID = MD5.hash(device.getMac()).toLowerCase();
			
			screenWidth = String.valueOf(device.getWidth());
			screenHeight = String.valueOf(device.getHeight());
			
			//�����������͸���Ӫ��
			setNetwork(radomDevice);
			//���������������ɶ�Ӧ�ֻ�����
			line1Number = RandomUtils.randomLine1Number(IMSI,networkType,networkOperation);
			
			androidSDKLevel =  String.valueOf(device.getApilevel());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
		
	//���ò���
	public void setXXX()
	{
		Log.d(TAG,"setXXX...");
		String ip = "";
		String port = "";
		try {
			while(true)
			{
				if(STOP_GET_IP){
					handler.removeCallbacks(runnable);
					return;
				}
				
				//��һ�η��������㲥
				sendHeartBeatInfo();
				
				try {
					Thread.sleep(1000);
				} catch (Exception e) {}
				
				Log.d(TAG,"��ȡip...");
				setText("��ȡip..");
				List<String> ipList = CheckIP.getIps(1);
				Log.d(TAG,"��ȡip����:"+ipList.size());
				setText("Get ip size:" + ipList.size());
				if(ipList == null || ipList.size() == 0) continue;
				
				IP ipvo = CheckIP.getIP(ipList.get(0));
				if(ipvo == null)continue;
		        ip = ipvo.getIpAddr();  
		        port = ipvo.getPort();
				
		        String localIP = CheckIP.getLocalIP();
		        Log.d(TAG,"����ip:"+localIP);
		        setText("This ip:"+localIP);	        
		        
		        proxy.startProxy(ip, port);
		        boolean result = CheckIP.isAvailProxy(localIP,ip,port);
		        Log.d(TAG,"��ȡ��ip����?"+result);
		        setText((ip +":" + port) + (result == true?"-ip Avaliable!":"#UnAvailable Proxy") );
		        if(result == false){
	        		proxy.stopProxy();
//	        		retryNum++;
//	        		if(retryNum>=4){
//	        			setText("���Դ�������4�Σ��ñ����㣡");	    
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
			}
					
			getDeviceInfo();
			
			try {
				if(isNewUser)
					haomatongDeviceCreator.saveInstalled(device);
			} catch (Exception e) {e.printStackTrace();}
				        
		//	if(!useLocalIP)
		//	{
				Log.d(TAG,"���ô���--ip="+ip+":" +port);
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
			
			if("".equals(currIP) && "".equals(currPort) ){
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						setXXX();
						
					}
				}).start();
				
				handler.removeCallbacks(this);
			}
			else
			{
				//�ڶ��η��������㲥
				sendHeartBeatInfo();
				
				if(STOP_GET_IP){
					handler.removeCallbacks(runnable);
					return;
				}
				
				startApp();			
				currIP = "";
				currPort = "";
				device = null;
    			try {
					Thread.sleep(currAd.getDelay() * 1000);
				} catch (Exception e) {}
	    		
    			killApp();
    			
    			/*int currentHour = AdUtils.getHour();//��ȡ��ǰʱ��
    			if(currentHour >=0 && currentHour <=7)
    				handler.postDelayed(this, 1*1000*60);
    			else*/
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
			// �й���ͨ
			if(IMSI.startsWith("46001") || IMSI.startsWith("46006")){
				networkType = Mobile.ChinaUnicom.getRandomType();					
				networkOperation = Mobile.CHINA_UNICOM;					
			}
			//�й��ƶ�
			if(IMSI.startsWith("46000") || IMSI.startsWith("46002") || IMSI.startsWith("46007")){
				networkType = Mobile.ChinaMobile.getRandomType();					
				networkOperation = Mobile.CHINA_MOBILE;					
			}	
			//�й�����
			if(IMSI.startsWith("46003") || IMSI.startsWith("46005")){
				networkType = Mobile.ChinaTelCom.getRandomType();					
				networkOperation = Mobile.CHINA_TELCOM;					
			}
		}	
	}	
	
	//��ȡ����txtview�ı�
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
	
	//����������Ϣ
	private void sendHeartBeatInfo(){
		sendBroadcast(new Intent("HeartBeat"));
	}
}