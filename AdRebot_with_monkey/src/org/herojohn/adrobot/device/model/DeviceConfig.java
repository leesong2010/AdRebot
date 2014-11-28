/**
 * 
 */
package org.herojohn.adrobot.device.model;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author zhouhe
 *
 */
public class DeviceConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9192045740745973250L;
	
	private String adSense;
	
	/**
	 * devices数据表索引
	 */
	private int devicesIndex;
	/**
	 * 应用名
	 */
	private String appName;
	/**
	 * 取前n天的已安装数据，默认是0，则表示取今天0点以前的数据，
	 * 1表示取昨天0点以前的数据，以此类推
	 */
	private int day;
	/**
	 * 获取今天已安装设备的几率，如果是70，则表示有70%的几率取到今天新增的数据
	 */
	private int rate = 70;
	
	private String endpoint;
	
	public DeviceConfig() {
	}
	
	public DeviceConfig(int devicesIndex,String appName) {
		this.devicesIndex = devicesIndex;
		this.appName = appName;
	}
	
	public DeviceConfig(int devicesIndex,String appName,int day,int rate) {
		this(devicesIndex,appName);
		this.day = day;
		this.rate = rate;
	}

	public String getAdSense() {
		return adSense;
	}

	public void setAdSense(String adSense) {
		this.adSense = adSense;
	}

	public int getDevicesIndex() {
		return devicesIndex;
	}
	public void setDevicesIndex(int devicesIndex) {
		this.devicesIndex = devicesIndex;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getRate() {
		return rate;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}
	public String getEndpoint() {
		return endpoint;
	}
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public static void main(String[] args) {
		Calendar c = Calendar.getInstance();
		int day=c.get(Calendar.DATE); 
		c.set(Calendar.DATE,day-0); 
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		System.out.println(304/100);
	}
}
