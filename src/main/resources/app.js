function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect();
    stompClient.subscribe('/index/messages', Refresh);
}

function Refresh() {
    location.reload(true);
}
