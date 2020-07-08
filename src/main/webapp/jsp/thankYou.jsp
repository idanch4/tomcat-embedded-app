<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <title>Thank You!</title>
    </head>
    <body>
        <jsp:include page="/jsp/header.jsp" />

        <script>
            function updateStatus() {
              var request = new XMLHttpRequest();
              request.onreadystatechange = function() {
                if (this.readyState == 4) {
                  document.getElementById("orderStatus").innerHTML = this.responseText;
                }
              }
              request.open("GET", "/updateStatus?id=${id}", true);
              request.send();
            }

            window.setInterval(function() { updateStatus(); }, 2000);
        </script>

        <p>Thank you - your order has been received. You need to pay ${totalPrice} NIS</p>
        <p>Order Status: <span id="orderStatus">${status}</span></p>

        <jsp:include page="/jsp/footer.jsp" />
    </body>
</html>
