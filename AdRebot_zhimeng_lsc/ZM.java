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
	private static int activteAppNum = 0,oldUserActivteAppNum;//����Ӧ����,��Ծ�û�����
	public static int targetActiviteAppNum = 0;//Ŀ�꼤��Ӧ����
	private static boolean stop = false;
	public static Map<Integer,Integer> numMap = new HashMap<Integer,Integer>();
	public static Map<Integer,Integer> userMap = new HashMap<Integer,Integer>();

	private static List<Integer> newUserIndexList = java.util.Collections.synchronizedList(new ArrayList<Integer>());//�����ڸ��б��е������ţ��������û�
	
	public static String ipUrl = "";
	public static List<String> ipAddressList  = null;

	public static int currentOpeationHour;
	public static int currH;
	//����ɵ�ʱ�Σ���¼Сʱ
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
		
		System.out.println("�������ʣ�"+ZMContact.DOWN_SPEED.split(",")[0]+"~~~~"+ZMContact.DOWN_SPEED.split(",")[1]);
		
		System.out.println("SV="+ZMContact.SV +",KV="+ZMContact.KV);
		
		//�������ļ����ȡip�ӿڵ�ַ
		ipAddressList = DJUtils.readProperties("../conf/zm_ip.properties");
		//�������ļ����ȡsdk�汾��Ϣ
		/*String sdk_vers  =  DJUtils.readProperty("../conf/config.properties","sdk_ver");
		sdk_lib_ver = sdk_vers.split(",");
		DJContacts.LIB_VER = sdk_lib_ver[new Random().nextInt(sdk_lib_ver.length)];
		System.out.println("SDK:"+sdk_vers);*/
		System.out.println("��ȡip�ӿڵ�ַ��:"+ ipAddressList.size());
		
		
		
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
			System.out.println("��ȡ����ip...");
			localIP = NetUtils.getLocalIP().trim();
			System.out.println("����ip��:"+localIP);
			if(localIP == null || "".equals(localIP))
			{
				System.out.println("�޷���ȡ����ip###");
				System.out.println("�������ļ���ȡ����IP...");
				localIP = DJUtils.readProperty("../conf/config.properties","local_ip");
				if(localIP == null || "".equals(localIP)){
					System.out.println("�����ļ���ȡ����IPʧ�ܣ�");
					System.exit(1);
				}
			}
			
			//�����ÿ��Сʱ�ļ�����
			numMap = DJUtils.getHourCountMap(targetActiviteAppNum);
			//�����ÿ��Сʱ�������û���
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
			
			
			currH = DMUtils.getHour();//��ȡ��ǰ��Сʱ
			//if(currH ==23) return;//tmp****************************
			
			//�����ǰСʱ��������ɵ��б�������߳�
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
				System.out.println("��" + ZMContact.APPNAME +"����ǰСʱ" + currentOpeationHour + "�Ѳ���..." );
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
			//http://60.173.9.43:2222/ ip����
			//http://121.199.38.28/   
			if(stop){break;}
				int ipNum[] = DYDCpUtils.getIPNumByHour();
				
				String tmpUrl = ipAddressList.get(new Random().nextInt(ipAddressList.size()));
				ipUrl = tmpUrl.split(",")[1];
				ipUrl = ipUrl +Test.getRangeRandomNum(ipNum[0], ipNum[1]);
				System.out.println("IP_ADDR="+ipUrl);
				//��ʱ��ȡip����ֹƵ�ʹ��߱���
				try {
					System.out.println("���ڵȴ�����ip...");
					Thread.sleep(Test.getRangeRandomNum(5*1000, 10*1000));
				} catch (Exception e) {
				}
				
				List<String> ipList = NetUtils.getIPList(ipUrl);
					
				System.out.println("ȡ��ip����:"+ipList.size());
				
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
				
				
				System.out.println("��ǰ����Сʱ��"+currentOpeationHour+"��,����Ŀ�꣺"+userMap.get(DMUtils.getHour())+"������Ŀ�꣺"+numMap.get(DMUtils.getHour()));
				System.out.println("==========��" + ZMContact.APPNAME +"����ˢ������:" + totalUserNum+",��ˢ������:"+oldUserActivteAppNum);
				
				if(isDelay)
					JzUtils.sleep();
				
			
		}
	}
	
	
	
	private void doNewUser(ZhimengDevice device,String appkey,String pkg,MyIP myip)throws Exception
	{
		if(stop)return;
		
		
		//��鵱ǰСʱ�Ƿ���ˢ��ָ���ļ�����
		boolean currHourCompleted = DJUtils.checkPoint(numMap, activteAppNum+oldUserActivteAppNum,currentOpeationHour);
		//��鵱ǰСʱ�Ƿ���ˢ��ָ�������û���
		boolean currHourUserCompleted = DJUtils.checkPoint(userMap, totalUserNum,currentOpeationHour);
		if(currHourUserCompleted && currHourCompleted){
			//��ȡ����ip��Ҫ��������ɵĻ���Сʱ�б���
			if(compList.size() > 0)
			{
				compList.remove(compList.size()-1);//ɾ��ǰһ������ɵ�ʱ��
			}
			
			compList.add(String.valueOf(currentOpeationHour));
			isShuaing = false;
			stop = true;
			activteAppNum = 0;
			oldUserActivteAppNum = 0;
			totalUserNum = 0;
			return;
		}
		
		
		if(!currHourUserCompleted)//��������û����ﵽĿ��������ˢ�����û���
		{
			
			List<Ad> list = Utils.getAds(device, myip);//��ȡ����б�
			if(list.size()>0){
				String data = Utils.getInitAdData(list);
				String resp = Utils.doAction(device, myip, data, true);
				System.out.println("��ʼ��post ���״̬:"+resp);
			}
			
			totalUserNum++;
			deviceCreator.saveInstalled(device);
			
		}

		//System.out.println("==========��" + AppContact.APPNAME +"����ˢ�����û�:" + totalUserNum);
	}
	
	
	private void doActive(ZhimengDevice device,MyIP myip)throws Exception
	{
		
			
			if(stop)return;
			
			//��鵱ǰСʱ�Ƿ���ˢ��ָ���ļ�����
			boolean currHourCompleted = DJUtils.checkPoint(numMap, activteAppNum+oldUserActivteAppNum,currentOpeationHour);
			//��鵱ǰСʱ�Ƿ���ˢ��ָ�������û���
			boolean currHourUserCompleted = DJUtils.checkPoint(userMap, totalUserNum,currentOpeationHour);
			if(currHourCompleted && currHourUserCompleted){
				//��ȡ����ip��Ҫ��������ɵĻ���Сʱ�б���
				if(compList.size() > 0)
				{
					compList.remove(compList.size()-1);//ɾ��ǰһ������ɵ�ʱ��
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
			 
			
			List<Ad> list = Utils.getAds(device, myip);//��ȡ����б�
			System.out.println("###ȡ�������:��"+list.size()+"��");
			if(list.size() == 0)return;
			if(list.size()>0){
				int receiveRandom = new Random().nextInt(100);
				//���ϼ��ʣ����͹�������
				if(receiveRandom>=0 && receiveRandom<30)
				{
					String data = Utils.getInitAdData(list);
					String resp = Utils.doAction(device, myip, data, true);
					System.out.println("��Ծ�û�--��ʼ��post ���״̬:"+resp);
				}
			}
			
			
			
			Ad ad = list.get(new Random().nextInt(list.size()));//������һ�����
			list.remove(ad);

			int cpClickRandom = new Random().nextInt(100);
			//���������40%
			if(cpClickRandom>=0 && cpClickRandom<ZMContact.CLICK_RATE){
				
				//����
				ZMDownload download = new ZMDownload(new URL(ad.getUrl()), device.getUa(), myip);
				download.toDownload();
				
				
				//90%�����������
				int downdoneRandom = new Random().nextInt(100);
				if(downdoneRandom>=0 && downdoneRandom<90){
					//15%�ļ���ȥ��װ
					int installRandom = new Random().nextInt(100);
					if(installRandom>=0 && installRandom<ZMContact.INSTALL_RATE){
						Thread.sleep(Test.getRangeRandomNum(2, 5)*1000);//ģ�ⰲװʱ��
						
						
						int moreActRandom = new Random().nextInt(100);
						//30%�ļ���һ���豸ֻ����һ�����
						 if(moreActRandom>=0 && moreActRandom <30){
							 //��ȥ�ظ�����
							if(device.getPkgs() != null && device.getPkgs().contains(ad.getPname())){
								return;
							}
							 
							 String data = Utils.getAdData(ad, ZMContact.INSTALL);
							 String resp = Utils.doAction(device, myip, data, false);
							 System.out.println(ad.getPname()+ ",��װ���:"+resp);
							
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
							 //70%����2�����
							 if(actTwoAdRan>=0 && actTwoAdRan<70){
								 for(int k=0;k<2;k++){
									 Ad _ad = list.get(new Random().nextInt(list.size()));
									 list.remove(_ad);
									//��ȥ�ظ�����
									if(device.getPkgs() != null && device.getPkgs().contains(_ad.getPname())){
											continue;
									}
									 String pkgs = device.getPkgs()==null?"":device.getPkgs();
									 device.setPkgs(pkgs+","+_ad.getPname());
									 toActAds.add(_ad);
									 
								 }
							 }else{//30%����3�����
								 for(int k=0;k<3;k++){
									 Ad _ad = list.get(new Random().nextInt(list.size()));
									 list.remove(_ad);
									//��ȥ�ظ�����
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
							 System.out.println("��"+toActAds.size()+ "�������,��װ���:"+resp);
							 
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
					//���û�а�װ����ô�������������
					else{
						String data = Utils.getAdData(ad, ZMContact.DOWN);
						Utils.doAction(device, myip, data, false);
					}
					
					
				}
				//���û��������ɵĻ�
				else{
					String data = Utils.getAdData(ad, ZMContact.CLICK);
					Utils.doAction(device, myip, data, false);
				}
				
			}
			//���û������Ǿ���չʾ
			else{
				String data = Utils.getAdData(ad, ZMContact.SHOW);
				Utils.doAction(device, myip, data, false);
			}
	
			
			
		//	System.out.println("==========��" + AppContact.APPNAME +"����Ծ�û���ˢ����Ӧ��:" + oldUserActivteAppNum);
		
			
		
	}
	
	//ˢ��Ծ�û��Ĳ���
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
					if(count >= 5) {//������ip�Ѿ�����3��imei������ˣ��������豸
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
