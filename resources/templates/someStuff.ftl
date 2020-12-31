<#import "lib/util.ftl" as u>
<@u.page>
  <ul>
   <#list data.items as item>
       <li>${item}</li>
   </#list>
   </ul>
</@u.page>
