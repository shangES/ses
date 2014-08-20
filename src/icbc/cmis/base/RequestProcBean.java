package icbc.cmis.base;

import javax.servlet.http.HttpServletRequest;
/**
 * Insert the type's description here.
 * Creation date: (2002-7-1 14:52:02)
 * @author: Administrator
 */
public class RequestProcBean {
	private String baseWebPath = null;
/**
 * JspPresentationBean constructor comment.
 */
public RequestProcBean() {
	super();
}
public String getBaseWebPath ( ){
	return baseWebPath;
	
}
/**
 * Insert the method's description here.
 * Creation date: (2002-7-1 15:00:58)
 * @return java.lang.String
 */
public String getOperationParameter(String operationName) {
	return "";
}
/**
 * Insert the method's description here.
 * Creation date: (2002-7-1 15:00:58)
 * @return java.lang.String
 */
public String getRequestURL() {
	return null;
}
/**
 * Insert the method's description here.
 * Creation date: (2002-7-1 15:00:58)
 * @return java.lang.String
 */
public String getRequestURL(String srcURL) {
	if(srcURL == null)return "";
	if(baseWebPath == null) baseWebPath = "";
	return baseWebPath+srcURL.trim();
}
public void initialize ( HttpServletRequest request ) throws Exception{
	baseWebPath = (String)CmisConstance.getParameterSettings().get("webBasePath");
	return;
	
}
}
