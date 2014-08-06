<%@ page contentType="text/xml;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="/META-INF/c.tld" %>
<anychart>
  <settings>
    <animation enabled="true" />
  </settings>
  <gauges>
    <gauge template="gaugesTemplate">
      <chart_settings>
        <title>
          <text><![CDATA[累积推荐率：${data.sumrecommends}%，当日推荐率：${data.recommendnum}%]]></text>
        </title>
        <data_plot_background>
			<effects enabled="true"/>
		</data_plot_background>
		<chart_background enabled="false">
       		<border enabled="false"/>
       	</chart_background>
    	<format><![CDATA[{%Icon} {%Name} ({%Value})]]></format>
	          <template />
	          <title enabled="false">
					<font bold="true" size="12"/>
				</title>
	          <columns_separator enabled="false" />
	          <background>
	            <inside_margin left="10" right="10" />
	          </background>
          <legend enabled="false" />
   		 <controls>
          <label text_align="center" position="Fixed" anchor="RightBottom" inside_dataplot="true" >
          <font size="11"/>
            <text><![CDATA[蓝色为：当天投递量${data.todayDeliver}，红色为：累积投递量${data.totalDeliver}]]></text>
          </label>
   		 </controls>
      </chart_settings>
      <linear name="Volume" orientation="Horizontal"  x="0" y="20">
        <axis size="0" position="10">
          <scale minimum="0" maximum="100" major_interval="10" />
          <labels padding="5" >
            <format>{%Value}{numDecimals:0}</format>
          </labels>
          <scale_bar enabled="false" />
          <color_ranges>
            <color_range start="0" end="100" align="Outside" padding="0" start_size="8" end_size="8">
              <fill type="Gradient">
                <gradient angle="0">
                  <key color="Red" />
                  <key color="Yellow" />
                  <key color="Green" />
                </gradient>
              </fill>
              <border enabled="true" type="Solid" color="Black" opacity="0.4" />
            </color_range>
          </color_ranges>
        </axis>
        <pointers>
          <pointer type="Marker" value="${data.todayDeliver}" color="Blue" >
            <tooltip enabled="true" />
            <marker_pointer_style align="Outside" padding="5" width="10" height="10" />
            <animation enabled="true" start_time="0" duration="1" interpolation_type="Back" />
          </pointer>
        </pointers>
      </linear>
      
      
      <linear name="Pressure" orientation="Horizontal"  x="0" y="20">
        <axis size="0" position="50">
          <scale minimum="0" maximum="5000" major_interval="500" />
          <labels padding="5" >
            <format>{%Value}{numDecimals:0}</format>
          </labels>
          <scale_bar enabled="false" />
          <color_ranges>
            <color_range start="0" end="5000" align="Outside" padding="0" start_size="8" end_size="8">
              <fill type="Gradient">
                <gradient angle="0">
                  <key color="#1D8BD1" />
                  <key color="#F1683C" />
                  <key color="#2AD62A" />
                </gradient>
              </fill>
              <border enabled="true" type="Solid" color="Black" opacity="0.4" />
            </color_range>
          </color_ranges>
        </axis>
        <pointers>
          <pointer type="Marker" value="${data.totalDeliver}" color="Red">
            <tooltip enabled="true" />
            <marker_pointer_style align="Outside" padding="5" width="10" height="10" />
            <animation enabled="true" start_time="0" duration="1" interpolation_type="Back" />
          </pointer>
        </pointers>
      </linear>
    </gauge>
  </gauges>
</anychart>