/*打印封页*/
function printFirstPage(assess_year,assess_kind,client_name,approve_bank,approve_date){
    icbcprint.printStartPage();

    var sText = "";
    sText = assess_year + "年度年初信用等级评定报告";
    icbcprint.printText(sText, 150, 600, 1950, 700, false, "center", "黑体", 240, "010");

    sText = assess_kind;
    icbcprint.printText(sText, 150, 800, 1950, 900, false, "center", "黑体", 180, "010");

    icbcprint.printBeginTable(470, 1850, 1400, 150, 0);

    sText = "客户名称：";
    icbcprint.printText(sText, 0, 0, 340, 150, false, "left", "宋体", 180, "000");

    sText = client_name;
    icbcprint.printText(sText, 340, 0, 1400, 150, false, "left", "宋体", 180, "110");

    icbcprint.printEndTable(0);

    icbcprint.printBeginTable(470, 2000, 1400, 300, 0);

    sText = "送审单位：";
    icbcprint.printText(sText, 0, 0, 340, 150, false, "left", "宋体", 180, "000");

    sText = approve_bank;
    icbcprint.printText(sText, 340, 0, 1400, 150, false, "left", "宋体", 180, "110");

    sText = "送审时间：";
    icbcprint.printText(sText, 0, 150, 340, 300, false, "left", "宋体", 180, "000");

    var year = approve_date.substring(0,4);
    var month = approve_date.substring(4,6);
    var day = approve_date.substring(6,8);
    sText = year+"年"+month+"月"+day+"日";
    icbcprint.printText(sText, 340, 150, 1400, 300, false, "left", "宋体", 180, "110");

    icbcprint.printEndTable(0);

    icbcprint.printEndPage();
}

