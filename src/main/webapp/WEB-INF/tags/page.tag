<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@tag description="Overall Page template" pageEncoding="UTF-8" %>
<%@ attribute name="title" required="true" %>
<%@ attribute name="onload" required="false" %>
<head>
      <title>${title}</title>
      <meta name="_csrf" content="${_csrf.token}"/>
      <meta name="_csrf_header" content="${_csrf.headerName}"/>
      <meta charset="utf-8" />
      <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
      <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous" />
      <link rel="stylesheet" href="/css/styles.css" />
      <script type="text/javascript" src="/js/jquery-3.3.1.js"></script>
</head>
    <c:if test="${not empty param.info}" >
        <c:set var="info" value="${param.info}" />
    </c:if>
    <c:if test="${not empty param.error}" >
        <c:set var="error" value="${param.error}" />
    </c:if>
<html>
    <body onload="${onload}">
        <div class="container-fluid">
                <div id="header" class="row align-items-start">
                        <div class="col left">V-Bank Internet Banking</div>
                        <div class="col right">
                                <sec:authorize access="isAuthenticated()">
                                        <span>Logged in as <sec:authentication property="name"/></span>
                                        <button id="logout-button" class="button"
                                        onclick="document.getElementById('logoutForm').submit()">LOGOUT</button>
                                        <form method="post" action="/logout" id="logoutForm">
                                                <sec:csrfInput />
                                        </form>
                                </sec:authorize>
                        </div>
                </div>
        </div>
    <c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
        <div class="container">
           <div class="row alert alert-danger" role="alert"><c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/></div>
        </div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="container">
          <div class="row alert alert-danger" role="alert">${error}</div>
        </div>
    </c:if>
    <c:if test="${not empty info}">
        <div class="container">
            <div class="row alert alert-info" role="alert">${info}</div>
        </div>
    </c:if>
    <c:if test="${not empty message}">
        <div class="container">
            <div class="row alert alert-warning" role="alert">${message}</div>
        </div>
    </c:if>
        <div id="body" class="container">
            <h2 id="title">${title}</h2>
            <jsp:doBody/>
        </div>
        <div class="container-fluid">
                <div id="footer" class="row align-items-end">
                    <div class="col left">Copyright: USSR (Usable, Secure, Simple and Robust) Software Corp. 2019</div>
                    <div class="col right">git.branch : <%=System.getProperty("git.branch","n/a")%></div>
                </div>
        </div>
    </body>
 </html>