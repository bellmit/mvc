<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<html xmlns="http://www.w3.org/1999/xhtml">
<#import "/base/macro/headMacro.ftl" as headMacro>
<head>
<@headMacro.headMacro/>
<script type="text/javascript" src="${rc.contextPath}/js/jquery/highcharts/highcharts.js"></script>
<script type="text/javascript" src="${rc.contextPath}/js/jquery/highcharts/modules/thd_exporting.src.js"></script>
<script type="text/javascript">
var chart;
$(document).ready(function() {
	chart = new Highcharts.Chart({
		chart: {
			renderTo: 'container',
			plotBackgroundColor: null,
			plotBorderWidth: null,
			plotShadow: false
		},
		title: {
			text: '各种浏览器在开发人员中的使用比例'
		},
		tooltip: {//鼠标移动显示内容
			formatter: function() {
				return '&lt;b>'+ this.point.name +'</b>: '+ this.percentage +' %';
			}
		},
		plotOptions: {//直接显示的内容
			pie: {
				allowPointSelect: true,
				cursor: 'pointer',
				dataLabels: {
					enabled: true,
					color: '#000000',
					connectorColor: '#000000',
					formatter: function() {
						return '&lt;b>'+ this.point.name +'</b>: '+ this.percentage +' %';
					}
				}
			}
		},
		series: [{
			type: 'pie',
			name: 'Browser share',
			data: [
				['Firefox',   45.0],
				['IE',       26.8],
				{
					name: 'Chrome',
					y: 12.8,
					sliced: true,
					selected: true
				},
				['Safari',    8.5],
				['Opera',     6.2],
				['Others',   0.7]
				]
			}]
		});
	});
</script>
</head>
<body>
	<div id="container" style="width: 1000px; height: 500px; margin: 0 auto"></div>
</body>
</html>
