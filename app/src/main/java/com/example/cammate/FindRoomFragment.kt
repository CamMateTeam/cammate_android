package com.example.cammate

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cammate.databinding.FragmentFindRoomBinding

class FindRoomFragment : Fragment() {

    private var _binding: FragmentFindRoomBinding? = null
    private val binding get() = _binding!!
    private val cammates = listOf(
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFindRoomBinding.inflate(inflater, container, false)

        initRecyclerView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnFindRoomNext.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
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