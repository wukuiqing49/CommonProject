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
 *@des
 *
 */

class ClassificationContentAdapter(mContext: Context) :
    KtAdapter<ClassificationContentInfo>(mContext) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var binding = DataBindingUtil.inflate<ItemClassificationContentBinding>(
            LayoutInflater.from(mContext),
            R.layout.item_classification_content, parent, false
        )
        var holder = KtDataBindingViewHolder(binding.root)
        holder.binding = binding
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var bindingHolder = holder as KtDataBindingViewHolder
        var binding = bindingHolder.binding as ItemClassificationContentBinding
        binding.tvTitle.text = getItem(position)!!.title
        binding.rvImgs.layoutManager = GridLayoutManager(mContext, 4)
        var adapter = ClassificationImgsAdapter(mContext)
        binding.rvImgs.adapter = adapter
        adapter.addItems(getItem(position)!!.imgs)

    }


}