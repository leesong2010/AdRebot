package com.ad.miidi;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import net.sf.json.JSONObject;

public class Net {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

	
	public static String postJSon(String strURL, JSONObject obj)throws Exception {  
       String resp = "";
       URL url = new URL(strURL);
       HttpURLConnection connection = (HttpURLConnection) url
               .openConnection();
       connection.setDoOutput(true);
       connection.setDoInput(true);
       connection.setRequestMethod("POST");
       connection.setUseCaches(false);
       connection.setInstanceFollowRedirects(true);
       connection.setRequestProperty("Content-Type",
               "application/x-www-form-urlencoded");
       connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式  
       connection.setRequestProperty("Host", "ads.miidi.net:80");
       connection.setRequestProperty("User-Agent", "Apache-HttpClient/UNAVAILABLE (java 1.4)");

      connection.connect();

      //POST请求
      DataOutputStream out = new DataOutputStream(
               connection.getOutputStream());
      out.writeBytes(obj.toString());
       out.flush();
       out.close();

      //读取响应
      BufferedReader reader = new BufferedReader(new InputStreamReader(
               connection.getInputStream()));
       String lines;
       StringBuffer sb = new StringBuffer("");
       while ((lines = reader.readLine()) != null) {
           lines = new String(lines.getBytes(), "utf-8");
           sb.append(lines);
       }
       resp = sb.toString();
       reader.close();
       // 断开连接
      connection.disconnect();
       return resp;
    }  
	
	public static String doGet(String url,Proxy proxy,String host,String UA) throws Exception{
		HttpURLConnection conn = null;
		StringBuffer sb = new StringBuffer();
		InputStream is = null;
		InputStreamReader isReader = null;
		BufferedReader bufReader = null;
		
		/*InetSocketAddress iss =  (InetSocketAddress)proxy.address();
		String ip = iss.getHostName();
		String port = String.valueOf(iss.getPort());
		boolean isAvailProxy = NetUtils.isAvailProxy(DianJoy.localIP,ip,port);
		if(!isAvailProxy)throw new Exception("Proxy not availbel!!!");*/
		try {
			URL mUrl = new URL(url);

			if(proxy == null)
				conn = (HttpURLConnection) mUrl.openConnection();
			else
				conn = (HttpURLConnection) mUrl.openConnection(proxy);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestProperty("Host", host);
			if(!"".equals(UA))
				conn.setRequestProperty(	"User-Agent",UA);
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
				throw new Throwable("Get Method:"+e.getMessage());
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
	
	
	public static String toPostData(String url,String data,String UA,Proxy p) throws Exception
	{
		PrintWriter out = null;
		BufferedReader in = null;
		URLConnection conn = null;
		StringBuffer result = new StringBuffer();
		
		
		/*InetSocketAddress iss =  (InetSocketAddress)p.address();
		String ip = iss.getHostName();
		String port = String.valueOf(iss.getPort());
		boolean isAvailProxy = NetUtils.isAvailProxy(DianJoy.localIP,ip,port);
		if(!isAvailProxy)throw new Exception("Proxy not availbel!!!");*/
		
		
		/*if("8088".equals(port) && DJContacts.useNewPort == true)
		{
			System.out.println("端口8088...");
			result.append(doGet(url, p, "", UA));
		}
		//else if(DJContacts.useGet == true && DJContacts.useNewPort == false && !"8088".equals(port))
		else if(DJContacts.useGet == true && DJContacts.useNewPort == false)
		{
			//System.out.println("Use Get Method...And No Use 8088 port");
			System.out.println("Use Get Method...");
			result.append(doGet(url, p, "nocheck", UA));
		}
		
		else{
			*/
		
		try {
			URL realUrl = new URL(url);
			if(p == null)
				conn = realUrl.openConnection();
			else
				conn = realUrl.openConnection(p);
			// 设置通用的请求属性
			conn.setRequestProperty("Host", "ads.miidi.net:80");
			conn.setRequestProperty("Accept", "*/*");
			conn.setRequestProperty("User-Agent",UA);
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// Post 请求不能使用缓存
			conn.setUseCaches(false);
			conn.setConnectTimeout(3 * 1000);
			conn.setReadTimeout(3 * 1000);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(data);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line = "";
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
		} catch (Exception e) {
			try {
				throw new Throwable("Post Method:"+e.getMessage());
			} catch (Throwable e1) {
				e1.getMessage();
			}
		}finally{
			if(in != null)in.close();
			conn = null;
		}
			
			
		//}
		
		
		return result.toString();
	}
	
	
}
