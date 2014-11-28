package com.ad.qm;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.ad.panda.MD5;
import org.herojohn.adrobot.device.DeviceFactory;
import org.herojohn.adrobot.device.creator.impl.QumiDeviceCreator;
import org.herojohn.adrobot.device.model.DeviceConfig;
import org.herojohn.adrobot.device.model.QumiDevice;
import org.nice.NetUtils;
import org.nice.Test;
import org.nice.Utils;

import com.ad.dianjoy.DJContacts;
import com.ad.dianjoy.DJUtils;
import com.ad.dianjoy.DianJoy;
import com.ad.doumob.DMContacts;
import com.ad.doumob.DMUtils;
import com.newqm.sdkoffer.Qmt;

public class QMUtils {

	//&carrier_name=China Mobile 4G | China Mobile 3G&carrier_country_code=cn&mobile_country_code=46000

	//exp.qumi.com	/api/sdk/wallboard?app_code=3d8bb6a81401aac6&app_secretkey=5c2db2f955a57056&channel_id=&udid=489ea6d44a165604&imsi=460028103162858&imsied=1&imei=863184026186609&device_name=Lenovo%20A788t&device_manufacturer=LENOVO&device_type=android&os_version=4.3&country_code=CN&language_code=zh&app_version_code=45&package_name=org.joe.game.runnershigh&library_version=5.5.6.2.8&platform=android&&carrier_name=China%20Mobile%204G%20%7C%20China%20Mobile%203G&carrier_country_code=cn&mobile_country_code=46000&screen_density=240&screen_layout_size=2&connection_type=wifi&screen_height=480&screen_width=854&timestamp=1402044316&point_user_id=863184026186609&
	
