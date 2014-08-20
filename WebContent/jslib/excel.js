<!--
document.writeln("<OBJECT classid=clsid:DE6DDE9F-317A-442B-82F6-E6271FD43636 id=excel1   codebase=\""+basepath0606+"/O/OU/ExcelAcc.cab#Version=1,0,5,2\" style=\"HEIGHT: 0px; LEFT: 0px; TOP: 0px; WIDTH: 0px\"></OBJECT>");
function TableToExcel()
{

	this0=window.event.srcElement;
	while (this0.tagName!="TABLE")
		this0=this0.parentElement;

	excel1.open();
	excel1.insertTable(this0.innerHTML);
	excel1.finish();

}

function ShowExcel()
{
	this0=window.event.srcElement;
	while (this0.tagName!="TABLE")
		this0=this0.parentElement;
	this0.ondblclick=TableToExcel
	this0.title="^_^双击鼠标可以将本表格导出到Excel^_^";

	return false;
}
//-->
