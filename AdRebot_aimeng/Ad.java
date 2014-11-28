package com.ad.appmob;

public class Ad {
	private String downurl;
	private String pkg;
	private String adName;
	private String token;
	private String adv;
	private String versioname;
	public Ad(String adName,String pkg,String downurl,String token,String versioname){
		this.adName = adName;
		this.pkg = pkg;
		this.downurl = downurl;
		this.token = token;
		this.versioname = versioname;
	}
	public Ad(){
		
	}

	public String getDownurl() {
		return downurl;
	}

	public void setDownurl(String downurl) {
		this.downurl = downurl;
	}

	public String getPkg() {
		return pkg;
	}

	public void setPkg(String pkg) {
		this.pkg = pkg;
	}

	public String getAdName() {
		return adName;
	}

	public void setAdName(String adName) {
		this.adName = adName;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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
	
	
	
}