/*打印第二页：客户概况*/
function printCustInfo(cust_infoArry,stock_infoArry,form){
    icbcprint.printStartPage();

    var sText = "一、客户基本情况";
    icbcprint.printText(sText, 150, 300, 1950, 360, false, "left", "黑体", 140, "000");

    sText = "1、客户概况";
    icbcprint.printText(sText, 150, 370, 1950, 430, false, "left", "宋体", 120, "010");

    icbcprint.printBeginTable(218, 430, 1790, 715,0);

    sText = "客户名称:";
    icbcprint.printText(sText, 0, 0, 260, 65, false, "left", "宋体", 120, "000");

    sText = cust_infoArry[0];
    icbcprint.printText(sText, 260, 0, 1790, 65, false, "left", "宋体", 120, "000");

    sText = "客户地址：";
    icbcprint.printText(sText, 0, 65, 260, 130, false, "left", "宋体", 120, "000");

    sText = cust_infoArry[8];
    icbcprint.printText(sText, 260, 65, 1790, 130, false, "left", "宋体", 120, "000");

    sText = "客户成立时间：";
    icbcprint.printText(sText, 0, 130, 350, 195, false, "left", "宋体", 120, "000");

    var year = cust_infoArry[2].substring(0,4);
    var month = cust_infoArry[2].substring(4,6);
    var day = cust_infoArry[2].substring(6,8);
    sText = year+"年"+month+"月"+day+"日";
    icbcprint.printText(sText, 350, 130, 930, 195, false, "left", "宋体", 120, "000");

    if (form=="高校" || form=="中小学" || form=="医疗机构"){
    	sText = "基金：";
    }else{
    	sText = "实收资本：";
    }
    icbcprint.printText(sText, 930, 130, 1160, 195, false, "left", "宋体", 120, "000");

    sText = cust_infoArry[9]+"万元";
    icbcprint.printText(sText, 1160, 130, 1790, 195, false, "left", "宋体", 120, "000");

    sText = "所属行业：";
    icbcprint.printText(sText, 0, 195, 260, 260, false, "left", "宋体", 120, "000");

    sText = cust_infoArry[4];
    icbcprint.printText(sText, 260, 195, 930, 260, false, "left", "宋体", 120, "000");

    if (form!="高校" && form!="中小学" && form!="医疗机构"){
    	sText = "企业规模：";
    	icbcprint.printText(sText, 930, 195, 1160, 260, false, "left", "宋体", 120, "000");

    	sText = cust_infoArry[5];
    	icbcprint.printText(sText, 1160, 195, 1790, 260, false, "left", "宋体", 120, "000");
    }

    sText = "客户法定代表人：";
    if (form=="高校" || form=="中小学"){sText = "校长：";}
    if (form=="医疗机构"){sText = "院长：";}
    icbcprint.printText(sText, 0, 260, 390, 325, false, "left", "宋体", 120, "000");

    sText = cust_infoArry[1];
    icbcprint.printText(sText, 390, 260, 930, 325, false, "left", "宋体", 120, "000");

    sText = "组织形式：";
    icbcprint.printText(sText, 930, 260, 1160, 325, false, "left", "宋体", 120, "000");

    sText = cust_infoArry[10];
    icbcprint.printText(sText, 1160, 260, 1790, 325, false, "left", "宋体", 120, "000");

    if (form=="高校" || form=="中小学" || form=="医疗机构"){
    	sText = "服务地区：";
    }else{
    	sText = "主要产品及销售市场：";
    }
    icbcprint.printText(sText, 0, 325, 470, 390, false, "left", "宋体", 120, "000");

    sText = cust_infoArry[6];
    icbcprint.printText(sText, 470, 325, 1790, 390, false, "left", "宋体", 120, "000");

    if (form!="高校" && form!="中小学" && form!="医疗机构"){
    	sText = "集团客户：";
    	icbcprint.printText(sText, 0, 390, 260, 455, false, "left", "宋体", 120, "000");

    	sText = cust_infoArry[7];
    	icbcprint.printText(sText, 260, 390, 1790, 455, false, "left", "宋体", 120, "000");
    }

    sText = "经济性质：";
    icbcprint.printText(sText, 0, 455, 260, 520, false, "left", "宋体", 120, "000");

    sText = cust_infoArry[3];
    icbcprint.printText(sText, 260, 455, 800, 520, false, "left", "宋体", 120, "000");

    sText = "政策性呆滞呆帐贷款余额：";
    icbcprint.printText(sText, 800, 455, 1400, 520, false, "left", "宋体", 120, "000");

    sText = cust_infoArry[17]+"万元";
    icbcprint.printText(sText, 1400, 455, 1790, 520, false, "left", "宋体", 120, "000");

    sText = "政策性欠息：";
    icbcprint.printText(sText, 0, 520, 300, 585, false, "left", "宋体", 120, "000");

    sText = cust_infoArry[16]+"万元";
    icbcprint.printText(sText, 260, 520, 800, 585, false, "left", "宋体", 120, "000");

    sText = "政策性可疑损失贷款余额：";
    icbcprint.printText(sText, 800, 520, 1400, 585, false, "left", "宋体", 120, "000");

    sText = cust_infoArry[18]+"万元";
    icbcprint.printText(sText, 1400, 520, 1790, 585, false, "left", "宋体", 120, "000");

    sText = "相关文件批号：";
    icbcprint.printText(sText, 0, 585, 350, 650, false, "left", "宋体", 120, "000");

    sText = cust_infoArry[21];
    icbcprint.printText(sText, 350, 585, 1790, 650, false, "left", "宋体", 120, "000");

    sText = "年初信用等级：";
    icbcprint.printText(sText, 0, 650, 350, 715, false, "left", "宋体", 120, "000");

    sText = cust_infoArry[19];
    icbcprint.printText(sText, 350, 650, 800, 715, false, "left", "宋体", 120, "000");

    sText = "";
    var flag = cust_infoArry[20];
    if (flag=="0"){sText="不是";}
    if (flag=="1"){sText="是";}
    sText = sText+"新开户企业";
    if (flag==""){sText="";}
    icbcprint.printText(sText, 800, 650, 1400, 715, false, "left", "宋体", 120, "000");

    icbcprint.printEndTable(0);


    if (form!="高校" && form!="中小学" && form!="医疗机构"){

	    sText = "客户主要股东实际投资额及股权比例：";
	    icbcprint.printText(sText, 218, 1150, 1950, 1210, false, "left", "宋体", 120, "010");

	    var len = stock_infoArry.length;
	    icbcprint.printBeginTable(218, 1210, 1700, 60+60*len,0);

	    sText = "主要股东名称";
	    icbcprint.printText(sText, 0, 0, 930, 60, true, "left", "宋体", 120, "000");

	    sText = "实际投资额（万元）";
	    icbcprint.printText(sText, 930, 0, 1440, 60, true, "center", "宋体", 120, "000");

	    sText = "股权比例";
	    icbcprint.printText(sText, 1440, 0, 1700, 60, true, "center", "宋体", 120, "000");

	    var cell_height = 60;
	    for (var i=0; i<len; i++) {
			sText = stock_infoArry[i][0];
			icbcprint.printText(sText, 0, 60+i*cell_height, 930, 120+i*cell_height, true, "left", "宋体", 120, "000");

			sText = stock_infoArry[i][1];
	                //alert(sText);
			icbcprint.printText(sText, 930, 60+i*cell_height, 1440, 120+i*cell_height, true, "center", "宋体", 120, "000");

			sText = stock_infoArry[i][2];
			icbcprint.printText(sText, 1440, 60+i*cell_height, 1700, 120+i*cell_height, true, "center", "宋体", 120, "000");
	     }

	    icbcprint.printEndTable(0);
    }

    sText = "2、客户历史简介";
    icbcprint.printText(sText, 150, 1420, 1790, 1485, false, "left", "宋体", 120, "010");

    sText = cust_infoArry[11];
    icbcprint.printText(sText, 218, 1485, 1790, 1600, false, "left", "宋体", 120, "000");

}
/*打印第二页：财务指标*/
function printFinanceInfo(year,financial_nameArry,financial_dataArry2,financial_dataArry1,financial_dataArry0){

     sText = "3、主要财务数据";
     icbcprint.printText(sText, 150, 1660, 1950, 1725, false, "left", "宋体", 120, "010");

     sText = "客户近三年主要财务指标情况：";
     icbcprint.printText(sText, 160, 1760, 1950, 1825, false, "left", "宋体", 120, "000");

     icbcprint.printBeginTable(180, 1825, 1760, 620,1);

     sText = "指标";
     icbcprint.printText(sText, 0, 0, 560, 80, true, "center", "宋体", 100, "000");

     var num = parseInt(year)-3;
     sText = num+"年末";
     icbcprint.printText(sText, 560, 0, 960, 80, true, "center", "宋体", 100, "000");

     num = num+1;
     sText = num+"年末";
     icbcprint.printText(sText, 960, 0, 1360, 80, true, "center", "宋体", 100, "000");

     num = num+1;
     sText = num+"年末";
     icbcprint.printText(sText, 1360, 0, 1760, 80, true, "center", "宋体", 100, "000");

     var cell_height = 60;
     for (var i=0; i<financial_nameArry.length; i++) {
		sText = financial_nameArry[i];
		icbcprint.printText(sText, 0, 80+i*cell_height, 560, 140+i*cell_height, true, "left", "宋体", 100, "000");

		sText = financial_dataArry2[i];
		icbcprint.printText(sText, 560, 80+i*cell_height, 960, 140+i*cell_height, true, "center", "宋体", 100, "000");

		sText = financial_dataArry1[i];
		icbcprint.printText(sText, 960, 80+i*cell_height, 1360, 140+i*cell_height, true, "center", "宋体", 100, "000");

                sText = financial_dataArry0[i];
		icbcprint.printText(sText, 1360, 80+i*cell_height, 1760, 140+i*cell_height, true, "center", "宋体", 100, "000");

	}

     icbcprint.printEndTable(10);

     icbcprint.printEndPage();
}

