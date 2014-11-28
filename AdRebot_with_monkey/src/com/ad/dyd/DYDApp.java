package com.ad.dyd;

import java.util.ArrayList;
import java.util.List;

import com.ad.vo.ActiveLog;

import android.app.Application;
import android.content.Intent;

public class DYDApp extends Application {
	public static List<ActiveLog> appList = new ArrayList<ActiveLog>();
	public final static String TAG = "dyd";
	public final static String ACTIVE_NAME = "daoyoudao";
	public final static String NO_HEART_JUMP = "no_heart_jump";//心跳机制,没有网络则发这个
	public final static String HEART_JUMP = "heart_jump";//心跳机制,有网络则发这个
	public final static String STOP_GET = "stop_get_service";
	public final static String GET_DEVICE = "get_device";//获取激活过的设备信息
	public final static String GET_OLD_DEVICE = "get_old_device";//获取活跃用户激活过的设备信息
	public final static int GET_NUM = 1;//一次性从服务器取几条
	public final static int DELAY = 10;
	public final static int MONEKY_TIMES = 100;
	public final static int STOPTIMS = 20;//如果停止心跳大于这个数，就重启设备
	public final static String SERVER_URL = "http://42.51.16.181:8080/adrobot-web";
	//public final static String SERVER_URL = "http://42.51.15.49:8089/adrobot-web";
	//public final static String SERVER_URL = "http://192.168.1.100:8080/adrobot-web";
	public static List<String> ipListAddress = new ArrayList<String>();//保存ip地址列表
	
	public static void Log(String str)
	{
		android.util.Log.d(TAG,str);
	}
	
	public void setText(String txt){
		try {
			String curText = getTextViewText();
			String newText = curText + "\n" + txt;
			
			
			Intent in = new Intent("adrebot_msg");
			if(MainActivity.instants.txtMsg.getLineCount()>=25)
				in.putExtra("msg", txt);
			else
				in.putExtra("msg", newText);
			sendBroadcast(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	//获取界面txtview文本
	private String getTextViewText()
	{
		String str = "";
		try {
			str = MainActivity.instants.txtMsg.getText().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return str;
		
	}
	
}
