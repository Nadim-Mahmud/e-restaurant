<%--
  User: nadimmahmud
  Since: 1/25/23
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>Waiter | Notification</title>
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
    <c:set var="prepared" value="PREPARED" scope="page"/>
    <div class="container col-md-10 mt-2">
        <div class="card">
            <div class="card-body">
                <h5 class="text-center mb-3">Waiter Notifications</h5>
            </div>

            <table class="table align-middle text-center">
                <thead>
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Table</th>
                    <th scope="col">Items</th>
                    <th scope="col">Est time</th>
                    <th scope="col">Status</th>
                    <th scope="col">Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${orderList}" var="order" varStatus="orderStat">
                    <tr>
                        <td>
                            <c:out value="${orderStat.count}"/>
                        </td>
                        <td>
                            <c:out value="${order.restaurantTable.name}"/>
                        </td>
                        <td>
                            <c:forEach items="${order.orderLineItemList}" var="orderLineItem">
                                <li><c:out value="${orderLineItem.item.name} x ${orderLineItem.quantity}"/></li>
                            </c:forEach>
                            </ul>
                        </td>
                        <td>
                            <c:out value="${order.estTime}"/>
                        </td>
                        <td>
                            <c:out value="${order.status.label}"/>
                        </td>
                        <td>
                            <c:if test="${order.status == prepared}">
                                <c:url var="preparedUrl" value="/waiter/notification/mark-served">
                                    <c:param name="orderId" value="${order.id}"/>
                                </c:url>
                                <form:form class="text-center my-0 mx-2 p-0" action="${preparedUrl}" method="post">
                                    <button class="btn btn-outline-primary center btn-sm"
                                            onclick="return confirm('Is it served?')">Mark as served
                                    </button>
                                </form:form>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN"
        crossorigin="anonymous">
</script>
</body>
</html>
