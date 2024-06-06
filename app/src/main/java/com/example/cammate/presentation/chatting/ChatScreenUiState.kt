package com.example.cammate.presentation.chatting

data class ChatScreenUiState(
    var messages: List<ChatMessage> = listOf(),
    val userId: String = "",
    val message: String = "",
    val connectionStatus: ConnectionStatus = ConnectionStatus.NOT_STARTED
)
