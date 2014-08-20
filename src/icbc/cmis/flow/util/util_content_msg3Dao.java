/*
 * 创建日期 2006-3-2
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */

package icbc.cmis.flow.util;

import oracle.jdbc.driver.*;
import icbc.cmis.service.CmisDao;
import icbc.cmis.operation.*;
import java.util.*;
import java.io.IOException;
import java.sql.*;
import icbc.cmis.base.TranFailException;
import icbc.cmis.util.DBTools;

/**
 * Title: Description: 查询审批流程表中具体内容 Copyright: Copyright (c) 2005
 * Company:icbcsdc
 * 
 * @author：郑期彬
 * @version 1.0
 */
public class util_content_msg3Dao extends CmisDao {
	public util_content_msg3Dao(CmisOperation op) {
		super(op);
	}

	/**
	 * 功能描述: 查询本人意见说明
	 * 
	 * @param entcode
	 *            //客户代码
	 * @param tradecode
	 *            //业务申请号
	 * @param xh
	 *            //序号
	 * @param step
	 *            //环节(主要用来区分是否是调查人)
	 * @return
	 * @throws
	 */
	public Vector getadvicetxt(String entcode, String tradecode, String xh,
			String step) throws TranFailException {
		String queryStr = "";

		queryStr = " select process021"
				+ " from mprocess_new   "
				+ " WHERE process001=?  AND process002=?  AND process005=? AND process006=? ";

		try {
			DBTools srv = new DBTools(this.getOperation());
			Vector param = new Vector(4);
			param.add(entcode);
			param.add(tradecode);
			param.add(xh);
			param.add(step);
			Vector v_result = srv.executeSQLResult(queryStr, param);
			return v_result;
		} catch (TranFailException te) {
			throw te;
		} catch (Exception e) {
			throw new TranFailException("util_content_msg3Dao",
					"util_content_msg3Dao.getadvicetxt", e.getMessage());
		}
	}
}