package com.ad.appmob;

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
import org.herojohn.adrobot.device.creator.impl.AppmobDeviceCreator;
import org.herojohn.adrobot.device.creator.impl.AppmobDeviceCreator;
import org.herojohn.adrobot.device.model.DeviceConfig;
import org.herojohn.adrobot.device.model.DianjoyDevice;
import org.herojohn.adrobot.device.model.AppmobDevice;
import org.nice.IP;
import org.nice.NetUtils;
import org.nice.Test;

import com.ad.dianjoy.DJContacts;
import com.ad.dianjoy.DJUtils;
import com.ad.dianjoy.DianJoy;
import com.ad.dianjoy.vo.DJAd;
import com.ad.doumob.ConfigReader;
import com.ad.doumob.DMContacts;
import com.ad.doumob.DMUtils;
import com.ad.dyd.DYDContacts;
import com.ad.jz.JzUtils;
import com.ad.qm.QMContacts;
import com.mysql.jdbc.Util;

public class AppMob {

	
	private static AppmobDeviceCreator deviceCreator = (AppmobDeviceCreator) DeviceFactory.getInstance("appmob");

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
	public static AppMob d;
	public static String DATA_NAME;
	public static String[] sdk_lib_ver = null;
	private static boolean isNewData = false;
	private static boolean isDelay = false;
	//public static Logger  log =  Logger.getLogger("JZ");
	
	
	public static void main(String[] args) {
		
		
		DATA_NAME = args[0];
		//DATA_NAME = "test";
		AppContact.APPID =  ConfigReader.get(DATA_NAME, "appid").get(0);
		AppContact.APPNAME = ConfigReader.get(DATA_NAME, "appname").get(0);
		AppContact.APPPKG =  ConfigReader.get(DATA_NAME, "app_pkg").get(0);
		AppContact.APPVer = 		ConfigReader.get(DATA_NAME, "app_version").get(0);
		AppContact.APPVC = 		ConfigReader.get(DATA_NAME, "appvc").get(0);
		AppContact.CLICK_RATE = 		Integer.parseInt(ConfigReader.get(DATA_NAME, "click_rate").get(0));
		AppContact.INSTALL_RATE = 		Integer.parseInt(ConfigReader.get(DATA_NAME, "install_rate").get(0));
		AppContact.TOTAL_NEW_USER = Integer.valueOf(ConfigReader.get(DATA_NAME, "users").get(0));
		AppContact.TABEL_INDEX = Integer.valueOf(ConfigReader.get(DATA_NAME, "table_index").get(0));
		AppContact.TOTAL_ACTIVTE_APP_NUM = Integer.valueOf(ConfigReader.get(DATA_NAME, "apps").get(0));
		AppContact.APP_TABLE_NAME = ConfigReader.get(DATA_NAME, "table").get(0);
		AppContact.DOWN_SPEED =  ConfigReader.get(DATA_NAME, "down_speed").get(0);
		
		
		System.out.println("下载速率："+AppContact.DOWN_SPEED.split(",")[0]+"~~~~"+AppContact.DOWN_SPEED.split(",")[1]);
		
		
		String apptPath = DJUtils.readProperty("../conf/config.properties","aapt_path");
		AppContact.AAPTPATH = apptPath;
		File aaptFile =  new File(AppContact.AAPTPATH+"/aapt.exe");
		if(!aaptFile.exists()){
			System.out.println("##########"+apptPath+"下面找不到aapt.exe !");
			System.exit(1);
		}

		//从配置文件里获取ip接口地址
		ipAddressList = DJUtils.readProperties("../conf/appmob_ip.properties");
		//从配置文件里获取sdk版本信息
		/*String sdk_vers  =  DJUtils.readProperty("../conf/config.properties","sdk_ver");
		sdk_lib_ver = sdk_vers.split(",");
		DJContacts.LIB_VER = sdk_lib_ver[new Random().nextInt(sdk_lib_ver.length)];
		System.out.println("SDK:"+sdk_vers);*/
		System.out.println("获取ip接口地址数:"+ ipAddressList.size());
		
		
		
		tartgetNum = AppContact.TOTAL_NEW_USER;
		targetActiviteAppNum = AppContact.TOTAL_ACTIVTE_APP_NUM;
		
		
		d = new AppMob();
		
		AppContact.JSONFILEPATH = DJUtils.readProperty("../conf/config.properties","appmob_jsonfile_path");
		System.out.println("JsonFilePath=="+AppContact.JSONFILEPATH);
		String delayStr = DJUtils.readProperty("../conf/config.properties","appmob_cp");
		if(delayStr == null || "".equals(delayStr.trim()))isDelay = true;
		else if(delayStr.equals("1"))
		isDelay = true;
		else isDelay =  false;
		
		System.out.println("isDelay="+isDelay);
		
		try {
			
			deviceCreator.init(new DeviceConfig(AppContact.TABEL_INDEX,AppContact.APP_TABLE_NAME));
			System.out.println("获取本机ip...");
			localIP = NetUtils.getLocalIP().trim();
			
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
			System.out.println("本机ip是:"+localIP);
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
				System.out.println("【" + AppContact.APPNAME +"】当前小时" + currentOpeationHour + "已操作..." );
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
				int ipNum[] = Utils.getIPNumByHour();
				
				String tmpUrl = ipAddressList.get(new Random().nextInt(ipAddressList.size()));
				AppContact.WHICH_IP = tmpUrl.split(",")[0];
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
			/*	//验证ip有效性
				for(int i=0;i<ipList.size();i++)
				{ 
					IP ipvo = Test.getIP(ipList.get(i));
					if(ipvo == null)continue;
					String ip = ipvo.getIpAddr();  
			        String port = ipvo.getPort();
			        boolean isAvailProxy = NetUtils.isAvailProxy(DianJoy.localIP,ip,port);
			        if(!isAvailProxy)ipList.remove(ip+":"+port);
				}*/
				
				
			//	System.out.println("验证后ip数量:"+ipList.size());
				
				
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
				     	
				     	new Thread(d.new ShuaActivitThread(AppContact.APPID, AppContact.APPPKG,myip)).start();
						
						/*int currHourNum = numMap.get(currentOpeationHour);//获取当前小时的数量
						if(i % 2 == 0)
						{
							if(currHourNum >=30 && currHourNum <=60)
								Thread.sleep(Test.getRangeRandomNum(3 *1000, 5*1000));
							else if(currHourNum >=15 && currHourNum <30)
								Thread.sleep(Test.getRangeRandomNum(5 *1000, 7*1000));
							else if(currHourNum >=1 && currHourNum <15)
								Thread.sleep(Test.getRangeRandomNum(8 *1000, 10*1000));
						}*/
					} catch (Exception e) {
						e.printStackTrace();
					}
					

			     	
				}
				
				
				System.out.println("当前操作小时【"+currentOpeationHour+"】,新增目标："+userMap.get(DMUtils.getHour())+"，激活目标："+numMap.get(DMUtils.getHour()));
				System.out.println("==========【" + AppContact.APPNAME +"】已刷新增数:" + totalUserNum+",已刷激活数:"+oldUserActivteAppNum);
				
