package org.blazer.groupreport.util;

import java.math.BigDecimal;

public class BigDecimalUtil {

	public static BigDecimal getBigDecimal0(Object obj) {
		if (obj == null) {
			return new BigDecimal(0);
		}
		try {
			return new BigDecimal(obj.toString());
		} catch (Exception e) {
		}
		return new BigDecimal(0);
	}

}
