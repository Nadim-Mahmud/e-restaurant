<%--
  User: nadimmahmud
  Date: 12/9/22
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>Error Occurred!</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
</head>
<body>
<div class="container p-3 text-center">
    <img src="${pageContext.request.contextPath}/assets/exclamation-triangle-fill.svg" class="img-fluid">
    <h1 class="display-3">Opps! Something went wrong!</h1>
    <a class="btn btn-outline-primary" href="/">Go Back Home</a>
    <br><br>
</div>
</body>
</html>
