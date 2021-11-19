package com.wu.third.classification

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wu.base.adapter.KtAdapter
import com.wu.base.base.adapter.KtDataBindingViewHolder
import com.wu.base.util.DeviceUtils
import com.wu.base.util.ScreenUtils
import com.wu.third.R
import com.wu.third.databinding.ItemClassficationContentImgBinding
import com.wu.third.databinding.LayoutClassficationTagBinding


/**
 * @author wkq
 *
 * @date 2021年11月18日 14:55
 *
 *@des  右侧内容子图片的Adapter
 *
 */

class ClassificationImgsAdapter(mContext: Context):KtAdapter<String>(mContext) {


    fun getHeight(): Int {
        return (ScreenUtils.getScreenWidth(mContext) -DeviceUtils.dip2px(mContext,100))/ 4
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var binding=DataBindingUtil.inflate<ItemClassficationContentImgBinding>(LayoutInflater.from(mContext),
            R.layout.item_classfication_content_img,parent,false)
        var layout = RelativeLayout.LayoutParams(getHeight(), getHeight())
        layout.setMargins(5, 5, 5, 5)
        binding.root.layoutParams = layout
        var holder=KtDataBindingViewHolder(binding.root)
        holder.binding=binding
        return  holder
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
       var bindingHolder=holder as KtDataBindingViewHolder
      var binding=  bindingHolder.binding as ItemClassficationContentImgBinding

        Glide.with(mContext).load(getItem(position)).into(binding.ivContent)
    }



}