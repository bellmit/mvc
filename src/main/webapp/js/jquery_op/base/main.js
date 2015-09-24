$(document).ready(function() {
	$('body').omBorderLayout({
				panels : [{
							id : "north-panel",
							resizable : false,
							collapsible : false,
							fit : true,
							header : false,
							region : "north"
						}, {
							id : "center-panel",
							header : false,
							region : "center"
						}, {
							id : "west-panel",
							resizable : true,
							region : "west",
							width : 289
						}]
			});

	var tabElement = $('#center-tab').omTabs({
				height : "fit"
			});

	var ifh = tabElement.height()
			- tabElement.find(".om-tabs-headers").outerHeight() - 4; // 为了照顾apusic皮肤，apusic没有2px的padding，只有边框，所以多减去2px
	$('#3Dbox').height(ifh);
	//加载个人提醒页面
	main.refreshPerson();
	//加载部门提醒页面
	main.refreshDepartments();
	//加载滚动提醒页面
	main.refreshRoll();
	//加载加载首页列表
//	main.showRollTaskForm();
	//加载我的紧急案件、待结案案件提醒
	main.remindAndSerious();
	
	if($("#userStatus").val() == "0"){
		if(confirm("您当前状态为Out Of Office，是否更改您的状态？")){
			var url = $("#updateUserStatusForm").attr("action");
			var param = {};
			// ajax访问服务器端
			$.post(url, param, function(data){
				alert(data.msg);
				
				// 关闭滚动条
				base.closepageloading();
			},"json");
		}
	}
	
	var approveFlag = $('#approveFlag').val();
	if(approveFlag == "1"){
		main.showApproveForm();
	}
	
	//我的待结案个数
//	var urlForGetRemindOwnerCaseCount = $('#common').attr('action')+'/case/getRemindOwnerCaseCount';
//	$.post(urlForGetRemindOwnerCaseCount, null, function(data) {
//			document.getElementById('count_2').innerHTML = data.count ;
//	})
//	//我的紧急案件个数
//	var urlForGetSeriousCaseCount = $('#common').attr('action')+'/case/getSeriousCaseCount';
//	$.post(urlForGetSeriousCaseCount, null, function(data) {
//			//alert(data.count);
//			document.getElementById('count_1').innerHTML = data.count ;
//	})
});
var main = {
	/**加载个人提醒**/
	refreshPerson : function() {
		var paramForm = $("#personRemindForm");
		var param = paramForm.serialize();
		$.post(paramForm.attr("action"), param, function(data) {
					$('#personRemind').html(data);
					base.closepageloading();
				});
	},/**加载部门提醒**/
	refreshDepartments : function() {
		var paramForm = $("#departmentsRemindForm");
		var param = paramForm.serialize();
		$.post(paramForm.attr("action"), param, function(data) {
					$('#departmentsRemind').html(data);
					base.closepageloading();
				});
	},/**加载滚动提醒**/
	refreshRoll : function() {
		var paramForm = $("#rollRemindForm");
		var param = paramForm.serialize();
		$.post(paramForm.attr("action"), param, function(data) {
					if(data != ""){
						$('#Marquee1').html(data);
					}
			
					
					base.closepageloading();
				});
	},
	loadPanel:function(){
		//加载个人提醒页面
		main.refreshPerson();
		//加载部门提醒页面
		main.refreshDepartments();
		//加载滚动提醒页面
		main.refreshRoll();
		//加载加载首页列表
//		main.showRollTaskForm();
	},
	showOrHideOne :function(){
		if($("#personRemind").attr("display")=="block"){
			$("#personRemind").attr("display","none");
			$("#personRemind").hide();
			$('#personImg').attr("class", "tit_icon_arrow1");
		}else{
			$("#personRemind").attr("display","block");
			$("#personRemind").show();
			$('#personImg').attr("class", "tit_icon_arrow2");
		}
	},
	showOrHideTwo :function(){
		if($("#departmentsRemind").attr("display")=="block"){
			$("#departmentsRemind").attr("display","none");
			$("#departmentsRemind").hide();
			$('#departmentsImg').attr("class", "tit_icon_arrow1");
		}else{
			$("#departmentsRemind").attr("display","block");
			$("#departmentsRemind").show();
			$('#departmentsImg').attr("class", "tit_icon_arrow2");
		}
	},
	showOrHideRoll :function(){
		if($("#rollRemind").attr("display")=="block"){
			$("#rollRemind").attr("display","none");
			$("#rollRemind").hide();
			$('#rollImg').attr("class", "tit_icon_arrow1");
		}else{
			$("#rollRemind").attr("display","block");
			$("#rollRemind").show();
			$('#rollImg').attr("class", "tit_icon_arrow2");
		}
		var url = $("#common").attr("action")+"/task/showRollTaskForm";
		base.addTab('showRollTaskForm', '任务到期提醒', url, true);
	},
	showRollTaskForm  :function(){
		var paramForm = $("#homePageShowRollTaskForm");
		var param =base.decodeURI(paramForm.serialize());//将查询的中文条件编码
		var url = paramForm.attr("action");
		$('#homePageShowRollTaskGrid').omGrid({
			method : 'post',
			height : 380,
			width : 'fit',
			autoFit : true,
			extraData : param,
			limit : base.pageSize,
			dataSource : url,
			colModel : [{
						header : '案件编号',
						name : 'caseCode',
						width : 100,
						align : 'left',
						renderer : function(colValue, rowData, rowIndex){
								return '<a class="f_blue" style="cursor:pointer;" onClick="main.showCaseDetailForm('+ rowData.caseId+')"> '+rowData.caseCode+'</a>';
						}					
					}, {
						header : '姓名',
						name : 'cupCustomerName',
						width : 100,
						align : 'left'
					}, {
						header : '投诉来源',
						name : 'complaintSourceL2',
						width : 100,
						align : 'left'
					}, {
						header : '任务编号',
						name : 'taskId',
						width : 100,
						align : 'left'
					}, {
						header : '创建人',
						name : 'crtUser',
						width : 100,
						align : 'left',
						renderer : function(v, rowData, rowIndex){
							return rowData.crtUserName;
						}
					}, {
						header : '创建时间',
						name : 'crtDttm',
						width : 100,
						align : 'left'
					}, {
						header : '处理人',
						name : 'owner',
						width : 100,
						align : 'left',
						renderer : function(v, rowData, rowIndex){
							if(rowData.ownerName){
								return rowData.ownerName;
							}
						}
					}, {
						header : '操作',
						name : '',
						width : 50,
						align : 'center',
						renderer : function(colValue, rowData, rowIndex) {
							var buttons = "";
							//若登陆人为创建人，则跳转到案件任务处理页面
							if (rowData.loginUser == rowData.crtUser) {
								return buttons += '<button onClick="main.showTask1('
									+ rowData.taskId +')">详情</button>';
							}else{//其他状态则到案件详细处理页面
							    if(rowData.status=='0'){
							    	return buttons += '<button onClick="main.showTask2('
									+ rowData.taskId +')">详情</button>';
							    }else{
							    	return buttons += '<button onClick="main.showTask3('
									+ rowData.taskId +')">详情</button>';
							    }
							}
						}
					}],
			onSuccess : function(data, textStatus, XMLHttpRequest, event) {
				base.ajaxSuccess(data);
			},
			onError : function(XMLHttpRequest, textStatus, event) {
				base.ajaxError(XMLHttpRequest, textStatus, event);
			}
		});
	},
	//跳转到案件任务处理页面
	showTask1 : function(taskId) {
		var url = $("#showTask").attr("action")+"/panel/publicDetailTaskForm";
			base.addTab('showTaskInCase', '案件任务处理', url + '?taskId=' + taskId, true);
	},
	//跳转到任务认领页面，此任务状态处于“未认领”
	showTask2 : function(taskId) {
		var url = $("#showTask").attr("action")+"/panel/taskQueryForm";
			base.addTab('13', '任务认领', url + '?taskId=' + taskId, true);
	},
	//跳转到任务处理页面，此任务状态处于处理中
	showTask3 : function(taskId) {
		var url = $("#showTask").attr("action")+"/panel/taskDealForm";
			base.addTab('adoptTask', '任务处理', url + '?taskId=' + taskId, true);
	},
	logout:function(url){
		var param = {};
		$.post(url, param, function() {
	
			if(navigator.appName == 'Microsoft Internet Explorer'){
				window.top.opener=null;
			}
			window.close();	

		});
	},
	showCaseDetailForm : function(caseId){//根据案件编号，转到案件处理页面		
		var url = $("#showTask").attr("action")+"/case/caseDetailForm";
		base.addTab('showCase', '案件处理详情', url + '?caseId=' + caseId, true);
	},
	showApproveForm:function(){
		var url = $("#approveDealForm").attr("action");
		base.addTab('showApprove', '审批处理', url + '?approveId=' + $('#approveId').val(), true);
	},
	remindAndSerious:function(){
		//我的待结案个数
//		var urlForGetRemindOwnerCaseCount = $('#common').attr('action')+'/case/getRemindOwnerCaseCount';
//		$.post(urlForGetRemindOwnerCaseCount, null, function(data) {
//				document.getElementById('count_2').innerHTML = data.count ;
//		})
		//我的紧急案件个数
		var urlForGetSeriousCaseCount = $('#common').attr('action')+'/case/getSeriousCaseCount';
		$.post(urlForGetSeriousCaseCount, null, function(data) {
		//alert(data.count);
			document.getElementById('count_1').innerHTML = data.count ;		
		})
		
		//我的紧急任务XX笔
		var urlForGetMySeriousTaskCount= $('#common').attr('action')+'/task/getMySeriousTaskCount';
		$.post(urlForGetMySeriousTaskCount, null, function(data) {
			document.getElementById('count_3').innerHTML = data.count ;
		});
		
		//为我完成的任务XX笔To complete the task for me
		var urlCompleteTaskForMeCount= $('#common').attr('action')+'/task/completeTaskForMeCount';
		$.post(urlCompleteTaskForMeCount, null, function(data) {
			document.getElementById('count_4').innerHTML = data.count ;
		});
		
		//我的提醒XX笔
		var urlCustomRemind= $('#common').attr('action')+'/common/getCustomRemindCount';
		$.post(urlCustomRemind, null, function(data) {
			document.getElementById('count_5').innerHTML = data.count ;
		});
	},
	showRemindOwner : function(caseId){		
		var url = $("#common").attr("action")+"/case/remindOwner";
		base.addTab('remindOwner', '待结案案件', url, true);
	},
	showCaseSeriousForm:function(){
		var url = $("#common").attr("action")+"/case/caseSeriousForm";
		base.addTab('caseSeriousForm', '我的紧急案件', url, true);
	},
	showTaskSeriousForm:function(){
		var url = $("#common").attr("action")+"/task/mySeriousTaskForm";
		base.addTab('mySeriousTask', '我的紧急任务', url, true);
	},
	showCompleteTaskForm:function(){
		var url = $("#common").attr("action")+"/task/completeTaskForMeForm";
		base.addTab('completeTaskForMeForm', '完成的任务(未读)', url, true);
	},
	showRollTaskForm:function(){
		var url = $("#common").attr("action")+"/task/showRollTaskForm";
		base.addTab('showRollTaskForm', '任务到期提醒', url, true);
	},
	showCustomRemindForm:function(){
		var url = $("#common").attr("action")+"/customRemind/showRemindFormList";
		base.addTab('showRemindFormList', '我的提醒', url, true);
	}
}