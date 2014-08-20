/*
 * 创建日期 2006-3-6
 * 
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
 
package icbc.cmis.flow.util;

import icbc.cmis.base.TranFailException;
import icbc.cmis.operation.DummyOperation;

import java.util.ArrayList;






/**
 * @author 
 * @author 袁文
 * 功能-显示签署人签署意见
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 * 
 * 这个是用来查询签署人签署意见的封装类，不是OP
 * 
 * 
 */

public class util_content_subscribe {
	public util_content_subscribe() {
	}
	
	public String querysubscribe(String entcode, String tradecode,String empareacode,String employeecode,String ordercode,String flowtype,String tradetype) throws Exception {
		String  msg = "";
				try {
					DummyOperation dummyop = new DummyOperation();
					util_content_subscribeDao dao = new util_content_subscribeDao(dummyop);
					msg = dao.getsubscribe( tradecode,entcode,tradetype);
				}
	
	 catch (TranFailException ee) {
				throw ee;
			} catch (Exception e) {
				throw new TranFailException("icbc.cmis.flow.util", "util_content_subscribe.querysubscribe()", e.getMessage(), e.getMessage());
			}
				return msg;
	}
}
