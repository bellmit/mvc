var caseDetail={
	showOrHide :function(id){
		if($('#'+id+'_div').attr("display")=="block"){
			$('#'+id+'_div').attr("display","none");
			$('#'+id+'_div').hide();
			$('#'+id).attr("class", "tit_icon_arrow1");
		}else{
			$('#'+id+'_div').attr("display","block");
			$('#'+id+'_div').show();
			$('#'+id).attr("class", "tit_icon_arrow2");
		}
	}
}