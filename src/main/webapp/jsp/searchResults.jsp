<%@ page import="java.util.List" %>
<%@ page import="com.idanch.data.representations.Dish" %>
<html>
    <head>
        <title>Search Results</title>
    </head>
    <body>
        <jsp:include page="/jsp/header.jsp" />

        <h1>Search Results:</h1>
        <ul>
            <%
            List<Dish> dishes = (List<Dish>) request.getAttribute("dishes");
            for (Dish dish: dishes) {%>

                <li><%=dish%></li>
            <% }%>
        </ul>

        <jsp:include page="/jsp/footer.jsp" />
    </body>

</html>