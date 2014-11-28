package com.ad.vo;


import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.herojohn.adrobot.device.model.Device;

/**
 * @author hezhou
 *
 */
public class ActiveLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5950647297389941303L;

	@JsonIgnore
	private int id;
	private String imei;
	private String pkg;
	private String ip;
	private int port;
	@JsonProperty("app_name")
	private String appName;
	@JsonProperty("active_time")
	private Date activeTime;
	private Device device;
	private boolean isOld;//是否是活跃用户
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getPkg() {
		return pkg;
	}
	public void setPkg(String pkg) {
		this.pkg = pkg;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public Date getActiveTime() {
		return activeTime;
	}
	public void setActiveTime(Date activeTime) {
		this.activeTime = activeTime;
	}
	public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}
	public boolean isOld() {
		return isOld;
	}
	public void setOld(boolean isOld) {
		this.isOld = isOld;
	}
	
	
}
