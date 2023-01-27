<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  User: nadimmahmud
  Since: 1/17/23
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>Home</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
<c:set var="available" value="AVAILABLE" scope="page"/>
<c:set var="color" value="" scope="page"/>
<jsp:include page="nvabar.jsp"/>
<div class="container">
    <div class="row">
        <c:forEach items="${itemList}" var="item">
            <div class="col-md-3 p-1">
                <div class="card shadow-sm p-3 mb-5 bg-body-tertiary rounded">
                    <div class="card-body">
                        <h5 class="card-title text-center">${item.name}</h5>
                        <hr>
                        <p class="m-0">Category: ${item.category.name}</p>
                        <p class="m-0">Price: ${item.price} Taka</p>
                        <p class="m-0 mb-2 ${item.availability != available ? 'text-danger' : ''}">Availability: ${item.availability.label}</p>
                        <hr>
                        <p class="card-text">${item.description}</p>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN"
        crossorigin="anonymous">
</script>
</body>
</html>
