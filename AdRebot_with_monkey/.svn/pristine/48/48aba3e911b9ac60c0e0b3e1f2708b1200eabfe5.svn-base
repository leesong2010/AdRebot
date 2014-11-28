package com.ad.dyd;



import java.io.File;

import com.ad.dyd.utils.AdUtils;
import com.ad.dyd.utils.MyLoger;
import com.ad.dyd.utils.ShellCommand;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

public class HeartService extends Service {
	
	private static HeartBrodcast brodcast;
	private static int stop_times = 0;
	private DYDApp app;
	private MyLoger log = new MyLoger("reboot");
	private boolean reloadWifi = false;//是否已关闭wifi，再打开wifi过
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	public void onCreate() {
		app = (DYDApp)getApplicationContext();
		brodcast = new HeartBrodcast();
		IntentFilter intentFilter=new IntentFilter();
		intentFilter.addAction(DYDApp.NO_HEART_JUMP);
		intentFilter.addAction(DYDApp.HEART_JUMP);
		intentFilter.addAction("stop_ad_service");
		registerReceiver(brodcast, intentFilter);
		
	}
	
	public void onStart(Intent intent, int startId) {
		DYDApp.Log("HeartService onStart()");
		handler.post(runnable);
	}
	
	
	public void onDestroy() {
		super.onDestroy();
		app.setText("HeartService onDestroy");
		DYDApp.Log("HeartService onDestroy");
		unregisterReceiver(brodcast);
	}
	
	private class HeartBrodcast extends BroadcastReceiver
	{

		@Override
		public void onReceive(Context context, Intent intent) {
			String act = intent.getAction();
			if(act.equals(DYDApp.HEART_JUMP)){
				stop_times = 0;
			}
			
			if(act.equals(DYDApp.NO_HEART_JUMP)){
				stop_times++;
			}
			
			if("stop_ad_service".equals(act)){
				handler.removeCallbacks(runnable);
				stopSelf();
			}
		}
	}
	
	
	Runnable runnable=new Runnable(){
		public void run() 
		{
			try {
					DYDApp.Log("心跳监测...停止次数:" + stop_times);
					//判断停止心跳的次数是否大于规定次数
					if(stop_times >=DYDApp.STOPTIMS){
						//说明没有尝试关闭、打开wifi
						if(reloadWifi == false)
						{
							new Thread(new Runnable() {
								
								@Override
								public void run() {
									/*QuMiApp.Log("关闭wifi..");
									//先停wifi
									AdUtils.switchWIFI(false);
									log.w("关闭wifi...");
									try {
										Thread.sleep(3*1000);
									} catch (Exception e) {
									}*/
									stop_times = 0;
									reloadWifi = true;
									log.w("打开wifi...");
									DYDApp.Log("打开wifi..");
									//再打开wifi
									AdUtils.switchWIFI(true);
									
									
									
								}
							}).start();
							
							
						}
						else
						{
							new Thread(new  Runnable() {
								public void run() {
									//删除配置文件
									File f = new File(Environment.getExternalStorageDirectory().getPath() + "/paramConfig");
									if(f.exists())f.delete();
									log.w("重启设备");
									//重启设备 
									AdUtils.rebootDevice();
								}
							}).start();
							
						}
						
						
				}
				handler.postDelayed(this, 2*1000);
			} catch (Exception e) {
				//handler.postDelayed(this, 2*1000);
				e.printStackTrace();
			}

		}
	};
	
	Handler handler = new Handler() {  
	    public void handleMessage(Message msg) {  
	    	
	        super.handleMessage(msg);  
	    }  
	};  
}
