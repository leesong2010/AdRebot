package com.ad.rebot;

import java.util.Random;

import org.herojohn.adrobot.device.DeviceFactory;
import org.herojohn.adrobot.device.creator.impl.HaomatongDeviceWebCreator;
import org.herojohn.adrobot.device.model.DeviceConfig;
import org.herojohn.adrobot.device.model.HaomatongDevice;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

public class AdRebotMainActivity extends Activity implements OnGestureListener{

	private static final String TAG = "AdRebotMainActivity";
	HaomatongDeviceWebCreator haomatongDeviceCreator = (HaomatongDeviceWebCreator) DeviceFactory.getInstance("haomatong_web");
	//view
	private Button btnStart,btnStop;
	public TextView txtMsg,txtSucc,rights;
	private ScrollView sv;
	public static AdRebotMainActivity instants;
	private GestureDetector mGestureDetector;
	private int originTextColor;
	private float originTextSize;
	private String txtSuccMsg = "";
	//����
		//����120�벻������������Ϊ���Ѿ������ˣ���Ϊҹ����60��ִ��һ�Σ�����Ҫ����60
	private int howLongRegardServiceDied = 100 * 1000;
	private int heartBeat = 0;
	private boolean ServiceRunning = false;
	private MsgBrodcast msgBrodcard;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		init();
		showNiuBEffect();
	}

	@Override
	protected void onDestroy(){
		super.onDestroy();
		stopAdService();
		unregisterReceiver(msgBrodcard);
	}
	
	private void init(){
		//ע����Ϣ�㲥
		registerBrodcard();
		//init Views
		instants = this;
		txtMsg = (TextView)findViewById(R.id.txtMsg);
		txtSucc = (TextView)findViewById(R.id.txtSucc);
		//�������
		mGestureDetector = new GestureDetector(this);
		//���ü���	
		sv = (ScrollView)findViewById(R.id.sv_show);
		sv.setOnTouchListener(mOnTouchListener);
		
		btnStart = (Button)findViewById(R.id.btn_start);
		btnStart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startAdService();
			}
		});
		
		btnStop = (Button)findViewById(R.id.btn_stop);
		btnStop.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				stopAdService();
			}
		});
	}
	
	private void startAdService(){
		
		/*	new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						DeviceConfig config = new DeviceConfig();
						config.setEndpoint("http://42.51.16.181:8089/adrobot-web");
						config.setAdSense("haomatong");
						config.setAppName("360");
						config.setDevicesIndex(4);
						haomatongDeviceCreator.init(config);
						HaomatongDevice device  = haomatongDeviceCreator.getDevice();
						System.out.println("device="+device.getImei());
						haomatongDeviceCreator.saveInstalled(device);
						haomatongDeviceCreator.updateInstalled(device);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			}).start();*/
			
	
		
	
		if(!ServiceRunning){
			ServiceRunning = true;
			
			//changeRightsVisibility();
			changeUiVisibility();
			handler.post(runnable);	
			txtMsg.setText("");
			txtSucc.setText("");
			Intent in = new Intent(AdRebotMainActivity.this,RebotService.class);
			startService(in);					
			��ʼ����();
		}
	}

	private void stopAdService(){
		if(ServiceRunning){
			ServiceRunning = false;
			
			//changeRightsVisibility();
			changeUiVisibility();
			handler.removeCallbacks(runnable);
			����Handler.removeCallbacks(����Runnable);
			Intent in = new Intent("stop_ad_service");
			sendBroadcast(in);
		}
	}

	private void changeRightsVisibility(){
		if(ServiceRunning){
			rights.setVisibility(View.VISIBLE);
		}else{
			rights.setVisibility(View.INVISIBLE);
		}
	}
	
	private void changeUiVisibility(){
		if(btnStart.getVisibility() == View.VISIBLE){
			btnStart.setVisibility(View.INVISIBLE);
			btnStop.setVisibility(View.INVISIBLE);
		}else{
			btnStart.setVisibility(View.VISIBLE);
			btnStop.setVisibility(View.VISIBLE);
		}
	}
	
	
	//***************************************************//
	//************************��������**********************//
	private Handler ����Handler = new Handler();
	private Runnable ����Runnable = new Runnable(){
		@Override
		public void run(){						
			restartAdService();
			����Handler.postDelayed(����Runnable,howLongRegardServiceDied);            
		}
	}; 
	
	private void ��ʼ����(){
		����Handler.postDelayed(����Runnable,howLongRegardServiceDied);
	}
	
	private void ��������(){
		if( ServiceRunning ){
			����Handler.removeCallbacks(����Runnable);
			����Handler.postDelayed(����Runnable,howLongRegardServiceDied);
		}
	}

	private void restartAdService(){
		//��ֹͣ
		stopAdService();
		//�ӳ�10�룬�Եȴ�����runnable��receiver����ֹͣ
		sleep(10);		
		//������
		startAdService();		
	}
		
	private void sleep(int time){	
		//�ӳ� 10 �� 
		try {
			Thread.sleep(time * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	//***************************************************//
	//************************��������**********************//
	
	
	private Runnable runnable=new Runnable(){		
		public void run(){
			if (sv == null || txtMsg == null) {
                return;
	        }					
	        // �ڲ�߶ȳ������
	        int offset = txtMsg.getMeasuredHeight() - sv.getMeasuredHeight();
	        if (offset < 0) {
	             offset = 0;
	        }
	    	sv.scrollTo(0, offset);	    	
	    	handler.postDelayed(this, 1000);
		}
	};
	
	private static Handler handler = new Handler();
	
	private void registerBrodcard(){
		msgBrodcard = new MsgBrodcast();
		IntentFilter intentFilter=new IntentFilter();
		intentFilter.addAction("adrebot_msg");
		intentFilter.addAction("succ_msg");
		intentFilter.addAction("HeartBeat");
		registerReceiver(msgBrodcard, intentFilter);
	}
	
	private class MsgBrodcast extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			String act = intent.getAction();
			if("adrebot_msg".equals(act)){
				String msg = intent.getStringExtra("msg");
				if(txtMsg.getLineCount() > 100)txtMsg.setText("");
				txtMsg.setText(msg);
			}
			if("succ_msg".equals(act)){
				String msg = intent.getStringExtra("succ");
				txtSuccMsg = msg;
				txtSucc.setText(msg + "   ��������:" + heartBeat);
			}
			//�յ�����������������
			if("HeartBeat".equals(act)){
				Log.d(TAG,"������");
				++heartBeat;
				txtSucc.setText(txtSuccMsg + "   ��������:" + heartBeat);
				��������();				
			}
		}		
	}

	//����
	private OnTouchListener mOnTouchListener = new OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			return mGestureDetector.onTouchEvent(event);
		}
	};
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return mGestureDetector.onTouchEvent(event);
	}
	
	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		changeUiVisibility();
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		return false;
	}

	//NiuB Effect
	private void showNiuBEffect(){
		new Thread(){
			@Override
			public void run(){
				saveOriginNiuBTextStatusAndChange();
				for(int i = 0;i < 30;i ++){
					try {
						Thread.sleep(30);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					niuBHandler.sendEmptyMessage('X');
				}
				niuBTextHandler.sendEmptyMessage('G');					
			}
		}.start();	
	}

	private void saveOriginNiuBTextStatusAndChange(){
		originTextColor = txtMsg.getCurrentTextColor();
		originTextSize = txtMsg.getTextSize();
		niuBTextHandler.sendEmptyMessage('W');
	}

	private void changeToNiuBText(){
		txtMsg.setTextColor(android.graphics.Color.WHITE);
		txtMsg.setTextSize(10);
	}
	
	private void restoreNiuBTextStatus(){
		txtMsg.setTextColor(originTextColor);
		txtMsg.setTextSize(TypedValue.COMPLEX_UNIT_PX,originTextSize);
		txtMsg.setText("    ������Ļѡ��Start,��������Ȩ��");
	}
	
	private Handler niuBTextHandler = new Handler(){
		@Override
		public void handleMessage(Message msg){
			super.handleMessage(msg);
			if(msg.what == 'W'){
				changeToNiuBText();				
			}else if(msg.what == 'G'){
				restoreNiuBTextStatus();
			}
		}
	};
	
    private Handler niuBHandler = new Handler(){
    	public void handleMessage(Message msg){
    		super.handleMessage(msg);
    		txtMsg.setText(txtMsg.getText() + "\n" +  getRandomString(new Random().nextInt(100)));
    		scroll();
    }};
    
	private void scroll(){
        int offset = txtMsg.getMeasuredHeight() - sv.getMeasuredHeight();
        if (offset < 0) offset = 0;
        sv.scrollTo(0, offset);
	}

	public String getRandomString(int length) {
		String val = "";
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
			if ("char".equalsIgnoreCase(charOrNum)) {
				int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
				val += (char) (choice + random.nextInt(26));
			} else if ("num".equalsIgnoreCase(charOrNum)) {
				val += String.valueOf(random.nextInt(10));
			}
		}
		return val;
	}
}
