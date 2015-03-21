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
public class JingzhongDevice extends Device {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8179251545393336813L;

	private String adid;
	private String packagenames;
	private Date updateTime;
	private int networkType = 4;
	
	public JingzhongDevice() {
		
	}
	
	public JingzhongDevice(Device device) {
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
	public String getAdid() {
		return adid;
	}
	public void setAdid(String adid) {
		this.adid = adid;
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
