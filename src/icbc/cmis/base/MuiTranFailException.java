package icbc.cmis.base;

/**
 * Insert the type's description here.
 * Creation date: (2001-1-5 16:11:38)
 * @author: Administrator
 */
public class MuiTranFailException extends TranFailException
{

//    private String classMethodName;
//    private String errorCode = "0";
//    private String dispMsg = null;
//    private String errorMsg = null;
public MuiTranFailException(
	String errCode, 
	String classMethodName, 
	String langCode)
{
	super(errCode);
	String errorMessage = null;
	errorMessage = genMsg.getErrMsgByLang(langCode,errCode);
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

public MuiTranFailException(
	String errCode, 
	String classMethodName,
	String paraMsg,
	String langCode
	 )
{
	
	super( errCode);

	String errorMessage = null;
	errorMessage = genMsg.getErrMsgByLang(langCode,errCode,paraMsg);
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


/**
 * Insert the method's description here.
 * Creation date: (2001-3-3 15:58:30)
 * @return java.lang.String
 */ 

}
