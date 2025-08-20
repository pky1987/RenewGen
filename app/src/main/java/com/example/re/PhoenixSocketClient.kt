package com.example.re

import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI

class PhoenixSocketClient(serverUri: URI) : WebSocketClient(serverUri) {

    var onMessageReceived: ((String) -> Unit)? = null

    override fun onOpen(handshakedata: ServerHandshake?) {
        send("{\"topic\":\"chat_channel:lobby\",\"event\":\"phx_join\",\"payload\":{},\"ref\":\"1\"}")
    }

    override fun onMessage(message: String?) {
        message?.let {
            onMessageReceived?.invoke(it)
        }
    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
        println("WebSocket closed. Code: $code Reason: $reason")
    }

    override fun onError(ex: Exception?) {
        ex?.printStackTrace()
    }
}
