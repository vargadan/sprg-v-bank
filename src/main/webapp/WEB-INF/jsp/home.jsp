<!doctype html>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:page t:onload="addAccountRows()" title="V-Bank Home Page">
    <script type="text/javascript">
        function addAccountRow(accountId) {
            console.log('accountId : ' + accountId);
            $(document).ready(function () {
                $.ajax({
                    url: "/api/v1/account/" + accountId
                }).then(function (data) {
                    var accountRow = getAccountBlock(data);
                    $('#accounts-holder').append(accountRow);
                });
            });
        };

        function getAccountBlock(data) {
            var trLinkTitle = 'SEND MONEY from ' + data.accountNo;
            var trLinkHref = '/transfer?fromAccountNo=' + data.accountNo;
            var accountRow = '<div class="account-block">' +
                '<div class="account-detail accountNo">Account Number : ' +
                '<a href="/history?accountNo=' + data.accountNo + '" title="Show Transactions" >' + data.accountNo + '</a><div>' +
                '<div class="account-detail balance">Balance : ' + data.balance + ' ' + data.currency + '</div>' +
                '<a class="button transfer-link" href="' + trLinkHref + '" title="' + trLinkTitle + '">Send Money</a></div>';
            return accountRow;
        }

    function addAccountRows() {
            <c:forEach var="accountId" items="${accountIds}">
                addAccountRow('${accountId}');
            </c:forEach>
    }
    </script>
    <div class="container" id="accounts-holder">
    </div>
<%--    <div class="container">--%>
<%--        <form method="POST" action="/uploadTransactions" enctype="multipart/form-data">--%>
<%--            <label path="file">Select XML file with transactions</label>--%>
<%--            <br/>--%>
<%--            <input type="file" name="file" class="file-upload" />--%>
<%--            <input id="upload-button" type="submit" class="button" value="Send" />--%>
<%--        </form>--%>
<%--    </div>--%>
</t:page>