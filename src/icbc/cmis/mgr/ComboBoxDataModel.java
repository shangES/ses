package icbc.cmis.mgr;

//import com.sun.java.swing.*;

import javax.swing.*;

/**
 * 
 *   @(#) *.java	1.0 05/13/2000
 *   Copyright (c) 1999 Nantian R&D. All Rights Reserved.
 *  
 *  
 *   @version 1.0 05/13/2000
 *   @author  ZhongMingChang
 *   
 */
public class ComboBoxDataModel extends ListDataModel implements ComboBoxModel {
	private Object selectedItem = null;
/**
 * ComboBoxDataModel constructor comment.
 */
public ComboBoxDataModel() {
	super();
}
/**
 * ComboBoxDataModel constructor comment.
 * @param data java.lang.String[]
 */
public ComboBoxDataModel(java.lang.String[] data) {
	super(data);
}
/**
 * getSelectedItem method comment.
 */
public Object getSelectedItem() {
	return selectedItem;
}
/**
 * setSelectedItem method comment.
 */
public void setSelectedItem(Object anItem) {
	selectedItem = anItem;
	fireContentsChanged(this, -1, -1);
	
}
}
