<%@ page contentType="text/xml;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="/META-INF/c.tld" %>
<anychart>
	<margin top="-20" left="-10" right="-10" bottom="0"/>
	<settings>
        <animation enabled="true"/>
    </settings>
  <charts>
    <chart plot_type="${chart}">
      <chart_settings>
        <title enabled="false">
        </title>
        <chart_background enabled="false">
   			<border enabled="false"/>
   		</chart_background> 
   		<!--图例显示设置
			<legend enabled="true" position="right" align="Near" ignore_auto_item="true">
				<format>
				{%Icon} {%Name} ({%Value}{numDecimals:0})
				</format>
				<template></template>
				<title enabled="true">
					<font bold="true" size="12"/>
					<text>图例</text>
				</title>
				<columns_separator enabled="false" />
				<background>
					<inside_margin left="10" right="10" />
				</background>
				<items>
					<item source="Points" />
				</items>
			</legend>
			-->
        <axes>
          <x_axis>
            <title enabled="false">
            </title>
            <labels padding="-3"/>
          </x_axis>
          <y_axis >
               <title>
                    <text></text>
                </title>
                <labels align="Inside">
					<format>{%Value}{numDecimals:0}</format>
				</labels>
            </y_axis>
        </axes>
      </chart_settings>
      <data_plot_settings default_series_type="${chart}" enable_3d_mode="${s3d}" z_padding="0.2" z_aspect="0.8">
			<bar_series shape_type="${cylinder}" point_padding="0" group_padding="0.2">
				<label_settings enabled="True" >
					<position anchor="CenterTop"/>
					<background enabled="false"/>
					<font color="Black" bold="false" size="8">
						<effects enabled="true">
							<drop_shadow enabled="true" color="White" opacity="0.3" distance="2"/>
						</effects>
					</font>
				   <format>{%Value}{numDecimals:0}</format>
				</label_settings>
				<tooltip_settings enabled="True"> 
					<background> 
					  <border color="DarkColor(%Color)" /> 
					</background> 
					<format><![CDATA[{%Name}: {%Value}{numDecimals:0}]]></format> 
				</tooltip_settings> 
				<bar_style>
					<states>
						<normal>
							<fill color="%Color"/>
							<border color="DarkColor(%Color)" thickness="1"/>
						</normal>
						<hover>
							<fill color="LightColor(%Color)"/>
							<border thickness="2"/>
						</hover>
					</states>
				</bar_style>
			</bar_series>
			<!--折线图配置信息-->
			<line_series>
				<label_settings enabled="True" >
				   <format>{%Value}{numDecimals:0}</format>
				</label_settings>
				<tooltip_settings enabled="True"> 
					<background> 
					  <border color="DarkColor(%Color)" /> 
					</background> 
					<format><![CDATA[{%Name}: {%Value}{numDecimals:0}]]></format> 
				</tooltip_settings>
				<marker_settings enabled="true"/>
			</line_series>
			<!--饼图配置信息-->
			<pie_series>
				<label_settings enabled="true" mode="Outside" multi_line_align="Center">
					<background enabled="false"/>
					<position anchor="Center" valign="Center" halign="Center" padding="10%"/>
					<format>{%Name}({%Value}{numDecimals:0})</format>
					<font bold="False"/>
		            <states>
		              <hover>
		                <font underline="true"/>
		              </hover>
		            </states>
				</label_settings>
				<tooltip_settings enabled="True">
					<format>{%Name}: {%Value}{numDecimals:0}</format>
					<background>
						<border color="DarkColor(%Color)"/>
						<font>
						</font>
					</background>
					<font color="DarkColor(%Color)"/>
				</tooltip_settings>
				<connector color="Black" opacity="0.4" />
			</pie_series>
			
			
			<!--区域配置信息-->
			<area_series point_padding="0.2" group_padding="1">
					<label_settings enabled="true">
						<background enabled="false"/>
						<font color="Rgb(45,45,45)" bold="true" size="9">
							<effects enabled="true">
								<glow enabled="true" color="White" opacity="1" blur_x="1.5" blur_y="1.5" strength="3"/>
							</effects>
						</font>
						<format>{%YValue}{numDecimals:0}</format>
					</label_settings>
					<tooltip_settings enabled="True">
						<format>{%Name}: {%Value}{numDecimals:0}</format>
						<background>
							<border color="DarkColor(%Color)"/>
							<font>
							</font>
						</background>
						<font color="DarkColor(%Color)"/>
					</tooltip_settings>
					<area_style>
						<line enabled="true" color="DarkColor(%Color)"/>
						<fill color="%Color" opacity="0.8"/>
						<states>
							<hover>
								<fill color="LightColor(%Color)"/>
							</hover>
						</states>
					</area_style>
					<marker_settings enabled="true"/>
				</area_series>
    		
    		
    		<!--標記配置信息-->
			<marker_series>
				<label_settings enabled="true">
						<background enabled="false"/>
						<font color="Rgb(45,45,45)" bold="true" size="9">
							<effects enabled="true">
								<glow enabled="true" color="White" opacity="1" blur_x="1.5" blur_y="1.5" strength="3"/>
							</effects>
						</font>
						<format>{%YValue}{numDecimals:0}</format>
					</label_settings>
		          <marker_style>
		            <marker size="14" />
		            <effects>
		              <drop_shadow enabled="true" />
		            </effects>
		          </marker_style>
		          <tooltip_settings enabled="True"> 
					<background> 
					  <border color="DarkColor(%Color)" /> 
					</background> 
					<format><![CDATA[{%Name}: {%Value}{numDecimals:0}]]></format> 
				</tooltip_settings>
	       		</marker_series>
    		
    		
		</data_plot_settings>
      <data>
        <series name="Series 1" >
	    		<c:forEach var="item" items="${data1}">
		             <point name="${item.name}" y="${item.value}" />
		         </c:forEach>
	    	</series>
	    	
	    	<series name="Series 2" color="#ff6000" y_axis="extra_y_axis_1">
	    		<c:forEach var="item" items="${data2}">
		             <point name="${item.name}" y="${item.value}" />
		         </c:forEach>
	    	</series>
      </data>
    </chart>
  </charts>
</anychart>
			
			