				if(isDelay)
					JzUtils.sleep();
				
			
		}
	}
	
	
	
	private void doNewUser(AppmobDevice device,String appkey,String pkg,MyIP myip)throws Exception
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
			//刷第一次运行,激活的用户数
			String resp = Utils.init(appkey, myip);
			//System.out.println("init1="+resp);
			resp = Utils.init(device, myip);
			//System.out.println("init2="+resp);
			List<Ad> list = Utils.requestInfo(device, myip);//请求插屏列表
			if(list.size() > 0){
				Ad ad = list.get(new Random().nextInt(list.size()));
				if(ad != null && !"".equals(ad.getToken())){
					Utils.doAction(device, ad, myip, AppContact.SHOW, "");//展示插屏
				}
			}
			Utils.requestList(device, myip);//请求推荐列表
			totalUserNum++;
			deviceCreator.saveInstalled(device);
			
		}

		//System.out.println("==========【" + AppContact.APPNAME +"】已刷新增用户:" + totalUserNum);
	}
	
	
	private void doActive(AppmobDevice device,MyIP myip)throws Exception
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
			 
			
			//卸载的几率5%
			int uninstallRan = new Random().nextInt(100);
			if(uninstallRan>=0 && uninstallRan <5){
				if(device.getAdInfo().keySet().size()>0){
					List<String> appinfo = new ArrayList<String>();
					for (String key : device.getAdInfo().keySet()) {
						String _adpkg  = key;
						String _token = (String)device.getAdInfo().get(key);
						if(  (_adpkg!=null && !"".equals(_adpkg)) && (_token!=null && !"".equals(_token))  )
							appinfo.add(_adpkg+"#"+_token);
					}
					
					if(appinfo.size()>0){
						String info = appinfo.get(new Random().nextInt(appinfo.size()));
						Ad ad = new Ad();
						ad.setToken(info.split("#")[1]);
						String uninstallResp = Utils.doAction(device, ad, myip, AppContact.UNINSTALL, "");
						System.out.println(info.split("#")[0]+",卸载结果:"+uninstallResp);
						return;
					}
						
				}
			}
			
			
			String resp = Utils.init(AppContact.APPID, myip);
			//System.out.println("init1="+resp);
			resp = Utils.init(device, myip);
			//System.out.println("init2="+resp);
			List<Ad> list = Utils.requestInfo(device, myip);//请求插屏列表
			if(list.size() > 0){
				Ad ad = list.get(new Random().nextInt(list.size()));
				if(ad != null && !"".equals(ad.getToken())){
					Utils.doAction(device, ad, myip, AppContact.SHOW, "");//展示插屏
				}
			}
			Utils.requestList(device, myip);//请求推荐列表
			
			
			//出现插屏后，滑动插屏
			if(list.size()>0){
				for(int i=0;i<new Random().nextInt(list.size());i++){
					Ad ad = list.get(i);
					//Utils.doAction(device, ad, myip, AppContact.SHOW, "");//暂时屏蔽，不然展示太多了
				}
			}
			
			
			
			
			Ad ad = Utils.showCPList(device, myip);
			if(ad == null)return;
			int doCPListRandom = new Random().nextInt(100);
			//80%去操作插屏框里的东西
			if(doCPListRandom>=0 && doCPListRandom<80){
				int cpClickRandom = new Random().nextInt(100);
				//插屏点击率40%
				if(cpClickRandom>=0 && cpClickRandom<AppContact.CLICK_RATE){
					if(ad.getToken() == null || "".equals(ad.getToken()))return;
					resp = Utils.doAction(device, ad, myip, AppContact.CLICK, "");
					System.out.println(ad.getAdName()+",被点击:"+resp);
					
					Map<String,Object> map = Utils.getLocalApkInfo();//获取本地保存的apk信息文件
					String adv = "";
					if(map.size() == 0)//如果没有，则真实下载
					{
						Download down = new Download(new URL(ad.getDownurl()), device.getUa(), myip,true);
						adv = down.toDownload();
					}else{
						String ver = ad.getVersioname().trim();
						PkgInfo p = (PkgInfo)map.get(ad.getPkg());
						//如果版本号不一样，也去下载
//						if(!ver.equalsIgnoreCase(p.getVersioname())){
						if(  ( (p==null || p.getVersioname() == null) || !ver.equalsIgnoreCase(p.getVersioname()) ) ){
							Download down = new Download(new URL(ad.getDownurl()), device.getUa(), myip,true);
							adv = down.toDownload();
						}else{
							adv = p.getAdv();
							//假下载
							Download down = new Download(new URL(ad.getDownurl()), device.getUa(), myip,false);
							down.toDownload();
						}
					}
					
					//90%几率下载完成
					int downdoneRandom = new Random().nextInt(100);
					if(downdoneRandom>=0 && downdoneRandom<90){
						resp = Utils.doAction(device, ad, myip, AppContact.DOWN, "");//下载完成
						System.out.println(ad.getAdName()+",下载完成:"+resp);
						
						//15%的几率去安装
						int installRandom = new Random().nextInt(100);
						if(installRandom>=0 && installRandom<AppContact.INSTALL_RATE){
							Thread.sleep(Test.getRangeRandomNum(2, 5)*1000);//模拟安装时间
							if(adv==null || "".equals(adv.trim())){
								System.out.println("###无法获取adv！！！###");
								return;
							}
							if(device.getAdInfo().keySet().size() >0)
								if(Utils.isInstall(ad.getPkg(), device.getAdInfo()))return;//已激活不再激活
							resp = Utils.doAction(device, ad, myip, AppContact.INSTALL, adv);
							System.out.println(ad.getAdName()+ ",安装完成:"+resp);
							
							 org.herojohn.adrobot.device.model.ActiveLog activeLog = new org.herojohn.adrobot.device.model.ActiveLog();
							 activeLog.setImei(device.getImei());
							 activeLog.setPkg(ad.getPkg());
							 activeLog.setIp(myip.getIp());
							 activeLog.setPort(Integer.parseInt(myip.getPort()));
							 activeLog.setAppName(AppContact.APP_TABLE_NAME );
							 activeLog.setDeviceIndex(AppContact.TABEL_INDEX);
							 deviceCreator.addActiveLog(activeLog);
							
							oldUserActivteAppNum++;
							device.addAd(ad.getPkg(), ad.getToken());
							deviceCreator.updateInstalled(device);
						}
					}
					
				}
			}
			//20%去点左边的“推荐列表”
			else{
				list = Utils.requestList(device, myip);//请求推荐列表
				int clickRandom = new Random().nextInt(100);
				//30%点下载
				if(clickRandom>=0 && clickRandom<30){
					ad = list.get(new Random().nextInt(list.size()));
					resp = Utils.doAction(device, ad, myip, AppContact.CLICK, "");
					System.out.println("推荐列表广告:"+ad.getAdName()+",点击:"+resp);
					
//					Download down = new Download(new URL(ad.getDownurl()), device.getUa(), myip);
//					String adv = down.toDownload();
					
					Map<String,Object> map = Utils.getLocalApkInfo();//获取本地保存的apk信息文件
					String adv = "";
					if(map.size() == 0)//如果没有，则真实下载
					{
						Download down = new Download(new URL(ad.getDownurl()), device.getUa(), myip,true);
						adv = down.toDownload();
					}else{
						String ver = ad.getVersioname().trim();
						PkgInfo p = (PkgInfo)map.get(ad.getPkg());
						//如果版本号不一样，也去下载
						//if(!ver.equalsIgnoreCase(p.getVersioname())){
						if(  ( (p==null || p.getVersioname() == null) || !ver.equalsIgnoreCase(p.getVersioname()) ) ){
							Download down = new Download(new URL(ad.getDownurl()), device.getUa(), myip,true);
							adv = down.toDownload();
						}else{
							adv = p.getAdv();
							//假下载
							Download down = new Download(new URL(ad.getDownurl()), device.getUa(), myip,false);
							down.toDownload();
						}
					}
					
					
					
					
					
					//90%几率下载完成
					int downdoneRandom = new Random().nextInt(100);
					if(downdoneRandom>=0 && downdoneRandom<90){
						resp = Utils.doAction(device, ad, myip, AppContact.DOWN, "");//下载完成
						System.out.println("推荐列表:"+ad.getAdName()+",下载完成:"+resp);
						
						//15%的几率去安装
						int installRandom = new Random().nextInt(100);
						if(installRandom>=0 && installRandom<15){
							Thread.sleep(Test.getRangeRandomNum(2, 5)*1000);//模拟安装时间
							if(adv==null || "".equals(adv.trim())){
								System.out.println("###推荐列表无法获取adv！！！###");
								return;
							}
							if(device.getAdInfo().keySet().size() >0)
								if(Utils.isInstall(ad.getPkg(), device.getAdInfo()))return;//已激活不再激活
							resp = Utils.doAction(device, ad, myip, AppContact.INSTALL, adv);
							System.out.println("推荐列表:"+ad.getAdName()+ ",安装完成:"+resp);
							
							org.herojohn.adrobot.device.model.ActiveLog activeLog = new org.herojohn.adrobot.device.model.ActiveLog();
							 activeLog.setImei(device.getImei());
							 activeLog.setPkg(ad.getPkg());
							 activeLog.setIp(myip.getIp());
							 activeLog.setPort(Integer.parseInt(myip.getPort()));
							 activeLog.setAppName(AppContact.APP_TABLE_NAME );
							 activeLog.setDeviceIndex(AppContact.TABEL_INDEX);
							 deviceCreator.addActiveLog(activeLog);
							
							oldUserActivteAppNum++;
							device.addAd(ad.getPkg(), ad.getToken());
							deviceCreator.updateInstalled(device);
						}
						
						
					}
				}
				
				
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
		        	AppmobDevice newUserDevice = deviceCreator.getDevice();
		        	doNewUser(newUserDevice, this.appkey, this.pkg, this.myip);
		          
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
		        	//AppmobDevice olderUserDevice = deviceCreator.getInstalledDeviceByIp(ip);
		        	AppmobDevice olderUserDevice = deviceCreator.getInstalledDeviceByIp(ip);
		        	doActive(olderUserDevice, this.myip);
		        }
				
			      
				} catch (Exception e) {
					System.out.println(e.getMessage());
					//e.printStackTrace();
				}
			
		}
	}
	
	
	

}
