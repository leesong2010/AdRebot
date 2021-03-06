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
public class WapsDevice extends Device {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8179251545393336813L;

	private boolean root;
	private String ip;
	private String mac;
	private int status;
	private Date createTime;
	private Date updateTime;
	private int networkType = 4;
	
	public WapsDevice() {
		
	}
	
	public WapsDevice(Device device) {
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
	}
	
	@JsonIgnore
	public boolean isRoot() {
		return root;
	}

	public void setRoot(boolean root) {
		this.root = root;
	}
	
	@JsonIgnore
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	@JsonIgnore
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	@JsonIgnore
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@JsonIgnore
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@JsonIgnore
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public void setNetworkType(int networkType) {
		this.networkType = networkType;
	}
	@JsonIgnore
	public int getNetworkType() {
		return networkType;
	}
}
