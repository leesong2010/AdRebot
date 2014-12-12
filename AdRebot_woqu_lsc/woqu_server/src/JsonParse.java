import java.text.NumberFormat;

import net.sf.json.JSONObject;



public class JsonParse {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String json = "{\"address\":\"CN|\u5e7f\u4e1c|\u6df1\u5733|None|CHINANET|0|0\",\"content\":{\"address\":\"\u5e7f\u4e1c\u7701\u6df1\u5733\u5e02\",\"address_detail\":{\"city\":\"\u6df1\u5733\u5e02\",\"city_code\":340,\"district\":\"\",\"province\":\"\u5e7f\u4e1c\u7701\",\"street\":\"\",\"street_number\":\"\"},\"point\":{\"x\":\"114.02597366\",\"y\":\"22.54605355\"}},\"status\":0}";
		XYInfo i = parseXY(json);
		System.out.println(i);
	}

	public static XYInfo parseXY(String json){
		XYInfo xyinfo = null;
		try {
			JSONObject jo = JSONObject.fromObject(json);
			JSONObject o = jo.getJSONObject("content");
			JSONObject oo = o.getJSONObject("address_detail");
			int cityCode = oo.getInt("city_code");
			
			oo = o.getJSONObject("point");
			
			String x =oo.getString("x");
			String y =oo.getString("y");
			
			
			NumberFormat nf = NumberFormat.getNumberInstance();
	        nf.setMaximumFractionDigits(6);
	        x = String.format("%.6f", Double.valueOf(x).doubleValue());
	        y = String.format("%.6f", Double.valueOf(y).doubleValue());
	        xyinfo = new XYInfo(cityCode, x, y);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xyinfo;
	}
	
}
