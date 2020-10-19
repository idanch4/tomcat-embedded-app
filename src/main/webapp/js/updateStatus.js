function helloWorld() {
  var request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
      var order = JSON.parse(this.responseText);
      document.getElementById("status").innerHTML = order.status;
    }
  }
  request.open("GET", "/getStatus?id=${id}", true);
  request.send();
}

window.setInterval(function() { helloWorld(); }, 2000);
