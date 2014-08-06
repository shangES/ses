package com.mk.framework.grid.util;

import java.util.ArrayList;

public class StringUtils {

	/**
	 * 将一个字符串的首字母改为大写或者小写
	 * 
	 * @param srcString
	 *            源字符串
	 * @param flag
	 *            大小写标识，ture小写，false大些
	 * @return 改写后的新字符串
	 */
	public static String toLowerCaseInitial(String srcString, boolean flag) {
		if (srcString == null || srcString.length() == 0)
			return null;
		StringBuilder sb = new StringBuilder();
		if (flag) {
			sb.append(Character.toLowerCase(srcString.charAt(0)));
		} else {
			sb.append(Character.toUpperCase(srcString.charAt(0)));
		}
		sb.append(srcString.substring(1));
		return sb.toString();
	}

	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0 || str.equals("null");
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	public static String emptyConvert(String str, String str1) {
		return isNotEmpty(str) ? str : str1;
	}

	public static String trim(String str) {
		return str == null ? null : str.trim();
	}

	public static String replaceOnce(String text, String searchString, String replacement) {
		return replace(text, searchString, replacement, 1);
	}

	public static String replace(String text, String searchString, String replacement) {
		return replace(text, searchString, replacement, -1);
	}

	public static String replace(String text, String searchString, String replacement, int max) {
		if (isEmpty(text) || isEmpty(searchString) || replacement == null || max == 0) {
			return text;
		}
		int start = 0;
		int end = text.indexOf(searchString, start);
		if (end == -1) {
			return text;
		}
		int replLength = searchString.length();
		int increase = replacement.length() - replLength;
		increase = (increase < 0 ? 0 : increase);
		increase *= (max < 0 ? 16 : (max > 64 ? 64 : max));
		StringBuffer buf = new StringBuffer(text.length() + increase);
		while (end != -1) {
			buf.append(text.substring(start, end)).append(replacement);
			start = end + replLength;
			if (--max == 0) {
				break;
			}
			end = text.indexOf(searchString, start);
		}
		buf.append(text.substring(start));
		return buf.toString();
	}

	public static boolean startsWith(String str, String prefix) {
		return startsWith(str, prefix, false);
	}

	public static boolean startsWithIgnoreCase(String str, String prefix) {
		return startsWith(str, prefix, true);
	}

	public static boolean startsWith(String str, String prefix, boolean ignoreCase) {
		return startsOrEndsWith(str, prefix, ignoreCase, false);
	}

	public static boolean endsWith(String str, String suffix) {
		return endsWith(str, suffix, false);
	}

	public static boolean endsWithIgnoreCase(String str, String suffix) {
		return endsWith(str, suffix, true);
	}

	public static boolean endsWith(String str, String prefix, boolean ignoreCase) {
		return startsOrEndsWith(str, prefix, ignoreCase, true);
	}

	private static boolean startsOrEndsWith(String str, String subStr, boolean ignoreCase, boolean endWidth) {
		if (str == null || subStr == null) {
			return (str == null && subStr == null);
		}
		if (subStr.length() > str.length()) {
			return false;
		}
		int strOffset = 0;
		if (endWidth) {
			strOffset = str.length() - subStr.length();
		}
		return str.regionMatches(ignoreCase, strOffset, subStr, 0, subStr.length());
	}

	public static final String EMPTY = "";

	public static String join(String[] array) {
		return join(array, null);
	}

	public static String join(String[] list, String separator) {
		separator = separator == null ? EMPTY : separator;
		StringBuffer buff = new StringBuffer(5 * list.length);
		for (int i = 0; i < list.length; i++) {
			String s = list[i];
			if (i > 0) {
				buff.append(separator);
			}
			if (s != null) {
				buff.append(s);
			}
		}
		return buff.toString();

	}

	public static String[] split2Array(String s, char separatorChar) {
		return split2Array(s, separatorChar, false);
	}

	public static String[] split2Array(String s, char separatorChar, boolean trim) {
		if (s == null) {
			return null;
		}
		if (s.length() == 0) {
			return new String[0];
		}
		ArrayList<String> list = new ArrayList<String>();
		StringBuffer buff = new StringBuffer(s.length());
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == separatorChar) {
				String e = buff.toString();
				list.add(trim ? e.trim() : e);
				buff.setLength(0);
			} else if (c == '\\' && i < s.length() - 1) {
				buff.append(s.charAt(++i));
			} else {
				buff.append(c);
			}
		}
		String e = buff.toString();
		list.add(trim ? e.trim() : e);
		String[] array = new String[list.size()];
		list.toArray(array);
		return array;
	}
}
