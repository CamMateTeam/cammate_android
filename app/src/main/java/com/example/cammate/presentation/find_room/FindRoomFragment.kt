package com.example.cammate.presentation.find_room

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cammate.R
import com.example.cammate.databinding.FragmentFindRoomBinding
import com.example.cammate.presentation.viewer.find_room.adapter.CammateViewHolder
import com.example.cammate.presentation.viewer.find_room.adapter.CammatesAdapter
import com.example.cammate.presentation.viewer.find_room.adapter.CammatesItem

class FindRoomFragment : Fragment() {
    private var _binding: FragmentFindRoomBinding? = null
    private val binding get() = _binding!!
    private val cammates = listOf( // 더미데이터
        CammatesItem("익명의 오소리 1"),
        CammatesItem("익명의 오소리 2"),
        CammatesItem("익명의 오소리 3"),
        CammatesItem("익명의 오소리 4"),
        CammatesItem("익명의 오소리 5"),
        CammatesItem("익명의 오소리 6"),
        CammatesItem("익명의 오소리 7"),
        CammatesItem("익명의 오소리 8"),
        CammatesItem("익명의 오소리 9"),
        CammatesItem("익명의 오소리 10"),
    )
    private val listAdapter =  CammatesAdapter(cammates)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFindRoomBinding.inflate(inflater, container, false)

        initRecyclerView()

/*        listAdapter.setItemClickListener(object: ListAdapter.OnItemClickListener{
            override fun onClick(v: View, position: Int) {
                // 클릭 시 이벤트 작성
                Toast.makeText(view?.context,
                    "${cammates[position]}",
                    Toast.LENGTH_SHORT).show()
            }
        })*/

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tmp = "tempData"
        binding.btnFindRoomNext.setOnClickListener {
            val sendData = Bundle().also {
                it.putString("roomName", tmp)
            }
            findNavController().navigate(R.id.action_findRoomFragment_to_enterRoomFragment, sendData)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecyclerView() {
        binding.rvCammateList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvCammateList.adapter = CammatesAdapter(cammates)
    }
}