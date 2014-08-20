// model_print.js
var webBasePath = "/icbc/bms";

function getURLHeader()
{
	return window.location.protocol+"//"+window.location.host+webBasePath;
}
function ObjectModelPrint(model,debug)
{	
	this.objWord = new ActiveXObject("Word.Application");
	if (debug==null)
		this.debug=false;
	else
		this.debug=debug;
	this.setField=ModelPrint_setField;
	this.print=ModelPrint_print;
	this.quit=ModelPrint_quit;
	this.saveAs=ModelPrint_saveAs;
	
	if (this.debug)
	{
		this.objWord.Visible=true;
		this.objWord.Activate();
	}
    var url=getURLHeader()+model;
	//this.objWordDoc=this.objWord.Documents.add(url); 20041231 wuff 改成适应office2000
	this.objWordDoc=this.objWord.Documents.open(url);
}
function ModelPrint_setField(field_name,field_value)
{
		this.objWordDoc.Bookmarks(field_name).Range.InsertAfter(field_value);
}
function ModelPrint_print()
{
	if (this.debug)
	{
		this.objWordDoc.PrintPreview();
		this.objWord.ActiveWindow.ActivePane.View.Zoom.Percentage = 100;
		alert("调试模式下自动打印预览");
	}
	else
		this.objWordDoc.PrintOut();
}
function ModelPrint_saveAs(filename)
{
//比如："c:\\worddemo\\forkjj.doc"
	objWordDoc.SaveAs(filename);
}
function ModelPrint_quit()
{
		this.objWordDoc.close(0);
		this.objWord.Application.quit();
}
