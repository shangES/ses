/*打印封页*/
function printFirstPage(client_name,proj_name,area_name,year,month,day){
    icbcprint.printStartPage();

    var sText = "内部资料，严禁外传                                                    有效期12个月";
    
    icbcprint.printText(sText, 100, 50, 1900, 110, false, "left", "黑体", 120, "010");
    
    
    var y=500;
    sText = "";
    sText = client_name;
    icbcprint.printText(sText, 0, y, 1950, y+150, false, "center", "黑体", 220, "010");

    sText = proj_name;
    icbcprint.printText(sText, 0, y+150, 1950, y+200, false, "center", "黑体", 220, "010");
    
    y=y+150;
    
    sText = "贷款评级报告";
    icbcprint.printText(sText, 0, y+150, 1950, y+300, false, "center", "黑体", 220, "010");
    y=y+150;
    
    icbcprint.printBeginTable(470, y+1200, 1480, 480, 0);
y=y+1200;
    sText = "评级行名称：";
    icbcprint.printText(sText, 0, 0, 450, 150, false, "left", "宋体", 180, "000");

    sText = area_name;
    icbcprint.printText(sText, 450, 0, 1400, 150, false, "left", "宋体", 180, "110");

    sText = "评级时间：";
    icbcprint.printText(sText, 0, 300, 450, 150, false, "left", "宋体", 180, "000");

    sText = year+"年"+month+"月"+day+"日";
    icbcprint.printText(sText, 450, 300, 1400, 150, false, "left", "宋体", 180, "110");

    icbcprint.printEndTable(0);

    icbcprint.printEndPage();
}

/*打印第二页：模块分配情况*/

function printRightInfo(principal_name,major_name,final_name,year,month,day,rightlist){
    var i=0;
    icbcprint.printStartPage();
    var y=400;
    
    icbcprint.printBeginTable(400, y, 1480, y+380, 0);
    
    var sText = "主评级人：";
    icbcprint.printText(sText, 0, 0, 450, 150, false, "left", "黑体", 140, "000");

    sText = principal_name;
    icbcprint.printText(sText, 450, 0, 1400, 150, false, "left", "宋体", 120, "010");

    sText = "评级小组成员: ";
    icbcprint.printText(sText, 0, 150, 600, 250, false, "left", "宋体", 120, "000");
    
    
    for ( i=0; i<rightlist.length; i++) {
		sText = rightlist[i][0]+" : ";
		icbcprint.printText(sText, 50, 250+i*100, 600, 150, false, "left", "宋体", 120, "000");
		
		sText = rightlist[i][1];
		icbcprint.printText(sText, 750, 250+i*100, 1480, 150, false, "left", "宋体", 120, "010");
    }
    
    var sText = "主管信贷员：";
    icbcprint.printText(sText, 0, 250+i*100, 450, 150, false, "left", "黑体", 140, "000");

    sText = major_name;
    icbcprint.printText(sText, 450, 250+i*100, 1400, 150, false, "left", "宋体", 120, "010");
    
    i++;
    
    var sText = "最终审批人：";
    icbcprint.printText(sText, 0, 250+i*100, 450, 150, false, "left", "黑体", 140, "000");

    sText = final_name;
    icbcprint.printText(sText, 450, 250+i*100, 1400, 150, false, "left", "宋体", 120, "010");
    
    i++;
    
    var sText = "最终审批时间：";
    icbcprint.printText(sText, 0, 250+i*100, 450, 150, false, "left", "黑体", 140, "000");

    sText = year+"年"+month+"月"+day+"日";;
    icbcprint.printText(sText, 450, 250+i*100, 1400, 150, false, "left", "宋体", 120, "010");
    
    icbcprint.printEndTable(0);

    icbcprint.printEndPage();

}

/*打印第三页：客户和项目基本情况*/
function printBaseInfo(proj_name,custInfo,projInfo){
    
    icbcprint.printStartPage();
    
    var sText = proj_name+"项目 ";
     y=200;
    icbcprint.printText(sText, 0, y, 1950, y+80, false, "center", "黑体", 170, "010");
    y=y+80;
      sText = "贷款评级报告 ";
    icbcprint.printText(sText, 0, y, 1950, y+160, false, "center", "黑体", 170, "010");
    
    y=y+160;
     sText = "第一章   借款人及项目基本情况介绍 ";
    icbcprint.printText(sText, 0, y, 1950, 320, false, "center", "宋体", 150, "010");
   
      y=y+80;
    sText = "第一节   客户基本情况 ";
    icbcprint.printText(sText, 0, y+160, 1950, 400, false, "center", "宋体", 140, "010");
    y=y+160;
     icbcprint.printBeginTable(100, y+80, 1480, 800, 0);
    y=y+80;
    
    for ( var i=0; i<custInfo.length; i++) {
		sText = custInfo[i][0]+" : ";
		
		
		icbcprint.printText(sText, 100, i*50, 600, 150+i*50, false, "left", "宋体", 120, "000");
		sText = custInfo[i][1];
		icbcprint.printText(sText, 600,i*50, 1480, 150+i*50, false, "left", "宋体", 120, "000");
    }
    
     sText = "第二节   项目基本情况介绍 ";
    icbcprint.printText(sText, 100, 150+i*50, 1750, 200+i*50, false, "center", "宋体", 140, "010");
    i++;
    var cell_height=200+i*50;
    for (var j=0; j<projInfo.length-2; j++) {
		sText = projInfo[j][0]+" : ";
		
		icbcprint.printText(sText, 100, cell_height, 880, 100+cell_height, false, "left", "宋体", 120, "000");
		
		sText = projInfo[j][1];
		icbcprint.printText(sText, 880,cell_height, 1600, 100+cell_height, false, "left", "宋体", 120, "000");
		i++;
		cell_height=100+cell_height;
    }
    icbcprint.printEndTable(0);
    
    
    
    icbcprint.printEndPage();
    icbcprint.printStartPage();
     sText = "● 申请报告(或其他重要说明) ";
    icbcprint.printText(sText, 200, 200, 1700, 260, false, "left", "宋体", 140, "010");
    sText = projInfo[9];
    icbcprint.printText(sText, 250, 260, 1700, 1000, false, "left", "宋体", 120, "000");
        
    sText = projInfo[10];
    
    if (sText!=""){
    
    
    icbcprint.printText(sText, 250, 1000, 1700, 2200, false, "left", "宋体", 120, "000");
}
    
    icbcprint.printEndPage();
}

/*打印第四页：财务指标*/
function printLoanorEconomy(economy,economy_name,beginYear,financial_kind){
    icbcprint.printStartPage();
   var y=200;
    var sText = "第二章  借款人评价 ";
    icbcprint.printText(sText, 100, y, 1800, y+80, false, "center", "宋体", 150, "010");
    sText = "第一节   借款人财务与管理评价 ";
    y=y+80;
    icbcprint.printText(sText, 100, y, 1800, y+100, false, "center", "宋体", 140, "010");
    y=y+100;
    sText = "一、借款人财务及管理定量评价: ";
    icbcprint.printText(sText, 200, y, 1700, y+100, false, "left", "宋体", 140, "010");
    y=y+100;
    sText = "借款人财务指标数据表： ";
    icbcprint.printText(sText, 200, y+50, 1400, y+150, false, "left", "宋体", 140, "000");
    y=y+100;
    icbcprint.printBeginTable(200, y+50, 1600, y+200, 1);
    y=y+200
    sText = "指标名称 ";
    icbcprint.printText(sText, 0, 0, 190, 100, true, "center", "宋体", 120, "010");
    sText = (beginYear -3) +"年末 ";
    icbcprint.printText(sText, 190, 0, 380, 100, true, "center", "宋体", 120, "010");
    sText = (beginYear -2) +"年末 ";
    icbcprint.printText(sText, 380, 0, 570, 100, true, "center", "宋体", 120, "010");
    sText = (beginYear -1) +"年末 ";
    icbcprint.printText(sText, 570, 0, 760, 100, true, "center", "宋体", 120, "010");
    sText = "指标名称 ";
    icbcprint.printText(sText, 760, 0, 950, 100, true, "center", "宋体", 120, "010");
     sText = (beginYear -3) +"年末 ";
    icbcprint.printText(sText, 950, 0, 1140, 100, true, "center", "宋体", 120, "010");
     sText = (beginYear -2) +"年末 ";
    icbcprint.printText(sText, 1140, 0, 1330, 100, true, "center", "宋体", 120, "010");
     sText = (beginYear -1) +"年末 ";
    icbcprint.printText(sText, 1330, 0, 1520, 100, true, "center", "宋体", 120, "010");
    //alert("economy.length"+economy.length);
    if (economy.length ==0 ){
        economy[0]=[' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '];
        economy[1]=[' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '];
        economy[2]=[' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '];
    }else 
    if (economy.length ==1 ){
    	economy[1]=[' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '];
        economy[2]=[' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '];
    }else
    if (economy.length ==2 ){
        economy[2]=[' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '];
    }    
    var cell_height = 80;     
     var num;
    if(financial_kind=="0"){//一般工业类
      num=10;
    }else{
      num=11;
    }
   
    for (var i=0; i<num; i++) {
		sText = economy_name[i];
		icbcprint.printText(sText, 0, 100+i*cell_height, 190, 180+i*cell_height, true, "center", "宋体", 105, "000");
		sText = economy[2][i];
		icbcprint.printText(sText, 190, 100+i*cell_height, 380, 180+i*cell_height, true, "center", "宋体", 105, "000");
		sText = economy[1][i];
		icbcprint.printText(sText, 380, 100+i*cell_height, 570, 180+i*cell_height, true, "center", "宋体", 105, "000");
		sText = economy[0][i];
		icbcprint.printText(sText, 570, 100+i*cell_height, 760, 180+i*cell_height, true, "center", "宋体", 105, "000");
		sText = economy_name[i+num];
		icbcprint.printText(sText, 760, 100+i*cell_height, 950, 180+i*cell_height, true, "center", "宋体", 105, "000");
		sText = economy[2][i+num];
		icbcprint.printText(sText, 950, 100+i*cell_height, 1140, 180+i*cell_height, true, "center", "宋体", 105, "000");
		sText = economy[1][i+num];
		icbcprint.printText(sText, 1140, 100+i*cell_height, 1330, 180+i*cell_height, true, "center", "宋体", 105, "000");
		sText = economy[0][i+num];
		icbcprint.printText(sText, 1330, 100+i*cell_height, 1520, 180+i*cell_height, true, "center", "宋体", 105, "000"); 
		//alert("economy_name[i]--"+economy_name[i]+"economy[2][i]--"+economy[2][i]+economy_name[i+10]+"---economy_name[i+10]---"+"economy[2][i+10]--"+economy[2][i+10]);
    }  
    icbcprint.printEndTable(0);
    
}
/*借款人财务及管理定性评价*/

