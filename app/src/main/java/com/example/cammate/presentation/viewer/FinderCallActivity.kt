package com.example.cammate.presentation.viewer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cammate.R
import com.example.cammate.databinding.ActivityFinderCallBinding
import com.example.cammate.databinding.ActivityMakerCallBinding
import com.example.cammate.presentation.chatting.ChatFragment
import com.example.cammate.webRTC.Models.IceCandidateModel
import com.example.cammate.webRTC.Models.MessageModel
import com.example.cammate.webRTC.RTCClient
import com.example.cammate.webRTC.SocketRepository
import com.example.cammate.webRTC.utils.NewMessageInterface
import com.example.cammate.webRTC.utils.PeerConnectionObserver
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.gson.Gson
import org.webrtc.IceCandidate
import org.webrtc.MediaStream
import org.webrtc.SessionDescription

class FinderCallActivity : AppCompatActivity(), NewMessageInterface {
    lateinit var binding: ActivityFinderCallBinding
    private var roomName:String?=null
    private var userName:String?=null
    private var socketRepository: SocketRepository?=null
    private var rtcClient : RTCClient?=null
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinderCallBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userName = intent.getStringExtra("UserName")
        userName += "01"
        roomName = intent.getStringExtra("TargetName")
        roomName += "01"
        init()

        binding.btnChatting.setOnClickListener{
            try {
                val bottomsheet: BottomSheetDialogFragment = ChatFragment()
                bottomsheet.show(supportFragmentManager, "bottomsheet")
            } catch (e: Exception){
                Log.d("tag", "${e}")
            }
        }
    }



    private fun init(){
        socketRepository = SocketRepository(this)
        userName?.let { socketRepository?.initSocket(it) }
        rtcClient = RTCClient(application, userName!!,socketRepository!!, object : PeerConnectionObserver() {
            override fun onIceCandidate(p0: IceCandidate?) {
                super.onIceCandidate(p0)
                rtcClient?.addIceCandidate(p0)
                val candidate = hashMapOf(
                    "sdpMid" to p0?.sdpMid,
                    "sdpMLineIndex" to p0?.sdpMLineIndex,
                    "sdpCandidate" to p0?.sdp
                )

                socketRepository?.sendMessageToSocket(
                    MessageModel("ice_candidate",userName,roomName,candidate)
                )

            }

            override fun onAddStream(p0: MediaStream?) {
                super.onAddStream(p0)
                p0?.videoTracks?.get(0)?.addSink(binding.remoteViewF)
                var tmp = p0?.videoTracks?.get(0)
                //Log.d(TAG, "onAddStream: $p0")
                Log.d("TAG", " MediaStream id : $p0 videoid : $tmp")

            }
        })

/*        socketRepository?.sendMessageToSocket(
            MessageModel(
            "start_call",userName,roomName,null
        )
        )*/
    }

    override fun onNewMessage(message: MessageModel) {
        when(message.type){

            "answer_received" ->{
                val session = SessionDescription(
                    SessionDescription.Type.ANSWER,
                    message.data.toString()
                )
                rtcClient?.onRemoteSessionReceived(session)
                Log.d("answer_received", "answer_received: session : $session")
            }
            "offer_received" ->{ // 전화 받는 사람
                runOnUiThread {
                    rtcClient?.initializeSurfaceView(binding.remoteViewF)
                    binding.remoteViewLoading.visibility = View.GONE
                }

                val session = SessionDescription(
                    SessionDescription.Type.OFFER,
                    message.data.toString()
                )
                rtcClient?.onRemoteSessionReceived(session)
                rtcClient?.answer(message.name!!)
                roomName = message.name!!
                Log.d("offer_Received", "offer_received")
                }


            "ice_candidate"->{
                try {
                    val receivingCandidate = gson.fromJson(gson.toJson(message.data),
                        IceCandidateModel::class.java)
                    rtcClient?.addIceCandidate(IceCandidate(receivingCandidate.sdpMid,
                        Math.toIntExact(receivingCandidate.sdpMLineIndex.toLong()),receivingCandidate.sdpCandidate))
                }catch (e:Exception){
                    e.printStackTrace()
                }
                Log.d("ice_Candidate", "ice_candidate")
            }
        }
    }
}