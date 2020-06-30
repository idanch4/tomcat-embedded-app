<%@ page import="com.idanch.data.factories.MenuDaoFactory" %>
<%@ page import="com.idanch.data.interfaces.MenuDao" %>
<%@ page import="java.util.List" %>
<%@ page import="com.idanch.data.representations.Dish" %>

<html>
    <head>
        <title>Home Page</title>

        <link rel="stylesheet" type="text/css" href="/styles.css" />
    </head>
    <body>
        <!-- header template -->
        <jsp:include page="/header.jsp" />

        <%
           MenuDao menuDao = MenuDaoFactory.getMenuDao();
           List<Dish> queryResult = menuDao.findDishes("");
        %>

        <h1>Welcome to Idan's Restaurant</h1>
        <h2>Menu:</h2>
        <ul>
            <% for (Dish dish: queryResult) { %>
                <li><%=dish%></li>
                <% } %>
        </ul>
        <a href="/listMenu.html">Place an Order</a>

        <! -- footer -->
        <jsp:include page="/footer.jsp" />
    <body>
</html>