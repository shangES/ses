package icbc.cmis.mgr;

/*
 * @(#)TreeData.java	1.0 10/18/99
 *
 * Copyright (c) 1999 ZhongMingChang. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of 
 * ZhongMingChang.("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Sun.
 *
 * ZhongMingChang MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE, OR NON-INFRINGEMENT. SUN SHALL NOT BE LIABLE FOR ANY DAMAGES
 * SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING
 * THIS SOFTWARE OR ITS DERIVATIVES.
 *
 */

 /**
 *
 * @version 1.0 10/18/99
 * @author  ZhongMingChang
 */

 import java.util.*;
 
public class TreeData {
	protected String name;
//	protected int id;
	protected TreeData father = null;
	protected java.util.Vector childrens;

	protected String kids;
	protected int curIdx;

	protected boolean isSort = false;
	
/**
 * TreeData constructor comment.
 */
public TreeData() {
	super();
}
/**
 * TreeData constructor comment.
 */
public TreeData(String name) {
	super();
	this.name = name;
	father = null;
	childrens = new Vector();
}
/**
 * TreeData constructor comment.
 */
public void addKids(TreeData kids) 
{
	boolean flag = false;
	if( childrens == null)
		childrens = new java.util.Vector();

	if( isSort )
	{
		for( int i=0; i< childrens.size(); i++)
		{
			String name = ((TreeData)childrens.elementAt( i )).getName();
			if( name.compareTo( kids.getName()) >= 0  )
			{
				childrens.insertElementAt( kids, i );
				flag = true;
				break;
			}
		}
		if( !flag)
			childrens.addElement( kids );

	}
	else
		childrens.addElement( kids );
	kids.setFather( this );
}
/**
 * TreeData addkids descriped with zmc/dir1/dir2 ect..
 */
public TreeData addKids(String str) 
{ 
	String nodeName;
	TreeData p = this;

	int j;
	try{

		setKids( str );
		
		while(true )
		{
			
			nodeName = getNextKid();
			if( nodeName == null )
				break;
			int ccount = p.getChildCount();
			int i;
			for( i=0; i<ccount; i++)
			{
				TreeData kid = p.getChildAt( i );
				if( nodeName.compareTo( kid.getName()) == 0)
				{
					p = kid;
					break;
				}
			}
			if( i == ccount )
			{
				TreeData nKid = new TreeData( nodeName );
				p.addKids( nKid );
				p = nKid;
				break;
			}
		}

		while(true )
		{
			nodeName = getNextKid();
			if( nodeName == null )
				break;
			TreeData nKid = new TreeData( nodeName );
			p.addKids( nKid );
			p = nKid;
		}
		
		return p;
	}
	
	catch (Exception e )
	{
		System.out.println("Add Kids:" + e );
		return null;
	}
}
/**
 * TreeData constructor comment.
 */
public void deleteKids(TreeData kids) 
{ 
	childrens.removeElement( kids );
}
/**
 * TreeData constructor comment.
 */
  public TreeData getChildAt(int i) {
		return (TreeData)childrens.elementAt(i);
	}
/**
 * TreeData constructor comment.
 */
public int getChildCount() {
	if( childrens == null )
		return 0;

	return childrens.size(); 
}
/**
 * TreeData constructor comment.
 */
public TreeData getFather() 
{ 
	return father; 
}
/**
 * TreeData constructor comment.
 */
public int getIndexOfChild(TreeData kid) {
	  return childrens.indexOf(kid);
	}
/**
 * TreeData constructor comment.
 */
public String getName() 
{ 
	return name; 
}
/**
 * TreeData constructor comment.
 */
public String getNextKid() 
{
	if( curIdx >= kids.length())
		return null;
		
	int idx = kids.indexOf( "/", curIdx);
	if(idx == -1)
		idx = kids.length();
		
	String tmp = kids.substring(curIdx, idx);
	curIdx = idx+1;
	return tmp;
}
/**
 * isLeaf method comment.
 */
public boolean isLeaf() {
		return getChildCount() == 0;
}
/**
 * TreeData constructor comment.
 */
public void removeChild() 
{ 
	childrens.removeAllElements(); 
}
/**
 * TreeData constructor comment.
 */
public void setFather(TreeData father) 
{ 
	this.father = father; 
}
/**
 * TreeData constructor comment.
 */
public void setKids( String str )
{ 
	kids = str;
	curIdx = 0;
}
/**
 * TreeData constructor comment.
 */
public void setName(String name) 
{ 
	this.name = name; 
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @param isSort boolean
 */
public void setSort( boolean isSort ) {
	this.isSort = isSort;
}
/**
 * TreeData constructor comment.
 */
public String toString() 
{ 
	return name; 
}
}
