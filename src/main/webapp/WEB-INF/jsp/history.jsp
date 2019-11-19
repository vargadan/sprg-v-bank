<!doctype html>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:page title="Transaction history of ${param.accountNo}">
<table class="table table-sm">
    <thead>
    <tr>
        <th scope="col">From Account</th>
        <th scope="col">To Account</th>
        <th scope="col">Amount</th>
        <th scope="col">Currency</th>
        <th scope="col">Executed</th>
        <th scope="col">Note</th>
    </tr>
    </thead>
    <tbody>
    <c:if test="${empty transactions}">
        <tr>
            <td colspan="2">No Transactions Available</td>
        </tr>
    </c:if>
    <c:forEach var="transaction" items="${transactions}">
        <tr>
            <td>${transaction.fromAccountNo}</td>
            <td>${transaction.toAccountNo}</td>
            <td>${transaction.amount}</td>
            <td>${transaction.currency}</td>
            <td>${transaction.executed}</td>
            <td>${transaction.note}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<a href="/">back</a>
</t:page>