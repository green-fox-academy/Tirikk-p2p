var updateCount = 0;
var socket = new SockJS('/test');
var stompClient = Stomp.over(socket);
// var audio = new Audio('notification.mp3');
stompClient.connect({}, function (frame) {
    stompClient.subscribe('/topic/messages');
});

document.head || (document.head = document.getElementsByName('head')[0]);


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
    changeFavicon('favicon-notification.ico');
    // audio.play();
}}

window.onfocus = function () {
    document.title = 'P2P Chat';
    changeFavicon('favicon.ico');
    updateCount = 0;
};

function reloadList(substring) {
    var username = JSON.parse(substring).username;
    var text = JSON.parse(substring).text;
    $("#refreshable").append("<ul><li>" + username + "<br/>" + text + "</li></ul>");
    updateScroll();
}

function changeFavicon(src) {
    var link = document.createElement('link'),
    oldLink = document.getElementById('dynamic-favicon');
    link.id = 'dynamic-favicon';
    link.rel = 'icon';
    link.href = src;
    if (oldLink) {
        document.head.removeChild(oldLink);
    }
    document.head.appendChild(link);
}

function updateScroll() {
    var element = document.getElementById('scroll');
    element.scrollTop = element.scrollHeight;
}

function Refresh() {
    window.location.reload(true);
}
