package com.ad.zhimeng;

public class AdStatus {
	private String gid;
	private String action;
	
	public AdStatus(String gid,String action){
		this.action = action;
		this.gid=gid;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	
}
