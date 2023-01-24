<%--
  User: nadimmahmud
  Since: 1/19/24
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>Waiter | Order Form</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
<div class="container">
    <%@ include file="nvabar.jsp" %>
    <c:set var="br" value="<br>" scope="page"/>
    <div class="container col-md-5 mt-2">
        <div class="card">
            <div class="card-body">
                <h5 class="text-center mb-3">Order form</h5>
                <form:form action="/waiter/new-order/add/order-form/save" modelAttribute="order" method="post">
                    <div class="mb-3">
                        <label for="restaurantTable" class="form-label">Select Table </label>
                        <form:select path="restaurantTable" class="form-select">
                            <form:options items="${resTableList}" itemLabel="name" itemValue="id"/>
                        </form:select>
                    </div>
                    <div class="mb-3">
                        <label for="estServeTime" class="form-label">EST Time (minuits)</label>
                        <form:input  path="estServeTime" class="form-control"/>
                        <form:errors path="estServeTime" cssClass="text-danger"/>
                    </div>
                    <button type="submit" class="btn btn-primary btn-sm">Save</button>
                </form:form>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN"
        crossorigin="anonymous">
</script>
</body>
</html>
