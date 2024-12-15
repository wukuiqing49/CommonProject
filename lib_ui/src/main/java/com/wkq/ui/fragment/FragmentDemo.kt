package com.wkq.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wkq.ui.databinding.FragmentDemoBinding

class FragmentDemo:Fragment() {

    companion object{
        fun genInstance(title:String):FragmentDemo{
            val fragment=FragmentDemo()
            val bundle=Bundle()
            bundle.putString("title",title)
            fragment.arguments=bundle
            return fragment
        }
    }
    lateinit var  binding:FragmentDemoBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentDemoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val title=arguments?.getString("title")
        binding.tvTitle.text=title
    }
}