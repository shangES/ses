package icbc.cmis.second.pkg;

import java.util.Vector;

import icbc.cmis.base.IndexedDataCollection;
import icbc.cmis.base.KeyedDataCollection;
import icbc.cmis.base.TranFailException;
import icbc.cmis.operation.CmisOperation;


/**
 * @author xgl
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class SQLExample extends CmisOperation {

	/**
	 * @see icbc.cmis.operation.CmisOperation#execute()
	 */
	public void execute() throws Exception, TranFailException {
		try {
			DBSQLParamsDef sqlDef = new DBSQLParamsDef();
			Vector vColumn = new Vector(3);
			vColumn.addElement("c1");
			vColumn.addElement("b.c2");
			vColumn.addElement("distinct c3");
			sqlDef.setVColumns(vColumn);

			Vector vInDataFields = new Vector();
			vInDataFields.addElement("inFieldName1");
			vInDataFields.addElement("inFieldName2");
			vInDataFields.addElement("inFieldName3");
			sqlDef.setInColumns(vInDataFields);

			sqlDef.setTableNames("a,b");
			sqlDef.setConditions("where c = ? and d = ? and j = ?");
			IndexedDataCollection iResult = new IndexedDataCollection();
			iResult.setName("iResult");
			DBProcedureAccessService srv = new DBProcedureAccessService(this);
			srv.executeQuery(sqlDef, this.getOperationData(), iResult);
			for (int i = 0; i < iResult.getSize(); i++) {
				KeyedDataCollection aRow =
					(KeyedDataCollection) iResult.getElement(i);
				//可以得到一条记录。
				String c1 = (String) aRow.getValueAt("c1"); //取得字段的值。
				String c2 = (String) aRow.getValueAt("b_c2");
				String c3 = (String) aRow.getValueAt("c3");
			}
		} catch (TranFailException e) {
			throw e;
		} catch (Exception e) {
			//handler your exception 
			throw e;

		}
		setReplyPage("/icbc/cmis/ok.jsp");

	}

}
