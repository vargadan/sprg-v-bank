<!doctype html>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:page title="V-Bank Home Page">
    <div class="container" id="accounts-holder">
        <c:forEach var="data" items="${accounts}">
            <div class="account-block">
                <div class="account-detail accountNo">Account Number :
                    <a href="/history?accountNo=' + data.accountNo + '" title="Show Transactions" >${data.accountNo} </a>
                    <div>
                        <div class="account-detail balance">Balance : ${data.balance} ${data.currency}</div>
                        <a class="button transfer-link" href="/transfer?fromAccountNo='${data.accountNo}"
                           title="SEND MONEY from ${data.accountNo}">Send Money</a></div>
                </div>
            </div>
        </c:forEach>
    </div>
</t:page>