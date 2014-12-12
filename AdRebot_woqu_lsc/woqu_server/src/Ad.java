
public class Ad {
	private String adid;
	private String adName;
	private String downurl;
	private int type;
	
	public Ad(String adid,String adName,String downurl,int type){
		this.adid = adid;
		this.adName = adName;
		this.downurl = downurl;
		this.type = type;
	}

	public String getAdid() {
		return adid;
	}

	public void setAdid(String adid) {
		this.adid = adid;
	}

	public String getAdName() {
		return adName;
	}

	public void setAdName(String adName) {
		this.adName = adName;
	}

	public String getDownurl() {
		return downurl;
	}

	public void setDownurl(String downurl) {
		this.downurl = downurl;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	
	
}
