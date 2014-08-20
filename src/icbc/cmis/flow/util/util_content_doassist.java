package icbc.cmis.flow.util;

import java.util.*;
import java.sql.*;
import icbc.cmis.base.*;
import icbc.cmis.operation.*;
import icbc.cmis.second.pkg.*;

/**
 * 查询辅助审批信息
 * 
 * @author zjfh-zhangyz
 * 
 * 更改所生成类型注释的模板为 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class util_content_doassist {
	public util_content_doassist() {

	}

	public HashMap queryass(String entcode, String tradecode, String ordernum,
			String employeecode, String empareacode) throws Exception {
		HashMap hmap = new HashMap();
		try {
			DummyOperation dummyop = new DummyOperation();
			util_content_doassistDao dao = new util_content_doassistDao(dummyop);
			hmap = dao.queryass(entcode, tradecode, ordernum, employeecode,
					empareacode);
		} catch (TranFailException ee) {
			throw ee;
		} catch (Exception e) {
			throw new TranFailException("icbc.cmis.flow.util",
					"util_content_doassist.queryass()", e.getMessage(), e
							.getMessage());
		}
		return hmap;
	}

}