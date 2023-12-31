<%--
  User: nadimmahmud
  Since: 1/19/23
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="en"/>
<fmt:setBundle basename="net.therap.estaurant" var="lang"/>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>
        <fmt:message key="order.item.form.page.title"/>
    </title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
<%@ include file="nvabar.jsp" %>
<div class="container">
    <div class="container col-md-5 mt-2">
        <div class="card">
            <div class="card-body">
                <h5 class="text-center mb-3">
                    <fmt:message key="order.items.form.title"/>
                </h5>
                <div class="text-center">
                    <c:if test="${emptyList != null}">
                        <p class="text-danger">
                            <fmt:message key="input.empty.selection"/>
                        </p>
                    </c:if>
                </div>
                <form:form action="/waiter/new-order/items/add" modelAttribute="orderLineItem" method="post">
                    <div class="d-flex justify-content-end">
                        <button type="submit" class="btn btn-sm btn-success">
                            <fmt:message key="button.add.item"/>
                        </button>
                    </div>
                    <div class="mb-3">
                        <label for="item" class="form-label">
                            <fmt:message key="label.select.item"/>
                        </label>
                        <form:select path="item" class="form-select">
                            <form:options items="${itemList}" itemLabel="name" itemValue="id"/>
                        </form:select>
                        <form:errors path="item" cssClass="text-danger"/>
                    </div>
                    <div class="mb-3">
                        <label for="quantity" class="form-label">
                            <fmt:message key="label.quantity"/>
                        </label>
                        <form:input path="quantity" class="form-control"/>
                        <form:errors path="quantity" cssClass="text-danger"/>
                    </div>
                </form:form>
                <c:if test="${orderLineItemList.size() == 0}">
                    <div>
                        <p class="text-center mb-3"><br>
                            <fmt:message key="message.empty.item"/>
                        </p>
                    </div>
                </c:if>
                <c:if test="${orderLineItemList.size() != 0}">
                    <div>
                        <h6 class="text-center mb-3">
                            <br>
                            <fmt:message key="message.added.item.list"/>
                        </h6>
                    </div>
                    <table class="table align-middle text-center">
                        <thead>
                        <tr>
                            <th scope="col">
                                <fmt:message key="label.serial"/>
                            </th>
                            <th scope="col">
                                <fmt:message key="label.name"/>
                            </th>
                            <th scope="col">
                                <fmt:message key="label.quantity"/>
                            </th>
                            <th scope="col">
                                <fmt:message key="label.action"/>
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${orderLineItemList}" var="orderLineItem" varStatus="orderLineStat">
                            <tr>
                                <td>
                                    <c:out value="${orderLineStat.count}"/>
                                </td>
                                <td>
                                    <c:out value="${orderLineItem.item.name}"/>
                                </td>
                                <td>
                                    <c:out value="${orderLineItem.quantity}"/>
                                </td>
                                <td>
                                    <c:url var="removeUrl" value="/waiter/new-order/items/remove">
                                        <c:param name="orderLineItemId" value="${orderLineItem.item.id}"/>
                                    </c:url>
                                    <form:form class="text-center my-0 mx-2 p-0" action="${removeUrl}" method="post">
                                        <button class="btn btn-outline-danger center btn-sm">
                                            <fmt:message key="button.remove"/>
                                        </button>
                                    </form:form>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:if>
                <form:form class="m-0 p - 0 " action="/waiter/new-order/list/save" method="post">
                    <div class="d-grid gap-2">
                        <button type="submit" class="btn btn-sm btn-primary">
                            <fmt:message key="button.place.order"/>
                        </button>
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
