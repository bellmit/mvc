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
		chart = new Highcharts.Chart({
			chart: {
				renderTo: 'container',
				plotBackgroundColor: null,
				plotBorderWidth: null,
				plotShadow: false
			},
			title: {//主标题
				text: '各种浏览器在开发人员中的使用比例'
			},
			subtitle:{//副标题
				text: '贺章鹏',
				style:{fontsize:'14px'},
				y:+40
			},
			tooltip: {
				formatter: function() {
					return '<b>'+ this.point.name +'</b>: '+Highcharts.numberFormat(this.percentage,2,".")  +' %';
				}
			},
			plotOptions: {
				pie: {
					allowPointSelect: true,
					cursor: 'pointer',
					dataLabels: {
						enabled: true,
						color: '#000000',
						connectorColor: '#000000',
						formatter: function() {
							return '<b>'+ this.point.name +'</b>: '+ this.percentage +' %';
						}
					}
				}
			},
			series: [{
				type: 'pie',
				name: 'Browser share',
				data: [
					//['男',   data.x],
					//['IE',       26.8],
					{
						name: '女',
						y: data.y,
						sliced: true,
						selected: true
					},
					['男',   data.x]
					//['Opera',     6.2],
					//['Others',   0.7]
					]
				}]
			});
	});
	
});
</script>
</head>
<body>
	<div id="container" style="width: 1000px; height: 500px; margin: 0 auto"></div>
	<form id="operatorSex" action="${rc.contextPath}/report/getOperatorSex"></form>
</body>
</html>
