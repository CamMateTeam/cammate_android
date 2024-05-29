package com.example.cammate.presentation.find_room

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cammate.R
import com.example.cammate.databinding.FragmentFindRoomBinding
import com.example.cammate.network.data.CammateRoomList
import com.example.cammate.network.data.config.RetrofitBuilder
import com.example.cammate.presentation.viewer.find_room.adapter.CammatesAdapter
import com.example.cammate.presentation.viewer.find_room.adapter.CammatesItem
import kotlinx.coroutines.launch

class FindRoomFragment : Fragment() {
    private var _binding: FragmentFindRoomBinding? = null
    private val binding get() = _binding!!
    private val cammates = mutableListOf( // 더미데이터
        CammatesItem("익명의 오소리 1"),
        CammatesItem("익명의 오소리 2"),
        CammatesItem("익명의 오소리 3"),
        CammatesItem("익명의 오소리 4"),
        CammatesItem("익명의 오소리 5"),
        CammatesItem("익명의 오소리 6"),
        CammatesItem("익명의 오소리 7"),
        CammatesItem("익명의 오소리 8"),
        CammatesItem("익명의 오소리 9"),
    )

    // retrofit으로 불러오는 애들
    lateinit private var cammatelist: CammateRoomList

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFindRoomBinding.inflate(inflater, container, false)

        val macAddress = "1.1.1.1" // 맥주소 리스트

        lifecycleScope.launch {
            try {
                val response = RetrofitBuilder.api.getRooms(macAddress)
                Log.d("FindRoomFragment", "Room Response: $response")
                for (room in response.data) {
                    cammates.add(CammatesItem(room.nickname))
                }
                Log.d("FindRoomFragment", "cammmate list : $cammates")
                initRecyclerView()
            } catch (e: Exception) {
                Log.e("FindRoomFragment", "Error: ${e.message}")
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnFindRoomNext.setOnClickListener {
            val bundle = bundleOf("checked_cammate" to "익명의 고양이 1")
            findNavController().navigate(R.id.action_findRoomFragment_to_enterRoomFragment, bundle)
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