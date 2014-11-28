package com.ad.dyd.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import com.ad.dyd.utils.ShellCommand.CommandResult;

import android.content.Context;
import android.util.Log;


public class Proxy {
	
	private final String TAG = "Proxy";
	
	//代理相关变量 
	private final int START = 1;
	private final int STOP = 2;
	private String host;
	private String port;
	private String basedir;	
	private Context context;
	
	public Proxy(Context context){
		this.context = context;
		initProxy();
	}

	//开始设置代理
	public void startProxy(final String host,final String port){
		this.host = host;
		this.port = port;
		proxy(STOP);
		try {
			//Thread.sleep(1000);
		} catch (Exception e) {
			Log.d(TAG,e.getMessage());
		}
		proxy(START);
	}
	
	//停止代理
	public void stopProxy(){
		proxy(STOP);
	}
	
    //初始化代理相关
	private void initProxy(){
		File f = new File("/system/xbin/iptables");
		if (!f.exists()) {
			f = new File("/system/bin/iptables");
			if (!f.exists()) {
				Log.d(TAG,"No iptables binary found on your ROM !");
			}
		}

		f = new File("/system/xbin/su");
		if (!f.exists()) {
			f = new File("/system/bin/su");
			if (!f.exists()) {
				Log.d(TAG,"No su binary found on your ROM !");
			}
		}

		try {
		 basedir = context.getFilesDir().getAbsolutePath();
		} catch (Exception e) {}

		copyfile("redsocks");
		copyfile("proxy.sh");
		copyfile("redirect.sh");		
	}
		
	//代理的干活函数
	public boolean proxy(int action) {

		if (action == START) {
						
			Boolean auth = false;
			String user = "";
			String pass = "";
			String domain = "";
			String proxy_type = "http";
						
			String ipaddr;

			if (host == null || host.trim().equals("")) {
				Log.d(TAG,"Hostname/IP is empty");
				return false;
			}
			if (port == null || port.trim().equals("")) {
				Log.d(TAG,"Port is NULL");
				return false;
			}
			if (auth) {
				if (user.trim().equals("")) {
					Log.d(TAG,"Auth is enabled but username is NULL");
					return false;
				}
				if (pass.trim().equals("")) {
					Log.d(TAG,"Auth is enabled but password is NULL");
					return false;
				}
			}
			try {
				InetAddress addr = InetAddress.getByName(host.trim());
				ipaddr = addr.getHostAddress();
			} catch (UnknownHostException e) {
				Log.d(TAG,"Cannot resolve hostname "+host); 
				return false;
			}
			Log.v("tproxy","proxy.sh start " + basedir + " "
					+"type=" + proxy_type + " "
					+"host=" + ipaddr + " "
					+"port=" + port.trim() + " "
					+"auth=" + auth + " "
					+"user=" + user.trim() + " "
					+"pass=*****"
					+"domain=" + domain.trim());
			
			ShellCommand cmd = new ShellCommand();
			CommandResult r = cmd.sh.runWaitFor(basedir+"/proxy.sh start " + basedir
					 	+ " " + proxy_type
					 	+ " " + ipaddr
						+ " " + port.trim()
						+ " " + auth
						+ " " + user.trim()
						+ " " + pass.trim()
						+ " " + domain.trim());

			if (!r.success()) {
			    Log.v("tproxy", "Error starting proxy.sh (" + r.stderr + ")");
				cmd.sh.runWaitFor(basedir+"/proxy.sh stop "+ basedir);
				Log.d(TAG,"Failed to start proxy.sh ("+ r.stderr + ")");
			    return false;
			} 
				 
			if (checklistener()) {
				 r = cmd.su.runWaitFor(basedir+"/redirect.sh start " + proxy_type);
				 
				 if (!r.success()) {
				    Log.v("tproxy", "Error starting redirect.sh (" + r.stderr +")");
					cmd.sh.runWaitFor(basedir+"/proxy.sh stop "+ basedir);
					Log.d(TAG,"Failed to start redirect.sh ("+ r.stderr + ")");
				    return false;
				} else {
					Log.d(TAG, "Successfully ran redirect.sh start " + proxy_type);
					return true;
				}
			} else {
				Log.d(TAG,"Proxy failed to start");
				return false;
			}
		} else {
		 	Log.v("tproxy", "Successfully ran redirect.sh stop");
			ShellCommand cmd = new ShellCommand();
			cmd.sh.runWaitFor(basedir+"/proxy.sh stop "+basedir);
			cmd.su.runWaitFor(basedir+"/redirect.sh stop");
			return true;
		}
    }

	//拷贝代理所需的脚本文件 
	public void copyfile(String file) {
		String of = file;
		File f = new File(of);

		if (!f.exists()) {
			try {
				InputStream in = context.getAssets().open(file);
				FileOutputStream out = context.openFileOutput(of, Context.MODE_PRIVATE);

				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				out.close();
				in.close();
				Runtime.getRuntime().exec("chmod 700 " + basedir + "/" + of);
			} catch (IOException e) {
			}
		}
	}
	
	//检查Socket
	public boolean checklistener() {
		Socket socket = null;
		try {
			socket = new Socket("127.0.0.1", 8123);
		} catch (Exception e) {
		}

		if (socket != null && socket.isConnected()) {
			try {
				socket.close();
			} catch (Exception e) {
			}
			return true;
		} else {
			return false;
		}
	}
	
}
