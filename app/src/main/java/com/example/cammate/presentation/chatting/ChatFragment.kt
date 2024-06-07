package com.example.cammate.presentation.chatting

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cammate.databinding.FragmentChattingBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.gson.Gson
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONException
import org.json.JSONObject

class ChatFragment : BottomSheetDialogFragment() {

    val TAG = FragmentChattingBottomSheetBinding::class.java.simpleName

    lateinit var binding: FragmentChattingBottomSheetBinding

    lateinit var mSocket: Socket
    lateinit var userName: String
    lateinit var roomName: String


    val gson: Gson = Gson()

    //For setting the recyclerView.
    val chatList: ArrayList<Message> = arrayListOf()
    lateinit var chatRoomAdapter: ChatRoomAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChattingBottomSheetBinding.inflate(inflater, container, false)

        //Get the nickname and roomname from entrance activity.
        /*try {
            userName = intent.getStringExtra("userName")!!
            roomName = intent.getStringExtra("roomName")!!
            Log.d("chat", "${userName}")
        } catch (e: Exception) {
            e.printStackTrace()
        }*/

        //Set Chatroom adapter

        chatRoomAdapter = ChatRoomAdapter(requireActivity(), chatList)
        binding.rvChatting.adapter = chatRoomAdapter

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvChatting.layoutManager = layoutManager

        //Let's connect to our Chat room! :D
        try {
            mSocket = IO.socket("http://10.0.2.2:3001/")
            mSocket.connect()
            if (mSocket.connected()) {
                Log.d(TAG, "서버에 연결되었습니다.")
            } else {
                Log.d(TAG, "서버에 연결되어 있지 않습니다.")
            }

            // 서버로부터 메시지 수신
            mSocket.on("SEND") { args ->
                activity?.runOnUiThread {
                    val data = args[0] as String
                    try {
                        // val message = data.getString("message")
                        // 메시지를 TextView에 표시
                        addItemToRecyclerView(Message(roomName="", userName = "", viewType = 1, messageContent = data))
                    } catch (e: Exception) {
                        Log.d("chat", "개빡침 $e")
                    }
                }
            }

        } catch (e: Exception) {
            Log.d("chat", "Failed to connect : ${e}")
        }

        // mSocket?.connect()
        // mSocket?.on("SEND", onNewMessage)


        binding.send.setOnClickListener {
            val message = binding.editText.text.toString().trim()
            if (message.isNotEmpty()) {
                sendMessage(message)
                binding.editText.setText("")
            }
        }

        /*mSocket.on(Socket.EVENT_CONNECT, onConnect)
        mSocket.on("newUserToChatRoom", onNewUser)
        mSocket.on("updateChat", onUpdateChat)
        mSocket.on("userLeftChatRoom", onUserLeft)*/

        return binding.root
    }

    private val onNewMessage: Emitter.Listener = Emitter.Listener { args ->
        activity?.runOnUiThread {
            val data = args[0] as JSONObject
            var message: String
            try {
                message = data.getString("message")
                addItemToRecyclerView(Message(roomName="", userName = "", viewType = 1, messageContent = message))
            } catch (e: JSONException) {
                Log.d("chat", "뭔데 이게")
                return@runOnUiThread
            }
        }
    }

    private fun sendMessage(message: String) {
        //val data = JSONObject()
        var data = ""
        try {
            data = message
            //data.put("msg", message)
        } catch (e: JSONException) {
            Log.d("chat", "falied")
            return
        }
        mSocket?.emit("SEND", data)
        addItemToRecyclerView(Message(roomName="", userName = "", viewType = 0, messageContent = message))
    }

    var onUserLeft = Emitter.Listener {
        val leftUserName = it[0] as String
        val chat: Message = Message(leftUserName, "", "", MessageType.USER_LEAVE.index)
        addItemToRecyclerView(chat)
    }

    var onUpdateChat = Emitter.Listener {
        val chat: Message = gson.fromJson(it[0].toString(), Message::class.java)
        chat.viewType = MessageType.CHAT_PARTNER.index
        addItemToRecyclerView(chat)
    }

    var onConnect = Emitter.Listener {
        val data = initialData(userName, roomName)
        val jsonData = gson.toJson(data)
        mSocket.emit("subscribe", jsonData)

    }

    var onNewUser = Emitter.Listener {
        val name = it[0] as String //This pass the userName!
        val chat = Message(name, "", roomName, MessageType.USER_JOIN.index)
        addItemToRecyclerView(chat)
        Log.d(TAG, "on New User triggered.")
    }


    /*private fun sendMessage() {
        val content = binding.editText.text.toString()
        val sendData = SendMessage(userName, content, roomName)
        val jsonData = gson.toJson(sendData)
        mSocket.emit("SEND", jsonData)

        val message = Message(userName, content, roomName, MessageType.CHAT_MINE.index)
        addItemToRecyclerView(message)
    }*/

    private fun addItemToRecyclerView(message: Message) {
        //Since this function is inside of the listener,
        // You need to do it on UIThread!
        activity?.runOnUiThread {
            chatList.add(message)
            chatRoomAdapter.notifyItemInserted(chatList.size)
            binding.editText.setText("")
            binding.rvChatting.scrollToPosition(chatList.size - 1) //move focus on last message
        }
    }


    /*override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.leave -> onDestroy()
        }
    }*/

    override fun onDestroy() {
        super.onDestroy()
        if (::userName.isInitialized && ::roomName.isInitialized) {
            val data = initialData(userName, roomName)
            val jsonData = gson.toJson(data)
            mSocket.emit("unsubscribe", jsonData)
            mSocket.disconnect()
        }
    }

}