package icbc.cmis.mgr;

/*
 * @(#)TreeDataModel.java	1.0 10/18/99
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

//import com.sun.java.swing.event.*;
//import com.sun.java.swing.tree.*;

import javax.swing.event.*;
import javax.swing.tree.*;


import java.util.Vector;



 

public class TreeDataModel implements TreeModel {

	private Vector treeModelListeners = new Vector(10);
	private TreeData rootTreeData;

	protected boolean isSort = false;
	
/**
 * TreeDataModel constructor comment.
 */
public TreeDataModel() {
	super();
}
/**
 * TreeDataModel constructor comment.
 */
public TreeDataModel(TreeData rootTreeData) {
	super();
	this.rootTreeData = rootTreeData;
}
/**
 * TreeDataModel constructor comment.
 */
public TreeDataModel(TreeData rootTreeData, boolean isSort) {
	super();
	this.isSort = isSort;
	this.rootTreeData = rootTreeData;
	rootTreeData.setSort( isSort);
}
/**
 * TreeDataModel constructor comment.
 */
public TreeDataModel(boolean isSort) {
	super();
	this.isSort = isSort;
}
public void addNode(TreeData parent, TreeData treeNode)
{

	parent.addKids(  treeNode );
	treeNode.setSort( isSort);
	fireTreeNodesInserted(parent,  treeNode);
}
/**
 * getRoot method comment.
 */
public TreeData addNodes(String str) {
	return rootTreeData.addKids( str );

}
/**
 * addTreeModelListener method comment.
 */
public void addTreeModelListener(TreeModelListener l) {

		treeModelListeners.addElement(l);
	int i = 0;
	i++;
}
/**
 * getRoot method comment.
 */
