package com.ad.appmob;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.herojohn.adrobot.device.DeviceFactory;
import org.herojohn.adrobot.device.creator.impl.AppmobDeviceCreator;
import org.herojohn.adrobot.device.creator.impl.AppmobDeviceCreator;
import org.herojohn.adrobot.device.model.AppmobDevice;
import org.herojohn.adrobot.device.model.DeviceConfig;
import org.herojohn.adrobot.device.model.AppmobDevice;

import com.ad.doumob.DMContacts;
import com.ad.doumob.DMUtils;
import com.ad.dyd.DYDContacts;

public class Utils {

	private static AppmobDeviceCreator deviceCreator = (AppmobDeviceCreator) DeviceFactory.getInstance("appmob");
	
	public static void main(String[] args) {
		try {
			AppContact.APPID = "de52589beb025f2997d260d61baba407";
			AppContact.APPVer = "1.0";
			AppContact.APPVC = "1";
			AppContact.APPNAME = "文怡家常菜";
			AppContact.APPPKG = "org.nice.caipu";
			
			//list
			//http://api.is.139mob.com/is/list.jsp?appid=de52589beb025f2997d260d61baba407&apptype=1&appVersion=1.0&appVersionInt=1&appname=文怡家常菜&sysApp=0&country=CN&lang=zh&udid=357806045565628&imsi=460075786406321&carrier=%E4%B8%AD%E5%9B%BD%E7%A7%BB%E5%8A%A8&manufacturer=samsung&model=gt-i9108&net=wifi&networkOperator=%E4%B8%AD%E5%9B%BD%E7%A7%BB%E5%8A%A8&osVersion=2.3.6&osVersionInt=10&packagename=org.nice.caipu&screen=800x480&sdkVersion=2.0.8&sign=null&channelid=appmob&bssid=e2:3b:b1:17:36:79&pushFlag=true&adType=1&page=1

			//info
			//http://api.is.139mob.com/is/info.jsp?appid=de52589beb025f2997d260d61baba407&apptype=1&appVersion=1.0&appVersionInt=1&appname=文怡家常菜&sysApp=0&country=CN&lang=zh&udid=356812044097648&imsi=460001289050576&carrier=CHINA+MOBILE&manufacturer=htc&model=desirehd&net=wifi&networkOperator=CHINA+MOBILE&osVersion=2.3.3&osVersionInt=10&packagename=org.nice.caipu&screen=800x480&sdkVersion=2.0.8&sign=null&channelid=appmob&bssid=c0:57:dc:48:9d:a8&pushFlag=true&adType=1&adCount=4
			
			MyIP myip = null;
			deviceCreator.init(new DeviceConfig(6,"test"));
			AppmobDevice device = deviceCreator.getDevice();
			requestInfo(device, myip);
			
			
			
			/*String s1 = init(AppContact.APPID, myip);
			System.out.println(s1);
			String s2 = init(device, myip);
			System.out.println(s2);
			
			List<Ad> list = requestInfo(device,myip);*/
			/*Ad ad = list.get(1);
			System.out.println("adname="+ad.getAdName());
			String resp = doAction(device, ad, p, AppContact.SHOW, "");
			System.out.println("show resp="+resp);
						
			String clickResp = doAction(device, ad, p, AppContact.CLICK, "");
			System.out.println("clickResp="+clickResp);
			Download down = new Download(new URL(ad.getDownurl()), device.getUa(), p);
			String adv = down.toDownload();
			System.out.println("adv="+adv);
			
			String downdone = doAction(device, ad, p, AppContact.DOWN, "");
			System.out.println("downdone="+downdone);
			
			Thread.sleep(1000);
			String installdone = doAction(device, ad, p, AppContact.INSTALL, adv);
			System.out.println("install"+installdone);*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getApkInfo(String apkPath, String aaptPath) {
		String adv = "";
		try {
			Runtime rt = Runtime.getRuntime();
			String order = aaptPath + "aapt.exe" + " d badging \"" + apkPath
					+ "\"";
			Process proc = rt.exec(order);
			InputStream inputStream = proc.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(inputStream, "UTF-8"));
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				/*if (line.contains("application:")) {
					System.out.println(line);
					continue;
				}*/
				if (line.contains("package:")) {
					//System.out.println(line);
					String ver = parseValue("versionname", line.toLowerCase());
					String vc = parseValue("versioncode", line.toLowerCase());
					adv = vc+","+ver;
					continue;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return adv;
	}
	
	
	private static  String parseValue(String key,String str){
		String result = "";
		Pattern pt = Pattern.compile(key+"='(.*?)'");
		Matcher m = pt.matcher(str);
		if(m.find()){
			result = m.group(1);
		}
		return result;
	}
	
	public static String init(String appid,MyIP myip)throws Exception{
		String url  = "http://api.is.139mob.com/is/init.jsp?appid=" + appid;
		return Net.doGet(url, myip, "api.is.139mob.com", " ");
	}
	
	public static String init(AppmobDevice device,MyIP myip)throws Exception{
		String imei = device.getImei();
		String imsi = device.getImsi();
		String manufacturer = device.getName();
		String model = device.getModel();
		String net = getNetWork(device.getNetworkType());
		String networkOperator = URLEncoder.encode(getRandomNetworkOperatorName(imsi), "utf-8");
		String carrier  = networkOperator.split(",")[0];
		String osVersion = device.getOsVersion();
		String osVersionInt = String.valueOf(device.getApilevel());
		String screen = device.getWidth() + "x" + device.getHeight();
		String bssid = net.contains("wifi")?device.getBssid():"";
		
		String url = "http://api.is.139mob.com/is/init.jsp";
		String data = "?appid="+AppContact.APPID+"&apptype=1&appVersion="+AppContact.APPVer+
				"&appVersionInt="+AppContact.APPVC+"&appname="+AppContact.APPNAME+"&sysApp=0&country=CN&lang=zh&udid="+
				imei+"&imsi="+imsi+"&carrier="+carrier+"&manufacturer="+manufacturer+"&model="+model+"&net="+net+
				"&networkOperator="+networkOperator+"&osVersion="+osVersion+"&osVersionInt="+osVersionInt+
				"&packagename="+AppContact.APPPKG+"&screen="+screen+"&sdkVersion="+AppContact.SDKVER+
				"&sign=null&channelid=appmob&bssid="+bssid+"&pushFlag=true&adType=1";
		url = url + data;
		String resp = Net.doGet(url, myip, "api.is.139mob.com", " ");
		return resp;
		
	}
	
	public static List<Ad> requestInfo(AppmobDevice device,MyIP myip)throws Exception{
		List<Ad> list = new ArrayList<Ad>();
		String imei = device.getImei();
		String imsi = device.getImsi();
		String manufacturer = device.getName();
		String model = device.getModel();
		String net = getNetWork(device.getNetworkType());
		String networkOperator = URLEncoder.encode(getRandomNetworkOperatorName(imsi), "utf-8");
		String carrier  = networkOperator.split(",")[0];
		String osVersion = device.getOsVersion();
		String osVersionInt = String.valueOf(device.getApilevel());
		String screen = device.getWidth() + "x" + device.getHeight();
		String bssid = net.contains("wifi")?device.getBssid():"";
		
		String url = "http://api.is.139mob.com/is/info.jsp?";
		String data = "appid="+AppContact.APPID+"&apptype=1&appVersion="+AppContact.APPVer+
				"&appVersionInt="+AppContact.APPVC+"&appname="+AppContact.APPNAME+"&sysApp=0&country=CN&lang=zh&udid="+
				imei+"&imsi="+imsi+"&carrier="+carrier+"&manufacturer="+manufacturer+"&model="+model+"&net="+net+
				"&networkOperator="+networkOperator+"&osVersion="+osVersion+"&osVersionInt="+osVersionInt+
				"&packagename="+AppContact.APPPKG+"&screen="+screen+"&sdkVersion="+AppContact.SDKVER+
				"&sign=null&channelid=appmob&bssid="+bssid+"&pushFlag=true&adType=1&adCount=4";
		url = url + data;
		String resp = Net.doGet(url, myip, "api.is.139mob.com", " ");
		if(resp !=null && !"".equals(resp)){
			list = JsonParse.parseInfoList(resp);
		/*	for(Ad ad : list){
				System.out.println(ad.getAdName()+","+ad.getPkg()+","+ad.getDownurl()+","+ad.getToken());
			}*/
		}
		
		return list;
	}
	
	
	
	public static List<Ad> requestList(AppmobDevice device,MyIP myip)throws Exception{
		List<Ad> list = new ArrayList<Ad>();
		String imei = device.getImei();
		String imsi = device.getImsi();
		String manufacturer = device.getName();
		String model = device.getModel();
		String net = getNetWork(device.getNetworkType());
		String networkOperator = URLEncoder.encode(getRandomNetworkOperatorName(imsi), "utf-8");
		String carrier  = networkOperator.split(",")[0];
		String osVersion = device.getOsVersion();
		String osVersionInt = String.valueOf(device.getApilevel());
		String screen = device.getWidth() + "x" + device.getHeight();
		String bssid = net.contains("wifi")?device.getBssid():"";
		
		String url = "http://api.is.139mob.com/is/list.jsp?";
		String data = "appid="+AppContact.APPID+"&apptype=1&appVersion="+AppContact.APPVer+
				"&appVersionInt="+AppContact.APPVC+"&appname="+AppContact.APPNAME+"&sysApp=0&country=CN&lang=zh&udid="+
				imei+"&imsi="+imsi+"&carrier="+carrier+"&manufacturer="+manufacturer+"&model="+model+"&net="+net+
				"&networkOperator="+networkOperator+"&osVersion="+osVersion+"&osVersionInt="+osVersionInt+
				"&packagename="+AppContact.APPPKG+"&screen="+screen+"&sdkVersion="+AppContact.SDKVER+
				"&sign=null&channelid=appmob&bssid="+bssid+"&pushFlag=true&adType=1&page=1";
		url = url + data;
		String resp = Net.doGet(url, myip, "api.is.139mob.com", " ");
		if(resp !=null && !"".equals(resp)){
			list = JsonParse.parseInfoList(resp);
//			for(Ad ad : list){
//				System.out.println(ad.getAdName()+","+ad.getPkg()+","+ad.getDownurl()+","+ad.getToken());
//			}
		}
		return list;
	}
	
	
	public static String doAction(AppmobDevice device,Ad ad,MyIP myip,int type,String adv)throws Exception{
		String imei = device.getImei();
		String imsi = device.getImsi();
		String manufacturer = device.getName();
		String model = device.getModel();
		String net = getNetWork(device.getNetworkType());
		String networkOperator = URLEncoder.encode(getRandomNetworkOperatorName(imsi), "utf-8");
		String carrier  = networkOperator.split(",")[0];
		String osVersion = device.getOsVersion();
		String osVersionInt = String.valueOf(device.getApilevel());
		String screen = device.getWidth() + "x" + device.getHeight();
		String bssid = net.contains("wifi")?device.getBssid():"";
		
		String url = "http://api.is.139mob.com/is/show.jsp?";
		String data = "appid="+AppContact.APPID+"&apptype=1&appVersion="+AppContact.APPVer+
				"&appVersionInt="+AppContact.APPVC+"&appname="+AppContact.APPNAME+"&sysApp=0&country=CN&lang=zh&udid="+
				imei+"&imsi="+imsi+"&carrier="+carrier+"&manufacturer="+manufacturer+"&model="+model+"&net="+net+
				"&networkOperator="+networkOperator+"&osVersion="+osVersion+"&osVersionInt="+osVersionInt+
				"&packagename="+AppContact.APPPKG+"&screen="+screen+"&sdkVersion="+AppContact.SDKVER+
				"&sign=null&channelid=appmob&bssid="+bssid+"&pushFlag=true&adType=1";
		
		if(type == AppContact.SHOW)
		{
			url = "http://api.is.139mob.com/is/show.jsp?";
			data = data + "&flag=1&ps=" + ad.getToken();
		}
		if(type == AppContact.CLICK){
			url = "http://api.is.139mob.com/is/click.jsp?";
			data = data + "&ps=" + ad.getToken();
		}
		if(type == AppContact.DOWN){
			url = "http://api.is.139mob.com/app/down.jsp?";
			data = data + "&ps=" + ad.getToken() + "&source=1";
		}
		if(type == AppContact.INSTALL){
			url = "http://api.is.139mob.com/app/install.jsp?";
			data = data + "&ps=" + ad.getToken() + "&source=1&download=ok&adv="+adv;
		}
		if(type == AppContact.UNINSTALL){
			url = "http://api.is.139mob.com/app/uninstall.jsp?";
			data = data + "&ps=" + ad.getToken() + "&source=1";
		}
		
		url = url + data;
		String resp = Net.doGet(url, myip, "api.is.139mob.com", " ");
		return resp;
	}
	
	
	//加载一次插屏列表，包括了init,list,info,showcp
	public static Ad showCPList(AppmobDevice device,MyIP myip)throws Exception{
		Ad ad = null;
		//init(device, myip);
		init(AppContact.APPID, myip);
		init(device, myip);
		List<Ad> infoList = requestInfo(device, myip);
		List<Ad> tuijianList = requestList(device, myip);
		if(infoList.size()>0){
			ad = infoList.get(new Random().nextInt(infoList.size()));
			doAction(device, ad, myip, AppContact.SHOW, "");//展示一次插屏
		}
		return ad;
	}
	
	
	 public static String getNetWork(int type) {
			String net = "wifi";
			switch(type) {
				case DMContacts.CMCC : 
					net = "cmnet";
					break;
				case DMContacts.CT : net = "ctnet";break;
				case DMContacts.UNICOM : net = "3gnet";break;
				default:net = "wifi";break;
			}
			return net;
		}
	
	//获取2个日期的时间差
	public static String getTimeDiff(Date d1,Date d2){
		String result = "";
		try {
		  SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		   java.util.Date begin=dfs.parse(dfs.format(d1));
		   java.util.Date end = dfs.parse(dfs.format(d2));
		   long between=(end.getTime()-begin.getTime())/1000;//除以1000是为了转换成秒

		   result = between + "";
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
										"CHINAMOBILE",
										"CMCC",
										"CHINA MOBILE 3G",
										"中国移动,null",
										"null",
										"中国移动-3G",
										"中国移动 3G",
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
					if(ran >= 6950 && ran < 7000) return chinaMobile[12];
					
					
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
					if(ran >= 1980 && ran < 2000) return chinaUnicom[11];
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (imsi.startsWith("46003") || imsi.startsWith("46005")) {
				String[] chinaTelecom = {"中国电信",
										 "China Telecom",
										 "null",
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
					if(ran >= 850 && ran < 1000) return chinaTelecom[6];
				
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				return "";
			}
			return null;
		}
		
	//根据当前小时来获取ip数量
	public static int[] getIPNumByHour()
	{
		int h = DMUtils.getHour();
		int num[] = new int[2];
		if(h >11 && h <=13)//中午时间
		{
			num[0] = 10;
			num[1] = 20;
		}
		else if(h>=19 && h<=23)//晚间高峰
		{
			num[0] = 15;
			num[1] =25;
		}
		else if(h>=0&& h<7)//凌晨到早上
		{
			num[0] = 5;
			num[1] = 10;
		}
		else if(h>=7 && h<=11)//7点到上午11点前
		{
			num[0] =5;
			num[1] =15;
		}
		else if(h>13 && h<19)//下午一点到晚上7点前 
		{
			num[0] =15;
			num[1] = 20;
		}
		return num;
	}
	
	public static String getUrlPath(String url){
		return url.substring(0,url.indexOf("?"));
	}
	
	//判断指定的包名是否安装过
	public static boolean isInstall(String pkg,Map<String,Object> map){
		boolean result = false;
		for (String key : map.keySet()) {
			if(pkg.equals(key))result = true;
		}
		
		return result;
	}
	
	//读取保存了apk信息的文件
	public static String readFile(){
		File file=new File(AppContact.JSONFILEPATH); 
        BufferedReader bufferedReader = null;
        InputStreamReader read = null;
        StringBuffer sb = new StringBuffer();
        try {
        	 if(file.isFile() && file.exists()){ //判断文件是否存在 
                 read = new InputStreamReader( 
                 new FileInputStream(file),"utf-8");//考虑到编码格式 
                 bufferedReader = new BufferedReader(read); 
                 String lineTxt = null; 
                 while((lineTxt = bufferedReader.readLine()) != null){ 
              	   sb.append(lineTxt);
                 }
              }
			} catch (Exception e) {
				e.printStackTrace();
			}
        finally
        {
        	try {
        		read.close();
            	bufferedReader.close();
				} catch (Exception e2) {
				}
        }
        
        return sb.toString();
	}
	
	public static Map<String,Object> getLocalApkInfo(){
		String json = readFile();
		Map<String,Object> map = new HashMap<String,Object>();
		if(json != null && !"".equals(json)){
			json = json.substring(1,json.length()-1);
			JSONObject jsonMap = JSONObject.fromObject(json);
			Iterator<String> it = jsonMap.keys();  
		    while(it.hasNext()) {  
		        String key = (String) it.next();  
		        PkgInfo u = (PkgInfo) JSONObject.toBean(  
		                JSONObject.fromObject(jsonMap.get(key)),  
		                PkgInfo.class);  
		        map.put(key, u);  
		    }  
		}
		
		return map;
	}

	//指定范围内随机数
	public static int getRangeRandomNum(int min,int max)
	{
		Random random = new Random();
		int s = random.nextInt(max)%(max-min+1) + min;
		return s;
	}
}
