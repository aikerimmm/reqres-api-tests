<html>
<body>
<h3>HTTP Request</h3>

<div>
    <b>Method:</b> ${data.method}
</div>

<div>
    <b>URL:</b> ${data.url}
</div>

<#if data.body??>
    <h4>Body</h4>
    <pre>${data.body}</pre>
</#if>

</body>
</html>