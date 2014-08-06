<%@ page contentType="text/xml;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="/META-INF/c.tld" %>
<anychart>
	<margin top="-10" left="0" right="0" bottom="0"/>
	<settings>
        <animation enabled="true"/>
    </settings>
    <charts>
    <chart plot_type="${chart}">
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
					<font color="Black" bold="false" size="12">
						<effects enabled="true">
							<drop_shadow enabled="true" color="White" opacity="0.3" distance="2"/>
						</effects>
					</font>
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
        <chart_settings>
        	<data_plot_background>
				<effects enabled="true"/>
			</data_plot_background>
			<chart_background enabled="false">
       			<border enabled="false"/>
       		</chart_background> 
			
            <title align="left">
            	<font bold="true" size="12" />
                <text><![CDATA[${title}]]></text>
            </title>
			<!--图例显示设置-->
			<legend enabled="True" position="Float" inside_dataplot="true" anchor="rightTop"  ignore_auto_item="false" rows_padding="0">
	          <format><![CDATA[{%Icon} {%Name} ({%Value}{numDecimals:0})]]></format>
	          <template />
	          <columns_separator enabled="false" />
	          <background>
	            <inside_margin left="10" right="10" />
	          </background>
	          <title enabled="false"/>
	          <icon>
	            <marker enabled="true" />
	          </icon>
	        </legend>
			
            <axes>
			 <x_axis tickmarks_placement="Center"> 
	             <labels rotation="${rotation}" align="inside" allow_overlap="false" /> 
	             <title enabled="False" /> 
	             <c:if test="${scrollbar }">
	             	<zoom enabled="true" start="0" visible_range="${scrollbar_range }" allow_drag="false"/>
	             </c:if> 
	          </x_axis>
                <y_axis >
                    <title enabled="False" /> 
                    <labels>
						<format>{%Value}{numDecimals:0}</format>
					</labels>
                </y_axis>
            </axes>
        </chart_settings>
        <data>
        	 <!-- 编制 -->
	    	<series name="招聘计划">
	    		 <c:forEach var="chartData" items="${data}">
	    			<point name="${chartData.name}" y="${chartData.value}"/>
	    		</c:forEach>
	    	</series>
     	</data>
    </chart>
    </charts>
</anychart>