package com.ad.zhimeng;

import java.io.File;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;

import org.apache.log4j.Logger;
import org.herojohn.adrobot.device.DeviceFactory;
import org.herojohn.adrobot.device.creator.impl.ZhimengDeviceCreator;
import org.herojohn.adrobot.device.creator.impl.ZhimengDeviceCreator;
import org.herojohn.adrobot.device.creator.impl.ZhimengDeviceCreator;
import org.herojohn.adrobot.device.creator.impl.ZhimengDeviceCreator;
import org.herojohn.adrobot.device.model.DeviceConfig;
import org.herojohn.adrobot.device.model.DianjoyDevice;
import org.herojohn.adrobot.device.model.ZhimengDevice;
import org.nice.IP;
import org.nice.NetUtils;
import org.nice.Test;

import com.ad.appmob.AppContact;
import com.ad.appmob.MyIP;
import com.ad.appmob.PkgInfo;
import com.ad.dianjoy.DJUtils;
import com.ad.doumob.ConfigReader;
import com.ad.doumob.DMUtils;
import com.ad.dyd.cp.DYDCpUtils;
import com.ad.jz.JzUtils;

public class ZM {

	
	private static ZhimengDeviceCreator deviceCreator = (ZhimengDeviceCreator) DeviceFactory.getInstance("zhimeng");

	public static String localIP;
	public static int totalUserNum = 0,tartgetNum;
	private static int activteAppNum = 0,oldUserActivteAppNum;//激活应用数,活跃用户激活
	public static int targetActiviteAppNum = 0;//目标激活应用数
	private static boolean stop = false;
	public static Map<Integer,Integer> numMap = new HashMap<Integer,Integer>();
	public static Map<Integer,Integer> userMap = new HashMap<Integer,Integer>();

	private static List<Integer> newUserIndexList = java.util.Collections.synchronizedList(new ArrayList<Integer>());//出现在该列表中的索引号，即新增用户
	
	public static String ipUrl = "";
	public static List<String> ipAddressList  = null;

