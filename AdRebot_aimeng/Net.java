package com.ad.appmob;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

import org.nice.NetUtils;

import com.ad.dianjoy.DianJoy;

public class Net {
	public static String doGet(String url,MyIP myip,String host,String UA) throws Exception{
		HttpURLConnection conn = null;
		StringBuffer sb = new StringBuffer();
		InputStream is = null;
		InputStreamReader isReader = null;
		BufferedReader bufReader = null;
		
		
		if(myip != null){
			myip.setRequestUrl(Utils.getUrlPath(url));
			NetUtils.isAvailProxy(AppMob.localIP,myip);
			//if(!isAvailProxy)throw new Exception(myip.getIpUrl() + "#Í¸Ã÷´úÀí£º"+ip+":"+port);
		}
		
		try {
			
			URL mUrl = new URL(url);
			if(myip == null)
				conn = (HttpURLConnection) mUrl.openConnection();
			else
				conn = (HttpURLConnection) mUrl.openConnection(myip.getProxy());
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestProperty("Host", host);
			if(!"".equals(UA))
				conn.setRequestProperty("User-Agent",UA);
			
			conn.setConnectTimeout(3 * 1000);
			is = conn.getInputStream();
		    isReader = new InputStreamReader(is, "utf-8");
			bufReader = new BufferedReader(isReader);
			String newsContents = "";
			while ((newsContents = bufReader.readLine()) != null) {
				sb.append(newsContents);
			}
		} catch (Exception e) {
			try {
				throw new Exception(e.getMessage() + "#" +myip.getIp()+":"+myip.getPort()+"path:"+myip.getProxy().address());
			} catch (Throwable e1) {
				e1.getMessage();
			}
		}finally{
			if(bufReader != null)bufReader.close();
			if(isReader != null)isReader.close();
			if(is != null)is.close();
			conn = null;
		}
		
		
		
		
		return sb.toString();
	}
}
