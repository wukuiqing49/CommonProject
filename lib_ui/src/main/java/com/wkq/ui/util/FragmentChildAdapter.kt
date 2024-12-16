package com.wkq.ui.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentChildAdapter public constructor(
    activity: FragmentActivity,
   var fragmentList: List<Fragment>
) : FragmentStateAdapter(activity.supportFragmentManager, activity.lifecycle) {

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return  fragmentList.get(position)
    }


}