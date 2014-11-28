/**
 * 
 */
package org.herojohn.adrobot.device.creator;

import org.herojohn.adrobot.device.model.Device;
import org.herojohn.adrobot.device.model.DeviceConfig;

/**
 * @author hezhou
 *
 */
public interface DeviceCreator<T extends Device> {

	/**
	 * 
	 * 
	 */
	public void init(DeviceConfig config) throws Exception;
	/**
	 * 随机获取新设备
	 * @return
	 * @throws Exception 
	 */
	public T getDevice() throws Exception;
	/**
	 * 获取已安装的设备
	 * @return
	 * @throws Exception
	 */
	public T getInstalledDevice() throws Exception;
	/**
	 * 获取指定设备
	 * @param imei
	 * @return
	 * @throws Exception
	 */
	public T getInstalledDevice(String imei) throws Exception;
	
	public void saveInstalled(T device) throws Exception;
	
	public void updateInstalled(T device) throws Exception;
	
}
