package com.ad.zhimeng;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class JsonParse {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	}

	
	public static List<Ad> parseAdList(String json){
		List<Ad> list = new ArrayList<Ad>();
		try {
			JSONArray ja = JSONArray.fromObject(json);
			if(ja == null){
				System.out.println("######获取不到AD列表#########");
				return list;
			}
			for(int i=0;i<ja.size();i++){
				JSONObject obj =(JSONObject) ja.get(i);
				String pkg = obj.getString("pname");
				String downurl = obj.getString("url");
				String gid = obj.getString("gid");
				Ad ad = new Ad(pkg, downurl, gid);
				list.add(ad);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
}
