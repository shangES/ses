package icbc.cmis.flow.util;

import java.util.*;
import icbc.cmis.operation.*;
import icbc.cmis.base.*;

public class util_content_def_2 {
	public util_content_def_2() {

	}

	/**
	 * 查询环节定义
	 * 
	 * @param entcode
	 * @param tradecode
	 * @param flowtype
	 * @param empareacode
	 * @param tradetype
	 * @param LangCode
	 *            语言标志
	 * @return
	 * @throws Exception
	 */
	public HashMap getflowdefconten(String entcode, String tradecode,
			String flowtype, String empareacode, String tradetype,
			String langcode) throws Exception {
		HashMap hmap = new HashMap();
		try {
			DummyOperation dummyop = new DummyOperation();
			util_content_def_2Dao dao = new util_content_def_2Dao(dummyop);
			hmap = dao.getflowdefconten(entcode, tradecode, flowtype,
					empareacode, tradetype, langcode);
		} catch (TranFailException ee) {
			throw ee;
		} catch (Exception e) {
			throw new TranFailException("icbc.cmis.flow.util",
					"util_content_def_2.getflowdefconten()", e.getMessage(), e
							.getMessage());
		}
		return hmap;
	}
}