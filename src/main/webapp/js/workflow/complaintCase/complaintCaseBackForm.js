var complaintCaseBackForm = {
	complaintObjTypeList:[],
	complaintGistList:[],
	schedulingTypeList:[],
	complaintTr1:'complaintTr1',
	complaintTr2:'complaintTr2',
	complaintTr3:'complaintTr3',
	number:0,
	numberMap:[],
	orgDialogIndex:0,
	operDialogIndex:0,
	supplierDialogIndex:0,
	relationComplaintMap:[],
	caseCodeStr:'',
	tableHtml:'',
	/**
	 * 页面初始化
	 * */
	init:function(caseCodeStr){
		complaintCaseBackForm.tableHtml = $('#complaintArea').html();
		complaintCaseBackForm.caseCodeStr = caseCodeStr;
		complaintCaseBackForm.numberMap = new TabMap();
		complaintCaseBackForm.relationComplaintMap = new TabMap();
		var commonParamForm = $('#commonParamForm');
		$.post(commonParamForm.attr("action"), {}, function(data){
			complaintCaseBackForm.complaintObjTypeList = data.complaintObjTypeList;
			complaintCaseBackForm.complaintGistList = data.complaintGistList;
			complaintCaseBackForm.schedulingTypeList = data.schedulingTypeList;
			complaintCaseBackForm.initComplaintObj();
			// 关闭滚动条
			base.closepageloading();
		});
		
		complaintCaseBackForm.initRelationComplaint(caseCodeStr);
		//机构列表初始化
		$('#orgDialogBtn').bind('click',function(){
			complaintCaseBackForm.orgDialogBtnClick();
		});
		//员工列表初始化
		$('#operDialogBtn').bind('click',function(){
			complaintCaseBackForm.operDialogBtnClick();
		});
		//供应商列表初始化
		$('#supplierDialogBtn').bind('click',function(){
			complaintCaseBackForm.supplierDialogBtnClick();
		});
		//车牌号
		$('#policyLicId').blur(function(){
			if($('#policyLicId').val() != ""){
				$("#relationComplaintForm input[name='policyLicId']").val($('#policyLicId').val().toUpperCase());
				$("#relationComplaintForm input[name='cplPhone']").val('');
				$("#relationComplaintForm input[name='cplMobile']").val('');
				var relationComplaintForm = $('#relationComplaintForm');
				$.post(relationComplaintForm.attr("action"), relationComplaintForm.serialize(), function(data){
					if(data==null || data.length <= 0){
						//alert("【车牌号："+$('#policyLicId').val().toUpperCase()+"】没有关联投诉！");
						complaintCaseBackForm.relationComplaintMap.remove('policyLicId');
					} else {
						complaintCaseBackForm.relationComplaintMap.remove('policyLicId');
						complaintCaseBackForm.relationComplaintMap.put('policyLicId',data);
					}
					complaintCaseBackForm.setRelationComplaint();
					// 关闭滚动条
					base.closepageloading();
				});
			} else {
				complaintCaseBackForm.relationComplaintMap.remove('policyLicId');
				complaintCaseBackForm.setRelationComplaint();
			}
		});
		//电话号
		$('#cplPhone3').blur(function(){
			if($('#cplPhone1').val() != "" && $('#cplPhone2').val() != "" && $('#cplPhone3').val() != ""){
				var cplPhone = $('#cplPhone1').val() + "-" + $('#cplPhone2').val() + "-" + $('#cplPhone3').val()
				$("#relationComplaintForm input[name='policyLicId']").val('');
				$("#relationComplaintForm input[name='cplPhone']").val(cplPhone);
				$("#relationComplaintForm input[name='cplMobile']").val('');
				var relationComplaintForm = $('#relationComplaintForm');
				$.post(relationComplaintForm.attr("action"), relationComplaintForm.serialize(), function(data){
					if(data==null || data.length <= 0){
						//alert("【电话号："+cplPhone+"】没有关联投诉！");
						complaintCaseBackForm.relationComplaintMap.remove('cplPhone');
					} else {
						complaintCaseBackForm.relationComplaintMap.remove('cplPhone');
						complaintCaseBackForm.relationComplaintMap.put('cplPhone',data);
					}
					complaintCaseBackForm.setRelationComplaint();
					// 关闭滚动条
					base.closepageloading();
				});
			} else {
				complaintCaseBackForm.relationComplaintMap.remove('cplPhone');
				complaintCaseBackForm.setRelationComplaint();
			}
		});
		//手机号
		$('#cplMobile').blur(function(){
			if($('#cplMobile').val() != ""){
				$("#relationComplaintForm input[name='policyLicId']").val('');
				$("#relationComplaintForm input[name='cplPhone']").val('');
				$("#relationComplaintForm input[name='cplMobile']").val($('#cplMobile').val());
				var relationComplaintForm = $('#relationComplaintForm');
				$.post(relationComplaintForm.attr("action"), relationComplaintForm.serialize(), function(data){
					if(data==null || data.length <= 0){
						//alert("【手机号："+$('#cplMobile').val()+"】没有关联投诉！");
						complaintCaseBackForm.relationComplaintMap.remove('cplMobile');
					} else {
						complaintCaseBackForm.relationComplaintMap.remove('cplMobile');
						complaintCaseBackForm.relationComplaintMap.put('cplMobile',data);
					}
					complaintCaseBackForm.setRelationComplaint();
					// 关闭滚动条
					base.closepageloading();
				});
			} else {
				complaintCaseBackForm.relationComplaintMap.remove('cplMobile');
				complaintCaseBackForm.setRelationComplaint();
			}
		});		
	},
	/**
	 * 关联投诉初始化
	 * */
	initRelationComplaint:function(caseCodeStr){
		if(caseCodeStr != null && caseCodeStr != ""){
			var policyLicId = [];
			var cplPhone = [];
			var cplMobile = [];
			var caseCodeList = caseCodeStr.split(",");
			$.each(caseCodeList,function(i,caseCodeAndKey){
				var key = caseCodeAndKey.split("/")[0];
				var caseCode = caseCodeAndKey.split("/")[1];
				if(key == "policyLicId"){
					policyLicId.push(caseCode);
				} else if(key == "cplPhone"){
					cplPhone.push(caseCode);
				} else if(key == "cplMobile"){
					cplMobile.push(caseCode);
				}
			});
			if(policyLicId.length > 0){
				complaintCaseBackForm.relationComplaintMap.put('policyLicId',policyLicId);
			}
			if(cplPhone.length > 0){
				complaintCaseBackForm.relationComplaintMap.put('cplPhone',cplPhone);
			}
			if(cplMobile.length > 0){
				complaintCaseBackForm.relationComplaintMap.put('cplMobile',cplMobile);
			}
			complaintCaseBackForm.setRelationComplaint();
		}
	},
	/**
	 * 设置关联投诉
	 * */
	setRelationComplaint:function(){
		$('#relationComplaint').html('');
		var noEqCaseCode = [];
//		$.each(complaintCaseBackForm.relationComplaintMap.keys(),function(i,key){
//			var caseCodeList = complaintCaseBackForm.relationComplaintMap.get(key);
//			$.each(caseCodeList,function(i,caseCode){
//				if(caseCode != null && caseCode != ""){
//					var href = $('<a href="javascript:void(0);">'+caseCode+'</a>').appendTo('#relationComplaint');
//					href.bind('click', function(){
//						alert(caseCode);
//					});
//				}
//			});
//		});

		$.each(complaintCaseBackForm.relationComplaintMap.keys(),function(i,key){
			var caseCodeList = complaintCaseBackForm.relationComplaintMap.get(key);
			$.each(caseCodeList,function(i,caseCode){
				if(caseCode != null && caseCode != "" && $.inArray(caseCode, noEqCaseCode)== -1){
					noEqCaseCode.push(caseCode);
				}
			});
		});
		noEqCaseCode.sort();
		$.each(noEqCaseCode,function(i,caseCode){
			var href = $('<a href="javascript:void(0);">'+caseCode+'</a>').appendTo('#relationComplaint');
			$('<span>&nbsp;;&nbsp;</span>').appendTo('#relationComplaint');
			href.bind('click', function(){
				alert(caseCode);
			});
		});
	},
	/**
	 * 设置被投诉对象
	 * */
	initComplaintObj:function(){
		var complaintSubObjectListForm = $('#complaintSubObjectListForm');
		$.post(complaintSubObjectListForm.attr("action"), complaintSubObjectListForm.serialize(), function(data){
			if(data != null){
				complaintCaseBackForm.createComplaintArea(complaintCaseBackForm.number,data);
			} else {
				complaintCaseBackForm.createComplaintArea(complaintCaseBackForm.number);
			}
			
			// 关闭滚动条
			base.closepageloading();
		});
	},
	/**
	 * 添加被投诉对象
	 * */
	addComplaintObj:function(){
		complaintCaseBackForm.createComplaintArea(complaintCaseBackForm.number);
	},
	/**
	 * 创建投诉对象
	 * */
	createComplaintArea:function(index,complaintSubObj){
		complaintCaseBackForm.numberMap.put(index,index);
		var table = $('#complaintArea');
		
		var tr1 = complaintCaseBackForm.createTR(table,complaintCaseBackForm.complaintTr1+index);
		complaintCaseBackForm.createTitle('style="width:13%"','<span class="txt-impt">*</span>&nbsp;被投诉对象类型：',tr1);
		var detail = complaintCaseBackForm.createDetail('','',tr1);
		var complaintObjTitle = complaintCaseBackForm.createTitle('','',tr1);
		var complaintObjDetail = complaintCaseBackForm.createDetail('','',tr1);
		complaintObjDetail.attr('colspan','2');
		complaintCaseBackForm.createComplaintObjType('complaintObjType'+index,detail,complaintObjTitle,complaintObjDetail,index,complaintSubObj);
		
		var tr2 = complaintCaseBackForm.createTR(table,complaintCaseBackForm.complaintTr2+index);
		complaintCaseBackForm.createTitle('','<span class="txt-impt">*</span>&nbsp;投诉要点：',tr2);
		detail = complaintCaseBackForm.createDetail('style="border-right:none;"','',tr2);
		complaintCaseBackForm.createComplaintGist('complaintGist'+index,detail,index);
		var multiSelectLeft = '<select id="multiSelectLeft'+index+'" style="width:220px;height:100px;" multiple></select>';
		complaintCaseBackForm.createDetail('style="border-right:none;border-left:none;"',multiSelectLeft,tr2);
		detail = complaintCaseBackForm.createDetail('style="text-align:center;border-right:none;border-left:none;"','',tr2);
		//******************选择按钮******************//
		btn = $('<a href="javascript:void(0);">选择></a>').appendTo(detail);
		btn.linkbutton({
			plain:true
		});
		btn.bind('click', function(){
			var oldSelected = $('#multiSelectRightSelected'+index);
			var newSelected = $('#multiSelectLeft'+index);
			if(newSelected.val()==null){
				alert("请至少选择一项！");
				return;
			}
			var oldSelectedValues = oldSelected.val().split(",");
			$.each(newSelected.val(),function(i,value){
				if($.inArray(value, oldSelectedValues)== -1){
					oldSelectedValues.push(value);
				}
			});
			oldSelected.val(oldSelectedValues.join(","));
			complaintCaseBackForm.setMultiSelectRight(index);
		});
		//******************删除按钮******************//
		$('<br>').appendTo(detail);
		btn = $('<a href="javascript:void(0);">删除<</a>').appendTo(detail);
		btn.linkbutton({
			plain:true
		});
		btn.bind('click', function(){
			var oldSelected = $('#multiSelectRightSelected'+index);
			var newSelected = $('#multiSelectRight'+index);
			if(newSelected.val()==null){
				alert("请至少选择一项！");
				return;
			}
			var oldSelectedValues = oldSelected.val().split(",");
			$.each(newSelected.val(),function(i,value){
				oldSelectedValues = $.grep(oldSelectedValues,function(n,j){
					return n != value;
				});
			});
			oldSelected.val(oldSelectedValues.join(","));
			complaintCaseBackForm.setMultiSelectRight(index);
		});
		//******************全选择按钮******************//
		$('<br>').appendTo(detail);
		btn = $('<a href="javascript:void(0);">全选择>></a>').appendTo(detail);
		btn.linkbutton({
			plain:true
		});
		btn.bind('click', function(){
			var oldSelected = $('#multiSelectRightSelected'+index);
			
			var oldSelectedValues = oldSelected.val().split(",");
			var selectLeft = document.getElementById("multiSelectLeft"+index);
			$.each(selectLeft.options,function(i,option){
				if($.inArray(option.value, oldSelectedValues)== -1){
					oldSelectedValues.push(option.value);
				}
			});
			
			oldSelected.val(oldSelectedValues.join(","));
			complaintCaseBackForm.setMultiSelectRight(index);
		});
		//******************全删除按钮******************//
		$('<br>').appendTo(detail);
		btn = $('<a href="javascript:void(0);">全删除<<</a>').appendTo(detail);
		btn.linkbutton({
			plain:true
		});
		btn.bind('click', function(){
			var oldSelected = $('#multiSelectRightSelected'+index);
			var newSelected = $('#multiSelectRight'+index);
			
			oldSelected.val('');
			newSelected.html('');
		});
		detail = complaintCaseBackForm.createDetail('style="border-left:none;"','',tr2);
		var multiSelectRight = $('<select id="multiSelectRight'+index+'" style="width:220px;height:100px;" multiple></select>').appendTo(detail);
		multiSelectRight.validatebox({
			required:true
		});
		var multiSelectRightSelected = $('<input id="multiSelectRightSelected'+index+'" type="hidden"/>').appendTo(detail);
		if(complaintSubObj!=null
			&&complaintSubObj!=""
			&&complaintSubObj.complaintGist!=null
			&&complaintSubObj.complaintGist!=""){
			$('#multiSelectRightSelected'+index).val(complaintSubObj.complaintGist);
			complaintCaseBackForm.setMultiSelectRight(index);
		}
		
		var tr3 = complaintCaseBackForm.createTR(table,complaintCaseBackForm.complaintTr3+index);
		complaintCaseBackForm.createTitle('','调度方式：',tr3);
		detail = complaintCaseBackForm.createDetail('','',tr3);
		complaintCaseBackForm.createSchedulingType('schedulingType'+index,detail,complaintSubObj);
		complaintCaseBackForm.createTitle('','是否加急处理：',tr3);
		detail = complaintCaseBackForm.createDetail('','',tr3);
		detail.attr('colspan','2');
		complaintCaseBackForm.createIsPressDo('isPressDo'+index,detail,complaintSubObj);
//		btn = $('<a style="float:right;" href="javascript:void(0);">删除投诉对象</a>').appendTo(detail);
//		btn.linkbutton({
//			iconCls:'icon-remove',
//			plain:true
//		});
//		btn.bind('click', function(){
//			if(complaintCaseBackForm.numberMap.size() == 1){
//				alert("至少要有一个被投诉对象！");
//				return;
//			} 
//			$('#'+complaintCaseBackForm.complaintTr1+index).remove();
//			$('#'+complaintCaseBackForm.complaintTr2+index).remove();
//			$('#'+complaintCaseBackForm.complaintTr3+index).remove();
//			complaintCaseBackForm.numberMap.remove(index);
//		});
		complaintCaseBackForm.number = complaintCaseBackForm.number + 1;
	},
	/**
	 * 创建TR
	 * */
	createTR:function(appendToCol,id){
		var tr =  $('<tr id="'+id+'"></tr>').appendTo(appendToCol);
		return tr;
	},
	/**
	 * 创建标题
	 * */
	createTitle:function(style,detailHtml,appendToCol){
		var td =  $('<td class="title" '+style+'>'+detailHtml+'</td>').appendTo(appendToCol);
		return td;
	},
	/**
	 * 创建明细
	 * */
	createDetail:function(style,detailHtml,appendToCol){
		var td = $('<td '+style+'>'+detailHtml+'</td>').appendTo(appendToCol);
		return td;
	},
	/**
	 * 创建投诉对象类型
	 * */
	createComplaintObjType:function(id,appendToCol,title,detail,index,complaintSubObj){
		var select = $('<select id="'+id+'"></select>').appendTo(appendToCol);
		var defaultOption = $('<option value="">---请选择---</option>').appendTo(select);
		if(complaintCaseBackForm.complaintObjTypeList){
			$.each(complaintCaseBackForm.complaintObjTypeList,function(i,param){
				if(complaintSubObj!=null
					&&complaintSubObj!=""
					&&complaintSubObj.complaintObjType!=null
					&&complaintSubObj.complaintObjType!=""
					&&complaintSubObj.complaintObjType==param.value){
					$('<option value="'+param.value+'" selected>'+param.text+'</option>').appendTo(select);
				} else {
					$('<option value="'+param.value+'">'+param.text+'</option>').appendTo(select);
				}
				
			});
		}
		select.bind('change',function(){
			complaintCaseBackForm.complaintObjTypeOnChange(select.val(),title,detail,index,complaintSubObj);
		});
		select.validatebox({
			required:true
		});
		select.change();
		
		return select;
	},
	/**
	 * 投诉对象类型onChange方法
	 * */
	complaintObjTypeOnChange:function(value,title,detail,index,complaintSubObj){
		title.html('');
		detail.html('');
		if(value == "公司部门"){
			title.html('<span class="txt-impt">*</span>&nbsp;机构名称：');
			var input = $('<input id="complaintObj'+index+'" class="inp_text" readonly="readonly"/>').appendTo(detail);
			$('<input id="complaintObjCode'+index+'" type="hidden"/>').appendTo(detail);
			$('<input id="complaintObjId'+index+'" type="hidden"/>').appendTo(detail);
			input.validatebox({
				required:true
			});
			var btn = $('<a href="javascript:void(0);">选择</a>').appendTo(detail);
			btn.linkbutton({
				iconCls:'icon-search',
				plain:true
			});
			btn.bind('click',function(){
				complaintCaseBackForm.orgDialogIndex = index;
				complaintCaseBackForm.initOrgGrid();
				$('#orgDialog').dialog('open').dialog('setTitle','机构选择');
			});
			if(complaintSubObj!=null
				&&complaintSubObj!=""
				&&complaintSubObj.orgId!=null
				&&complaintSubObj.orgId!=""){
				$('#complaintObj'+index).val(complaintSubObj.complaintObjName);
				$('#complaintObjCode'+index).val(complaintSubObj.orgId);
				$('#complaintObjId'+index).val(complaintSubObj.companyId);
			}

		}
		if(value == "公司员工"){
			title.html('<span class="txt-impt">*</span>&nbsp;被投诉人姓名：');
			var input = $('<input id="complaintObj'+index+'" class="inp_text" readonly="readonly"/>').appendTo(detail);
			$('<input id="complaintObjCode'+index+'" type="hidden"/>').appendTo(detail);
			$('<input id="complaintObjId'+index+'" type="hidden"/>').appendTo(detail);
			input.validatebox({
				required:true
			});
			var btn = $('<a href="javascript:void(0);">选择</a>').appendTo(detail);
			btn.linkbutton({
				iconCls:'icon-search',
				plain:true
			});
			btn.bind('click',function(){
				complaintCaseBackForm.operDialogIndex = index;
				complaintCaseBackForm.operReset();
				complaintCaseBackForm.initOperGrid();
				$('#operDialog').dialog('open').dialog('setTitle','员工选择');
			});
			if(complaintSubObj!=null
				&&complaintSubObj!=""
				&&complaintSubObj.operId!=null
				&&complaintSubObj.operId!=""){
				$('#complaintObj'+index).val(complaintSubObj.complaintObjName);
				$('#complaintObjCode'+index).val(complaintSubObj.operId);
				$('#complaintObjId'+index).val(complaintSubObj.companyId);
			}
		}
		if(value == "服务供应商"){
			title.html('<span class="txt-impt">*</span>&nbsp;服务供应商：');
			var input = $('<input id="complaintObj'+index+'" class="inp_text" readonly="readonly"/>').appendTo(detail);
			$('<input id="complaintObjCode'+index+'" type="hidden"/>').appendTo(detail);
			$('<input id="complaintObjId'+index+'" type="hidden"/>').appendTo(detail);
			input.validatebox({
				required:true
			});
			var btn = $('<a href="javascript:void(0);">选择</a>').appendTo(detail);
			btn.linkbutton({
				iconCls:'icon-search',
				plain:true
			});
			btn.bind('click',function(){
				complaintCaseBackForm.supplierDialogIndex = index;
				complaintCaseBackForm.supplierReset();
				complaintCaseBackForm.initSupplierGrid();
				$('#supplierDialog').dialog('open').dialog('setTitle','供应商选择');
			});
			if(complaintSubObj!=null
				&&complaintSubObj!=""
				&&complaintSubObj.company!=null
				&&complaintSubObj.company!=""){
				$('#complaintObj'+index).val(complaintSubObj.complaintObjName);
				$('#complaintObjCode'+index).val(complaintSubObj.company);
				$('#complaintObjId'+index).val(complaintSubObj.companyId);
			}
		}
	},
	/**
	 * 创建投诉要点
	 * */
	createComplaintGist:function(id,appendToCol,index){
		var select = $('<select id="'+id+'"></select>').appendTo(appendToCol);
		var defaultOption = $('<option value="">---请选择---</option>').appendTo(select);
		if(complaintCaseBackForm.complaintGistList){
			$.each(complaintCaseBackForm.complaintGistList,function(i,param){
				$('<option value="'+param.value+'">'+param.text+'</option>').appendTo(select);
			});
		}
		select.bind('change',function(){
			var multiSelectLeft = $('#multiSelectLeft'+index);
			multiSelectLeft.html('');
			$.each(complaintCaseBackForm.complaintGistList,function(i,param){
				if(param.value == select.val()){
					$.each(param.childParams,function(j,childParam){
						$('<option value="'+param.value+'/'+childParam.value+'">'+childParam.text+'</option>').appendTo(multiSelectLeft);
					});
					return false;
				}
			});
		});
		
		return select;
	},
	/**
	 * 投诉要点多选（右侧）设置
	 * */
	setMultiSelectRight:function(index){
		var selected = $('#multiSelectRightSelected'+index);
		var multiSelectRight = $('#multiSelectRight' + index);
		
		multiSelectRight.html('');
		var selectedValues = selected.val().split(",");
		$.each(selectedValues,function(i,value){
			if(value!=""){
				$('<option value="'+value+'">'+value+'</option>').appendTo(multiSelectRight);	
			}
		});
	},
	/**
	 * 创建调度类型
	 * */
	createSchedulingType:function(id,appendToCol,complaintSubObj){
		var select = $('<select id="'+id+'"></select>').appendTo(appendToCol);
		var defaultOption = $('<option value="">---请选择---</option>').appendTo(select);
		if(complaintCaseBackForm.schedulingTypeList){
			$.each(complaintCaseBackForm.schedulingTypeList,function(i,schedulingType){
				if(complaintSubObj!=null
					&&complaintSubObj!=""
					&&complaintSubObj.schedulingType!=null
					&&complaintSubObj.schedulingType!=""
					&&complaintSubObj.schedulingType==schedulingType[0]){
					$('<option value="'+schedulingType[0]+'" selected>'+schedulingType[1]+'</option>').appendTo(select);
				} else {
					$('<option value="'+schedulingType[0]+'">'+schedulingType[1]+'</option>').appendTo(select);
				}
			});
		}
		select.validatebox({
			required:true
		});
		
		return select;
	},
	/**
	 * 创建是否加急
	 * */
	createIsPressDo:function(id,appendToCol,complaintSubObj){
		var select = $('<select style="float:left;" id="'+id+'"></select>').appendTo(appendToCol);
		if(complaintSubObj!=null
			&&complaintSubObj!=""
			&&complaintSubObj.isPressDo!=null
			&&complaintSubObj.isPressDo!=""
			&&complaintSubObj.isPressDo==true){
			$('<option value="1" selected="selected">是</option>').appendTo(select);
			$('<option value="0">否</option>').appendTo(select);
		} else {
			$('<option value="1">是</option>').appendTo(select);
			$('<option value="0" selected="selected">否</option>').appendTo(select);
		}
		select.validatebox({
			required:true
		});
		
		return select;
	},
	/**
	 * 案件暂存
	 * */
	caseSave:function(handleFlag){
		// 页面校验
		if(handleFlag=="SUBMIT" || handleFlag=="CLOSE"){
			if(!complaintCaseBackForm.isValidate()){
				return;
			}
		}
		
		$("#submitForm input[name='policyOrg']").val($('#policyOrg').val());//投保机构
		$("#submitForm input[name='policyType']").val($('#policyType').val());//投诉险种
		$("#submitForm input[name='policyNo']").val($('#policyNo').val());//保单号
		$("#submitForm input[name='policyReportNo']").val($('#policyReportNo').val());//报案号码
		$("#submitForm input[name='policyObjName']").val($('#policyObjName').val());//被保险人姓名
		$("#submitForm input[name='policyCertType']").val($('#policyCertType').val());//证件类型
		$("#submitForm input[name='policyCertNo']").val($('#policyCertNo').val());//证件号码
		$("#submitForm input[name='policyLicId']").val($('#policyLicId').val().toUpperCase());//车牌号
		$("#submitForm input[name='isPhonePolicy']").val($('#isPhonePolicy').val());//是否电销保单
		$("#submitForm input[name='policyAddress']").val($('#policyAddress').val());//家庭住址
		$("#submitForm input[name='cplType']").val($('#cplType').val());//投诉人类型
		$("#submitForm input[name='cplName']").val($('#cplName').val());//投诉人
		$("#submitForm input[name='genderType']").val($('#genderType').val());//性别
		$("#submitForm input[name='cplMode']").val($('#cplMode').val());//投诉方式
		$("#submitForm input[name='cplPhone1']").val($('#cplPhone1').val());//联系电话1
		$("#submitForm input[name='cplPhone2']").val($('#cplPhone2').val());//联系电话2
		$("#submitForm input[name='cplPhone3']").val($('#cplPhone3').val());//联系电话3
		$("#submitForm input[name='cplMobile']").val($('#cplMobile').val());//手机
		$("#submitForm input[name='cplAddress']").val($('#cplAddress').val());//地址
		$("#submitForm input[name='cplPostCode']").val($('#cplPostCode').val());//邮编
		$("#submitForm input[name='cplMail']").val($('#cplMail').val());//电子邮件
		$("#submitForm input[name='cplMemo']").val($('#cplMemo').val());//投诉内容概述
		$("#submitForm input[name='cplOtherMemo']").val($('#cplOtherMemo').val());//投诉人其它要求
		//关联案件
		var relationCaseCode = "";
		$.each(complaintCaseBackForm.relationComplaintMap.keys(),function(i,key){
			var caseCodeList = complaintCaseBackForm.relationComplaintMap.get(key);
			$.each(caseCodeList,function(i,caseCode){
				if(relationCaseCode==""){
					relationCaseCode = key + "/" + caseCode;
				} else {
					relationCaseCode = relationCaseCode + "," + key + "/" + caseCode;
				}
			});
		});
		$("#submitForm input[name='relationComplaint']").val(relationCaseCode);
		
		$.each(complaintCaseBackForm.numberMap.keys(),function(i,key){
			if(i==0){
				var inputName = "complaintSubObjects["+i+"].id";
				var inputValue = $("#submitForm input[name='id']").val();
				$("<input/>").attr("type","hidden").attr("name",inputName).attr("value",inputValue).appendTo("#submitForm");
			}
			//被投诉人类型
			var complaintObjType = $('#complaintObjType'+key).val();
			var inputName = "complaintSubObjects["+i+"].complaintObjType";
			var inputValue = complaintObjType;
			$("<input/>").attr("type","hidden").attr("name",inputName).attr("value",inputValue).appendTo("#submitForm");
			
			//被投诉人
			if(complaintObjType=="公司部门"){
				inputName = "complaintSubObjects["+i+"].orgId";
				inputValue = $('#complaintObjCode'+key).val();
				$("<input/>").attr("type","hidden").attr("name",inputName).attr("value",inputValue).appendTo("#submitForm");
			} else if(complaintObjType=="公司员工"){
				inputName = "complaintSubObjects["+i+"].operId";
				inputValue = $('#complaintObjCode'+key).val();
				$("<input/>").attr("type","hidden").attr("name",inputName).attr("value",inputValue).appendTo("#submitForm");
			} else if(complaintObjType=="服务供应商"){
				inputName = "complaintSubObjects["+i+"].company";
				inputValue = $('#complaintObjCode'+key).val();
				$("<input/>").attr("type","hidden").attr("name",inputName).attr("value",inputValue).appendTo("#submitForm");
			}

			inputName = "complaintSubObjects["+i+"].companyId";
			inputValue = $('#complaintObjId'+key).val();
			$("<input/>").attr("type","hidden").attr("name",inputName).attr("value",inputValue).appendTo("#submitForm");
			
			//投诉要点
			var complaintGist = "";
			var selectLeft = document.getElementById("multiSelectRight"+key);
			$.each(selectLeft.options,function(i,option){
				if(i==0){
					complaintGist = option.value;
				} else {
					complaintGist = complaintGist + "," + option.value;
				}
			}); 
			inputName = "complaintSubObjects["+i+"].complaintGist";
			inputValue = complaintGist;
			$("<input/>").attr("type","hidden").attr("name",inputName).attr("value",inputValue).appendTo("#submitForm");
			
			//调度类型
			inputName = "complaintSubObjects["+i+"].schedulingType";
			inputValue = $('#schedulingType'+key).val();
			$("<input/>").attr("type","hidden").attr("name",inputName).attr("value",inputValue).appendTo("#submitForm");
			
			//是否加急
			inputName = "complaintSubObjects["+i+"].isPressDo";
			inputValue = $('#isPressDo'+key).val();
			$("<input/>").attr("type","hidden").attr("name",inputName).attr("value",inputValue).appendTo("#submitForm");
		});

		var submitForm = $('#submitForm');
		var url = submitForm.attr("action");
		if(handleFlag=="TEMP"){
			url = url + "/workflow/complaintCase/tempUpdateComplaintCase";
		} else if(handleFlag=="SUBMIT"){
			url = url + "/workflow/complaintCase/backSubmitComplaint";
		} else if(handleFlag=="CLOSE"){
			url = url + "/workflow/complaintCase/backApplyCloseCase";
		}
		$.post(url,submitForm.serialize(),function(data){
			alert(data.successMsg);
			// 关闭滚动条
			base.closepageloading();
			if(data.successFlg == "success"){
				if(handleFlag=="TEMP"){
					base.reloadTab('complaintCaseListForm');
				} else {
					var url = $('#complaintCaseDetailForm').attr('action') + "?";
					$.each(data.comlIdList,function(i,comlId){
						url = url + "&complaintSubObjects["+i+"].id="+comlId;
					});
					base.addTab('complaintCaseDetailForm', '案件详细', url, true);
				}
			}
			var tabId=$('#tabId').val();
			base.reloadTab('myHandlingForm');//刷新“个人提醒”的列表
			base.reloadTab('DepRemindListForm');//刷新“部门提醒”的列表
			base.closeTab(tabId);

			/*base.reloadTab('commonBackListForm');//刷新列表
			base.closeTab('commonBackDealForm');*/
		});
	},
	/**
	 * 重置按钮事件
	 * */
	reset:function(){
		$("#checkForm")[0].reset();
//		$('#relationComplaint').html('');
		$('#complaintArea').html(complaintCaseBackForm.tableHtml);
		complaintCaseBackForm.init(complaintCaseBackForm.caseCodeStr);
	},
	/**
	 * 页面校验
	 * */
	isValidate:function(){
		// 投保机构
		if(!$('#policyOrg').validatebox('isValid')){
			alert("请选择投保机构！");
			return false;
		}
		// 投诉险种
		if(!$('#policyType').validatebox('isValid')){
			alert("请选择投诉险种！");
			return false;
		}
		// 车牌号
		if(!$('#policyLicId').validatebox('isValid')){
			alert("请填写正确的车牌号！");
			return false;
		}
		// 投诉人
		if(!$('#cplName').validatebox('isValid')){
			alert("请填写投诉人！");
			return false;
		}
		// 投诉人类型
		if(!$('#cplType').validatebox('isValid')){
			alert("请选择投诉人类型！");
			return false;
		}
		// 投诉人性别
		if(!$('#genderType').validatebox('isValid')){
			alert("请选择投诉人性别！");
			return false;
		}
		// 投诉方式
		if(!$('#cplMode').validatebox('isValid')){
			alert("请选择投诉方式！");
			return false;
		}
		// 联系电话
		if($('#cplPhone1').val()!="" || $('#cplPhone2').val()!="" || $('#cplPhone3').val()!=""){
			if($('#cplPhone1').val()==""){
				alert("请填写正确的联系电话！");
				return false;
			}
			if($('#cplPhone2').val()==""){
				alert("请填写正确的联系电话！");
				return false;
			}
			if($('#cplPhone3').val()==""){
				alert("请填写正确的联系电话！");
				return false;
			}
		}
		// 手机
		if($('#cplMobile').val()!=""){
			if(!$('#cplMobile').validatebox('isValid')){
				alert("请填写正确的手机！");
				return false;
			}
		}
		// 手机、联系电话至少输入一项
		if($('#cplPhone1').val()=="" && $('#cplPhone2').val()=="" && $('#cplPhone3').val()=="" && $('#cplMobile').val()==""){
			alert("联系电话、手机至少输入一项！");
			return false;
		}
		// 投诉对象
		var checkFlag = true;
		$.each(complaintCaseBackForm.numberMap.keys(),function(i,key){
			//被投诉人类型
			if(!$('#complaintObjType'+key).validatebox('isValid')){
				alert("请选择被投诉人类型！");
				checkFlag = false;
				return false;
			}
			
			//被投诉人
			if(!$('#complaintObj'+key).validatebox('isValid')){
				alert("请选择被投诉人！");
				checkFlag = false;
				return false;
			}

			//投诉要点
			var selectLeft = document.getElementById("multiSelectRight"+key);
			if(selectLeft.options.length <= 0){
				alert("请选择投诉要点！");
				checkFlag = false;
				return false;
			}
			
			//调度类型
			if(!$('#schedulingType'+key).validatebox('isValid')){
				alert("请选择调度类型！");
				checkFlag = false;
				return false;
			}
		});
		if(!checkFlag){
			return false;
		}
		// 投诉内容概述
		if(!$('#cplMemo').validatebox('isValid')){
			alert("请填写投诉内容概述！");
			return false;
		}
		return true;
	},
	//****************************************************
	// 机构查询页面JS                                 开始
	//****************************************************
	/**
	 * 机构确定按钮事件
	 * */
	orgDialogBtnClick:function(){
		var node = $('#orgGrid').treegrid("getSelected");
		if(!node){
			alert("请选择机构！");
			return;
		}
		$('#complaintObjCode'+complaintCaseBackForm.orgDialogIndex).val(node.id);
		$('#complaintObj'+complaintCaseBackForm.orgDialogIndex).val(node.obj.orgName);
		$('#complaintObjId'+complaintCaseBackForm.orgDialogIndex).val(node.id);
		$('#orgDialog').dialog('close');
	},
	/**
	 * 机构列表-初始化
	 * */
	initOrgGrid:function(){
		var queryForm = $('#orgListForm');
		$('#orgGrid').treegrid({
			url:queryForm.attr("action"),
			idField:'id',
			treeField:'orgName',
			rownumbers:true,
			fitColumns:true,//列表自适应屏幕的宽度
			singleSelect:true,
			toolbar:'#orgGridToolbar',
			columns:[[
				{title:'机构名称',
				field:'orgName',
				width:250,
				formatter:function(value,rowData,rowIndex){
					return rowData.obj.orgName;
				}},
				{title:'机构代码',
				field:'orgCode',
				width:200,
				formatter:function(value,rowData,rowIndex){
					return rowData.obj.orgCode;
				}},
				{title:'机构说明',
				field:'orgMemo',
				width:600,
				formatter:function(value,rowData,rowIndex){
					return rowData.obj.orgMemo;
				}},
				{title:'机构地址',
				field:'orgAddress',
				width:350,
				formatter:function(value,rowData,rowIndex){
					return rowData.obj.orgAddress;
				}}
			]],
			onLoadSuccess:function(data){
				base.ajaxSuccess(data);
			},
			onLoadError:function(XMLHttpRequest, textStatus, event){
				base.ajaxError(XMLHttpRequest, textStatus, event);
			}
		});	
	},
	//****************************************************
	// 机构查询页面JS                                 结束
	//****************************************************
	
	//****************************************************
	// 员工查询页面JS                                 开始
	//****************************************************
	/**
	 * 员工确定按钮事件
	 * */
	operDialogBtnClick:function(){
		var node = $('#operGrid').datagrid("getSelected");
		if(!node){
			alert("请选择员工！");
			return;
		}
		$('#complaintObjCode'+complaintCaseBackForm.operDialogIndex).val(node.id);
		$('#complaintObj'+complaintCaseBackForm.operDialogIndex).val(node.operName);
		$('#complaintObjId'+complaintCaseBackForm.operDialogIndex).val(node.id);
		$('#operDialog').dialog('close');
	},
	/**
	 * 员工列表-初始化
	 * */
	initOperGrid:function(){
		var queryForm = $('#operListForm');
		var param = base.decodeURI(queryForm.serialize());
		$('#operGrid').datagrid({
			url:queryForm.attr('action'),
			queryParams:base.strToJson(param),
			rownumbers:true,
			singleSelect:true,
			autoRowHeight:false,
			pagination:true,
			pageSize:base.pageSize,
			fitColumns:true,
			pageNumber:1,
			toolbar:'#operGridToolbar',
			columns:[[
				{title:'登录名',
				field:'operAccountNo',
				width:250,
				formatter:function(value,rowData,rowIndex){
					return rowData.operAccountNo;
				}},
				{title:'姓名',
				field:'operName',
				width:250,
				formatter:function(value,rowData,rowIndex){
					return rowData.operName;
				}},
				{title:'姓别',
				field:'operGender',
				width:100,
				formatter:function(value,rowData,rowIndex){
					return rowData.operGenderText;
				}},	
				{title:'用户类型',
				field:'operType',
				width:250,
				formatter:function(value,rowData,rowIndex){
					return rowData.operTypeText;
				}},	
				{title:'用户职位',
				field:'positionType',
				width:250,
				formatter:function(value,rowData,rowIndex){
					return rowData.positionTypeText;
				}},	
				{title:'联系电话',
				field:'operPhone',
				width:350,
				formatter:function(value,rowData,rowIndex){
					return rowData.operPhone;
				}},			
				{title:'邮箱',
				field:'operMail',
				width:600,
				formatter:function(value,rowData,rowIndex){
					return rowData.operMail;
				}},
				{title:'所属机构',
				field:'orgName',
				width:500,
				formatter:function(value,rowData,rowIndex){
					var orgStr = '';
					$.each(rowData.organizations,function(i,org){
						if(i==0){
							orgStr = org.orgName;
						} else {
							orgStr = orgStr + "," + org.orgName;
						}
						
					});
					return orgStr;
				}}
			]],
			onLoadSuccess:function(data){
				base.ajaxSuccess(data);
			},
			onLoadError:function(XMLHttpRequest, textStatus, event){
				base.ajaxError(XMLHttpRequest, textStatus, event);
			}
		});
	},
	/**
	 * 员工列表-查询
	 * */
	operQuery:function(){
		complaintCaseBackForm.initOperGrid();
	},
	/**
	 * 员工列表-重置
	 * */
	operReset:function(){
		$('#orgId').val('');
		$("#operListForm")[0].reset();
	},
	/**
	 * 员工列表-机构列表初始化
	 * */
	initSubOrgGrid:function(){
		var queryForm = $('#orgListForm');
		$('#orgSubGrid').treegrid({
			url:queryForm.attr("action"),
			idField:'id',
			treeField:'orgName',
			rownumbers:true,
			fitColumns:true,//列表自适应屏幕的宽度
			singleSelect:true,
			toolbar:'#orgSubGridToolbar',
			columns:[[
				{title:'机构名称',
				field:'orgName',
				width:250,
				formatter:function(value,rowData,rowIndex){
					return rowData.obj.orgName;
				}},
				{title:'机构代码',
				field:'orgCode',
				width:200,
				formatter:function(value,rowData,rowIndex){
					return rowData.obj.orgCode;
				}},
				{title:'机构说明',
				field:'orgMemo',
				width:600,
				formatter:function(value,rowData,rowIndex){
					return rowData.obj.orgMemo;
				}},
				{title:'机构地址',
				field:'orgAddress',
				width:350,
				formatter:function(value,rowData,rowIndex){
					return rowData.obj.orgAddress;
				}}
			]],
			onLoadSuccess:function(data){
				base.ajaxSuccess(data);
			},
			onLoadError:function(XMLHttpRequest, textStatus, event){
				base.ajaxError(XMLHttpRequest, textStatus, event);
			}
		});	
	},
	/**
	 * 员工列表-机构查询
	 * */
	seachOrgInfo:function(){
		complaintCaseBackForm.initSubOrgGrid();
		$('#orgSubDialog').dialog('open').dialog('setTitle','机构选择');
	},
	/**
	 * 员工列表-机构查询-确定按钮
	 * */
	selectedOrgInfo:function(){
		var node = $('#orgSubGrid').treegrid("getSelected");
		if(!node){
			alert("请选择机构！");
			return;
		}
		$('#orgId').val(node.id);
		$('#orgName').val(node.obj.orgName);
		$('#orgSubDialog').dialog('close');
	},
	//****************************************************
	// 员工查询页面JS                                 结束
	//****************************************************
	
	//****************************************************
	// 供应商查询页面JS                               开始
	//****************************************************
	/**
	 * 供应商确定按钮事件
	 * */
	supplierDialogBtnClick:function(){
		var node = $('#supplierGrid').datagrid("getSelected");
		if(!node){
			alert("请选择供应商！");
			return;
		}
		$('#complaintObjCode'+complaintCaseBackForm.supplierDialogIndex).val(node.supName);
		$('#complaintObj'+complaintCaseBackForm.supplierDialogIndex).val(node.supName);
		$('#complaintObjId'+complaintCaseBackForm.supplierDialogIndex).val(node.id);
		$('#supplierDialog').dialog('close');
	},
	/**
	 * 供应商列表初始化
	 * */
	initSupplierGrid:function(){
		var queryForm = $('#querySupplierListForm');
		var param = base.decodeURI(queryForm.serialize());
		$('#supplierGrid').datagrid({
			url:queryForm.attr('action'),
			queryParams:base.strToJson(param),
			rownumbers:true,
			singleSelect:true,
			autoRowHeight:false,
			pagination:true,
			pageSize:base.pageSize,
			fitColumns:true,
			toolbar:'#supplierGridToolbar',
			columns:[[
				{title:'编号',
				field:'supCode',
				width:250,
				formatter:function(value,rowData,rowIndex){
					return rowData.supCode;
				}},
				{title:'供应商名称',
				field:'supName',
				width:250,
				formatter:function(value,rowData,rowIndex){
					return rowData.supName;
				}},
				{title:'所属机构',
				field:'orgText',
				width:250,
				formatter:function(value,rowData,rowIndex){
					return rowData.orgText;
				}},			
				{title:'分类',
				field:'supClassify',
				width:200,
				formatter:function(value,rowData,rowIndex){
					return rowData.supClassify;
				}},
				{title:'地址',
				field:'supAddress',
				width:400,
				formatter:function(value,rowData,rowIndex){
					return rowData.supAddress;
				}},	
				{title:'联系电话',
				field:'supPhone',
				width:250,
				formatter:function(value,rowData,rowIndex){
					return rowData.supPhone;
				}},	
				{title:'邮编',
				field:'supPostCode',
				width:250,
				formatter:function(value,rowData,rowIndex){
					return rowData.supPostCode;
				}}
			]],
			onLoadSuccess:function(data){
				base.ajaxSuccess(data);
			},
			onLoadError:function(XMLHttpRequest, textStatus, event){
				base.ajaxError(XMLHttpRequest, textStatus, event);
			}
		});
	},
	/**
	 * 供应商-查询
	 * */
	supplierQuery:function(){
		complaintCaseBackForm.initSupplierGrid();
	},
	/**
	 * 供应商-重置
	 * */
	supplierReset:function(){
		$("#querySupplierListForm")[0].reset();
		$("#supplierOrgId").val('');
	},
	/**
	 * 供应商-机构列表初始化
	 * */
	initSupplierSubOrgGrid:function(){
		var queryForm = $('#orgListForm');
		$('#supplierSubGrid').treegrid({
			url:queryForm.attr("action"),
			idField:'id',
			treeField:'orgName',
			rownumbers:true,
			fitColumns:true,//列表自适应屏幕的宽度
			singleSelect:true,
			toolbar:'#supplierSubGridToolbar',
			columns:[[
				{title:'机构名称',
				field:'orgName',
				width:250,
				formatter:function(value,rowData,rowIndex){
					return rowData.obj.orgName;
				}},
				{title:'机构代码',
				field:'orgCode',
				width:200,
				formatter:function(value,rowData,rowIndex){
					return rowData.obj.orgCode;
				}},
				{title:'机构说明',
				field:'orgMemo',
				width:600,
				formatter:function(value,rowData,rowIndex){
					return rowData.obj.orgMemo;
				}},
				{title:'机构地址',
				field:'orgAddress',
				width:350,
				formatter:function(value,rowData,rowIndex){
					return rowData.obj.orgAddress;
				}}
			]],
			onLoadSuccess:function(data){
				base.ajaxSuccess(data);
			},
			onLoadError:function(XMLHttpRequest, textStatus, event){
				base.ajaxError(XMLHttpRequest, textStatus, event);
			}
		});	
	},
	/**
	 * 供应商-机构查询
	 * */
	seachSupplierOrgInfo:function(){
		complaintCaseBackForm.initSupplierSubOrgGrid();
		$('#supplierSubDialog').dialog('open').dialog('setTitle','机构选择');
	},
	/**
	 * 供应商-机构页面-确定
	 * */
	supplierOrgDialogBtnClick:function(){
		var node = $('#supplierSubGrid').treegrid("getSelected");
		if(!node){
			alert("请选择机构！");
			return;
		}
		$('#supplierOrgId').val(node.id);
		$('#orgText').val(node.obj.orgName);
		$('#supplierSubDialog').dialog('close');
	}
	//****************************************************
	// 供应商查询页面JS                               结束
	//****************************************************
}