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
public class QumiDevice extends Device {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8179251545393336813L;

	@JsonIgnore
	private String ip;
	private String adids = "";
	private Date updateTime;
	private Date activeTime;
	private int networkType = 4;
	
	public QumiDevice() {
		
	}
	
	public QumiDevice(Device device) {
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
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getAdids() {
		return adids;
	}
	public void setAdids(String adids) {
		this.adids += adids+",";
	}

	public void setMac(String mac) {
		this.mac = mac.toUpperCase();
	}
	
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
