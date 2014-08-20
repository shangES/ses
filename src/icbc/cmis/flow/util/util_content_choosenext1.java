/*
 * 创建日期 2006-7-10
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
 * @author 袁文
 * 功能-选择下一环节,下一地区
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class util_content_choosenext1 {
	public util_content_choosenext1() {

	}

	/**
	 * 取得环节列表
	 * @param flowtype
	 * @return
	 * @throws Exception
	 */
	public ArrayList getflowlist(String flowtype, String busi_type,
			String langcode) throws Exception {
		ArrayList alist = new ArrayList();
		try {
			DummyOperation dummyop = new DummyOperation();
			util_content_choosenextDao dao = new util_content_choosenextDao(
					dummyop);
			alist = dao.getflowlist(flowtype, busi_type, langcode);
		} catch (TranFailException ee) {
			throw ee;
		} catch (Exception e) {
			throw new TranFailException("icbc.cmis.flow.util",
					"util_content_choosenext.getflowlist()", e.getMessage(), e
							.getMessage());
		}
		return alist;
	}

	/**
	 * 取得发送的下一地区
	 * @param areacode
	 * @param busi_type
	 * @param entcode
	 * @return
	 * @throws Exception
	 */
	public ArrayList getnextarea(String areacode, String busi_type, String entcode,String tradecode,String tradetype) throws Exception {
		ArrayList alist = new ArrayList();
		try {
			DummyOperation dummyop = new DummyOperation();
			util_content_choosenextDao1 dao = new util_content_choosenextDao1(dummyop);
			alist = dao.getnextarea(areacode, busi_type, entcode,tradecode,tradetype);
		} catch (TranFailException ee) {
			throw ee;
		} catch (Exception e) {
			throw new TranFailException(
				"icbc.cmis.flow.util",
				"util_content_choosenext.getflowlist()",
				e.getMessage(),
				e.getMessage());
		}
		return alist;
	}

}
