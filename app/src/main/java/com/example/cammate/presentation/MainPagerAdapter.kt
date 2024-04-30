package com.example.cammate

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cammate.presentation.viewer.find_room.FindRoomHostFragment
import com.example.cammate.presentation.viewer.make_room.MakeRoomHostFragment

class MainPagerAdapter(activity: FragmentActivity) :
    FragmentStateAdapter(activity) {

    var fragments: ArrayList<Fragment> = ArrayList()
    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MakeRoomHostFragment()
            1 -> FindRoomHostFragment()
            else -> MakeRoomHostFragment()
        }
    }

    fun addFragment(fragment: Fragment) {
        fragments.add(fragment)
        notifyItemInserted(fragments.size-1)
    }

    fun removeFragment() {
        fragments.removeLast()
        notifyItemRemoved(fragments.size)
    }

}