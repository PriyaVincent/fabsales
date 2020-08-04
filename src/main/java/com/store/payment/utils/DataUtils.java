package com.store.payment.utils;

import java.util.Calendar;
import java.util.Date;

public class DataUtils {

	public static boolean calculate2Years(Date registeredDate) {
		Calendar today = Calendar.getInstance();
		today.add(Calendar.YEAR, -2);
		return (registeredDate.compareTo(today.getTime()) <= 0);
	}

}
