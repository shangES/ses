package icbc.cmis.base;

/**
 * Insert the type's description here.
 * Creation date: (2002-4-25 17:50:54)
 * @author: Administrator
 */
public class DA200251012Bean extends DictBean {
	private java.util.Hashtable ratio = new java.util.Hashtable();
/**
 * DA200251012 constructor comment.
 */
public DA200251012Bean() {
	super();
}
	/**
 * Insert the method's description here.
 * Creation date: (2002-1-8 13:25:23)
 * @param key java.lang.String
 * @param value java.lang.String
 */
public void addData(String key, String value,String ratio1) {
	addData(key,value);
	ratio.put(key,ratio1.trim());
	
}
/**
 * Insert the method's description here.
 * Creation date: (2002-4-25 18:02:24)
 * @return java.lang.String
 * @param key java.lang.String
 */
public String getRatio(String key) {
	return (String)ratio.get(key.trim());
}
}
