<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<#import "/base/macro/headMacro.ftl" as headMacro>
<#setting number_format="#">
<head>
<@headMacro.headMacro/>
<script type="text/javascript" src="${rc.contextPath}/js/jquery/highcharts/highcharts.js"></script>
<script type="text/javascript" src="${rc.contextPath}/js/jquery/highcharts/modules/thd_exporting.src.js"></script>
<script type="text/javascript">
var chart;
$(document).ready(function() {
	var url = $('#operatorSex').attr('action');
	$.post(url,null,function(data){
		Highcharts.setOptions({colors: ['#DDDF00', '#50B432', '#ED561B', '#058DC7', '#24CBE5', '#64E572', '#FF9655',  '#FFF263', '#6AF9C4']});
		var colors = Highcharts.getOptions().colors,
		chart = new Highcharts.Chart(
		{
			chart : {
				renderTo : 'container'
			},
			title : {
				text : '用户比例'
			},
			subtitle : {
				text : '来源: 凯捷技术'
			},
			xAxis : {
				categories : ['男','女']
			},
			yAxis : [{
					min : 0,
					title : {text : '人数 (人)'}
				},{
					min : 0,
					opposite:true,
					title : {text : '人数 (%)'}
				}
			],
			legend : {
				layout : 'vertical',
				backgroundColor : '#FFFFFF',
				align : 'left',
				verticalAlign : 'top',
				x : 100,
				y : 70,
				floating : true,
				shadow : true
			},
			tooltip : {
				formatter : function() {
					return '' + this.x + ': ' + this.y + (this.series.name=='主管%'||this.series.name=='全体员工%'?'/'+data.totle:'人');
				}
			},
			credits:{//标签控制
				enabled:false
			},
			exporting:{//导出
				enabled:true, //是否显示导出按钮 默认显示true
				url:$('#export').attr('action')+'/common/exportSvg',
				filename:'operatorsex'
			},
			plotOptions : {
				column : {
					pointPadding : 0.2,
					borderWidth : 0
				}
			},
			series : 
			[{
				type: 'column',
				name : '全体员工',
				data : [ data.x ,data.y]
			},{
				type: 'column',
				name : '主管',
				data : [ data.a ,data.b]
			},{
				type: 'line',
				name : '全体员工%',
				yAxis:1,
				data : [ data.x ,data.y]
			},{
				type: 'line',
				name : '主管%',
				data : [ data.a ,data.b]
			}]
		});
	});
});
</script>
</head>
<body>
<form id="operatorSex" action="${rc.contextPath}/report/getClumnSex"></form>
<form id="export" action="${rc.contextPath}"></form>
<div id="container" style="width: 1000px; height: 500px; margin: 0 auto"></div>
</body>
</html>
