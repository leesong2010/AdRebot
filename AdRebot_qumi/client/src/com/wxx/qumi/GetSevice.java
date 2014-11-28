package com.wxx.qumi;

import java.io.File;
import java.util.List;

import com.wxx.qumi.R;
import com.ad.qumi.utils.AdUtils;
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

	private QuMiApp app;
	private static GetBrodcast brodcast;
	
	
	public void onCreate() {
		app = (QuMiApp)getApplicationContext();
		brodcast = new GetBrodcast();
		IntentFilter intentFilter=new IntentFilter();
		intentFilter.addAction(QuMiApp.STOP_GET);
		intentFilter.addAction(QuMiApp.GET_DEVICE);
		intentFilter.addAction(QuMiApp.GET_OLD_DEVICE);
		registerReceiver(brodcast, intentFilter);
		
		
		QuMiApp.Log("GetSerive OnCreate()");
	}
	
	public void onStart(Intent intent, int startId) {
		QuMiApp.Log("GetSerive OnStart()");
		
		
	}

	public void onDestroy() {
		super.onDestroy();
		app.setText("GetSevice onDestroy");
		QuMiApp.Log("GetSevice onDestroy");
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
			if(QuMiApp.STOP_GET.equals(act)){
				stopSelf();
			}
			
			//获取激活过的设备信息
			if(QuMiApp.GET_DEVICE.equals(act)){
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						try {
							app.setText(getString(R.string.getInfo));//"获取激活信息.."
							QuMiApp.Log("Getting active info.....");
							List<ActiveLog> list = AdUtils.getActiveLogs(QuMiApp.SERVER_URL, QuMiApp.ACTIVE_NAME, QuMiApp.GET_NUM);
							QuMiApp.Log(getString(R.string.get) +list.size()+getString(R.string.get1));
							app.setText(getString(R.string.get) +list.size()+getString(R.string.get1));
							
							if(list != null && list.size()>0)
								QuMiApp.appList.addAll(list);
							
						} catch (Exception e) {
							
							QuMiApp.Log(getString(R.string.getNetErr));
							app.setText(getString(R.string.getNetErr));
							e.printStackTrace();
						}
						
					}
				}).start();
			}
			
			//获取活跃设备
			if(QuMiApp.GET_OLD_DEVICE.equals(act)){
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						try {
							app.setText(getString(R.string.getOldInfo));//"获取活跃激活信息.."
							QuMiApp.Log("Getting Old Device  info.....");
							List<ActiveLog> list = AdUtils.getOldActiveLogs(QuMiApp.SERVER_URL, QuMiApp.ACTIVE_NAME, QuMiApp.GET_NUM);
							QuMiApp.Log("getted old devices:" +list.size());
							app.setText("获取到活跃设备:" +list.size());
							
							if(list != null && list.size()>0){
								for(ActiveLog al:list)al.setOld(true);
								QuMiApp.appList.add(list.get(0));
							}
							
						} catch (Exception e) {
							
							QuMiApp.Log(getString(R.string.getNetErr));
							app.setText(getString(R.string.getNetErr));
							e.printStackTrace();
						}
						
					}
				}).start();
			}
			
			
			
			
		}
		
	}
}
