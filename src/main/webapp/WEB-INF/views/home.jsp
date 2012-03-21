<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>
    <c:forEach items="${entries}" var="entry">
        <a href="${entry.link}">${entry.title}</a><br/>
    </c:forEach>
</body>
</html>