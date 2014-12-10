package com.ad.zhimeng;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;

import org.nice.NetUtils;

import com.ad.appmob.MyIP;
import com.ad.dianjoy.DianJoy;

public class Net {
	public static String doGet(String url,MyIP myip,String host,String UA) throws Exception{
		HttpURLConnection conn = null;
		StringBuffer sb = new StringBuffer();
		InputStream is = null;
		InputStreamReader isReader = null;
		BufferedReader bufReader = null;
		
		
		if(myip != null){
			myip.setRequestUrl(com.ad.appmob.Utils.getUrlPath(url));
			NetUtils.isAvailProxy(ZM.localIP,myip);
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
			//conn.setRequestProperty("Accept-Encoding", "gzip");
			if(!"".equals(UA))
				conn.setRequestProperty("User-Agent",UA);
			
			conn.setConnectTimeout(3 * 1000);
		//is = new GZIPInputStream(conn.getInputStream());
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
	
	public static String toPostData(String url,String data,MyIP myip,String host,String UA) throws Exception{
	{
		PrintWriter out = null;
		BufferedReader in = null;
		URLConnection conn = null;
		StringBuffer result = new StringBuffer();
		
		
		if(myip != null){
			myip.setRequestUrl(com.ad.appmob.Utils.getUrlPath(url));
			NetUtils.isAvailProxy(ZM.localIP,myip);
		}
		
		
		
		try {
			URL realUrl = new URL(url);
			if(myip == null)
				conn = realUrl.openConnection();
			else
				conn = realUrl.openConnection(myip.getProxy());
			// 设置通用的请求属性
			conn.setRequestProperty("Host", host);
			if(!"".equals(UA))
				conn.setRequestProperty("User-Agent",UA);
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
				throw new Exception(e.getMessage() + "#" +myip.getIp()+":"+myip.getPort()+"path:"+myip.getProxy().address());
			} catch (Throwable e1) {
				e1.getMessage();
			}
		}finally{
			if(in != null)in.close();
			conn = null;
		}
			
		return result.toString();
	}
	}
}
