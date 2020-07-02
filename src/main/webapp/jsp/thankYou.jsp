<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <title>Thank You!</title>
    </head>
    <body>
        <jsp:include page="/jsp/header.jsp" />

        <p>Thank you - your order has been received. You need to pay ${totalPrice} NIS</p>

        <jsp:include page="/jsp/footer.jsp" />
    </body>
</html>