/*打印封页*/
function printFirstPage(assess_year,assess_month,assess_kind,client_name,approve_bank,approve_date){
    icbcprint.printStartPage();

   



    var sText = "";
    sText=assess_year +"年"+ assess_month+"月信用等级评定报告";
    if (assess_month=="00"){
    sText = assess_year + "年度信用等级评定报告";}
    
    icbcprint.printText(sText, 150, 600, 1950, 700, false, "center", "黑体", 240, "010");

	if (assess_month=="00"){
    sText = assess_kind;
    icbcprint.printText(sText, 150, 800, 1950, 900, false, "center", "黑体", 180, "010");
			}
    icbcprint.printBeginTable(470, 1850, 1400, 150, 0);

    sText = "客户名称：";
    icbcprint.printText(sText, 0, 0, 340, 150, false, "left", "宋体", 180, "000");

    sText = client_name;
    icbcprint.printText(sText, 340, 0, 1400, 150, false, "left", "宋体", 180, "110");

    icbcprint.printEndTable(0);

    icbcprint.printBeginTable(470, 2000, 1400, 300, 0);

    sText = "评级单位：";
    	if (assess_month=="00"){
    sText = "送审单位：";}
    icbcprint.printText(sText, 0, 0, 340, 150, false, "left", "宋体", 180, "000");

    sText = approve_bank;
    icbcprint.printText(sText, 340, 0, 1400, 150, false, "left", "宋体", 180, "110");
	
    sText = "评级日期：";
    	if (assess_month=="00"){
    sText = "送审时间：";}
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
/*function printCustInfo(cust_infoArry,stock_infoArry,form){*/
  function printCustInfo(cust_infoArry,stock_infoArry,trade_infoArry){	
    icbcprint.printStartPage();

    var sText = "一、客户基本情况";
    icbcprint.printText(sText, 150, 300, 1950, 360, false, "left", "黑体", 140, "000");

    sText = "1、客户概况";
    icbcprint.printText(sText, 150, 370, 1950, 430, false, "left", "宋体", 120, "010");

    icbcprint.printBeginTable(218, 430, 1790, 975,0);

    sText = "客户名称:";
    icbcprint.printText(sText, 0, 0, 260, 65, false, "left", "宋体", 120, "000");

    sText = cust_infoArry[0];
    icbcprint.printText(sText, 260, 0, 1790, 65, false, "left", "宋体", 120, "000");

    sText = "客户地址：";
    icbcprint.printText(sText, 0, 65, 260, 130, false, "left", "宋体", 120, "000");

    sText = cust_infoArry[8];
               var flag = cust_infoArry[8];
   
 	if (flag=="null"){sText="";}
    icbcprint.printText(sText, 260, 65, 1790, 130, false, "left", "宋体", 120, "000");

    sText = "客户负责人：";
    icbcprint.printText(sText, 0, 130, 310, 195, false, "left", "宋体", 120, "000");
    
    sText = cust_infoArry[1];
   icbcprint.printText(sText, 310, 130, 930, 195, false, "left", "宋体", 120, "000");
   
    sText = "成立日期：";
    icbcprint.printText(sText, 930, 130, 1190, 195, false, "left", "宋体", 120, "000");
   
    var year = cust_infoArry[2].substring(0,4);
    var month = cust_infoArry[2].substring(4,6);
    var day = cust_infoArry[2].substring(6,8);
    sText = year+"年"+month+"月"+day+"日";
    
               var flag = cust_infoArry[2];
   
 	if (flag=="null"){sText="";}
    icbcprint.printText(sText, 1190, 130, 1790, 195, false, "left", "宋体", 120, "000");
    
    sText = "建立信贷关系的时间：";
    icbcprint.printText(sText, 0, 195, 510, 260, false, "left", "宋体", 120, "000");
 
   var year = cust_infoArry[23].substring(0,4);
    var month = cust_infoArry[23].substring(4,6);
    var day = cust_infoArry[23].substring(6,8);
    sText = year+"年"+month+"月"+day+"日";
    
    		var flag = cust_infoArry[23];
   
 		if (flag=="null"){sText="";}
   icbcprint.printText(sText, 510, 195, 930, 260, false, "left", "宋体", 120, "000");
   
   sText = "注册资本：";
    icbcprint.printText(sText, 930, 195, 1190, 260, false, "left", "宋体", 120, "000");
 
   sText = cust_infoArry[9]+"万元";
                var flag = cust_infoArry[9];
   
 	if (flag=="null"){sText="";}
   icbcprint.printText(sText, 1190, 195, 1790, 260, false, "left", "宋体", 120, "000");
   
   sText = "年初信用等级：";
    icbcprint.printText(sText, 0, 260, 360, 325, false, "left", "宋体", 120, "000");
 
   sText = cust_infoArry[19];
   icbcprint.printText(sText, 360, 260, 930, 325, false, "left", "宋体", 120, "000");
   
   sText = "客户类型：";
    icbcprint.printText(sText, 930, 260, 1190, 325, false, "left", "宋体", 120, "000");
 
   sText = cust_infoArry[32];
   icbcprint.printText(sText, 1190, 260, 1790, 325, false, "left", "宋体", 120, "000");
   
    sText = "经济性质：";
    icbcprint.printText(sText, 0, 325, 260, 390, false, "left", "宋体", 120, "000");
 
   sText = cust_infoArry[3];
   icbcprint.printText(sText, 260, 325, 930, 390, false, "left", "宋体", 120, "000");
   
   sText = "所属行业：";
    icbcprint.printText(sText, 930, 325, 1190, 390, false, "left", "宋体", 120, "000");
 
   sText = cust_infoArry[4];
   icbcprint.printText(sText, 1190, 325, 1790, 390, false, "left", "宋体", 120, "000");

   sText = "职工人数：";
    icbcprint.printText(sText, 0, 390, 260, 455, false, "left", "宋体", 120, "000");
 
   sText = cust_infoArry[24];
                var flag = cust_infoArry[24];
   
 	if (flag=="null"){sText="";}
   icbcprint.printText(sText, 260, 390, 930, 455, false, "left", "宋体", 120, "000");
   
   sText = "技术人员人数：";
    icbcprint.printText(sText, 930, 390, 1340, 455, false, "left", "宋体", 120, "000");
 
   var flag = cust_infoArry[25];
   sText = cust_infoArry[25];
 	if (flag=="null"){sText="";}
 	
   
   icbcprint.printText(sText, 1340, 390, 1790, 455, false, "left", "宋体", 120, "000");
   
     sText = "技术监督局代码：";
    icbcprint.printText(sText, 0, 455, 410, 520, false, "left", "宋体", 120, "000");
 
   sText = cust_infoArry[27];
                var flag = cust_infoArry[27];
   
 	if (flag=="null"){sText="";}
   icbcprint.printText(sText, 410, 455, 930, 520, false, "left", "宋体", 120, "000");
   
   sText = "是否上市公司：";
    icbcprint.printText(sText, 930, 455, 1340, 520, false, "left", "宋体", 120, "000");
 
   var flag = cust_infoArry[20];
    if (flag=="0"){sText="不是";}
    if (flag=="1"){sText="是";}
    if (flag==""){sText="";}
    icbcprint.printText(sText, 1340, 455, 1790, 520, false, "left", "宋体", 120, "000");
   
  

   sText = "隶属关系：";
    icbcprint.printText(sText, 0, 520, 260, 585, false, "left", "宋体", 120, "000");
 
   sText = cust_infoArry[5];
   icbcprint.printText(sText, 260, 520, 930, 585, false, "left", "宋体", 120, "000");
   
   sText = "法人资格：";
    icbcprint.printText(sText, 930, 520, 1190, 585, false, "left", "宋体", 120, "000");
 
   sText = cust_infoArry[10];
   icbcprint.printText(sText, 1190, 520, 1790, 585, false, "left", "宋体", 120, "000");


  sText = "法律责任形式：";
    icbcprint.printText(sText, 0, 585, 360, 650, false, "left", "宋体", 120, "000");
 
   sText = cust_infoArry[26];
                var flag = cust_infoArry[26];
   
 	if (flag=="null"){sText="";}
   icbcprint.printText(sText, 360, 585, 930, 650, false, "left", "宋体", 120, "000");
   
   sText = "是否集团客户：";
    icbcprint.printText(sText, 930, 585, 1340, 650, false, "left", "宋体", 120, "000");
 
    var flag = cust_infoArry[15];
    if (flag=="0"){sText="不是";}
    if (flag=="1"){sText="是";}
    if (flag==""){sText="";}
   icbcprint.printText(sText, 1340, 585, 1790, 650, false, "left", "宋体", 120, "000");
   
     sText = "集团龙头企业的名称：";
    icbcprint.printText(sText, 0, 650, 460, 715, false, "left", "宋体", 120, "000");
 
   sText = cust_infoArry[7];
                var flag = cust_infoArry[7];
   
 	if (flag=="null"){sText="";}
   icbcprint.printText(sText, 460, 650, 1790, 715, false, "left", "宋体", 120, "000");
   
   sText = "服务地区：";
    icbcprint.printText(sText, 0, 715, 260, 780, false, "left", "宋体", 120, "000");
 
   sText = cust_infoArry[6];
                var flag = cust_infoArry[6];
   
 	if (flag=="null"){sText="";}
   icbcprint.printText(sText, 260, 715, 1790, 780, false, "left", "宋体", 120, "000");
   
  sText = "工行贷款行的名称：";
    icbcprint.printText(sText, 0, 780, 460, 845, false, "left", "宋体", 120, "000");
 
   sText = cust_infoArry[28];
                var flag = cust_infoArry[28];
   
 	if (flag=="null"){sText="";}
   icbcprint.printText(sText, 460, 780, 1790, 845, false, "left", "宋体", 120, "000");
   
   sText = "最新报表时间：";
    icbcprint.printText(sText, 0, 845, 360, 910, false, "left", "宋体", 120, "000");
 
   var year = cust_infoArry[29].substring(0,4);
    var month = cust_infoArry[29].substring(4,6);
    
    sText = year+"年"+month+"月";
                 var flag = cust_infoArry[29];
   
 	if (flag=="null"){sText="";}
   icbcprint.printText(sText, 360, 845, 1790, 910, false, "left", "宋体", 120, "000");

 sText = "相关文件批号：";
    icbcprint.printText(sText, 0, 910, 360, 975, false, "left", "宋体", 120, "000");
 
   sText = cust_infoArry[28];
                var flag = cust_infoArry[28];
   
 	if (flag=="null"){sText="";}
   icbcprint.printText(sText, 360, 910, 1790, 975, false, "left", "宋体", 120, "000");


    icbcprint.printEndTable(0);
    
  /*  股东情况      */  
  sText = "客户主要股东实际投资额及股权比例：";
	    icbcprint.printText(sText, 218, 1150, 1950, 1210, false, "left", "宋体", 120, "010");

	    var len = stock_infoArry.length;
	    icbcprint.printBeginTable(218, 1210, 1700, 60+60*len,0);
	    
	    sText = "序号";
	    icbcprint.printText(sText, 0, 0, 80, 60, true, "center", "宋体", 100, "000");

	    sText = "主要股东名称";
	    icbcprint.printText(sText, 80, 0, 630, 60, true, "center", "宋体", 100, "000");
	    
	    sText = "信用等级";
	    icbcprint.printText(sText, 630, 0, 780, 60, true, "center", "宋体", 100, "000");
	    
	    sText = "实收资本（万元）";
	    icbcprint.printText(sText, 780, 0, 1100, 60, true, "center", "宋体", 100, "000");

	    sText = "实际投资额（万元）";
	    icbcprint.printText(sText, 1100, 0, 1500, 60, true, "center", "宋体", 100, "000");

	    sText = "股权比例";
	    icbcprint.printText(sText, 1500, 0, 1700, 60, true, "center", "宋体", 100, "000");

	    var cell_height = 60;
	    for (var i=0; i<len; i++) {
			sText =i+1;
			icbcprint.printText(sText, 0, 60+i*cell_height, 80, 120+i*cell_height, true, "center", "宋体", 100, "000");

			sText = stock_infoArry[i][0];
	           
			icbcprint.printText(sText, 80, 60+i*cell_height, 630, 120+i*cell_height, true, "center", "宋体", 100, "000");

			sText = stock_infoArry[i][1];
			icbcprint.printText(sText, 630, 60+i*cell_height, 780, 120+i*cell_height, true, "center", "宋体", 100, "000");
			
			sText = stock_infoArry[i][2];
			icbcprint.printText(sText, 780, 60+i*cell_height, 1100, 120+i*cell_height, true, "center", "宋体", 100, "000");
			
			sText = stock_infoArry[i][3];
			icbcprint.printText(sText, 1100, 60+i*cell_height, 1500, 120+i*cell_height, true, "center", "宋体", 100, "000");
			
			sText = stock_infoArry[i][4];
			icbcprint.printText(sText, 1500, 60+i*cell_height, 1700, 120+i*cell_height, true, "center", "宋体", 100, "000");
	     }

	    icbcprint.printEndTable(0);

   /*   end--股东情况     */
   
   
   
    /*  交易对象情况      */  
  sText = "客户交易对象及交易情况：";
	    icbcprint.printText(sText, 218, 1350, 1950, 1410, false, "left", "宋体", 120, "010");

	    var len = stock_infoArry.length;
	    icbcprint.printBeginTable(218, 1410, 1700, 60+60*len,0);
	    
	    sText = "序号";
	    icbcprint.printText(sText, 0, 0, 80, 60, true, "center", "宋体", 100, "000");

	    sText = "交易对象名称";
	    icbcprint.printText(sText, 80, 0, 830, 60, true, "center", "宋体", 100, "000");
	    
	    sText = "信用等级";
	    icbcprint.printText(sText, 830, 0, 980, 60, true, "center", "宋体", 100, "000");
	    
	    sText = "交易金额（万元）";
	    icbcprint.printText(sText, 980, 0, 1700, 60, true, "center", "宋体", 100, "000");

	    

	    

	
	    for (var i=0; i<len; i++) {
			sText =i+1;
			icbcprint.printText(sText, 0, 60+i*60, 80, 120+i*60, true, "center", "宋体", 100, "000");

			sText = trade_infoArry[i][0];
	           
			icbcprint.printText(sText, 80, 60+i*60, 830, 120+i*60, true, "center", "宋体", 100, "000");

			sText = trade_infoArry[i][1];
			icbcprint.printText(sText, 830, 60+i*60, 980, 120+i*60, true, "center", "宋体", 100, "000");
			
			sText = trade_infoArry[i][2];
			icbcprint.printText(sText, 980, 60+i*60, 1700, 120+i*60, true, "center", "宋体", 100, "000");
			
			
	     }

	    icbcprint.printEndTable(0);

   /*   end--交易对象情况     */

    sText = "2、客户历史简介";
    icbcprint.printText(sText, 150, 1420, 1790, 1485, false, "left", "宋体", 120, "010");

    sText = cust_infoArry[11];
    icbcprint.printText(sText, 218, 1485, 1790, 1600, false, "left", "宋体", 120, "000");
    
    icbcprint.printEndPage();

}
/*打印第二页：我行融资情况*/

function printFinancing(){
  	icbcprint.printStartPage();
  	
    sText = "二、我行融资情况";
    icbcprint.printText(sText, 150, 300, 1950, 360, false, "left", "黑体", 140, "000");
         	
  	sText = "单位：万元";
  	  icbcprint.printText(sText, 1700, 360, 1990, 420, false, "left", "宋体", 100, "000");
  	  
       icbcprint.printBeginTable(180,420, 1760, 1320, 0);  	
       
        sText = "贷款五级分类";
        icbcprint.printText(sText,0 , 0, 220, 420, true, "center", "宋体", 100, "000");
        
        	sText = "正常贷款余额";
        	icbcprint.printText(sText,220 , 0, 670, 60, true, "left", "宋体", 100, "000");
        	
        	sText = "关注贷款余额";
        	icbcprint.printText(sText,220 , 60, 670, 120, true, "left", "宋体", 100, "000");
        	
        	sText = "次级贷款余额";
        	icbcprint.printText(sText,220 , 120, 670, 180, true, "left", "宋体", 100, "000");
  	  
         	sText = "可疑贷款余额";
        	icbcprint.printText(sText,220 , 180, 670, 240, true, "left", "宋体", 100, "000");
        	
        	sText = "损失贷款余额";
        	icbcprint.printText(sText,220 , 240, 670, 300, true, "left", "宋体", 100, "000");
        	
        	sText = "各类贷款余额合计";
        	icbcprint.printText(sText,220 , 300, 670, 360, true, "left", "宋体", 100, "000");
        	
        	sText = "　期中：各类业务形成垫付";
        	icbcprint.printText(sText,220 , 360, 670, 420, true, "left", "宋体", 100, "000");
        	
        	for (var i=0;i<20;i++)
        		{
        			sText = "";
        			icbcprint.printText(sText, 670,  0+60*i, 920, 60+60*i, true, "left", "宋体", 100, "000");
        			
        			sText = "";
        			icbcprint.printText(sText, 1460,  0+60*i, 1720, 60+60*i, true, "left", "宋体", 100, "000");
        		}
        		
			sText = "";
        			icbcprint.printText(sText, 670,  1200, 920, 1260, true, "left", "宋体", 100, "000");
        			
        			sText = "";
        			icbcprint.printText(sText, 1080,  1200, 1720, 1260, true, "left", "宋体", 100, "000");        		
			
        sText = "拆借";
        icbcprint.printText(sText,920 , 0, 1080, 120, true, "center", "宋体", 100, "000");			
        
        	sText = "本年度同业拆借累计额";
        	icbcprint.printText(sText,1080 , 0, 1460, 60, true, "left", "宋体", 100, "000");
        	
        	sText = "当年拆借余额";
        	icbcprint.printText(sText,1080 , 60, 1460, 120, true, "left", "宋体", 100, "000");
        	
        sText = "回购";
        icbcprint.printText(sText,920 , 120, 1080, 420, true, "center", "宋体", 100, "000");			
        	
	sText = "本年度回购累计额";
        	icbcprint.printText(sText,1080 , 120, 1460, 180, true, "left", "宋体", 100, "000");
        	
        	sText = "当年回购余额";
        	icbcprint.printText(sText,1080 , 180, 1460, 240, true, "left", "宋体", 100, "000");	
        	
        	sText = "其中";
        	icbcprint.printText(sText,1080 , 240, 1140, 420, true, "left", "宋体", 100, "000");	
        		
        		sText = "债券回购余额";
        		icbcprint.printText(sText,1140 , 240, 1460, 300, true, "left", "宋体", 100, "000");	
        				
  	  	sText = "票据回购余额";
        		icbcprint.printText(sText,1140 , 300, 1460, 360, true, "left", "宋体", 100, "000");	
        		
        		sText = "信贷资产回购余额";
        		icbcprint.printText(sText,1140 , 360, 1460, 420, true, "left", "宋体", 100, "000");	
        		
         sText = "法人帐户透支";
        icbcprint.printText(sText,0 , 420, 220, 480, true, "center", "宋体", 100, "000");			          		
        
        	 sText = "法人帐户透支余额";
        	icbcprint.printText(sText,220 , 420, 670, 480, true, "left", "宋体", 100, "000");			          		
  	  	
        sText = "贴现及转贴现";
        icbcprint.printText(sText, 0 , 480, 220, 660, true, "center", "宋体", 100, "000");			          		
        
        	 sText = "当前贴现余额";
        	icbcprint.printText(sText,220 , 480, 670, 540, true, "left", "宋体", 100, "000");			          		

	sText = "当前转贴现余额";
        	icbcprint.printText(sText,220 , 540, 670, 600, true, "left", "宋体", 100, "000");			          		
        	
        	sText = "本年度累计贴现及转贴现余额";
        	icbcprint.printText(sText,220 , 600, 670, 660, true, "left", "宋体", 90, "000");			          		
        	
         sText = "质押贷款";
        icbcprint.printText(sText, 920 , 420, 1080, 660, true, "center", "宋体", 100, "000");			          		        	
        
        	 sText = "质押贷款余额";
        	icbcprint.printText(sText,1080 , 420, 1460, 480, true, "left", "宋体", 100, "000");			          		
        	
        	sText = "其中";
        	icbcprint.printText(sText,1080 , 480, 1140, 660, true, "center", "宋体", 100, "000");			          		
        	
        		sText = "国债或金融债券质押";
        		icbcprint.printText(sText,1140 , 480, 1460, 540, true, "left", "宋体", 100, "000");			          		
        		
        		sText = "股票质押贷款";
        		icbcprint.printText(sText,1140 , 540, 1460, 600, true, "left", "宋体", 100, "000");			          		
  	  	
  	  	sText = "金融企业债券质押";
        		icbcprint.printText(sText,1140 , 600, 1460, 660, true, "left", "宋体", 100, "000");			          		
  	  	
             	  	
         sText = "抵押贷款";
        icbcprint.printText(sText, 0 , 660, 220, 840, true, "center", "宋体", 100, "000");			          		        	  	  
        
        	sText = "抵押贷款余额";
        	icbcprint.printText(sText, 220 , 660, 670, 720, true, "left", "宋体", 100, "000");			          		
        	
        	sText = "其中";
        	icbcprint.printText(sText,220 , 720, 280, 840, true, "center", "宋体", 100, "000");			          		
        	
        		sText = "短期贷款余额";
        		icbcprint.printText(sText,280 , 720, 670, 780, true, "left", "宋体", 100, "000");			          		
        		
        		sText = "中长期贷款余额";
        		icbcprint.printText(sText,280 , 780, 670, 840, true, "left", "宋体", 100, "000");			          		
  	  	
  	  	
           sText = "信用贷款";
        icbcprint.printText(sText, 920 , 660, 1080, 840, true, "center", "宋体", 100, "000");			          		        	
        
        	 sText = "信用贷款余额";
        	icbcprint.printText(sText,1080 , 660, 1460, 720, true, "left", "宋体", 100, "000");			          		
        	
        	sText = "其中";
        	icbcprint.printText(sText,1080 , 720, 1140, 840, true, "center", "宋体", 100, "000");			          		
        	
        		sText = "短期贷款余额";
        		icbcprint.printText(sText,1140 , 720, 1460, 780, true, "left", "宋体", 100, "000");			          		
        		
        		sText = "中长期贷款余额";
        		icbcprint.printText(sText,1140 , 780, 1460, 840, true, "left", "宋体", 100, "000");		  	
  	  	
  	
  	             	  	
         sText = "外汇融资类业务";
        icbcprint.printText(sText, 0 , 840, 220, 1260, true, "center", "宋体", 100, "000");			 
  	
  	  sText = "外汇融资类业务余额";
        	icbcprint.printText(sText, 220 , 840, 670, 900, true, "left", "宋体", 100, "000");	
        	
        	  sText = "其中";
        	icbcprint.printText(sText, 220 , 900, 280, 1200, true, "center", "宋体", 100, "000");	
        	
        		sText = "投资类余额";
        		icbcprint.printText(sText, 280 , 900, 670, 960, true, "left", "宋体", 100, "000");	
  	
  		sText = "拆放类余额";
        		icbcprint.printText(sText, 280 , 960, 670, 1020, true, "left", "宋体", 100, "000");	
        		
        		sText = "交易类余额";
        		icbcprint.printText(sText, 280 , 1020, 670, 1080, true, "left", "宋体", 100, "000");	
        		
        		sText = "担保类余额";
        		icbcprint.printText(sText, 280 , 1080, 670, 1140, true, "left", "宋体", 100, "000");	
        		
        		sText = "结算类余额";
        		icbcprint.printText(sText, 280 , 1140, 670, 1200, true, "left", "宋体", 100, "000");	
        	
  	  sText = "当前形成外汇垫付余额";
        	icbcprint.printText(sText, 220 , 1200, 670, 1260, true, "left", "宋体", 100, "000");	
        	
        sText = "保函";
        icbcprint.printText(sText, 920 , 840, 1080, 960, true, "center", "宋体", 100, "000");		
        
        	sText = "保函业务余额";
        	icbcprint.printText(sText, 1080 , 840, 1460, 900, true, "left", "宋体", 100, "000");		
  	
  	sText = "当前保函业务垫付余额";
        	icbcprint.printText(sText, 1080 , 900, 1460, 960, true, "left", "宋体", 100, "000");		
        	
        sText = "承兑业务";
        icbcprint.printText(sText, 920 , 960, 1080, 1080, true, "center", "宋体", 100, "000");		
        
        	sText = "当前承兑余额";
        	icbcprint.printText(sText, 1080 , 960, 1460, 1020, true, "left", "宋体", 100, "000");		
  	
  	sText = "当前垫付余额";
        	icbcprint.printText(sText, 1080 , 1020, 1460, 1080, true, "left", "宋体", 100, "000");	
  	  	
         sText = "担保业务";
        icbcprint.printText(sText, 920 , 1080, 1080, 1200, true, "center", "宋体", 100, "000");		
        
        	sText = "当前担保金额";
        	icbcprint.printText(sText, 1080 , 1080, 1460, 1140, true, "left", "宋体", 100, "000");		
  	
  	sText = "当前担保垫付余额";
        	icbcprint.printText(sText, 1080 , 1140, 1460, 1200, true, "left", "宋体", 100, "000");
  
     sText = "备注：";
        icbcprint.printText(sText, 920 , 1200, 1080, 1260, true, "center", "宋体", 100, "000");	
  
  	  	
    icbcprint.printEndTable(0);
    
          icbcprint.printEndPage();	  	

}
/*END  我行融资情况*/

/*打印第二页：财务指标*/
function printFinanceInfo(ckind,year,financial_nameArry,financial_dataArry2,financial_dataArry1,financial_dataArry0,cust_infoArry){
	
	icbcprint.printStartPage();

     sText = "三、客户主要财务数据";
     icbcprint.printText(sText, 150, 300, 1950, 360, false, "left", "黑体", 140, "000");
     
      icbcprint.printBeginTable(180, 360, 1760, 600,0);
     
     sText = "当期财务报表是否经过审计：";
     icbcprint.printText(sText, 0, 0, 720, 60, false, "left", "宋体", 120, "000");
     
      var flag = cust_infoArry[12];
    if (flag=="0"){sText="不是";}
    if (flag=="1"){sText="是";}
    if (flag==""){sText="";}
     icbcprint.printText(sText, 720, 0, 980, 60, false, "left", "宋体", 120, "000");
     
     sText = "如经过审计，审计结论：";
     icbcprint.printText(sText, 0, 60, 720, 120, false, "left", "宋体", 120, "000");
     
     sText = cust_infoArry[14];
     icbcprint.printText(sText, 720, 60, 1500, 120, false, "left", "宋体", 120, "000");
     
     sText = "审计意见：";
     icbcprint.printText(sText, 0, 120, 720, 180, false, "left", "宋体", 120, "000");
     
       sText = cust_infoArry[30];
        var flag = cust_infoArry[30];
   
 	if (flag=="null"){sText="";}
     icbcprint.printText(sText, 100, 180, 1790, 240, false, "left", "宋体", 120, "000");
     
     sText = "会计师事务所名称：";
     icbcprint.printText(sText, 0,240 , 720, 180, false, "left", "宋体", 120, "000");
     
     sText =  cust_infoArry[13];
      var flag = cust_infoArry[13];
   
 	if (flag=="null"){sText="";}
     icbcprint.printText(sText, 720,240 , 1790, 300, false, "left", "宋体", 120, "000");

     icbcprint.printEndTable(0);

     sText = "客户近三年主要财务指标情况：";
     icbcprint.printText(sText, 180, 660, 1950, 720, false, "left", "宋体", 120, "000");

     icbcprint.printBeginTable(180, 720, 1760, 920,1);

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
     
     
     
     if(ckind=="01")
     {
     	
     	/*data_order 数组是财务指标的打印顺序 分行业，制定不同的顺序			*/
     	var data_order = new Array(0,1,18,4,5,7,8,11,19,13,14,15,16,17);	
     	
     	
     	
     	
     	
     	   for (var i=0; i<14; i++) {
     	   	
     	   	var j=data_order[i];
     	   	
     	   	
		sText = financial_nameArry[j];
		icbcprint.printText(sText, 0, 80+i*cell_height, 560, 140+i*cell_height, true, "left", "宋体", 100, "000");

		sText = financial_dataArry2[j];
		icbcprint.printText(sText, 560, 80+i*cell_height, 960, 140+i*cell_height, true, "center", "宋体", 100, "000");

		sText = financial_dataArry1[j];
		icbcprint.printText(sText, 960, 80+i*cell_height, 1360, 140+i*cell_height, true, "center", "宋体", 100, "000");

                sText = financial_dataArry0[j];
		icbcprint.printText(sText, 1360, 80+i*cell_height, 1760, 140+i*cell_height, true, "center", "宋体", 100, "000");

	}

     	    	
     	
     }
     
       if(ckind=="04")
     {
     	var data_order = new Array(0,18,1,19,5,6,7,8,9,10,13,14,15,16,17);	
     	
     	
     	   for (var i=0; i<15; i++) {
     	   	
     	   	var j=data_order[i];
     	   	
     	   	
		sText = financial_nameArry[j];
		icbcprint.printText(sText, 0, 80+i*cell_height, 560, 140+i*cell_height, true, "left", "宋体", 100, "000");

		sText = financial_dataArry2[j];
		icbcprint.printText(sText, 560, 80+i*cell_height, 960, 140+i*cell_height, true, "center", "宋体", 100, "000");

		sText = financial_dataArry1[j];
		icbcprint.printText(sText, 960, 80+i*cell_height, 1360, 140+i*cell_height, true, "center", "宋体", 100, "000");

                sText = financial_dataArry0[j];
		icbcprint.printText(sText, 1360, 80+i*cell_height, 1760, 140+i*cell_height, true, "center", "宋体", 100, "000");

	}

     	    	
     	
     }
     
     
       if(ckind=="05")
     {
     	var data_order = new Array(0,1,2,4,5,6,7,8,9,10,11,13,14,15,16,17);	
     	
     	
     	   for (var i=0; i<16; i++) {
     	   	
     	   	var j=data_order[i];
     	   	
     	   	
		sText = financial_nameArry[j];
		icbcprint.printText(sText, 0, 80+i*cell_height, 560, 140+i*cell_height, true, "left", "宋体", 100, "000");

		sText = financial_dataArry2[j];
		icbcprint.printText(sText, 560, 80+i*cell_height, 960, 140+i*cell_height, true, "center", "宋体", 100, "000");

		sText = financial_dataArry1[j];
		icbcprint.printText(sText, 960, 80+i*cell_height, 1360, 140+i*cell_height, true, "center", "宋体", 100, "000");

                sText = financial_dataArry0[j];
		icbcprint.printText(sText, 1360, 80+i*cell_height, 1760, 140+i*cell_height, true, "center", "宋体", 100, "000");

	}

     	    	
     	
     }
     
     
       if(ckind=="06")
     {
     	var data_order = new Array(0,18,2,19,4,5,6,7,8,9,10,13,14,15,16,17);	
     	
     	
     	   for (var i=0; i<16; i++) {
     	   	
     	   	var j=data_order[i];
     	   	
     	   	
		sText = financial_nameArry[j];
		icbcprint.printText(sText, 0, 80+i*cell_height, 560, 140+i*cell_height, true, "left", "宋体", 100, "000");

		sText = financial_dataArry2[j];
		icbcprint.printText(sText, 560, 80+i*cell_height, 960, 140+i*cell_height, true, "center", "宋体", 100, "000");

		sText = financial_dataArry1[j];
		icbcprint.printText(sText, 960, 80+i*cell_height, 1360, 140+i*cell_height, true, "center", "宋体", 100, "000");

                sText = financial_dataArry0[j];
		icbcprint.printText(sText, 1360, 80+i*cell_height, 1760, 140+i*cell_height, true, "center", "宋体", 100, "000");

	}

     	    	
     	
     }
     
     
     
       if(ckind=="07")
     {
     	var data_order = new Array(0,1,2,3,4,5,6,7,9,18,13,14,15,16,17);	
     	
     	
     	   for (var i=0; i<15; i++) {
     	   	
     	   	var j=data_order[i];
     	   	
     	   	
		sText = financial_nameArry[j];
		icbcprint.printText(sText, 0, 80+i*cell_height, 560, 140+i*cell_height, true, "left", "宋体", 100, "000");

		sText = financial_dataArry2[j];
		icbcprint.printText(sText, 560, 80+i*cell_height, 960, 140+i*cell_height, true, "center", "宋体", 100, "000");

		sText = financial_dataArry1[j];
		icbcprint.printText(sText, 960, 80+i*cell_height, 1360, 140+i*cell_height, true, "center", "宋体", 100, "000");

                sText = financial_dataArry0[j];
		icbcprint.printText(sText, 1360, 80+i*cell_height, 1760, 140+i*cell_height, true, "center", "宋体", 100, "000");

	}

     	    	
     	
     }
     
     if(ckind=="02"||ckind=="03")
     {
     
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
	
	}

     icbcprint.printEndTable(10);

     icbcprint.printEndPage();
}

/*     END********** 财务指标     */


/**第四页 资产负债表************/

	
  	function printzc(zc_dataArry,zc_dataArry2){
  	icbcprint.printStartPage();
  	 sText = "资产负债表";     
     icbcprint.printText(sText, 150, 300, 1950, 360, false, "center", "宋体", 120, "000");
     
     	sText = zc_dataArry2[0].substring(0,4)+"年12月";
     icbcprint.printText(sText, 150, 360, 1950, 420, false, "center", "宋体", 120, "000");
     
        icbcprint.printBeginTable(180, 420, 1760, 600,0);
     
     	sText="报表类型:";
     	 icbcprint.printText(sText, 0, 0, 170, 60, false, "left", "宋体", 100, "000");
     	 
     	 var flag = zc_dataArry2[2];
    if (flag=="77"){sText="非保险";}
    if (flag=="88"){sText="保险";}
    if (flag==""){sText="";}
     	 icbcprint.printText(sText, 170, 0, 380, 60, false, "left", "宋体", 100, "000");
     	 
     	 
     	 
          sText="报表是否合并:";
           icbcprint.printText(sText, 380, 0, 640, 60, false, "left", "宋体", 100, "000");
           
       var flag = zc_dataArry2[1];
    if (flag=="0"){sText="不是";}
    if (flag=="1"){sText="是";}
    if (flag==""){sText="";}
     icbcprint.printText(sText, 640, 0, 740, 60, false, "left", "宋体", 100, "000");
     
     
     
	     sText = "单位：万元";
  	  icbcprint.printText(sText, 1300, 0, 1560, 60, false, "left", "宋体", 100, "000");
  	  
  	  icbcprint.printEndTable(0);
     
   var len = zc_dataArry.length;
   
icbcprint.printBeginTable(180, 480, 1760, 60+60*len,0);
     
  	  sText = "项目";
	    icbcprint.printText(sText, 0, 0, 660, 60, true, "center", "宋体", 100, "000");

	    sText = "年初数";
	    icbcprint.printText(sText, 660, 0, 1100, 60, true, "center", "宋体", 100, "000");
	    
	    sText = "年末数";
	    icbcprint.printText(sText, 1100, 0, 1500, 60, true, "center", "宋体", 100, "000");
	    
	
	    
	      for (var i=1; i<len; i++) {
			sText =zc_dataArry[i][0];
			icbcprint.printText(sText, 0, i*60, 660, 60+i*60, true, "left", "宋体", 100, "000");

			sText = zc_dataArry[i][1];
	           
			icbcprint.printText(sText, 660, i*60, 1100, 60+i*60, true, "right", "宋体", 100, "000");

			sText = zc_dataArry[i][2];
			icbcprint.printText(sText, 1100, i*60, 1500, 60+i*60, true, "right", "宋体", 100, "000");
			
			
	     }
  	
  	
  	   icbcprint.printEndTable(0);
  icbcprint.printEndPage();
}  


/**     END 资产负债表       **/





/**第四页 损益表************/

	
  	function printsy(sy_dataArry,sy_dataArry2){
  	icbcprint.printStartPage();
  	 sText = "损益表";     
     icbcprint.printText(sText, 150, 300, 1950, 360, false, "center", "宋体", 120, "000");
     
     	sText = sy_dataArry2[0].substring(0,4)+"年12月";
     icbcprint.printText(sText, 150, 360, 1950, 420, false, "center", "宋体", 120, "000");
     
        icbcprint.printBeginTable(180, 420, 1760, 600,0);
     
     	sText="报表类型:";
     	 icbcprint.printText(sText, 0, 0, 170, 60, false, "left", "宋体", 100, "000");
     	 
     	 var flag = sy_dataArry2[2];
    if (flag=="77"){sText="非保险";}
    if (flag=="88"){sText="保险";}
    if (flag==""){sText="";}
     	 icbcprint.printText(sText, 170, 0, 380, 60, false, "left", "宋体", 100, "000");
     	 
     	 
     	 
          sText="报表是否合并:";
           icbcprint.printText(sText, 380, 0, 640, 60, false, "left", "宋体", 100, "000");
           
       var flag = sy_dataArry2[1];
    if (flag=="0"){sText="不是";}
    if (flag=="1"){sText="是";}
    if (flag==""){sText="";}
     icbcprint.printText(sText, 640, 0, 740, 60, false, "left", "宋体", 100, "000");
     
     
     
	     sText = "单位：万元";
  	  icbcprint.printText(sText, 1300, 0, 1560, 60, false, "left", "宋体", 100, "000");
  	  
  	  icbcprint.printEndTable(0);
     
   var len = sy_dataArry.length;
   
icbcprint.printBeginTable(180, 480, 1760, 60+60*len,0);
     
  	  sText = "项目";
	    icbcprint.printText(sText, 0, 0, 660, 60, true, "center", "宋体", 100, "000");

	    sText = "年初数";
	    icbcprint.printText(sText, 660, 0, 1100, 60, true, "center", "宋体", 100, "000");
	    
	    sText = "年末数";
	    icbcprint.printText(sText, 1100, 0, 1500, 60, true, "center", "宋体", 100, "000");
	    
	
	    
	      for (var i=1; i<len; i++) {
			sText =sy_dataArry[i][0];
			icbcprint.printText(sText, 0, i*60, 660, 60+i*60, true, "left", "宋体", 100, "000");

			sText = sy_dataArry[i][1];
	           
			icbcprint.printText(sText, 660, i*60, 1100, 60+i*60, true, "right", "宋体", 100, "000");

			sText = sy_dataArry[i][2];
			icbcprint.printText(sText, 1100, i*60, 1500, 60+i*60, true, "right", "宋体", 100, "000");
			
			
	     }
  	
  	
  	   icbcprint.printEndTable(0);
  icbcprint.printEndPage();
}  


/**     END 损益表       **/




/**第四页 现金流量表************/

	
  	function printxj(xj_dataArry,xj_dataArry2){
  	icbcprint.printStartPage();
  	 sText = "现金流量表";     
     icbcprint.printText(sText, 150, 300, 1950, 360, false, "center", "宋体", 120, "000");
     
     	sText = xj_dataArry2[0].substring(0,4)+"年12月";
     icbcprint.printText(sText, 150, 360, 1950, 420, false, "center", "宋体", 120, "000");
     
        icbcprint.printBeginTable(180, 420, 1760, 600,0);
     
     	sText="报表类型:";
     	 icbcprint.printText(sText, 0, 0, 170, 60, false, "left", "宋体", 100, "000");
     	 
     	 var flag = xj_dataArry2[2];
    if (flag=="77"){sText="非保险";}
    if (flag=="88"){sText="保险";}
    if (flag==""){sText="";}
     	 icbcprint.printText(sText, 170, 0, 380, 60, false, "left", "宋体", 100, "000");
     	 
     	 
     	 
          sText="报表是否合并:";
           icbcprint.printText(sText, 380, 0, 640, 60, false, "left", "宋体", 100, "000");
           
       var flag = xj_dataArry2[1];
    if (flag=="0"){sText="不是";}
    if (flag=="1"){sText="是";}
    if (flag==""){sText="";}
     icbcprint.printText(sText, 640, 0, 740, 60, false, "left", "宋体", 100, "000");
     
     
     
	     sText = "单位：万元";
  	  icbcprint.printText(sText, 1300, 0, 1560, 60, false, "left", "宋体", 100, "000");
  	  
  	  icbcprint.printEndTable(0);
     
   var len = xj_dataArry.length;
   
icbcprint.printBeginTable(180, 480, 1760, 60+60*len,0);
     
  	  sText = "项目";
	    icbcprint.printText(sText, 0, 0, 660, 60, true, "center", "宋体", 100, "000");

	    sText = "年初数";
	    icbcprint.printText(sText, 660, 0, 1100, 60, true, "center", "宋体", 100, "000");
	    
	    sText = "年末数";
	    icbcprint.printText(sText, 1100, 0, 1500, 60, true, "center", "宋体", 100, "000");
	    
	
	    
	      for (var i=1; i<len; i++) {
			sText =xj_dataArry[i][0];
			icbcprint.printText(sText, 0, i*60, 660, 60+i*60, true, "left", "宋体", 100, "000");

			sText = xj_dataArry[i][1];
	           
			icbcprint.printText(sText, 660, i*60, 1100, 60+i*60, true, "right", "宋体", 100, "000");

			sText = xj_dataArry[i][2];
			icbcprint.printText(sText, 1100, i*60, 1500, 60+i*60, true, "right", "宋体", 100, "000");
			
			
	     }
  	
  	
  	   icbcprint.printEndTable(0);
  icbcprint.printEndPage();
}  


/**     END 现金流量表       **/

/**第四页 数据补录表************/

	
  	function printsj(sj_dataArry,sj_dataArry2){
  	icbcprint.printStartPage();
  	 sText = "数据补录表";     
     icbcprint.printText(sText, 150, 300, 1950, 360, false, "center", "宋体", 120, "000");
     
     	sText = sj_dataArry2[0].substring(0,4)+"年12月";
     icbcprint.printText(sText, 150, 360, 1950, 420, false, "center", "宋体", 120, "000");
     
        icbcprint.printBeginTable(180, 420, 1760, 600,0);
     
	     sText = "单位：万元";
  	  icbcprint.printText(sText, 1300, 0, 1560, 60, false, "left", "宋体", 100, "000");
  	  
  	  icbcprint.printEndTable(0);
     
   var len = sj_dataArry.length;
   
icbcprint.printBeginTable(180, 480, 1760, 60+60*len,0);
     
  	  sText = "项目";
	    icbcprint.printText(sText, 0, 0, 660, 60, true, "center", "宋体", 100, "000");

	    sText = "年初数";
	    icbcprint.printText(sText, 660, 0, 1100, 60, true, "center", "宋体", 100, "000");
	    
	    sText = "年末数";
	    icbcprint.printText(sText, 1100, 0, 1500, 60, true, "center", "宋体", 100, "000");
	    
	
	    
	      for (var i=1; i<len; i++) {
			sText =sj_dataArry[i][0];
			icbcprint.printText(sText, 0, i*60, 660, 60+i*60, true, "left", "宋体", 100, "000");

			sText = sj_dataArry[i][1];
	           
			icbcprint.printText(sText, 660, i*60, 1100, 60+i*60, true, "right", "宋体", 100, "000");

			sText = sj_dataArry[i][2];
			icbcprint.printText(sText, 1100, i*60, 1500, 60+i*60, true, "right", "宋体", 100, "000");
			
			
	     }
  	
  	
  	   icbcprint.printEndTable(0);
  icbcprint.printEndPage();
}  


/**     END 数据补录表       **/


/***   评级计分表***/

function  printscore(ration_score,ration_infoArry,nature_nameArry,nature_score,nature_infoArry){
	   var enptype = ration_infoArry[24];
	   var typeFlag = ration_infoArry[25];
	icbcprint.printStartPage();   
	

	   var sText = "四、信用评级计分表";
     icbcprint.printText(sText, 150, 300, 1950, 360, false, "left", "黑体", 140, "000");
   if (enptype=="01"||enptype=="04"||enptype=="05"||enptype=="06"||enptype=="07") {


	
	
  

     sText = "定量评价得分明细";
     icbcprint.printText(sText, 150, 370, 1950, 460, false, "center", "宋体", 120, "010");     

    var x=190;
    var y=460;

     icbcprint.printBeginTable(x, y, x+1510, y+720, 1);

     sText = "评价内容";
     icbcprint.printText(sText, 0, 0, 300, 120, true, "center", "宋体", 100, "000");
     
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
     
     		
     		
     		
     		 if (enptype=="01"){
     sText = "一、流动性";
     icbcprint.printText(sText, 0, 120, 300, 300, true, "center", "宋体", 100, "000");

     sText = "二、安全性";
     icbcprint.printText(sText, 0, 300, 300, 480, true, "center", "宋体", 100, "000");

     sText = "三、盈利性";
     icbcprint.printText(sText, 0, 480, 300, 660, true, "center", "宋体", 100, "000");

     
     sText = "定量评价总得分";
     icbcprint.printText(sText, 0, 660, 300, 720, true, "center", "宋体", 100, "000");
     
     sText = "流动比率";
     icbcprint.printText(sText, 300, 120, 850, 180, true, "center", "宋体", 100, "000");
     
     sText = "存货比";
     icbcprint.printText(sText, 300, 180, 850, 240, true, "center", "宋体", 100, "000");
     
     sText = "净拆借回购比率";
     icbcprint.printText(sText, 300, 240, 850, 300, true, "center", "宋体", 100, "000");
     
     sText = "不良贷款比率";
     icbcprint.printText(sText, 300, 300, 850, 360, true, "center", "宋体", 100, "000");
     
     sText = "呆帐准备覆盖率";
     icbcprint.printText(sText, 300, 360, 850, 420, true, "center", "宋体", 100, "000");
     
     sText = "资本充足率";
     icbcprint.printText(sText, 300, 420, 850, 480, true, "center", "宋体", 100, "000");
     
     sText = "净资产收益率";
     icbcprint.printText(sText, 300, 480, 850, 540, true, "center", "宋体", 100, "000");
     
     sText = "利息回收率";
     icbcprint.printText(sText, 300, 540, 850, 600, true, "center", "宋体", 100, "000");
     
     sText = "净利润增长率";
     icbcprint.printText(sText, 300, 600, 850, 660, true, "center", "宋体", 100, "000");
     
    
     
		     	
     		
     			
     			sText =ration_score[1];
     			 icbcprint.printText(sText, 850, 60+60*1, 1050, 120+60*1, true, "center", "宋体", 100, "000");
     			
     			sText =  ration_infoArry[0];
     			 icbcprint.printText(sText, 1050, 60+60*1, 1250, 120+60*1, true, "center", "宋体", 100, "000");
     			 
     			
     			 
     			 sText =ration_score[2];
     			 icbcprint.printText(sText, 850, 60+60*2, 1050, 120+60*2, true, "center", "宋体", 100, "000");
     			
     			sText =  ration_infoArry[1];
     			 icbcprint.printText(sText, 1050, 60+60*2, 1250, 120+60*2, true, "center", "宋体", 100, "000");
     			 
     			 
     			 
     			 sText =ration_score[13];
     			 icbcprint.printText(sText, 850, 60+60*3, 1050, 120+60*3, true, "center", "宋体", 100, "000");
     			
     			sText =  ration_infoArry[26];
     			 icbcprint.printText(sText, 1050, 60+60*3, 1250, 120+60*3, true, "center", "宋体", 100, "000");
     			 
     			 
     			 
     			 sText =ration_score[5];
     			 icbcprint.printText(sText, 850, 60+60*4, 1050, 120+60*4, true, "center", "宋体", 100, "000");
     			
     			sText =  ration_infoArry[4];
     			 icbcprint.printText(sText, 1050, 60+60*4, 1250, 120+60*4, true, "center", "宋体", 100, "000");
          		
          		
          	
          		sText =ration_score[6];
     			 icbcprint.printText(sText, 850, 60+60*5, 1050, 120+60*5, true, "center", "宋体", 100, "000");
     			
     			sText =  ration_infoArry[5];
     			 icbcprint.printText(sText, 1050, 60+60*5, 1250, 120+60*5, true, "center", "宋体", 100, "000");
     			 
     			 
     			 
     			 sText =ration_score[8];
     			 icbcprint.printText(sText, 850, 60+60*6, 1050, 120+60*6, true, "center", "宋体", 100, "000");
     			
     			sText =  ration_infoArry[7];
     			 icbcprint.printText(sText, 1050, 60+60*6, 1250, 120+60*6, true, "center", "宋体", 100, "000");
     			 
     			 
     			 
     			 sText =ration_score[9];
     			 icbcprint.printText(sText, 850, 60+60*7, 1050, 120+60*7, true, "center", "宋体", 100, "000");
     			
     			sText =  ration_infoArry[8];
     			 icbcprint.printText(sText, 1050, 60+60*7, 1250, 120+60*7, true, "center", "宋体", 100, "000");
     			 
     			 
     			 
     			 sText =ration_score[14];
     			 icbcprint.printText(sText, 850, 60+60*8, 1050, 120+60*8, true, "center", "宋体", 100, "000");
     			
     			sText =  ration_infoArry[27];
     			 icbcprint.printText(sText, 1050, 60+60*8, 1250, 120+60*8, true, "center", "宋体", 100, "000");
     			 
     			 
     			 
     			 sText =ration_score[12];
     			 icbcprint.printText(sText, 850, 60+60*9, 1050, 120+60*9, true, "center", "宋体", 100, "000");
     			
     			sText =  ration_infoArry[11];
     			 icbcprint.printText(sText, 1050, 60+60*9, 1250, 120+60*9, true, "center", "宋体", 100, "000");
     			 
     			 
     			 
     			 
     			 
     			 
          		sText = ration_infoArry[13];
     			 icbcprint.printText(sText, 1250, 120, 1450, 300, true, "center", "宋体", 100, "000");
     			
     			sText =  ration_infoArry[14];
     			 icbcprint.printText(sText, 1450, 120, 1700, 300, true, "center", "宋体", 100, "000");
     			 
     			 sText =  ration_infoArry[15];
     			 icbcprint.printText(sText, 1250, 300, 1450, 480, true, "center", "宋体", 100, "000");
     			 
     			 sText =  ration_infoArry[16];
     			 icbcprint.printText(sText, 1450, 300, 1700, 480, true, "center", "宋体", 100, "000");
     			 
     			 sText =  ration_infoArry[17];
     			 icbcprint.printText(sText, 1250, 480, 1450, 660, true, "center", "宋体", 100, "000");
     			 
     			 sText =  ration_infoArry[18];
     			 icbcprint.printText(sText, 1450, 480, 1700, 660, true, "center", "宋体", 100, "000");
          	          			
          		sText =  ration_infoArry[23];
     		icbcprint.printText(sText, 300, 660, 1700, 720, true, "center", "宋体", 100, "000");
          	}
		
     
     
     
     
         if (enptype=="04")
     	{
    sText = "一、安全性";
     icbcprint.printText(sText, 0, 120, 300, 240, true, "center", "宋体", 100, "000");

     sText = "二、偿债能力";
     icbcprint.printText(sText, 0, 240, 300, 360, true, "center", "宋体", 100, "000");

     sText = "三、盈利能力";
     icbcprint.printText(sText, 0, 360, 300, 480, true, "center", "宋体", 100, "000");
     
     sText = "四、成长性";
     icbcprint.printText(sText, 0, 480, 300, 600, true, "center", "宋体", 100, "000");
     
     sText = "五、资产结构";
     icbcprint.printText(sText, 0, 600, 300, 720, true, "center", "宋体", 100, "000");

     
     sText = "定量评价总得分";
     icbcprint.printText(sText, 0, 720, 300, 780, true, "center", "宋体", 100, "000");
     
     sText = "资本充足率";
     icbcprint.printText(sText, 300, 120, 850, 180, true, "center", "宋体", 100, "000");
     
     sText = "不良资产比率";
     icbcprint.printText(sText, 300, 180, 850, 240, true, "center", "宋体", 100, "000");
     
     sText = "合计流动比率";
     icbcprint.printText(sText, 300, 240, 850, 300, true, "center", "宋体", 100, "000");
     
     sText = "总债务/EBITDA";
     icbcprint.printText(sText, 300, 300, 850, 360, true, "center", "宋体", 100, "000");
     
     sText = "净资产收益率";
     icbcprint.printText(sText, 300, 360, 850, 420, true, "center", "宋体", 100, "000");
     
     sText = "营业收入利润率";
     icbcprint.printText(sText, 300, 420, 850, 480, true, "center", "宋体", 100, "000");
     
     sText = "净利润增长率";
     icbcprint.printText(sText, 300, 480, 850, 540, true, "center", "宋体", 100, "000");
     
     sText = "营业收入增长率";
     icbcprint.printText(sText, 300, 540, 850, 600, true, "center", "宋体", 100, "000");
     
     sText = "租赁资产占比";
     icbcprint.printText(sText, 300, 600, 850, 660, true, "center", "宋体", 100, "000");
     
     sText = "应收款占比";
     icbcprint.printText(sText, 300, 660, 850, 720, true, "center", "宋体", 100, "000");
     
     
     			
     
			sText =ration_score[1];
     			 icbcprint.printText(sText, 850, 60+60*1, 1050, 120+60*1, true, "center", "宋体", 100, "000");
     			
     			sText =  ration_infoArry[0];
     			 icbcprint.printText(sText, 1050, 60+60*1, 1250, 120+60*1, true, "center", "宋体", 100, "000");
     			 
     			 

     
     			sText =ration_score[13];
     			 icbcprint.printText(sText, 850, 60+60*2, 1050, 120+60*2, true, "center", "宋体", 100, "000");
     			
     			sText =  ration_infoArry[26];
     			 icbcprint.printText(sText, 1050, 60+60*2, 1250, 120+60*2, true, "center", "宋体", 100, "000");
     			 
     			 
     			 
     			 sText =ration_score[4];
     			 icbcprint.printText(sText, 850, 60+60*3, 1050, 120+60*3, true, "center", "宋体", 100, "000");
     			
     			sText =  ration_infoArry[3];
     			 icbcprint.printText(sText, 1050, 60+60*3, 1250, 120+60*3, true, "center", "宋体", 100, "000");
     			 
     			 
     			 
     			 sText =ration_score[14];
     			 icbcprint.printText(sText, 850, 60+60*4, 1050, 120+60*4, true, "center", "宋体", 100, "000");
     			
     			sText =  ration_infoArry[27];
     			 icbcprint.printText(sText, 1050, 60+60*4, 1250, 120+60*4, true, "center", "宋体", 100, "000");
     
     
     
     
     	for(var i=5;i<11;i++)
     		{
     			
     			sText =ration_score[i+1];
     			 icbcprint.printText(sText, 850, 60+60*i, 1050, 120+60*i, true, "center", "宋体", 100, "000");
     			
     			sText =  ration_infoArry[i];
     			 icbcprint.printText(sText, 1050, 60+60*i, 1250, 120+60*i, true, "center", "宋体", 100, "000");
          		}
          		
          	
          		
          			sText = ration_infoArry[13];
     			 icbcprint.printText(sText, 1250, 120, 1450, 240, true, "center", "宋体", 100, "000");
     			
     			sText =  ration_infoArry[14];
     			 icbcprint.printText(sText, 1450, 120, 1700, 240, true, "center", "宋体", 100, "000");
     			 
     			 sText =  ration_infoArry[15];
     			 icbcprint.printText(sText, 1250, 240, 1450, 360, true, "center", "宋体", 100, "000");
     			 
     			 sText =  ration_infoArry[16];
     			 icbcprint.printText(sText, 1450, 240, 1700, 360, true, "center", "宋体", 100, "000");
     			 
     			 sText =  ration_infoArry[17];
     			 icbcprint.printText(sText, 1250, 360, 1450, 480, true, "center", "宋体", 100, "000");
     			 
     			 sText =  ration_infoArry[18];
     			 icbcprint.printText(sText, 1450, 360, 1700, 480, true, "center", "宋体", 100, "000");
     			 
     			 sText =  ration_infoArry[19];
     			 icbcprint.printText(sText, 1250, 480, 1450, 600, true, "center", "宋体", 100, "000");
     			 
     			 sText =  ration_infoArry[20];
     			 icbcprint.printText(sText, 1450, 480, 1700, 600, true, "center", "宋体", 100, "000");
     			 
     			 sText =  ration_infoArry[21];
     			 icbcprint.printText(sText, 1250, 600, 1450, 720, true, "center", "宋体", 100, "000");
     			 
     			 sText =  ration_infoArry[22];
     			 icbcprint.printText(sText, 1450, 600, 1700, 720, true, "center", "宋体", 100, "000");
          	          			
          		sText =  ration_infoArry[23];
     		icbcprint.printText(sText, 300, 720, 1700, 780, true, "center", "宋体", 100, "000");
          	}
     
     
     
      if (enptype=="05"){
     sText = "一、资产安全性";
     icbcprint.printText(sText, 0, 120, 300, 300, true, "center", "宋体", 100, "000");

     sText = "二、偿债能力指标";
     icbcprint.printText(sText, 0, 300, 300, 480, true, "center", "宋体", 100, "000");

     sText = "三、盈利能力指标";
     icbcprint.printText(sText, 0, 480, 300, 660, true, "center", "宋体", 100, "000");

     sText = "四、成长性";
     icbcprint.printText(sText, 0, 660, 300, 780, true, "center", "宋体", 100, "000");
     
     sText = "定量评价总得分";
     icbcprint.printText(sText, 0, 780, 300, 840, true, "center", "宋体", 100, "000");
     
     sText = "净资产风险度";
     icbcprint.printText(sText, 300, 120, 850, 180, true, "center", "宋体", 100, "000");
     
     sText = "自营证券比率";
     icbcprint.printText(sText, 300, 180, 850, 240, true, "center", "宋体", 100, "000");
     
     sText = "一般风险准备率";
     icbcprint.printText(sText, 300, 240, 850, 300, true, "center", "宋体", 100, "000");
     
     sText = "公司自有资产负债率";
     icbcprint.printText(sText, 300, 300, 850, 360, true, "center", "宋体", 100, "000");
     
     sText = "公司自由流动比率";
     icbcprint.printText(sText, 300, 360, 850, 420, true, "center", "宋体", 100, "000");
     
     sText = "已获利息倍数";
     icbcprint.printText(sText, 300, 420, 850, 480, true, "center", "宋体", 100, "000");
     
     sText = "净资产收益率";
     icbcprint.printText(sText, 300, 480, 850, 540, true, "center", "宋体", 100, "000");
     
     sText = "营业收入利润率";
     icbcprint.printText(sText, 300, 540, 850, 600, true, "center", "宋体", 100, "000");
     
     sText = "自营证券收益率";
     icbcprint.printText(sText, 300, 600, 850, 660, true, "center", "宋体", 100, "000");
     
     sText = "净资本增长率";
     icbcprint.printText(sText, 300, 660, 850, 720, true, "center", "宋体", 100, "000");
     
     sText = "净利润增长率";
     icbcprint.printText(sText, 300, 720, 850, 780, true, "center", "宋体", 100, "000");
     
     			for(var i=1;i<4;i++)
     		{
     			
     			sText =ration_score[i];
     			 icbcprint.printText(sText, 850, 60+60*i, 1050, 120+60*i, true, "center", "宋体", 100, "000");
     			
     			sText =  ration_infoArry[i-1];
     			 icbcprint.printText(sText, 1050, 60+60*i, 1250, 120+60*i, true, "center", "宋体", 100, "000");
          		}
     
     
     
     
     	for(var i=4;i<12;i++)
     		{
     			
     			sText =ration_score[i+1];
     			 icbcprint.printText(sText, 850, 60+60*i, 1050, 120+60*i, true, "center", "宋体", 100, "000");
     			
     			sText =  ration_infoArry[i];
     			 icbcprint.printText(sText, 1050, 60+60*i, 1250, 120+60*i, true, "center", "宋体", 100, "000");
          		}
          		
          	
          		
          			sText = ration_infoArry[13];
     			 icbcprint.printText(sText, 1250, 120, 1450, 300, true, "center", "宋体", 100, "000");
     			
     			sText =  ration_infoArry[14];
     			 icbcprint.printText(sText, 1450, 120, 1700, 300, true, "center", "宋体", 100, "000");
     			 
     			 sText =  ration_infoArry[15];
     			 icbcprint.printText(sText, 1250, 300, 1450, 480, true, "center", "宋体", 100, "000");
     			 
     			 sText =  ration_infoArry[16];
     			 icbcprint.printText(sText, 1450, 300, 1700, 480, true, "center", "宋体", 100, "000");
     			 
     			 sText =  ration_infoArry[17];
     			 icbcprint.printText(sText, 1250, 480, 1450, 660, true, "center", "宋体", 100, "000");
     			 
     			 sText =  ration_infoArry[18];
     			 icbcprint.printText(sText, 1450, 480, 1700, 660, true, "center", "宋体", 100, "000");
     			 
     			 sText =  ration_infoArry[19];
     			 icbcprint.printText(sText, 1250, 660, 1450, 780, true, "center", "宋体", 100, "000");
     			 
     			 sText =  ration_infoArry[20];
     			 icbcprint.printText(sText, 1450, 660, 1700, 780, true, "center", "宋体", 100, "000");
          	          			
          		sText =  ration_infoArry[23];
     		icbcprint.printText(sText, 300, 780, 1700, 840, true, "center", "宋体", 100, "000");
          	}
		
     
     
     
                                
              if (enptype=="06")
     	{
    sText = "一、安全性";
     icbcprint.printText(sText, 0, 120, 300, 240, true, "center", "宋体", 100, "000");

     sText = "二、偿债能力";
     icbcprint.printText(sText, 0, 240, 300, 360, true, "center", "宋体", 100, "000");

     sText = "三、盈利能力";
     icbcprint.printText(sText, 0, 360, 300, 480, true, "center", "宋体", 100, "000");
     
     sText = "四、发展能力";
     icbcprint.printText(sText, 0, 480, 300, 660, true, "center", "宋体", 100, "000");
     
     sText = "五、资产及负债结构";
     icbcprint.printText(sText, 0, 660, 300, 780, true, "center", "宋体", 100, "000");

     
     sText = "定量评价总得分";
     icbcprint.printText(sText, 0, 780, 300, 840, true, "center", "宋体", 100, "000");
     
     sText = "资本充足率";
     icbcprint.printText(sText, 300, 120, 850, 180, true, "center", "宋体", 100, "000");
     
     sText = "不良贷款比率";
     icbcprint.printText(sText, 300, 180, 850, 240, true, "center", "宋体", 100, "000");
     
     sText = "流动比率";
     icbcprint.printText(sText, 300, 240, 850, 300, true, "center", "宋体", 100, "000");
     
     sText = "总债务/EBITDA";
     icbcprint.printText(sText, 300, 300, 850, 360, true, "center", "宋体", 100, "000");
     
     sText = "净资产收益率";
     icbcprint.printText(sText, 300, 360, 850, 420, true, "center", "宋体", 100, "000");
     
     sText = "营业收入利润率";
     icbcprint.printText(sText, 300, 420, 850, 480, true, "center", "宋体", 100, "000");
     
     sText = "净利润增长率";
     icbcprint.printText(sText, 300, 480, 850, 540, true, "center", "宋体", 100, "000");
     
     sText = "存款增长率";
     icbcprint.printText(sText, 300, 540, 850, 600, true, "center", "宋体", 100, "000");
     
     sText = "控股公司净资产增长率";
     icbcprint.printText(sText, 300, 600, 850, 660, true, "center", "宋体", 100, "000");
     
     sText = "拆入资金比率";
     icbcprint.printText(sText, 300, 660, 850, 720, true, "center", "宋体", 100, "000");
     
     sText = "长期负债比率";
     icbcprint.printText(sText, 300, 720, 850, 780, true, "center", "宋体", 100, "000");
     
     
     
     	
     
			sText =ration_score[1];
     			 icbcprint.printText(sText, 850, 60+60*1, 1050, 120+60*1, true, "center", "宋体", 100, "000");
     			
     			sText =  ration_infoArry[0];
     			 icbcprint.printText(sText, 1050, 60+60*1, 1250, 120+60*1, true, "center", "宋体", 100, "000");
     			 
     			 

     
     			sText =ration_score[13];
     			 icbcprint.printText(sText, 850, 60+60*2, 1050, 120+60*2, true, "center", "宋体", 100, "000");
     			
     			sText =  ration_infoArry[26];
     			 icbcprint.printText(sText, 1050, 60+60*2, 1250, 120+60*2, true, "center", "宋体", 100, "000");
     			 
     			 
     			 
     			 sText =ration_score[3];
     			 icbcprint.printText(sText, 850, 60+60*3, 1050, 120+60*3, true, "center", "宋体", 100, "000");
     			
     			sText =  ration_infoArry[2];
     			 icbcprint.printText(sText, 1050, 60+60*3, 1250, 120+60*3, true, "center", "宋体", 100, "000");
     			 
     			 
     			 
     			 sText =ration_score[14];
     			 icbcprint.printText(sText, 850, 60+60*4, 1050, 120+60*4, true, "center", "宋体", 100, "000");
     			
     			sText =  ration_infoArry[27];
     			 icbcprint.printText(sText, 1050, 60+60*4, 1250, 120+60*4, true, "center", "宋体", 100, "000");
     
     
     
     
     	for(var i=5;i<11;i++)
     		{
     			
     			sText =ration_score[i+1];
     			 icbcprint.printText(sText, 850, 60+60*i, 1050, 120+60*i, true, "center", "宋体", 100, "000");
     			
     			sText =  ration_infoArry[i];
     			 icbcprint.printText(sText, 1050, 60+60*i, 1250, 120+60*i, true, "center", "宋体", 100, "000");
          		}
          		
          	
          		
          			sText = ration_infoArry[13];
     			 icbcprint.printText(sText, 1250, 120, 1450, 240, true, "center", "宋体", 100, "000");
     			
     			sText =  ration_infoArry[14];
     			 icbcprint.printText(sText, 1450, 120, 1700, 240, true, "center", "宋体", 100, "000");
     			 
     			 sText =  ration_infoArry[15];
     			 icbcprint.printText(sText, 1250, 240, 1450, 360, true, "center", "宋体", 100, "000");
     			 
     			 sText =  ration_infoArry[16];
     			 icbcprint.printText(sText, 1450, 240, 1700, 360, true, "center", "宋体", 100, "000");
     			 
     			 sText =  ration_infoArry[17];
     			 icbcprint.printText(sText, 1250, 360, 1450, 480, true, "center", "宋体", 100, "000");
     			 
     			 sText =  ration_infoArry[18];
     			 icbcprint.printText(sText, 1450, 360, 1700, 480, true, "center", "宋体", 100, "000");
     			 
     			 sText =  ration_infoArry[19];
     			 icbcprint.printText(sText, 1250, 480, 1450, 660, true, "center", "宋体", 100, "000");
     			 
     			 sText =  ration_infoArry[20];
     			 icbcprint.printText(sText, 1450, 480, 1700, 660, true, "center", "宋体", 100, "000");
     			 
     			 sText =  ration_infoArry[21];
     			 icbcprint.printText(sText, 1250, 660, 1450, 780, true, "center", "宋体", 100, "000");
     			 
     			 sText =  ration_infoArry[22];
     			 icbcprint.printText(sText, 1450, 660, 1700, 780, true, "center", "宋体", 100, "000");
          	          			
          		sText =  ration_infoArry[23];
     		icbcprint.printText(sText, 300, 780, 1700, 840, true, "center", "宋体", 100, "000");
          	}
            
		
		
  if (enptype=="07")
     	{
    sText = "一、安全性";
     icbcprint.printText(sText, 0, 120, 300, 240, true, "center", "宋体", 100, "000");

     sText = "二、偿债能力";
     icbcprint.printText(sText, 0, 240, 300, 420, true, "center", "宋体", 100, "000");

     sText = "三、盈利能力";
     icbcprint.printText(sText, 0, 420, 300, 600, true, "center", "宋体", 100, "000");
     
     sText = "四、发展能力";
     icbcprint.printText(sText, 0, 600, 300, 720, true, "center", "宋体", 100, "000");
     
     

     
     sText = "定量评价总得分";
     icbcprint.printText(sText, 0, 720, 300, 780, true, "center", "宋体", 100, "000");
     
     sText = "信托业务规模率";
     icbcprint.printText(sText, 300, 120, 850, 180, true, "center", "宋体", 100, "000");
     
     sText = "总不良资产比率";
     icbcprint.printText(sText, 300, 180, 850, 240, true, "center", "宋体", 100, "000");
     
     sText = "公司自有资产负债率";
     icbcprint.printText(sText, 300, 240, 850, 300, true, "center", "宋体", 100, "000");
     
     sText = "公司拆借负债率";
     icbcprint.printText(sText, 300, 300, 850, 360, true, "center", "宋体", 100, "000");
     
     sText = "公司自由流动比率";
     icbcprint.printText(sText, 300, 360, 850, 420, true, "center", "宋体", 100, "000");
     
     sText = "公司净资产收益率";
     icbcprint.printText(sText, 300, 420, 850, 480, true, "center", "宋体", 100, "000");
     
     sText = "公司营业收入利润率";
     icbcprint.printText(sText, 300, 480, 850, 540, true, "center", "宋体", 100, "000");
     
     sText = "信托资产收益率";
     icbcprint.printText(sText, 300, 540, 850, 600, true, "center", "宋体", 100, "000");
     
     sText = "信托资产增长率";
     icbcprint.printText(sText, 300, 600, 850, 660, true, "center", "宋体", 100, "000");
     
     sText = "公司净利润增长率";
     icbcprint.printText(sText, 300, 660, 850, 720, true, "center", "宋体", 100, "000");
     
     
     
     
     
     	for(var i=1;i<9;i++)
     		{
     			
     			sText =ration_score[i];
     			 icbcprint.printText(sText, 850, 60+60*i, 1050, 120+60*i, true, "center", "宋体", 100, "000");
     			
     			sText =  ration_infoArry[i-1];
     			 icbcprint.printText(sText, 1050, 60+60*i, 1250, 120+60*i, true, "center", "宋体", 100, "000");
          		}
          		
          		var i=9;
          		sText =ration_score[13];
     			 icbcprint.printText(sText, 850, 60+60*i, 1050, 120+60*i, true, "center", "宋体", 100, "000");
     			
     			sText =  ration_infoArry[26];
     			 icbcprint.printText(sText, 1050, 60+60*i, 1250, 120+60*i, true, "center", "宋体", 100, "000");
     			 
     			 
     			 	var i=10;
          		sText =ration_score[i];
     			 icbcprint.printText(sText, 850, 60+60*i, 1050, 120+60*i, true, "center", "宋体", 100, "000");
     			
     			sText =  ration_infoArry[i-1];
     			 icbcprint.printText(sText, 1050, 60+60*i, 1250, 120+60*i, true, "center", "宋体", 100, "000");
          	
          	
          	
          	
          		
          			sText = ration_infoArry[13];
     			 icbcprint.printText(sText, 1250, 120, 1450, 240, true, "center", "宋体", 100, "000");
     			
     			sText =  ration_infoArry[14];
     			 icbcprint.printText(sText, 1450, 120, 1700, 240, true, "center", "宋体", 100, "000");
     			 
     			 sText =  ration_infoArry[15];
     			 icbcprint.printText(sText, 1250, 240, 1450, 420, true, "center", "宋体", 100, "000");
     			 
     			 sText =  ration_infoArry[16];
     			 icbcprint.printText(sText, 1450, 240, 1700, 420, true, "center", "宋体", 100, "000");
     			 
     			 sText =  ration_infoArry[17];
     			 icbcprint.printText(sText, 1250, 420, 1450, 600, true, "center", "宋体", 100, "000");
     			 
     			 sText =  ration_infoArry[18];
     			 icbcprint.printText(sText, 1450, 420, 1700, 600, true, "center", "宋体", 100, "000");
     			 
     			 sText =  ration_infoArry[19];
     			 icbcprint.printText(sText, 1250, 600, 1450, 720, true, "center", "宋体", 100, "000");
     			 
     			 sText =  ration_infoArry[20];
     			 icbcprint.printText(sText, 1450, 600, 1700, 720, true, "center", "宋体", 100, "000");
     			 
     			
          	          			
          		sText =  ration_infoArry[23];
     		icbcprint.printText(sText, 300, 780, 1700, 840, true, "center", "宋体", 100, "000");
          	}
     		
     		
     		
     		
     		
     		
     		
     		
     		
     			
		
		
  icbcprint.printEndTable(10);	
  icbcprint.printEndPage();
  icbcprint.printStartPage();
}
  
  
  	  var sText = "定性评价得分明细";
     	icbcprint.printText(sText, 150, 270, 1950, 360, false, "center", "宋体", 120, "010");	
  
   icbcprint.printBeginTable(190, 360, 190+1310, 360+1620, 1);
   
     sText = "评价内容";
     icbcprint.printText(sText, 0, 0, 300, 60, true, "center", "宋体", 100, "000");

     sText = "评议指标";
     icbcprint.printText(sText, 300, 0, 800, 60, true, "center", "宋体", 100, "000");

     sText = "得分";
     icbcprint.printText(sText, 800, 0, 1150, 60, true, "center", "宋体", 100, "000");

     sText = "实际得分";
     icbcprint.printText(sText, 1150, 0, 1500, 60, true, "center", "宋体", 100, "000");
     
     	sText = "支持评价";
     	icbcprint.printText(sText, 0, 60, 300, 420, true, "center", "宋体", 100, "000");
     	
     	sText = "管理状况";
     	icbcprint.printText(sText, 0, 420, 300, 720, true, "center", "宋体", 100, "000");
     	
     	sText = "内控水平";
     	icbcprint.printText(sText, 0, 720, 300, 960, true, "center", "宋体", 100, "000");
     	
     	sText = "市场竞争力";
     	icbcprint.printText(sText, 0, 960, 300, 1200, true, "center", "宋体", 100, "000");
     	
     	sText = "经营环境";
     	icbcprint.printText(sText, 0, 1200, 300, 1380, true, "center", "宋体", 100, "000");
     	
     	sText = "信誉状况";
     	icbcprint.printText(sText, 0, 1380, 300, 1560, true, "center", "宋体", 100, "000");
     	
     	if(typeFlag=="15"){
     	sText = "定性评价总得分";
     	icbcprint.printText(sText, 0,1560, 300, 1620, true, "center", "宋体", 100, "000");
	}
	
	if(typeFlag=="16"){
		
	sText = "经营情况";
     	icbcprint.printText(sText, 0, 1560, 300, 1740, true, "center", "宋体", 100, "000");
		
	sText = "定性评价总得分";
     	icbcprint.printText(sText, 0,1740, 300, 1800, true, "center", "宋体", 100, "000");
	}
	
	 
	if (typeFlag=="17")
	{
		
	sText = "发行保荐人情况";
     	icbcprint.printText(sText, 0, 1560, 300, 1680, true, "center", "宋体", 100, "000");
		
	sText = "定性评价总得分";
     	icbcprint.printText(sText, 0,1680, 300, 1740, true, "center", "宋体", 100, "000");
	}
     	
     	var j=25;
     	
     	if(typeFlag=="15"){j=25;}
     	if(typeFlag=="16"){j=28;}
     	if(typeFlag=="17"){j=27;}
     	
     	
     	
     	for (var i=0;i<j;i++)
     		{
     			sText = nature_nameArry[i];
     			icbcprint.printText(sText, 300, 60+i*60, 800, 120+i*60, true, "center", "宋体", 100, "000");		
     			
     			sText = nature_score[i];
     			icbcprint.printText(sText, 800, 60+i*60, 1150, 120+i*60, true, "center", "宋体", 100, "000");		
     	
     			sText = nature_infoArry[i];
     			icbcprint.printText(sText, 1150, 60+i*60, 1500, 120+i*60, true, "center", "宋体", 100, "000");		
   		}
   		

	
   		
   	if(typeFlag=="15"){
   	sText = nature_infoArry[28];
     	icbcprint.printText(sText, 300, 1560, 1500, 1620, true, "center", "宋体", 100, "000");
  	}
  	
  	if(typeFlag=="16"){
   	sText = nature_infoArry[28];
     	icbcprint.printText(sText, 300, 1740, 1500, 1800, true, "center", "宋体", 100, "000");
  	}
  	
  	if(typeFlag=="17"){
   	sText = nature_infoArry[28];
     	icbcprint.printText(sText, 300, 1680, 1500, 1740, true, "center", "宋体", 100, "000");
  	}
  	
   icbcprint.printEndTable(10);			
  
  icbcprint.printEndPage();
		
	}



/*** END 评级计分表***/

/**************  评级结论******************/

function pringEvaluateResult(evaluate_infoArry,opinion_infoArry){
     icbcprint.printStartPage();

	var y=300;
     var sText = "五、评级结论";
     icbcprint.printText(sText, 150, y, 1950, 360, false, "left", "黑体", 120, "000");

    	icbcprint.printBeginTable(180, y+70, 1700, 600, 0);
    	
    	    sText = "   财务报表时间";
	     icbcprint.printText(sText, 0, 0, 400, 60, true, "left",  "宋体", 100, "000");
	
	     var rdate = evaluate_infoArry[14];
	     var year = rdate.substring(0,4);
	     var month = rdate.substring(4,6);
	     sText = year+"年"+month+"月";
	     icbcprint.printText(sText, 400, 0, 700, 60, true, "center",  "宋体", 100, "000");
	     
	         sText = "   评议疑问标识";
	     icbcprint.printText(sText, 700, 0, 1000, 60, true, "left",  "宋体", 100, "000");

	     sText = "";
	     var flag = evaluate_infoArry[0];
	     if (flag=="0"){sText = "行内评议得分高于基本及修正评价得分25%";}
	     if (flag=="1"){sText = " ";}
	     icbcprint.printText(sText, 1000, 0, 1700, 60, true, "center",  "宋体", 100, "000");
	     
	       sText = "   基本及修正评价得分";
	     icbcprint.printText(sText, 0, 60, 400, 120, true, "left",  "宋体", 100, "000");

	     sText = evaluate_infoArry[1];
	     icbcprint.printText(sText, 400, 60, 700, 120, true, "center",  "宋体", 100, "000");

	     sText = "   实力评价得分";
	     icbcprint.printText(sText, 700, 60, 1000, 120, true, "left",  "宋体", 100, "000");

	     sText = evaluate_infoArry[2];
	     icbcprint.printText(sText, 1000, 60, 1700, 120, true, "center",  "宋体", 100, "000");
	     
	       sText = "   综合波动性系数";
	     icbcprint.printText(sText, 0, 120, 400, 180, true, "left",  "宋体", 100, "000");

	     sText = evaluate_infoArry[3];
	     icbcprint.printText(sText, 400, 120, 700, 180, true, "center",  "宋体", 100, "000");
	     
	         sText = "   支持评价系数";
	     icbcprint.printText(sText, 700, 120, 1000, 180, true, "left",  "宋体", 100, "000");

	     sText = evaluate_infoArry[4];
	     icbcprint.printText(sText, 1000, 120, 1700, 180, true, "center",  "宋体", 100, "000");
	     
	    sText = "   行内评价得分";
	     icbcprint.printText(sText, 0, 180, 400, 240, true, "left",  "宋体", 100, "000");

	     sText = evaluate_infoArry[5];
	     icbcprint.printText(sText, 400,180, 700, 240, true, "center",  "宋体", 100, "000");
	     
	     sText = "   综合评价得分";
	     icbcprint.printText(sText, 700, 180, 1000, 240, true, "left",  "宋体", 100, "000");

	     sText = evaluate_infoArry[6];
	     icbcprint.printText(sText, 1000, 180, 1700, 240, true, "center",  "宋体", 100, "000");
	     
	      sText = "   综合评价结果";
	     icbcprint.printText(sText, 0, 240, 400, 300, true, "left",  "宋体", 100, "000");

	     sText = evaluate_infoArry[7];
	     icbcprint.printText(sText, 400,240, 700, 300, true, "center",  "宋体", 100, "000");
	     
	     sText = "   行业调整得分";
	     icbcprint.printText(sText, 700, 240, 1000, 300, true, "left",  "宋体", 100, "000");

	     sText = evaluate_infoArry[8];
	     icbcprint.printText(sText, 1000, 240, 1700, 300, true, "center",  "宋体", 100, "000");
	     
	     sText = "  行业调整结果";
	     icbcprint.printText(sText, 0, 300, 400, 360, true, "left",  "宋体", 100, "000");

	     sText = evaluate_infoArry[9];
	     icbcprint.printText(sText, 400,300, 700, 360, true, "center",  "宋体", 100, "000");

	     sText = "   预警指标警示情况：";
	     icbcprint.printText(sText, 0, 360, 700, 420, true, "left",  "宋体", 100, "000");
	     
	     var flag = evaluate_infoArry[10];
	      sText = "有";
	       if (flag==""){ sText = "无";}
	      icbcprint.printText(sText, 700, 360, 1700, 420, true, "left",  "宋体", 100, "000");
	      
	   
	       	sText = flag;	
                      icbcprint.printText(sText, 0, 420, 1700, 600, true, "left",  "宋体", 100, "000");
                      
                      sText = "  特例调整及级别限定：";
	     icbcprint.printText(sText, 0, 600, 700, 660, true, "left",  "宋体", 100, "000");
	     
	     var flag = evaluate_infoArry[11];
	      sText = "有";
	       if (flag==""){ sText = "无";}
	      icbcprint.printText(sText, 700, 600, 1700, 660, true, "left",  "宋体", 100, "000");
	      
	   
	       	sText = flag;	
                      icbcprint.printText(sText, 0, 660, 1700, 840, true, "left",  "宋体", 100, "000");
                      
                      
                          sText = "   调整后信用等级";
	     icbcprint.printText(sText, 0, 840, 400, 900, true, "left",  "宋体", 100, "000");

	     sText = evaluate_infoArry[12];
	     icbcprint.printText(sText, 400,840, 700, 900, true, "center",  "宋体", 100, "000");
	     
	     sText = "   评价结论";
	     icbcprint.printText(sText, 700, 840, 1000, 900, true, "left",  "宋体", 100, "000");

	     sText = evaluate_infoArry[13];
	     icbcprint.printText(sText, 1000, 840, 1700, 900, true, "center",  "宋体", 100, "000");
	     
	     
	     
	     
	     
	     
	     sText = "对客户信用状况的综合评价（如果建议调整评级结果，请说明支持调级的理由，特别要对重大事项进行说明）";
     icbcprint.printText(sText, 160, 800, 1700, 1520, false, "left",  "宋体", 120, "010");

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

/*******************END 评级结论*********************/


/*  打印落款*/
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