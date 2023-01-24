<%--
  User: nadimmahmud
  Since: 1/19/23
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8">
  <title>Admin | Items</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>

<div class="container">
  <jsp:include page="nvabar.jsp"/>
  <div class="d-flex justify-content-end mt-3 mb-1">
    <form:form class="m-0" action="${pageContext.request.contextPath}/admin/item/form" method="get">
      <button class="btn btn-success btn-sm">+ Add Item</button>
    </form:form>
  </div>
  <div class="text-center">
    <c:if test="${success != null}">
      <p class="text-success">
        &check; success !!
      </p>
    </c:if>
  </div>
  <table class="table table-hover table-sm align-middle text-center">
    <thead class="table-head bg-primary bg-opacity-50">
    <tr>
      <th scope="col" class="text-center">ID</th>
      <th scope="col" class="text-center">Item</th>
      <th scope="col" class="text-center">Price</th>
      <th scope="col" class="text-center">Category</th>
      <th scope="col" class="text-center">Availability</th>
      <th scope="col" class="text-center">Description</th>
      <th scope="col" class="text-center">Created At</th>
      <th scope="col" class="text-center">Updated At</th>
      <th scope="col" class="text-center">Action</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${itemList}" var="item" varStatus="itemStat">
      <tr>
        <td class="text-center">
          <c:out value="${itemStat.count}"/>
        </td>
        <td class="text-center">
          <c:out value="${item.name}"/>
        </td>
        <td class="text-center">
          <c:out value="${item.price}"/>
        </td>
        <td class="text-center">
          <c:out value="${item.category.name}"/>
        </td>
        <td class="text-center">
          <c:out value="${item.availability.label}"/>
        </td>
        <td class="text-center">
          <c:out value="${item.description}"/>
        </td>
        <td class="text-center">
          <fmt:formatDate value="${item.createdAt}" type="date"/>
        </td>
        <td class="text-center">
          <fmt:formatDate value="${item.updatedAt}" type="date"/>
        </td>
        <td>
          <div class="d-flex justify-content-center my-1">
            <c:url var="updateUrl" value="${pageContext.request.contextPath}/admin/item/form">
              <c:param name="itemId" value="${item.id}"/>
            </c:url>
            <a class="text-center my-0 mx-2 p-0" href="${updateUrl}">
              <button class="btn btn-outline-primary center btn-sm">Update</button>
            </a>
            <c:url var="deleteUrl" value="${pageContext.request.contextPath}/admin/item/delete">
              <c:param name="itemId" value="${item.id}"/>
            </c:url>
            <form:form class="text-center my-0 mx-2 p-0" action="${deleteUrl}" method="post">
              <button class="btn btn-outline-danger center btn-sm"
                      onclick="return confirm('Are you sure to delete?')">Delete
              </button>
            </form:form>
          </div>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>

</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN"
        crossorigin="anonymous">
</script>
</body>
</html>
