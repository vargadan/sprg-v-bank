<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<head>
    <title>Logged in as ${username}</title>
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <link rel="stylesheet" href="/css/styles.css">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">


    </head>
<body>

<div id="header">
    <span class="left">
        V-Bank Internet Banking
    </span>
    <span class="right" style="width: 49%; text-align: right; display: block; float: right">
        <sec:authorize access="isAuthenticated()">
            <span>Logged in as <sec:authentication property="name"/></span>
            <button id="logout-button" class="button"
                onclick="document.getElementById('logoutForm').submit()">LOGOUT</button>
            <form method="post" action="/logout" id="logoutForm">
                <sec:csrfInput />
            </form>
        </sec:authorize>
    </span>
</div>

    <div class="limiter">
    <div class="container">
    <div class="wrapper">
    <span id="title">${param.title}</span>
    <c:if test="${not empty param.message}"><span class="message">${param.message}</span></c:if>