<span class="orderCell">
<#if paging_orderDir?exists && paging_orderDir=="true" >
	<a class="orderUpOnly" href="javascript:orderBy('${paging_fullUrl ? default("")}');">
 <#elseif paging_orderDir?exists && paging_orderDir=="false" >
 	<a class="orderDownOnly" href="javascript:orderBy('${paging_fullUrl ? default("")}');">
 <#else>
 	<a href="javascript:orderBy('${paging_fullUrl ? default("")}');">
 </#if>
	<span>
		<span id="ajaxPageHeadTagAnchor"></span>
	</span>
	</a>
</span>