function printLoanorProp(name,show,score,imshow,newname,newscore){
	
	
    sText = "二、借款人财务及管理定性评价: ";
    icbcprint.printText(sText, 200, y, 1600, y+60, false, "left", "宋体", 140, "010");
    y=y+60;
    //icbcprint.printBeginTable(200, y, 1600, y+3000, 1);
   // y=y+3000;
    var cell_height = 80;
    
    for (var i=0; i<name.length; i++) {
		//alert("name[i]--"+name[i]+"show[i]--"+show[i]+"score[i]--"+score[i]);
		sText = (i+1)+"、 "+name[i];
		icbcprint.printText(sText, 200, cell_height, 1400, 60+cell_height, false, "left", "宋体", 120, "000");
		sText = show[i];
		icbcprint.printText(sText, 280, 60+cell_height, 1700, 700+cell_height, false, "left", "宋体", 120, "000");
		
		sText = "● 评价等级 ："+score[i];
		icbcprint.printText(sText, 280, 700+cell_height, 1400, 780+cell_height, false, "left", "宋体", 120, "010");
	//	sText = score[i];
	//	icbcprint.printText(sText, 420, 760+cell_height, 1400, 840+cell_height, false, "left", "宋体", 120, "010");
		cell_height = 800 +cell_height;
		
				
   }
   for(var j=0;j<newname.length;j++){
        sText = (j+1)+"、 "+newname[j];
		icbcprint.printText(sText, 200, cell_height, 1400, 60+cell_height, false, "left", "宋体", 120, "000");
		sText = newscore[j];
		icbcprint.printText(sText, 280, 60+cell_height, 1700, 700+cell_height, false, "left", "宋体", 120, "000");
        cell_height = 800 +cell_height;
   }
   sText = "综合评价 ：";
   icbcprint.printText(sText, 200, cell_height, 1400, 80+cell_height, false, "left", "黑体", 140, "010");		
   sText = imshow;
   icbcprint.printText(sText, 280, 80+cell_height, 1700, 1300+cell_height, false, "left", "宋体", 120, "000");		
  // icbcprint.printEndTable(0);     
}
/*行业市场定性评价*/
function printMarketProp(name,show,score,productShow,imshow){
	var y=0;
    sText = "第二节   行业市场评价 ";
    icbcprint.printText(sText, 100, y, 1800, y+80, false, "center", "宋体", 140, "010");
    
  //  icbcprint.printBeginTable(200, y+80, 1600, y+3000, 1);
    y=y+80;
    sText = "一、宏观经济、产业总体背景: ";
    icbcprint.printText(sText, 200, y, 1400, y+60, false, "left", "宋体", 140, "010");
    y=y+60;
    var cell_height = y;

    var i=0;
    for (i=0; i<3; i++) {
		//alert("name[i]--"+name[i]+"show[i]--"+show[i]+"score[i]--"+score[i]);
		sText = (i+1)+"、 "+name[i];
		icbcprint.printText(sText, 200, cell_height, 1400, 60+cell_height, false, "left", "宋体", 120, "000");
		sText = show[i];
		icbcprint.printText(sText, 280, 60+cell_height, 1700, 760+cell_height, false, "left", "宋体", 120, "000");
		
		sText = "● 评价等级 ："+score[i];
		icbcprint.printText(sText, 280, 760+cell_height, 1400, 840+cell_height, false, "left", "宋体", 120, "010");
		//sText = score[i];
		//icbcprint.printText(sText, 420, 760+cell_height, 1400, 840+cell_height, false, "left", "宋体", 120, "010");
		cell_height = 860 +cell_height;
		
				
   }
   sText = "二、产业评价 ：";
   icbcprint.printText(sText, 200, cell_height, 1400, 80+cell_height, false, "left", "宋体", 140, "010");		
   cell_height = 80+cell_height;
   
   for (var j=0; j<4; j++) {
		//alert("name[i]--"+name[i]+"show[i]--"+show[i]+"score[i]--"+score[i]);
		sText = (j+1)+"、 "+name[i];
		icbcprint.printText(sText, 200, cell_height, 1400, 60+cell_height, false, "left", "宋体", 120, "000");
		sText = show[i];
		icbcprint.printText(sText, 280, 60+cell_height, 1700, 760+cell_height, false, "left", "宋体", 120, "000");
		
		
		sText = "● 评价等级 ："+score[i];
		icbcprint.printText(sText, 280, 760+cell_height, 1400, 840+cell_height, false, "left", "宋体", 120, "010");
		//sText = score[i];
	//	icbcprint.printText(sText, 420, 760+cell_height, 1600, 840+cell_height, false, "left", "宋体", 120, "010");
		cell_height = 860 +cell_height;
		
		i++;				
   }		
   sText = "三、竞争分析 ：";
   icbcprint.printText(sText, 200, cell_height, 1400, 80+cell_height, false, "left", "宋体", 140, "010");		
   cell_height = 80+cell_height;
   
   for (var j=0; j<5; j++) {
		//alert("name[i]--"+name[i]+"show[i]--"+show[i]+"score[i]--"+score[i]);
		sText = (j+1)+"、 "+name[i];
		icbcprint.printText(sText, 200, cell_height, 1400, 60+cell_height, false, "left", "宋体", 120, "000");
		sText = show[i];
		icbcprint.printText(sText, 280, 60+cell_height, 1700, 760+cell_height, false, "left", "宋体", 120, "000");
		
		sText = "● 评价等级 ："+score[i];
		icbcprint.printText(sText, 280, 760+cell_height, 1400, 840+cell_height, false, "left", "宋体", 120, "010");
		//sText = score[i];
		//icbcprint.printText(sText, 420, 760+cell_height, 1400, 840+cell_height, false, "left", "宋体", 120, "010");
		cell_height = 860 +cell_height;
		
		i++;				
   }
   sText = "四、产品分析 ：";
   icbcprint.printText(sText, 200, cell_height, 1400, 80+cell_height, false, "left", "宋体", 140, "010");		
   cell_height = 80+cell_height;
   
   for (var j=0; j<3; j++) {
		//alert("name[i]--"+name[i]+"show[i]--"+show[i]+"score[i]--"+score[i]);
		sText = (j+1)+"、 "+name[i];
		icbcprint.printText(sText, 200, cell_height, 1400, 60+cell_height, false, "left", "宋体", 120, "000");
		sText = productShow[j];
		icbcprint.printText(sText, 280, 60+cell_height, 1700, 760+cell_height, false, "left", "宋体", 120, "000");
		cell_height = 760+cell_height;
		
		i++;				
   }
   sText = "综合评价 ：";
   icbcprint.printText(sText, 200, cell_height, 1400, 80+cell_height, false, "left", "黑体", 140, "010");		
   sText = imshow;
   icbcprint.printText(sText, 280, 80+cell_height, 1700, 1000+cell_height, false, "left", "宋体", 120, "000");		
   //icbcprint.printEndTable(0);     
}
/*现金流量定性评价*/
function printCashflow06(name,show,score,imshow){
	var y=0;
    sText = "第三节   现金流量评价 ";
    icbcprint.printText(sText, 100, y, 1800, y+80, false, "center", "宋体", 140, "010");
    y=y+80;
    sText = "一、现金流量定性评价 ";
    icbcprint.printText(sText, 200, y, 1400, y+80, false, "left", "宋体", 140, "010");
    y=y+80;
    sText = "(一) 取值标准: ";
    icbcprint.printText(sText, 200, y, 1400, y+80, false, "left", "宋体", 140, "010");  
    y=y+80;
   // icbcprint.printBeginTable(200, y, 1600, y+3000, 1);
    //y=y+3000;
    var cell_height = y;
    
    var i=0;
    for (i=0; i<1; i++) {
		//alert("name[i]--"+name[i]+"show[i]--"+show[i]+"score[i]--"+score[i]);
		sText = (i+1)+"、 "+name[i];
		icbcprint.printText(sText, 200, cell_height, 1400, 60+cell_height, false, "left", "宋体", 120, "000");
		sText = show[i];
		icbcprint.printText(sText, 280, 80+cell_height, 1700, 760+cell_height, false, "left", "宋体", 120, "000");
		
		
		sText = "● 评价等级 ："+score[i];
		icbcprint.printText(sText, 280, 760+cell_height, 1400, 840+cell_height, false, "left", "宋体", 120, "010");
		//sText = score[i];
		//icbcprint.printText(sText, 420, 860+cell_height, 1400, 940+cell_height, false, "left", "宋体", 120, "010");
		cell_height = 900 +cell_height;
		
				
   }
   sText = "(二) 筹资能力分析 ：";
   icbcprint.printText(sText, 200, cell_height, 1400, 80+cell_height, false, "left", "宋体", 140, "010");		
   cell_height = 80+cell_height;
   
   for (var j=0; j<4; j++) {
		//alert("name[i]--"+name[i]+"show[i]--"+show[i]+"score[i]--"+score[i]);
		sText = (j+1)+"、 "+name[i];
		icbcprint.printText(sText, 200, cell_height, 1400, 60+cell_height, false, "left", "宋体", 120, "000");
		sText = show[i];
		icbcprint.printText(sText, 280, 60+cell_height, 1700, 760+cell_height, false, "left", "宋体", 120, "000");
		
		
		sText = "● 评价等级 ："+score[i];
		icbcprint.printText(sText, 280, 760+cell_height, 1400, 840+cell_height, false, "left", "宋体", 120, "010");
		//sText = score[i];
		//icbcprint.printText(sText, 420, 760+cell_height, 1400, 840+cell_height, false, "left", "宋体", 120, "010");
		cell_height = 860 +cell_height;
		
		i++;				
   }		
   sText = "(三) 现金流量平衡分析 ：";
   icbcprint.printText(sText, 200, cell_height, 1400, 80+cell_height, false, "left", "宋体", 140, "010");		
   cell_height = 80+cell_height;
   
   for (var j=0; j<2; j++) {
		//alert("name[i]--"+name[i]+"show[i]--"+show[i]+"score[i]--"+score[i]);
		sText = (j+1)+"、 "+name[i];
		icbcprint.printText(sText, 200, cell_height, 1400, 60+cell_height, false, "left", "宋体", 120, "000");
		sText = show[i];
		icbcprint.printText(sText, 280, 60+cell_height, 1700, 760+cell_height, false, "left", "宋体", 120, "000");
		
		sText = "● 评价等级 ："+score[i];
		icbcprint.printText(sText, 280, 760+cell_height, 1400, 840+cell_height, false, "left", "宋体", 120, "010");
		//sText = score[i];
		//icbcprint.printText(sText, 420, 760+cell_height, 1400, 840+cell_height, false, "left", "宋体", 120, "010");
		cell_height = 860 +cell_height;
		
		i++;				
   }
   sText = "(四) 敏感性分析 ：";
   icbcprint.printText(sText, 200, cell_height, 1400, 80+cell_height, false, "left", "宋体", 140, "010");		
   cell_height = 80+cell_height;
   
   for (var j=0; j<1; j++) {
		//alert("name[i]--"+name[i]+"show[i]--"+show[i]+"score[i]--"+score[i]);
		sText = (j+1)+"、 "+name[i];
		icbcprint.printText(sText, 200, cell_height, 1400, 60+cell_height, false, "left", "宋体", 120, "000");
		sText = show[i];
		icbcprint.printText(sText, 280, 60+cell_height, 1700, 760+cell_height, false, "left", "宋体", 120, "000");
		
		sText = "● 评价等级 ："+score[i];
		icbcprint.printText(sText, 280, 760+cell_height, 1400, 840+cell_height, false, "left", "宋体", 120, "010");
		//sText = score[i];
		//icbcprint.printText(sText, 420, 760+cell_height, 1400, 840+cell_height, false, "left", "宋体", 120, "010");
		cell_height = 860 +cell_height;
		
		i++;				
   }
   sText = "综合评价 ：";
   icbcprint.printText(sText, 200, cell_height, 1400, 80+cell_height, false, "left", "黑体", 140, "010");		
   sText = imshow;
   icbcprint.printText(sText, 280, 80+cell_height, 1700, 1000+cell_height, false, "left", "宋体", 120, "000");		
  // icbcprint.printEndTable(0);       
}
/*现金流量定量评价*/
function printCashflow05(name,show,imshow){
	var y=0;
    sText = "二、现金流量定量评价 ";
    icbcprint.printText(sText, 200, y, 1600, y+80, false, "left", "宋体", 140, "010");
    y=y+80;
    sText = "现金流量定量评价指标 ";
    icbcprint.printText(sText, 200, y, 1400, y+60, false, "left", "宋体", 120, "000");
    y=y+60;
   // icbcprint.printBeginTable(200, y+80, 1600, 3000, 1);
    
    var i=0;
    var cell_height = 80;
    for ( i=0; i<name.length; i++) {
		sText = name[i]+" : "+show[i];
		icbcprint.printText(sText, 250, y+i*cell_height,1700, y+60+i*cell_height, false, "left", "宋体", 120, "000");
		
		//sText = show[i];
		//icbcprint.printText(sText, 1000, i*cell_height, 1480,50+i*cell_height, false, "left", "宋体", 120, "000");
    }
    y=y+i*cell_height;
   sText = "综合评价 ：";
   icbcprint.printText(sText, 200, y, 1400, y+80, false, "left", "黑体", 140, "010");		
   sText = imshow;
   icbcprint.printText(sText, 280,y+80, 1700, y+1400, false, "left", "宋体", 120, "000");		
   //icbcprint.printEndTable(0);           
}
/*重大因素调整*/
function printImport(name,show,score){
	var y=0;
    sText = "第四节   减分因素调整 ";
    icbcprint.printText(sText, 100, y, 1800, y+80, false, "center", "宋体", 140, "010");
    y=y+80;
    sText = "一、重大事项 ";
    icbcprint.printText(sText, 200, y, 1400, y+80, false, "left", "宋体", 140, "010");
    y=y+80;
   // icbcprint.printBeginTable(200, y, 1600, y+3000, 1);
    //y=y+3000;
    var cell_height = y;
    
    var i=0;
    for (i=0; i<6; i++) {
		//alert("name[i]--"+name[i]+"show[i]--"+show[i]+"score[i]--"+score[i]);
		sText = (i+1)+"、 "+name[i];
		icbcprint.printText(sText, 200, cell_height, 1400, 60+cell_height, false, "left", "宋体", 120, "000");
		sText = show[i];
		icbcprint.printText(sText, 280, 60+cell_height, 1700, 700+cell_height, false, "left", "宋体", 120, "000");
		
		sText = "● 不利因素影响程度 ："+score[i];
		icbcprint.printText(sText, 280, 700+cell_height, 1400, 780+cell_height, false, "left", "宋体", 120, "010");
		//sText = score[i];
		//icbcprint.printText(sText, 600, 760+cell_height, 1400, 840+cell_height, false, "left", "宋体", 120, "010");
		cell_height = 800 +cell_height;
		
				
   }
   //新增重大关联交易
   sText = (7)+"、 "+name[8];
   icbcprint.printText(sText, 200, cell_height, 1400, 60+cell_height, false, "left", "宋体", 120, "000");
   sText = show[8];
   icbcprint.printText(sText, 280, 60+cell_height, 1700, 700+cell_height, false, "left", "宋体", 120, "000");
   sText = "● 不利因素影响程度 ："+score[8];
   icbcprint.printText(sText, 280, 700+cell_height, 1400, 780+cell_height, false, "left", "宋体", 120, "010");
   cell_height = 800 +cell_height;
   
   
   sText = "二、客户信用状况 ";
   icbcprint.printText(sText, 200, cell_height, 1400, 80+cell_height, false, "left", "宋体", 140, "010");		
   cell_height = 80+cell_height;
   
   for (var j=0; j<2; j++) {
		//alert("name[i]--"+name[i]+"show[i]--"+show[i]+"score[i]--"+score[i]);
		sText = (j+1)+"、 "+name[i];
		icbcprint.printText(sText, 200, cell_height, 1400, 60+cell_height, false, "left", "宋体", 120, "000");
		sText = show[i];
		icbcprint.printText(sText, 280, 60+cell_height, 1700, 700+cell_height, false, "left", "宋体", 120, "000");
		
		sText = "● 不利因素影响程度 ："+score[i];
		icbcprint.printText(sText, 280, 700+cell_height, 1400, 780+cell_height, false, "left", "宋体", 120, "010");
		//sText = score[i];
		//icbcprint.printText(sText, 600, 760+cell_height, 1400, 840+cell_height, false, "left", "宋体", 120, "010");
		cell_height = 800 +cell_height;
		
		i++;				
   }		
  // icbcprint.printEndTable(0);       
   icbcprint.printEndPage();
}
/*借款人中长期信用评级*/
function printFirstStep(score,levle,msg,show,sign){
	if (sign==1){
	
	    icbcprint.printStartPage();
	
	
}
	var y=150;
	
    sText = "第三章   借款人中长期信用评级 ";
    icbcprint.printText(sText, 100, y, 1800, y+80, false, "center", "宋体", 150, "010");
    //icbcprint.printBeginTable(200, y+80, 1600, y+800, 1);
    y=y+80;
    sText = "借款人中长期信用评级得分： "+score;
    icbcprint.printText(sText, 200, y, 1700, y+100, false, "left", "宋体", 120, "000");
    
   // sText = score;
    //icbcprint.printText(sText, 800, y, 1400, y+100, false, "left", "宋体", 120, "010");
    y=y+100;
    sText = "借款人中长期信用等级： "+levle;
    icbcprint.printText(sText, 200, y, 800, y+100, false, "left", "宋体", 120, "000");
   // sText = levle;
    //icbcprint.printText(sText, 800, y, 1400, y+100, false, "left", "宋体", 120, "010");
    
    y=y+100;
    
    /* update by zxf 2004-03-01   */
    sText = "借款人评级严重不匹配信息： ";
    icbcprint.printText(sText, 200, y, 800, y+150, false, "left", "宋体", 120, "000");
    
    y=y+160;
    
     for ( i=0; i<msg.length; i++) {
		sText = msg[i];
		if (sText =="") sText ="无";
		icbcprint.printText(sText, 260, y+i*60, 1700, y+60+i*60, false, "left", "宋体", 120, "010");
		
		
    }  
   y=y+60+i*60;
    
    sText = "综合评价 ";
    icbcprint.printText(sText, 200, y, 550, y+100, false, "left", "黑体", 140, "010"); 
    y=y+100;
    sText = show;
    if(sign==1){
    icbcprint.printText(sText, 300, y, 1700, y+900, false, "left", "宋体", 120, "000"); 
}
   if(sign==0){
    icbcprint.printText(sText, 300, y, 1700, y+900, false, "left", "宋体", 120, "000"); 
}
   // icbcprint.printEndTable(0); 
    if (sign==1){
       icbcprint.printEndPage();
}
}
/*项目特性定量评价*/
function printProj07(info,name,show,score,imshow){
	icbcprint.printStartPage();
	var y=150;
    var sText = "第四章   项目评价 ";
    icbcprint.printText(sText, 100, y, 1800, y+60, false, "center", "宋体", 150, "010");
    y=y+60;
    sText = "第一节   项目特性评价 ";
    icbcprint.printText(sText, 100, y, 1800, y+60, false, "center", "宋体", 140, "010");
    y=y+60;
    sText = "一、项目特性定量评价 ";
    icbcprint.printText(sText, 200, y, 1600, y+100, false, "left", "宋体", 140, "010");
    y=y+100;
    icbcprint.printBeginTable(0, y, 1600, y+400, 1);
    
    var i= 0;
    for (i=0; i<info.length-1; i++) {
		sText = info[i][0]+" : ";
		icbcprint.printText(sText, 200, i*100, 800, 100+100*i, true, "left", "宋体", 120, "000");
		sText = info[i][1];
		icbcprint.printText(sText, 800,i*100, 1680,100+100*i, true, "left", "宋体", 120, "000");
    }
      icbcprint.printEndTable(0);  
    y=y+400;
   sText = "综合评价 ：";
   icbcprint.printText(sText, 200, y+100, 1400, y+280, false, "left", "黑体", 140, "010");		
   sText = info[5];
   icbcprint.printText(sText, 280,y+280, 1700, y+1000, false, "left", "宋体", 120, "000");	
   y=1180;
    sText = "二、项目特性定性评价: ";
    icbcprint.printText(sText, 200, y, 1600, y+60, false, "left", "宋体", 140, "010");
    
    //icbcprint.printBeginTable(100, y+80, 1600, y+1000, 1);
    y=y+60;
    var cell_height = y;
    
    for (var i=0; i<name.length; i++) {
		//alert("name[i]--"+name[i]+"show[i]--"+show[i]+"score[i]--"+score[i]);
		sText = (i+1)+"、 "+name[i];
		icbcprint.printText(sText, 200, cell_height, 1400, 60+cell_height, false, "left", "宋体", 120, "000");
		sText = show[i];
		icbcprint.printText(sText, 280, 60+cell_height, 1700, 700+cell_height, false, "left", "宋体", 120, "000");
		
		sText = "● 评价等级 ："+score[i];
		icbcprint.printText(sText, 280, 700+cell_height, 1520, 780+cell_height, false, "left", "宋体", 120, "010");
		//sText = score[i];
		//icbcprint.printText(sText, 520, 760+cell_height, 1400, 840+cell_height, false, "left", "宋体", 120, "010");
		cell_height = 800 +cell_height;
		
				
   }
   sText = "综合评价 ：";
   icbcprint.printText(sText, 200, cell_height, 1400, 80+cell_height, false, "left", "黑体", 140, "010");		
   sText = imshow;
   icbcprint.printText(sText, 280, 80+cell_height, 1700, 800+cell_height, false, "left", "宋体", 120, "000");		
 
   	
 //  icbcprint.printEndTable(0);
  
   
}    
/*项目特性定性评价*/
/*
function printProp08(name,show,score,imshow){
	   
}
*/

