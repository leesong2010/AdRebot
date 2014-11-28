package com.ad.appmob;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ad.dianjoy.vo.Task;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonParse {

	
	
	public static void main(String[] args)
	{
		String d = "{\"data\":{\"province\":440000,\"city\":440300,\"uid\":\"15263030\"},\"result\":\"200\",\"head\":{\"server\":\"api.xianglianai.cn\"}}";
	}
	
	//通过key获取json对象value
	public static Object getValueByKey(String key,String jsonStr)
	{
		JSONObject jsonObject = JSONObject.fromObject(jsonStr);
		return jsonObject.get(key);
	}
	
	
	
	
	//解析info列表
	public static List<Ad> parseInfoList(String json){
		List<Ad> list = new ArrayList<Ad>();
		try {
			JSONObject jo = JSONObject.fromObject(json);
			JSONArray ja =  jo.getJSONArray("list");
			if(ja == null){
				System.out.println("######获取不到INFO列表#########");
				return list;
			}
			for(int i=0;i<ja.size();i++){
				JSONObject obj =(JSONObject) ja.get(i);
				String adName = obj.getString("title2");
				String pkg = obj.getString("pkg");
				String downurl = obj.getString("fileurl");
				String token = obj.getString("token");
				String versioname = obj.getString("versioname");
				Ad ad = new Ad(adName,pkg,downurl,token,versioname);
				list.add(ad);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
}
