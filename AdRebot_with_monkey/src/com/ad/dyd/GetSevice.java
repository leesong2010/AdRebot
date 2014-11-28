package com.ad.dyd;

import java.io.File;
import java.util.List;

import com.ad.dyd.utils.AdUtils;
import com.ad.vo.ActiveLog;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

public class GetSevice extends Service {

	private DYDApp app;
	private static GetBrodcast brodcast;
	
	
	public void onCreate() {
		app = (DYDApp)getApplicationContext();
		brodcast = new GetBrodcast();
		IntentFilter intentFilter=new IntentFilter();
		intentFilter.addAction(DYDApp.STOP_GET);
		intentFilter.addAction(DYDApp.GET_DEVICE);
		intentFilter.addAction(DYDApp.GET_OLD_DEVICE);
		registerReceiver(brodcast, intentFilter);
		
		
		DYDApp.Log("GetSerive OnCreate()");
	}
	
	public void onStart(Intent intent, int startId) {
		DYDApp.Log("GetSerive OnStart()");
		
		
	}

	public void onDestroy() {
		super.onDestroy();
		app.setText("GetSevice onDestroy");
		DYDApp.Log("GetSevice onDestroy");
		unregisterReceiver(brodcast);
	}
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	private class GetBrodcast extends BroadcastReceiver
	{

		@Override
		public void onReceive(Context context, Intent intent) {
			String act = intent.getAction();
			//停止本服务器
			if(DYDApp.STOP_GET.equals(act)){
				stopSelf();
			}
			
			//获取激活过的设备信息
			if(DYDApp.GET_DEVICE.equals(act)){
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						try {
							app.setText(getString(R.string.getInfo));//"获取激活信息.."
							DYDApp.Log("Getting active info.....");
							List<ActiveLog> list = AdUtils.getActiveLogs(DYDApp.SERVER_URL, DYDApp.ACTIVE_NAME, DYDApp.GET_NUM);
							DYDApp.Log(getString(R.string.get) +list.size()+getString(R.string.get1));
							app.setText(getString(R.string.get) +list.size()+getString(R.string.get1));
							
							if(list != null && list.size()>0)
								DYDApp.appList.addAll(list);
							
						} catch (Exception e) {
							
							DYDApp.Log(getString(R.string.getNetErr));
							app.setText(getString(R.string.getNetErr));
							e.printStackTrace();
						}
						
					}
				}).start();
			}
			
			//获取活跃设备
			if(DYDApp.GET_OLD_DEVICE.equals(act)){
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						try {
							app.setText(getString(R.string.getOldInfo));//"获取活跃激活信息.."
							DYDApp.Log("Getting Old Device  info.....");
							List<ActiveLog> list = AdUtils.getOldActiveLogs(DYDApp.SERVER_URL, DYDApp.ACTIVE_NAME, DYDApp.GET_NUM);
							DYDApp.Log("getted old devices:" +list.size());
							app.setText("获取到活跃设备:" +list.size());
							
							if(list != null && list.size()>0){
								for(ActiveLog al:list)al.setOld(true);
								DYDApp.appList.add(list.get(0));
							}
							
						} catch (Exception e) {
							
							DYDApp.Log(getString(R.string.getNetErr));
							app.setText(getString(R.string.getNetErr));
							e.printStackTrace();
						}
						
					}
				}).start();
			}
			
			
			
			
		}
		
	}
}