/*------------------------贷款方式评价--------------------*/
function printLoan10(value,details,imshow){
	
    var sText = "第二节    贷款方式评价 ";
    icbcprint.printText(sText, 100, y, 1800, y+100, false, "center", "宋体", 140, "010");
    icbcprint.printBeginTable(0, y+100, 1600, y+580, 1);
    y=y+580;
    sText = "贷款方式类型 ";
    icbcprint.printText(sText, 200, 0, 600, 100, true, "center", "宋体", 120, "010");
    sText = "金额占比(%) ";
    icbcprint.printText(sText, 600, 0, 900, 100, true, "center", "宋体", 120, "010");
    sText = "贷款方式期限/贷款期限(%) ";
    icbcprint.printText(sText, 900, 0, 1200, 100, true, "center", "宋体", 120, "010");
    sText = "担保是否足额、可行 ";
    icbcprint.printText(sText, 1200, 0, 1500, 100, true, "center", "宋体", 120, "010");
    sText = "变现能力 ";
    icbcprint.printText(sText, 1500, 0, 1800, 100, true, "center", "宋体", 120, "010");
    
    
    if (value.length ==0 ){
        value[0]=[' ',' ',' ',' ',' '];
    }
    var cell_height = 80;     
    var i =0;
    for (i=0; i<value.length; i++) {
		sText = value[i][0];
		icbcprint.printText(sText, 200, 100+i*cell_height, 600, 180+i*cell_height, true, "center", "宋体", 105, "000");
		sText = value[i][1];
		icbcprint.printText(sText, 600, 100+i*cell_height, 900, 180+i*cell_height, true, "center", "宋体", 105, "000");
		sText = value[i][2];
		icbcprint.printText(sText, 900, 100+i*cell_height, 1200, 180+i*cell_height, true, "center", "宋体", 105, "000");
		sText = value[i][3];
		if (sText == '1' ){
		  sText = "是 ";
		}else if(sText == '0' ){
	      sText = "否 ";
	    }else if(sText == '0.5' ){
	      sText = "待确定";
	    }  
		icbcprint.printText(sText, 1200, 100+i*cell_height, 1500, 180+i*cell_height, true, "center", "宋体", 105, "000");
		sText = value[i][4];
		icbcprint.printText(sText, 1500, 100+i*cell_height, 1800, 180+i*cell_height, true, "center", "宋体", 105, "000");
    }  
    icbcprint.printEndTable(0);
    y=240+i*cell_height;
    for (i=0; i<value.length; i++) {
    	sText = value[i][0];
    	 icbcprint.printText(sText, 200, y, 1600, y+60, false, "left", "宋体", 120, "000");
    	 y=y+60;
	 sText=details[i];   	
 	 icbcprint.printText(sText, 280, y, 1700, y+700, false, "left", "宋体", 120, "000");
 		y=y+760;
	}
    
   sText = "综合评价 ：";
   icbcprint.printText(sText, 200, y, 1400, y+80, false, "left", "黑体", 140, "010");		
   y=y+80;
   sText = imshow;
   icbcprint.printText(sText, 280, y, 1700, y+860, false, "left", "宋体", 120, "000");		
   
}
/*--------避险措施及贡献度定性评价----------*/
function printTable11(name,show,score,imshow,riskpointName,riskpointShow){
	var y=0;
    sText = "第三节   避险措施及贡献度定性评价 ";
    icbcprint.printText(sText, 100, y, 1800, 50, false, "center", "宋体", 140, "010");
    sText = "一、贡献度评价 ";
    icbcprint.printText(sText, 200, y+80, 1400, 130, false, "left", "宋体", 140, "010");
    y=y+80;
   // icbcprint.printBeginTable(200, y+50, 1600, 3000, 1);
    var cell_height = y;
    
    for (var i=0; i<name.length; i++) {
		//alert("name[i]--"+name[i]+"show[i]--"+show[i]+"score[i]--"+score[i]);
		sText = (i+1)+"、 "+name[i];
		icbcprint.printText(sText, 200, cell_height, 1400, 60+cell_height, false, "left", "宋体", 120, "000");
		sText = show[i];
		icbcprint.printText(sText, 280, 60+cell_height, 1700, 700+cell_height, false, "left", "宋体", 120, "000");
		
		sText = "● 评价等级 ："+score[i];
		icbcprint.printText(sText, 280, 700+cell_height, 1700, 780+cell_height, false, "left", "宋体", 120, "010");
		//sText = score[i];
		//icbcprint.printText(sText, 420, 760+cell_height, 1400, 840+cell_height, false, "left", "宋体", 120, "010");
		cell_height = 800 +cell_height;
		
				
   }
   sText = "二、风险点及避险措施描述 ";
   icbcprint.printText(sText, 200, cell_height, 1400, 80+cell_height, false, "left", "宋体", 140, "010");		
   cell_height = 80+cell_height;
   
   for (var j=0; j<riskpointShow.length; j++) {
		//alert("name[i]--"+name[i]+"show[i]--"+show[i]+"score[i]--"+score[i]);
		sText = (j+1)+"、"+riskpointName[j] +": ";
		icbcprint.printText(sText, 200, cell_height, 1600, 60+cell_height, false, "left", "宋体", 120, "000");
		sText = riskpointShow[j];
		icbcprint.printText(sText, 280, 60+cell_height, 1700, 700+cell_height, false, "left", "宋体", 120, "000");
		cell_height = 700+cell_height;
   		
   }
   cell_height = 60+cell_height;
   
   sText = "避险措施描述 ：";
   icbcprint.printText(sText, 200, cell_height, 1400, 80+cell_height, false, "left", "黑体", 140, "010");		
   sText = imshow;
   icbcprint.printText(sText, 280, 80+cell_height, 1700, 800+cell_height, false, "left", "宋体", 120, "000");		
   //icbcprint.printEndTable(0);     
      icbcprint.printEndPage();   
}
/*中长期贷款信用评级*/
function printSecondStep(score,levle,show,risk,sign){
	if(sign==1){
	icbcprint.printStartPage();   
}
	y=250;
	sText = "第五章   中长期贷款信用评级";
	 icbcprint.printText(sText, 100, y, 1800, y+100, false, "center", "宋体", 150, "010");
	//icbcprint.printBeginTable(200, y+100, 1600, 600, 1);
    y=y+100;
    sText = "中长期贷款信用得分： "+score;
    icbcprint.printText(sText, 200, y, 1700, y+100, false, "left", "宋体", 120, "000");
   // sText = score;
    //icbcprint.printText(sText, 700, y, 1600, y+100, false, "left", "宋体", 120, "010");
    sText = "中长期贷款信用等级： "+levle;
    icbcprint.printText(sText, 200, y+100, 1700, y+200, false, "left", "宋体", 120, "000");
    //sText = levle;
    //icbcprint.printText(sText, 700, y+100, 1600, y+200, false, "left", "宋体", 120, "010");
    sText = "中长期贷款风险度: "+risk;
    icbcprint.printText(sText, 200, y+200, 1700, y+300, false, "left", "黑体", 120, "000"); 
   // sText = risk;
    //icbcprint.printText(sText, 700, y+200, 1600, y+300, false, "left", "黑体", 120, "010"); 
    
    //icbcprint.printEndTable(0); 
    sText = "综合评价 ";
    icbcprint.printText(sText, 200, y+300, 650, y+400, false, "left", "黑体", 140, "010"); 
    sText = show;
    icbcprint.printText(sText, 260, y+400, 1700, y+1200, false, "left", "宋体", 120, "000"); 
    
    	
    icbcprint.printEndPage();   

}
/*------------------------审查结论--------------------*/
function printFlowslot(value,sign){
	if(sign==1){
    icbcprint.printStartPage();
}
    var y=150;
    sText ="第六章     评级流程意见汇总";
    icbcprint.printText(sText, 100, y, 1600, y+60, false, "center", "宋体", 150, "010");
    icbcprint.printBeginTable(200, y+100, 1600, y+500, 1);
    y=y+500;
    sText = "柜员代码 ";
    icbcprint.printText(sText, 60, 0, 300, 100, true, "center", "宋体", 120, "010");
    sText = "柜员姓名 ";
    icbcprint.printText(sText, 300, 0, 500, 100, true, "center", "宋体", 120, "010");
    sText = "柜员角色 ";
    icbcprint.printText(sText, 500, 0, 800, 100, true, "center", "宋体", 120, "010");
    sText = "处理时间 ";
    icbcprint.printText(sText, 800, 0, 1300, 100, true, "center", "宋体", 120, "010");
    sText = "处理意见 ";
    icbcprint.printText(sText, 1300, 0, 1500, 100, true, "center", "宋体", 120, "010");
    
    if (value.length ==0 ){
        value[0]=[' ',' ',' ',' ',' ',' '];
    }
    var cell_height = 80;     
    var i =0;
    for (i=0; i<value.length; i++) {
		sText = value[i][0];
		icbcprint.printText(sText, 60, 100+i*cell_height, 300, 180+i*cell_height, true, "left", "宋体", 105, "000");
		sText = value[i][1];
		icbcprint.printText(sText, 300, 100+i*cell_height, 500, 180+i*cell_height, true, "left", "宋体", 105, "000");
		sText = value[i][2];
		icbcprint.printText(sText, 500, 100+i*cell_height, 800, 180+i*cell_height, true, "left", "宋体", 105, "000");
		var tempTime = value[i][3];
		sText = tempTime.substring(0,4)+"/"+tempTime.substring(4,6)+"/"+tempTime.substring(6,8)+"/"+tempTime.substring(8,10)+":"+tempTime.substring(10,12)+":"+tempTime.substring(12,14);
		icbcprint.printText(sText, 800, 100+i*cell_height, 1300, 180+i*cell_height, true, "left", "宋体", 105, "000");
		sText = value[i][4];
		icbcprint.printText(sText, 1300, 100+i*cell_height, 1500, 180+i*cell_height, true, "left", "宋体", 105, "000");
		
    }  
    
    icbcprint.printEndTable(0);
    y=240+i*cell_height;
    sText = "评级流程意见详细信息 ";
    icbcprint.printText(sText, 260, y, 1300, y+80, false, "left", "宋体", 140, "010");
    y=y+80;
    var cell_height =60;
   // icbcprint.printBeginTable(260, y, 1600, y+3000, 1);
    
    
    for (var i=0;i<value.length;i++){
    	sText = "柜员代码: "+value[i][0];
    	icbcprint.printText(sText, 200, cell_height, 1460, 60+cell_height, false, "lefe", "宋体", 120, "010");
    	//sText = value[i][0];
	//icbcprint.printText(sText,460, cell_height, 1000, 60+cell_height, false, "left", "宋体", 120, "000");
	sText = "柜员姓名: "+value[i][1];
    	icbcprint.printText(sText, 200, 60+cell_height, 1460, 120+cell_height, false, "lefe", "宋体", 120, "010");
    	//sText = value[i][1];
	//icbcprint.printText(sText,460, 60+cell_height, 1000, 120+cell_height, false, "left", "宋体", 120, "000");
	sText = "柜员角色: "+value[i][2];
    	icbcprint.printText(sText, 200, 120+cell_height, 1460, 180+cell_height, false, "lefe", "宋体", 120, "010");
    	//sText = value[i][2];
	//icbcprint.printText(sText,460, 120+cell_height, 1000, 180+cell_height, false, "left", "宋体", 120, "000");
	
	var tempTime = value[i][3];
	sText = tempTime.substring(0,4)+"/"+tempTime.substring(4,6)+"/"+tempTime.substring(6,8)+"/"+tempTime.substring(8,10)+":"+tempTime.substring(10,12)+":"+tempTime.substring(12,14);
	sText = "处理时间: "+sText;
    	icbcprint.printText(sText, 200, 180+cell_height, 1460, 240+cell_height, false, "lefe", "宋体", 120, "010");
    	//var tempTime = value[i][3];
	//sText = tempTime.substring(0,4)+"/"+tempTime.substring(4,6)+"/"+tempTime.substring(6,8)+"/"+tempTime.substring(8,10)+":"+tempTime.substring(10,12)+":"+tempTime.substring(12,14);
	//icbcprint.printText(sText,460, 180+cell_height, 1000, 240+cell_height, false, "left", "宋体", 120, "000");
	sText = "处理意见: "+value[i][4];
    	icbcprint.printText(sText, 200, 240+cell_height, 1460, 300+cell_height, false, "lefe", "宋体", 120, "010");
    	//sText = value[i][4];
	//icbcprint.printText(sText,460, 240+cell_height, 1000, 300+cell_height, false, "left", "宋体", 120, "000");
	sText = "处理说明: ";
    	icbcprint.printText(sText, 200, 300+cell_height, 1460, 360+cell_height, false, "lefe", "宋体", 120, "010");
    	sText = value[i][5];
    	if (sText!=""){
	icbcprint.printText(sText,260, 360+cell_height, 1700, 1000+cell_height, false, "left", "宋体", 120, "000");
	
    	cell_height=1060+cell_height;
	}else{
	sText="";
	icbcprint.printText(sText,260, 360+cell_height, 1500, 420+cell_height, false, "left", "宋体", 120, "000");
	cell_height=480+cell_height;
	}
	
	}
    
    
    
    
   
   // icbcprint.printEndTable(0);
  
   	
   icbcprint.printEndPage();

}



