<html>
    <head>
        <title>Thank You!</title>
    </head>
    <body>
        <jsp:include page="/jsp/header.jsp" />

        <% Double totalPrice = (Double) request.getAttribute("totalPrice");
         if (totalPrice != null) { %>
            <p>Thank you - your order has been received. You need to pay
            <%=totalPrice%> Shekels</p>
        <% } %>

        <jsp:include page="/jsp/footer.jsp" />
    </body>
</html>