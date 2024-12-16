package com.wkq.ui.goods

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.bumptech.glide.Glide
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player.REPEAT_MODE_ONE
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout.RESIZE_MODE_FIXED_HEIGHT
import com.wkq.ui.databinding.FragmentGoodsTopBinding

class VideoAndImageFragment:Fragment() {

    companion object{
        fun genInstance(type:Int,title:String):VideoAndImageFragment{
            val fragment=VideoAndImageFragment()
            val bundle=Bundle()
            bundle.putString("url",title)
            bundle.putInt("type",type)
            fragment.arguments=bundle
            return fragment
        }
    }
    lateinit var  binding:FragmentGoodsTopBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentGoodsTopBinding.inflate(inflater,container,false)
        return binding.root
    }
    var player:ExoPlayer?=null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val title=arguments?.getString("url")
        val type=arguments?.getInt("type")
        if (type== 0){
            binding.video.visibility=View.VISIBLE
            player = ExoPlayer.Builder(requireActivity()).build()
            player!!.repeatMode=REPEAT_MODE_ONE

            binding.video.setUseController(false)
//            binding.video.resizeMode=(AspectRatioFrameLayout.RESIZE_MODE_FIXED_HEIGHT)
//            binding.video.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);

            binding.video.player=player
            val mediaItem: MediaItem = MediaItem.fromUri("https://200024424.vod.myqcloud.com/200024424_709ae516bdf811e6ad39991f76a4df69.f20.mp4")
            player!!.setMediaItem(mediaItem)
            player!!.prepare()
            player!!.play()

//            binding.ivCover.visibility=View.VISIBLE
//            Glide.with(this).load("https://allanshaws.co.uk/wp-content/uploads/2016/08/DSCN2249.jpg").into(binding.ivCover)
        }else{
            binding.ivCover.visibility=View.VISIBLE
            Glide.with(this).load("https://allanshaws.co.uk/wp-content/uploads/2016/08/DSCN2249.jpg").into(binding.ivCover)
        }





    }

    override fun onPause() {
        super.onPause()
        if (player!=null){
            player!!.pause()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (player!=null){
            player!!.stop()
            player!!.release()
        }

    }
}