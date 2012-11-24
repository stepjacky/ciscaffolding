<script type="text/javascript" src="/resources/scripts/${entityName}/edit.js"></script>
<link href="/resources/styles/${entityName}/style.css" media="screen" rel="stylesheet" type="text/css" />
<form id="${entityName}form" >
<input type="hidden" id="action" value="/${entityName}/saveupdate" />
<fieldset>
<legend>编辑/新增</legend>
<table class="table table-hover table-bordered">
<tbody>
<#list attrs?keys as attrName>
 <#assign meta = attrs[attrName] >
 <#if meta.key=="PRI">
     <input type="hidden" name="${meta.name}" id="${meta.name}"  value="${r"<?php echo isset($id)?$"}${meta.name} ${r":'';?>"} "/>   
     
    <#elseif meta.key=="MUL">
        <tr>
   <td>${meta.comment}</td>
   <td>       
       
        <?= form_dropdown("${meta.name}",$shopcategory)?>       
    
   </td>     
    <#else>
       <tr>
   <td>${meta.comment}</td>
   <td> 
      <input type="text" name="${meta.name}" id="${meta.name}"  value="${r"<?php echo isset($id)?$"}${meta.name} ${r":'';?>"} "/>   
   
   </td>
 </#if> 
 
 
    
   
 </tr>    
</#list>
</tbody>
<tfoot>
  <tr>
   <td>
      <button class="btn btn-success" id="saveBtn">保存</button>
      <button class="btn" type="reset">重置</button>      
   </td>
   <td></td>
  </tr>
</tfoot>     
</table>
</fieldset>
</form>