package icbc.cmis.base;

//import com.ecc.echannels.desktop.editor.*;
import icbc.cmis.base.*;
import java.awt.Color;

import java.util.*;

/**
 * 
 *   @(#) ColorConvertor.java	1.0 05/04/2000
 *   Copyright (c) 1999 EChannel R&D. All Rights Reserved.
 *  
 *  
 *   @version 1.0 05/04/2000
 *   @author  ZhongMingChang
 *   
 */
public class ColorConvertor extends PropertyConvertorSurport {
/**
 * ByteConvertor constructor comment.
 */
public ColorConvertor() {
	super();
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/04/2000
 *  
 * 
 */
public String getAsText(){
	Color color = (Color)getValue();
	String str = color.getRed() + "," + color.getGreen() + "," + color.getBlue();
	return str;
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/04/2000
 *  
 * 
 * @param text java.lang.String
 */
public void setAsText(String text)  throws IllegalArgumentException{

	StringTokenizer st = new StringTokenizer(text, ",");

	int r=0, g=0, b=0;

	r = new Integer( st.nextToken() ).intValue();
	g = new Integer( st.nextToken() ).intValue();
	b = new Integer( st.nextToken() ).intValue();
	Color value = new Color(r,g,b);
	setValue( value );
}
}
