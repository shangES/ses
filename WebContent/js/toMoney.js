<!--
//**************************************************************
//	痴思化生成最大和最小金额域
//	Creation date: (2003-05-12)	
//	@author: ecc-wangdong
//	@version: 1.0
//	@param：hidMin_money, hidMax_money, txtMin_money, txtMax_money, form
//      @param说明：
//          输入参数：隐藏域名称（最小金额），隐藏域名称（最大金额），表现域名称（最小金额），表现域名称（最大金额），FORM名	
//      @condition：最大和最小金额（表现域）已赋初值
//	@调用样例
//	使用说明：                                                             	
//	1．	此部分jsp代码用语完成表现域初值的设定                          	
//	<%  String minAmtTxt ="0";            //默认最小金额表现值              	
//	    String maxAmtTxt ="1000000000000";//默认最大金额表现值             	
//	   if (opCtx.getName()!=null){                                         	
//	       //当前交易数据（最小金额），其值和网关约定（后两位表示小数部分）	
//		minAmtTxt = (String)opCtx.getValueAt("minAmount");		       	
//	     	                                                               	
//		//最小金额痴思表现值(增加小数点)                                       	
//		minAmtTxt = (String)minAmtTxt.substring(0,minAmtTxt.length()-2)+"."+(String)minAmtTxt.substring(minAmtTxt.length()-2);                	
//	   	//当前交易数据（最大金额），其值和网关约定（后两位表示小数部分）    
//        	maxAmtTxt = (String)opCtx.getValueAt("maxAmount");                     
//        	//最大金额痴思表现值(增加小数点)                                       
//        	maxAmtTxt = (String)maxAmtTxt.substring(0,maxAmtTxt.length()-2)+"."+(String)maxAmtTxt.substring(maxAmtTxt.length()-2);                
//           }                                                                   
//        %>                                                                     
//	2.	在相关的表现域进行赋值
// 	<td height="28" colspan="2" bgcolor="e7E7E7"> 　发生额：　大于 
//   		 <input type="text" name="txtMinAmount" size="15" style="font-size:12px" value="<%=minAmtTxt%>">元　小于 
//    		 <input type="text" name="txtMaxAmount" size="17" style="font-size:12px" value="<%=maxAmtTxt%>">
// 	</td>
//	3.  调用方法
//   	在页面onload时调用此方法  
//	<body onLoad="iniTxtMoney('minAmount','maxAmount','txtMinAmount','txtMaxAmount','thisform')；">
//**************************************************************
function iniTxtMoney(hidMin_money,hidMax_money,txtMin_money,txtMax_money,form){
    convertToMoney(form,txtMin_money,hidMin_money);
    convertToMoney(form,txtMax_money,hidMax_money);	
}


//**************************************************************
//	对金额进行转换，包括表现域和隐藏域
//	Creation date: (2003-05-12)	
//	@author: ecc-wangdong
//	@version: 1.0
//	@param：form, txtmoney, hidmoney
//      @param说明：
//              FORM名，表现域名称，隐藏域名称
//      @condition：该表现域已赋初值	
//**************************************************************


function convertToMoney(form,txtmoney,hidmoney){
    var tonumber;
    var re = /,/g;
    var txt_money = eval("document."+form+"."+txtmoney);
    var hid_money = eval("document."+form+"."+hidmoney);
    tonumber = txt_money.value.replace(re,"");

    txt_money.value = "";
    if (tonumber !="" && tonumber!=null){
   	rep = / /g;
		var amt = tonumber.replace(rep,"");
		
		for(var j = 0; j < amt.length; j++){
			if(isNaN(parseInt(amt.charAt(j),10)) && amt.charAt(j)!="," && amt.charAt(j)!=".") {
				alert("请输入正确的金额!");
				txt_money.value="";
				txt_money.focus();
				return;
			}
		}
		if(amt.indexOf(".")!=amt.lastIndexOf(".")){
			alert("请输入正确的金额!");
			txt_money.focus();
			return;
		}
	
		re = /,/g;
		var amt1 = amt.replace(re,"");

		var amt2=parseFloat(amt1);		
		if(amt2<0){
			alert("输入的金额小于零,请重新输入!");
			txt_money.focus();
			return;
		}else{		//大于0的正数;
			if(amt1.indexOf(".")!=-1){				
				var str = amt1.substr(amt1.indexOf(".")+1);				
				if(str.length>2){
					alert("输入的金额小数点后只能保留两位,请重新输入!");
					txt_money.focus();
					return;
				}else if(str.length==1){
					amt1=amt1 + "0";
				}else if(str.length<1){
					amt1=amt1 + "00";
				}
			}else{
				amt1=amt1 + ".00";
			}
			if(amt1.charAt(0)=='0' && amt1.indexOf(".")!=1){
			alert("请输入正确的金额!");
			txt_money.focus();
			return;
			}
			hid_money.value=amt1.substring(0,amt1.indexOf(".")) + amt1.substr(amt1.indexOf(".")+1);
			var temp=amt1.substring(0,amt1.indexOf("."));
			if (hid_money.value.length > 18){
			    alert("金额太大");
			    txt_money.focus();
			    return;
			}
			txt_money.value=comma(temp) + amt1.substring(amt1.indexOf("."));
			return;							
		}
    }
}
//**************************************************************
//	表现形式增加逗号
//	Creation date: (2003-05-12)	
//	@author: ecc-wangdong
//	@version: 1.0
//	@param：number
//      @param说明：
//              需转换数值	
//**************************************************************

function comma(number) {
	number = '' + number;
	if (number.length > 3) {
		var mod = number.length % 3;
		var output = (mod > 0 ? (number.substring(0,mod)) : '');
		for (i=0 ; i < Math.floor(number.length / 3); i++) {
			if ((mod == 0) && (i == 0))
				output += number.substring(mod+ 3 * i, mod + 3 * i + 3);
			else
				output += ',' + number.substring(mod + 3 * i, mod + 3 * i + 3);
		}
		return (output);
	}
	else return number;
}