/*---------------打印简要评级报告----------------*/
/*打印封页*/
function printFirstPageSimple(client_name,proj_name,area_name,year,month,day){
    icbcprint.printStartPage();
	
	 var sText = "内部资料，严禁外传                                                      有效期12个月";
    
    icbcprint.printText(sText, 100, 50, 1900, 110, false, "left", "黑体", 120, "010");
	
	
   var sText = "";
    sText = client_name;
    var y=500;
    icbcprint.printText(sText, 0, y, 1950, y+160, false, "center", "黑体", 220, "010");
    y=y+160;
    sText = proj_name;
    icbcprint.printText(sText, 0, y, 1950, y+160, false, "center", "黑体", 220, "010");
    y=y+160;
    
    sText = "贷款评级报告摘要 ";
    icbcprint.printText(sText, 0, y, 1950, y+160, false, "center", "黑体", 220, "010");
    y=y+1200;
    
    icbcprint.printBeginTable(470, y, 1480, y+480, 0);
    y=y+480;

    sText = "评级行名称：";
    icbcprint.printText(sText, 0, 0, 450, 150, false, "left", "宋体", 180, "000");

    sText = area_name;
    icbcprint.printText(sText, 450, 0, 1400, 150, false, "left", "宋体", 180, "110");

    sText = "评级时间：";
    icbcprint.printText(sText, 0, 300, 450, 450, false, "left", "宋体", 180, "000");

    sText = year+"年"+month+"月"+day+"日";
    icbcprint.printText(sText, 450, 300, 1400, 450, false, "left", "宋体", 180, "110");

    icbcprint.printEndTable(0);

    icbcprint.printEndPage();
}

