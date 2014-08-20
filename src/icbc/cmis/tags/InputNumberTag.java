package icbc.cmis.tags;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import java.util.StringTokenizer;
import java.text.DecimalFormat;

/**
 * Title:        cmis
 * Description:  cmis3
 * Copyright:    Copyright (c) 2001
 * Company:      icbc
 * @author icbc
 * @version 1.0
 */

public class InputNumberTag extends TagSupport {
  private String name = "number";
  private double value = 0.00;
  private int maxLength = 22;//最大长度
  private int dataLength = 16; //数字位数
  private int decimalLength = 2;//小数位数
  private int size = 22;
  private String dataFormat = "16,2";

  public InputNumberTag() {
  }

  private String formatStr(double value) {
    String format = "#,##0";
    if(decimalLength > 0) {
      format += ".";
      for (int i = 0; i < decimalLength; i++) {
        format += "0";
      }

    }
    DecimalFormat df = new DecimalFormat(format);
    String ts = df.format(value);
    for (int i = ts.length(); i <= size; i++) {
      ts = " " + ts;
    }
    return ts;
  }

  private int maxLen(int len) {
    return len + (len - 1)/3;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setValue(String value) {
    try {
      this.value = Double.valueOf(value).doubleValue();
    }
    catch (Exception ex) {

    }

  }

  public void setSize(int size) {
    this.size = size;
  }

  public void setMaxLength(int len) {
    this.maxLength = len;
  }
  /**
   * 设置长度
   * @param len 由逗号分隔  数字位数，小数位数
   */
  public void setDataFormat(String dataFormat) {
    this.dataFormat = dataFormat;
    //取数字位数，小数位数
    StringTokenizer st = new StringTokenizer(dataFormat,",");
    dataLength = 16;
    try{ dataLength = Integer.valueOf(st.nextToken()).intValue();} catch (Exception ex) {};

    decimalLength = 0;
    try{ decimalLength = Integer.valueOf(st.nextToken()).intValue();} catch (Exception ex) {};

  }

  public int doStartTag() throws javax.servlet.jsp.JspException {
    //计算最大长度
    int maxLength = maxLen(dataLength - decimalLength) + decimalLength + 2;
    if(maxLength > size) size = maxLength;
    try {
      JspWriter out = this.pageContext.getOut();
      out.print("<input type='text' id='H7D4F6Z' name='"+ name +"' size='"+ size +"' maxLength='"+ maxLength +"'  value='"+ formatStr(value) +"' dataLen='"+ dataLength +"' decLen='"+ decimalLength +"' onKeypress='inputNumber(this);' onblur='toExact2(this);' onfocus='toNumber(this);'>");
    } catch (Exception ex) {
      throw new JspException("InputNumberTag doStartTag() error." + ex.getMessage());
    }
    return SKIP_BODY;
  }
}