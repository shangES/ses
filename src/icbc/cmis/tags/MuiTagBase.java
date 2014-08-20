package icbc.cmis.tags;

import java.util.Hashtable;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 建立一个静态hashtable，放置使用的ResourceBundle
 * 如果使用的ResourceBundle不在hashtable中
 * 则进行初始化载入一次
 * @author zjfh-zhangyz
 * 2007-4-4 / 8:53:16
 *
 */

public class MuiTagBase {
	//静态hashtable
	private static Hashtable hTag = new Hashtable();

	public static String getStr(String filename, String language, String StrID) {
		String file = filename + "_" + language;//作为该ResourceBundle的key值
		ResourceBundle bundle = (ResourceBundle)hTag.get(file);
		//如果还没有载入则载入一次
		if (bundle == null) {
			Locale local = getDefaultLocale(language);
			bundle = ResourceBundle.getBundle(filename, local);
			hTag.put(file, bundle);
		}
		//返回搜索到的字符串
		return (String)bundle.getString(StrID);
	}

	public static Locale getDefaultLocale(String languageMsg) {
		if (languageMsg != null && languageMsg.length() != 0) {
			String language = "";
			String country = "";
			String viriat = "";
			int idx = languageMsg.indexOf("_");
			if (idx != -1) {
				language = languageMsg.substring(0, idx);
				languageMsg = languageMsg.substring(idx + 1);
				int idx1 = languageMsg.indexOf("_");
				if (idx1 != -1) {
					country = languageMsg.substring(0, idx1);
					viriat = languageMsg.substring(idx + 1);
				} else {
					country = languageMsg;
				}
			} else {
				language = languageMsg;
			}
			if (language == null) {
				language = "";
			}
			if (country == null) {
				country = "";
			}
			if (viriat == null) {
				viriat = "";
			}
			return new Locale(language, country, viriat);
		} else {
			return Locale.getDefault();
		}
	}

}