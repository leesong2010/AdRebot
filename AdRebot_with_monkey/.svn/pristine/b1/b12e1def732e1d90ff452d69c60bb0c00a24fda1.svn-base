/**
 * 
 */
package org.herojohn.adrobot.device.creator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.herojohn.adrobot.device.util.MD5;

import com.ad.dyd.utils.AdUtils;

/**
 * @author hezhou
 *
 */
public class DeviceWebCreator {
	private static final String secert = "b5j9G7F4e2t0M1==";
	
	protected static String getSign(String queryString) {
		return MD5.hash(queryString+'@'+secert);
	}

	protected static String doGet(String url) throws Exception {
		/*HttpURLConnection conn = null;
		BufferedReader bufReader = null;
		try {
			StringBuilder sb = new StringBuilder();
			conn = (HttpURLConnection) new URL(url).openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setConnectTimeout(400000);
			conn.setReadTimeout(400000);
			bufReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			String newsContents = "";
			while ((newsContents = bufReader.readLine()) != null) {
				sb.append(newsContents);
			}
			return sb.toString();
		}catch(Exception e) {
			throw new Exception(e.getMessage());
		}finally {
			if(bufReader != null)
				bufReader.close();
		}	*/
		HttpGet getMethod = new HttpGet(url); 
		BasicHttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 5*1000);
		HttpConnectionParams.setSoTimeout(httpParams, 5*1000);
		HttpClient httpClient = new DefaultHttpClient(httpParams);  
		String result  = "";
		String encodeType = "utf-8";
		
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
	
	
	
//	 public static String toPostData(String url,String data) 
//	 {
//		 String resultStr="";
//		 BasicHttpParams httpParams = new BasicHttpParams();
//		 HttpConnectionParams.setConnectionTimeout(httpParams, 5*1000);
//		 HttpConnectionParams.setSoTimeout(httpParams, 5*1000);
//         HttpClient httpclient = new DefaultHttpClient(); 
//         //参数列表
//         List<NameValuePair> nameValuePairs =new ArrayList<NameValuePair>();
//         nameValuePairs.add(new BasicNameValuePair("",data));
//         HttpPost httppost = new HttpPost(url);
//         try {
//            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//            HttpResponse response; 
//            response=httpclient.execute(httppost);     
//            resultStr=EntityUtils.toString(response.getEntity());
//        } catch (Exception e) {
//            e.printStackTrace();
//        } 
//        return resultStr;
//	 }
	
	
	
	protected static String toPostData(String url,String data)  {
		//PrintWriter out = null;
//		BufferedReader in = null;
//		HttpURLConnection conn = null;
//		StringBuffer result = new StringBuffer();
//		try {
//			
//			URL realUrl = new URL(url);
//			conn = (HttpURLConnection) realUrl.openConnection();
//			
//			conn.setRequestMethod("POST");
//			conn.setRequestProperty("Accept", "*/*");
//			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//			conn.setDoOutput(true);
//			conn.setDoInput(true);
//			conn.setUseCaches(false);
//			conn.setConnectTimeout(300000);
//			conn.setReadTimeout(500000);
//			
//			if(data != null) {
//				OutputStream out = conn.getOutputStream();
//				out.write(data.getBytes("utf-8"));
//				out.flush();
//				out.close();
//			}
//	
//			//out = new PrintWriter(conn.getOutputStream());
//			//out.print(data);
//			//out.flush();
//			in = new BufferedReader( new InputStreamReader(conn.getInputStream(), "utf-8"));
//			String line = "";
//			while ((line = in.readLine()) != null) {
//				result.append(line);
//			}
//			return result.toString();
//		}catch(Exception e) {
//			//throw new Exception(e.getMessage());
//			AdUtils.getErrorInfoFromException(e);
//		}finally {
//			if (in != null) {
//				try {
//					in.close();
//				} catch (Exception e2) {
//					AdUtils.getErrorInfoFromException(e2);
//				}
//				
//			}
//		}
		
		
		
		HttpGet getMethod = new HttpGet(url); 
		BasicHttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 3*1000);
		HttpConnectionParams.setSoTimeout(httpParams, 3*1000);
		HttpClient httpClient = new DefaultHttpClient(httpParams);  
		String result  = "";
		String encodeType = "utf-8";
		
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
		
		return result.toString();
	}
	
}
