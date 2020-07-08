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
                if (this.readyState == 4 && this.status == 200) {
                  var order = JSON.parse(this.responseText);
                  document.getElementById("status").innerHTML = order.status;
                  document.getElementById("time").innerHTML = order.time;
                }
              }
              request.open("GET", "/updateStatus?id=${order.id}", true);
              request.send();
            }

            window.setInterval(function() { updateStatus(); }, 2000);
        </script>

        <p>Thank you - your order has been received. You need to pay ${totalPrice} NIS</p>
        <p>Order Status: <span id="status">${order.status}</span></p>
        <p>Time updated: <span id="time">${time}</span></p>

        <jsp:include page="/jsp/footer.jsp" />
    </body>
</html>