/*打印第三页：信用评级计分表－定量评价得分明细(企业性质为“房开”，“建安”，“工商”)*/
function printDLscore2(guide_lineArry,power_scoreArry,dl_scoreArry,modi_quotietyArry,modi_scoreArry){
     icbcprint.printStartPage();

     var sText = "二、信用评级计分表";
     icbcprint.printText(sText, 150, 300, 1950, 360, false, "left", "黑体", 140, "000");

     sText = "定量评价得分明细";
     icbcprint.printText(sText, 150, 370, 1950, 460, false, "center", "宋体", 140, "010");

     var change = 0;
     if(guide_lineArry.length==10){change = 60;}

     icbcprint.printBeginTable(190, 460, 1700, 720+change, 1);

     sText = "评价内容";
     icbcprint.printText(sText, 0, 0, 300, 120, true, "center", "宋体", 100, "000");

     sText = "一、偿债能力状况（40分）";
     icbcprint.printText(sText, 0, 120, 300, 300, true, "center", "宋体", 100, "000");

     sText = "二、财务效益状况（32分）";
     icbcprint.printText(sText, 0, 300, 300, 420+change, true, "center", "宋体", 100, "000");

     sText = "三、资金营运状况（18分）";
     icbcprint.printText(sText, 0, 420+change, 300, 540+change, true, "center", "宋体", 100, "000");

     sText = "四、发展能力状况（10分）";
     icbcprint.printText(sText, 0, 540+change, 300, 660+change, true, "center", "宋体", 100, "000");

     sText = "合    计";
     icbcprint.printText(sText, 0, 660+change, 300, 720+change, true, "center", "宋体", 100, "000");

     sText = "基本指标";
     icbcprint.printText(sText, 300, 0, 1050, 60, true, "center", "宋体", 100, "000");

     sText = "指标";
     icbcprint.printText(sText, 300, 60, 850, 120, true, "center", "宋体", 100, "000");

     sText = "权数分";
     icbcprint.printText(sText, 850, 60, 1050, 120, true, "center", "宋体", 100, "000");

     sText = "基本得分";
     icbcprint.printText(sText, 1050, 0, 1250, 120, true, "center", "宋体", 100, "000");

     sText = "修正系数";
     icbcprint.printText(sText, 1250, 0, 1450, 120, true, "center", "宋体", 100, "000");

     sText = "修正后得分";
     icbcprint.printText(sText, 1450, 0, 1700, 120, true, "center", "宋体", 100, "000");

     var cell_height = 60;
     var total = 0;
     var flag = "no";
     for(var i=0;i<guide_lineArry.length;i++){
     	 sText = guide_lineArry[i];
     	 icbcprint.printText(sText, 300, 120+cell_height*i, 850, 180+cell_height*i, true, "left", "宋体", 100, "000");

     	 sText = power_scoreArry[i];
     	 icbcprint.printText(sText, 850, 120+cell_height*i, 1050, 180+cell_height*i, true, "center", "宋体", 100, "000");

     	 sText = dl_scoreArry[i];
     	 icbcprint.printText(sText, 1050, 120+cell_height*i, 1250, 180+cell_height*i, true, "center", "宋体", 100, "000");

         if(dl_scoreArry[i]!=""){total += parseFloat(dl_scoreArry[i]);flag = "yes";}

     }

     sText = "";
     icbcprint.printText(sText, 300, 660+change, 850, 720+change, true, "center", "宋体", 100, "0001");

     sText = "100";
     icbcprint.printText(sText, 850, 660+change, 1050, 720+change, true, "center", "宋体", 100, "000");

     if (flag=="no"){sText = "";}
     else{sText = total;}
     icbcprint.printText(sText, 1050, 660+change, 1250, 720+change, true, "center", "宋体", 100, "000");

     sText = modi_quotietyArry[0];
     icbcprint.printText(sText, 1250, 120, 1450, 300, true, "center", "宋体", 100, "000");

     sText = modi_quotietyArry[1];
     icbcprint.printText(sText, 1250, 300, 1450, 420+change, true, "center", "宋体", 100, "000");

     sText = modi_quotietyArry[2];
     icbcprint.printText(sText, 1250, 420+change, 1450, 540+change, true, "center", "宋体", 100, "000");

     sText = modi_quotietyArry[3];
     icbcprint.printText(sText, 1250, 540+change, 1450, 660+change, true, "center", "宋体", 100, "000");

     sText = modi_scoreArry[0];
     icbcprint.printText(sText, 1450, 120, 1700, 300, true, "center", "宋体", 100, "000");

     sText = modi_scoreArry[1];
     icbcprint.printText(sText, 1450, 300, 1700, 420+change, true, "center", "宋体", 100, "000");

     sText = modi_scoreArry[2];
     icbcprint.printText(sText, 1450, 420+change, 1700, 540+change, true, "center", "宋体", 100, "000");

     sText = modi_scoreArry[3];
     icbcprint.printText(sText, 1450, 540+change, 1700, 660+change, true, "center", "宋体", 100, "000");

     sText = "";
     icbcprint.printText(sText, 1250, 660+change, 1450, 720+change, true, "center", "宋体", 100, "0001");

     total = 0;
     flag = "no";
     for(var i=0;i<4;i++){
        if (modi_scoreArry[i]!=""){total += parseFloat(modi_scoreArry[i]);flag="yes";}
     }

     if (flag=="no"){sText = "";}
     else{sText = total;}
     icbcprint.printText(sText, 1450, 660+change, 1700, 720+change, true, "center", "宋体", 100, "000");


     icbcprint.printEndTable(10);

     icbcprint.printEndPage();
}
/*打印第三页：信用评级计分表－定量评价得分明细(企业性质为“高校”，“中小学”，“医疗机构”)*/
function printDLscore1(guide_lineArry,power_scoreArry,dl_scoreArry,modi_quotietyArry,modi_scoreArry){
     icbcprint.printStartPage();

     var sText = "二、信用评级计分表";
     icbcprint.printText(sText, 150, 300, 1950, 360, false, "left", "黑体", 140, "000");

     sText = "定量评价得分明细";
     icbcprint.printText(sText, 150, 370, 1950, 460, false, "center", "宋体", 140, "010");

     var change = 0;
     if(guide_lineArry.length==9){change = 60;}

     icbcprint.printBeginTable(190, 460, 1700, 600+change, 1);

     sText = "基本指标";
     icbcprint.printText(sText, 0, 0, 600, 60, true, "center", "宋体", 100, "000");

     sText = "总分";
     icbcprint.printText(sText, 600, 0, 1150, 60, true, "center", "宋体", 100, "000");

     sText = "基本得分";
     icbcprint.printText(sText, 1150, 0, 1700, 60, true, "center", "宋体", 100, "000");

     var cell_height = 60;
     var total = 0;
     var flag = "no";
     for(var i=0;i<guide_lineArry.length;i++){
     	 sText = guide_lineArry[i];
     	 icbcprint.printText(sText, 0, 60+cell_height*i, 600, 120+cell_height*i, true, "left", "宋体", 100, "000");

     	 sText = power_scoreArry[i];
     	 icbcprint.printText(sText, 600, 60+cell_height*i, 1150, 120+cell_height*i, true, "center", "宋体", 100, "000");

     	 sText = dl_scoreArry[i];
     	 icbcprint.printText(sText, 1150, 60+cell_height*i, 1700, 120+cell_height*i, true, "center", "宋体", 100, "000");

         if(dl_scoreArry[i]!=""){total += parseFloat(dl_scoreArry[i]);flag = "yes";}

     }

     sText = "合    计";
     icbcprint.printText(sText, 0, 540+change, 600, 600+change, true, "center", "宋体", 100, "000");

     sText = "40";
     icbcprint.printText(sText, 600, 540+change, 1150, 600+change, true, "center", "宋体", 100, "000");

     if (flag=="no"){sText = "";}
     else{sText = total;}
     icbcprint.printText(sText, 1150, 540+change, 1700, 600+change, true, "center", "宋体", 100, "000");

     icbcprint.printEndTable(10);

     icbcprint.printEndPage();
}

