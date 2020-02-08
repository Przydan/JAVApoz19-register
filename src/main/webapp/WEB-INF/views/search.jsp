<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>search</title>
</head>
<body>

<form method="get" action="/users">
    <input type="text" name="firstName"/>
    <input type="submit"/>
    <p>
        <label>matchExact</label>
        <input type="checkbox" name="matchExact">
    </p>
</form>


</body>
</html>
