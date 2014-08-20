package icbc.cmis.flow.BTN;

import java.util.*;
import icbc.cmis.service.CmisDao;
import icbc.cmis.base.*;

public class tuihuiDao extends CmisDao {
	public tuihuiDao(icbc.cmis.operation.CmisOperation op) {
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
	 * 取得退回的环节
	 * 
	 * @param entcode
	 * @param tradecode
	 * @return
	 * @throws TranFailException
	 */
	public String querybackemp(String entcode, String tradecode)
			throws TranFailException {
		String sql = "";
		java.sql.ResultSet rs = null;
		java.sql.PreparedStatement pstmt = null;
		String slastfj = "0";
		try {
			this.getConnection("cmis3");
			sql = "select process005,process011 from mprocess_new where process001=? and process002=? order by process005";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, entcode);
			pstmt.setString(2, tradecode);
			rs = pstmt.executeQuery();
			ArrayList alist = new ArrayList();
			while (rs.next()) {
				HashMap hmap = new HashMap();
				hmap.put("process005", killnull(rs.getString("process005")));
				hmap.put("process011", killnull(rs.getString("process011")));
				alist.add(hmap);
			}
			java.util.Stack sGarbage = new java.util.Stack();
			for (int i = 0; i < alist.size() - 1; i++) {
				HashMap hmap = (HashMap) alist.get(i);
				String process005 = (String) hmap.get("process005");
				String process011 = (String) hmap.get("process011");
				if (process011.equals("0")) { //同意
					sGarbage.push(hmap);
				} else if (process011.equals("1")) { //否决
					sGarbage.clear();
					slastfj = process005;
				} else if (process011.equals("2")) { //退回
					sGarbage.pop();
				} else if (process011.equals("3")) { //保留意见
					sGarbage.push(hmap);
				} else { //防止数据错误
					sGarbage.push(hmap);
				}
			}
			HashMap hresult = (HashMap) sGarbage.peek();
			return (String) hresult.get("process005");
		} catch (EmptyStackException ese) {
			return (Integer.parseInt(slastfj) + 1) + "";
		} catch (TranFailException e) {
			throw e;
		} catch (Exception e) {
			return "1";
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
	}

}