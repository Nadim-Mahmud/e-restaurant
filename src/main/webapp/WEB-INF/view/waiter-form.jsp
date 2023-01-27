<%--
  User: nadimmahmud
  Since: 1/19/23
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>Admin | Waiter Form</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
<%@ include file="nvabar.jsp" %>
<div class="container">
    <c:set var="br" value="<br>" scope="page"/>
    <div class="container col-md-5 mt-2">
        <div class="card">
            <div class="card-body">
                <h5 class="text-center mb-3">Waiter form</h5>
                <form:form action="/admin/waiter/save" modelAttribute="waiter" method="post">
                    <div class="mb-3">
                        <label for="firstName" class="form-label">First Name</label>
                        <form:input path="firstName" class="form-control"/>
                        <form:errors path="firstName" cssClass="text-danger"/>
                    </div>
                    <div class="mb-3">
                        <label for="lastName" class="form-label">Last Name</label>
                        <form:input path="lastName" class="form-control"/>
                        <form:errors path="lastName" cssClass="text-danger"/>
                    </div>
                    <div class="mb-3">
                        <label for="dateOfBirth" class="form-label">Date of birth</label>
                        <form:input type="date" path="dateOfBirth" class="form-control"/>
                        <form:errors path="dateOfBirth" cssClass="text-danger"/>
                    </div>
                    <div class="mb-3">
                        <label for="joiningDate" class="form-label">Joining Date</label>
                        <form:input type="date" path="joiningDate" class="form-control"/>
                        <form:errors path="joiningDate" cssClass="text-danger"/>
                    </div>
                    <div class="mb-2">
                        <label for="restaurantTableList" class="form-label">Table List :</label>
                        <form:checkboxes path="restaurantTableList" items="${resTableList}" itemLabel="name"
                                         itemValue="id" class="form-check-input ms-2 me-2"/>
                    </div>
                    <div class="mb-3">
                        <label for="email" class="form-label">Email </label>
                        <form:input type="email" path="email" class="form-control"/>
                        <form:errors path="email" cssClass="text-danger"/>
                    </div>
                    <c:if test="${updatePage == null}">
                        <div class="mb-3">
                            <label for="email" class="form-label">Password </label>
                            <form:input type="password" path="password" class="form-control"/>
                            <form:errors path="password" cssClass="text-danger"/>
                        </div>
                    </c:if>
                    <div class="d-grid gap-2">
                        <button type="submit" class="btn btn-primary btn-sm">Save</button>
                    </div>
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
