package com.ad.appmob;

import java.net.Proxy;

public class MyIP {
	private Proxy proxy;
	private String ip;
	private String port;
	private String ipUrl;
	
	private String requestUrl;
	public MyIP(String ip,String port,String ipUrl,Proxy p){
		this.ip = ip;
		this.port = port;
		this.ipUrl = ipUrl;
		this.proxy = p;
	}

	

	public Proxy getProxy() {
		return proxy;
	}



	public void setProxy(Proxy proxy) {
		this.proxy = proxy;
	}



	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getIpUrl() {
		return ipUrl;
	}

	public void setIpUrl(String ipUrl) {
		this.ipUrl = ipUrl;
	}



	public String getRequestUrl() {
		return requestUrl;
	}



	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}
	
	
	
	
}
