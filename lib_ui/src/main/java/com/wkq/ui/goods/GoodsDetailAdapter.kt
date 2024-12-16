package com.wkq.ui.goods

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.qian.du.base.module.adapter.BaseRvTypeAdapter
import com.qian.du.base.module.adapter.BaseViewTypeHolder
import com.wkq.ui.databinding.ItemGoodsImageVideoBinding
import com.wkq.ui.databinding.ItemGoodsInfoBinding
import com.wkq.ui.util.FragmentAdapter
import com.wkq.ui.util.FragmentChildAdapter

/**
 *@Desc:
 *
 *@Author: wkq
 *
 *@Time: 2024/12/16 11:47
 *
 */
class GoodsDetailAdapter(context:Context): BaseRvTypeAdapter<GoodsInfo>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewTypeHolder {
        when (viewType) {
            TYPE_VIDEO -> {
                return  BaseViewTypeHolder(ItemGoodsImageVideoBinding.inflate(LayoutInflater.from(mContext),parent,false))
            }
            else -> {
                return  BaseViewTypeHolder(ItemGoodsInfoBinding.inflate(LayoutInflater.from(mContext),parent,false))
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewTypeHolder, position: Int) {

        when (getItemViewType(position)) {
            TYPE_VIDEO -> {
                processVideo(holder,   getBean(position))
            }
            else -> {
                processGoods(holder,   getBean(position))
            }
        }
    }

    private fun processGoods(holder: BaseViewTypeHolder, bean: GoodsInfo) {
        val binding= holder.binding as ItemGoodsInfoBinding
        binding.tvContent.text=bean.content
    }

    private fun processVideo(holder: BaseViewTypeHolder, bean: GoodsInfo) {
        //禁止复用
        holder.setIsRecyclable(false)
       val binding= holder.binding as ItemGoodsImageVideoBinding
        val fragmentList =ArrayList<Fragment>()
        bean.bannerList?.forEach {fragmentList.add(VideoAndImageFragment.genInstance(it.type,it.url))
        }

       val adapter= FragmentChildAdapter(mContext as FragmentActivity,fragmentList)
        binding.vpContent.adapter=adapter
    }


    override fun getItemViewType(position: Int): Int {
        return getBean(position).type
    }
}