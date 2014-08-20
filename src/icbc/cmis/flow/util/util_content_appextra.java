package icbc.cmis.flow.util;

import java.util.*;
import java.sql.*;
import icbc.cmis.base.*;
import icbc.cmis.operation.*;
import icbc.cmis.second.pkg.*;

/**
 * 调查资料修改相关
 * 
 * @author zjfh-zhangyz
 * 
 * 更改所生成类型注释的模板为 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class util_content_appextra {
	public util_content_appextra() {

	}

	public String spellbtn(String entcode, String tradecode, String tradetype)
			throws Exception {
		String url = "";
		try {
			DummyOperation dummyop = new DummyOperation();
			util_content_appextraDao dao = new util_content_appextraDao(dummyop);
			url = dao.spellbtn(entcode, tradecode, tradetype);
		} catch (TranFailException ee) {
			throw ee;
		} catch (Exception e) {
			throw new TranFailException("icbc.cmis.flow.util",
					"util_content_appextra.spellbtn()", e.getMessage(), e
							.getMessage());
		}
		return url;
	}

}