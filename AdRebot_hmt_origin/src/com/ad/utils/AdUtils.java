package com.ad.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.os.Environment;

import com.ad.vo.AdItem;

public class AdUtils {
	
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
}
