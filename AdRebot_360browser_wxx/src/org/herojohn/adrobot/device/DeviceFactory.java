/**
 * 
 */
package org.herojohn.adrobot.device;


import org.herojohn.adrobot.device.creator.DeviceCreator;
import org.herojohn.adrobot.device.creator.impl.HaomatongDeviceWebCreator;

/**
 * @author zhouhe
 *
 */
public class DeviceFactory<T extends DeviceCreator<?>> {
	
	public static DeviceCreator<?> getInstance(String adrobot) {
		if("haomatong_web".equalsIgnoreCase(adrobot)) {
			return new HaomatongDeviceWebCreator();
		}
		return null;
	}
}