function printBaseInfoSimple(proj_name,custInfo,projInfo){
   
    icbcprint.printStartPage();
    
    
    
     var sText = proj_name+"项目 ";
    var y=250;
    icbcprint.printText(sText, 0, y, 1950, y+80, false, "center", "黑体", 170, "010");
    y=y+80;
    sText = "贷款评级报告摘要 ";
    icbcprint.printText(sText, 0, y, 1950, y+160, false, "center", "黑体", 170, "010");
    y=y+160;
    
    sText = "第一章   借款人及项目基本情况介绍 ";
    icbcprint.printText(sText, 100, y, 1800, y+80, false, "center", "宋体", 150, "010");
   
    y=y+80;
    sText = "第一节   客户基本情况 ";
    icbcprint.printText(sText, 100, y, 1800, y+80, false, "center", "宋体", 140, "010");
    y=y+80;
   
   icbcprint.printBeginTable(200, y, 1480, y+800, 0);
    y=y+800;
    for ( var i=0; i<custInfo.length; i++) {
		sText = custInfo[i][0]+" : ";
		icbcprint.printText(sText, 100, i*50, 600, 150+i*50, false, "left", "宋体", 120, "000");
		sText = custInfo[i][1];
		icbcprint.printText(sText, 600,i*50, 1480, 150+i*50, false, "left", "宋体", 120, "000");
    }
    
     sText = "第二节   项目基本情况介绍 ";
    icbcprint.printText(sText, 0, 150+i*50, 1700, 200+i*50, false, "center", "宋体", 140, "010");
    i++;
  var cell_height=200+i*50;
    for (var j=0; j<projInfo.length-2; j++) {
		sText = projInfo[j][0]+" : ";
		
		icbcprint.printText(sText, 100, cell_height, 880, 100+cell_height, false, "left", "宋体", 120, "000");
		
		sText = projInfo[j][1];
		icbcprint.printText(sText, 880,cell_height, 1600, 100+cell_height, false, "left", "宋体", 120, "000");
		i++;
		cell_height=100+cell_height;
    }
    icbcprint.printEndTable(0);
    
      icbcprint.printEndPage();
    icbcprint.printStartPage();
     sText = "● 申请报告(或其他重要说明) ";
    icbcprint.printText(sText, 200, 200, 1700, 260, false, "left", "宋体", 140, "010");
    sText = projInfo[9];
    icbcprint.printText(sText, 250, 260, 1700, 1000, false, "left", "宋体", 120, "000");
        
    sText = projInfo[10];
    
    if (sText!=""){
    
    
    icbcprint.printText(sText, 250, 1000, 1700, 2200, false, "left", "宋体", 120, "000");
}
    
    
}
   
   
/*打印第四页：财务指标*/
function printLoanorEconomySimple(economy,economy_name,beginYear){
    
    y=200;
   var sText = "第二章  借款人评价 ";
    icbcprint.printText(sText, 100, y, 1800, y+80, false, "center", "宋体", 150, "010");
    y=y+80;
     sText = "一、   借款人财务与管理评价 ";
    icbcprint.printText(sText, 200, y, 1600, y+50, false, "left", "宋体", 140, "010");
    
    y=y+50;
    sText = "(一)   借款人财务及管理定量评价: ";
    icbcprint.printText(sText, 200, y, 1600, y+50, false, "left", "宋体", 140, "000");
    y=y+50;
    sText = "借款人财务指标数据表： ";
    icbcprint.printText(sText, 200, y+50, 1400, y+50, false, "left", "宋体", 140, "000");
    y=y+50;
     icbcprint.printBeginTable(200, y, 1600, y+980, 1);
    y=y+980;
    sText = "指标名称 ";
    icbcprint.printText(sText, 0, 0, 190, 100, true, "center", "宋体", 120, "010");
    sText = (beginYear -3) +"年末 ";
    icbcprint.printText(sText, 190, 0, 380, 100, true, "center", "宋体", 120, "010");
    sText = (beginYear -2) +"年末 ";
    icbcprint.printText(sText, 380, 0, 570, 100, true, "center", "宋体", 120, "010");
    sText = (beginYear -1) +"年末 ";
    icbcprint.printText(sText, 570, 0, 760, 100, true, "center", "宋体", 120, "010");
    sText = "指标名称 ";
    icbcprint.printText(sText, 760, 0, 950, 100, true, "center", "宋体", 120, "010");
     sText = (beginYear -3) +"年末 ";
    icbcprint.printText(sText, 950, 0, 1140, 100, true, "center", "宋体", 120, "010");
     sText = (beginYear -2) +"年末 ";
    icbcprint.printText(sText, 1140, 0, 1330, 100, true, "center", "宋体", 120, "010");
     sText = (beginYear -1) +"年末 ";
    icbcprint.printText(sText, 1330, 0, 1520, 100, true, "center", "宋体", 120, "010");
    //alert("economy.length"+economy.length);
    if (economy.length ==0 ){
        economy[0]=[' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '];
        economy[1]=[' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '];
        economy[2]=[' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '];
    }else 
    if (economy.length ==1 ){
    	economy[1]=[' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '];
        economy[2]=[' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '];
    }else
    if (economy.length ==2 ){
        economy[2]=[' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '];
    }    
    var cell_height = 80;     
    for (var i=0; i<10; i++) {
		sText = economy_name[i];
		icbcprint.printText(sText, 0, 100+i*cell_height, 190, 180+i*cell_height, true, "center", "宋体", 105, "000");
		sText = economy[2][i];
		icbcprint.printText(sText, 190, 100+i*cell_height, 380, 180+i*cell_height, true, "center", "宋体", 105, "000");
		sText = economy[1][i];
		icbcprint.printText(sText, 380, 100+i*cell_height, 570, 180+i*cell_height, true, "center", "宋体", 105, "000");
		sText = economy[0][i];
		icbcprint.printText(sText, 570, 100+i*cell_height, 760, 180+i*cell_height, true, "center", "宋体", 105, "000");
		sText = economy_name[i+10];
		icbcprint.printText(sText, 760, 100+i*cell_height, 950, 180+i*cell_height, true, "center", "宋体", 105, "000");
		sText = economy[2][i+10];
		icbcprint.printText(sText, 950, 100+i*cell_height, 1140, 180+i*cell_height, true, "center", "宋体", 105, "000");
		sText = economy[1][i+10];
		icbcprint.printText(sText, 1140, 100+i*cell_height, 1330, 180+i*cell_height, true, "center", "宋体", 105, "000");
		sText = economy[0][i+10];
		icbcprint.printText(sText, 1330, 100+i*cell_height, 1520, 180+i*cell_height, true, "center", "宋体", 105, "000"); 
		//alert("economy_name[i]--"+economy_name[i]+"economy[2][i]--"+economy[2][i]+economy_name[i+10]+"---economy_name[i+10]---"+"economy[2][i+10]--"+economy[2][i+10]);
    }  
    icbcprint.printEndTable(0);
}

function printLoanorPropSimple(imshow){
	
    sText = "(二)  借款人财务及管理定性评价: ";
    icbcprint.printText(sText, 200, y, 1600, y+60, false, "left", "宋体", 140, "000");
    y=y+60;
   // icbcprint.printBeginTable(200, y, 1600, y+3000, 1);
    
    var cell_height = y;
    
   sText = "综合评价 ：";
   icbcprint.printText(sText, 200, cell_height, 1400, 80+cell_height, false, "left", "黑体", 140, "010");		
   sText = imshow;
   icbcprint.printText(sText, 280, 80+cell_height, 1700, 1200+cell_height, false, "left", "宋体", 120, "000");		
   //icbcprint.printEndTable(0);     
}
/*行业市场定性评价*/
function printMarketPropSimple(imshow){
	
    sText = "二、  行业市场评价 ";
    icbcprint.printText(sText, 200, y, 1600, y+60, false, "left", "宋体", 140, "010");
    y=y+60;
   // icbcprint.printBeginTable(200, y, 1600, y+3000, 1);
   
    var cell_height = y;
    
   sText = "综合评价 ：";
   icbcprint.printText(sText, 200, cell_height, 1400, 80+cell_height, false, "left", "黑体", 140, "010");		
   sText = imshow;
   icbcprint.printText(sText, 280, 80+cell_height, 1700, 1000+cell_height, false, "left", "宋体", 120, "000");		
  // icbcprint.printEndTable(0);     
}
/*现金流量定性评价*/
function printCashflow06Simple(imshow){
	var y=0;
    sText = "三、   现金流量评价 ";
    icbcprint.printText(sText, 200, y, 1600, y+80, false, "left", "宋体", 140, "010");
    y=y+80;
    sText = "(一)  现金流量定性评价 ";
    icbcprint.printText(sText, 200, y, 1400, y+80, false, "left", "宋体", 140, "000");
    y=y+80;
   // icbcprint.printBeginTable(200, y+50, 1500, 3000, 1);
    y=y+50;
    var cell_height = y;
     
    sText = "综合评价 ：";
   icbcprint.printText(sText, 200, cell_height, 1400, 60+cell_height, false, "left", "黑体", 140, "010");		
   sText = imshow;
   icbcprint.printText(sText, 280, 60+cell_height, 1700, 1000+cell_height, false, "left", "宋体", 120, "000");		
   //icbcprint.printEndTable(0);       
}
/*现金流量定量评价*/
function printCashflow05Simple(name,show,imshow){
	var y=0;
    sText = "(二)  现金流量定量评价 ";
    icbcprint.printText(sText, 200, y, 1600, y+80, false, "left", "宋体", 140, "000");
    y=y+80;
    sText = "现金流量定量评价指标 ";
    icbcprint.printText(sText, 200, y, 1400, y+80, false, "left", "宋体", 120, "000");
    y=y+80;
    //icbcprint.printBeginTable(200, y+80, 1600, 3000, 1);
    
    var i=0;
    var cell_height = 80;
    for ( i=0; i<name.length; i++) {
		sText = name[i]+" : "+show[i];
		icbcprint.printText(sText, 250, y+i*cell_height,1700, y+60+i*cell_height, false, "left", "宋体", 120, "000");
		
		//sText = show[i];
		//icbcprint.printText(sText, 800, i*cell_height, 1480,50+i*cell_height, false, "left", "宋体", 120, "000");
    }
    y=y+i*cell_height
   sText = "综合评价 ：";
   icbcprint.printText(sText, 200, y, 1400, y+60, false, "left", "黑体", 140, "010");		
   sText = imshow;
   icbcprint.printText(sText, 280,y+60, 1700, y+1400, false, "left", "宋体", 120, "000");		
  // icbcprint.printEndTable(0);  
          
}
/*项目特性定量评价*/
function printProj07Simple(info){
	
	var y=150;
    var sText = "第四章   项目评价 ";
    icbcprint.printText(sText, 100, y, 1800, y+80, false, "center", "宋体", 150, "010");
    y=y+80;
    sText = "一、   项目特性评价 ";
    icbcprint.printText(sText, 200, y, 1600, y+80, false, "left", "宋体", 140, "010");
    y=y+80;
    sText = "(一)  项目特性定量评价 ";
    icbcprint.printText(sText, 200, y, 1600, y+80, false, "left", "宋体", 140, "000");
    y=y+80;
    icbcprint.printBeginTable(200, y, 1600, y+300, 1);
    
    var i= 0;
    for (i=0; i<info.length-1; i++) {
		sText = info[i][0]+" : ";
		icbcprint.printText(sText, 0, i*100, 600, 100+100*i, true, "left", "宋体", 120, "000");
		sText = info[i][1];
		icbcprint.printText(sText, 600,i*100, 1480,100+100*i, true, "left", "宋体", 120, "000");
    }
    icbcprint.printEndTable(0);
    y=y+300;
   sText = "综合评价 ：";
   icbcprint.printText(sText, 200, y, 1400, y+80, false, "left", "黑体", 140, "010");		
   y=y+80;
   sText = info[5];
   icbcprint.printText(sText, 280,y, 1700, y+800, false, "left", "宋体", 120, "000");		
   
}    
/*项目特性定性评价*/
function printProp08Simple(imshow){
	var y=0;
    sText = "(二)  项目特性定性评价: ";
    icbcprint.printText(sText, 200, y, 1600, y+80, false, "left", "宋体", 140, "000");
    y=y+80;
   // icbcprint.printBeginTable(200, y, 1600, y+900, 1);
    
    var cell_height = y;
    
    sText = "综合评价 ：";
   icbcprint.printText(sText, 200, cell_height, 1400, 80+cell_height, false, "left", "黑体", 140, "010");		
   sText = imshow;
   icbcprint.printText(sText, 280, 80+cell_height, 1700, 860+cell_height, false, "left", "宋体", 120, "000");	
   
   y=900+cell_height;	
  // icbcprint.printEndTable(0);     
}


