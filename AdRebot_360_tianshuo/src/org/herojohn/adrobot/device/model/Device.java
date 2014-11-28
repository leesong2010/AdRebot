/**
 * 
 */
package org.herojohn.adrobot.device.model;

import java.util.Random;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.herojohn.adrobot.device.util.DeviceUtil;

/**
 * @author zhouhe
 *
 */
public class Device implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4159229163750461292L;
	
	private int id;
	private String imei;
	private String imsi;
	private String name;
	private String model;
	private String cpu;
	private String dpi;
	private int width;
	private int height;
	private String osVersion;
	protected String mac;
	protected int custom;
	@JsonIgnore
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getImsi() {
		return imsi;
	}
	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getCpu() {
		return cpu;
	}
	public void setCpu(String cpu) {
		this.cpu = cpu;
	}
	public String getDpi() {
		return dpi;
	}
	public void setDpi(String dpi) {
		this.dpi = dpi;
	}
	public String getOs() {
		return "Android"+osVersion;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getCustom() {
		return custom;
	}
	public void setCustom(int custom) {
		this.custom = custom;
	}
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}
	public String getOsVersion() {
		return this.osVersion;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	@JsonIgnore
	public String getDalvikVersion() {
		return osVersion.compareTo("4.0") >= 0 ? "1.6.0" : "1.4.0";
	}
	@JsonIgnore
	public String getSafariVersion() {
		String safariVersion = "533.1";
		if(getDalvikVersion().equals("1.6.0")) {
			String[] ss = new String[]{"534.30","534.31","536.26"};
			safariVersion = ss[new Random().nextInt(ss.length)];
		}
		return safariVersion;
	}
	@JsonIgnore
	public String getRom() {
		if("xiaomi".equalsIgnoreCase(this.getName())) {
			if("mi-one".equalsIgnoreCase(this.getModel())) {
				return "MI-ONE MIUI/3.10.18";
			}else if("mi-oneplus".equalsIgnoreCase(this.getModel())) {
				return "MI-ONE Plus MIUI/3.10.18";
			}else if("mi2".equalsIgnoreCase(this.getModel())) {
				return "MI-TWO MIUI/3.10.18";
			}else if("mi2a".equalsIgnoreCase(this.getModel())) {
				return "MI-TWO A MIUI/3.10.12";
			}else if("mi2s".equalsIgnoreCase(this.getModel())) {
				return "MI-TWO S MIUI/3.10.12";
			}
			return this.getModel().toUpperCase();
		}else {
			String tel_model = this.getModel().toUpperCase();
			if("lenovo".equalsIgnoreCase(this.getName())) {
				tel_model = "Lenovo "+this.getModel().replace("lenovo", "");
			}else if("huawei".equalsIgnoreCase(this.getName())) {
				tel_model = "HUAWEI "+this.getModel().replace("huawei", "").toUpperCase();
			}else if("htc".equalsIgnoreCase(this.getName())) {
				tel_model = "HTC "+this.getModel().replace("htc", "").toUpperCase();
			}else if("zte".equalsIgnoreCase(this.getName())) {
				tel_model = tel_model.replace("-", " ");
			}else if("meizu".equalsIgnoreCase(this.getName())) {
				tel_model = "meizu_"+this.getModel();
			}else if("coolpad".equalsIgnoreCase(this.getName())) {
				tel_model = "COOLPAD "+this.getModel().replace("coolpad", "").toUpperCase();
			}
			return tel_model+" Build/"+DeviceUtil.getChars(3)+new String(DeviceUtil.generateCheckCode(2));
		}
	}

	@JsonIgnore
	public String getMnc() {
		return imsi.charAt(3)+""+imsi.charAt(4);
	}
	@JsonIgnore
	public String getMcc() {
		return imsi.substring(0,3);
	}
	@JsonIgnore
	public String getUa() {
		return "Dalvik/"+getDalvikVersion()+" (Linux; U; Android "+getOsVersion()+"; "+getRom()+")";
	}
	@JsonIgnore
	public String getBrowserUa() {
		//"Mozilla/5.0 (Linux; U; Android 4.1.2; zh-cn; SHV-E210L Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30";
		String safariVersion = getSafariVersion();
		return "Mozilla/5.0 (Linux; U; Android "+getOsVersion()+"; zh-cn; "+getRom()+") AppleWebKit/"+safariVersion+" (KHTML, like Gecko) Version/4.0 Mobile Safari/"+safariVersion;
	}
	@JsonIgnore
	public int getApilevel() {
		String osversion = getOsVersion();
		if("1.0".equals(osversion)) 
			return 1;
		else if(osversion.compareTo("1.1") >= 0 && osversion.compareTo("1.5") < 0)
			return 2;
		else if(osversion.compareTo("1.5") >= 0 && osversion.compareTo("1.6") < 0)
			return 3;
		else if(osversion.compareTo("1.6") >= 0 && osversion.compareTo("2.0") < 0)
			return 4;
		else if(osversion.equals("2.0"))
			return 5;
		else if(osversion.compareTo("2.0.1") >= 0 && osversion.compareTo("2.1.0") < 0)
			return 6;
		else if(osversion.compareTo("2.1.0") >= 0 && osversion.compareTo("2.2.0") < 0)
			return 7;
		else if(osversion.compareTo("2.2.0") >= 0 && osversion.compareTo("2.3") < 0)
			return 8;
		else if(osversion.compareTo("2.3") >= 0 && osversion.compareTo("2.3.3") < 0)
			return 9;
		else if(osversion.compareTo("2.3.3") >= 0 && osversion.compareTo("3.0.0") < 0)
			return 10;
		else if(osversion.compareTo("3.0.0") >= 0 && osversion.compareTo("3.1.0") < 0)
			return 11;
		else if(osversion.compareTo("3.1.0") >= 0 && osversion.compareTo("3.2") < 0)
			return 12;
		else if(osversion.compareTo("3.2") >= 0 && osversion.compareTo("4.0") < 0)
			return 13;
		else if(osversion.compareTo("4.0") >= 0 && osversion.compareTo("4.0.3") < 0)
			return 14;
		else if(osversion.compareTo("4.0.3") >= 0 && osversion.compareTo("4.1") < 0)
			return 15;
		else if(osversion.compareTo("4.1") >= 0 && osversion.compareTo("4.2") < 0)
			return 16;
		else if(osversion.compareTo("4.2") >= 0 && osversion.compareTo("4.3") < 0)
			return 17;
		else if(osversion.compareTo("4.3") >= 0)
			return 18;
		return new Random().nextInt(18)+1;
	}
	
	/**
	 * 
	 * @return 2为中国移动，3为中国联通，1为中国电信CDMA,4为wifi
	 */
	@JsonIgnore
	public int getNetworkType() {
		String mnc = getMnc();
		if("00".equals(mnc) || "02".equals(mnc) || "07".equals(mnc)) 
			return 2;
		else if("01".equals(mnc) || "06".equals(mnc))
			return 3;
		else if("03".equals(mnc) || "05".equals(mnc))
			return 1;
		else
			return 4;
	}
	@JsonIgnore
	public int getSimType() {
		String mnc = getMnc();
		if("00".equals(mnc) || "02".equals(mnc) || "07".equals(mnc)) 
			return 2;
		else if("01".equals(mnc) || "06".equals(mnc))
			return 3;
		else if("03".equals(mnc) || "05".equals(mnc))
			return 1;
		else
			return 4;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append('{');
		sb.append("\"imei\":\"").append(imei).append("\",");
		sb.append("\"imsi\":\"").append(imsi).append("\",");
		sb.append("\"name\":\"").append(name).append("\",");
		sb.append("\"model\":\"").append(model).append("\",");
		sb.append("\"width\":").append(width).append(",");
		sb.append("\"height\":").append(height).append(",");
		sb.append("\"mac\":\"").append(mac).append("\",");
		sb.append("\"osVersion\":\"").append(osVersion).append('\"');
		sb.append('}');
		return sb.toString();
	}
}
