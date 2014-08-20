package icbc.cmis.base;

import icbc.cmis.base.*;
import java.awt.Font;

/**
 * 
 *   @(#) FontConvertor.java	1.0 05/04/2000
 *   Copyright (c) 1999 EChannel R&D. All Rights Reserved.
 *  
 *  
 *   @version 1.0 05/04/2000
 *   @author  ZhongMingChang
 *   
 */
public class FontConvertor extends PropertyConvertorSurport {
/**
 * ByteConvertor constructor comment.
 */
public FontConvertor() {
	super();
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/04/2000
 *  
 * 
 */
public String getAsText()
{
	Font font = (Font) getValue();
	String strStyle = null;
	if (font.isBold())
	{
		strStyle = font.isItalic() ? "bolditalic" : "bold";
	}
	else
	{
		strStyle = font.isItalic() ? "italic" : null;
	}

	if( strStyle != null)
		return font.getFamily() + "-" + strStyle + "-" + font.getSize();
	else
		return font.getFamily() + "-" + font.getSize();
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
	
	Font value = Font.decode(text);
	setValue( value );
}
}
