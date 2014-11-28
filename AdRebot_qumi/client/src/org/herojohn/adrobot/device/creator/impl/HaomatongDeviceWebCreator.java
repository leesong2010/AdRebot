/**
 * 
 */
package org.herojohn.adrobot.device.creator.impl;

import org.herojohn.adrobot.device.DeviceFactory;
import org.herojohn.adrobot.device.creator.DeviceCreator;
import org.herojohn.adrobot.device.creator.DeviceWebCreator;
import org.herojohn.adrobot.device.model.DeviceConfig;
import org.herojohn.adrobot.device.model.HaomatongDevice;
import org.herojohn.adrobot.device.util.JsonUtils;

import android.util.Log;

/**
 * @author hezhou
 *
 */
public class HaomatongDeviceWebCreator extends DeviceWebCreator implements DeviceCreator<HaomatongDevice> {

	private DeviceConfig config;
	
	@Override
	public void init(DeviceConfig config) throws Exception {
		this.config = config;
	}

	@Override
	public HaomatongDevice getDevice() throws Exception {
		String queryString = "action=getnew&ad_sense="+config.getAdSense()+"&app_name="+config.getAppName()+"&data_index="+config.getDevicesIndex();
		String sign = getSign(queryString);
		String result = doGet(config.getEndpoint()+"/devices.jsp?"+queryString+"&sig="+sign);
		return JsonUtils.toBean(result, HaomatongDevice.class);
	}

	@Override
	public HaomatongDevice getInstalledDevice() throws Exception {
		String queryString = "action=getinstalled&ad_sense="+config.getAdSense()+"&app_name="+config.getAppName()+"&data_index="+config.getDevicesIndex();
		String sign = getSign(queryString);
		String result = doGet(config.getEndpoint()+"/devices.jsp?"+queryString+"&sig="+sign);
		return JsonUtils.toBean(result, HaomatongDevice.class);
	}

	@Override
	public HaomatongDevice getInstalledDevice(String imei) throws Exception {
		String queryString = "action=getinstalled&ad_sense="+config.getAdSense()+"&app_name="+config.getAppName()+"&data_index="+config.getDevicesIndex()+"&imei="+imei;
		String sign = getSign(queryString);
		String result = doGet(config.getEndpoint()+"/devices.jsp?"+queryString+"&sig="+sign);
		return JsonUtils.toBean(result, HaomatongDevice.class);
	}

	@Override
	public void saveInstalled(HaomatongDevice device) throws Exception {
		String queryString = "action=saveinstalled&ad_sense="+config.getAdSense()+"&app_name="+config.getAppName()+"&data_index="+config.getDevicesIndex()+"&imei="+device.getImei();
		String sign = getSign(queryString);
		Log.d("adrebot",config.getEndpoint()+"/devices.jsp?"+queryString+"&sig="+sign);
		String result = toPostData(config.getEndpoint()+"/devices.jsp?"+queryString+"&sig="+sign,"");
		Log.d("adrebot","result="+result);
		if(!"{\"code\":0}".equals(result.trim()))
			throw new Exception("操作失败");
	}

	@Override
	public void updateInstalled(HaomatongDevice device) throws Exception {
		String queryString = "action=updateinstalled&ad_sense="+config.getAdSense()+"&app_name="+config.getAppName()+"&data_index="+config.getDevicesIndex()+"&imei="+device.getImei();
		String sign = getSign(queryString);
		String result = toPostData(config.getEndpoint()+"/devices.jsp?"+queryString+"&sig="+sign,"");
		if(!"{\"code\":0}".equals(result.trim()))
			throw new Exception("操作失败");
		
	}

	public static void main(String[] args) {
		HaomatongDeviceWebCreator haomatongDeviceCreator = (HaomatongDeviceWebCreator) DeviceFactory.getInstance("haomatong_web");
		try {
			DeviceConfig config = new DeviceConfig();
			config.setEndpoint("http://127.0.0.1:8080");
			config.setAdSense("haomatong");
			config.setAppName("shua");
			config.setDay(1);
			config.setRate(70);
			config.setDevicesIndex(0);
			haomatongDeviceCreator.init(config);
			HaomatongDevice device = haomatongDeviceCreator.getDevice();
			haomatongDeviceCreator.saveInstalled(device);
			//System.out.println(device);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
