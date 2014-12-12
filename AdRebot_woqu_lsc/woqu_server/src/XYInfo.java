
public class XYInfo {
	private String locationlat;//百度api里返回的y
	private String locationlon;//百度api里返回的x
	private int cityCode; 
	public XYInfo(int cityCode,String locationlon,String locationlat){
		this.cityCode = cityCode;
		this.locationlat = locationlat;
		this.locationlon = locationlon;
	}
	public String getLocationlat() {
		return locationlat;
	}
	public void setLocationlat(String locationlat) {
		this.locationlat = locationlat;
	}
	public String getLocationlon() {
		return locationlon;
	}
	public void setLocationlon(String locationlon) {
		this.locationlon = locationlon;
	}
	public int getCityCode() {
		return cityCode;
	}
	public void setCityCode(int cityCode) {
		this.cityCode = cityCode;
	}
	
	
	
}
