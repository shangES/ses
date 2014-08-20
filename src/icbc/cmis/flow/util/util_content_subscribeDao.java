/*
 * 创建日期 2006-3-6
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */

package icbc.cmis.flow.util;

import icbc.cmis.base.TranFailException;
import icbc.cmis.service.CmisDao;

/**
 * Title:
 * Description: 签署人签批意见
 * Copyright:    Copyright (c) 2005
 * Company:icbcsdc
 * @author：袁文
 * @version 1.0
 */

public class util_content_subscribeDao extends CmisDao{
	public util_content_subscribeDao(icbc.cmis.operation.CmisOperation op) {
		super(op);
		}
	private String killnull(String strin) {
			if (strin == null) {
				return "";
			} else {
				return strin;
			}
		}
	/**
 * 取得签署人签批意见
 * @param tradecode   申请号
 * @param entcode     客户代码
 * @return
 * @throws Exception
 */
		
		public String getsubscribe(String tradecode,String entcode,String tradetype )
	throws TranFailException{
		java.sql.ResultSet rs = null;
		java.sql.PreparedStatement pstmt = null;
		String sql = null;
		String subscribe = "";
		try{
		getConnection("cmis3");
		//查询签批意见
		sql= 
              "select process021  from mprocess_new" 
		    +" where process001=? and process002=? "
			+"and process005=(select max(process005) from mprocess_new where process001=? and process002=? and process004=?  )";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,entcode);
			pstmt.setString(2,tradecode);
			pstmt.setString(3,entcode);
		    pstmt.setString(4,tradecode);
		    pstmt.setString(5,tradetype);
			rs = pstmt.executeQuery();
		    
		    if(rs.next()){
		    
		    subscribe = rs.getString("process021");
		}
		}
		catch (TranFailException e) {
					throw e;
				} catch (Exception e) {
					throw new TranFailException("icbc.cmis.flow.util", "util_content_subscribeDao.getsubscribe()", e.getMessage(), e.getMessage());
				} finally {
					if (rs != null)
						try {
							rs.close();
						} catch (Exception ex) {
						}
					if (pstmt != null)
						try {
							pstmt.close();
						} catch (Exception ex) {
						}
					closeConnection();
		}
	
				
				return subscribe;
	}
}