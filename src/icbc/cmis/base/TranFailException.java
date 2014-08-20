package icbc.cmis.base;

/**
 * Insert the type's description here.
 * Creation date: (2001-1-5 16:11:38)
 * @author: Administrator
 */
public class TranFailException extends Exception
{

    protected String classMethodName;
	protected String errorCode = "0";
	protected String dispMsg = null;
	protected String errorMsg = null;
public TranFailException(
	String errCode, 
	String classMethodName, 
	String errorMessage)
{
	super( errorMessage);
	
	errorCode = errCode;
	
	if(errorCode == null)
		errorCode = "";
	this.classMethodName = classMethodName;
	this.errorMsg = errorMessage;
	
	if(errorCode == null || errorCode.trim().length() == 0 ){
			this.dispMsg =  "´íÎóÂëÎª: "+errorCode+",Î´Öª´íÎó";
	}
	else{
		if(errorCode.trim().length() == 4)
			errorCode = "0"+errorCode.trim();
		this.dispMsg = (String) CmisConstance.getDispErrorMsg(errorCode);
		if(this.dispMsg == null) this.dispMsg = "";
	}
}

public TranFailException(
	String errCode 
	)
{
	super( errCode);

}

public TranFailException(
	String errCode, 
	String classMethodName )
{
	super( errCode);
	String errorMessage = null;
	errorMessage = genMsg.getErrMsg(errCode);
	if(errorMessage==null)  errorMessage="";
			
	errorCode = errCode;
	
	if(errorCode == null)
		errorCode = "";
	this.classMethodName = classMethodName;
	this.errorMsg = errCode;
	
	if(errorCode == null || errorCode.trim().length() == 0 ){
			this.dispMsg =  "code: "+errorCode+",unkoow";
	}
	else{
		this.dispMsg = errorMessage;
	}
}

public TranFailException(
	String errCode, 
	String classMethodName,
	String paraMsg,
	boolean needErrcode
	 )
{
	
	super( errCode);

	String errorMessage = null;
	errorMessage = genMsg.getErrMsg(errCode,paraMsg);
	if(errorMessage==null)  errorMessage="";
	
	errorCode = errCode;
	
	if(errorCode == null)
		errorCode = "";
	this.classMethodName = classMethodName;
	this.errorMsg = errCode;
	
	if(errorCode == null || errorCode.trim().length() == 0 ){
			this.dispMsg =  "code: "+errorCode+",unkoow";
	}
	else{
		this.dispMsg = errorMessage;
	}
}



public TranFailException(
	String errorCode, 
	String classMethodName, 
	String errorMessage,
	String errorDispMsg)
{
	super( errorMessage);

	this.errorCode = errorCode;
	if(this.errorCode == null)
		this.errorCode = "";
	this.classMethodName = classMethodName;
	this.errorMsg = errorMessage;
	this.dispMsg = errorDispMsg;
	
	
}/**
 * Insert the method's description here.
 * Creation date: (2001-3-3 15:58:30)
 * @return java.lang.String
 */
public String getDisplayMessage() {

	return dispMsg;
	
}
/**
 * Insert the method's description here.
 * Creation date: (2001-3-3 15:58:30)
 * @return java.lang.String
 */ 

public String getDisplayMessage(String errorCode) {

	return dispMsg; 
	
}
public String getErrorCode() {
	return errorCode;
}
public String getErrorLocation() {
	return classMethodName;
}
public String getErrorMsg() {
	return errorMsg;
}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-30 18:46:02)
 * @return java.lang.String
 */
public String toMsg() {
	String retStr = "TranFailException:\n";
	if(errorCode == null){
		retStr =retStr+"ErrorCode:"+"\n";
	}else{
		retStr =retStr + "ErrorCode:"+errorCode+"\n";
	}
	if(classMethodName == null){
		retStr = retStr+"ErrorLocation:\n";
	}else{
		retStr = retStr+"ErrorLocation:"+classMethodName+"\n";
	}
	if(dispMsg == null){
		retStr = retStr+"ErrorDisplayMsg:\n";
	}else{
		retStr = retStr+"ErrorDisplayMsg:"+dispMsg+"\n";
	}
	if(errorMsg == null){
		retStr = retStr+"ErrorMessage:\n";
	}else{
		retStr = retStr+"ErrorMessage:"+errorMsg+"\n";
	}
	return retStr;
}
}
