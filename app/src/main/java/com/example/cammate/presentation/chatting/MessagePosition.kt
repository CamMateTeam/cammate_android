package com.example.cammate.presentation.chatting

enum class MessagePosition {
    LEFT, RIGHT
}

enum class MessageType(val index : Int){
    CHAT_MINE(0),CHAT_PARTNER(1),USER_JOIN(2),USER_LEAVE(3);
}