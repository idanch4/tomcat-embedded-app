<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>Process Order</title>
    </head>
    <body>
        <jsp:include page="/jsp/header.jsp" />

        <table>
            <tr>
                <td>Id</td>
                <td>Price</td>
                <td>Status</td>
            </tr>
            <!-- Insert ALL orders -->
            <form method="POST" action="/processOrders">
                <c:forEach items="${orders}" var="dish">
                    <tr>
                        <td>${dish.id}</td>
                        <td>${dish.customer}</td>
                        <td>${dish.status}</td>
                        <td>
                            <select name="update_status_${dish.id}">
                                <c:forEach items="${order_statuses}" var="status">
                                    <c:choose>
                                        <c:when test="${status eq dish.status}">
                                            <option value="${status}" selected="selected">${status}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${status}">${status}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                </c:forEach>
                <tr>
                    <td><input type="submit" value="Update"/></td>
                </tr>
            </form>
        </table>

        <jsp:include page="/jsp/footer.jsp" />
    </body>
</html>