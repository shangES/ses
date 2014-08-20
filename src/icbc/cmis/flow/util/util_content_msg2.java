/*
 * 创建日期 2006-3-24
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package icbc.cmis.flow.util;

import java.util.*;
import java.sql.*;
import icbc.cmis.base.*;
import icbc.cmis.operation.*;
import icbc.cmis.second.pkg.*;

/**
 * @author 郑期彬
 * 
 * 更改所生成类型注释的模板为 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */

public class util_content_msg2 {
	public util_content_msg2() {
	}

	/**
	 * 得到该业务是否是发起人flag
	 * 
	 * @param entcode
	 * @param tradecode
	 * @returnflow_entcode,flow_tradecode,
	 * @throws Exception
	 */
	public String queryfirstflag(String entcode, String tradecode,
			String ordercode, String empareacode, String employeecode)
			throws Exception {
		String str = "";
		try {
			DummyOperation dummyop = new DummyOperation();
			util_content_msg2Dao dao = new util_content_msg2Dao(dummyop);
			str = dao.queryfirstflag(entcode, tradecode, ordercode,
					empareacode, employeecode);
		} catch (TranFailException ee) {
			throw ee;
		} catch (Exception e) {
			throw new TranFailException("icbc.cmis.flow.util",
					"util_content_msg2.queryass()", e.getMessage(), e
							.getMessage());
		}
		return str;
	}
}