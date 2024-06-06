package com.example.cammate.presentation.chatting

import com.tinder.scarlet.WebSocket
import io.reactivex.Flowable
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send

interface ChatService {
    @Receive
    fun observeConnection(): Flowable<WebSocket.Event>

    @Send
    fun sendMessage(message: String)
}