	private static QumiDeviceCreator deviceCreator = (QumiDeviceCreator) DeviceFactory.getInstance("qumi");
	public static void main(String[] args) {

		try {
			int num = 0;
			Map<Integer,Integer> m = getHourCountMap(88);
			Set<Integer> key = m.keySet();
			 for (Iterator it = key.iterator(); it.hasNext();) {
		            Integer h = (Integer) it.next();
		           System.out.println(h +"="+m.get(h));
		           num += m.get(h);
		        }
			 System.out.println("num="+num);
			QMContacts.APP_PKG="com.a";
			QMContacts.APPVC="10";
			QMContacts.APPCODE = "5208ebe102c79e43";//"5208ebe102c79e43";
			QMContacts.APPKEY = "33be9768074e7057";//"33be9768074e7057";
			
			deviceCreator.init(new DeviceConfig(6,"test",1,80));
			QumiDevice d =  deviceCreator.getDevice();
			//d.setImei("861884010123714");
			System.out.println("imei="+d.getImei());
			
			System.out.println(getCarrierName(d.getImsi()));
			String ip="111.72.171.121";
			String port="18186";
			QM.localIP  = "183.12.64.91";
		     	Proxy p= null;
		    	long time = System.currentTimeMillis() / 1000;
			/*	String json = userInit(d, time, p);
				System.out.println("新增用户:"+json);
			//http://exp.qumi.com/api/sdk/wallboard?app_code=4d42e18857108ef0&app_secretkey=56cd94f1548bb6a8&channel_id=&udid=56b891ae2b9e1076&imsi=460070924946837&imsied=1&imei=862413015494661&device_name=hs-u8&device_manufacturer=hisense&device_type=android&os_version=2.3.6&country_code=CN&language_code=zh&app_version_code=105&package_name=com.a49971169&library_version=5.5.6.2.8&platform=android&&screen_density=320&screen_layout_size=2&connection_type=wifi&screen_height=480&screen_width=800&timestamp=1401350407&point_user_id=862413015494661&
			//http://exp.qumi.com/api/sdk/wallboard?app_code=56cd94f1548bb6a8&app_secretkey=4d42e18857108ef0&channel_id=&udid=1029b4349dfe18fe&imsi=310260524316454&imsied=1&imei=350577656536335&device_name=GT-N7000&device_manufacturer=samsung&device_type=android&os_version=4.0.4&country_code=CN&language_code=zh&app_version_code=17&package_name=com.evy.guessword&library_version=5.5.6.2.8&platform=android&&carrier_name=T-Mobile&carrier_country_code=us&mobile_country_code=310260&screen_density=160&screen_layout_size=3&connection_type=wifi&screen_height=1232&screen_width=720&timestamp=1402553500&point_user_id=350577656536335&
			String getPointResp = getPoint(d, time, p);
			System.out.println("积分="+getPointResp);
			String ipdata = JsonParse.getIPData(json);
			deviceCreator.saveInstalled(d);*/
		/*String s = openWall(d,  time, 1, p);
		List<QMAd> list =  HtmlParse.parseAdList(s);
			
		System.out.println("ad List size="+list.size());
		QMAd ad = list.get(0);
		System.out.println("name:"+ad.getName());
		System.out.println("detial url="+ad.getDetailUrl());
		String detailResp = openDetail(d, ad.getDetailUrl(), p);
		String downUrl = HtmlParse.parseDownloadUrl(detailResp);
		String adPkg = HtmlParse.parseAdPkg(detailResp);
		System.out.println("adPkg="+adPkg);
		System.out.println("downurl="+downUrl);*/
		
		
	/*	String downUrl = "http://node.cache.qumi.com/files/ads/human/A1012-20140609.apk";
		QMDown download = new QMDown(new URL(downUrl), "", p);
		long fileSize = download.toDownload();
		System.out.println("fileSize="+Math.ceil((double)fileSize/1024/1024));*/
		
		
		/*Thread.sleep(1000);
		
		String downDoneResp = doAction(d, ad, time, ipdata, QMContacts.DOWN, p);
		System.out.println("下载完成:"+downDoneResp);
		
		Thread.sleep(2000);
		
		String installDoneResp = doAction(d, ad, time, ipdata, QMContacts.INSTALL, p);
		System.out.println("安装完成:"+installDoneResp);
		
		
		String activeDoneResp = doAction(d, ad, time, ipdata, QMContacts.ACTIVE, p);
		System.out.println("激活完成:"+activeDoneResp);*/
		
		
		
		
		
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	//新增、活跃首次调用
	public static String userInit(QumiDevice device,long timeStamp,Proxy p)throws Exception
	{
		System.out.println("【" + QMContacts.WHICH_IP +"】Action:刷userInit接口...当前操作小时【" + QM.currentOpeationHour + "】,【"+QMContacts.APP_NAME+"】");
		
		String resp = "";
		String udid = MD5.hash(device.getImei() + "fuck" + device.getModel()).toLowerCase();
		udid = udid.substring(0,16);
		String imei = device.getImei();
		String imsi = device.getImsi();
		String model = device.getModel();
		String deviceName = device.getName();
		String osVer = device.getOsVersion();
		String appVC = QMContacts.APPVC;
		String appPkg = QMContacts.APP_PKG;
		String libVer = QMContacts.LIB_VER;
		String appCode = QMContacts.APPCODE;
		String appKey = QMContacts.APPKEY;
		String density = getDensity(device);
		int layoutSize = getLayoustSize(device);
		int width = device.getWidth();
		int height = device.getHeight();
		String net = getNetWork(device.getNetworkType());
		
		String data = "app_code="+appCode+"&app_secretkey="+appKey+"&channel_id=&udid="+udid+"&imsi="+imsi+
				"&imsied=1&imei="+imei+"&device_name="+model+"&device_manufacturer="+deviceName+"&device_type=android&os_version="+osVer+
				"&country_code=CN&language_code=zh&app_version_code="+appVC+"&package_name="+appPkg+"&library_version="+libVer+
				"&platform=android&&carrier_name="+getCarrierName(imsi)+"&carrier_country_code=cn&mobile_country_code="+ getMobileCountryCode(imsi)+
				"&screen_density="+density+"&screen_layout_size="+layoutSize+"&connection_type="+net+
				"&screen_height="+height+"&screen_width="+width+"&timestamp="+timeStamp+"&point_user_id="+imei+"&";
		//app_code=0600adfe36e8443b&app_secretkey=3f89c4bb14c81d6c&channel_id=&udid=1029b4349dfe18fe&imsi=310260524316454&
		//imsied=1&imei=350577656536335&device_name=GT-N7000&device_manufacturer=samsung&device_type=android&os_version=4.0.4&
		//country_code=CN&language_code=zh&app_version_code=1&package_name=com.example.qumidemo&library_version=5.5.6.2.8&platform=android&
		//&carrier_name=T-Mobile&carrier_country_code=us&mobile_country_code=310260&screen_density=160&screen_layout_size=3&connection_type=wifi&screen_height=672&screen_width=1280&timestamp=1400821032&point_user_id=350577656536335&

		//System.out.println("d="+data);
		
		String url = "http://cfg.qumi.com/api/sdk/connect?data=";
		
		data=  data.replaceAll(" ", "%20");
		String encodeData = URLEncoder.encode(Qmt.authcodeEncode(data,imei),"utf-8");
		url = url +encodeData;
		
		resp  = QMNet.doGet(url, p, imei, "cfg.qumi.com", device.getUa());
		
		
		return resp;
	}
	
	public static String openWall(QumiDevice device,long timeStamp,int pageNum,Proxy p)throws Exception
	{
		
		System.out.println("【" + QMContacts.WHICH_IP +"】Action:刷广告列表接口...当前操作小时【" + QM.currentOpeationHour + "】,新增用户(" + QM.userMap.get(DMUtils.getHour()) +"/" + QM.tartgetNum+")" +
				",激活应用(" +QM.numMap.get(DMUtils.getHour()) +"/" + QM.targetActiviteAppNum + ")" + ",【"+QMContacts.APP_NAME+"】");
		
		String resp = "";
		String udid = MD5.hash(device.getImei() + "fuck" + device.getModel()).toLowerCase();
		udid = udid.substring(0,16);
		String imei = device.getImei();
		String imsi = device.getImsi();
		String model = device.getModel();
		String deviceName = device.getName();
		String osVer = device.getOsVersion();
		String appVC = QMContacts.APPVC;
		String appPkg = QMContacts.APP_PKG;
		String libVer = QMContacts.LIB_VER;
		String appCode = QMContacts.APPCODE;
		String appKey = QMContacts.APPKEY;
		String density = getDensity(device);
		int layoutSize = getLayoustSize(device);
		int width = device.getWidth();
		int height = device.getHeight();
		String net = getNetWork(device.getNetworkType());
		
		String data = "app_code="+appCode+"&app_secretkey="+appKey+"&channel_id=&udid="+udid+"&imsi="+imsi+
				"&imsied=1&imei="+imei+"&device_name="+model+"&device_manufacturer="+deviceName+"&device_type=android&os_version="+osVer+
				"&country_code=CN&language_code=zh&app_version_code="+appVC+"&package_name="+appPkg+"&library_version="+libVer+
				"&platform=android&&carrier_name="+getCarrierName(imsi)+"&carrier_country_code=cn&mobile_country_code="+ getMobileCountryCode(imsi)+
				"&screen_density="+density+"&screen_layout_size="+layoutSize+"&connection_type="+net+
				"&screen_height="+height+"&screen_width="+width+"&timestamp="+timeStamp+"&point_user_id="+imei+"&";
		
		if(pageNum >1)
			data += "flagid=-1&page="+pageNum+"&rt=" + System.currentTimeMillis();
		
		String url = "http://exp.qumi.com/api/sdk/wallboard?";
		url += data;
		resp  = QMNet.doGet(url, p, "", "exp.qumi.com", device.getBrowserUa());
		
		return resp;
	}
	
	
	public static String openDetail(QumiDevice device,String url,Proxy p)throws Exception
	{
		System.out.println("【" + QMContacts.WHICH_IP +"】Action:刷详情接口...当前操作小时【" + QM.currentOpeationHour + "】,【"+QMContacts.APP_NAME+"】");
		return QMNet.doGet(url, p, "", "exp.qumi.com", device.getBrowserUa());
	}
	
	//下载完成或者安装完成或者给积分奖励调用
	public static String doAction(QumiDevice device,QMAd ad,long timeStamp,String ipdata,int flag,Proxy p)throws Exception
	{
		System.out.println("【" + QMContacts.WHICH_IP +"】Action:刷doAction接口...当前操作小时【" + QM.currentOpeationHour + "】,【"+QMContacts.APP_NAME+"】");
		String resp = "";
		String udid = MD5.hash(device.getImei() + "fuck" + device.getModel()).toLowerCase();
		udid = udid.substring(0,16);
		String imei = device.getImei();
		String imsi = device.getImsi();
		String model = device.getModel();
		String deviceName = device.getName();
		String osVer = device.getOsVersion();
		String appVC = QMContacts.APPVC;
		String appPkg = QMContacts.APP_PKG;
		String libVer = QMContacts.LIB_VER;
		String appCode = QMContacts.APPCODE;
		String appKey = QMContacts.APPKEY;
		String density = getDensity(device);
		int layoutSize = getLayoustSize(device);
		int width = device.getWidth();
		int height = device.getHeight();
		String net = getNetWork(device.getNetworkType());
		
		String data = "app_code="+appCode+"&app_secretkey="+appKey+"&channel_id=&udid="+udid+"&imsi="+imsi+
				"&imsied=1&imei="+imei+"&device_name="+model+"&device_manufacturer="+deviceName+"&device_type=android&os_version="+osVer+
				"&country_code=CN&language_code=zh&app_version_code="+appVC+"&package_name="+appPkg+"&library_version="+libVer+
				"&platform=android&&carrier_name="+getCarrierName(imsi)+"&carrier_country_code=cn&mobile_country_code="+ getMobileCountryCode(imsi)+
				"&screen_density="+density+"&screen_layout_size="+layoutSize+"&connection_type="+net+
				"&screen_height="+height+"&screen_width="+width+"&timestamp="+timeStamp+"&point_user_id="+imei+"&";
		String activeParmeter = "";
		if(flag == QMContacts.ACTIVE)activeParmeter = "&active_count=0";
		data += "ad_id="+ ad.getAdId() +activeParmeter + "&track_id="+ad.getTruckId()+"&";		
		data=  data.replaceAll(" ", "%20");
		String encodeData = URLEncoder.encode(Qmt.authcodeEncode(data,ipdata),"utf-8");
		String url = "";
		if(flag == QMContacts.DOWN)//下载完成
			url = "http://exp.qumi.com/api/sdk/wallboard/stat/downloaded?data=" + encodeData;
		else if(flag == QMContacts.INSTALL)//安装完成，已计费
			url = "http://exp.qumi.com/api/sdk/wallboard/stat/installed?data=" + encodeData;
		else if(flag == QMContacts.ACTIVE)//送积分，奖励用户
			url = "http://exp.qumi.com/api/sdk/wallboard/stat/actived?data=" + encodeData;
		else if(flag == QMContacts.GETPOINT)//获取积分
			url = "http://exp.qumi.com/api/sdk/wallboard/point/get?data=" + encodeData;
		resp  = QMNet.doGet(url, p, imei, "exp.qumi.com", device.getUa());
		return resp;
	}
	
	//获取用户积分
	public static String getPoint(QumiDevice device,long timeStamp,Proxy p)throws Exception
	{
		
		System.out.println("【" + QMContacts.WHICH_IP +"】Action:刷获取用户积分接口...");
		
		String resp = "";
		String udid = MD5.hash(device.getImei() + "fuck" + device.getModel()).toLowerCase();
		udid = udid.substring(0,16);
		String imei = device.getImei();
		String imsi = device.getImsi();
		String model = device.getModel();
		String deviceName = device.getName();
		String osVer = device.getOsVersion();
		String appVC = QMContacts.APPVC;
		String appPkg = QMContacts.APP_PKG;
		String libVer = QMContacts.LIB_VER;
		String appCode = QMContacts.APPCODE;
		String appKey = QMContacts.APPKEY;
		String density = getDensity(device);
		int layoutSize = getLayoustSize(device);
		int width = device.getWidth();
		int height = device.getHeight();
		String net = getNetWork(device.getNetworkType());
		
		String data = "app_code="+appCode+"&app_secretkey="+appKey+"&channel_id=&udid="+udid+"&imsi="+imsi+
				"&imsied=1&imei="+imei+"&device_name="+model+"&device_manufacturer="+deviceName+"&device_type=android&os_version="+osVer+
				"&country_code=CN&language_code=zh&app_version_code="+appVC+"&package_name="+appPkg+"&library_version="+libVer+
				"&platform=android&&carrier_name="+getCarrierName(imsi)+"&carrier_country_code=cn&mobile_country_code="+ getMobileCountryCode(imsi)+
				"&screen_density="+density+"&screen_layout_size="+layoutSize+"&connection_type="+net+
				"&screen_height="+height+"&screen_width="+width+"&timestamp="+timeStamp+"&point_user_id="+imei+"&";
		
		//app_code=0600adfe36e8443b&app_secretkey=3f89c4bb14c81d6c&channel_id=&udid=1029b4349dfe18fe&imsi=310260524316454&
		//imsied=1&imei=350577656536335&device_name=GT-N7000&device_manufacturer=samsung&device_type=android&os_version=4.0.4&
		//country_code=CN&language_code=zh&app_version_code=1&package_name=com.example.qumidemo&library_version=5.5.6.2.8&platform=android&
		//&carrier_name=T-Mobile&carrier_country_code=us&mobile_country_code=310260&screen_density=160&screen_layout_size=3&connection_type=wifi&screen_height=672&screen_width=1280&timestamp=1400821032&point_user_id=350577656536335&

		
		String url = "http://exp.qumi.com/api/sdk/wallboard/point/get?data=";
		
		data=  data.replaceAll(" ", "%20");
		String encodeData = URLEncoder.encode(Qmt.authcodeEncode(data,imei),"utf-8");
		url = url +encodeData;
		
		resp  = QMNet.doGet(url, p, imei, "exp.qumi.com", device.getUa());
		
		return resp;
	}
	
	//增加、扣取积分
	public static String giveAndSpendPoint(QumiDevice device,long timeStamp,int type,int point,Proxy p)throws Exception
	{
		
		String resp = "";
		String udid = MD5.hash(device.getImei() + "fuck" + device.getModel()).toLowerCase();
		udid = udid.substring(0,16);
		String imei = device.getImei();
		String imsi = device.getImsi();
		String model = device.getModel();
		String deviceName = device.getName();
		String osVer = device.getOsVersion();
		String appVC = QMContacts.APPVC;
		String appPkg = QMContacts.APP_PKG;
		String libVer = QMContacts.LIB_VER;
		String appCode = QMContacts.APPCODE;
		String appKey = QMContacts.APPKEY;
		String density = getDensity(device);
		int layoutSize = getLayoustSize(device);
		int width = device.getWidth();
		int height = device.getHeight();
		String net = getNetWork(device.getNetworkType());
		
		String data = "app_code="+appCode+"&app_secretkey="+appKey+"&channel_id=&udid="+udid+"&imsi="+imsi+
				"&imsied=1&imei="+imei+"&device_name="+model+"&device_manufacturer="+deviceName+"&device_type=android&os_version="+osVer+
				"&country_code=CN&language_code=zh&app_version_code="+appVC+"&package_name="+appPkg+"&library_version="+libVer+
				"&platform=android&&carrier_name="+getCarrierName(imsi)+"&carrier_country_code=cn&mobile_country_code="+ getMobileCountryCode(imsi)+
				"&screen_density="+density+"&screen_layout_size="+layoutSize+"&connection_type="+net+
				"&screen_height="+height+"&screen_width="+width+"&timestamp="+timeStamp+"&point_user_id="+imei+"&&point=" + point +"&" ;
		
		//app_code=0600adfe36e8443b&app_secretkey=3f89c4bb14c81d6c&channel_id=&udid=1029b4349dfe18fe&imsi=310260524316454&
		//imsied=1&imei=350577656536335&device_name=GT-N7000&device_manufacturer=samsung&device_type=android&os_version=4.0.4&
		//country_code=CN&language_code=zh&app_version_code=1&package_name=com.example.qumidemo&library_version=5.5.6.2.8&platform=android&
		//&carrier_name=T-Mobile&carrier_country_code=us&mobile_country_code=310260&screen_density=160&screen_layout_size=3&connection_type=wifi&screen_height=672&screen_width=1280&timestamp=1400821032&point_user_id=350577656536335&

		
		String url = "";
		if(type == QMContacts.GIVE_POINT)
			url = "http://exp.qumi.com/api/sdk/wallboard/point/award?data=";
		else
			url = "http://exp.qumi.com/api/sdk/wallboard/point/spend?data=";
		
		data=  data.replaceAll(" ", "%20");
		String encodeData = URLEncoder.encode(Qmt.authcodeEncode(data,imei),"utf-8");
		url = url +encodeData;
		
		resp  = QMNet.doGet(url, p, imei, "exp.qumi.com", device.getUa());
		
		if(type == QMContacts.GIVE_POINT)System.out.println("增加积分:"+resp);
		else System.out.println("消耗积分:"+resp);
		
		return resp;
	}
	
	//获取密度
	public static String getDensity(QumiDevice device)
	{
		String density = "";
		int w = device.getWidth();
		if(w== 720 || w==800)
		{	
				density="320";
		}
		else if(w ==1080)
		{
				density = "480";
		}
		else if(w ==480)
		{
				density = "240";
		}
		else if(w == 320)
		{
			density = "160";
		}
		else
		{
			density = "320";
		}
		
		return density;
	}
	//获取layoutsize
	public static int getLayoustSize(QumiDevice device)
	{
		int layoutSize = 0;
		int w = device.getWidth();
		if(w== 720 || w==800)
		{	
				layoutSize = 2;
		}
		else if(w ==1080)
		{
				layoutSize = 2;
		}
		else if(w ==480)
		{
				layoutSize = 2;
		}
		else if(w == 320)
		{
			layoutSize = 1;
		}
		else
		{
			layoutSize = 2;
		}
		return layoutSize;
	}
	
	  public static String getNetWork(int type) {
			String net = "wifi";
			switch(type) {
				case DMContacts.CMCC : 
					net = "mobile";
//					int random = new Random().nextInt(100);
//					if(random >=0 && random < 40)
//						net = "mobile,cmtds,TD-SCDMA,3";
					break;
				case DMContacts.CT : net = "mobile";break;
				case DMContacts.UNICOM : net = "mobile";break;
				default:net = "wifi";break;
			}
			return net;
		}
	  
	  //返回sim卡网络类型
	  private static String getCarrierName(String IMSI)
	  {
		  String result = "";
		  try {
			  
			 /* //中国联通
			  if(IMSI.startsWith("46001") || IMSI.startsWith("46006")){
				  String[] s= {"ChinaUnicom","China Unicom","中国联通","CHN-CUGSM","CHN-UNICOM"};
				  result = s[new Random().nextInt(s.length)];
			  }
				//中国移动
			  if(IMSI.startsWith("46000") || IMSI.startsWith("46002") || IMSI.startsWith("46007")){
				  String[] s= {"ChinaMobile","China Mobile","中国移动"};
				  result = s[new Random().nextInt(s.length)];
			  }
			  
			  //中国电信
			  if(IMSI.startsWith("46003") || IMSI.startsWith("46005")){
				  String[] s= {"ChinaTelecom","China Telecom","中国电信"};
				  result = s[new Random().nextInt(s.length)];
			  }*/
			  
			  result =  URLEncoder.encode(getRandomNetworkOperatorName(IMSI), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		  
		  return result;
	  }
	  
	//传入当前的imsi得到运营商名字
		public  static String getRandomNetworkOperatorName(String imsi) {
			if (imsi.startsWith("46000") || imsi.startsWith("46002")
					|| imsi.startsWith("46007")) {
				String[] chinaMobile = {"中国移动",
										"CHINA MOBILE",
										"",
										"CMCC",
										"CHINA MOBILE 3G",
										"中国移动,null",
										"null",
										"中国移动-3G",
										"中国移动 3G",
										"46002",
										"null,null",
										"null,中国移动",
										"China Mobile,null",
										"中国移动，中国移动"};			
				int ran = new Random().nextInt(7000);
				try {
					if(ran >= 0 && ran < 5000)    return chinaMobile[0];
					if(ran >= 5000 && ran < 6000) return chinaMobile[1];
					if(ran >= 6000 && ran < 6400) return chinaMobile[2];
					if(ran >= 6400 && ran < 6650) return chinaMobile[3];
					if(ran >= 6650 && ran < 6750) return chinaMobile[4];
					if(ran >= 6750 && ran < 6800) return chinaMobile[5];
					if(ran >= 6800 && ran < 6850) return chinaMobile[6];
					if(ran >= 6850 && ran < 6870) return chinaMobile[7];
					if(ran >= 6870 && ran < 6890) return chinaMobile[8];
					if(ran >= 6890 && ran < 6910) return chinaMobile[9];
					if(ran >= 6910 && ran < 6930) return chinaMobile[10];
					if(ran >= 6930 && ran < 6950) return chinaMobile[11];
					if(ran >= 6950 && ran < 6970) return chinaMobile[12];
					if(ran >= 6970 && ran < 6990) return chinaMobile[13];
					if(ran >= 6990 && ran < 7000) return chinaMobile[14];
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (imsi.startsWith("46001") || imsi.startsWith("46006")) {
				String[] chinaUnicom = {"中国联通",
										"CHN-UNICOM",
										"China Unicom",
										"CHN-CUGSM",
										"null",
										"CHINA UNICOM",
										"中国联通,中国移动",
										"null,中国联通",
										"null,null",
										"中国联通,中国联通",
										"中国联通,null",
										"China Unicom,null"};			
				int ran = new Random().nextInt(2000);
				try {
					if(ran >= 0 && ran < 1600)    return chinaUnicom[0];
					if(ran >= 1600 && ran < 1800) return chinaUnicom[1];
					if(ran >= 1800 && ran < 1900) return chinaUnicom[2];
					if(ran >= 1900 && ran < 1910) return chinaUnicom[3];
					if(ran >= 1910 && ran < 1920) return chinaUnicom[4];
					if(ran >= 1920 && ran < 1930) return chinaUnicom[5];
					if(ran >= 1930 && ran < 1940) return chinaUnicom[6];
					if(ran >= 1940 && ran < 1950) return chinaUnicom[7];
					if(ran >= 1950 && ran < 1960) return chinaUnicom[8];
					if(ran >= 1960 && ran < 1970) return chinaUnicom[9];
					if(ran >= 1970 && ran < 1980) return chinaUnicom[10];
					if(ran >= 1980 && ran < 1990) return chinaUnicom[11];
					if(ran >= 1990 && ran < 2000) return chinaUnicom[12];
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (imsi.startsWith("46003") || imsi.startsWith("46005")) {
				String[] chinaTelecom = {"中国电信",
										 "China Telecom",
										 "null",
										 "46003",
										 "CTC",
										 "null,null",
										 "中国电信,null",
										 "ctnet"};			
				int ran = new Random().nextInt(1000);
				try {
					if(ran >= 0 && ran < 400)   return chinaTelecom[0];
					if(ran >= 400 && ran < 650) return chinaTelecom[1];
					if(ran >= 650 && ran < 700) return chinaTelecom[2];
					if(ran >= 700 && ran < 750) return chinaTelecom[3];
					if(ran >= 750 && ran < 800) return chinaTelecom[4];
					if(ran >= 800 && ran < 850) return chinaTelecom[5];
					if(ran >= 850 && ran < 900) return chinaTelecom[6];
					if(ran >= 900 && ran < 950) return chinaTelecom[7];
					if(ran >= 950 && ran < 1000)return chinaTelecom[8];
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				return "";
			}
			return null;
		}
	  
	  private static String getMobileCountryCode(String IMSI){
		  String result = "";
		  if(IMSI != null && !"".equals(IMSI))result =  IMSI.substring(0,5);
		  return result;
	  }
	
		//设置要刷激活的应用数量，算出每个小时需要激活的次数
		public static Map<Integer,Integer> getHourCountMap(int targetActiveNum)
		{
			Map<Integer,Integer> numMap = new HashMap<Integer,Integer>();
			Map<Integer,Float> m = new HashMap<Integer,Float>();
			m.put(0, new Float(getRangeRandomNum(1, 3)));
			m.put(1, new Float(getRangeRandomNum(1, 8)));
			m.put(2, new Float(getRangeRandomNum(1, 3)));
			m.put(3, new Float(getRangeRandomNum(1, 5)));
			m.put(4, 0.5f);
			m.put(5, new Float(getRangeRandomNum(1, 8)));
			m.put(6, 0.5f);
			m.put(7, new Float(getRangeRandomNum(2, 4)));
			m.put(8, new Float(getRangeRandomNum(1, 3)));
			m.put(9, new Float(getRangeRandomNum(1, 4)));
			m.put(10, new Float(getRangeRandomNum(2, 4)));
			m.put(11, new Float(getRangeRandomNum(2, 10)));
			m.put(12, new Float(getRangeRandomNum(3,15)));
			m.put(13, new Float(getRangeRandomNum(4, 7)));
			m.put(14,new Float(getRangeRandomNum(4, 6)));
			m.put(15,new Float(getRangeRandomNum(3, 6)));
			m.put(16, new Float(getRangeRandomNum(1, 10)));
			m.put(17, new Float(getRangeRandomNum(1, 15)));
			m.put(18, new Float(getRangeRandomNum(5, 7)));
			m.put(19, new Float(getRangeRandomNum(6, 15)));
			m.put(20, new Float(getRangeRandomNum(5,13)));
			m.put(21, new Float(getRangeRandomNum(6, 10)));
			m.put(22, new Float(getRangeRandomNum(3, 10)));
			m.put(23, new Float(getRangeRandomNum(2, 8)));
			
			
			 Set<Integer> key = m.keySet();
		     for (Iterator it = key.iterator(); it.hasNext();) {
		            Integer h = (Integer) it.next();
		            float rate = m.get(h);
		            int num = Math.round(targetActiveNum * rate/100);
		            if(num==0)num=1;
		            numMap.put(h, num);
		        }
			
			return numMap;
		}
	  
		//指定范围内随机数
		public static int getRangeRandomNum(int min,int max)
		{
			Random random = new Random();
			int s = random.nextInt(max)%(max-min+1) + min;
			return s;
		}
		
		
		//根据文件尺寸来做下载延时
		public static void sleepByFileSize(double size){
			try {
				size = Math.ceil((double)size/1024/1024);
				System.out.println("下载文件大小:"+size + " MB");
				
				if(size >0 && size <5)Thread.sleep(getRangeRandomNum(5, 8));
				else if(size>5 && size<10)Thread.sleep(getRangeRandomNum(8, 15));
				else if(size>10 && size<20)Thread.sleep(getRangeRandomNum(12, 20));
				else if(size>20 && size<40)Thread.sleep(getRangeRandomNum(20, 30));
				else Thread.sleep(getRangeRandomNum(30, 50));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		//根据时间调整延时
		public static void sleep()
		{
			int h = DMUtils.getHour();
			try {
				
				String weekOfDate = DMUtils.getWeekOfDate();//获得是星期几
				
				
				//如果是周末
				if("星期六".equals(weekOfDate) || "星期日".equals(weekOfDate))
				{
					if(h >=11 && h <=13)//中午时间
						Thread.sleep(Test.getRangeRandomNum(20*1188, 40*1055));
					else if(h>=19 && h<=23)//晚间高峰
						Thread.sleep(Test.getRangeRandomNum(50*1188, 60*1055));
					else if(h>=0&& h<7)//凌晨到早上
						Thread.sleep(Test.getRangeRandomNum(60*1188, 1*60*1055));
					else if(h>=7 && h<11)//7点到上午11点前
						Thread.sleep(Test.getRangeRandomNum(60*1188, 2*60*1055));
					else if(h>13 && h<19) //下午1点到晚上7点前
						Thread.sleep(Test.getRangeRandomNum(40*1188, 50*1055));
				}
				else
				{
					if(h >=11 && h <=13)//中午时间
						Thread.sleep(Test.getRangeRandomNum(20*1188, 40*1055));
					else if(h>=19 && h<=23)//晚间高峰
						Thread.sleep(Test.getRangeRandomNum(50*1188, 60*1055));
					else if(h>=0&& h<7)//凌晨到早上
						Thread.sleep(Test.getRangeRandomNum(60*1188, 1*60*1055));
					else if(h>=7 && h<11)//7点到上午11点前
						Thread.sleep(Test.getRangeRandomNum(60*1188, 2*60*1055));
					else if(h>13 && h<19) //下午1点到晚上7点前
						Thread.sleep(Test.getRangeRandomNum(40*1188, 50*1055));
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
}
