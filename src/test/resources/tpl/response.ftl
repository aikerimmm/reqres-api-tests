<html>
<body>
<h3>HTTP Response</h3>

<div>
    <b>Status code:</b> ${data.responseCode}
</div>

<#if data.body??>
    <h4>Body</h4>
    <pre>${data.body}</pre>
</#if>

</body>
</html>