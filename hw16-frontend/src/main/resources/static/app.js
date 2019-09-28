let stompClient = null;

const setConnected = (connected) => {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#usersList").show();
    }
    else {
        $("#usersList").hide();
    }
    $("#newUser").html("");
}

const connect = () => {
    stompClient = Stomp.over(new SockJS('/gs-guide-websocket'));
    stompClient.connect({}, (frame) => {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/response', (greeting) => showGreeting(JSON.parse(greeting.body).id, JSON.parse(greeting.body).name));
    });
}

const disconnect = () => {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

const sendName = () => stompClient.send("/app/user", {}, JSON.stringify({'name': $("#newUser").val()}))


const showGreeting = (id, name) => $("#usersList").append("<tr><td>" + id + "</td><td>" + name + "</td></tr>")


$(function () {
    $("form").on('submit', (event) => {
        event.preventDefault();
    });
    $("#connect").click(connect);
    $("#disconnect").click(disconnect);
    $("#send").click(sendName);
});