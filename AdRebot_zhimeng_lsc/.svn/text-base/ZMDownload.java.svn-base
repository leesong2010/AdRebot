package com.ad.zhimeng;


import java.io.*;
import java.net.*;
import java.util.*;

import org.nice.NetUtils;

import com.ad.appmob.MyIP;
import com.ad.appmob.Utils;

// This class downloads a file from a URL.
class ZMDownload extends Observable{
    
    // Max size of download buffer.
    private static final int MAX_BUFFER_SIZE = 1024;
    
    // These are the status names.
    public static final String STATUSES[] = {"Downloading",
    "Paused", "Complete", "Cancelled", "Error"};
    
    // These are the status codes.
    public static final int DOWNLOADING = 0;
    public static final int PAUSED = 1;
    public static final int COMPLETE = 2;
    public static final int CANCELLED = 3;
    public static final int ERROR = 4;
    
    private URL url; // download URL
    private int size; // size of download in bytes
    private int downloaded; // number of bytes downloaded
    private int status; // current status of download
    private String UA;
    private MyIP myip;
    // Constructor for Download.
    public ZMDownload(URL url,String UA,MyIP myip) {
        this.url = url;
        size = -1;
        downloaded = 0;
        status = DOWNLOADING;
        this.UA = UA;
        this.myip = myip;
        // Begin the download.
     //   download();
    }
    
    // Get this download's URL.
    public String getUrl() {
        return url.toString();
    }
    
    // Get this download's size.
    public int getSize() {
        return size;
    }
    
    // Get this download's progress.
    public float getProgress() {
        return ((float) downloaded / size) * 100;
    }
    
    // Get this download's status.
    public int getStatus() {
        return status;
    }
    
    // Pause this download.
    public void pause() {
        status = PAUSED;
        stateChanged();
    }
    
    // Resume this download.
    public void resume() {
        status = DOWNLOADING;
        stateChanged();
        download();
    }
    
    // Cancel this download.
    public void cancel() {
        status = CANCELLED;
        stateChanged();
    }
    
    // Mark this download as having an error.
    private void error() throws Exception{
        status = ERROR;
        stateChanged();
        if(myip!= null)
        	throw new Exception("######Download Error!!!#######" +myip.getIp()+":"+myip.getPort()+"#"+myip.getIpUrl());
        //throw new Exception("######Download Error!!!######");
    }
    
    // Start or resume downloading.
    private void download() {
     /*   Thread thread = new Thread(this);
        thread.start();*/
    }
    
    public String toDownload() throws Exception
    {
        RandomAccessFile file = null;
        InputStream stream = null;
        String tmpFileName = System.currentTimeMillis() + "";
        String adv = "";
        Date stime ;
        Date  etime;
        
        int contentLength = 0;
        
         if(myip != null)
		 NetUtils.isAvailProxy(ZM.localIP,myip);
		//if(!isAvailProxy)throw new Exception(myip.getIpUrl() + "#透明代理："+ip+":"+port);
        
        try {
            // Open connection to URL.
            HttpURLConnection connection = null;
            if(myip == null)
            	connection = (HttpURLConnection) url.openConnection();
			else
				connection = (HttpURLConnection) url.openConnection(myip.getProxy());
            
            connection.setRequestProperty("Host", "zmup.bb800.com");
            connection.setRequestProperty("User-Agent", UA);
            // Connect to server.
            connection.connect();
            
           // System.out.println("resCode:"+connection.getResponseCode());//
            // Make sure response code is in the 200 range.
            if (connection.getResponseCode() / 100 != 2) {
                error();
            }
           
            // Check for valid content length.
            contentLength = connection.getContentLength();
            if (contentLength < 1) {
                error();
            }
            // Set the size for this download if it hasn't been already set.
            if (size == -1) {
                size = contentLength;
                stateChanged();
            }
          /*  stream = connection.getInputStream();
            byte b[] = new byte[10];
            stream.read(b);
            stream.close();*/
           
         //   cancel();
            // Open file and seek to the end of it.
            //file = new RandomAccessFile(DJContext.FILESAVEPATH + getFileName(url), "rw");
            

        	stream = connection.getInputStream();
        	byte buffer[];
            buffer = new byte[2];
            stream.read(buffer);
            
            int fileKB = contentLength / 1024;//转换成kb
            int minSpeed = Integer.parseInt(ZMContact.DOWN_SPEED.split(",")[0]);
            int maxSpeed = Integer.parseInt(ZMContact.DOWN_SPEED.split(",")[1]);
            int downSpeed = Utils.getRangeRandomNum(minSpeed, maxSpeed);
            int time = fileKB / downSpeed;
            System.out.println("文件大小：【"+fileKB+"】KB，下载速率【"+downSpeed+"】 kb/s,延时【"+time+"】秒");
            Thread.sleep(time * 1000);
    } catch (Exception e) {
        	
            error();
        } finally {
            // Close file.
            if (file != null) {
                try {
                    file.close();
                } catch (Exception e) {}
            }
            
            // Close connection to server.
            if (stream != null) {
                try {
                    stream.close();
                } catch (Exception e) {}
            }
        }
            
            return adv;
    }
    
