var updateCount = 0;
var socket = new SockJS('/test');
var stompClient = Stomp.over(socket);
// var audio = new Audio('notification.mp3');
stompClient.connect({}, function (frame) {
    stompClient.subscribe('/topic/messages');
});

socket.addEventListener('message', function(event) {
    var message = String(event.data);
    if (message.indexOf('username') !== -1) {
        var substring = event.data.substring(event.data.indexOf('{'), event.data.lastIndexOf('}') + 1);
        reloadList(substring);
        notify()
    }
});

function notify() {
    if (document.hidden) {
    updateCount++;
    document.title = '(' + updateCount + ')P2P Chat';
    // audio.play();
}}

window.onfocus = function () {
    document.title = 'P2P Chat';
    updateCount = 0;
};

function reloadList(substring) {
    var username = JSON.parse(substring).username;
    var text = JSON.parse(substring).text;
    $("#refreshable").append("<ul><li>" + username + "<br/>" + text + "</li></ul>");
}

function Refresh() {
    window.location.reload(true);
}
