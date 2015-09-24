<script langage=javascript>
function go2page(page, pageSize, totalpages){
    if(isNaN(pageSize) | pageSize < 1 | pageSize > 100){
        alert("请输入有效的每页记录数！");
        ${paging_pageSizeParam}.focus(); return false;
    }else if(isNaN(page) | page < 1 | page > totalpages){
        alert("请输入有效页码！");
        ${paging_toPageNoParam}.focus(); return false;
    }else{
        return true;
    }
}

function ajaxPaging(urlPage){
	$.cover(true);
    var options = { 
        url:urlPage,
        type:'POST', 
        success: function(data){ 
        	$("#${pageList_DivId}").html(data);
            $.cover(false);
        } 
    }; 
    $('#${formId}').ajaxSubmit(options); 
    return false; //为了不刷新页面,返回false！
}

function goToPage(){
	$.cover(true);
	var page=$("#${paging_toPageNoParam}").val();
	if(isNaN(page)){
        alert("请输入有效页码！");
        $("#${paging_toPageNoParam}").focus(); 
        $.cover(false);
        return false;
    }
    var pageSize = $("#${paging_pageSizeParam}").val();
    if(isNaN(pageSize)){
    	alert("请输入有效记录数！");
        $("#${paging_pageSizeParam}").focus(); 
        $.cover(false);
        return false;
    }
    
    var pageUrl='${paging_formActionUrl}';
    pageUrl+="p_toPageNo="+page+"&${paging_pageSizeParam}="+pageSize;
    
    $.ajax({ 
		type: "post",
		url : pageUrl,
		success: function(transport){
			$("#${pageList_DivId}").html(transport);	
			$.cover(false);
		}
	});
}

function orderBy(orderByUrl){
	$.ajax({ 
			type: "post",
			url : orderByUrl,
			success: function(transport){
			$("#${pageList_DivId}").html(transport);	
		}
	});
}

</script>
<form id="inf-paging-form" action="${paging_formActionUrl}" method="GET" 
    onsubmit="return go2page(${paging_toPageNoParam}.value,${paging_pageSizeParam}.value,${paging_totalPages})">
<table class="pageBar">
<tr>
    <td>
        <span class="pageBarEndPage">
             <#if paging_firstUrl?exists><a onclick="ajaxPaging('${paging_firstUrl}')" href="#">首页</a><#else>首页</#if>
        </span>
        <#list paging_nearPageList as nearPageInfo>
            <#if nearPageInfo["nearPageUrl"]?exists>
                <span class="pageBarIndex">
                	<a onclick="ajaxPaging('${nearPageInfo['nearPageUrl']}')" href="#">${nearPageInfo["nearPageNo"]}</a>
                </span>
            <#else>
                <span class="pageBarCurrPage">${nearPageInfo["nearPageNo"]}</span>
            </#if>
        </#list>
        <span class="pageBarEndPage">
            <#if paging_firstUrl?exists><a onclick="ajaxPaging('${paging_tailUrl}')" href="#">尾页</a><#else>尾页</#if>&nbsp;&nbsp;
        </span>
        <span>总记录数:${paging_totalRecord}&nbsp;&nbsp;
              &nbsp;&nbsp;总页数:${paging_totalPages}&nbsp;&nbsp;</span>
         <span>每页记录<input type="text" id="${paging_pageSizeParam}" name="${paging_pageSizeParam}" 
           style="width:25px;" maxlength="3" size="2" value="${paging_size}"/>条
         </span>
        <span>转到<input type="text" id="${paging_toPageNoParam}" name="${paging_toPageNoParam}" 
         maxlength="5" size="2" value="${paging_toPageNo}" />
        <input type="button" onclick="goToPage()" value="Go" ></span>
     </td>
</tr>
</table>
</form>