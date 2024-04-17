package com.example.cammate

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cammate.find_room.ContainerFragment
import com.example.cammate.make_room.MakeRoomFragment

class MainPagerAdapter(activity: FragmentActivity) :
    FragmentStateAdapter(activity) {

    var fragments: ArrayList<Fragment> = ArrayList()
    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MakeRoomFragment()
            1 -> ContainerFragment()
            else -> MakeRoomFragment()
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