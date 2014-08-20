/*
 * 创建日期 2005-7-27
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package icbc.cmis.util;
import java.util.*;
import icbc.cmis.base.*;
import icbc.cmis.operation.CmisOperation;
import icbc.cmis.util.module.*;

/**
 * @author Administrator
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class GeneralViewOp extends CmisOperation {
  String baseWebPath = (String)CmisConstance.getParameterSettings().get("webBasePath");
  GeneralViewDAO dao = null;
  /**
  	 * <b>功能描述: </b><br>
  	 * <p>运行方法  </p>
  	 * @see icbc.cmis.operation.CmisOperation#execute()
  	 * @throws TranFailException
  	 *
  	 */
  public void execute() throws TranFailException {
    try {
      String opAction = getStringAt("opAction");
      if (opAction.equals("showInsertView")) { //进入新增页面
        showInsertView();
      }
      else if (opAction.equals("showModifyView")) { //进入修改页面
        showModifyView();
      }
      else if (opAction.equals("showQueryView")) { //进入查询页面
        showQueryView();
      }
      else if (opAction.equals("add")) { //新增
        addArchive();
      }
      else if (opAction.equals("modify")) { //修改
        modifyArchive();
      }
      else if (opAction.equals("delete")) { //删除
        deleteArchive();
      }
      else if (opAction.equals("saveTempData")) { //保存临时数据
        saveTempData();
      }
      else if (opAction.equals("clearTempData")) { //清除临时数据
        clearTempData();
      }
      else if (opAction.equals("addjjArchive")){
      
				addjjArchive();
      }
      else if (opAction.equals("modjjArchive")){
      	
				modjjArchive();
      }
		else if (opAction.equals("deljjArchive")){
      	
			deljjArchive();
			 }	

      this.setOperationDataToSession();
    }
    catch (TranFailException tfe) {
      throw tfe;
    }
    catch (Exception e) {
      throw new TranFailException("100751", "icbc.cmis.util.GeneralViewOp[execute]", e.getMessage(), genMsg.getErrMsg("100751"));
    }
  }

  /**
   * 生成结构数（带输入域的值）
   * @param root 根节点
   * @param vecFieldDefine 字段定义
   * @param vecLayoutDefine 字段布局
   * @param data 字段的值
   * @throws TranFailException
   */
  private void buildTreeWithInput(Cell root, Vector vecFieldDefine, Vector vecLayoutDefine, Hashtable data) throws TranFailException {
    Hashtable htFieldDefine = reformatVector(vecFieldDefine);
    Hashtable htLayoutDefine = new Hashtable();
    for (int i = 0; i < vecLayoutDefine.size(); i++) {
      Hashtable layout = (Hashtable)vecLayoutDefine.get(i);

      //取单元格信息
      String cellNo = (String)layout.get("CELL_CODE");
      String preCellNo = (String)layout.get("PRE_CELL_CODE");
      String cellType = (String)layout.get("CELL_TYPE");
      String textName = (String)layout.get("TEXT_NAME");
      String inputTable = (String)layout.get("INPUT_TABLE");
      String inputField = (String)layout.get("INPUT_FIELD");
      String cellColspan = (String)layout.get("CELL_COLSPAN");

      String cellName = null;
      if (cellType.equals("text"))
        cellName = textName;
      else if (cellType.equals("input"))
        cellName = inputTable + "#" + inputField; //inputNAME;
      //如果前单元格是<table>
      if (preCellNo.startsWith("t")) {
        //建立<tr><td>
        TrTdCell trtdCell = null;
        if (cellType.equals("input"))
          trtdCell =
            new TrTdCell(cellNo, preCellNo, cellName, cellType, cellColspan, (Hashtable) ((Hashtable)htFieldDefine.get(inputTable)).get(inputField));
        else
          trtdCell = new TrTdCell(cellNo, preCellNo, cellName, cellType, cellColspan);
        Cell preCell = (Cell)htLayoutDefine.get(preCellNo);
        //如果前一个单元格在缓存htLayoutDefine中不存在，即第一次引用
        if (preCell == null) {
          //建立<table>，放入缓存
          preCell = new TableCell(preCellNo, "root", preCellNo, null);
          root.addChild(preCell);
          htLayoutDefine.put(preCellNo, preCell);
        }
        preCell.addChild(trtdCell);
        htLayoutDefine.put(cellNo, trtdCell);

      }
      //如果前一个单元格是普通单元格，如010030
      else {
        Object cellOrList = htLayoutDefine.get(preCellNo);
        if (cellOrList instanceof Cell) {
          Cell preCell = (Cell)cellOrList;
          //如果前一个单元格已经有子单元格
          if (preCell.getChildren().size() >= 1) {
            TrTdCell trtdCell = null;
            //如果cellType是input，则取出字段定义
            if (cellType.equals("input")) {
              Hashtable ht_field = (Hashtable) ((Hashtable)htFieldDefine.get(inputTable)).get(inputField);
              if (ht_field == null)
                throw new TranFailException(
                  "100752",
                  "icbc.cmis.util.GeneralViewOp[buildTreeWithInput]",
                  genMsg.getErrMsg("100751", inputTable + "|" + inputField),
                  genMsg.getErrMsg("100751", inputTable + "|" + inputField));
              if (ht_field.get("IS_NEED").equals("1"))
                setNeedMark(preCellNo, htLayoutDefine);

              trtdCell = new TrTdCell(cellNo, preCellNo, cellName, cellType, cellColspan, ht_field);
            }

            //如果是text
            else if (cellType.equals("text")) {
              trtdCell = new TrTdCell(cellNo, preCellNo, cellName, cellType, cellColspan);
              trtdCell.syncAncestorRowSpan(htLayoutDefine);
            }
            else
              throw new TranFailException(
                "100753",
                "icbc.cmis.util.GeneralViewOp[buildTreeWithInput]",
                genMsg.getErrMsg("100753", cellType + "|" + cellNo),
                genMsg.getErrMsg("100753", cellType + "|" + cellNo));

            preCell.addChild(trtdCell);
            htLayoutDefine.put(cellNo, trtdCell);
          }
          //如果前一个单元格没有子单元格
          else {
            TdCell tdCell = null;
            if (cellType.equals("input")) {
              Hashtable ht_field = (Hashtable) ((Hashtable)htFieldDefine.get(inputTable)).get(inputField);
              if (ht_field == null)
                throw new TranFailException(
                  "100752",
                  "icbc.cmis.util.GeneralViewOp[buildTreeWithInput]",
                  genMsg.getErrMsg("100751", inputTable + "|" + inputField),
                  genMsg.getErrMsg("100751", inputTable + "|" + inputField));
              if (ht_field.get("IS_NEED").equals("1"))
                setNeedMark(preCellNo, htLayoutDefine);

              try {
                String value = "";
                try {
                  value = (String) ((Hashtable)data.get(inputTable)).get(inputField);
                }
                catch (Exception e) {
                  throw new TranFailException(
                    "100754",
                    "icbc.cmis.util.GeneralViewOp[buildTreeWithInput]",
                    e.getMessage(),
                    genMsg.getErrMsg("100754", cellName));
                }
                String modifyPriv = (String)ht_field.get("MODIFY_PRIV");
                //如果是新增页面展示前就需确定的值
                if (modifyPriv.equals("3")) {
                  tdCell = new TdCell(cellNo, preCellNo, cellName, cellType, cellColspan, ht_field, value, true);
                }
                //如果是新增页面展示后就需确定的值
                else if (modifyPriv.equals("2")) {
                  tdCell = new TdCell(cellNo, preCellNo, cellName, cellType, cellColspan, ht_field, value, true);
                }
                //从临时数据集合中取值
                else
                  tdCell = new TdCell(cellNo, preCellNo, cellName, cellType, cellColspan, ht_field, value, false);
              }
              catch (Exception e) {
                //普通的不含输入值的输入域
                tdCell = new TdCell(cellNo, preCellNo, cellName, cellType, cellColspan, ht_field);
              }

            }
            else if (cellType.equals("text")) {
              tdCell = new TdCell(cellNo, preCellNo, cellName, cellType, cellColspan);
            }
            preCell.addChild(tdCell);
            htLayoutDefine.put(cellNo, tdCell);
          }
        }

        else
          throw new TranFailException(
            "100753",
            "icbc.cmis.util.GeneralViewOp[buildTreeWithInput]",
            genMsg.getErrMsg("100753", cellType + "|" + cellNo),
            genMsg.getErrMsg("100753", cellType + "|" + cellNo));
      }
    }
  }

  /**
   * 生成结构数（带输入域的值）
   * @param root 根节点
   * @param vecFieldDefine 字段定义
   * @param vecLayoutDefine 字段布局
   * @param recordData 字段的值
   * @param isPlain 是否为文本显示
   * @throws TranFailException
   */
  private void buildTreeWithInputValue(Cell root, Vector vecFieldDefine, Vector vecLayoutDefine, Hashtable recordData, boolean isPlain)
    throws TranFailException {
    Hashtable htFieldDefine = reformatVector(vecFieldDefine);
    Hashtable htLayoutDefine = new Hashtable();
    Hashtable recordData2D = null;
    Vector seqDict = null;
    for (int i = 0; i < vecLayoutDefine.size(); i++) {
      Hashtable layout = (Hashtable)vecLayoutDefine.get(i);

      //取单元格信息
      String cellNo = (String)layout.get("CELL_CODE");
      String preCellNo = (String)layout.get("PRE_CELL_CODE");
      String cellType = (String)layout.get("CELL_TYPE");
      String textName = (String)layout.get("TEXT_NAME");
      String inputTable = (String)layout.get("INPUT_TABLE");
      String inputField = (String)layout.get("INPUT_FIELD");
      //String inputNAME = (String)layout.get("INPUT_NAME");
      String cellColspan = (String)layout.get("CELL_COLSPAN");
      String cellName = null;
      if (cellType.equals("text"))
        cellName = textName;
      else if (cellType.equals("input"))
        cellName = inputTable + "#" + inputField; //inputNAME;
      //如果前单元格是<table>
      if (preCellNo.startsWith("t")) {
        //建立<tr><td>
        TrTdCell trtdCell = null;
        if (cellType.equals("input"))
          trtdCell =
            new TrTdCell(cellNo, preCellNo, cellName, cellType, cellColspan, (Hashtable) ((Hashtable)htFieldDefine.get(inputTable)).get(inputField));
        else
          trtdCell = new TrTdCell(cellNo, preCellNo, cellName, cellType, cellColspan);
        Cell preCell = (Cell)htLayoutDefine.get(preCellNo);
        //如果前一个单元格在缓存htLayoutDefine中不存在，即第一次引用
        if (preCell == null) {
          //建立<table>，放入缓存
          preCell = new TableCell(preCellNo, "root", preCellNo, null);
          root.addChild(preCell);
          htLayoutDefine.put(preCellNo, preCell);
        }
        preCell.addChild(trtdCell);
        htLayoutDefine.put(cellNo, trtdCell);
      }
      //如果前一个单元格是普通单元格，如001003
      else {
        Object cellOrList = htLayoutDefine.get(preCellNo);
        if (cellOrList instanceof Cell) {
          Cell preCell = (Cell)cellOrList;
          //如果前一个单元格已经有子单元格
          if (preCell.getChildren().size() >= 1) {
            TrTdCell trtdCell = null;
            //如果cellType是input，则取出字段定义
            if (cellType.equals("input")) {
              Hashtable ht_field = (Hashtable) ((Hashtable)htFieldDefine.get(inputTable)).get(inputField);
              if (ht_field == null)
                throw new TranFailException(
                  "100752",
                  "icbc.cmis.util.GeneralViewOp[buildTreeWithInputValue]",
                  genMsg.getErrMsg("100751", inputTable + "|" + inputField),
                  genMsg.getErrMsg("100751", inputTable + "|" + inputField));
              if (ht_field.get("IS_NEED").equals("1"))
                setNeedMark(preCellNo, htLayoutDefine);
              String value = "";
              try {
                value = (String) ((Hashtable)recordData.get(inputTable)).get(inputField);
              }
              catch (Exception e) {
                throw new TranFailException(
                  "100754",
                  "icbc.cmis.util.GeneralViewOp[buildTreeWithInputValue]",
                  e.getMessage(),
                  genMsg.getErrMsg("100754", cellName));
              }

              trtdCell = new TrTdCell(cellNo, preCellNo, cellName, cellType, cellColspan, ht_field, value, isPlain);

            }
            //如果是text
            else if (cellType.equals("text")) {
              trtdCell = new TrTdCell(cellNo, preCellNo, cellName, cellType, cellColspan);
              trtdCell.syncAncestorRowSpan(htLayoutDefine);
            }
            else
              throw new TranFailException(
                "100753",
                "icbc.cmis.util.GeneralViewOp[buildTreeWithInputValue]",
                genMsg.getErrMsg("100753", cellType + "|" + cellNo),
                genMsg.getErrMsg("100753", cellType + "|" + cellNo));

            preCell.addChild(trtdCell);
            htLayoutDefine.put(cellNo, trtdCell);
          }
          else {
            TdCell tdCell = null;
            if (cellType.equals("input")) {
              Hashtable ht_field = (Hashtable) ((Hashtable)htFieldDefine.get(inputTable)).get(inputField);
              if (ht_field == null)
                throw new TranFailException(
                  "100752",
                  "icbc.cmis.util.GeneralViewOp[buildTreeWithInputValue]",
                  genMsg.getErrMsg("100751", inputTable + "|" + inputField),
                  genMsg.getErrMsg("100751", inputTable + "|" + inputField));
              if (ht_field.get("IS_NEED").equals("1"))
                setNeedMark(preCellNo, htLayoutDefine);
              String modifyPriv = (String)ht_field.get("MODIFY_PRIV");

              if (modifyPriv.equals("3") || modifyPriv.equals("2")) {
                String value = "";
                try {
                  value = (String) ((Hashtable)recordData.get(inputTable)).get(inputField);
                }
                catch (Exception e) {
                  throw new TranFailException(
                    "100754",
                    "icbc.cmis.util.GeneralViewOp[buildTreeWithInputValue]",
                    e.getMessage(),
                    genMsg.getErrMsg("100754", cellName));
                }
                tdCell = new TdCell(cellNo, preCellNo, cellName, cellType, cellColspan, ht_field, value, true);
              }
              else {
                String value = "";
                try {
                  value = (String) ((Hashtable)recordData.get(inputTable)).get(inputField);
                }
                catch (Exception e) {
                  throw new TranFailException(
                    "100754",
                    "icbc.cmis.util.GeneralViewOp[buildTreeWithInputValue]",
                    e.getMessage(),
                    genMsg.getErrMsg("100754", cellName));
                }
                tdCell = new TdCell(cellNo, preCellNo, cellName, cellType, cellColspan, ht_field, value, isPlain);
              }

            }
            else if (cellType.equals("text")) {
              tdCell = new TdCell(cellNo, preCellNo, cellName, cellType, cellColspan);
            }
            preCell.addChild(tdCell);
            htLayoutDefine.put(cellNo, tdCell);
          }
        }
        else
          throw new TranFailException(
            "100753",
            "icbc.cmis.util.GeneralViewOp[buildTreeWithInputValue]",
            genMsg.getErrMsg("100753", cellType + "|" + cellNo),
            genMsg.getErrMsg("100753", cellType + "|" + cellNo));
      }
    }
  }

  /**
   * 显示新增页面
   * @throws TranFailException
   */
  private void showInsertView() throws TranFailException {
    String formCode = getStringAt("formCode");
    String empCode = (String)this.getSessionData("EmployeeCode");
    String empName = (String)this.getSessionData("EmployeeName");
    String primaryInfo = getStringAt("primaryInfo"); //业务主键信息
    String isJieju=(String)this.getSessionData("isJieju");
	String Isquery=(String)this.getSessionData("Isquery");  //是否查询
	//	primaryInfo=isJieju+"|"+primaryInfo;
	String	querytj=this.getSessionData("querytj")==null ? "" :(String)this.getSessionData("querytj");
	String	jjapply=this.getSessionData("jjapply")==null ? "" :(String)this.getSessionData("jjapply");	
    try {
      if("2".equals(isJieju)||"6".equals(isJieju))
      {
		showjjlist();
      }
	  else
	  {
	  	
      dao = new GeneralViewDAO(this);
      
      //取字段定义
      Vector vecFieldDefine =null; 
      //取布局定义
      Vector vecLayoutDefine =null; 
      if("3".equals(isJieju)||"4".equals(isJieju) ||"7".equals(isJieju)) {     //借据新增页面
				vecFieldDefine =dao.getFieldDefine(formCode+"0");
				vecLayoutDefine =dao.getLayoutDefine(formCode+"0");
      
      }else{
		vecFieldDefine =dao.getFieldDefine(formCode);
		vecLayoutDefine =dao.getLayoutDefine(formCode);
      
      }
      
      
      //取联动定义
      String linkageInit = pickLinkageInit(vecFieldDefine);
      //取操作类名
      String extendsDMName = dao.getExtentsDMName(formCode);
      Class c = (Class)Class.forName(extendsDMName);
      GeneralViewBaseDM dm = (GeneralViewBaseDM)c.newInstance();
      dm.setDatabaseTool(this);
      Hashtable fixedData = null;
      Hashtable query_hs=null;
      try {
        fixedData = (Hashtable) ((Hashtable)this.getSessionData("tempDataCollection")).get("ht_hs");
        if(!querytj.equals("") && ("4".equals(isJieju)||"7".equals(isJieju))){
        
			Vector v=(Vector)this.getSessionData("jjlist_hs");
			query_hs=(Hashtable)((Vector)this.getSessionData("jjlist_hs")).get(Integer.parseInt(querytj.substring(9)));
			fixedData.put(querytj.substring(0,8),query_hs);
        }
        if ("3".equals(isJieju) || "4".equals(isJieju)){ //生成借据申请号和借据金额的判断以及合同金额
        	Hashtable jj_hs=(Hashtable) ((Hashtable)this.getSessionData("tempDataCollection")).get("jj_hs");
		String maxjjcode=(String)jj_hs.get("maxjjcode");	
        String sumDueMoney=	(String)jj_hs.get("sumDueMoney");
        	
        dm.getDueData(fixedData,maxjjcode,sumDueMoney,isJieju);
		//fixedData.p.put("JjInfo",jjhidden_hs);
        }
        
		 
      }
      catch (Exception e) {
        fixedData = dm.queryFix(formCode, primaryInfo);
        if(Isquery.equals("1"))
		fixedData= dm.query(formCode, primaryInfo);
      }
      if (fixedData == null) {
        fixedData = dm.queryFix(formCode, primaryInfo);
				if(Isquery.equals("1"))
						fixedData= dm.query(formCode, primaryInfo);
      }
      //build a tree 
      Cell root = new Cell("0000", null, "root", null);
      if (Isquery.equals("1") && !"3".equals(isJieju) && !"7".equals(isJieju)){
				if (!jjapply.equals("")&& "0".equals(isJieju))
						buildTreeWithInputValue(root, vecFieldDefine, vecLayoutDefine, fixedData, true);
						else	
				buildTreeWithInputValue(root, vecFieldDefine, vecLayoutDefine, fixedData, false);
      }
	  
	  else if ("7".equals(isJieju))
		buildTreeWithInputValue(root, vecFieldDefine, vecLayoutDefine, fixedData, true);
			else{
		
      buildTreeWithInput(root, vecFieldDefine, vecLayoutDefine, fixedData);
			}

      //输出必输项
      String script =
        "<input type=\"hidden\" id=\"needParamString\" name=\"needParamString\" value=\"" + pickNeedParams(vecFieldDefine) + "\" disabled>";
      //输出联动项
      if (jjapply.equals(""))
      script = script + "<input type=\"hidden\" id=\"linkageInit\" name=\"linkageInit\" value=\"" + linkageInit + "\" disabled>";
      else
			script = script + "<input type=\"hidden\" id=\"linkageInit\" name=\"linkageInit\"  disabled>";
      //输出隐藏项
      if ("4".equals(isJieju)||"3".equals(isJieju)){  //如果是借据修改和新增页面，隐藏借据主键信息,和不是主键的隐藏信息
      
		script = script + getjjHiddenField(vecFieldDefine, (Hashtable)this.getSessionData("tempDataCollection"));
		script = script + jjHiddenField(vecFieldDefine, (Hashtable)((Hashtable)this.getSessionData("tempDataCollection")).get("ht_hs"));
      }
			else
						script = script + pickHiddenField(vecFieldDefine, fixedData);
      //输出主键信息
	  if(Isquery.equals("1")){
	  
	  script = script + pickPrimaryField(vecFieldDefine, fixedData);

	  
	  }
      script = script + "<input type=\"hidden\" id=\"primaryInfo\" name=\"primaryInfo\" value=\"" + primaryInfo + "\" >";
      script = script + "<input type=\"hidden\" id=\"formCode\" name=\"formCode\" value=\"" + formCode + "\" >";
      //输出HTML
      
//		去掉session中的值(用于修改单笔借据时候的多余借据session信息)
					if(!querytj.equals("") && ("4".equals(isJieju)||"7".equals(isJieju))){
        
							 Vector v=(Vector)this.getSessionData("jjlist_hs");
			
							 query_hs=(Hashtable)((Vector)this.getSessionData("jjlist_hs")).get(Integer.parseInt(querytj.substring(9)));
							 fixedData.remove(querytj.substring(0,8));
								 }
			if ("3".equals(isJieju)){
			
				  fixedData.remove("JjInfo");
						 }	
						 
						 
		//	String ss=script + root.toHtmlStringWithInputValue();			 				 
		//	String ss=script+root.toHtmlStringWithInput();
			if(Isquery.equals("1") && !"3".equals(isJieju))
			setReplyPage("DirectOutput" + script + root.toHtmlStringWithInputValue());
			else
			setReplyPage("DirectOutput" + script + root.toHtmlStringWithInput());
				
			
	  }
    }
    catch (TranFailException te) {
      setReplyPage("DirectOutput" + toErrorString(te));
    }
    catch (Exception e) {
      setReplyPage("DirectOutput" + toErrorString(e));
    }
  }

  /**
   * 显示修改页面
   * @throws TranFailException
   */
  private void showModifyView() throws TranFailException {
    String formCode = getStringAt("formCode");
    String primaryInfo = getStringAt("primaryInfo");
    try {

      dao = new GeneralViewDAO(this);
      //取字段定义
      Vector vecFieldDefine = dao.getFieldDefine(formCode);
      //取布局定义
      Vector vecLayoutDefine = dao.getLayoutDefine(formCode);
      //取联动定义
      String linkageInit = pickLinkageInit(vecFieldDefine);
      //取操作类名
      String extendsDMName = dao.getExtentsDMName(formCode);

      Class c = (Class)Class.forName(extendsDMName);
      GeneralViewBaseDM dm = (GeneralViewBaseDM)c.newInstance();
      dm.setDatabaseTool(this);
      Hashtable data = null;
      try {
        data = (Hashtable) ((Hashtable)this.getSessionData("tempDataCollection")).get("ht_hs");
      }
      catch (Exception e) {
        data = dm.query(formCode, primaryInfo);
      }
      if (data == null) {
        data = dm.query(formCode, primaryInfo);
      }

      //build a tree
      Cell root = new Cell("0000", null, "root", null);
      buildTreeWithInputValue(root, vecFieldDefine, vecLayoutDefine, data, false);

      //      StringBuffer needParamBuffer = new StringBuffer();
      //      for (int i = 0; i < vecFieldDefine.size(); i++) {
      //        Hashtable ht = (Hashtable)vecFieldDefine.get(i);
      //        String isNeed = (String)ht.get("IS_NEED");
      //        if (isNeed.equals("1")) {
      //          needParamBuffer.append("|");
      //          needParamBuffer.append((String)ht.get("DISP_CODE"));
      //          needParamBuffer.append(",");
      //          needParamBuffer.append((String)ht.get("FIELD_NAME"));
      //        }
      //      }
      //输出必输项
      String script =
        "<input type=\"hidden\" id=\"needParamString\" name=\"needParamString\" value=\"" + pickNeedParams(vecFieldDefine) + "\" disabled>";
      //输出联动项
      script = script + "<input type=\"hidden\" id=\"linkageInit\" name=\"linkageInit\" value=\"" + linkageInit + "\" disabled>";
      //输出隐藏项
      script = script + pickHiddenField(vecFieldDefine, data);
      //输出主键信息
      script = script + pickPrimaryField(vecFieldDefine, data);
      //输出主键信息
      script = script + "<input type=\"hidden\" id=\"primaryInfo\" name=\"primaryInfo\" value=\"" + primaryInfo + "\" >";
      script = script + "<input type=\"hidden\" id=\"formCode\" name=\"formCode\" value=\"" + formCode + "\" >";
      setReplyPage("DirectOutput" + script + root.toHtmlStringWithInputValue());

    }
    catch (TranFailException te) {
      setReplyPage("DirectOutput" + toErrorString(te));
    }
    catch (Exception e) {
      setReplyPage("DirectOutput" + toErrorString(e));
    }
  }

  /**
   * 显示查询页面
   * @throws TranFailException
   */
  private void showQueryView() throws TranFailException {
  	String isJieju =this.getSessionData("isJieju").toString();
    String formCode = getStringAt("formCode");
    String primaryInfo = getStringAt("primaryInfo");
    String isSessionClear="";//该值是判断是否是已经点确定后再返回的页面
    String Apply_stage=this.getSessionData("Apply_stage").toString();//判断是申请还是台帐
	String Apply_customerCode=this.getSessionData("Apply_customerCode").toString();
	String Apply_contractID=this.getSessionData("Apply_contractID").toString();
    try {
      
      dao = new GeneralViewDAO(this);
      //取字段定义
      Vector vecFieldDefine = dao.getFieldDefine(formCode);
      //取布局定义
      Vector vecLayoutDefine = dao.getLayoutDefine(formCode);
      //取操作类名
      String extendsDMName = dao.getExtentsDMName(formCode);

      Class c = (Class)Class.forName(extendsDMName);
      GeneralViewBaseDM dm = (GeneralViewBaseDM)c.newInstance();
      dm.setDatabaseTool(this);
     // Hashtable data = dm.query(formCode, primaryInfo);
		 try{
			isSessionClear = this.getStringAt("isSessionClear");
                
				}catch (Exception e) {
			isSessionClear = "";
					}
			
			Hashtable tempDataCollection = null;
			
//						try {
//							tempDataCollection = (Hashtable)this.getSessionData("tempDataCollection");
//						}
//						catch (Exception e) {
//							tempDataCollection = new Hashtable();
//						}
						if (tempDataCollection == null || isSessionClear.equals("1"))
							tempDataCollection = dm.query(formCode, primaryInfo);	
			            Hashtable ht_hs=(Hashtable)tempDataCollection.get("ht_hs");
						Hashtable jj_hs=(Hashtable)tempDataCollection.get("jj_hs");
						Hashtable jjhash=(Hashtable)jj_hs.clone();
			           String sumDueMoney=dm.sumDueMoney(Apply_customerCode,Apply_contractID);
			           int jjmaxcode=0;
			           int j=0;
                       //进入借据列表
      	Vector vc=null;
//                try{
//				vc = (Vector)this.getSessionData("jjlist_hs");
//                
//                }catch (Exception e) {
//					vc = new Vector();
//			}
	 if (vc==null || vc.size()==0)	{
		vc=new Vector();
		Enumeration eu = jj_hs.keys();
		String str = "";
		Hashtable hs=new Hashtable();

		while (eu.hasMoreElements()) {
		if (Apply_stage.equals("0")){  //如果是申请
		
		str=((String) eu.nextElement()).substring(0,8);
		jjmaxcode=getmaxkey(jjhash);//最大序号
		
		hs=(Hashtable)jjhash.get(str+"#"+String.valueOf(jjmaxcode));
		vc.add(hs);
		jjhash.remove(str+"#"+String.valueOf(jjmaxcode));
		}else{
		
			str=(String)eu.nextElement();
			hs=(Hashtable)jjhash.get(str);
			vc.add(hs);
		
		}
		
		}
		
		
		Vector vv=new Vector();
		for (int i=vc.size()-1;i>=0;i--){
		
		vv.add((Hashtable)vc.get(i));
		}
		
		this.updateSessionData("jjlist_hs", vv);	
	   }
	   
	   //把最大序号和金额放到一个Hashtable中
		 if (Apply_stage.equals("0"))
		 jjmaxcode=getmaxkey(jj_hs);
		 
		 jj_hs.put("maxjjcode",String.valueOf(jjmaxcode+1));
		 if(sumDueMoney!=null)
		 jj_hs.put("sumDueMoney",sumDueMoney);
		 
		 
		 this.updateSessionData("tempDataCollection", tempDataCollection);	 
		
      
      Cell root = new Cell("0000", null, "root", null);
      
      buildTreeWithInputValue(root, vecFieldDefine, vecLayoutDefine, ht_hs, true);

      String script = pickHiddenField(vecFieldDefine, ht_hs);
      String ss=script + root.toHtmlStringWithInputValue();
      setReplyPage("DirectOutput" + script + root.toHtmlStringWithInputValue());

		

    }
    catch (TranFailException te) {
      setReplyPage("DirectOutput" + toErrorString(te));
    }
    catch (Exception e) {
      setReplyPage("DirectOutput" + toErrorString(e));
    }
  }
  
	/**
	 * 取得一个Hashtable中最大的键值（借据Hashtable中）
	 */
	private int getmaxkey(Hashtable ht) {
		        int maxcode=0;
		        int j=0;
		        Enumeration eu = ht.keys();
				String str = "";
			//	Hashtable hs=new Hashtable();
				while (eu.hasMoreElements()) {
		
				str=(String) eu.nextElement();
				if(str.indexOf("#")!=-1){
				
				maxcode=Integer.parseInt(str.substring(9));
		        if(maxcode>j)
		        maxcode=maxcode;
		        else
		        maxcode=j;
		        
		        j=maxcode;
				}
				}
		
		return maxcode;
	}




  /**
   * 合同新增
   * @throws TranFailException
   */
  private void addArchive() throws TranFailException {
    String formCode = getStringAt("formCode");
    String primaryInfo = getStringAt("primaryInfo");
    try {
			dao = new GeneralViewDAO(this);
			Vector vecFieldDefine = dao.getFieldDefine(formCode);
			Hashtable params = new Hashtable();
			for (int i = 0; i < vecFieldDefine.size(); i++) {
				String table = (String) ((Hashtable)vecFieldDefine.get(i)).get("TABLE_CODE");
				String field = (String) ((Hashtable)vecFieldDefine.get(i)).get("FIELD_CODE");
				String dispCode = (String) ((Hashtable)vecFieldDefine.get(i)).get("DISP_CODE");
				Hashtable item = (Hashtable)params.get(table);
				if (item == null) {
					item = new Hashtable();
					params.put(table, item);
				}
				try {
					String value = getStringAt(dispCode);
					item.put(field, value);
				}
				catch (Exception e) {
					throw new TranFailException(
						"100758",
						"icbc.cmis.util.GeneralViewOp[saveTempData]",
						genMsg.getErrMsg("100758", table + "|" + field),
						genMsg.getErrMsg("100758", table + "|" + field));
				}

			}
			Hashtable tempDataCollection = null;
			Hashtable  jj_hs=null;//借据的hashtable
			
			try {
				tempDataCollection = (Hashtable)this.getSessionData("tempDataCollection");
			}
			catch (Exception e) {
				tempDataCollection = new Hashtable();
			}
			if (tempDataCollection == null)
				tempDataCollection = new Hashtable();

			tempDataCollection.put("ht_hs", params);
			try {
      
			jj_hs=(Hashtable)tempDataCollection.get("jj_hs");//获得借据下的所有信息
			}catch (Exception e) {
				jj_hs=new Hashtable();
      
			}
			if(jj_hs==null)
			jj_hs=new Hashtable();
      
			tempDataCollection.put("jj_hs", jj_hs);
			this.updateSessionData("tempDataCollection", tempDataCollection);

      //取操作类名
      String extendsDMName = dao.getExtentsDMName(formCode);

      Class c = (Class)Class.forName(extendsDMName);
      GeneralViewBaseDM dm = (GeneralViewBaseDM)c.newInstance();
      dm.setDatabaseTool(this);

      //新增
      String canDo[] = dm.canInsert(formCode, params, primaryInfo);
      if (canDo[0].equals("0"))
        dm.insert(formCode, tempDataCollection, primaryInfo);
      else
        throw new TranFailException(
          "100755",
          "icbc.cmis.util.GeneralViewOp[addArchive]",
          genMsg.getErrMsg(canDo[1], canDo[2]),
          genMsg.getErrMsg("100755"));

      String[] retInfo = dm.getInsertReturn(params, primaryInfo);

      this.setFieldValue("okTitle", retInfo[0]);
      this.setFieldValue("okMsg", retInfo[1]);
      this.setFieldValue("okReturn", retInfo[2]);
      this.setOperationDataToSession();
      this.setReplyPage("/ok.jsp");

      //清除临时值，如果存在的话
      try {
        tempDataCollection = (Hashtable)this.getSessionData("tempDataCollection");
        if (tempDataCollection != null)
          tempDataCollection.remove(formCode);
        this.updateSessionData("tempDataCollection", tempDataCollection);
      }
      catch (Exception e) {}
    }
    catch (TranFailException te) {
      throw te;
    }
    catch (Exception e) {
      throw new TranFailException("100755", "icbc.cmis.util.GeneralViewOp[addArchive]", e.getMessage(), genMsg.getErrMsg("100755"));
    }
  }

	/**
		* 借据单笔新增
		* @throws TranFailException
		*/
	 private void addjjArchive() throws TranFailException {
		 String formCode = getStringAt("formCode");
		 String primaryInfo = getStringAt("primaryInfo");
		 try {
			 dao = new GeneralViewDAO(this);
			Vector v_result = dao.getdueDefine(formCode+"0");
			String jjfield_code=(String)((Hashtable)v_result.get(0)).get("field_name");  //取出借据对应的字段
			String table=(String)((Hashtable)v_result.get(0)).get("table_name"); 
			 Vector vecFieldDefine = dao.getFieldDefine(formCode+"0");
			 String jjvalue="";//借据的值，用来起借据session的第二层名字
			 String jj_amort="";
			 //取context中的数据
			 Hashtable params = new Hashtable();
			 
			 for (int i = 0; i < vecFieldDefine.size(); i++) {
				// String table = (String) ((Hashtable)vecFieldDefine.get(i)).get("TABLE_CODE");
				 String field = (String) ((Hashtable)vecFieldDefine.get(i)).get("FIELD_CODE");
				 String restricts =(String) ((Hashtable)vecFieldDefine.get(i)).get("RESTRICTS");
				 String dispCode = (String) ((Hashtable)vecFieldDefine.get(i)).get("DISP_CODE");
				 if(dispCode.equals(jjfield_code))
				 jjvalue=getStringAt(dispCode);//获得借据的值
                 
                 
				 try {
					 String value = getStringAt(dispCode);
					 
					 if(restricts.equals("1"))
					  jj_amort=value;
					params.put(field, value);
				 }
				 catch (Exception e) {
					 throw new TranFailException(
						 "100758",
						 "icbc.cmis.util.GeneralViewOp[addArchive]",
						 genMsg.getErrMsg("100758", table + "|" + field),
						 genMsg.getErrMsg("100758", table + "|" + field));
				 }

			 }
			 params.put("dowhat","add");//增加操作动作
			 params.put("barFlag","1");//表示的是该借据是否是已经存在于数据库中的数据
			 //保存到借据session 第二层中
			Hashtable tempDataCollection = null;
			 try {
			 tempDataCollection = (Hashtable)this.getSessionData("tempDataCollection");
			 if (tempDataCollection != null){
			 
				Hashtable  jj_hs=(Hashtable)tempDataCollection.get("jj_hs");//获得借据下的所有信息
				if(jj_hs==null)
				jj_hs=new Hashtable();
				jj_hs.put(table+"#"+jjvalue,params);
				String maxjjcode=(String)jj_hs.get("maxjjcode");//获得最大序号
				String sumDueMoney=(String)jj_hs.get("sumDueMoney");//获得借据最大金额

				double aa=Integer.parseInt(jj_amort.substring(0,jj_amort.indexOf('.')))+Double.parseDouble(jj_amort.substring(jj_amort.indexOf('.')+1))/100;
				
				 String sumDueMoney1=String.valueOf(Double.parseDouble(sumDueMoney)+aa);
				jj_hs.put("sumDueMoney",sumDueMoney1); //把借据最大金额放到session中 
				jj_hs.put("maxjjcode",String.valueOf(Integer.parseInt(maxjjcode)+1)); //把最大序号放到session中
				tempDataCollection.put("jj_hs",jj_hs);
				
					
			 }
								 
			this.updateSessionData("tempDataCollection", tempDataCollection);
				 }
				catch (Exception e) {
					throw e;
				}
				
		//保存数据到借据列表session中 		
		Vector jjlist_hs=null;
		try {
			jjlist_hs = (Vector)this.getSessionData("jjlist_hs");
					}
			catch (Exception e) {
			 jjlist_hs = new Vector();
					}
					if (jjlist_hs == null)
			jjlist_hs = new Vector();

			jjlist_hs.add(params);
			this.updateSessionData("jjlist_hs", jjlist_hs);	

			 //取操作类名
			 String extendsDMName = dao.getExtentsDMName(formCode);

			 Class c = (Class)Class.forName(extendsDMName);
			 GeneralViewBaseDM dm = (GeneralViewBaseDM)c.newInstance();
			 dm.setDatabaseTool(this);

		//	 String[] retInfo = dm.getInsertReturn(params, primaryInfo);
             String returnurl=
			baseWebPath
				+ "/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.util.GeneralContentOp&opDataUnclear=true&isJieju=2";
			 this.setFieldValue("okTitle", "成功");
			 this.setFieldValue("okMsg", "操作成功！");
			 this.setFieldValue("okReturn", returnurl);
			 this.setOperationDataToSession();
			 this.setReplyPage("/ok.jsp");

	
		 }
		 catch (TranFailException te) {
			 throw te;
		 }
		 catch (Exception e) {
			 throw new TranFailException("100755", "icbc.cmis.util.GeneralViewOp[addArchive]", e.getMessage(), genMsg.getErrMsg("100755"));
		 }
	 }


	/**
			* 借据单笔修改
			* @throws TranFailException
			*/
		 private void modjjArchive() throws TranFailException {
			 String formCode = getStringAt("formCode");
			 String primaryInfo = getStringAt("primaryInfo");
			 try {
				 dao = new GeneralViewDAO(this);
				Vector v_result = dao.getdueDefine(formCode+"0");
				String jjfield_code=(String)((Hashtable)v_result.get(0)).get("field_name");  //取出借据对应的字段
				String table=(String)((Hashtable)v_result.get(0)).get("table_name"); 
				 Vector vecFieldDefine = dao.getFieldDefine(formCode+"0");
				 String jjvalue="";//借据的值，用来起借据session的第二层名字
				 String jj_amort="";
				 String amort_name="";//金额对应的字段名称
				 //取context中的数据
				 Hashtable params = new Hashtable();
			 
				 for (int i = 0; i < vecFieldDefine.size(); i++) {
					// String table = (String) ((Hashtable)vecFieldDefine.get(i)).get("TABLE_CODE");
					 String field = (String) ((Hashtable)vecFieldDefine.get(i)).get("FIELD_CODE");
					 String restricts =(String) ((Hashtable)vecFieldDefine.get(i)).get("RESTRICTS");
					 String dispCode = (String) ((Hashtable)vecFieldDefine.get(i)).get("DISP_CODE");
					 if(dispCode.equals(jjfield_code))
					 jjvalue=getStringAt(dispCode);//获得借据的值

					 try {
						 String value = getStringAt(dispCode);
						params.put(field, value);
						if(restricts.equals("1")){
							jj_amort=value;
							amort_name=dispCode;
						}
						
					 }
					 catch (Exception e) {
						 throw new TranFailException(
							 "100758",
							 "icbc.cmis.util.GeneralViewOp[addArchive]",
							 genMsg.getErrMsg("100758", table + "|" + field),
							 genMsg.getErrMsg("100758", table + "|" + field));
					 }

				 }
				 params.put("dowhat","mod");//增加操作动作
				 //保存到借据session 第二层中
				Hashtable tempDataCollection = null;
				 try {
				 tempDataCollection = (Hashtable)this.getSessionData("tempDataCollection");
				 if (tempDataCollection != null){
			 
					Hashtable  jj_hs=(Hashtable)tempDataCollection.get("jj_hs");//获得借据下的所有信息
					if(jj_hs==null)
					jj_hs=new Hashtable();
					else  //判断借据是否是数据库中的数据
					{
					String ss=(String)((Hashtable)jj_hs.get(table+"#"+jjvalue)).get("barFlag");
					params.put("barFlag",ss);
					if(ss.equals("1")){
						params.put("dowhat","add");//增加操作动作
						params.put("barFlag","1");//表示的是该借据是否是已经存在于数据库中的数据
					}
					
					}
					
					String yl_amort=(String)((Hashtable)jj_hs.get(table+"#"+jjvalue)).get(amort_name);//获得修改前的金额
					jj_hs.put(table+"#"+jjvalue,params);
					double aa=Integer.parseInt(jj_amort.substring(0,jj_amort.indexOf('.')))+Double.parseDouble(jj_amort.substring(jj_amort.indexOf('.')+1))/100;
					
					String sumDueMoney=(String)jj_hs.get("sumDueMoney");//获得借据最大金额
					String sumDueMoney1=String.valueOf(Double.parseDouble(sumDueMoney)-Double.parseDouble(yl_amort)+aa);
					jj_hs.put("sumDueMoney",sumDueMoney1); //把借据最大金额放到session中 
					
					
					tempDataCollection.put("jj_hs",jj_hs);
			 
				 }
								 
				this.updateSessionData("tempDataCollection", tempDataCollection);
					 }
					catch (Exception e) {}
				
			//保存数据到借据列表session中 		
			Vector jjlist_hs=null;
			try {
				jjlist_hs = (Vector)this.getSessionData("jjlist_hs");
						}
				catch (Exception e) {
				 jjlist_hs = new Vector();
						}
						if (jjlist_hs == null)
				jjlist_hs = new Vector();
				
				//更新对应的session vector中的借据列表
				String	querytj=this.getSessionData("querytj")==null ? "" :(String)this.getSessionData("querytj");		
			   Hashtable	query_hs=(Hashtable)((Vector)this.getSessionData("jjlist_hs")).get(Integer.parseInt(querytj.substring(9)));
				jjlist_hs.remove(Integer.parseInt(querytj.substring(9)));
				
				jjlist_hs.add(Integer.parseInt(querytj.substring(9)),params);
				this.updateSessionData("jjlist_hs", jjlist_hs);	

							 String returnurl=
				baseWebPath
					+ "/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.util.GeneralContentOp&opDataUnclear=true&isJieju=2";
				 this.setFieldValue("okTitle", "成功");
				 this.setFieldValue("okMsg", "借据修改成功！");
				 this.setFieldValue("okReturn", returnurl);
				 this.setOperationDataToSession();
				 this.setReplyPage("/ok.jsp");

	
			 }
			 catch (TranFailException te) {
				 throw te;
			 }
			 catch (Exception e) {
				 throw new TranFailException("100755", "icbc.cmis.util.GeneralViewOp[addArchive]", e.getMessage(), genMsg.getErrMsg("100755"));
			 }
		 }

	/**
				* 借据单笔删除
				* @throws TranFailException
				*/
			 private void deljjArchive() throws TranFailException {
				 String formCode = getStringAt("formCode");
				 String primaryInfo = getStringAt("primaryInfo");
				 try {
					 dao = new GeneralViewDAO(this);
					Vector v_result = dao.getdueDefine(formCode+"0");
					String jjfield_code=(String)((Hashtable)v_result.get(0)).get("field_name");  //取出借据对应的字段
					String table=(String)((Hashtable)v_result.get(0)).get("table_name"); 
					 Vector vecFieldDefine = dao.getFieldDefine(formCode+"0");
					 String jjvalue="";//借据的值，用来起借据session的第二层名字
					String jj_amort="";
					String amort_name="";//金额对应的字段名称
					 //取context中的数据
					 Hashtable params = new Hashtable();
			 
					 for (int i = 0; i < vecFieldDefine.size(); i++) {
						// String table = (String) ((Hashtable)vecFieldDefine.get(i)).get("TABLE_CODE");
						 String field = (String) ((Hashtable)vecFieldDefine.get(i)).get("FIELD_CODE");
						String restricts =(String) ((Hashtable)vecFieldDefine.get(i)).get("RESTRICTS");
						 String dispCode = (String) ((Hashtable)vecFieldDefine.get(i)).get("DISP_CODE");
						 if(dispCode.equals(jjfield_code))
						 jjvalue=getStringAt(dispCode);//获得借据的值

						 try {
							 String value = getStringAt(dispCode);
							params.put(field, value);
							
							if(restricts.equals("1")){
							jj_amort=value;
							amort_name=dispCode;
													}	
						 }
						 catch (Exception e) {
							 throw new TranFailException(
								 "100758",
								 "icbc.cmis.util.GeneralViewOp[addArchive]",
								 genMsg.getErrMsg("100758", table + "|" + field),
								 genMsg.getErrMsg("100758", table + "|" + field));
						 }

					 }
					 params.put("dowhat","del");//增加操作动作
					 //保存到借据session 第二层中
					Hashtable tempDataCollection = null;
					 try {
					 tempDataCollection = (Hashtable)this.getSessionData("tempDataCollection");
					 if (tempDataCollection != null){
			 
						Hashtable  jj_hs=(Hashtable)tempDataCollection.get("jj_hs");//获得借据下的所有信息
						String yl_amort=(String)((Hashtable)jj_hs.get(table+"#"+jjvalue)).get(amort_name);//获得删除前的金额
						if(jj_hs==null){
							jj_hs=new Hashtable();
							jj_hs.put(table+"#"+jjvalue,params);
						}
						
						else{
						
							String ss=(String)((Hashtable)jj_hs.get(table+"#"+jjvalue)).get("barFlag");//判断该借据是否是数据库中的数据
						  if (ss.equals("1"))
						  jj_hs.remove(table+"#"+jjvalue);
						  else
							jj_hs.put(table+"#"+jjvalue,params);
						}
						
//						String maxjjcode=(String)jj_hs.get("maxjjcode");//获得最大序号
//						if(jjvalue.equals(String.valueOf(Integer.parseInt(maxjjcode)-1))){//如果删除的是最大一笔借据，那么把最大的一笔放到session中
//						
//						jj_hs.put("maxjjcode",String.valueOf(Integer.parseInt(jjvalue)+1)); 
//						
//						
//						}
						
						String sumDueMoney=(String)jj_hs.get("sumDueMoney");//获得借据最大金额
						String sumDueMoney1=String.valueOf(Double.parseDouble(sumDueMoney)-Double.parseDouble(yl_amort));
						jj_hs.put("sumDueMoney",sumDueMoney1); //把借据最大金额放到session中 
						
						tempDataCollection.put("jj_hs",jj_hs);
			 
					 }
								 
					this.updateSessionData("tempDataCollection", tempDataCollection);
						 }
						catch (Exception e) {}
				
				//保存数据到借据列表session中 		
				Vector jjlist_hs=null;
				try {
					jjlist_hs = (Vector)this.getSessionData("jjlist_hs");
							}
					catch (Exception e) {
					 jjlist_hs = new Vector();
							}
							if (jjlist_hs == null)
					jjlist_hs = new Vector();
				
					//删除对应的session vector中的借据列表
					String	querytj=this.getSessionData("querytj")==null ? "" :(String)this.getSessionData("querytj");		
					 Hashtable	query_hs=(Hashtable)((Vector)this.getSessionData("jjlist_hs")).get(Integer.parseInt(querytj.substring(9)));
					jjlist_hs.remove(Integer.parseInt(querytj.substring(9)));
					this.updateSessionData("jjlist_hs", jjlist_hs);	

								 String returnurl=
					baseWebPath
						+ "/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.util.GeneralContentOp&opDataUnclear=true&isJieju=2";
					 this.setFieldValue("okTitle", "成功");
					 this.setFieldValue("okMsg", "借据删除成功！");
					 this.setFieldValue("okReturn", returnurl);
					 this.setOperationDataToSession();
					 this.setReplyPage("/ok.jsp");

	
				 }
				 catch (TranFailException te) {
					 throw te;
				 }
				 catch (Exception e) {
					 throw new TranFailException("100755", "icbc.cmis.util.GeneralViewOp[addArchive]", e.getMessage(), genMsg.getErrMsg("100755"));
				 }
			 }
  /**
   * 修改
   * @throws TranFailException
   */
  private void modifyArchive() throws TranFailException {
    String formCode = getStringAt("formCode");
    String primaryInfo = getStringAt("primaryInfo");
    try {
      dao = new GeneralViewDAO(this);
      Vector vecFieldDefine = dao.getFieldDefine(formCode);
      Hashtable params = new Hashtable();
      Hashtable primaryParams = new Hashtable();
      for (int i = 0; i < vecFieldDefine.size(); i++) {
        String table = (String) ((Hashtable)vecFieldDefine.get(i)).get("TABLE_CODE");
        String field = (String) ((Hashtable)vecFieldDefine.get(i)).get("FIELD_CODE");
        String isPrimary = (String) ((Hashtable)vecFieldDefine.get(i)).get("IS_PRIMARY");
        String dispCode = (String) ((Hashtable)vecFieldDefine.get(i)).get("DISP_CODE");
        Hashtable item = (Hashtable)params.get(table);
        if (item == null) {
          item = new Hashtable();
          params.put(table, item);
        }
        try {
          String value = getStringAt(dispCode);
          item.put(field, value);
        }
        catch (Exception e) {
          throw new TranFailException(
            "100758",
            "icbc.cmis.util.GeneralViewOp[modifyArchive]",
            genMsg.getErrMsg("100758", table + "|" + field),
            genMsg.getErrMsg("100758", table + "|" + field));
        }
        if (isPrimary.equals("1")) {
          Hashtable pItem = (Hashtable)primaryParams.get(table);
          if (pItem == null) {
            pItem = new Hashtable();
            primaryParams.put(table, pItem);
          }

          String pValue = getStringAt(dispCode + "_old");
          primaryParams.put(field, pValue);
        }
      }
      
         Hashtable tempDataCollection=null;
			try {
							tempDataCollection = (Hashtable)this.getSessionData("tempDataCollection");
						}
						catch (Exception e) {
							tempDataCollection = new Hashtable();
						}
						if (tempDataCollection == null)
							tempDataCollection = new Hashtable();

						tempDataCollection.put("ht_hs", params);
      String extendsDMName = dao.getExtentsDMName(formCode);

      Class c = (Class)Class.forName(extendsDMName);
      GeneralViewBaseDM dm = (GeneralViewBaseDM)c.newInstance();
      dm.setDatabaseTool(this);
            Hashtable jj_key=new Hashtable();
			Hashtable total=dm.query(formCode, primaryInfo);//获得总的合同借据信息
			Hashtable jj_hstable=(Hashtable)total.get("jj_hs");
			if (jj_hstable.size()>0){
				Enumeration eu = jj_hstable.keys();
						String str = "";
						Hashtable hs=new Hashtable();
						while (eu.hasMoreElements()) {
		                Hashtable  hh=new Hashtable();
						str=(String) eu.nextElement();
						hs=(Hashtable)jj_hstable.get(str);
						Vector vecFieldDefine1 = dao.getFieldDefine(formCode+"0");//得到借据表单的信息	
						for (int i = 0; i < vecFieldDefine1.size(); i++) {
							String table = (String) ((Hashtable)vecFieldDefine1.get(i)).get("TABLE_CODE");
							String field = (String) ((Hashtable)vecFieldDefine1.get(i)).get("FIELD_CODE");
							String isPrimary = (String) ((Hashtable)vecFieldDefine1.get(i)).get("IS_PRIMARY");
							String dispCode = (String) ((Hashtable)vecFieldDefine1.get(i)).get("DISP_CODE");		
							
						if (isPrimary.equals("1"))
						hh.put(dispCode,hs.get(dispCode));		
						}
						jj_key.put(str,hh);
						}   
				
			}
	 Hashtable  key_hs=new Hashtable();
	 		
	 key_hs.put("jj_key",jj_key);
	 key_hs.put("ht_key",primaryParams);	
			
      String[] canDo = dm.canModify(formCode, params, primaryParams, primaryInfo);
      if (canDo[0].equals("0"))
        dm.modify(formCode, tempDataCollection, key_hs, primaryInfo);
      else
        throw new TranFailException(
          "100756",
          "icbc.cmis.util.GeneralViewOp[modifyArchive]",
          genMsg.getErrMsg(canDo[1], canDo[2]),
          genMsg.getErrMsg("100756"));

      String[] retInfo = dm.getModifyReturn(params, primaryInfo);

      this.setFieldValue("okTitle", retInfo[0]);
      this.setFieldValue("okMsg", retInfo[1]);
      this.setFieldValue("okReturn", retInfo[2]);
      this.setOperationDataToSession();
      this.setReplyPage("/ok.jsp");
    }
    catch (TranFailException te) {
      throw te;
    }
    catch (Exception e) {
      throw new TranFailException("100756", "icbc.cmis.util.GeneralViewOp[modifyArchive]", e.getMessage(), genMsg.getErrMsg("100756"));
    }
  }

  /**
   * 删除
   * @throws TranFailException
   */
  private void deleteArchive() throws TranFailException {
    String formCode = getStringAt("formCode");
    String primaryInfo = getStringAt("primaryInfo");
    try {
      dao = new GeneralViewDAO(this);
      Vector vecFieldDefine = dao.getFieldDefine(formCode);
      Hashtable primaryParams = new Hashtable();
      

      String extendsDMName = dao.getExtentsDMName(formCode);

      Class c = (Class)Class.forName(extendsDMName);
      GeneralViewBaseDM dm = (GeneralViewBaseDM)c.newInstance();
      dm.setDatabaseTool(this);
			
									try {
										primaryParams = (Hashtable)this.getSessionData("tempDataCollection");
									}
									catch (Exception e) {
										primaryParams = new Hashtable();
									}
									if (primaryParams == null)
			primaryParams = dm.query(formCode, primaryInfo);
			
      String[] canDo = dm.canDelete(formCode, primaryParams, primaryInfo);
      if (canDo[0].equals("0"))
        dm.delete(formCode, primaryParams, primaryInfo);
      else
        throw new TranFailException(
          "100757",
          "icbc.cmis.util.GeneralViewOp[deleteArchive]",
          genMsg.getErrMsg(canDo[1], canDo[2]),
          genMsg.getErrMsg("100757"));

      String[] retInfo = dm.getDeleteReturn(primaryParams, primaryInfo);
      this.setFieldValue("okTitle", retInfo[0]);
      this.setFieldValue("okMsg", retInfo[1]);
      this.setFieldValue("okReturn", retInfo[2]);
      this.setOperationDataToSession();
      this.setReplyPage("/ok.jsp");

      Hashtable tempDataCollection = null;
      try {
        tempDataCollection = (Hashtable)this.getSessionData("tempDataCollection");
        if (tempDataCollection != null){
        
          tempDataCollection.remove("ht_hs");
		  tempDataCollection.remove("jj_hs");
				}
        this.updateSessionData("tempDataCollection", tempDataCollection);
      }
      catch (Exception e) {}
    }
    catch (TranFailException te) {
      throw te;
    }
    catch (Exception e) {
      throw new TranFailException("100757", "icbc.cmis.util.GeneralViewOp[deleteArchive]", e.getMessage(), genMsg.getErrMsg("100757"));
    }
  }

  /**
   * 保存临时值
   * @throws TranFailException
   */
  private void saveTempData() throws TranFailException {
    String formCode = getStringAt("formCode");
    try {
      dao = new GeneralViewDAO(this);
      Vector vecFieldDefine = dao.getFieldDefine(formCode);
      Hashtable params = new Hashtable();
      for (int i = 0; i < vecFieldDefine.size(); i++) {
        String table = (String) ((Hashtable)vecFieldDefine.get(i)).get("TABLE_CODE");
        String field = (String) ((Hashtable)vecFieldDefine.get(i)).get("FIELD_CODE");
        String dispCode = (String) ((Hashtable)vecFieldDefine.get(i)).get("DISP_CODE");
        Hashtable item = (Hashtable)params.get(table);
        if (item == null) {
          item = new Hashtable();
          params.put(table, item);
        }
        try {
          String value = getStringAt(dispCode);
          item.put(field, value);
        }
        catch (Exception e) {
          throw new TranFailException(
            "100758",
            "icbc.cmis.util.GeneralViewOp[saveTempData]",
            genMsg.getErrMsg("100758", table + "|" + field),
            genMsg.getErrMsg("100758", table + "|" + field));
        }

      }
      Hashtable tempDataCollection = null;
			Hashtable  jj_hs=null;//借据的hashtable
      try {
        tempDataCollection = (Hashtable)this.getSessionData("tempDataCollection");
      }
      catch (Exception e) {
        tempDataCollection = new Hashtable();
      }
      if (tempDataCollection == null)
        tempDataCollection = new Hashtable();

      tempDataCollection.put("ht_hs", params);
      try {
      
		  jj_hs=(Hashtable)tempDataCollection.get("jj_hs");//获得借据下的所有信息
      }catch (Exception e) {
				jj_hs=new Hashtable();
      
      }
			if(jj_hs==null)
			jj_hs=new Hashtable();
      String maxjjcode=(String)jj_hs.get("maxjjcode");
      if (maxjjcode==null)   //取得最大借据号
      maxjjcode="1";
			String sumDueMoney=(String)jj_hs.get("sumDueMoney");
			if (sumDueMoney==null)   //取得最大借据号
			sumDueMoney="0";		
      jj_hs.put("maxjjcode",maxjjcode);
			jj_hs.put("sumDueMoney",sumDueMoney);
			tempDataCollection.put("jj_hs", jj_hs);
      this.updateSessionData("tempDataCollection", tempDataCollection);

      this.setOperationDataToSession();
      this.setReplyPage("DirectOutput<?xml version=\"1.0\" encoding=\"GBK\"?><info>ok</info>");
    }
    catch (Exception e) {
      this.setReplyPage("DirectOutput<?xml version=\"1.0\" encoding=\"GBK\"?><error>" + e.getMessage() + "</error>");
    }
  }

  /**
   * 清楚临时值
   * @throws TranFailException
   */
  private void clearTempData() throws TranFailException {
    try {
      this.removeSessionData("tempDataCollection");
      this.removeSessionData("jjlist_hs");
    }
    catch (Exception e) {}

    setReplyPage("DirectOutput<?xml version=\"1.0\" encoding=\"GBK\"?><info>ok</info>");

  }
  
  
	/**
		* 显示列表查询页面
		* @throws TranFailException
		*/
	 private void showjjlist() throws TranFailException {
		java.text.DecimalFormat df = new java.text.DecimalFormat();
				df.applyLocalizedPattern("#,##0.00");
		 String formCode = getStringAt("formCode");
		
		 String empCode = (String)this.getSessionData("EmployeeCode");
		 String empName = (String)this.getSessionData("EmployeeName");
		 String primaryInfo = getStringAt("primaryInfo"); //业务主键信息
		 try {

			 dao = new GeneralViewDAO(this);
			 //取字段定义
//			 Vector vecFieldDefine = dao.getFieldDefine(formCode);
//
//			 //取操作类名
			 String extendsDMName = dao.getExtentsDMName(formCode);
			 Class c = (Class)Class.forName(extendsDMName);
			 GeneralViewBaseDM dm = (GeneralViewBaseDM)c.newInstance();
			 dm.setDatabaseTool(this);
			 Vector fixedData = null;
			Vector v_result=null;
			 try {
				 fixedData = this.getSessionData("jjlist_hs")==null ? null :(Vector)this.getSessionData("jjlist_hs");
			 }
			 catch (Exception e) {
				 fixedData = new Vector();
			 }
//			 if (fixedData == null) {
//				v_result = dao.getdueDefine(formCode+"0");
//			 }
			String creattable="";
			v_result = dao.getdueDefine(formCode+"0");
			  creattable="<table border='0' cellspacing='1' cellpadding='0' align='center' width='100%'><tr class=tr1><td align='center'>序号</td>";
		     for (int i=0;i<v_result.size();i++){
					creattable+="<td align='center'>"+(String)((Hashtable)v_result.get(i)).get("text_name")+"</td>";
		     
		     }
			creattable=creattable+"</tr>";
			int x=1;
			if(fixedData!=null && fixedData.size()>0){
				for (int i=0;i<fixedData.size();i++){
					
						creattable+="<tr onmouseover=\"javascript:this.bgColor='#e4e4e4';\"><td align='center'> "+(x++)+"</td>";
				boolean tempFlag=false;  //超级链接的判断
				for (int j=0;j<v_result.size();j++){
			
					if(tempFlag)
						{
						if(((String)((Hashtable)v_result.get(j)).get("amort_flag")).equals("1")){
						double f_mgAmount = Double.parseDouble((String)((Hashtable)fixedData.get(i)).get((String)((Hashtable)v_result.get(j)).get("field_name")));
						String balance = df.format(f_mgAmount);
						creattable+="<td align='center'>"+balance+"</td>";
						
						}else{
						
						creattable+="<td align='center'>"+(String)((Hashtable)fixedData.get(i)).get((String)((Hashtable)v_result.get(j)).get("field_name"))+"</td>";
						}
						}
					else
					{
						creattable+="<td align='center'> <a href=\"javascript:gotoModjj('"+i+"','"+(String)((Hashtable)v_result.get(j)).get("table_name")+"');\">"+(String)((Hashtable)fixedData.get(i)).get((String)((Hashtable)v_result.get(j)).get("field_name"))+"</a></td>";
						tempFlag=true;
					}  
			
				}
				creattable=creattable+"</tr>";
					}
						
			}
				
			creattable=creattable+"</table>";
			String script="";
			 //输出隐藏项
			// script = script + pickHiddenField(vecFieldDefine, fixedData);
			 //输出主键信息
			 script = script + "<input type=\"hidden\" id=\"primaryInfo\" name=\"primaryInfo\" value=\"" + primaryInfo + "\" >";
			 script = script + "<input type=\"hidden\" id=\"linkageInit\" name=\"linkageInit\" value=\"\" >";
			 //输出HTML
			 setReplyPage("DirectOutput" + script+creattable );
		 }
		 catch (TranFailException te) {
			 setReplyPage("DirectOutput" + toErrorString(te));
		 }
		 catch (Exception e) {
			 setReplyPage("DirectOutput" + toErrorString(e));
		 }
	 }
  

  /**
   * 格式化结构
   * @param input 输入结构Vector
   * @return Hashtable
   */
  Hashtable reformatVector(Vector input) {
    Hashtable ht = new Hashtable();
    for (int i = 0; i < input.size(); i++) {
      Hashtable tmp = (Hashtable)input.get(i);
      String tableCode = (String)tmp.get("TABLE_CODE");
      Hashtable item = (Hashtable)ht.get(tableCode);
      if (item == null) {
        item = new Hashtable();
        ht.put(tableCode, item);
      }
      item.put(tmp.get("FIELD_CODE"), tmp);
    }
    return ht;
  }

  /**
   * 将异常信息转成String输出
   * @param e 异常
   * @return String
   */
  private String toErrorString(Exception e) {
    String errorDisp = null;
    String error = null;
    String location = null;
    String code = null;
    if (e instanceof TranFailException) {
      TranFailException te = (TranFailException)e;
      errorDisp = te.getDisplayMessage();
      error = te.getErrorMsg();
      location = te.getErrorLocation();
      code = te.getErrorCode();
    }
    else {
      errorDisp = e.getMessage();
      error = e.getMessage();
      location = "icbc.cmis.util.GeneralViewOp";
      code = "GeneralViewOp";
    }

    return "<table width='100%' border='0' cellspacing='0' cellpadding='0'>"
      + "<tr>"
      + "<td width='80%'>"
      + "<br>"
      + errorDisp
      + "<br>"
      + location
      + "<br>"
      + error
      + "</td>"
      + "</tr>"
      + "</table>";
  }

  /**
   * 设置必输项的标记
   * @param cellName 节点名称
   * @param htLayout 节点集合
   */
  private void setNeedMark(String cellName, Hashtable htLayout) {
    Object temp = htLayout.get(cellName);
    if (temp instanceof TdCell) {
      TdCell tdCell = (TdCell)temp;
      if (tdCell.getCellType().equals("text"))
        tdCell.setNeed();
    }
    else if (temp instanceof TdCell[]) {
      TdCell[] tdCellList = (TdCell[])temp;
      for (int i = 0; i < tdCellList.length; i++) {
        if (tdCellList[i].getCellType().equals("text"))
          tdCellList[i].setNeed();
      }
    }
  }

  /**
   * 取得联动项的集合
   * @param src 字段定义
   * @return String
   */
  private String pickLinkageInit(Vector src) {
    StringBuffer linkage = new StringBuffer();
    for (int i = 0; i < src.size(); i++) {
      if (((Hashtable)src.get(i)).get("SELECT_TYPE").equals("06")
        || ((Hashtable)src.get(i)).get("SELECT_TYPE").equals("07")
        || ((Hashtable)src.get(i)).get("SELECT_TYPE").equals("08")) {
        linkage.append("|");
        String restricts = (String) ((Hashtable)src.get(i)).get("RESTRICTS");
        linkage.append((String) ((Hashtable)src.get(i)).get("ONSCRIPTVALUE"));
        linkage.append(";#");
        linkage.append(restricts);
      }
    }
    if (linkage.length() > 0)
      return linkage.substring(1);
    else
      return "";
  }

  /**
   * 取得必输项的集合
   * @param src 字段定义
   * @return String
   */
  private String pickNeedParams(Vector src) {
    StringBuffer needParamBuffer = new StringBuffer();
    for (int i = 0; i < src.size(); i++) {
      Hashtable ht = (Hashtable)src.get(i);
      String isNeed = (String)ht.get("IS_NEED");
      if (isNeed.equals("1")) {
        String sel_type = (String)ht.get("SELECT_TYPE");
        needParamBuffer.append("|");
        if (sel_type != null && (sel_type.equals("04") || sel_type.equals("05")))
          needParamBuffer.append("lab_" + (String)ht.get("DISP_CODE"));
        else
          needParamBuffer.append((String)ht.get("DISP_CODE"));
        needParamBuffer.append(",");
        needParamBuffer.append((String)ht.get("FIELD_NAME"));
      }
    }
    return needParamBuffer.substring(1);
  }

  /**
   * 取得隐藏项的集合
   * @param src 字段定义
   * @param ht 字段值
   * @return String
   */
  private String pickHiddenField(Vector src, Hashtable ht) {
    StringBuffer hiddenFields = new StringBuffer();
    for (int i = 0; i < src.size(); i++) {
      if (((Hashtable)src.get(i)).get("FIELD_TYPE").equals("hidden") || ((Hashtable)src.get(i)).get("FIELD_TYPE").equals("HIDDEN")) {
        String table = (String) ((Hashtable)src.get(i)).get("TABLE_CODE");
        String field = (String) ((Hashtable)src.get(i)).get("FIELD_CODE");
        String disp = (String) ((Hashtable)src.get(i)).get("DISP_CODE");
        String value = "";
        try {
          value = (String) ((Hashtable)ht.get(table)).get(field);
        }
        catch (Exception e) {}
        if (value == null)
          value = "";
        hiddenFields.append("<input type=\"hidden\" isAutoGen=\"true\" name=\"");
        hiddenFields.append(disp);
        hiddenFields.append("\" value=\"");
        hiddenFields.append(value);
        hiddenFields.append("\" >");
      }
      else
        continue;
    }
    return hiddenFields.toString();
  }
  
  
	/**
	 * 取得隐藏项的集合(非主键)
	 * @param src 字段定义
	 * @param ht 字段值
	 * @return String
	 */
	private String jjHiddenField(Vector src, Hashtable ht) {
		StringBuffer hiddenFields = new StringBuffer();
		for (int i = 0; i < src.size(); i++) {
			if ((((Hashtable)src.get(i)).get("FIELD_TYPE").equals("hidden") || ((Hashtable)src.get(i)).get("FIELD_TYPE").equals("HIDDEN"))&&(((Hashtable)src.get(i)).get("IS_PRIMARY").equals("0"))) {
				String table = (String) ((Hashtable)src.get(i)).get("TABLE_CODE");
				String field = (String) ((Hashtable)src.get(i)).get("FIELD_CODE");
				String disp = (String) ((Hashtable)src.get(i)).get("DISP_CODE");
				String value = "";
				try {
					value = (String) ((Hashtable)ht.get(table)).get(field);
				}
				catch (Exception e) {}
				if (value == null)
					value = "";
				hiddenFields.append("<input type=\"hidden\" isAutoGen=\"true\" name=\"");
				hiddenFields.append(disp);
				hiddenFields.append("\" value=\"");
				hiddenFields.append(value);
				hiddenFields.append("\" >");
			}
			else
				continue;
		}
		return hiddenFields.toString();
	}
  
	/**
	 * 取得隐藏项的集合(借据的主键信息)
	 * @param src 字段定义
	 * @param ht 字段值
	 * @return String
	 */
	private String getjjHiddenField(Vector src, Hashtable ht) {
		StringBuffer hiddenFields = new StringBuffer();
		for (int i = 0; i < src.size(); i++) {
			if ((((Hashtable)src.get(i)).get("FIELD_TYPE").equals("hidden") || ((Hashtable)src.get(i)).get("FIELD_TYPE").equals("HIDDEN"))&&(((Hashtable)src.get(i)).get("IS_PRIMARY").equals("1"))) {
				String table = (String) ((Hashtable)src.get(i)).get("TABLE_CODE");
				String field = (String) ((Hashtable)src.get(i)).get("FIELD_CODE");
				String disp = (String) ((Hashtable)src.get(i)).get("DISP_CODE");
				String value = "";
				try {
					Hashtable ht_info=(Hashtable)ht.get("ht_hs");//获得session中合同的信息
					Enumeration eu = ht_info.keys();
							String str = "";
							
							if (eu.hasMoreElements()) {
		
							str=(String) eu.nextElement();  //获得合同的表名
							
							}
					
					value = (String) ((Hashtable)ht_info.get(str)).get(str+disp.substring(8));
				}
				catch (Exception e) {}
				if (value == null)
					value = "";
				hiddenFields.append("<input type=\"hidden\" isAutoGen=\"true\" name=\"");
				hiddenFields.append(disp);
				hiddenFields.append("\" value=\"");
				hiddenFields.append(value);
				hiddenFields.append("\" >");
			}
			else
				continue;
		}
		return hiddenFields.toString();
	}
  

  /**
   * 取得主键值的集合
   * @param src 字段定义
   * @param ht 字段值
   * @return String
   */
  private String pickPrimaryField(Vector src, Hashtable ht) {
    StringBuffer hiddenFields = new StringBuffer();
    for (int i = 0; i < src.size(); i++) {
      if (((Hashtable)src.get(i)).get("IS_PRIMARY").equals("1")) {
        String table = (String) ((Hashtable)src.get(i)).get("TABLE_CODE");
        String field = (String) ((Hashtable)src.get(i)).get("FIELD_CODE");
        String disp = (String) ((Hashtable)src.get(i)).get("DISP_CODE");
        String value = "";
        try {
          value = (String) ((Hashtable)ht.get(table)).get(field);
        }
        catch (Exception e) {}
        if (value == null)
          value = "";
        hiddenFields.append("<input type=\"hidden\" name=\"");
        hiddenFields.append(disp);
        hiddenFields.append("_old\" value=\"");
        hiddenFields.append(value);
        hiddenFields.append("\" >");
      }
      else
        continue;
    }
    return hiddenFields.toString();
  }

}
