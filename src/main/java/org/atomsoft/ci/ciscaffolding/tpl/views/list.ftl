<link href="/resources/styles/${entityName}/style.css" media="screen" rel="stylesheet" type="text/css" />
<h3>${entityLabel}列表</h3>
<table id="list" class="table table-striped table-bordered table-hover">
        <#assign attrlen=attrs?size >
        <thead>
        <tr>
          <th colspan="${attrlen}">
            <button type="button" class="btn btn-info" onclick="newOne();">
             <i class="icon-plus"></i>新增${entityLabel}
            </button>
          </th>
        </tr>
        <tr>
        <#list attrs?keys as attrName>
           <#assign meta = attrs[attrName]>
             <th>${meta.comment}</th>            
        </#list>
            <th>管理</th>
        </tr>
        </thead>
        <tbody>

        <#noparse>
            <?php foreach($datasource as $bean):?>
        </#noparse>  
           <tr>
         <#list attrs?keys as attrName>
           <#assign meta = attrs[attrName]>
            <td>
              ${r"<?=$bean['"}${meta.name}${r"']?>"}               
            </td>           
         </#list>   
           <td>
             <button class="btn btn-success" type="button" onclick="editOne('${r"<?php echo $bean['id'];?>"}');">
               <i class="icon-edit"></i>
             </button>
             <button class="btn btn-danger" type="button" onclick="removeOne('${r"<?php echo $bean['id'];?>"}');">
               <i class="icon-remove"></i>
             </button>
           </td>
           </tr>
        <#noparse>
        <?php endforeach; ?>
        </#noparse>
        
        
        </tbody>
        <tfoot>
        <tr>          
            <td colspan="${attrlen}">
                <?=$pagelink;?>
            </td>
        </tr>
        </tfoot>
    </table>
<script type="text/javascript" src="/resources/scripts/list.js"></script>
<script type="text/javascript" src="/resources/scripts/${entityName}/list.js"></script>