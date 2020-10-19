pageController();

function pageController() {
  var maxId = getAllOrders();
  checkForNewOrders(maxId);
}

function getAllOrders() {
  var maxId = 0;
  var conn = new XMLHttpRequest();
  conn.onreadystatechange = function () {
    if (this.readyState == 4 && this.status == 200) {
      var orders = JSON.parse(this.responseText);
      for (var i = 0; i < orders.length; i++) {
        var order = orders[i];
        if (order.id > maxId) {
          maxId = order.id;
        }

        addOrder(order);
      }
    }
  }
  conn.open("GET", "/getAllOrders", true);
  conn.send();
}

// long-poll the server for any new orders and update the DOM immediately
function checkForNewOrders(maxId) {
  var socket = new WebSocket(
    "ws://localhost:8082/checkNewOrders");
  socket.onmessage = function(event) {
    var resp = JSON.parse(event.data);
    var order = JSON.parse(resp.order);
    if (resp.action == "add"){
      addOrder(order);
    } else {
      removeOrder(order);
    }
  }
}


// constructs and adds a div for an order object
// @order {id: long, customer: string, status: string, contents: @Dictionary}
function addOrder(order) {
  if(order.status != "READY") {

    var contents = document.getElementById("contents");
    var orderDiv = document.createElement("div");
    orderDiv.className = "order";
    orderDiv.setAttribute("id", "order_" + order.id);
    orderDiv.innerHTML = "<h3>Order #" + order.id + " by " +
     order.customer + "</h3>";

    for(var i = 0; i < order.contents.length; i++) {
      orderDiv.innerHTML += "<p>" + order.contents[i].name + " - " +
      order.contents[i].priceShekels + " NIS</p>";
    }

    orderDiv.innerHTML += "<p>Total: " + order.totalPrice + " NIS</p>";
    contents.appendChild(orderDiv);
  }
}

function removeOrder(id) {
  var orderEl = document.getElementById("order_" + id);
  document.getElementById("contents").removeChild(orderEl);
}
