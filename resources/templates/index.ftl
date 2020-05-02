<#-- @ftlvariable name="data" type="com.sample_junk_traders.IndexData" -->
<html>
    <head>
        <link rel="stylesheet" href="/static/styles.css">
    </head>
    <body>
        <ul>
        <#list data.items as item>
            <li>${item}</li>
        </#list>
        </ul>
    </body>
</html>
