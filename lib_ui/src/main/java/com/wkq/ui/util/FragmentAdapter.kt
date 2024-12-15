package com.wkq.ui.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentAdapter(activity:FragmentActivity, var fragmentList:List<Fragment>):FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return  fragmentList.get(position)
    }


}