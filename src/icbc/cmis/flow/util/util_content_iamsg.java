/*
 * 创建日期 2006-3-6
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */

package icbc.cmis.flow.util;

import java.util.*;
import icbc.cmis.operation.*;
import icbc.cmis.second.pkg.*;
import icbc.cmis.base.*;

/**
 * @author 功能-显示机评意见信息 更改所生成类型注释的模板为 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 * 
 * 这个是用来查询只读机评的封装类，不是OP 只读机评采取在页面上直接取得的方法
 *  
 */

public class util_content_iamsg {
	public util_content_iamsg() {
	}

	/**
	 * 查询机评意见
	 * 
	 * @param entcode
	 * @param tradecode
	 * @returnflow_entcode,flow_tradecode,
	 * @throws Exception
	 */
	public ArrayList queryiamsg(String entcode, String tradecode,
			String empareacode, String employeecode, String ordercode,
			String flowtype, String tradetype) throws Exception {
		ArrayList alist = new ArrayList();
		try {
			DummyOperation dummyop = new DummyOperation();
			util_content_iamsgDao dao = new util_content_iamsgDao(dummyop);
			alist = dao.getiamsg(entcode, tradecode, empareacode, employeecode,
					ordercode, flowtype, tradetype);
		} catch (TranFailException ee) {
			throw ee;
		} catch (Exception e) {
			throw new TranFailException("icbc.cmis.flow.util",
					"util_content_iamsg.queryass()", e.getMessage(), e
							.getMessage());
		}
		return alist;
	}

}