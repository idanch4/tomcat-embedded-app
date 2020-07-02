<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>Home Page</title>
    </head>
    <body>
        <!-- header template -->
        <jsp:include page="/jsp/header.jsp" />

        <h1>Welcome to Idan's Restaurant</h1>
        <h2>Menu:</h2>
        <ul>
            <c:forEach items="${menu}" var="dish">
                <li>${dish}</li>
            </c:forEach>
        </ul>
        <a href="/listMenu">Place an Order</a>

        <! -- footer -->
        <jsp:include page="/jsp/footer.jsp" />
    <body>
</html>