package org.blazer.groupreport.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");

	public static String now() {
		return sdf.format(new Date());
	}

}
