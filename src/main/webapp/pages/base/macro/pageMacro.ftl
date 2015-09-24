<#macro pageTemplate pageFun="paging">
<div class="pagecxpage">
	<div class="pageinnerCss">
	<ul>
		<li>
		<img src="${rc.contextPath}/images/pagejt-5.gif" alt="" />&nbsp;共${page.totalSize?c}条
		
		<img src="${rc.contextPath}/images/pagejt-5.gif" />&nbsp;${page.currentPage?c}/${page.totalPage?c}
		<#if page.currentPage == 0 || page.currentPage == 1>
		<img src="${rc.contextPath}/images/pagejt-3.gif"  alt="" />
		<img src="${rc.contextPath}/images/pagejt-4.gif" alt="" />
		<#else>
        <a onclick="javascript:${pageFun}(0)"><img src="${rc.contextPath}/images/pagejt-3.gif"  alt="" class="ahand"/></a>
		<a onclick="javascript:${pageFun}(${((page.currentPage-2)*page.pageSize)?c})" class="ahand"><img src="${rc.contextPath}/images/pagejt-4.gif"  alt="" /></a>
        </#if>
		<#if page.currentPage == page.totalPage>
		<img src="${rc.contextPath}/images/pagejt-2.gif" alt="" />
		<img src="${rc.contextPath}/images/pagejt-1.gif" alt="" />
        <#else>  
        <a onclick="javascript:${pageFun}(${((page.currentPage)*page.pageSize)?c})" class="ahand"><img src="${rc.contextPath}/images/pagejt-2.gif"  alt="" /></a>
		<a onclick="javascript:${pageFun}(${((page.totalPage-1)*page.pageSize)?c})" class="ahand"><img src="${rc.contextPath}/images/pagejt-1.gif"  alt="" /></a>
        </#if>
        第<input type="text" id="pageNum" size="2" style="background:#E6E6E6; font-size: 8pt; height: 12.5pt; line-height: 10pt; padding: 0 0 -2.5 0;" onblur="javascript:base.checkPageNum('pageNum', (this.value-1)*${page.pageSize?c}, ${page.totalPage?c});"/>页<a href="javascript:base.pagingFun(${pageFun})" class="lj02">GO</a>
    </li></ul>
    </div>
</div>
</#macro> 
