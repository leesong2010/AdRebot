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
public class DoumobDevice extends Device {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8179251545393336813L;

	private double lon;
	private double lat;
	private String lac;
	private String wifi;
	private String wifis;
	private String packagenames;
	private boolean weixinflag;
	private int status;
	private Date updateTime;
	private int networkType = 4;
	
	public DoumobDevice(Device device) {
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
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	@JsonIgnore
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	@JsonIgnore
	public String getLac() {
		return lac;
	}
	public void setLac(String lac) {
		this.lac = lac;
	}
	public void setWifi(String wifi) {
		this.wifi = wifi.toLowerCase();
	}
	@JsonIgnore
	public String getWifi() {
		return wifi;
	}
	
	public String getWifis() {
		return wifis;
	}

	public void setWifis(String wifis) {
		this.wifis = wifis.toLowerCase();
	}

	@JsonIgnore
	public String getPackagenames() {
		return packagenames;
	}
	public void setPackagenames(String packagenames) {
		this.packagenames = packagenames;
	}
	
	public boolean isWeixinflag() {
		return weixinflag;
	}
	public void setWeixinflag(boolean weixinflag) {
		this.weixinflag = weixinflag;
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
	public void setNetworkType(int networkType) {
		this.networkType = networkType;
	}
	@JsonIgnore
	public int getNetworkType() {
		return networkType;
	}
}
