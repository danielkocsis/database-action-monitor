var stompClient = null;

function setConnected(connected) {
  $("#connect").prop("disabled", connected);
  $("#disconnect").prop("disabled", !connected);

  const messages = $("#messages");

  messages.html("");

  if (connected) {
    $("#content-area").show();
    messages.append("<tr><td>WebSocket connection established!</td></tr>");
  }
  else {
    $("#content-area").hide();
  }
}

function connect() {
  const socket = new SockJS('/gs-guide-websocket');
  stompClient = Stomp.over(socket);

  stompClient.connect({}, function (frame) {
    setConnected(true);

    console.log('Connected: ' + frame);

    stompClient.subscribe('/ws/database-insert-broadcast', function (message) {
      showMessages(JSON.parse(message.body).payload);
    });
  });
}

function disconnect() {
  if (stompClient !== null) {
    stompClient.disconnect();
  }

  setConnected(false);

  console.log("Disconnected");
}

function showMessages(message) {
  $("#messages").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
  $("form").on('submit', function (e) {
    e.preventDefault();
  });
  $("#connect").click(function () {
    connect();
  });
  $("#disconnect").click(function () {
    disconnect();
  });
  $("#content-area").hide();
  $("#connection-message").hide();
});