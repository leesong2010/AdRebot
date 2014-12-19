package com.ad.miidi;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;
import java.util.logging.SimpleFormatter;


import net.sf.json.JSONObject;

public class Utils {

	public static void main(String[] args) {
		
		MDContacts.APPID  = "18770";
		MDContacts.APPKEY  = "eg1lkgwjmq3ex6fs";
		MDContacts.APP_PKG = "org.jia.caipu";
		try {
			Proxy p = null;
		/*	String  s = getAppconf("18770", "eg1lkgwjmq3ex6fs");
			System.out.println(s);
			System.out.println(activeBin(p));
			System.out.println(aaBin(MDContacts.APPID, p));
			System.out.println(anBin(MDContacts.APPID, p));*/
			
			
			
		 List<MDAd> list	= openWall(p);

			
		   MDAd ad = list.get(3);
		   System.out.println(ad.getAdName());
			String downAppidEncode = openDetail(ad.getDetailUrl(), p);
			System.out.println("downAppidEncode="+downAppidEncode);
			String downurl = getCpaappconfBin(downAppidEncode, p);
			System.out.println("downurl="+downurl);
			
			SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.'SSS'Z'");
			TimeZone b = TimeZone.getTimeZone("GMT+0");
			a.setTimeZone(b);
			String stime = a.format(new Date());
			
			Download down = new Download(new URL(downurl), "Mozilla/5.0 (Linux; U; Android 4.2.2; zh-cn; X909 Build/JDQ39) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30", p);
			down.toDownload();
			Thread.sleep(2000);
			
			String etime = a.format(new Date());
			
			String downResp = downOK(ad, stime, etime, downAppidEncode, p);
			System.out.println("������ɷ���="+downResp);
			
			Thread.sleep(1000);
			stime = a.format(new Date());//��ʼ��װ
			Thread.sleep(2000);
			etime = a.format(new Date());
			
			System.out.println("stime="+stime);
			System.out.println("etime="+etime);
			String installResp = installOK(ad, stime, etime, downAppidEncode, p);
			System.out.println("��װ��ɷ���="+installResp);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	//�ӷ�������ȡ������Ϣ
	public static String getAppconf(String appId,String appKey) throws Exception{
		String resp = "";
		JSONObject obj = new JSONObject();
        obj.element("pad", "1");
        obj.element("model", "X909");
        obj.element("channelId", "0");
        obj.element("imei", "352265053995043");
        obj.element("unionId", "8000");
        obj.element("netChannel", "0");
        obj.element("versoft", "android_v2.4.2");
        obj.element("installedList", new ArrayList());
        obj.element("appPackageName", "org.jia.caipu");
        obj.element("country", "CHN");
        obj.element("productId", "18770");
        obj.element("mac_address", "95:3e:e3:d9:b3:b1");
        obj.element("sysVer", "17");
        obj.element("android_id", "ae593b2401f7eddb");
        obj.element("token", "d0379d11ae233776d3c30a605c23d0c3");
        obj.element("serialnum", "d398a5cb");
        obj.element("testMode",false);
        obj.element("language", "zho");
        obj.element("imsi", "123456789");
        obj.element("apn", "CHINANET");
        resp  = Net.postJSon("http://ads.miidi.net:80/appscore4/getappconf.bin", obj);
		return resp;
	}
	
	//����post����get����
	public static String aaBin(String appId,Proxy p)throws Exception{
		String resp = "";
		resp = Net.doGet("http://api.miidi.net/sys_sta/aa.bin?pid="+appId, p, "api.miidi.net", "Apache-HttpClient/UNAVAILABLE (java 1.4)");
		return resp;
	}
	
	//����post����get����
	public static String anBin(String appId,Proxy p)throws Exception{
		String resp = "";
		resp = Net.doGet("http://api.miidi.net/sys_sta/an.bin?pid="+appId, p, "api.miidi.net", "Apache-HttpClient/UNAVAILABLE (java 1.4)");
		return resp;
	}
	
	//�����û������(ԭpost����get)
	public static String activeBin(Proxy p)throws Exception{
		String resp = "";
		String model = "X909";
		String imei = "352265053995043";
		String imsi = "123456789";
		String sysVer = "17";
		String mac = "95:3e:e3:d9:b3:b1";
		String deviceName = "OPPO";
		String deviceModel = "X909";
		mac = URLEncoder.encode(mac, "utf-8");
		String android_id = MD5.hash(imei +"fuck").substring(16);
		String serialnum = MD5.hash(imei +"fuckyou").substring(8);
		String osVer = "4.2.2";
		String deviceOS = URLEncoder.encode("Android " + osVer, "utf-8");
		String token = "acti3ci98sw2kj6de23ksh7cjcx"+imei+MDContacts.APPID+MDContacts.APPKEY+"Android " +osVer ;
		token = MD5.hash(token);
		String apn = "CHINANET";//TODO �����mobile�����ǲ���ҪΪ��??
		//0wifi 1 mobile
		String netChannel = "0";//TODO �����device��ȡ
		String imsiType = "46001";//imsiǰ5λ
		int width = 1080;
		int height = 1920;
		String url = "http://ads.miidi.net:80/appscore4/active.bin";
		url += "?model="+model+"&imei="+imei+"&imsi="+imsi+"&versoft="+MDContacts.LIB_VER+"&sysVer="+sysVer+
				"&appPackageName="+MDContacts.APP_PKG+"&productId="+MDContacts.APPID+"&mac_address="+mac+
				"&android_id="+android_id+"&serialnum="+serialnum+"&language=zho&country=CHN&pad=1&testMode=false&channelId=0&netChannel="+
				netChannel+"&token=" +token +"&apn="+apn+"&unionId=8000&deviceManufacturer="+deviceName+
				"&deviceOS="+deviceOS+"&deviceModel="+deviceModel+"&net="+imsiType+"&screenWidth="+width+"&screenHeight="+height+"&colorDepth=24";
		resp = Net.doGet(url, p, "ads.miidi.net:80", "Apache-HttpClient/UNAVAILABLE (java 1.4)");
		
		return resp;
	}

	//��ǽ
	public static List<MDAd> openWall(Proxy p)throws Exception{
		List<MDAd> list =new ArrayList<MDAd>();
		String model = "X909";
		String imei = "352265053995043";
		String imsi = "123456789";
		String sysVer = "17";
		String mac = "95:3e:e3:d9:b3:b1";
		String deviceName = "OPPO";
		String deviceModel = "X909";
		mac = URLEncoder.encode(mac, "utf-8");
		String android_id = MD5.hash(imei +"fuck").substring(16);
		String serialnum = MD5.hash(imei +"fuckyou").substring(8);
		String osVer = "4.2.2";
		String deviceOS = URLEncoder.encode("Android " + osVer, "utf-8");
		String token = "qzx6d9wd03r8ji73hs4e8lw2c3dj"+imei+MDContacts.APPID+MDContacts.APPKEY+"020" +sysVer ;
		token = MD5.hash(token);
		String apn = "CHINANET";//TODO �����mobile�����ǲ���ҪΪ��??
		//0wifi 1 mobile
		String netChannel = "0";//TODO �����device��ȡ
		String imsiType = "46001";//imsiǰ5λ
		int width = 1080;
		int height = 1920;
		String UA = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36 LBBROWSER";
		
		
		String url = "http://ads.miidi.net/appscore4/webofferview.bin?model="+model+"&imei="+imei+"&imsi="+imsi+"&mac_address="+mac+"&android_id="+android_id+
				"&serialnum="+serialnum+"&language=zho&country=CHN&pad=1&token="+token+"&productId="+MDContacts.APPID+"&sysVer="+sysVer+
				"&versoft="+MDContacts.LIB_VER+"&testMode=false&channelId=0&netChannel="+netChannel+"&appPackageName="+MDContacts.APP_PKG+"&apn="+apn+"&unionId=8000";
		
		String resp = Net.doGet(url, p, "ads.miidi.net", UA);
		if(resp != null && !"".equals(resp)){
			list = HtmlParse.parseAdList(resp);
		}
		return list;
		
		
	}
	
	//������,������downAct
	public static String openDetail(String url,Proxy p)throws Exception{
		String resp = "";
		String downAct = "";
		resp = Net.doGet(url, p, "ads.miidi.net", "Apache-HttpClient/UNAVAILABLE (java 1.4)");
		System.out.println("detailresp="+resp);
		if(resp != null && !"".equals(resp)){
			downAct = HtmlParse.parseDownAppidEncode(resp);
		}
		return downAct;
	}
	
	//��ȡҪ����apk����Ϣ����Ҫ�ǻ�ȡ���ص�ַ
	public static String getCpaappconfBin(String downAppidEncode,Proxy p)throws Exception{
		String downUrl = "";
		
		String model = "X909";
		String imei = "352265053995043";
		String imsi = "123456789";
		String sysVer = "17";
		String mac = URLEncoder.encode("95:3e:e3:d9:b3:b1","utf-8");
		mac = URLEncoder.encode(mac, "utf-8");
		String android_id = MD5.hash(imei +"fuck").substring(16);
		String serialnum = MD5.hash(imei +"fuckyou").substring(8);
		String token = "qzx6d9wd03r8ji73hs4e8lw2c3dj"+imei+MDContacts.APPID+MDContacts.APPKEY+"020" +sysVer ;
		token = MD5.hash(token);
		String apn = "CHINANET";//TODO �����mobile�����ǲ���ҪΪ��??
		//0wifi 1 mobile
		String netChannel = "0";//TODO �����device��ȡ
		
		String url = "http://ads.miidi.net:80/appscore4/cpaappconf.bin?model="+model+"&imei="+imei+"&imsi="+imsi+"&versoft="+MDContacts.LIB_VER+"&sysVer="+sysVer+
				"&appPackageName="+MDContacts.APP_PKG+"&productId="+MDContacts.APPID+"&mac_address="+mac+"&android_id="+android_id+"&serialnum="+serialnum+
				"&language=zho&country=CHN&pad=1&testMode=false&channelId=0&netChannel="+netChannel+"&token=&apn="+apn+
				"&unionId=8000&downAppidEncode="+downAppidEncode;
		
		String resp = Net.doGet(url, p, "ads.miidi.net:80", "Apache-HttpClient/UNAVAILABLE (java 1.4)");
		if(resp != null && !"".equals(resp)){
			downUrl = HtmlParse.parseDownUrl(resp);
		}
		
		return downUrl;
		
	}

	//������ɵ��ýӿ�
	public static String downOK(MDAd ad,String stime,String etime,String downAppidEncode,Proxy p)throws Exception{
		String result = "";
		String model = "X909";
		String imei = "352265053995043";
		String imsi = "123456789";
		String sysVer = "17";
		String mac = URLEncoder.encode("95:3e:e3:d9:b3:b1","utf-8");
		mac = URLEncoder.encode(mac, "utf-8");
		String android_id = MD5.hash(imei +"fuck").substring(16);
		String serialnum = MD5.hash(imei +"fuckyou").substring(8);
		String osVer = "4.2.2";
		String apn = "CHINANET";//TODO �����mobile�����ǲ���ҪΪ��??
		//0wifi 1 mobile
		String netChannel = "0";//TODO �����device��ȡ
		String imsiType = "46001";//imsiǰ5λ
		int width = 1080;
		int height = 1920;
		String UA = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36 LBBROWSER";
		String token = "d8io3usx445cls39ij3lk3kjw2sd"+imei+MDContacts.APPID+MDContacts.APPKEY+stime+ad.getCpaID()+etime+model+downAppidEncode;
		token = MD5.hash(token);
		
		stime = URLEncoder.encode(stime, "utf-8");
		etime = URLEncoder.encode(etime, "utf-8");
		String url = "http://ads.miidi.net:80/appscore4/downok.bin?model="+model+"&imei="+imei+"&imsi="+imsi+"&versoft="+MDContacts.LIB_VER+"&sysVer="+sysVer+
				"&appPackageName="+MDContacts.APP_PKG+"&productId="+MDContacts.APPID+"&mac_address="+mac+"&android_id="+android_id+"&serialnum="+serialnum+
				"&language=zho&country=CHN&pad=1&testMode=false&channelId=0&netChannel="+netChannel+"&token="+token+"&apn="+apn+
				"&unionId=8000&stime="+stime+"&etime="+etime+"&downAppid="+ad.getCpaID()+"&downAppidEncode="+downAppidEncode;
		
		result = Net.doGet(url, p, "ads.miidi.net:80", "Apache-HttpClient/UNAVAILABLE (java 1.4)");
		return result;
	}
	
	//��װ���
	public static String installOK(MDAd ad,String stime,String etime,String downAppidEncode,Proxy p)throws Exception{
		String result = "";
		String model = "X909";
		String imei = "352265053995043";
		String imsi = "123456789";
		String sysVer = "17";
		String mac = URLEncoder.encode("95:3e:e3:d9:b3:b1","utf-8");
		mac = URLEncoder.encode(mac, "utf-8");
		String android_id = MD5.hash(imei +"fuck").substring(16);
		String serialnum = MD5.hash(imei +"fuckyou").substring(8);
		String osVer = "4.2.2";
		String apn = "CHINANET";//TODO �����mobile�����ǲ���ҪΪ��??
		//0wifi 1 mobile
		String netChannel = "0";//TODO �����device��ȡ
		String token = "iLogj386cu8982j9di30ozpiki93"+imei+MDContacts.APPID+MDContacts.APPKEY+stime+ad.getCpaID()+etime+model+downAppidEncode;
		token = MD5.hash(token);
		
		stime = URLEncoder.encode(stime, "utf-8");
		etime = URLEncoder.encode(etime, "utf-8");
		
		String url = "http://ads.miidi.net:80/appscore4/installok.bin?model="+model+"&imei="+imei+"&imsi="+imsi+"&versoft="+MDContacts.LIB_VER+"&sysVer="+sysVer+
				"&appPackageName="+MDContacts.APP_PKG+"&productId="+MDContacts.APPID+"&mac_address="+mac+"&android_id="+android_id+"&serialnum="+serialnum+
				"&language=zho&country=CHN&pad=1&testMode=false&channelId=0&netChannel="+netChannel+"&token="+token+"&apn="+apn+
				"&unionId=8000&downAppid="+ad.getCpaID()+"&downAppidEncode="+downAppidEncode+"&installType=0"+
				"&stime="+stime+"&etime="+etime;
		System.out.println(url);
		result = Net.doGet(url, p, "ads.miidi.net:80", "Apache-HttpClient/UNAVAILABLE (java 1.4)");
		return result;
	}
	
	 //����imsi��ȡApn
    public static String getApn(final String imsi){
    	if (imsi.startsWith("46000") || imsi.startsWith("46002")
				|| imsi.startsWith("46007")) {
    		return "cmnet";
    	} else if (imsi.startsWith("46001") || imsi.startsWith("46006")) {
    		return "3gnet";
    	} else if (imsi.startsWith("46003") || imsi.startsWith("46005")){
    		return "ctnet";
    	}
       	
    	return "";
    }
    
    //��ȡ���Wifi����
    public static String getRandomWifiName(){
    	return getRandomString( new Random().nextInt(6) + 3 );	
    }
    
	public static String getRandomString(int length) {
		String val = "";
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
			if ("char".equalsIgnoreCase(charOrNum)) {
				int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
				val += (char) (choice + random.nextInt(26));
			} else if ("num".equalsIgnoreCase(charOrNum)) {
				val += String.valueOf(random.nextInt(10));
			}
		}
		return val;
	}

	
	public static String getNetWork(int type) {
		String net = "0";
		switch(type) {
			case MDContacts.CMCC : 
				net = "1";
				break;
			case MDContacts.CT : net = "1";break;
			case MDContacts.UNICOM : net = "1";break;
			default:net = "0";break;
		}
		return net;
	}
	
}
