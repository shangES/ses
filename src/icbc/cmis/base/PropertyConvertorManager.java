package icbc.cmis.base;

import java.util.*;
/**
 * 
 *   @(#) PropertyConvertorManager.java	1.0 05/04/2000
 *   Copyright (c) 1999 EChannel R&D. All Rights Reserved.
 *  
 *  
 *   @version 1.0 05/04/2000
 *   @author  ZhongMingChang
 *   
 */
public class PropertyConvertorManager {

	//private static String[] searchPath = { "com.zmc.editor" };
	private static String[] searchPath = { "icbc.cmis.base" };
	private static java.util.Hashtable registry;
	
/**
 * PropertyConvertorManager constructor comment.
 */
public PropertyConvertorManager() {
	super();
}
/**
 * Locate a value editor for a given target type.
 * @param targetType  The Class object for the type to be edited
 * @return An editor object for the given target class. 
 * The result is null if no suitable editor can be found.
 */

public static PropertyConvertor findConvertor(Class targetType)
{
	initialize();
	Class editorClass = (Class) registry.get(targetType);
	if (editorClass != null)
	{
		try
		{
			Object o = editorClass.newInstance();
			return (PropertyConvertor) o;
		}
		catch (Exception ex)
		{
			System.err.println("Couldn't instantiate type convertor \"" + editorClass.getName() + "\" : " + ex);
		}
	}

	// Now try adding "Editor" to the class name.

	String editorName = targetType.getName() + "Convertor";
	try
	{
		return instantiate(targetType, editorName);
	}
	catch (Exception ex)
	{
		// Silently ignore any errors.
	}

	// Now try looking for <searchPath>.fooEditor
	editorName = targetType.getName();
	while (editorName.indexOf('.') > 0)
	{
		editorName = editorName.substring(editorName.indexOf('.') + 1);
	}
	for (int i = 0; i < searchPath.length; i++)
	{
		String name = searchPath[i] + "." + editorName + "Convertor";
		try
		{
			return instantiate(targetType, name);
		}
		catch (Exception ex)
		{
			// Silently ignore any errors.
		}
	}

	// We couldn't find a suitable Editor.
	return (null);
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/04/2000
 *  
 * 
 */
synchronized private static void initialize()
{
	if (registry != null)
		//its already initialized
		return;
	try
	{
		registry = new Hashtable();
		load(Byte.TYPE, "ByteConvertor");
		load(Short.TYPE, "ShortConvertor");
		load(Integer.TYPE, "IntConvertor");
		load(Long.TYPE, "LongConvertor");
		load(Boolean.TYPE, "BooleanConvertor");
		load(Float.TYPE, "FloatConvertor");
		load(Double.TYPE, "DoubleConvertor");
		load(Class.forName("java.lang.Integer"), "IntConvertor");
		
		load(Class.forName("java.lang.String"), "StringConvertor");
		load(Class.forName("java.awt.Color"), "ColorConvertor");
		load(Class.forName("java.awt.Font"), "FontConvertor");
		load(new String[1].getClass(), "StringArrayConvertor");
	}
	catch (Exception e)
	{
	}
}
private static PropertyConvertor instantiate(Class sibling, String className) throws InstantiationException, IllegalAccessException, ClassNotFoundException
{
	// First check with siblings classloader (if any). 
	ClassLoader cl = sibling.getClassLoader();
	if (cl != null)
	{
		try
		{
			Class cls = cl.loadClass(className);
			Object o = cls.newInstance();
			PropertyConvertor ed = (PropertyConvertor) o;
			return ed;
		}
		catch (Exception ex)
		{
			// Just drop through
		}
	}
	// Now try the system classloader.
	Class cls = Class.forName(className);
	Object o = cls.newInstance();
	PropertyConvertor ed = (PropertyConvertor) o;
	return ed;
}
private static void load(Class targetType, String name)
{
	String editorName = name;
	for (int i = 0; i < searchPath.length; i++)
	{
		try
		{
			editorName = searchPath[i] + "." + name;
			Class cls = Class.forName(editorName);
			registry.put(targetType, cls);
			return;
		}
		catch (Exception ex)
		{
			// Drop through and try next package.
		}
	}
	// This shouldn't happen.
	System.out.println("load of " + editorName + " failed!");
}
/**
 * Register an editor class to be used to editor values of
 * a given target class.
 * @param targetType the Class object of the type to be edited
 * @param editorClass the Class object of the editor class.  If
 *	   this is null, then any existing definition will be removed.
 */

public static void registerEditor(Class targetType, Class editorClass)
{
	initialize();
	if (editorClass == null)
	{
		registry.remove(targetType);
	}
	else
	{
		registry.put(targetType, editorClass);
	}
}
}
