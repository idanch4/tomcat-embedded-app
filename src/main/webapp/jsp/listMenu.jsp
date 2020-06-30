<%@ page import="com.idanch.data.representations.Dish" %>
<%@ page import="java.util.List" %>

<html>
    <head>
        <title>Idan's Restaurant - Menu</title>
    </head>

    <body>
        <jsp:include page="/jsp/header.jsp"/>

        <h1>Welcome to Idan's Restaurant</h1>
        <h2>please choose an order from the menu:</h2>
        <form action='/orderReceived.html' method='post'>
            <ul>

            <%
             List<Dish> menu = (List<Dish>) request.getAttribute("menu");
             for (Dish dish: menu) { %>
                <li>
                        <%=dish%>" <input type='text' name='dish_<%=dish.getId()%>'/>
                </li>
            <% } %>

            </ul>
            <input type='submit' value='Place Order'/>
        </form>

        <jsp:include page="/jsp/footer.jsp"/>
    </body>
</html>