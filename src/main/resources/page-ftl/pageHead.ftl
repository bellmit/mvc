<span class="orderCell">
<#if paging_orderDir?exists && paging_orderDir=="true" >
	<a class="orderUpOnly" href="javascript:location='${paging_fullUrl ? default("")}';">
<#elseif paging_orderDir?exists && paging_orderDir=="false" >
 	<a class="orderDownOnly" href="javascript:location='${paging_fullUrl ? default("")}';">
 <#else>
 	<a href="javascript:location='${paging_fullUrl ? default("")}';">
 </#if>
	<span>
		<span id="pageHeadTagAnchor"></span>
	</span>
	</a>
</span>