/*打印第三页：信用评级计分表－定性评价得分明细*/
function printDXscore(ckind,competeArry,manageArry,dealArry,creditArry,developArry,base_infoArry,wholescore){
     icbcprint.printStartPage();

     var sText = "定性评价得分明细";
     icbcprint.printText(sText, 150, 300, 1950, 360, false, "center", "宋体", 140, "010");

     var x=0;
     if (ckind=="房开"){x = base_infoArry[0].length;}
     var height = 140 + (competeArry[0].length + manageArry[0].length + dealArry[0].length + creditArry[0].length + developArry[0].length + x)*55 + 70;

     icbcprint.printBeginTable(250, 400, 1500, height, 1);

     //var sText = "定性评价得分明细";
     //icbcprint.printText(sText, 0, 0, 1500, 70, false, "center", "宋体", 140, "010");

     sText = "评价内容";
     icbcprint.printText(sText, 0, 0, 300, 70, true, "center", "宋体", 100, "000");

     sText = "评议指标";
     icbcprint.printText(sText, 300, 0, 800, 70, true, "center", "宋体", 100, "000");

     sText = "得分";
     icbcprint.printText(sText, 800, 0, 1150, 70, true, "center", "宋体", 100, "000");

     sText = "实际得分";
     icbcprint.printText(sText, 1150, 0, 1500, 70, true, "center", "宋体", 100, "000");

     var height2 = 70 ;
     var cell_height = 55;

     if (ckind=="房开"||ckind=="高校"||ckind=="中小学"||ckind=="医疗机构"){
     	sText = "基本情况";
     	var height1 = height2;
        var height2 = height1 + base_infoArry[0].length*55;
     	icbcprint.printText(sText, 0, height1, 300, height2, true, "center", "宋体", 100, "000");

     	for (i=0;i<base_infoArry[0].length;i++){
     	    sText = base_infoArry[0][i];
     	    icbcprint.printText(sText, 300, height1+cell_height*i, 800, height1+55+cell_height*i, true, "left", "宋体", 100, "000");

     	    sText = base_infoArry[1][i];
     	    icbcprint.printText(sText, 800, height1+cell_height*i, 1150, height1+55+cell_height*i, true, "center", "宋体", 100, "000");

     	    sText = base_infoArry[2][i];
     	    icbcprint.printText(sText, 1150, height1+cell_height*i, 1500, height1+55+cell_height*i, true, "center", "宋体", 100, "000");
        }
     }

     if (ckind=="高校"||ckind=="中小学"||ckind=="医疗机构"){
     	sText = "竞争能力";
     }else{
     	sText = "市场竞争力";
     }
     var height1 = height2;
     var height2 = height1 + competeArry[0].length*55;
     icbcprint.printText(sText, 0, height1, 300, height2, true, "center", "宋体", 100, "000");

     for (i=0;i<competeArry[0].length;i++){
     	 sText = competeArry[0][i];
     	 icbcprint.printText(sText, 300, height1+cell_height*i, 800, height1+55+cell_height*i, true, "left", "宋体", 100, "000");

     	 sText = competeArry[1][i];
     	 icbcprint.printText(sText, 800, height1+cell_height*i, 1150, height1+55+cell_height*i, true, "center", "宋体", 100, "000");

     	 sText = competeArry[2][i];
     	 icbcprint.printText(sText, 1150, height1+cell_height*i, 1500, height1+55+cell_height*i, true, "center", "宋体", 100, "000");
     }

     sText = "管理水平";
     if (ckind=="建安"||ckind=="房开"||ckind=="高校"||ckind=="中小学"||ckind=="医疗机构") {sText = "管理状况";}
     var height1 = height2;
     var height2 = height1 + manageArry[0].length*55;
     icbcprint.printText(sText, 0, height1, 300, height2, true, "center", "宋体", 100, "000");

     for (i=0;i<manageArry[0].length;i++){
     	 sText = manageArry[0][i];
     	 icbcprint.printText(sText, 300, height1+cell_height*i, 800, height1+55+cell_height*i, true, "left", "宋体", 100, "000");

     	 sText = manageArry[1][i];
     	 icbcprint.printText(sText, 800, height1+cell_height*i, 1150, height1+55+cell_height*i, true, "center", "宋体", 100, "000");

     	 sText = manageArry[2][i];
     	 icbcprint.printText(sText, 1150, height1+cell_height*i, 1500, height1+55+cell_height*i, true, "center", "宋体", 100, "000");
     }

     sText = "经营状况";
     if (ckind=="房开") {sText = "环境状况";}
     if (ckind=="高校"||ckind=="中小学"||ckind=="医疗机构") {sText = "区域环境";}
     var height1 = height2;
     var height2 = height1 + dealArry[0].length*55;
     icbcprint.printText(sText, 0, height1, 300, height2, true, "center", "宋体", 100, "000");

     for (i=0;i<dealArry[0].length;i++){
     	 sText = dealArry[0][i];
     	 icbcprint.printText(sText, 300, height1+cell_height*i, 800, height1+55+cell_height*i, true, "left", "宋体", 100, "000");

     	 sText = dealArry[1][i];
     	 icbcprint.printText(sText, 800, height1+cell_height*i, 1150, height1+55+cell_height*i, true, "center", "宋体", 100, "000");

     	 sText = dealArry[2][i];
     	 icbcprint.printText(sText, 1150, height1+cell_height*i, 1500, height1+55+cell_height*i, true, "center", "宋体", 100, "000");
     }

     sText = "信誉状况";
     if (ckind=="高校"||ckind=="中小学"||ckind=="医疗机构") {sText = "与我行关系";}
     var height1 = height2;
     var height2 = height1 + creditArry[0].length*55;
     icbcprint.printText(sText, 0, height1, 300, height2, true, "center", "宋体", 100, "000");

     for (i=0;i<creditArry[0].length;i++){
     	 sText = creditArry[0][i];
     	 icbcprint.printText(sText, 300, height1+cell_height*i, 800, height1+55+cell_height*i, true, "left", "宋体", 100, "000");

     	 sText = creditArry[1][i];
     	 icbcprint.printText(sText, 800, height1+cell_height*i, 1150, height1+55+cell_height*i, true, "center", "宋体", 100, "000");

     	 sText = creditArry[2][i];
     	 icbcprint.printText(sText, 1150, height1+cell_height*i, 1500, height1+55+cell_height*i, true, "center", "宋体", 100, "000");
     }

     sText = "发展前景";
     var height1 = height2;
     var height2 = height1 + developArry[0].length*55;
     icbcprint.printText(sText, 0, height1, 300, height2, true, "center", "宋体", 100, "000");

     for (i=0;i<developArry[0].length;i++){
     	 sText = developArry[0][i];
     	 icbcprint.printText(sText, 300, height1+cell_height*i, 800, height1+55+cell_height*i, true, "left", "宋体", 100, "000");

     	 sText = developArry[1][i];
     	 icbcprint.printText(sText, 800, height1+cell_height*i, 1150, height1+55+cell_height*i, true, "center", "宋体", 100, "000");

     	 sText = developArry[2][i];
     	 icbcprint.printText(sText, 1150, height1+cell_height*i, 1500, height1+55+cell_height*i, true, "center", "宋体", 100, "000");
     }

     sText = "定性评价总得分";
     var height1 = height2;
     var height2 = height1+70;
     icbcprint.printText(sText, 0, height1, 800, height2, true, "center", "宋体", 120, "000");

     sText = wholescore;
     icbcprint.printText(sText, 800, height1, 1500, height2, true, "center", "宋体", 120, "000");

     icbcprint.printEndTable(10);

     icbcprint.printEndPage();
}
/*20050217by wufei增加打印第四页：波动明细*/
function printBDscore(bdguide_lineArry,bd_scoreArry,bd_Arry){
     icbcprint.printStartPage();

     sText = "波动性调整得分明细";
     icbcprint.printText(sText, 150, 370, 1950, 460, false, "center", "宋体", 140, "010");

    var change = 0;
     if(guide_lineArry.length==11){change = 60;}

     icbcprint.printBeginTable(190, 460, 1700, 670+change, 1);

    sText = "基本指标";
	     icbcprint.printText(sText, 0, 0, 400, 120, true, "center",  "宋体", 100, "000");

	     sText = "波动标准差";
	     icbcprint.printText(sText, 400, 0, 850, 120, true, "center",  "宋体", 100, "000");


	     sText = "波动性评价内容";
	     icbcprint.printText(sText, 850, 0, 1220, 120, true, "center",  "宋体", 100, "000");

	     sText = "得分";
	     
	     icbcprint.printText(sText, 1220, 0, 1700, 120, true, "center",  "宋体", 100, "000");


     var cell_height = 60;
     var i=0
     for( i;i<bdguide_lineArry.length;i++){
     	 sText = bdguide_lineArry[i];
     	 icbcprint.printText(sText, 0, 120+cell_height*i, 400, 180+cell_height*i, true, "left", "宋体", 100, "000");

     	 sText = bd_scoreArry[i];
     	 icbcprint.printText(sText, 400, 120+cell_height*i, 850, 180+cell_height*i, true, "center", "宋体", 100, "000");
     }
         sText = "有形资产总额a";
	     icbcprint.printText(sText,850, 120,1220, 180, true, "center",  "宋体", 100, "000");
         sText = bd_Arry[0];
	     icbcprint.printText(sText, 1220, 120, 1700, 180, true, "center",  "宋体", 100, "000");

         sText = "销售收入b";
	     icbcprint.printText(sText,850, 180,1220, 240, true, "center",  "宋体", 100, "000");
         sText = bd_Arry[1];
	     icbcprint.printText(sText, 1220, 180, 1700, 240, true, "center",  "宋体", 100, "000");
	     
	     sText = "规模指标K";
	     icbcprint.printText(sText,850, 240,1220, 300, true, "center",  "宋体", 100, "000");
         sText = bd_Arry[2];
	     icbcprint.printText(sText, 1220, 240, 1700, 300, true, "center",  "宋体", 100, "000");
	     
	     sText = "波动性调整结果Z";
	     icbcprint.printText(sText,850, 60+cell_height*i,1220, 120+cell_height*i, true, "center",  "宋体", 100, "000");
         sText = bd_Arry[3];
	     icbcprint.printText(sText, 1220, 60+cell_height*i, 1700, 120+cell_height*i, true, "center",  "宋体", 100, "000");
	     
	     
	     for( var j=0;j<i-3;j++){
     	 sText = "      ";
     	 icbcprint.printText(sText, 850, 240+cell_height*j, 1220, 300+cell_height*j, true, "left", "宋体", 100, "000");

     	 sText = "     ";
     	 icbcprint.printText(sText, 1220, 240+cell_height*j, 1700, 300+cell_height*j, true, "center", "宋体", 100, "000");
     }

     icbcprint.printEndTable(10);

     icbcprint.printEndPage();
}
/*打印第四页：评级结论*/
function pringEvaluateResult(rdate,evaluate_infoArry,opinion_infoArry,adjustArry,ckind){
     icbcprint.printStartPage();

     var sText = "三、评级结论";
     icbcprint.printText(sText, 150, 300, 1950, 360, false, "left", "黑体", 140, "000");

     if (ckind=="高校" || ckind=="中小学" || ckind=="医疗机构"){
	     icbcprint.printBeginTable(180, 370, 1700, 620, 0);

	     sText = "   财务报表时间";
	     icbcprint.printText(sText, 0, 0, 400, 70, true, "left",  "宋体", 100, "000");

	     var year = rdate.substring(0,4);
	     var month = rdate.substring(4,6);
	     sText = "   " + year + "年" + month + "月";
	     icbcprint.printText(sText, 400, 0, 1700, 70, true, "left",  "宋体", 100, "000");

	     sText = "   定量评价得分";
	     icbcprint.printText(sText, 0, 70, 400, 140, true, "left",  "宋体", 100, "000");

	     sText = "   " + evaluate_infoArry[0];
	     icbcprint.printText(sText, 400, 70, 1700, 140, true, "left",  "宋体", 100, "000");

	     sText = "   定性评价得分";
	     icbcprint.printText(sText, 0, 140, 400, 210, true, "left",  "宋体", 100, "000");

	     sText = "   " + evaluate_infoArry[1];
	     icbcprint.printText(sText, 400, 140, 1700, 210, true, "left",  "宋体", 100, "000");

	     sText = "   特别规定调整：";
	     icbcprint.printText(sText, 0, 210, 400, 280, true, "left",  "宋体", 100, "000");

	     var flag = "0";
	     for(var i=0;i<7;i++){
	     	if(adjustArry[i]!=""){flag = "1";}
	     }
	     if (flag=="1"){ sText = "   " + "有";}
	     if (flag=="0"){ sText = "   " + "无";}
	     icbcprint.printText(sText, 400, 210, 1700, 280, true, "left",  "宋体", 100, "000");

	     sText = "   调整项目：";
	     icbcprint.printText(sText, 0, 280, 400, 840, true, "left",  "宋体", 100, "000");

	     sText = "";

	     for(i=0;i<7;i++){
	     	 if (adjustArry[i]!=""){
	     	 	sText += adjustArry[i]+"\n";
	     	 }
	     }
	     icbcprint.printText(sText, 400, 280, 1700, 840, true, "left",  "宋体", 100, "000");

	     sText = "   应授信用等级为";
	     icbcprint.printText(sText, 0, 840, 850, 910, true, "left",  "宋体", 100, "000");

	     sText =evaluate_infoArry[20];;
	     icbcprint.printText(sText, 850, 840, 1700, 910, true, "center",  "宋体", 100, "000");

	     icbcprint.printEndTable(10);
     }else{
	     icbcprint.printBeginTable(180, 370, 1700, 620, 0);

	     sText = "   财务报表时间";
	     icbcprint.printText(sText, 0, 0, 400, 70, true, "left",  "宋体", 100, "000");

	     var year = rdate.substring(0,4);
	     var month = rdate.substring(4,6);
	     sText = year+"年"+month+"月";
	     icbcprint.printText(sText, 400, 0, 850, 70, true, "center",  "宋体", 100, "000");


	     sText = "   评议疑问标识";
	     icbcprint.printText(sText, 850, 0, 1220, 70, true, "left",  "宋体", 100, "000");

	     sText = "";
	     var flag = evaluate_infoArry[6];
	     if (flag=="0"){sText = "定性得分高于定量得分25%";}
	     if (flag=="1"){sText = "定性得分底于定量得分25%";}
	     icbcprint.printText(sText, 1220, 0, 1700, 70, true, "center",  "宋体", 100, "000");

	     sText = "   定量评价得分";
	     icbcprint.printText(sText, 0, 70, 400, 140, true, "left",  "宋体", 100, "000");

	     sText = evaluate_infoArry[0];
	     icbcprint.printText(sText, 400, 70, 850, 140, true, "center",  "宋体", 100, "000");

	     sText = "   行业调整系数";
	     icbcprint.printText(sText, 850, 70, 1220, 140, true, "left",  "宋体", 100, "000");

	     sText = evaluate_infoArry[7];
	     icbcprint.printText(sText, 1220, 70, 1700, 140, true, "center",  "宋体", 100, "000");

	     sText = "   定性评价得分";
	     icbcprint.printText(sText, 0, 140, 400, 210, true, "left",  "宋体", 100, "000");

	     sText = evaluate_infoArry[1];
	     icbcprint.printText(sText, 400, 140, 850, 210, true, "center",  "宋体", 100, "000");

	     sText = "   行业调整后得分";
	     icbcprint.printText(sText, 850, 140, 1220, 210, true, "left",  "宋体", 100, "000");

	     sText = evaluate_infoArry[8];
	     icbcprint.printText(sText, 1220, 140, 1700, 210, true, "center",  "宋体", 100, "000");

	     sText = "   加分后得分";
	     icbcprint.printText(sText, 0, 210, 400, 280, true, "left",  "宋体", 100, "000");

	     sText = evaluate_infoArry[14];
	     icbcprint.printText(sText, 400, 210, 850, 280, true, "center",  "宋体", 100, "000");

	     sText = "";
	     icbcprint.printText(sText, 850, 210, 1700, 280, true, "center",  "宋体", 100, "0001");

	     sText = "   特别规定调整：";
	     icbcprint.printText(sText, 0, 280, 400, 350, true, "left",  "宋体", 100, "000");

	     var flag = "0";
	     for(var i=0;i<7;i++){
	     	if(adjustArry[i]!=""){flag = "1";}
	     }
	     if (flag=="1"){ sText = "  有";}
	     if (flag=="0"){ sText = "  无";}
	     icbcprint.printText(sText, 400, 280, 1700, 350, true, "left",  "宋体", 100, "000");

	     sText = "   调整项目：";
	     icbcprint.printText(sText, 0, 350, 400, 840, true, "left",  "宋体", 100, "000");

	     sText = "";

	     for(i=0;i<7;i++){
	     	 if (adjustArry[i]!=""){
	     	 	sText += adjustArry[i]+"\n";
	     	 }
	     }
	     icbcprint.printText(sText, 400, 350, 1700, 840, true, "left",  "宋体", 100, "000");

	     sText = "   应授信用等级为";
	     icbcprint.printText(sText, 0, 840, 850, 910, true, "left",  "宋体", 100, "000");

	     sText =evaluate_infoArry[20];;
	     icbcprint.printText(sText, 850, 840, 1700, 910, true, "center",  "宋体", 100, "000");

	     icbcprint.printEndTable(10);
     }

     sText = "对客户信用状况的综合评价（如果建议调整评级结果，请说明支持调级的理由，特别要对重大事项进行说明）";
     icbcprint.printText(sText, 160, 1410, 1900, 1520, false, "left",  "宋体", 120, "010");

     var cell_height = 370;
     for(var i=0;i<opinion_infoArry.length;i++){
         icbcprint.printBeginTable(150, 1520+cell_height*i, 1750, 70, 1);

         sText = opinion_infoArry[i][4];  //评级人地区级别
         icbcprint.printText(sText, 0, 0, 210, 70,  false, "left",  "宋体", 120, "000");

         sText = opinion_infoArry[i][5]+"   意见：";
         icbcprint.printText(sText, 210, 0, 1750, 70, false, "left",  "宋体", 120, "000");

         icbcprint.printEndTable(0);

         sText = opinion_infoArry[i][7];
         icbcprint.printText(sText, 250, 1590+cell_height*i, 1900, 1660+cell_height*i, false, "left",  "宋体", 120, "000");

         icbcprint.printBeginTable(150, 1660+cell_height*i, 1750, 210, 1);

         sText = "据以上分析，建议授予该客户信用等级为：";
         icbcprint.printText(sText, 0, 0, 850, 70, false, "left",  "宋体", 120, "000");

         sText = "  "+opinion_infoArry[i][9]+"  ";
         icbcprint.printText(sText, 850, 0, 1650, 70, false, "right",  "宋体", 120, "100");

         sText = "级";
         icbcprint.printText(sText, 1650, 0, 1750, 70, false, "left",  "宋体", 120, "000");

         sText = opinion_infoArry[i][5]; //评级人角色
         icbcprint.printText(sText, 0, 70, 1400, 140, false, "right",  "宋体", 120, "000");

         sText = "  "+opinion_infoArry[i][1]+"  "; //姓名
         icbcprint.printText(sText, 1400, 70, 1750, 140, false, "center",  "宋体", 120, "100");

         var year = opinion_infoArry[i][8].substring(0,4);
         var month = opinion_infoArry[i][8].substring(4,6);
         var day = opinion_infoArry[i][8].substring(6,8);
         sText = year+"年"+month+"月"+day+"日"; //评级人处理时间
         icbcprint.printText(sText, 0, 140, 1750, 210, false, "right",  "宋体", 120, "000");

         icbcprint.printEndTable(0);
     }

}

function printEndInfo(approve_bank,print_date){

     sText = "————————————————————————————————————";
     icbcprint.printText(sText, 150, 2000, 1950, 2100, false, "center", "宋体", 140, "000");

     icbcprint.printBeginTable(1000, 2200, 900, 260, 0);

     sText = "评级单位：";
     icbcprint.printText(sText, 0, 0, 300, 130, false, "center", "宋体", 140, "010");

     sText = approve_bank;
     icbcprint.printText(sText, 300, 0, 900, 130, false, "left", "宋体", 140, "010");

     sText = "打印日期：";
     icbcprint.printText(sText, 0, 130, 300, 260, false, "center", "宋体", 140, "010");

     var year = print_date.substring(0,4);
     var month = print_date.substring(4,6);
     var day = print_date.substring(6,8);
     sText = year+"年"+month+"月"+day+"日";
     icbcprint.printText(sText, 300, 130, 900, 260, false, "left", "宋体", 140, "010");

     icbcprint.printEndTable(0);

     icbcprint.printEndPage();
}