/*------------------------贷款方式评价--------------------*/
function printLoan10Simple(value,imshow){
	var y=0;
    var sText = "二、    贷款方式评价 ";
    icbcprint.printText(sText, 200, y, 1600, y+80, false, "left", "宋体", 140, "010");
    y=y+80;
    icbcprint.printBeginTable(200, y, 1600, y+380, 1);
    y+380;
    
    sText = "贷款方式类型 ";
    icbcprint.printText(sText, 0, 0, 300, 100, true, "center", "宋体", 120, "010");
    sText = "金额占比(%) ";
    icbcprint.printText(sText, 300, 0, 600, 100, true, "center", "宋体", 120, "010");
    sText = "贷款方式期限/贷款期限(%) ";
    icbcprint.printText(sText, 600, 0, 900, 100, true, "center", "宋体", 120, "010");
    sText = "担保是否足额、可行 ";
    icbcprint.printText(sText, 900, 0, 1200, 100, true, "center", "宋体", 120, "010");
    sText = "变现能力 ";
    icbcprint.printText(sText, 1200, 0, 1400, 100, true, "center", "宋体", 120, "010");
    if (value.length ==0 ){
        value[0]=[' ',' ',' ',' ',' '];
    }
    var cell_height = 80;     
    var i =0;
    for (i=0; i<value.length; i++) {
		sText = value[i][0];
		icbcprint.printText(sText, 0, 100+i*cell_height, 300, 180+i*cell_height, true, "center", "宋体", 105, "000");
		sText = value[i][1];
		icbcprint.printText(sText, 300, 100+i*cell_height, 600, 180+i*cell_height, true, "center", "宋体", 105, "000");
		sText = value[i][2];
		icbcprint.printText(sText, 600, 100+i*cell_height, 900, 180+i*cell_height, true, "center", "宋体", 105, "000");
		sText = value[i][3];
		if (sText == '1' ){
		  sText = "是 ";
		}else if(sText == '0' ){
	      sText = "否 ";
	    }else if(sText == '0.5' ){
	      sText = "待确定";
	    }  
		icbcprint.printText(sText, 900, 100+i*cell_height, 1200, 180+i*cell_height, true, "center", "宋体", 105, "000");
		sText = value[i][4];
		icbcprint.printText(sText, 1200, 100+i*cell_height, 1400, 180+i*cell_height, true, "center", "宋体", 105, "000");
    }  
    
    icbcprint.printEndTable(0);
   sText = "综合评价 ：";
   icbcprint.printText(sText, 200, y, 1400, y+80, false, "left", "黑体", 140, "010");		
   sText = imshow;
   icbcprint.printText(sText, 280, y+80, 1700, y+980, false, "left", "宋体", 120, "000");		
   
}
/*--------避险措施及贡献度定性评价----------*/
function printTable11Simple(name,show,score,imshow,riskpointName,riskpointShow){
	var y=0;
    sText = "三、   避险措施及贡献度定性评价 ";
    icbcprint.printText(sText, 200, y, 1600, 50, false, "left", "宋体", 140, "010");
    sText = "(一)   贡献度评价 ";
    icbcprint.printText(sText, 200, y+80, 1400, 130, false, "left", "宋体", 140, "000");
    y=y+80;
    //icbcprint.printBeginTable(200, y+50, 1600, 3000, 1);
    var cell_height = y;
    
    for (var i=0; i<name.length; i++) {
		//alert("name[i]--"+name[i]+"show[i]--"+show[i]+"score[i]--"+score[i]);
		sText = (i+1)+"、 "+name[i];
		icbcprint.printText(sText, 200, cell_height, 1400, 60+cell_height, false, "left", "宋体", 120, "000");
		sText = show[i];
		icbcprint.printText(sText, 280, 60+cell_height, 1700, 700+cell_height, false, "left", "宋体", 120, "000");
		cell_height = 700 +cell_height;
		
   }
   sText = "(二)  风险点及避险措施描述 ";
   icbcprint.printText(sText, 200, cell_height, 1400, 80+cell_height, false, "left", "宋体", 140, "000");		
   cell_height = 80+cell_height;
   
   for (var j=0; j<riskpointShow.length; j++) {
		//alert("name[i]--"+name[i]+"show[i]--"+show[i]+"score[i]--"+score[i]);
		sText = (j+1)+"、"+riskpointName[j] +": ";
		icbcprint.printText(sText, 200, cell_height, 1400, 60+cell_height, false, "left", "宋体", 120, "000");
		sText = riskpointShow[j];
		icbcprint.printText(sText, 280, 60+cell_height, 1700, 700+cell_height, false, "left", "宋体", 120, "000");
		cell_height = 700+cell_height;
   		
   }
   cell_height = 60+cell_height;
   
   sText = "避险措施描述 ：";
   icbcprint.printText(sText, 200, cell_height, 1400, 80+cell_height, false, "left", "黑体", 140, "010");		
   sText = imshow;
   icbcprint.printText(sText, 280, 80+cell_height, 1700, 760+cell_height, false, "left", "宋体", 120, "000");		
   //icbcprint.printEndTable(0);     
   
}

/*-------------------------------2005王拯打印------------------------------------------------*/
/*--------为老的代码该得--------*/
function printtitleforold(showtitle){
  var sText=showtitle;
  icbcprint.printText(sText, 0, 0, 1700, 0, false, "center", "宋体", 140, "010");
}

function printdxdlold(showhave,showname,showresult,showtype,showtitle,isendpage){
  
    var sText;
    sText = showtitle;
    icbcprint.printText(sText, 0, 0, 1950, 0, false, "center", "宋体", 150, "010");
  if(showhave!="0"){ 
    var type="";
    var cell_height=100;
    var san_num=0;
    
    for(var j=0;j<showname.length;j++){
      sText=showname[j];
      type=showtype[j];
     
      if(type=="0"||type=="1"){//如果是三级指标，序号加一
        san_num=san_num+1;
      }else if(type=="3"){
        san_num=san_num;//如果是评级等级，序号不变
      }else{
        san_num=0;//如果是其他的，序号清零
      }      
      
      if(type=="-2"){//一级指标
       icbcprint.printText(sText, 200, cell_height, 1700, 80+cell_height, false, "left", "宋体", 140, "010");		
      }else if(type=="-1"){//二级指标
       icbcprint.printText(sText, 220, cell_height, 1700, 80+cell_height, false, "left", "宋体", 130, "010");		
      }else if(type=="3"||type=="5"){//评价等级和综合评价
        icbcprint.printText(sText, 260, cell_height, 1700, 80+cell_height, false, "left", "黑体", 140, "010");		
      }else if(type=="4"){//行业评价中的节号
        icbcprint.printText(sText, 100, cell_height, 1600, 80+cell_height, false, "center", "宋体", 150, "010");		
      }else{//三级指标
        if(san_num!=0){//序号不是零，才显示
          sText=san_num+'、 '+sText;
        }
        icbcprint.printText(sText, 240, cell_height, 1700, 80+cell_height, false, "left", "宋体", 120, "000");		
      }
      cell_height=cell_height+150;
      sText=showresult[j];
      if(type=="3"){//评价等级显示
        icbcprint.printText(sText, 280, cell_height, 1800, 80+cell_height, false, "left", "宋体", 130, "010");		
      }else if(type=="5"){//综合评价
        icbcprint.printText(sText, 280, cell_height, 1800, 80+cell_height, false, "left", "宋体", 130, "000");		
      }else if(type=="0"||type=="1"){//说明
        //cell_height=cell_height+150;
        icbcprint.printText(sText, 280, cell_height, 1800, 80+cell_height, false, "left", "宋体", 120, "000");		
      }
      cell_height=cell_height+150;
    }
    
  }
  
  if(isendpage=="1"){
    icbcprint.printEndPage();
  }
}



/*----------新的代码-----------*/
function printdxdl2005(showhave,showname,showresult,showtype,showtitle,isendpage){
  
    var sText;
    sText = showtitle;
    icbcprint.printText(sText, 0, 0, 1950, 0, false, "center", "宋体", 150, "010");
  if(showhave!="0"){ 
    var type="";
    var cell_height=100;
    var san_num=0;
    
    for(var j=0;j<showname.length;j++){
      sText=showname[j];
      type=showtype[j];
     
      if(type=="0"||type=="1"){//如果是三级指标，序号加一
        san_num=san_num+1;
      }else if(type=="3"){
        san_num=san_num;//如果是评级等级，序号不变
      }else{
        san_num=0;//如果是其他的，序号清零
      }      
      
      if(type=="-2"){//一级指标
       icbcprint.printText(sText, 200, cell_height, 1700, 80+cell_height, false, "left", "宋体", 140, "010");		
      }else if(type=="-1"){//二级指标
       icbcprint.printText(sText, 220, cell_height, 1700, 80+cell_height, false, "left", "宋体", 130, "010");		
      }else if(type=="3"||type=="5"){//评价等级和综合评价
        icbcprint.printText(sText, 260, cell_height, 1700, 80+cell_height, false, "left", "黑体", 140, "010");		
      }else if(type=="4"){//行业评价中的节号
        icbcprint.printText(sText, 100, cell_height, 1600, 80+cell_height, false, "center", "宋体", 150, "010");		
      }else{//三级指标
        if(san_num!=0){//序号不是零，才显示
          sText=san_num+'、 '+sText;
        }
        icbcprint.printText(sText, 240, cell_height, 1700, 80+cell_height, false, "left", "宋体", 120, "010");		
      }
      cell_height=cell_height+150;
      sText=showresult[j];
      if(type=="3"){//评价等级显示
        icbcprint.printText(sText, 280, cell_height, 1800, 80+cell_height, false, "left", "宋体", 130, "010");		
      }else if(type=="5"){//综合评价
        icbcprint.printText(sText, 280, cell_height, 1800, 80+cell_height, false, "left", "宋体", 130, "000");		
      }else if(type=="0"||type=="1"){//说明
        //cell_height=cell_height+150;
        icbcprint.printText(sText, 280, cell_height, 1800, 80+cell_height, false, "left", "宋体", 120, "000");		
      }
      cell_height=cell_height+150;
    }
    
  }
  
  if(isendpage=="1"){
    icbcprint.printEndPage();
  }
}

function printtitle2005(showtitle){
  icbcprint.printStartPage();
  var sText=showtitle;
  icbcprint.printText(sText, 0, 0, 1950, 0, false, "center", "宋体", 160, "010");
}

