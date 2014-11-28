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
	
	//�����ļ�����·��
	private static String configFilePath = "/mnt/sdcard/";
	//�����ļ�����
	private static final String configFileName = "paramConfig";
	
	
	//�������ò���
	private String IMEI;					//IMEI                 	   1
	private String IMSI;					//IMSI                     2
	private String Mac;						//Macaddr 				   3
	private String UDID;					//UDID					   4
	private String line1Number;				//�绰����  					   5
	private String screenWidth;				//��Ļ��                                                                           6
	private String screenHeight;			//��Ļ��                                                                           7
	private String networkType;				//�������� 				       8
	private String networkOperation;        //������Ӫ��                                                                 9
	private String phoneModel = "";			//�ֻ��ͺ�                                                                     10
	private String phoneManufacturer;		//�ֻ�����                                                                     11
	private String androidReleaseVersion;	//Android�汾��                                                 12
	private String screenLayoutSize;		//screenLayoutSize         13	
	private String androidSDKLevel;	 //Android SDk Level  	   13

	public SetParams(Context context){
		this.context = context;
	}
		
    //һ���Խ���������д�뵽�����ļ��У���������ļ������������������д�룬����������򴴽�
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
		//��ʼ�Ѳ���д�뵽sdcard�������ļ���
    	if( checkConfigFile() ){
    		writeParam();   	
    	}
    }
 
    //��ȡsdcard·��
    private void getSdcardPath(){
    	if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)){
    		//��������Android������·����Ȼ������б�ܣ�������������
    		configFilePath = Environment.getExternalStorageDirectory().toString() + "/";
    	}
    	Log.d(TAG,"__GetSdcardPath____" + configFilePath);
    }
    
    //��������ļ�״̬
    private boolean checkConfigFile(){
    	//�Ȼ�ȡ�������ļ����·��//����ʹ��sdcard
    	getSdcardPath();	
    	       
        File configFile = new File(configFilePath + configFileName);  
        if (!configFile.exists()) {  
            try {  
                  //��ָ����·���д����ļ�  
            	configFile.createNewFile();  
            } catch (Exception e) {
            	Log.d(TAG,"Create New ConfigFile Failed");
            	return false;
            }  
        }
        return true;
    }
    
    //������д��ָ���������ļ�
    private void writeParam(){
    	FileWriter fw = null;  
        BufferedWriter bw = null;  

        try {  
        	//����գ���д�룡
            fw = new FileWriter(configFilePath + configFileName, false);
            bw = new BufferedWriter(fw); 
                       
            //д��IMEI
            bw.write("IMEI=" + (IMEI == null ? "" : IMEI)); 
            bw.newLine();
            //д��IMEI
            bw.write("IMSI=" +( IMSI== null ? "" : IMSI));   
            bw.newLine();
            //д��MAC
            bw.write("Mac=" + (Mac == null ? "" : Mac)); 
            bw.newLine();
            //д��UDID
            bw.write("UDID=" +( UDID == null ? "" : UDID));
            bw.newLine();
            //д��绰����
            bw.write("Line1Number=" + (line1Number == null ? "" : line1Number));   
            bw.newLine();
            //д����Ļ���
            bw.write("ScreenWidth=" + (screenWidth == null ? "" : screenWidth)); 
            bw.newLine();
            //д����Ļ�߶�
            bw.write("ScreenHeight=" + (screenHeight == null ? "" : screenHeight));  
            bw.newLine();
                      
            //д����������
            bw.write("NetworkTypeName=" + (networkType == null ? "" : networkType));   
            bw.newLine();
            //д��������Ӫ�� ��ChinaMobile��ChinaUnicom��ChinaTelecom
            //�����WIFI���磬�봫��null�����߿��ַ���
            if(!TextUtils.isEmpty(networkOperation)){
            	//�й��ƶ�
            	if(networkOperation.equalsIgnoreCase("ChinaMobile")){
            		bw.write("NetworkOperation=" + networkOperation);   
            		bw.newLine();
            	}
            	//�й�����
            	else if(networkOperation.equalsIgnoreCase("ChinaTelecom")){
            		bw.write("NetworkOperation=" + networkOperation);   
            		bw.newLine();
            	}
            	//�й���ͨ
            	else if(networkOperation.equalsIgnoreCase("ChinaUnicom")){
            		bw.write("NetworkOperation=" + networkOperation);   
            		bw.newLine();
            	}            	
            }
          
            
            //д���ֻ��ͺ�
            bw.write("MODEL=" + (phoneModel == null ? "" : phoneModel));   
            bw.newLine();
            //д��������
            bw.write("MANUFACTURER=" +( phoneManufacturer == null ? "" : phoneManufacturer));   
            bw.newLine();
            //д��Android�汾��
            bw.write("VERSION=" +( androidReleaseVersion == null ? "" : androidReleaseVersion));   
            bw.newLine();

            //д��Android api level
            bw.write("SDK_INT=" +( androidSDKLevel == null ? "" : androidSDKLevel));   
            bw.newLine();
            //�޸�screenLayoutSize
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
