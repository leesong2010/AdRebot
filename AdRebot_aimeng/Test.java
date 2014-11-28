package com.ad.appmob;

import java.util.Timer;

import com.ad.appmob.AppMob.MyTask;

public class Test {

	/**
	 * @param args
	 */
	
	private static Test t;
	public static void main(String[] args) {
		t = new Test();
		Timer timer = new Timer();
		timer.schedule(new MyTask(), 0, 1*1000);
	}

	
	static class MyTask extends java.util.TimerTask{
		@Override
		public void run() {
			System.out.println("call do it method");
			doIt();
		}
	}
	
	private static void doIt(){
		while(true){
			System.out.println("Do it...");
			try {
				Thread.sleep(5000);
			} catch (Exception e) {
			}
		}
	}
}
