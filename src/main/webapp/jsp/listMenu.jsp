<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>Idan's Restaurant - Menu</title>
    </head>

    <body>
        <jsp:include page="/jsp/header.jsp"/>

        <h1>Welcome to the Restaurant</h1>
        <h2>please choose an order from the menu:</h2>
        <form action='/orderReceived.html' method='post'>
            <ul>
                <c:forEach items="${menu}" var="dish">
                    <li>${dish} <input type='text' name='dish_${dish.id}'/></li>
                </c:forEach>
            </ul>
            <input type='submit' value='Place Order'/>
        </form>

        <jsp:include page="/jsp/footer.jsp"/>
    </body>
</html>