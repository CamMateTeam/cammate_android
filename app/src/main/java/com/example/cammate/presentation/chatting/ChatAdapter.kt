package com.example.cammate.presentation.chatting

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cammate.CammateApp
import com.example.cammate.R
import com.example.cammate.databinding.ItemMessageBinding
import com.example.cammate.network.model.Chat

class ChatAdapter(
    private var chats: List<Chat>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_ENTER = 0 // 입장했을 때
        private const val TYPE_LEFT = 1 // 떠났을 때
        private const val TYPE_MESSAGE_LEFT = 2 // 유저1(상대방)
        private const val TYPE_MESSAGE_RIGHT = 3 // 유저2(나)
    }

    override fun getItemViewType(position: Int): Int {
        val chat = chats[position]
        return when (chat.type) {
            "ENTER" -> TYPE_ENTER
            "LEFT" -> TYPE_LEFT
            "MESSAGE" -> if (chat.from != CammateApp.userName) TYPE_MESSAGE_LEFT else TYPE_MESSAGE_RIGHT
            else -> throw IllegalArgumentException("Invalid chat type")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_ENTER -> CenterMessageViewHolder(
                inflater.inflate(
                    R.layout.item_message,
                    parent,
                    false
                )
            )

            TYPE_LEFT -> CenterMessageViewHolder(
                inflater.inflate(
                    R.layout.item_message,
                    parent,
                    false
                )
            )

            TYPE_MESSAGE_LEFT -> SendMessageViewHolder(
                inflater.inflate(
                    R.layout.item_message,
                    parent,
                    false
                )
            )

            TYPE_MESSAGE_RIGHT -> RecieveMessageViewHolder(
                inflater.inflate(
                    R.layout.item_message,
                    parent,
                    false
                )
            )
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val chat = chats[position]
        when (holder) {
            is CenterMessageViewHolder -> holder.bind(chat)
            is SendMessageViewHolder -> holder.bind(chat)
            is RecieveMessageViewHolder -> holder.bind(chat)
        }
    }

    override fun getItemCount(): Int = chats.size

    fun update(list: ArrayList<Chat>) {
        this.chats = list
        notifyDataSetChanged()
    }

    class SendMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val messageView: TextView = itemView.findViewById(R.id.message)
        fun bind(chat: Chat) {
            messageView.text = chat.content
        }
    }

    class RecieveMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val messageView: TextView = itemView.findViewById(R.id.message)
        fun bind(chat: Chat) {
            messageView.text = chat.content
        }
    }


    class CenterMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val messageView: TextView = itemView.findViewById(R.id.message)
        fun bind(chat: Chat) {
            messageView.text = chat.content
        }
    }

}