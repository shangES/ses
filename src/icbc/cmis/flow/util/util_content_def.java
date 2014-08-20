/*
 * 创建日期 2006-3-8
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */

package icbc.cmis.flow.util;

import java.util.*;
import icbc.cmis.operation.*;
import icbc.cmis.base.*;

/**
 * @author 郑期彬 功能-显示各字段信息 更改所生成类型注释的模板为 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */

public class util_content_def {
	public util_content_def() {
	}

	/**
	 * 查询环节定义
	 * 
	 * @param entcode
	 * @param tradecode
	 * @param flowtype
	 * @param empareacode
	 * @param busitype
	 * @return
	 * @throws Exception
	 */
	public ArrayList getflowdefconten(String entcode, String tradecode,
			String flowtype, String empareacode, String busitype)
			throws Exception {
		ArrayList alist = new ArrayList();
		try {
			DummyOperation dummyop = new DummyOperation();
			util_content_defDao dao = new util_content_defDao(dummyop);
			alist = dao.getflowdefconten(entcode, tradecode, flowtype,
					empareacode, busitype);
		} catch (TranFailException ee) {
			throw ee;
		} catch (Exception e) {
			throw new TranFailException("icbc.cmis.flow.util",
					"util_content_def.getflowdefconten()", e.getMessage(), e
							.getMessage());
		}
		return alist;
	}

}