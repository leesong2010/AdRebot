package com.ad.zhimeng;

public class Ad {
	private String url;
	private String pname;
	private String gid;
	
	public Ad(String pname,String url,String gid){
		this.url = url;
		this.gid = gid;
		this.pname = pname;
	}
	public Ad(){
		
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}

	
	
	
}
