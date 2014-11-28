package com.wxx.qumi;

import com.wxx.qumi.R;
import com.ad.qumi.utils.MyLoger;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends Activity {

	
	private MsgBrodcast msgBrodcard;
	
	public TextView txtMsg,txtSucc;
	private ScrollView sv;
	public static MainActivity instants;
	private CheckBox chkBox;
	private MyLoger log = new MyLoger("reboot");
	private int heartBeat = 0;
	private boolean ServiceRunning = false;
	private int howLongRegardServiceDied = 30 * 1000;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("MainActivity","OnCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		registerBrodcard();//ע����Ϣ�㲥
		
		instants = this;
		txtMsg = (TextView)findViewById(R.id.txtMsg);
		txtSucc = (TextView)findViewById(R.id.txtSucc);
		sv = (ScrollView)findViewById(R.id.sv_show);
		
		//����ǿ���������
		boolean autorun = getIntent().getBooleanExtra("autorun", false);
		if(autorun){
			log.w("����������...");
			buttonStartFun();
		}
		
		Button btnStart = (Button)findViewById(R.id.btn_start);
		btnStart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			    buttonStartFun();
				
			}
		});
		
		
		
		
		
		Button btnStop = (Button)findViewById(R.id.btn_stop);
		btnStop.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				stopService();
			}
		});
		
		
	 chkBox = (CheckBox)findViewById(R.id.chk);
	 chkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			if(isChecked){
				Intent in = new Intent("get_old_device_on");
				sendBroadcast(in);
			}
			else{
				Intent in = new Intent("get_old_device_off");
				sendBroadcast(in);
			}
		}
	  });
		
	
		
	}

	int i = 0;
	
	private void stopService(){
		if(ServiceRunning)
			ServiceRunning = false;
		handler.removeCallbacks(runnable);
		Intent in = new Intent("stop_ad_service");
		sendBroadcast(in);
		
		 in = new Intent(QuMiApp.STOP_GET);
		 sendBroadcast(in);
		}
	
	
	Runnable heartRun=new Runnable()
	{
		public void run() 
		{
			handler.postDelayed(this, 20*1000);
		}
	};
	
	
	Runnable runnable=new Runnable()
	{
		public void run() 
		{
			if (sv == null || txtMsg == null) {
                return;
        }
			
			
        // �ڲ�߶ȳ������
        int offset = txtMsg.getMeasuredHeight()
                        - sv.getMeasuredHeight();
        if (offset < 0) {
                offset = 0;
        }
        	sv.scrollTo(0, offset);
        	
        	handler.postDelayed(this, 1000);
		}
	};
	
	static Handler handler = new Handler();
	public static void scroll2Bottom(final ScrollView scroll, final View inner) {
        
        handler.post(new Runnable()
        {
        	
                @Override
                public void run() {
                    
                    
                }
                
        });
        
	}
	
	private void registerBrodcard()
	{
		msgBrodcard = new MsgBrodcast();
		IntentFilter intentFilter=new IntentFilter();
		intentFilter.addAction("adrebot_msg");
		intentFilter.addAction("succ_msg");
		registerReceiver(msgBrodcard, intentFilter);
	}
	
	private class MsgBrodcast extends BroadcastReceiver
	{

		@Override
		public void onReceive(Context context, Intent intent) {
			String act = intent.getAction();
			if("adrebot_msg".equals(act)){
				String msg = intent.getStringExtra("msg");
				txtMsg.setText(msg);
			}
			if("succ_msg".equals(act)){
				String msg = intent.getStringExtra("succ");
				txtSucc.setText(msg);
			}
			
			//�յ�����������������
			if("HeartBeat".equals(act)){
				QuMiApp.Log("������...");
				++heartBeat;
				��������();				
			}
		}
		
	}
	
	private void buttonStartFun(){
		
		if(!ServiceRunning){
			ServiceRunning = true;
		
			handler.post(runnable);
			handler.postDelayed(heartRun	,2000);
			txtMsg.setText("");
			txtSucc.setText("");
			Intent in = new Intent(MainActivity.this,RebotService.class);
			startService(in);
			
			in = new Intent(MainActivity.this,GetSevice.class);
			startService(in);
			
			/*in = new Intent(MainActivity.this,HeartService.class);
			startService(in);*/
			
			in = new Intent(QuMiApp.GET_DEVICE);
			sendBroadcast(in);
			
			
			if(chkBox.isChecked()){
				in = new Intent("get_old_device_on");
				sendBroadcast(in);
			}
			else{
				in = new Intent("get_old_device_off");
				sendBroadcast(in);
			}
			
			//��ʼ����();
		}
	}
	
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(msgBrodcard);
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
		stopService();
		//�ӳ�10�룬�Եȴ�����runnable��receiver����ֹͣ
		sleep(10);		
		//������
		buttonStartFun();
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
}
