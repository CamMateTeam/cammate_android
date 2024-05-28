package com.example.cammate.find_room

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cammate.R


class EnterRoomFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_enter_room, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

/*        view.findViewById<Button>(R.id.btn).setOnClickListener{
           *//* try {
                //findNavController().navigate(R.id.action_enterRoomFragment_to)
            } catch (e: Exception){
                //Log.d("tag", "${e.message}")
            }*//*
            val dialog = NetworkDialog()
            dialog.show((activity as AppCompatActivity).supportFragmentManager, "NetworkDialog")
        }*/
    }
}