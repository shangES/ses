var lastBgColor;
var lastStyle;
var lastColor;
var lastStatus;

function HighLightMouseOver(info)
{
	var info;
	this0=window.event.srcElement;
	while (this0.tagName!="TR")
		this0=this0.parentElement;

	lastStatus=window.status;
	if (info!=null)
		window.status=info;
	
	lastBgColor=this0.bgColor;
	this0.bgColor="8080ff";

	lastStyle=this0.style.cursor;
	this0.style.cursor="hand";

	lastColor=this0.style.color;
	this0.style.color="#FFFFFF";
	
	this0.onmouseout=HighLightMouseOut;
}
function HighLightMouseOut()
{
	this0=window.event.srcElement;
	while (this0.tagName!="TR")
		this0=this0.parentElement;

	window.status=lastStatus;
	this0.bgColor=lastBgColor;
	this0.style.cursor=lastStyle;
	this0.style.color=lastColor;
}
