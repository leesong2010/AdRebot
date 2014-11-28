package com.ad.appmob;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.nice.NetUtils;

public class GetAppInfo {

	private static GetAppInfo instans;
	private Map<String,Ad> adMap = new HashMap<String,Ad>();
	private static final String JSONFILEPATH = "c:/app.json";
	private static final String AAPTPATH = "d:/";
	private static final String SAVEPATH = "c:/apks/";
	public static void main(String[] args) {

		String s = readFile("c:/app.json");
		System.out.println(s);
		s = s.substring(1,s.length()-1);
		System.out.println(s);
		JSONObject jsonMap = JSONObject.fromObject(s);
		Map<String,Object> map = new HashMap<String,Object>();
		Iterator<String> it = jsonMap.keys();  
	    while(it.hasNext()) {  
	        String key = (String) it.next();  
	        PkgInfo u = (PkgInfo) JSONObject.toBean(  
	                JSONObject.fromObject(jsonMap.get(key)),  
	                PkgInfo.class);  
	        map.put(key, u);  
	    }  
		
	    Set<String> set = map.keySet();   
		for (String a:set) {
			PkgInfo p = (PkgInfo)map.get(a);
			System.out.println(p.getDownurl());
		}
		//toDo();
		
	}

	private static void toDo(){
		Map<String,Ad> map = new HashMap<String,Ad>();
		instans = new GetAppInfo();
		String url = "http://api.is.139mob.com/is/list.jsp?appid=80a3914b11a44bab41463521ef65252f&apptype=1&appVersion=1.0&appVersionInt=1&appname=test&sysApp=0&country=CN&lang=zh&udid=357806045565628&imsi=460075786406321&carrier=%E4%B8%AD%E5%9B%BD%E7%A7%BB%E5%8A%A8&manufacturer=samsung&model=gt-i9108&net=wifi&networkOperator=%E4%B8%AD%E5%9B%BD%E7%A7%BB%E5%8A%A8&osVersion=2.3.6&osVersionInt=10&packagename=org.nice.caipu&screen=800x480&sdkVersion=2.0.8&sign=null&channelid=appmob&bssid=e2:3b:b1:17:36:79&pushFlag=true&adType=1&page=1";
		try {
			
			//打开20次推荐列表
			for(int i=0;i<20;i++){
				String resp = doGet(url);
				if(!"".equals(resp)){
					List<Ad> list =	JsonParse.parseInfoList(resp);
					if(list!=null && list.size()>0){
						for(Ad ad:list){
							map.put(ad.getPkg(), ad);
						}
					}
				}
			}
			
			if(map.size()>0){
				System.out.println("获取到了"+map.size()+"个广告");
				System.out.println("开始准备下载...");
				new Thread(instans.new DownThread(map)).start();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static String doGet(String url) throws Exception{
		HttpURLConnection conn = null;
		StringBuffer sb = new StringBuffer();
		InputStream is = null;
		InputStreamReader isReader = null;
		BufferedReader bufReader = null;
		
		
		
		try {
			
			URL mUrl = new URL(url);
			conn = (HttpURLConnection) mUrl.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestProperty("Host", "api.is.139mob.com");
			conn.setRequestProperty("User-Agent"," ");
			
			conn.setConnectTimeout(3 * 1000);
			is = conn.getInputStream();
		    isReader = new InputStreamReader(is, "utf-8");
			bufReader = new BufferedReader(isReader);
			String newsContents = "";
			while ((newsContents = bufReader.readLine()) != null) {
				sb.append(newsContents);
			}
		} catch (Exception e) {
			
				e.getMessage();
			
		}finally{
			if(bufReader != null)bufReader.close();
			if(isReader != null)isReader.close();
			if(is != null)is.close();
			conn = null;
		}
		return sb.toString();
	}
	
	public class DownThread implements Runnable {
		private Map<String,Ad> map;
		private DownThread(Map<String,Ad> map){
			this.map=map;
		}
		@Override
		public void run() {
			
			Set<String> set = map.keySet();   
			for (String s:set) {  
				Ad ad = map.get(s);
				String fn = ad.getDownurl().substring(ad.getDownurl().lastIndexOf("/")+1);
				try {
					ApkDownload down = new ApkDownload(new URL(ad.getDownurl()), fn,SAVEPATH,AAPTPATH);
					String adv = down.toDownload();
					if(adv!=null && !"".equals(adv)){
						ad.setAdv(adv);
						adMap.put(ad.getPkg(), ad);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			JSONArray json = JSONArray.fromObject(adMap); 
			System.out.println(json.toString()); 
			saveToFile(json.toString());
			
		}
	}
	
	private void saveToFile(String json){
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		try {
            fos = new FileOutputStream(JSONFILEPATH);   
            osw = new OutputStreamWriter(fos, "UTF-8"); 
            osw.write(json); 
            osw.flush(); 
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static String readFile(String path){
		  File file=new File(path); 
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
	
}
