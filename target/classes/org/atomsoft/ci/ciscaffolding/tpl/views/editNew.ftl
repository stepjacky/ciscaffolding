
<link href="/resources/styles/${entityName}/style.css" media="screen" rel="stylesheet" type="text/css" />
<form id="${entityName}form" method="post" >

<fieldset>
<legend>编辑/新增-${entityLabel}</legend>
<table class="table table-hover table-bordered">
<tbody>
<#list attrs?keys as attrName>
 <#assign meta = attrs[attrName] >
  <#if meta.name=="id">
      ${meta.html}
     <#else>
     <tr>
   <td>${meta.comment}</td>
   <td>            
        ${meta.html}
   </td>     
   
<tr>
  </#if>
  
  
     
  
</#list>
</tbody>
<tfoot>
  <tr>
   <td>
           
   </td>
   <td>
   <button class="btn btn-success" id="saveBtn" type="button">保存</button>
      <button class="btn" type="reset">重置</button> 
   </td>
  </tr>
</tfoot>     
</table>
</fieldset>
</form>
<script type="text/javascript" src="/resources/bootstrap/js/locales/bootstrap-datepicker.zh-CN.js" charset="UTF-8"></script>
<script type="text/javascript" src="/resources/scripts/${entityName}/edit.js"></script>