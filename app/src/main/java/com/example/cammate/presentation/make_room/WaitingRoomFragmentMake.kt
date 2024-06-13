package com.example.cammate.presentation.make_room

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cammate.databinding.FragmentWaitingRoomMakeBinding
import com.example.cammate.presentation.viewer.MakerCallActivity
import com.example.cammate.webRTC.Models.IceCandidateModel
import com.example.cammate.webRTC.Models.MessageModel
import com.example.cammate.webRTC.RTCClient
import com.example.cammate.webRTC.SocketRepository
import com.example.cammate.webRTC.utils.NewMessageInterface
import com.example.cammate.webRTC.utils.PeerConnectionObserver
import com.google.gson.Gson
import org.webrtc.IceCandidate
import org.webrtc.MediaStream
import org.webrtc.SessionDescription


class WaitingRoomFragmentMake : Fragment(), NewMessageInterface {
    private var _binding: FragmentWaitingRoomMakeBinding? = null
    private val binding get() = _binding!!
    private var roomName:String?=null
    private var userName:String?=null
    private var socketRepository: SocketRepository?=null
    private var rtcClient : RTCClient?=null
    private val gson = Gson()
    private var condition:String?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWaitingRoomMakeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userName = arguments?.getString("nickname")
        val password = arguments?.getString("password")
        binding.waitingTextMake.text = "비밀번호 : $password \n $userName 님의 방에 사람이 들어올 때까지 대기중입니다"
        init()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun init(){
        socketRepository = SocketRepository(this)
        userName?.let { socketRepository?.initSocket(it) }
        rtcClient = RTCClient(requireActivity().application, userName!!,socketRepository!!, object : PeerConnectionObserver() {
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
                //p0?.videoTracks?.get(0)?.addSink(binding.remoteView)
                var tmp = p0?.videoTracks?.get(0)
                //Log.d(TAG, "onAddStream: $p0")
                Log.d("TAG", " MediaStream id : $p0 videoid : $tmp")

            }

        })
/*        socketRepository?.sendMessageToSocket(MessageModel(
            "start_call",userName,roomName,null
        ))*/

    }



    override fun onNewMessage(message: MessageModel) {
        when(message.type){

/*            "call_response"->{ //전화 거는 사람
                if (message.data == "user is not online"){
                    //user is not reachable
                    Toast.makeText(requireActivity(),"user is not reachable", Toast.LENGTH_LONG).show()

                }else{
                    //we are ready for call, we started a call
                    runOnUiThread {
                        setWhoToCallLayoutGone()
                        setCallLayoutVisible()
                        binding.apply {
                            //rtcClient?.initializeSurfaceView(localView)
                            rtcClient?.initializeSurfaceView(remoteView)
                            rtcClient?.startLocalVideo(remoteView) //remoteView로 바꿔야
                            rtcClient?.startAddStream()
                            rtcClient?.call(targetUserNameEt.text.toString())
                        }


                    }

                }
            }*/
            "answer_received" ->{
                val session = SessionDescription(
                    SessionDescription.Type.ANSWER,
                    message.data.toString()
                )
                rtcClient?.onRemoteSessionReceived(session)
                Log.d("answer_received", "answer_received: session : $session")
            }
            "offer_received" ->{ // 전화 받는 사람

/*              val dialog = CallReceivedDialog()
                dialog.show((activity as AppCompatActivity).supportFragmentManager, "CallReceivedDialog")*/


                val session = SessionDescription(
                    SessionDescription.Type.OFFER,
                    message.data.toString()
                )
                rtcClient?.onRemoteSessionReceived(session)
                rtcClient?.answer(message.name!!)
                roomName = message.name!!
                Log.d("offer_Received", "offer_received")
                requireActivity().runOnUiThread{
                    // 원래 글자 안 보이게
                    binding.waitingTextMake.visibility = View.GONE
                    binding.loadingBarMake.visibility = View.GONE

                    // 알림창 뜨게
                    binding.incomingCallLayout.visibility = View.VISIBLE

                    // 거절 시
                    binding.rejectButton.setOnClickListener {
                        binding.incomingCallLayout.visibility = View.GONE
                        // incomingCallLayout invisible
                    }

                    // 수락 시
                    binding.acceptButton.setOnClickListener {
                        // find waitfragment한테 함수 보내서 (find에서 함수값 체크하고? intent로 callActivity로 넘어가)
                        val intent = Intent(getActivity(), MakerCallActivity::class.java)
                        intent.putExtra("UserName",userName)
                        intent.putExtra("TargetName",roomName)
                        startActivity(intent)
                    }
                }

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