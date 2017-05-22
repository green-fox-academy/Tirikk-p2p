var socket = new SockJS('/test');
var stompClient = Stomp.over(socket);
stompClient.connect({}, function (frame) {
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/messages');
});

socket.addEventListener('message', function(event) {
    var message = String(event.data);
    if (message.indexOf('proba') !== -1) {
        Refresh();
    }
});

function sendMessage() {
stompClient.send('/app/message', {}, 'message');
}

function Refresh() {
    window.location.reload(true);
}