    // Get file name portion of URL.
    private String getFileName(URL url) {
        String fileName = url.getFile();
        return fileName.substring(fileName.lastIndexOf('/') + 1);
    }
    
    // Download file.
/*    public void run() {
        RandomAccessFile file = null;
        InputStream stream = null;
        
        try {
            // Open connection to URL.
            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();
            
            // Specify what portion of file to download.
            connection.setRequestProperty("Range",
                    "bytes=" + downloaded + "-");
            connection.setRequestProperty("Host", "a.dianjoy.com");
            connection.setRequestProperty("User-Agent", UA);
            // Connect to server.
            connection.connect();
            
            Thread.currentThread().interrupt();
            
            // Make sure response code is in the 200 range.
            if (connection.getResponseCode() / 100 != 2) {
                error();
            }
            
            // Check for valid content length.
            int contentLength = connection.getContentLength();
            if (contentLength < 1) {
                error();
            }
            
            // Set the size for this download if it hasn't been already set.
            if (size == -1) {
                size = contentLength;
                stateChanged();
            }
            
            cancel();
            // Open file and seek to the end of it.
            //file = new RandomAccessFile(DJContext.FILESAVEPATH + getFileName(url), "rw");
//            file = new RandomAccessFile(DJContext.FILESAVEPATH + new Random().nextInt(10000)+".pp", "rw");//TODO 这里改名字
//            file.seek(downloaded);
//            
//            stream = connection.getInputStream();
//            while (status == DOWNLOADING) {
//            // Size buffer according to how much of the file is left to download.
//                byte buffer[];
//                if (size - downloaded > MAX_BUFFER_SIZE) {
//                    buffer = new byte[MAX_BUFFER_SIZE];
//                } else {
//                    buffer = new byte[size - downloaded];
//                }
//                
//                // Read from server into buffer.
//                int read = stream.read(buffer);
//                if (read == -1)
//                    break;
//                
//                // Write buffer to file.
//                file.write(buffer, 0, read);
//                downloaded += read;
//                stateChanged();
//            }
            
      // Change status to complete if this point was reached because downloading has finished.
            if (status == DOWNLOADING) {
                status = COMPLETE;
                stateChanged();
            }
        } catch (Exception e) {
           // error();
        	
        } finally {
            // Close file.
            if (file != null) {
                try {
                    file.close();
                } catch (Exception e) {}
            }
            
            // Close connection to server.
            if (stream != null) {
                try {
                    stream.close();
                } catch (Exception e) {}
            }
        }
    }*/
    
    // Notify observers that this download's status has changed.
    private void stateChanged() {
        setChanged();
        notifyObservers();
    }

    public static void main(String args[]){
    }
}