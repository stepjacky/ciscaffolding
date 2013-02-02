<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');
/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * FileName application/controllers/${entityName}.php
 * Created by CIscaffolding.
 * User: qujiakang
 * QQ:myqq_postor@qq.com
 * Email: qujiakang@gmail.com  
 * Date: ${createDate}
 *    
 */

class ${entityName?cap_first} extends MY_Controller {
     
    public  function __construct(){
        parent::__construct("${entityName?cap_first}_model");
        <#list attrs?keys as attr>
            <#assign meta = attrs[attr]>
             <#if meta.inputType=="ckeditor">
               $this->load->library('create_ckeditor');
                
            </#if>
        </#list>
    }

    public function index(){
        $data = array();
        
        $this->__user_header($data);
        $this->load->view("${entityName}/index",$data);
        $this->load->view("apps/footer");
    }
    
     /**
      * 新增编辑
      */
    public function editNew($id=-1){
        
       $data = array(); 
	   <#list attrs?keys as attr>
            <#assign meta = attrs[attr]>
            <#if meta.inputType=="ckeditor">
             
               $ckcfg = array();
               $ckcfg["name"]  ="${meta.name}";          
                <#break>     
                    
            </#if>
        </#list>
      
        if($id!=-1){
           $data = $this->dao->get($id);
           <#list attrs?keys as attr>
            <#assign meta = attrs[attr]>
             <#if meta.inputType=="ckeditor">
              $ckcfg["value"] = $data["${meta.name}"];       
              <#break>               
            </#if>
        </#list>
          
        }
        
        <#list attrs?keys as attr>
            <#assign meta = attrs[attr]>
             <#if meta.inputType=="ckeditor">
             $data['my_editor'] = $this->create_ckeditor->createEditor( $ckcfg);        
         <#break>               
            </#if>
        </#list>
        $this->load->view("admin/res-head");
        $this->load->view($this->dao->table()."/editNew",$data);
        $this->load->view("admin/footer");
    }
    
    
}   