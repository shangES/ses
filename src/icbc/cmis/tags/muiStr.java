package icbc.cmis.tags;

/**
 * 为了在页面使用resource的scriptlet而
 * @author zjfh-zhangyz
 * 2007-4-4 / 9:06:43
 *
 */

public class muiStr {
	private String sDef;
	private String sLang;
	/**
	 * 设置resource定义和语言
	 * @param def resource定义
	 * @param lang 使用的语言
	 */
	public muiStr(String def, String lang) {
		this.sDef = def;
		this.sLang = lang;
	}
	/**
	 * 返回resource
	 * @param sItem
	 * @return
	 */
	public String getStr(String sItem) {
		return icbc.cmis.tags.MuiTagBase.getStr(sDef, sLang, sItem);
	}
}