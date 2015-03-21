package com.ad.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;

import android.util.Log;

import com.ad.rebot.HMTContant;
import com.ad.vo.IP;

public class CheckIP {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

	//获取本机电信ip
	public static String getLocalIP()
	{
		String result  = "";
		String html = ""; 
		html = getIpCheckReps("http://1111.ip138.com/ic.asp");
		if(!"".equals(html))
		{
				result = html.substring(html.indexOf("[") + 1,html.indexOf("]") );
		}
		else
		{
			html = getIpCheckReps("http://ip.blueera.net/api?type=text");
			result = parseIpAddr(html,2);
		}
		
		return result;
	}
	
	public static String parseIpAddr(String html,int which)
	{
		String ip = "";
		if(which == 1)
		{
			Pattern pt = Pattern.compile("<code>(.*?)</code>");
			//Pattern pt = Pattern.compile("<body>(.*?)<!");
			Matcher m = pt.matcher(html);
			if(m.find()){
				ip = m.group(1);
			}
		}
		if(which == 2)
		{
			ip = html.split(" ")[0];
		}
		
		return ip.trim();
	}
	
	
	
	public static List<String> getIPList(String urlStr){
		List<String> list = new ArrayList<String>();
		BufferedReader in = null;
		URLConnection conn = null;
		StringBuffer result = new StringBuffer();
		try {
			URL realUrl = new URL(urlStr);
			// 打开和URL之间的连接
			conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("Accept", "*/*");
			// conn.setRequestProperty("User-Agent",
			// "ymxh/1.55 CFNetwork/609.1.4 Darwin/13.0.0 Paros/3.2.13");
			conn.setRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			// 发送POST请求必须设置如下两行
			// conn.setDoOutput(true);
			conn.setDoInput(true);
			// Post 请求不能使用缓存
			conn.setUseCaches(false);
			conn.setConnectTimeout(5 * 1000);
			conn.setReadTimeout(5 * 1000);
			// 获取URLConnection对象对应的输出流

			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line = "";
			while ((line = in.readLine()) != null) {
				Log.d("Main",line);
				if (!"".equals(line) && line.contains(":")) {
					list.add(line.trim());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			new Exception("ip");
			return list;
		} finally {
			try {
				if (conn != null)
					conn = null;
				if (in != null) {
					in.close();
					in = null;
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				return list;
			}
		}
		return list;
	}
	
	public static IP getIP(String s)
	{
		IP ipvo = null;
		try {
			String ip = s.split(":")[0];
			
			int f = s.lastIndexOf(":") + 1;
			int e = s.length();
			String port = s.substring(f,e);
			ipvo = new IP(ip, port);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ipvo;
	}
	
	
	private static final String LAN_IP_REGEX = "(127[.]0[.]0[.]1)|" + "(localhost)|" + "(10[.]\\d{1,3}[.]\\d{1,3}[.]\\d{1,3})|" +
			"(172[.]((1[6-9])|(2d)|(3[01]))[.]\\d{1,3}[.]\\d{1,3})|" + "(192[.]168[.]\\d{1,3}[.]\\d{1,3})";
	private static final String WAN_IP_REGEX = "(2[5][0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})";
	
	//验证ip的正则表达式
	public static boolean isValidIP(String ip) {
		if(ip.matches(LAN_IP_REGEX)) {
			return false;
		}
		return ip.matches(WAN_IP_REGEX);
	}
	
	public static boolean isAvailProxy(String localIP,String proxyIP,String port)
	{
		if(!isValidIP(proxyIP))return false;
		
		String checkUrl = "";
     	
		String result = ""; 
		checkUrl = "http://1111.ip138.com/ic.asp";
		//checkUrl = "http://ip.blueera.net/api?type=text";
		result = getIpCheckReps(checkUrl).trim();
		Log.d("Main",result);
		//如果返回null，则用另外一个验证地址去验证
	/*	if("".equals(result))
		{
			result = getIpCheckReps("").trim();
			checkUrl ="http://ip.blueera.net/api?type=text";
		}*/
		
		
		if("".equals(result))return false;
		else
		{
			/*if(checkUrl.contains("http://1111.ip138.com/ic.asp"))
			{*/
				if(result.contains(localIP))
					return false;
				else
						return true;
/*			}
			else
			{
				result = parseIpAddr(result,2);
				if(!isValidIP(result))return false;
				if(result.contains(localIP))return false;
				else return true;
			}*/
		}
	}
	
	public static String getIpCheckReps(String url)
	{
		HttpGet getMethod = new HttpGet(url); 
		BasicHttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 2*1000);
		HttpConnectionParams.setSoTimeout(httpParams, 2*1000);
		HttpClient httpClient = new DefaultHttpClient(httpParams);  
		String result  = "";
		String encodeType = "";
		if(url.contains("ip138"))encodeType = "gbk";
		else encodeType = "utf-8";
		encodeType="utf-8";
		try {  
		    HttpResponse response = httpClient.execute(getMethod); //发起GET请求  
		  
		    int code = response.getStatusLine().getStatusCode(); //获取响应码  
		    if(code == 200)
		    {
		    		result =  EntityUtils.toString(response.getEntity(), encodeType);//获取服务器响应内容 
		    }
		} catch (Exception e) {  
		    e.printStackTrace();  
		} 
		
		return result;
	}
	
/*	public static List<String> getIps(int num)
	{
		List<String> ips = new ArrayList<String>();
		//String url="http://www.yun-ip.com/api.php?key=652908815738223&filterip=on&getnum=" + num;
		String url = "http://www.56pu.com/api?orderId=693466099408223&line=all&region=&regionEx=&beginWith=&ports=&vport=&speed=&anonymity=&scheme=&duplicate=3&sarea=&quantity=" + num; 
				//"http://www.56pu.com/api/q?orderId=575366356888223&line=all&region=&beginWith=&ports=&duplicate=2&anonymity=3&quantity=" + num;
		List<String> ipList = getIPList(url);
		Log.d("Main","获取ip数量:"+ipList.size());
		ips = ipList;
		return ips;
	}*/
	
	public static List<String> getIps(int num) throws Exception
	{
		List<String> ips = new ArrayList<String>();
		String tmp = HMTContant.ipListAddress.get(new Random().nextInt(HMTContant.ipListAddress.size()));
		String url = tmp.split(",")[1] + num;
		String whichIp= tmp.split(",")[0];
		/*DYDApp.Log("from[ "+whichIp+" ] getting ip...");
		DYDApp.Log(url);*/
		Log.d("HMT",url);
				//"http://www.56pu.com/api/q?orderId=575366356888223&line=all&region=&beginWith=&ports=&duplicate=2&anonymity=3&quantity=" + num;
		List<String> ipList = getIPListByAndroid(url); //getIPList(url);
		Log.d("Main","[ + " +whichIp +" ] getted ip num:"+ipList.size());
		ips = ipList;
		return ips;
	}
	
	public static List<String> getIPListByAndroid(String url){
		List<String> ips = new ArrayList<String>();
		try
		{
			 HttpGet httpRequest = new HttpGet(url);
			//取得HttpClient对象
			HttpClient httpclient = new DefaultHttpClient();
			httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000);
             // 读取超时
			httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 3000    );
			//请求HttpClient，取得HttpResponse
			HttpResponse httpResponse = httpclient.execute(httpRequest);
			//请求成功
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
			{
				//取得返回的字符串
				String strResult = EntityUtils.toString(httpResponse.getEntity());
				Log.d("HMT",strResult);
				if (!"".equals(strResult) && strResult.contains(":")) {
					ips.add(strResult.trim());
				}
			}
			else
			{
				Log.d("HMT","获取ip失败...");
				//resultText.setText("请求错误!");
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		
		return ips;
	}
}
