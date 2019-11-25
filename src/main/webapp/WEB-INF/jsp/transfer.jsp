<!doctype html>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:page title="Make a transfer from ${param.fromAccountNo}">
    <div class="container">
        <form action="/doTransfer" method="post">
            <input id="fromAccount" name="fromAccountNo" type="hidden" value="${param.fromAccountNo}">
            <fieldset>
                <p>
                    <label for="toAccount">To Account:</label>
                    <input id="toAccount" name="toAccountNo" type="text" value="">
                </p>
                <p>
                    <label for="amount">Amount:</label>
                    <input id="amount" name="amount" type="text" value="">
                </p>
                <p>
                    <label for="currency">Currency:</label>
                    <input id="currency" name="currency" type="text" value="CHF">
                </p>
                <p>
                    <label for="note">Note:</label>
                    <input id="note" name="note" type="text" value="">
                </p>
                <%-- Check if the _csrf attribute is set in the session --%>
                <c:if test="${not empty csrfTokenAttribute}">
                <%-- And, if so, set on the form as hidden parameter. --%>
                     <input type="hidden" name="csrfToken" value="${csrfTokenAttribute}"/>
                </c:if>
                <%-- This is for SPRING-SECURITY built in CSRF protection if enabled. --%>
                <c:if test="${not empty _csrf}">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </c:if>
                <p>
                    <input type="submit" value="send" class="button"/>
                </p>
            </fieldset>
        </form>
    </div>
    <div class="container">
        <a href="/">back</a>
    </div>
</t:page>