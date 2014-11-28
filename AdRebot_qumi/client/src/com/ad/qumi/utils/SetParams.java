package com.ad.qumi.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;


public class SetParams {
	private final String TAG = "SetParams";
	private Context context;
	
	//配置文件保存路径
	private static String configFilePath = "/mnt/sdcard/";
	//配置文件名字
	private static final String configFileName = "paramConfig";
	
	
	//所有配置参数
	private String IMEI;					//IMEI                 	   1
	private String IMSI;					//IMSI                     2
	private String Mac;						//Macaddr 				   3
	private String UDID;					//UDID					   4
	private String line1Number;				//电话号码  					   5
	private String screenWidth;				//屏幕宽                                                                           6
	private String screenHeight;			//屏幕高                                                                           7
	private String networkType;				//网络类型 				       8
	private String networkOperation;        //网络运营商                                                                 9
	private String phoneModel = "";			//手机型号                                                                     10
	private String phoneManufacturer;		//手机厂商                                                                     11
	private String androidReleaseVersion;	//Android版本号                                                 12
	private String screenLayoutSize;		//screenLayoutSize         13	
	private String androidSDKLevel;	 //Android SDk Level  	   13

	public SetParams(Context context){
		this.context = context;
	}
		
    //一次性将所有输入写入到配置文件中，如果配置文件存在则清空内容重新写入，如果不存在则创建
    public void set(
			final String IMEI,
			final String IMSI,
			final String Mac,
			final String UDID,
			final String line1Number,
			final String screenWidth,
			final String screenHeight,
			final String networkType,
			final String networkOperation,
			final String phoneModel,
			final String phoneManufacturer,
			final String androidReleaseVersion,
			final String androidSDKLevel,    	
			final String screenLayoutSize    		 		
    		){
 
		this.IMEI = IMEI;
		this.IMSI = IMSI;
		this.Mac = Mac;
		this.UDID = UDID;
		this.line1Number = line1Number;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.networkType = networkType;
		this.networkOperation = networkOperation;
		this.phoneModel = phoneModel;
		this.phoneManufacturer = phoneManufacturer;
		this.androidReleaseVersion = androidReleaseVersion;
		this.screenLayoutSize = screenLayoutSize;
		this.androidSDKLevel = androidSDKLevel;
		//开始把参数写入到sdcard的配置文件中
    	if( checkConfigFile() ){
    		writeParam();   	
    	}
    }
 
    //获取sdcard路径
    private void getSdcardPath(){
    	if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)){
    		//草你妹妹Android，返回路径居然不带反斜杠！！！！！！！
    		configFilePath = Environment.getExternalStorageDirectory().toString() + "/";
    	}
    	Log.d(TAG,"__GetSdcardPath____" + configFilePath);
    }
    
    //检测配置文件状态
    private boolean checkConfigFile(){
    	//先获取到配置文件存放路径//我们使用sdcard
    	getSdcardPath();	
    	       
        File configFile = new File(configFilePath + configFileName);  
        if (!configFile.exists()) {  
            try {  
                  //在指定的路径中创建文件  
            	configFile.createNewFile();  
            } catch (Exception e) {
            	Log.d(TAG,"Create New ConfigFile Failed");
            	return false;
            }  
        }
        return true;
    }
    
    //将参数写入指定的配置文件
    private void writeParam(){
    	FileWriter fw = null;  
        BufferedWriter bw = null;  

        try {  
        	//先清空，再写入！
            fw = new FileWriter(configFilePath + configFileName, false);
            bw = new BufferedWriter(fw); 
                       
            //写入IMEI
            bw.write("IMEI=" + (IMEI == null ? "" : IMEI)); 
            bw.newLine();
            //写入IMEI
            bw.write("IMSI=" +( IMSI== null ? "" : IMSI));   
            bw.newLine();
            //写入MAC
            bw.write("Mac=" + (Mac == null ? "" : Mac)); 
            bw.newLine();
            //写入UDID
            bw.write("UDID=" +( UDID == null ? "" : UDID));
            bw.newLine();
            //写入电话号码
            bw.write("Line1Number=" + (line1Number == null ? "" : line1Number));   
            bw.newLine();
            //写入屏幕宽度
            bw.write("ScreenWidth=" + (screenWidth == null ? "" : screenWidth)); 
            bw.newLine();
            //写入屏幕高度
            bw.write("ScreenHeight=" + (screenHeight == null ? "" : screenHeight));  
            bw.newLine();
                      
            //写入网络类型
            bw.write("NetworkTypeName=" + (networkType == null ? "" : networkType));   
            bw.newLine();
            //写入网络运营商 ：ChinaMobile，ChinaUnicom，ChinaTelecom
            //如果是WIFI网络，请传入null，或者空字符串
            if(!TextUtils.isEmpty(networkOperation)){
            	//中国移动
            	if(networkOperation.equalsIgnoreCase("ChinaMobile")){
            		bw.write("NetworkOperation=" + networkOperation);   
            		bw.newLine();
            	}
            	//中国电信
            	else if(networkOperation.equalsIgnoreCase("ChinaTelecom")){
            		bw.write("NetworkOperation=" + networkOperation);   
            		bw.newLine();
            	}
            	//中国联通
            	else if(networkOperation.equalsIgnoreCase("ChinaUnicom")){
            		bw.write("NetworkOperation=" + networkOperation);   
            		bw.newLine();
            	}            	
            }
          
            
            //写入手机型号
            bw.write("MODEL=" + (phoneModel == null ? "" : phoneModel));   
            bw.newLine();
            //写入制造商
            bw.write("MANUFACTURER=" +( phoneManufacturer == null ? "" : phoneManufacturer));   
            bw.newLine();
            //写入Android版本号
            bw.write("VERSION=" +( androidReleaseVersion == null ? "" : androidReleaseVersion));   
            bw.newLine();

            //写入Android api level
            bw.write("SDK_INT=" +( androidSDKLevel == null ? "" : androidSDKLevel));   
            bw.newLine();
            //修改screenLayoutSize
            //Configuration localConfiguration = context.getResources().getConfiguration();
            //localConfiguration.screenLayout = Integer.parseInt(TextUtils.isEmpty(screenLayoutSize) ? "888" : screenLayoutSize.trim());
           
        } catch (IOException e) {  
        	Log.d(TAG,"Error in write Param");
            e.printStackTrace();  
 
        } finally{
            try {  
            	if(bw != null){
            		bw.flush();
            		bw.close();   
            	}
            } catch (IOException e1) {  
            	Log.d(TAG,"Error in write Param");
            }       	
        }    	
    }
    
    private void checkIfSetSucess(){

    }
}