function printLoanorEconomy2005(economy,economy_name,beginYear,economy_kind,showtitle,proj_name){
    icbcprint.printStartPage();
    var sText = "";
     var y=200;
    sText = proj_name+"项目 ";
    icbcprint.printText(sText, 0, y, 1950, y+80, false, "center", "黑体", 170, "010");
    y=y+80;
      sText = "贷款评级报告 ";
    icbcprint.printText(sText, 0, y, 1950, y+160, false, "center", "黑体", 170, "010");
    
    y=y+160;
     sText = "第一章   财务及管理评价 ";
    icbcprint.printText(sText, 0, y, 1950, 320, false, "center", "宋体", 150, "010");
      y=y+80;
    sText = showtitle;
    icbcprint.printText(sText, 0, y, 1950, y+100, false, "center", "宋体", 140, "010");
    sText = "借款人财务指标数据表： ";
    icbcprint.printText(sText, 200, 50, 1400, 50, false, "left", "宋体", 140, "000");
    
    icbcprint.printBeginTable(200, 100, 1800, 980, 1);
    sText = "指标名称 ";
    icbcprint.printText(sText, 0, 0, 190, 100, true, "center", "宋体", 120, "010");
    sText = (beginYear -3) +"年末 ";
    icbcprint.printText(sText, 190, 0, 380, 100, true, "center", "宋体", 120, "010");
    sText = (beginYear -2) +"年末 ";
    icbcprint.printText(sText, 380, 0, 570, 100, true, "center", "宋体", 120, "010");
    sText = (beginYear -1) +"年末 ";
    icbcprint.printText(sText, 570, 0, 760, 100, true, "center", "宋体", 120, "010");
    sText = "指标名称 ";
    icbcprint.printText(sText, 760, 0, 950, 100, true, "center", "宋体", 120, "010");
     sText = (beginYear -3) +"年末 ";
    icbcprint.printText(sText, 950, 0, 1140, 100, true, "center", "宋体", 120, "010");
     sText = (beginYear -2) +"年末 ";
    icbcprint.printText(sText, 1140, 0, 1330, 100, true, "center", "宋体", 120, "010");
     sText = (beginYear -1) +"年末 ";
    icbcprint.printText(sText, 1330, 0, 1520, 100, true, "center", "宋体", 120, "010");
    //alert("economy.length"+economy.length);
    if (economy.length ==0 ){
        economy[0]=[' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '];
        economy[1]=[' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '];
        economy[2]=[' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '];
    }else 
    if (economy.length ==1 ){
    	economy[1]=[' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '];
        economy[2]=[' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '];
    }else
    if (economy.length ==2 ){
        economy[2]=[' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '];
    }    
    var cell_height = 80;     
    var num;
    if(economy_kind=="0"){//一般工业类
      num=10;
    }else{
      num=11;
    }
    for (var i=0; i<num; i++) {
		sText = economy_name[i];
		icbcprint.printText(sText, 0, 100+i*cell_height, 190, 180+i*cell_height, true, "center", "宋体", 105, "000");
		sText = economy[2][i];
		icbcprint.printText(sText, 190, 100+i*cell_height, 380, 180+i*cell_height, true, "center", "宋体", 105, "000");
		sText = economy[1][i];
		icbcprint.printText(sText, 380, 100+i*cell_height, 570, 180+i*cell_height, true, "center", "宋体", 105, "000");
		sText = economy[0][i];
		icbcprint.printText(sText, 570, 100+i*cell_height, 760, 180+i*cell_height, true, "center", "宋体", 105, "000");
		sText = economy_name[i+num];
		icbcprint.printText(sText, 760, 100+i*cell_height, 950, 180+i*cell_height, true, "center", "宋体", 105, "000");
		sText = economy[2][i+num];
		icbcprint.printText(sText, 950, 100+i*cell_height, 1140, 180+i*cell_height, true, "center", "宋体", 105, "000");
		sText = economy[1][i+num];
		icbcprint.printText(sText, 1140, 100+i*cell_height, 1330, 180+i*cell_height, true, "center", "宋体", 105, "000");
		sText = economy[0][i+num];
		icbcprint.printText(sText, 1330, 100+i*cell_height, 1520, 180+i*cell_height, true, "center", "宋体", 105, "000"); 
		//alert("economy_name[i]--"+economy_name[i]+"economy[2][i]--"+economy[2][i]+economy_name[i+10]+"---economy_name[i+10]---"+"economy[2][i+10]--"+economy[2][i+10]);
    }  
    icbcprint.printEndTable(0);
}
//客户基项目基本情况
function printBaseInfo2005(proj_name,custInfo,projInfo,prokind){  
    icbcprint.printStartPage();  
    var sText;
     y=200;  
    sText = "客户基本情况 ";
    icbcprint.printText(sText, 0, y+160, 1950, 400, false, "center", "宋体", 140, "010");
    y=y+160;
     icbcprint.printBeginTable(100, y+80, 1480, 800, 0);
    y=y+80;
    
    for ( var i=0; i<custInfo.length; i++) {
		sText = custInfo[i][0]+" : ";	
		icbcprint.printText(sText, 100, i*50, 600, 150+i*50, false, "left", "宋体", 120, "000");
		sText = custInfo[i][1];
		icbcprint.printText(sText, 600,i*50, 1480, 150+i*50, false, "left", "宋体", 120, "000");
	  
    }
    
     sText = "项目基本情况介绍 ";
    icbcprint.printText(sText, 100, 150+15*50, 1750, 200+12*50, false, "center", "宋体", 140, "010");
    var cell_height=200+16*50;
    for (var j=0; j<projInfo.length-2; j++) {
      if(prokind!="1"||(j!=1&&j!=2)){//对于公路类的，006，007两项指标不显示
		 sText = projInfo[j][0]+" : ";		
		 icbcprint.printText(sText, 100, cell_height, 1000, 100+cell_height, false, "left", "宋体", 120, "000");		
		 sText = projInfo[j][1];
		 icbcprint.printText(sText, 1000,cell_height, 1700, 100+cell_height, false, "left", "宋体", 120, "000");
		 cell_height=100+cell_height;
	  }	
    }
    icbcprint.printEndTable(0);
    
    
    
    icbcprint.printEndPage();
    icbcprint.printStartPage();
     sText = "● 申请报告(或其他重要说明) ";
    icbcprint.printText(sText, 200, 200, 1700, 260, false, "left", "宋体", 140, "010");
    sText = projInfo[9];
    icbcprint.printText(sText, 250, 260, 1700, 1000, false, "left", "宋体", 120, "000");
        
    sText = projInfo[10];
    
    if (sText!=""){ 
      icbcprint.printText(sText, 250, 1000, 1700, 2200, false, "left", "宋体", 120, "000");
    }
    icbcprint.printEndPage();
}

function printhylist2005(hy_list_num,hy_list,hy_list_zhpj,showtitle){
   if(hy_list_num!=0){
      var sText;
      var y=0;
      sText = showtitle;
      icbcprint.printText(sText, 100, 0, 1600, 0, false, "center", "宋体", 150, "010");
      icbcprint.printBeginTable(100, y+100, 1600, y+580, 1);
      y=y+580;
      sText = "序号 ";
      icbcprint.printText(sText, 200, 0, 500, 100, true, "center", "宋体", 120, "010");
      sText = "行业名称 ";
      icbcprint.printText(sText, 500, 0, 1200, 100, true, "center", "宋体", 120, "010");
      sText = "行业占比(%)";
      icbcprint.printText(sText, 1200, 0, 1700, 100, true, "center", "宋体", 120, "010");
 
      var cell_height = 80;     
      var i =0;
      for (i=0; i<hy_list_num; i++) {
		sText = i+1;
		icbcprint.printText(sText, 200, 100+i*cell_height, 500, 180+i*cell_height, true, "center", "宋体", 105, "000");
		sText = hy_list[i][1];
		icbcprint.printText(sText, 500, 100+i*cell_height, 1200, 180+i*cell_height, true, "center", "宋体", 105, "000");
		sText = hy_list[i][2];
		icbcprint.printText(sText, 1200, 100+i*cell_height, 1700, 180+i*cell_height, true, "center", "宋体", 105, "000");
		
     }  
    icbcprint.printEndTable(0);
    y=240+i*cell_height;
   sText = "综合评价 ：";
   icbcprint.printText(sText, 260, y, 1400, y+80, false, "left", "黑体", 140, "010");		
   y=y+80;
   sText = hy_list_zhpj;
   icbcprint.printText(sText, 280, y, 1700, y+860, false, "left", "宋体", 120, "000");
    
   }else{
     icbcprint.printEndPage();//如果没有行业市场评价，需要翻页，和借款人评级分开
   } 
   
}   
   /*借款人中长期信用评级*/
function printFirstStep2005(score,levle,msg,show,sign){
	if (sign==1){
	    icbcprint.printStartPage();
    }
	var y=150;
	
    sText = "借款人中长期信用评级 ";
    icbcprint.printText(sText, 100, y, 1900, y+80, false, "center", "宋体", 150, "010");
    y=y+80;
    sText = "借款人中长期信用评级得分： "+score;
    icbcprint.printText(sText, 200, y, 1700, y+100, false, "left", "宋体", 120, "000");
    y=y+100;
    sText = "借款人中长期信用等级： "+levle;
    icbcprint.printText(sText, 200, y, 800, y+100, false, "left", "宋体", 120, "000");
    y=y+100;

    sText = "借款人评级严重不匹配信息： ";
    icbcprint.printText(sText, 200, y, 1800, y+150, false, "left", "宋体", 120, "000");
    y=y+160; 
     for ( i=0; i<msg.length; i++) {
		sText = msg[i];
		if (sText =="") sText ="无";
		icbcprint.printText(sText, 260, y+i*60, 1800, y+60+i*60, false, "left", "宋体", 120, "010");
		
		
    }  
    y=y+60+i*60;
    
    sText = "综合评价 ";
    icbcprint.printText(sText, 200, y, 550, y+100, false, "left", "黑体", 140, "010"); 
    y=y+100;
    sText = show;
    if(sign==1){
      icbcprint.printText(sText, 300, y, 1700, y+900, false, "left", "宋体", 120, "000"); 
    }
    if(sign==0){
      icbcprint.printText(sText, 300, y, 1700, y+900, false, "left", "宋体", 120, "000"); 
    }
    if (sign==1){
       icbcprint.printEndPage();
    }
}

