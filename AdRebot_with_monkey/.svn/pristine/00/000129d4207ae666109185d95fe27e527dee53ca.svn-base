package com.ad.dyd.utils;

import java.io.File;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import android.os.Environment;
import de.mindpipe.android.logging.log4j.LogConfigurator;

public class MyLoger {

	private String logName;
	private Logger log;
	public MyLoger(String logName){
		this.logName = logName;
		
		configLoger();
		log = Logger.getRootLogger();
		
	}
	
	private void configLoger()
	{
		LogConfigurator logConfigurator = new LogConfigurator();
		logConfigurator.setFileName(Environment.getExternalStorageDirectory()
		+ File.separator + "adrebot" + File.separator  + logName + ".txt");
		logConfigurator.setRootLevel(Level.DEBUG);
		logConfigurator.setLevel("org.apache", Level.ERROR);
		logConfigurator.setFilePattern("%-d{yyyy-MM-dd HH:mm:ss} [%c]-[%p] %m%n");
		logConfigurator.setMaxFileSize(1024 * 1024 * 5);
		logConfigurator.setImmediateFlush(true);
		logConfigurator.configure();
	}
	
	public Logger getLoger(){
		return log;
	}
	
	public void w(String str){
		log.debug(str);
	}
}
