<%--
User: nadimmahmud
Date: 12/7/22
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<nav class="navbar navbar-expand-lg bg-body-tertiary sticky-top bg-light">
    <c:set var="chef" value="chef" scope="page"/>
    <c:set var="waiter" value="waiter" scope="page"/>
    <c:set var="category" value="category" scope="page"/>
    <c:set var="item" value="item" scope="page"/>
    <c:set var="resTable" value="resTable" scope="page"/>
    <div class="container-fluid">
        <form:form cssClass="m-0" action="/login" method="post">
            <button class="navbar-brand btn">
                <b>E-Staurant</b>
            </button>
        </form:form>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarText"
                aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarText">
            <c:if test="${admin != null}">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link active underline-hover" aria-current="page" href="/admin/chef">
                                ${navItem == chef ? '<b>Chef</b>' : 'Chef'}
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active underline-hover" aria-current="page" href="/admin/waiter">
                                ${navItem == waiter ? '<b>Waiter</b>' : 'Waiter'}
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active underline-hover" aria-current="page" href="/admin/category">
                                ${navItem == category ? '<b>Category</b>' : 'Category'}
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active underline-hover" aria-current="page" href="/admin/item">
                                ${navItem == item ? '<b>Item</b>' : 'Item'}
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active underline-hover" aria-current="page" href="/admin/res-table">
                                ${navItem == resTable ? '<b>Table</b>' : 'Table'}
                        </a>
                    </li>
                </ul>
            </c:if>
            <span class="navbar-text">
                <div class="nav-item dropdown">
                <p class="nav-link dropdown-toggle text-black m-0" role="button" data-bs-toggle="dropdown"
                   aria-expanded="false">${user.email}</p>
                <ul class="dropdown-menu">
                    <c:if test="${admin==null}">
                        <li>
                            <a class="dropdown-item" href="update">Update Profile</a>
                        </li>
                        <li>
                            <hr class="dropdown-divider">
                        </li>
                    </c:if>
                    <li>
                        <a class="m-0 dropdown-item" href="/logout">Log Out</a>
                    </li>
                </ul>
            </div>
            </span>
        </div>
    </div>
</nav>