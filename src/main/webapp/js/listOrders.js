function getAllOrders() {
  var conn = new XMLHttpRequest();
  conn.onreadystatechange = function () {
    if (this.readyState == 4 && this.status == 200) {
      var orders = JSON.parse(this.responseText);
      for (var i = 0; i < orders.length; i++) {
        addOrder(orders[i]);
      }
    }
  }
  conn.open("GET", "/getAllOrders", true);
  conn.send();
}

getAllOrders();

// constructs and adds a div for an @order object
// @order {id: long, customer: string, status: string, contents: @Dictionary}
function addOrder(order) {
  var contents = document.getElementById("contents");
  var orderDiv = document.createElement("div");
  orderDiv.className = "order";
  orderDiv.innerHTML = "<h3>Order #" + order.id + " by " +
   order.customer + "</h3>" +
    "<p>Total: " + order.totalPrice + " NIS</p>";
  contents.appendChild(orderDiv);
}
