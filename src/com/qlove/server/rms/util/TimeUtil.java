package com.qlove.server.rms.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

	/**
	 * @return	获取当前时间
	 */
	public static String getCurrentTime(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return df.format(new Date());
	}
}
