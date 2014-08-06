package com.mk.framework.grid.util;

import java.io.Serializable;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtil implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 计算年龄
	public static final int getBetweenAge(Date now, Date birthDay) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);

		if (cal.before(birthDay)) {
			return 0;
		}

		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH);
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(birthDay);

		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				// monthNow==monthBirth
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				} else {
					// do nothing
				}
			} else {
				// monthNow>monthBirth
				age--;
			}
		} else {
			// monthNow<monthBirth
			// donothing
		}
		return age;
	}

	// 计算年龄
	public static final int getAge(Date birthDay) {
		Calendar cal = Calendar.getInstance();

		if (cal.before(birthDay)) {
			return 0;
		}

		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH);
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(birthDay);

		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				// monthNow==monthBirth
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				} else {
					// do nothing
				}
			} else {
				// monthNow>monthBirth
				age--;
			}
		} else {
			// monthNow<monthBirth
			// donothing
		}
		return age;
	}

	/**
	 * 格式化日期到年月日时分
	 * 
	 * @param time
	 * @return
	 */
	public static final String formatDateYMD(java.util.Date date) {
		if (date == null)
			return null;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(date);
	}

	/**
	 * 格式化日期到年月日时分
	 * 
	 * @param time
	 * @return
	 */
	public static final String formatDateYM(java.util.Date date) {
		if (date == null)
			return null;
		DateFormat df = new SimpleDateFormat("yyyyMM");
		return df.format(date);
	}

	/**
	 * 格式化日期到年月日时分
	 * 
	 * @param time
	 * @return
	 */
	public static final String formatDateMDHHmm(java.util.Date date) {
		if (date == null)
			return null;
		DateFormat df = new SimpleDateFormat("MM-dd HH:mm");
		return df.format(date);
	}

	/**
	 * 格式化日期到年月日时分
	 * 
	 * @param time
	 * @return
	 */
	public static final String formatDateYMDHHmmSS(java.util.Date date) {
		if (date == null)
			return null;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
		return df.format(date);
	}

	/**
	 * 格式化日期到年月日时分
	 * 
	 * @param time
	 * @return
	 */
	public static final String formatDateYMDHHmmChina(java.util.Date date) {
		if (date == null)
			return null;
		DateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
		return df.format(date);
	}

	/**
	 * 将string date转化成date
	 * 
	 * @param strDate
	 * @return
	 * @throws ParseException
	 */
	public static final java.sql.Date parse(String strDate) {
		if (StringUtils.isEmpty(strDate))
			return null;
		try {
			return new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(strDate).getTime());
		} catch (ParseException e) {

		}
		return null;

	}

	/**
	 * 将string date转化成date
	 * 
	 * @param strDate
	 * @return
	 * @throws ParseException
	 */
	public static final java.sql.Date parseChina(String strDate) {
		if (StringUtils.isEmpty(strDate))
			return null;
		try {
			return new java.sql.Date(new SimpleDateFormat("yyyy年MM月dd日").parse(strDate).getTime());
		} catch (ParseException e) {

		}
		return null;

	}

	/**
	 * 将string date转化成date
	 * 
	 * @param strDate
	 * @return
	 * @throws ParseException
	 */
	public static final java.sql.Date parseEnglish(String strDate) {
		if (StringUtils.isEmpty(strDate))
			return null;
		try {
			return new java.sql.Date(new SimpleDateFormat("yyyy/MM/dd").parse(strDate).getTime());
		} catch (ParseException e) {
		}
		return null;

	}

	/**
	 * 将string date转化成date
	 * 
	 * @param strDate
	 * @return
	 * @throws ParseException
	 */
	public static final java.sql.Timestamp parseTime(String strDate) {
		if (StringUtils.isEmpty(strDate) || strDate.equals("null"))
			return null;
		try {
			return new java.sql.Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:SS").parse(strDate).getTime());
		} catch (ParseException e) {

		}
		return null;

	}

	/**
	 * 日期序列化
	 * 
	 * @param requestStartTime
	 * @return
	 */
	public static String parseYMDHSTime(String time) {
		if (StringUtils.isEmpty(time) || time.equals("null"))
			return null;
		try {
			java.sql.Timestamp mytime = new java.sql.Timestamp(new SimpleDateFormat("yyyyMMddHHmmSS").parse(time).getTime());
			return formatDateYMDHHmmSS(mytime);
		} catch (ParseException e) {

		}
		return null;
	}

	
	/**
	 * 获取两个日期相差的天数
	 * 
	 * @param smdate
	 * @param bdate
	 * @return
	 * @throws ParseException
	 */
	public static int daysBetween(Date mintime, Date date) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		mintime = new Date(format.parse(format.format(mintime)).getTime());
		date = new Date(format.parse(format.format(date)).getTime());
		Long temp = date.getTime()-mintime.getTime();
		//取出天数 =num/毫秒/秒/分/时
		Long num = temp/1000/60/60/24;
		return num.intValue();
	}

}
