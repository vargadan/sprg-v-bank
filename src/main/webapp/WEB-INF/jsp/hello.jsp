<!DOCTYPE html>
<html lang="en">
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
    <meta charset="UTF-8">
    <title>Hello</title>
</head>
<body>
    <h2 class="hello-title">Hello ${output}!</h2>
    <p>${param.x}</p>
</body>
</html>