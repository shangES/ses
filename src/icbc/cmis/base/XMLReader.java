package icbc.cmis.base;

import com.ibm.xml.dom.*;
import com.ibm.xml.parser.*;
import com.ibm.xml.parsers.*;
import org.w3c.dom.*;

import java.util.*;

import java.beans.*;
import java.lang.reflect.*;

//import com.ecc.echannels.desktop.editor.*;
public class XMLReader implements ErrorListener {

	//all the classType for those Element that been loaded
	private Hashtable classes = new Hashtable();

	//all the class's method descriptor
	private Hashtable methodDescriptors = new Hashtable();
	private Hashtable propertyLists = new Hashtable();
	//the hashtable contains all the beans index with Node
	private Hashtable nodeBeanTable = null;

	private Object rootObject = null;

	private String packages = "icbc.cmis.base";

	private Parser parser = null;
	//private DOMParser parser = null;

	private Document document = null;

	Hashtable formatDefs = new Hashtable();
/**
 * XMLReader constructor comment.
 */
public XMLReader() {
	super();
	initialize();
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @param parent java.lang.Object
 * @param o java.lang.Object
 */
private void addObjectToParent(Object parent, Object o)
{
	try
	{
		String methodName = "add";

/*		
		try{
			Object args[] = {o};
			args[0] = o;

			Class clas[] = new Class[1];
			clas[0] = o.getClass();
						
			Method adder = parent.getClass().getMethod(methodName,clas);
			adder.invoke( parent, args);
			
		}catch(Exception e)
		{
			System.out.println( "Exception from XMLLoader.addObjectToParent(1):" + e);
			String msg = parent.toString() + ":" + o.toString();
			System.out.println( msg);
		}

*/		
		String classType = parent.getClass().getName();
		int k = classType.lastIndexOf(".");
		if (k != -1)
			classType = classType.substring(k+1);
		MethodDescriptor[] descriptors = (MethodDescriptor[]) methodDescriptors.get(classType);
		
		
		for (int i = 0; i < descriptors.length; i++)
		{
			//find out the add method;	with the same type parameter of object o.
			Method aMethod = descriptors[i].getMethod();
			String aMethodName = aMethod.getName();
			if (aMethodName.compareTo(methodName) == 0)
			{
				Method setter = aMethod;
				Class[] types = setter.getParameterTypes();
				if (types.length == 1 && types[0].equals(o.getClass()))
				{
					Object args[] = {o};
					args[0] = o;
					setter.invoke(parent, args);
					return;
				}
			}
		}

		
	}
	catch (Exception e)
	{
		System.out.println("Exception in XMLReader.addObjectToParent(Object, Object): " + e);
		String msg = "Can't Add " + o.getClass().toString() + " to:" + parent.getClass().toString();
		System.out.println( msg + "\n");
	}

	String msg = "Can't Add " + o.getClass().toString() + " to:" + parent.getClass().toString();
	System.out.println( msg + "\n");
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @param parent java.lang.Object
 * @param o java.lang.Object
 */
private void addObjectToParent(Object parent, Object o, String elementName)
{
	try
	{

		String methodName = "add"+ ("" + elementName.charAt(0)).toUpperCase()+elementName.substring(1);
		
		String classType = parent.getClass().getName();
		int k = classType.lastIndexOf(".");
		if (k != -1)
			classType = classType.substring(k+1);
		MethodDescriptor[] descriptors = (MethodDescriptor[]) methodDescriptors.get(classType);
		
		
		for (int i = 0; i < descriptors.length; i++)
		{
			//find out the add method;	with the same type parameter of object o.
			Method aMethod = descriptors[i].getMethod();
			String aMethodName = aMethod.getName();
			if (aMethodName.compareTo(methodName) == 0)
			{
				Method setter = aMethod;
				Class[] types = setter.getParameterTypes();

//				if (types.length == 1 && o.getClass().isInstance(types[0]) )  //types[0].equals(o.getClass()))	//
				{
					Object args[] = {o};
					args[0] = o;
					setter.invoke(parent, args);
					return;
				}
			}
		}

		
	}
	catch (Exception e)
	{
		System.out.println("Exception in XMLReader.addObjectToParent(Object, Object, String): " + e);
		String msg = "Can't Add " + o.getClass().toString() + " to:" + parent.getClass().toString();
		System.out.println( msg + "\n");
	}

	String msg = "Can't Add " + o.getClass().toString() + " to:" + parent.getClass().toString();
	System.out.println( msg + "\n");
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/04/2000
 *  
 * 
 * @return java.lang.Object
 * @param node org.w3c.dom.Node
 */
private Object createObject(Node node)
{
	try
	{


		if(node.getNodeName().equals( "Format" ))
			return null;
		
		Object o = null;

		String elementName = node.getNodeName();
//		Node aNode = profile.getElement(elementName);
		String classType = null;
		try{
			classType = node.getAttributes().getNamedItem("classType").getNodeValue();
		}catch(Exception e)
		{
			
		}
		if( classType == null)
			classType = node.getNodeName();

//		getProgressIndicator().showMessage( "Create the Object: " + classType );
		
		Class cl = getClass( classType);

		if( cl == null)
		{
			System.out.println("Exception from XMLReader: class +[" + classType + "] not found!");

			return null;
		}

		try{
			o = cl.newInstance();
		}catch(Throwable te)
		{
			System.out.println( "Error instantiating a bean from node: " + node.getNodeName() + ". " + te);

			te.printStackTrace();
			return null;
		}
		
//		getProgressIndicator().showMessage( "Set the Object:[ " + classType + "]'s Attributes..." );
		
		setAttributes( o, classType, node );
		
//		Wrapper aWrapper = new Wrapper( o, classType, classType, elementName);
//		aWrapper.setAttributes(node);

		nodeBeanTable.put(node, o);
		
		if( rootObject == null)
		{
			rootObject = o;
		}
		else
		{
			Object parentObject = (Object)nodeBeanTable.get(node.getParentNode());
			addObjectToParent( parentObject, o, elementName);
			
		}
		
		return o;
	}
	catch (Exception e)
	{
		System.out.println( "Error instantiating a bean from node: " + node.getNodeName() + ". " + e);
	}
	return null;
}
public int error(String fileName, int lineNo, int charOffset, Object key, String msg)
{
	// Do nothing..in other words, suppress all notifications.
	System.out.println(key.toString() + "Error in line[ " + lineNo + "] : " + msg);
//	numOfErrors++;
	return 0;
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @return java.lang.Class
 * @param classType java.lang.String
 */
private Class getClass(String classType)
{
	Class o = (Class) classes.get(classType);
	
	if (o == null)
	{
		try
		{
			StringTokenizer st = new StringTokenizer(packages, ",");
			while (o == null && st.hasMoreTokens())
			{
				try
				{
					String className = st.nextToken() + "." + classType;
					o = Class.forName(className); //.newInstance();
				}
				catch (Exception e)
				{
					//				System.out.println( e );
				}
			}
			if (o == null)
			{
				System.out.println("XMLReader.createObject: failed to create the bean: " + classType);
			}
			
			classes.put(classType, o);


			try
			{
				BeanInfo bi = Introspector.getBeanInfo(o);

				MethodDescriptor[] descriptors = bi.getMethodDescriptors();
				methodDescriptors.put( classType, descriptors);

				PropertyDescriptor[] propertyList = bi.getPropertyDescriptors();
				propertyLists.put(classType, propertyList);
				
			}
			catch (IntrospectionException ex)
			{
				System.out.println("XMLReader: Couldn't introspect: " + ex.toString());
				return null;
			}
			
		}
		catch (Exception  e)
		{
		}
	}
	return o;
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @return Node
 * @param name java.lang.String

*	return a node named name in this XML tree
 
 */



 
public Node getNode(String name) {


	NodeList nodeList = document.getChildNodes();
	int len = nodeList.getLength();  
	if (len > 0) 
	{
		for (int i = 0; i< len; i++)
		{
			switch (nodeList.item(i).getNodeType()) 
			{
				case Node.DOCUMENT_NODE: 
				{
					System.out.println("DOCUMENT_NODE");
					break;
				}
				case Node.ELEMENT_NODE: 
				{
					Node aNode = nodeList.item(i);
					if( aNode.getNodeName().equals(name))
						return aNode;
					else 
						return getNode(aNode, name);

				}
				default: 
				{
					break; 
				}
			}
		}
	} 
	return null;
}
/**
 * Allows to check if a node is a text node or an element node.
 * If a node is an element node, then creates the corresponding element instance.
 * @param node Nodeorg.w3c.dom.Node
 */
private Node getNode(Node parent, String name)
{
	
	NodeList children = parent.getChildNodes();
	int childrenSize = children.getLength();
	for (int i = 0; i < childrenSize; i++)
	{
		Node child = children.item(i);
		switch (child.getNodeType())
		{
			case (Node.TEXT_NODE) :
			{
				break;
			}
			case (Node.ELEMENT_NODE) :
			
			{
				if( child.getNodeName().equals(name))
					return child;
				else
					return getNode( child, name );
			}
			default :
			{
				break;
			}
		}
	}
	return null;
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @return org.w3c.dom.Node
 * @param id java.lang.String
 */
public Node getNodeByID(Node parent, String id)
{
	NodeList children = parent.getChildNodes();
	int childrenSize = children.getLength();

	for (int i = 0; i < childrenSize; i++)
	{
		Node child = children.item(i);
		switch (child.getNodeType())
		{
			case (Node.TEXT_NODE) :

				{
					break;
				}
			case (Node.ELEMENT_NODE) :

				{
					try
					{
						String aid = child.getAttributes().getNamedItem("id").getNodeValue();
						if (aid.equals(id))
							return child;
					}
					catch (Exception e)
					{
					}
					break;
				}
			default :

				{
					break;
				}
		}
	}
	return null;
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 */
private void initialize()
{

	try
	{
		parser = new Parser("XMLLoader", this, null);
//		parser = new DOMParser();
	}
	catch (Exception e)
	{
		System.out.println("Exception in XMLLoader.initialize : " + e);
	}
	
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @param content java.lang.String
 */
public void loadXMLContent(String content) throws Exception
{

	if (content.length() == 0)
		return;
//	getProgressIndicator().show();
//	getProgressIndicator().showMessage("Parsering the XML Content... ");
	nodeBeanTable = new Hashtable();

	org.xml.sax.InputSource inputSource = new org.xml.sax.InputSource();
	inputSource.setCharacterStream(new java.io.StringReader(content));
	try
	{
		parser.parse(inputSource);
	}
	catch (org.xml.sax.SAXException se)
	{
		System.out.println("Not properly parsed: " + se.toString());
		throw new Exception("Not properly parsed: " + se.toString());
	}
	catch (java.io.IOException ioe)
	{
		System.out.println("Not properly parsed: " + ioe.toString());
		throw new Exception(ioe.toString());
	}
	catch (Exception e)
	{
		System.out.println("Warning - XMLLoader.LoadXMLContent(String content): The XML parser has not read correctly the XML content!");
	}

	Document doc = parser.getDocument();
	if (doc == null)
	{
		System.out.println("Error - check XML content! ");
//		getProgressIndicator().showMessage("Error - check XML Content! ");
//		getProgressIndicator().dispose();
		return;
	}

	document = doc;
	rootObject = null;

//	getProgressIndicator().showMessage("Destorying objects to free memory...");
	//	parser = null;
	//	System.runFinalization();
//	getProgressIndicator().showMessage("Process the XML File finished!");
//	getProgressIndicator().dispose();
}


/////////////////////////////////////////////////////////////////////////////
//
//	修该原因：为了在解析XML包时避免解析器要求DTD定义文件的要求。更换了XML parser
//				类型.
//	修改人：YangGuangRun
//	修改时间：2004-10-25
////////////////////////////////////////////////////////////////////////////
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @param content java.lang.String
 */
public void loadXMLContentNew(String content) throws Exception
{
	DOMParser parser=null;
	if (content.length() == 0)
		return;
//	getProgressIndicator().show();
//	getProgressIndicator().showMessage("Parsering the XML Content... ");
	nodeBeanTable = new Hashtable();

	org.xml.sax.InputSource inputSource = new org.xml.sax.InputSource();
	inputSource.setCharacterStream(new java.io.StringReader(content));
	try
	{	
		parser=new DOMParser();
		parser.parse(inputSource);
	}
	catch (org.xml.sax.SAXException se)
	{
		System.out.println("Not properly parsed: " + se.toString());
		throw new Exception("Not properly parsed: " + se.toString());
	}
	catch (java.io.IOException ioe)
	{
		System.out.println("Not properly parsed: " + ioe.toString());
		throw new Exception(ioe.toString());
	}
	catch (Exception e)
	{
		System.out.println("Warning - XMLLoader.LoadXMLContent(String content): The XML parser has not read correctly the XML content!");
	}

	Document doc = parser.getDocument();
	if (doc == null)
	{
		System.out.println("Error - check XML content! ");
//		getProgressIndicator().showMessage("Error - check XML Content! ");
//		getProgressIndicator().dispose();
		return;
	}

	document = doc;
	rootObject = null;

//	getProgressIndicator().showMessage("Destorying objects to free memory...");
	//	parser = null;
	//	System.runFinalization();
//	getProgressIndicator().showMessage("Process the XML File finished!");
//	getProgressIndicator().dispose();
}
//////////////////////////////////////////////////////////////////////////////


/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/04/2000
 *  
 * 
 * @param fileName java.lang.String
 */
public void loadXMLFile(String fileName)throws Exception {

	String xml_source;

//	getProgressIndicator().show();
//	getProgressIndicator().showMessage( "Parsering the XML File: " + fileName  );

	
	if( fileName.indexOf(":") != -1)
	{
		if( fileName.indexOf(":") <=2 )
		{
			xml_source = "file:///" + fileName;
			java.net.URL aURL = new java.net.URL(xml_source); //getClass().getResource( xml_source );
			xml_source = aURL.toString();
		}
		else
			xml_source = fileName;
	}
	else
	{
		xml_source = fileName;
	}
//	DOMParser parser = new DOMParser();

	nodeBeanTable = new Hashtable();
	

	try 
	{
		try
		{
			parser.parse(xml_source);
		}
		catch(Exception e)
		{
			System.out.println("Warning - XMLLoader.loadXMLFile(String fileName): The XML parser has not read correctly the XML file , check file "+fileName+": "+e.toString());
			//try to read as a stream from a JAR, if XML file not found
			java.net.URL aURL = getClass().getResource( xml_source );
			System.out.println("XMLLoader.loadXMLFile(): Now Load the XML file As: " + aURL.toString());
			parser.parse(aURL.toString());
				
		}
	} 
	catch (org.xml.sax.SAXException se) 
	{
		System.out.println("Not properly parsed: "+ se.toString());
		throw new Exception("Not properly parsed: "+ se.toString());
	}
	catch (java.io.IOException ioe) 
	{
		System.out.println("Not properly parsed: "+ ioe.toString());
		throw new Exception(ioe.toString());
	}


	Document doc = parser.getDocument();


	if( doc == null)
	{
		System.out.println("Error - check file: "+fileName);
	//	Console.showMessage("Error - check file: "+fileName);
		return;		
	}

	rootObject = null;
	
	document = doc;	

//	getProgressIndicator().setTotal(numNodes);
		
	if (doc == null)
	{
		System.out.println("Error - check file "+fileName);

//		getProgressIndicator().showMessage( "Error - check file "+fileName  );
//		getProgressIndicator().dispose();
		return;		
	}

	/*	
	else {
		NodeList nodeList = doc.getChildNodes();
		try
		{
//			setAttributes(doc.getDocumentElement());	
		}
		catch (Exception e)
		{
			System.out.println("Error in file "+fileName+". Root element not correctly defined.");
		}
		
		int len = nodeList.getLength();  
		if (len > 0) 
		{
			for (int i = 0; i< len; i++)
			{
				switch (nodeList.item(i).getNodeType()) 
				{
					case Node.DOCUMENT_NODE: 
					{
						System.out.println("DOCUMENT_NODE");
						break;
					}
					case Node.ELEMENT_NODE: 
					{
						manageNode(nodeList.item(i));
						break;
					}
					default: 
					{
						break; 
					}
				}
			}
		} 
	}
*/

}
/**
 * Allows to check if a node is a text node or an element node.
 * If a node is an element node, then creates the corresponding element instance.
 * @param node Nodeorg.w3c.dom.Node
 */
public void manageNode(Node node)
{

	createObject( node );
	
	NodeList children = node.getChildNodes();
	int childrenSize = children.getLength();
	for (int i = 0; i < childrenSize; i++)
	{
		Node child = children.item(i);
		switch (child.getNodeType())
		{
			case (Node.TEXT_NODE) :
			{
				break;
			}
			case (Node.ELEMENT_NODE) :
			
			{
				manageNode(child);
				break;
			}
			default :
			{
				break;
			}
		}
	}
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @return com.zmc.chanel.base.FormatElement
 * @param name java.lang.String
 */
public FormatElement readFormatElement( String name ) {


	FormatElement fmtElement = (FormatElement)formatDefs.get(name);
	if( fmtElement != null)
		return fmtElement;
	
	this.rootObject = null;
	this.nodeBeanTable = null;
	nodeBeanTable = new Hashtable();

	Node aNode = this.getNode( "FormatDef");
	if( aNode != null )
	{
		Node fmtNode = getNodeByID( aNode, name );

		if( fmtNode == null)
		{
			System.out.println("can't find a format node named: " + name );
			return null;
		}
		manageNode( fmtNode );
		
	}

	formatDefs.put( name, rootObject );
	
	return (FormatElement)rootObject;
	
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @param fileName java.lang.String
 */
public void readXML(String fileName) {
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @return 
 * @param content java.lang.String
 */
public void readXMLContent(String content) {
	
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @param o java.lang.Object
 * @param node org.w3c.dom.Node
 */
private void setAttributes(Object o, String classType, Node node)
{

	int x = 0, y = 0, width = 0, height = 0;

	NamedNodeMap attributes = node.getAttributes();
	int attSize = attributes.getLength();
	for (int i = 0; i < attSize; i++)
	{
		Node attribute = attributes.item(i);
		String attrName = attribute.getNodeName();
		String attrValue = attribute.getNodeValue();

		setPropertyValue(o, classType, attrName, attrValue);
	}
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/04/2000
 *  
 * 
 * @param name java.lang.String
 * @param value java.lang.String
 */
private void setPropertyValue(Object o, String classType, String name, String value)
{
	try
	{
		
		MethodDescriptor[] descriptors = (MethodDescriptor[])methodDescriptors.get( classType);

		String methodName = "set"+ ("" + name.charAt(0)).toUpperCase()+name.substring(1);

		PropertyDescriptor[] propertyList = (PropertyDescriptor[]) propertyLists.get(classType);

//		first find out the setter method from property descriptors


		for (int i = 0; i < propertyList.length; i++)
		{

			Class type = propertyList[i].getPropertyType();
			Method setter = propertyList[i].getWriteMethod();

			if (setter == null)
			{
				continue;

			}
			
			if( setter.getName().compareTo( methodName ) == 0 )
			{
				if( setter.getParameterTypes().length > 1)		//not the proper method
					continue;
					
				PropertyConvertor convertor = PropertyConvertorManager.findConvertor(type);
				convertor.setAsText(value);
				Object objValue = convertor.getValue();
		        Object args[] = { objValue };
		        args[0] = objValue;
		        setter.invoke(o, args);
				return;
			}
		}	

//		then find it from the method declares!		
//		System.out.print( classType + ": " );
		for( int i=0; i<descriptors.length; i++)
		{
			//find out the setter  method;
			Method aMethod = descriptors[i].getMethod();
			String aMethodName = aMethod.getName();

//			System.out.print( aMethodName + ", " );
			
			if( aMethodName.compareTo( methodName ) == 0)
			{
				Method setter = aMethod;
				Class[] types = setter.getParameterTypes();

				if( types.length > 1)	//not the proper method
					continue;
				
				Class type = types[0];

				PropertyConvertor convertor = PropertyConvertorManager.findConvertor(type);
				convertor.setAsText(value);
				Object objValue = convertor.getValue();

		        Object args[] = { objValue };
		        args[0] = objValue;
		        setter.invoke(o, args);
		
//		        System.out.println( );
		        
				return;
			}
		}

//		System.out.println( );
			
		System.out.println( "Warning - can't set the property [From XMLLoader.setPropertyValue(Object ,string, string, string)]:");
		//Console.showMessage( "Warning - can't set the property [From XMLLoader.setPropertyValue(Object ,string, string, string)]:");
		String msg = "[Object:]" + o.getClass() + "[ClassType]:" + classType.toString() + "[Name]:" + name + "[Value]:" + value; 
		System.out.println( msg + "\n");
		//Console.showMessage(msg);
		/*
		System.out.print(classType + ":");
		for (int i = 0; i < propertyList.length; i++)
		{

			Method setter = propertyList[i].getWriteMethod();

			if (setter == null)
			{
				continue;

			}
			
			System.out.print(setter.getName() + ", ");
		}
		System.out.println(".");

		for( int i=0; i<descriptors.length; i++)
		{
			//find out the setter  method;
			Method aMethod = descriptors[i].getMethod();
			System.out.print(aMethod.getName() + ", ");
		}
		System.out.println(".\n");
*/			
	}
	catch (Exception e)
	{
		System.out.println( "Exception in XMLLoader.setPropertyValue(Object, String, String): " + e);
		//Console.showMessage("Exception in XMLLoader.setPropertyValue(Object, String, String): " + e);
		String msg = "[Object:]" + o.getClass() + "[ClassType]:" + classType.toString() + "[Name]:" + name + "[Value]:" + value; 
		System.out.println( msg + "\n");
	//	Console.showMessage( msg );
		
	}
}
}
