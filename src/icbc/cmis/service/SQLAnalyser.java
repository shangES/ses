package icbc.cmis.service;

import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;
/**
 * Insert the type's description here.
 * Creation date: (2002-12-19 19:40:13)
 * @author: Administrator
 */

public class SQLAnalyser {
	private static Vector sqlPool = new Vector();
	/**
	 * SQLAnalyser constructor comment.
	 */
	public SQLAnalyser() {
		super();
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (2002-12-19 19:41:04)
	 * @return java.lang.String
	 * @param sql java.lang.String
	 */
	public static String getAnalyserSQL(String sql, Hashtable hValue) {
		try {
			int iv = (sql.toLowerCase()).indexOf("values");
			if (iv != -1 && sql.substring(iv + 6).startsWith("(")) {
				return sql;
			} else {
				java.util.StringTokenizer token =
					new java.util.StringTokenizer(sql, "=!<>");
				StringBuffer buffer = new StringBuffer();
				boolean isFirstStep = true;
				int count = 0;
				String oldStr = null;
				boolean isEnd = false;
				while (token.hasMoreElements()) {
					String subSql = (String) token.nextElement();
					int iCount = 0;
					String tmps = subSql;
					while(true){
						 int x = tmps.indexOf("'");
						 if(x == -1){
						 	if(iCount%2 != 0){
						 		try{
						 			hValue.clear();
						 		}catch(Exception ee){}
						 		return sql;
						 	}else{
						 		break;
						 	}
						 }else{
						 	tmps = tmps.substring(x+1);
						 	iCount++;
						 }
						
					}
					if (isFirstStep) {
						oldStr = sql.substring(subSql.length());
						isFirstStep = false;
						buffer.append(subSql.trim());
						continue;
					}
					String delim = oldStr.substring(0, oldStr.indexOf(subSql));
					if (oldStr.trim().compareTo((delim + subSql).trim())
						== 0) {
						isEnd = true;
						oldStr = "";
					} else {
						oldStr =
							oldStr.substring(delim.length() + subSql.length());
					}
					subSql = subSql.trim();
					int first = subSql.indexOf("'");
					if (first != -1 && first == 0) {
						int second = subSql.indexOf("'", first + 1);
						String value = subSql.substring(first + 1, second);
						String[] tmpArray = { "S", value };
						hValue.put(new Integer(++count), tmpArray);
						buffer.append(
							subSql.substring(0, first)
								+ delim
								+ "? "
								+ subSql.substring(second + 1)
								+ " ");

					} else {
						int idxSpace = subSql.indexOf(" ");
						if (idxSpace == -1) {
							idxSpace = subSql.indexOf(",");
						}
						if (idxSpace == -1 && isEnd)
							idxSpace = subSql.length();
						try {
							String tmpStr = subSql.substring(0, idxSpace);
							new Double(tmpStr).doubleValue();
							int idxDot = tmpStr.indexOf(".");
							String type = "";
							if (idxDot != -1) {
								if ((tmpStr.substring(idxDot)).length() >= 3) {
									type = "D";
								} else {
									type = "F";
								}
							} else {
								type = "I";
							}
							String[] tmpArray = { type, tmpStr };
							hValue.put(new Integer(++count), tmpArray);
							buffer.append(
								delim
									+ "? "
									+ subSql.substring(idxSpace)
									+ " ");

						} catch (NumberFormatException e) {
							buffer.append(delim + subSql + " ");
						}

					}
				}
				icbc.cmis.base.CmisConstance.pringMsg(new String(buffer));
				return new String(buffer);
			}
		} catch (Exception e) {
			hValue.clear();
			return sql;
		}
	}
}
