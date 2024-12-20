package com.wu.third.classification

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wu.base.adapter.KtAdapter
import com.wu.base.base.adapter.KtDataBindingViewHolder
import com.wu.third.R
import com.wu.third.databinding.ItemClassificationContentBinding


/**
 * @author wkq
 *
 * @date 2021年11月18日 15:03
 *
 *@des  右侧内容的Adapter
 *
 */

class ClassificationContentAdapter(mContext: Context) :
    KtAdapter<ClassificationContentInfo>(mContext) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var binding = ItemClassificationContentBinding.inflate(LayoutInflater.from(mContext),parent,false)
        var holder = KtDataBindingViewHolder(binding.root)

        return holder
    }
    var binding:ItemClassificationContentBinding?=null
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var bindingHolder = holder as KtDataBindingViewHolder
         binding = bindingHolder.binding as ItemClassificationContentBinding
        binding!!.tvTitle.text = getItem(position)!!.title
        binding!!.rvImgs.layoutManager = GridLayoutManager(mContext, 4)
        var adapter = ClassificationImgsAdapter(mContext)
        binding!!.rvImgs.adapter = adapter
        adapter.addItems(getItem(position)!!.imgs)
    }

    fun setNewData(newDatas:ArrayList<ClassificationContentInfo>){
        removeAllItems()
        itemList=newDatas
        notifyDataSetChanged()
        binding!!.rvImgs.scrollToPosition(0)
    }


}