public void clearAllNodes()
{
	if (rootTreeData != null)
	{
		rootTreeData.removeChild();
		fireTreeStructureChanged(rootTreeData);
	}
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @param node com.zmc.datamodel.TreeData
 */
public void deleteNode(TreeData node)
{
	fireTreeNodesRemoved(node);
	TreeData father = node.getFather();
	if (father != null)
		father.deleteKids(node);
}
public void fireTreeNodesChanged(TreeData treeNodes)
{
	Vector v = new Vector(10);
	TreeData p = treeNodes.getFather();
	if( p == null)
		return;

	int anIndex = p.getIndexOfChild(treeNodes);

	int[]        cIndexs = new int[1];
	if(anIndex != -1) {
		cIndexs[0] = anIndex;
	}

	Object[] path = getPathToRoot(p);

	Object[] cChildren = new Object[1];
	cChildren[0] = treeNodes;
	
	
	int len = treeModelListeners.size();
	TreeModelEvent e = new TreeModelEvent(this, path, cIndexs, cChildren);
	for (int i = 0; i < len; i++)
	{
		((TreeModelListener) treeModelListeners.elementAt(i)).treeNodesChanged(e);
	}
}
public void fireTreeNodesInserted(TreeData parent, TreeData treeNodes)
{


	Vector v = new Vector(10);
	TreeData p = parent;

	int anIndex = p.getIndexOfChild(treeNodes);
	int[]        cIndexs = new int[1];
	if(anIndex != -1) {
		cIndexs[0] = anIndex;
	}

//get to root path	
	Object[] path = getPathToRoot(parent);

	Object[] cChildren = new Object[1];
	cChildren[0] = treeNodes;
	
	
	int len = treeModelListeners.size();
	TreeModelEvent e = new TreeModelEvent(this, path, cIndexs, cChildren);
	for (int i = 0; i < len; i++)
	{
		((TreeModelListener) treeModelListeners.elementAt(i)).treeNodesInserted(e);
	}
}
public void fireTreeNodesRemoved(TreeData treeNodes)
{

	Vector v = new Vector(10);
	TreeData p = treeNodes.getFather();

	if( p == null)
		return;

	int anIndex = p.getIndexOfChild(treeNodes);
	
	int[]        cIndexs = new int[1];
	if(anIndex != -1) {
		cIndexs[0] = anIndex;
	}

	Object[] path = getPathToRoot(p);

	Object[] cChildren = new Object[1];
	cChildren[0] = treeNodes;
	
	
	int len = treeModelListeners.size();
	TreeModelEvent e = new TreeModelEvent(this, path, cIndexs, cChildren);
	for (int i = 0; i < len; i++)
	{
		((TreeModelListener) treeModelListeners.elementAt(i)).treeNodesRemoved(e);
	}
}
/**
 * getRoot method comment.
 */
public void fireTreeStructureChanged(TreeData treeRoot) {
	  int len = treeModelListeners.size();
		TreeModelEvent e = new TreeModelEvent(this, 
											  new Object[] {treeRoot});
		for (int i = 0; i < len; i++) {
			((TreeModelListener)treeModelListeners.elementAt(i)).
					treeStructureChanged(e);
		}
	}
/**
 * getChild method comment.
 */
public Object getChild(Object parent, int index) {

	  TreeData p = (TreeData)parent;
  /*      if (showAncestors) {
			if ((index > 0) && (p.getFather() != null)) {
				return p.getMother();
			}
			return p.getFather();
		}
		*/
		return p.getChildAt(index);	
//	return null;
}
/**
 * getChildCount method comment.
 */
public int getChildCount(Object parent) {

	  TreeData p = (TreeData)parent;
/*		if (showAncestors) {
			int count = 0;
			if (p.getFather() != null) { 
				count++;
			}
			return count;
		}*/
		return p.getChildCount();
  }
/**
 * getIndexOfChild method comment.
 */
public int getIndexOfChild(Object parent, Object child) {
	  TreeData p = (TreeData)parent;
/*        if (showAncestors) {
			int count = 0;
			Person father = p.getFather();
			if (father != null) {
				count++;
				if (father == child) {
					return 0;
				}
			}
			if (p.getMother() != child) {
				return count;
			}
			return -1;
		}
	 */
		return p.getIndexOfChild((TreeData)child);
}
public TreeData[]  getPathToRoot(TreeData treeNode)
{

	Vector v = new Vector(10);

	if( treeNode == null )
		return null;

	TreeData p = treeNode;
	v.addElement( p );
	
	while( true )
	{
		p = p.getFather();
		if( p != null )
			v.addElement( p );
		else
			break;
	}

	int len = v.size();
	TreeData[] path = new TreeData[ len ];
//revers the path	
	for( int i=0; i<v.size(); i++)
	{
		path[len - i- 1] = (TreeData)v.elementAt(i);
	}

	return path;
}
/**
 * getRoot method comment.
 */
public Object getRoot() {
	return rootTreeData;

}
/**
 * isLeaf method comment.
 */
public boolean isLeaf(Object node) {
		TreeData p = (TreeData)node;
/*        if (showAncestors) {
			return ((p.getFather() == null)
				 && (p.getMother() == null));
		}*/
		
//		return p.getChildCount() == 0;

	return p.isLeaf();
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @param aNode com.zmc.datamodel.TreeData
 */
public void modifyNodes(TreeData aNode) {

	updateNode( aNode );
	for( int i=0; i<aNode.getChildCount(); i++)
		modifyNodes( aNode.getChildAt(i));
}
/**
 * removeTreeModelListener method comment.
 */
public void removeTreeModelListener(TreeModelListener l) {

		treeModelListeners.removeElement(l);
	
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @param node com.zmc.datamodel.TreeData
 */
public void setRootNode( TreeData node) {
	this.rootTreeData = node;
	node.setSort( isSort );
	fireTreeStructureChanged( rootTreeData );
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @param isSort boolean
 */
public void setSort( boolean isSort) {
	this.isSort = isSort;
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @param node com.zmc.datamodel.TreeData
 */
public void updateNode(TreeData node)
{
	fireTreeNodesChanged(node);
}
/**
 * valueForPathChanged method comment.
 */
public void valueForPathChanged(TreePath path, Object newValue) {
}
}
