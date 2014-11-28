package com.ad.qumi.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.type.TypeReference;
import org.herojohn.adrobot.device.model.QumiDevice;
import org.herojohn.adrobot.device.util.JsonUtils;
import org.herojohn.adrobot.device.util.MD5;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.Environment;

import com.ad.vo.ActiveLog;
import com.ad.vo.AdItem;
import com.wxx.qumi.QuMiApp;

public class AdUtils {
	
	private static final String secert = "b5j9G7F4e2t0M1==";
	
	//��sdcard��ȡad.xml��ȡ�����Ϣ
	public static List<AdItem> getAds() throws Exception
	{
		List<AdItem> adList = new ArrayList<AdItem>();
		File f = new File(Environment.getExternalStorageDirectory() + "/ad.xml");
		if(!f.exists()) throw new Exception("Sdcard Canot Found ADFILE!!!");
        FileInputStream in = new FileInputStream(f);
		adList = XMLParse.getAds(in);
		return adList;
	}
	
	//��printStackTrace �����String
	public static String getErrorInfoFromException(Exception e) {  
        try {  
            StringWriter sw = new StringWriter();  
            PrintWriter pw = new PrintWriter(sw);  
            e.printStackTrace(pw);  
            return "\r\n" + sw.toString() + "\r\n";  
        } catch (Exception e2) {  
            return "bad getErrorInfoFromException";  
        }  
    }  
	
	//��ȡ��ǰ��Сʱ
	public static int getHour()
	{

		Calendar c = Calendar.getInstance();//���Զ�ÿ��ʱ���򵥶��޸�

		int year = c.get(Calendar.YEAR); 
		int month = c.get(Calendar.MONTH); 
		int date = c.get(Calendar.DATE); 
		int hour = c.get(Calendar.HOUR_OF_DAY); 
		int minute = c.get(Calendar.MINUTE); 
		int second = c.get(Calendar.SECOND); 
		return hour;
	}
	
	
	public static String doGET(String url)
	{
		HttpGet getMethod = new HttpGet(url); 
		BasicHttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 2*1000);
		HttpConnectionParams.setSoTimeout(httpParams, 2*1000);
		HttpClient httpClient = new DefaultHttpClient(httpParams);  
		String result  = "";
		String encodeType = "utf-8";
		
		try {  
		    HttpResponse response = httpClient.execute(getMethod); //����GET����  
		  
		    int code = response.getStatusLine().getStatusCode(); //��ȡ��Ӧ��  
		    if(code == 200)
		    {
		    		result =  EntityUtils.toString(response.getEntity(), encodeType);//��ȡ��������Ӧ���� 
		    }
		} catch (Exception e) {  
		    e.printStackTrace();  
		} 
		
		return result;
	}
	
	protected static String getSign(String queryString) {
		return MD5.hash(queryString+'@'+secert);
	}
	
	//�ӷ��������ؼ����豸��Ϣ
	public static List<ActiveLog> getActiveLogs(String endpoint,String adSense,int length) throws Exception {
		String queryString = "action=getactivelogs&ad_sense="+adSense+"&length="+length;
		String sign = getSign(queryString);
		System.out.println(endpoint+"/devices.jsp?"+queryString+"&sig="+sign);
		String result = doGET(endpoint+"/devices.jsp?"+queryString+"&sig="+sign);
		
		return JsonUtils.toBean(result, new TypeReference<List<ActiveLog>>(){});
	}
	
	
	//�ӷ������������û��豸��Ϣ
	public static List<ActiveLog> getOldActiveLogs(String endpoint,String adSense,int length) throws Exception {
		String queryString = "action=getactivelogs&ad_sense="+adSense+"&length="+length +"&status=1";
		String sign = getSign(queryString);
		System.out.println(endpoint+"/devices.jsp?"+queryString+"&sig="+sign);
		String result = doGET(endpoint+"/devices.jsp?"+queryString+"&sig="+sign);
		
		return JsonUtils.toBean(result, new TypeReference<List<ActiveLog>>(){});
	}
	
	//�жϳ����Ƿ��Ѱ�װ
	public static boolean isAppInstalled(Context context,String uri) {
		PackageManager pm = context.getPackageManager();
		boolean installed =false;
		try {
			pm.getPackageInfo(uri,PackageManager.GET_ACTIVITIES);
			installed =true;
		} catch(PackageManager.NameNotFoundException e) {
			installed =false;
		}
		return installed;
	}
	
	//��ʽ������
	public static String formatDate(Date d){
		return (new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss")).format(d);
	}
	
	//��sdcard��ȡip�б�����
	public static List<String> readProperties(String filePath) {
		List<String> list = new ArrayList<String>();
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(
					filePath));
			props.load(in);
			Enumeration en = props.propertyNames();
			while (en.hasMoreElements()) {
				String key = (String) en.nextElement();
				String property = props.getProperty(key);
				list.add(key+","+property);
				//System.out.println(key + "=" + property);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		QuMiApp.Log("��ȡ��ip�б���:"+list.size());
		return list;
	}
	
	//�򿪻��߹ر�wifi
	public static void switchWIFI(boolean result){
		ShellCommand cmd = new ShellCommand();
		try {
			//��wifi
			if(result){
				cmd.su.runWaitFor("svc wifi enable|adb shell chmod 777 data/local/tmp");
			}
			else{
				cmd.su.runWaitFor("svc wifi disable|adb shell chmod 777 data/local/tmp");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	//�����豸
	public static void rebootDevice(){
		ShellCommand cmd = new ShellCommand();
		cmd.su.runWaitFor("reboot");
	}
	
	//�ж�wifi�Ƿ��
	public static boolean isWifiOpened(Context ctx){
		boolean result = false;
		ConnectivityManager manager = (ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
		//��ȡ״̬
		State wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
		//�ж�wifi�����ӵ�����
		if(wifi == State.CONNECTED||wifi==State.CONNECTING)result = true;
		return result;
	}
	
	//�ж��Ƿ���wifi
	public static boolean isWifi(Context mContext) {  
	    ConnectivityManager connectivityManager = (ConnectivityManager) mContext  
	            .getSystemService(Context.CONNECTIVITY_SERVICE);  
	    NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();  
	    if (activeNetInfo != null  
	            && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {  
	        return true;  
	    }  
	    return false;  
	}
	
	
	//����������Ϣ
	private void sendHeartBeatInfo(Context ctx){
		ctx.sendBroadcast(new Intent("HeartBeat"));
	}
}
