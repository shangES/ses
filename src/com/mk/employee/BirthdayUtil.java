package com.mk.employee;

import java.util.Calendar;
import java.util.Map;

import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.util.DateUtil;
import com.mk.framework.grid.util.StringUtils;

public class BirthdayUtil {

	// 年龄转生日=========
	@SuppressWarnings("unchecked")
	public static final void fromBirthdayToAge(GridServerHandler grid) {
		Map<String, String> map = (Map<String, String>) grid.getParameters();
		String birthday_s = grid.getPageParameter("birthday_s");
		String birthday_e = grid.getPageParameter("birthday_e");
		Calendar cal = Calendar.getInstance();
		int yearNow = cal.get(Calendar.YEAR);

		// 生日开始
		if (StringUtils.isNotEmpty(birthday_s)) {
			int year_s = yearNow - Integer.valueOf(birthday_s);
			cal.set(Calendar.YEAR, year_s);
			map.put("birthday_s", DateUtil.formatDateYMD(cal.getTime()));
		}
		// 生日结束
		if (StringUtils.isNotEmpty(birthday_e)) {
			int year_e = yearNow - Integer.valueOf(birthday_e);
			cal.set(Calendar.YEAR, year_e);
			map.put("birthday_e", DateUtil.formatDateYMD(cal.getTime()));
		}
	}

}
