<!doctype html>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:page title="Free SQL">
    <div class="container">
        <form action="/sql" method="post">
            <fieldset>
                <p>
                    <label for="sql">SQL:</label>
                    <input id="sql" name="sql" type="text" value="" />
                </p>
                <p>
                    <input type="submit" value="send" class="button"/>
                </p>
            </fieldset>
        </form>
</t:page>