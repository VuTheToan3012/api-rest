// var _webSocket;

var WEBSOCKET_MANAGER = {};

WEBSOCKET_MANAGER.webSocket;
WEBSOCKET_MANAGER.webSocketConnected = false;

WEBSOCKET_MANAGER.connectWebSocketServer = function () {
    WEBSOCKET_MANAGER.webSocket = new WebSocket(SOCKET_URL);

    WEBSOCKET_MANAGER.webSocket.onopen = async function (e) {
        WEBSOCKET_MANAGER.webSocketConnected = true;
        console.log("WebSocket OnOpened");

    };

    WEBSOCKET_MANAGER.webSocket.onmessage = async function (event) {
        const responseMessage = JSON.parse(event.data);
        if (responseMessage.result === "ERROR") {
            // send log if error
            //
            console.log("Websocket Error:" + responseMessage.message);
        } else {
            const repsonseData = responseMessage.responseData;
            switch (responseMessage.protocol){
                case WEBSOCKET_PROTOCOL.CIDS_MONITOR_OPERATION_PRTSCN:
                    // all target element set id name is screenshotData
                    if(repsonseData?.screenshotData && repsonseData.screenshotData.includes("data:image/png;base64")){
                        $("#screenshotData").show();
                        $("#screenshotData").attr("src", responseMessage.responseData?.screenshotData);
                    }else{
                        $("#screenshotData").hide();
                    }
                    break;
            }
        }
    };

    WEBSOCKET_MANAGER.webSocket.onerror = function (e) {
        WEBSOCKET_MANAGER.webSocketConnected = false;
        // send log if error
        console.log("WebSocket onError : " + e);
    };

    WEBSOCKET_MANAGER.webSocket.onclose = function (e) {
        WEBSOCKET_MANAGER.webSocketConnected = false;
        console.log("WebSocket onClose : " + e);
        console.log("Try again in 5 seconds.");
        // 5초 후에 다시 재연결
        setTimeout(
            WEBSOCKET_MANAGER.connectWebSocketServer,
            5000
        );
    };
};

WEBSOCKET_MANAGER.isWebSocketConnected = function () {
    return WEBSOCKET_MANAGER.webSocketConnected;
};

/**
 * Initialize WebSocket Client
 */
function initializeWebSocketClient() {
    WEBSOCKET_MANAGER.connectWebSocketServer();
}

function generateTransactionId() {
    // Get the current timestamp
    var timestamp = new Date().getTime();

    // Generate a random number between 1 and 100
    var randomNum = Math.floor(Math.random() * 100) + 1;

    // Combine the timestamp and random number
    var transactionId = timestamp + "-" + randomNum;

    return transactionId;
}

/**
 * The function `wsSendMessage` sends a WebSocket message with the specified protocol, data, and
 * monitorId.
 * @param protocol - The protocol parameter is the type of protocol to be used for the WebSocket
 * connection. It can be either "ws" for WebSocket or "wss" for WebSocket Secure.
 * @param data - The `data` parameter is the payload or message that you want to send over the
 * WebSocket connection. It can be any valid JSON object or string that you want to transmit to the
 * server.
 * @param monitorId - The `monitorId` parameter is used to identify the specific monitor or client that
 * the message is being sent to. It could be an identifier or a unique value that helps in routing the
 * message to the correct recipient.
 */
function wsSendMessage(protocol, data, monitorId){
    const params = {
        protocol,
        transactionId: generateTransactionId(),
        monitorId,
        requestData: data
    }
    WEBSOCKET_MANAGER.webSocket.send(JSON.stringify(params));
    console.log("Websocket send message to: "+protocol);
}

/**
 * The function `wsSendResponseMessage` sends a response message over a WebSocket connection with the
 * specified protocol, data, monitorId, result, and message.
 * @param protocol - The protocol parameter is a string that represents the communication protocol
 * being used, such as "HTTP" or "WebSocket".
 * @param data - The `data` parameter is the response data that you want to send back to the client. It
 * can be any type of data, such as a string, object, or array.
 * @param monitorId - The `monitorId` parameter is used to identify the specific monitor or client that
 * the response message is being sent to. It could be an identifier or a unique value associated with
 * the monitor or client.
 * @param result - The "result" parameter is used to indicate the result of the response message. It
 * can be a boolean value, a string, or any other data type that represents the result of the
 * operation.
 * @param message - The `message` parameter is a string that represents a message or description
 * related to the response being sent. It can be used to provide additional information or context
 * about the response.
 */
function wsSendResponseMessage(protocol, data, transactionId, result, message) {
    const params = {
        protocol,
        transactionId: transactionId ?? generateTransactionId(),
        result,
        message,
        responseData: data
    }
    WEBSOCKET_MANAGER.webSocket.send(JSON.stringify(params));
    console.log("send socket with protocol:", protocol + " and transactionId: "+ transactionId);
}