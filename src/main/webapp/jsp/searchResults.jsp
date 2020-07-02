<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>Search Results</title>
    </head>
    <body>
        <jsp:include page="/jsp/header.jsp" />

        <h1>Search Results:</h1>
        <ul>
            <c:forEach items="${menu}" var="dish">
                <li>${dish}</li>
            </c:forEach>
        </ul>

        <jsp:include page="/jsp/footer.jsp" />
    </body>
</html>