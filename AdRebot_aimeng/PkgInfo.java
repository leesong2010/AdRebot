package com.ad.appmob;

public class PkgInfo {
	private String pkg;
	private String adv;
	private String versioname;
	private String downurl;
	
	public PkgInfo(){}
	public PkgInfo(String pkg,String adv,String versioname){
		this.pkg = pkg;
		this.adv = adv;
		this.versioname = versioname;
	}
	public String getPkg() {
		return pkg;
	}
	public void setPkg(String pkg) {
		this.pkg = pkg;
	}
	public String getAdv() {
		return adv;
	}
	public void setAdv(String adv) {
		this.adv = adv;
	}
	public String getVersioname() {
		return versioname;
	}
	public void setVersioname(String versioname) {
		this.versioname = versioname;
	}
	public String getDownurl() {
		return downurl;
	}
	public void setDownurl(String downurl) {
		this.downurl = downurl;
	}
	
	
}
