package com.wkq.ui.goods

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.wkq.ui.IndicatorActivity
import com.wkq.ui.databinding.ActivityGoodsBinding

/**
 *@Desc:
 *
 *@Author: wkq
 *
 *@Time: 2024/12/16 9:39
 *
 */
class GoodsDetailActivity:AppCompatActivity() {
    companion object{
        fun startActivity(content: Context){
            content.startActivity(Intent(content, GoodsDetailActivity::class.java))
        }
    }
    lateinit var binding: ActivityGoodsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityGoodsBinding.inflate(LayoutInflater.from(this))
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
        val goodsList= ArrayList<GoodsInfo>()
       goodsList.add(GoodsInfo(TYPE_VIDEO,bannerInfoList,""))
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