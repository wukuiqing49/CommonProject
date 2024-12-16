package com.wkq.ui.goods

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.wkq.ui.IndicatorActivity
import com.wkq.ui.databinding.ActivityGoods2Binding
import com.wkq.ui.databinding.ActivityGoodsBinding
import com.wkq.ui.util.FragmentChildAdapter

/**
 *@Desc:
 *
 *@Author: wkq
 *
 *@Time: 2024/12/16 9:39
 *
 */
class GoodsDetail2Activity:AppCompatActivity() {
    companion object{
        fun startActivity(content: Context){
            content.startActivity(Intent(content, GoodsDetail2Activity::class.java))
        }
    }
    lateinit var binding: ActivityGoods2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityGoods2Binding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        initView()

    }

    private fun initView() {
        val bannerInfoList= ArrayList<BannerInfo>()
        for (i in 0 until 10) {
            if (i==0){
                bannerInfoList.add(BannerInfo(0,"https://200024424.vod.myqcloud.com/200024424_709ae516bdf811e6ad39991f76a4df69.f20.mp4"))
            }else
            {
                bannerInfoList.add(BannerInfo(1,"https://th.bing.com/th/id/OIP.VzhOTC3SVqdVV48AhF5grwHaFS?rs=1&pid=ImgDetMain"))
            }

        }

        val fragmentList =ArrayList<Fragment>()
        bannerInfoList.forEach {fragmentList.add(VideoAndImageFragment.genInstance(it.type,it.url))
        }

        val adapter= FragmentChildAdapter(this,fragmentList)
        binding.vpContent.adapter=adapter

        val goodsList= ArrayList<GoodsInfo>()
//       goodsList.add(GoodsInfo(TYPE_VIDEO,bannerInfoList,""))
        goodsList.add(GoodsInfo(TYPE_GOODS,null,"1"))
        goodsList.add(GoodsInfo(TYPE_GOODS,null,"1"))
        goodsList.add(GoodsInfo(TYPE_GOODS,null,"2"))
        goodsList.add(GoodsInfo(TYPE_GOODS,null,"3"))
        goodsList.add(GoodsInfo(TYPE_GOODS,null,"4"))
        goodsList.add(GoodsInfo(TYPE_GOODS,null,"5"))
        goodsList.add(GoodsInfo(TYPE_GOODS,null,"6"))
        goodsList.add(GoodsInfo(TYPE_GOODS,null,"6"))
        goodsList.add(GoodsInfo(TYPE_GOODS,null,"6"))
        goodsList.add(GoodsInfo(TYPE_GOODS,null,"6"))
        goodsList.add(GoodsInfo(TYPE_GOODS,null,"6"))
        goodsList.add(GoodsInfo(TYPE_GOODS,null,"6"))
        goodsList.add(GoodsInfo(TYPE_GOODS,null,"6"))
        goodsList.add(GoodsInfo(TYPE_GOODS,null,"6"))
        goodsList.add(GoodsInfo(TYPE_GOODS,null,"6"))
        goodsList.add(GoodsInfo(TYPE_GOODS,null,"6"))
        goodsList.add(GoodsInfo(TYPE_GOODS,null,"6"))
        goodsList.add(GoodsInfo(TYPE_GOODS,null,"6"))
        goodsList.add(GoodsInfo(TYPE_GOODS,null,"6"))
        goodsList.add(GoodsInfo(TYPE_GOODS,null,"6"))
        goodsList.add(GoodsInfo(TYPE_GOODS,null,"6"))
        goodsList.add(GoodsInfo(TYPE_GOODS,null,"6"))
        goodsList.add(GoodsInfo(TYPE_GOODS,null,"6"))
        goodsList.add(GoodsInfo(TYPE_GOODS,null,"6"))
        goodsList.add(GoodsInfo(TYPE_GOODS,null,"6"))
        goodsList.add(GoodsInfo(TYPE_GOODS,null,"6"))
        goodsList.add(GoodsInfo(TYPE_GOODS,null,"6"))
        goodsList.add(GoodsInfo(TYPE_GOODS,null,"6"))
        goodsList.add(GoodsInfo(TYPE_GOODS,null,"6"))
        goodsList.add(GoodsInfo(TYPE_GOODS,null,"6"))
        goodsList.add(GoodsInfo(TYPE_GOODS,null,"6"))
        goodsList.add(GoodsInfo(TYPE_GOODS,null,"6"))
        goodsList.add(GoodsInfo(TYPE_GOODS,null,"6"))
        goodsList.add(GoodsInfo(TYPE_GOODS,null,"6"))
        goodsList.add(GoodsInfo(TYPE_GOODS,null,"6"))
        goodsList.add(GoodsInfo(TYPE_GOODS,null,"6"))
        goodsList.add(GoodsInfo(TYPE_GOODS,null,"6"))
        binding.rvContent.layoutManager= LinearLayoutManager(this)
        val  mAdapter=GoodsDetailAdapter(this)
        binding.rvContent.adapter=mAdapter
        mAdapter.addData(goodsList)

    }

}