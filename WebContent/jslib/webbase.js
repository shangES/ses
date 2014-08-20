var basepath0606 ="/icbc/cmis";
function SetTDNowrap(langCode){
	//if (langCode!="zh_CN") return;
	var atd=document.getElementsByTagName("TD");
	for(i=0;i<atd.length;i++)
	{
		if (atd[i].className=="td1")
			atd[i].noWrap=true;
   }
}
