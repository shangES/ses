package icbc.cmis.flow.util;

import java.util.*;
import icbc.cmis.base.*;
import icbc.cmis.operation.*;

public class util_content_flow {
	public util_content_flow() {
	}

	/**
	 * 得到审批记录
	 * 
	 * @param entcode
	 * @param tradecode
	 * @param flowtype
	 * @param employeecode
	 * @param empareacode
	 * @return
	 * @throws Exception
	 */
	public ArrayList queryhistory(String entcode, String tradecode,
			String flowtype, String employeecode, String empareacode)
			throws Exception {
		ArrayList alist = new ArrayList();
		try {
			DummyOperation dummyop = new DummyOperation();
			util_content_flowDao dao = new util_content_flowDao(dummyop);
			alist = dao.queryhistory(entcode, tradecode, flowtype,
					employeecode, empareacode);
		} catch (TranFailException ee) {
			throw ee;
		} catch (Exception e) {
			throw new TranFailException("icbc.cmis.flow.util",
					"util_content_doassist.queryass()", e.getMessage(), e
							.getMessage());
		}
		return alist;
	}

	/**
	 * 得到审批记录，支持语言
	 * 
	 * @param entcode
	 * @param tradecode
	 * @param flowtype
	 * @param employeecode
	 * @param empareacode
	 * @param langcode
	 * @return
	 * @throws Exception
	 */
	public ArrayList queryhistory2(String entcode, String tradecode,
			String flowtype, String employeecode, String empareacode,
			String langcode) throws Exception {
		ArrayList alist = new ArrayList();
		try {
			DummyOperation dummyop = new DummyOperation();
			util_content_flowDao dao = new util_content_flowDao(dummyop);
			alist = dao.queryhistory2(entcode, tradecode, flowtype,
					employeecode, empareacode, langcode);
		} catch (TranFailException ee) {
			throw ee;
		} catch (Exception e) {
			throw new TranFailException("icbc.cmis.flow.util",
					"util_content_doassist.queryass()", e.getMessage(), e
							.getMessage());
		}
		return alist;
	}
}