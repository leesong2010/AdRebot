package com.ad.appmob;


import java.io.*;
import java.net.*;
import java.util.*;

import org.nice.NetUtils;

// This class downloads a file from a URL.
class ApkDownload extends Observable{
    
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
    private String fileName;
    private String aaptPath;
    private String savePath;
    // Constructor for Download.
    public ApkDownload(URL url,String fn,String savePath,String aaptPath) {
        this.url = url;
        size = -1;
        downloaded = 0;
        status = DOWNLOADING;
        this.fileName = fn;
        this.aaptPath = aaptPath;
        this.savePath = savePath;		
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
        throw new Exception("######Download Error!!!#######" + url.getPath());
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
        
        
        try {
            // Open connection to URL.
            HttpURLConnection connection = null;
            connection = (HttpURLConnection) url.openConnection();
            
            connection.setRequestProperty("Host", "apk.d.ad.appmob.cn");
            connection.setRequestProperty("User-Agent", " ");
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
            
            File dir = new File(savePath);
            if(!dir.exists())dir.mkdirs();
            file = new RandomAccessFile(savePath + fileName, "rw");//TODO 这里改名字
            //file.seek(downloaded);
            
            stime = new Date();
            stream = connection.getInputStream();
            while (status == DOWNLOADING) {
            // Size buffer according to how much of the file is left to download.
                byte buffer[];
                if (size - downloaded > MAX_BUFFER_SIZE) {
                    buffer = new byte[MAX_BUFFER_SIZE];
                } else {
                    buffer = new byte[size - downloaded];
                }
                
                // Read from server into buffer.
                int read = stream.read(buffer);
                if (read == -1)
                    break;
                
                // Write buffer to file.
                file.write(buffer, 0, read);
                downloaded += read;
                stateChanged();
            }
            
      // Change status to complete if this point was reached because downloading has finished.
            if (status == DOWNLOADING) {
                status = COMPLETE;
                stateChanged();
            }
            etime =new Date();
            String sizeMB = String.valueOf( Math.ceil((double)contentLength/1024/1024));
            System.out.println("文件大小："+sizeMB + "MB,下载耗时：" + Utils.getTimeDiff(stime, etime) +" 秒");
            adv =  Utils.getApkInfo(savePath+fileName, aaptPath);
           System.out.println("down adv="+adv);
        } catch (Exception e) {
        	File f = new File(savePath+fileName);
        	if(f.exists()){
        		System.out.println("下载错误删除未下载完成的文件##");
        		f.delete();
        	}
        	
            error();
        } finally {
            // Close file.
            if (file != null) {
                try {
                    file.close();
                    File f = new File(savePath+fileName);
                	if(f.exists()){
                		f.delete();
                	}
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