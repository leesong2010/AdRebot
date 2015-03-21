/**
 * 
 */
package org.herojohn.adrobot.device.model;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * @author zhouhe
 *
 */
public class DaoyoudaoDevice extends Device {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8179251545393336813L;

	private String ip;
	private String bssid;
	private String bssids;
	private String packagenames;
	private int status;
	private Date updateTime;
	private Date activeTime;
	private int networkType = 4;
	
	public DaoyoudaoDevice() {
		
	}
	
	public DaoyoudaoDevice(Device device) {
		this.setId(device.getId());
		this.setImei(device.getImei());
		this.setImsi(device.getImsi());
		this.setName(device.getName());
		this.setModel(device.getModel());
		this.setCpu(device.getCpu());
		this.setDpi(device.getDpi());
		this.setWidth(device.getWidth());
		this.setHeight(device.getHeight());
		this.setOsVersion(device.getOsVersion());
		this.setCustom(device.getCustom());
		this.setMac(device.getMac());
	}
	@JsonIgnore
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	@JsonIgnore
	public String getBssid() {
		return bssid;
	}
	public void setBssid(String bssid) {
		this.bssid = bssid.toUpperCase();
	}
	@JsonIgnore
	public String getBssids() {
		return bssids;
	}
	public void setBssids(String bssids) {
		this.bssids = bssids.toUpperCase();
	}
	@JsonIgnore
	public String getPackagenames() {
		return packagenames;
	}
	public void setPackagenames(String packagenames) {
		this.packagenames = packagenames;
	}
	public void setMac(String mac) {
		this.mac = mac.toUpperCase();
	}
	@JsonIgnore
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@JsonIgnore
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getActiveTime() {
		return activeTime;
	}
	public void setActiveTime(Date activeTime) {
		this.activeTime = activeTime;
	}

	public void setNetworkType(int networkType) {
		this.networkType = networkType;
	}
	@JsonIgnore
	public int getNetworkType() {
		return networkType;
	}
}
