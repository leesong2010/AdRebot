import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class ParseWoquMain{
	
	private static String domain = "http://ts.sogo.wuquad.com/";
	
	private static String app_id = "451FC30A-85DB-4825-82B6-F4B9E655E5DB";
	private static String adid = "573";
	private static String imei = "99000537521731";
	private static String imsi = "460021650483116";
	private static String channel = "9999";
	private static String standBy = "YWJjZGVmZ2hpamtsbW5vcHFyc3R1dnd5";
	
	private static String preType_init = "1";
	private static String preType_click = "2";
	private static String preType_downloadComplete = "3";
	private static String preType_installOk = "4";
	
	private static String adAction = "";
	
	public static void main(String..._){
		//System.out.println("服务器返回结果" + getShowFlowAdString(preType_init));
		
		
		try {
			String init = init();
			XYInfo xyinfo = getXYInfo("183.38.4.187");
			String localUrl = "http://ts.sogo.wuquad.com/api30/Location.aspx?app_id="+app_id+"&imei="+imei+"&channel=9999&locationcode="+xyinfo.getCityCode()+"&locationlat="+xyinfo.getLocationlat()+"&locationlon="+xyinfo.getLocationlon();
			System.out.println(localUrl);
			String localREsp = doGet(localUrl);
			System.out.println("local:"+localREsp);
			AdInfo adinfo =getAds();
			Ad ad = adinfo.getListAd().get(0);
			System.out.println("adnma="+ad.getAdName()+",adid="+ad.getAdid());
			standBy = adinfo.getStandby();
			adid = ad.getAdid();
			adAction = preType_init;
			String adUrl = "http://ts.sogo.wuquad.com/api30/ShowFlowAd.aspx?"+FlowCountState.encode("app_id=" + app_id + "&adid=" + adid + "&imei=" + imei + "&channel=" + channel + "&FlowType=" + adAction, 
					   standBy).replaceAll("\n", "");;
			System.out.println(adUrl);
			System.out.println("展示服务器返回结果" + getShowFlowAdString(preType_init));
			
			adAction = preType_click;
			
			System.out.println("点击服务器返回结果" + getShowFlowAdString(preType_click));
			Download down = new Download(new URL(ad.getDownurl()), "", false);
			down.toDownload();
			System.out.println("下载完成服务器返回结果" + getShowFlowAdString(preType_downloadComplete));
			Thread.sleep(3000);
			System.out.println("安装完成服务器返回结果" + getShowFlowAdString(preType_installOk));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	public static XYInfo getXYInfo(String ip)throws Exception{
		String url = "http://api.map.baidu.com/location/ip?ak=pZktE8qGLxccnGKgf8aSAsjb&ip="+ip+"&coor=bd09ll";
		String resp = doGet(url);
		return JsonParse.parseXY(resp);
	}
	public static String getShowFlowAdString(String preType){
        String string2 = FlowCountState.encode("app_id=" + app_id + "&adid=" + adid + "&imei=" + imei + "&channel=" + channel + "&FlowType=" + preType, 
				   standBy).replaceAll("\n", "");
        //参数组成
		System.out.println("参数：" + string2);
		//打印请求的网址
		System.out.println("请求网址：" + String.valueOf(domain) + "/api30/ShowFlowAd.aspx?" + string2);
		//打印服务器返回值
		String serverReturn = HttpRequest.OpenUrl(String.valueOf(domain) + "/api30/ShowFlowAd.aspx?", "GET", string2);
		
		return serverReturn;
	}
		
	
	public static void domintest(){
		String url = "http://ts.sogo.wuquad.com/api30/sdkupdate/domintest.aspx";
		//UA 手机ua
		//host:ts.sogo.wuquad.com
	}
	
	public static String init(){
		String resp = "";
		String url ="http://ts.sogo.wuquad.com//api30/InitConnect.aspx?app_id="+app_id+"&imei="+imei+"&imsi="+imsi+"&net=1&device_name=X909&os=1&os_version=4.2.2&app_version=SDK3.0Demo&sdk_version=3.0&mac=8c:0e:e3:d9:b3:b1&channel=9999&width=1080&height=1920";
		try {
			resp = doGet(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return resp;
		
			
	}
	
	
	public static AdInfo getAds(){
		AdInfo adinfo = null;
		String url = "http://ts.sogo.wuquad.com//api30/GetADToLatest.aspx?app_id="+app_id+"&imei="+imei+"&ad_type=20";
		try {
			String resp = doGet(url);
			adinfo = parseAdInfo(resp);
			System.out.println(adinfo.getStandby());
			/*for(Ad ad:adinfo.getListAd()){
				System.out.println("name="+ad.getAdName()+",adid="+ad.getAdid()+",down="+ad.getDownurl());
			}*/
		} catch (Exception e) {
		}
		
		return adinfo;
	}
	
	public static AdInfo parseAdInfo(String json){
		AdInfo adinfo = new AdInfo();
		List<Ad> list = new ArrayList<Ad>();
		try {
			JSONObject jo = JSONObject.fromObject(json);
			String standby = jo.getString("Standby");
			JSONArray ja =  jo.getJSONArray("AdlistLatest");
			if(ja != null){
				for(int i=0;i<ja.size();i++){
					JSONObject obj =(JSONObject) ja.get(i);
					String adid = obj.getString("AdId");
					String adName =obj.getString("Name");
					int type = Integer.parseInt(obj.getString("Type"));
					String downurl = obj.getString("Url");
					
					Ad ad = new Ad(adid, adName, downurl, type);
					list.add(ad);
				}
			}
			
			if(list.size()>0 && !"".equals(standby)){
				adinfo.setListAd(list);
				adinfo.setStandby(standby);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return adinfo;
	}
	
	public static String doGet(String urStr) throws Exception{
		HttpURLConnection conn = null;
		StringBuffer sb = new StringBuffer();
		InputStream is = null;
		InputStreamReader isReader = null;
		BufferedReader bufReader = null;
		
		
		
		try {
			
			URL mUrl = new URL(urStr);
			conn = (HttpURLConnection) mUrl.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestProperty("Host", "ts.sogo.wuquad.com");
				conn.setRequestProperty("User-Agent","Dalvik/1.6.0 (Linux; U; Android 4.2.2; X909 MIUI/4.6.20)");
			
			conn.setConnectTimeout(3 * 1000);
			is = conn.getInputStream();
		    isReader = new InputStreamReader(is, "utf-8");
			bufReader = new BufferedReader(isReader);
			String newsContents = "";
			while ((newsContents = bufReader.readLine()) != null) {
				sb.append(newsContents);
			}
		} catch (Exception e) {
		}finally{
			if(bufReader != null)bufReader.close();
			if(isReader != null)isReader.close();
			if(is != null)is.close();
			conn = null;
		}
		return sb.toString();
	}
}