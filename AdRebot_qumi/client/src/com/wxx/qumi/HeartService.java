package com.wxx.qumi;



import java.io.File;

import com.ad.qumi.utils.AdUtils;
import com.ad.qumi.utils.MyLoger;
import com.ad.qumi.utils.ShellCommand;

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
	private QuMiApp app;
	private MyLoger log = new MyLoger("reboot");
	private boolean reloadWifi = false;//�Ƿ��ѹر�wifi���ٴ�wifi��
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	public void onCreate() {
		app = (QuMiApp)getApplicationContext();
		brodcast = new HeartBrodcast();
		IntentFilter intentFilter=new IntentFilter();
		intentFilter.addAction(QuMiApp.NO_HEART_JUMP);
		intentFilter.addAction(QuMiApp.HEART_JUMP);
		intentFilter.addAction("stop_ad_service");
		registerReceiver(brodcast, intentFilter);
		
	}
	
	public void onStart(Intent intent, int startId) {
		QuMiApp.Log("HeartService onStart()");
		handler.post(runnable);
	}
	
	
	public void onDestroy() {
		super.onDestroy();
		app.setText("HeartService onDestroy");
		QuMiApp.Log("HeartService onDestroy");
		unregisterReceiver(brodcast);
	}
	
	private class HeartBrodcast extends BroadcastReceiver
	{

		@Override
		public void onReceive(Context context, Intent intent) {
			String act = intent.getAction();
			if(act.equals(QuMiApp.HEART_JUMP)){
				stop_times = 0;
			}
			
			if(act.equals(QuMiApp.NO_HEART_JUMP)){
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
					QuMiApp.Log("�������...ֹͣ����:" + stop_times);
					//�ж�ֹͣ�����Ĵ����Ƿ���ڹ涨����
					if(stop_times >=QuMiApp.STOPTIMS){
						//˵��û�г��Թرա���wifi
						if(reloadWifi == false)
						{
							new Thread(new Runnable() {
								
								@Override
								public void run() {
									/*QuMiApp.Log("�ر�wifi..");
									//��ͣwifi
									AdUtils.switchWIFI(false);
									log.w("�ر�wifi...");
									try {
										Thread.sleep(3*1000);
									} catch (Exception e) {
									}*/
									stop_times = 0;
									reloadWifi = true;
									log.w("��wifi...");
									QuMiApp.Log("��wifi..");
									//�ٴ�wifi
									AdUtils.switchWIFI(true);
									
									
									
								}
							}).start();
							
							
						}
						else
						{
							new Thread(new  Runnable() {
								public void run() {
									//ɾ�������ļ�
									File f = new File(Environment.getExternalStorageDirectory().getPath() + "/paramConfig");
									if(f.exists())f.delete();
									log.w("�����豸");
									//�����豸 
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
