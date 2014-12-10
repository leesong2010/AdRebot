package com.ad.zhimeng;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import org.herojohn.adrobot.device.DeviceFactory;
import org.herojohn.adrobot.device.creator.impl.ZhimengDeviceCreator;
import org.herojohn.adrobot.device.creator.impl.ZhimengDeviceCreator;
import org.herojohn.adrobot.device.model.ZhimengDevice;
import org.herojohn.adrobot.device.model.DeviceConfig;
import org.herojohn.adrobot.device.model.ZhimengDevice;

import com.ad.appmob.MyIP;


public class Utils {
	private static ZhimengDeviceCreator deviceCreator = (ZhimengDeviceCreator) DeviceFactory.getInstance("zhimeng");
	public static void main(String[] args) {
		ZMContact.APPKEY = "F6A0E8E4C8E84494B445C6BA91E064C7";
		ZMContact.APPPKG = "org.studiosgames.3dball";
		ZMContact.APPNAME = "3D×ÀÇò";
		ZMContact.DOWN_SPEED = "555,1500";
		MyIP myip = null;
		
		
		try {
			
			DeviceConfig config = new DeviceConfig(6, "test");
			deviceCreator.init(config);
			ZhimengDevice device = deviceCreator.getDevice();
			
			
			List<Ad> list = getAds(device,myip);
			System.out.println("list size:"+list.size());
			String data = getInitAdData(list);
			String resp = doAction(device, myip, data, true);
			System.out.println("initADStatus="+resp);
			
			Ad ad = list.get(0);
			System.out.println("Pkg="+ad.getPname());
			System.out.println(ad.getUrl());
			ZMDownload download = new ZMDownload(new URL(ad.getUrl()), device.getUa(), myip);
			download.toDownload();
			
			data = getAdData(ad, ZMContact.INSTALL);
			resp = doAction(device, myip, data, false);
			System.out.println("Install=="+resp);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	public static List<Ad> getAds(ZhimengDevice device,MyIP myip)throws Exception{
		List<Ad> list = new ArrayList<Ad>();
		String imei = device.getImei();
		String model = device.getModel();
		String networkname = getNetworkname(device.getImsi());
		String url = "http://cpget.shunshou.com:5021/ugetad.do";
		url += "?mid="+imei+"&appkey="+ZMContact.APPKEY+"&count=0&sv="+ZMContact.SV+"&kv="+ZMContact.KV+"&pname="+ZMContact.APPPKG+
				"&appname="+ZMContact.APPNAME+"&model="+model+"&networkname="+networkname;
		//System.out.println(url);
		String resp = Net.doGet(url, myip, "cpget.shunshou.com:5021", " ");
//		String data = "mid="+imei+"&appkey="+ZMContact.APPKEY+"&count=0&sv=303&kv=302&pname="+ZMContact.APPPKG+
//				"&appname="+ZMContact.APPNAME+"&model="+model+"&networkname="+networkname;
//		String resp = Net.toPostData(url, data, myip, "cpget.shunshou.com:5021", " ");
	//	System.out.println(resp);
		if(resp != null && !"".equals(resp)){
				list = JsonParse.parseAdList(resp);
			/*for(Ad ad : list){
				System.out.println(ad.getGid()+","+ad.getPname()+","+ad.getUrl());
			}*/
		}
		
		return list;
	}
	
	public static String  doAction(ZhimengDevice device,MyIP myip,String data,boolean init)throws Exception{
		String imei = device.getImei();
		String model = device.getModel();
		String networkname = getNetworkname(device.getImsi());
		String url = "http://cpreport.shunshou.com:5022/upostdata.do?";
		String postdata = "";
		if(init){
			url += "mid="+imei+"&appkey="+ZMContact.APPKEY+"&data="+data+"&sv="+ZMContact.SV+"&kv="+ZMContact.KV+"&pname="+ZMContact.APPPKG
			+"&appname="+ZMContact.APPNAME+"&model="+model+"&networkname="+networkname;
			postdata = "mid="+imei+"&appkey="+ZMContact.APPKEY+"&data="+data+"&dataa={\"show\":1}&sv="+ZMContact.SV+"&kv="+ZMContact.KV+"&pname="+ZMContact.APPPKG
					+"&appname="+ZMContact.APPNAME+"&model="+model+"&networkname="+networkname;
		}
		else{
			url += "mid="+imei+"&appkey="+ZMContact.APPKEY+"&data="+data+"&dataa={\"show\":1}&sv="+ZMContact.SV+"&kv="+ZMContact.KV+"&pname="+ZMContact.APPPKG
				+"&appname="+ZMContact.APPNAME+"&model="+model+"&networkname="+networkname;
			postdata = "mid="+imei+"&appkey="+ZMContact.APPKEY+"&data="+data+"&dataa={\"show\":1}&sv="+ZMContact.SV+"&kv="+ZMContact.KV+"&pname="+ZMContact.APPPKG
					+"&appname="+ZMContact.APPNAME+"&model="+model+"&networkname="+networkname;
		}
		String resp = Net.doGet(url, myip, "cpreport.shunshou.com:5022", " ");
		//String resp = Net.toPostData(url, postdata, myip, "cpreport.shunshou.com:5022", " ");
		return resp;
	}
	
   public static String getNetworkname(String imsi) {
    	String netName = "";
    	netName = !( (CharSequence)imsi == null || ( (CharSequence)imsi).length() == 0 ) ? (imsi.startsWith("46000") || imsi.startsWith("46002") || imsi.startsWith("46007") ? "ChinaMobile" : (imsi.startsWith("46001") ? "ChinaUnicom" : (imsi.startsWith("46003") ? "ChinaTelecom" : "UNKNOWN"))) : "UNKNOWN";
        return netName;
    }
   
   public static String getInitAdData(List<Ad> list){
	   String res = "";
	   List<AdStatus> listStatus = new ArrayList<AdStatus>();
	   for(Ad ad :list){
		   AdStatus status = new AdStatus(ad.getGid(),"0");
		   listStatus.add(status);
	   }
	   JSONArray json = JSONArray.fromObject(listStatus);
	   res = json.toString();
	   
	   return res;
   }
   
   public static String getAdData(Ad ad,int status){
	   String res = "";
	   List<AdStatus> listStatus = new ArrayList<AdStatus>();
	   if(status == ZMContact.SHOW){
		   AdStatus st = new AdStatus(ad.getGid(),"1");
		   listStatus.add(st);
	   }
	   
	   if(status == ZMContact.CLICK){
		   AdStatus st = new AdStatus(ad.getGid(),"1");
		   listStatus.add(st);
		   st = new AdStatus(ad.getGid(), "2");
		   listStatus.add(st);
	   }
	   
	   if(status == ZMContact.DOWN){
		   AdStatus st = new AdStatus(ad.getGid(),"1");
		   listStatus.add(st);
		   st = new AdStatus(ad.getGid(), "2");
		   listStatus.add(st);
		   st = new AdStatus(ad.getGid(), "3");
		   listStatus.add(st);
	   }
	   
	   if(status == ZMContact.INSTALL){
		   AdStatus st = new AdStatus(ad.getGid(),"1");
		   listStatus.add(st);
		   st = new AdStatus(ad.getGid(), "2");
		   listStatus.add(st);
		   st = new AdStatus(ad.getGid(), "3");
		   listStatus.add(st);
		   st = new AdStatus(ad.getGid(), "4");
		   listStatus.add(st);
	   }
	   
	   JSONArray json = JSONArray.fromObject(listStatus);
	   res = json.toString();
	   return res;
   }
   
   public static String getInstallAdsData(List<Ad> list){
	   String res = "";
	   List<AdStatus> listStatus = new ArrayList<AdStatus>();
	   for(Ad ad : list){
		   AdStatus st = new AdStatus(ad.getGid(),"1");
		   listStatus.add(st);
		   st = new AdStatus(ad.getGid(), "2");
		   listStatus.add(st);
		   st = new AdStatus(ad.getGid(), "3");
		   listStatus.add(st);
		   st = new AdStatus(ad.getGid(), "4");
		   listStatus.add(st);
	   }
	   JSONArray json = JSONArray.fromObject(listStatus);
	   res = json.toString();
	   return res;
   }
	
}