/*------------------------贷款方式评价--------------------*/
function printLoan102005(value,details,imshow){
	
    var sText = "第一节    贷款方式评价 ";
    icbcprint.printText(sText, 100, y, 1800, y+100, false, "center", "宋体", 140, "010");
    icbcprint.printBeginTable(0, y+100, 1600, y+580, 1);
    y=y+580;
    sText = "贷款方式类型 ";
    icbcprint.printText(sText, 200, 0, 600, 100, true, "center", "宋体", 120, "010");
    sText = "金额占比(%) ";
    icbcprint.printText(sText, 600, 0, 900, 100, true, "center", "宋体", 120, "010");
    sText = "贷款方式期限/贷款期限(%) ";
    icbcprint.printText(sText, 900, 0, 1200, 100, true, "center", "宋体", 120, "010");
    sText = "担保是否足额、可行 ";
    icbcprint.printText(sText, 1200, 0, 1500, 100, true, "center", "宋体", 120, "010");
    sText = "变现能力 ";
    icbcprint.printText(sText, 1500, 0, 1800, 100, true, "center", "宋体", 120, "010");
    
    
    if (value.length ==0 ){
        value[0]=[' ',' ',' ',' ',' '];
    }
    var cell_height = 80;     
    var i =0;
    for (i=0; i<value.length; i++) {
		sText = value[i][0];
		icbcprint.printText(sText, 200, 100+i*cell_height, 600, 180+i*cell_height, true, "center", "宋体", 105, "000");
		sText = value[i][1];
		icbcprint.printText(sText, 600, 100+i*cell_height, 900, 180+i*cell_height, true, "center", "宋体", 105, "000");
		sText = value[i][2];
		icbcprint.printText(sText, 900, 100+i*cell_height, 1200, 180+i*cell_height, true, "center", "宋体", 105, "000");
		sText = value[i][3];
		if (sText == '1' ){
		  sText = "是 ";
		}else if(sText == '0' ){
		  sText = "否 ";
		}else{
		  sText = "待确定";
		} 
		icbcprint.printText(sText, 1200, 100+i*cell_height, 1500, 180+i*cell_height, true, "center", "宋体", 105, "000");
		sText = value[i][4];
		icbcprint.printText(sText, 1500, 100+i*cell_height, 1800, 180+i*cell_height, true, "center", "宋体", 105, "000");
    }  
    icbcprint.printEndTable(0);
    y=240+i*cell_height;
    for (i=0; i<value.length; i++) {
    	sText = value[i][0];
    	 icbcprint.printText(sText, 200, y, 1600, y+60, false, "left", "宋体", 120, "000");
    	 y=y+60;
	 sText=details[i];   	
 	 icbcprint.printText(sText, 280, y, 1700, y+700, false, "left", "宋体", 120, "000");
 		y=y+760;
	}
    
   sText = "综合评价 ：";
   icbcprint.printText(sText, 200, y, 1400, y+80, false, "left", "黑体", 140, "010");		
   y=y+80;
   sText = imshow;
   icbcprint.printText(sText, 280, y, 1700, y+980, false, "left", "宋体", 120, "000");		
   icbcprint.printEndPage();
}   
/*--------避险措施及贡献度定性评价----------*/
function printTable112005(name,show,score,imshow,riskpointName,riskpointShow){
	var y=0;
    sText = "第一节   避险措施及贡献度定性评价 ";
    icbcprint.printText(sText, 100, y, 1800, 50, false, "center", "宋体", 140, "010");
    sText = "一、贡献度评价 ";
    icbcprint.printText(sText, 200, y+80, 1400, 130, false, "left", "宋体", 140, "010");
    y=y+80;
    var cell_height = y;
    
    for (var i=0; i<name.length; i++) {
		sText = (i+1)+"、 "+name[i];
		icbcprint.printText(sText, 200, cell_height, 1400, 60+cell_height, false, "left", "宋体", 120, "010");
		sText = show[i];
		icbcprint.printText(sText, 280, 60+cell_height, 1700, 700+cell_height, false, "left", "宋体", 120, "000");
		
		sText = "● 评价等级 ："+score[i];
		icbcprint.printText(sText, 280, 700+cell_height, 1700, 780+cell_height, false, "left", "宋体", 120, "010");
		cell_height = 800 +cell_height;
		
				
   }
   sText = "二、风险点及避险措施描述 ";
   icbcprint.printText(sText, 200, cell_height, 1400, 80+cell_height, false, "left", "宋体", 140, "010");		
   cell_height = 80+cell_height;
   
   for (var j=0; j<riskpointShow.length; j++) {
		sText = (j+1)+"、"+riskpointName[j] +": ";
		icbcprint.printText(sText, 200, cell_height, 1600, 60+cell_height, false, "left", "宋体", 120, "010");
		sText = riskpointShow[j];
		icbcprint.printText(sText, 280, 60+cell_height, 1700, 700+cell_height, false, "left", "宋体", 120, "000");
		cell_height = 700+cell_height;
   		
   }
   cell_height = 60+cell_height;
   
   sText = "避险措施描述 ：";
   icbcprint.printText(sText, 200, cell_height, 1400, 80+cell_height, false, "left", "黑体", 140, "010");		
   sText = imshow;
   icbcprint.printText(sText, 280, 80+cell_height, 1700, 800+cell_height, false, "left", "宋体", 120, "000");		    
   icbcprint.printEndPage();   
}

/*中长期贷款信用评级*/
function printSecondStep2005(score,levle,show,risk,sign){
	if(sign==1){
	icbcprint.printStartPage();   
}
	y=250;
	sText = "中长期贷款信用评级";
	 icbcprint.printText(sText, 100, y, 1800, y+100, false, "center", "宋体", 150, "010");
	y=y+100;
    sText = "中长期贷款信用得分： "+score;
    icbcprint.printText(sText, 200, y, 1700, y+100, false, "left", "宋体", 120, "000");
    sText = "中长期贷款信用等级： "+levle;
    icbcprint.printText(sText, 200, y+100, 1700, y+200, false, "left", "宋体", 120, "000");
    sText = "中长期贷款风险度: "+risk;
    icbcprint.printText(sText, 200, y+200, 1700, y+300, false, "left", "黑体", 120, "000"); 
    sText = "综合评价 ";
    icbcprint.printText(sText, 200, y+300, 650, y+400, false, "left", "黑体", 140, "010"); 
    sText = show;
    icbcprint.printText(sText, 260, y+400, 1700, y+1200, false, "left", "宋体", 120, "000"); 	
    icbcprint.printEndPage();   

}
/*------------------------审查结论--------------------*/
function printFlowslot2005(value,sign){
	if(sign==1){
      icbcprint.printStartPage();
    }
    var y=150;
    sText ="评级流程意见汇总";
    icbcprint.printText(sText, 100, y, 1900, y+60, false, "center", "宋体", 150, "010");
    icbcprint.printBeginTable(200, y+100, 1600, y+500, 1);
    y=y+500;
    sText = "柜员代码 ";
    icbcprint.printText(sText, 60, 0, 300, 100, true, "center", "宋体", 120, "010");
    sText = "柜员姓名 ";
    icbcprint.printText(sText, 300, 0, 500, 100, true, "center", "宋体", 120, "010");
    sText = "柜员角色 ";
    icbcprint.printText(sText, 500, 0, 800, 100, true, "center", "宋体", 120, "010");
    sText = "处理时间 ";
    icbcprint.printText(sText, 800, 0, 1300, 100, true, "center", "宋体", 120, "010");
    sText = "处理意见 ";
    icbcprint.printText(sText, 1300, 0, 1500, 100, true, "center", "宋体", 120, "010");
    
    if (value.length ==0 ){
        value[0]=[' ',' ',' ',' ',' ',' '];
    }
    var cell_height = 80;     
    var i =0;
    for (i=0; i<value.length; i++) {
		sText = value[i][0];
		icbcprint.printText(sText, 60, 100+i*cell_height, 300, 180+i*cell_height, true, "left", "宋体", 105, "000");
		sText = value[i][1];
		icbcprint.printText(sText, 300, 100+i*cell_height, 500, 180+i*cell_height, true, "left", "宋体", 105, "000");
		sText = value[i][2];
		icbcprint.printText(sText, 500, 100+i*cell_height, 800, 180+i*cell_height, true, "left", "宋体", 105, "000");
		var tempTime = value[i][3];
		sText = tempTime.substring(0,4)+"/"+tempTime.substring(4,6)+"/"+tempTime.substring(6,8)+"/"+tempTime.substring(8,10)+":"+tempTime.substring(10,12)+":"+tempTime.substring(12,14);
		icbcprint.printText(sText, 800, 100+i*cell_height, 1300, 180+i*cell_height, true, "left", "宋体", 105, "000");
		sText = value[i][4];
		icbcprint.printText(sText, 1300, 100+i*cell_height, 1500, 180+i*cell_height, true, "left", "宋体", 105, "000");
		
    }  
    
    icbcprint.printEndTable(0);
    y=240+i*cell_height;
    sText = "评级流程意见详细信息 ";
    icbcprint.printText(sText, 260, y, 1300, y+80, false, "left", "宋体", 140, "010");
    y=y+80;
    var cell_height =60;
    for (var i=0;i<value.length;i++){
    	sText = "柜员代码: "+value[i][0];
    	icbcprint.printText(sText, 200, cell_height, 1460, 60+cell_height, false, "lefe", "宋体", 120, "010");
    	sText = "柜员姓名: "+value[i][1];
    	icbcprint.printText(sText, 200, 60+cell_height, 1460, 120+cell_height, false, "lefe", "宋体", 120, "010");
    	sText = "柜员角色: "+value[i][2];
    	icbcprint.printText(sText, 200, 120+cell_height, 1460, 180+cell_height, false, "lefe", "宋体", 120, "010");
    	
    	var tempTime = value[i][3];
	    sText = tempTime.substring(0,4)+"/"+tempTime.substring(4,6)+"/"+tempTime.substring(6,8)+"/"+tempTime.substring(8,10)+":"+tempTime.substring(10,12)+":"+tempTime.substring(12,14);
	    sText = "处理时间: "+sText;
    	icbcprint.printText(sText, 200, 180+cell_height, 1460, 240+cell_height, false, "lefe", "宋体", 120, "010");
    	sText = "处理意见: "+value[i][4];
    	icbcprint.printText(sText, 200, 240+cell_height, 1460, 300+cell_height, false, "lefe", "宋体", 120, "010");
    	sText = "处理说明: ";
    	icbcprint.printText(sText, 200, 300+cell_height, 1460, 360+cell_height, false, "lefe", "宋体", 120, "010");
    	sText = value[i][5];
    	if (sText!=""){
	      icbcprint.printText(sText,260, 360+cell_height, 1700, 1000+cell_height, false, "left", "宋体", 120, "000");
    	  cell_height=1060+cell_height;
	    }else{
	      sText="";
	      icbcprint.printText(sText,260, 360+cell_height, 1500, 420+cell_height, false, "left", "宋体", 120, "000");
	      cell_height=480+cell_height;
	    }	
	}
   icbcprint.printEndPage();

}

/*现金流量定量评价*/
function printCashflow05Simple2005(name,show){
	var y=0;
    sText = "(二)  现金流量定量评价 ";
    icbcprint.printText(sText, 200, y, 1600, y+80, false, "left", "宋体", 140, "000");
    y=y+80;
    sText = "现金流量定量评价指标 ";
    icbcprint.printText(sText, 200, y, 1400, y+80, false, "left", "宋体", 120, "000");
    y=y+80;
        
    var i=0;
    var cell_height = 80;
    for ( i=0; i<name.length-2; i++) {
		sText = name[i]+" : "+show[i];
		icbcprint.printText(sText, 250, y+i*cell_height,1700, y+60+i*cell_height, false, "left", "宋体", 120, "000");
    }
    y=y+i*cell_height
   sText = "综合评价 ：";
   icbcprint.printText(sText, 200, y, 1400, y+60, false, "left", "黑体", 140, "010");		
   sText = show[name.length-1];
   icbcprint.printText(sText, 280,y+60, 1700, y+1400, false, "left", "宋体", 120, "000");		  
          
}
/*项目特性定量评价*/
function printProj07Simple2005(showname_xm_dl,showresult_xm_dl){
	
	var y=150;
    var sText = "第四章   项目评价 ";
    icbcprint.printText(sText, 100, y, 1800, y+80, false, "center", "宋体", 150, "010");
    y=y+80;
    sText = "一、   项目特性评价 ";
    icbcprint.printText(sText, 200, y, 1600, y+80, false, "left", "宋体", 140, "010");
    y=y+80;
    sText = "(一)  项目特性定量评价 ";
    icbcprint.printText(sText, 200, y, 1600, y+80, false, "left", "宋体", 140, "000");
    y=y+80;
    icbcprint.printBeginTable(200, y, 1600, y+300, 1);
    
    var i= 0;
    for (i=0; i<showname_xm_dl.length-3; i++) {
		sText = showname_xm_dl[i]+" : ";
		icbcprint.printText(sText, 0, i*100, 600, 100+100*i, true, "left", "宋体", 120, "000");
		sText = showresult_xm_dl[i];
		icbcprint.printText(sText, 600,i*100, 1480,100+100*i, true, "left", "宋体", 120, "000");
    }
    icbcprint.printEndTable(0);
    y=y+300;
   sText = "综合评价 ：";
   icbcprint.printText(sText, 200, y, 1400, y+80, false, "left", "黑体", 140, "010");		
   y=y+80;
   sText = showresult_xm_dl[showresult_xm_dl.length-3];
   icbcprint.printText(sText, 280,y, 1700, y+800, false, "left", "宋体", 120, "000");		
   
}    