	public static int currentOpeationHour;
	public static int currH;
	//已完成的时段，记录小时
	public static List<String> compList =  java.util.Collections.synchronizedList(new ArrayList<String>());
	public static boolean isShuaing = false;
	public static ZM d;
	public static String DATA_NAME;
	public static String[] sdk_lib_ver = null;
	private static boolean isNewData = false;
	private static boolean isDelay = false;
	//public static Logger  log =  Logger.getLogger("JZ");
	
	
	public static void main(String[] args) {
		
		
		DATA_NAME = args[0];
		//DATA_NAME = "test";
		ZMContact.APPKEY =  ConfigReader.get(DATA_NAME, "appid").get(0);
		ZMContact.APPNAME = ConfigReader.get(DATA_NAME, "appname").get(0);
		ZMContact.APPPKG =  ConfigReader.get(DATA_NAME, "app_pkg").get(0);
		ZMContact.CLICK_RATE = Integer.parseInt(ConfigReader.get(DATA_NAME, "click_rate").get(0));
		ZMContact.INSTALL_RATE = 		Integer.parseInt(ConfigReader.get(DATA_NAME, "install_rate").get(0));
		ZMContact.TOTAL_NEW_USER = Integer.valueOf(ConfigReader.get(DATA_NAME, "users").get(0));
		ZMContact.TABEL_INDEX = Integer.valueOf(ConfigReader.get(DATA_NAME, "table_index").get(0));
		ZMContact.TOTAL_ACTIVTE_APP_NUM = Integer.valueOf(ConfigReader.get(DATA_NAME, "apps").get(0));
		ZMContact.APP_TABLE_NAME = ConfigReader.get(DATA_NAME, "table").get(0);
		ZMContact.DOWN_SPEED =  ConfigReader.get(DATA_NAME, "down_speed").get(0);
		ZMContact.SV = ConfigReader.get(DATA_NAME, "sv").get(0);
		ZMContact.KV = ConfigReader.get(DATA_NAME, "kv").get(0);
		
		System.out.println("下载速率："+ZMContact.DOWN_SPEED.split(",")[0]+"~~~~"+ZMContact.DOWN_SPEED.split(",")[1]);
		
		System.out.println("SV="+ZMContact.SV +",KV="+ZMContact.KV);
		
		//从配置文件里获取ip接口地址
		ipAddressList = DJUtils.readProperties("../conf/zm_ip.properties");
		//从配置文件里获取sdk版本信息
		/*String sdk_vers  =  DJUtils.readProperty("../conf/config.properties","sdk_ver");
		sdk_lib_ver = sdk_vers.split(",");
		DJContacts.LIB_VER = sdk_lib_ver[new Random().nextInt(sdk_lib_ver.length)];
		System.out.println("SDK:"+sdk_vers);*/
		System.out.println("获取ip接口地址数:"+ ipAddressList.size());
		
		
		
		tartgetNum = ZMContact.TOTAL_NEW_USER;
		targetActiviteAppNum = ZMContact.TOTAL_ACTIVTE_APP_NUM;
		
		
		d = new ZM();
		
		String delayStr = DJUtils.readProperty("../conf/config.properties","zm_cp");
		if(delayStr == null || "".equals(delayStr.trim()))isDelay = true;
		else if(delayStr.equals("1"))
		isDelay = true;
		else isDelay =  false;
		
		System.out.println("isDelay="+isDelay);
		
		try {
			
			DeviceConfig config = new DeviceConfig(ZMContact.TABEL_INDEX, ZMContact.APP_TABLE_NAME);
			config.setAdType("cp");
			deviceCreator.init(config);
			System.out.println("获取本机ip...");
			localIP = NetUtils.getLocalIP().trim();
			System.out.println("本机ip是:"+localIP);
			if(localIP == null || "".equals(localIP))
			{
				System.out.println("无法获取本机ip###");
				System.out.println("从配置文件获取本地IP...");
				localIP = DJUtils.readProperty("../conf/config.properties","local_ip");
				if(localIP == null || "".equals(localIP)){
					System.out.println("配置文件获取本地IP失败！");
					System.exit(1);
				}
			}
			
			//计算出每个小时的激活量
			numMap = DJUtils.getHourCountMap(targetActiviteAppNum);
			//计算出每个小时的新增用户量
			userMap = DJUtils.getHourCountMap(tartgetNum);
			System.out.println("hourActNum="+numMap.get(DMUtils.getHour()));
			System.out.println("hourUserNum="+userMap.get(DMUtils.getHour()));
			
			
			Timer timer = new Timer();
			timer.schedule(new MyTask(), 0, 2*1000);
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		
			
			
	}
	
	
	static class MyTask extends java.util.TimerTask{
		@Override
		public void run() {
			
			
			currH = DMUtils.getHour();//获取当前的小时
			//if(currH ==23) return;//tmp****************************
			
			//如果当前小时不在已完成的列表里，则开启线程
			if(!compList.contains(String.valueOf(currH)) )
			{
				if(!isShuaing)
				{
					currentOpeationHour = currH;
					doIt();
				}
			}
			else
			{
				System.out.println("【" + ZMContact.APPNAME +"】当前小时" + currentOpeationHour + "已操作..." );
				activteAppNum = 0;
				oldUserActivteAppNum = 0;
				totalUserNum = 0;
				
			}
			
			
			
			
		}
	}
	
	private static void doIt()
	{
		stop = false;
		isShuaing = true;
		while(true)
		{
			//http://60.173.9.43:2222/ ip总量
			//http://121.199.38.28/   
			if(stop){break;}
				int ipNum[] = DYDCpUtils.getIPNumByHour();
				
				String tmpUrl = ipAddressList.get(new Random().nextInt(ipAddressList.size()));
				ipUrl = tmpUrl.split(",")[1];
				ipUrl = ipUrl +Test.getRangeRandomNum(ipNum[0], ipNum[1]);
				System.out.println("IP_ADDR="+ipUrl);
				//延时获取ip，防止频率过高被封
				try {
					System.out.println("正在等待请求ip...");
					Thread.sleep(Test.getRangeRandomNum(5*1000, 10*1000));
				} catch (Exception e) {
				}
				
				List<String> ipList = NetUtils.getIPList(ipUrl);
					
				System.out.println("取到ip数量:"+ipList.size());
				
				for(int i = 0;i<ipList.size();i++)
				{
					try {
						
						if(stop){break;}
						IP ipvo = Test.getIP(ipList.get(i));
						if(ipvo == null)continue;
				        String ip = ipvo.getIpAddr();  
				        String port = ipvo.getPort();
				        
				        SocketAddress sa=new InetSocketAddress(ip,Integer.parseInt(port));  
				     	Proxy proxy=new Proxy(java.net.Proxy.Type.HTTP,sa);     
				     	MyIP myip = new MyIP(ip,port,ipUrl.substring(0,ipUrl.indexOf("?")),proxy);
				     	
				     	new Thread(d.new ShuaActivitThread(ZMContact.APPKEY, ZMContact.APPPKG,myip)).start();
						
					} catch (Exception e) {
						//e.printStackTrace();
						System.out.println(e.getMessage());
					}
					

			     	
				}
				
				
				System.out.println("当前操作小时【"+currentOpeationHour+"】,新增目标："+userMap.get(DMUtils.getHour())+"，激活目标："+numMap.get(DMUtils.getHour()));
				System.out.println("==========【" + ZMContact.APPNAME +"】已刷新增数:" + totalUserNum+",已刷激活数:"+oldUserActivteAppNum);
				
				if(isDelay)
					JzUtils.sleep();
				
			
		}
	}
	
	
	
	private void doNewUser(ZhimengDevice device,String appkey,String pkg,MyIP myip)throws Exception
	{
		if(stop)return;
		
		
		//检查当前小时是否已刷到指定的激活量
		boolean currHourCompleted = DJUtils.checkPoint(numMap, activteAppNum+oldUserActivteAppNum,currentOpeationHour);
		//检查当前小时是否已刷到指定新增用户量
		boolean currHourUserCompleted = DJUtils.checkPoint(userMap, totalUserNum,currentOpeationHour);
		if(currHourUserCompleted && currHourCompleted){
			//获取到了ip，要加入已完成的机会小时列表里
			if(compList.size() > 0)
			{
				compList.remove(compList.size()-1);//删除前一个已完成的时间
			}
			
			compList.add(String.valueOf(currentOpeationHour));
			isShuaing = false;
			stop = true;
			activteAppNum = 0;
			oldUserActivteAppNum = 0;
			totalUserNum = 0;
			return;
		}
		
		
		if(!currHourUserCompleted)//如果新增用户数达到目标数，则不刷新增用户数
		{
			
			List<Ad> list = Utils.getAds(device, myip);//获取广告列表
			if(list.size()>0){
				String data = Utils.getInitAdData(list);
				String resp = Utils.doAction(device, myip, data, true);
				System.out.println("初始化post 广告状态:"+resp);
			}
			
			totalUserNum++;
			deviceCreator.saveInstalled(device);
			
		}

		//System.out.println("==========【" + AppContact.APPNAME +"】已刷新增用户:" + totalUserNum);
	}
	
	
	private void doActive(ZhimengDevice device,MyIP myip)throws Exception
	{
		
			
			if(stop)return;
			
			//检查当前小时是否已刷到指定的激活量
			boolean currHourCompleted = DJUtils.checkPoint(numMap, activteAppNum+oldUserActivteAppNum,currentOpeationHour);
			//检查当前小时是否已刷到指定新增用户量
			boolean currHourUserCompleted = DJUtils.checkPoint(userMap, totalUserNum,currentOpeationHour);
			if(currHourCompleted && currHourUserCompleted){
				//获取到了ip，要加入已完成的机会小时列表里
				if(compList.size() > 0)
				{
					compList.remove(compList.size()-1);//删除前一个已完成的时间
				}
				compList.add(String.valueOf(currentOpeationHour));
				isShuaing = false;
				stop = true;
				activteAppNum = 0;
				oldUserActivteAppNum = 0;
				totalUserNum = 0;
				return;
			}
			
			if(currHourCompleted) return;
			 
			
			List<Ad> list = Utils.getAds(device, myip);//获取广告列表
			System.out.println("###取到广告数:【"+list.size()+"】");
			if(list.size() == 0)return;
			if(list.size()>0){
				int receiveRandom = new Random().nextInt(100);
				//加上几率，降低广告接收数
				if(receiveRandom>=0 && receiveRandom<30)
				{
					String data = Utils.getInitAdData(list);
					String resp = Utils.doAction(device, myip, data, true);
					System.out.println("活跃用户--初始化post 广告状态:"+resp);
				}
			}
			
			
			
			Ad ad = list.get(new Random().nextInt(list.size()));//随意拿一个广告
			list.remove(ad);

			int cpClickRandom = new Random().nextInt(100);
			//插屏点击率40%
			if(cpClickRandom>=0 && cpClickRandom<ZMContact.CLICK_RATE){
				
				//下载
				ZMDownload download = new ZMDownload(new URL(ad.getUrl()), device.getUa(), myip);
				download.toDownload();
				
				
				//90%几率下载完成
				int downdoneRandom = new Random().nextInt(100);
				if(downdoneRandom>=0 && downdoneRandom<90){
					//15%的几率去安装
					int installRandom = new Random().nextInt(100);
					if(installRandom>=0 && installRandom<ZMContact.INSTALL_RATE){
						Thread.sleep(Test.getRangeRandomNum(2, 5)*1000);//模拟安装时间
						
						
						int moreActRandom = new Random().nextInt(100);
						//30%的几率一个设备只激活一个广告
						 if(moreActRandom>=0 && moreActRandom <30){
							 //不去重复激活
							if(device.getPkgs() != null && device.getPkgs().contains(ad.getPname())){
								return;
							}
							 
							 String data = Utils.getAdData(ad, ZMContact.INSTALL);
							 String resp = Utils.doAction(device, myip, data, false);
							 System.out.println(ad.getPname()+ ",安装完成:"+resp);
							
							 String pkgs = device.getPkgs()==null?"":device.getPkgs();
							 device.setPkgs(pkgs+","+ad.getPname());
							 
							 oldUserActivteAppNum++;
							 
							 org.herojohn.adrobot.device.model.ActiveLog activeLog = new org.herojohn.adrobot.device.model.ActiveLog();
							 activeLog.setImei(device.getImei());
							 activeLog.setPkg(ad.getPname());
							 activeLog.setIp(myip.getIp());
							 activeLog.setPort(Integer.parseInt(myip.getPort()));
							 activeLog.setAppName(ZMContact.APP_TABLE_NAME );
							 activeLog.setDeviceIndex(ZMContact.TABEL_INDEX);
							 deviceCreator.addActiveLog(activeLog);
						 }
						 else{
							 List<Ad> toActAds = new ArrayList<Ad>();
							 int actTwoAdRan = new Random().nextInt(100);
							 //70%激活2个广告
							 if(actTwoAdRan>=0 && actTwoAdRan<70){
								 for(int k=0;k<2;k++){
									 Ad _ad = list.get(new Random().nextInt(list.size()));
									 list.remove(_ad);
									//不去重复激活
									if(device.getPkgs() != null && device.getPkgs().contains(_ad.getPname())){
											continue;
									}
									 String pkgs = device.getPkgs()==null?"":device.getPkgs();
									 device.setPkgs(pkgs+","+_ad.getPname());
									 toActAds.add(_ad);
									 
								 }
							 }else{//30%激活3个广告
								 for(int k=0;k<3;k++){
									 Ad _ad = list.get(new Random().nextInt(list.size()));
									 list.remove(_ad);
									//不去重复激活
									if(device.getPkgs() != null && device.getPkgs().contains(_ad.getPname())){
											continue;
									}
									 String pkgs = device.getPkgs()==null?"":device.getPkgs();
									 device.setPkgs(pkgs+","+_ad.getPname());
									 toActAds.add(_ad);
								 }
							 }
							 
							 String data = Utils.getInstallAdsData(toActAds);
							 String resp = Utils.doAction(device, myip, data, false);
							 System.out.println("【"+toActAds.size()+ "】个广告,安装完成:"+resp);
							 
							 for(int k=0;k<toActAds.size();k++){
								 oldUserActivteAppNum++;
								 org.herojohn.adrobot.device.model.ActiveLog activeLog = new org.herojohn.adrobot.device.model.ActiveLog();
								 activeLog.setImei(device.getImei());
								 activeLog.setPkg(toActAds.get(k).getPname());
								 activeLog.setIp(myip.getIp());
								 activeLog.setPort(Integer.parseInt(myip.getPort()));
								 activeLog.setAppName(ZMContact.APP_TABLE_NAME );
								 activeLog.setDeviceIndex(ZMContact.TABEL_INDEX);
								 deviceCreator.addActiveLog(activeLog);
							 }
							 
						 }
						
						
		
						
						deviceCreator.updateInstalled(device);
					}
					//如果没有安装，那么就是下载完成了
					else{
						String data = Utils.getAdData(ad, ZMContact.DOWN);
						Utils.doAction(device, myip, data, false);
					}
					
					
				}
				//如果没有下载完成的话
				else{
					String data = Utils.getAdData(ad, ZMContact.CLICK);
					Utils.doAction(device, myip, data, false);
				}
				
			}
			//如果没点击，那就是展示
			else{
				String data = Utils.getAdData(ad, ZMContact.SHOW);
				Utils.doAction(device, myip, data, false);
			}
	
			
			
		//	System.out.println("==========【" + AppContact.APPNAME +"】活跃用户已刷激活应用:" + oldUserActivteAppNum);
		
			
		
	}
	
	//刷活跃用户的操作
	private class ShuaActivitThread implements Runnable 
	{
		private MyIP myip;
		private String appkey;
		private String pkg;
		
		public ShuaActivitThread(String appkey,String pkg,MyIP myip)
		{
			this.myip = myip;
			this.appkey = appkey;
			this.pkg = pkg;
		}
		
		@Override
		public void run() {

			try {
				
				InetSocketAddress iss =  (InetSocketAddress)myip.getProxy().address();
				String ip = iss.getHostName();
				 int actionRandom = new Random().nextInt(100);
		        if ((actionRandom >= 0) && (actionRandom < 60))
		        {
		        	ZhimengDevice newUserDevice = deviceCreator.getDevice();
		        	doNewUser(newUserDevice, this.appkey, this.pkg, this.myip);
		          
		        	
		        	int count = deviceCreator.getImeiCount(ip);
					if(count >= 5) {//如果这个ip已经超过3个imei激活过了，该新增设备
						//device = deviceCreator.getInstalledDeviceByIp(ip.getIpAddr());
						return;
					}
		        	
		          int actveRandom = new Random().nextInt(100);
		          if ((actveRandom >= 0) || (actveRandom < 70))
		        	  doActive(newUserDevice,  this.myip);
		          else
		          {
		        	 // doActive(deviceCreator.getInstalledDeviceByIp(ip), this.appkey, this.pkg, this.proxy);
		        	  doActive(deviceCreator.getInstalledDeviceByIp(ip), myip);
		          }
		        }
		        else
		        {
		        	//ZhimengDevice olderUserDevice = deviceCreator.getInstalledDeviceByIp(ip);
		        	ZhimengDevice olderUserDevice = deviceCreator.getInstalledDeviceByIp(ip);
		        	doActive(olderUserDevice, this.myip);
		        }
				
			      
				} catch (Exception e) {
					System.out.println(e.getMessage());
					//e.printStackTrace();
				}
			
		}
	}
	
	
	

}
